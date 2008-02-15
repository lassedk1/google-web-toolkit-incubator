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
package com.google.gwt.demos.scrollingbulkloadedtable.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.table.client.PagingGridBulkRenderer;
import com.google.gwt.widgetideas.table.client.ClientTableModel;
import com.google.gwt.widgetideas.table.client.EditablePagingGrid;
import com.google.gwt.widgetideas.table.client.FixedWidthFlexTable;
import com.google.gwt.widgetideas.table.client.PagingScrollTable;
import com.google.gwt.widgetideas.table.client.ScrollTable;
import com.google.gwt.widgetideas.table.client.TableModel;
import com.google.gwt.widgetideas.table.client.TextCellEditor;
import com.google.gwt.widgetideas.table.client.overrides.HTMLTable;

/**
 * Demo for a scrolling bulk loaded table.
 */
public class ScrollingBulkLoadedTableDemo implements EntryPoint {

  class TimingPagingGrid extends EditablePagingGrid {
    long currentTime = 0;

    public TimingPagingGrid(TableModel model) {
      super(model);
    }

    public void gotoPage(int page, boolean forced) {
      currentTime = System.currentTimeMillis();
      super.gotoPage(page, forced);
    }

    /**
     * Fire an event when a page has loaded.
     */
    protected void firePageLoadedEvent() {
      super.firePageLoadedEvent();
      Window.alert("Page loaded in "
          + (System.currentTimeMillis() - currentTime) + " milliseconds");
    }
  }

  int numColumns = 8;
  int numRowsPerPage = 4;
  int numPages = 2;
  int numRows = numPages * numRowsPerPage;
  ScrollTable current;

  /**
   * Create Demo.
   */
  public void onModuleLoad() {
    final TableModel adaptor = createOracle();
    final TextBox numColumnsBox = new TextBox();
    numColumnsBox.addChangeListener(new ChangeListener() {
      public void onChange(Widget sender) {
        numColumns = toInt(numColumnsBox);
      }
    });

    numColumnsBox.setText(numColumns + "");
    final TextBox numRowsBox = new TextBox();
    numRowsBox.setText(numRowsPerPage + "");
    numRowsBox.addChangeListener(new ChangeListener() {
      public void onChange(Widget sender) {
        numRowsPerPage = Integer.parseInt(numRowsBox.getText());
        numRows = numPages * numRowsPerPage;
      }
    });

    final Button addCheckBoxes = new Button("Go", new ClickListener() {

      public void onClick(Widget sender) {
        if (current != null) {
          long currentTime = System.currentTimeMillis();
          HTMLTable body = (HTMLTable) current.getDataTable();
          ClickListener l = new ClickListener() {

            public void onClick(Widget sender) {
              Window.alert("I was clicked");
            }
          };
          for (int i = 0; i < numRowsPerPage; i++) {
            CheckBox b = new CheckBox("row" + i);
            b.addClickListener(l);
            body.setWidget(i, 0, b);
          }
          Window.alert(System.currentTimeMillis() - currentTime
              + " milliseconds to create and add checkboxes");
        }
      }
    });

    final Button addTextBoxes = new Button("Go", new ClickListener() {

      public void onClick(Widget sender) {
        if (current != null) {
          long currentTime = System.currentTimeMillis();
          HTMLTable body = (HTMLTable) current.getDataTable();
          for (int i = 0; i < numRowsPerPage; i++) {
            for (int j = 0; j < numColumns; j++) {
              TextBox t = new TextBox();
              body.setWidget(i, j, t);
            }
          }
          Window.alert(System.currentTimeMillis() - currentTime
              + " milliseconds to create text boxes");
        }
      }
    });

    FlexTable t = new FlexTable();
    t.setText(0, 0, "num rows per page");
    t.setWidget(0, 1, numRowsBox);
    t.setText(0, 2, "num columns:");
    t.setWidget(0, 3, numColumnsBox);
    t.setText(1, 0, "add checkboxes to first column");
    t.setWidget(1, 1, addCheckBoxes);
    t.setText(1, 2, "add text boxes to all cells");
    t.setWidget(1, 3, addTextBoxes);

    RootPanel.get().add(t);

    Button b = new Button("Create traditional ScrollTable");
    b.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        clearCurrent();
        EditablePagingGrid body = new TimingPagingGrid(adaptor);
        createScrollTable(body);
      }
    });
    RootPanel.get().add(b);
    Button b2 = new Button("Create bulkloading ScrollTable");
    b2.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        clearCurrent();
        EditablePagingGrid body = new TimingPagingGrid(adaptor);
        new PagingGridBulkRenderer(body, numColumns);
        createScrollTable(body);
      }
    });
    RootPanel.get().add(b2);
    Button c = new Button("Clear current table", new ClickListener() {
      public void onClick(Widget sender) {
        clearCurrent();
      }

    });
    RootPanel.get().add(c);
  }

  private void clearCurrent() {
    if (current != null) {
      current.removeFromParent();
    }
  }

  private TableModel createOracle() {
    ClientTableModel oracle = new ClientTableModel() {
      public Object getCell(int rowNum, int cellNum) {
        if (rowNum >= numRows | cellNum >= numColumns) {
          return null;
        }
        return "cell " + rowNum + ", " + cellNum;
      }

      public void onSetData(int row, int cell, Object data) {
        RootPanel.get().add(new Label("Setting cell: " + data));
      }
    };
    return oracle;
  }

  private void createScrollTable(EditablePagingGrid body) {
    // Must set page size for paging scroll table to display.
    body.setPageSize(numRows / 2);

    // Add cell editor.
    for (int i = 1; i < numColumns; i++) {
      body.setCellEditor(i, new TextCellEditor());
    }

    // Scroll Table header.
    FixedWidthFlexTable header = new FixedWidthFlexTable();
    for (int i = 0; i < numColumns; i++) {
      header.setText(0, i, "Col " + i);
    }

    PagingScrollTable scrollTable = new PagingScrollTable(body, header);

    // Must set pixel size if scrolling is enabled.
    scrollTable.setPixelSize(700, 200);
    scrollTable.setScrollingEnabled(true);
    body.gotoFirstPage();
    RootPanel.get().add(scrollTable);
    current = scrollTable;
  }

  private int toInt(TextBox box) {
    String s = box.getText();
    return Integer.parseInt(s);
  }
}
