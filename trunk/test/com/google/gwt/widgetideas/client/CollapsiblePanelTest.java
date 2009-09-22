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

package com.google.gwt.widgetideas.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.widgetideas.client.CollapsiblePanel.State;

/**
 * Test for the {@link PinnedPanel}.
 */
public class CollapsiblePanelTest extends WidgetTestBase {
  public void testBasic() {
    int width = 30;
    SimplePanel simple = new SimplePanel();
    simple.setSize("30px", "30px");
    CollapsiblePanel p = new CollapsiblePanel(simple);
    // Need to set the height to 1px, or the width will be 0px
    p.setHeight("1px");
    RootPanel.get().add(p);
    assertEquals(width, p.getOffsetWidth());
  }

  public void testRefreshWidthWithTimer() {
    // Create the panel.
    SimplePanel simple = new SimplePanel();
    simple.setSize("30px", "30px");
    final CollapsiblePanel p = new CollapsiblePanel(simple);
    // Need to set the height to 1px, or the width will be 0px
    p.setHeight("1px");
    p.becomeCollapsed();
    RootPanel.get().add(p);

    // Hook a toggle button.
    p.hookupControlToggle(new ToggleButton("Toggle"));

    // Fire an event on the hover bar that will trigger a delayed timer.
    NativeEvent event = Document.get().createMouseOverEvent(0, 0, 0, 0, 0,
        false, false, false, false, 0, null);
    p.getHoverBar().getElement().dispatchEvent(event);
    assertEquals(State.WILL_SHOW, p.getState());

    // Programatically force refreshWidth to be called.
    p.setWidth("100px");
    assertEquals(State.IS_HIDDEN, p.getState());

    // Verify the delay show timer never fires.
    new Timer() {
      @Override
      public void run() {
        assertEquals(State.IS_HIDDEN, p.getState());
        finishTest();
      }
    }.schedule(1000);
    delayTestFinish(2000);
  }
}
