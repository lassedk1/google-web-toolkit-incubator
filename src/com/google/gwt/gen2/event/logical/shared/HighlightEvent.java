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

/**
 * Represents a highlight event.
 * 
 * @param <Value> the value of the element highlighted
 */
public class HighlightEvent<Value> extends ValueEvent<Value> {
  /**
   * Event Key for Highlight.
   */
  public static final Key<HighlightEvent, HighlightHandler> KEY = new Key<HighlightEvent, HighlightHandler>() {
    @Override
    protected void fire(HighlightHandler handler, HighlightEvent event) {
      handler.onHighlight(event);
    }
  };

  /**
   * Constructs a HighlightEvent event.
   * 
   * @param value value highlighted
   */
  public HighlightEvent(Value value) {
    super(value);
  }

  @Override
  protected Key getKey() {
    return KEY;
  }
}
