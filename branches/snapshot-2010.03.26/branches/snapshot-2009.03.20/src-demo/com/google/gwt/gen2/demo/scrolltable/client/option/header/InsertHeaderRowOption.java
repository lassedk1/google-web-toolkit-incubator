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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.demo.scrolltable.client.ScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.CustomForm;
import com.google.gwt.gen2.table.override.client.FlexTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option.
 */
public class InsertHeaderRowOption extends AbstractOption {
  @Override
  protected String getDescription() {
    return "Insert a row at a specified index";
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Row selection
    final TextBox rowBox = new TextBox();
    rowBox.setText("1");
    rowBox.setWidth("50px");
    form.addLabeledWidget("Row Index:", rowBox);

    // Add button to insert one row
    {
      Button button = new Button("Insert 1 Row", new ClickHandler() {
        public void onClick(ClickEvent event) {
          try {
            int row = Integer.parseInt(rowBox.getText());
            FlexTable headerTable = ScrollTableDemo.get().getHeaderTable();
            headerTable.insertRow(row);
            headerTable.setHTML(row, 0, "&nbsp;");
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

    // Add button to remove a row
    {
      Button button = new Button("Remove Row", new ClickHandler() {
        public void onClick(ClickEvent event) {
          try {
            int row = Integer.parseInt(rowBox.getText());
            ScrollTableDemo.get().getHeaderTable().removeRow(row);
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

    // Cell selection
    final TextBox cellBox = new TextBox();
    cellBox.setText("0");
    cellBox.setWidth("50px");
    form.addLabeledWidget("Cell Index", cellBox);

    // Add button to insert one cell
    {
      Button button = new Button("Insert 1 Cell", new ClickHandler() {
        public void onClick(ClickEvent event) {
          try {
            int row = Integer.parseInt(rowBox.getText());
            int cell = Integer.parseInt(cellBox.getText());
            FlexTable headerTable = ScrollTableDemo.get().getHeaderTable();
            headerTable.insertCell(row, cell);
            headerTable.setHTML(row, 0, "&nbsp;");
          } catch (NumberFormatException e) {
            Window.alert("Please enter valid integers for the row and column.");
          } catch (IndexOutOfBoundsException e) {
            Window.alert("The row or column index you entered is out of bounds.");
          }
        }
      });
      form.addButton(button);
    }

    // Add button to remove a cell
    {
      Button button = new Button("Remove Cell", new ClickHandler() {
        public void onClick(ClickEvent event) {
          try {
            int row = Integer.parseInt(rowBox.getText());
            int cell = Integer.parseInt(cellBox.getText());
            ScrollTableDemo.get().getHeaderTable().removeCell(row, cell);
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
