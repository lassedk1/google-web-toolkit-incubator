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

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Tests for {@link ResizableWidgetCollection}.
 */
public class ResizableWidgetCollectionTest extends WidgetTestBase {
  /**
   * A {@link ResizableWidget} used for testing.
   */
  private static class MockResizableWidget implements ResizableWidget {
    private boolean isAttached;

    public Element getElement() {
      return null;
    }

    public boolean isAttached() {
      return isAttached;
    }

    public void onResize(int width, int height) {
    }

    public void setAttached(boolean isAttached) {
      this.isAttached = isAttached;
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
      rw0.setAttached(true);
      rw1.setAttached(true);
      rw2.setAttached(true);
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
  }
}
