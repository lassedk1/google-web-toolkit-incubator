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
package com.google.gwt.libideas.events.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages a type-safe map of event Keys to EventHandlers.
 */
public class HandlerManager {

  // TODO(cromwellian) add implementation that uses native JS hash/arrays
  private Map<AbstractEvent.Key, ArrayList<EventHandler>> handlerRegistry;

  private Object source;

  public HandlerManager(Object source) {
    this.handlerRegistry
        = new HashMap<AbstractEvent.Key, ArrayList<EventHandler>>();
    this.source = source;
  }

  public void fireEvent(AbstractEvent event) {
    ArrayList<EventHandler> handlers = handlerRegistry.get(event.getKey());
    event.setSource(source);
    if (handlers != null) {
      for (EventHandler handler : handlers) {
        event.fireEvent(handler);
      }
    }
  }

  public <T extends EventHandler> HandlerRegistration addEventHandler(
      AbstractEvent.Key<T> key, final T handler) {
    ArrayList<EventHandler> handlers = handlerRegistry.get(key);
    if (handlers == null) {
      handlers = new ArrayList<EventHandler>();
      handlerRegistry.put(key, handlers);
    }
    handlers.add(handler);
    final ArrayList<EventHandler> finalHandlers = handlers;
    return new HandlerRegistration() {
      public void removeHandler() {
        finalHandlers.remove(handler);
      }
    };
  }
}
