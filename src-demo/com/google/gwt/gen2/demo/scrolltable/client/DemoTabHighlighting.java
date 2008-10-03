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
package com.google.gwt.gen2.demo.scrolltable.client;

import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.SelectionGrid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * A tab panel of various options related to row selection and highlighting.
 */
public class DemoTabHighlighting extends DemoTab implements ClickListener {
  /**
   * The description of row selection.
   */
  private static final String DESC_SELECTION = "Row selection allows the user "
      + "to select one or more rows from the table "
      + "using mouse clicks.  Use the ctrl button to add to the selected rows, "
      + "and use the shift button to select multiple rows in a range.";

  /**
   * The grid used for layout.
   */
  private Grid grid;

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
   * The button used to set the selection policy.
   */
  private Button selectionToggleButton = new Button("Toggle Selection", this);

  /**
   * Handle click events from the buttons in this panel.
   * 
   * @param sender
   */
  public void onClick(Widget sender) {
    FixedWidthGrid dataTable = ScrollTableDemo.getDataTable();
    if (sender == selectionPolicyButton) {
      // Set Selection Policy
      String selection = selectionPolicyBox.getValue(selectionPolicyBox.getSelectedIndex());
      if (selection.equals("Multi Row")) {
        dataTable.setSelectionPolicy(SelectionGrid.SelectionPolicy.MULTI_ROW);
      } else if (selection.equals("Single Row")) {
        dataTable.setSelectionPolicy(SelectionGrid.SelectionPolicy.ONE_ROW);
      } else if (selection.equals("Checkboxes")) {
        dataTable.setSelectionPolicy(SelectionGrid.SelectionPolicy.CHECKBOX);
      } else if (selection.equals("Radio Buttons")) {
        dataTable.setSelectionPolicy(SelectionGrid.SelectionPolicy.RADIO);
      }
    } else if (sender == selectionToggleButton) {
      boolean enabled = dataTable.isSelectionEnabled();
      dataTable.setSelectionEnabled(!enabled);
      if (enabled) {
        grid.setHTML(1, 1, "disabled");
      } else {
        grid.setHTML(1, 1, "enabled");
      }
    }
  }

  @Override
  protected Widget onInitialize() {
    grid = new Grid(2, 3);
    grid.setBorderWidth(2);
    grid.setCellPadding(3);
    grid.setCellSpacing(0);

    // Set selection policy
    selectionPolicyBox.addItem("Multi Row");
    selectionPolicyBox.addItem("Single Row");
    selectionPolicyBox.addItem("Checkboxes");
    selectionPolicyBox.addItem("Radio Buttons");
    grid.setWidget(0, 0, selectionPolicyButton);
    grid.setWidget(0, 1, selectionPolicyBox);
    grid.setHTML(0, 2, DESC_SELECTION);

    // Toggle selection
    grid.setWidget(1, 0, selectionToggleButton);
    grid.setHTML(1, 1, "enabled");

    return grid;
  }
}
