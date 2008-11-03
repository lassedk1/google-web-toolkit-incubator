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
public class CellPaddingAndSpacingOption extends AbstractOption {

  @Override
  protected String getDescription() {
    return "Set the padding within cells and spacing between cells";
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Padding selection
    final TextBox paddingBox = new TextBox();
    paddingBox.setText("3");
    paddingBox.setWidth("50px");
    form.addLabeledWidget("Cell Padding:", paddingBox);

    // Spacing selection
    final TextBox spacingBox = new TextBox();
    spacingBox.setText("3");
    spacingBox.setWidth("50px");
    form.addLabeledWidget("Cell Spacing:", spacingBox);

    // Add button to set padding and spacing
    {
      Button button = new Button("Set Padding and Spacing",
          new ClickListener() {
            public void onClick(Widget sender) {
              try {
                int padding = Integer.parseInt(paddingBox.getText());
                int spacing = Integer.parseInt(spacingBox.getText());
                ScrollTableDemo.get().getScrollTable().setCellPadding(padding);
                ScrollTableDemo.get().getScrollTable().setCellSpacing(spacing);
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
