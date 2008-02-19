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
 * An interface for tables that can do paging.
 */
public interface HasRowPaging extends SourceRowPagingEvents,
    HasFixedColumnWidth {
  /**
   * @return the current page
   */
  int getCurrentPage();

  /**
   * Get the number of pages. If the number of pages is unknown, -1 is returned.
   * 
   * @return the page count
   */
  int getNumPages();

  /**
   * Get the number of rows per page.
   * 
   * @return the number of rows per page
   */
  int getPageSize();

  /**
   * Go to the first page.
   */
  void gotoFirstPage();

  /**
   * Go to the last page.
   */
  void gotoLastPage();

  /**
   * Set the current page. If the page is out of bounds, it will be
   * automatically set to zero or the last page without throwing any errors.
   * 
   * @param page the page
   * @param forced reload the page even if it is already loaded
   */
  void gotoPage(int page, boolean forced);

  /**
   * Set the number of rows per page.
   * 
   * By default, the page size is zero, which indicates that all rows should be
   * shown on the page.
   * 
   * @param pageSize the number of rows per page
   */
  void setPageSize(int pageSize);
}
