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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.table.client.FixedWidthFlexTable;
import com.google.gwt.widgetideas.table.client.FixedWidthGrid;
import com.google.gwt.widgetideas.table.client.ScrollTable;
import com.google.gwt.widgetideas.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.widgetideas.table.client.overrides.FlexTable.FlexCellFormatter;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ScrollTableDemo implements EntryPoint {
  /**
   * The data portion of the {@link ScrollTable}.
   */
  protected static FixedWidthGrid dataTable = null;

  /**
   * The footer portion of the {@link ScrollTable} .
   */
  protected static FixedWidthFlexTable footerTable = null;

  /**
   * The header portion of the {@link ScrollTable}.
   */
  protected static FixedWidthFlexTable headerTable = null;

  /**
   * The scroll table.
   */
  protected static ScrollTable scrollTable = null;

  /**
   * Get the data table.
   * 
   * @return the data table.
   */
  public static FixedWidthGrid getDataTable() {
    if (dataTable == null) {
      dataTable = new FixedWidthGrid();
      dataTable.setSelectionPolicy(SelectionPolicy.CHECKBOX);
    }
    return dataTable;
  }

  /**
   * Get the footer table.
   * 
   * @return the footer table.
   */
  public static FixedWidthFlexTable getFooterTable() {
    if (footerTable == null) {
      footerTable = new FixedWidthFlexTable();
    }
    return footerTable;
  }

  /**
   * Get the header table.
   * 
   * @return the header table.
   */
  public static FixedWidthFlexTable getHeaderTable() {
    if (headerTable == null) {
      headerTable = new FixedWidthFlexTable();
    }
    return headerTable;
  }

  /**
   * Get the scroll table.
   * 
   * @return the scroll table.
   */
  public static ScrollTable getScrollTable() {
    return scrollTable;
  }

  /**
   * Add a row of data cells each consisting of a string that describes the
   * row:column coordinates of the new cell. The number of columns in the new
   * row will match the number of columns in the grid.
   * 
   * @param beforeRow the index to add the new row into
   */
  public static void insertDataRow(int beforeRow) {
    // Insert the new row
    beforeRow = dataTable.insertRow(beforeRow);

    // Set the data in the new row
    int numColumns = dataTable.getColumnCount();
    for (int column = 0; column < numColumns; column++) {
      String label = beforeRow + ":" + column;
      if (column == 2) {
        int rand = (int) (Math.random() * 100000);
        dataTable.setHTML(beforeRow, column, rand + "");
      } else {
        dataTable.setHTML(beforeRow, column, label);
      }
    }
  }

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    // Create the inner tables
    getHeaderTable();
    getFooterTable();
    getDataTable();

    // Add the scroll table to the page
    scrollTable = new ScrollTable(dataTable, headerTable);
    scrollTable.setFooterTable(footerTable);

    // Setup the header
    setupScrollTable();

    // Add some data the data table
    dataTable.resize(0, 13);
    for (int i = 0; i < 15; i++) {
      insertDataRow(i);
    }

    // Add some data to the footer table
    footerTable.setHTML(0, 0, "&nbsp;");
    for (int i = 0; i < 13; i++) {
      footerTable.setText(0, i + 1, "Col " + i);
    }

    // Redraw the scroll table
    scrollTable.redraw();

    // Add the components to the page
    RootPanel.get().add(scrollTable);
    RootPanel.get().add(new HTML("<BR>"));
    RootPanel.get().add(createTabPanel());
  }

  /**
   * Create a new tab panel with options to affect the grid.
   * 
   * @return the new tab panel
   */
  private TabPanel createTabPanel() {
    TabPanel panel = new TabPanel();
    panel.setWidth("95%");

    // Add the panels
    panel.add(new DemoTabResizing(), "Resizability");
    panel.add(new DemoTabColumnWidth(), "Column Width");
    panel.add(new DemoTabHighlighting(), "Highlighting");
    panel.add(new DemoTabSorting(), "Sorting");
    panel.add(new DemoTabHeaderManipulation(), "Header Manipulation");
    panel.add(new DemoTabDataManipulation(), "Data Manipulation");
    panel.add(new DemoTabPanelLog(), "Log");
    panel.selectTab(0);

    // Return the new panel
    return panel;
  }

  /**
   * Setup the scroll table.
   */
  private void setupScrollTable() {
    // Setup the formatting
    scrollTable.setSize("95%", "50%");
    scrollTable.setCellPadding(3);
    scrollTable.setCellSpacing(1);
    scrollTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);

    // Level 1 headers
    FlexCellFormatter headerFormatter = headerTable.getFlexCellFormatter();
    headerTable.setHTML(0, 0, "Info Table");
    headerFormatter.setColSpan(0, 0, 14);

    // Create the select all checkbox
    final CheckBox selectAll = new CheckBox();
    selectAll.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        if (selectAll.isChecked()) {
          dataTable.selectAllRows();
        } else {
          dataTable.deselectAllRows();
        }
      }
    });

    // Level 2 headers
    headerTable.setWidget(1, 0, selectAll);
    headerFormatter.setRowSpan(1, 0, 2);
    headerFormatter.setHorizontalAlignment(1, 0,
        HasHorizontalAlignment.ALIGN_CENTER);
    headerTable.setHTML(1, 1, "Group Header 0<BR>Multiline");
    headerFormatter.setColSpan(1, 1, 2);
    headerFormatter.setRowSpan(1, 1, 2);
    headerTable.setHTML(1, 2, "Group Header 1");
    headerFormatter.setColSpan(1, 2, 3);
    headerTable.setHTML(1, 3, "Group Header 2");
    headerFormatter.setColSpan(1, 3, 1);
    headerFormatter.setRowSpan(1, 3, 2);
    headerTable.setHTML(1, 4, "Group Header 3");
    headerFormatter.setColSpan(1, 4, 1);
    headerFormatter.setRowSpan(1, 4, 2);
    headerTable.setHTML(1, 5, "Group Header 4");
    headerFormatter.setColSpan(1, 5, 3);
    headerTable.setHTML(1, 6, "Group Header 5");
    headerFormatter.setColSpan(1, 6, 3);

    // Level 3 headers
    for (int cell = 0; cell < 9; cell++) {
      headerTable.setHTML(2, cell, "Header " + cell);
    }
  }
}
