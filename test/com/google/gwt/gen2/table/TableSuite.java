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

package com.google.gwt.gen2.table;

import com.google.gwt.gen2.table.client.CachedTableModelTest;
import com.google.gwt.gen2.table.client.ClientTableModelTest;
import com.google.gwt.gen2.table.client.ColumnDefinitionTest;
import com.google.gwt.gen2.table.client.FixedWidthFlexTableTest;
import com.google.gwt.gen2.table.client.FixedWidthGridTest;
import com.google.gwt.gen2.table.client.FlexTableBulkRendererTest;
import com.google.gwt.gen2.table.client.GridBulkRendererTest;
import com.google.gwt.gen2.table.client.GridTest;
import com.google.gwt.gen2.table.client.InlineCellEditorTest;
import com.google.gwt.gen2.table.client.ListCellEditorTest;
import com.google.gwt.gen2.table.client.ListTableModelTest;
import com.google.gwt.gen2.table.client.MutableTableModelTest;
import com.google.gwt.gen2.table.client.PagingScrollTableTest;
import com.google.gwt.gen2.table.client.RadioCellEditorTest;
import com.google.gwt.gen2.table.client.ScrollTableTest;
import com.google.gwt.gen2.table.client.SelectionGridTest;
import com.google.gwt.gen2.table.client.SortableGridTest;
import com.google.gwt.gen2.table.client.TableDefinitionTest;
import com.google.gwt.gen2.table.client.TableModelTest;
import com.google.gwt.gen2.table.client.TextCellEditorTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Table suite.
 */

public class TableSuite {
  public static Test suite() {
    TestSuite suite = new TestSuite("Test for com.google.gwt.widgetideas.table.client");
    // $JUnit-BEGIN$

    // TableModel tests
    suite.addTestSuite(TableModelTest.class);
    suite.addTestSuite(MutableTableModelTest.class);
    suite.addTestSuite(ClientTableModelTest.class);
    suite.addTestSuite(ListTableModelTest.class);
    suite.addTestSuite(CachedTableModelTest.class);

    // Basic table tests
    suite.addTestSuite(GridTest.class);
    suite.addTestSuite(SelectionGridTest.class);
    suite.addTestSuite(SortableGridTest.class);
    suite.addTestSuite(FixedWidthGridTest.class);
    suite.addTestSuite(FixedWidthFlexTableTest.class);

    // Cell Editor Tests
    suite.addTestSuite(InlineCellEditorTest.class);
    suite.addTestSuite(TextCellEditorTest.class);
    suite.addTestSuite(ListCellEditorTest.class);
    suite.addTestSuite(RadioCellEditorTest.class);

    // ScrollTable tests
    suite.addTestSuite(ScrollTableTest.class);
    suite.addTestSuite(ColumnDefinitionTest.class);
    suite.addTestSuite(TableDefinitionTest.class);
    suite.addTestSuite(PagingScrollTableTest.class);

    // Bulk Renderer Tests
    suite.addTestSuite(FlexTableBulkRendererTest.class);
    suite.addTestSuite(GridBulkRendererTest.class);

    // $JUnit-END$
    return suite;
  }
}
