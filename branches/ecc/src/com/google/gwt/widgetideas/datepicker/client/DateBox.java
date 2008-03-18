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

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.datepicker.client.events.ChangeEvent;
import com.google.gwt.widgetideas.datepicker.client.events.ChangeHandler;

import java.util.Date;

/**
 * Most of the functionality needed to create a simple date box.
 */
public class DateBox extends Composite {
  static class Styles {
    public static String DEFAULT = "gwt-DateBox";
  }

  private DropDownPanel popup = new DropDownPanel();
  private TextBox box = new TextBox();
  private DatePicker picker;
  private DateTimeFormat formatter = DateTimeFormat.getMediumDateFormat();

  public DateBox() {
    this(new DatePicker());
  }

  public DateBox(final DatePicker picker) {
    FlowPanel p = new FlowPanel();
    p.add(box);
    this.picker = picker;
    popup.setWidget(picker);
    initWidget(p);
    setStyleName(Styles.DEFAULT);
    picker.addChangeHandler(new ChangeHandler<Date>() {
      public void onChange(ChangeEvent<Date> event) {
        box.setText(formatter.format(event.getNewValue()));
        hideDatePicker();
      }
    });
    box.addFocusListener(new FocusListener() {
      public void onFocus(Widget sender) {
        showDatePicker();
      }

      public void onLostFocus(Widget sender) {
      }
    });
  }

  public DatePicker getDatePicker() {
    return picker;
  }

  public FlowPanel getWidget() {
    return (FlowPanel) super.getWidget();
  }

  public void hideDatePicker() {
    popup.hide();
  }

  public void showDatePicker() {
    picker.showDate(new Date());
    popup.showBelow(this);
  }
}
