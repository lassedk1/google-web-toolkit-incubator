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
import com.google.gwt.user.client.ui.Widget;

/**
 * An impl class that provides basic functionally to support the standard
 * handling of css interface classes.
 * 
 * Applications that care about Css size and performance should use css
 * resources.
 * 
 * @param <WidgetType> the type of widget
 */
public class StandardCssImpl<WidgetType extends Widget> implements WidgetCss {
  private String baseName;

  /**
   * Constructor.
   */
  public StandardCssImpl(String baseStyleName) {
    this.baseName = baseStyleName;
  }

  /**
   * Gets the style name. Conforming to Constant/CssResource pattern of omitting
   * the "get".
   * 
   * @return the style name.
   */
  public String baseName() {
    return baseName;
  }

  /**
   * Sets the base name.
   */
  protected void setBaseStyleName(String style) {
    baseName = style;
  }

  /**
   * Prepends the base name to the given style.
   * 
   * @param style style name
   * @return style name
   */
  protected String wrap(String styleName) {
    return baseName + "-" + styleName;
  }

  /**
   * Creates the given dependent style name.
   * 
   * @param dependent dependent style name
   * @return style name
   */
  protected String wrap(String base, String dependent) {
    return baseName + "-" + base + "-" + dependent;
  }
}