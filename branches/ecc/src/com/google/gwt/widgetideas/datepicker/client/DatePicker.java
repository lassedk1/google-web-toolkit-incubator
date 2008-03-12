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

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.datepicker.client.impl.CalendarModel;

import java.util.Date;

/**
 * Date Picker Widget. Use {@link AbstractDatePicker} if you wish to create a
 * new type of {@link DatePicker}, as {@link DatePicker} is the simple
 * Instantiation.
 */
public class DatePicker extends
    AbstractDatePicker<DatePicker, SimpleCalendarView, SimpleMonthSelector> {

  /**
   * Creates the default DatePicker, using a ClientDateCellOracle.
   */
  public DatePicker() {
    super(new SimpleMonthSelector(), new SimpleCalendarView(),
        new CalendarModel());
    initWidget(new VerticalPanel());
    setStyleName(Styles.DEFAULT);
    getWidget().add(this.getMonthSelector());
    showDate(new Date());
    getWidget().add(this.getCalendarView());
  }

  @Override
  protected VerticalPanel getWidget() {
    return (VerticalPanel) super.getWidget();
  }

}
