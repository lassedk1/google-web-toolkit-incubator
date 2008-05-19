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
package com.google.gwt.demos.scrolltable.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
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
   * The description of resizing a column.
   */
  private static final String DESC_RESIZE = "Manually set the absolute size of a column.";

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
   * The checkbox used to select guaranteed sizing.
   */
  private CheckBox guaranteedCheck = new CheckBox("Guaranteed");

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
        if (guaranteedCheck.isChecked()) {
          scrollTable.setGuaranteedColumnWidth(column, width);
        } else {
          scrollTable.setColumnWidth(column, width);
        }
      } else if (sender == resizePolicyButton) {
        // Set the resize policy
        String selection = resizePolicyBox.getValue(resizePolicyBox.getSelectedIndex());
        if (selection.equals("Disabled")) {
          scrollTable.setResizePolicy(ScrollTable.ResizePolicy.DISABLED);
        } else if (selection.equals("Unconstrained")) {
          scrollTable.setResizePolicy(ScrollTable.ResizePolicy.UNCONSTRAINED);
        } else if (selection.equals("Flow")) {
          scrollTable.setResizePolicy(ScrollTable.ResizePolicy.FLOW);
        } else if (selection.equals("Fixed")) {
          scrollTable.setResizePolicy(ScrollTable.ResizePolicy.FIXED_WIDTH);
        } else if (selection.equals("Fill")) {
          scrollTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);
        } else if (selection.equals("Fill Disabled")) {
          scrollTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH_DISABLED);
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
    grid = new Grid(3, 4);
    grid.setCellSpacing(0);
    grid.setBorderWidth(1);

    // Column options
    columnIndexBox.setWidth("70px");
    columnIndexBox.setText("0");
    grid.setHTML(0, 0, "<B>Column:</B>");
    grid.setWidget(0, 1, columnIndexBox);

    // Width options
    VerticalPanel widthContainer = new VerticalPanel();
    widthContainer.add(columnWidthBox);
    widthContainer.add(guaranteedCheck);
    columnWidthBox.setWidth("70px");
    columnWidthBox.setText("10");
    grid.setHTML(1, 0, "<B>Width:</B>");
    grid.setWidget(1, 1, widthContainer);
    grid.setWidget(1, 2, resizeButton);
    grid.setHTML(1, 3, DESC_RESIZE);

    // Fixed width options
    resizePolicyBox.addItem("Disabled");
    resizePolicyBox.addItem("Unconstrained");
    resizePolicyBox.addItem("Flow");
    resizePolicyBox.addItem("Fixed");
    resizePolicyBox.addItem("Fill");
    resizePolicyBox.addItem("Fill Disabled");
    resizePolicyBox.setSelectedIndex(3);
    grid.setHTML(2, 0, "<BR>");
    grid.setWidget(2, 1, resizePolicyButton);
    grid.setWidget(2, 2, resizePolicyBox);
    grid.setHTML(2, 3, DESC_FIXED_WIDTH);

    return grid;
  }
}
