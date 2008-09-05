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
package com.google.gwt.gen2.event.dom.client;

import com.google.gwt.user.client.Event;
import com.google.gwt.gen2.event.shared.HandlerRegistration;

/**
  * Represents a KeyPressedEvent event.
  */
public class KeyPressedEvent extends KeyboardEvent<KeyPressedHandler> {

  /**
   * A widget that implements this interface is a public source of KeyPressedEvent
   * events.
   */
  public static interface Source {

    /**
     * Adds a {@link KeyPressedEvent} handler.
     *
     * @param handler the handler
     */
    HandlerRegistration addKeyPressedHandler(KeyPressedHandler handler);
  }

  public static Key<KeyPressedHandler> KEY = new Key<KeyPressedHandler>(
      BrowserEvents.ONKEYPRESSED);

  /**
    * Constructs a KeyPressedEvent event.
    * @param e An event object, typically from an onBrowserEvent call
    */
  public KeyPressedEvent(Event e) {
    super(KEY, e);
  }

  /**
    * Fires a KeyPressedEvent.
    * @param handler the handler
    */
  @Override
  protected void fireEvent(KeyPressedHandler handler) {
    handler.onKeyPressed(this);
  }
}