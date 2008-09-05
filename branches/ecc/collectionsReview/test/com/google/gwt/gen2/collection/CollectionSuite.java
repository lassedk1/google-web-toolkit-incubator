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

package com.google.gwt.gen2.collection;

import com.google.gwt.gen2.collection.client.JsCollectionTest;
import com.google.gwt.gen2.collection.client.JsStringMapTest;
import com.google.gwt.junit.tools.GWTTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Collections suite.
 */
public class CollectionSuite extends GWTTestSuite {
  /**
   * Suite.
   */
  public static Test suite() {
    TestSuite suite = new TestSuite("tests for gen2 collections");
    suite.addTestSuite(JsCollectionTest.class);
    suite.addTestSuite(JsStringMapTest.class);
    return suite;
  }
}
