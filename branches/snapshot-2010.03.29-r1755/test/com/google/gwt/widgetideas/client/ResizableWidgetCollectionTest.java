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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.ResizableWidgetCollection.ResizableWidgetInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Tests for {@link ResizableWidgetCollection}.
 */
public class ResizableWidgetCollectionTest extends WidgetTestBase {
  /**
   * A {@link ResizableWidget} used for testing.
   */
  private static class MockResizableWidget extends Widget implements
      ResizableWidget {
    private boolean onResizeCalled;

    public MockResizableWidget() {
      setElement(Document.get().createDivElement());
    }

    public void assertOnResizeCalled(boolean expected) {
      assertEquals(expected, onResizeCalled);
    }

    public void onResize(int width, int height) {
      onResizeCalled = true;
    }
  }

  /**
   * Test that a {@link ResizableWidget} adds itself on attach and removes
   * itself on detach. Note that this isn't strictly required, but good practice
   * to ensure performance.
   * 
   * @param widget the widget to test
   */
  public static void testResizableWidget(ResizableWidget widget) {
    testResizableWidget(widget, ResizableWidgetCollection.get());
  }

  /**
   * Test that a {@link ResizableWidget} adds itself on attach and removes
   * itself on detach. Note that this isn't strictly required, but good practice
   * to ensure performance.
   * 
   * @param widget the widget to test
   * @param rwc the collection the should contain the widget
   */
  public static void testResizableWidget(ResizableWidget widget,
      ResizableWidgetCollection rwc) {
    assertNotContains(rwc.iterator(), widget);

    // Add to the page
    RootPanel.get().add((Widget) widget);
    assertContains(rwc.iterator(), widget);

    // Remove from the page
    RootPanel.get().remove((Widget) widget);
    assertNotContains(rwc.iterator(), widget);
  }

  /**
   * Assert that the iterator contains only the specified widgets.
   * 
   * @param iter the iterator to check
   * @param widgets the expected widgets
   */
  private static void assertContains(Iterator<ResizableWidget> iter,
      ResizableWidget... widgets) {
    // Create a map of matches
    Map<ResizableWidget, Boolean> matches = new HashMap<ResizableWidget, Boolean>();
    for (ResizableWidget widget : widgets) {
      matches.put(widget, false);
    }

    // Check the iterator for the matches
    int count = 0;
    while (iter.hasNext()) {
      ResizableWidget cur = iter.next();
      for (ResizableWidget widget : widgets) {
        if (cur == widget) {
          matches.put(widget, true);
        }
      }
      count++;
    }

    // Verify the results
    for (ResizableWidget widget : widgets) {
      assertTrue(matches.get(widget));
    }
  }

  /**
   * Assert that the iterator does not contain the specified widgets.
   * 
   * @param iter the iterator to check
   * @param widgets the widgets that should not be contained
   */
  private static void assertNotContains(Iterator<ResizableWidget> iter,
      ResizableWidget... widgets) {
    while (iter.hasNext()) {
      ResizableWidget cur = iter.next();
      for (ResizableWidget widget : widgets) {
        if (cur == widget) {
          fail("Iterator should not contain this widget");
        }
      }
    }
  }

  @Override
  public String getModuleName() {
    return "com.google.gwt.widgetideas.WidgetIdeas";
  }

  public void testAccessors() {
    ResizableWidgetCollection rwc = new ResizableWidgetCollection();

    // setResizeCheckDelay
    {
      rwc.setResizeCheckDelay(1234);
      assertEquals(1234, rwc.getResizeCheckDelay());
    }

    // setResizeCheckingEnabled
    {
      assertTrue(rwc.isResizeCheckingEnabled());
      rwc.setResizeCheckingEnabled(false);
      assertFalse(rwc.isResizeCheckingEnabled());
      rwc.setResizeCheckingEnabled(true);
      assertTrue(rwc.isResizeCheckingEnabled());
    }

    // Cleanup
    cleanup(rwc);
  }

