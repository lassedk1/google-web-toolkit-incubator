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
package com.google.gwt.gen2.demo.scrolltable.client.option.setup;

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
public class ScrollPolicyOption extends AbstractOption {
  private static final String DESC_SCROLLING = "Enable scrolling to confine "
      + "the data area to a scrollable window.  Disable scrolling to allow the "
      + "data area to grow as needed to fit its content.";

  /**
   * The list box used to select the scrolling policy.
   */
  private ListBox scrollPolicyBox = null;

  @Override
  protected String getDescription() {
    return DESC_SCROLLING;
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Add the policy
    scrollPolicyBox = new ListBox();
    scrollPolicyBox.addItem("both");
    scrollPolicyBox.addItem("horizontal");
    scrollPolicyBox.addItem("disabled");
    form.addLabeledWidget("Scroll Policy:", scrollPolicyBox);
    refreshPolicy();

    // Add button to change status
    {
      Button button = new Button("Set Scroll Policy", new ClickListener() {
        public void onClick(Widget sender) {
          AbstractScrollTable scrollTable = ScrollTableDemo.get().getScrollTable();
          switch (scrollPolicyBox.getSelectedIndex()) {
            case 0:
              scrollTable.setScrollPolicy(ScrollTable.ScrollPolicy.BOTH);
              break;
            case 1:
              scrollTable.setScrollPolicy(ScrollTable.ScrollPolicy.HORIZONTAL);
              break;
            case 2:
              scrollTable.setScrollPolicy(ScrollTable.ScrollPolicy.DISABLED);
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
    ScrollTable.ScrollPolicy policy = ScrollTableDemo.get().getScrollTable().getScrollPolicy();
    if (policy == ScrollTable.ScrollPolicy.BOTH) {
      scrollPolicyBox.setSelectedIndex(0);
    } else if (policy == ScrollTable.ScrollPolicy.BOTH) {
      scrollPolicyBox.setSelectedIndex(1);
    } else if (policy == ScrollTable.ScrollPolicy.DISABLED) {
      scrollPolicyBox.setSelectedIndex(2);
    }
  }
}
