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

package com.google.gwt.overrides.client;

import com.google.gwt.benchmarks.client.Benchmark;
import com.google.gwt.benchmarks.client.IntRange;
import com.google.gwt.benchmarks.client.Operator;
import com.google.gwt.benchmarks.client.RangeField;

/**
 * A {@link Benchmark} Tests gwt-incubator overridden StringBuffer against
 * gwt-standard StringBuilder.
 */
public class StringBufferBenchmark extends Benchmark {
  final IntRange sizeRange = new IntRange(50, 2000, Operator.ADD, 50);
  
  /**
   * This method gets the module name.
   * 
   * @return the module name.
   * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
   */
  @Override
  public String getModuleName() {
    return "com.google.gwt.overrides.Overrides";
  }

  // Required for JUnit
  public void testStringBufferAppend() {
  }

  /**
   * Basic test of currency format.
   * 
   * @param size the size
   */
  public void testStringBufferAppend(@RangeField("sizeRange")
  Integer size) {
    int limit = size.intValue();
    StringBuffer b = new StringBuffer();
    for (int i = 0; i < limit; i++) {
      b.append(i + "");
    }
  }

  // Required for JUnit
  public void testStringBuilderAppend() {
  }

  /**
   * Basic test of currency format.
   * 
   * @param size the size
   */
  public void testStringBuilderAppend(@RangeField("sizeRange")
  Integer size) {
    int limit = size.intValue();
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < limit; i++) {
      b.append(i + "");
    }
  }
}
