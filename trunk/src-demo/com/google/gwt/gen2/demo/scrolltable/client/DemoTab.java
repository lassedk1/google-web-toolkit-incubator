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
package com.google.gwt.gen2.demo.scrolltable.client;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A tab used in this demo, which includes a pointer to the scrolltable.
 */
public abstract class DemoTab extends SimplePanel {
  /**
   * Indicates if we've initialized this tab or not.
   */
  private boolean initialized = false;
  
  /**
   * Initialize the tab for the first time.
   */
  protected void initialize() {
    if (initialized) {
      return;
    }
    
    // Get the contents
    Widget w = onInitialize();
    if (w != null) {
      setWidget(w);
    }
  }
  
  /**
   * Render the tab.
   * 
   * @return the widget to add to the tab
   */
  protected abstract Widget onInitialize();
  
  /**
   * This method is called immediately after a widget becomes attached to the
   * browser's document.
   */
  @Override
  protected void onLoad() {
    super.onLoad();
    initialize();
  }
}
