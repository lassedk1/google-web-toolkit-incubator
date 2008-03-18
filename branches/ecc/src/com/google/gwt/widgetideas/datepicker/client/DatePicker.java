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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.datepicker.client.events.ChangeEvent;
import com.google.gwt.widgetideas.datepicker.client.events.ChangeHandler;
import com.google.gwt.widgetideas.datepicker.client.events.FiresChangeEvents;
import com.google.gwt.widgetideas.datepicker.client.events.FiresHighlightEvents;
import com.google.gwt.widgetideas.datepicker.client.events.FiresRenderingEvents;
import com.google.gwt.widgetideas.datepicker.client.events.Handlers;
import com.google.gwt.widgetideas.datepicker.client.events.HighlightEvent;
import com.google.gwt.widgetideas.datepicker.client.events.HighlightHandler;
import com.google.gwt.widgetideas.datepicker.client.events.RenderingEvent;
import com.google.gwt.widgetideas.datepicker.client.events.RenderingHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Date picker.
 */
public class DatePicker extends Composite implements FiresChangeEvents<Date>,
    FiresHighlightEvents<Date>, FiresRenderingEvents {

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
    public static final String DAY = "day";
    public static final String DISABLED = "disabled";
    public static final String TODAY = "today";
  }

  private class DateStyler {
    private Map<String, String> info = new HashMap<String, String>();

    public String getStyleName(Date d) {
      return info.get(genKey(d));
    }

    public void setStyleName(Date d, String styleName, boolean add) {
      // TODO should use UIObject's code long term. This code therefore is
      // assuming good input and no StyleNames that are proper subsets of each
      // other.
      String key = genKey(d);

      String current = info.get(key);
      if (add) {
        if (current == null) {
          info.put(key, styleName);
        } else if (current.indexOf(styleName) == -1) {
          info.put(key, current + " " + styleName);
        }
      } else {

        if (current == null) {
          throw new IllegalStateException("Removing style " + styleName
              + " from date " + d + " but the stylename wasn't there");
        }
        String newValue = current.replaceAll(styleName, "");
        if (newValue.equals("")) {
          info.remove(key);
        } else {
          info.put(key, newValue);
        }
      }
    }

    private String genKey(Date d) {
      return getModel().createKeyFromDate(d);
    }
  }

  private Handlers<ChangeHandler<Date>> changeHandlers = null;
  private Handlers<HighlightHandler<Date>> highlightChangeHandlers = null;
  private Handlers<RenderingHandler> renderingHandlers = null;

  private DateStyler styler = new DateStyler();

  private Date highlightedDate;
  private MonthSelector monthSelector;
  private CalendarView calendar;
  private CalendarModel model;
  private Date selectedDate;

  /**
   * Constructor.
   */
  public DatePicker() {
    this(new SimpleMonthSelector(), new SimpleCalendarView(),
        new CalendarModel());
  }

  protected DatePicker(MonthSelector monthSelector, CalendarView cellViewer,
      CalendarModel model) {
    this.setModel(model);
    this.monthSelector = monthSelector;
    monthSelector.setDatePicker(this);
    this.calendar = cellViewer;
    calendar.setDatePicker(this);
    calendar.setup();
    monthSelector.setup();
    this.setup();
    showDate(new Date());
    addGlobalDateStyle(new Date(), Styles.TODAY);
  }

  public final void addChangeHandler(ChangeHandler<Date> handler) {
    changeHandlers = Handlers.add(changeHandlers, handler);
  }

  /**
   * Globally adds a style name to a date. i.e. the style name is associated
   * with the date each time it is rendered.
   * 
   * @param date date
   * @param styleName style name
   */
  public void addGlobalDateStyle(Date date, String styleName) {
    styler.setStyleName(date, styleName, true);
    calendar.addDateStyle(date, styleName);
  }

  public final void addHighlightHandler(HighlightHandler<Date> handler) {
    highlightChangeHandlers = Handlers.add(highlightChangeHandlers, handler);
  }

  public final void addRenderingHandler(RenderingHandler handler) {
    renderingHandlers = Handlers.add(renderingHandlers, handler);
  }

  /**
   * Shows the given style name on the specified date. This is only set until
   * the next time the DatePicker is refreshed.
   * 
   * @param date date
   * @param styleName style name
   */
  public final void addVisibleDateStyle(Date date, String styleName) {
    calendar.addDateStyle(date, styleName);
  }

  /**
   * Removes a style name from multiple dates.
   * 
   * @param dates dates that will have the supplied style removed
   * @param styleName style name to remove
   */
  public final void addVisibleDateStyles(Iterable<Date> dates, String styleName) {
    getCalendarView().addDateStyles(dates, styleName);
  }

  /**
   * Gets the currently shown date.
   */
  public Date getDateShown() {
    return getModel().getCurrentMonthAndYear();
  }

  /**
   * Gets the global style name associated with a date.
   */
  public String getGlobalDateStyle(Date d) {
    return styler.getStyleName(d);
  }

  /**
   * Gets the highlighted date, if any.
   */
  public final Date getHighlightedDate() {
    return highlightedDate;
  }

  /**
   * Gets the selected date, if any.
   */
  public final Date getSelectedDate() {
    return selectedDate;
  }

  /**
   * Is the date enabled?
   * 
   * @param date
   * @return is the date enabled?
   */
  public boolean isDateEnabled(Date date) {
    return calendar.isDateEnabled(date);
  }

  /**
   * Is the date currently shown in the date picker?
   * 
   * @param date
   * @return is the date currently shown
   */
  public boolean isDateVisible(Date date) {
    return calendar.isVisible(date);
  }

  public final void removeChangeHandler(ChangeHandler<Date> handler) {
    Handlers.remove(changeHandlers, handler);
  }

  /**
   * Globally removes a style from a date.
   * 
   * @param date date
   * @param styleName style name
   */
  public void removeGlobalDateStyle(Date date, String styleName) {
    styler.setStyleName(date, styleName, false);
    calendar.removeStyleName(date, styleName);
  }

  public final void removeHighlightHandler(HighlightHandler<Date> handler) {
    Handlers.remove(highlightChangeHandlers, handler);
  }

  public final void removeRenderingHandler(RenderingHandler handler) {
    Handlers.remove(renderingHandlers, handler);
  }

  /**
   * Removes a style name from multiple visible dates.
   * 
   * @param dates dates that will have the supplied style removed
   * @param styleName style name to remove
   */
  public final void removeVisibleDateStyles(Iterator<Date> dates,
      String styleName) {
    for (; dates.hasNext();) {
      Date date = (Date) dates.next();
      calendar.removeStyleName(date, styleName);
    }
  }

  /**
   * Selects the current highlighted date.
   */
  public final void selectHighlightedDate() {
    setSelectedDate(getHighlightedDate());
  }

  /**
   * Restricts the date range this picker can show.
   * <p>
   * (Note: not yet implemented for default case).
   * </p>
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
      ChangeEvent event = new ChangeEvent(this, old, date);
      changeHandlers.fire(event);
      if (event.isCanceled()) {
        return;
      }
    }
    if (old != null) {
      removeGlobalDateStyle(old, Styles.SELECTED);
    }
    addGlobalDateStyle(date, Styles.SELECTED);
    selectedDate = CalendarModel.copy(date);
  }

  /**
   * Sets a visible date to be enabled or disabled. This is only set until the
   * next time the DatePicker is refreshed.
   * 
   * @param date the date
   * @param enabled is enabled
   */
  public final void setVisibleDateEnabled(Date date, boolean enabled) {
    getCalendarView().setDateEnabled(date, enabled);
  }

  /**
   * Sets a group of visible dates to be enabled or disabled. This is only set
   * until the next time the DatePicker is refreshed.
   * 
   * @param dates the dates
   * @param enabled is enabled
   */
  public final void setVisibleEnabledDates(Iterable<Date> dates, boolean enabled) {
    getCalendarView().setEnabledDates(dates, enabled);
  }

  /**
   * This method causes the DatePicker to show the given date.
   */
  public final void showDate(Date date) {
    getModel().setCurrentMonthAndYear(date);
    refresh();
  }

  /**
   * Gets the date view currently associated with this date picker.
   * 
   * @return calendar view
   */
  protected final CalendarView getCalendarView() {
    return calendar;
  }

  /**
   * Gets the model associated with this date picker.
   * 
   * @return the model
   */
  protected final CalendarModel getModel() {
    return model;
  }

  /**
   * Gets the month selector associated with this date picker.
   * 
   * @return the month selector
   */
  protected final Widget getMonthSelector() {
    return monthSelector;
  }

  protected void setup() {
    FlowPanel panel = new FlowPanel();
    initWidget(panel);
    setStyleName(Styles.DEFAULT);
    panel.add(this.getMonthSelector());
    showDate(new Date());
    panel.add(this.getCalendarView());
  }

  final void refresh() {
    highlightedDate = null;
    calendar.refresh();
    monthSelector.refresh();

    if (renderingHandlers != null) {
      RenderingEvent event = new RenderingEvent((FiresRenderingEvents) this);
      renderingHandlers.fire(event);
    }
  }

  /**
   * Sets the highlighted date. Only {@link CalendarView} can set the
   * highlighted date.
   * 
   * @param highlightedDate selected date
   */
  void setHighlightedDate(Date highlightedDate) {

    if (highlightChangeHandlers != null) {
      HighlightEvent<Date> event = new HighlightEvent<Date>(this,
          highlightedDate);
      if (!highlightChangeHandlers.fire(event)) {
        return;
      }
    }
    this.highlightedDate = highlightedDate;
  }

  final void setModel(CalendarModel model) {
    this.model = model;
  }
}
