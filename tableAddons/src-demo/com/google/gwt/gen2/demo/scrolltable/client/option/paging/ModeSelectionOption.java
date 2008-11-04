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

import com.google.gwt.gen2.demo.scrolltable.client.PagingScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.table.override.client.FlexTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option.
 */
public class ModeSelectionOption extends AbstractOption {
  /**
   * The main layout widget.
   */
  private FlexTable layout = new FlexTable();

  @Override
  protected String getDescription() {
    return "The following modes are useful for debugging";
  }

  @Override
  protected Widget onInitialize() {
    layout.setBorderWidth(2);

    // Labels
    {
      int rowCount = layout.getRowCount();
      layout.setHTML(rowCount, 0, "<b>Mode</b>");
      layout.setHTML(rowCount, 1, "<b>Status</b>");
      layout.setHTML(rowCount, 2, "<b>Description</b>");
    }

    // Error Mode
    {
      final int rowCount = layout.getRowCount();
      Button button = new Button("Toggle Error Mode", new ClickListener() {
        public void onClick(Widget sender) {
          if (PagingScrollTableDemo.get().getTableModel().isErrorModeEnabled()) {
            PagingScrollTableDemo.get().getTableModel().setErrorModeEnabled(
                false);
            layout.setText(rowCount, 1, "disabled");
          } else {
            PagingScrollTableDemo.get().getTableModel().setErrorModeEnabled(
                true);
            layout.setText(rowCount, 1, "enabled");
          }
        }
      });
      layout.setWidget(rowCount, 0, button);
      layout.setText(rowCount, 1, "disabled");
      layout.setText(rowCount, 2, "If the table model throws an error during "
          + "a paging request, the ScrollTable will display the error "
          + "gracefully.");
    }

    // RPC Mode
    {
      final int rowCount = layout.getRowCount();
      Button button = new Button("Toggle RPC Mode", new ClickListener() {
        public void onClick(Widget sender) {
          if (PagingScrollTableDemo.get().getTableModel().isRPCModeEnabled()) {
            PagingScrollTableDemo.get().getTableModel().setRPCModeEnabled(false);
            layout.setText(rowCount, 1, "disabled");
          } else {
            PagingScrollTableDemo.get().getTableModel().setRPCModeEnabled(true);
            layout.setText(rowCount, 1, "enabled");
          }
        }
      });
      layout.setWidget(rowCount, 0, button);
      layout.setText(rowCount, 1, "disabled");
      layout.setText(rowCount, 2, "Retrieve data from a server using an RPC "
          + "request instead of generating data locally. "
          + "This requests an RPC server.");
    }

    // Zero Mode
    {
      final int rowCount = layout.getRowCount();
      Button button = new Button("Toggle Zero Mode", new ClickListener() {
        public void onClick(Widget sender) {
          if (PagingScrollTableDemo.get().getTableModel().isZeroModeEnabled()) {
            PagingScrollTableDemo.get().getTableModel().setZeroModeEnabled(
                false);
            layout.setText(rowCount, 1, "disabled");
          } else {
            PagingScrollTableDemo.get().getTableModel().setZeroModeEnabled(true);
            layout.setText(rowCount, 1, "enabled");
          }
        }
      });
      layout.setWidget(rowCount, 0, button);
      layout.setText(rowCount, 1, "disabled");
      layout.setText(rowCount, 2, "Enable to return zero results for each "
          + "request, resulting in a special widget being display.");
    }

    // Add the description
    return layout;
  }
}
