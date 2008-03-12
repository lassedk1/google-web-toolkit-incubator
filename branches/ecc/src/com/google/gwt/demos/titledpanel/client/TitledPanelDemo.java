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
package com.google.gwt.demos.titledpanel.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.TitledPanel;

import java.util.Date;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TitledPanelDemo implements EntryPoint {
  /**
   * The titled panel.
   */
  private TitledPanel titledPanel = null;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    // Create the titled panel
    titledPanel = new TitledPanel("Hello World");
    titledPanel.setWidth("500px");
    setTitledPanelContents(new HTML("This is a widget in the TitledPanel"));
    RootPanel.get().add(titledPanel);

    // Create a grid to add all of the options
    Grid grid = new Grid(3, 3);
    grid.setBorderWidth(1);
    grid.setCellPadding(3);
    RootPanel.get().add(new HTML("<BR><BR>"));
    RootPanel.get().add(grid);

    // Set title option
    final TextBox titleBox = new TextBox();
    titleBox.setText(titledPanel.getTitle());
    grid.setWidget(0, 0, new Button("Set Title", new ClickListener() {
      public void onClick(Widget sender) {
        titledPanel.setTitle(titleBox.getText());
      }
    }));
    grid.setWidget(0, 1, titleBox);
    grid.setHTML(0, 2, "Change the title dynamically");

    // Clear title option
    grid.setWidget(1, 0, new Button("Clear Title", new ClickListener() {
      public void onClick(Widget sender) {
        titledPanel.setTitle(null);
      }
    }));
    grid.setHTML(1, 1, "<BR>");
    grid.setHTML(1, 2, "Clear out the title of the box.  Note that this is "
        + "different from setting the title to an empty string.  When you set "
        + "the title to an empty string, you will see a small gap where the "
        + "text would normally go.  Clearing the title effectively creates a "
        + "bordered widget.");

    // Set the widget inside the titled panel
    grid.setHTML(2, 1, "<BR>");
    grid.setHTML(2, 2, "The TitledPanel is a type of SimplePanel, so you can "
        + "add any widget to it.");
    grid.setWidget(2, 0, new Button("Set Widget", new ClickListener() {
      public void onClick(Widget sender) {
        Date d = new Date();
        HTML newContents = new HTML("This is a new widget created on:<BR> "
            + d.toLocaleString());
        setTitledPanelContents(newContents);
      }
    }));
  }

  /**
   * Update the widget inside the titled panel to show the current date.
   * 
   * @param contents the contents to put inside the titled panel
   */
  private void setTitledPanelContents(Widget contents) {
    titledPanel.setWidget(contents);
    contents.setStyleName("titledPanelDemoContents");
  }
}
