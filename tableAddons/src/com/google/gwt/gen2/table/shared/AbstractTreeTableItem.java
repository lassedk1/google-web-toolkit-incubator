package com.google.gwt.gen2.table.shared;

public abstract class AbstractTreeTableItem implements TreeTableItem {
  String id, displayName;
  TreeTableItem parent;
  int row;
  boolean hasChildren;

  public AbstractTreeTableItem() {
  }

  public abstract String getId();
  
  public abstract String getDisplayName();
  
  public TreeTableItem getParent() {
    return parent;
  }

  public void setParent(TreeTableItem parent) {
    this.parent = parent;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public boolean hasChildren() {
    return hasChildren;
  }

  public void setChildren(boolean hasChildren) {
    this.hasChildren = hasChildren;
  }
}