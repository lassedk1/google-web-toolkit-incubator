package com.google.gwt.gen2.table.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Set;

/**
 * Request object taking care of the tree nodes state
 */
public class TreeRequest extends Request implements IsSerializable {
  private Set<String> invertedNodes;
  private boolean open;

  public TreeRequest() {
  }

  public TreeRequest(int startRow, int numRows, ColumnSortList columnSortList,
      ColumnFilterList columnFilterList, boolean open, Set<String> invertedNodes) {
    super(startRow, numRows, columnSortList, columnFilterList);
    this.open = open;
    this.invertedNodes = invertedNodes;
  }

  public Set<String> getInvertedNodes() {
    return invertedNodes;
  }

  public boolean isOpen() {
    return open;
  }
}