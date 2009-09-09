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
package com.google.gwt.gen2.widgetbase.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Test methods for {@link Gen2Widget}.
 */
public class Gen2WidgetTest extends Gen2TestBase {
  /**
   * A Mock widget used for testing.
   */
  @SuppressWarnings("deprecation")
  private static class MockWidget extends Gen2Widget implements
      HasClickHandlers, com.google.gwt.gen2.event.dom.client.HasClickHandlers {
    public MockWidget() {
      setElement(Document.get().createDivElement());
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
      return addDomHandler(handler, ClickEvent.getType());
    }

    @SuppressWarnings("deprecation")
    public com.google.gwt.gen2.event.shared.HandlerRegistration addClickHandler(
        com.google.gwt.gen2.event.dom.client.ClickHandler handler) {
      return addDomHandler(
          com.google.gwt.gen2.event.dom.client.ClickEvent.TYPE, handler);
    }
  }

  /**
   * Test DOM handlers in the Widget class.
   */
  public void testDomHandlers() {
    MockWidget widget = new MockWidget();
    RootPanel.get().add(widget);

    // Add a click handler
    final List<String> ret = new ArrayList<String>();
    widget.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        ret.add("clicked");
      }
    });

    // Fire an event on the element
    NativeEvent clickEvent = Document.get().createClickEvent(0, 0, 0, 0, 0,
        false, false, false, false);
    widget.getElement().dispatchEvent(clickEvent);
    assertEquals(1, ret.size());
    RootPanel.get().remove(widget);
  }

  /**
   * Test DOM handlers in the {@link Gen2Widget} class.
   */
  @SuppressWarnings("deprecation")
  public void testLegacyDomHandlers() {
    MockWidget widget = new MockWidget();
    RootPanel.get().add(widget);

    // Add a click handler
    final List<String> ret = new ArrayList<String>();
    widget.addClickHandler(new com.google.gwt.gen2.event.dom.client.ClickHandler() {
      public void onClick(com.google.gwt.gen2.event.dom.client.ClickEvent event) {
        ret.add("clicked");
      }
    });

    // Fire an event on the element
    NativeEvent clickEvent = Document.get().createClickEvent(0, 0, 0, 0, 0,
        false, false, false, false);
    widget.getElement().dispatchEvent(clickEvent);
    assertEquals(1, ret.size());
    RootPanel.get().remove(widget);
  }
}
