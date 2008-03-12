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

package com.google.gwt.widgetideas.table;

import com.google.gwt.widgetideas.table.client.FlexTableBulkRendererTest;
import com.google.gwt.widgetideas.table.client.GridBulkRendererTest;
import com.google.gwt.widgetideas.table.client.PagingGridBulkRendererTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests the bulk rendering table code.
 */
public class BulkRenderingSuite {
  public static Test suite() {
    TestSuite suite = new TestSuite("Test for bulk loading tables");
    suite.addTestSuite(FlexTableBulkRendererTest.class);
    suite.addTestSuite(GridBulkRendererTest.class);
    suite.addTestSuite(PagingGridBulkRendererTest.class);

    return suite;
  }
}
