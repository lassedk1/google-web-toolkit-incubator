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
package com.google.gwt.widgetideas.table.client.filter;

import com.google.gwt.widgetideas.table.client.TableModel.ColumnFilterInfo;

/**
 * 
 */
public class ColumnNumberFilterInfo extends ColumnFilterInfo {
  public static final int EQUAL = 0;
  public static final int NOT_EQUAL = 1;
  public static final int LESS_THAN = 2;
  public static final int GREATER_THAN = 3;
  public static final int BETWEEN = 4;
  public static final int COUNT_OPERATORS = 5;

  private Float primaryNumber, secondaryNumber;
  private int operator;

  public ColumnNumberFilterInfo() {
    super();
  }

  public ColumnNumberFilterInfo(int column, Float primaryNumber,
      Float secondaryNumber, int operator) {
    super(column);
    this.primaryNumber = primaryNumber;
    this.secondaryNumber = secondaryNumber;
    this.operator = operator;
  }

  public int getOperator() {
    return operator;
  }

  public float getPrimaryNumber() {
    return primaryNumber;
  }

  public float getSecondaryNumber() {
    return secondaryNumber;
  }

  public boolean isFilterMatchingCellContent(String cellContent) {
    try {
      Float number = Float.parseFloat(cellContent);
      return isFilterMatchingValueObject(number);
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public boolean isFilterMatchingValueObject(Object object) {
    // Empty filter will match everything
    if (primaryNumber == null) {
      return true;
    }
    Number number = (Number) object;
    if (operator == EQUAL) {
      return number.floatValue() == primaryNumber;
    } else if (operator == NOT_EQUAL) {
      return number.floatValue() != primaryNumber;
    } else if (operator == LESS_THAN) {
      return number.floatValue() < primaryNumber;
    } else if (operator == GREATER_THAN) {
      return number.floatValue() > primaryNumber;
    } else if (operator == BETWEEN) {
      return number.floatValue() >= primaryNumber
          && number.floatValue() <= secondaryNumber;
    }
    return false;
  }
}