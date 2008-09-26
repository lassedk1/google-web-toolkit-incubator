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

import java.util.Date;

/**
 * The CalendarView is a calendar grid that represents the current view of a
 * {@link DatePicker}.
 * 
 */
public abstract class CalendarView extends DatePickerComponent {

  /**
   * Constructor.
   */
  public CalendarView() {
  }

  /**
   * Adds a style name to the cell of the supplied date. This style is only set
   * until the next time the {@link CalendarView} is refreshed.
   * 
   * @param date date that will have the supplied style added
   * @param styleName style name to add
   */
  public abstract void addVisibleDateStyle(Date date, String styleName);

  /**
   * Adds a style name to the cell of the supplied dates. This is only set until
   * the next time the {@link CalendarView} is refreshed.
   * 
   * @param date date that will have the supplied style added
   * @param styleName style name to add
   */
  public void addVisibleDateStyle(Iterable<Date> dates, String styleName) {
    for (Date date : dates) {
      addVisibleDateStyle(date, styleName);
    }
  }

  /**
   * Returns the first date that is currently shown by the calendar.
   */
  public abstract Date getFirstVisibleDate();

  /**
   * Returns the last date that is currently shown by the calendar.
   */
  public abstract Date getLastVisibleDate();

  /**
   * Is the cell representing the given date enabled?
   * 
   * @param date the date
   * @return is the date enabled
   */
  public abstract boolean isEnabled(Date d);

  /**
   * Is the cell representing the given date visible?
   * 
   * @param date the date
   * @return whether the date is visible
   */
  public boolean isVisible(Date date) {
    Date first = getFirstVisibleDate();
    Date last = getLastVisibleDate();
    return (date != null && (first.equals(date) || last.equals(date) || (first.before(date) && last.after(date))));
  }

  @Override
  public abstract void refresh();

  /**
   * Removes a visible style name from the cell of the supplied date.
   * 
   * @param date date that will have the supplied style added
   * @param styleName style name to remove
   */
  public abstract void removeVisibleStyleName(Date date, String styleName);

  /**
   * Enables or Disables a particular date. by default all valid dates are
   * enabled after a rendering event. Disabled dates cannot be selected.
   * 
   * @param date date to enable or disable
   * 
   * @param enabled true for enabled, false for disabled
   */
  public abstract void setEnabledDate(Date date, boolean enabled);

  /**
   * Enables or disables multiple dates.
   * 
   * @param dates dates to [en|dis]able
   * @param enabled true to enable, false to disable
   */
  public void setEnabledDates(Iterable<Date> dates, boolean enabled) {
    for (Date date : dates) {
      setEnabledDate(date, enabled);
    }
  }

  /**
   * Allows the calendar view to update the date picker's highlighted date.
   */
  protected final void setHighlightedDate(Date date) {
    getDatePicker().setHighlightedDate(date);
  }
}