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

import org.apache.commons.collections.TestMap;

import java.util.Map;

/**
 * JsStringMapTest.
 */
public class JsStringMapTest extends TestMap {

  @Override
  public String getModuleName() {
    return CollectionTestBase.MODULE;
  }

  /**
   * Tests Map.put(Object, Object).
   */
  @Override
  public void testMapPut() {
    if (!isAddRemoveModifiable()) {
      return;
    }

    resetEmpty();

    Object[] keys = getSampleKeys();
    Object[] values = getSampleValues();
    Object[] newValues = getNewSampleValues();

    for (int i = 0; i < keys.length; i++) {
      Object o = map.put(keys[i], values[i]);
      confirmed.put(keys[i], values[i]);
      verify();
      assertTrue("First map.put should return null", o == null);
      assertTrue("Map should contain key after put", map.containsKey(keys[i]));
      assertTrue("Map should contain value after put",
          map.containsValue(values[i]));
    }

    for (int i = 0; i < keys.length; i++) {
      Object o = map.put(keys[i], newValues[i]);
      confirmed.put(keys[i], newValues[i]);
      verify();
      assertEquals("Second map.put should return previous value", values[i], o);
      assertTrue("Map should still contain key after put",
          map.containsKey(keys[i]));
      assertTrue("Map should contain new value after put",
          map.containsValue(newValues[i]));

      // if duplicates are allowed, we're not guaranteed that the value
      // no longer exists, so don't try checking that.
      if (!useDuplicateValues()) {
        assertTrue("Map should not contain old value after second put",
            !map.containsValue(values[i]));
      }
    }
  }

  @Override
  protected Map makeEmptyMap() {
    return new JsStringMap().createMapAdaptor();
  }

  /**
   * Override if your map does not allow a <code>null</code> key. The default
   * implementation returns <code>true</code>
   */
  @Override
  protected boolean useNullKey() {
    return false;
  }

  @Override
  protected boolean useNullValue() {
    return false;
  }

}
