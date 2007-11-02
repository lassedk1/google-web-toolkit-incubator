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

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.HTML;

/**
 * Tests methods used for all {@link AbstractCellEditor} class.
 */
public class AbstractCellEditorTest extends GWTTestCase {
  /**
   * An {@link AbstractCellEditor} used for testing.
   */
  private AbstractCellEditor testCellEditor = null;

  public String getModuleName() {
    return "com.google.gwt.widgetideas.WidgetIdeas";
  }

  /**
   * Get the cell editor.
   * 
   * @return the cell editor to test
   */
  public AbstractCellEditor getCellEditor() {
    if (testCellEditor == null) {
      testCellEditor = new AbstractCellEditor(new HTML(), true) {
        private Object value = null;

        public Object getValue() {
          return value;
        }

        protected void setValue(Object value) {
          this.value = value;
        }

      };
    }
    return testCellEditor;
  }
  
  /**
   * Test the basic accessors.
   */
  public void testAccessors() {
    // Initialize the cell editor
    AbstractCellEditor editor = getCellEditor();
    
    // Accept/Cancel buttons
    assertNotNull(editor.getAcceptWidget());
    assertNotNull(editor.getCancelWidget());
    HTML newAccept = new HTML("Accept");
    HTML newCancel = new HTML("Cancel");
    editor.setAcceptWidget(newAccept);
    editor.setCancelWidget(newCancel);
    assertEquals(newAccept, editor.getAcceptWidget());
    assertEquals(newCancel, editor.getCancelWidget());
    
    // Label
    editor.setLabel("Hello World");
    assertEquals("Hello World", editor.getLabel());
    editor.setLabel(null);
    assertEquals("", editor.getLabel());
  }

  /**
   * Test setting the value to null.
   */
  public void testSetNullValue() {
    // Initialize the cell editor
    AbstractCellEditor editor = getCellEditor();
    
    // Set value to null
    editor.setValue(null);
    assertEquals(null, editor.getValue());
  }
  
  /**
   * Test setting the value.
   */
  public void testSetValue() {
    // Initialize the cell editor
    AbstractCellEditor editor = getCellEditor();
    
    // Set the value
    String value = "value";
    editor.setValue(value);
    assertEquals(value, editor.getValue());
  }
}
