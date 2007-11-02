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
 * An interface for tables that support automatic resizing of columns to fit the
 * contents of the column based on the content of the column.
 */
public interface HasAutoFitColumn extends HasFixedColumnWidth {
  /**
   * Stretches or shrinks the column to automatically fit its data content.
   * 
   * @param column the column to fit
   * @return the new column width
   * @throws IndexOutOfBoundsException
   */
  public int autoFitColumnWidth(int column);

  /**
   * Calculate the width required for a content to fit its contents.
   * 
   * @param column the column that will be fitted
   * @return the default column width
   */
  public int getAutoFitColumnWidth(int column);
}
