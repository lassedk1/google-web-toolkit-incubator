package com.google.gwt.widgetideas.table.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;

public class BulkEditablePagingGrid extends EditableGridView implements
    BulkLoader.BulkLoadable {

  private static StringCellRenderer cellRenderer = new StringCellRenderer() {
    public String renderCell(int row, int cell, Object cellData) {
      return cellData.toString();
    }
  };

  private TableModel tableModel;
  private BulkLoader loader;

  /**
   * Constructor
   * 
   * @param tableModel table model
   * @param rows number of rows displayed
   * @param columns number of columns displayed
   */
  public BulkEditablePagingGrid(MutableTableModel tableModel, int rows,
      int columns) {
    super(tableModel);
    super.resize(rows, columns);
    this.tableModel = tableModel;
    loader = new BulkLoader(this);
  }

  public void setBodyElement(Element e) {
    super.setBodyElement(e);
  }

  public StringCellRenderer getRenderer() {
    return cellRenderer;
  }

  /**
   * Render the visible contents. Does not call onSetData as the data is assumed
   * to be already known to the {@link TableModel}.
   */
  protected void renderContents() {
    int numRows = getLastRow() - getFirstRow() + 1;

    // Remove current widgets.
    Iterator widgets = this.iterator();
    while (widgets.hasNext()) {
      ((Widget) widgets.next()).removeFromParent();
    }

    // Modify numRows
    if (numRows != getRowCount()) {
      resizeRows(numRows);

      // Alert to changed row events.
      tableControllerListener.onNumRowsChanged(numRows);
    }

    updateGhostRow();
  
    String headerRow = DOM.toString(getGhostRow());
    loader.renderRows(tableModel, getFirstRow(), numRows, headerRow, "<td><span>", "</span></td>");

    Element e =  getGhostRow(this.getBodyElement()); 
    
    // Set the ghost row to point at correct location
    setGhostRow(e);
    
    // Fire row paging listeners.
    fireRowPagingEvent();
  }
  
  protected native Element getGhostRow(Element elem) /*-{
    return elem.rows[0];
   }-*/;

  /**
   * Resizes the grid to the specified number of columns. Does nothing until the
   * next table model reload.
   * 
   * @param columns the number of columns
   * @throws IndexOutOfBoundsException
   */
  public void resizeColumns(int columns) {
    if (numColumns == columns) {
      return;
    }
    if (columns < 0) {
      throw new IndexOutOfBoundsException("Cannot set number of columns to "
          + columns);
    }

    numColumns = columns;
  }

  /**
   * Resizes the grid to the specified number of rows. Does nothing until the
   * table model reload.
   * 
   * @param rows the number of rows
   * @throws IndexOutOfBoundsException
   */
  public void resizeRows(int rows) {
    if (numRows == rows) {
      return;
    }
    if (rows < 0) {
      throw new IndexOutOfBoundsException("Cannot set number of rows to "
          + rows);
    }
    numRows = rows;
  }
}
