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

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.libideas.resources.client.impl.ImageResourcePrototype;
import com.google.gwt.user.rebind.SourceWriter;

import java.net.URL;

/**
 * Builds an image strip for all ImageResources defined within an
 * ImmutableResourceBundle.
 */
public class ImageResourceGenerator extends ResourceGenerator {
  ResourceContext context;
  ImageBundleBuilder builder;

  public void init(ResourceContext context) {
    this.context = context;
    builder = new ImageBundleBuilder();
  }

  public void prepare(JMethod method) throws UnableToCompleteException {
    TreeLogger logger = context.getLogger();
    URL[] resources = ResourceGeneratorUtil.findResources(context, method);

    if (resources.length != 1) {
      logger.log(TreeLogger.ERROR, "Exactly one image may be specified", null);
      throw new UnableToCompleteException();
    }

    URL resource = resources[0];

    builder.assimilate(logger, method.getName(), resource);
  }

  public void writeAssignment(JMethod method) throws UnableToCompleteException {
    String name = method.getName();

    SourceWriter sw = context.getSourceWriter();
    sw.println("new " + ImageResourcePrototype.class.getName() + "(");
    sw.indent();
    sw.println('"' + name + "\",");

    // This is a reference to a field
    sw.println("imageResourceBundleUrl,");

    ImageBundleBuilder.ImageRect rect = builder.getMapping(method.getName());
    sw.println(rect.left + ", 0, " + rect.width + ", " + rect.height);

    sw.outdent();
    sw.print(")");
  }

  public void writeFields() throws UnableToCompleteException {
    String bundleUrlExpression = builder.writeBundledImage(context);

    SourceWriter sw = context.getSourceWriter();
    sw.println("private static final String imageResourceBundleUrl = "
        + bundleUrlExpression + ";");
  }
}
