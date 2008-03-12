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

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.widgetideas.datepicker.client.impl.CalendarModel;

import java.util.Date;

abstract class DatePickerComponent<MyDatePicker extends AbstractDatePicker>
    extends Composite {
  protected MyDatePicker datePicker;

  public CalendarModel getModel() {
    return datePicker.getModel();
  }

  protected void addMonths(int numMonths) {
    getModel().shiftMonths(numMonths);
    getDatePicker().refresh();
  }

  protected void fireHighlightChangeEvent(ChangeEvent<Date, MyDatePicker> event) {
    datePicker.highlightChangeHandlers.fire(event);
  }

  protected MyDatePicker getDatePicker() {
    return datePicker;
  }

  protected abstract void refresh();

  protected abstract void setup();

  void setDatePicker(MyDatePicker dp) {
    this.datePicker = dp;
  }

}
