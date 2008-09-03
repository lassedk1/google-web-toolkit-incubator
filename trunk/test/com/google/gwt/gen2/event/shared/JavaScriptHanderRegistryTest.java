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

import com.google.gwt.gen2.client.Gen2TestBase;
import com.google.gwt.gen2.event.dom.client.ClickEvent;
import com.google.gwt.gen2.event.dom.client.ClickHandler;
import com.google.gwt.gen2.event.dom.client.MouseDownEvent;
import com.google.gwt.gen2.event.dom.client.MouseDownHandler;

/**
 * Basic tests for Handler registry's. Not much to it as most testing should be
 * in GWTEventsTest.
 */
public class JavaScriptHanderRegistryTest extends Gen2TestBase {
  // Most testing is done using server side logging, this is just a liveness
  // check.

  MouseDownHandler mouseDownHandler = new MouseDownHandler() {
    public void onMouseDown(MouseDownEvent event) {
    }
  };

  MouseDownHandler mouseDownHandler2 = new MouseDownHandler() {
    public void onMouseDown(MouseDownEvent event) {
    }
  };

  ClickHandler clickHandler = new ClickHandler() {

    public void onClick(ClickEvent event) {
    }
  };

  public void testAccesors() {
    HandlerRegistry registry = new HandlerRegistry.JSHandlerRegistry();
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
    HandlerRegistry registry = new HandlerRegistry.JSHandlerRegistry();
    registry.addHandler(MouseDownEvent.KEY, mouseDownHandler);
    assertEquals(1, registry.getHandlerCount(MouseDownEvent.KEY));
  }

  public void testRemove() {
    HandlerRegistry registry = new HandlerRegistry.JSHandlerRegistry();
    registry.addHandler(MouseDownEvent.KEY, mouseDownHandler);
    assertEquals(1, registry.getHandlerCount(MouseDownEvent.KEY));
    registry.removeHandler(MouseDownEvent.KEY, mouseDownHandler);
    assertEquals(0, registry.getHandlerCount(MouseDownEvent.KEY));
  }
}
