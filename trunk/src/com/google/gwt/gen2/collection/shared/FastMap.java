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

package com.google.gwt.gen2.collection.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.collection.client.AbstractJsMap;
import com.google.gwt.gen2.collection.client.JsStringMap;

import java.util.HashMap;

/**
 * This is a wrapper class used to select either a js map or hash map.
 * 
 * @param <KeyType> type of key
 * @param <ValueType> type of value
 */
public abstract class FastMap<KeyType, ValueType> {

  private static class JavaFastMap<KeyType, ValueType> extends
      FastMap<KeyType, ValueType> {
    private final HashMap<KeyType, ValueType> map = new HashMap();

    @Override
    public ValueType get(KeyType key) {
      return map.get(key);
    }

    @Override
    public void put(KeyType key, ValueType value) {
      map.put(key, value);
    }
  }

  private static class JSFastMap<KeyType, ValueType> extends
      FastMap<KeyType, ValueType> {
    private final AbstractJsMap<KeyType, ValueType> map;

    JSFastMap(AbstractJsMap<KeyType, ValueType> map) {
      this.map = map;
    }

    @Override
    public ValueType get(KeyType key) {
      return map.get(key);
    }

    @Override
    public void put(KeyType key, ValueType value) {
      map.put(key, value);
    }
  }

  private static final boolean useJS = GWT.isScript();

  /**
   * Creates a HashMap in hosted mode, a {@link JsStringMap} in web mode.
   * 
   * @param <ValueType>
   * @return
   */
  public static <ValueType> FastMap<String, ValueType> createStringMap() {
    if (useJS) {
      return new JSFastMap<String, ValueType>(new JsStringMap<ValueType>());
    } else {
      return new JavaFastMap<String, ValueType>();
    }
  }

  public abstract ValueType get(KeyType key);
  public abstract void put(KeyType key, ValueType value);
}
