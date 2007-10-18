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
package com.google.gwt.widgetideas.table.client;

/**
 * An interface for tables that manipulate columns using a fixed table width.
 */
public interface HasFixedColumnWidth extends HasColumns {
  /**
   * Gets the number of columns in this table.
   * 
   * @return the number of columns
   */
  public int getColumnCount();
  
  /**
   * Get the width of a column in pixels.
   * 
   * @param column the column index
   * @return the column width
   */
  public int getColumnWidth(int column);

  /**
   * Get the default width of a column in pixels.
   * 
   * @return the default column width
   */
  public int getDefaultColumnWidth();

  /**
   * Set the width of a column in pixels.
   * 
   * @param column the column index
   * @param width the column width
   */
  public void setColumnWidth(int column, int width);
}
