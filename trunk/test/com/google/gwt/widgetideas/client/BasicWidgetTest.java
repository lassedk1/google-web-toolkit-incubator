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

package com.google.gwt.widgetideas.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;

public abstract class BasicWidgetTest extends GWTTestCase {

  public String getModuleName() {
    return "com.google.gwt.widgetideas.BasicWidget";
  }

  public void testBasicCreate() {
    ToggleButton testOne = new ToggleButton("You will never see me");
    HTML hello = new HTML("hello");
    PinnedPanel p = new PinnedPanel(20, testOne, hello);
    RootPanel.get().add(p);
    assertEquals(20, p.getOffsetWidth());
  }
}