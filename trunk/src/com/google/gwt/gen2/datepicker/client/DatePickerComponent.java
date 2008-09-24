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

package com.google.gwt.gen2.datepicker.client;

import com.google.gwt.user.client.ui.Composite;

abstract class DatePickerComponent extends Composite {
  private DatePicker datePicker;

  public CalendarModel getModel() {
    return datePicker.getModel();
  }

  protected void addMonths(int numMonths) {
    getModel().shiftCurrentMonth(numMonths);
    getDatePicker().refresh();
  }

  protected DatePicker.Css css() {
    return datePicker.css();
  }

  protected DatePicker getDatePicker() {
    return datePicker;
  }

  /**
   * Refresh the component. Usually called because the model's current date has
   * changed.
   */
  protected abstract void refresh();

  /**
   * Set up the component.
   */
  protected abstract void setup();

  void setDatePicker(DatePicker me) {
    this.datePicker = me;
  }
}
