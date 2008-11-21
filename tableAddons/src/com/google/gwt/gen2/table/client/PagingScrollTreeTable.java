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
import com.google.gwt.gen2.table.shared.ColumnFilterList;
import com.google.gwt.gen2.table.shared.ColumnSortList;
import com.google.gwt.gen2.table.shared.Request;
import com.google.gwt.gen2.table.shared.TreeRequest;
import com.google.gwt.gen2.table.shared.TreeTableItem;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashSet;
import java.util.Set;

/**
 * A {@link PagingScrollTable} that hides child rows to emulate TreeTable
 * 
 * @param <RowType>
 *            the data type of the row values
 */
public class PagingScrollTreeTable<RowType extends TreeTableItem> extends PagingScrollTable<RowType> {

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
	}

	protected static class DefatulTreeTableResources implements TreeTableResources {
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

	protected static class PagingScrollTreeTableCellView<RowType extends TreeTableItem> extends PagingScrollTableCellView<RowType> {
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
			} else {
				treeNode.add(createSpacer());
			}
			return treeNode;
		}

		protected Widget createSpacer() {
			HTML spacer = new HTML();
			spacer.setStylePrimaryName(table.getResources().getStyle().css().treeTableIndent());
			return spacer;
		}

		protected void indent(HorizontalPanel widget, TreeTableItem item) {
			while (item.getParent() != null) {
				widget.add(createSpacer());
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
	protected static class PagingScrollTreeTableRowView<RowType extends TreeTableItem> extends AbstractRowView<RowType> {
		private PagingScrollTreeTable<RowType> table;

		public PagingScrollTreeTableRowView(PagingScrollTreeTableCellView<RowType> cellView, PagingScrollTreeTable<RowType> table) {
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
	protected PagingScrollTreeTableRowView<RowType> rowView;

	public PagingScrollTreeTable(TreeTableModel<RowType> tableModel, FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
			TableDefinition<RowType> tableDefinition, boolean open) {
		this(tableModel, dataTable, headerTable, tableDefinition, open, new DefatulTreeTableResources());
	}

	public PagingScrollTreeTable(TreeTableModel<RowType> tableModel, FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
			TableDefinition<RowType> tableDefinition, boolean open, TreeTableResources resources) {
		super(tableModel, dataTable, headerTable, tableDefinition, resources);
		this.resources = resources;
		this.open = open;
		rowView = new PagingScrollTreeTableRowView<RowType>(new PagingScrollTreeTableCellView<RowType>(this), this);
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
		gotoPage(getCurrentPage(), true);
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