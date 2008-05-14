/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.libideas.resources.rebind;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.libideas.resources.client.ResourceGeneratorType;
import com.google.gwt.libideas.resources.client.ResourcePrototype;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base class for creating new ResourceBundle implementations.
 */
public abstract class AbstractResourceBundleGenerator extends Generator {

  public AbstractResourceBundleGenerator() {
    super();
  }

  public final String generate(TreeLogger logger, GeneratorContext context,
      String typeName) throws UnableToCompleteException {

    // The TypeOracle knows about all types in the type system
    final TypeOracle typeOracle = context.getTypeOracle();

    // Get a reference to the type that the generator should implement
    final JClassType sourceType = typeOracle.findType(typeName);

    // Ensure that the requested type exists
    if (sourceType == null) {
      logger.log(TreeLogger.ERROR, "Could not find requested typeName", null);
      throw new UnableToCompleteException();
    }

    String locale;
    try {
      locale = context.getPropertyOracle().getPropertyValue(logger, "locale");
    } catch (BadPropertyValueException e) {
      // Don't care, likely the user isn't using localization.
      locale = "";
    }

    // Pick a name for the generated class to not conflict. Enclosing class
    // names must be preserved.
    final String generatedSimpleSourceName = generateSimpleSourceName(logger,
        context.getPropertyOracle(), sourceType.getName())
        + locale;

    // Begin writing the generated source.
    final ClassSourceFileComposerFactory f = new ClassSourceFileComposerFactory(
        sourceType.getPackage().getName(), generatedSimpleSourceName);

    // The generated class needs to be able to determine the module base URL
    f.addImport(GWT.class.getName());
    f.addImport(ResourcePrototype.class.getName());

    // Determine the interface to implement
    if (sourceType.isInterface() != null) {
      f.addImplementedInterface(sourceType.getQualifiedSourceName());

    } else {
      // The incoming type wasn't a plain interface, we don't support
      // abstract base classes
      logger.log(TreeLogger.ERROR, sourceType.getQualifiedSourceName()
          + " is not an interface.", null);
      throw new UnableToCompleteException();
    }

    // All source gets written through this Writer
    final PrintWriter out = context.tryCreate(logger,
        sourceType.getPackage().getName(), generatedSimpleSourceName);

    // Aggregates the field names of the resources for use with
    // ResourceBundle.getResources()
    List<String> fieldNames = new ArrayList<String>();

    // If an implementation already exists, we don't need to do any work
    if (out != null) {

      // We really use a SourceWriter since it's convenient
      final SourceWriter sw = f.createSourceWriter(context, out);

      JMethod[] methods = sourceType.getMethods();

      Map<Class<? extends ResourceGenerator>, List<JMethod>> resourceGenerators = new HashMap<Class<? extends ResourceGenerator>, List<JMethod>>();

      ResourceContext resourceContext = createResourceContext(logger, context,
          sourceType, generatedSimpleSourceName, sw);

      // First assemble all of the ResourceGenerators that we may need for the
      // type
      for (JMethod m : methods) {
        JClassType returnType = m.getReturnType().isClassOrInterface();
        if (returnType == null) {
          logger.log(TreeLogger.ERROR, "Cannot implement " + m.getName()
              + ": not a class or interface.", null);
          throw new UnableToCompleteException();
        }

        Class<? extends ResourceGenerator> clazz = findResourceGenerator(
            logger, typeOracle, m);
        List<JMethod> generatorMethods;
        if (resourceGenerators.containsKey(clazz)) {
          generatorMethods = resourceGenerators.get(clazz);
        } else {
          generatorMethods = new ArrayList<JMethod>();
          resourceGenerators.put(clazz, generatorMethods);
        }

        generatorMethods.add(m);
      }

      // Run the ResourceGenerator code
      for (Map.Entry<Class<? extends ResourceGenerator>, List<JMethod>> entry : resourceGenerators.entrySet()) {
        Class<? extends ResourceGenerator> generatorClass = entry.getKey();
        List<JMethod> generatorMethods = entry.getValue();

        // Create the ResourceGenerator
        ResourceGenerator rg;
        try {
          rg = generatorClass.newInstance();
          rg.init(logger.branch(TreeLogger.DEBUG,
              "Initializing ResourceGenerator", null), resourceContext);
        } catch (InstantiationException e) {
          logger.log(TreeLogger.ERROR,
              "Unable to initialize ResourceGenerator", e);
          throw new UnableToCompleteException();
        } catch (IllegalAccessException e) {
          logger.log(TreeLogger.ERROR,
              "Unable to instantiate ResourceGenerator. "
                  + "Does it have a public default constructor?", e);
          throw new UnableToCompleteException();
        }

        // Prepare the ResourceGenerator by telling it all methods that it is
        // expected to produce.
        for (JMethod m : generatorMethods) {
          rg.prepare(logger.branch(TreeLogger.DEBUG, "Preparing method "
              + m.getName(), null), m);
        }

        // Write all field values
        rg.writeFields(logger.branch(TreeLogger.DEBUG, "Writing fields", null));

        // Create the instance variables in the IRB subclass by calling
        // writeAssignment() on the ResourceGenerator
        for (JMethod m : generatorMethods) {
          // Strip off all but the access modifiers
          sw.print(m.getReadableDeclaration(false, true, true, true, true));
          sw.println(" {");
          sw.indent();

          String fieldName = (m.getName() + "_instance").toUpperCase();
          fieldNames.add(fieldName);
          sw.println("return " + fieldName + ";");

          sw.outdent();
          sw.println("}");

          sw.print("private final "
              + m.getReturnType().getQualifiedSourceName() + " " + fieldName
              + " = ");

          rg.writeAssignment(logger.branch(TreeLogger.DEBUG,
              "Writing assignment for " + m.getName(), null), m);

          sw.println(";");
        }

        // Finalize the ResourceGenerator
        rg.finish(logger.branch(TreeLogger.DEBUG,
            "Finishing ResourceGenerator", null));
      }

      // Complete the IRB contract
      sw.println("public ResourcePrototype[] getResources() {");
      sw.indent();
      sw.println("return new ResourcePrototype[] {");
      sw.indent();
      for (String fieldName : fieldNames) {
        sw.println(fieldName + ",");
      }
      sw.outdent();
      sw.println("};");
      sw.outdent();
      sw.println("}");

      // Allow map-style lookup
      sw.println("public native ResourcePrototype "
          + "getResource(String name) /*-{");
      sw.indent();
      sw.println("switch (name) {");
      sw.indent();
      for (JMethod m : methods) {
        sw.println("case '" + m.getName() + "': return this.@"
            + f.getCreatedClassName() + "::"
            + (m.getName() + "_instance").toUpperCase() + ";");
      }
      sw.outdent();
      sw.println("}");
      sw.println("return null;");
      sw.outdent();
      sw.println("}-*/;");

      // Write the generated code to disk
      sw.commit(logger);
    }

    // Return the name of the concrete class
    return f.getCreatedClassName();
  }

