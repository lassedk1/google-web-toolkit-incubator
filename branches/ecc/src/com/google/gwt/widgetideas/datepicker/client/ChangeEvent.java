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

package com.google.gwt.widgetideas.datepicker.client;

import com.google.gwt.widgetideas.client.AbstractEvent;

import java.io.Serializable;

/**
 * Change event
 * 
 * @param <Value> value type
 * @param <S> source type
 */
public class ChangeEvent<Value, S extends FiresChangeEvents> extends
    AbstractEvent<S> implements Serializable {
  private Value oldValue;
  private Value newValue;

  public ChangeEvent(S source, Value oldValue, Value newValue) {
    super(source);
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  public Value getNewValue() {
    return newValue;
  }

  public Value getOldValue() {
    return oldValue;
  }

}
