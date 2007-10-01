/*
 * Copyright 2007 Google Inc.
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
package com.google.gwt.widgetideas.table.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.widgetideas.table.client.overrides.HTMLTable;

/**
 * An abstract representation of an editor used to edit the contents of a cell.
 */
public abstract class AbstractCellEditor extends PopupPanel {
  /**
   * An {@link ImageBundle} that provides images for {@link AbstractCellEditor}.
   */
  public static interface AbstractCellEditorImages extends ImageBundle {
    /**
     * An image used to fill the available width.
     * 
     * @return a prototype of this image
     */
    AbstractImagePrototype cellEditorAccept();

    /**
     * An image indicating that a column is sorted in ascending order.
     * 
     * @return a prototype of this image
     */
    AbstractImagePrototype cellEditorCancel();
  }

  /**
   * Callback for {@link AbstractCellEditor}. The callback will be used when
   * the user finishes editing the cell.
   */
  public static interface Callback {
    /**
     * Called when the new value of the cell is ready.
     * 
     * @param row the edited row index
     * @param cell the edited cell index
     * @param value the new value of the cell
     */
    public void onAccept(int row, int cell, Object value);

    /**
     * Called when the edit is cancelled.
     * 
     * @param row the edited row index
     * @param cell the edited cell index
     */
    public void onCancel(int row, int cell);
  }

  /**
   * The images used for all cell editors.
   */
  private static AbstractCellEditorImages images = (AbstractCellEditorImages) GWT.create(AbstractCellEditorImages.class);

  /**
   * The current callback.
   */
  private Callback curCallback = null;

  /**
   * The current cell index.
   */
  private int curCell = -1;

  /**
   * The current row index.
   */
  private int curRow = -1;

  /**
   * The label element.
   */
  private HTML label;

  /**
   * The main grid used for layout.
   */
  private FlexTable layoutTable;

  /**
   * Constructor.
   * 
   * @param content the {@link Widget} used to edit
   */
  public AbstractCellEditor(Widget content) {
    setStyleName("gwt-CellEditor");

    // Wrap contents in a grid
    layoutTable = new FlexTable();
    layoutTable.setCellSpacing(0);
    setWidget(layoutTable);

    // Add a label
    label = new HTML("");
    layoutTable.setWidget(0, 0, label);
    FlexCellFormatter formatter = layoutTable.getFlexCellFormatter();
    formatter.setColSpan(0, 0, 3);
    Element td = formatter.getElement(0, 0);
    DOM.setStyleAttribute(td, "padding", "0px");

    // Add content widget
    layoutTable.setWidget(1, 0, content);

    // Add accept button
    Image acceptImage = new Image() {
      public void onBrowserEvent(Event event) {
        if (DOM.eventGetType(event) == Event.ONCLICK) {
          accept();
        }
      }
    };
    images.cellEditorAccept().applyTo(acceptImage);
    DOM.setStyleAttribute(acceptImage.getElement(), "cursor", "pointer");
    acceptImage.setTitle("Accept");
    layoutTable.setWidget(1, 1, acceptImage);

    // Add cancel button
    Image cancelImage = new Image() {
      public void onBrowserEvent(Event event) {
        if (DOM.eventGetType(event) == Event.ONCLICK) {
          cancel();
        }
      }
    };
    images.cellEditorCancel().applyTo(cancelImage);
    DOM.setStyleAttribute(cancelImage.getElement(), "cursor", "pointer");
    cancelImage.setTitle("Cancel");
    layoutTable.setWidget(1, 2, cancelImage);
  }

  /**
   * Popup the editor and start editing the cell value.
   * 
   * @param table the table that contains the cell
   * @param row the row index
   * @param cell the cell index
   */
  public void editCell(HTMLTable table, int row, int cell, Callback callback) {
    // Save the current values
    curCallback = callback;
    curRow = row;
    curCell = cell;

    // Get the location of the cell
    Element cellElem = table.getCellFormatter().getElement(row, cell);
    int top = DOM.getAbsoluteTop(cellElem);
    int left = DOM.getAbsoluteLeft(cellElem);
    setPopupPosition(left, top);

    // Set the current value
    setValue(getCellValue(table, row, cell));

    // Show the editor
    show();

    // Fire events
    onEditCell(table, row, cell);
  }

  /**
   * Get the label text.
   * 
   * @return the label text
   */
  public String getLabel() {
    return label.getHTML();
  }

  /**
   * Get the new value from the cell editor.
   * 
   * @return the new value
   */
  public abstract Object getValue();

  /**
   * Fired when the user accepts. Override this method to return false if the
   * user data is invalid.
   * 
   * @param value the value that is to be accepted
   * @return true to allow accept, false to ignore
   */
  public boolean onAccept(Object value) {
    return true;
  }

  /**
   * Fired when the user cancels. Override this method and return false to
   * prevent the user from cancelling.
   * 
   * @return true to allow cancel, false to ignore
   */
  public boolean onCancel() {
    return true;
  }

  /**
   * Fired when editing a cell, just after the cell is shown.
   * 
   * @param table the table that contains the cell
   * @param row the row index
   * @param cell the cell index
   */
  public void onEditCell(HTMLTable table, int row, int cell) {
  }

  /**
   * Set the label for this cell editor.
   * 
   * @param label the new label
   */
  public void setLabel(String label) {
    this.label.setHTML(label);
  }

  /**
   * Save the value in the cell editor to the actual cell.
   * 
   * @return true of the accept worked, false if not
   */
  protected boolean accept() {
    // Get the value
    Object value = getValue();
    if (!onAccept(value)) {
      return false;
    }
  
    // Hide the popup
    hide();
  
    // Set the cell's contents
    curCallback.onAccept(curRow, curCell, value);
  
    // Clear out the data
    curCallback = null;
    curRow = -1;
    curCell = -1;
    
    return true;
  }

  /**
   * Cancel the cell edit.
   * 
   * @return true of the cancel worked, false if not
   */
  protected boolean cancel() {
    // Fire event
    if (!onCancel()) {
      return false;
    }
    
    // Hide the popup
    hide();

    // Call the callback
    if (curCallback != null) {
      curCallback.onCancel(curRow, curCell);
      curCallback = null;
      curRow = -1;
      curCell = -1;
    }
    
    return true;
  }

  /**
   * Get the current value of the cell. By default, this method returns the
   * Widget or text within the cell. Override it to return a custom object based
   * on the cell's contents.
   * 
   * @param table the table that contains the cell
   * @param row the row index
   * @param cell the cell index
   */
  protected Object getCellValue(HTMLTable table, int row, int cell) {
    Widget widget = table.getWidget(row, cell);
    if (widget == null) {
      return table.getHTML(row, cell);
    } else {
      return widget;
    }
  }

  /**
   * Get the editable widget.
   * 
   * @return the widget used to edit values.
   */
  protected Widget getContentWidget() {
    return layoutTable.getWidget(1, 0);
  }

  /**
   * Set the current value in the cell editor.
   * 
   * @param value the current value
   */
  protected abstract void setValue(Object value);
}
