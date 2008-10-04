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

/**
 * A definition of a column in a table.
 * 
 * @param <RowType> the type of the row value
 * @param <ColType> the data type of the column
 */
public interface ColumnDefinition<RowType, ColType> {
  /**
   * Edit a cell.
   * 
   * @param cellEditInfo information about the source of the edit request
   * @param rowValue the value associated with the row being edited
   * @param callback callback used when editing is complete
   * @return true if a cell editor is associated with this column definition
   */
  boolean editCell(CellEditInfo cellEditInfo, final RowType rowValue,
      final Callback<ColType> callback);

  /**
   * Get the cell value associated with the row value.
   * 
   * @param rowValue the row value
   * @return the cell value for the given row value
   */
  ColType getCellValue(RowType rowValue);

  /**
   * Render the cell as an html string.
   * 
   * @param rowValue the object associated with the row
   * @param view the view to append the string to
   */
  void renderRowValue(RowType rowValue, HTMLCellView<RowType> view);

  /**
   * Render the cell as a {@link com.google.gwt.user.client.ui.Widget} or set
   * the contents of the cell.
   * 
   * @param rowValue the object associated with the row
   * @param view the view used to set the cell contents
   */
  void renderRowValue(RowType rowValue, TableCellView<RowType> view);

  /**
   * Set the value of this column in the row value.
   * 
   * @param rowValue the value of the row
   * @param cellValue the new value of the cell
   */
  void setCellValue(RowType rowValue, ColType cellValue);
}
