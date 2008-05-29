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
import com.google.gwt.widgetideas.table.client.ScrollTable.ScrollPolicy;

/**
 * Panel full of resizing options.
 */
public class DemoTabResizing extends DemoTab implements ClickListener {
  /**
   * The description of min width.
   */
  private static final String DESC_MIN_WIDTH = "If the minimum width is set to"
      + "a value greater than -1, the table will not be allowed to become smaller"
      + "than the minimum width.";

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
   * The button used to set the min width.
   */
  private Button minWidthButton = new Button("Set Min Width", this);

  /**
   * Text box used to set the min width.
   */
  private TextBox minWidthText = new TextBox();

  /**
   * Redraw the table.
   */
  private Button redrawButton = new Button("Redraw Scroll Table", this);

  /**
   * The grid that holds the toggle buttons.
   */
  private Grid toggleButtonGrid = new Grid(4, 3);

  /**
   * The button used to toggle static resize checking.
   */
  private Button toggleStaticCheckingButton = new Button(
      "Toggle Resize Checking", this);

  /**
   * The list box used to select the scrolling policy.
   */
  private ListBox scrollingBox = new ListBox();

  /**
   * The button used to set the scrolling policy.
   */
  private Button scrollingButton = new Button("Set Scroll Policy", this);

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
    } else if (sender == scrollingButton) {
      String policy = scrollingBox.getValue(scrollingBox.getSelectedIndex());
      if (policy.equals("horizontal")) {
        scrollTable.setScrollPolicy(ScrollPolicy.HORIZONTAL);
      } else if (policy.equals("both")) {
        scrollTable.setScrollPolicy(ScrollPolicy.BOTH);
      } else if (policy.equals("disabled")) {
        scrollTable.setScrollPolicy(ScrollPolicy.DISABLED);
      }
    } else if (sender == redrawButton) {
      scrollTable.redraw();
    } else if (sender == heightWidthButton) {
      DOM.setStyleAttribute(scrollTable.getElement(),
          heightWidthBox.getValue(heightWidthBox.getSelectedIndex()),
          heightWidthText.getText());
    } else if (sender == minWidthButton) {
      int minWidth = Integer.parseInt(minWidthText.getText());
      scrollTable.setMinWidth(minWidth);
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
    scrollingBox.addItem("horizontal");
    scrollingBox.addItem("both");
    scrollingBox.addItem("disabled");
    scrollingBox.setSelectedIndex(1);
    toggleButtonGrid.setWidget(1, 0, scrollingButton);
    toggleButtonGrid.setWidget(1, 1, scrollingBox);
    toggleButtonGrid.setHTML(1, 2, DESC_SCROLLING);

    // Min width button
    minWidthText.setText("-1");
    minWidthText.setWidth("80px");
    toggleButtonGrid.setWidget(2, 0, minWidthButton);
    toggleButtonGrid.setWidget(2, 1, minWidthText);
    toggleButtonGrid.setHTML(2, 2, DESC_MIN_WIDTH);

    // Redraw button
    toggleButtonGrid.setWidget(3, 0, redrawButton);

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
    panel.add(new HTML(
        "<BR><B>Change the overall height/width of the table:</B>"));
    panel.add(styleHolder);
    return panel;
  }
}