  /**
   * Create the ResourceContext object that will be used by
   * {@link ResourceGenerator} subclasses. This is the primary way to implement
   * custom logic in the resource generation pass.
   */
  protected abstract ResourceContext createResourceContext(TreeLogger logger,
      GeneratorContext context, JClassType resourceBundleType,
      String simpleSourceName, SourceWriter sw);

  /**
   * Given a user-defined type name, determine the type name for the generated
   * class.
   */
  protected abstract String generateSimpleSourceName(TreeLogger logger,
      PropertyOracle oracle, String sourceType)
      throws UnableToCompleteException;

  /**
   * Given a JMethod, find the a ResourceGenerator class that will be able to
   * provide an implementation of the method.
   */
  private Class<? extends ResourceGenerator> findResourceGenerator(
      TreeLogger logger, TypeOracle typeOracle, JMethod method)
      throws UnableToCompleteException {

    JClassType resourceType = method.getReturnType().isClassOrInterface();
    ResourceGeneratorType generatorType = resourceType.getAnnotation(ResourceGeneratorType.class);

    // Look at the implemented interfaces for a ResourceGenerator annotation
    if (generatorType == null) {
      for (JClassType t : resourceType.getImplementedInterfaces()) {
        generatorType = t.getAnnotation(ResourceGeneratorType.class);

        if (generatorType != null) {
          break;
        }
      }
    }

    if (generatorType == null) {
      logger.log(TreeLogger.ERROR, "No @"
          + ResourceGeneratorType.class.getName()
          + " was specifed for resource type "
          + resourceType.getQualifiedSourceName() + " or its supertypes");
      throw new UnableToCompleteException();
    }

    String className = generatorType.value();
    try {
      return Class.forName(className).asSubclass(ResourceGenerator.class);
    } catch (ClassCastException e) {
      logger.log(TreeLogger.ERROR, className + " is not a "
          + ResourceGenerator.class.getName());
    } catch (ClassNotFoundException e) {
      logger.log(TreeLogger.ERROR, "Could not load " + className, e);
    }

    throw new UnableToCompleteException();
  }
}