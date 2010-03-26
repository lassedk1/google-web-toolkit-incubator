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

package com.google.gwt.museum.client.common;

import com.google.gwt.widgetideas.table.client.ClientTableModel;

/**
 * G
 * @author ecc
 *
 */
public class SampleTableModel extends ClientTableModel<Object> {
  public int numRows = 100;
  public int numColumns = 15;

  public void setNumRows(int numRows){
    this.numRows = numRows;
  }
  public void setNumColumns(int numColumns){
    this.numColumns = numColumns;
  }
  
  @Override
  public Object getCell(int rowNum, int cellNum) {
    if (rowNum >= numRows | cellNum >= numColumns) {
      return null;
    }
    return "cell " + rowNum + ", " + cellNum;
  }

  @Override
  protected boolean onRowInserted(int beforeRow) {
    return false;
  }

  @Override
  protected boolean onRowRemoved(int row) {
    return false;
  }

  @Override
  protected boolean onSetData(int row, int cell, Object data) {
    return false;
  }
}
