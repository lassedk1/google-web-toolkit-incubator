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
package com.google.gwt.gen2.demo.scrolltable.client.option.header;

import com.google.gwt.gen2.demo.scrolltable.client.ScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.CustomForm;
import com.google.gwt.gen2.table.override.client.FlexTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option.
 */
public class ColSpanOption extends AbstractOption {
  @Override
  protected String getDescription() {
    return "Set the colspan of a cell";
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Row selection
    final TextBox rowBox = new TextBox();
    rowBox.setText("1");
    rowBox.setWidth("50px");
    form.addLabeledWidget("Row Index:", rowBox);

    // Cell selection
    final TextBox cellBox = new TextBox();
    cellBox.setText("0");
    cellBox.setWidth("50px");
    form.addLabeledWidget("Cell Index:", cellBox);

    // Colspan selection
    final TextBox colSpanBox = new TextBox();
    colSpanBox.setText("1");
    colSpanBox.setWidth("50px");
    form.addLabeledWidget("ColSpan:", colSpanBox);

    // Rowspan selection
    final TextBox rowSpanBox = new TextBox();
    rowSpanBox.setText("1");
    rowSpanBox.setWidth("50px");
    form.addLabeledWidget("RowSpan:", rowSpanBox);

    // Add button to set the spans
    {
      Button button = new Button("Set row/colspan", new ClickListener() {
        public void onClick(Widget sender) {
          try {
            int row = Integer.parseInt(rowBox.getText());
            int cell = Integer.parseInt(cellBox.getText());
            int rowSpan = Integer.parseInt(rowSpanBox.getText());
            int colSpan = Integer.parseInt(colSpanBox.getText());
            FlexTable headerTable = ScrollTableDemo.get().getHeaderTable();
            headerTable.getFlexCellFormatter().setRowSpan(row, cell, rowSpan);
            headerTable.getFlexCellFormatter().setColSpan(row, cell, colSpan);
            ScrollTableDemo.get().getScrollTable().redraw();
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
