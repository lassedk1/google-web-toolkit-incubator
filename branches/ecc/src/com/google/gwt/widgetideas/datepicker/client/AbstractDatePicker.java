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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.AbstractEvent;
import com.google.gwt.widgetideas.datepicker.client.impl.CalendarModel;

import java.util.Date;
import java.util.Iterator;

/**
 * Abstract date picker.
 * 
 * @param <Impl> the actual implementation class
 * @param <View> the calendar view
 * @param <Selector> the month selector
 */
public abstract class AbstractDatePicker<Impl extends AbstractDatePicker, View extends CalendarView, Selector extends MonthSelector>
    extends Composite implements FiresChangeEvents<Date, Impl>,
    FiresHighlightChangeEvents<Date, Impl> {

  /**
   * Rendering event.
   */
  public class RenderingEvent extends AbstractEvent<Impl> {
    Date from;
    Date to;

    public RenderingEvent(Impl picker, Date from, Date to) {
      super(picker);
      this.from = from;
      this.to = to;
    }

    public Date getBeginDate() {
      return from;
    }

    public Date getEndDate() {
      return to;
    }
  }

  /**
   * Styles used for the DatePicker family of widgets.
   */
  public static class Styles {
    public static final String MONTH_SELECTOR = "monthSelector";
    public static final String MONTH_FORWARD = "month-forward";
    public static final String MONTH_BACKWARD = "month-backward";
    public static final String MONTH_LABEL = "month-label";
    public static final String DEFAULT = "gwt-DatePicker";
    public static final String WEEKEND = "weekend";
    public static final String FILLER = "filler";
    public static final String SELECTED = "selected";
    public static final String HIGHLIGHTED = "highlighted";
    public static final String MONTH_SELECTED = "month-selected";
    public static final String CALENDAR_VIEW = "calendarView";
    public static final String DAY_TITLE = "day-title";
    public static final String DATE_CELL = "date-cell";
  }

  int nextMonthDays;

  Handlers<ChangeEvent<Date, Impl>> changeHandlers = null;

  Handlers<ChangeEvent<Date, Impl>> highlightChangeHandlers = null;
  private Date highlightedDate;
  private Selector monthSelector;
  private View calendar;

  private CalendarModel model;
  private Handlers<RenderingEvent> renderingHandlers;

  private Date selectedDate;

  protected AbstractDatePicker(Selector monthSelector, View cellViewer,
      CalendarModel model) {
    this.setModel(model);
    this.monthSelector = monthSelector;
    monthSelector.setDatePicker(this);
    this.calendar = cellViewer;
    calendar.setDatePicker(this);

    calendar.setup();
    monthSelector.setup();
    showDate(new Date());
  }

  public final void addChangeHandler(Handler<ChangeEvent<Date, Impl>> handler) {
    changeHandlers = Handlers.add(changeHandlers, handler);
  }

  public final void addHighlightChangeHandler(
      Handler<ChangeEvent<Date, Impl>> handler) {
    highlightChangeHandlers = Handlers.add(highlightChangeHandlers, handler);
  }

  public final void addRenderingHandler(Handler<RenderingEvent> handler) {
    renderingHandlers = Handlers.add(renderingHandlers, handler);
  }

  /**
   * Returns the date of the highlighted cell.
   */
  public final Date getHighlightedDate() {
    return highlightedDate;
  }

  /**
   * Gets the selected date.
   */
  public final Date getSelectedDate() {
    return selectedDate;
  }

  public final void refresh() {
    calendar.refresh();
    monthSelector.refresh();

    if (renderingHandlers != null) {
      RenderingEvent event = new RenderingEvent((Impl) this,
          calendar.getFirstVisibleDate(), calendar.getLastVisibleDate());
      renderingHandlers.fire(event);
    }
  }

  public final void removeChangeHandler(Handler<ChangeEvent<Date, Impl>> handler) {
    Handlers.remove(changeHandlers, handler);
  }

  public final void removeHighlightChangeHandler(
      Handler<ChangeEvent<Date, Impl>> handler) {
    Handlers.remove(highlightChangeHandlers, handler);
  }

  /**
   * Removes a Style Name from multiple dates.
   * 
   * @param dates dates that will have the supplied style removed
   * @param styleName style name to remove
   */
  public final void removeStyleName(Iterator dates, String styleName) {
    for (; dates.hasNext();) {
      Date date = (Date) dates.next();
      calendar.removeStyleName(date, styleName);
    }
  }

  public final void selectHighlightedDate() {
    setSelectedDate(getHighlightedDate());
  }

  /**
   * Method to limit the available range of months that will be selectable from
   * the MonthSelectorBar.
   */
  public final void setAllowableDates(Date from, Date to) {
    monthSelector.setAllowableDateRange(from, to);
  }

  /**
   * Sets the selected date.
   */
  public final void setSelectedDate(Date date) {
    Date old = selectedDate;
    if (changeHandlers != null) {
      AbstractEvent event = changeHandlers.fire(new ChangeEvent<Date, Impl>(
          (Impl) this, old, date));
      if (event.isCanceled()) {
        return;
      }
    }

    if (calendar.isVisible(old)) {
      calendar.removeStyleName(date, Styles.SELECTED);
    }
    if (calendar.isVisible(date)) {
      calendar.showStyleName(date, Styles.SELECTED);
    }
  }

  /**
   * This method causes the DatePicker to render a view containing the supplied
   * date.
   * 
   */
  public final void showDate(Date date) {
    getModel().setMonthAndYear(date);
    refresh();
  }

  public final void showEnabled(Date date, boolean enabled) {

  }

  /**
   * Shows the given style name on the specified date. TEMPORARY
   * 
   * @param date
   * @param styleName
   */
  public final void showStyleName(Date date, String styleName) {
    calendar.showStyleName(date, styleName);
  }

  /**
   * Gets the date view currently associated with this date picker. In the
   * {@link CalendarView} you can add styling to the dates currently displayed
   * in the view.
   * 
   * @return calendar view
   */
  protected final View getCalendarView() {
    return calendar;
  }

  protected final CalendarModel getModel() {
    return model;
  }

  protected final Widget getMonthSelector() {
    return monthSelector;
  }

  protected final void setModel(CalendarModel model) {
    this.model = model;
  }

  /**
   * Sets the highlighted date. Only {@link CalendarView} can set the
   * highlighted date.
   * 
   * @param highlightedDate selected date
   */
  void setHighlightedDate(Date highlightedDate) {
    Date old = this.highlightedDate;

    if (highlightChangeHandlers != null) {
      AbstractEvent event = highlightChangeHandlers.fire(new ChangeEvent<Date, Impl>(
          (Impl) this, old, highlightedDate));
      if (event.isCanceled()) {
        return;
      }
    }

    this.highlightedDate = highlightedDate;
  }
}
