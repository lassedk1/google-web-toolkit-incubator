package com.google.gwt.gen2.demo.scrolltable.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.gen2.datepicker.client.DatePicker;
import com.google.gwt.gen2.demo.scrolltable.client.TreeTableDemo.ClientFileTableModel.TreeItemComparator;
import com.google.gwt.gen2.table.client.ColumnDefinition;
import com.google.gwt.gen2.table.client.DateColumnDefinition;
import com.google.gwt.gen2.table.client.DefaultTableDefinition;
import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.NumberColumnDefinition;
import com.google.gwt.gen2.table.client.PagingOptions;
import com.google.gwt.gen2.table.client.PagingScrollTreeTable;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.gen2.table.client.TextColumnDefinition;
import com.google.gwt.gen2.table.client.TreeTableModel;
import com.google.gwt.gen2.table.client.AbstractScrollTable.SortPolicy;
import com.google.gwt.gen2.table.shared.ColumnFilterInfo;
import com.google.gwt.gen2.table.shared.ColumnFilterList;
import com.google.gwt.gen2.table.shared.ColumnSortInfo;
import com.google.gwt.gen2.table.shared.ColumnSortList;
import com.google.gwt.gen2.table.shared.SerializableResponse;
import com.google.gwt.gen2.table.shared.TreeItem;
import com.google.gwt.gen2.table.shared.TreeRequest;
import com.google.gwt.gen2.table.shared.TreeTableItem;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class TreeTableDemo implements EntryPoint {
	class File implements TreeTableItem {
		private String path, name;
		private double size;
		private Date lastModified;
		private TreeTableItem parent;
		private boolean children;
		private int page, row;

		public File(String path, String name, TreeTableItem parent, boolean children, double size, Date lastModified) {
			this.path = path;
			this.name = name;
			this.parent = parent;
			this.children = children;
			this.size = size;
			this.lastModified = lastModified;
		}

		public String getId() {
			return path;
		}

		public String getDisplayName() {
			return name;
		}

		public TreeTableItem getParent() {
			return parent;
		}

		public boolean hasChildren() {
			return children;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getSize() {
			return size;
		}

		public void setSize(double size) {
			this.size = size;
		}

		public void setPage(int page) {
			this.page = page;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public Date getLastModified() {
			return lastModified;
		}

		public void setLastModified(Date lastModified) {
			this.lastModified = lastModified;
		}

		public boolean isChildren() {
			return children;
		}

		public void setChildren(boolean children) {
			this.children = children;
		}

		public void setParent(File parent) {
			this.parent = parent;
		}

		public int getPage() {
			return page;
		}

		public int getRow() {
			return row;
		}
	}

	class ClientFileTableModel extends TreeTableModel<File> {
		class TreeItemComparator implements Comparator<TreeItem<File>> {
			private final ColumnDefinition<File, ?> columnDefinition;
			private final boolean ascending;

			public TreeItemComparator(ColumnDefinition<File, ?> columnDefinition, boolean ascending) {
				this.columnDefinition = columnDefinition;
				this.ascending = ascending;
			}

			public int compare(TreeItem<File> o1, TreeItem<File> o2) {
				return ((Comparable) columnDefinition.getCellValue(o1.getTreeTableItem())).compareTo(columnDefinition.getCellValue(o2.getTreeTableItem())) * (ascending ? 1 : -1);
			}
		}

		private List<TreeItem<File>> files = new ArrayList<TreeItem<File>>();
		private final DefaultTableDefinition<File> tableDefinition;
		private boolean filterTopLevel = true;
		private boolean sortTopLevel = true;

		public ClientFileTableModel(DefaultTableDefinition<File> fileTableDefinition) {
			this.tableDefinition = fileTableDefinition;
			// Create some dummy data
			for (int i = 0; i < 10; i++) {
				TreeItem<File> file = new TreeItem<File>(new File("C:/tmp/" + i, "File" + i, null, true, Random.nextInt(100000), new Date()));
				addChildren(file, Random.nextInt(5) + 1, 0);
				files.add(file);
			}
		}

		private void addChildren(TreeItem<File> file, int numberOfChildren, int level) {
			for (int i = 0; i < numberOfChildren; i++) {
				TreeItem<File> child = new TreeItem<File>(new File(file.getTreeTableItem().getId() + "/" + i, "Level-" + level + "-Child-" + i, (File) file
						.getTreeTableItem(), level < 5, Random.nextInt(100000), new Date()));
				file.addChild(child);
				if (level < 5) {
					addChildren(child, Random.nextInt(5) + 1, level + 1);
				}
			}
		}

		@Override
		public void requestTreeItems(TreeRequest request, Callback<File> callback) {
			boolean open = request.isOpen();
			Set<String> invertedNodes = request.getInvertedNodes();
			int numRows = request.getNumRows();
			int startRow = request.getStartRow();
			int currentRow = 0;
			ColumnSortList columnSortList = request.getColumnSortList();
			ColumnSortInfo columnSortInfo = columnSortList.getPrimaryColumnSortInfo();
			TreeItemComparator comparator = null;
			if (columnSortInfo != null) {
				List<ColumnDefinition<File, ?>> visibleColumnDefinitions = tableDefinition.getVisibleColumnDefinitions();
				ColumnDefinition<File, ?> sortableColumnDefinition = null;
				for (ColumnDefinition<File, ?> columnDefinition : visibleColumnDefinitions) {
					if (columnDefinition.getColumnFilter().getColumn() == columnSortInfo.getColumn()) {
						sortableColumnDefinition = columnDefinition;
					}
				}
				comparator = new TreeItemComparator(sortableColumnDefinition, columnSortInfo.isAscending());
				Collections.sort(files, comparator);
			}
			List<File> visibleFiles = new ArrayList<File>();
			for (TreeItem<File> file : files) {
				currentRow = addTreeItem(request, visibleFiles, file, open, invertedNodes, comparator, currentRow, startRow, numRows, 0);
			}
			callback.onRowsReady(request, new SerializableResponse<File>(visibleFiles, currentRow));
		}

		private int addTreeItem(TreeRequest request, List<File> visibleFiles, TreeItem<File> treeItem, boolean open, Set<String> invertedNodes, TreeItemComparator comparator, int currentRow,
				int startRow, int numRows, int level) {
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
					List<TreeItem<File>> children = treeItem.getChildren();
					if ( comparator != null ) {
						Collections.sort(children, comparator);
					}
					for (TreeItem<File> child : children) {
						currentRow = addTreeItem(request, visibleFiles, child, open, invertedNodes, comparator, currentRow, startRow, numRows, level + 1);
					}
				}
			}
			return currentRow;
		}

		private boolean isFilterMatching(TreeItem<File> treeItem, ColumnFilterList columnFilterList) {
			for (ColumnFilterInfo columnFilterInfo : columnFilterList) {
				List<ColumnDefinition<File, ?>> visibleColumnDefinitions = tableDefinition.getVisibleColumnDefinitions();
				for (ColumnDefinition<File, ?> columnDefinition : visibleColumnDefinitions) {
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

	public void onModuleLoad() {
		DefaultTableDefinition<File> fileTableDefinition = new DefaultTableDefinition<File>();
		fileTableDefinition.addColumnDefinition(new TextColumnDefinition<File>(true, false) {
			@Override
			public String getCellValue(File file) {
				return file.getName();
			}

			@Override
			public void setCellValue(File file, String cellValue) {
				file.setName(cellValue);
			}
		});
		fileTableDefinition.addColumnDefinition(new NumberColumnDefinition<File>(NumberFormat.getDecimalFormat(), true, false) {
			@Override
			public Double getCellValue(File file) {
				return file.getSize();
			}

			@Override
			public void setCellValue(File file, Double cellValue) {
				file.setSize(cellValue);
			}
		});
		fileTableDefinition.addColumnDefinition(new DateColumnDefinition<File>(DateTimeFormat.getMediumDateTimeFormat(), true, true, false) {
			@Override
			public Date getCellValue(File file) {
				return file.getLastModified();
			}

			@Override
			public void setCellValue(File file, Date cellValue) {
				file.setLastModified(cellValue);
			}
		});
		FixedWidthGrid dataTable = new FixedWidthGrid();
		FixedWidthFlexTable headerTable = new FixedWidthFlexTable();
		headerTable.setHTML(0, 0, "Name");
		headerTable.setHTML(0, 1, "Size");
		headerTable.setHTML(0, 2, "Last modified");
		DatePicker.injectDefaultCss();
		PagingScrollTreeTable<File> treeTable = new PagingScrollTreeTable<File>(new ClientFileTableModel(fileTableDefinition), dataTable, headerTable,
				fileTableDefinition, false);
		treeTable.setCellPadding(0);
		treeTable.setCellSpacing(0);
		treeTable.setSortPolicy(SortPolicy.SINGLE_CELL);
		treeTable.setPageSize(25);
		treeTable.setWidth("100%");
		treeTable.setHeight("100%");
		treeTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);
		PagingOptions pagingOptions = new PagingOptions(treeTable);
		treeTable.setSize("100%", "100%");
		DockPanel dockPanel = new DockPanel();
		dockPanel.setWidth("100%");
		dockPanel.setHeight("400px");
		dockPanel.add(treeTable, DockPanel.CENTER);
		dockPanel.setCellHeight(treeTable, "100%");
		dockPanel.setCellWidth(treeTable, "100%");
		dockPanel.add(pagingOptions, DockPanel.SOUTH);
		RootPanel.get().add(dockPanel);
	}
}