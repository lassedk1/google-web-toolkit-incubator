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

package com.google.gwt.widgetideas.datepicker.client.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.widgetideas.datepicker.client.overrides.DateTimeConstants;

import java.util.Date;

/**
 * Model used to get locale info.
 */
public class CalendarModel {

  public static final int WEEKS_IN_MONTH = 6;
  public static final int DAYS_IN_WEEK = 7;

  private static final DateTimeConstants intlConstants = (DateTimeConstants) GWT.create(DateTimeConstants.class);

  /**
   * dayOfWeekNames is kept as strings because used only once for initial
   * drawing.
   */
  private static final String[] dayOfWeekNames = new String[7];

  private static final DateTimeFormat dayOfMonthFormatter = DateTimeFormat.getFormat("d");

  private static final DateTimeFormat yearFormatter = DateTimeFormat.getFormat("yyyy");

  private static final DateTimeFormat monthFormatter = DateTimeFormat.getFormat("MMM");

  private static final DateTimeFormat dayOfWeekFormatter = DateTimeFormat.getFormat("ccccc");

  private static final DateTimeFormat monthAndYearFormatter = DateTimeFormat.getFormat("MMMM yyyy");

  private static final long MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;

  public static int diffDates(Date a, Date b) {
    long aTime = a.getTime();
    long bTime = b.getTime();

    long adjust = 60 * 60 * 1000;
    adjust = (bTime > aTime) ? adjust : -adjust;

    int diff = (int) ((bTime - aTime + adjust) / (24 * 60 * 60 * 1000));

    return diff;
  }

  private static boolean computeYearBeforeMonth() {
    // Finding whether year is before month
    String[] dateFormats = intlConstants.dateFormats();
    String dateLongFormat = dateFormats[3];

    int yIndex = dateLongFormat.indexOf("y");
    int mIndex = dateLongFormat.indexOf("M");

    return (yIndex < mIndex);
  }

  /**
   * Public static method getDateAtDayStart() return the Date object with time
   * set to 00:00:00. Keeping a fixed the time of day intended to make it easier
   * to find day differences of dates that are initiated to different times of
   * the day.
   * 
   * @return new Date object
   */
  private static Date createDateWithoutTime() {
    Date date = new Date();
    resetTime(date);
    return date;
  }

  /**
   * Public static method getDateAtMonthStart() return the Date object with date
   * set to 1 and time set to 00:00:00.
   * 
   * @return new Date object
   */
  private static Date createFirstDayOfMonth() {
    Date date = createDateWithoutTime();
    date.setDate(1);
    return date;
  }

  private static void resetTime(Date date) {
    long msec = date.getTime();
    msec = (msec / 1000) * 1000;
    date.setTime(msec);

    date.setHours(0);
    date.setMinutes(0);
    date.setSeconds(0);
  }

  private final Date curMonthAndYear = createFirstDayOfMonth();
  private final boolean isYearBeforeMonth = isYearBeforeMonth();

  private int firstDayOfWeekend;

  private int lastDayOfWeekend;

  private int dayOfWeekStart;

  private String[] dayOfMonthNames = new String[32];

  private int numDaysInMonth;

  public CalendarModel() {
    // Finding day of week names
    Date date = new Date();
    for (int i = 1; i <= 7; i++) {
      date.setDate(i);
      int dayOfWeek = date.getDay();
      dayOfWeekNames[dayOfWeek] = dayOfWeekFormatter.format(date);
    }

    // Finding day of month names
    date.setMonth(0);

    for (int i = 1; i < 32; ++i) {
      date.setDate(i);
      dayOfMonthNames[i] = dayOfMonthFormatter.format(date);
    }

    // Finding the start and end of weekend
    firstDayOfWeekend = Integer.parseInt(intlConstants.weekendRange()[0]) - 1;
    lastDayOfWeekend = Integer.parseInt(intlConstants.weekendRange()[1]) - 1;
  }

  public Date createDate(int dayOfMonth) {
    Date d = new Date(curMonthAndYear.getTime());
    d.setDate(dayOfMonth);
    return d;
  }

  public String formatDayOfMonth(Date date) {
    return dayOfMonthNames[date.getDate()];
  }

  public String formatDayOfMonth(int date) {
    return dayOfMonthNames[date];
  }

  public String formatDayOfWeek(int i) {
    return dayOfWeekNames[i];
  }

  public String formatMonth() {
    return monthAndYearFormatter.format(curMonthAndYear);
  }

