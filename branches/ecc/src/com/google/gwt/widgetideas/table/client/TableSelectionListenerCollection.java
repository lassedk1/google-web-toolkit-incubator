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

/**
 * A helper class for implementers of the {@link SourceTableSelectionEvents}
 * interface. This subclass of {@link ArrayList} assumes that all objects added
 * to it will be of type {@link TableSelectionListener}.
 */
public class TableSelectionListenerCollection extends
    ArrayList<TableSelectionListener> {
  /**
   * Fired when all rows are deselected.
   */
  public void fireAllRowsDeselected() {
    for (TableSelectionListener listener : this) {
      listener.onAllRowsDeselected();
    }
  }

  /**
   * Fired when a cell is clicked.
   * 
   * @param row the row index
   * @param cell the cell index
   */
  public void fireCellClicked(int row, int cell) {
    for (TableSelectionListener listener : this) {
      listener.onCellClicked(row, cell);
    }
  }

  /**
   * Fired when a cell is hovered.
   * 
   * @param row the row index
   * @param cell the cell index
   */
  public void fireCellHover(int row, int cell) {
    for (TableSelectionListener listener : this) {
      listener.onCellHover(row, cell);
    }
  }

  /**
   * Fired when a cell is unhovered.
   * 
   * @param row the row index
   * @param cell the cell index
   */
  public void fireCellUnhover(int row, int cell) {
    for (TableSelectionListener listener : this) {
      listener.onCellUnhover(row, cell);
    }
  }

  /**
   * Fired when a single row is deselected. This method will not fire when all
   * rows are deselected. In that case, use the onAllRowsDeselected events.
   * 
   * @param row the row index
   */
  public void fireRowDeselected(int row) {
    for (TableSelectionListener listener : this) {
      listener.onRowDeselected(row);
    }
  }

  /**
   * Fired when a row is hovered.
   * 
   * @param row the row index
   */
  public void fireRowHover(int row) {
    for (TableSelectionListener listener : this) {
      listener.onRowHover(row);
    }
  }

  /**
   * Fired when one or more rows are selected.
   * 
   * @param firstRow the row index of the first row
   * @param numRows the number of selected rows
   */
  public void fireRowsSelected(int firstRow, int numRows) {
    for (TableSelectionListener listener : this) {
      listener.onRowsSelected(firstRow, numRows);
    }
  }

  /**
   * Fired when a row is unhovered.
   * 
   * @param row the row index
   */
  public void fireRowUnhover(int row) {
    for (TableSelectionListener listener : this) {
      listener.onRowUnhover(row);
    }
  }
}
