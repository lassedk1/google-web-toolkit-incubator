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
import com.google.gwt.gen2.table.shared.ColumnFilterInfo;
import com.google.gwt.gen2.table.shared.ColumnFilterList;
import com.google.gwt.gen2.table.shared.ColumnSortInfo;
import com.google.gwt.gen2.table.shared.ColumnSortList;
import com.google.gwt.gen2.table.shared.Request;
import com.google.gwt.gen2.table.shared.SerializableResponse;
import com.google.gwt.gen2.table.shared.TreeRequest;
import com.google.gwt.gen2.table.shared.TreeTableItem;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.libideas.resources.client.ImageResource.ImageOptions;
import com.google.gwt.libideas.resources.client.ImageResource.RepeatStyle;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A {@link PagingScrollTable} that hides child rows to emulate TreeTable
 * 
 * @param <RowType>
 *            the data type of the row values
 */
public class TreeTable<RowType extends TreeTableItem> extends PagingScrollTable<RowType> {
	static class ClientTreeTableModel<RowType extends TreeTableItem> extends TreeTableModel<RowType> {

		class TreeItemComparator implements Comparator<TreeItem<RowType>> {
			private final ColumnDefinition<RowType, ?> columnDefinition;
			private final boolean ascending;

			public TreeItemComparator(ColumnDefinition<RowType, ?> columnDefinition, boolean ascending) {
				this.columnDefinition = columnDefinition;
				this.ascending = ascending;
			}

			public int compare(TreeItem<RowType> o1, TreeItem<RowType> o2) {
				return ((Comparable) columnDefinition.getCellValue(o1.getTreeTableItem())).compareTo(columnDefinition.getCellValue(o2.getTreeTableItem()))
						* (ascending ? 1 : -1);
			}
		}

		private List<TreeItem<RowType>> rootItems = new ArrayList<TreeItem<RowType>>();
		private final TableDefinition<RowType> tableDefinition;
		private boolean filterTopLevel = true;

		public ClientTreeTableModel(TableDefinition<RowType> tableDefinition, List<TreeItem<RowType>> rootItems, boolean filterTopLevel) {
			this.tableDefinition = tableDefinition;
			this.rootItems = rootItems;
			this.filterTopLevel = filterTopLevel;
		}

		@Override
		public void requestTreeItems(TreeRequest request, Callback<RowType> callback) {
			boolean open = request.isOpen();
			Set<String> invertedNodes = request.getInvertedNodes();
			int numRows = request.getNumRows();
			int startRow = request.getStartRow();
			int currentRow = 0;
			ColumnSortList columnSortList = request.getColumnSortList();
			ColumnSortInfo columnSortInfo = columnSortList.getPrimaryColumnSortInfo();
			TreeItemComparator comparator = null;
			if (columnSortInfo != null) {
				List<ColumnDefinition<RowType, ?>> visibleColumnDefinitions = tableDefinition.getVisibleColumnDefinitions();
				ColumnDefinition<RowType, ?> sortableColumnDefinition = null;
				for (ColumnDefinition<RowType, ?> columnDefinition : visibleColumnDefinitions) {
					if (columnDefinition.getColumnFilter().getColumn() == columnSortInfo.getColumn()) {
						sortableColumnDefinition = columnDefinition;
					}
				}
				comparator = new TreeItemComparator(sortableColumnDefinition, columnSortInfo.isAscending());
				Collections.sort(rootItems, comparator);
			}
			List<RowType> visibleFiles = new ArrayList<RowType>();
			for (TreeItem<RowType> file : rootItems) {
				currentRow = addTreeItem(request, visibleFiles, file, open, invertedNodes, comparator, currentRow, startRow, numRows, 0);
			}
			callback.onRowsReady(request, new SerializableResponse<RowType>(visibleFiles, currentRow));
		}

