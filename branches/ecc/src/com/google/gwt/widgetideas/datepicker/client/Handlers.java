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

package com.google.gwt.widgetideas.datepicker.client;

import com.google.gwt.widgetideas.client.AbstractEvent;

import java.util.ArrayList;

/**
 * Generic handlers collection.
 * 
 * @param <T> event type
 */
public class Handlers<T extends AbstractEvent> extends ArrayList<Handler> {

  public static <T extends AbstractEvent> Handlers<T> add(
      Handlers<T> collection, Handler handler) {
    if (collection == null) {
      collection = new Handlers<T>();
    }
    collection.add(handler);
    return collection;
  }

  public static void remove(Handlers collection, Handler handler) {
    if (collection != null) {
      collection.remove(handler);
    }
  }

  public T fire(T event) {
    for (Handler e : this) {
      e.fire(event);
      if (event.isCanceled()) {
        return event;
      }
    }
    return event;
  }
}