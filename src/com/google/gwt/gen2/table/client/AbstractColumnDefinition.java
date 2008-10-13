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
   * The renderer used to render the contents of this column.
   */
  private CellRenderer<RowType, ColType> cellRenderer = null;

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
   * Get the cell renderer associated with the column.
   * 
   * @return the associated {@link CellRenderer}, or null if one does not exist
   */
  public CellRenderer<RowType, ColType> getCellRenderer() {
    return cellRenderer;
  }

  /**
   * @return the cell value for the given row value
   */
  public abstract ColType getCellValue(RowType rowValue);

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
   * Set the {@link CellRenderer} that should be used to render cells in this
   * column.
   * 
   * @param cellRenderer the {@link CellRenderer} to use for this column
   */
  public void setCellRenderer(CellRenderer<RowType, ColType> cellRenderer) {
    this.cellRenderer = cellRenderer;
  }

  /**
   * Set the value of this column in the row value.
   * 
   * @param rowValue the value of the row
   * @param cellValue the new value of the cell
   */
  public abstract void setCellValue(RowType rowValue, ColType cellValue);
}
