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

import com.google.gwt.gen2.widgetbase.client.WidgetCss;
import com.google.gwt.libideas.resources.client.CssResource.ClassName;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.event.ChangeEvent;
import com.google.gwt.widgetideas.client.event.ChangeHandler;
import com.google.gwt.widgetideas.client.event.EventHandlers;
import com.google.gwt.widgetideas.client.event.FiresChangeEvents;
import com.google.gwt.widgetideas.client.event.FiresHighlightEvents;
import com.google.gwt.widgetideas.client.event.FiresRenderingEvents;
import com.google.gwt.widgetideas.client.event.HighlightEvent;
import com.google.gwt.widgetideas.client.event.HighlightHandler;
import com.google.gwt.widgetideas.client.event.RenderingEvent;
import com.google.gwt.widgetideas.client.event.RenderingHandler;

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
   * GWT Date picker css.
   */
  public interface Css extends WidgetCss {
    @ClassName("gwt-DatePicker-day")
    public String day();

    @ClassName("gwt-DatePicker-days")
    public String days();

    @ClassName("gwt-DatePicker-dayTitle")
    public String dayTitle();

    @ClassName("gwt-DatePicker-disabledCell")
    public String disabledCell();

    @ClassName("gwt-DatePicker-fillerCell")
    public String fillerCell();

    @ClassName("gwt-DatePicker-highlightedCell")
    public String highlightedCell();

    @ClassName("gwt-DatePicker-nextButton")
    public String nextButton();

    @ClassName("gwt-DatePicker-previousButton")
    public String previousButton();

    @ClassName("gwt-DatePicker-selectedAndHighlightedCell")
    public String selectedAndHighlightedCell();

    @ClassName("gwt-DatePicker-selectedCell")
    public String selectedCell();

    @ClassName("gwt-DatePicker-title")
    public String title();

    @ClassName("gwt-DatePicker-todayCell")
    public String todayCell();

    @ClassName("gwt-DatePicker-weekendColumn")
    public String weekendColumn();
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

  private static class StandardCss extends
      com.google.gwt.gen2.commonwidget.client.impl.StandardCssImpl<DatePicker>
      implements Css {
    public StandardCss(String baseStyleName) {
      super(baseStyleName);
    }

    public String day() {
      return wrap("day");
    }

    public String days() {
      return wrap("days");
    }

    public String dayTitle() {
      return wrap("dayTitle");
    }

    public String disabledCell() {
      return wrap("disabledCell");
    }

    public String fillerCell() {
      return wrap("fillerCell");
    }

    public String highlightedCell() {
      return wrap("highlightedCell");
    }

    public String nextButton() {
      return wrap("nextButton");
    }

    public String previousButton() {
      return wrap("previousButton");
    }

    public String selectedAndHighlightedCell() {
      return wrap("selectedAndHighlightedCell");
    }

    public String selectedCell() {
      return wrap("selectedCell");
    }

    public String title() {
      return wrap("title");
    }

    public String todayCell() {
      return wrap("todayCell");
    }

    public String weekendColumn() {
      return wrap("weekendColumn");
    }
  }

  public static Css DEFAULT_CSS;

  private static Css ensureDefaultCss() {
    if (DEFAULT_CSS == null) {
      DEFAULT_CSS = new StandardCss("gwt-DatePicker");
    }
    return DEFAULT_CSS;
  }

  private EventHandlers handlers = new EventHandlers();

  private DateStyler styler = new DateStyler();
  private Date highlightedDate;
  private MonthSelector monthSelector;
  private CalendarView calendar;
  private CalendarModel model;
  private Date selectedDate;
  private Css css;

  /**
   * Constructor.
   */
  public DatePicker() {
    this(new DefaultMonthSelector(), new DefaultCalendarView(),
        new CalendarModel(), ensureDefaultCss());
  }

  /**
   * Constructor.
   */
  public DatePicker(Css css) {
    this(new DefaultMonthSelector(), new DefaultCalendarView(),
        new CalendarModel(), css);
  }

  /**
   * Constructor for use by subtypes.
   * 
   * @param monthSelector the month selector
   * @param calendarView the calendar view
   * @param model the calendar model
   */

  protected DatePicker(MonthSelector monthSelector, CalendarView calendarView,
      CalendarModel model, Css css) {
    this.setModel(model);
    this.css = css;
    this.monthSelector = monthSelector;
    monthSelector.setDatePicker(this);
    this.calendar = calendarView;
    calendar.setDatePicker(this);
    calendar.setup();
    monthSelector.setup();
    this.setup();
    showDate(new Date());
    addGlobalDateStyle(new Date(), css().todayCell());
  }

  public final void addChangeHandler(ChangeHandler<Date> handler) {
    handlers.add(ChangeEvent.class, handler);
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
    if (isDateVisible(date)) {
      calendar.addDateStyle(date, styleName);
    }
  }

  public final void addHighlightHandler(HighlightHandler<Date> handler) {
    handlers.add(HighlightEvent.class, handler);
  }

  public final void addRenderingHandler(RenderingHandler handler) {
    handlers.add(RenderingEvent.class, handler);

    // Render onto the current display.
    handler.onRendered(new RenderingEvent(this));
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

  public final Css css() {
    return css;
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
    handlers.remove(ChangeEvent.class, handler);
  }

  /**
   * Globally removes a style from a date.
   * 
   * @param date date
   * @param styleName style name
   */
  public void removeGlobalDateStyle(Date date, String styleName) {
    styler.setStyleName(date, styleName, false);
    if (isDateVisible(date)) {
      calendar.removeStyleName(date, styleName);
    }
  }

  public final void removeHighlightHandler(HighlightHandler<Date> handler) {
    handlers.remove(HighlightEvent.class, handler);
  }

  public final void removeRenderingHandler(RenderingHandler handler) {
    handlers.remove(RenderingEvent.class, handler);
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
    setSelectedDate(date, true);
  }

  /**
   * Sets the selected date.
   */
  public final void setSelectedDate(Date date, boolean fireEvents) {
    Date old = selectedDate;
    if (fireEvents && handlers.has(ChangeEvent.class)) {
      ChangeEvent event = new ChangeEvent(this, old, date);
      handlers.fire(event);
    }
    if (old != null) {
      removeGlobalDateStyle(old, css().selectedCell());
    }

    selectedDate = CalendarModel.copy(date);
    if (selectedDate != null) {
      addGlobalDateStyle(selectedDate, css().selectedCell());
    }
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

  /**
   * Sets up the date picker.
   */
  protected void setup() {
    FlowPanel panel = new FlowPanel();
    initWidget(panel);
    setStyleName(css().baseName());
    panel.add(this.getMonthSelector());
    panel.add(this.getCalendarView());
  }

  final void refresh() {
    highlightedDate = null;
    calendar.refresh();
    monthSelector.refresh();

    if (handlers.has(RenderingEvent.class)) {
      RenderingEvent event = new RenderingEvent((FiresRenderingEvents) this);
      handlers.fire(event);
    }
  }

  /**
   * Sets the highlighted date. Only {@link CalendarView} can set the
   * highlighted date.
   * 
   * @param highlightedDate selected date
   */
  void setHighlightedDate(Date highlightedDate) {
    if (handlers.has(HighlightEvent.class)) {
      HighlightEvent<Date> event = new HighlightEvent<Date>(this,
          highlightedDate);
      handlers.fire(event);
    }
    this.highlightedDate = highlightedDate;
  }

  final void setModel(CalendarModel model) {
    this.model = model;
  }
}
