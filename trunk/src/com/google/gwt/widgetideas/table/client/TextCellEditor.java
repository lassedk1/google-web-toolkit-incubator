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

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.widgetideas.table.client.overrides.HTMLTable;

/**
 * A text editor used to edit a single line of text.
 */
public class TextCellEditor extends AbstractCellEditor {
  /**
   * The text field used in this editor.
   */
  private TextBoxBase textBox;
  
  /**
   * Constructor.
   */
  public TextCellEditor() {
    this(new TextBox());
  }
  
  /**
   * Constructor.
   * 
   * Create a new TextCellEditor using a custom defined text box.
   * 
   * @param textBox the text box to use
   */
  public TextCellEditor(TextBoxBase textBox) {
    super(textBox);
    this.textBox = textBox;
  }
  
  /**
   * Get the new value from the cell editor.
   * 
   * @return the new value
   */
  public Object getValue() {
    return textBox.getText();
  }

  /**
   * Fired when editing a cell, just after the cell is shown.
   * 
   * @param table the table that contains the cell
   * @param row the row index
   * @param cell the cell index
   */
  public void onEditCell(HTMLTable table, int row, int cell) {
    textBox.setFocus(true);
  }
  
  /**
   * Set the current value in the cell editor.
   * 
   * @param value the current value
   */
  protected void setValue(Object value) {
    textBox.setText(value.toString());
  }
}
