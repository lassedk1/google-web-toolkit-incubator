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

package com.google.gwt.gen2.logging.handler.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.logging.shared.Level;
import com.google.gwt.gen2.widgetbase.client.Gen2CssInjector;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

/**
 * Useful utilities for creating log handlers.
 */
public class LogHandlerUtil {
  /**
   * CSS resource.
   */
  static interface Resources extends ImmutableResourceBundle {
    @Resource("com/google/gwt/gen2/widgetbase/public/LogHandlers.css")
    CssResource css();
  }

  /**
   * Gets the style name associated with all predefined levels. This interface
   * is primarily used by widget log handlers to display log levels
   * consistently.
   * 
   * <dl>
   * <dt>Severe</dt><dd>logSEVERE</dd>
   * <dt>Config</dt><dd>logCONFIG</dd>
   * <dt>Fine</dt><dd>logFINE</dd>
   * <dt>. . .</dt><dd></dd>
   * </dl>
   * 
   * 
   * In other words, uses "log" + name of level to calculate the level name.
   * 
   * @param level level
   * 
   * @return style name
   */
  public static String getStyle(Level level) {
    return "log" + level.getName();
  }

  /**
   * Injects the default css used for web log handlers.
   */
  public static void injectDefaultCss() {
    if (Gen2CssInjector.isInjectionEnabled()) {
      CssResource css = ((Resources) GWT.create(Resources.class)).css();
      StyleInjector.injectStylesheet(css.getText());
    }
  }

  /**
   * Splits the category into its component pieces with "." as the separator.
   * 
   * @param category base category
   * @return split category
   */
  public static String[] splitCategory(String category) {
    if (category == null) {
      return new String[0];
    } else {
      return category.split("[.]");
    }
  }

  private LogHandlerUtil() {
  }
}
