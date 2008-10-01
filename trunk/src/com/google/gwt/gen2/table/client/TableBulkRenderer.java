/*
 * Copyright 2008 Google Inc.
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

package com.google.gwt.gen2.table.client;

import com.google.gwt.core.client.Duration;
import com.google.gwt.gen2.table.client.TableDefinition.HTMLCellView;
import com.google.gwt.gen2.table.client.TableModelHelper.Request;
import com.google.gwt.gen2.table.client.TableModelHelper.Response;
import com.google.gwt.gen2.table.client.overrides.HTMLTable;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Enables bulk rendering of tables. Each subclass that needs special handling
 * for bulk rendering should have its own bulk renderer.
 * 
 * @param <RowType> the data type of the row values
 */
public abstract class TableBulkRenderer<RowType> {
  /**
   * A simple class that keeps track of a widget that needs to be add to the
   * table after it finishes loading.
   */
  private static class DelayedWidget {
    public int cellIndex;
    public int rowIndex;
    public Widget widget;

    /**
     * Construct a new {@link DelayedWidget}.
     */
    public DelayedWidget(int rowIndex, int cellIndex, Widget widget) {
      this.rowIndex = rowIndex;
      this.cellIndex = cellIndex;
      this.widget = widget;
    }
  }

  /**
   * A customized {@link HTMLCellView} used by the {@link TableBulkRenderer}.
   */
  protected class BulkCellView extends HTMLCellView<RowType> {
    /**
     * A {@link StringBuffer} used to assemble the HTML of the table.
     */
    private StringBuffer buffer;

    /**
     * The horizontal alignment to apply to the current cell.
     */
    private HorizontalAlignmentConstant curCellHorizontalAlign = null;

    /**
     * The style attributes to apply to the current cell.
     */
    private Map<String, String> curCellStyles = new HashMap<String, String>();

    /**
     * The style name to be applied to the current cell.
     */
    private String curCellStyleName = null;

    /**
     * A boolean indicating that the current cell's tag has been added.
     */
    private boolean curCellTagAdded = false;

    /**
     * The vertical alignment to apply to the current cell.
     */
    private VerticalAlignmentConstant curCellVerticalAlign = null;

    /**
     * The {@link Widget Widgets} that need to be added to the table after it
     * has finished loading.
     */
    private List<DelayedWidget> delayedWidgets = new ArrayList<DelayedWidget>();

    /**
     * The absolute end time of the rendering process. If the table takes longer
     * than this to render, an error will be thrown.
     */
    private double endTime;

    /**
     * The {@link RenderingOptions} to apply to the table.
     */
    private RenderingOptions options;

    /**
     * Construct a new {@link BulkCellView}.
     * 
     * @param options the {@link RenderingOptions} to apply to the table
     */
    public BulkCellView(RenderingOptions options) {
      this.options = options;
      endTime = Duration.currentTimeMillis() + MAX_TIME;

      // Create a string buffer to assemble the table
      buffer = new StringBuffer();
      buffer.append("<table><tbody>");
      if (options.headerRow != null) {
        buffer.append(options.headerRow);
      }
    }

    @Override
    public void addHTML(String html) {
      maybeAddOpenTag();
      buffer.append(html);
    }

    @Override
    public void setHorizontalAlignment(HorizontalAlignmentConstant align) {
      assert !curCellTagAdded : "You cannot call setHorizontalAlignment() after calling addHTML()";
      curCellHorizontalAlign = align;
    }

    @Override
    public void setStyleAttribute(String attr, String value) {
      assert !curCellTagAdded : "You cannot call setStyleAttribute() after calling addHTML()";
      curCellStyles.put(attr, value);
    }

    @Override
    public void setStyleName(String stylename) {
      assert !curCellTagAdded : "You cannot call setStyleName() after calling addHTML()";
      curCellStyleName = stylename;
    }

    @Override
    public void setVerticalAlignment(VerticalAlignmentConstant align) {
      assert !curCellTagAdded : "You cannot call setVerticalAlignment() after calling addHTML()";
      curCellVerticalAlign = align;
    }

    @Override
    public void setWidget(Widget widget) {
      int row = getRowIndex();
      int cell = getCellIndex();
      delayedWidgets.add(new DelayedWidget(row, cell, widget));
    }

    protected StringBuffer getStringBuffer() {
      return buffer;
    }

    @Override
    protected void renderCellImpl(RowType rowValue,
        ColumnDefinition<RowType, ?> columnDef) {
      curCellTagAdded = false;
      curCellStyleName = null;
      curCellHorizontalAlign = null;
      curCellVerticalAlign = null;
      curCellStyles.clear();
      super.renderCellImpl(rowValue, columnDef);
      maybeAddOpenTag();
      buffer.append("</td>");
    }

    @Override
    protected void renderRowImpl(RowType rowValue,
        List<ColumnDefinition<RowType, ?>> visibleColumns) {
      buffer.append("<tr>");
      super.renderRowImpl(rowValue, visibleColumns);
      buffer.append("</tr>");
    }

