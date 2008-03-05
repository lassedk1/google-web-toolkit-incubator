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

import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.widgetideas.table.client.AbstractCellEditor.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link PagingGrid} that supports editing cell contents using a cell editor.
 * 
 * {@link AbstractCellEditor}s can be assigned on a column by column basis.
 * When the user clicks on a cell, the assigned editor will popup near the cell
 * and allow the user to change the value of the cell. When the user accepts,
 * the change is sent directly to the underlying {@link TableController}.
 */
public class EditablePagingGrid extends PagingGrid {
  /**
   * Enable hovering of cells that are editable.
   */
  public static final int HOVERING_POLICY_EDITABLE_CELL = 3;

  /**
   * The cell editors for each column.
   */
  private Map<Integer, AbstractCellEditor> cellEditors = new HashMap<Integer, AbstractCellEditor>();

  /**
   * The callback used to save data after editing a cell value.
   */
  private Callback cellEditorCallback = new Callback() {
    public void onAccept(int row, int cell, Object value) {
      tableController.setData(row + getFirstRow(), cell, value);
    }

    public void onCancel(int row, int cell) {
    }
  };

  /**
   * The table controller.
   */
  private TableController tableController = null;

  /**
   * Constructor.
   * 
   * @param tableController the underlying {@link TableController}
   */
  public EditablePagingGrid(TableController tableController) {
    super(tableController);
    this.tableController = tableController;

    // Show the new cell editor
    addTableListener(new TableListener() {
      public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
        AbstractCellEditor cellEditor = getCellEditor(cell);
        if (cellEditor != null) {
          cellEditor.editCell(EditablePagingGrid.this, row - 1, cell,
              cellEditorCallback);
        }
      }
    });
  }

  /**
   * Constructor.
   * 
   * @param tableModel the {@link TableModel} used to retrieve data
   */
  public EditablePagingGrid(TableModel tableModel) {
    this(new TableController(tableModel));
  }

  /**
   * Get the column editor for a column.
   * 
   * @param column the column index
   * @return the cell editor
   */
  public AbstractCellEditor getCellEditor(int column) {
    return cellEditors.get(new Integer(column));
  }

  /**
   * Check whether a column has a cell editor.
   * 
   * @param column the column index
   * @return true if a cell editor is assigned
   */
  public boolean hasCellEditor(int column) {
    return cellEditors.containsKey(new Integer(column));
  }

  /**
   * Set the cell editor for a column.
   * 
   * @param column the column index
   * @param editor the cell editor
   */
  public void setCellEditor(int column, AbstractCellEditor editor) {
    if (editor == null) {
      cellEditors.remove(new Integer(column));
    } else {
      cellEditors.put(new Integer(column), editor);
    }
  }
}
