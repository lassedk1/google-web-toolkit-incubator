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

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.events.client.AbstractEvent.Key;

/**
 * Manages a type-safe map of event keys to event handlers.
 */
public class HandlerManager {

  private HandlerRegistry registry;
  private Object source;

  public HandlerManager(Object source, HandlerRegistry registry) {
    this.registry = registry;
    this.source = source;
  }

  public HandlerManager(Object source) {
    this(source, pickRegistry());

  }

  private static HandlerRegistry pickRegistry() {
    // TODO(ECC) once fully debugged, should also you java version in hosted
    // mode.
    if (GWT.isClient()) {
      return new JSHandlerRegistry();
    } else {
      return new JavaHandlerRegistry();
    }
  }

  public void fireEvent(AbstractEvent event) {
    Key key = event.getKey();
    int count = registry.getHandlerCount(key);
    event.setSource(source);
    for (int i = 0; i < count; i++) {
      EventHandler handler = registry.getHandler(key, i);
      event.fireEvent(handler);
    }
  }

  public <T extends EventHandler> HandlerRegistration addEventHandler(
      AbstractEvent.Key<T> key, final T handler) {
    registry.addHandler(key, handler);
    return new HandlerRegistration(this, key, handler);
  }

  // Overridable by subclasses.
  protected void removeHandler(Key key, EventHandler handler) {
    registry.removeHandler(key, handler);
  }

}
