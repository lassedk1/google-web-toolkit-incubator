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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option.
 */
public class ShiftRowsOption extends AbstractOption {
  @Override
  protected String getDescription() {
    return "Shift rows up or down";
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Row selection
    final TextBox rowBox = new TextBox();
    rowBox.setText("3");
    rowBox.setWidth("50px");
    form.addLabeledWidget("Row Index:", rowBox);

    // Add button to shift row up
    {
      Button button = new Button("Shift Row Up", new ClickListener() {
        public void onClick(Widget sender) {
          try {
            int row = Integer.parseInt(rowBox.getText());
            if (row >= 0) {
              ScrollTableDemo.get().getDataTable().moveRowUp(row);
              rowBox.setText((row - 1) + "");
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

    // Add button to shift row down
    {
      Button button = new Button("Shift Row Down", new ClickListener() {
        public void onClick(Widget sender) {
          try {
            int row = Integer.parseInt(rowBox.getText());
            if (row >= 0) {
              ScrollTableDemo.get().getDataTable().moveRowDown(row);
              rowBox.setText((row + 1) + "");
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

    return form;
  }
}
