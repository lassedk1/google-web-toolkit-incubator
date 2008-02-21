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
package com.google.gwt.demos.scrolltable.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.ResizableWidgetCollection;
import com.google.gwt.widgetideas.table.client.ScrollTable;

/**
 * Panel full of resizing options.
 */
public class DemoTabResizing extends DemoTab implements ClickListener {
  /**
   * The description of scrolling.
   */
  private static final String DESC_SCROLLING = "Enable scrolling to confine "
    + "the data area to a scrollable window.  Disable scrolling to allow the "
    + "data area to grow as needed to fit its content.";
  
  /**
   * The description of static resize checking.
   */
  private static final String DESC_STATIC_CHECKING = "Enable resize checking "
      + "to start a Timer that compares the offset width and height of the "
      + "scroll table at periodic intervals.  If the width or height change "
      + "for an undetectable reason (such as changing the width style "
      + "attribute of the widget), the ResizableWidgetCollection class will "
      + "fire the onResize() method in the ScrollTable, which implements "
      + "ResizableWidget.";

  /**
   * Drop box used to select height or width.
   */
  private ListBox heightWidthBox = new ListBox();

  /**
   * The button used to set the height or width.
   */
  private Button heightWidthButton = new Button("Apply", this);

  /**
   * Text box used to set height or width.
   */
  private TextBox heightWidthText = new TextBox();

  /**
   * Redraw the table.
   */
  private Button redrawButton = new Button("Redraw Scroll Table", this);

  /**
   * The grid that holds the toggle buttons.
   */
  private Grid toggleButtonGrid = new Grid(2, 3);

  /**
   * The button used to toggle static resize checking.
   */
  private Button toggleStaticCheckingButton = new Button(
      "Toggle Resize Checking", this);

  /**
   * The button used to toggle scrolling.
   */
  private Button toggleScrollingButton = new Button(
      "Toggle Scrolling", this);
  
  /**
   * Handle click events from the buttons in this panel.
   * 
   * @param sender
   */
  public void onClick(Widget sender) {
    ScrollTable scrollTable = ScrollTableDemo.getScrollTable();
    if (sender == toggleStaticCheckingButton) {
      if (ResizableWidgetCollection.get().isResizeCheckingEnabled()) {
        ResizableWidgetCollection.get().setResizeCheckingEnabled(false);
        toggleButtonGrid.setHTML(0, 1, "disabled");
      } else {
        ResizableWidgetCollection.get().setResizeCheckingEnabled(true);
        toggleButtonGrid.setHTML(0, 1, "enabled");
      }
    } else if (sender == toggleScrollingButton) {
      boolean scrollEnabled = scrollTable.isScrollingEnabled();
      if (scrollEnabled) {
        scrollTable.setScrollingEnabled(false);
        toggleButtonGrid.setHTML(1, 1, "disabled");
      } else {
        scrollTable.setScrollingEnabled(true);
        toggleButtonGrid.setHTML(1, 1, "enabled");
      }
    } else if (sender == redrawButton) {
      scrollTable.redraw();
    } else if (sender == heightWidthButton) {
      DOM.setStyleAttribute(scrollTable.getElement(),
          heightWidthBox.getValue(heightWidthBox.getSelectedIndex()),
          heightWidthText.getText());
    }
  }

  @Override
  protected Widget onInitialize() {
    // Define this as a 2x2 grid
    toggleButtonGrid.setBorderWidth(2);
    toggleButtonGrid.setCellPadding(3);
    toggleButtonGrid.setCellSpacing(0);

    // Toggle resize checking button
    toggleButtonGrid.setWidget(0, 0, toggleStaticCheckingButton);
    toggleButtonGrid.setHTML(0, 1, "enabled");
    toggleButtonGrid.setHTML(0, 2, DESC_STATIC_CHECKING);

    // Toggle scrolling button
    toggleButtonGrid.setWidget(1, 0, toggleScrollingButton);
    toggleButtonGrid.setHTML(1, 1, "enabled");
    toggleButtonGrid.setHTML(1, 2, DESC_SCROLLING);
    
    // Set the overall table height or width
    heightWidthBox.addItem("height");
    heightWidthBox.addItem("width");
    heightWidthText.setWidth("50px");
    heightWidthText.setText("40%");

    HorizontalPanel styleHolder = new HorizontalPanel();
    styleHolder.add(new HTML("Set table "));
    styleHolder.add(heightWidthBox);
    styleHolder.add(new HTML(" to "));
    styleHolder.add(heightWidthText);
    styleHolder.add(heightWidthButton);

    VerticalPanel panel = new VerticalPanel();
    panel.add(toggleButtonGrid);
    panel.add(redrawButton);
    panel.add(new HTML(
        "<BR><B>Change the overall height/width of the table:</B>"));
    panel.add(styleHolder);
    return panel;
  }
}
