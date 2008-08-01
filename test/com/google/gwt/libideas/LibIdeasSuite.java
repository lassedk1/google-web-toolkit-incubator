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
package com.google.gwt.libideas;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.google.gwt.libideas.event.client.EventsTest;
import com.google.gwt.libideas.logging.LogServerTest;
import com.google.gwt.libideas.logging.client.LogTest;
import com.google.gwt.libideas.resources.ResourcesSuite;
import com.google.gwt.libideas.server.LocaleMatcherTest;

import junit.framework.Test;

/**
 * All lib ideas test suite.
 */
public class LibIdeasSuite {
  public static Test suite() {
    GWTTestSuite suite = new GWTTestSuite(
        "Test for com.google.gwt.libideas.resources.client");
    suite.addTest(ResourcesSuite.suite());
    suite.addTestSuite(LogTest.class);
    suite.addTestSuite(LogServerTest.class);
    suite.addTestSuite(LocaleMatcherTest.class);
    suite.addTestSuite(EventsTest.class);

    return suite;
  }
}
