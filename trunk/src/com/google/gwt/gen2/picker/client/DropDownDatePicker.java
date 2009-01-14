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
package com.google.gwt.gen2.picker.client;
 
import com.google.gwt.user.datepicker.client.CalendarModel;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.user.datepicker.client.DefaultCalendarView;

/**
 * A {@link DatePicker} that uses {@link DropDownListBox} for selecting month
 * and year.
 */
public class DropDownDatePicker extends DatePicker {
  /**
   * Creates a new <code>DropDownDatePicker</code>.
   */
  public DropDownDatePicker() {
    this(1950, 100);
  }

  /**
   * Creates a new <code>DropDownDatePicker</code>.
   * 
   * @param startYear
   * @param yearsToDisplay
   */
  public DropDownDatePicker(int startYear, int yearsToDisplay) {
    super(new DropDownMonthSelector(startYear, yearsToDisplay),
        new DefaultCalendarView(), new CalendarModel());
  }

  /**
   * Sets the first year to display in the year drop down.
   * 
   * @param startYear
   */
  public void setStartYear(final int startYear) {
    ((DropDownMonthSelector) getMonthSelector()).setStartYear(startYear);
  }

  /**
   * Sets the number of years to display in the year drop down.
   * 
   * @param yearsToDisplay
   */
  public void setYearsToDisplay(final int yearsToDisplay) {
    ((DropDownMonthSelector) getMonthSelector()).setYearsToDisplay(yearsToDisplay);
  }
}
