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
    HasShowRangeHandlers<Date> {
  /**
   * Css interface for DatePicker.
   */
  public interface Css extends WidgetCss {
    @ClassName("gwt-DatePicker")
    String datePicker();

    String datePickerDay();

    String datePickerDayIsDisabled();

    String datePickerDayIsFiller();

    String datePickerDayIsHighlighted();

    String datePickerDayIsSelected();

    String datePickerDayIsSelectedAndHighlighted();

    String datePickerDayIsToday();

    String datePickerDayIsWeekend();

    String datePickerDays();

    String datePickerMonth();

    String datePickerMonthSelector();

    String datePickerNextButton();

    String datePickerPreviousButton();

    String datePickerWeekdayLabel();

    String datePickerWeekendLabel();
  }

  /**
   * Convenience class to group css handling code.
   * 
   */
  static class StandardCss extends StandardCssImpl implements Css {
    /**
     * CSS resource for {@link WrappedDropDownListBox}.
     * 
     */
    static interface Resources extends ImmutableResourceBundle {
      @Resource("com/google/gwt/gen2/widgetbase/public/DatePicker.css")
      Css css();
    }

    /**
     * Default Css for {@link DatePicker}
     */
    static Css DEFAULT = createCss("gwt-DatePicker");

    static void injectCss() {
      if (Gen2CssInjector.isInjectionEnabled()) {
        Css css = ((Resources) GWT.create(Resources.class)).css();
        DEFAULT = Gen2CssInjector.inject(css);
      }
    }

    public StandardCss(String baseStyleName) {
      super(baseStyleName);
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
      return wrap("WeekdayLabel");
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
      return getModel().createKeyFromDate(d);
    }
  }

  /**
   * Creates a {@link Css} instance with the given widget and base name.
   * 
   * @param baseName base style name.
   */
  public static Css createCss(String baseName) {
    return new StandardCss(baseName);
  }

  /**
   * Injects the default css for DatePicker.
   */
  public static void injectDefaultCss() {

    StandardCss.injectCss();
  }

  /**
   * Sets the default css for {@link DatePicker}
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
    // Fire the handler at once to apply it to the current shown month.
    handler.onShowRange(new ShowRangeEvent(calendar.getFirstDate(),
        calendar.getLastDate()));
    // Add it as a handler.
    return addHandler(ShowRangeEvent.KEY, handler);
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
   * Adds a style name on multiple currently visible dates. This is only set
   * until the next time the DatePicker is refreshed.
   * 
   * @param visibleDates dates that will have the supplied style removed
   * @param styleName style name to remove
   */
  public final void addStyleToVisibleDates(Iterable<Date> visibleDates,
      String styleName) {
    getCalendarView().addStyleToDates(visibleDates, styleName);
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
  public String getGlobalStyleOfDate(Date d) {
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
    return calendar.isDateVisible(date);
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
    for (; dates.hasNext();) {
      Date date = (Date) dates.next();
      calendar.removeStyleFromDate(date, styleName);
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
   * Sets a visible date to be enabled or disabled. This is only set until the
   * next time the DatePicker is refreshed.
   * 
   * @param date the date
   * @param enabled is enabled
   */
  public final void setEnabledOnVisibleDate(Date date, boolean enabled) {
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
   * Sets the selected date.
   */
  public final void setSelectedDate(Date date) {
    setSelectedDate(date, true);
  }

  /**
   * Sets the selected date.
   */
  public final void setSelectedDate(Date newSelected, boolean fireEvents) {
    Date oldSelected = selectedDate;

    if (oldSelected != null) {
      removeGlobalStyleFromDate(oldSelected, css().datePickerDayIsSelected());
    }

    selectedDate = CalendarModel.copy(newSelected);
    if (selectedDate != null) {
      addGlobalStyleToDate(selectedDate, css().datePickerDayIsSelected());
    }

    if (fireEvents && isEventHandled(SelectionEvent.KEY)) {
      fireEvent(new SelectionEvent<Date>(oldSelected, newSelected));
    }
  }

  /**
   * Sets the date picker style name and all related style names.
   * <p>
   * So for style name "gwt-Foo", the widget's name would be "gwt-Foo" and the
   * month selector's name would be "fooMonthSelector". For style name "Bar",
   * the month selector would be "barMonthSelector".
   */
  @Override
  public void setStyleName(String styleName) {
    css = createCss(styleName);
    super.setStyleName(styleName);
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
    setStyleName(panel.getElement(), css.datePicker());
    setStyleName(css().datePicker());
    panel.add(this.getMonthSelector());
    panel.add(this.getCalendarView());
  }

  final void refresh() {
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
