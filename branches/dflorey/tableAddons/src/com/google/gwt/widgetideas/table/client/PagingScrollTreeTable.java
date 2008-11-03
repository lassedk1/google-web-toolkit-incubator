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
package com.google.gwt.widgetideas.table.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.TextResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle.Resource;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.table.client.TableModel.ColumnFilterList;
import com.google.gwt.widgetideas.table.client.TableModel.ColumnSortList;
import com.google.gwt.widgetideas.table.client.TableModelHelper.Request;

import java.util.HashSet;
import java.util.Set;

/**
 * A {@link PagingScrollTable} that hides child rows to emulate TreeTable
 * 
 * @param <R> the data type of the row values
 */
public class PagingScrollTreeTable<R extends TreeTableItem> extends
    PagingScrollTable<R> {

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

  public PagingScrollTreeTable(TreeTableModel<R> tableModel,
      FixedWidthGrid dataTable, FixedWidthFlexTable headerTable) {
    this(tableModel, dataTable, headerTable,
        (TreeTableResources) GWT.create(TreeTableResources.class));
  }

  public PagingScrollTreeTable(TreeTableModel<R> tableModel,
      FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
      ScrollTableImages scrollTableImages) {
    this(tableModel, dataTable, headerTable,
        (TreeTableResources) GWT.create(TreeTableResources.class),
        scrollTableImages);
  }

  public PagingScrollTreeTable(TreeTableModel<R> tableModel,
      FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
      TreeTableResources resources) {
    super(tableModel, dataTable, headerTable);
    StyleInjector.injectStylesheet(resources.css().getText(), resources);
    this.resources = resources;
  }

  public PagingScrollTreeTable(TreeTableModel<R> tableModel,
      FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
      TreeTableResources resources, ScrollTableImages scrollTableImages) {
    super(tableModel, dataTable, headerTable, scrollTableImages);
    StyleInjector.injectStylesheet(resources.css().getText(), resources);
    this.resources = resources;
  }

  public Set<String> getOpenNodes() {
    return openNodes;
  }

  public void gotoPage(int page, boolean forced) {
    super.gotoPage(page, forced);
    redraw();
  }

  protected Request createRequest(int startRow, int pageSize,
      ColumnSortList columnSortList, ColumnFilterList columnFilterList) {
    return new TreeTableModel.Request(startRow, pageSize, columnSortList,
        columnFilterList, openNodes);
  }

  protected Widget createSpacer() {
    Widget spacer = new AbsolutePanel();
    spacer.setStylePrimaryName("spacer");
    return spacer;
  }

  protected TreeTableModel<R> getTreeTableModel() {
    return (TreeTableModel<R>) getTableModel();
  }

  protected void indent(HorizontalPanel treeNode, TreeTableItem treeTableRow) {
    if (treeTableRow.getParent() != null) {
      treeNode.add(createSpacer());
      indent(treeNode, treeTableRow.getParent());
    }
  }

  /**
   * Render the contents of the cell.
   * 
   * @param row the row index
   * @param column the column index
   * @param data the data to render
   */
  protected void renderCell(int row, int column, Object data) {
    CellRenderer customRenderer = getCellRenderer();
    if (column == 0) {
      HorizontalPanel treeNode = new HorizontalPanel();
      treeNode.setStylePrimaryName("treeNode");
      treeNode.setSpacing(0);
      treeNode.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
      final TreeTableItem treeTableRow = getRowValue(row);
      indent(treeNode, treeTableRow);
      if (openNodes.contains(treeTableRow.getId())) {
        Image image = resources.treeOpen().createImage();
        image.addClickListener(new ClickListener() {
          public void onClick(Widget sender) {
            openNodes.remove(treeTableRow.getId());
            reloadPage();
          }
        });
        treeNode.add(image);
      } else if (treeTableRow.hasChildren()) {
        Image image = resources.treeClosed().createImage();
        image.addClickListener(new ClickListener() {
          public void onClick(Widget sender) {
            openNodes.add(treeTableRow.getId());
            reloadPage();
          }
        });
        treeNode.add(image);
      } else {
        treeNode.add(createSpacer());
      }
      if (data instanceof Widget) {
        treeNode.add((Widget) data);
      } else {
        treeNode.add(new HTML((String) data));
      }
      getDataTable().setWidget(row, column, treeNode);
    } else {
      if (customRenderer == null) {
        if (data instanceof Widget) {
          getDataTable().setWidget(row, column, (Widget) data);
        } else {
          getDataTable().setHTML(row, column, data + "");
        }
      } else {
        customRenderer.renderCell(getDataTable(), row, column, data);
      }
    }
  }
}