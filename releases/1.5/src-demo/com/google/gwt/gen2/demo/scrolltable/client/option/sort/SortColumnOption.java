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

import com.google.gwt.gen2.demo.scrolltable.client.PagingScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.ScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.CustomForm;
import com.google.gwt.gen2.table.client.AbstractScrollTable;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.gen2.table.client.AbstractScrollTable.SortPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ListBox;
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
   * The list box used to select the sort policy.
   */
  private ListBox policyBox = null;

  @Override
  protected String getDescription() {
    return DESC_SORTING;
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Add the current policy
    policyBox = new ListBox();
    policyBox.addItem("Disabled");
    policyBox.addItem("Single Cell");
    policyBox.addItem("Multi Cell");
    form.addLabeledWidget("Sort Policy:", policyBox);
    refreshPolicy();

    // Add button to change policy
    {
      Button button = new Button("Set Sort Policy", new ClickListener() {
        public void onClick(Widget sender) {
          AbstractScrollTable scrollTable = ScrollTableDemo.get().getScrollTable();
          switch (policyBox.getSelectedIndex()) {
            case 0:
              scrollTable.setSortPolicy(SortPolicy.DISABLED);
              break;
            case 1:
              scrollTable.setSortPolicy(SortPolicy.SINGLE_CELL);
              break;
            case 2:
              scrollTable.setSortPolicy(SortPolicy.MULTI_CELL);
              break;
          }
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
    if (PagingScrollTableDemo.get() == null) {
      Button button = new Button("Make Sortable", new ClickListener() {
        public void onClick(Widget sender) {
          try {
            int column = Integer.parseInt(columnBox.getText());
            ScrollTable scrollTable = (ScrollTable) ScrollTableDemo.get().getScrollTable();
            scrollTable.setColumnSortable(column, true);
          } catch (NumberFormatException e) {
            Window.alert("Please enter valid integers for the row and column.");
          }
        }
      });
      form.addButton(button);
    }

    // Add a button to make column unsortable
    if (PagingScrollTableDemo.get() == null) {
      Button button = new Button("Make Unsortable", new ClickListener() {
        public void onClick(Widget sender) {
          try {
            int column = Integer.parseInt(columnBox.getText());
            ScrollTable scrollTable = (ScrollTable) ScrollTableDemo.get().getScrollTable();
            scrollTable.setColumnSortable(column, false);
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
   * Refresh the policy.
   */
  private void refreshPolicy() {
    SortPolicy policy = ScrollTableDemo.get().getScrollTable().getSortPolicy();
    if (policy == SortPolicy.DISABLED) {
      policyBox.setSelectedIndex(0);
    } else if (policy == SortPolicy.SINGLE_CELL) {
      policyBox.setSelectedIndex(1);
    } else if (policy == SortPolicy.MULTI_CELL) {
      policyBox.setSelectedIndex(2);
    }
  }
}
