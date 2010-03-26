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

import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

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
