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

import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.table.client.overrides.Grid;
import com.google.gwt.widgetideas.table.client.overrides.HTMLTable.CellFormatter;

import java.util.Date;

/**
 * A simple {@link MonthSelector} used for the default date picker. Not part of
 * the public API as we wish to evolve it freely over time. Please copy though,
 * as we like it getting used.
 * 
 * @deprecated use the com.google.gwt.user.datepicker.client classes instead
 */
@Deprecated
public final class DefaultMonthSelector extends MonthSelector {

  private PushButton backwards;
  private PushButton forwards;
  private Grid grid;

  /**
   * Constructor.
   */
  public DefaultMonthSelector() {
  }

  @Override
  public void setAllowableDateRange(Date from, Date to) {
    Log.info("setAllowableDateRange is not yet implemented");
  }

  @Override
  protected void refresh() {
    String formattedMonth = getModel().formatCurrentMonth();
    grid.setText(0, 1, formattedMonth);
  }

  @Override
  protected void setup() {
    // Set up backwards.
    backwards = new PushButton();
    backwards.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        addMonths(-1);
      }
    });

    backwards.getUpFace().setHTML("&laquo;");
    backwards.setStyleName(css().datePickerPreviousButton());

    forwards = new PushButton();
    forwards.getUpFace().setHTML("&raquo;");
    forwards.setStyleName(css().datePickerNextButton());
    forwards.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        addMonths(+1);
      }
    });

    // Set up grid.
    grid = new Grid(1, 3);
    grid.setWidget(0, 0, backwards);
    grid.setWidget(0, 2, forwards);

    CellFormatter formatter = grid.getCellFormatter();
    formatter.setStyleName(0, 1, css().datePickerMonth());
    formatter.setWidth(0, 0, "20%");
    formatter.setWidth(0, 2, "20%");
    grid.setStyleName(css().datePickerDays());
    initWidget(grid);
  }

}
