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

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.widgetideas.client.WidgetTestBase;
import com.google.gwt.widgetideas.table.client.AbstractCellEditor.CellEditInfo;
import com.google.gwt.widgetideas.table.client.overrides.Grid;

/**
 * Tests methods used for all {@link AbstractCellEditor} class.
 */
public class InlineCellEditorTest extends WidgetTestBase {
  /**
   * A callback used for testing.
   */
  public static class TestCallback implements
      AbstractCellEditor.Callback<Object> {
    /**
     * Has cancel been called.
     */
    private boolean cancelled = false;

    /**
     * Has completed been called.
     */
    private boolean completed = false;

    /**
     * The expected {@link CellEditInfo} to be returned with each callback.
     */
    private CellEditInfo<Object> expectedInfo;

    /**
     * Constructor.
     * 
     * @param expectedInfo the expected {@link CellEditInfo}
     */
    public TestCallback(CellEditInfo<Object> expectedInfo) {
      this.expectedInfo = expectedInfo;
    }

    /**
     * Assert that cancel has been called.
     * 
     * @param expected do we expect this to be cancelled
     */
    public void assertCancelled(boolean expected) {
      assertEquals(expected, cancelled);
    }

    /**
     * Assert that completed has been called.
     * 
     * @param expected do we expect this to be completed
     */
    public void assertCompleted(boolean expected) {
      assertEquals(expected, completed);
    }

    public void onCancel(CellEditInfo<Object> cellEditInfo) {
      assertEquals(expectedInfo, cellEditInfo);
      cancelled = true;
    }

    public void onComplete(CellEditInfo<Object> cellEditInfo, Object value) {
      assertEquals(expectedInfo, cellEditInfo);
      completed = true;
    }
  }
 
  /**
   * Get the cell editor.
   * 
   * @return the cell editor to test
   */
  public InlineCellEditor<Object> getCellEditor() {
    return new InlineCellEditor<Object>(new Label()) {
      private Object value = null;

      @Override
      protected Object getValue() {
        return value;
      }

      @Override
      protected void setValue(Object value) {
        this.value = value;
      }
    };
  }

  /**
   * Test accepting the value in the editor.
   */
  public void testAccept() {
    // Initialize the cell editor
    InlineCellEditor<Object> editor = getCellEditor();

    // Create the edit info
    Grid grid = new Grid(1, 1);
    grid.setHTML(0, 0, "test");
    CellEditInfo<Object> info = new CellEditInfo<Object>(grid, 0, 0, null);

    // Create the callback
    TestCallback callback = new TestCallback(info);

    // Edit the cell
    editor.editCell(info, callback);
    assertEquals("test", editor.getValue());

    // Accept the value
    editor.accept();
    callback.assertCompleted(true);
    callback.assertCancelled(false);
  }

  /**
   * Test the basic accessors.
   */
  public void testAccessors() {
    // Initialize the cell editor
    Label content = new Label("content");
    InlineCellEditor<Object> editor = new InlineCellEditor<Object>(content) {
      private Object value = null;

      @Override
      protected Object getValue() {
        return value;
      }

      @Override
      protected void setValue(Object value) {
        this.value = value;
      }
    };

    // Label
    editor.setLabel("Hello World");
    assertEquals("Hello World", editor.getLabel());
    editor.setLabel(null);
    assertEquals("", editor.getLabel());

    // Accept widget
    HTML acceptWidget = new HTML("Accept");
    editor.setAcceptWidget(acceptWidget);
    assertEquals(acceptWidget, editor.getAcceptWidget());

    // Cancel widget
    HTML cancelWidget = new HTML("Cancel");
    editor.setCancelWidget(cancelWidget);
    assertEquals(cancelWidget, editor.getCancelWidget());

    // Content widget
    assertEquals(content, editor.getContentWidget());
  }

  /**
   * Test accepting the value in the editor.
   */
  public void testCancelled() {
    // Initialize the cell editor
    InlineCellEditor<Object> editor = getCellEditor();

    // Create the edit info
    Grid grid = new Grid(1, 1);
    grid.setHTML(0, 0, "test");
    CellEditInfo<Object> info = new CellEditInfo<Object>(grid, 0, 0, null);

    // Create the callback
    TestCallback callback = new TestCallback(info);

    // Edit the cell
    editor.editCell(info, callback);
    assertEquals("test", editor.getValue());

    // Cancel the value
    editor.cancel();
    callback.assertCompleted(false);
    callback.assertCancelled(true);
  }

  /**
   * Test the {@link CellEditInfo} inner class.
   */
  public void testCellEditInfo() {
    Grid grid = new Grid(1, 1);
    String testObject = "TestObject";
    CellEditInfo<String> info = new CellEditInfo<String>(grid, 1, 2, testObject);
    assertEquals(grid, info.getTable());
    assertEquals(1, info.getRow());
    assertEquals(2, info.getCell());
    String testRowValue = info.getRowValue();
    assertEquals(testObject, testRowValue);
  }

  /**
   * Test setting the value.
   */
  public void testSetValue() {
    // Initialize the cell editor
    InlineCellEditor<Object> editor = getCellEditor();

    // Set the value
    String value = "value";
    editor.setValue(value);
    assertEquals(value, editor.getValue());
  }
}
