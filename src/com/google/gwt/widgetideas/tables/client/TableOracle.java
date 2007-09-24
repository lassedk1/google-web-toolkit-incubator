/*
 * Copyright 2007 Google Inc.
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
package com.google.gwt.widgetideas.tables.client;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Collection;

/**
 * A class to retrieve row data to be used in a table.
 */
public abstract class TableOracle {
  /**
   * Callback for {@link TableOracle}. Every Request should be associated
   * with a callback that should be called after a Response is generated.
   */
  public static interface Callback {
    /**
     * Consume the data created by {@link TableOracle} in response to a
     * Request.
     * 
     * @param request the request
     * @param response the response
     */
    public void onRowsReady(Request request, Response response);
  }

  /**
   * A {@link TableOracle} request.
   */
  public static class Request implements IsSerializable {
    /**
     * The first row of table data to request.
     */
    private int startRow;

    /**
     * The number of rows to request.
     */
    private int numRows;

    /**
     * Constructor.
     */
    public Request() {
    }
    
    /**
     * Constructor.
     * 
     * @param startRow the first row to request
     * @param numRows the number of rows to request
     */
    public Request(int startRow, int numRows) {
      this.startRow = startRow;
      this.numRows = numRows;
    }

    /**
     * Get the number of rows to request.
     * 
     * @return the number of requested rows
     */
    public int getNumRows() {
      return numRows;
    }

    /**
     * Get the first row to request.
     * 
     * @return the first requested row
     */
    public int getStartRow() {
      return startRow;
    }
  }

  /**
   * A {@link TableOracle} response.
   */
  public static class Response implements IsSerializable {

    /**
     * A collection of rows.
     */
    private Collection/* <Collection<Object>> */rows;

    /**
     * Constructor.
     */
    public Response() {
    }
    
    /**
     * Constructor. Create a response with the data to enter into each cell of
     * each row.
     * 
     * @param rows the data for the table
     */
    public Response(Collection/* <Collection<Object>> */rows) {
      this.rows = rows;
    }

    /**
     * Get the rows data.
     * 
     * @return the rows data
     */
    public Collection/* <Collection<Object>> */getRows() {
      return rows;
    }
  }

  /**
   * Generate a Response based on a specific Request. After the is created, it
   * is passed into the Callback.
   * 
   * @param startRow the first row to request
   * @param numRows the number of rows to request
   * @param callback the callback to use for the response
   */
  public void requestRows(int startRow, int numRows, Callback callback) {
    requestRows(new Request(startRow, numRows), callback);
  }

  /**
   * Generate a Response based on a specific Request. After the is created, it
   * is passed into the Callback.
   * 
   * @param request the request
   * @param callback the callback to use for the response
   */
  public abstract void requestRows(Request request, Callback callback);
}
