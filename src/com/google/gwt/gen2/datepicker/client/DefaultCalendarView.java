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

import com.google.gwt.gen2.commonwidget.client.impl.CellGridImpl;
import com.google.gwt.gen2.datepicker.client.DefaultCalendarView.CellGrid.DateCell;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.widgetideas.table.client.overrides.HTMLTable.CellFormatter;

import java.util.Date;

/**
 * Simple calendar view. Not part of the public API as we wish to evolve it freely
 * over time.  Please copy though, as we like it getting used.
 */

@SuppressWarnings(/* Date manipulation required */{"deprecation"})
class DefaultCalendarView extends CalendarView {

  /**
   * Cell grid.
   */
  //Javac bug requires that date be fully specified here.
  class CellGrid extends CellGridImpl<java.util.Date> {
    class DateCell extends Cell {
      String cellStyle;
      String dateStyle;

      DateCell(Element td, boolean isWeekend) {
        super(td, new Date());
        cellStyle = css().day();
        if (isWeekend) {
          cellStyle += " " + css().dayIsWeekend();
        }
      }

      public boolean isFiller() {
        return !getModel().isInCurrentMonth(getValue());
      }

      @Override
      public void onSelected(boolean selected) {
        if (selected) {
          getDatePicker().setSelectedDate(getValue());
          if (isFiller()) {
            getDatePicker().showDate(getValue());
          }
        }
        super.onSelected(selected);
      }

      public void update(Date current) {
        setEnabled(true);
        deregisterValue(getValue());
        getValue().setTime(current.getTime());
        registerValue(getValue());
        String value = getModel().formatDayOfMonth(getValue());
        setText(value);
        dateStyle = cellStyle;
        if (isFiller()) {
          dateStyle += " " + css().dayIsFiller();
        }
        String extraStyle = getDatePicker().getGlobalDateStyle(current);
        if (extraStyle != null) {
          dateStyle += " " + extraStyle;
        }
        updateStyle();
      }

      @Override
      public void updateStyle() {
        String accum = dateStyle;
        if (isSelected()) {
          accum += " " + css().dayIsSelected();
        }
        if (isHighlighted()) {
          accum += " " + css().dayIsHighlighted();
        }
        if (isHighlighted() && isSelected()) {
          accum += " " + css().dayIsSelectedAndHighlighted();
        }
        if (!isEnabled()) {
          accum += " " + css().dayIsDisabled();
        }
        setStyleName(accum);
      }

      private void setText(String value) {
        DOM.setInnerText(getElement(), value);
      }
    }

    CellGrid() {
      resize(CalendarModel.WEEKS_IN_MONTH + 1, CalendarModel.DAYS_IN_WEEK);
    }

    @Override
    protected void onSelected(Cell lastSelected, Cell cell) {
    }
  }

  private CellGrid grid = new CellGrid();

  private Date firstDisplayed;

  private Date lastDisplayed = new Date();

  /**
   * Constructor.
   */
  public DefaultCalendarView() {
  }

  @Override
  public void addDateStyle(Date date, String styleName) {
    getCell(date).addStyleName(styleName);
  }

  @Override
  public Date getFirstVisibleDate() {
    return firstDisplayed;
  }

  @Override
  public Date getLastVisibleDate() {
    return lastDisplayed;
  }

  @Override
  public boolean isDateEnabled(Date d) {
    return getCell(d).isEnabled();
  }

  @Override
  public void refresh() {
    firstDisplayed = getModel().getFirstDayOfCurrentFirstWeek();

    if (firstDisplayed.getDate() == 1) {
      // show one empty week if date is Monday is the first in month.
      CalendarModel.shiftDays(firstDisplayed, -7);
    }

    lastDisplayed.setTime(firstDisplayed.getTime());

    for (int i = 0; i < grid.getNumCells(); i++) {
      if (i != 0) {
        CalendarModel.shiftDays(lastDisplayed, 1);
      }
      DateCell cell = (DateCell) grid.getCell(i);
      cell.update(lastDisplayed);
    }
  }

  @Override
  public void removeStyleName(Date date, String styleName) {
    getCell(date).removeStyleName(styleName);
  }

  @Override
  public void setDateEnabled(Date date, boolean enabled) {
    getCell(date).setEnabled(enabled);
  }

  @Override
  public void setup() {
    // Preparation
    CellFormatter formatter = grid.getCellFormatter();
    int weekendStartColumn = -1;
    int weekendEndColumn = -1;

    // Set up the day labels.
    for (int i = 0; i < CalendarModel.DAYS_IN_WEEK; i++) {
      int shift = CalendarModel.getLocaleStartingDayOfWeek();
      int dayIdx = i + shift < CalendarModel.DAYS_IN_WEEK ? i + shift : i
          + shift - CalendarModel.DAYS_IN_WEEK;
      grid.setText(0, i, getModel().formatDayOfWeek(dayIdx));

      if (getModel().isWeekend(dayIdx)) {
        formatter.setStyleName(0, i, css().weekendLabel());
        if (weekendStartColumn == -1) {
          weekendStartColumn = i;
        } else {
          weekendEndColumn = i;
        }
      } else {
        formatter.setStyleName(0, i, css().weekdayLabel());
      }
    }

    // Set up the calendar grid.
    for (int row = 1; row <= CalendarModel.WEEKS_IN_MONTH; row++) {
      for (int column = 0; column < CalendarModel.DAYS_IN_WEEK; column++) {
        // set up formatter.
        Element e = formatter.getElement(row, column);
        grid.new DateCell(e, column == weekendStartColumn
            || column == weekendEndColumn);
      }
    }
    initWidget(grid);
    grid.setStyleName(css().days());
  }

  private DateCell getCell(Date d) {
    int index = CalendarModel.diffDays(firstDisplayed, d);

    DateCell cell = (DateCell) grid.getCell(index);
    if (cell.getValue().getDate() != d.getDate()) {
      throw new IllegalStateException(d + " cannot be associated with cell "
          + cell + " as it has date " + cell.getValue());
    }
    return cell;
  }
}
