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
package com.google.gwt.gen2.demo.scrolltable.client.option.column;

import com.google.gwt.gen2.demo.scrolltable.client.ScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.CustomForm;
import com.google.gwt.gen2.table.client.AbstractScrollTable;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option.
 */
public class ColumnResizePolicyOption extends AbstractOption {
  /**
   * The list box used to select the resize policy.
   */
  private ListBox policyBox = null;

  @Override
  protected String getDescription() {
    return "Specify how users can resize columns.";
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Add the current policy
    policyBox = new ListBox();
    policyBox.addItem("Disabled");
    policyBox.addItem("Single Cell");
    policyBox.addItem("Multi Cell");
    form.addLabeledWidget("Column Resize Policy:", policyBox);
    refreshPolicy();

    // Add button to change status
    {
      Button button = new Button("Set Resize Policy", new ClickListener() {
        public void onClick(Widget sender) {
          AbstractScrollTable scrollTable = ScrollTableDemo.get().getScrollTable();
          switch (policyBox.getSelectedIndex()) {
            case 0:
              scrollTable.setColumnResizePolicy(ScrollTable.ColumnResizePolicy.DISABLED);
              break;
            case 1:
              scrollTable.setColumnResizePolicy(ScrollTable.ColumnResizePolicy.SINGLE_CELL);
              break;
            case 2:
              scrollTable.setColumnResizePolicy(ScrollTable.ColumnResizePolicy.MULTI_CELL);
              break;
          }
        }
      });
      form.addButton(button);
    }

    // Add button to reset column widths
    {
      Button button = new Button("Reset Column Widths", new ClickListener() {
        public void onClick(Widget sender) {
          AbstractScrollTable scrollTable = ScrollTableDemo.get().getScrollTable();
          scrollTable.resetColumnWidths();
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
    ScrollTable.ColumnResizePolicy policy = ScrollTableDemo.get().getScrollTable().getColumnResizePolicy();
    if (policy == ScrollTable.ColumnResizePolicy.DISABLED) {
      policyBox.setSelectedIndex(0);
    } else if (policy == ScrollTable.ColumnResizePolicy.SINGLE_CELL) {
      policyBox.setSelectedIndex(1);
    } else if (policy == ScrollTable.ColumnResizePolicy.MULTI_CELL) {
      policyBox.setSelectedIndex(2);
    }
  }
}
