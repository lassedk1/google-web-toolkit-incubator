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

package com.google.gwt.gen2.demo.simplewidget.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.gen2.commonwidget.client.SimpleWidget;
import com.google.gwt.gen2.event.dom.client.ClickEvent;
import com.google.gwt.gen2.event.dom.client.ClickHandler;
import com.google.gwt.gen2.event.dom.client.MouseDownEvent;
import com.google.gwt.gen2.event.dom.client.MouseDownHandler;
import com.google.gwt.gen2.event.dom.client.MouseMoveEvent;
import com.google.gwt.gen2.event.dom.client.MouseMoveHandler;
import com.google.gwt.gen2.event.dom.client.MouseUpEvent;
import com.google.gwt.gen2.event.dom.client.MouseUpHandler;
import com.google.gwt.libideas.logging.client.SimpleLogHandler;
import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimpleWidgetDemo implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    // Set up log handler.
    SimpleLogHandler handler = new SimpleLogHandler(false);
    Log.addLogHandler(handler);
    RootPanel.get().add(handler.getWidget(), 300, 0);

    // Set up clickable widget.
    SimpleWidget clickable = new SimpleWidget("<h1>clickMe</h1>");
    RootPanel.get().add(clickable);
    clickable.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        report("clicked on clickable");
      }

    });

    // Set up mouse move widget.
    SimpleWidget mouseMove = new SimpleWidget("<h2>mouseMove</h2>");
    RootPanel.get().add(mouseMove);
    mouseMove.addMouseMoveHandler(new MouseMoveHandler() {

      public void onMouseMove(MouseMoveEvent event) {
        report("moved over mouseMove");
      }
    });

    // Set up mouse down and up handler, here some syntactic sugar would be nice
    // to have.

    SimpleWidget mouseDownAndUp = new SimpleWidget(
        "<h3>mouse down and up events</h3>");
    RootPanel.get().add(mouseDownAndUp);

    class MyHandler implements MouseDownHandler, MouseUpHandler {
      public void onMouseDown(MouseDownEvent event) {
        report("on mouse down");
      }

      public void onMouseUp(MouseUpEvent event) {
        report("on mouse up");
      }
    }
    MyHandler myHandler = new MyHandler();
    mouseDownAndUp.addMouseDownHandler(myHandler);
    mouseDownAndUp.addMouseUpHandler(myHandler);
  }

  private void report(String string) {
    Log.info(string);
  }
}
