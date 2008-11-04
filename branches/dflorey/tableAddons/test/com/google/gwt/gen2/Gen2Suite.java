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

package com.google.gwt.gen2;

import com.google.gwt.gen2.commonwidget.client.DecoratorTest;
import com.google.gwt.gen2.event.dom.client.EventsTest;
import com.google.gwt.gen2.event.dom.client.GWTEventsTest;
import com.google.gwt.gen2.event.shared.JSHanderRegistryTest;
import com.google.gwt.gen2.selection.client.DropDownListBoxTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Gen2 test suite.
 */
public class Gen2Suite {
  /** These tests should eventually be refactored into their own sub-suites. */
  public static Test suite() {
    TestSuite suite = new TestSuite("Tests for gen2 widgets and libraries.");
    // Widget Tests.
    suite.addTestSuite(EventsTest.class);
    suite.addTestSuite(GWTEventsTest.class);
    suite.addTestSuite(JSHanderRegistryTest.class);
    suite.addTestSuite(DropDownListBoxTest.class);
    suite.addTestSuite(DecoratorTest.class);
    return suite;
  }
}
