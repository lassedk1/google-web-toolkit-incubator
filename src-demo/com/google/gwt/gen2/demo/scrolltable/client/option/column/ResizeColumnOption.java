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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option.
 */
public class ResizeColumnOption extends AbstractOption {

  @Override
  protected String getDescription() {
    return "Manually set the absolute size of a column.";
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Column selection
    final TextBox columnBox = new TextBox();
    columnBox.setText("3");
    columnBox.setWidth("50px");
    form.addLabeledWidget("Column Index:", columnBox);

    // Width selection
    final TextBox widthBox = new TextBox();
    widthBox.setText("25");
    widthBox.setWidth("50px");
    form.addLabeledWidget("Width (pixels):", widthBox);

    // Add button to set column size
    {
      Button button = new Button("Set Column Size", new ClickListener() {
        public void onClick(Widget sender) {
          try {
            int column = Integer.parseInt(columnBox.getText());
            int width = Integer.parseInt(widthBox.getText());
            if (column >= 0) {
              ScrollTableDemo.get().getScrollTable().setColumnWidth(column,
                  width);
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

    // Add button to set guaranteed column size
    {
      Button button = new Button("Set Guarenteed Column Size",
          new ClickListener() {
            public void onClick(Widget sender) {
              try {
                int column = Integer.parseInt(columnBox.getText());
                int width = Integer.parseInt(widthBox.getText());
                if (column >= 0) {
                  ScrollTableDemo.get().getScrollTable().setGuaranteedColumnWidth(
                      column, width);
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
