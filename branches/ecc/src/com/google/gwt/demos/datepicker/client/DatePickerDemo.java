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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.LazyPanel;
import com.google.gwt.widgetideas.datepicker.client.ChangeEvent;
import com.google.gwt.widgetideas.datepicker.client.DatePicker;
import com.google.gwt.widgetideas.datepicker.client.Handler;

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

    }, "simple picker");

    panel.add(new LazyPanel() {

      @Override
      public Widget createWidget() {
        return eventPicker();
      }

    }, "event picker");

    RootPanel.get().add(master);
  }

  private Widget eventPicker() {
    DatePicker picker = new DatePicker();
    HorizontalPanel panel = new HorizontalPanel();
    panel.add(picker);
    picker.addHighlightChangeHandler(new Handler<ChangeEvent<Date, DatePicker>>() {

      public void fire(ChangeEvent<Date, DatePicker> event) {
        Log.info("fired hover event from:" + event.getOldValue() + " new " + event.getNewValue());
      }
    });

    return panel;
  }

  private DatePicker simplePicker() {
    DatePicker picker = new DatePicker();
    return picker;
  }
}
