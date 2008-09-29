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

package com.google.gwt.gen2.demo.datepicker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.gen2.datepicker.client.DateBox;
import com.google.gwt.gen2.datepicker.client.DatePicker;
import com.google.gwt.gen2.event.dom.client.KeyDownEvent;
import com.google.gwt.gen2.event.dom.client.KeyDownHandler;
import com.google.gwt.gen2.event.logical.shared.HasHighlightHandlers;
import com.google.gwt.gen2.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.gen2.event.logical.shared.HasShowRangeHandlers;
import com.google.gwt.gen2.event.logical.shared.HighlightEvent;
import com.google.gwt.gen2.event.logical.shared.HighlightHandler;
import com.google.gwt.gen2.event.logical.shared.SelectionEvent;
import com.google.gwt.gen2.event.logical.shared.SelectionHandler;
import com.google.gwt.gen2.event.logical.shared.ShowRangeEvent;
import com.google.gwt.gen2.event.logical.shared.ShowRangeHandler;
import com.google.gwt.gen2.event.shared.EventHandler;
import com.google.gwt.gen2.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.libideas.logging.client.SimpleLogHandler;
import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.LazyPanel;

import java.util.Date;

/**
 * Date picker demo.
 */
public class DatePickerDemo implements EntryPoint {
  private abstract class HandlerCheckBox<EventHandlerType extends EventHandler, HasHandlers>
      extends CheckBox {
    protected HandlerRegistration registration;
    protected final HasHandlers widget;
    protected final EventHandlerType handler;
    String title;

    HandlerCheckBox(final String title, HasHandlers widget,
        final EventHandlerType handler) {
      super(title);
      this.widget = widget;
      this.handler = handler;
      this.title = title;
      this.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          if (isChecked()) {
            Log.info("Adding handler '" + title + "'");
            addHandler();
          } else {
            Log.info("Removing handler '" + title + "'");
            registration.removeHandler();
          }
        }
      });
    }

    abstract void addHandler();
  }

  private class HighlightHandlerCheckBox extends
      HandlerCheckBox<HighlightHandler<Date>, HasHighlightHandlers<Date>> {

    HighlightHandlerCheckBox(String title, HasHighlightHandlers<Date> widget,
        HighlightHandler handler) {
      super(title, widget, handler);
    }

    @Override
    void addHandler() {
      registration = widget.addHighlightHandler(handler);
    }
  }

  private class SelectionHandlerCheckBox extends
      HandlerCheckBox<SelectionHandler<Date>, HasSelectionHandlers<Date>> {

    SelectionHandlerCheckBox(String title, HasSelectionHandlers<Date> widget,
        SelectionHandler handler) {
      super(title, widget, handler);
    }

    @Override
    void addHandler() {
      registration = widget.addSelectionHandler(handler);
    }
  }

  private class ShowRangeHandlerCheckBox extends
      HandlerCheckBox<ShowRangeHandler<Date>, HasShowRangeHandlers<Date>> {

    ShowRangeHandlerCheckBox(String title, HasShowRangeHandlers<Date> widget,
        ShowRangeHandler handler) {
      super(title, widget, handler);
    }

    @Override
    void addHandler() {
      registration = widget.addShowRangeHandler(handler);
    }
  }

  private DateBox start;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    Log.info("Log messages enabled");
    HorizontalPanel master = new HorizontalPanel();
    SimpleLogHandler handler = new SimpleLogHandler(false);
    Log.addLogHandler(handler);

    DatePicker.injectDefaultCss();
    TabPanel panel = new TabPanel();
    master.add(panel);
    master.add(handler.getWidget());
    panel.add(new LazyPanel<Widget>() {

      @Override
      public Widget createWidget() {
        return simplePicker();
      }

    }, "simple");

    panel.add(new LazyPanel<Widget>() {

      @Override
      public Widget createWidget() {
        return eventPicker();
      }

    }, "events");

    panel.add(new LazyPanel<Widget>() {

      @Override
      public Widget createWidget() {
        return stylingPicker();
      }

    }, "styles");

    panel.add(new LazyPanel<Widget>() {

      @Override
      public Widget createWidget() {
        return dateRange();
      }

    }, "date range");

    panel.selectTab(0);

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
        if (e.isRightKeyCode()
            && start.getCursorPos() == start.getText().length()) {
          start.hideDatePicker();
          end.setFocus(true);
        }
      }
    });

    end.addKeyDownHandler(new KeyDownHandler() {
      public void onKeyDown(KeyDownEvent e) {
        if ((e.isLeftKeyCode()) && end.getCursorPos() == 0) {
          start.setFocus(true);
          end.hideDatePicker();
        }
      }
    });

    end.getDatePicker().addSelectionHandler(new SelectionHandler<Date>() {
      public void onSelection(SelectionEvent<Date> event) {
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

    monitorEvents.add(new HighlightHandlerCheckBox("Log highlight events",
        picker, new HighlightHandler<Date>() {
          public void onHighlight(HighlightEvent<Date> event) {
            Log.info("Highlight: " + event.getValue());
          }
        }));

    monitorEvents.add(new SelectionHandlerCheckBox("Log select events", picker,
        new SelectionHandler<Date>() {
          public void onSelection(SelectionEvent<Date> event) {
            Log.info("Select: " + event.getOldValue() + "-->"
                + event.getNewValue());
          }
        }));

    monitorEvents.add(new SelectionHandlerCheckBox("Disable selected item",
        picker, new SelectionHandler<Date>() {
          public void onSelection(SelectionEvent<Date> event) {
            Date d = event.getNewValue();
            picker.setEnabledOnVisibleDate(d, !picker.isDateEnabled(d));
          }
        }));

    // Add alerts on bad user input

    return panel;
  };

  private DatePicker simplePicker() {
    DatePicker picker = new DatePicker();
    Date d = new Date();
    d.setMonth(2);
    d.setDate(1);
    picker.setSelectedDate(d);
    return picker;
  };

  private Widget stylingPicker() {
    final DatePicker picker = new DatePicker();
    HorizontalPanel panel = new HorizontalPanel();
    panel.add(picker);
    VerticalPanel styling = new VerticalPanel();
    panel.add(styling);

    styling.add(new ShowRangeHandlerCheckBox(
        "First and last displayed date will be underlined and bold", picker,
        new ShowRangeHandler<Date>() {
          public void onShowRange(ShowRangeEvent<Date> event) {
            Date startDate = event.getStart();
            Date endDate = event.getEnd();
            picker.addStyleToVisibleDate(startDate, "underlined-and-bold-text");
            picker.addStyleToVisibleDate(endDate, "underlined-and-bold-text");
          }
        }));

    styling.add(new ShowRangeHandlerCheckBox("5th of the month will be red",
        picker, new ShowRangeHandler<Date>() {
          public void onShowRange(ShowRangeEvent<Date> event) {
            Date monthShown = picker.getDateShown();
            Date d = new Date(monthShown.getTime());
            d.setDate(5);
            Log.info("Adding a red style to the 5th day of the month");
            picker.addStyleToVisibleDate(d, "red-date");
          }
        }));

    styling.add(new SelectionHandlerCheckBox(
        "Add random style to selected date", picker,
        new SelectionHandler<Date>() {
          String[] styles = {
              "blue-background", "green-border", "red-text", "big-text",
              "green-background", "underlined-and-bold-text",
              "yellow-background"};

          int styleIndex = 0;

          public void onSelection(SelectionEvent<Date> event) {
            if (event.getNewValue() != null) {
              styleIndex = ++styleIndex % styles.length;
              String styling = styles[styleIndex];
              Log.info(event.getNewValue() + " has style " + styling, "styling");
              picker.addGlobalStyleToDate(event.getNewValue(), styling);
            }
          }
        }));

    return panel;
  };

}