  /**
   * Test collection methods.
   */
  public void testCollection() {
    ResizableWidgetCollection rwc = new ResizableWidgetCollection();
    MockResizableWidget rw0 = new MockResizableWidget();
    MockResizableWidget rw1 = new MockResizableWidget();
    MockResizableWidget rw2 = new MockResizableWidget();

    // Add when not attached
    rwc.add(rw0);

    // Add when attached
    {
      RootPanel.get().add(rw0);
      RootPanel.get().add(rw1);
      RootPanel.get().add(rw2);
      rwc.add(rw1);
      rwc.add(rw2);
    }

    // Remove
    rwc.remove(rw1);

    // iterator
    {
      Iterator<ResizableWidget> iter = rwc.iterator();
      assertContains(iter, rw0, rw2);
    }

    // Cleanup
    cleanup(rwc);
  }

  public void testResizableWidgetInfo() {
    final int WIDTH = 40;
    final int HEIGHT = 50;

    final MockResizableWidget widget = new MockResizableWidget();
    RootPanel.get().add(widget);
    Element elem = widget.getElement();
    elem.getStyle().setProperty("border", "2px solid black");
    elem.getStyle().setMargin(0.0, Unit.PX);
    elem.getStyle().setPadding(0.0, Unit.PX);
    widget.setPixelSize(WIDTH, HEIGHT);

    // Check initialization.
    final ResizableWidgetInfo info = new ResizableWidgetInfo(widget);
    final int clientWidth = info.getClientWidth();
    final int clientHeight = info.getClientHeight();
    final int offsetWidth = info.getOffsetWidth();
    final int offsetHeight = info.getOffsetHeight();

    // Update with no change.
    assertFalse(info.updateSizes());
    assertEquals(clientWidth, info.getClientWidth());
    assertEquals(clientHeight, info.getClientHeight());
    assertEquals(offsetWidth, info.getOffsetWidth());
    assertEquals(offsetHeight, info.getOffsetHeight());

    // Change the size and verify that it is updated.
    widget.setPixelSize(WIDTH - 2, HEIGHT - 10);
    assertTrue(info.updateSizes());
    assertEquals(clientWidth - 2, info.getClientWidth());
    assertEquals(clientHeight - 10, info.getClientHeight());
    assertEquals(offsetWidth - 2, info.getOffsetWidth());
    assertEquals(offsetHeight - 10, info.getOffsetHeight());

    RootPanel.get().remove(widget);
  }

  /**
   * Test that resize works even if the widget has a board.
   */
  public void testResizeWithBorder() {
    final ResizableWidgetCollection rwc = new ResizableWidgetCollection();
    rwc.setResizeCheckDelay(100);

    // Add a widget with a height of 1px, border of 1px.
    final MockResizableWidget rw = new MockResizableWidget() {
      @Override
      public void onResize(int width, int height) {
        super.onResize(width, height);
        RootPanel.get().remove(this);
        cleanup(rwc);
        finishTest();
      }
    };
    rw.setPixelSize(20, 10);
    rw.getElement().getStyle().setProperty("border", "1px solid red");
    RootPanel.get().add(rw);
    rwc.add(rw);
    rw.assertOnResizeCalled(false);

    // Change the border height, which changes the offsetHeight but not the
    // clientHeight.
    delayTestFinish(5000);
    rw.getElement().getStyle().setProperty("border", "2px solid red");
  }

  private void cleanup(ResizableWidgetCollection rwc) {
    // Get a list of all widgets
    List<ResizableWidget> widgets = new ArrayList<ResizableWidget>();
    Iterator<ResizableWidget> iter = rwc.iterator();
    while (iter.hasNext()) {
      widgets.add(iter.next());
    }

    // Remove each widget
    for (ResizableWidget widget : widgets) {
      rwc.remove(widget);
    }
  }
}
