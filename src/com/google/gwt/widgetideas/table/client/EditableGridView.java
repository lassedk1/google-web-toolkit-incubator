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

import com.google.gwt.widgetideas.table.client.AbstractCellEditor.Callback;

import java.util.HashMap;

/**
 * A {@link GridView} that supports editing cell contents using a cell editor.
 * 
 * {@link AbstractCellEditor}s can be assigned on a column by column basis.
 * When the user clicks on a cell, the assigned editor will popup near the cell
 * and allow the user to change the value of the cell. When the user accepts,
 * the change is sent directly to the underlying {@link TableController}.
 */
public class EditableGridView extends GridView {
  /**
   * The cell editors for each column.
   */
  private HashMap/* Integer, AbstractCellEditor */cellEditors = new HashMap();

  /**
   * The callback used to save data after editing a cell value.
   */
  private Callback cellEditorCallback = new Callback() {
    public void onAccept(int row, int cell, Object value) {
      curCellEditor = null;
      renderCell(row, cell, value);
      getTableController().setData(row, cell, value);
    }

    public void onCancel(int row, int cell) {
      curCellEditor = null;
    }
  };

  /**
   * The current cell editor that is being displayed.
   */
  private AbstractCellEditor curCellEditor = null;

  /**
   * Constructor.
   * 
   * @param tableController the underlying {@link TableController}
   */
  public EditableGridView(TableController tableController) {
    super(tableController);
    this.setHoveringPolicy(HOVERING_POLICY_CELL);
    this.setSelectionPolicy(SELECTION_POLICY_DISABLED);
  }

  /**
   * Constructor.
   * 
   * @param tableModel the {@link MutableTableModel} used to retrieve data
   */
  public EditableGridView(MutableTableModel tableModel) {
    super(tableModel);
    this.setHoveringPolicy(HOVERING_POLICY_CELL);
    this.setSelectionPolicy(SELECTION_POLICY_DISABLED);
  }

  /**
   * Get the column editor for a column.
   * 
   * @param column the column index
   * @return the cell editor
   */
  public AbstractCellEditor getCellEditor(int column) {
    return (AbstractCellEditor) cellEditors.get(new Integer(column));
  }

  /**
   * Handle cell click events.
   * 
   * @param row the row index
   * @param cell the cell index
   */
  public void onCellClicked(int row, int cell) {
    // Hide the current cell editor
    if (curCellEditor != null) {
      if (curCellEditor.cancel()) {
        curCellEditor = null;
      } else {
        return;
      }
    }

    // Show the new cell editor
    AbstractCellEditor cellEditor = getCellEditor(cell);
    if (cellEditor != null) {
      curCellEditor = cellEditor;
      curCellEditor.editCell(this, row, cell, cellEditorCallback);
    }
  }

  /**
   * Set the cell editor for a column.
   * 
   * @param column the column index
   * @param editor the cell editor
   */
  public void setCellEditor(int column, AbstractCellEditor editor) {
    cellEditors.put(new Integer(column), editor);
  }
}
