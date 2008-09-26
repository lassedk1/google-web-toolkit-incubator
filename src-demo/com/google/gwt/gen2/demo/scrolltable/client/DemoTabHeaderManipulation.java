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

import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel with options for manipulating header contents and number of cells.
 */
public class DemoTabHeaderManipulation extends DemoTab implements ClickListener {
  /**
   * The cell index box.
   */
  private TextBox cellIndexBox = new TextBox();

  /**
   * The colSpan text box.
   */
  private TextBox colSpanBox = new TextBox();

  /**
   * Button used to insert a cell.
   */
  private Button insertCellButton = new Button("Insert Cell", this);

  /**
   * Button used to insert a row.
   */
  private Button insertRowButton = new Button("Insert Row", this);

  /**
   * Button used to remove a cell.
   */
  private Button removeCellButton = new Button("Remove Cell", this);

  /**
   * Button used to remove a row.
   */
  private Button removeRowButton = new Button("Remove Row", this);

  /**
   * The row index box.
   */
  private TextBox rowIndexBox = new TextBox();

  /**
   * The rowSpan text box.
   */
  private TextBox rowSpanBox = new TextBox();

  /**
   * The button used to set colSpan of a cell.
   */
  private Button setColSpanButton = new Button("Set ColSpan", this);

  /**
   * The button used to set html of a cell.
   */
  private Button setHtmlButton = new Button("Set HTML", this);

  /**
   * The button used to set rowSpan of a cell.
   */
  private Button setRowSpanButton = new Button("Set RowSpan", this);

  /**
   * The button used to set text of a cell.
   */
  private Button setTextButton = new Button("Set Text", this);

  /**
   * The text box.
   */
  private TextBox textBox = new TextBox();

  /**
   * Handle click events from the buttons in this panel.
   * 
   * @param sender
   */
  public void onClick(Widget sender) {
    FixedWidthFlexTable headerTable = ScrollTableDemo.getHeaderTable();
    try {
      if (sender == setTextButton) {
        // Set Text
        int cell = Integer.parseInt(cellIndexBox.getText());
        int row = Integer.parseInt(rowIndexBox.getText());
        headerTable.setText(row, cell, textBox.getText());
      } else if (sender == setHtmlButton) {
        // Set HTML
        int cell = Integer.parseInt(cellIndexBox.getText());
        int row = Integer.parseInt(rowIndexBox.getText());
        headerTable.setHTML(row, cell, textBox.getText());
      } else if (sender == insertRowButton) {
        // Insert Row
        int row = Integer.parseInt(rowIndexBox.getText());
        headerTable.insertRow(row);
      } else if (sender == insertCellButton) {
        // Insert Cell
        int cell = Integer.parseInt(cellIndexBox.getText());
        int row = Integer.parseInt(rowIndexBox.getText());
        headerTable.insertCell(row, cell);
      } else if (sender == removeRowButton) {
        // Remove Row
        int row = Integer.parseInt(rowIndexBox.getText());
        headerTable.removeRow(row);
      } else if (sender == removeCellButton) {
        // Remove Cell
        int cell = Integer.parseInt(cellIndexBox.getText());
        int row = Integer.parseInt(rowIndexBox.getText());
        headerTable.removeCell(row, cell);
      } else if (sender == setRowSpanButton) {
        // Set RowSpan
        int cell = Integer.parseInt(cellIndexBox.getText());
        int row = Integer.parseInt(rowIndexBox.getText());
        int rowSpan = Integer.parseInt(rowSpanBox.getText());
        headerTable.getFlexCellFormatter().setRowSpan(row, cell, rowSpan);
      } else if (sender == setColSpanButton) {
        // Set ColSpan
        int cell = Integer.parseInt(cellIndexBox.getText());
        int row = Integer.parseInt(rowIndexBox.getText());
        int colSpan = Integer.parseInt(colSpanBox.getText());
        headerTable.getFlexCellFormatter().setColSpan(row, cell, colSpan);
      }
    } catch (IndexOutOfBoundsException e) {
      // Invalid cell index
      Window.alert("The column or row indexes you entered is out of bounds.");
    } catch (NumberFormatException e) {
      // Invalid Integer characters
      String message = "Please enter valid integers for row, column, rowSpan, and colSpan.";
      Window.alert(message);
    }
  }

  @Override
  protected Widget onInitialize() {
    Grid grid = new Grid(5, 3);

    // Row Index
    HorizontalPanel panel1 = new HorizontalPanel();
    panel1.add(insertRowButton);
    panel1.add(new HTML("&nbsp;"));
    panel1.add(removeRowButton);
    rowIndexBox.setWidth("50px");
    rowIndexBox.setText("0");
    grid.setHTML(0, 0, "<B>Row:</B>");
    grid.setWidget(0, 1, rowIndexBox);
    grid.setWidget(0, 2, panel1);

    // Column Index
    HorizontalPanel panel2 = new HorizontalPanel();
    panel2.add(insertCellButton);
    panel2.add(new HTML("&nbsp;"));
    panel2.add(removeCellButton);
    cellIndexBox.setWidth("50px");
    cellIndexBox.setText("0");
    grid.setHTML(1, 0, "<B>Cell:</B>");
    grid.setWidget(1, 1, cellIndexBox);
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

    // ColSpan
    HorizontalPanel panel4 = new HorizontalPanel();
    panel4.add(setColSpanButton);
    colSpanBox.setWidth("50px");
    colSpanBox.setText("1");
    grid.setHTML(3, 0, "<B>ColSpan:</B>");
    grid.setWidget(3, 1, colSpanBox);
    grid.setWidget(3, 2, panel4);

    // RowSpan
    HorizontalPanel panel5 = new HorizontalPanel();
    panel5.add(setRowSpanButton);
    rowSpanBox.setWidth("50px");
    rowSpanBox.setText("1");
    grid.setHTML(4, 0, "<B>RowSpan:</B>");
    grid.setWidget(4, 1, rowSpanBox);
    grid.setWidget(4, 2, panel5);

    return grid;
  }
}
