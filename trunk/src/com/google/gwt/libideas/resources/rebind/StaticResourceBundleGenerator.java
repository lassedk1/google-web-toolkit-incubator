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
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.libideas.resources.client.DataResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;
import com.google.gwt.libideas.resources.client.TextResource;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Copies selected files into module output with strong names and generates the
 * ResourceBundle mappings.
 */
public class StaticResourceBundleGenerator extends Generator {

  public String generate(TreeLogger logger, GeneratorContext context,
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
    final String generatedSimpleSourceName =
        generateSimpleSourceName(sourceType.getName()) + locale;

    // Begin writing the generated source.
    final ClassSourceFileComposerFactory f =
        new ClassSourceFileComposerFactory(sourceType.getPackage().getName(),
            generatedSimpleSourceName);

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
    final PrintWriter out =
        context.tryCreate(logger, sourceType.getPackage().getName(),
            generatedSimpleSourceName);

    // Aggregates the field names of the resources for use with
    // ResourceBundle.getResources()
    List fieldNames = new ArrayList();

    // If an implementation already exists, we don't need to do any work
    if (out != null) {

      // We really use a SourceWriter since it's convenient
      final SourceWriter sw = f.createSourceWriter(context, out);

      JMethod[] methods = sourceType.getMethods();

      Map /* <JClassType, ResourceGenerator> */resourceGenerators =
          new HashMap();

      ResourceContext resourceContext =
          createResourceContext(logger, context, sourceType, sw);

      // First assemble all of the ResourceGenerators that we may need for the
      // type
      for (int i = 0; i < methods.length; i++) {
        JMethod m = methods[i];
        JClassType returnType = m.getReturnType().isClassOrInterface();
        if (returnType == null) {
          logger.log(TreeLogger.ERROR, "Cannot implement " + m.getName()
              + ": not a class or interface.", null);
          throw new UnableToCompleteException();
        }

        if (!resourceGenerators.containsKey(returnType)) {
          ResourceGenerator rg =
              findResourceGenerator(logger, typeOracle, returnType);
          rg.init(resourceContext);
          resourceGenerators.put(returnType, rg);
        }
      }

      // Now run the ResourceGenerators
      for (int i = 0; i < methods.length; i++) {
        JMethod m = methods[i];

        // Strip off all but the access modifiers
        sw.print(m.getReadableDeclaration(false, true, true, true, true));
        sw.println(" {");
        sw.indent();

        String fieldName = (m.getName() + "_instance").toUpperCase();
        fieldNames.add(fieldName);
        sw.println("return " + fieldName + ";");

        sw.outdent();
        sw.println("}");

        sw.print("private static final "
            + m.getReturnType().getQualifiedSourceName() + " " + fieldName
            + " = ");

        ResourceGenerator rg =
            (ResourceGenerator) resourceGenerators.get(m.getReturnType()
                .isClassOrInterface());
        rg.writeAssignment(m);

        sw.println(";");
      }

      // Allow ResourceGenerators to clean up
      Set resourceGeneratorSet = new HashSet(resourceGenerators.values());
      for (Iterator i = resourceGeneratorSet.iterator(); i.hasNext();) {
        ((ResourceGenerator) i.next()).finish();
      }

      sw.println("public ResourcePrototype[] getResources() {");
      sw.indent();
      sw.println("return new ResourcePrototype[] {");
      sw.indent();
      for (Iterator i = fieldNames.iterator(); i.hasNext();) {
        sw.println(i.next().toString() + ",");
      }
      sw.outdent();
      sw.println("};");
      sw.outdent();
      sw.println("}");
      
      // Write the generated code to disk
      sw.commit(logger);
    }

    // Return the name of the concrete class
    return f.getCreatedClassName();
  }

  protected ResourceContext createResourceContext(TreeLogger logger,
      GeneratorContext context, JClassType resourceBundleType, SourceWriter sw) {
    return new StaticResourceContext(logger, context, resourceBundleType, sw);
  }

  protected ResourceGenerator findResourceGenerator(TreeLogger logger,
      TypeOracle typeOracle, JClassType resourceType)
      throws UnableToCompleteException {
    // TODO: Make this function more dynamic
    JClassType dataType =
        typeOracle.findType(DataResource.class.getName()).isInterface();
    JClassType textType =
        typeOracle.findType(TextResource.class.getName()).isInterface();

    if (resourceType.isAssignableTo(dataType)) {
      return new DataResourceGenerator();
    } else if (resourceType.isAssignableTo(textType)) {
      return new TextResourceGenerator();
    } else {
      logger.log(TreeLogger.ERROR, "No ResourceGenerator for type "
          + resourceType.getQualifiedSourceName(), null);
      throw new UnableToCompleteException();
    }
  }

  /**
   * Given a user-defined type name, determine the type name for the generated
   * class.
   */
  protected String generateSimpleSourceName(String sourceType) {
    return sourceType.replaceAll("\\.", "_") + "_externalBundle";
  }
}
