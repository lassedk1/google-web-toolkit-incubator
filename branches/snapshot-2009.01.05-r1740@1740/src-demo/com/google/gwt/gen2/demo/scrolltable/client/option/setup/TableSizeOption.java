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
public class TableSizeOption extends AbstractOption {
  @Override
  protected String getDescription() {
    return "Set the overall size of the table";
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Height selection
    final TextBox heightBox = new TextBox();
    heightBox.setText("400px");
    heightBox.setWidth("50px");
    form.addLabeledWidget("Height:", heightBox);

    // Width selection
    final TextBox widthBox = new TextBox();
    widthBox.setText("95%");
    widthBox.setWidth("50px");
    form.addLabeledWidget("Width:", widthBox);

    // Add button to change status
    {
      Button button = new Button("Set Table Size", new ClickHandler() {
        public void onClick(ClickEvent event) {
          String height = heightBox.getText();
          String width = widthBox.getText();
          ScrollTableDemo.get().getScrollTable().setSize(width, height);
        }
      });
      form.addButton(button);
    }

    // Add button to get minimum offset width
    {
      Button button = new Button("Get Minimum Offset Width",
          new ClickHandler() {
            public void onClick(ClickEvent event) {
              int width = ScrollTableDemo.get().getScrollTable().getMinimumOffsetWidth();
              Window.alert("Minimum Offset Width: " + width);
            }
          });
      form.addButton(button);
    }

    return form;
  }
}
