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

import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Tests methods used for all {@link TextCellEditor} class.
 * 
 * @deprecated Replaced by
 *             {@link com.google.gwt.gen2.table.client.TextCellEditorTest}
 */
@Deprecated
public class TextCellEditorTest extends InlineCellEditorTest {
  @Override
  public InlineCellEditor<Object> getCellEditor() {
    return new TextCellEditor<Object>();
  }

  /**
   * Test the ability to retrieve a text box.
   */
  public void testCustomTextBox() {
    // Use a text box
    TextBox textBox = new TextBox();
    TextCellEditor<Object> editor1 = new TextCellEditor<Object>(textBox);
    assertEquals(textBox, editor1.getTextBox());

    // Use a text area
    TextArea textArea = new TextArea();
    TextCellEditor<Object> editor2 = new TextCellEditor<Object>(textArea);
    assertEquals(textArea, editor2.getTextBox());

    // Use a password field
    PasswordTextBox passBox = new PasswordTextBox();
    TextCellEditor<Object> editor3 = new TextCellEditor<Object>(passBox);
    assertEquals(passBox, editor3.getTextBox());
  }
}
