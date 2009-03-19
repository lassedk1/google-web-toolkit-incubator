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
 * Represents a native mouse out event.
 */
public class MouseOutEvent extends MouseEvent {

  /**
    Event type for mouse out events. Represents the meta-data associated with this event.
  */
  public static final Type<MouseOutEvent, MouseOutHandler> TYPE = new Type<MouseOutEvent,MouseOutHandler>(
      Event.ONMOUSEOUT) {
     @Override
     public void fire(MouseOutHandler handler, MouseOutEvent event) {
       handler.onMouseOut(event);
     }

     @Override     
    MouseOutEvent wrap(Event nativeEvent) {
       return new MouseOutEvent(nativeEvent);
     }
   };

  /**
   * Constructor.
   * 
   * @param nativeEvent the native event object
   */
  public MouseOutEvent(Event nativeEvent) {
    super(nativeEvent);
  }
  
 @Override
  protected Type getType() {
    return TYPE;
  }

}