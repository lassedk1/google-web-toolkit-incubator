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
import com.google.gwt.widgetideas.table.client.overrides.HTMLTable.ColumnFormatter;

import java.util.Date;

/**
 * Simple calendar view.
 * 
 */
@SuppressWarnings( {"deprecation"})
public class DefaultCalendarView extends CalendarView {

  /**
   * Cell grid.
   * 
   */
  class CellGrid extends CellGridImpl<Date> {

    public class DateCell extends Cell {
      String baseStyle;

      DateCell(Element td) {
        super(td, new Date());
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
        String style = css().day();
        if (isFiller()) {
          baseStyle += css().fillerCell();
        }
        String extraStyle = getDatePicker().getGlobalDateStyle(getValue());
        if (extraStyle != null) {
          baseStyle += " " + extraStyle;
        }
        updateStyle();
      }

      @Override
      public void updateStyle() {
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
    CellFormatter formatter = grid.getCellFormatter();
    // Set up title.
    for (int i = 0; i < CalendarModel.DAYS_IN_WEEK; i++) {
      int shift = CalendarModel.getLocaleStartingDayOfWeek();
      int dayIdx = i + shift < CalendarModel.DAYS_IN_WEEK ? i + shift : i
          + shift - CalendarModel.DAYS_IN_WEEK;
      grid.setText(0, i, getModel().formatDayOfWeek(dayIdx));
      formatter.setStyleName(0, i, css().dayTitle());
    }

    for (int row = 1; row <= CalendarModel.WEEKS_IN_MONTH; row++) {
      for (int column = 0; column < CalendarModel.DAYS_IN_WEEK; column++) {
        grid.new DateCell(formatter.getElement(row, column));
      }
    }
    initWidget(grid);
    setStyleName(css().days());
    ColumnFormatter columnFormatter = grid.getColumnFormatter();
    for (int i = 0; i < 7; i++) {
      int shift = CalendarModel.getLocaleStartingDayOfWeek();
      int dayIdx = i + shift < CalendarModel.DAYS_IN_WEEK ? i + shift : i
          + shift - CalendarModel.DAYS_IN_WEEK;
      if (getModel().isWeekend(dayIdx)) {
        columnFormatter.addStyleName(i, css().days());
      }
    }
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
