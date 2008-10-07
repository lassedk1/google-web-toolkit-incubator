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

package com.google.gwt.museum.client.feature;

import com.google.gwt.libideas.logging.shared.TimingLog;
import com.google.gwt.museum.client.common.AbstractIssueOverride;
import com.google.gwt.museum.client.common.MoneyTableModel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.ResizableWidgetCollection;
import com.google.gwt.widgetideas.table.client.FixedWidthFlexTable;
import com.google.gwt.widgetideas.table.client.FixedWidthGrid;
import com.google.gwt.widgetideas.table.client.FixedWidthGridBulkRenderer;
import com.google.gwt.widgetideas.table.client.PagingScrollTable;
import com.google.gwt.widgetideas.table.client.RowPagingListener;

/**
 * Checks the timing of scrolling through a paging scroll table.
 * 
 */
public class IssuePagingScrollTableTiming extends AbstractIssueOverride {
  private MoneyTableModel model = new MoneyTableModel();
  int numColumns = 10;
  int numRows = 500;
  private PagingScrollTable t;

  @Override
  public Widget createIssue() {

    final VerticalPanel p = new VerticalPanel();
    FixedWidthFlexTable header = new FixedWidthFlexTable();
    for (int i = 0; i < numColumns; i++) {
      header.setText(0, i, "column" + i);
    }

    model.setNumColumns(numColumns);
    model.setNumRows(numRows);
    ResizableWidgetCollection.get().setResizeCheckingEnabled(false);
    t = new PagingScrollTable(model, new FixedWidthGrid(), header);
    t.setHeight("200px");
    t.setPageSize(50);
    FixedWidthGridBulkRenderer renderer = new FixedWidthGridBulkRenderer(
        t.getDataTable(), numColumns);
    t.setBulkRenderer(renderer);

    renderer.setStyleNameByColumn(0, "col1");
    renderer.setStyleNameByColumn(0, "col2");
    renderer.setStyleNameByColumn(0, "col3");
    renderer.setStyleNameByColumn(0, "col4");
    renderer.setStyleNameByColumn(0, "col5");

    t.setColumnWidth(0, 40);
    t.setColumnWidth(0, 40);

    t.addRowPagingListener(new RowPagingListener() {

      public void onNumPagesChanges(int numPages) {
      }

      public void onPageChanged(int page) {
      }

      public void onPageLoaded(int page) {
      }

      public void onPagingFailure(Throwable caught) {
      }

    });
    p.add(new Button("Switch page", new ClickListener() {

      public void onClick(Widget sender) {
        TimingLog.startTiming();
        if (t.isAttached() == false) {
          p.add(t);
          return;
        }

        if (t.getCurrentPage() == 0) {
          t.gotoPage(1, true);
        } else {
          t.gotoPage(0, true);
        }
      }

    }));
    return p;
  }

  @Override
  public String getInstructions() {
    return "TODO";
  }

  @Override
  public String getSummary() {
    return "TODO";
  }

  @Override
  public boolean hasCSS() {
    return false;
  }

}
