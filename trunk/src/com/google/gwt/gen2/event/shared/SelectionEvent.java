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

package com.google.gwt.gen2.event.shared;

/**
 * This synthetic event is called after a value has been selected.
 * 
 * @param <Value> the type of value being updated.
 */
public class SelectionEvent<Value> extends
    AbstractEvent<SelectionHandler<Value>> {
  /**
   * Convenience interface used to supply default add handler method.
   * 
   */
  public static interface Source<ValueType> {
    /**
     * Adds a new selection handler
     * 
     * @param handler handler
     * @return the handler registration
     */
    HandlerRegistration addSelectionHandler(SelectionHandler<ValueType> handler);
  }

  /**
   * The event key.
   */
  public static Key<SelectionHandler> KEY = new Key<SelectionHandler>();

  private Value oldValue;

  private Value newValue;

  /**
   * Constructor.
   * 
   * @param oldValue old value
   * @param newValue new value
   */

  public SelectionEvent(Value oldValue, Value newValue) {
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  @Override
  public String getEventDetails() {
    return super.getEventDetails() + " old = " + oldValue + " new =" + newValue;
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
  protected void fireEvent(SelectionHandler<Value> handler) {
    handler.onSelection(this);
  }

  @Override
  protected Key getKey() {
    return KEY;
  }
}
