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
 * Represents a show range event.
 * 
 * @param <Value> the type of value shown in the range
 */
public class ShowRangeEvent<Value> extends AbstractEvent {

  /**
   * Event Key for ShowRange.
   */
  public static final Key<ShowRangeEvent, ShowRangeHandler> KEY = new Key<ShowRangeEvent, ShowRangeHandler>() {
    @Override
    protected void fire(ShowRangeHandler handler, ShowRangeEvent event) {
      handler.onShowRange(event);
    }
  };
  private Value start;
  private Value end;

  /**
   * Constructs a ShowRangeEvent event.
   */
  public ShowRangeEvent(Value start, Value end) {
    this.start = start;
    this.end = end;
  }

  /**
   * Gets the end of the range.
   * 
   * @return range end
   */
  public Value getEnd() {
    return end;
  }

  /**
   * Gets the start of the range.
   * 
   * @return range start
   */
  public Value getStart() {
    return start;
  }

  @Override
  protected Key getKey() {
    return KEY;
  }
}