		private int addTreeItem(TreeRequest request, List<RowType> visibleFiles, TreeItem<RowType> treeItem, boolean open, Set<String> invertedNodes,
				TreeItemComparator comparator, int currentRow, int startRow, int numRows, int level) {
			// Check if filters are matching...
			ColumnFilterList columnFilterList = request.getColumnFilterList();
			boolean filterMatching = false;
			if (level == 0 && filterTopLevel) {
				filterMatching = isFilterMatching(treeItem, columnFilterList);
			} else {
				filterMatching = true;
			}
			if (filterMatching) {
				treeItem.getTreeTableItem().setRow(currentRow);
				currentRow++;
				if (currentRow > startRow && (currentRow - startRow) <= numRows) {
					visibleFiles.add(treeItem.getTreeTableItem());
				}
			}
			if (filterMatching || !filterTopLevel) {
				if (treeItem.hasChildren()
						&& (open && !invertedNodes.contains(treeItem.getTreeTableItem().getId()) || !open
								&& invertedNodes.contains(treeItem.getTreeTableItem().getId()))) {
					List<TreeItem<RowType>> children = treeItem.getChildren();
					if (comparator != null) {
						Collections.sort(children, comparator);
					}
					for (TreeItem<RowType> child : children) {
						currentRow = addTreeItem(request, visibleFiles, child, open, invertedNodes, comparator, currentRow, startRow, numRows, level + 1);
					}
				}
			}
			return currentRow;
		}

		private boolean isFilterMatching(TreeItem<RowType> treeItem, ColumnFilterList columnFilterList) {
			for (ColumnFilterInfo columnFilterInfo : columnFilterList) {
				List<ColumnDefinition<RowType, ?>> visibleColumnDefinitions = tableDefinition.getVisibleColumnDefinitions();
				for (ColumnDefinition<RowType, ?> columnDefinition : visibleColumnDefinitions) {
					if (columnDefinition.getColumnFilter().getColumn() == columnFilterInfo.getColumn()) {
						if (!columnFilterInfo.isFilterMatching(columnDefinition.getCellValue(treeItem.getTreeTableItem()))) {
							return false;
						}
					}
				}
			}
			return true;
		}
	}

	/**
	 * Interface used to allow the widget access to css style names. <p/> The
	 * class names indicate the default gwt names for these styles. <br>
	 * 
	 */
	public static interface Css extends AbstractScrollTable.Css {
		/**
		 * Widget style name.
		 * 
		 * @return the widget's style name
		 */
		@ClassName("gwt-TreeTable")
		String defaultStyleName();

		/**
		 * Tree node wrapper
		 */
		String treeNodeWrapper();

		/**
		 * Indentation style
		 */
		String treeTableIndent();

		/**
     * Indentation style
     */
    String treeTableIndentUp();

		/**
		 * Open tree node
		 */
		String treeTableOpenNode();

		/**
		 * Open tree node
		 */
		String treeTableClosedNode();
	}

	public interface TreeTableResources extends ScrollTableResources {
		TreeTableStyle getStyle();

		TreeTableConstants getConstants();
	}

	public interface TreeTableConstants extends ScrollTableConstants {
		@DefaultStringValue("Open the tree node by clicking on the plus icon")
		String openTreeNodeTooltip();
	}

	/**
	 * Resources used.
	 */
	public interface TreeTableStyle extends ScrollTableStyle {
		/**
		 * The css file.
		 */
		@Resource("com/google/gwt/gen2/widgetbase/public/TreeTable.css")
		Css css();

		@Resource("treeClosed.gif")
		ImageResource treeClosed();

		@Resource("treeOpen.gif")
		ImageResource treeOpen();

		@Resource("treeIndent.gif")
		ImageResource treeIndent();

    @Resource("treeIndentUp.gif")
    ImageResource treeIndentUp();

		@Resource("headerBackground.png")
		@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
		ImageResource headerBackground();
	}

	protected static class DefaultTreeTableResources implements TreeTableResources {
		private TreeTableStyle style;
		private TreeTableConstants constants;

		public TreeTableStyle getStyle() {
			if (style == null) {
				style = ((TreeTableStyle) GWT.create(TreeTableStyle.class));
			}
			return style;
		}

		public TreeTableConstants getConstants() {
			if (constants == null) {
				constants = ((TreeTableConstants) GWT.create(TreeTableConstants.class));
			}
			return constants;
		}
	}

	protected static class TreeTableCellView<RowType extends TreeTableItem> extends PagingScrollTableCellView<RowType> {
		private TreeTable<RowType> table;

