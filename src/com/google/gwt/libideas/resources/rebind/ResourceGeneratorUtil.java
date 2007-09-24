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

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JMethod;

import java.net.URL;

/**
 * Utility methods for building ResourceGenerators.
 */
public final class ResourceGeneratorUtil {
  public static final String METADATA_TAG = "gwt.resource";
  public static final String TRANSFORMER_TAG = "gwt.transformer";

  /**
   * Apply Transformers to a resource. The presence of one or more
   * <code>gwt.transformer</code> annotations will specify the
   * {@link Transformer} class to use. Multiple transformations will be applied
   * in their declared order.
   * 
   * @param <T> the Java type that encapsulates the value of the resource
   * @param logger the TreeLogger context
   * @param method the method to examine
   * @param baseType a class literal which specifies the base type
   * @param input the value to transform
   * @return the transformed value.
   * @throws UnableToCompleteException
   */
  public static <T> T applyTransformations(TreeLogger logger, JMethod method,
      Class<T> baseType, T input) throws UnableToCompleteException {
    String[][] md = method.getMetaData(TRANSFORMER_TAG);

    logger =
        logger.branch(TreeLogger.DEBUG, "Applying transformations for method "
            + method.getName(), null);
    
    try {
      for (int i = 0; i < md.length; i++) {
        String className = md[i][0];

        // This may throw a ClassCastException, which we trap below
        Class<? extends Transformer> clazz =
            Class.forName(className).asSubclass(Transformer.class);

        Transformer<?> t = clazz.newInstance();

        // This, too, may throw a ClassCastException.
        Transformer<T> t2 = t.asSubclass(baseType);
        input = t2.transform(method, input);
      }

      return input;
    } catch (ClassCastException e) {
      logger.log(TreeLogger.ERROR, "The class specified by " + TRANSFORMER_TAG
          + " annotation is not a Transformer<" + baseType.getName() + ">",
          null);
    } catch (ClassNotFoundException e) {
      logger.log(TreeLogger.ERROR, "Could not find Transformer", e);
    } catch (IllegalAccessException e) {
      logger.log(TreeLogger.ERROR, "TextTransformers must be public", e);
    } catch (InstantiationException e) {
      logger.log(TreeLogger.ERROR,
          "TextTransformers must have public, no-arg constructors", e);
    }

    throw new UnableToCompleteException();
  }

  public static String baseName(URL resource) {
    String path = resource.getPath();
    return path.substring(path.lastIndexOf('/') + 1);
  }

  public static URL[] findResources(ResourceContext context, JMethod method)
      throws UnableToCompleteException {
    TreeLogger logger =
        context.getLogger().branch(TreeLogger.DEBUG, "Finding resources", null);
    String[][] md = method.getMetaData(METADATA_TAG);

    if (md.length == 0) {
      logger.log(TreeLogger.ERROR, "Method " + method.getName()
          + " does not have any @" + METADATA_TAG + " annotations.", null);
      throw new UnableToCompleteException();
    }

    String locale;
    try {
      PropertyOracle oracle = context.getGeneratorContext().getPropertyOracle();
      locale = oracle.getPropertyValue(logger, "locale");
    } catch (BadPropertyValueException e) {
      locale = null;
    }

    URL[] toReturn = new URL[md.length];

    for (int tagIndex = 0; tagIndex < toReturn.length; tagIndex++) {
      int lastValueIndex = md[tagIndex].length - 1;
      String resourceNameFromMetaData = md[tagIndex][lastValueIndex];

      // Make sure the name is either absolute or package-relative.
      if (resourceNameFromMetaData.indexOf("/") == -1) {
        String pkgName = method.getEnclosingType().getPackage().getName();
        // This construction handles the default package correctly, too.
        resourceNameFromMetaData =
            pkgName.replace('.', '/') + "/" + resourceNameFromMetaData;
      }

      URL resourceURL = tryFindResource(resourceNameFromMetaData, locale);

      if (resourceURL == null) {
        logger.log(TreeLogger.ERROR, "Resource " + resourceNameFromMetaData
            + " not found on classpath. "
            + "Is the name specified as Class.getResource() would expect?",
            null);
        throw new UnableToCompleteException();
      }
      toReturn[tagIndex] = resourceURL;
    }

    // In the future, it would be desirable to be able to automatically
    // determine the resource name to use from the method declaration. We're
    // currently limited by the inability to list the contents of the classpath
    // and not having a set number of file extensions to empirically test.

    return toReturn;
  }

  private static URL tryFindResource(String resourceName, String locale) {
    URL toReturn = null;
    ClassLoader cl = ClassLoader.getSystemClassLoader();

    // Look for locale-specific variants of individual resources
    if (locale != null) {
      // Convert language_country_variant to independent pieces
      String[] localeSegments = locale.split("_");
      int lastDot = resourceName.lastIndexOf(".");
      String prefix = resourceName.substring(0, lastDot);
      String extension = resourceName.substring(lastDot);

      for (int i = localeSegments.length - 1; i >= -1; i--) {
        String localeInsert = "";
        for (int j = 0; j <= i; j++) {
          localeInsert += "_" + localeSegments[j];
        }

        toReturn = cl.getResource(prefix + localeInsert + extension);
        if (toReturn != null) {
          break;
        }
      }
    }

    return toReturn;
  }

  private ResourceGeneratorUtil() {
  }
}
