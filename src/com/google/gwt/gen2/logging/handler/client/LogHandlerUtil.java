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

import com.google.gwt.gen2.logging.shared.Level;

/**
 * Useful utilities for creating log handlers.
 */
public class LogHandlerUtil {

  /**
   * Gets the style name associated with all predefined levels. This interface
   * is primarily used by widget log handlers to display log levels
   * consistently.
   * 
   * <dl>
   * <dt>Severe</dt>
   * <dd>logSEVERE</dd>
   * <dt>Config</dt>
   * <dd>logCONFIG</dd>
   * <dt>Fine</dt>
   * <dd>logFINE</dd>
   * <dt>. . .</dt>
   * <dd></dd>
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
