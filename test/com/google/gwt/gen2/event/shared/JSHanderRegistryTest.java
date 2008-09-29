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

package com.google.gwt.gen2.event.shared;

import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.gen2.event.dom.client.ClickEvent;
import com.google.gwt.gen2.event.dom.client.ClickHandler;
import com.google.gwt.gen2.event.dom.client.MouseDownEvent;
import com.google.gwt.gen2.event.dom.client.MouseDownHandler;

/**
 * Basic tests for Handler registry's. Not much to it as most testing should be
 * in GWTEventsTest.
 */
public class JSHanderRegistryTest extends Gen2TestBase {
  // Most testing is done using server side logging, this is just a liveness
  // check.

  boolean mouseDown1 = false;
  boolean mouseDown2 = false;
  MouseDownHandler mouseDownHandler = new MouseDownHandler() {
    public void onMouseDown(MouseDownEvent event) {
      mouseDown1 = true;
    }
  };

  MouseDownHandler mouseDownHandler2 = new MouseDownHandler() {
    public void onMouseDown(MouseDownEvent event) {
      mouseDown2 = true;
    }
  };

  ClickHandler clickHandler = new ClickHandler() {

    public void onClick(ClickEvent event) {
    }
  };

  public void testAccesors() {
    JsHandlerRegistry registry = JsHandlerRegistry.create();
    registry.addHandler(MouseDownEvent.KEY, mouseDownHandler);
    assertEquals(mouseDownHandler, registry.getHandler(MouseDownEvent.KEY, 0));

    registry.addHandler(ClickEvent.KEY, clickHandler);
    registry.addHandler(MouseDownEvent.KEY, mouseDownHandler2);
    assertEquals(2, registry.getHandlerCount(MouseDownEvent.KEY));
    registry.removeHandler(MouseDownEvent.KEY, mouseDownHandler);

    assertEquals(1, registry.getHandlerCount(MouseDownEvent.KEY));
    assertEquals(1, registry.getHandlerCount(ClickEvent.KEY));
    assertEquals(mouseDownHandler2, registry.getHandler(MouseDownEvent.KEY, 0));
    assertEquals(clickHandler, registry.getHandler(ClickEvent.KEY, 0));
  }

  public void testAdd() {
    JsHandlerRegistry registry = JsHandlerRegistry.create();
    registry.addHandler(MouseDownEvent.KEY, mouseDownHandler);
    assertEquals(1, registry.getHandlerCount(MouseDownEvent.KEY));
  }

  public void testRemove() {
    JsHandlerRegistry registry = JsHandlerRegistry.create();
    registry.addHandler(MouseDownEvent.KEY, mouseDownHandler);
    registry.addHandler(MouseDownEvent.KEY, mouseDownHandler2);

    assertEquals(2, registry.getHandlerCount(MouseDownEvent.KEY));
    registry.removeHandler(MouseDownEvent.KEY, mouseDownHandler2);
    assertEquals(1, registry.getHandlerCount(MouseDownEvent.KEY));

    // Check for correct firing.
    mouseDown1 = false;
    mouseDown2 = false;
    registry.fireEvent(new MouseDownEvent(null));
    assertTrue(mouseDown1);
    assertFalse(mouseDown2);

    registry.removeHandler(MouseDownEvent.KEY, mouseDownHandler);

    // Nothing should happen now.
    mouseDown1 = false;
    mouseDown2 = false;
    registry.fireEvent(new MouseDownEvent(null));
    assertFalse(mouseDown1);
    assertFalse(mouseDown2);

    registry.addHandler(MouseDownEvent.KEY, mouseDownHandler2);
    assertEquals(1, registry.getHandlerCount(MouseDownEvent.KEY));

    // Two should fire
    mouseDown1 = false;
    mouseDown2 = false;
    registry.fireEvent(new MouseDownEvent(null));
    assertFalse(mouseDown1);
    assertTrue(mouseDown2);
  }
}