  public String formatYear() {
    return yearFormatter.format(curMonthAndYear);
  }

  public Date getFirstDayOfFirstWeek() {
    if (getStartingDayOfWeek() == getLocaleStartingDayOfWeek()) {
      return curMonthAndYear;
    } else {
      int dayInMonth = computeDaysInMonth(-1) - (getStartingDayOfWeek() - 1);
      Date d = createDate(dayInMonth);
      shiftMonths(d, -1);
      return d;
    }
  }

  /**
   * Public method weekStart() returns the day of the week on which week starts
   * as per the locale. The range between 0 for Sunday and 6 for Saturday.
   * 
   * @return the day of the week on which week starts as per the locale.
   */
  public int getLocaleStartingDayOfWeek() {
    return Integer.parseInt(intlConstants.firstDayOfTheWeek()) - 1;
  }

  public int getMonth() {
    return curMonthAndYear.getMonth();
  }

  public Date getMonthAndYear() {
    return curMonthAndYear;
  }

  /**
   * Public method currMonthSize() returns size of the current month.
   * 
   * @return number of days in the current month
   */
  public int getNumberOfDaysInMonth() {
    return numDaysInMonth;
  }

  public int getStartingDayOfWeek() {
    return dayOfWeekStart;
  }

  public int getYear() {
    return curMonthAndYear.getYear();
  }

  public boolean isWeekend(int dayOfWeek) {
    return dayOfWeek == firstDayOfWeekend || dayOfWeek == lastDayOfWeekend;
  }

  /**
   * Public method isYearBeforeMonth() returns whether the year is before month
   * in the current locale or not.
   * 
   * @return returns whether the year is before month in the current locale or
   *         not.
   */
  public boolean isYearBeforeMonth() {
    return isYearBeforeMonth;
  }

  public void setMonthAndYear(Date currentMonthAndYear) {
    this.curMonthAndYear.setYear(currentMonthAndYear.getYear());
    this.curMonthAndYear.setMonth(currentMonthAndYear.getDate());
    refresh();
  }

  public void shiftDays(Date date, int i) {
    long offset = MILLISECONDS_IN_DAY * i;
    date.setTime(date.getTime() + offset);
  }

  /**
   * Public method addMonths() add a positive or negative number to the date.
   * The day of the month will be pinned to the original value as far as
   * possible.
   * 
   * @param deltaMonths - number of months to be added to the current date
   */
  public void shiftMonths(int deltaMonths) {
    shiftMonths(curMonthAndYear, deltaMonths);
    refresh();
  }

  /**
   * Private method getNumberOfDaysInMonth() gets number of days in the month
   * indicated.
   * 
   * @param deltaMonth - the position of the month with respect to current month
   * 
   * @return number of days in the month
   */

  private int computeDaysInMonth(int deltaMonth) {
    int month = curMonthAndYear.getMonth();
    int year = curMonthAndYear.getYear();

    Date a = DatePickerDate.getDateAtMonthStart();
    Date b = DatePickerDate.getDateAtMonthStart();

    int resultMonthCount = year * 12 + month + deltaMonth;

    int aYear = resultMonthCount / 12;
    int aMonth = resultMonthCount - aYear * 12;

    resultMonthCount++;

    int bYear = resultMonthCount / 12;
    int bMonth = resultMonthCount - bYear * 12;

    a.setMonth(aMonth);
    a.setYear(aYear);

    b.setMonth(bMonth);
    b.setYear(bYear);

    int diff = diffDates(a, b);

    return (diff >= 0) ? diff : -diff;
  }

  private void refresh() {
    int weekStart = this.getLocaleStartingDayOfWeek();
    int dayOfWeek = curMonthAndYear.getDay();
    int date = curMonthAndYear.getDate();
    int month = curMonthAndYear.getMonth();
    int year = curMonthAndYear.getYear();
    // offset from Sunday == 0; +70 to make number +ve
    int offset = (dayOfWeek - date + 1 - weekStart + 70) % 7;
    numDaysInMonth = computeDaysInMonth(0);
    dayOfWeekStart = offset;
  }

  private void shiftMonths(Date date, int deltaMonths) {
    if (deltaMonths != 0) {
      int month = date.getMonth();
      int year = date.getYear();

      int resultMonthCount = year * 12 + month + deltaMonths;
      int resultYear = resultMonthCount / 12;
      int resultMonth = resultMonthCount - resultYear * 12;

      date.setMonth(resultMonth);
      date.setYear(resultYear);
    }
  }

}