    @Override
    protected void renderRowsImpl(final Iterator<RowType> rowValues,
        final List<ColumnDefinition<RowType, ?>> visibleColumns) {
      // Reset the row index
      setRowIndex(0);
      final int myStamp = ++requestStamp;

      // Use an incremental command to render rows in increments
      class RenderTableCommand implements IncrementalCommand {
        public boolean execute() {
          // Poor man's cancel() event.
          if (myStamp != requestStamp) {
            return false;
          }
          int checkRow = ROWS_PER_TIME_CHECK;
          double endSlice = Duration.currentTimeMillis() + TIME_SLICE;

          // Loop through the rows
          while (rowValues.hasNext()) {
            // Check if we've exceed the time slice
            if (options.syncCall == false && --checkRow == 0) {
              checkRow = ROWS_PER_TIME_CHECK;
              double time = Duration.currentTimeMillis();
              if (time > endSlice) {
                // We only check endTime at each slice.
                if (time > endTime) {
                  throw new IllegalStateException(
                      "Cannot bulk render a table which takes more than "
                          + MAX_TIME + " milliseconds to render");
                }
                return true;
              }
            }

            // Render a single row and increment the row index
            renderRowImpl(rowValues.next(), visibleColumns);
            setRowIndex(getRowIndex() + 1);
          }

          // Finish rendering the table
          buffer.append("</tbody></table>");
          renderRows(buffer.toString());

          // Add widgets into the table
          for (DelayedWidget dw : delayedWidgets) {
            table.setWidget(dw.rowIndex, dw.cellIndex, dw.widget);
          }

          // Trigger the callback
          if (options.callback != null) {
            options.callback.onRendered();
          }
          return false;
        }
      }

      // Fire the incremental command
      RenderTableCommand renderTable = new RenderTableCommand();
      if (renderTable.execute()) {
        DeferredCommand.addCommand(renderTable);
      }
    }

    /**
     * Add the open tag to the current cell if it hasn't been added already.
     * Also add all markups to the cell including the style name, alignment, and
     * style attributes.
     */
    private void maybeAddOpenTag() {
      if (!curCellTagAdded) {
        curCellTagAdded = true;
        buffer.append("<td");
        if (curCellHorizontalAlign != null) {
          buffer.append(" align=\"");
          buffer.append(curCellHorizontalAlign.getTextAlignString());
          buffer.append("\"");
        }
        if (curCellVerticalAlign != null) {
          curCellStyles.put("verticalAlign",
              curCellVerticalAlign.getVerticalAlignString());
        }
        if (curCellStyleName != null) {
          buffer.append(" class=\"");
          buffer.append(curCellStyleName);
          buffer.append("\"");
        }
        if (curCellStyles.size() > 0) {
          buffer.append(" style=\"");
          for (Map.Entry<String, String> entry : curCellStyles.entrySet()) {
            buffer.append(entry.getKey());
            buffer.append(":");
            buffer.append(entry.getValue());
            buffer.append(";");
          }
          buffer.append("\"");
        }
        buffer.append(">");
      }
    }
  }

  /**
   * Convenience class used to specify rendering options for the table.
   */
  protected class RenderingOptions {
    public int startRow = 0;
    public int numRows = MutableTableModel.ALL_ROWS;
    public boolean syncCall = false;
    public String headerRow = null;
    public RendererCallback callback = null;
  }

  /**
   * Time slice in milliseconds that the construction of the string can take
   * before flushing the event cue.
   */
  public static int TIME_SLICE = 1000;

  /**
   * Maximum time allowable to create the table string. Ever.
   */
  public static int MAX_TIME = 20000;

  /**
   * How many rows should be processed before time is checked and the event loop
   * is potentially flushed.
   */
  // TODO check how long time check takes to see if this guard is worth having.
  public static int ROWS_PER_TIME_CHECK = 10;

  /**
   * Scratch Element used to render table and row strings.
   */
  private static final Element WRAPPER_DIV = DOM.createElement("div");

  /**
   * Stamp used to detect when a request has been orphaned.
   */
  private int requestStamp = 0;

  /**
   * Table to be bulk rendered.
   */
  private final HTMLTable table;

  /**
   * The definition of the columns.
   */
  private TableDefinition<RowType> tableDefinition;

  /**
   * Constructor for the bulk renderer.
   * 
   * @param table the table to be bulk rendered
   * @param tableDefinition the renderer that should be used during bulk
   *          rendering
   */
  public TableBulkRenderer(HTMLTable table,
      TableDefinition<RowType> tableDefinition) {
    this.table = table;
    setTableDefinition(tableDefinition);
  }

  /**
   * @return the {@link TableDefinition} that defines the columns
   */
  public TableDefinition<RowType> getTableDefinition() {
    return tableDefinition;
  }

  /**
   * Removes all rows in the current table replaces them with the rows provided.
   * <p>
   * This method should only be used when the number of rows is known and of
   * reasonable size, therefore this call is synchronous by default.
   * </p>
   * 
   * @param rows {@link Iterable} of row values
   */
  public final void renderRows(Iterable<RowType> rows) {
    renderRows(rows, null);
  }

