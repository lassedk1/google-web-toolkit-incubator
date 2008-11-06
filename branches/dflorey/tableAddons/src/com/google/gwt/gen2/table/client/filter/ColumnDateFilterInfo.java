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
package com.google.gwt.gen2.table.client.filter;

import com.google.gwt.gen2.table.client.TableModelHelper.ColumnFilterInfo;
import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;

/**
 * The {@link ColumnDateFilterInfo} represents the current state of a
 * {@link ColumnDateFilter} and supports some default filtering
 */
public class ColumnDateFilterInfo extends ColumnFilterInfo {
  public static final int EQUAL = 0;
  public static final int NOT_EQUAL = 1;
  public static final int LESS_THAN = 2;
  public static final int GREATER_THAN = 3;
  public static final int BETWEEN = 4;
  public static final int COUNT_OPERATORS = 5;

  private Date primaryDate, secondaryDate;
  private transient DateTimeFormat dateTimeFormat = null;
  private String datePattern;
  private int operator;

  /**
   * Default constructor
   */
  public ColumnDateFilterInfo() {
    super();
  }

  /**
   * @param column the column to be filtered
   * @param datePattern the patter of the date formatting used in this column
   * @param primaryDate the date to be used for filtering
   * @param secondaryDate the end date to be used for filtering date ranges
   * @param operator the filter operator
   */
  public ColumnDateFilterInfo(int column, String datePattern, Date primaryDate,
      Date secondaryDate, int operator) {
    super(column);
    this.datePattern = datePattern;
    this.primaryDate = primaryDate;
    this.secondaryDate = secondaryDate;
    this.operator = operator;
  }

  /**
   * @return the operator of this filter
   */
  public int getOperator() {
    return operator;
  }

  /**
   * @return the date that will be used for filtering
   */
  public Date getPrimaryDate() {
    return primaryDate;
  }

  /**
   * @return the end date that will be used for range filtering
   */
  public Date getSecondaryDate() {
    return secondaryDate;
  }

  /*
   * (non-Javadoc)
   * 
   * @seecom.google.gwt.widgetideas.table.client.TableModel.ColumnFilterInfo#
   * isFilterMatchingCellContent(java.lang.String)
   */
  public boolean isFilterMatchingCellContent(String cellContent) {
    if (dateTimeFormat == null) {
      dateTimeFormat = DateTimeFormat.getFormat(datePattern);
    }
    try {
      Date date = dateTimeFormat.parse(cellContent);
      return isFilterMatchingValueObject(date);
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @seecom.google.gwt.widgetideas.table.client.TableModel.ColumnFilterInfo#
   * isFilterMatchingValueObject(java.lang.Object)
   */
  public boolean isFilterMatchingValueObject(Object object) {
    Date date = (Date) object;
    if (primaryDate == null) {
      return false;
    }
    if (operator == EQUAL) {
      return date.equals(primaryDate);
    } else if (operator == NOT_EQUAL) {
      return !date.equals(primaryDate);
    } else if (operator == LESS_THAN) {
      return date.before(primaryDate);
    } else if (operator == GREATER_THAN) {
      return date.after(primaryDate);
    } else if (operator == BETWEEN) {
      if (secondaryDate == null) {
        return false;
      }
      return date.after(primaryDate) && date.before(secondaryDate);
    }
    return false;
  }

  public ColumnFilterInfo copy() {
    return new ColumnDateFilterInfo(getColumn(), datePattern,
        primaryDate, secondaryDate, operator);
  }
}