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
package com.google.gwt.gen2.demo.scrolltable.client;

import com.google.gwt.gen2.demo.scrolltable.shared.Student;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.MutableTableModel;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel with options for manipulating data contents and number of cells.
 */
public class ModeledTabDataManipulation extends DemoTabDataManipulation implements ClickListener {

  /**
   * Handle click events from the buttons in this panel.
   * 
   * @param sender
   */
  @Override
  public void onClick(Widget sender) {
    FixedWidthGrid gridView = ScrollTableDemo.getDataTable();
    MutableTableModel<Student> tableModel = PagingScrollTableDemo.getCachedTableModel();
    try {
      if (sender == add1RowButton) {
        // Insert 1 row
        int row = Integer.parseInt(rowIndexBox.getText());
        PagingScrollTableDemo.insertDataRow(row);
      } else if (sender == add10RowButton) {
        // Insert 10 rows
        int row = Integer.parseInt(rowIndexBox.getText());
        for (int i = row; i < row + 10; i++) {
          PagingScrollTableDemo.insertDataRow(i);
        }
      } else if (sender == add100RowButton) {
        // Insert 100 rows
        int row = Integer.parseInt(rowIndexBox.getText());
        for (int i = row; i < row + 100; i++) {
          PagingScrollTableDemo.insertDataRow(i);
        }
      } else if (sender == removeRowButton) {
        // Remove a row
        int row = Integer.parseInt(rowIndexBox.getText());
        tableModel.removeRow(row);
      } else {
        // Default to parent class
        super.onClick(sender);
      }
    } catch (IndexOutOfBoundsException e) {
      Window.alert("The cell index you entered is out of bounds.");
    } catch (NumberFormatException e) {
      Window.alert("Please enter valid integers for the row and column.");
    }
  }

  @Override
  protected Widget onInitialize() {
    Widget w = super.onInitialize();
    return w;
  }
}
