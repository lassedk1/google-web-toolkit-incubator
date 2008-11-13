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
package com.google.gwt.widgetideas.table.client;

import com.google.gwt.user.client.ui.RadioButton;

/**
 * Tests methods used for all {@link RadioCellEditor} class.
 * 
 * @deprecated Replaced by
 *             {@link com.google.gwt.gen2.table.client.RadioCellEditorTest}
 */
@Deprecated
public class RadioCellEditorTest extends InlineCellEditorTest {
  @Override
  public InlineCellEditor<Object> getCellEditor() {
    return getRadioCellEditor();
  }

  /**
   * Get a new {@link RadioCellEditor}.
   * 
   * @return a radio cell editor
   */
  public RadioCellEditor<Object> getRadioCellEditor() {
    RadioCellEditor<Object> radioEditor = new RadioCellEditor<Object>();
    radioEditor.addRadioButton(new RadioButton("testgroup", "test"));
    radioEditor.addRadioButton(new RadioButton("testgroup", "value"));
    radioEditor.addRadioButton(new RadioButton("testgroup", "test2"));
    return radioEditor;
  }

  /**
   * Test adding and removing buttons.
   */
  public void testAddRadioButtons() {
    // Get a cell editor
    RadioCellEditor<Object> editor = new RadioCellEditor<Object>();

    // We cannot set the value if we have no radio buttons
    editor.setValue("test");
    assertNull(editor.getValue());

    // Add a button and set the value
    RadioButton r1 = new RadioButton("testgroup", "radio1");
    editor.addRadioButton(r1);
    editor.setValue("radio1");
    assertEquals("radio1", editor.getValue());

    // Add another button
    RadioButton r2 = new RadioButton("testgroup", "radio2");
    editor.addRadioButton(r2);
    editor.setValue("radio2");
    assertEquals("radio2", editor.getValue());

    // Remove a button
    editor.removeRadioButton(r1);
    editor.setValue("radio1");
    assertNull(editor.getValue());
  }
}
