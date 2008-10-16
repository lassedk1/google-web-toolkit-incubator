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
package com.google.gwt.gen2.demo.scrolltable.client.option.sort;

import com.google.gwt.gen2.demo.scrolltable.client.ScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.CustomForm;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option.
 */
public class SortColumnOption extends AbstractOption {
  private static final String DESC_SORTING = "When enabled, users can click on "
      + "header cells to sort the associated "
      + "column.  The default implementation uses a client-side quicksort that "
      + "compares the innerText of cells using the native string comparison "
      + "method.  Users can easily override the sorting algorithm and implement "
      + "their own server-side method using the sorting callback function.";

  /**
   * The label that displays the status.
   */
  private Label statusLabel = null;

  @Override
  protected String getDescription() {
    return DESC_SORTING;
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Add the current status
    statusLabel = new Label();
    form.addLabeledWidget("Current Status:", statusLabel);
    refreshStatus();

    // Add button to change status
    {
      Button button = new Button("Toggle Sorting", new ClickListener() {
        public void onClick(Widget sender) {
          ScrollTable scrollTable = ScrollTableDemo.get().getScrollTable();
          scrollTable.setSortingEnabled(!scrollTable.isSortingEnabled());
          refreshStatus();
        }
      });
      form.addButton(button);
    }

    // Select the column index
    final TextBox columnBox = new TextBox();
    columnBox.setText("3");
    columnBox.setWidth("50px");
    form.addLabeledWidget("Column Index:", columnBox);

    // Add a button to sort the column
    {
      Button button = new Button("Sort Column", new ClickListener() {
        public void onClick(Widget sender) {
          try {
            int column = Integer.parseInt(columnBox.getText());
            ScrollTableDemo.get().getDataTable().sortColumn(column);
            ScrollTableDemo.get().getScrollTable().redraw();
          } catch (NumberFormatException e) {
            Window.alert("Please enter valid integers for the row and column.");
          } catch (IndexOutOfBoundsException e) {
            Window.alert("The row or column index you entered is out of bounds.");
          }
        }
      });
      form.addButton(button);
    }

    // Add a button to make column sortable
    {
      Button button = new Button("Make Sortable", new ClickListener() {
        public void onClick(Widget sender) {
          try {
            int column = Integer.parseInt(columnBox.getText());
            ScrollTableDemo.get().getScrollTable().setColumnSortable(column,
                true);
          } catch (NumberFormatException e) {
            Window.alert("Please enter valid integers for the row and column.");
          }
        }
      });
      form.addButton(button);
    }

    // Add a button to make column unsortable
    {
      Button button = new Button("Make Unsortable", new ClickListener() {
        public void onClick(Widget sender) {
          try {
            int column = Integer.parseInt(columnBox.getText());
            ScrollTableDemo.get().getScrollTable().setColumnSortable(column,
                false);
          } catch (NumberFormatException e) {
            Window.alert("Please enter valid integers for the row and column.");
          }
        }
      });
      form.addButton(button);
    }

    return form;
  }

  /**
   * Refresh the status.
   */
  private void refreshStatus() {
    if (ScrollTableDemo.get().getScrollTable().isSortingEnabled()) {
      statusLabel.setText("enabled");
    } else {
      statusLabel.setText("disabled");
    }
  }
}
