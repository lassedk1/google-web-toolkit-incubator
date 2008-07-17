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

package com.google.gwt.libideas.event.shared;

import com.google.gwt.libideas.client.LibTestBase;
import com.google.gwt.libideas.event.client.ClickEvent;
import com.google.gwt.libideas.event.client.ClickHandler;
import com.google.gwt.libideas.event.client.MouseDownEvent;
import com.google.gwt.libideas.event.client.MouseDownHandler;
import com.google.gwt.libideas.event.shared.HandlerRegistry;

/**
 * Basic tests for Handler registry's. Not much to it as most testing should be
 * in GWTEventsTest.
 */
public class JavaScriptHanderRegistryTest extends LibTestBase {
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

  public void testAdd() {
    HandlerRegistry registry = new HandlerRegistry.JSHandlerRegistry();
    registry.addHandler(MouseDownEvent.KEY, mouseDownHandler);
    assertEquals(1, registry.getHandlerCount(MouseDownEvent.KEY));
  }

  public void testRemove() {
    HandlerRegistry registry = new HandlerRegistry.JSHandlerRegistry();
    registry.addHandler(MouseDownEvent.KEY, mouseDownHandler);
    registry.removeHandler(MouseDownEvent.KEY, mouseDownHandler);
    assertEquals(0, registry.getHandlerCount(MouseDownEvent.KEY));
  }

  public void testAccesors() {
    HandlerRegistry registry = new HandlerRegistry.JSHandlerRegistry();
    registry.addHandler(MouseDownEvent.KEY, mouseDownHandler);
    registry.addHandler(ClickEvent.KEY, clickHandler);
    registry.addHandler(MouseDownEvent.KEY, mouseDownHandler2);
    registry.removeHandler(MouseDownEvent.KEY, mouseDownHandler);
    
    assertEquals(1, registry.getHandlerCount(MouseDownEvent.KEY));
    assertEquals(1, registry.getHandlerCount(ClickEvent.KEY));
    assertEquals(mouseDownHandler2, registry.getHandler(MouseDownEvent.KEY,0));
    assertEquals(clickHandler, registry.getHandler(ClickEvent.KEY, 0));
    
  }
}
