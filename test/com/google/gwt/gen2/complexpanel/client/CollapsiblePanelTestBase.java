/*
 * Copyright 2009 Google Inc.
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
package com.google.gwt.gen2.complexpanel.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.gen2.complexpanel.client.CollapsiblePanel.State;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * Test base for collapsible panel tests.
 */
public abstract class CollapsiblePanelTestBase extends GWTTestCase {

  @Override
  protected void gwtTearDown() throws Exception {
    RootPanel.get().clear();
  }

  @Override
  public String getModuleName() {
    return Gen2TestBase.MODULE;
  }

  public void testComputeSizeWhileWillShow() {
    // Create thep panel.
    SimplePanel simple = new SimplePanel();
    simple.setSize("30px", "30px");
    final CollapsiblePanel p = new CollapsiblePanel(simple);
    // Need to set the height to 1px, or the width will be 0px
    p.setHeight("1px");
    p.getHoverBar().setPixelSize(100, 100);
    p.setCollapsedState(true);
    RootPanel.get().add(p);
  
    // Hook a toggle button.
    p.hookupControlToggle(new ToggleButton("Toggle"));
  
    // Fire an event on the hover bar.
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

  public void testFromCollapsedState() {
    CollapsiblePanel collapsed = new CollapsiblePanel();
    HTML bar = new HTML("|");
    bar.setWidth("30px");
    collapsed.setHoverBarContents(bar);
    collapsed.setCollapsedState(true);
    HTML html = new HTML(
        "This is very long content which should not effect the width");
    html.setWidth("600px");
    collapsed.add(html);
    RootPanel.get().add(collapsed);
    assertEquals(31, collapsed.getOffsetWidth());

    collapsed.setCollapsedState(false);
    assertEquals(600, collapsed.getOffsetWidth());
  }

  public void testFromExpandedState() {
    CollapsiblePanel expanded = new CollapsiblePanel();
    expanded.setWidth("200px");
    HTML bar = new HTML("|");
    bar.setWidth("30");
    expanded.setHoverBarContents(bar);
    expanded.add(new HTML("collapedContent"));
    RootPanel.get().add(expanded);
    assertEquals(200, expanded.getOffsetWidth());
    expanded.setCollapsedState(true);

    // We had to add one to the width to get around an IE rtl mode bug.
    assertEquals(31, expanded.getOffsetWidth());
  }

  public void testWidthAndHeightResetWithinCollapsedState() {
    CollapsiblePanel collapsed = new CollapsiblePanel();
    collapsed.getHoverBar().setPixelSize(100, 100);
    collapsed.setCollapsedState(true);
    collapsed.add(new HTML(
        "This is very long content which should not effect the width"));
    collapsed.setHoverBarWidth("30px");
    RootPanel.get().add(collapsed);
    collapsed.setWidth("400px");
    assertEquals(31, collapsed.getOffsetWidth());
    collapsed.setCollapsedState(false);
    assertEquals(400, collapsed.getOffsetWidth());
  }

  public void testWidthAndHeightResetWithinExpandedState() {
    CollapsiblePanel collapsed = new CollapsiblePanel();
    collapsed.getHoverBar().setPixelSize(100, 100);
    collapsed.setHoverBarWidth("30px");
    collapsed.add(new HTML(
        "This is very long content which should not effect the width"));
    RootPanel.get().add(collapsed);
    collapsed.setWidth("400px");
    assertEquals(400, collapsed.getOffsetWidth());
    collapsed.setCollapsedState(true);
    collapsed.setHoverBarWidth("34px");
    assertEquals(35, collapsed.getOffsetWidth());
    collapsed.setHoverBarWidth("10px");
    assertEquals(11, collapsed.getOffsetWidth());
  }
}
