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
 * This synthetic event that is called after a widget hides its contents.
 */
public class AfterHideEvent extends AbstractEvent<AfterHideHandler> {
  /**
   * Convenience interface used to supply default add handler method.
   */
  public static interface Source {
    /**
     * Called after a widget hides its contents.
     */
    HandlerRegistration addAfterHideHandler(AfterHideHandler handler);
  }

  /**
   * The event key.
   */
  public static Key<AfterHideHandler> KEY = new Key<AfterHideHandler>();

  /**
   * Constructor.
   */
  public AfterHideEvent() {
  }

  @Override
  protected void fireEvent(AfterHideHandler handler) {
    handler.afterHide(this);
  }

  @Override
  protected Key getKey() {
    return KEY;
  }
}
