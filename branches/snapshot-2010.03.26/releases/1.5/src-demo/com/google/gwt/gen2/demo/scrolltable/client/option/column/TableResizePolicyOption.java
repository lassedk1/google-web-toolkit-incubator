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
public class TableResizePolicyOption extends AbstractOption {
  private static final String DESC_RESIZING = "When fixed width or fill "
      + "width resizing is "
      + "enabled, the grid will do its best to make sure that its contents fit "
      + "withing its visible area so you do not see a horizontal scrollbar.";

  /**
   * The list box used to select the scrolling policy.
   */
  private ListBox policyBox = null;

  @Override
  protected String getDescription() {
    return DESC_RESIZING;
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Add the current policy
    policyBox = new ListBox();
    policyBox.addItem("Unconstrained");
    policyBox.addItem("Flow");
    policyBox.addItem("Fixed");
    policyBox.addItem("Fill");
    form.addLabeledWidget("Resize Policy:", policyBox);
    refreshPolicy();

    // Add button to change status
    {
      Button button = new Button("Set Resize Policy", new ClickListener() {
        public void onClick(Widget sender) {
          AbstractScrollTable scrollTable = ScrollTableDemo.get().getScrollTable();
          switch (policyBox.getSelectedIndex()) {
            case 0:
              scrollTable.setResizePolicy(ScrollTable.ResizePolicy.UNCONSTRAINED);
              break;
            case 1:
              scrollTable.setResizePolicy(ScrollTable.ResizePolicy.FLOW);
              break;
            case 2:
              scrollTable.setResizePolicy(ScrollTable.ResizePolicy.FIXED_WIDTH);
              break;
            case 3:
              scrollTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);
              break;
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
    ScrollTable.ResizePolicy policy = ScrollTableDemo.get().getScrollTable().getResizePolicy();
    if (policy == ScrollTable.ResizePolicy.UNCONSTRAINED) {
      policyBox.setSelectedIndex(0);
    } else if (policy == ScrollTable.ResizePolicy.FLOW) {
      policyBox.setSelectedIndex(1);
    } else if (policy == ScrollTable.ResizePolicy.FIXED_WIDTH) {
      policyBox.setSelectedIndex(2);
    } else if (policy == ScrollTable.ResizePolicy.FILL_WIDTH) {
      policyBox.setSelectedIndex(3);
    }
  }
}
