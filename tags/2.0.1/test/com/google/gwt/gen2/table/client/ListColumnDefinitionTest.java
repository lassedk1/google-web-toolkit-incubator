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

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link ListColumnDefinition}.
 */
public class ListColumnDefinitionTest extends AbstractColumnDefinitionTest {
  /**
   * Test getting and setting cell values.
   */
  public void testCellValues() {
    // index within range
    {
      List<Integer> rowValue = getRowValue();
      ListColumnDefinition<Integer> colDef = new ListColumnDefinition<Integer>(
          4);

      // get the cell value
      assertEquals(5, rowValue.get(4).intValue());
      assertEquals(5, colDef.getCellValue(rowValue).intValue());

      // set the cell value
      colDef.setCellValue(rowValue, 13);
      assertEquals(13, rowValue.get(4).intValue());
      assertEquals(13, colDef.getCellValue(rowValue).intValue());
    }

    // index outside range
    {
      List<Integer> rowValue = getRowValue();
      ListColumnDefinition<Integer> colDef = new ListColumnDefinition<Integer>(
          6);

      // get the cell value
      assertNull(colDef.getCellValue(rowValue));

      // set the cell value
      colDef.setCellValue(rowValue, 13);
      assertEquals(13, rowValue.get(6).intValue());
      assertEquals(13, colDef.getCellValue(rowValue).intValue());
    }
  }

  /**
   * Returns a row value with a short sequence of Fibonacci numbers.
   * 
   * @return a row value
   */
  private List<Integer> getRowValue() {
    List<Integer> rowValue = new ArrayList<Integer>();
    rowValue.add(1);
    rowValue.add(1);
    rowValue.add(2);
    rowValue.add(3);
    rowValue.add(5);
    rowValue.add(8);
    return rowValue;
  }
}