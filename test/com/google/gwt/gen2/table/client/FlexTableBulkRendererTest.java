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

import com.google.gwt.gen2.table.client.ColumnDefinitionTest.CustomColumnDefinition;
import com.google.gwt.gen2.table.override.client.FlexTable;

/**
 * {@link FlexTableBulkRenderer} test.
 * 
 */
public class FlexTableBulkRendererTest extends TableBulkRendererTestBase {

  @Override
  protected <RowType> TableRendererPair<RowType> createTableAndRenderer(int numColumns) {
    FlexTable table = new FlexTable();
    TableDefinition<RowType> tcr = new TableDefinition<RowType>();
    for (int i = 0; i < numColumns; i++) {
      tcr.addColumnDefinition(new CustomColumnDefinition<RowType, Object>());
    }
    FlexTableBulkRenderer<RowType> renderer = new FlexTableBulkRenderer<RowType>(table, tcr);
    return new TableRendererPair<RowType>(table, renderer);
  }

}
