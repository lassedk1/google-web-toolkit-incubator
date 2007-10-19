package com.google.gwt.widgetideas.table.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.widgetideas.table.client.BulkLoadableImpl.BulkLoadable.StringCellRenderer;

import java.util.Iterator;
import java.util.List;

/**
 * A helper class to enable bulk loading of tables from a {@link TableModel}.
 */
class BulkLoadableImpl {

  public static interface BulkLoadable {

    /**
     * Controls how each cell is rendered. By default, calls toString on the
     * cell data.
     */
    public static class StringCellRenderer {
      public String renderCell(int row, int cell, Object cellData) {
        return cellData.toString();
      }
    }

    public StringCellRenderer getRenderer();

    public Element getElement();

    public void setBodyElement(Element newBody);
  }

  BulkLoadable table;
  StringCellRenderer renderer;

  public BulkLoadableImpl(BulkLoadable table) {
    this.table = table;
    this.renderer = table.getRenderer();
  }

  /**
   * Scratch Element used to load table and row strings.
   */
  private static final Element WRAPPER_DIV = DOM.createElement("div");

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
            tempAdd(temp, "<table><tbody>");
            Iterator rows = response.getRows();
            for (int rowIndex = 0; rows.hasNext(); ++rowIndex) {
              tempAdd(temp, "<tr>");
              Iterator row = (Iterator) rows.next();
              for (int cellIndex = 0; row.hasNext(); ++cellIndex) {
                tempAdd(temp, "<td>");
                tempAdd(temp, renderer.renderCell(rowIndex, cellIndex,
                    row.next()));
                tempAdd(temp, "</td>");
              }
              tempAdd(temp, "</tr>");
            }
            tempAdd(temp, "</tbody> </table>");
            renderRows(toStringTemp(temp));
          }
        });
  }

  private void renderRows(String rawHTMLTable) {
    // TODO when assertions are added, extra checks should be added to check
    // that the raw html is valid for this table.
    DOM.setInnerHTML(WRAPPER_DIV, rawHTMLTable);
    Element tableElement = DOM.getFirstChild(WRAPPER_DIV);
    Element newBody = renderRowsImpl(table.getElement(), tableElement);
    table.setBodyElement(newBody);
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
