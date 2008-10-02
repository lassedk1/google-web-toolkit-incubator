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

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.table.client.FixedWidthGrid;
import com.google.gwt.widgetideas.table.client.SelectionGrid;

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
      } else {
        dataTable.setSelectionPolicy(SelectionGrid.SelectionPolicy.DISABLED);
      }
    }
  }

  @Override
  protected Widget onInitialize() {
    Grid grid = new Grid(1, 3);
    grid.setBorderWidth(2);
    grid.setCellPadding(3);
    grid.setCellSpacing(0);

    // Toggle selection
    selectionPolicyBox.addItem("Multi Row");
    selectionPolicyBox.addItem("Single Row");
    selectionPolicyBox.addItem("Checkboxes");
    selectionPolicyBox.addItem("Radio Buttons");
    selectionPolicyBox.addItem("Disabled");
    grid.setWidget(0, 0, selectionPolicyButton);
    grid.setWidget(0, 1, selectionPolicyBox);
    grid.setHTML(0, 2, DESC_SELECTION);

    return grid;
  }
}
