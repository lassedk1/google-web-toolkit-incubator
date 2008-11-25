package com.google.gwt.gen2.demo.scrolltable.shared;

import com.google.gwt.gen2.table.shared.TreeTableItem;

import java.util.Date;

public class FileTreeTableItem implements TreeTableItem {
  private String path, name;
  private Double size;
  private Date lastModified;
  private FileTreeTableItem parent;
  private int row;
  private boolean hasChildren;
  
  public FileTreeTableItem() {}
  
  public FileTreeTableItem(String path, String name, Double size, Date lastModified) {
    this.path = path;
    this.name = name;
    this.size = size;
    this.lastModified = lastModified;
  }

  public String getPath() {
    return path;
  }

  public String getName() {
    return name;
  }

  public Double getSize() {
    return size;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public String getDisplayName() {
    return name;
  }

  public String getId() {
    return path;
  }

  public void setParent(FileTreeTableItem parent) {
    this.parent = parent;
  }

  public TreeTableItem getParent() {
    return parent;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getRow() {
    return row;
  }

  public void setChildren(boolean hasChildren) {
    this.hasChildren = hasChildren;
  }
  
  public boolean hasChildren() {
    return hasChildren;
  }
}
