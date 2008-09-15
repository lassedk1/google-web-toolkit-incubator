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
 * Logical event fired after the widget has selected a value.
 * 
 * @param <Value> the type of value the widget has selected
 */
public class SelectionEvent<Value> extends
    AbstractEvent<SelectionHandler<Value>> {

  /**
   * The event key.
   */
  public static final Key<SelectionHandler> KEY = new Key<SelectionHandler>();

  private Value oldValue;
  private Value newValue;

  /**
   * Constructor.
   * 
   * @param oldValue the old value
   * @param newValue the new value
   */

  public SelectionEvent(Value oldValue, Value newValue) {
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  /**
   * Returns the new value.
   */
  public Value getNewValue() {
    return newValue;
  }

  /**
   * Returns the old value.
   */
  public Value getOldValue() {
    return oldValue;
  }

  @Override
  public String toDebugString() {
    return super.toDebugString() + " old = " + oldValue + " new =" + newValue;
  }

  @Override
  protected void fireEvent(SelectionHandler<Value> handler) {
    handler.onSelection(this);
  }

  @Override
  protected Key getKey() {
    return KEY;
  }
}
