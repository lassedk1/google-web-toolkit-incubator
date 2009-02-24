/*
 * Copyright 2009 Google Inc.
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
package com.google.gwt.gen2.table.client.property;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link ColumnProperty} that provides information about the headers above a
 * column. The row indexes start from the bottom of the header, such that all
 * headers at the 0th index refer to the row directly above the data table.
 */
public class HeaderProperty extends ColumnProperty {
  /**
   * Property type.
   */
  public static final Type<HeaderProperty> TYPE = new Type<HeaderProperty>() {
    private HeaderProperty instance;

    @Override
    public HeaderProperty getDefault() {
      if (instance == null) {
        instance = new HeaderProperty();
      }
      return instance;
    }
  };

  private int headerCount;
  private Map<Integer, Object> headers = new HashMap<Integer, Object>();

  /**
   * Get the header at the given row index.
   * 
   * @param row the row index from the bottom.
   * @return the header for the given row
   */
  public Object getHeader(int row) {
    return headers.get(new Integer(row));
  }

  /**
   * @return get the number of headers above the column
   */
  public int getHeaderCount() {
    return headerCount;
  }

  /**
   * Remove the header above this column for the specified row.
   * 
   * @param row the row index from the bottom
   */
  public void removeHeader(int row) {
    headers.remove(new Integer(row));
  }

  /**
   * Set the header above this column. The row index starts with the bottom row,
   * which is reverse of a normal table. The headerCount will automatically be
   * increased to accommodate the row.
   * 
   * @param row the row index from the bottom
   * @param header the header
   */
  public void setHeader(int row, Object header) {
    headers.put(new Integer(row), header);
    headerCount = Math.max(headerCount, row + 1);
  }

  /**
   * Set the number of headers above the column.
   * 
   * @param headerCount the number of headers
   */
  public void setHeaderCount(int headerCount) {
    this.headerCount = headerCount;
  }
}
