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
package com.google.gwt.libideas.event.client.mouse;

import com.google.gwt.libideas.event.shared.AbstractEvent;
import com.google.gwt.libideas.event.shared.HandlerRegistration;
import com.google.gwt.libideas.event.client.BrowserEvents;
import com.google.gwt.user.client.Event;

public class DoubleClickEvent extends MouseEvent<DoubleClickHandler> {

  /**
   * A widget that implements this interface is a public source of DoubleClick
   * events.
   */
  public static interface Source {

    /**
     * Adds a DoubleClickEvent handler.
     */
    HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler);
  }

  public static Key<DoubleClickHandler> KEY = new Key<DoubleClickHandler>(
      BrowserEvents.ONDBLCLICK);

  public DoubleClickEvent(Event e) {
    super(e);
  }

  protected void fireEvent(DoubleClickHandler handler) {
    handler.onDoubleClick(this);
  }

  protected AbstractEvent.Key getKey() {
    return KEY;
  }
}