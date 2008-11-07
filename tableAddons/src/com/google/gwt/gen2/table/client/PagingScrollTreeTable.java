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
import com.google.gwt.gen2.table.client.TableDefinition.AbstractRowView;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnFilterList;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnSortList;
import com.google.gwt.gen2.table.client.TableModelHelper.Request;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.TextResource;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

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

  protected static class PagingScrollTreeTableCellView<RowType extends TreeTableItem>
      extends PagingScrollTableCellView<RowType> {
    private PagingScrollTreeTable<RowType> table;

    public PagingScrollTreeTableCellView(PagingScrollTreeTable<RowType> table) {
      super(table);
      this.table = table;
    }

    @Override
    public void setText(String text) {
      if (getCellIndex() == 0) {
        ComplexPanel treeNode = renderTreeNode(getRowIndex());
        if (text != null && text.length() > 0) {
          treeNode.add(new Label(text));
        }
        table.getDataTable().setWidget(getRowIndex(), getCellIndex(), treeNode);
      } else {
        super.setText(text);
      }
    }

    @Override
    public void setHTML(String html) {
      if (getCellIndex() == 0) {
        ComplexPanel treeNode = renderTreeNode(getRowIndex());
        if (html != null && html.length() > 0) {
          treeNode.add(new HTML(html));
        }
        table.getDataTable().setWidget(getRowIndex(), getCellIndex(), treeNode);
      } else {
        super.setHTML(html);
      }
    }

    @Override
    public void setWidget(Widget widget) {
      if (getCellIndex() == 0) {
        ComplexPanel treeNode = renderTreeNode(getRowIndex());
        treeNode.add(widget);
        table.getDataTable().setWidget(getRowIndex(), getCellIndex(), treeNode);
      } else {
        super.setWidget(widget);
      }
    }

    protected ComplexPanel renderTreeNode(int rowIndex) {
      final RowType treeTableItem = table.getRowValue(getRowIndex());
      final Set<String> invertedNodes = table.getInvertedNodes();
      boolean open = table.isOpen();
      TreeTableResources resources = table.getResources();
      HorizontalPanel treeNode = new HorizontalPanel();
      treeNode.setStylePrimaryName("treeNode");
      treeNode.setSpacing(0);
      treeNode.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
      indent(treeNode, treeTableItem);
      if (invertedNodes.contains(treeTableItem.getId())) {
        Image image;
        if (table.isOpen()) {
          image = resources.treeClosed().createImage();
        } else {
          image = resources.treeOpen().createImage();
        }
        image.addClickListener(new ClickListener() {
          public void onClick(Widget sender) {
            invertedNodes.remove(treeTableItem.getId());
            table.reloadPage();
          }
        });
        treeNode.add(image);
      } else if (treeTableItem.hasChildren()) {
        Image image;
        if (open) {
          image = resources.treeOpen().createImage();
        } else {
          image = resources.treeClosed().createImage();
        }
        image.addClickListener(new ClickListener() {
          public void onClick(Widget sender) {
            invertedNodes.add(treeTableItem.getId());
            table.reloadPage();
          }
        });
        treeNode.add(image);
      } else {
        treeNode.add(createSpacer());
      }
      return treeNode;
    }

    protected Widget createSpacer() {
      Widget spacer = new AbsolutePanel();
      spacer.setStylePrimaryName("spacer");
      return spacer;
    }

    protected void indent(HorizontalPanel widget, TreeTableItem item) {
      if (item.getParent() != null) {
        widget.add(createSpacer());
        indent(widget, item.getParent());
      }
    }
  }

  /**
   * A custom {@link AbstractRowView} used by the {@link PagingScrollTable}.
   * 
   * @param <RowType> the type of the row values
   */
  protected static class PagingScrollTreeTableRowView<RowType extends TreeTableItem>
      extends AbstractRowView<RowType> {
    private PagingScrollTreeTable<RowType> table;

    public PagingScrollTreeTableRowView(
        PagingScrollTreeTableCellView<RowType> cellView,
        PagingScrollTreeTable<RowType> table) {
      super(cellView);
      this.table = table;
    }

    @Override
    public void setStyleAttribute(String attr, String value) {
      table.getDataTable().getFixedWidthGridRowFormatter().getRawElement(
          getRowIndex()).getStyle().setProperty(attr, value);
    }

    @Override
    public void setStyleName(String stylename) {
      table.getDataTable().getRowFormatter().setStyleName(getRowIndex(),
          stylename);
    }

    @Override
    public void removeStyleName(String stylename) {
      table.getDataTable().getRowFormatter().removeStyleName(getRowIndex(),
          stylename);
    }
  }

  protected boolean open = false;
  protected Set<String> invertedNodes = new HashSet<String>();
  protected TreeTableResources resources;
  protected PagingScrollTreeTableRowView<RowType> rowView = new PagingScrollTreeTableRowView<RowType>(
      new PagingScrollTreeTableCellView<RowType>(this), this);

  public PagingScrollTreeTable(TreeTableModel<RowType> tableModel,
      FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
      TableDefinition<RowType> tableDefinition, boolean open) {
    this(tableModel, dataTable, headerTable, tableDefinition, open,
        (TreeTableResources) GWT.create(TreeTableResources.class));
  }

  public PagingScrollTreeTable(TreeTableModel<RowType> tableModel,
      FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
      TableDefinition<RowType> tableDefinition, boolean open,
      ScrollTableImages scrollTableImages) {
    this(tableModel, dataTable, headerTable, tableDefinition, open,
        (TreeTableResources) GWT.create(TreeTableResources.class),
        scrollTableImages);
  }

  public PagingScrollTreeTable(TreeTableModel<RowType> tableModel,
      FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
      TableDefinition<RowType> tableDefinition, boolean open,
      TreeTableResources resources) {
    super(tableModel, dataTable, headerTable, tableDefinition);
    StyleInjector.injectStylesheet(resources.css().getText(), resources);
    this.resources = resources;
    this.open = open;
  }

  public PagingScrollTreeTable(TreeTableModel<RowType> tableModel,
      FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
      TableDefinition<RowType> tableDefinition, boolean open,
      TreeTableResources resources, ScrollTableImages scrollTableImages) {
    super(tableModel, dataTable, headerTable, tableDefinition,
        scrollTableImages);
    StyleInjector.injectStylesheet(resources.css().getText(), resources);
    this.resources = resources;
    this.open = open;
  }

  public void gotoPage(int page, boolean forced) {
    super.gotoPage(page, forced);
    redraw();
  }

  public Set<String> getInvertedNodes() {
    return invertedNodes;
  }

  public boolean isOpen() {
    return open;
  }

  public TreeTableResources getResources() {
    return resources;
  }

  public void setOpen(boolean open) {
    this.open = open;
    gotoPage(getCurrentPage(), true);
  }

  protected Request createRequest(int startRow, int pageSize,
      ColumnSortList columnSortList, ColumnFilterList columnFilterList) {
    return new TreeTableModel.Request(startRow, pageSize, columnSortList,
        columnFilterList, open, invertedNodes);
  }

  @Override
  protected AbstractRowView<RowType> getRowView() {
    return rowView;
  }
}