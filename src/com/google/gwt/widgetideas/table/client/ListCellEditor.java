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

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.widgetideas.table.client.overrides.HTMLTable;

/**
 * A cell editor that lists its options in a drop down box.
 */
public class ListCellEditor extends AbstractCellEditor {
  /**
   * The list box of options.
   */
  private ListBox listBox;
  
  /**
   * Constructor.
   */
  public ListCellEditor() {
    this(new ListBox());
  }

  /**
   * Constructor.
   * 
   * @param listBox a custom {@link ListBox} to use
   */
  public ListCellEditor(ListBox listBox) {
    super(listBox);
    this.listBox = listBox;
  }

  /**
   * Get the {@link ListBox} of options.
   * 
   * @return the listBox
   */
  public ListBox getListBox() {
    return listBox;
  }

  /**
   * Get the new value from the cell editor.
   * 
   * @return the new value
   */
  public Object getValue() {
    return listBox.getValue(listBox.getSelectedIndex());
  }

  /**
   * Fired when editing a cell, just after the cell is shown.
   * 
   * @param table the table that contains the cell
   * @param row the row index
   * @param cell the cell index
   */
  public void onEditCell(HTMLTable table, int row, int cell) {
    listBox.setFocus(true);
  }
  
  /**
   * Set the current value in the cell editor.
   * 
   * @param value the current value
   */
  protected void setValue(Object value) {
    int numItems = listBox.getItemCount();
    for (int i = 0; i < numItems; i++) {
      if (listBox.getValue(i).equals(value)) {
        listBox.setSelectedIndex(i);
        return;
      }
    }
  }
}
