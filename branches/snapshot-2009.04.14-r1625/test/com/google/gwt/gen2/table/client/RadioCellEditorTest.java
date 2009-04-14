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

import com.google.gwt.user.client.ui.RadioButton;

/**
 * Tests methods used for all {@link RadioCellEditor} class.
 */
public class RadioCellEditorTest extends InlineCellEditorTest {
  @Override
  public InlineCellEditor<String> createCellEditor() {
    RadioCellEditor<String> radioEditor = new RadioCellEditor<String>();
    for (int i = 0; i < 3; i++) {
      radioEditor.addRadioButton(new RadioButton("testgroup", "label" + i),
          "value" + i);
    }
    return radioEditor;
  }

  /**
   * Test adding and removing buttons.
   */
  public void testAddRadioButtons() {
    // Get a cell editor
    RadioCellEditor<Integer> editor = new RadioCellEditor<Integer>();

    // We cannot set the value if we have no radio buttons
    {
      editor.setValue(new Integer(0));
      assertNull(editor.getValue());
    }

    // Add a button and set the value
    {
      Integer cellValue = new Integer(0);
      RadioButton radio = new RadioButton("testgroup", "radio0");
      editor.addRadioButton(radio, cellValue);
      assertNull(editor.getValue());
      editor.setValue(cellValue);
      assertEquals(cellValue, editor.getValue());
      assertTrue(radio.getValue());
    }

    // Add another button
    {
      Integer cellValue = new Integer(1);
      RadioButton radio = new RadioButton("testgroup", "radio1");
      editor.addRadioButton(radio, cellValue);
      editor.setValue(cellValue);
      assertEquals(cellValue, editor.getValue());
      assertTrue(radio.getValue());

      // Remove the new button
      editor.removeRadioButton(radio);
      editor.setValue(cellValue);
      assertNull(editor.getValue());
    }
  }
}
