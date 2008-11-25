package com.google.gwt.gen2.demo.scrolltable.shared;

import com.google.gwt.gen2.table.shared.AbstractTreeTableItem;

import java.util.Date;

public class FileTreeTableItem extends AbstractTreeTableItem {
  private String path, name;
  private Double size;
  private Date lastModified;
  
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
}
