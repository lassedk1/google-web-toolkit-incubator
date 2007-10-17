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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.widgetideas.table.client.overrides.FlexTable;

import java.util.Iterator;
import java.util.List;

/**
 * Table data, provided by a specific{@link TableModel}, is bulk loaded into
 * the current table.
 */
public class BulkLoadedTable extends FlexTable {

  /**
   * Controls how each cell is rendered. By default, calls toString on the cell
   * data.
   */
  public static class Renderer {
    public String renderCell(int row, int cell, Object cellData) {
      return cellData.toString();
    }
  }

  /**
   * Scratch Element used to load table and row strings.
   */
  private static final Element WRAPPER_DIV = DOM.createElement("div");

  private Renderer renderer = new Renderer();

  /**
   * Sets the table's rows to the list of rows provided.
   * 
   * @param rows list of rows. Each row itself must be another list
   */
  public void renderRows(List rows) {
    TableModel.ListTableModel tableModel = new TableModel.ListTableModel(rows);
    renderRows(tableModel, 0, rows.size());
  }

  /**
   * Sets the table's rows to all the data contained in the given tableModel.
   * 
   * @param tableModel the data tableModel
   */
  public void renderRows(TableModel tableModel) {
    renderRows(tableModel, 0, TableModel.ALL_ROWS);
  }

  /**
   * Init rows removes all rows in the current table and adds the rows supplied
   * by the tableModel.
   * 
   * @param tableModel the table data
   * @param startRow the tableModel's start row index
   * @param numRows the number of rows to request from the tableModel -1
   *          indicates all of them
   */
  public void renderRows(TableModel tableModel, int startRow, int numRows) {
    // TODO clear all widgets in widget cache.
    tableModel.requestRows(new TableModel.Request(startRow, numRows),
        new TableModel.Callback() {
          public void onRowsReady(TableModel.Request request,
              TableModel.Response response) {
            JavaScriptObject temp = tempNew();
            int index = 0;
            tempAdd(temp, "<table><tbody>");
            Iterator rows = response.getRows();
            for (int rowIndex = 0; rows.hasNext(); ++rowIndex) {
              tempAdd(temp, "<tr>");
              Iterator row = (Iterator) rows.next();
              for (int cellIndex = 0; row.hasNext(); ++cellIndex) {
                tempAdd(temp, "<td>");
                tempAdd(temp, renderCell(rowIndex, cellIndex, row.next()));
                tempAdd(temp, "</td>");
              }
              tempAdd(temp, "</tr>");
            }
            tempAdd(temp, "</tbody> </table>");
            renderRows(toStringTemp(temp));
          }
        });
  }

  /**
   * Adds a renderer to the data table to change how the tableModel's cells are
   * rendered.
   * 
   * @param renderer the renderer
   */
  public void setRenderer(Renderer renderer) {
    this.renderer = renderer;
  }

  /**
   * Renders the given cell. Replace the {@link Renderer} to change how the
   * rendering is performed.
   * 
   * @param row row
   * @param cell cell
   * @param cellData cellData used to render cell
   * @return the rendered cell
   */
  protected final String renderCell(int row, int cell, Object cellData) {
    return renderer.renderCell(row, cell, cellData);
  }

  private void renderRows(String rawHTMLTable) {
    // TODO when assertions are added, extra checks should be added to check
    // that the raw html is valid for this table.
    DOM.setInnerHTML(WRAPPER_DIV, rawHTMLTable);
    Element table = DOM.getFirstChild(WRAPPER_DIV);
    Element newBody = renderRowsImpl(this.getElement(), table);
    setBodyElement(newBody);
  }

  /**
   * Helper native method for <code>initRows</code>.
   */
  private native Element renderRowsImpl(Element table, Element thatBody) /*-{ 
    table.removeChild(table.tBodies[0]);
    var thatChild = thatBody.tBodies[0];
    table.appendChild(thatChild);
    return thatChild;
  }-*/;

  private native void tempAdd(JavaScriptObject temp, String stuff) /*-{
    temp[temp.length] = stuff;
  }-*/;

  private native JavaScriptObject tempNew() /*-{
    return [];
  }-*/;

  private native String toStringTemp(JavaScriptObject temp) /*-{ 
    return temp.join("");
  }-*/;

}
