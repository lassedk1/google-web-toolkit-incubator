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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.demo.scrolltable.client.PagingScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.StudentColumnDefinition;
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.CustomForm;
import com.google.gwt.gen2.demo.scrolltable.shared.Student;
import com.google.gwt.gen2.table.client.DefaultTableDefinition;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option.
 */
public class HideColumnOption extends AbstractOption {
  @Override
  protected String getDescription() {
    return "Hide or show columns";
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    final DefaultTableDefinition<Student> tableDef = PagingScrollTableDemo.get().getTableDefinition();
    int numColumns = tableDef.getColumnDefinitionCount();
    for (int i = 0; i < numColumns; i++) {
      final int rowIndex = i;
      final StudentColumnDefinition<?> colDef = (StudentColumnDefinition<?>) tableDef.getColumnDefinition(i);
      final Button button = new Button("Hide Column");
      button.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          if (tableDef.isColumnVisible(colDef)) {
            tableDef.setColumnVisible(colDef, false);
            button.setText("Show Column");
          } else {
            tableDef.setColumnVisible(colDef, true);
            button.setText("Hide Column");
          }
          PagingScrollTableDemo.get().getPagingScrollTable().reloadPage();
        }
      });
      form.addLabeledWidget(colDef.getName(), button);
    }

    // Add the description
    return form;
  }
}
