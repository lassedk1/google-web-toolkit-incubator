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

import com.google.gwt.user.client.Element;

import java.util.Iterator;

/**
 * Helper class to bulk load {@link FixedWidthGrid} tables.
 * 
 */
public class PagingGridBulkRenderer extends FixedWidthGridBulkRenderer {

  /**
   * The renderer used to render an entire table when a data request is
   * received.
   */
  protected class PagingGridRendererAdaptor implements PagingGrid.TableRenderer {
    RenderingOptions options = new RenderingOptions();

    public void renderTable(PagingGrid grid, Iterator rows,
        RendererCallback callback) {
      options.callback = callback;
      renderRows(rows, options);
    }
  }

  /**
   * Constructor.
   * 
   * @param table the table to be bulk rendered
   * @param numColumns number of columns in the table
   */
  public PagingGridBulkRenderer(PagingGrid table, int numColumns) {
    super(table,numColumns);
    table.setTableRenderer(new PagingGridRendererAdaptor());
  }

  protected native Element getBulkLoadedGhostRow(Element elem) /*-{
    return elem.tbodies[0].rows[0];
   }-*/;
}
