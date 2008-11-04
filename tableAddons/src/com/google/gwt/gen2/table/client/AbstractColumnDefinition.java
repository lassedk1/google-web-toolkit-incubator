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
   * The default width of a column.
   */
  private static final int DEFAULT_WIDTH = 80;

  /**
   * The default {@link CellRenderer} to use when the
   * {@link AbstractColumnDefinition} does not specify one.
   */
  private static final CellRenderer DEFAULT_CELL_RENDERER = new DefaultCellRenderer();

  /**
   * The cell editor used to edit the contents of this column.
   */
  private CellEditor<ColType> cellEditor = null;

  /**
   * The renderer used to render the contents of this column.
   */
  private CellRenderer<RowType, ColType> cellRenderer = DEFAULT_CELL_RENDERER;

  /**
   * A boolean indicating whether or not the column is sortable. True by default
   * to be consistent with {@link ScrollTable}.
   */
  private boolean isColumnSortable = true;

  /**
   * The minimum width of the column.
   */
  private int minWidth = -1;

  /**
   * The maximum width of the column.
   */
  private int maxWidth = -1;

  /**
   * The preferred width of the column.
   */
  private int preferredWidth = DEFAULT_WIDTH;

  public CellEditor<ColType> getCellEditor() {
    return cellEditor;
  }

  public CellRenderer<RowType, ColType> getCellRenderer() {
    return cellRenderer;
  }

  public abstract ColType getCellValue(RowType rowValue);

  public int getMaximumColumnWidth() {
    return maxWidth;
  }

  public int getMinimumColumnWidth() {
    return minWidth;
  }

  public int getPreferredColumnWidth() {
    return preferredWidth;
  }

  public boolean isColumnSortable() {
    return isColumnSortable;
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
   * Set the {@link CellRenderer} that should be used to render cells in this
   * column.
   * 
   * @param cellRenderer the {@link CellRenderer} to use for this column
   */
  public void setCellRenderer(CellRenderer<RowType, ColType> cellRenderer) {
    assert cellRenderer != null : "cellRenderer cannot be null";
    this.cellRenderer = cellRenderer;
  }

  public abstract void setCellValue(RowType rowValue, ColType cellValue);

  /**
   * Set whether or not this column is sortable.
   * 
   * @param sortable true to make sortable, false to make unsortable
   */
  public void setColumnSortable(boolean sortable) {
    isColumnSortable = sortable;
  }

  /**
   * Set the maximum width of the column.
   * 
   * @param maxWidth the max width
   */
  public void setMaximumColumnWidth(int maxWidth) {
    this.maxWidth = maxWidth;
  }

  /**
   * Set the minimum width of the column.
   * 
   * @param minWidth the min width
   */
  public void setMinimumColumnWidth(int minWidth) {
    this.minWidth = minWidth;
  }

  /**
   * Set the preferred width of the column.
   * 
   * @param preferredWidth the preferred width
   * @see #getPreferredColumnWidth()
   */
  public void setPreferredColumnWidth(int preferredWidth) {
    this.preferredWidth = preferredWidth;
  }
}
