/*
 * Copyright 2009 Google Inc.
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
package com.google.gwt.gen2.commonevent.shared;

import com.google.gwt.event.logical.shared.HasHandlers;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents an event that should fire after close.
 * 
 * @param <T> the type being closed
 */
public class BeforeCloseEvent<T> extends GwtEvent<BeforeCloseHandler<T>> {
  
  /**
   * Fires an beforeClose event on all registered handlers in the handler manager.
   * If no such handlers exist, this method will do nothing.
   * 
   * @param <T> the target type
   * @param <S> The event source
   * @param source the source of the handlers
   * @param target the target
   */
  public static <T, S extends HasBeforeCloseHandlers<T> & HasHandlers> void fire(
      S source, T target) {
    if (TYPE != null) {
      BeforeCloseEvent<T> event = new BeforeCloseEvent<T>(target);
      source.fireEvent(event);
    }
  }
  
  public static Type<BeforeCloseHandler<?>> getType() {
    if (TYPE == null) {
      TYPE = new Type<BeforeCloseHandler<?>>();
    }
    return TYPE;
  }
  
  /**
   * Gets the target.
   * 
   * @return the target
   */
  public T getTarget() {
    return target;
  }
  
  @Override
  protected void dispatch(BeforeCloseHandler<T> handler) {
    handler.onBeforeClose(this);
  }

  // Because of type erasure, our static type is
  // wild carded, yet the "real" type should use our I param.
  @SuppressWarnings("unchecked")
  @Override
  public final Type<BeforeCloseHandler<T>> getAssociatedType() {
    return (Type) TYPE;
  }
  
  /**
   * Creates a new before close event.
   * 
   * @param target the ui object being closed
   */
  protected BeforeCloseEvent(T target) {
    this.target = target;
  }
  
  private final T target;
  
  /**
   * Handler type.
   */
  private static Type<BeforeCloseHandler<?>> TYPE;
}
