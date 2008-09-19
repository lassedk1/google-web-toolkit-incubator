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
package com.google.gwt.gen2.event.logical.shared;

import com.google.gwt.gen2.event.shared.AbstractEvent;

/**
 * Represents a logical before show event.
 */
public class BeforeShowEvent extends AbstractEvent {

  /**
   * Event Key for BeforeShow.
   */
  public static final Key<BeforeShowEvent, BeforeShowHandler> KEY = new Key<BeforeShowEvent, BeforeShowHandler>() {
    @Override
    protected void fire(BeforeShowHandler handler, BeforeShowEvent event) {
      handler.onBeforeShow(event);
    }
  };

  /**
   * Constructs a BeforeShowEvent event.
   */
  public BeforeShowEvent() {
  }

  @Override
  protected Key getKey() {
    return KEY;
  }
}
