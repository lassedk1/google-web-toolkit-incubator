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

package com.google.gwt.gen2.table.event.client;

import com.google.gwt.gen2.event.logical.shared.HighlightEvent;

/**
 * Logical event fired when a cell is highlighted.
 */
public class CellHighlightEvent extends HighlightEvent<CellHighlightEvent.Cell> {
  /**
   * Event Key for {@link CellHighlightEvent}.
   */
  public static final Key<CellHighlightEvent, CellHighlightHandler> KEY = new Key<CellHighlightEvent, CellHighlightHandler>() {
    @Override
    protected void fire(CellHighlightHandler handler, CellHighlightEvent event) {
      handler.onCellHighlight(event);
    }
  };

  /**
   * Information about the cell that is being hovered.
   */
  public static class Cell {
    private int cellIndex;
    private int rowIndex;

    /**
     * Construct a new Cell.
     * 
     * @param rowIndex the index of the highlighted row
     * @param cellIndex the index of the highlighted cell
     */
    public Cell(int rowIndex, int cellIndex) {
      this.cellIndex = cellIndex;
      this.rowIndex = rowIndex;
    }

    /**
     * @return the cell index
     */
    public int getCellIndex() {
      return cellIndex;
    }

    /**
     * @return the row index
     */
    public int getRowIndex() {
      return rowIndex;
    }
  }

  /**
   * Construct a new {@link CellHighlightEvent}.
   * 
   * @param rowIndex the index of the highlighted row
   * @param cellIndex the index of the highlighted cell
   */
  public CellHighlightEvent(int rowIndex, int cellIndex) {
    this(new Cell(rowIndex, cellIndex));
  }

  /**
   * Construct a new {@link CellHighlightEvent}.
   * 
   * @param cell the cell being hovered
   */
  public CellHighlightEvent(Cell cell) {
    super(cell);
  }

  @Override
  protected Key getKey() {
    return KEY;
  }
}
