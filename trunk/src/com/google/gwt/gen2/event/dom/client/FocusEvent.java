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

/**
 * Represents a native focus event.
 */
public class FocusEvent extends DomEvent {

  /**
    Event Key for Focus.
  */
  public static final Key<FocusEvent, FocusHandler> KEY = new Key<FocusEvent,FocusHandler>(
      Event.ONFOCUS) {
     @Override
     public void fire(FocusHandler handler, FocusEvent event) {
       handler.onFocus(event);
     }

     @Override     
    FocusEvent wrap(Event nativeEvent) {
       return new FocusEvent(nativeEvent);
     }
   };

  /**
   * Constructs a FocusEvent event.
   * 
   * @param nativeEvent the native event object wrapped by this event
   */
  public FocusEvent(Event nativeEvent) {
    super(nativeEvent);
  }
  
  @Override
  protected Key getKey() {
    return KEY;
  }
}
