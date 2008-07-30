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

package com.google.gwt.widgetideas.client.event;

/**
 * Abstract event type.
 * 
 * @param <FiresEvent> source of the event
 */
public abstract class AbstractEvent<FiresEvent> {
  private FiresEvent source;

  /**
   * The key
   */
  public static class Key {
  }

  protected AbstractEvent(FiresEvent source) {
    this.source = source;
  }

  /**
   * The event type the handlers will be listening for.
   * 
   * @return the event class
   */
  public Class getEventClass() {
    return this.getClass();
  }

  /**
   * Gets the source of the event.
   */
  public FiresEvent getSource() {
    return source;
  }

  /**
   * Sets the source of the event. This should only be used by composites or
   * similar widgets which need to re-source events.
   */
  public void setSource(FiresEvent source) {
    this.source = source;
  }

  /**
   * Delegates firing the event to the given handler type.
   * 
   * @param e event
   */
  protected abstract void fire(EventHandler e);

}