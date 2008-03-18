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

package com.google.gwt.demos.datepicker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.libideas.logging.client.SimpleLogHandler;
import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.LazyPanel;
import com.google.gwt.widgetideas.datepicker.client.DateBox;
import com.google.gwt.widgetideas.datepicker.client.DatePicker;
import com.google.gwt.widgetideas.datepicker.client.events.ChangeEvent;
import com.google.gwt.widgetideas.datepicker.client.events.ChangeHandler;
import com.google.gwt.widgetideas.datepicker.client.events.HighlightEvent;
import com.google.gwt.widgetideas.datepicker.client.events.HighlightHandler;
import com.google.gwt.widgetideas.datepicker.client.events.RenderingEvent;
import com.google.gwt.widgetideas.datepicker.client.events.RenderingHandler;

import java.util.Date;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DatePickerDemo implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    HorizontalPanel master = new HorizontalPanel();
    SimpleLogHandler handler = new SimpleLogHandler(false);
    Log.addLogHandler(handler);
    TabPanel panel = new TabPanel();
    master.add(panel);
    master.add(handler.getWidget());
    panel.add(new LazyPanel() {

      @Override
      public Widget createWidget() {
        return simplePicker();
      }

    }, "simple");

    panel.add(new LazyPanel() {

      @Override
      public Widget createWidget() {
        return eventPicker();
      }

    }, "events");

    panel.add(new LazyPanel() {

      @Override
      public Widget createWidget() {
        return stylingPicker();
      }

    }, "styles");

    panel.add(new LazyPanel() {

      @Override
      public Widget createWidget() {
        return dateRange();
      }

    }, "dateRange");

    RootPanel.get().add(master);
  }

  private Widget dateRange() {
    HorizontalPanel p = new HorizontalPanel();
    DateBox start = new DateBox();
    DateBox end = new DateBox();
    p.add(start);
    p.add(end);

    return p;
  }

  private Widget eventPicker() {

    // Set up code
    final DatePicker picker = new DatePicker();
    HorizontalPanel panel = new HorizontalPanel();
    panel.add(picker);
    VerticalPanel monitorEvents = new VerticalPanel();
    panel.add(monitorEvents);

    // Log hover events.
    HighlightHandler logHovers = new HighlightHandler<Date>() {
      public void onHighlight(HighlightEvent<Date> event) {
        Log.info("Hover:" + event.getHighlightedValue());
      }
    };
    monitorEvents.add(toggleHighlightHandler("Log hover events", picker,
        logHovers));

    // Log select events.
    ChangeHandler logSelects = new ChangeHandler<Date>() {
      public void onChange(ChangeEvent<Date> event) {
        Log.info("Select:" + event.getOldValue() + "-->" + event.getNewValue());
      }
    };
    monitorEvents.add(toggleChangeHandler("Log select events", picker,
        logSelects));

    // Disable selected date
    // Log select events.
    ChangeHandler toggleEnabled = new ChangeHandler<Date>() {
      public void onChange(ChangeEvent<Date> event) {
        Date d = event.getNewValue();
        picker.setVisibleDateEnabled(d, !picker.isDateEnabled(d));
      }
    };

    monitorEvents.add(toggleChangeHandler("toggle currently enabled on date",
        picker, toggleEnabled));
    return panel;
  }

  private DatePicker simplePicker() {
    DatePicker picker = new DatePicker();
    return picker;
  }

  private Widget stylingPicker() {
    final DatePicker picker = new DatePicker();
    HorizontalPanel panel = new HorizontalPanel();
    panel.add(picker);
    VerticalPanel styling = new VerticalPanel();
    panel.add(styling);

    // 5th of each month is red.
    RenderingHandler redHandler = new RenderingHandler() {
      public void onRendered(RenderingEvent event) {
        Date shown = picker.getDateShown();
        Date d = new Date();
        d.setYear(shown.getYear());
        d.setMonth(shown.getMonth());
        d.setDate(5);
        picker.addVisibleDateStyle(d, "red-date");
      }
    };

    styling.add(toggleRenderingHandler("5th of each month is read", picker,
        redHandler));

    // Random style adder
    ChangeHandler<Date> randomStyles = new ChangeHandler<Date>() {
      String[] styles = {"blue-background", "green-border", "red-text"};
      int styleIndex = 0;

      public void onChange(ChangeEvent<Date> event) {
        if (event.getNewValue() != null) {
          styleIndex = ++styleIndex % styles.length;
          String styling = styles[styleIndex];
          Log.info(event.getNewValue() + " has style " + styling, "styling");
          picker.addGlobalDateStyle(event.getNewValue(), styling);
        }
      }
    };
    styling.add(toggleChangeHandler("Add random style to selected date",
        picker, randomStyles));

    // Visiblely disable selected date.
    ChangeHandler<Date> disabler = new ChangeHandler<Date>() {

      public void onChange(ChangeEvent<Date> event) {
        if (event.getNewValue() != null) {
          picker.setVisibleDateEnabled(event.getNewValue(), false);
        }
      }
    };

    return panel;
  }

  private CheckBox toggleChangeHandler(String title, final DatePicker picker,
      final ChangeHandler handler) {
    final CheckBox select = new CheckBox(title);
    select.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        if (select.isChecked()) {
          picker.addChangeHandler(handler);
        } else {
          picker.removeChangeHandler(handler);
        }
      }
    });
    return select;
  }

  private CheckBox toggleRenderingHandler(String title,
      final DatePicker picker, final RenderingHandler handler) {
    final CheckBox select = new CheckBox(title);
    select.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        if (select.isChecked()) {
          picker.addRenderingHandler(handler);
        } else {
          picker.removeRenderingHandler(handler);
        }
      }
    });
    return select;
  }

  private CheckBox toggleHighlightHandler(String title,
      final DatePicker picker, final HighlightHandler handler) {
    final CheckBox select = new CheckBox(title);
    select.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        if (select.isChecked()) {
          picker.addHighlightHandler(handler);
        } else {
          picker.removeHighlightHandler(handler);
        }
      }
    });
    return select;
  }
}
