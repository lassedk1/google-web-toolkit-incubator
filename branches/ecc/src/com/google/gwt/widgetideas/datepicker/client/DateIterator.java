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

import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Date Iterator.
 */
public class DateIterator implements Iterator<Date> {
  private Date startDate;
  private Date endDate;
  private Date currentDate;

  public DateIterator(Date startDate, Date endDate) {
    // normalize dates
    this.startDate = new Date(startDate.getYear(), startDate.getMonth(),
        startDate.getDate());
    this.endDate = new Date(endDate.getYear(), endDate.getMonth(),
        endDate.getDate());
  }

  public boolean hasNext() {
    Date next = nextDate(currentDate);
    return !endDate.before(next);
  }

  public Date next() {
    currentDate = nextDate(currentDate);
    if (endDate.before(currentDate)) {
      throw new NoSuchElementException();
    }
    return currentDate;
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

  private Date nextDate(Date date) {
    Date next;
    if (date != null) {

      int year = date.getYear();
      int month = date.getMonth();
      int day = date.getDate() + 1;

      if (day > 31) {
        month++;
        day = 1;
      }

      if (month > 11) {
        year++;
        month = 0;
      }
      next = new Date(year, month, day);
    } else {
      next = new Date(startDate.getYear(), startDate.getMonth(),
          startDate.getDate());
    }
    return next;
  }

}
