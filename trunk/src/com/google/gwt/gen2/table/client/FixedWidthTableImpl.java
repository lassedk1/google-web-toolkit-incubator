/*
 * Copyright 2009 Google Inc.
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
import com.google.gwt.gen2.table.override.client.HTMLTable;
import com.google.gwt.gen2.table.override.client.OverrideDOM;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;

/**
 * Implementation class that handles common code shared between the
 * {@link FixedWidthGrid} and {@link FixedWidthFlexTable}.
 */
class FixedWidthTableImpl {
  /**
   * The implementation singleton.
   */
  private static Impl impl;

  /**
   * Get the implementation singleton.
   */
  public static Impl get() {
    if (impl == null) {
      impl = GWT.create(Impl.class);
    }
    return impl;
  }

  /**
   * Information used to calculate the ideal column widths of a table.
   */
  public static class IdealColumnWidthInfo {
    private HTMLTable table;
    private Element clonedTable;
    private Element clonedTableBody;
  }

  /**
   * An implementation used accommodate differences in column width
   * implementations between browsers.
   */
  public static class Impl {

    /**
     * Create a cell to use in the ghost row.
     * 
     * @param td the optional td to modify
     * @return the cell
     */
    public Element createGhostCell(Element td) {
      if (td == null) {
        td = OverrideDOM.createTD();
      }
      td.getStyle().setPropertyPx("height", 0);
      td.getStyle().setProperty("overflow", "hidden");
      td.getStyle().setPropertyPx("paddingTop", 0);
      td.getStyle().setPropertyPx("paddingBottom", 0);
      td.getStyle().setPropertyPx("borderTop", 0);
      td.getStyle().setPropertyPx("borderBottom", 0);
      td.getStyle().setPropertyPx("margin", 0);
      return td;
    }

    /**
     * Create the ghost row.
     * 
     * @return the ghost row element
     */
    public Element createGhostRow() {
      Element ghostRow = DOM.createTR();
      ghostRow.getStyle().setPropertyPx("margin", 0);
      ghostRow.getStyle().setPropertyPx("padding", 0);
      ghostRow.getStyle().setPropertyPx("height", 0);
      ghostRow.getStyle().setProperty("overflow", "hidden");
      return ghostRow;
    }

    /**
     * Returns a cell in the ghost row.
     * 
     * @param column the cell's column
     * @return the ghost cell
     */
    public Element getGhostCell(Element ghostRow, int column) {
      return DOM.getChild(ghostRow, column);
    }

    /**
     * Recalculate the ideal column widths of each column in the data table.
     * This method assumes that the tableLayout has already been changed.
     * 
     * @param info the {@link IdealColumnWidthInfo}
     * @param columnCount the number of columns in the table
     * @param offset the offset of the first cell in the row
     * @return the ideal column widths
     */
    public int[] recalculateIdealColumnWidths(IdealColumnWidthInfo info,
        int columnCount, int offset) {
      // We need at least one cell to do any calculations
      HTMLTable table = info.table;
      if (!table.isAttached() || table.getRowCount() == 0 || columnCount < 1) {
        return new int[0];
      }

      // Get the first row in the cloned table
      Element tbody = info.clonedTableBody;
      com.google.gwt.dom.client.Element tr = getRowElement(tbody, 0);

      // Determine the width of each column
      int[] idealWidths = new int[columnCount];
      com.google.gwt.dom.client.Element td = tr.getFirstChildElement();
      for (int i = 0; i < offset; i++) {
        td = td.getNextSiblingElement();
      }
      for (int i = 0; i < columnCount; i++) {
        idealWidths[i] = td.getPropertyInt("clientWidth");
        td = td.getNextSiblingElement();
      }
      return idealWidths;
    }

    /**
     * Setup to recalculate column widths. This method clones and returns the
     * table element.
     * 
     * @param table the table
     * @param ghostRow the ghost row
     * @return the {@link IdealColumnWidthInfo} used during calculations
     */
    public IdealColumnWidthInfo recalculateIdealColumnWidthsSetup(
        HTMLTable table, Element ghostRow) {
      Element tableElem = table.getElement();
      IdealColumnWidthInfo info = new IdealColumnWidthInfo();
      info.table = table;
      info.clonedTable = (Element) tableElem.cloneNode(false);
      info.clonedTableBody = (Element) getTableBody(table).cloneNode(true);
      info.clonedTable.appendChild(info.clonedTableBody);
      info.clonedTable.getStyle().setProperty("tableLayout", "");
      tableElem.getParentElement().insertBefore(info.clonedTable, tableElem);
      return info;
    }

