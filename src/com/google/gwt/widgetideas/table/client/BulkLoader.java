package com.google.gwt.widgetideas.table.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.widgetideas.table.client.BulkLoader.BulkLoadable.StringCellRenderer;

import java.util.Iterator;
import java.util.List;

/**
 * A helper class to enable bulk loading of tables from a {@link TableModel}.
 */
public class BulkLoader {

  /**
   * A bulk loader may have a single {@link OnLoadCompleteCallBack} so the user
   * may continue processing after the table is fully loaded.
   */
  public OnLoadCompleteCallBack onLoadComplete;

  /**
   * CallBack for when loading the table is complete.
   */
  public static interface OnLoadCompleteCallBack {
    public void onLoadComplete();
  }

  /**
   * Time slice in milliseconds that the construction of the string can take
   * before flushing the event cue. As time is only checked periodically, this
   * number should always be set conservatively.
   * <p>
   * Note: TIME_SLICE does not to anything unless rowsPerTimeCheck is set to
   * something other than Integer.MAX_VALUE.
   * </p>
   */
  public static int TIME_SLICE = 1000;

  /**
   * Maximum time allowable to create the table string. Ever.
   * <p>
   * Note: MAX_TIME does not to anything unless rowsPerTimeCheck is set to
   * something other than Integer.MAX_VALUE.
   * </p>
   */
  public int MAX_TIME = 20000;

  /**
   * How many rows should be processed before time is checked and the event loop
   * is potentially flushed.
   */
  private int rowsPerTimeCheck = Integer.MAX_VALUE;

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

  public BulkLoader(BulkLoadable table) {
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
    renderRows(tableModel, startRow, numRows, null);
  }

  /**
   * Init rows removes all rows in the current table and adds the rows supplied
   * by the tableModel.
   * 
   * @param tableModel the table data
   * @param startRow the tableModel's start row index
   * @param numRows the number of rows to request from the tableModel -1
   *          indicates all of them
   * @param headerRow the initial row of the table. As this can have custom
   *          styling, it is passed into the renderer
   */
  public void renderRows(TableModel tableModel, int startRow, int numRows,
      final String headerRow) {
    renderRows(tableModel, startRow, numRows, headerRow, "<td>", "</td>");
  }

  /**
   * Init rows removes all rows in the current table and adds the rows supplied
   * by the tableModel.
   * 
   * @param tableModel the table data
   * @param startRow the tableModel's start row index
   * @param numRows the number of rows to request from the tableModel -1
   *          indicates all of them
   * @param headerRow the initial row of the table. As this can have custom
   *          styling, it is passed into the renderer
   * @param startCell the
   *          <td> with all extra needed tags
   * @param endCell the "</td>
   *          with any extra needed tags
   */
  public void renderRows(TableModel tableModel, int startRow, int numRows,
      final String headerRow, final String startCell, final String endCell) {
    // TODO clear all widgets in widget cache.
    tableModel.requestRows(new TableModel.Request(startRow, numRows),
        new TableModel.Callback() {
          public void onRowsReady(TableModel.Request request,
              final TableModel.Response response) {
            final JavaScriptObject temp = tempNew();
            tempAdd(temp, "<table><tbody>");
            if (headerRow != null) {
              tempAdd(temp, headerRow);
            }
            final long endTime = System.currentTimeMillis() + MAX_TIME;

            class LoadTableCommand implements IncrementalCommand {
              Iterator rows = response.iterator();
              int rowIndex = 0;

              public boolean execute() {
                int checkRow = getRowsPerTimeCheck();
                long endSlice = System.currentTimeMillis() + TIME_SLICE;

                for (; rows.hasNext(); ++rowIndex) {
                  if (--checkRow == 0) {
                    checkRow = getRowsPerTimeCheck();
                    long time = System.currentTimeMillis();
                    if (time > endSlice) {
                      // We only check endTime at each slice.
                      if (time > endTime) {
                        throw new IllegalStateException(
                            "Cannot bulk load a table which takes more than "
                                + MAX_TIME + " milliseconds to load");
                      }
                      return true;
                    }
                  }
                  tempAdd(temp, "<tr>");
                  Iterator row = (Iterator) rows.next();
                  for (int cellIndex = 0; row.hasNext(); ++cellIndex) {
                    tempAdd(temp, startCell);
                    tempAdd(temp, renderer.renderCell(rowIndex, cellIndex,
                        row.next()));
                    tempAdd(temp, endCell);
                  }
                  tempAdd(temp, "</tr>");
                }
                tempAdd(temp, "</tbody> </table>");
                renderRows(toStringTemp(temp));
                if (onLoadComplete != null) {
                  onLoadComplete.onLoadComplete();
                }
                return false;
              }
            }
            LoadTableCommand loadTable = new LoadTableCommand();
            if (getRowsPerTimeCheck() == Integer.MAX_VALUE) {
              loadTable.execute();
            } else {
              DeferredCommand.addCommand(new LoadTableCommand());
            }
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

   /**
    * Sets how many rows should be processed before time is checked for an event flush.  Note: If rowsPerTimeCheck is set to anything other than Integer.MAX_VALUE, then the table is constructed asynchronously.
    * @param rowsPerTimeCheck
    */
  public void setRowsPerTimeCheck(int rowsPerTimeCheck) {
    this.rowsPerTimeCheck = rowsPerTimeCheck;
  }

  public int getRowsPerTimeCheck() {
    return rowsPerTimeCheck;
  }
}
