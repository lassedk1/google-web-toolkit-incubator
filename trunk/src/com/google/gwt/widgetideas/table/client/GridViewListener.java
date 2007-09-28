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
 * Event listener interface for {@link GridView} events.
 */
public interface GridViewListener {
  /**
   * Fired when the number of rows changes.
   * 
   * @param numRows the new number of rows
   * @param numPages the new number of pages
   */
  public void onNumRowsChanges(int numRows, int numPages);
  
  /**
   * Fired when the current page changes.
   * 
   * @param page the new page
   */
  public void onPageChanged(int page);
  
  /**
   * Fired when the current page has completely finished loading.
   */
  public void onPageLoaded();

  /**
   * Fired when the page size changes.
   * 
   * @param pageSize the new page size
   * @param numPages the number of pages
   */
  public void onPageSizeChange(int pageSize, int numPages);
}
