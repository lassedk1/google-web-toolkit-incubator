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

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

import java.util.Iterator;
import java.util.List;

/**
 * A collection of {@link ColumnDefinition ColumnDefinitions} that define a
 * table.
 * 
 * @param <RowType> the type of the row values
 */
public interface TableDefinition<RowType> {
  /**
   * A hook into where the rendered cell will be displayed.
   * 
   * @param <RowType> the type of the row values
   */
  public abstract static class AbstractCellView<RowType> {
    private int cellIndex = 0;
    private int rowIndex = 0;
    private HasTableDefinition<RowType> source = null;

    /**
     * Construct a new {@link TableDefinition.AbstractCellView}.
     * 
     * @param sourceTableDef the {@link HasTableDefinition} that defined the
     *          cell view
     */
    public AbstractCellView(HasTableDefinition<RowType> sourceTableDef) {
      this.source = sourceTableDef;
    }

    /**
     * @return the actual cell index being rendered
     */
    public int getCellIndex() {
      return cellIndex;
    }

    /**
     * @return the actual row index being rendered
     */
    public int getRowIndex() {
      return rowIndex;
    }

    /**
     * @return the source of the {@link TableDefinition}
     */
    public HasTableDefinition<RowType> getSourceTableDefinition() {
      return source;
    }

    /**
     * Render a single cell.
     * 
     * @param rowValue the row value associated with the row
     * @param columnDef the {@link ColumnDefinition} associated with the column
     */
    protected abstract void renderCellImpl(RowType rowValue,
        ColumnDefinition<RowType, ?> columnDef);

    /**
     * Render all of the cells in a single row.
     * 
     * @param rowValue the row value associated with the row
     * @param visibleColumns the list of visible {@link ColumnDefinition}
     */
    protected void renderRowImpl(RowType rowValue,
        List<ColumnDefinition<RowType, ?>> visibleColumns) {
      int numColumns = visibleColumns.size();
      for (int i = 0; i < numColumns; i++) {
        cellIndex = i;
        renderCellImpl(rowValue, visibleColumns.get(i));
      }
    }

    /**
     * Render the rows in the table given the list of visible
     * {@link ColumnDefinition ColumnDefinitions}.
     * 
     * @param startRowIndex the index of the first row to render
     * @param rowValues the values associated with each row
     * @param visibleColumns the list of visible {@link ColumnDefinition}
     */
    protected void renderRowsImpl(int startRowIndex,
        Iterator<RowType> rowValues,
        List<ColumnDefinition<RowType, ?>> visibleColumns) {
      rowIndex = startRowIndex;
      while (rowValues.hasNext()) {
        renderRowImpl(rowValues.next(), visibleColumns);
        rowIndex++;
      }
    }

    /**
     * Set the current cell index.
     * 
     * @param cellIndex the current row index
     */
    protected void setCellIndex(int cellIndex) {
      this.cellIndex = cellIndex;
    }

    /**
     * Set the current row index.
     * 
     * @param rowIndex the current row index
     */
    protected void setRowIndex(int rowIndex) {
      this.rowIndex = rowIndex;
    }
  }

