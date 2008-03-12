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

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.table.client.ScrollTable;

/**
 * A tab panel of various options used to change the width of columns in the
 * table.
 */
public class DemoTabColumnWidth extends DemoTab implements ClickListener {
  /**
   * The description of fixed width resizing.
   */
  private static final String DESC_FIXED_WIDTH = "When fixed width resizing is "
      + "enabled, the grid will do its best to make sure that its contents fit "
      + "withing its visible area so you do not see a horizontal scrollbar.";

  /**
   * The description of hide a column.
   */
  private static final String DESC_HIDE = "Completely hide a column from view";

  /**
   * The description of resizing a column.
   */
  private static final String DESC_RESIZE = "Manually set the absolute size of a column.";

  /**
   * The description of stretching a column.
   */
  private static final String DESC_STRETCH = "Stretch a column to automatically calculate the minimum column width "
      + "required to display the contents of every cell in the data portion of "
      + "the column.  The header cells are ignored when stretching a column.";

  /**
   * The column index box.
   */
  private TextBox columnIndexBox = new TextBox();

  /**
   * The width box.
   */
  private TextBox columnWidthBox = new TextBox();

  /**
   * The grid used for formatting.
   */
  private Grid grid;

  /**
   * The button used to hide a column.
   */
  private Button hideButton = new Button("Hide Column", this);

  /**
   * The button used to show a column.
   */
  private Button showButton = new Button("Show Column", this);

  /**
   * The button used to resize a column.
   */
  private Button resizeButton = new Button("Resize Column", this);

  /**
   * The drop box of resize policy options.
   */
  private ListBox resizePolicyBox = new ListBox();

  /**
   * The button used to stretch a column.
   */
  private Button resizePolicyButton = new Button("Set Resize Policy", this);

  /**
   * The button used to stretch a column.
   */
  private Button stretchButton = new Button("Stretch to Fit", this);

  /**
   * Handle click events from the buttons in this panel.
   * 
   * @param sender
   */
  public void onClick(Widget sender) {
    try {
      ScrollTable scrollTable = ScrollTableDemo.getScrollTable();
      if (sender == resizeButton) {
        // Resize the column
        int column = Integer.parseInt(columnIndexBox.getText());
        int width = Integer.parseInt(columnWidthBox.getText());
        scrollTable.setColumnWidth(column, width);
      } else if (sender == stretchButton) {
        // Stretch to fit contents
        int column = Integer.parseInt(columnIndexBox.getText());
        scrollTable.autoFitColumnWidth(column);
      } else if (sender == hideButton) {
        // Hide a column
        Window.alert("Feature not available");
      } else if (sender == showButton) {
        // Show a column
        Window.alert("Feature not available");
      } else if (sender == resizePolicyButton) {
        // Set the resize policy
        String selection = resizePolicyBox.getValue(resizePolicyBox.getSelectedIndex());
        if (selection.equals("Unconstrained")) {
          scrollTable.setResizePolicy(ScrollTable.RESIZE_POLICY_UNCONSTRAINED);
        } else if (selection.equals("Flow")) {
          scrollTable.setResizePolicy(ScrollTable.RESIZE_POLICY_FLOW);
        } else if (selection.equals("Fixed")) {
          scrollTable.setResizePolicy(ScrollTable.RESIZE_POLICY_FIXED_WIDTH);
        } else if (selection.equals("Fill")) {
          scrollTable.setResizePolicy(ScrollTable.RESIZE_POLICY_FILL_WIDTH);
        }
      }
    } catch (IndexOutOfBoundsException e) {
      Window.alert("The column index you entered is out of bounds.");
    } catch (NumberFormatException e) {
      Window.alert("Please enter valid integers for the column and width.");
    }
  }

  @Override
  protected Widget onInitialize() {
    grid = new Grid(4, 4);
    grid.setCellSpacing(0);
    grid.setBorderWidth(1);

    // Column options
    columnIndexBox.setWidth("70px");
    columnIndexBox.setText("0");
    grid.setHTML(0, 0, "<B>Column:</B>");
    grid.setWidget(0, 1, columnIndexBox);
    grid.setWidget(0, 2, stretchButton);
    grid.setHTML(0, 3, DESC_STRETCH);

    // Width options
    columnWidthBox.setWidth("70px");
    columnWidthBox.setText("10");
    grid.setHTML(1, 0, "<B>Width:</B>");
    grid.setWidget(1, 1, columnWidthBox);
    grid.setWidget(1, 2, resizeButton);
    grid.setHTML(1, 3, DESC_RESIZE);

    // hide options
    columnWidthBox.setWidth("70px");
    columnWidthBox.setText("10");
    grid.setHTML(2, 0, "<BR>");
    grid.setWidget(2, 1, showButton);
    grid.setWidget(2, 2, hideButton);
    grid.setHTML(2, 3, DESC_HIDE);

    // Fixed width options
    resizePolicyBox.addItem("Unconstrained");
    resizePolicyBox.addItem("Flow");
    resizePolicyBox.addItem("Fixed");
    resizePolicyBox.addItem("Fill");
    resizePolicyBox.setSelectedIndex(1);
    grid.setHTML(3, 0, "<BR>");
    grid.setWidget(3, 1, resizePolicyButton);
    grid.setWidget(3, 2, resizePolicyBox);
    grid.setHTML(3, 3, DESC_FIXED_WIDTH);

    return grid;
  }
}
