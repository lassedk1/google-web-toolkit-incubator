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
package com.google.gwt.gen2.demo.scrolltable.client.option.highlight;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.demo.scrolltable.client.PagingScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.ScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.CustomForm;
import com.google.gwt.gen2.table.client.SelectionGrid;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option.
 */
public class RowSelectionPolicyOption extends AbstractOption {
  private static final String DESC_SELECTION = "Row selection allows the user "
      + "to select one or more rows from the table "
      + "using mouse clicks.  Use the ctrl button to add to the selected rows, "
      + "and use the shift button to select multiple rows in a range.";

  /**
   * The list box used to select the selection policy.
   */
  private ListBox policyBox = null;

  /**
   * The label used to display selection status.
   */
  private Label statusLabel = null;

  @Override
  protected String getDescription() {
    return DESC_SELECTION;
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Add the current status
    statusLabel = new Label();
    form.addLabeledWidget("Selection Status:", statusLabel);

    // Add the current policy
    policyBox = new ListBox();
    policyBox.addItem("Multi Row");
    policyBox.addItem("Single Row");
    policyBox.addItem("Checkboxes");
    policyBox.addItem("Radio Buttons");
    form.addLabeledWidget("Selection Policy:", policyBox);

    // Add button to change policy
    {
      Button button = new Button("Set Selection Policy", new ClickHandler() {
        public void onClick(ClickEvent event) {
          SelectionGrid grid = ScrollTableDemo.get().getDataTable();
          switch (policyBox.getSelectedIndex()) {
            case 0:
              grid.setSelectionPolicy(SelectionPolicy.MULTI_ROW);
              break;
            case 1:
              grid.setSelectionPolicy(SelectionPolicy.ONE_ROW);
              break;
            case 2:
              grid.setSelectionPolicy(SelectionPolicy.CHECKBOX);
              break;
            case 3:
              grid.setSelectionPolicy(SelectionPolicy.RADIO);
              break;
          }
          PagingScrollTableDemo.get().getPagingScrollTable().reloadPage();
        }
      });
      form.addButton(button);
    }

    // Add button to change status
    {
      Button button = new Button("Toggle Status", new ClickHandler() {
        public void onClick(ClickEvent event) {
          SelectionGrid grid = ScrollTableDemo.get().getDataTable();
          grid.setSelectionEnabled(!grid.isSelectionEnabled());
          refreshPolicy();
        }
      });
      form.addButton(button);
    }

    // Refresh policy and return
    refreshPolicy();
    return form;
  }

  /**
   * Refresh the policy.
   */
  private void refreshPolicy() {
    SelectionGrid grid = ScrollTableDemo.get().getDataTable();

    // Refresh the status
    if (grid.isSelectionEnabled()) {
      statusLabel.setText("enabled");
    } else {
      statusLabel.setText("disabled");
    }

    // Refresh the policy
    SelectionPolicy policy = grid.getSelectionPolicy();
    if (policy == SelectionPolicy.MULTI_ROW) {
      policyBox.setSelectedIndex(0);
    } else if (policy == SelectionPolicy.ONE_ROW) {
      policyBox.setSelectedIndex(1);
    } else if (policy == SelectionPolicy.CHECKBOX) {
      policyBox.setSelectedIndex(2);
    } else if (policy == SelectionPolicy.RADIO) {
      policyBox.setSelectedIndex(3);
    }
  }
}