    /**
     * Tear down after recalculating column widths.
     * 
     * @param info the {@link IdealColumnWidthInfo}
     */
    public void recalculateIdealColumnWidthsTeardown(IdealColumnWidthInfo info) {
      info.clonedTable.getParentElement().removeChild(info.clonedTable);
      info.table = null;
      info.clonedTable = null;
      info.clonedTableBody = null;
    }

    /**
     * Set the width of a column using the ghost row.
     * 
     * @param table the table containing the column
     * @param ghostRow the ghost row element
     * @param column the index of the column
     * @param width the width in pixels
     * @throws IndexOutOfBoundsException
     */
    public void setColumnWidth(HTMLTable table, Element ghostRow, int column,
        int width) {
      getGhostCell(ghostRow, column).getStyle().setPropertyPx("width", width);
    }

    /**
     * Gets the TR element representing the specified row.
     * 
     * @param tbody the table body element
     * @param row the row of the cell to be retrieved
     * @return the row's TR element
     */
    private native com.google.gwt.dom.client.Element getRowElement(
        com.google.gwt.dom.client.Element tbody, int row)
    /*-{
      return tbody.rows[row];
    }-*/;

    /**
     * Get the body element of an {@link HTMLTable}.
     * 
     * @param table the table
     * @return the body elements
     */
    private native Element getTableBody(HTMLTable table)
    /*-{
      return table.@com.google.gwt.gen2.table.override.client.HTMLTable::getBodyElement()();
    }-*/;
  }

  /**
   * Firefox version of the implementation refreshes the table display so the
   * new column size takes effect. Without the display refresh, the column width
   * doesn't update in the browser.
   */
  @SuppressWarnings("unused")
  public static class ImplFirefox extends Impl {
    private boolean pendingUpdate = false;

    @Override
    public void setColumnWidth(HTMLTable table, Element ghostRow, int column,
        int width) {
      super.setColumnWidth(table, ghostRow, column, width);
      if (!pendingUpdate) {
        pendingUpdate = true;
        final Element tableElem = table.getElement();
        // TODO (jlabanca): Can we work around this deferred command?
        tableElem.getStyle().setProperty("width", "1px");
        DeferredCommand.addCommand(new Command() {
          public void execute() {
            tableElem.getStyle().setProperty("width", "0px");
            pendingUpdate = false;
          }
        });
      }
    }
  }

  /**
   * IE6 version of the implementation. IE6 sets the overall column width
   * instead of the innerWidth, so we need to add the padding and spacing.
   */
  @SuppressWarnings("unused")
  public static class ImplIE6 extends Impl {
    @Override
    public Element createGhostRow() {
      Element ghostRow = super.createGhostRow();
      ghostRow.getStyle().setProperty("display", "none");
      return ghostRow;
    }

    @Override
    public IdealColumnWidthInfo recalculateIdealColumnWidthsSetup(
        HTMLTable table, Element ghostRow) {
      ghostRow.getStyle().setProperty("display", "");
      IdealColumnWidthInfo toRet = super.recalculateIdealColumnWidthsSetup(
          table, ghostRow);
      ghostRow.getStyle().setProperty("display", "none");
      return toRet;
    }

    @Override
    public void setColumnWidth(HTMLTable table, Element ghostRow, int column,
        int width) {
      width += 2 * table.getCellPadding() + table.getCellSpacing();
      super.setColumnWidth(table, ghostRow, column, width);
    }
  }

  /**
   * Safari version of the implementation. Safari sets the overall column width
   * instead of the innerWidth, so we need to add the padding and spacing.
   */
  @SuppressWarnings("unused")
  public static class ImplSafari extends Impl {
    @Override
    public void setColumnWidth(HTMLTable table, Element ghostRow, int column,
        int width) {
      width += 2 * table.getCellPadding() + table.getCellSpacing();
      super.setColumnWidth(table, ghostRow, column, width);
    }
  }

  /**
   * Opera version of the implementation refreshes the table display so the new
   * column size takes effect. Without the display refresh, the column width
   * doesn't update in the browser.
   */
  @SuppressWarnings("unused")
  public static class ImplOpera extends Impl {
    @Override
    public void setColumnWidth(HTMLTable table, Element ghostRow, int column,
        int width) {
      super.setColumnWidth(table, ghostRow, column, width);
      Element tableElem = table.getElement();
      Element parentElem = DOM.getParent(tableElem);
      int scrollLeft = 0;
      if (parentElem != null) {
        scrollLeft = parentElem.getScrollLeft();
      }
      tableElem.getStyle().setProperty("display", "none");
      tableElem.getStyle().setProperty("display", "");
      if (parentElem != null) {
        parentElem.setScrollLeft(scrollLeft);
      }
    }
  }
}
