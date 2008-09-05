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

import com.google.gwt.benchmarks.client.Benchmark;
import com.google.gwt.benchmarks.client.IntRange;
import com.google.gwt.benchmarks.client.Operator;
import com.google.gwt.benchmarks.client.RangeEnum;
import com.google.gwt.benchmarks.client.RangeField;
import com.google.gwt.benchmarks.client.Setup;
import com.google.gwt.gen2.collection.shared.FastMap;

import java.util.HashMap;

/**
 * JsStringMap benchmark.
 */
public class JsStringMapBenchmark extends Benchmark {

  enum Type {
    JS, HASH
  }

  IntRange keyRange = new IntRange(1000, 10000, Operator.ADD, 1000);;

  Object map;

  String[] keys;

  @Override
  public String getModuleName() {
    return "com.google.gwt.gen2.collection.Collection";
  }

  public void testGets() {
  }

  @Setup("setupPuts")
  public void testGets(@RangeField("keyRange") Integer size,
      @RangeEnum(Type.class) Type type) {
    if (type == Type.HASH) {
      HashMap<String, Object> m = (HashMap<String, Object>) map;
      for (String key : keys) {
        if (m.get(key) == "never should") {
          throw new IllegalStateException();
        }
      }
      preserve(m);
    } else if (type == Type.JS) {
      JsStringMap m = (JsStringMap) map;
      for (String key : keys) {
        if (m.get(key) == "never should") {
          throw new IllegalStateException();
        }
      }
      preserve(m);
    } else {
      throw new IllegalArgumentException(type + " is not handled");
    }
    keys = null;
    map = null;
  }

  public void testPuts() {
  }

  @Setup("setupKeys")
  public void testPuts(@RangeField("keyRange") Integer size,
      @RangeEnum(Type.class) Type type) {
    if (type == Type.HASH) {
      HashMap<String, Object> m = new HashMap();
      for (String key : keys) {
        m.put(key, key);
      }
      preserve(m);
    } else if (type == Type.JS) {
      JsStringMap m = new JsStringMap();
      for (String key : keys) {
        m.put(key, key);
      }
      preserve(m);
    } else {
      throw new IllegalArgumentException(type + " is not handled");
    }
    keys = null;
  }

  public void testPutsAndGets() {
  }

  @Setup("setupKeys")
  public void testPutsAndGets(@RangeField("keyRange") Integer size,
      @RangeEnum(Type.class) Type type) {
    if (type == Type.HASH) {
      HashMap<String, Object> m = new HashMap<String, Object>();
      for (String key : keys) {
        m.put(key, key);
      }
      for (String key : keys) {
        if (m.get(key) == "never should") {
          throw new IllegalStateException();
        }
      }
      preserve(m);
    } else if (type == Type.JS) {
      JsStringMap m = new JsStringMap();
      for (String key : keys) {
        m.put(key, key);
      }
      for (String key : keys) {
        if (m.get(key) == "never should") {
          throw new IllegalStateException();
        }
      }
      preserve(m);
    } else {
      throw new IllegalArgumentException(type + " is not handled");
    }
    keys = null;
    map = null;
  }

  String[] createKeys(String postfix, int size) {
    String[] keys = new String[size];
    for (int i = 0; i < size; i++) {
      keys[i] = i + postfix;
    }
    return keys;
  }

  void setupKeys(Integer size, Type type) {
    keys = createKeys("intKey", size);
  }

  void setupPuts(Integer size, Type type) {
    setupKeys(size, type);
    if (type == Type.HASH) {
      HashMap m = new HashMap();
      for (String key : keys) {
        m.put(key, key);
      }
      map = m;
    } else if (type == Type.JS) {
      JsStringMap s = new JsStringMap();
      for (String key : keys) {
        s.put(key, key);
      }
      map = s;
    } else {
      FastMap<String, String> s = FastMap.createStringMap();
      for (String key : keys) {
        s.put(key, key);
      }
      map = s;
    }
  }

  private void preserve(HashMap<String, Object> m) {
    // Should never to true, used to prevent compiler optimizations
    if (m.get("never") != null) {
      throw new IllegalStateException();
    }
  }

  private void preserve(JsStringMap m) {
    if (m.get("never") != null) {
      throw new IllegalStateException();
    }
  }

}
