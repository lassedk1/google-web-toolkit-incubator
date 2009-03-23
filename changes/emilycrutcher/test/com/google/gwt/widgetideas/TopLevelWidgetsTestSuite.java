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

package com.google.gwt.widgetideas;

import com.google.gwt.gen2.selection.client.DropDownListBoxTest;
import com.google.gwt.widgetideas.client.CollapsiblePanelTest;
import com.google.gwt.widgetideas.client.ProgressBarTest;
import com.google.gwt.widgetideas.client.ResizableWidgetCollectionTest;
import com.google.gwt.widgetideas.client.SliderBarTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests the top level widgets.
 */
public class TopLevelWidgetsTestSuite {
  public static Test suite() {
    TestSuite suite = new TestSuite(
        "Test for com.google.gwt.widgetideas.client");
    // Widget Tests.
    suite.addTestSuite(ProgressBarTest.class);
    suite.addTestSuite(SliderBarTest.class);
    suite.addTestSuite(CollapsiblePanelTest.class);
    suite.addTestSuite(DropDownListBoxTest.class);
    suite.addTestSuite(ResizableWidgetCollectionTest.class);
    return suite;
  }

}
