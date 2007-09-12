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
package com.google.gwt.libideas.rebind;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.dev.util.Util;
import com.google.gwt.libideas.client.DataResource;
import com.google.gwt.user.rebind.SourceWriter;

import java.io.IOException;
import java.net.URL;

/**
 * This is a refinement that will use data urls for browsers that support them.
 * Only files whose size are smaller than MAX_INLINE_SIZE will be inlined.
 * Larger files will use the standard CacheBundle behavior.
 * 
 * @see "RFC 2397"
 */
public class InlineResourceBundleGenerator extends
    StaticResourceBundleGenerator {
  /**
   * The largest file size that will be inlined. Note that this value is taken
   * before any encodings are applied.
   */
  // The JLS specifies a maximum size for any string to be 2^16 characters, so
  // we'll leave some padding. Assuming a Base64 encoding, it is true that
  // (2 ^ 15) * 4/3 < 2 ^ 16, so we can safely inline files up to 32k.
  private static final int MAX_INLINE_SIZE = 2 << 15;

  protected void addDataResource(TreeLogger logger, GeneratorContext context,
      String name, URL resource, SourceWriter sw)
      throws UnableToCompleteException {
    byte[] bytes = Util.readURLAsBytes(resource);

    if (bytes.length < MAX_INLINE_SIZE) {
      logger.log(TreeLogger.DEBUG, "Inlining " + resource.getFile(), null);

      // This is bad, but I am lazy and don't want to write _another_ encoder
      sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
      String base64Contents = enc.encode(bytes).replaceAll("\\s+", "");
      String mimeType;
      try {
        mimeType = resource.openConnection().getContentType();
      } catch (IOException e) {
        logger.log(TreeLogger.ERROR, "Unable to open resource "
            + resource.toExternalForm(), e);
        throw new UnableToCompleteException();
      }

      sw.println("new " + DataResource.class.getName() + "() {");
      sw.indent();

      sw.println("public String getName() {");
      sw.indent();
      sw.println("return \"" + name + "\";");
      sw.outdent();
      sw.println("}");

      sw.println("public String getUrl() {");
      sw.indent();
      sw.println("return \"data:" + mimeType + ";base64," + base64Contents
          + "\";");
      sw.outdent();
      sw.println("}");
      
      sw.outdent();
      sw.println("}");

    } else {
      super.addDataResource(logger, context, name, resource, sw);
    }
  }

  protected String generateSimpleSourceName(String sourceType) {
    return sourceType.replaceAll("\\.", "_") + "_inlineBundle";
  }
}
