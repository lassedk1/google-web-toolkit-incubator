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
import com.google.gwt.gen2.event.dom.client.MouseMoveEvent;
import com.google.gwt.gen2.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimpleWidgetDemo implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    SimpleWidget clickable = new SimpleWidget("<div>clickMe</div>");
    RootPanel.get().add(clickable);
    clickable.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        report("clicked on clickable");
      }

    });

    SimpleWidget mouseMove = new SimpleWidget("<h1>mouseMove</h1>");
    RootPanel.get().add(mouseMove);
    mouseMove.addMouseMoveHandler(new MouseMoveHandler() {

      public void onMouseMove(MouseMoveEvent event) {
        report("moved over mouseMove");
      }
    });
  }

  private void report(String string) {
    RootPanel.get().add(new Label(string));
  }
}
