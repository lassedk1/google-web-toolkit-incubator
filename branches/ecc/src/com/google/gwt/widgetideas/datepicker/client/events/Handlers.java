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

package com.google.gwt.widgetideas.datepicker.client.events;

import java.util.ArrayList;

/**
 * Generic handlers collection.
 * 
 * @param <HandlerType> handler type
 */
public class Handlers<HandlerType extends EventHandler> extends
    ArrayList<HandlerType> {

  /**
   * Adds a handler to a handler collection. Creates the handler collection if
   * necessary.
   * 
   * @param <T> the handler type
   * @param collection the handler collection
   * @param handler the handler to be added
   * @return the handler collection
   */
  public static <T extends EventHandler> Handlers<T> add(
      Handlers<T> collection, T handler) {
    if (collection == null) {
      collection = new Handlers<T>();
    }
    collection.add(handler);
    return collection;
  }

  /**
   * Removes the handler from the collection if present.
   * 
   * @param collection the collection
   * @param handler the handler to be removed
   */
  public static boolean remove(Handlers collection, EventHandler handler) {
    if (collection != null) {
      return collection.remove(handler);
    }
    return false;
  }

  /**
   * Fires the given event. Returns false if the event was canceled.
   * 
   * @param event the event
   * @return whether the event succeeded.
   */
  public boolean fire(AbstractEvent event) {
    for (EventHandler e : this) {
      ((AbstractEvent) event).tryFire(e);
      if (event.isCanceled()) {
        return false;
      }
    }
    return true;
  }
}