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

package com.google.gwt.gen2.commonwidget.client.impl;

import com.google.gwt.gen2.widgetbase.client.WidgetCss;

/**
 * An impl class that provides basic functionally to support the standard
 * handling of css interface classes.
 * 
 * Applications that care about Css size and performance should use css
 * resources.
 * 
 */
public class StandardCssImpl implements WidgetCss {
  private String baseName;
  private String widgetName;

  /**
   * Constructor.
   */
  public StandardCssImpl(String baseStyleName) {
    setBaseStyleName(baseStyleName);
  }

  /**
   * Gets the style name.
   * 
   * @return the style name.
   */
  public String getBaseStyleName() {
    return baseName;
  }

  // Bogus impl of css resources getName.
  public String getName() {
    return null;
  }

  // Bogus impl of css resources getText.
  public String getText() {
    return null;
  }

  public String getWidgetStyleName() {
    return widgetName;
  }

  /**
   * Sets the base name.
   */
  protected void setBaseStyleName(String style) {
    if (style == null || style.length() == 0) {
      assert (false) : "Should not have a null style name";
    }
    widgetName = style;
    int find = Math.max(0, style.lastIndexOf("-"));
    if (find == style.length() - 1) {
      baseName = "";
    } else {
      String lowerCaseFirstLetter = style.substring(find + 1, find + 2).toLowerCase();
      baseName = lowerCaseFirstLetter + style.substring(find + 2);
    }
  }

  /**
   * Prepends the base name to the given style.
   * 
   * @param style style name
   * @return style name
   */
  protected String wrap(String styleName) {
    return baseName + styleName;
  }

}