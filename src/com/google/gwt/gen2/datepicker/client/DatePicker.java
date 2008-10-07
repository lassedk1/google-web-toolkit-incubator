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

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.commonwidget.client.impl.StandardCssImpl;
import com.google.gwt.gen2.event.logical.shared.HasHighlightHandlers;
import com.google.gwt.gen2.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.gen2.event.logical.shared.HasShowRangeHandlers;
import com.google.gwt.gen2.event.logical.shared.HighlightEvent;
import com.google.gwt.gen2.event.logical.shared.HighlightHandler;
import com.google.gwt.gen2.event.logical.shared.SelectionEvent;
import com.google.gwt.gen2.event.logical.shared.SelectionHandler;
import com.google.gwt.gen2.event.logical.shared.ShowRangeEvent;
import com.google.gwt.gen2.event.logical.shared.ShowRangeHandler;
import com.google.gwt.gen2.event.shared.HandlerRegistration;
import com.google.gwt.gen2.widgetbase.client.Gen2Composite;
import com.google.gwt.gen2.widgetbase.client.Gen2CssInjector;
import com.google.gwt.gen2.widgetbase.client.WidgetCss;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Standard GWT date picker.
 */
public class DatePicker extends Gen2Composite implements
    HasSelectionHandlers<Date>, HasHighlightHandlers<Date>,
    HasShowRangeHandlers<Date>, HasValue<Date> {
  /**
   * Css interface for DatePicker.
   */
  public interface Css extends WidgetCss {
    /**
     * Widget style name.
     * 
     * @return the widget's style name
     */
    @ClassName("gwt-DatePicker")
    String datePicker();

    /**
     * Day style.
     * 
     * @return the style
     */
    String datePickerDay();

    /**
     * Disabled day style.
     * 
     * @return the style
     */
    String datePickerDayIsDisabled();

    /**
     * Filler day style.
     * 
     * @return the style
     */
    String datePickerDayIsFiller();

    /**
     * Highlighted day style.
     * 
     * @return the style
     */
    String datePickerDayIsHighlighted();

    /**
     * Selected day style.
     * 
     * @return the style
     */
    String datePickerDayIsSelected();

    /**
     * Selected and highlighted day style.
     * 
     * @return the style
     */
    String datePickerDayIsSelectedAndHighlighted();

    /**
     * Today's day style.
     * 
     * @return style
     */
    String datePickerDayIsToday();

    /**
     * Weekend day style.
     * 
     * @return the style
     */
    String datePickerDayIsWeekend();

    /**
     * Days container style.
     * 
     * @return the style
     */
    String datePickerDays();

    /**
     * Label for months style.
     * 
     * @return the style
     */
    String datePickerMonth();

    /**
     * Month selector style.
     * 
     * @return the style
     */
    String datePickerMonthSelector();

    /**
     * The navigation next button style.
     * 
     * @return the style
     */
    String datePickerNextButton();

    /**
     * The navigation previous button style.
     * 
     * @return the type
     */
    String datePickerPreviousButton();

    /**
     * Label for weekdays style.
     * 
     * @return the style
     */
    String datePickerWeekdayLabel();

    /**
     * Label for weekends style.
     * 
     * @return the style
     */
    String datePickerWeekendLabel();
  }

  /**
   * Convenience class to group css handling code.
   */
  static class StandardCss extends StandardCssImpl implements Css {
    /**
     * CSS resource for {@link WrappedDropDownListBox}.
     */
    static interface Resources extends ImmutableResourceBundle {
      @Resource("com/google/gwt/gen2/widgetbase/public/DatePicker.css")
      Css css();
    }

    static Css DEFAULT = createCss("gwt-DatePicker");

    static void injectCss() {
      if (Gen2CssInjector.isInjectionEnabled()) {
        Css css = ((Resources) GWT.create(Resources.class)).css();
        DEFAULT = Gen2CssInjector.inject(css);
      }
    }

    public StandardCss(String widgetName, String baseStyleName) {
      super(widgetName, baseStyleName);
    }

    public String datePicker() {
      return getWidgetStyleName();
    }

    public String datePickerDay() {
      return wrap("Day");
    }

    public String datePickerDayIsDisabled() {
      return day("Disabled");
    }

    public String datePickerDayIsFiller() {
      return day("Filler");
    }

    public String datePickerDayIsHighlighted() {
      return day("Highlighted");
    }

    public String datePickerDayIsSelected() {
      return day("Selected");
    }

    public String datePickerDayIsSelectedAndHighlighted() {
      return datePickerDayIsSelected() + "AndHighlighted";
    }

    public String datePickerDayIsToday() {
      return day("Today");
    }

    public String datePickerDayIsWeekend() {
      return day("Weekend");
    }

    public String datePickerDays() {
      return wrap("Days");
    }

    public String datePickerMonth() {
      return wrap("Month");
    }

    public String datePickerMonthSelector() {
      return wrap("MonthSelector");
    }

    public String datePickerNextButton() {
      return wrap("NextButton");
    }

    public String datePickerPreviousButton() {
      return wrap("PreviousButton");
    }

    public String datePickerWeekdayLabel() {
      return wrap("WeekdayLabel");
    }

    public String datePickerWeekendLabel() {
      return wrap("WeekendLabel");
    }

    public String day(String dayModifier) {
      return datePickerDay() + "Is" + dayModifier;
    }

    public String daysLabel() {
      return wrap("DaysLabel");
    }
  }

  private class DateStyler {
    private Map<String, String> info = new HashMap<String, String>();

    public String getStyleName(Date d) {
      return info.get(genKey(d));
    }

    public void setStyleName(Date d, String styleName, boolean add) {
      // Code is easier to maintain if surrounded by " ", and on all browsers
      // this is a no-op.
      styleName = " " + styleName + " ";
      String key = genKey(d);
      String current = info.get(key);

      if (add) {
        if (current == null) {
          info.put(key, styleName);
        } else if (current.indexOf(styleName) == -1) {
          info.put(key, current + styleName);
        }
      } else {
        assert current != null : "Removing style " + styleName + " from date "
            + d + " but the style name wasn't there";

        String newValue = current.replaceAll(styleName, "");
        if (newValue.trim().length() == 0) {
          info.remove(key);
        } else {
          info.put(key, newValue);
        }
      }
    }

    private String genKey(Date d) {
      return d.getYear() + "/" + d.getMonth() + "/" + d.getDate();
    }
  }

  /**
   * 
   * Creates a {@link Css} instance with the given style name. Note, this does
   * not change the base name used for auxiliary styles. So, for instance,
   * create("myDatePicker") would replace the default "gwt-DatePicker" with
   * "myDatePicker" but would not effect the style "datePickerMonthSelector".
   * 
   * @param styleName widget's style name.
   * @return the created css
   */
  public static Css createCss(String styleName) {
    return new StandardCss(styleName, "datePicker");
  }

  /**
   * Injects the default css for {@link DatePicker}.
   */
  public static void injectDefaultCss() {
    StandardCss.injectCss();
  }

  /**
   * Sets the default css for {@link DatePicker}.
   * 
   * @param css the css.
   */
  public static void setDefaultCss(Css css) {
    StandardCss.DEFAULT = css;
  }

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
        new CalendarModel(), StandardCss.DEFAULT);
  }

  /**
   * Constructor.
   * 
   * @param css the css to use with this date picker
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
   * @param css the css to use
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
    addGlobalStyleToDate(new Date(), css().datePickerDayIsToday());
  }

  /**
   * Globally adds a style name to a date. i.e. the style name is associated
   * with the date each time it is rendered.
   * 
   * @param date date
   * @param styleName style name
   */
  public void addGlobalStyleToDate(Date date, String styleName) {
    styler.setStyleName(date, styleName, true);
    if (isDateVisible(date)) {
      calendar.addStyleToDate(date, styleName);
    }
  }

  public HandlerRegistration addHighlightHandler(HighlightHandler<Date> handler) {
    return addHandler(HighlightEvent.KEY, handler);
  }

  public HandlerRegistration addSelectionHandler(SelectionHandler<Date> handler) {
    return addHandler(SelectionEvent.KEY, handler);
  }

  public HandlerRegistration addShowRangeHandler(ShowRangeHandler<Date> handler) {
    return addHandler(ShowRangeEvent.KEY, handler);
  }

  /**
   * Adds a show range handler and immediately activate the handler on the
   * current calendar view.
   * 
   * @param handler the handler
   * @return the handler registration
   */
  public HandlerRegistration addShowRangeHandlerAndFire(
      ShowRangeHandler<Date> handler) {
    handler.onShowRange(new ShowRangeEvent(calendar.getFirstDate(),
        calendar.getLastDate()));
    return addShowRangeHandler(handler);
  }

  /**
   * Shows the given style name on the specified date. This is only set until
   * the next time the DatePicker is refreshed.
   * 
   * @param visibleDate current visible date
   * @param styleName style name
   */
  public final void addStyleToVisibleDate(Date visibleDate, String styleName) {
    calendar.addStyleToDate(visibleDate, styleName);
  }

  /**
   * Adds a style name on a set of currently visible dates. This is only set
   * until the next time the DatePicker is refreshed.
   * 
   * @param visibleDates dates that will have the supplied style removed
   * @param styleName style name to remove
   */
  public final void addStyleToVisibleDates(Iterable<Date> visibleDates,
      String styleName) {
    getCalendarView().addStyleToDates(visibleDates, styleName);
  }

  /**
   * Gets the {@link Css} associated with this date picker.
   * 
   * @return the css.
   */
  public final Css css() {
    return css;
  }

  /**
   * Gets the currently shown date.
   * 
   * @return the current date shown
   */
  public Date getDateShown() {
    return getModel().getCurrentMonth();
  }

  /**
   * Gets the global style name associated with a date.
   * 
   * @param date the date
   * @return the styles associated with this date
   */
  public String getGlobalStyleOfDate(Date date) {
    return styler.getStyleName(date);
  }

  /**
   * Gets the highlighted date, if any.
   * 
   * @return the highlighted date
   */
  public final Date getHighlightedDate() {
    return highlightedDate;
  }

  /**
   * Gets the selected date, if any.
   * 
   * @return the selected date
   */
  public final Date getValue() {
    return selectedDate;
  }

  /**
   * Is the date currently shown in the date picker?
   * 
   * @param date
   * @return is the date currently shown
   */
  public boolean isDateVisible(Date date) {
    return calendar.isDateVisible(date);
  }

  /**
   * Is the visible date enabled?
   * 
   * @param date the date
   * @return is the date enabled?
   */
  public boolean isVisibleDateEnabled(Date date) {
    assert isDateVisible(date) : date + " is not visible";
    return calendar.isDateEnabled(date);
  }

  /**
   * Globally removes a style from a date.
   * 
   * @param date date
   * @param styleName style name
   */
  public void removeGlobalStyleFromDate(Date date, String styleName) {
    styler.setStyleName(date, styleName, false);
    if (isDateVisible(date)) {
      calendar.removeStyleFromDate(date, styleName);
    }
  }

  /**
   * Removes a style name from multiple visible dates.
   * 
   * @param dates dates that will have the supplied style removed
   * @param styleName style name to remove
   */
  public final void removeStyleFromVisibleDates(Iterator<Date> dates,
      String styleName) {
    while (dates.hasNext()) {
      Date date = (Date) dates.next();
      assert (isDateVisible(date)) : date + " should be visible";
      calendar.removeStyleFromDate(date, styleName);
    }
  }

  /**
   * Selects the current highlighted date.
   */
  public final void selectHighlightedDate() {
    setValue(getHighlightedDate());
  }

  /**
   * Restricts the date range this picker can show.
   * <p>
   * (Note: not yet implemented for default case).
   * </p>
   * 
   * @param from from date
   * @param to date
   */
  public final void setAllowableDates(Date from, Date to) {
    monthSelector.setAllowableDateRange(from, to);
  }

  /**
   * Sets a visible date to be enabled or disabled. This is only set until the
   * next time the DatePicker is refreshed.
   * 
   * @param date the date
   * @param enabled is enabled
   */
  public final void setEnabledOnVisibleDate(Date date, boolean enabled) {
    assert isDateVisible(date) : date
        + " cannot be enabled or disabled as it is not visible";
    getCalendarView().setDateEnabled(date, enabled);
  }

  /**
   * Sets a group of visible dates to be enabled or disabled. This is only set
   * until the next time the DatePicker is refreshed.
   * 
   * @param dates the dates
   * @param enabled is enabled
   */
  public final void setEnabledOnVisibleDates(Iterable<Date> dates,
      boolean enabled) {
    getCalendarView().setDatesEnabled(dates, enabled);
  }

  /**
   * Sets the date picker style name. Note, this does not change the base name
   * used for auxiliary styles. So, for instance, setStyleName("myDatePicker")
   * would replace the default "gwt-DatePicker", but would not effect
   * "datePickerMonthSelector".
   * 
   */
  @Override
  public void setStyleName(String styleName) {
    css = createCss(styleName);
    super.setStyleName(styleName);
  }

  /**
   * Sets the selected date.
   * 
   * @param date the new selected date
   */
  public final void setValue(Date date) {
    setValue(date, true);
  }

  /**
   * Sets the selected date.
   * 
   * @param newSelected the new selected date
   * @param fireEvents should events be fired.
   */
  public final void setValue(Date newSelected, boolean fireEvents) {
    Date oldSelected = selectedDate;

    if (oldSelected != null) {
      removeGlobalStyleFromDate(oldSelected, css().datePickerDayIsSelected());
    }

    selectedDate = CalendarUtil.copyDate(newSelected);
    if (selectedDate != null) {
      addGlobalStyleToDate(selectedDate, css().datePickerDayIsSelected());
    }

    if (fireEvents && isEventHandled(SelectionEvent.KEY)) {
      fireEvent(new SelectionEvent<Date>(oldSelected, newSelected));
    }
  }

  /**
   * Shows the given date.
   * 
   * @param date the date to show
   */
  public final void showDate(Date date) {
    getModel().setCurrentMonth(date);
    refreshAll();
  }

  /**
   * Gets the {@link CalendarView} associated with this date picker.
   * 
   * @return calendar view
   */
  protected final CalendarView getCalendarView() {
    return calendar;
  }

  /**
   * Gets the {@link CalendarModel} associated with this date picker.
   * 
   * @return the model
   */
  protected final CalendarModel getModel() {
    return model;
  }

  /**
   * Gets the {@link MonthSelector} associated with this date picker.
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
    setStyleName(panel.getElement(), css.datePicker());
    setStyleName(css().datePicker());
    panel.add(this.getMonthSelector());
    panel.add(this.getCalendarView());
  }

  /**
   * Refreshes all components of this date picker.
   */
  final void refreshAll() {
    highlightedDate = null;
    calendar.refresh();
    monthSelector.refresh();
    if (isEventHandled(ShowRangeEvent.KEY)) {
      fireEvent(new ShowRangeEvent<Date>(getCalendarView().getFirstDate(),
          getCalendarView().getLastDate()));
    }
  }

  /**
   * Sets the highlighted date.
   * 
   * @param highlightedDate highlighted date
   */
  void setHighlightedDate(Date highlightedDate) {
    this.highlightedDate = highlightedDate;
    if (isEventHandled(HighlightEvent.KEY)) {
      fireEvent(new HighlightEvent<Date>(highlightedDate));
    }
  }

  final void setModel(CalendarModel model) {
    this.model = model;
  }
}
