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

import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Tests methods used for all {@link TextCellEditor} class.
 */
public class TextCellEditorTest extends InlineCellEditorTest {
  @Override
  public InlineCellEditor<String> createCellEditor() {
    return new TextCellEditor();
  }

  /**
   * Test the ability to retrieve a text box.
   */
  public void testCustomTextBox() {
    // Use a text box
    {
      TextBox textBox = new TextBox();
      TextCellEditor editor = new TextCellEditor(textBox);
      assertEquals(textBox, editor.getTextBox());
    }

    // Use a text area
    {
      TextArea textArea = new TextArea();
      TextCellEditor editor = new TextCellEditor(textArea);
      assertEquals(textArea, editor.getTextBox());
    }

    // Use a password field
    {
      PasswordTextBox passBox = new PasswordTextBox();
      TextCellEditor editor = new TextCellEditor(passBox);
      assertEquals(passBox, editor.getTextBox());
    }
  }
}
