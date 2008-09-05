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

import java.util.HashMap;
import java.util.Map;

/**
 * JS collections test.
 */
public class JsCollectionTest extends CollectionTestBase {

 
  /**
   * Check the state of a newly constructed, empty HashMap.
   * 
   * @param m
   */
  private static void checkEmptyMapAssumptions(AbstractJsMap m) {
    assertNotNull(m);
    assertTrue(m.isEmpty());

    assertNotNull(m.copyValues());
    assertTrue(m.copyValues().isEmpty());
    assertTrue(m.copyValues().size() == 0);

    assertNotNull(m.copyKeys());
    assertTrue(m.copyKeys().isEmpty());
    assertTrue(m.copyKeys().size() == 0);
  }

  /**
   * String map test
   */
  public void testStringMap() {
    // Empty check.
    JsStringMap<Integer> m = new JsStringMap<Integer>();
    checkEmptyMapAssumptions(m);
    assertSame(0, m.size());

    // Keyword check
    JsStringMap<String> newMap = new JsStringMap<String>();
    assertNull(newMap.get("watch"));
    assertFalse(newMap.containsKey("watch"));
    newMap.put("watch", "watch me");
    assertEquals("watch me", newMap.get("watch"));
    assertTrue(newMap.containsKey("watch"));

    // Put check.
    m.put("a", -1);
    m.put("b", 2);
    m.put("d", 3);
    m.put("a", 1);
    m.put("c", 2);
    assertEquals(m.get("a"), Integer.valueOf(1));
    assertEquals(m.get("b"), Integer.valueOf(2));
    assertEquals(m.get("c"), Integer.valueOf(2));
    assertEquals(m.get("d"), Integer.valueOf(3));
    assertSame(4, m.size());

    
    // Wrapper and creation check.
    Map wrapper = m.createMapAdaptor();
    assertEquals(4, wrapper.size());
    HashMap dummy = new HashMap();
    m.copyInto(dummy);
    assertEquals(4, dummy.size());
    assertEquals(dummy.hashCode(), wrapper.hashCode());
  }
}
