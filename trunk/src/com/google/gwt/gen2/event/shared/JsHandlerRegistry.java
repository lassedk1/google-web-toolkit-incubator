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

package com.google.gwt.gen2.event.shared;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.gen2.event.shared.AbstractEvent.Key;

/**
 * Default JavaScript handler registry. This is in the shared package so we
 * don't have to make it public, should never be called outside of GWT.
 * 
 * The {@link JsHandlerRegistry} makes use of the fact that in the large
 * majority of cases, only one or two handlers are added for each event type.
 * Therefore, rather than storing handlers in a list of lists, we store then in
 * a single flattened array with an escape clause to handler the rare case where
 * we have more handlers then expected.
 */
class JsHandlerRegistry extends JavaScriptObject {

  public static JsHandlerRegistry create() {
    return (JsHandlerRegistry) JavaScriptObject.createObject();
  }

  /**
   * Required constructor.
   */
  protected JsHandlerRegistry() {
  }

  public final void addHandler(AbstractEvent.Key eventKey, EventHandler handler) {
    int base = eventKey.hashCode();
    int count = getCount(base);

    // Switch from flattened list of handlers to list of list of handlers.
    if (count == HandlerManager.EXPECTED_HANDLERS && isFlattened(base)) {
      unflatten(base);
    }
    setHandler(base, count, handler, isFlattened(base));
    setCount(base, count + 1);
  }

  public final void clearHandlers(Key<?, ?> key) {
    int base = key.hashCode();
    unflatten(base);
    setHandlerList(base + 1, JavaScriptObject.createArray());
    setCount(base, 0);
  }

  public final void fireEvent(AbstractEvent event) {
    Key key = event.getKey();
    int base = key.hashCode();
    int count = getCount(base);
    boolean isFlattened = isFlattened(base);

    for (int i = 0; i < count; i++) {
      EventHandler handler = getHandler(base, i, isFlattened);
      key.fire(handler, event);
    }
  }

  public final EventHandler getHandler(AbstractEvent.Key eventKey, int index) {
    int base = eventKey.hashCode();
    int count = getCount(base);
    if (index >= count) {
      throw new IndexOutOfBoundsException("index: " + index);
    }
    return getHandler(base, index, isFlattened(base));
  }

  public final int getHandlerCount(AbstractEvent.Key eventKey) {
    return getCount(eventKey.hashCode());
  }

  public final void removeHandler(AbstractEvent.Key eventKey,
      EventHandler handler) {
    int base = eventKey.hashCode();

    // Removing a handler is unusual, so smaller code is preferable then
    // handling both flat and dangling list of pointers.
    unflatten(base);
    boolean result = removeHelper(base, handler);
    assert result : handler + " did not exist";
  }

  private native int getCount(int index) /*-{
    var count = this[index];
    return count == null? 0:count;
  }-*/;

  private native EventHandler getHandler(int base, int index, boolean flattened) /*-{
    if(flattened) {
      return this[base + 2 + index];
    } else {
     return this[base + 1][index];
    }
  }-*/;

  private native boolean isFlattened(int base) /*-{
    return this[base + 1] == null;
  }-*/;

  private native boolean removeHelper(int base, EventHandler handler) /*-{
    // Find the handler.
    var count = this[base];
    var handlerList = this[base + 1];
    var handlerIndex = -1;
    for(var index = 0;  index < count; index++){
      if(handlerList[index] == handler){
        handlerIndex = index;
        break;
      }
    }
    if(handlerIndex == -1) {
      return false;
    }

    // Remove the handler.
    var last = count -1;
    for(; handlerIndex < last; handlerIndex++){
      handlerList[handlerIndex] = handlerList[handlerIndex+1]
    }
    handlerList[last] = null;
    --this[base];
    return true;
  }-*/;

  private native void setCount(int index, int count) /*-{
    this[index] = count;
  }-*/;

  private native void setHandler(int base, int index, EventHandler handler,
      boolean flattened) /*-{
    if(flattened) {
      this[base + 2 + index] = handler;
    } else {
      this[base + 1][index] = handler;
    }
  }-*/;

  private native void setHandlerList(int base, JavaScriptObject handlerList) /*-{
    this[base + 1] = handlerList;
  }-*/;

  private native void unflatten(int base) /*-{
    var handlerList = {};
    var count = this[base];
    var start = base + 2;
     for(var i = 0; i < count;i++){
       handlerList[i] = this[start + i];
       this[start + i] = null;
      }
     this[base + 1] = handlerList;
  }-*/;
}