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

import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.gen2.table.client.CellEditor.CellEditInfo;
import com.google.gwt.gen2.table.override.client.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

/**
 * Tests methods used for all {@link InlineCellEditor} class.
 */
public class InlineCellEditorTest extends Gen2TestBase {
  /**
   * A callback used for testing.
   */
  public static class TestCallback<ColType> implements CellEditor.Callback<ColType> {
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
    private CellEditInfo expectedInfo;

    /**
     * Construct a new {@link TestCallback}.
     * 
     * @param expectedInfo the expected {@link CellEditInfo}
     */
    public TestCallback(CellEditInfo expectedInfo) {
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

    public void onCancel(CellEditInfo cellEditInfo) {
      assertEquals(expectedInfo, cellEditInfo);
      cancelled = true;
    }

    public void onComplete(CellEditInfo cellEditInfo, ColType cellValue) {
      assertEquals(expectedInfo, cellEditInfo);
      completed = true;
    }
  }

  /**
   * Get a new {@link InlineCellEditor}. It should be possible to set a value
   * equal to the String "value1".
   * 
   * @return the cell editor to test
   */
  public InlineCellEditor<String> createCellEditor() {
    return new InlineCellEditor<String>(new Label()) {
      private String cellValue = null;

      @Override
      protected String getValue() {
        return cellValue;
      }

      @Override
      protected void setValue(String cellValue) {
        this.cellValue = cellValue;
      }
    };
  }

  /**
   * Test accepting the value in the editor.
   */
  public void testAccept() {
    // Initialize the cell editor
    InlineCellEditor<String> editor = createCellEditor();

    // Create the edit info
    Grid grid = new Grid(1, 1);
    CellEditInfo cellEditInfo = new CellEditInfo(grid, 0, 0);

    // Create the callback
    TestCallback<String> callback = new TestCallback<String>(cellEditInfo);

    // Edit the cell
    editor.editCell(cellEditInfo, "value1", callback);
    assertEquals("value1", editor.getValue());

    // Accept the value
    editor.accept();
    callback.assertCompleted(true);
    callback.assertCancelled(false);
  }

  /**
   * Test the basic accessors.
   */
  public void testAccessors() {
    // getLabel
    {
      InlineCellEditor<String> editor = createCellEditor();
      editor.setLabel("Hello World");
      assertEquals("Hello World", editor.getLabel());
      editor.setLabel(null);
      assertEquals("", editor.getLabel());
    }

    // setAcceptWidget
    {
      InlineCellEditor<String> editor = createCellEditor();
      HTML acceptWidget = new HTML("Accept");
      editor.setAcceptWidget(acceptWidget);
      assertEquals(acceptWidget, editor.getAcceptWidget());
    }

    // setCancelWidget
    {
      InlineCellEditor<String> editor = createCellEditor();
      HTML cancelWidget = new HTML("Cancel");
      editor.setCancelWidget(cancelWidget);
      assertEquals(cancelWidget, editor.getCancelWidget());
    }
  }

  /**
   * Test accepting the value in the editor.
   */
  public void testCancelled() {
    // Initialize the cell editor
    InlineCellEditor<String> editor = createCellEditor();

    // Create the edit info
    Grid grid = new Grid(1, 1);
    CellEditInfo cellEditInfo = new CellEditInfo(grid, 0, 0);

    // Create the callback
    TestCallback<String> callback = new TestCallback<String>(cellEditInfo);

    // Edit the cell
    editor.editCell(cellEditInfo, "value1", callback);
    assertEquals("value1", editor.getValue());

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
    CellEditInfo info = new CellEditInfo(grid, 1, 2);
    assertEquals(grid, info.getTable());
    assertEquals(1, info.getRowIndex());
    assertEquals(2, info.getCellIndex());
  }
}
