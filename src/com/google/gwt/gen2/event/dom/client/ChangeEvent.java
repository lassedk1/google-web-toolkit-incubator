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
 * Represents a native change event.
 */
public class ChangeEvent extends  DomEvent<ChangeHandler> {

 
  public static final Key<ChangeHandler> KEY = new Key<ChangeHandler>(
      NativeEventType.ONCHANGE);

  /**
   * Constructs a ChangeEvent event.
   * 
   * @param nativeEvent the native event object wrapped by this event
   */
  public ChangeEvent(Event e) {
    super(e);
  }
  
  /**
   * Fires a {@link ChangeEvent}.
   * 
   * @param handler the handler
   */
  @Override
  protected void fireEvent(ChangeHandler handler) {
    handler.onChange(this);
  }

  @Override
  protected Key getKey() {
    return KEY;
  }
}
