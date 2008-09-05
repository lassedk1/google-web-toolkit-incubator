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

package com.google.gwt.gen2.collection.client;

import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * Test base for collection classes.
 */
public class CollectionTestBase extends GWTTestCase {
  /**
   * The local module defined for collection.
   */
  public static String LOCAL_MODULE = "com.google.gwt.gen2.collection.CollectionSuite";

  /**
   * Module used for this test suite.
   */
  public static String MODULE = Gen2TestBase.MODULE;

  @Override
  public String getModuleName() {
    return LOCAL_MODULE;
  }

}
