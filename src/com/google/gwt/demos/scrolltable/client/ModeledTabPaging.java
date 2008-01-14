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
import com.google.gwt.widgetideas.table.client.CachedTableController;
import com.google.gwt.widgetideas.table.client.PagingScrollTable;

/**
 * Panel with options for paging.
 */
public class ModeledTabPaging extends DemoTab implements ClickListener {
  /**
   * The cache size.
   */
  private TextBox cacheSizeBox = new TextBox();

  /**
   * The button used to set the post cache size.
   */
  private Button cachePostSizeButton = new Button("Set Post Cache Size", this);

  /**
   * The button used to set the pre cache size.
   */
  private Button cachePreSizeButton = new Button("Set Pre Cache Size", this);

  /**
   * The button used to toggle error mode.
   */
  private Button errorModeButton = new Button("Toggle Error Mode", this);

  /**
   * The main layout grid.
   */
  private Grid grid = null;

  /**
   * The num rows box.
   */
  private TextBox numRowsBox = new TextBox();

  /**
   * The button used to set the number of rows.
   */
  private Button numRowsButton = new Button("Set Num Rows", this);

  /**
   * The page size box.
   */
  private TextBox pageSizeBox = new TextBox();

  /**
   * The button used to set the number of rows per page.
   */
  private Button pageSizeButton = new Button("Set Page Size", this);

  /**
   * Keep track of the paging options.
   */
  private boolean pagingOptionsVisible = true;

  /**
   * The button used to toggle RPC mode.
   */
  private Button rpcModeButton = new Button("Toggle RPC Mode", this);

  /**
   * The button used to set html of a cell.
   */
  private Button togglePagingButton = new Button("Toggle Paging Options", this);

  /**
   * Handle click events from the buttons in this panel.
   * 
   * @param sender
   */
  public void onClick(Widget sender) {
    PagingScrollTable scrollTable = PagingScrollTableDemo.getPagingScrollTable();
    CachedTableController tableController = PagingScrollTableDemo.getTableController();
    DataSourceTableModel tableModel = PagingScrollTableDemo.getTableModel();
    try {
      if (sender == numRowsButton) {
        // Set number of rows
        int row = Integer.parseInt(numRowsBox.getText());
        tableController.setNumRows(row);
      } else if (sender == pageSizeButton) {
        // Set page size
        int pageSize = Integer.parseInt(pageSizeBox.getText());
        scrollTable.setPageSize(pageSize);
      } else if (sender == togglePagingButton) {
        // Toggle paging options
        pagingOptionsVisible = !pagingOptionsVisible;
        scrollTable.setPagingOptionsVisible(pagingOptionsVisible);
      } else if (sender == cachePreSizeButton) {
        // Set Precached rows
        int cacheSize = Integer.parseInt(cacheSizeBox.getText());
        tableController.setNumPreCachedRows(cacheSize);
      } else if (sender == cachePostSizeButton) {
        // Set Post cached rows
        int cacheSize = Integer.parseInt(cacheSizeBox.getText());
        tableController.setNumPostCachedRows(cacheSize);
      } else if (sender == errorModeButton) {
        // Toggle error mode
        boolean enabled = tableModel.isErrorModeEnabled();
        tableModel.setErrorModeEnabled(!enabled);
        if (enabled) {
          grid.setHTML(3, 1, "disabled");
        } else {
          grid.setHTML(3, 1, "enabled");
        }
      } else if (sender == rpcModeButton) {
        // Toggle RPC mode
        boolean enabled = tableModel.isRPCModeEnabled();
        tableModel.setRPCModeEnabled(!enabled);
        if (enabled) {
          grid.setHTML(4, 1, "disabled");
        } else {
          grid.setHTML(4, 1, "enabled");
        }
      }
    } catch (NumberFormatException e) {
      Window.alert("Please enter valid integers for the row and column.");
    }
  }

  protected Widget onInitialize() {
    grid = new Grid(5, 3);

    // Num Rows
    HorizontalPanel panel1 = new HorizontalPanel();
    panel1.add(numRowsButton);
    panel1.add(new HTML("&nbsp;"));
    panel1.add(togglePagingButton);
    numRowsBox.setWidth("50px");
    numRowsBox.setText("1000");
    grid.setHTML(0, 0, "<B>Num Rows:</B>");
    grid.setWidget(0, 1, numRowsBox);
    grid.setWidget(0, 2, panel1);

    // Page Size
    HorizontalPanel panel2 = new HorizontalPanel();
    panel2.add(pageSizeButton);
    pageSizeBox.setWidth("50px");
    pageSizeBox.setText("8");
    grid.setHTML(1, 0, "<B>Page Size:</B>");
    grid.setWidget(1, 1, pageSizeBox);
    grid.setWidget(1, 2, panel2);

    // Cache size
    HorizontalPanel panel3 = new HorizontalPanel();
    panel3.add(cachePreSizeButton);
    panel3.add(new HTML("&nbsp;"));
    panel3.add(cachePostSizeButton);
    cacheSizeBox.setWidth("50px");
    cacheSizeBox.setText("16");
    grid.setHTML(2, 0, "<B>Cache Size:</B>");
    grid.setWidget(2, 1, cacheSizeBox);
    grid.setWidget(2, 2, panel3);

    // Error mode
    grid.setWidget(3, 0, errorModeButton);
    grid.setHTML(3, 1, "disabled");
    grid.setHTML(3, 2, "If the table model throws an error during a paging "
        + "request, the ScrollTable will display the error gracefully.");

    // RPC mode
    grid.setWidget(4, 0, rpcModeButton);
    grid.setHTML(4, 1, "disabled");
    grid.setHTML(4, 2, "Retrieve data from a server using an RPC request"
        + " instead of generating data locally. This requests an RPC server.");

    return grid;
  }
}
