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

import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.gen2.table.client.AbstractColumnDefinitionTest.CustomColumnDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link TableDefinition}.
 */
public class DefaultTableDefinitionTest extends Gen2TestBase {
  /**
   * Test addition and removal of {@link ColumnDefinition}.
   */
  public void testColumnDefinitionAddition() {
    // Add column definition
    {
      DefaultTableDefinition<Object> tcr = new DefaultTableDefinition<Object>();
      assertEquals(tcr.getColumnDefinitionCount(), 0);

      ColumnDefinition<Object, Object> colDef0 = new CustomColumnDefinition<Object, Object>();
      tcr.addColumnDefinition(colDef0);
      assertEquals(tcr.getColumnDefinition(0), colDef0);
      assertEquals(tcr.getColumnDefinitionCount(), 1);

      ColumnDefinition<Object, Object> colDef1 = new CustomColumnDefinition<Object, Object>();
      tcr.addColumnDefinition(colDef1);
      assertEquals(tcr.getColumnDefinition(1), colDef1);
      assertEquals(tcr.getColumnDefinitionCount(), 2);
    }

    // Insert column definition
    {
      DefaultTableDefinition<Object> tcr = new DefaultTableDefinition<Object>();
      assertEquals(tcr.getColumnDefinitionCount(), 0);

      ColumnDefinition<Object, Object> colDef0 = new CustomColumnDefinition<Object, Object>();
      tcr.addColumnDefinition(0, colDef0);
      assertEquals(tcr.getColumnDefinition(0), colDef0);
      assertEquals(tcr.getColumnDefinitionCount(), 1);

      ColumnDefinition<Object, Object> colDef1 = new CustomColumnDefinition<Object, Object>();
      tcr.addColumnDefinition(0, colDef1);
      assertEquals(tcr.getColumnDefinition(0), colDef1);
      assertEquals(tcr.getColumnDefinitionCount(), 2);

      ColumnDefinition<Object, Object> colDef2 = new CustomColumnDefinition<Object, Object>();
      tcr.addColumnDefinition(1, colDef2);
      assertEquals(tcr.getColumnDefinition(1), colDef2);
      assertEquals(tcr.getColumnDefinitionCount(), 3);
    }

    // Remove column definition
    {
      DefaultTableDefinition<Object> tcr = new DefaultTableDefinition<Object>();
      ColumnDefinition<Object, Object> colDef0 = new CustomColumnDefinition<Object, Object>();
      ColumnDefinition<Object, Object> colDef1 = new CustomColumnDefinition<Object, Object>();
      ColumnDefinition<Object, Object> colDef2 = new CustomColumnDefinition<Object, Object>();
      tcr.addColumnDefinition(colDef0);
      tcr.addColumnDefinition(colDef1);
      tcr.addColumnDefinition(colDef2);
      assertEquals(tcr.getColumnDefinitionCount(), 3);

      tcr.removeColumnDefinition(colDef1);
      assertEquals(tcr.getColumnDefinition(0), colDef0);
      assertEquals(tcr.getColumnDefinition(1), colDef2);
      assertEquals(tcr.getColumnDefinitionCount(), 2);

      tcr.removeColumnDefinition(colDef0);
      assertEquals(tcr.getColumnDefinition(0), colDef2);
      assertEquals(tcr.getColumnDefinitionCount(), 1);

      tcr.removeColumnDefinition(colDef2);
      assertEquals(tcr.getColumnDefinitionCount(), 0);
    }
  }

  /**
   * Test setting and retrieving {@link ColumnDefinition ColumnDefinitions}.
   */
  public void testColumnDefinitionsInConstructor() {
    // Create some column definitions
    List<ColumnDefinition<Object, ?>> colDefs = new ArrayList<ColumnDefinition<Object, ?>>();
    for (int i = 0; i < 10; i++) {
      AbstractColumnDefinition<Object, Object> colDef = new CustomColumnDefinition<Object, Object>();
      colDefs.add(colDef);
    }

    // Create a TableDefinition to test
    DefaultTableDefinition<Object> tcr = new DefaultTableDefinition<Object>(
        colDefs);

    // Hide every other column definition
    for (int i = 0; i < 10; i++) {
      if (i % 2 == 1) {
        tcr.setColumnVisible(tcr.getColumnDefinition(i), false);
      }
    }

    // Verify the column definitions
    assertEquals(colDefs.size(), tcr.getColumnDefinitionCount());
    for (int i = 0; i < colDefs.size(); i++) {
      assertEquals(colDefs.get(i), tcr.getColumnDefinition(i));
    }

    // Verify the visible column definitions (every other column)
    List<ColumnDefinition<Object, ?>> visibleColDefs = tcr.getVisibleColumnDefinitions();
    assertEquals(colDefs.size() / 2, visibleColDefs.size());
    for (int i = 0; i < colDefs.size(); i++) {
      if (i % 2 == 0) {
        assertEquals(colDefs.get(i), visibleColDefs.get(i / 2));
      }
    }
  }

  /**
   * Test hiding and showing columns.
   */
  public void testHiddenColumns() {
    DefaultTableDefinition<Object> tcr = new DefaultTableDefinition<Object>();
    ColumnDefinition<Object, Object> colDef0 = new CustomColumnDefinition<Object, Object>();
    ColumnDefinition<Object, Object> colDef1 = new CustomColumnDefinition<Object, Object>();
    ColumnDefinition<Object, Object> colDef2 = new CustomColumnDefinition<Object, Object>();
    tcr.addColumnDefinition(colDef0);
    tcr.addColumnDefinition(colDef1);
    tcr.addColumnDefinition(colDef2);

    // All columns visible
    assertEquals(3, tcr.getColumnDefinitionCount());
    assertEquals(3, tcr.getVisibleColumnDefinitions().size());
    assertTrue(tcr.isColumnVisible(colDef0));
    assertTrue(tcr.isColumnVisible(colDef1));
    assertTrue(tcr.isColumnVisible(colDef2));

    // Hide one column
    tcr.setColumnVisible(colDef1, false);
    assertEquals(3, tcr.getColumnDefinitionCount());
    assertEquals(2, tcr.getVisibleColumnDefinitions().size());
    assertTrue(tcr.isColumnVisible(colDef0));
    assertFalse(tcr.isColumnVisible(colDef1));
    assertTrue(tcr.isColumnVisible(colDef2));

    // Hide one more column
    tcr.setColumnVisible(colDef2, false);
    assertEquals(3, tcr.getColumnDefinitionCount());
    assertEquals(1, tcr.getVisibleColumnDefinitions().size());
    assertTrue(tcr.isColumnVisible(colDef0));
    assertFalse(tcr.isColumnVisible(colDef1));
    assertFalse(tcr.isColumnVisible(colDef2));

    // Show a hidden column
    tcr.setColumnVisible(colDef2, true);
    assertEquals(3, tcr.getColumnDefinitionCount());
    assertEquals(2, tcr.getVisibleColumnDefinitions().size());
    assertTrue(tcr.isColumnVisible(colDef0));
    assertFalse(tcr.isColumnVisible(colDef1));
    assertTrue(tcr.isColumnVisible(colDef2));
  }
}