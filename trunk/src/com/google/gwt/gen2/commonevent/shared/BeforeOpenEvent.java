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
import com.google.gwt.event.shared.HandlerManager;

/**
 * Represents an event that should fire after open.
 * When a tree item receives a request to open for the first time the
 * isFirstTime flag will be set.
 * 
 * @param <T> the type being opened
 */
public class BeforeOpenEvent<T> extends GwtEvent<BeforeOpenHandler<T>> {

  /**
   * Fires a beforeOpen event on all registered handlers in the handler manager.
   * If no such handlers exist, this method will do nothing.
   * 
   * @param <T> the target type
   * @param <S> The event source
   * @param source the source of the handlers
   * @param target the target
   */
  public static <T, S extends HasBeforeOpenHandlers<T> & HasHandlers> void fire(
      S source, T target, boolean isFirstTime) {
    if (TYPE != null) {
      HandlerManager handlers = source.getHandlers();
      if (handlers != null) {
        BeforeOpenEvent<T> event = new BeforeOpenEvent<T>(target, isFirstTime);
        handlers.fireEvent(event);        
      }
    }
  }
  
  public static Type<BeforeOpenHandler<?>> getType() {
    if (TYPE == null) {
      TYPE = new Type<BeforeOpenHandler<?>>();
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
  
  public void setFirstTime(boolean isFirstTime) {
    this.isFirstTime = isFirstTime;
  }

  public boolean isFirstTime() {
    return isFirstTime;
  }

  @Override
  protected void dispatch(BeforeOpenHandler<T> handler) {
    handler.onBeforeOpen(this);
  }

  // Because of type erasure, our static type is
  // wild carded, yet the "real" type should use our I param.
  @SuppressWarnings("unchecked")
  @Override
  public final Type<BeforeOpenHandler<T>> getAssociatedType() {
    return (Type) TYPE;
  }

  /**
   * Creates a new before open event.
   * 
   * @param target the ui object being opened
   * @param isFirstTime 
   */
  protected BeforeOpenEvent(T target, boolean isFirstTime) {
    this.target = target;
    this.isFirstTime = isFirstTime;
  }
  
  private final T target;
  
  /**
   * Handler type.
   */
  private static Type<BeforeOpenHandler<?>> TYPE;
  
  /**
   * Flag to keep track of the first time @FastTreeItem has been opened.
   */
  private boolean isFirstTime;
}
