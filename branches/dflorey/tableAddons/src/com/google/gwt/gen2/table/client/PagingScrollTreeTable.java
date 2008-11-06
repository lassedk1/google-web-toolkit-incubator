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

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnFilterList;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnSortList;
import com.google.gwt.gen2.table.client.TableModelHelper.Request;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.TextResource;

import java.util.HashSet;
import java.util.Set;

/**
 * A {@link PagingScrollTable} that hides child rows to emulate TreeTable
 * 
 * @param <RowType> the data type of the row values
 */
public class PagingScrollTreeTable<RowType extends TreeTableItem> extends
    PagingScrollTable<RowType> {
  /**
   * Resources used.
   */
  public interface TreeTableResources extends ImmutableResourceBundle {
    /**
     * The css file.
     */
    @Resource("TreeTable.css")
    TextResource css();

    @Resource("treeClosed.gif")
    ImageResource treeClosed();

    @Resource("treeOpen.gif")
    ImageResource treeOpen();
  }

  protected Set<String> openNodes = new HashSet<String>();
  protected TreeTableResources resources;

  public PagingScrollTreeTable(TreeTableModel<RowType> tableModel,
      FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
      TableDefinition<RowType> tableDefinition) {
    this(tableModel, dataTable, headerTable, tableDefinition,
        (TreeTableResources) GWT.create(TreeTableResources.class));
  }

  public PagingScrollTreeTable(TreeTableModel<RowType> tableModel,
      FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
      TableDefinition<RowType> tableDefinition,
      ScrollTableImages scrollTableImages) {
    this(tableModel, dataTable, headerTable, tableDefinition,
        (TreeTableResources) GWT.create(TreeTableResources.class),
        scrollTableImages);
  }

  public PagingScrollTreeTable(TreeTableModel<RowType> tableModel,
      FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
      TableDefinition<RowType> tableDefinition, TreeTableResources resources) {
    super(tableModel, dataTable, headerTable, tableDefinition);
    StyleInjector.injectStylesheet(resources.css().getText(), resources);
    this.resources = resources;
    applyTable(tableDefinition);
  }

  public PagingScrollTreeTable(TreeTableModel<RowType> tableModel,
      FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
      TableDefinition<RowType> tableDefinition, TreeTableResources resources,
      ScrollTableImages scrollTableImages) {
    super(tableModel, dataTable, headerTable, tableDefinition,
        scrollTableImages);
    StyleInjector.injectStylesheet(resources.css().getText(), resources);
    this.resources = resources;
    applyTable(tableDefinition);
  }

  public void gotoPage(int page, boolean forced) {
    super.gotoPage(page, forced);
    redraw();
  }

  public Set<String> getOpenNodes() {
    return openNodes;
  }

  public TreeTableResources getResources() {
    return resources;
  }
  
  protected Request createRequest(int startRow, int pageSize,
      ColumnSortList columnSortList, ColumnFilterList columnFilterList) {
    return new TreeTableModel.Request(startRow, pageSize, columnSortList,
        columnFilterList, openNodes);
  }

  protected TreeTableModel<RowType> getTreeTableModel() {
    return (TreeTableModel<RowType>) getTableModel();
  }
  
  /**
   * 
   * TreeColumnDefinition needs references to this table
   * @param tableDefinition the table definition most likely containing treeColumnDefinition
   */
  private void applyTable(TableDefinition<RowType> tableDefinition) {
    for ( ColumnDefinition<RowType, ?> columnDefinition : tableDefinition.getVisibleColumnDefinitions() ) {
      if ( columnDefinition instanceof TreeColumnDefinition) {
        ((TreeColumnDefinition<RowType, ?>)columnDefinition).setTreeTable(this);
      }
    }
  }
}