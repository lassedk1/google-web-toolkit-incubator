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
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.CssResource.ClassName;
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
    String baseName();

    @ClassName("gwt-DatePicker-day")
    String day();

    @ClassName("gwt-DatePicker-day-disabled")
    String dayIsDisabled();

    @ClassName("gwt-DatePicker-day-filler")
    String dayIsFiller();

    @ClassName("gwt-DatePicker-day-highlighted")
    String dayIsHighlighted();

    @ClassName("gwt-DatePicker-day-selected")
    String dayIsSelected();

    @ClassName("gwt-DatePicker-day-selectedAndHighlighted")
    String dayIsSelectedAndHighlighted();

    @ClassName("gwt-DatePicker-day-today")
    String dayIsToday();

    @ClassName("gwt-DatePicker-day-weekend")
    String dayIsWeekend();

    @ClassName("gwt-DatePicker-days")
    String days();

    @ClassName("gwt-DatePicker-month")
    String month();

    @ClassName("gwt-DatePicker-monthSelector")
    String monthSelector();

    @ClassName("gwt-DatePicker-nextButton")
    String nextButton();

    @ClassName("gwt-DatePicker-previousButton")
    String previousButton();

    @ClassName("gwt-DatePicker-weekdayLabel")
    String weekdayLabel();

    @ClassName("gwt-DatePicker-weekendLabel")
    String weekendLabel();
  }

  /**
   * Convenience class to group css handling code.
   * 
   */
  static class CssHandling {
    static interface BundledCss extends DatePicker.Css, CssResource {
    }

    /**
     * CSS resource for {@link WrappedDropDownListBox}.
     * 
     */
    static interface Resources extends ImmutableResourceBundle {
      @Resource("com/google/gwt/gen2/widgetbase/public/DatePicker.css")
      BundledCss css();
    }

    static class StandardCss extends StandardCssImpl<DatePicker> implements Css {
      public StandardCss(String baseStyleName) {
        super(baseStyleName);
      }

      public String day() {
        return wrap("day");
      }

      public String dayIsDisabled() {
        return wrap("day", "disabled");
      }

      public String dayIsFiller() {
        return wrap("day", "filler");
      }

      public String dayIsHighlighted() {
        return wrap("day", "highlighted");
      }

      public String dayIsSelected() {
        return wrap("day", "selected");
      }

      public String dayIsSelectedAndHighlighted() {
        return wrap("day", "selectedAndHighlighted");
      }

      public String dayIsToday() {
        return wrap("day", "today");
      }

      public String dayIsWeekend() {
        return wrap("day", "weekend");
      }

      public String days() {
        return wrap("days");
      }

      public String daysLabel() {
        return wrap("daysLabel");
      }

      public String month() {
        return wrap("month");
      }

      public String monthSelector() {
        return wrap("monthSelector");
      }

      public String nextButton() {
        return wrap("nextButton");
      }

      public String previousButton() {
        return wrap("previousButton");
      }

      public String weekdayLabel() {
        return wrap("weekdayLabel");
      }

      public String weekendLabel() {
        return wrap("weekdayLabel");
      }
    }

    static void injectCss() {
      if (DEFAULT_CSS != null) {
        assert (false) : "At least one date picker was instaciated with the previous default";
        throw new IllegalStateException();
      }
      if (Gen2CssInjector.isInjectionEnabled()) {
        BundledCss css = ((Resources) GWT.create(Resources.class)).css();
        assert (!css.weekdayLabel().equals("null")) : "Something has gone wrong with your css injection for date picker";
        DatePicker.DEFAULT_CSS = Gen2CssInjector.inject(css);
      }
    }

    private static Css ensureDefaultCss() {
      if (DEFAULT_CSS == null) {
        DEFAULT_CSS = createCss("gwt-DatePicker");
      }
      return DEFAULT_CSS;
    }
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

  /**
   * Default Css for {@link DatePicker}
   */
  public static Css DEFAULT_CSS;

  /**
   * Creates a {@link Css} instance with the given base name.
   * 
   * @param baseName base style name.
   */
  public static Css createCss(String baseName) {
    return new CssHandling.StandardCss(baseName);
  }

  /**
   * Injects the default css for DatePicker.
   */
  public static void injectDefaultCss() {

    CssHandling.injectCss();
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
        new CalendarModel(), CssHandling.ensureDefaultCss());
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
    addGlobalDateStyle(new Date(), css().dayIsToday());
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

  public HandlerRegistration addHighlightHandler(HighlightHandler<Date> handler) {
    return addHandler(HighlightEvent.KEY, handler);
  }

  public HandlerRegistration addSelectionHandler(SelectionHandler<Date> handler) {
    return super.addHandler(SelectionEvent.KEY, handler);
  }

  public HandlerRegistration addShowRangeHandler(ShowRangeHandler<Date> handler) {
    return addHandler(ShowRangeEvent.KEY, handler);
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
  public final void setSelectedDate(Date newSelected, boolean fireEvents) {
    Date oldSelected = selectedDate;
    if (fireEvents && isEventHandled(SelectionEvent.KEY)) {
      fireEvent(new SelectionEvent<Date>(oldSelected, newSelected));
    }
    if (oldSelected != null) {
      removeGlobalDateStyle(oldSelected, css().dayIsSelected());
    }

    selectedDate = CalendarModel.copy(newSelected);
    if (selectedDate != null) {
      addGlobalDateStyle(selectedDate, css().dayIsSelected());
    }
  }

  @Override
  public void setStyleName(String styleName) {
    css = createCss(styleName);
    super.setStyleName(styleName);
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
    getCalendarView().setDatesEnabled(dates, enabled);
  }

  /**
   * This method causes the DatePicker to show the given date.
   */
  public final void showDate(Date date) {
    getModel().setCurrentMonthAndYear(date);
    refresh();
    if (isEventHandled(ShowRangeEvent.KEY)) {
      fireEvent(new ShowRangeEvent<Date>(
          getCalendarView().getFirstVisibleDate(),
          getCalendarView().getLastVisibleDate()));
    }
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
    setStyleName(panel.getElement(), css.baseName());
    setStyleName(css().baseName());
    panel.add(this.getMonthSelector());
    panel.add(this.getCalendarView());
  }

  final void refresh() {
    highlightedDate = null;
    calendar.refresh();
    monthSelector.refresh();
  }

  /**
   * Sets the highlighted date. Only {@link CalendarView} can set the
   * highlighted date.
   * 
   * @param highlightedDate selected date
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
