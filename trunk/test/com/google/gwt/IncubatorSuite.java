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

package com.google.gwt;

import com.google.gwt.gen2.Gen2Suite;
import com.google.gwt.junit.tools.GWTTestSuite;
import com.google.gwt.libideas.LibIdeasSuite;
import com.google.gwt.widgetideas.WidgetIdeasSuite;

import junit.framework.Test;

/**
 * All the incubator unit tests, run at once.
 */
public class IncubatorSuite {
  public static Test suite() {
    GWTTestSuite suite = new GWTTestSuite("Tests for incubator");
    suite.addTest(WidgetIdeasSuite.suite());
    suite.addTest(LibIdeasSuite.suite());
    suite.addTest(Gen2Suite.suite());
    return suite;
  }
}
