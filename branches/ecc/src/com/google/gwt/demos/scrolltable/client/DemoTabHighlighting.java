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
import com.google.gwt.widgetideas.table.client.EditablePagingGrid;
import com.google.gwt.widgetideas.table.client.FixedWidthGrid;
import com.google.gwt.widgetideas.table.client.SortableFixedWidthGrid;

/**
 * A tab panel of various options related to row selection and highlighting.
 */
public class DemoTabHighlighting extends DemoTab implements ClickListener {
  /**
   * The description hovering.
   */
  private static final String DESC_HOVERING = "When hovering is enabled, a "
      + "special CSS style property will be applied "
      + "to the current row that the mouse cursor is hovering over.";

  /**
   * The description of row selection.
   */
  private static final String DESC_MIN_ROW = "If your Grid uses the first one "
      + "or more rows as headers, you can set "
      + "the minimum selection row so users cannot select or hover the header.";

  /**
   * The description of row selection.
   */
  private static final String DESC_SELECTION = "Row selection allows the user "
      + "to select one or more rows from the table "
      + "using mouse clicks.  Use the ctrl button to add to the selected rows, "
      + "and use the shift button to select multiple rows in a range.";

  /**
   * The drop box of selection options.
   */
  private ListBox hoveringPolicyBox = new ListBox();

  /**
   * The button used to toggle row hovering.
   */
  private Button hoveringPolicyButton = new Button("Set Hovering Policy", this);

  /**
   * The text field with the minimum row index.
   */
  private TextBox minRowIndexBox = new TextBox();

  /**
   * The button used to toggle row hovering.
   */
  private Button setMinRowButton = new Button("Set Minimum Row", this);

  /**
   * The drop box of selection options.
   */
  private ListBox selectionPolicyBox = new ListBox();

  /**
   * The button used to set the selection policy.
   */
  private Button selectionPolicyButton = new Button("Set Selection Policy",
      this);

  /**
   * Handle click events from the buttons in this panel.
   * 
   * @param sender
   */
  public void onClick(Widget sender) {
    FixedWidthGrid dataTable = ScrollTableDemo.getDataTable();
    try {
      if (sender == hoveringPolicyButton) {
        // Toggle hovering
        String selection = hoveringPolicyBox.getValue(hoveringPolicyBox.getSelectedIndex());
        if (selection.equals("Row")) {
          dataTable.setHoveringPolicy(SortableFixedWidthGrid.HOVERING_POLICY_ROW);
        } else if (selection.equals("Cell")) {
          dataTable.setHoveringPolicy(SortableFixedWidthGrid.HOVERING_POLICY_CELL);
        } else if (selection.equals("Editable Cell")) {
          dataTable.setHoveringPolicy(EditablePagingGrid.HOVERING_POLICY_EDITABLE_CELL);
        } else {
          dataTable.setHoveringPolicy(SortableFixedWidthGrid.HOVERING_POLICY_DISABLED);
        }
      } else if (sender == selectionPolicyButton) {
        // Set Selection Policy
        String selection = selectionPolicyBox.getValue(selectionPolicyBox.getSelectedIndex());
        if (selection.equals("Multi Row")) {
          dataTable.setSelectionPolicy(SortableFixedWidthGrid.SELECTION_POLICY_MULTI_ROW);
        } else if (selection.equals("Single Row")) {
          dataTable.setSelectionPolicy(SortableFixedWidthGrid.SELECTION_POLICY_ONE_ROW);
        } else {
          dataTable.setSelectionPolicy(SortableFixedWidthGrid.SELECTION_POLICY_DISABLED);
        }
      } else if (sender == setMinRowButton) {
        // Set min row
        int row = Integer.parseInt(minRowIndexBox.getText());
        dataTable.setMinHoverRow(row);
      }
    } catch (IndexOutOfBoundsException e) {
      Window.alert("The cell index you entered is out of bounds.");
    } catch (NumberFormatException e) {
      Window.alert("Please enter valid integers for the row and column.");
    }
  }

  @Override
  protected Widget onInitialize() {
    Grid grid = new Grid(3, 3);
    grid.setBorderWidth(2);
    grid.setCellPadding(3);
    grid.setCellSpacing(0);

    // Toggle hovering
    hoveringPolicyBox.addItem("Row");
    hoveringPolicyBox.addItem("Cell");
    if (ScrollTableDemo.getDataTable() instanceof EditablePagingGrid) {
      hoveringPolicyBox.addItem("Editable Cell");
    }
    hoveringPolicyBox.addItem("Disabled");
    grid.setWidget(0, 0, hoveringPolicyButton);
    grid.setWidget(0, 1, hoveringPolicyBox);
    grid.setHTML(0, 2, DESC_HOVERING);

    // Toggle selection
    selectionPolicyBox.addItem("Multi Row");
    selectionPolicyBox.addItem("Single Row");
    selectionPolicyBox.addItem("Disabled");
    grid.setWidget(1, 0, selectionPolicyButton);
    grid.setWidget(1, 1, selectionPolicyBox);
    grid.setHTML(1, 2, DESC_SELECTION);

    // Set minimum row
    minRowIndexBox.setText("2");
    minRowIndexBox.setWidth("50px");
    grid.setWidget(2, 0, setMinRowButton);
    grid.setWidget(2, 1, minRowIndexBox);
    grid.setHTML(2, 2, DESC_MIN_ROW);

    return grid;
  }
}
