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

package com.google.gwt.widgetideas.testsuites;

import com.google.gwt.widgetideas.client.ProgressBarTest;
import com.google.gwt.widgetideas.client.SliderBarTest;
import com.google.gwt.widgetideas.client.TitledPanelTest;
import com.google.gwt.widgetideas.table.client.BulkLoadedTableTest;
import com.google.gwt.widgetideas.table.client.ExtendedGridTest;
import com.google.gwt.widgetideas.table.client.ScrollTableTest;
import com.google.gwt.widgetideas.table.client.SizableFlexTableTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class WidgetSuite {

  public static Test suite() {
    TestSuite suite = new TestSuite(
        "Test for com.google.gwt.widgetideas.client");
    // Widget Tests.
    suite.addTestSuite(ProgressBarTest.class);
    suite.addTestSuite(SliderBarTest.class);
    suite.addTestSuite(TitledPanelTest.class);

    // Table Tests
    suite.addTestSuite(BulkLoadedTableTest.class);
    suite.addTestSuite(ScrollTableTest.class);
    suite.addTestSuite(ExtendedGridTest.class);
    suite.addTestSuite(BulkLoadedTableTest.class);
    suite.addTestSuite(SizableFlexTableTest.class);
    return suite;
  }

}
