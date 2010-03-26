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
package com.google.gwt.gen2.demo.scrolltable.client.option.paging;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.demo.scrolltable.client.PagingScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.CustomForm;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option.
 */
public class CrossPageSelectionOption extends AbstractOption {
  /**
   * The label that displays the status.
   */
  private Label statusLabel = null;

  @Override
  protected String getDescription() {
    return "Enable cross page selection to remember selected row values "
        + "across pages.";
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
      Button button = new Button("Toggle Cross Page Selection",
          new ClickHandler() {
            public void onClick(ClickEvent event) {
              boolean enabled = PagingScrollTableDemo.get().getPagingScrollTable().isCrossPageSelectionEnabled();
              PagingScrollTableDemo.get().getPagingScrollTable().setCrossPageSelectionEnabled(
                  !enabled);
              refreshStatus();
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
    if (PagingScrollTableDemo.get().getPagingScrollTable().isCrossPageSelectionEnabled()) {
      statusLabel.setText("enabled");
    } else {
      statusLabel.setText("disabled");
    }
  }
}
