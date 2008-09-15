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

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.event.shared.AbstractEvent.Key;

/**
 * Manager responsible for adding handlers to events and firing those handlers
 * on the given events.
 */
public class HandlerManager {
  // TODO(ECC) once fully vetted, we should use java mode for hosted mode.
  private static final boolean useJs = GWT.isClient();
  private final JsHandlerRegistry javaScriptRegistry;
  private final JavaHandlerRegistry javaRegistry;
  private final Object source;

  public HandlerManager(Object source) {
    if (useJs) {
      javaScriptRegistry = JsHandlerRegistry.create();
      javaRegistry = null;
    } else {
      javaRegistry = new JavaHandlerRegistry();
      javaScriptRegistry = null;
    }
    this.source = source;
  }

  /**
   * Adds a handler to this registry.
   * 
   * @param <T> The type of handler.
   * @param key the event key associated with this handler
   * @param handler the handler
   * @return the handler registration, can be stored in order to remove the
   *         handler later.
   */
  public <T extends EventHandler> HandlerRegistration addHandler(
      AbstractEvent.Key<T> key, final T handler) {
    if (useJs) {
      javaScriptRegistry.addHandler(key, handler);
    } else {
      javaRegistry.addHandler(key, handler);
    }
    return new HandlerRegistration(this, key, handler);
  }

  /**
   * Fires the given event to the handlers listening to the event's key.
   * 
   * @param event the event
   */
  public void fireEvent(AbstractEvent event) {
    event.setSource(source);
    if (useJs) {
      javaScriptRegistry.fireEvent(event);
    } else {
      javaRegistry.fireEvent(event);
    }
  }

  /**
   * Gets the number of handlers listening to the event key.
   * 
   * @param key the event key
   * @return the number of registered handlers
   */
  public int getHandlerCount(Key key) {
    if (useJs) {
      return javaScriptRegistry.getHandlerCount(key);
    } else {
      return javaRegistry.getHandlerCount(key);
    }
  }

  /**
   * Are there handlers in this manager listening to the given event key?
   * 
   * @param key the event key
   * @return are handlers listening
   */
  public boolean isEventHandled(Key key) {
    return getHandlerCount(key) > 0;
  }

  /**
   * Removes the given handler from the specified event key. Normally,
   * applications should call {@link HandlerRegistration#removeHandler()}
   * instead. This method is provided primary to support deprecated APIS.
   * 
   * @param key the event key
   * @param handler the handler
   */
  public <T extends EventHandler> void removeHandler(AbstractEvent.Key<T> key,
      final T handler) {
    if (useJs) {
      javaScriptRegistry.removeHandler(key, handler);
    } else {
      javaRegistry.removeHandler(key, handler);
    }
  }
}