  /**
   * <p>
   * The {@link TableDefinition.AbstractCellView} used by views that render the
   * table as a single HTML string.
   * </p>
   * <p>
   * Views that use this method build the table as you go, so you may not be
   * able to call methods out of order. For example, you cannot set style
   * attributes after you've set the html or text in the cell.
   * </p>
   * 
   * @param <RowType> the type of the row values
   */
  public abstract static class HTMLCellView<RowType> extends
      AbstractCellView<RowType> {
    /**
     * Construct a new {@link TableDefinition.HTMLCellView}.
     * 
     * @param sourceTableDef the {@link HasTableDefinition} that defined the
     *          cell view
     */
    public HTMLCellView(HasTableDefinition<RowType> sourceTableDef) {
      super(sourceTableDef);
    }

    /**
     * Append a chunk of html to the html string that will be rendered. It is
     * safe to call this method multiple times when rendering a single cell.
     * 
     * @param html the html chunk to append
     */
    public abstract void addHTML(String html);

    /**
     * Sets the horizontal alignment of the specified cell. This method cannot
     * be called after {@link #addHTML(String)}.
     * 
     * @param align the cell's new horizontal alignment as specified in
     *          {@link com.google.gwt.user.client.ui.HasHorizontalAlignment}.
     */
    public void setHorizontalAlignment(HorizontalAlignmentConstant align) {
    }

    /**
     * Sets an attribute on the cell's style. This method cannot be called after
     * {@link #addHTML(String)}.
     * 
     * @param attr the name of the style attribute to be set
     * @param value the style attribute's new value
     */
    public void setStyleAttribute(String attr, String value) {
    }

    /**
     * Set the style name of the cell, which can be a space delimited list of
     * style names. This method cannot be called after {@link #addHTML(String)}.
     */
    public void setStyleName(String stylename) {
    }

    /**
     * Sets the vertical alignment of the specified cell. This method cannot be
     * called after {@link #addHTML(String)}.
     * 
     * @param align the cell's new vertical alignment as specified in
     *          {@link com.google.gwt.user.client.ui.HasVerticalAlignment}.
     */
    public void setVerticalAlignment(VerticalAlignmentConstant align) {
    }

    /**
     * Set the {@link Widget} in the current
     * {@link TableDefinition.HTMLCellView}. Note that {@link Widget Widgets}
     * set using this method will actually be added to the table after the bulk
     * renderer has finished rendering the HTML contents of ALL cells. Widget's
     * will not be rendered as part of the HTML String, using this method does
     * not give the performance benefit of setting the contents as an HTML
     * string.
     * 
     * @param widget the widget to set
     */
    public abstract void setWidget(Widget widget);

    @Override
    protected void renderCellImpl(RowType rowValue,
        ColumnDefinition<RowType, ?> columnDef) {
      columnDef.renderRowValue(rowValue, this);
    }
  }

  /**
   * <p>
   * The {@link TableDefinition.AbstractCellView} used by views that render the
   * table using direct {@link Widget} and DOM manipulation.
   * </p>
   * <p>
   * Views that use this method update the DOM as you make method calls, so it
   * is safe to call methods in any order. For example, you cannot set the html
   * inside of the cell, and then style the cell.
   * </p>
   * 
   * @param <RowType> the type of the row values
   */
  public abstract static class TableCellView<RowType> extends
      AbstractCellView<RowType> {
    /**
     * Construct a new {@link TableDefinition.TableCellView}.
     * 
     * @param sourceTableDef the {@link HasTableDefinition} that defined the
     *          cell view
     */
    public TableCellView(HasTableDefinition<RowType> sourceTableDef) {
      super(sourceTableDef);
    }

    /**
     * Sets the horizontal alignment of the specified cell.
     * 
     * @param align the cell's new horizontal alignment as specified in
     *          {@link com.google.gwt.user.client.ui.HasHorizontalAlignment}.
     */
    public void setHorizontalAlignment(HorizontalAlignmentConstant align) {
    }

    /**
     * Set the html string in the current cell.
     * 
     * @param html the html string to set
     */
    public abstract void setHTML(String html);

    /**
     * Sets an attribute on the cell's style.
     * 
     * @param attr the name of the style attribute to be set
     * @param value the style attribute's new value
     */
    public void setStyleAttribute(String attr, String value) {
    }

    /**
     * Set the style name of the cell, which can be a space delimited list of
     * style names.
     */
    public void setStyleName(String stylename) {
    }

    /**
     * Set the text string in the current cell.
     * 
     * @param text the text string to set
     */
    public abstract void setText(String text);

    /**
     * Sets the vertical alignment of the specified cell.
     * 
     * @param align the cell's new vertical alignment as specified in
     *          {@link com.google.gwt.user.client.ui.HasVerticalAlignment}.
     */
    public void setVerticalAlignment(VerticalAlignmentConstant align) {
    }

    /**
     * Set the {@link Widget} in the current cell.
     * 
     * @param widget the widget to set
     */
    public abstract void setWidget(Widget widget);

    @Override
    protected void renderCellImpl(RowType rowValue,
        ColumnDefinition<RowType, ?> columnDef) {
      columnDef.renderRowValue(rowValue, this);
    }
  }

  /**
   * Get a list of the visible {@link ColumnDefinition ColumnDefinitions}.
   * 
   * @return the visible {@link ColumnDefinition ColumnDefinitions}
   */
  List<ColumnDefinition<RowType, ?>> getVisibleColumnDefinitions();

  /**
   * Iterator over the row values, rendering every cell in every row.
   * 
   * @param startRowIndex the index of the first row to render
   * @param rowValues the values associated with the rows
   * @param view the view used to render the cells
   */
  void renderRows(int startRowIndex, Iterator<RowType> rowValues,
      AbstractCellView<RowType> view);
}
