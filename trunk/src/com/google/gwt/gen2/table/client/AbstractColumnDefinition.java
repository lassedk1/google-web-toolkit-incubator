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
package com.google.gwt.gen2.table.client;

import com.google.gwt.gen2.table.client.CellEditor.Callback;
import com.google.gwt.gen2.table.client.CellEditor.CellEditInfo;
import com.google.gwt.gen2.table.client.TableDefinition.HTMLCellView;
import com.google.gwt.gen2.table.client.TableDefinition.TableCellView;
import com.google.gwt.user.client.ui.Widget;

/**
 * A definition of a column in a table.
 * 
 * @param <RowType> the type of the row value
 * @param <ColType> the data type of the column
 */
public abstract class AbstractColumnDefinition<RowType, ColType> implements
    ColumnDefinition<RowType, ColType> {
  /**
   * The cell editor used to edit the contents of this column.
   */
  private CellEditor<ColType> cellEditor = null;

  /**
   * Edit a cell using the cell editor, if one exists.
   * 
   * @param cellEditInfo information about the source of the edit request
   * @param rowValue the value associated with the row being edited
   * @param callback callback used when editing is complete
   * @return true if a cell editor is associated with this column definition
   */
  public boolean editCell(CellEditInfo cellEditInfo, final RowType rowValue,
      final Callback<ColType> callback) {
    // Return if no cell editor
    if (cellEditor == null) {
      return false;
    }

    // Forward the request to the cell editor
    Callback<ColType> chainCallback = new Callback<ColType>() {
      public void onCancel(CellEditInfo cellEditInfo) {
        callback.onCancel(cellEditInfo);
      }

      public void onComplete(CellEditInfo cellEditInfo, ColType cellValue) {
        setCellValue(rowValue, cellValue);
        callback.onComplete(cellEditInfo, cellValue);
      }
    };
    cellEditor.editCell(cellEditInfo, getCellValue(rowValue), chainCallback);
    return true;
  }

  /**
   * Get the {@link CellEditor} that should be used to edit the contents of
   * cells in this column.
   * 
   * @return the associated {@link CellEditor}
   */
  public CellEditor<ColType> getCellEditor() {
    return cellEditor;
  }

  /**
   * @return the cell value for the given row value
   */
  public abstract ColType getCellValue(RowType rowValue);

  /**
   * Render the cell as an html string. HTML string should be appended to the
   * view by calling view.addHTML(String).
   * 
   * @param rowValue the object associated with the row
   * @param view the view to append the string to
   */
  public void renderRowValue(RowType rowValue, HTMLCellView<RowType> view) {
    // Get the cell value
    ColType cellValue = getCellValue(rowValue);
    if (cellValue == null) {
      return;
    }

    // Set the value in the view
    if (cellValue instanceof Widget) {
      view.setWidget((Widget) cellValue);
    } else {
      view.addHTML(cellValue.toString());
    }
  }

  /**
   * Render the cell as a {@link com.google.gwt.user.client.ui.Widget} or set
   * the contents of the cell.
   * 
   * @param rowValue the object associated with the row
   * @param view the view used to set the cell contents
   */
  public void renderRowValue(RowType rowValue, TableCellView<RowType> view) {
    // Get the cell value
    ColType cellValue = getCellValue(rowValue);
    if (cellValue == null) {
      return;
    }

    // Set the value in the view
    if (cellValue instanceof Widget) {
      view.setWidget((Widget) cellValue);
    } else {
      view.setHTML(cellValue.toString());
    }
  }

  /**
   * Set the {@link CellEditor} that should be used to edit cells in this
   * column.
   * 
   * @param cellEditor the {@link CellEditor} to use for this column
   */
  public void setCellEditor(CellEditor<ColType> cellEditor) {
    this.cellEditor = cellEditor;
  }

  /**
   * Set the value of this column in the row value.
   * 
   * @param rowValue the value of the row
   * @param cellValue the new value of the cell
   */
  public abstract void setCellValue(RowType rowValue, ColType cellValue);
}
