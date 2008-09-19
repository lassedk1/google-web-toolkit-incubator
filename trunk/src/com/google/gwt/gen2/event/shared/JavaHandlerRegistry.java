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

import com.google.gwt.gen2.event.shared.AbstractEvent.Key;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The default Handler manager's handler registry.
 */
class JavaHandlerRegistry extends
    HashMap<AbstractEvent.Key, ArrayList<EventHandler>> {

  public void addHandler(AbstractEvent.Key eventKey, EventHandler handler) {
    ArrayList<EventHandler> l = super.get(eventKey);
    if (l == null) {
      l = new ArrayList<EventHandler>();
      super.put(eventKey, l);
    }
    l.add(handler);
  }

  public void fireEvent(AbstractEvent event) {
    Key key = event.getKey();
    int count = getHandlerCount(key);
    for (int i = 0; i < count; i++) {
      EventHandler handler = getHandler(key, i);
      key.fire(handler, event);
    }
  }

  public EventHandler getHandler(AbstractEvent.Key eventKey, int index) {
    assert (index < getHandlerCount(eventKey));
    ArrayList<EventHandler> l = super.get(eventKey);
    return l.get(index);
  }

  public int getHandlerCount(AbstractEvent.Key eventKey) {
    ArrayList<EventHandler> l = super.get(eventKey);
    if (l == null) {
      return 0;
    } else {
      return l.size();
    }
  }

  public void removeHandler(AbstractEvent.Key eventKey, EventHandler handler) {
    ArrayList<EventHandler> l = super.get(eventKey);
    if (l != null) {
      l.remove(handler);
    }
  }
}
