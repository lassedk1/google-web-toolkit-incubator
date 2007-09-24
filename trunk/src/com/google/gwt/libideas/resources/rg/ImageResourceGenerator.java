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
package com.google.gwt.libideas.resources.rg;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.libideas.resources.client.impl.ImageResourcePrototype;
import com.google.gwt.libideas.resources.rebind.ResourceContext;
import com.google.gwt.libideas.resources.rebind.ResourceGenerator;
import com.google.gwt.libideas.resources.rebind.ResourceGeneratorUtil;
import com.google.gwt.user.rebind.SourceWriter;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds an image strip for all ImageResources defined within an
 * ImmutableResourceBundle.
 */
public final class ImageResourceGenerator extends ResourceGenerator {
  private ResourceContext context;
  private ImageBundleBuilder builder;
  private Map externalLocationExpressions;
  private Map externalImageRects;
  private int imageStripCount;

  public void init(ResourceContext context) {
    this.context = context;
    builder = new ImageBundleBuilder();
    externalLocationExpressions = new HashMap();
    externalImageRects = new HashMap();
    imageStripCount = 0;
  }

  public void prepare(JMethod method) throws UnableToCompleteException {
    TreeLogger logger = context.getLogger();
    URL[] resources = ResourceGeneratorUtil.findResources(context, method);

    if (resources.length != 1) {
      logger.log(TreeLogger.ERROR, "Exactly one image may be specified", null);
      throw new UnableToCompleteException();
    }

    URL resource = resources[0];
    String name = method.getName();

    try {
      builder.assimilate(logger, name, resource);
      imageStripCount++;
    } catch (UnsuitableForStripException e) {
      // Add the image to the output as a separate resource
      String urlExpression = context.addToOutput(resource);
      externalLocationExpressions.put(name, urlExpression);
      externalImageRects.put(name, e.getImageRect());
    }
  }

  public void writeAssignment(JMethod method) throws UnableToCompleteException {
    TreeLogger logger = context.getLogger();
    String name = method.getName();

    SourceWriter sw = context.getSourceWriter();
    sw.println("new " + ImageResourcePrototype.class.getName() + "(");
    sw.indent();
    sw.println('"' + name + "\",");

    ImageBundleBuilder.ImageRect rect;
    if (!externalImageRects.containsKey(name)) {
      // This is a reference to a field
      sw.println("imageResourceBundleUrl,");
      rect = builder.getMapping(method.getName());
    } else {
      sw.println(externalLocationExpressions.get(name).toString() + ", ");
      rect = (ImageBundleBuilder.ImageRect) externalImageRects.get(name);
    }

    if (rect == null) {
      logger.log(TreeLogger.ERROR, "No ImageRect ever computed for " + name,
          null);
      throw new UnableToCompleteException();
    }
    
    sw.println(rect.left + ", 0, " + rect.width + ", " + rect.height);

    sw.outdent();
    sw.print(")");
  }

  public void writeFields() throws UnableToCompleteException {
    if (imageStripCount > 0) {
      String bundleUrlExpression = builder.writeBundledImage(context);
  
      SourceWriter sw = context.getSourceWriter();
      sw.println("private String imageResourceBundleUrl = " + bundleUrlExpression
          + ";");
    }
  }
}
