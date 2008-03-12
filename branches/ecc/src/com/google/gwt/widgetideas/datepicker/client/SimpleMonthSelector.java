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

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.datepicker.client.AbstractDatePicker.Styles;

import java.util.Date;

/**
 * A simple {@link MonthSelector} used for the default date picker.
 */
public class SimpleMonthSelector extends MonthSelector<AbstractDatePicker> {

  PushButton backwards = new PushButton("<<");
  PushButton forwards = new PushButton(">>");
  Label label = new Label();

  public SimpleMonthSelector() {
  }

  public void setAllowableDateRange(Date from, Date to) {
    // TODO(ecc).
  }

  protected void refresh() {
    label.setText(getModel().formatMonth());
  }

  @Override
  protected void setup() {
    HorizontalPanel p = new HorizontalPanel();
    p.add(backwards);
    backwards.addStyleName(Styles.MONTH_BACKWARD);
    p.add(label);
    label.addStyleName(Styles.MONTH_LABEL);
    p.add(forwards);
    p.setCellWidth(label, "100%");
    forwards.addStyleName(Styles.MONTH_FORWARD);
    initWidget(p);
    setStyleName(Styles.MONTH_SELECTOR);

    backwards.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        addMonths(-1);
      }
    });

    forwards.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        addMonths(+1);
      }
    });
  }

}
