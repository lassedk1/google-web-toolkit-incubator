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
package com.google.gwt.widgetideas.table.client;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to retrieve row data to be used in a table.
 */
public class ListTableModel extends ClientTableModel {
  /**
   * The row data.
   */
  private List rows;

  /**
   * 
   * Constructor.
   * 
   * @param rows the data that this model feeds from.
   */
  public ListTableModel(List rows) {
    this.rows = rows;
  }

  /**
   * @see ClientTableModel
   */
  public Object getCell(int rowNum, int cellNum) {
    // Get the row List
    List row = getList(rowNum);
    if (row == null) {
      return null;
    }

    // Get the
    if (cellNum >= row.size()) {
      return null;
    }
    return row.get(cellNum);
  }

  public void onRowInserted(int beforeRow) {
    if (beforeRow <= rows.size()) {
      // Insert new row
      rows.add(beforeRow, null);
    } else {
      // Expand to fit row
      for (int i = rows.size(); i <= beforeRow; i++) {
        rows.add(null);
      }
    }
  }

  public void onRowRemoved(int row) {
    if (row < rows.size()) {
      rows.remove(row);
    }
  }

  public void onSetData(int row, int cell, Object data) {
    // Get the row list
    List rowList = getList(row);
    if (rowList == null) {
      // Expand to fit row
      for (int i = rows.size(); i <= row; i++) {
        rows.add(null);
      }

      // Create the rowList
      rowList = new ArrayList();
      rows.set(row, rowList);
    }

    // Enlarge the row as needed
    for (int i = rowList.size(); i <= cell; i++) {
      rowList.add(null);
    }
    rowList.set(cell, data);
  }

  /**
   * Get the list for a given row.
   * 
   * @param rowIndex the row index
   */
  private List getList(int rowIndex) {
    if (rowIndex >= rows.size()) {
      return null;
    }
    return (List) rows.get(rowIndex);
  }
}
