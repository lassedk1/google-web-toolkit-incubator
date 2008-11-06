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

/**
 * 
 */
public class ColumnTextFilterInfo extends ColumnFilterInfo {
  public static final int STARTS_WITH = 0;
  public static final int STARTS_WITH_CASE_SENSITIVE = 1;
  public static final int CONTAINS = 2;
  public static final int CONTAINS_CASE_SENSITIVE = 3;
  public static final int ENDS_WITH = 4;
  public static final int ENDS_WITH_CASE_SENSITIVE = 5;
  public static final int COUNT_OPERATORS = 6;

  private String filterText;
  private int operator;

  public ColumnTextFilterInfo() {
    super();
  }

  public ColumnTextFilterInfo(int column, String filterText, int operator) {
    super(column);
    this.filterText = filterText;
    this.operator = operator;
  }

  public String getFilterText() {
    return filterText;
  }

  public int getOperator() {
    return operator;
  }

  public boolean isFilterMatchingCellContent(String cellContent) {
    return isFilterMatchingValueObject(cellContent);
  }

  public boolean isFilterMatchingValueObject(Object object) {
    String cellContent = (String) object;
    if (operator == STARTS_WITH) {
      return cellContent.toLowerCase().startsWith(filterText.toLowerCase());
    } else if (operator == STARTS_WITH_CASE_SENSITIVE) {
      return cellContent.startsWith(filterText);
    } else if (operator == CONTAINS) {
      return cellContent.toLowerCase().contains(filterText.toLowerCase());
    } else if (operator == CONTAINS_CASE_SENSITIVE) {
      return cellContent.contains(filterText);
    } else if (operator == ENDS_WITH) {
      return cellContent.toLowerCase().endsWith(filterText.toLowerCase());
    } else if (operator == ENDS_WITH_CASE_SENSITIVE) {
      return cellContent.endsWith(filterText);
    }
    return false;
  }

  public ColumnFilterInfo copy() {
    return new ColumnTextFilterInfo(getColumn(), filterText, operator);
  }
}