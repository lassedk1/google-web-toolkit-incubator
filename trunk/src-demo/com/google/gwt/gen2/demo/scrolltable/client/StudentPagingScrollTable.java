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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.demo.scrolltable.client.StudentColumnDefinition.Group;
import com.google.gwt.gen2.demo.scrolltable.shared.Student;
import com.google.gwt.gen2.table.client.ColumnDefinition;
import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.gen2.table.client.TableDefinition;
import com.google.gwt.gen2.table.client.TableModel;
import com.google.gwt.gen2.table.override.client.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

import java.util.Iterator;
import java.util.List;

/**
 * An customized version of the {@link PagingScrollTable} that updated the
 * header and footer tables to reflect the currently visible rows.
 */
public class StudentPagingScrollTable extends PagingScrollTable<Student> {
  /**
   * The previous list of visible column definitions.
   */
  private List<ColumnDefinition<Student, ?>> lastColDefs = null;

  /**
   * The {@link CheckBox} used to select all rows.
   */
  private CheckBox selectAllCheckBox = new CheckBox();

  /**
   * Construct a new {@link StudentPagingScrollTable}.
   * 
   * @param tableModel the underlying table model
   * @param dataTable the table used to display data
   * @param headerTable the header table
   * @param tableDefinition the column definitions
   */
  public StudentPagingScrollTable(TableModel<Student> tableModel,
      FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
      TableDefinition<Student> tableDefinition) {
    super(tableModel, dataTable, headerTable, tableDefinition);

    // Setup the selectAll checkbox
    selectAllCheckBox.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (selectAllCheckBox.getValue()) {
          getDataTable().selectAllRows();
        } else {
          getDataTable().deselectAllRows();
        }
      }
    });
  }

  @Override
  protected void setData(int firstRow, Iterator<Student> rows) {
    // Get the visible column definitions
    List<ColumnDefinition<Student, ?>> colDefs = getTableDefinition().getVisibleColumnDefinitions();
    if (!colDefs.equals(lastColDefs)) {
      lastColDefs = colDefs;
      updateHeaderTable(colDefs);
    }

    // Set the actual data
    super.setData(firstRow, rows);
  }

  /**
   * Update the header table to match the data table.
   */
  private void updateHeaderTable(List<ColumnDefinition<Student, ?>> colDefs) {
    int numColumns = colDefs.size();

    // Remove everything from the header
    FixedWidthFlexTable headerTable = getHeaderTable();
    int rowCount = headerTable.getRowCount();
    for (int i = 0; i < rowCount; i++) {
      headerTable.removeRow(0);
    }

    // Add the row that spans everything
    FlexCellFormatter formatter = headerTable.getFlexCellFormatter();
    int offset = getDataTable().getSelectionPolicy().hasInputColumn() ? 1 : 0;
    headerTable.setText(0, 0, "User Information");
    formatter.setColSpan(0, 0, numColumns + offset);

    // Add the select all checkbox
    if (offset > 0) {
      headerTable.setWidget(1, 0, selectAllCheckBox);
      formatter.setRowSpan(1, 0, 2);
      formatter.setHorizontalAlignment(1, 0,
          HasHorizontalAlignment.ALIGN_CENTER);
    }

    // Add the column and group headers
    Group lastGroup = null;
    for (int i = 0; i < numColumns; i++) {
      // Add the name
      StudentColumnDefinition<?> colDef = (StudentColumnDefinition<?>) colDefs.get(i);
      headerTable.setText(2, i, colDef.getName());

      // Add the group header
      Group group = colDef.getGroup();
      if (group == lastGroup) {
        int curCell = headerTable.getCellCount(1) - 1;
        int curColSpan = formatter.getColSpan(1, curCell);
        formatter.setColSpan(1, curCell, curColSpan + 1);
      } else {
        headerTable.setText(1, headerTable.getCellCount(1), group.getName());
        lastGroup = group;
      }
    }

    // Also update the footer table
    {
      FixedWidthFlexTable footerTable = getFooterTable();
      rowCount = footerTable.getRowCount();
      for (int i = 0; i < rowCount; i++) {
        footerTable.removeRow(0);
      }
      if (offset > 0) {
        footerTable.setHTML(0, 0, "&nbsp;");
      }
      for (int i = 0; i < numColumns; i++) {
        footerTable.setText(0, i + offset, "Col " + i);
      }
    }
  }
}
