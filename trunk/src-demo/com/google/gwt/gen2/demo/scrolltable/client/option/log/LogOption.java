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
package com.google.gwt.gen2.demo.scrolltable.client.option.log;

import com.google.gwt.core.client.Duration;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.demo.scrolltable.client.PagingScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.ScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.StudentPagingScrollTable;
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnSortList;
import com.google.gwt.gen2.table.event.client.CellHighlightEvent;
import com.google.gwt.gen2.table.event.client.CellHighlightHandler;
import com.google.gwt.gen2.table.event.client.CellUnhighlightEvent;
import com.google.gwt.gen2.table.event.client.CellUnhighlightHandler;
import com.google.gwt.gen2.table.event.client.ColumnSortEvent;
import com.google.gwt.gen2.table.event.client.ColumnSortHandler;
import com.google.gwt.gen2.table.event.client.PageChangeEvent;
import com.google.gwt.gen2.table.event.client.PageChangeHandler;
import com.google.gwt.gen2.table.event.client.PageLoadEvent;
import com.google.gwt.gen2.table.event.client.PageLoadHandler;
import com.google.gwt.gen2.table.event.client.RowHighlightEvent;
import com.google.gwt.gen2.table.event.client.RowHighlightHandler;
import com.google.gwt.gen2.table.event.client.RowSelectionEvent;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.gen2.table.event.client.RowUnhighlightEvent;
import com.google.gwt.gen2.table.event.client.RowUnhighlightHandler;
import com.google.gwt.gen2.table.event.client.TableEvent.Cell;
import com.google.gwt.gen2.table.event.client.TableEvent.Row;
import com.google.gwt.gen2.table.override.client.FlexTable;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;

import java.util.Set;

/**
 * An option.
 */
public class LogOption extends AbstractOption {
  /**
   * The main layout widget.
   */
  private FlexTable layout = null;

  /**
   * The label that contains the log info.
   */
  private HTML logLabel = null;

  /**
   * The line count in the log.
   */
  private int lineCount = 0;

  /**
   * The duration of a page load.
   */
  private Duration pageLoadDuration = null;

  public LogOption() {
    // The log should initialize immediately
    initialize();
  }

  @Override
  protected String getDescription() {
    return "";
  }

  @Override
  protected Widget onInitialize() {
    layout = new FlexTable();

    // Create the log area
    logLabel = new HTML();
    logLabel.setHeight("200px");
    DOM.setStyleAttribute(logLabel.getElement(), "font", "8pt/10pt courier");
    ScrollPanel scrollPanel = new ScrollPanel(logLabel);
    scrollPanel.setPixelSize(500, 200);
    DOM.setStyleAttribute(scrollPanel.getElement(), "border", "1px solid black");
    layout.setWidget(0, 0, scrollPanel);
    layout.getFlexCellFormatter().setColSpan(0, 0, 2);

    // Add a clear button
    Button clearButton = new Button("Clear Log", new ClickHandler() {
      public void onClick(ClickEvent event) {
        logLabel.setHTML("");
        lineCount = 0;
      }
    });
    layout.setWidget(1, 0, clearButton);
    layout.getFlexCellFormatter().setColSpan(1, 0, 2);

    // Add labels for highlighting
    final Label highlightedCellLabel = new Label("Highlighted cell:");
    final Label highlightedRowLabel = new Label("Highlighted row:");
    final Label unhighlightedCellLabel = new Label("Last unhighlighted cell:");
    final Label unhighlightedRowLabel = new Label("Last unhighlighted row:");
    layout.setWidget(2, 0, highlightedCellLabel);
    layout.setWidget(3, 0, highlightedRowLabel);
    layout.setWidget(2, 1, unhighlightedRowLabel);
    layout.setWidget(3, 1, unhighlightedCellLabel);

    // Add all of the listeners
    FixedWidthGrid dataTable = ScrollTableDemo.get().getDataTable();
    dataTable.addTableListener(new TableListener() {
      public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
        addLogEntry("cell clicked: (" + row + "," + cell + ")", "#ff00ff");
      }
    });
    dataTable.addColumnSortHandler(new ColumnSortHandler() {
      public void onColumnSorted(ColumnSortEvent event) {
        ColumnSortList sortList = event.getColumnSortList();
        int column = -1;
        boolean ascending = true;
        if (sortList != null) {
          column = sortList.getPrimaryColumn();
          ascending = sortList.isPrimaryAscending();
        }
        if (ascending) {
          addLogEntry("sorted column: " + column + " (ascending)", "black");
        } else {
          addLogEntry("sorted column: " + column, "black");
        }
      }
    });
    dataTable.addCellHighlightHandler(new CellHighlightHandler() {
      public void onCellHighlight(CellHighlightEvent event) {
        Cell cell = event.getValue();
        highlightedCellLabel.setText("Highlighted cell: (" + cell.getRowIndex()
            + "," + cell.getCellIndex() + ")");
      }
    });
    dataTable.addCellUnhighlightHandler(new CellUnhighlightHandler() {
      public void onCellUnhighlight(CellUnhighlightEvent event) {
        Cell cell = event.getValue();
        unhighlightedCellLabel.setText("Last unhighlighted cell: ("
            + cell.getRowIndex() + "," + cell.getCellIndex() + ")");
      }
    });
    dataTable.addRowHighlightHandler(new RowHighlightHandler() {
      public void onRowHighlight(RowHighlightEvent event) {
        Row cell = event.getValue();
        highlightedRowLabel.setText("Highlighted row: (" + cell.getRowIndex()
            + ")");
      }
    });
    dataTable.addRowUnhighlightHandler(new RowUnhighlightHandler() {
      public void onRowUnhighlight(RowUnhighlightEvent event) {
        Row cell = event.getValue();
        unhighlightedRowLabel.setText("Last unhighlighted row: ("
            + cell.getRowIndex() + ")");
      }
    });
    dataTable.addRowSelectionHandler(new RowSelectionHandler() {
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
        addLogEntry(previous, "green");

        // Show the currently selected rows
        Set<Row> selectedRows = event.getSelectedRows();
        String current = "Currently selected rows: ";
        for (Row row : event.getNewValue()) {
          if (selectedRows.contains(row)) {
            current += "+";
          }
          current += row.getRowIndex() + ", ";
        }

        addLogEntry(current, "green");
      }
    });

    // Paging specific options
    if (PagingScrollTableDemo.get() != null) {
      StudentPagingScrollTable pagingScrollTable = PagingScrollTableDemo.get().getPagingScrollTable();
      if (pagingScrollTable != null) {
        pagingScrollTable.addPageChangeHandler(new PageChangeHandler() {
          public void onPageChange(PageChangeEvent event) {
            pageLoadDuration = new Duration();
          }
        });

        pagingScrollTable.addPageLoadHandler(new PageLoadHandler() {
          public void onPageLoad(PageLoadEvent event) {
            // Convert to 1 based index
            int page = event.getPage() + 1;
            int duration = -1;
            if (pageLoadDuration != null) {
              duration = pageLoadDuration.elapsedMillis();
              pageLoadDuration = null;
            }
            String text = "Page " + page + " loaded in " + duration + "ms";
            addLogEntry(text, "black");
          }
        });
      }
    }

    return layout;
  }

  /**
   * Add some data to the log.
   * 
   * @param text the text to add
   * @param color the text color
   */
  private void addLogEntry(String text, String color) {
    text = "<B>" + lineCount + ":</B> " + "<FONT color=\"" + color + "\">"
        + text + "</FONT>" + "<BR>" + logLabel.getHTML();
    logLabel.setHTML(text);
    lineCount++;
  }
}
