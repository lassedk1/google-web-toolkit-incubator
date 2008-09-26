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
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnSortList;
import com.google.gwt.gen2.table.event.client.CellHighlightEvent;
import com.google.gwt.gen2.table.event.client.CellHighlightHandler;
import com.google.gwt.gen2.table.event.client.CellUnhighlightEvent;
import com.google.gwt.gen2.table.event.client.CellUnhighlightHandler;
import com.google.gwt.gen2.table.event.client.ColumnSortEvent;
import com.google.gwt.gen2.table.event.client.ColumnSortHandler;
import com.google.gwt.gen2.table.event.client.RowHighlightEvent;
import com.google.gwt.gen2.table.event.client.RowHighlightHandler;
import com.google.gwt.gen2.table.event.client.RowSelectionEvent;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.gen2.table.event.client.RowUnhighlightEvent;
import com.google.gwt.gen2.table.event.client.RowUnhighlightHandler;
import com.google.gwt.gen2.table.event.client.CellHighlightEvent.Cell;
import com.google.gwt.gen2.table.event.client.RowHighlightEvent.Row;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Set;

/**
 * Logs events fired by the ScrollTable.
 */
public class DemoTabPanelLog extends DemoTab implements ClickListener, TableListener,
    CellHighlightHandler, CellUnhighlightHandler, RowHighlightHandler, RowUnhighlightHandler,
    RowSelectionHandler, ColumnSortHandler {
  /**
   * The button used to clear the log.
   */
  Button clearButton = new Button("Clear Log", this);

  /**
   * The label used to display the currently highlighted cell.
   */
  private Label highlightedCellLabel = new Label("Highlighted cell:");

  /**
   * The label used to display the currently highlighted row.
   */
  private Label highlightedRowLabel = new Label("Highlighted row:");

  /**
   * The label that contains the log info.
   */
  private HTML logLabel = new HTML();

  /**
   * The line count in the log.
   */
  private int lineCount = 0;

  /**
   * The scroll panel that holds the log.
   */
  private ScrollPanel scrollPanel = new ScrollPanel(logLabel);

  /**
   * The label used to display the previously highlighted cell.
   */
  private Label unhighlightedCellLabel = new Label("Last unhighlighted cell:");

  /**
   * The label used to display the previously highlighted row.
   */
  private Label unhighlightedRowLabel = new Label("Last unhighlighted row:");

  public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
    addText("cell clicked: (" + row + "," + cell + ")", "#ff00ff");
  }

  public void onCellHighlight(CellHighlightEvent event) {
    Cell cell = event.getValue();
    highlightedCellLabel.setText("Highlighted cell: (" + cell.getRowIndex() + ","
        + cell.getCellIndex() + ")");
  }

  public void onCellUnhighlight(CellUnhighlightEvent event) {
    Cell cell = event.getValue();
    unhighlightedCellLabel.setText("Last unhighlighted cell: (" + cell.getRowIndex() + ","
        + cell.getCellIndex() + ")");
  }

  /**
   * Handle click events from the buttons in this panel.
   * 
   * @param sender
   */
  public void onClick(Widget sender) {
    if (sender == clearButton) {
      logLabel.setHTML("");
      lineCount = 0;
    }
  }

  public void onColumnSorted(ColumnSortEvent event) {
    ColumnSortList sortList = event.getColumnSortList();
    int column = -1;
    boolean ascending = true;
    if (sortList != null) {
      column = sortList.getPrimaryColumn();
      ascending = sortList.isPrimaryAscending();
    }
    if (ascending) {
      addText("sorted column: " + column + " (ascending)", "black");
    } else {
      addText("sorted column: " + column, "black");
    }
  }

  public void onRowHighlight(RowHighlightEvent event) {
    Row cell = event.getValue();
    highlightedRowLabel.setText("Highlighted row: (" + cell.getRowIndex() + ")");
  }

  public void onRowSelection(RowSelectionEvent event) {
    // Show the previously selected rows
    Set<Row> deselectedRows = event.getDeselectedRows();
    String previous = "Previously selected rows: ";
    for (Row row : event.getOldValue()) {
      if (deselectedRows.contains(row)) {
        previous += "-";
      }
      previous += row.getRowIndex() + ", ";
    }
    addText(previous, "green");

    // Show the currently selected rows
    Set<Row> selectedRows = event.getSelectedRows();
    String current = "Currently selected rows: ";
    for (Row row : event.getNewValue()) {
      if (selectedRows.contains(row)) {
        current += "+";
      }
      current += row.getRowIndex() + ", ";
    }

    addText(current, "green");
  }

  public void onRowUnhighlight(RowUnhighlightEvent event) {
    Row cell = event.getValue();
    unhighlightedRowLabel.setText("Last unhighlighted row: (" + cell.getRowIndex() + ")");
  }

  @Override
  protected Widget onInitialize() {
    FixedWidthGrid dataTable = ScrollTableDemo.getDataTable();
    logLabel.setHeight("200px");
    scrollPanel.setWidth("95%");
    scrollPanel.setHeight("200px");
    DOM.setStyleAttribute(logLabel.getElement(), "font", "8pt/10pt courier");
    DOM.setStyleAttribute(scrollPanel.getElement(), "border", "1px solid black");
    dataTable.addColumnSortHandler(this);
    dataTable.addCellHighlightHandler(this);
    dataTable.addCellUnhighlightHandler(this);
    dataTable.addRowHighlightHandler(this);
    dataTable.addRowUnhighlightHandler(this);
    dataTable.addRowSelectionHandler(this);

    VerticalPanel panel = new VerticalPanel();
    panel.setWidth("100%");
    panel.add(scrollPanel);
    panel.add(clearButton);
    panel.add(highlightedCellLabel);
    panel.add(highlightedRowLabel);
    panel.add(unhighlightedRowLabel);
    panel.add(unhighlightedCellLabel);
    return panel;
  }

  /**
   * Add some data to the log.
   * 
   * @param text the text to add
   * @param color the text color
   */
  private void addText(String text, String color) {
    text = "<B>" + lineCount + ":</B> " + "<FONT color=\"" + color + "\">" + text + "</FONT>"
        + "<BR>" + logLabel.getHTML();
    logLabel.setHTML(text);
    lineCount++;
  }
}
