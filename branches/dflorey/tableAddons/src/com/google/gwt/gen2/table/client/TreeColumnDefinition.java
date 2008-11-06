package com.google.gwt.gen2.table.client;

import com.google.gwt.gen2.table.client.PagingScrollTreeTable.TreeTableResources;
import com.google.gwt.gen2.table.client.TableDefinition.AbstractCellView;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import java.util.Set;

public abstract class TreeColumnDefinition<RowType extends TreeTableItem, ColType>
    extends AbstractColumnDefinition<RowType, ColType> {
  public static class TreeCellRenderer<RowType extends TreeTableItem, ColType>
      implements CellRenderer<RowType, ColType> {
    private PagingScrollTreeTable<RowType> table;
    private Set<String> openNodes;
    private TreeTableResources resources;

    public void renderRowValue(final RowType treeTableItem,
        ColumnDefinition<RowType, ColType> columnDef,
        AbstractCellView<RowType> view) {
      Object cellValue = columnDef.getCellValue(treeTableItem);
      HorizontalPanel treeNode = new HorizontalPanel();
      treeNode.setStylePrimaryName("treeNode");
      treeNode.setSpacing(0);
      treeNode.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
      indent(treeNode, treeTableItem);
      if (getOpenNodes().contains(treeTableItem.getId())) {
        Image image = getResources().treeOpen().createImage();
        image.addClickListener(new ClickListener() {
          public void onClick(Widget sender) {
            getOpenNodes().remove(treeTableItem.getId());
            getTable().reloadPage();
          }
        });
        treeNode.add(image);
      } else if (treeTableItem.hasChildren()) {
        Image image = getResources().treeClosed().createImage();
        image.addClickListener(new ClickListener() {
          public void onClick(Widget sender) {
            getOpenNodes().add(treeTableItem.getId());
            getTable().reloadPage();
          }
        });
        treeNode.add(image);
      } else {
        treeNode.add(createSpacer());
      }
      if (cellValue != null) {
        if (cellValue instanceof Widget) {
          treeNode.add((Widget) cellValue);
        } else {
          treeNode.add(new HTML(cellValue.toString()));
        }
      }
      view.setWidget(treeNode);
    }

    public void setTable(PagingScrollTreeTable<RowType> table) {
      this.table = table;
    }

    public void setOpenNodes(Set<String> openNodes) {
      this.openNodes = openNodes;
    }

    public void setResources(TreeTableResources resources) {
      this.resources = resources;
    }

    public PagingScrollTreeTable<RowType> getTable() {
      return table;
    }

    public Set<String> getOpenNodes() {
      return openNodes;
    }

    public TreeTableResources getResources() {
      return resources;
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

  private TreeCellRenderer<RowType, ColType> cellRenderer = new TreeCellRenderer<RowType, ColType>();

  public TreeColumnDefinition() {
    setCellRenderer(cellRenderer);
  }

  public void setTreeTable(PagingScrollTreeTable<RowType> table) {
    cellRenderer.setTable(table);
    cellRenderer.setOpenNodes(table.getOpenNodes());
    cellRenderer.setResources(table.getResources());
  }
}