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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.table.client.FixedWidthGrid;

/**
 * Panel with options for manipulating data contents and number of cells.
 */
public class DemoTabDataManipulation extends DemoTab implements ClickListener {
  /**
   * The button used to add 1 row.
   */
  protected Button add1RowButton = new Button("Insert 1 Row", this);

  /**
   * The button used to add 10 row.
   */
  protected Button add10RowButton = new Button("Insert 10 Rows", this);

  /**
   * The button used to add 100 row.
   */
  protected Button add100RowButton = new Button("Insert 100 Rows", this);

  /**
   * The column index box.
   */
  protected TextBox columnIndexBox = new TextBox();

  /**
   * The button used to remove a row.
   */
  protected Button removeRowButton = new Button("Remove Row", this);

  /**
   * The button used to resize the number of columns.
   */
  protected Button resizeColumnCountButton = new Button("Set Column Count", this);

  /**
   * The row index box.
   */
  protected TextBox rowIndexBox = new TextBox();

  /**
   * The button used to set html of a cell.
   */
  protected Button setHtmlButton = new Button("Set HTML", this);

  /**
   * The button used to set text of a cell.
   */
  protected Button setTextButton = new Button("Set Text", this);

  /**
   * The text box.
   */
  protected TextBox textBox = new TextBox();

  /**
   * Handle click events from the buttons in this panel.
   * 
   * @param sender
   */
  public void onClick(Widget sender) {
    FixedWidthGrid dataTable = ScrollTableDemo.getDataTable();
    try {
      if (sender == setTextButton) {
        // Set cell text
        int column = Integer.parseInt(columnIndexBox.getText());
        int row = Integer.parseInt(rowIndexBox.getText());
        dataTable.setText(row, column, textBox.getText());
      } else if (sender == setHtmlButton) {
        // Set cell HTML
        int column = Integer.parseInt(columnIndexBox.getText());
        int row = Integer.parseInt(rowIndexBox.getText());
        dataTable.setHTML(row, column, textBox.getText());
      } else if (sender == add1RowButton) {
        // Insert 1 row
        int row = Integer.parseInt(rowIndexBox.getText());
        ScrollTableDemo.insertDataRow(row);
      } else if (sender == add10RowButton) {
        // Insert 10 rows
        int row = Integer.parseInt(rowIndexBox.getText());
        for (int i = row; i < row + 10; i++) {
          ScrollTableDemo.insertDataRow(i);
        }
      } else if (sender == add100RowButton) {
        // Insert 100 rows
        int row = Integer.parseInt(rowIndexBox.getText());
        for (int i = row; i < row + 100; i++) {
          ScrollTableDemo.insertDataRow(i);
        }
      } else if (sender == removeRowButton) {
        // Remove a row
        int row = Integer.parseInt(rowIndexBox.getText());
        dataTable.removeRow(row);
      } else if (sender == resizeColumnCountButton) {
        // Set column count
        int column = Integer.parseInt(columnIndexBox.getText());
        dataTable.resizeColumns(column);
      }
    } catch (IndexOutOfBoundsException e) {
      Window.alert("The cell index you entered is out of bounds.");
    } catch (NumberFormatException e) {
      Window.alert("Please enter valid integers for the row and column.");
    }
  }

  protected Widget onInitialize() {
    Grid grid = new Grid(3, 3);

    // Row Index
    HorizontalPanel panel1 = new HorizontalPanel();
    panel1.add(add1RowButton);
    panel1.add(new HTML("&nbsp;"));
    panel1.add(add10RowButton);
    panel1.add(new HTML("&nbsp;"));
    panel1.add(add100RowButton);
    panel1.add(new HTML("&nbsp;"));
    panel1.add(removeRowButton);
    rowIndexBox.setWidth("50px");
    rowIndexBox.setText("4");
    grid.setHTML(0, 0, "<B>Row:</B>");
    grid.setWidget(0, 1, rowIndexBox);
    grid.setWidget(0, 2, panel1);

    // Column Index
    HorizontalPanel panel2 = new HorizontalPanel();
    panel2.add(resizeColumnCountButton);
    columnIndexBox.setWidth("50px");
    columnIndexBox.setText("4");
    grid.setHTML(1, 0, "<B>Column:</B>");
    grid.setWidget(1, 1, columnIndexBox);
    grid.setWidget(1, 2, panel2);

    // HTML string
    HorizontalPanel panel3 = new HorizontalPanel();
    panel3.add(setTextButton);
    panel3.add(new HTML("&nbsp;"));
    panel3.add(setHtmlButton);
    textBox.setWidth("200px");
    textBox.setText("<B>Hello World</B>");
    grid.setHTML(2, 0, "<B>Text:</B>");
    grid.setWidget(2, 1, textBox);
    grid.setWidget(2, 2, panel3);
    
    return grid;
  }
}
