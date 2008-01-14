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
package com.google.gwt.demos.scrolltable.client;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.widgetideas.table.client.TableModel.ColumnSortList;
import com.google.gwt.widgetideas.table.client.TableModel.SerializableResponse;
import com.google.gwt.widgetideas.table.client.TableModel.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataSourceServiceImpl extends RemoteServiceServlet implements
    DataSourceService {
  /**
   * The source of the data.
   */
  private DataSourceData data = new DataSourceData() {
    public int getRandomInt(int max) {
      return random.nextInt(max);
    }
  };

  /**
   * A random number generator.
   */
  private Random random = new Random();

  public SerializableResponse requestRows(Request request) {
    // Get the sort info, even though we ignore it
    ColumnSortList sortList = request.getColumnSortList();
    int primarySortColumn = sortList.getPrimaryColumn();
    boolean ascending = sortList.isPrimaryAscending();

    // Create some fake data
    List rowData = new ArrayList();
    int startRow = request.getStartRow();
    int lastRow = startRow + request.getNumRows();
    for (int row = startRow; row < lastRow; row++) {
      List cellData = new ArrayList();
      for (int col = 0; col < 12; col++) {
        cellData.add(data.getCell(row, col));
      }
      rowData.add(cellData);
    }

    // Return the data in a Default Response
    return new SerializableResponse(rowData);
  }
}
