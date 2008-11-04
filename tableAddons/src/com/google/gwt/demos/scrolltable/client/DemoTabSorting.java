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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.table.client.ScrollTable;
import com.google.gwt.widgetideas.table.client.SortableGrid;

/**
 * Panel full of data sorting and movement options.
 */
public class DemoTabSorting extends DemoTab implements ClickListener {
  /**
   * The description of stretching a column.
   */
  private static final String DESC_SORTING = "When enabled, users can click on header cells to sort the associated "
      + "column.  The default implementation uses a client-side quicksort that "
      + "compares the innerText of cells using the native string comparison "
      + "method.  Users can easily override the sorting algorithm and implement "
      + "their own server-side method using the sorting callback function.";

  /**
   * The column index box.
   */
  private TextBox columnIndexBox = new TextBox();

  /**
   * The grid of data.
   */
  private Grid grid;

  /**
   * The button used to enable/disable column sorting.
   */
  private Button toggleSortingButton = new Button("Toggle Sorting", this);

  /**
   * The button used to make a column sortable.
   */
  private Button makeSortableButton = new Button("Make Sortable", this);

  /**
   * The button used to make a column unsortable.
   */
  private Button makeUnsortableButton = new Button("Make Unsortable", this);

  /**
   * The button used to move a row up one index.
   */
  private Button moveRowUpButton = new Button("Move Row Up", this);

  /**
   * The button used to move a row down one index.
   */
  private Button moveRowDownButton = new Button("Move Row Down", this);

  /**
   * The button used to reverse all rows.
   */
  private Button reverseRowsButton = new Button("Reverse all rows", this);

  /**
   * The first row index box.
   */
  private TextBox rowIndexBox1 = new TextBox();

  /**
   * The second row index box.
   */
  private TextBox rowIndexBox2 = new TextBox();

  /**
   * The button used to sort a column.
   */
  private Button sortColumnButton = new Button("Sort Column", this);

  /**
   * The button used to swap to rows.
   */
  private Button swapRowsButton = new Button("Swaps Rows", this);

  /**
   * Handle click events from the buttons in this panel.
   * 
   * @param sender
   */
  public void onClick(Widget sender) {
    ScrollTable scrollTable = ScrollTableDemo.getScrollTable();
    SortableGrid dataTable = ScrollTableDemo.getDataTable();

    try {
      if (sender == moveRowUpButton) {
        // Move row up
        int row1 = Integer.parseInt(rowIndexBox1.getText());
        dataTable.moveRowUp(row1);
        rowIndexBox1.setText((row1 - 1) + "");
      } else if (sender == moveRowDownButton) {
        // Move row down
        int row1 = Integer.parseInt(rowIndexBox1.getText());
        dataTable.moveRowDown(row1);
        rowIndexBox1.setText((row1 + 1) + "");
      } else if (sender == swapRowsButton) {
        // Swap two rows
        int row1 = Integer.parseInt(rowIndexBox1.getText());
        int row2 = Integer.parseInt(rowIndexBox2.getText());
        dataTable.swapRows(row1, row2);
      } else if (sender == reverseRowsButton) {
        // Reverse all rows
        dataTable.reverseRows();
      } else if (sender == sortColumnButton) {
        // Sort a column
        int column = Integer.parseInt(columnIndexBox.getText());
        dataTable.sortColumn(column);
      } else if (sender == makeSortableButton) {
        // Make column sortable
        int column = Integer.parseInt(columnIndexBox.getText());
        scrollTable.setColumnSortable(column, true);
      } else if (sender == makeUnsortableButton) {
        // Make column unsortable
        int column = Integer.parseInt(columnIndexBox.getText());
        scrollTable.setColumnSortable(column, false);
      } else if (sender == toggleSortingButton) {
        if (scrollTable.isSortingEnabled()) {
          scrollTable.setSortingEnabled(false);
          grid.setHTML(3, 1, "disabled");
        } else {
          scrollTable.setSortingEnabled(true);
          grid.setHTML(3, 1, "enabled");
        }
      }
    } catch (IndexOutOfBoundsException e) {
      Window.alert("The row or column index you entered is out of bounds.");
      throw e;
    } catch (NumberFormatException e) {
      Window.alert("Please enter valid integers for the row and column.");
      throw e;
    }
  }

  @Override
  protected Widget onInitialize() {
    grid = new Grid(4, 3);

    // Row Index 1
    HorizontalPanel panel1 = new HorizontalPanel();
    panel1.add(moveRowUpButton);
    panel1.add(new HTML("&nbsp;"));
    panel1.add(moveRowDownButton);
    panel1.add(new HTML("&nbsp;"));
    panel1.add(reverseRowsButton);
    rowIndexBox1.setWidth("50px");
    rowIndexBox1.setText("3");
    grid.setHTML(0, 0, "<B>Row 1:</B>");
    grid.setWidget(0, 1, rowIndexBox1);
    grid.setWidget(0, 2, panel1);

    // Row Index 2
    HorizontalPanel panel2 = new HorizontalPanel();
    panel2.add(swapRowsButton);
    rowIndexBox2.setWidth("50px");
    rowIndexBox2.setText("4");
    grid.setHTML(1, 0, "<B>Row 2:</B>");
    grid.setWidget(1, 1, rowIndexBox2);
    grid.setWidget(1, 2, panel2);

    // Column Index
    HorizontalPanel panel3 = new HorizontalPanel();
    panel3.add(sortColumnButton);
    panel3.add(new HTML("&nbsp;"));
    panel3.add(makeSortableButton);
    panel3.add(new HTML("&nbsp;"));
    panel3.add(makeUnsortableButton);
    columnIndexBox.setWidth("50px");
    columnIndexBox.setText("3");
    grid.setHTML(2, 0, "<B>Column:</B>");
    grid.setWidget(2, 1, columnIndexBox);
    grid.setWidget(2, 2, panel3);

    // Enable/Disable sorting
    DOM.setStyleAttribute(grid.getCellFormatter().getElement(3, 2), "border",
        "2px solid #AAAAAA");
    grid.setWidget(3, 0, toggleSortingButton);
    grid.setHTML(3, 1, "enabled");
    grid.setHTML(3, 2, DESC_SORTING);

    return grid;
  }
}
