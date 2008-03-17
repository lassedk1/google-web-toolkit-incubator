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

package com.google.gwt.widgetideas.table.client;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests all widgets in the incubator. Must be run with lots of memory and gives
 * a warning "Undefined behavior".
 */
public class JohnSuite {

  public static Test suite() {
    TestSuite suite = new TestSuite();

    // Run one test
    if (false) {
      suite.addTestSuite(PagingScrollTableTest.class);
      return suite;
    }

    // TableModel
    suite.addTestSuite(TableModelTest.class);
    suite.addTestSuite(ReadOnlyTableModelTest.class);
    suite.addTestSuite(ClientTableModelTest.class);
    suite.addTestSuite(ListTableModelTest.class);
    suite.addTestSuite(CachedTableModelTest.class);

    // Grid and FlexTable
    suite.addTestSuite(SelectionGridTest.class);
    suite.addTestSuite(SortableGridTest.class);
    suite.addTestSuite(FixedWidthGridTest.class);
    suite.addTestSuite(FixedWidthFlexTableTest.class);

    // CellEditor
    suite.addTestSuite(InlineCellEditorTest.class);
    suite.addTestSuite(TextCellEditorTest.class);
    suite.addTestSuite(ListCellEditorTest.class);
    suite.addTestSuite(RadioCellEditorTest.class);

    // ScrollTable
    suite.addTestSuite(ScrollTableTest.class);
    suite.addTestSuite(PagingScrollTableTest.class);

    // Return the suite
    return suite;
  }

}