  /**
   * Removes all rows in the current table replaces them with the rows provided.
   * <p>
   * This method should only be used when the number of rows is known and of
   * reasonable size, therefore this call is synchronous by default.
   * </p>
   * 
   * @param rows {@link Iterable} of row values
   * @param callback callback to be called after the rows are rendered
   */
  public final void renderRows(Iterable<RowType> rows, RendererCallback callback) {
    IterableTableModel<RowType> tableModel = new IterableTableModel<RowType>(
        rows);
    RenderingOptions options = createRenderingOptions();
    options.syncCall = true;
    options.callback = callback;
    renderRows(tableModel, options);
  }

  /**
   * Removes all rows in the current table replaces them with the rows supplied
   * by the iterator. Each element of the rows iterator is an {@link Iterator}
   * which represents a single row.
   * 
   * @param rows iterator of row values
   * @param callback callback to be called after the rows are rendered
   */
  public final void renderRows(Iterator<RowType> rows, RendererCallback callback) {
    RenderingOptions options = createRenderingOptions();
    options.callback = callback;
    renderRows(rows, options);
  }

  /**
   * Removes all rows in the current table and replaces them with the rows
   * supplied by the provided {@link MutableTableModel}.
   * 
   * @param tableModel the table data
   * @param startRow the tableModel's start row index
   * @param numRows the number of rows to request from the tableModel -1
   *          indicates all of them *
   * @param callback callback to call after the table is finished being rendered
   */
  public final void renderRows(MutableTableModel<RowType> tableModel,
      int startRow, int numRows, RendererCallback callback) {
    RenderingOptions options = createRenderingOptions();
    options.startRow = startRow;
    options.numRows = numRows;
    options.callback = callback;
    renderRows(tableModel, options);
  }

  /**
   * Removes all rows in the current table and replaces them with the rows
   * supplied by the provided {@link MutableTableModel}.
   * 
   * @param tableModel the table model
   * @param callback callback to call after the table is finished being rendered
   */
  public final void renderRows(MutableTableModel<RowType> tableModel,
      RendererCallback callback) {
    renderRows(tableModel, 0, MutableTableModel.ALL_ROWS, callback);
  }

  /**
   * Sets the {@link TableDefinition} that defines the columns.
   * 
   * @param tableDefinition the {@link TableDefinition} that defines the columns
   */
  public void setTableDefinition(TableDefinition<RowType> tableDefinition) {
    this.tableDefinition = tableDefinition;
  }

  /**
   * Create a {@link HTMLCellView} that can be used to render the table.
   * 
   * @param options the {@link RenderingOptions}
   * @return the cell view
   */
  protected HTMLCellView<RowType> createCellView(final RenderingOptions options) {
    return new BulkCellView(options);
  }

  /**
   * Creates the rendering options associated with this renderer.
   * 
   * @return the rendering options
   */
  protected RenderingOptions createRenderingOptions() {
    return new RenderingOptions();
  }

  /**
   * Gets the table.
   * 
   * @returns the current html table.
   */
  protected HTMLTable getTable() {
    return table;
  }

  /**
   * Work horse protected rendering method.
   * 
   * @param rows Iterator of row iterators
   * @param options rendering options for this table
   */
  protected void renderRows(final Iterator<RowType> rows,
      final RenderingOptions options) {
    getTableDefinition().renderRows(rows, createCellView(options));
  }

  /**
   * Render rows using a table model.
   * 
   * @param tableModel table model
   * @param options options
   */
  protected final void renderRows(TableModel<RowType> tableModel,
      final RenderingOptions options) {

    // Create a callback to handle the request
    TableModel.Callback<RowType> requestCallback = new TableModel.Callback<RowType>() {
      public void onFailure(Throwable caught) {
      }

      public void onRowsReady(Request request, final Response<RowType> response) {
        final Iterator<RowType> rows = response.getRowValues();
        renderRows(rows, options);
      }
    };

    tableModel.requestRows(new Request(options.startRow, options.numRows),
        requestCallback);
  }

  protected void renderRows(String rawHTMLTable) {
    DOM.setInnerHTML(WRAPPER_DIV, rawHTMLTable);
    Element tableElement = DOM.getFirstChild(WRAPPER_DIV);
    Element newBody = replaceBodyElement(table.getElement(), tableElement);
    setBodyElement(table, newBody);
  }

  /**
   * Replace a table's body element with the body element from another table.
   * 
   * @param table the table element who's body will be replaced
   * @param thatBody the table element with the donor body
   */
  private native Element replaceBodyElement(Element table, Element thatBody) /*-{
    table.removeChild(table.tBodies[0]);
    var thatChild = thatBody.tBodies[0];
    table.appendChild(thatChild);
    return thatChild;
  }-*/;

  /**
   * Short term hack to get protected setBodyElement.
   */
  private native void setBodyElement(HTMLTable table, Element newBody) /*-{
    table.@com.google.gwt.gen2.table.client.overrides.HTMLTable::setBodyElement(Lcom/google/gwt/user/client/Element;)(newBody);
  }-*/;
}