		public TreeTableCellView(TreeTable<RowType> table) {
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
				String treeNodeWrapper = table.getResources().getStyle().css().treeNodeWrapper();
				treeNode.setStyleName(treeNodeWrapper);
				table.getDataTable().setWidget(getRowIndex(), getCellIndex(), treeNode);
			} else {
				super.setText(text);
			}
		}

		@Override
		public void setHTML(String html) {
			if (getCellIndex() == 0) {
				ComplexPanel treeNode = renderTreeNode(getRowIndex());
				String treeNodeWrapper = table.getResources().getStyle().css().treeNodeWrapper();
				treeNode.setStyleName(treeNodeWrapper);
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
			String treeNodeWrapper = table.getResources().getStyle().css().treeNodeWrapper();
			if (getCellIndex() == 0) {
				ComplexPanel treeNode = renderTreeNode(getRowIndex());
				treeNode.add(widget);
				treeNode.setStyleName(treeNodeWrapper);
				table.getDataTable().setWidget(getRowIndex(), getCellIndex(), treeNode);
			} else {
				widget.setStyleName(treeNodeWrapper);
				super.setWidget(widget);
			}
		}

		protected ComplexPanel renderTreeNode(int rowIndex) {
			final RowType treeTableItem = table.getRowValue(getRowIndex());
			final Set<String> invertedNodes = table.getInvertedNodes();
			boolean open = table.isTreeOpen();
			Css css = table.getResources().getStyle().css();
			HorizontalPanel treeNode = new HorizontalPanel();
			treeNode.setSpacing(0);
			treeNode.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
			indent(treeNode, treeTableItem);
			TreeTableConstants constants = table.getResources().getConstants();
			if (invertedNodes.contains(treeTableItem.getId())) {
				HTML nodeIndicator = new HTML();
				nodeIndicator.setTitle(constants.openTreeNodeTooltip());
				if (table.isTreeOpen()) {
					nodeIndicator.setStyleName(css.treeTableClosedNode());
				} else {
					nodeIndicator.setStyleName(css.treeTableOpenNode());
				}
				nodeIndicator.addClickListener(new ClickListener() {
					public void onClick(Widget sender) {
						invertedNodes.remove(treeTableItem.getId());
						table.reloadPage();
					}
				});
				treeNode.add(nodeIndicator);
			} else if (treeTableItem.hasChildren()) {
				HTML nodeIndicator = new HTML();
				nodeIndicator.setTitle(constants.openTreeNodeTooltip());
				if (open) {
					nodeIndicator.setStyleName(css.treeTableOpenNode());
				} else {
					nodeIndicator.setStyleName(css.treeTableClosedNode());
				}
				nodeIndicator.addClickListener(new ClickListener() {
					public void onClick(Widget sender) {
						invertedNodes.add(treeTableItem.getId());
						table.reloadPage();
					}
				});
				treeNode.add(nodeIndicator);
			}
			return treeNode;
		}

		protected Widget createSpacer(TreeTableItem treeTableItem) {
			HTML spacer = new HTML();
			final TreeTableItem parent = treeTableItem.getParent();
			spacer.setTitle(parent.getDisplayName());
			spacer.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					table.gotoPage(parent.getRow() / table.getPageSize(), false);
					table.getDataTable().selectRow(parent.getRow() % table.getPageSize(), true);
				}
			});
			return spacer;
		}

		protected void indent(HorizontalPanel widget, TreeTableItem item) {
		  boolean first = true;
			while (item.getParent() != null) {
			  Widget spacer = createSpacer(item);
			  if ( first ) {
          spacer.setStylePrimaryName(table.getResources().getStyle().css().treeTableIndent());
          first = false;
			  } else {
			    spacer.setStylePrimaryName(table.getResources().getStyle().css().treeTableIndentUp());
			  }
				widget.insert(spacer, 0);
				item = item.getParent();
			}
		}
	}

	/**
	 * A custom {@link AbstractRowView} used by the {@link PagingScrollTable}.
	 * 
	 * @param <RowType>
	 *            the type of the row values
	 */
	protected static class TreeTableRowView<RowType extends TreeTableItem> extends AbstractRowView<RowType> {
		private TreeTable<RowType> table;

		public TreeTableRowView(TreeTableCellView<RowType> cellView, TreeTable<RowType> table) {
			super(cellView);
			this.table = table;
		}

		@Override
		public void setStyleAttribute(String attr, String value) {
			table.getDataTable().getFixedWidthGridRowFormatter().getRawElement(getRowIndex()).getStyle().setProperty(attr, value);
		}

		@Override
		public void setStyleName(String stylename) {
			table.getDataTable().getRowFormatter().setStyleName(getRowIndex(), stylename);
		}

		@Override
		public void removeStyleName(String stylename) {
			table.getDataTable().getRowFormatter().removeStyleName(getRowIndex(), stylename);
		}
	}

	protected boolean open = false;
	protected Set<String> invertedNodes = new HashSet<String>();
	protected TreeTableResources resources;
	protected TreeTableRowView<RowType> rowView;

  public TreeTable(DefaultTableDefinition<RowType> tableDefinition, List<TreeItem<RowType>> rootItems) {
    this(new ClientTreeTableModel<RowType>(tableDefinition, rootItems, true), new FixedWidthGrid(), new FixedWidthFlexTable(), tableDefinition, false);
  }

	public TreeTable(DefaultTableDefinition<RowType> tableDefinition, List<TreeItem<RowType>> rootItems, boolean open) {
		this(new ClientTreeTableModel<RowType>(tableDefinition, rootItems, true), new FixedWidthGrid(), new FixedWidthFlexTable(), tableDefinition, open);
	}

	public TreeTable(TreeTableModel<RowType> tableModel, FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
			TableDefinition<RowType> tableDefinition, boolean open) {
		this(tableModel, dataTable, headerTable, tableDefinition, open, new DefaultTreeTableResources());
	}

	public TreeTable(TreeTableModel<RowType> tableModel, FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
			TableDefinition<RowType> tableDefinition, boolean open, TreeTableResources resources) {
		super(tableModel, dataTable, headerTable, tableDefinition, resources);
		this.resources = resources;
		this.open = open;
		rowView = new TreeTableRowView<RowType>(new TreeTableCellView<RowType>(this), this);
		setCellPadding(0);
		setCellSpacing(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.gen2.table.client.PagingScrollTable#gotoPage(int,
	 * boolean)
	 */
	public void gotoPage(int page, boolean forced) {
		super.gotoPage(page, forced);
		redraw();
	}

	/**
	 * Return a set of nodes containing all tree nodes that differ from the tree
	 * state
	 * 
	 * @return the list of inverted nodes
	 */
	public Set<String> getInvertedNodes() {
		return invertedNodes;
	}

	/**
	 * @return the default state of the tree nodes. true is all nodes are
	 *         displayed open by default, false if all nodes are closed
	 */
	public boolean isTreeOpen() {
		return open;
	}

	public TreeTableResources getResources() {
		return resources;
	}

	/**
	 * Opens all tree nodes
	 */
	public void openTree() {
		setTreeOpen(true);
	}

	/**
	 * Closes all tree nodes
	 */
	public void closeTree() {
		setTreeOpen(false);
	}

	/**
	 * Opens the given tree item if children are available
	 * 
	 * @param item
	 *            the tree node to open
	 */
	public void openTreeNode(TreeTableItem item) {
		setTreeNodeOpen(item, true);
	}

	/**
	 * Closes the given tree node
	 * 
	 * @param item
	 *            the tree node to open
	 */
	public void closeTreeNode(TreeTableItem item) {
		setTreeNodeOpen(item, false);
	}

	protected Request createRequest(int startRow, int pageSize, ColumnSortList columnSortList, ColumnFilterList columnFilterList) {
		return new TreeRequest(startRow, pageSize, columnSortList, columnFilterList, open, invertedNodes);
	}

	@Override
	protected AbstractRowView<RowType> getRowView() {
		return rowView;
	}

	protected void setTreeOpen(boolean open) {
		this.open = open;
		invertedNodes.clear();
		gotoPage(0, true);
	}

	protected void setTreeNodeOpen(TreeTableItem item, boolean open) {
		if (item.hasChildren()) {
			if (isTreeOpen() && open) {
				invertedNodes.remove(item.getId());
			} else {
				invertedNodes.add(item.getId());
			}
		}
	}
}