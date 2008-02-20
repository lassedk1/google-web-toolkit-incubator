/*
 * Copyright 2008 Google Inc.
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

package com.google.gwt.libideas.linker;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.dev.linker.Linker;
import com.google.gwt.dev.linker.LinkerContext;
import com.google.gwt.dev.linker.SelectionProperty;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Produces a list of locales as a linker artifact for use by server-side code.
 * 
 * The locale list, which will include the default locale, will be stored in a
 * text file locale-list.txt in the linker output directory (which will be based
 * on the name used in the define-linker directive).
 * 
 * Example .gwt.xml usage:
 * <pre>
 *  <define-linker name="localeList" class="com.google.gwt.libideas.linker.LocaleListLinker"/>
 *  <set-linker name="std,localeList"/>
 * </pre>
 */
public class LocaleListLinker extends Linker {

  /**
   * Output file name.
   */
  private static final String LOCALE_LIST_OUTPUT_FILE = "locale-list.txt";

  @Override
  public String getDescription() {
    return "LocaleListLinker";
  }

  @Override
  public void link(TreeLogger logger, LinkerContext context)
      throws UnableToCompleteException {
    for (SelectionProperty property : context.getProperties()) {
      if (property.getName().equals("locale")) {
        // Create output file.
        OutputStream str = context.tryCreateArtifact(logger, LOCALE_LIST_OUTPUT_FILE);
        OutputStreamWriter writer;
        try {
          writer = new OutputStreamWriter(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
          logger.log(TreeLogger.ERROR, "UTF-8 encoding not supported", e);
          throw new UnableToCompleteException();
        }
        
        // Print supported locales (including default), one per line.
        PrintWriter print = new PrintWriter(writer);
        for (String locale : property.getPossibleValues()) {
          print.println(locale);
        }
        
        print.close();
        try {
          writer.close();
        } catch (IOException e) {
          logger.log(TreeLogger.ERROR, "I/O error writing locale list", e);
          throw new UnableToCompleteException();
        }
        context.commit(logger, str);
        return;
      }
    }
    logger.log(
        TreeLogger.WARN,
        "LocaleListLinker - No locale property defined, did you inherit the i18n module?",
        null);
  }
}
