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

import com.google.gwt.gen2.table.client.property.ColumnProperty;
import com.google.gwt.gen2.table.client.property.ColumnPropertyManager;
import com.google.gwt.gen2.table.client.property.FilterableProperty;
import com.google.gwt.gen2.table.client.property.MaximumWidthProperty;
import com.google.gwt.gen2.table.client.property.MinimumWidthProperty;
import com.google.gwt.gen2.table.client.property.PreferredWidthProperty;
import com.google.gwt.gen2.table.client.property.SortableProperty;
import com.google.gwt.gen2.table.client.property.TruncationProperty;
import com.google.gwt.user.client.ui.Widget;

/**
 * A definition of a column in a table.
 * 
 * @param <RowType>
 *            the type of the row value
 * @param <ColType>
 *            the data type of the column
 */
/**
 * @author dflorey
 *
 * @param <RowType>
 * @param <ColType>
 */
public abstract class AbstractColumnDefinition<RowType, ColType> implements ColumnDefinition<RowType, ColType> {
  /**
   * The default {@link CellRenderer} to use when the
   * {@link AbstractColumnDefinition} does not specify one.
   */
  private static final CellRenderer DEFAULT_CELL_RENDERER = new DefaultCellRenderer();

  /**
   * The cell editor used to edit the contents of this column.
   */
  private CellEditor<ColType> cellEditor = null;

  /**
   * The renderer used to render the contents of this column.
   */
  private CellRenderer<RowType, ColType> cellRenderer = DEFAULT_CELL_RENDERER;

  /**
   * The filter used to filter the contents of this column.
   */
  private ColumnFilter<ColType> columnFilter = null;

  /**
   * The header string used for this column
   */
  private String header = null;
  
  /**
   * The header widget used for this column
   */
  private Widget headerWidget = null;
  
  /**
   * The properties assigned to this column.
   */
  private ColumnPropertyManager properties = new ColumnPropertyManager();

  public CellEditor<ColType> getCellEditor() {
    return cellEditor;
  }

  public CellRenderer<RowType, ColType> getCellRenderer() {
    return cellRenderer;
  }

  public ColumnFilter<ColType> getColumnFilter() {
    return columnFilter;
  }
  
  public abstract ColType getCellValue(RowType rowValue);

  public <P extends ColumnProperty> P getColumnProperty(
      ColumnProperty.Type<P> type) {
    return properties.getColumnProperty(type);
  }

  public String getHeader() {
    return header;
  }

  public Widget getHeaderWidget() {
    return headerWidget;
  }
  
  /**
   * Get the maximum width of the column. A return value of -1 indicates that
   * the column has no maximum width, but the consumer of the data may impose
   * one anyway.
   * 
   * @return the maximum allowable width of the column
   */
  public int getMaximumColumnWidth() {
    return getColumnProperty(MaximumWidthProperty.TYPE).getMaximumColumnWidth();
  }

  /**
   * Get the minimum width of the column. A return value of -1 indicates that
   * the column has no minimum width, but the consumer of the data may impose
   * one anyway.
   * 
   * @return the minimum allowable width of the column
   */
  public int getMinimumColumnWidth() {
    return getColumnProperty(MinimumWidthProperty.TYPE).getMinimumColumnWidth();
  }

  /**
   * Returns the preferred width of the column in pixels. Views should respect
   * the preferred column width and attempt to size the column to its preferred
   * width. If the column must be resized, the preferred width should serve as a
   * weight relative to the preferred widths of other ColumnDefinitions.
   * 
   * @return the preferred width of the column
   */
  public int getPreferredColumnWidth() {
    return getColumnProperty(PreferredWidthProperty.TYPE).getPreferredColumnWidth();
  }

  /**
   * Returns true if the column is filterable, false if it is not.
   * 
   * @return true if the column is filterable, false if it is not filterable
   */
  public boolean isColumnFilterable() {
    return getColumnProperty(FilterableProperty.TYPE).isColumnFilterable();
  }

