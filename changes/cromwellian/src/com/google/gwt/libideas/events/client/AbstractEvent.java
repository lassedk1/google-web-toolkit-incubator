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

/**
 * AbstractEvent is the root of all events.
 *
 * @param <T> the type of handler used for this event
 */
public abstract class AbstractEvent<T extends EventHandler> {

  /**
   * Key class used to register events with HandlerManager.
   */
  public static class Key<T> {
  }

  private Object source;

  /**
   * Constructs a new event with the given event source.
   */
  protected AbstractEvent(Object source) {
    setSource(source);
  }

  /**
   * Constructs a new event with no event source. The event source may be
   * specified by a followup call to setSource()
   */
  protected AbstractEvent() {
  }

  /**
   * Returns the source that generated the event.
   */
  public Object getSource() {
    return source;
  }

  /**
   * Fires event for given handler type.
   *
   * @param handler of type T
   */
  protected abstract void fireEvent(T handler);

  /**
   * Returns the Key used to register this event.
   */
  protected abstract Key getKey();

  /**
   * Set the source that triggered this event.
   *
   * @param source An object of type F
   */
  void setSource(Object source) {
    this.source = source;
  }
}
