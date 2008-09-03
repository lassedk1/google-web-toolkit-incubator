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

import com.google.gwt.core.client.JavaScriptObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Special-case Map implementation which imposes limits on the types of keys
 * that can be used in return for much faster speed and defines a new map API.
 * <br/> This map is optimized for the get/set operations. Use the
 * {@link JsMapAdaptor} class to get access to the more expensive java.util API.
 * 
 * @param <KeyType> type of key
 * @param <ValueType> type of value
 */

public abstract class AbstractJsMap<KeyType, ValueType> {

  protected static final JavaScriptObject proto = JavaScriptObject.createFunction();

  protected final JavaScriptObject map;
 
  /**
   * Constructor.Package protected because we do not yet want to open this
   * interface up to extenders outside of gwt-incubator.
   */
  AbstractJsMap() {
    map = createMap(proto);
  }

  /**
   * Does this map contain the given key?
   */
  public boolean containsKey(KeyType key) {
    return containsKey(map, key);
  }

  /**
   * Copies the given map into the supplied map.
   */
  public void copyInto(Map<KeyType, ValueType> target) {
    assert (target != null);
    copyInto(map, target);
  }

  /**
   * Returns a collection populated with the current set of keys.
   */
  public Collection<KeyType> copyKeys() {
    ArrayList<KeyType> list = new ArrayList();
    copyKeysInto(list);
    return list;
  }

  /**
   * Copy the maps keys into the given collection.
   */
  public void copyKeysInto(Collection<KeyType> s) {
    copyKeysInto(map, s);
  }

  /**
   * Returns a collection populated with the current set of values.
   */
  public Collection<ValueType> copyValues() {
    ArrayList<ValueType> list = new ArrayList();
    copyValuesInto(list);
    return list;
  }

  /**
   * Adds all the values in this map to the given collection.
   */
  public void copyValuesInto(Collection<ValueType> collection) {
    copyValuesInto(map, collection);
  }

  /**
   * Creates a java wrapper for this map. All methods are less efficient when
   * run through the mapping adaptor.
   */
  public abstract Map<KeyType, ValueType> createMapAdaptor();

  public ValueType get(KeyType key) {
    return get(map, key);
  }

  /**
   * Is the map empty?
   */
  public boolean isEmpty() {
    return isEmpty(map);
  }

  /**
   * Puts the key and value pair into the map.
   */
  public void put(KeyType key, ValueType value) {
    put(proto, map, key, value);
  }

  /**
   * Removes the given key from the map.
   */
  public void remove(KeyType key) {
    remove(map, key);
  }

  /**
   * Gets the size of the map. Note,this is an expensive operation as the values
   * must be manually counted.
   */
  public int size() {
    return size(map);
  }

  native boolean containsKey(JavaScriptObject map, KeyType key)/*-{
    return (key) in map;
  }-*/;

  native void copyInto(JavaScriptObject map, Map<KeyType, ValueType> addTo) /*-{
    for(var key in map) {
      addTo.@java.util.Map::put(Ljava/lang/Object;Ljava/lang/Object;)(key, map[key]);
    }
  }-*/;

  native void copyKeysInto(JavaScriptObject map, Collection<KeyType> addTo) /*-{
    for(var key in map) {
      addTo.@java.util.Collection::add(Ljava/lang/Object;)(key);
    }
  }-*/;

  native void copyValuesInto(JavaScriptObject map, Collection<ValueType> addTo)
  /*-{
    for(var key in map) {
      var value = map[key];
      addTo.@java.util.Collection::add(Ljava/lang/Object;)(value);
    }
  }-*/;

  native JavaScriptObject createMap(JavaScriptObject proto) /*-{
    return new proto;
  }-*/;

  native ValueType get(JavaScriptObject map, KeyType key) /*-{
    return map[key];
  }-*/;

  native boolean isEmpty(JavaScriptObject map) /*-{
    for(var key in map) {
      return false;
    }
    return true;
  }-*/;

  native void put(JavaScriptObject proto, JavaScriptObject map, KeyType key,
      ValueType value) /*-{
    if(key in proto.prototype) {
      delete proto.prototype[key];
    }
    map[key] = value;
  }-*/;

  native void remove(JavaScriptObject map, KeyType key) /*-{
    var previous = map[key];
    delete map[key];
    return previous;
  }-*/;

  native int size(JavaScriptObject map) /*-{
    var count = 0;
    for(var key in map) {
      ++count;
    }
    return count;
  }-*/;

}
