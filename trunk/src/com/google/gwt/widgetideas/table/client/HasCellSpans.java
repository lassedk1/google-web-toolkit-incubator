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

/**
 * An interface for tables that support column and row spans.
 */
public interface HasCellSpans {
  /**
   * Gets the overall column index of this cell as if this table were a grid, as
   * determined by the rowspan and colspan of other cells in the table.
   * 
   * @param row the cell's row
   * @param column the cell's column
   * @return the cell's column index
   * @throws IndexOutOfBoundsException
   */
  int getColumnIndex(int row, int column);
}
