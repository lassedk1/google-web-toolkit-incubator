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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.widgetideas.datepicker.client.AbstractDatePicker.Styles;
import com.google.gwt.widgetideas.datepicker.client.SimpleCalendarView.CellGrid.Cell;
import com.google.gwt.widgetideas.datepicker.client.impl.CalendarModel;
import com.google.gwt.widgetideas.table.client.overrides.ElementMapper;
import com.google.gwt.widgetideas.table.client.overrides.Grid;
import com.google.gwt.widgetideas.table.client.overrides.HTMLTable.CellFormatter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Simple calendar view.
 * 
 */
public class SimpleCalendarView extends CalendarView {

  class CellGrid extends Grid {

    class Cell extends UIObject {
      int index;
      Date date = new Date();

      Cell(Element td, int column) {
        index = cellList.size();
        cellList.add(this);
        setElement(td);
        cellMap.put(this);

        setStyleName(Styles.DATE_CELL);
        if (getModel().isWeekend(column)) {
          addStyleName(Styles.WEEKEND);
        }
      }

      public Date getDate() {
        return date;
      }

      public void onClick(Event event) {

        if (isFiller()) {
          getDatePicker().showDate(date);
        }
        datePicker.setSelectedDate(date);
      }

      public void update(Date current) {
        this.date.setTime(current.getTime());
        String value = getModel().formatDayOfMonth(date);
        setText(value);
        setStyleName(this.getElement(), Styles.FILLER, isFiller());
      }

      boolean isFiller() {
        return this.date.getMonth() != getModel().getMonthAndYear().getMonth();
      }

      private void setText(String value) {
        DOM.setInnerText(getElement(), value);
      }
    }

    private ElementMapper<Cell> cellMap = new ElementMapper<Cell>();

    private ArrayList<Cell> cellList = new ArrayList<Cell>();

    CellGrid() {
      super(CalendarModel.WEEKS_IN_MONTH + 1, CalendarModel.DAYS_IN_WEEK);
      setCellPadding(0);
      setCellSpacing(0);
      sinkEvents(Event.ONCLICK | Event.ONMOUSEOVER | Event.ONMOUSEOUT);
    }

    public Cell getCell(int i) {
      return cellList.get(i);
    }

    @Override
    public void onBrowserEvent(Event event) {

      switch (DOM.eventGetType(event)) {
        case Event.ONCLICK: {
          Cell cell = getCell(event);
          if (cell != null) {
            cell.onClick(event);
          }
          break;
        }
        case Event.ONMOUSEOUT: {
          Element e = DOM.eventGetFromElement(event);
          Element target = DOM.eventGetToElement(event);
          if (e != null) {
            Cell cell = cellMap.get(e);
            if (cell != null && cellMap.get(target) == null) {
              getDatePicker().setHighlightedDate(null);
            }
          }
          break;
        }
        case Event.ONMOUSEOVER: {
          Element e = DOM.eventGetToElement(event);
          if (e != null) {
            Cell cell = cellMap.get(e);
            if (cell != null) {
              getDatePicker().setHighlightedDate(cell.getDate());
            }
          }
          break;
        }
      }
    }

    public int size() {
      return cellList.size();
    }

    private Cell getCell(Event e) {
      // Find out which cell was actually clicked.
      Element td = getEventTargetCell(e);
      return td != null ? cellMap.get(td) : null;
    }
  }

  CellGrid grid = new CellGrid();

  private Date firstDisplayed;

  private Date lastDisplayed = new Date();

  SimpleCalendarView() {
  }

  @Override
  public Date getFirstVisibleDate() {
    return firstDisplayed;
  }

  @Override
  public Date getLastVisibleDate() {
    return lastDisplayed;
  }

  public void refresh() {
    firstDisplayed = getModel().getFirstDayOfFirstWeek();

    lastDisplayed.setTime(firstDisplayed.getTime());

    for (int i = 0; i < grid.cellList.size(); i++) {
      Cell cell = grid.cellList.get(i);
      cell.update(lastDisplayed);
      getModel().shiftDays(lastDisplayed, 1);
    }
  }

  @Override
  public void removeStyleName(Date date, String styleName) {
    getCell(date).removeStyleName(styleName);
  }

  @Override
  public void setEnabled(Date date, boolean enabled) {
    // TODO Auto-generated method stub
  }

  @Override
  public void setup() {
    CellFormatter formatter = grid.getCellFormatter();
    // Set up title.
    for (int i = 0; i < CalendarModel.DAYS_IN_WEEK; i++) {
      grid.setText(0, i, getModel().formatDayOfWeek(i));
      formatter.setStyleName(0, i, Styles.DAY_TITLE);
    }

    for (int row = 1; row <= CalendarModel.WEEKS_IN_MONTH; row++) {
      for (int column = 0; column < CalendarModel.DAYS_IN_WEEK; column++) {
        grid.new Cell(formatter.getElement(row, column), column);
      }
    }
    initWidget(grid);
    setStyleName(Styles.CALENDAR_VIEW);
  }

  @Override
  public void showStyleName(Date date, String styleName) {
    getCell(date).addStyleName(styleName);
  }

  private Cell getCell(Date d) {
    int index = getModel().diffDates(firstDisplayed, d);
    Cell cell = grid.getCell(index);
    if (cell.date.equals(d) == false) {
      throw new IllegalStateException(d + " cannot be associated with cell "
          + cell);
    }
    return cell;
  }
}
