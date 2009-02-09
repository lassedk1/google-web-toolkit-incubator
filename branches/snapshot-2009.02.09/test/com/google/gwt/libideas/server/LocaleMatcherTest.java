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

package com.google.gwt.libideas.server;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;

/**
 * Tests for {@link com.google.gwt.libideas.server.LocaleMatcher}.
 */
public class LocaleMatcherTest extends TestCase {

  /**
   * The LocaleMatcher instance under test.
   */
  private LocaleMatcher matcher;
  
  /**
   * Tests fallback "approximate match" behavior.
   */
  public void testFallback() {
    assertEquals("fr", matcher.findBestMatch("fr-be"));
    assertEquals("en", matcher.findBestMatch("fr-be,en;q=0.5"));
    assertEquals("default", matcher.findBestMatch("de"));
  }

  /**
   * Tests that default is returned if nothing matches.
   */
  public void testNonMatching() {
    assertEquals("default", matcher.findBestMatch("zh-cn,zh,ja,ko"));
  }

  /**
   * Tests that various orderings work properly.
   */
  public void testOrdering() {
    assertEquals("en_US", matcher.findBestMatch("en;q=0.3,en-us,q=1.0"));
    assertEquals("en_US", matcher.findBestMatch("en;q=0.3,en-us"));
    assertEquals("en_US", matcher.findBestMatch("en-us,en;q=0.3"));
  }
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    String contents = "default\nen\nen_US\nde_AT\nfr\n";
    ByteArrayInputStream str = new ByteArrayInputStream(contents.getBytes());
    matcher = new LocaleMatcher(str);
  }
}
