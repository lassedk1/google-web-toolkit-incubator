/*
 * Copyright 2008 Google Inc.
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

/**
 * Tests methods used for all {@link ListCellEditor} class.
 */
public class ListCellEditorTest extends InlineCellEditorTest {
  @Override
  public InlineCellEditor<String> createCellEditor() {
    ListCellEditor<String> listEditor = new ListCellEditor<String>();
    for (int i = 0; i < 3; i++) {
      listEditor.addItem("text" + i, "value" + i);
    }
    return listEditor;
  }

  /**
   * Test adding and removing items.
   */
  public void testAddItems() {
    // Get a cell editor
    ListCellEditor<Integer> editor = new ListCellEditor<Integer>();

    // We cannot set the value if we have no radio buttons
    {
      editor.setValue(new Integer(0));
      assertNull(editor.getValue());
    }

    // Add a button and set the value
    {
      Integer cellValue = new Integer(0);
      editor.addItem("item0", cellValue);
      editor.setValue(cellValue);
      assertEquals(cellValue, editor.getValue());
    }

    // Add another button
    {
      Integer cellValue = new Integer(1);
      editor.addItem("item1", cellValue);
      editor.setValue(cellValue);
      assertEquals(cellValue, editor.getValue());

      // Remove the new button
      editor.removeItem(1);
      editor.setValue(cellValue);
    }
  }
}