  /**
   * Returns true if the column is sortable, false if it is not.
   * 
   * @return true if the column is sortable, false if it is not sortable
   */
  public boolean isColumnSortable() {
    return getColumnProperty(SortableProperty.TYPE).isColumnSortable();
  }

  /**
   * Returns true if the column is truncatable, false if it is not.
   * 
   * @return true if the column is truncatable, false if it is not sortable
   */
  public boolean isColumnTruncatable() {
    return getColumnProperty(TruncationProperty.TYPE).isColumnTruncatable();
  }

  /**
   * Remove an existing {@link ColumnProperty} if it has already been added.
   * 
   * @param type the type of the property to remove
   */
  public <P extends ColumnProperty> P removeColumnProperty(
      ColumnProperty.Type<P> type) {
    return properties.removeColumnProperty(type);
  }

  /**
   * Set the {@link CellEditor} that should be used to edit cells in this
   * column.
   * 
   * @param cellEditor the {@link CellEditor} to use for this column
   */
  public void setCellEditor(CellEditor<ColType> cellEditor) {
    this.cellEditor = cellEditor;
  }

  /**
   * Set the {@link CellRenderer} that should be used to render cells in this
   * column.
   * 
   * @param cellRenderer the {@link CellRenderer} to use for this column
   */
  public void setCellRenderer(CellRenderer<RowType, ColType> cellRenderer) {
    assert cellRenderer != null : "cellRenderer cannot be null";
    this.cellRenderer = cellRenderer;
  }

  public abstract void setCellValue(RowType rowValue, ColType cellValue);

  /**
   * Set a {@link ColumnProperty}.
   * 
   * @param <P> the column property type
   * @param type the {@link ColumnProperty} type
   * @param property the property to set
   */
  public <P extends ColumnProperty> void setColumnProperty(
      ColumnProperty.Type<P> type, P property) {
    properties.setColumnProperty(type, property);
  }

  /**
   * Set the {@link ColumnFilter} that should be used to filter this
   * column.
   * 
   * @param columnFilter the {@link ColumnFilter} to use for this column
   */
  public void setColumnFilter(ColumnFilter<ColType> columnFilter) {
    this.columnFilter = columnFilter;
  }

  /**
   * Set whether or not this column is sortable.
   * 
   * @param sortable true to make sortable, false to make unsortable
   */
  public void setColumnSortable(boolean sortable) {
    setColumnProperty(SortableProperty.TYPE, new SortableProperty(sortable));
  }

  /**
   * Set whether or not this column is filterable.
   * 
   * @param filterable true to make filterable, false to make unfilterable
   */
  public void setColumnFilterable(boolean filterable) {
    setColumnProperty(FilterableProperty.TYPE, new FilterableProperty(filterable));
  }

  /**
   * Set whether or not this column is truncatable.
   * 
   * @param truncatable true to make truncatable, false to make non truncatable
   */
  public void setColumnTruncatable(boolean truncatable) {
    setColumnProperty(TruncationProperty.TYPE, new TruncationProperty(
        truncatable));
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public void setHeaderWidget(Widget headerWidget) {
    this.headerWidget = headerWidget;
  }
  
  /**
   * Set the maximum width of the column.
   * 
   * @param maxWidth the max width
   */
  public void setMaximumColumnWidth(int maxWidth) {
    setColumnProperty(MaximumWidthProperty.TYPE, new MaximumWidthProperty(
        maxWidth));
  }

  /**
   * Set the minimum width of the column.
   * 
   * @param minWidth the min width
   */
  public void setMinimumColumnWidth(int minWidth) {
    setColumnProperty(MinimumWidthProperty.TYPE, new MinimumWidthProperty(
        minWidth));
  }

  /**
   * Set the preferred width of the column.
   * 
   * @param preferredWidth the preferred width
   * @see #getPreferredColumnWidth()
   */
  public void setPreferredColumnWidth(int preferredWidth) {
    setColumnProperty(PreferredWidthProperty.TYPE, new PreferredWidthProperty(
        preferredWidth));
  }}
