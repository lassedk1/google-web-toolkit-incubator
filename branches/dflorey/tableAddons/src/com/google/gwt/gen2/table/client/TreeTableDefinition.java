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

import java.util.Iterator;
import java.util.List;

/**
 * A collection of {@link ColumnDefinition ColumnDefinitions} that define a
 * table.
 * 
 * @param <RowType> the type of the row values
 */
public class TreeTableDefinition<RowType> extends DefaultTableDefinition<RowType> {
  public void renderRows(int startRowIndex, Iterator<RowType> rowValues,
      AbstractCellView<RowType> view) {
    List<ColumnDefinition<RowType, ?>> visibleColumns = getVisibleColumnDefinitions();
    view.renderRowsImpl(startRowIndex, rowValues, visibleColumns);
  }
}
