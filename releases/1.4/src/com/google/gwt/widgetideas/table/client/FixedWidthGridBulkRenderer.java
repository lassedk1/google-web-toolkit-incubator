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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.widgetideas.table.client.overrides.HTMLTable;

import java.util.Iterator;

/**
 * Helper class to bulk load {@link FixedWidthGrid} tables.
 * 
 */
public class FixedWidthGridBulkRenderer extends GridBulkRenderer {

  /**
   * Chaining callback for fixed width grid.
   */
  protected class ChainingCallback extends GridBulkRenderer.ChainingCallback {
    RendererCallback userCallback;

    ChainingCallback(RendererCallback userCallback) {
      super(userCallback);
    }

    public void localOnRendered() {
      // We're saving an extra object creation by calling super rather than
      // chaining them.
      super.localOnRendered();
      Element newGhostRow = getBulkLoadedGhostRow(getTable());
      
      // Set the ghost row to point at correct location
      ((FixedWidthGrid) getTable()).setGhostRow(newGhostRow);
      if (userCallback != null) {
        userCallback.onRendered();
      }
    }
  }

  /**
   * Constructor. Takes in the number of columns in the table to allow efficient
   * creation of the header row.
   * 
   * @param table table to be bulk rendered
   * @param numColumns number of columns in table
   */
  public FixedWidthGridBulkRenderer(FixedWidthGrid table, int numColumns) {
    super(table);
    table.resizeColumns(numColumns);
  }

  protected void addChainingCallback(final RenderingOptions options) {
    // Set the callback.
    options.callback = new ChainingCallback(options.callback);
  }

  /**
   * Gets the new ghost element from the table.
   * 
   * @param table the table
   * @return the new ghost row
   */
  protected native Element getBulkLoadedGhostRow(HTMLTable table) /*-{
    return table.@com.google.gwt.widgetideas.table.client.overrides.HTMLTable::getBodyElement()(table).rows[0];
    }-*/;

  protected void renderRows(Iterator iterator, final RenderingOptions options) {
    options.startCell = "<td><span>";
    options.endCell = "</span></td>";
    FixedWidthGrid table = (FixedWidthGrid) super.getTable();
    if (table.getColumnCount() == 0) {
      throw new IllegalStateException(
          "Bulk Rendered FixedWidthGrids must call resizeColumns() before being loaded");
    }
    table.updateGhostRow();
    options.headerRow = DOM.toString(table.getGhostRow());
    super.renderRows(iterator, options);
  }
}
