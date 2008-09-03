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

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Wraps a java.util.Map around a java.util map. This will, in general, be much
 * slower that using the JsMap directly.
 * 
 * @param <KeyType> type of key
 * @param <ValueType> type of value
 */
public abstract class JsMapAdaptor<KeyType, ValueType> extends
    AbstractMap<KeyType, ValueType> {
  /**
   * Default Map entry.
   * 
   * @param <KeyType> key type
   * @param <ValueType> value type
   */
  protected class ImplMapEntry implements Map.Entry<KeyType, ValueType> {

    private KeyType key;

    private ValueType value;

    ImplMapEntry(KeyType key, ValueType value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public boolean equals(Object a) {
      if (a instanceof Map.Entry) {
        Map.Entry<?, ?> s = (Map.Entry<?, ?>) a;
        if (equalsWithNullCheck(key, s.getKey())
            && equalsWithNullCheck(value, s.getValue())) {
          return true;
        }
      }
      return false;
    }

    public KeyType getKey() {
      return key;
    }

    public ValueType getValue() {
      return value;
    }

    @Override
    public int hashCode() {
      int keyHash = 0;
      int valueHash = 0;
      if (key != null) {
        keyHash = key.hashCode();
      }
      if (value != null) {
        valueHash = value.hashCode();
      }
      return keyHash ^ valueHash;
    }

    public ValueType setValue(ValueType object) {
      ValueType old = value;
      value = object;
      return old;
    }

    private boolean equalsWithNullCheck(Object a, Object b) {
      if (a == b) {
        return true;
      } else if (a == null) {
        return false;
      } else {
        return a.equals(b);
      }
    }
  }

  private AbstractJsMap<KeyType, ValueType> js;

  public JsMapAdaptor() {
    clear();
  }

  public JsMapAdaptor(AbstractJsMap<KeyType, ValueType> js) {
    this.js = js;
  }

  @Override
  public final void clear() {
    js = create();
  }

  @Override
  public boolean containsKey(Object key) {
    return js.containsKey(checkKey(key));
  }

  @Override
  public final Set<Map.Entry<KeyType, ValueType>> entrySet() {
    return new AbstractSet<Map.Entry<KeyType, ValueType>>() {

      @Override
      public boolean contains(Object key) {
        Map.Entry<KeyType, ?> s = (Map.Entry<KeyType, ?>) key;
        ValueType value = js.get(checkKey(s.getKey()));
        if (value == null) {
          return value == s.getValue();
        } else {
          return value.equals(s.getValue());
        }
      }

      @Override
      public final Iterator<Map.Entry<KeyType, ValueType>> iterator() {
        Iterator<Map.Entry<KeyType, ValueType>> custom = new Iterator<Map.Entry<KeyType, ValueType>>() {
          Iterator<KeyType> keys = js.copyKeys().iterator();

          public boolean hasNext() {
            return keys.hasNext();
          }

          public Map.Entry<KeyType, ValueType> next() {
            KeyType key = keys.next();
            return new ImplMapEntry(key, js.get(key));
          }

          public void remove() {
            keys.remove();
          }
        };
        return custom;
      }

      @Override
      public int size() {
        return js.size();
      }
    };
  }

  @Override
  public ValueType get(Object key) {
    return js.get(checkKey(key));
  }

  @Override
  public ValueType put(KeyType key, ValueType value) {
    ValueType old = js.get(key);
    js.put(key, value);
    return old;
  }

  @Override
  public ValueType remove(Object key) {
    KeyType checked = checkKey(key);
    ValueType oldValue = js.get(checked);
    js.remove(checked);
    return oldValue;
  }

  @Override
  public int size() {
    return js.size();
  }

  protected abstract KeyType checkKey(Object value);

  protected abstract AbstractJsMap<KeyType, ValueType> create();

}