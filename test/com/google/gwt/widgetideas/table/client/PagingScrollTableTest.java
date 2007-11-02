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
 * Tests methods in the {@link PagingScrollTable} class.
 */
public class PagingScrollTableTest extends ScrollTableTest {
  /**
   * The scroll table.
   */
  private PagingScrollTable scrollTable = null;

  /**
   * The table controller.
   */
  private TableController tableController = null;

  /**
   * Test paging features.
   */
  public void testPaging() {
    // Initialize the table
    PagingScrollTable table = (PagingScrollTable) getScrollTable();
    
    // Page size
    table.setPageSize(20);
    assertEquals(20, table.getPageSize());
    assertEquals(20, ((PagingGrid) dataTable).getPageSize());
  }

  /**
   * Get the scroll table.
   * 
   * @return the scroll table
   */
  protected ScrollTable getScrollTable() {
    if (scrollTable == null) {
      createTables();
      tableController = new TableController(new ClientTableModel() {
        public Object getCell(int rowNum, int colNum) {
          if (colNum > 4) {
            return null;
          } else {
            return rowNum + ":" + colNum;
          }
        }
      });
      dataTable = new PagingGrid(tableController);
      scrollTable = new PagingScrollTable((PagingGrid) dataTable, headerTable);
      scrollTable.setFooterTable(footerTable);
    }
    return scrollTable;
  }
}
