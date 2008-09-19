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
 */
class JsHandlerRegistry extends JavaScriptObject {

  /**
   * Create a {@link JsHandlerRegistry} object.
   * 
   * @return the handler
   */
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
    int count = getCount(this, base);
    if (count < AbstractEvent.Key.EXPECTED_MAX_HANDLERS_PER_WIDGET) {
      ++count;
      int slot = base + count;
      set(this, slot, handler);
      setCount(this, base, count);
    } else {
      throw new IllegalStateException("Else case not implemented yet");
    }
  }

  public final void fireEvent(AbstractEvent event) {
    Key key = event.getKey();
    int count = getHandlerCount(key);
    for (int i = 0; i < count; i++) {
      EventHandler handler = getHandler(key, i);
      key.fire(handler, event);
    }
  }

  public final EventHandler getHandler(AbstractEvent.Key eventKey, int index) {
    assert (index < getHandlerCount(eventKey));
    int findAt = eventKey.hashCode() + index + 1;
    return get(this, findAt);
  }

  public final int getHandlerCount(AbstractEvent.Key eventKey) {
    int index = eventKey.hashCode();
    return getCount(this, index);
  }

  public final void removeHandler(AbstractEvent.Key eventKey,
      EventHandler handler) {
    remove(this, eventKey.hashCode(), handler);
  }

  private native EventHandler get(JavaScriptObject me, int index) /*-{
     return this[index];
   }-*/;

  private native int getCount(JavaScriptObject me, int index) /*-{
     var count = this[index];
     if(count == null){
       return 0;
     } else{
       return count;
     }
   }-*/;

  private native void remove(JavaScriptObject me, int base, EventHandler handler) /*-{
     var count = this[base];
     var index = base + 1;
     var last =  base + count;

     for(;index <= last;index++){
       if(this[index] == handler){
         break;
       }
     }

     for(; index < last; index++){
       this[index] = this[index+1]
     }
     this[last] = null;
     --this[base];
   }-*/;

  private native void set(JavaScriptObject me, int slot, EventHandler handler) /*-{
     this[slot] = handler;
   }-*/;

  private native void setCount(JavaScriptObject me, int index, int count) /*-{
     this[index] = count;
   }-*/;
}