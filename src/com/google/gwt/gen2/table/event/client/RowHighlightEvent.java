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
 * Logical event fired when a row is highlighted.
 */
public class RowHighlightEvent extends HighlightEvent<RowHighlightEvent.Row> {
  /**
   * Event Key for {@link RowHighlightEvent}.
   */
  public static final Key<RowHighlightEvent, RowHighlightHandler> KEY = new Key<RowHighlightEvent, RowHighlightHandler>() {
    @Override
    protected void fire(RowHighlightHandler handler, RowHighlightEvent event) {
      handler.onRowHighlight(event);
    }
  };

  /**
   * Information about the cell that is being hovered.
   */
  public static class Row implements Comparable<Row> {
    private int rowIndex;

    /**
     * Construct a new Row.
     * 
     * @param rowIndex the index of the highlighted row
     */
    public Row(int rowIndex) {
      this.rowIndex = rowIndex;
    }

    public int compareTo(Row o) {
      if (o == null) {
        return 1;
      } else {
        return rowIndex - o.getRowIndex();
      }
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Row) {
        return compareTo((Row) o) == 0;
      }
      return false;
    }

    /**
     * @return the row index
     */
    public int getRowIndex() {
      return rowIndex;
    }
  }

  /**
   * Construct a new {@link RowHighlightEvent}.
   * 
   * @param rowIndex the index of the highlighted row
   */
  public RowHighlightEvent(int rowIndex) {
    this(new Row(rowIndex));
  }

  /**
   * Construct a new {@link RowHighlightEvent}.
   * 
   * @param row the row being hovered
   */
  public RowHighlightEvent(Row row) {
    super(row);
  }

  @Override
  protected Key getKey() {
    return KEY;
  }
}
