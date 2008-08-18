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

package com.google.gwt.demos.bulkloadingtable.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.table.client.ClientTableModel;
import com.google.gwt.widgetideas.table.client.FlexTableBulkRenderer;
import com.google.gwt.widgetideas.table.client.PreloadedTable;
import com.google.gwt.widgetideas.table.client.RendererCallback;
import com.google.gwt.widgetideas.table.client.overrides.FlexTable;
import com.google.gwt.widgetideas.table.client.overrides.Grid;
import com.google.gwt.widgetideas.table.client.overrides.HTMLTable;

/**
 * Demo of {@link PreloadedTable} and
 * {@link com.google.gwt.widgetideas.table.client.TableBulkRenderer}.
 */
public class BulkLoadingTableDemo implements EntryPoint {
  int numColumns = 15;
  int numRows = 120;
  HTMLTable curTable;
  Panel panel;

  ClientTableModel<Object> tableModel = new ClientTableModel<Object>() {
    @Override
    public Object getCell(int rowNum, int cellNum) {
      if (rowNum >= numRows | cellNum >= numColumns) {
        return null;
      }
      return "cell " + rowNum + ", " + cellNum;
    }

    @Override
    protected boolean onRowInserted(int beforeRow) {
      return false;
    }

    @Override
    protected boolean onRowRemoved(int row) {
      return false;
    }

    @Override
    protected boolean onSetData(int row, int cell, Object data) {
      return false;
    }
  };

  public void log(String log) {
    Window.setTitle(log);
  }

  public void onModuleLoad() {

    panel = new VerticalPanel();
    RootPanel.get().add(panel);
    panel.add(new HTML(
        "<h2>A very boring demo showing the speed difference of using bulk loading tables.</h2>"));
    panel.add(new Label("Number of rows"));
    final TextBox rows = new TextBox();
    panel.add(rows);
    rows.setText(numRows + "");
    rows.addChangeListener(new ChangeListener() {
      public void onChange(Widget sender) {
        numRows = Integer.parseInt(rows.getText().trim());
      }
    });

    panel.add(new Label("Number of columns"));
    final TextBox columns = new TextBox();
    panel.add(columns);
    columns.addChangeListener(new ChangeListener() {

      public void onChange(Widget sender) {
        numColumns = Integer.parseInt(columns.getText());
      }
    });
    columns.setText(numColumns + "");

    panel.add(new HTML(
        "<p/><p/><b>Clear Table now </b> (clearing will also happen if the buttons are clicked below)"));
    panel.add(new Button("Go", new ClickListener() {

      public void onClick(Widget sender) {
        clearTable();
      }

    }));

    panel.add(new HTML("<p/><p/><b> Use the traditional FlexTable API</b>"));
    Button flexTableAPI = new Button("Go", new ClickListener() {

      public void onClick(Widget sender) {
        clearTable();
        long milli = System.currentTimeMillis();
        FlexTable newTable = new FlexTable();
        usingFlexTableAPI(newTable);
        finishTable(newTable, milli);
      }
    });
    panel.add(flexTableAPI);

    panel.add(new HTML("<p/><p/><b> Use the traditional Grid API</b>"));
    Button gridAPI = new Button("Go", new ClickListener() {
      public void onClick(Widget sender) {
        clearTable();
        long milli = System.currentTimeMillis();
        Grid newTable = new Grid();
        usingGridAPI(newTable);
        finishTable(newTable, milli);
      }

    });
    panel.add(gridAPI);

    panel.add(new HTML("<p/><p/><b> Use the attached Grid API</b>"));
    Button detachedGridAPI = new Button("Go", new ClickListener() {
      public void onClick(Widget sender) {
        clearTable();

        long milli = System.currentTimeMillis();
        Grid table = new Grid();
        curTable = table;
        table.setBorderWidth(2);
        panel.add(table);
        usingGridAPI(table);
        log("Finished in " + (System.currentTimeMillis() - milli)
            + " milliseconds");
      }

    });
    panel.add(detachedGridAPI);

    panel.add(new HTML("<p/><p/><b> Use Async BulkLoadedTable API</b>"));
    Button asyncAPI = new Button("Go", new ClickListener() {
      public void onClick(Widget sender) {
        clearTable();
        long milli = System.currentTimeMillis();
        FlexTable table = new FlexTable();
        usingBulkLoadedTableAPI(table, milli);
      }
    });

    panel.add(asyncAPI);

    panel.add(new HTML("<p/><p/><b> Use the PreloadedTable  API</b>"));
    Button pendingAPI = new Button("Go", new ClickListener() {

      public void onClick(Widget sender) {
        clearTable();
        long milli = System.currentTimeMillis();
        PreloadedTable table = new PreloadedTable();
        usingPreloadedTableAPI(table);
        finishTable(table, milli);
      }

    });
    panel.add(pendingAPI);
  }

  private void clearTable() {
    if (curTable != null) {
      curTable.removeFromParent();
      curTable = null;
    }
  }

  private void finishTable(final HTMLTable table, final long milli) {

    // In order to compare apples-to-apples for rendering time letting event cue
    // flush once.

    curTable = table;
    table.setBorderWidth(2);
    panel.add(table);

    table.setWidget(0, 3, new Button("A widget"));
    log("Finished in " + (System.currentTimeMillis() - milli) + " milliseconds");
  }

  private void usingBulkLoadedTableAPI(final FlexTable table, final long milli) {
    RendererCallback callback = new RendererCallback() {
      public void onRendered() {
        finishTable(table, milli);
      }
    };

    FlexTableBulkRenderer renderer = new FlexTableBulkRenderer(table);
    renderer.renderRows(tableModel, callback);
  }

  private void usingFlexTableAPI(FlexTable table) {
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numColumns; j++) {
        table.setHTML(i, j, "cell " + i + ", " + j);
      }
    }
  }

  private void usingGridAPI(Grid table) {
    table.resize(numRows, numColumns);
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numColumns; j++) {
        table.setHTML(i, j, "cell " + i + ", " + j);
      }
    }
  }

  private void usingPreloadedTableAPI(PreloadedTable table) {
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numColumns; j++) {
        table.setPendingHTML(i, j, "cell " + i + ", " + j);
      }
    }
  }
}
