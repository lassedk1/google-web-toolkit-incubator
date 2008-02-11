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
package com.google.gwt.examples;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.widgetideas.client.GlassPanel;

/**
 * Glass Panel example.
 */
public class GlassPanelExample implements EntryPoint {
  public void onModuleLoad() {
    // Create a glass panel covering the entire browser document
    RootPanel.get().add(
        new Label(
            "The browser document will be covered by a semi-transparent glass panel."));
    RootPanel.get().add(
        new Label("Hit ESC or click anywhere on the glass panel to remove it."));
    RootPanel.get().add(new GlassPanel(true), 0, 0);

    // Create a small AbsolutePanel
    AbsolutePanel absolutePanel = new AbsolutePanel();
    absolutePanel.setPixelSize(100, 100);
    DOM.setStyleAttribute(absolutePanel.getElement(), "border",
        "1px solid black");
    absolutePanel.add(new Label(
        "This AbsolutePanel is covered with a semi-transparent green glass panel"));

    // Attach the AbsolutePanel
    RootPanel.get().add(absolutePanel, 50, 50);

    // Attach an absolute panel and style it green using this !important CSS
    // rule:
    // .green { background-color: green !important; }
    GlassPanel greenGlassPanel = new GlassPanel(false);
    greenGlassPanel.addStyleName("green");
    absolutePanel.add(greenGlassPanel, 0, 0);
  }
}
