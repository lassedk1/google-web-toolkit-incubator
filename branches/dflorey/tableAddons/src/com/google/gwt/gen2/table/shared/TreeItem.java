package com.google.gwt.gen2.table.shared;

import java.util.ArrayList;
import java.util.List;

public class TreeItem<T extends TreeTableItem> {
	private List<TreeItem<T>> children = null;
	private final T treeTableItem;
	
	public TreeItem(T treeTableItem) {
		this.treeTableItem = treeTableItem;
	}
	
	public void addChild(TreeItem<T> child) {
		if ( children == null ) {
			children = new ArrayList<TreeItem<T>>();
		}
		children.add(child);
	}
	
	public boolean hasChildren() {
		return children != null;
	}
	
	public List<TreeItem<T>> getChildren() {
		return children;
	}

	public T getTreeTableItem() {
		return treeTableItem;
	}
}