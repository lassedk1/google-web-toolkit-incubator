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

import java.util.Date;

import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

/**
 * A simple {@link MonthSelector} used for the default date picker.
 */
public class DefaultMonthSelector extends MonthSelector {

  PushButton backwards = new PushButton();
  PushButton forwards = new PushButton();
  Label label = new Label();
  Date from;
  Date to;

  public DefaultMonthSelector() {
    backwards.getUpFace().setHTML("&laquo;");
    forwards.getUpFace().setHTML("&raquo;");
  }

  public void setAllowableDateRange(Date from, Date to) {
    this.from = from;
    this.to = to;
    Log.info("setAllowableDateRange is not yet implemented");
  }

  protected void refresh() {
    String text = getModel().formatCurrentMonth();
    label.setText(text);
  }

  @Override
  protected void setup() {
    HorizontalPanel p = new HorizontalPanel();
    p.add(backwards);
    backwards.addStyleName(css().previousButton());
    p.add(label);
    label.addStyleName(css().title());
    p.add(forwards);
    p.setCellWidth(label, "100%");
    forwards.addStyleName(css().nextButton());
    initWidget(p);
    setStyleName(css().days());

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
