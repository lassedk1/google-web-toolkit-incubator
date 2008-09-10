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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.libideas.logging.client.SimpleLogHandler;
import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.LazyPanel;
import com.google.gwt.widgetideas.client.event.ChangeEvent;
import com.google.gwt.widgetideas.client.event.ChangeHandler;
import com.google.gwt.widgetideas.client.event.EventHandler;
import com.google.gwt.widgetideas.client.event.HighlightEvent;
import com.google.gwt.widgetideas.client.event.HighlightHandler;
import com.google.gwt.widgetideas.client.event.KeyDownEvent;
import com.google.gwt.widgetideas.client.event.KeyDownHandler;
import com.google.gwt.widgetideas.client.event.RenderingEvent;
import com.google.gwt.widgetideas.client.event.RenderingHandler;
import com.google.gwt.widgetideas.datepicker.client.DateBox;
import com.google.gwt.widgetideas.datepicker.client.DatePicker;

import java.util.Date;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DatePickerDemo implements EntryPoint {

  DateBox start = new DateBox();

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    HorizontalPanel master = new HorizontalPanel();
    SimpleLogHandler handler = new SimpleLogHandler(false);
    Log.addLogHandler(handler);

    TabPanel panel = new TabPanel();
    panel.getDeckPanel().setAnimationEnabled(true);
    LazyPanel.addTabListener(panel);
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

    }, "date range");

    panel.selectTab(0);
    panel.addTabListener(new TabListener() {

      public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {

        return true;
      }

      public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
        if (tabIndex == 3) {
          Timer t = new Timer() {

            @Override
            public void run() {
              start.setFocus(true);
            }
          };
          t.schedule(350);
        }
      }

    });
    RootPanel.get().add(master);
  }

  private Widget dateRange() {
    VerticalPanel v = new VerticalPanel();
    HorizontalPanel p = new HorizontalPanel();
    v.add(p);
    start = new DateBox();
    start.setAnimationEnabled(true);
    final DateBox end = new DateBox();
    end.setAnimationEnabled(true);

    start.addKeyDownHandler(new KeyDownHandler() {
      public void onKeyDown(KeyDownEvent e) {
        if (e.getKeyCode() == KEY_RIGHT
            && start.getCursorPos() == start.getText().length()) {
          start.hideDatePicker();
          end.setFocus(true);
        }
      }
    });

    end.addKeyDownHandler(new KeyDownHandler() {
      public void onKeyDown(KeyDownEvent e) {
        if ((e.getKeyCode() == KEY_LEFT) && end.getCursorPos() == 0) {
          start.setFocus(true);
          end.hideDatePicker();
        }
      }
    });

    end.getDatePicker().addChangeHandler(new ChangeHandler<Date>() {
      public void onChange(ChangeEvent<Date> event) {
        start.removeStyleName("user-modified");
      }
    });

    start.showDate(new Date());

    p.add(start);
    Label l = new Label(" - ");
    l.setStyleName("filler");
    p.add(l);
    p.add(end);
    HorizontalPanel h2 = new HorizontalPanel();
    v.add(h2);
    h2.add(new Button("Short format", new ClickListener() {

      public void onClick(Widget sender) {
        start.setDateFormat(DateTimeFormat.getShortDateFormat());
        end.setDateFormat(DateTimeFormat.getShortDateFormat());
      }
    }));
    h2.add(new Button("Long format", new ClickListener() {

      public void onClick(Widget sender) {
        start.setDateFormat(DateTimeFormat.getLongDateFormat());
        end.setDateFormat(DateTimeFormat.getLongDateFormat());
      }
    }));

    h2.add(new Button("clear", new ClickListener() {
      public void onClick(Widget sender) {
        start.clear();
        end.clear();
      }
    }));
    return v;
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
    monitorEvents.add(toggleHandler("Log hover events", picker, logHovers));

    // Log select events.
    ChangeHandler logSelects = new ChangeHandler<Date>() {
      public void onChange(ChangeEvent<Date> event) {
        Log.info("Select:" + event.getOldValue() + "-->" + event.getNewValue());
      }
    };
    monitorEvents.add(toggleHandler("Log select events", picker, logSelects));

    // Disable selected date
    ChangeHandler toggleEnabled = new ChangeHandler<Date>() {
      public void onChange(ChangeEvent<Date> event) {
        Date d = event.getNewValue();
        picker.setVisibleDateEnabled(d, !picker.isDateEnabled(d));
      }
    };

    monitorEvents.add(toggleHandler("Toggle enabled property", picker,
        toggleEnabled));

    // Add alerts on bad user input

    return panel;
  }

  private DatePicker simplePicker() {
    DatePicker picker = new DatePicker();
    Date d = new Date();
    d.setMonth(2);
    d.setDate(1);
    picker.setSelectedDate(d);
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

    styling.add(toggleHandler("5th of the month will be red", picker,
        redHandler));

    // Random style adder.
    ChangeHandler<Date> randomStyles = new ChangeHandler<Date>() {
      String[] styles = {
          "blue-background", "green-border", "red-text", "big-text",
          "green-background", "underlined-and-bold-text", "yellow-background"};

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
    styling.add(toggleHandler("Add random style to selected date", picker,
        randomStyles));

    // Visibly disable selected date.
    ChangeHandler<Date> disabler = new ChangeHandler<Date>() {

      public void onChange(ChangeEvent<Date> event) {
        if (event.getNewValue() != null) {
          picker.setVisibleDateEnabled(event.getNewValue(), false);
        }
      }
    };
    styling.add(toggleHandler("Disable selected date", picker, disabler));
    return panel;
  }

  private CheckBox toggleHandler(String title, final DatePicker picker,
      final EventHandler handler) {
    final CheckBox select = new CheckBox(title);
    select.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        if (select.isChecked()) {
          if (handler instanceof ChangeHandler) {
            picker.addChangeHandler((ChangeHandler<Date>) handler);
          } else if (handler instanceof HighlightHandler) {
            picker.addHighlightHandler((HighlightHandler<Date>) handler);
          } else if (handler instanceof RenderingHandler) {
            picker.addRenderingHandler((RenderingHandler) handler);
          }
        } else {
          if (handler instanceof ChangeHandler) {
            picker.removeChangeHandler((ChangeHandler<Date>) handler);
          } else if (handler instanceof HighlightHandler) {
            picker.removeHighlightHandler((HighlightHandler<Date>) handler);
          } else if (handler instanceof RenderingHandler) {
            picker.removeRenderingHandler((RenderingHandler) handler);
          }
        }
      }
    });
    return select;
  }

}
