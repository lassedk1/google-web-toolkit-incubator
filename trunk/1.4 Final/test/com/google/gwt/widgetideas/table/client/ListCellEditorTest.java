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

/**
 * Tests methods used for all {@link ListCellEditor} class.
 */
public class ListCellEditorTest extends AbstractCellEditorTest {
  /**
   * An editor used for testing.
   */
  private ListCellEditor listEditor = null;

  /**
   * Get the cell editor.
   * 
   * @return the cell editor to test
   */
  public AbstractCellEditor getCellEditor() {
    if (listEditor == null) {
      listEditor = new ListCellEditor();
      ListBox box = listEditor.getListBox();
      box.addItem("test");
      box.addItem("value");
      box.addItem("test2");
    }
    return listEditor;
  }

  /**
   * Test setting the value to null.
   */
  public void testSetNullValue() {
    // Initialize the cell editor
    AbstractCellEditor editor = getCellEditor();

    // Set value to null
    editor.setValue("value");
    editor.setValue(null);
    assertEquals("value", editor.getValue());
  }

}
