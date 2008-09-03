/*
 * Copyright 2007 Google Inc.
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

import java.util.Map;

/**
 * Special-case Map implementation which imposes limits on the types of keys
 * that can be used in return for much faster speed.
 * @param <ValueType> value type
 */

public class JsStringMap<ValueType> extends AbstractJsMap<String, ValueType> {
   
  public JsStringMap() {
   }

  @Override
  public Map<String, ValueType> createMapAdaptor() {
    return new JsMapAdaptor<String, ValueType>(this) {

      @Override
      protected String checkKey(Object key) {
        assert key instanceof String : key + " is not a string";
        return (String) key;
      }

      @Override
      protected AbstractJsMap<String, ValueType> create() {
        return new JsStringMap();
      }
    };
  }
 
}
