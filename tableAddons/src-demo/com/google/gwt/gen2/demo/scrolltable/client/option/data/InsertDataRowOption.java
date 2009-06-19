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
import com.google.gwt.gen2.demo.scrolltable.client.PagingScrollTableDemo;
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
public class InsertDataRowOption extends AbstractOption {
  @Override
  protected String getDescription() {
    return "Insert a row at a specified index";
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Row selection
    final TextBox rowBox = new TextBox();
    rowBox.setText("3");
    rowBox.setWidth("50px");
    form.addLabeledWidget("Row Index:", rowBox);

    // Add button to insert one row
    {
      Button button = new Button("Insert 1 Row", new ClickHandler() {
        public void onClick(ClickEvent event) {
          try {
            int row = Integer.parseInt(rowBox.getText());
            insertDataRows(row, 1);
          } catch (NumberFormatException e) {
            Window.alert("Please enter valid integers for the row and column.");
          } catch (IndexOutOfBoundsException e) {
            Window.alert("The row or column index you entered is out of bounds.");
          }
        }
      });
      form.addButton(button);
    }

    // Add button to insert 10 rows
    {
      Button button = new Button("Insert 10 Rows", new ClickHandler() {
        public void onClick(ClickEvent event) {
          try {
            int row = Integer.parseInt(rowBox.getText());
            insertDataRows(row, 10);
          } catch (NumberFormatException e) {
            Window.alert("Please enter valid integers for the row and column.");
          } catch (IndexOutOfBoundsException e) {
            Window.alert("The row or column index you entered is out of bounds.");
          }
        }
      });
      form.addButton(button);
    }

    // Add button to insert 100 rows
    {
      Button button = new Button("Insert 100 Rows", new ClickHandler() {
        public void onClick(ClickEvent event) {
          try {
            int row = Integer.parseInt(rowBox.getText());
            insertDataRows(row, 100);
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
            ScrollTableDemo.get().getDataTable().removeRow(row);
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

  /**
   * Insert rows at the specified index.
   * 
   * @param beforeRow the row index
   * @param numRows the number of rows to insert
   */
  private void insertDataRows(int beforeRow, int numRows) {
    boolean paging = PagingScrollTableDemo.get() != null;
    for (int i = beforeRow; i < beforeRow + numRows; i++) {
      if (paging) {
        PagingScrollTableDemo.get().insertDataRow(i);
      } else {
        ScrollTableDemo.get().insertDataRow(i);
      }
    }
  }
}
