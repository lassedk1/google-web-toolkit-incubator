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
import com.google.gwt.gen2.demo.scrolltable.client.option.CustomForm;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option.
 */
public class PageSizeOption extends AbstractOption {
  @Override
  protected String getDescription() {
    return "Set the page size and total row count";
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Num rows selection
    final TextBox rowCountBox = new TextBox();
    rowCountBox.setText("1000");
    rowCountBox.setWidth("50px");
    form.addLabeledWidget("Total Row Count:", rowCountBox);

    // Add button to set the row count
    {
      Button button = new Button("Set Total Row Count", new ClickListener() {
        public void onClick(Widget sender) {
          try {
            int rowCount = Integer.parseInt(rowCountBox.getText());
            PagingScrollTableDemo.get().getCachedTableModel().setRowCount(
                rowCount);
          } catch (NumberFormatException e) {
            Window.alert("Please enter valid integers for the row and column.");
          }
        }
      });
      form.addButton(button);
    }

    // Page Size selection
    final TextBox pageSizeBox = new TextBox();
    pageSizeBox.setText("10");
    pageSizeBox.setWidth("50px");
    form.addLabeledWidget("Page Size", pageSizeBox);

    // Add button to insert one cell
    {
      Button button = new Button("Set Page Size", new ClickListener() {
        public void onClick(Widget sender) {
          try {
            int pageSize = Integer.parseInt(pageSizeBox.getText());
            PagingScrollTableDemo.get().getPagingScrollTable().setPageSize(
                pageSize);
          } catch (NumberFormatException e) {
            Window.alert("Please enter valid integers for the row and column.");
          }
        }
      });
      form.addButton(button);
    }

    return form;
  }
}
