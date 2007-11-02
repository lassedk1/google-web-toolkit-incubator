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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.table.client.BulkEditablePagingGrid;
import com.google.gwt.widgetideas.table.client.ClientTableModel;
import com.google.gwt.widgetideas.table.client.EditablePagingGrid;
import com.google.gwt.widgetideas.table.client.FixedWidthFlexTable;
import com.google.gwt.widgetideas.table.client.PagingScrollTable;
import com.google.gwt.widgetideas.table.client.ScrollTable;
import com.google.gwt.widgetideas.table.client.TableModel;
import com.google.gwt.widgetideas.table.client.TextCellEditor;

/**
 * Demo for a scrolling bulk loaded table.
 */
public class ScrollingBulkLoadedTableDemo implements EntryPoint {
  int numColumns = 8;
  int numRows = 200;
  ScrollTable current;

  /**
   * Create Demo.
   */
  public void onModuleLoad() {
    final TableModel adaptor = createOracle();
    final TextBox numColumnsBox = new TextBox();
    numColumnsBox.setText(numColumns + "");
    final TextBox numRowsBox = new TextBox();
    numRowsBox.setText(numRows + "");
    FlexTable t = new FlexTable();
    t.setText(0, 0, "num rows");
    t.setWidget(0, 1, numRowsBox);
    t.setText(0, 2, "num columns:");
    t.setWidget(0, 3, numColumnsBox);
    RootPanel.get().add(t);

    Button b = new Button("Create traditional ScrollTable");
    b.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        EditablePagingGrid body = new EditablePagingGrid(adaptor);
        createScrollTable(body);
      }
    });
    RootPanel.get().add(b);
    Button b2 = new Button("Create bulkloading ScrollTable");
    b2.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        EditablePagingGrid body =
            new BulkEditablePagingGrid(adaptor, toInt(numRowsBox),
                toInt(numColumnsBox));
        createScrollTable(body);
      }

      private int toInt(TextBox box) {
        String s = box.getText();
        return Integer.parseInt(s);
      }
    });
    RootPanel.get().add(b2);
    Button c = new Button("Clear current table", new ClickListener() {
      public void onClick(Widget sender) {
        if (current != null) {
          current.removeFromParent();
        }
      }
    });

    RootPanel.get().add(c);
  }

  private void createScrollTable(EditablePagingGrid body) {
    // Must set page size for paging scroll table to display.
    body.setPageSize(numRows / 2);

    // Add cell editor.
    for (int i = 0; i < numColumns; i++) {
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
}
