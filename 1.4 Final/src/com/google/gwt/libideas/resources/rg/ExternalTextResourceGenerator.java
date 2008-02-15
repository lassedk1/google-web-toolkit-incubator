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

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.dev.util.Util;
import com.google.gwt.libideas.resources.client.TextResource;
import com.google.gwt.libideas.resources.client.impl.ExternalTextResourcePrototype;
import com.google.gwt.libideas.resources.rebind.ResourceContext;
import com.google.gwt.libideas.resources.rebind.ResourceGenerator;
import com.google.gwt.libideas.resources.rebind.ResourceGeneratorUtil;
import com.google.gwt.user.rebind.SourceWriter;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Adds {@link ExternalTextResourcePrototype} objects to the bundle.
 */
public final class ExternalTextResourceGenerator extends ResourceGenerator {
  private ResourceContext context;
  private StringBuffer data;
  private boolean first;
  private String urlExpression;
  private Map<String, Integer> hashes;
  private Map<String, Integer> offsets;
  private int currentIndex;

  @Override
  public void init(TreeLogger logger, ResourceContext context)
      throws UnableToCompleteException {
    this.context = context;
    data = new StringBuffer("[\n");
    first = true;
    urlExpression = null;
    hashes = new HashMap<String, Integer>();
    offsets = new HashMap<String, Integer>();
    currentIndex = 0;
  }

  @Override
  public void prepare(TreeLogger logger, JMethod method)
      throws UnableToCompleteException {

    URL[] urls = ResourceGeneratorUtil.findResources(logger, context, method);

    if (urls.length != 1) {
      logger.log(TreeLogger.ERROR, "Exactly one resource must be specified",
          null);
      throw new UnableToCompleteException();
    }

    URL resource = urls[0];

    TreeLogger transformLogger = logger.branch(TreeLogger.DEBUG,
        "Applying Transformers", null);
    String toWrite = ResourceGeneratorUtil.applyTransformations(
        transformLogger, method, String.class, new String(
            Util.readURLAsChars(resource)));

    // This de-duplicates strings in the bundle.
    if (!hashes.containsKey(toWrite)) {
      hashes.put(toWrite, currentIndex++);

      if (!first) {
        data.append(",\n");
      } else {
        first = false;
      }

      data.append('"');
      data.append(Generator.escape(toWrite));
      data.append('"');
    }

    // Store the (possibly n:1) mapping of resource function to bundle index.
    offsets.put(method.getName(), hashes.get(toWrite));
  }

  @Override
  public void writeAssignment(TreeLogger logger, JMethod method)
      throws UnableToCompleteException {
    String name = method.getName();

    SourceWriter sw = context.getSourceWriter();
    sw.println("new " + ExternalTextResourcePrototype.class.getName() + "(");
    sw.indent();
    sw.println('"' + name + "\",");
    // These are field names
    sw.println("EXTERNAL_TEXT_URL, EXTERNAL_TEXT_CACHE,");
    sw.println(offsets.get(method.getName()).toString());
    sw.outdent();
    sw.print(")");
  }

  @Override
  public void writeFields(TreeLogger logger) throws UnableToCompleteException {
    data.append(']');

    urlExpression = context.addToOutput(
        context.getResourceBundleType().getQualifiedSourceName().replace('.',
            '_')
            + "_jsonbundle.txt", "text/plain", data.toString().getBytes(), true);

    SourceWriter sw = context.getSourceWriter();
    sw.println("final String EXTERNAL_TEXT_URL = " + urlExpression + ";");
    sw.println("final " + TextResource.class.getName()
        + "[] EXTERNAL_TEXT_CACHE = new " + TextResource.class.getName() + "["
        + currentIndex + "];");
  }
}
