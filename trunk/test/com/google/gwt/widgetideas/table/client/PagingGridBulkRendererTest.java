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
 * {@link FlexTableBulkRenderer} test.
 * 
 */
public class PagingGridBulkRendererTest extends TableBulkRendererTestBase {

  protected Object[] createTableAndRenderer() {
    throw new UnsupportedOperationException(
        "Use gotoPage to test bulk loading of paging grids");
  }

  protected void doTest(TableModel model, final TestCallback callback) {
    delayTestFinish(TIME_OUT);
    PagingGrid table = new PagingGrid(model);
    callback.table = table;
    table.addRowPagingListener(new RowPagingListener() {

      public void onNumPagesChanges(int numPages) {
      }

      public void onPageChanged(int page) {
      }

      public void onPageLoaded(int page) {
        callback.onRendered();
      }

      public void onPagingFailure(Throwable caught) {
      }

    });
    // In a real bulk renderer the number of columns would have to be correct.
    new PagingGridBulkRenderer(table, 5);
    table.gotoFirstPage();
  }

}
