package com.google.gwt.widgetideas.table.client;

import java.util.ArrayList;
import java.util.Iterator;

public class RowPagingListenerCollection extends ArrayList {

  public void firePageChange(int currentPage, HasRowPaging sender) {
    Iterator it = iterator();
    while (it.hasNext()) {
      HasRowPagingListener listener = (HasRowPagingListener) it.next();
      listener.onPageChanged(currentPage);
    }
  }
}
