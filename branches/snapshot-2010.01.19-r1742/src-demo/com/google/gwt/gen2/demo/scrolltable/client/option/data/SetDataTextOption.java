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
package com.google.gwt.gen2.demo.scrolltable.client.option.data;

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
public class SetDataTextOption extends AbstractOption {
  @Override
  protected String getDescription() {
    return "Set the contents of a cell";
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Row selection
    final TextBox rowBox = new TextBox();
    rowBox.setText("3");
    rowBox.setWidth("50px");
    form.addLabeledWidget("Row Index", rowBox);

    // Column selection
    final TextBox columnBox = new TextBox();
    columnBox.setText("4");
    columnBox.setWidth("50px");
    form.addLabeledWidget("Column Index", columnBox);

    // Text selection
    final TextBox textBox = new TextBox();
    textBox.setText("<b>Hello World</b>");
    form.addLabeledWidget("Text:", textBox);

    // Add button to set text
    {
      Button button = new Button("Set Cell Text", new ClickHandler() {
        public void onClick(ClickEvent event) {
          try {
            int row = Integer.parseInt(rowBox.getText());
            int column = Integer.parseInt(columnBox.getText());
            String text = textBox.getText();
            ScrollTableDemo.get().getDataTable().setText(row, column, text);
          } catch (NumberFormatException e) {
            Window.alert("Please enter valid integers for the row and column.");
          } catch (IndexOutOfBoundsException e) {
            Window.alert("The row or column index you entered is out of bounds.");
          }
        }
      });
      form.addButton(button);
    }

    // Add button to set html
    {
      Button button = new Button("Set Cell HTML", new ClickHandler() {
        public void onClick(ClickEvent event) {
          try {
            int row = Integer.parseInt(rowBox.getText());
            int column = Integer.parseInt(columnBox.getText());
            String text = textBox.getText();
            ScrollTableDemo.get().getDataTable().setHTML(row, column, text);
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
