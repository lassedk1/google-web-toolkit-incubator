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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.demo.scrolltable.client.ScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.CustomForm;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option.
 */
public class SwapRowsOption extends AbstractOption {

  @Override
  protected String getDescription() {
    return "Swap rows";
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Row1 selection
    final TextBox rowBox1 = new TextBox();
    rowBox1.setText("3");
    rowBox1.setWidth("50px");
    form.addLabeledWidget("Row 1 Index:", rowBox1);

    // Row2 selection
    final TextBox rowBox2 = new TextBox();
    rowBox2.setText("6");
    rowBox2.setWidth("50px");
    form.addLabeledWidget("Row 2 Index:", rowBox2);

    // Add button to swap rows
    {
      Button button = new Button("Swap Rows", new ClickHandler() {
        public void onClick(ClickEvent event) {
          try {
            int row1 = Integer.parseInt(rowBox1.getText());
            int row2 = Integer.parseInt(rowBox2.getText());
            if (row1 >= 0 && row2 >= 0) {
              ScrollTableDemo.get().getDataTable().swapRows(row1, row2);
            }
          } catch (NumberFormatException e) {
            Window.alert("Please enter valid integers for the row and column.");
          } catch (IndexOutOfBoundsException e) {
            Window.alert("The row or column index you entered is out of bounds.");
          }
        }
      });
      form.addButton(button);
    }

    // Add button to reverse all rows
    {
      Button button = new Button("Reverse All Rows", new ClickHandler() {
        public void onClick(ClickEvent event) {
          ScrollTableDemo.get().getDataTable().reverseRows();
        }
      });
      form.addButton(button);
    }

    return form;
  }
}
