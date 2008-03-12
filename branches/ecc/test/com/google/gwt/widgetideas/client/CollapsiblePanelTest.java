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

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * Test for the {@link PinnedPanel}.
 */
public class CollapsiblePanelTest extends BasicWidgetTestBase {
  public void testBasic() {
    int width = 30;
    ToggleButton b = new ToggleButton();
    SimplePanel simple = new SimplePanel();
    CollapsiblePanel p = new CollapsiblePanel(simple);
    p.setWidth(30 + "px");
    RootPanel.get().add(p);
    assertEquals(width, p.getOffsetWidth());
  }
}
