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
package com.google.gwt.libideas.event.client;

import com.google.gwt.libideas.event.client.keyboard.KeyDownEvent;
import com.google.gwt.libideas.event.client.keyboard.KeyDownHandler;
import com.google.gwt.libideas.event.client.keyboard.KeyPressedEvent;
import com.google.gwt.libideas.event.client.keyboard.KeyPressedHandler;
import com.google.gwt.libideas.event.client.keyboard.KeyUpEvent;
import com.google.gwt.libideas.event.client.keyboard.KeyUpHandler;
import com.google.gwt.libideas.event.client.mouse.ClickEvent;
import com.google.gwt.libideas.event.client.mouse.ClickHandler;
import com.google.gwt.libideas.event.client.mouse.DoubleClickEvent;
import com.google.gwt.libideas.event.client.mouse.DoubleClickHandler;
import com.google.gwt.libideas.event.client.mouse.MouseDownEvent;
import com.google.gwt.libideas.event.client.mouse.MouseDownHandler;
import com.google.gwt.libideas.event.client.mouse.MouseMoveEvent;
import com.google.gwt.libideas.event.client.mouse.MouseMoveHandler;
import com.google.gwt.libideas.event.client.mouse.MouseOutEvent;
import com.google.gwt.libideas.event.client.mouse.MouseOutHandler;
import com.google.gwt.libideas.event.client.mouse.MouseOverEvent;
import com.google.gwt.libideas.event.client.mouse.MouseOverHandler;
import com.google.gwt.libideas.event.client.mouse.MouseUpEvent;
import com.google.gwt.libideas.event.client.mouse.MouseUpHandler;
import com.google.gwt.libideas.event.client.mouse.MouseWheelEvent;
import com.google.gwt.libideas.event.client.mouse.MouseWheelHandler;
import com.google.gwt.libideas.event.shared.AbstractEvent;
import com.google.gwt.libideas.event.shared.HandlerRegistration;
import com.google.gwt.libideas.event.shared.HandlerManager;

import junit.framework.TestCase;

public class EventsTest extends TestCase {

  private HandlerManager manager;

  public void setUp() {
    manager = new HandlerManager(this);
  }

  static class Flag {

    public boolean flag = false;
  }

  public void testKeyEvents() {

    final Flag flag = new Flag();
    HandlerRegistration downRegistration = manager
        .addEventHandler(KeyDownEvent.KEY, new KeyDownHandler() {
          public void onKeyDown(KeyDownEvent event) {
            flag.flag = true;
          }
        });
    HandlerRegistration upRegistration = manager
        .addEventHandler(KeyUpEvent.KEY, new KeyUpHandler() {
          public void onKeyUp(KeyUpEvent event) {
            flag.flag = true;
          }
        });

    HandlerRegistration pressRegistration = manager
        .addEventHandler(KeyPressedEvent.KEY, new KeyPressedHandler() {
          public void onKeyPressed(KeyPressedEvent event) {
            flag.flag = true;
          }
        });

    checkFire(new KeyDownEvent(null), downRegistration, flag, "onKeyDown");
    checkFire(new KeyUpEvent(null), upRegistration, flag, "onKeyUp");
    checkFire(new KeyPressedEvent(null), pressRegistration, flag,
        "onKeyPressed");

  }


  public void testMouseEvents() {

    final Flag flag = new Flag();
    HandlerRegistration downRegistration = manager
        .addEventHandler(MouseDownEvent.KEY, new MouseDownHandler() {
          public void onMouseDown(MouseDownEvent event) {
            flag.flag = true;
          }
        });
    HandlerRegistration upRegistration = manager
        .addEventHandler(MouseUpEvent.KEY, new MouseUpHandler() {
          public void onMouseUp(MouseUpEvent event) {
            flag.flag = true;
          }
        });

    HandlerRegistration clickRegistration = manager
        .addEventHandler(ClickEvent.KEY, new ClickHandler() {
          public void onClick(ClickEvent event) {
            flag.flag = true;
          }
        });

    HandlerRegistration dblclickRegistration = manager
        .addEventHandler(DoubleClickEvent.KEY, new DoubleClickHandler() {
          public void onDoubleClick(DoubleClickEvent event) {
            flag.flag = true;
          }
        });

    HandlerRegistration outRegistration = manager
        .addEventHandler(MouseOutEvent.KEY, new MouseOutHandler() {
          public void onMouseOut(MouseOutEvent event) {
            flag.flag = true;
          }
        });
    HandlerRegistration overRegistration = manager
        .addEventHandler(MouseOverEvent.KEY, new MouseOverHandler() {
          public void onMouseOver(MouseOverEvent event) {
            flag.flag = true;
          }
        });

    HandlerRegistration moveRegistration = manager
        .addEventHandler(MouseMoveEvent.KEY, new MouseMoveHandler() {
          public void onMouseMove(MouseMoveEvent event) {
            flag.flag = true;
          }
        });

    HandlerRegistration wheelRegistration = manager
        .addEventHandler(MouseWheelEvent.KEY, new MouseWheelHandler() {
          public void onMouseWheel(MouseWheelEvent event) {
            flag.flag = true;
          }
        });

    checkFire(new MouseDownEvent(null), downRegistration, flag, "onMouseDown");
    checkFire(new MouseUpEvent(null), upRegistration, flag, "onMouseUp");
    checkFire(new MouseOutEvent(null), outRegistration, flag, "onMouseOut");
    checkFire(new MouseOverEvent(null), overRegistration, flag, "onMouseOver");
    checkFire(new MouseMoveEvent(null), moveRegistration, flag, "onMouseMove");
    checkFire(new MouseWheelEvent(null), wheelRegistration, flag,
        "onMouseWheel");
    checkFire(new ClickEvent(null), clickRegistration, flag, "onClick");
    checkFire(new DoubleClickEvent(null), dblclickRegistration, flag,
        "onDoubleClick");


  }

  private void checkFire(AbstractEvent event, HandlerRegistration registration,
      Flag flag, String eventName) {

    flag.flag = false;
    manager.fireEvent(event);
    assertTrue(eventName + " didn't fire.", flag.flag);

    flag.flag = false;
    registration.removeHandler();
    manager.fireEvent(event);
    assertTrue(eventName + " fired when it shouldn't have.", !flag.flag);
  }


}
