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
package com.google.gwt.gen2.demo.scrolltable.client.option;

import com.google.gwt.gen2.table.override.client.FlexTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;

/**
 * A convenience class use to build a standard 2 column form.
 */
public class CustomForm extends Composite {
  /**
   * The style name applied to cells that contain labels.
   */
  protected static final String STYLENAME_LABEL = "AbstractOption-Label";

  /**
   * The panel that wraps the contents.
   */
  private FlexTable table = new FlexTable();

  /**
   * Construct a new {@link AbstractOption}.
   */
  public CustomForm() {
    initWidget(table);
  }

  /**
   * Add a {@link Button} to the form.
   * 
   * @param button the button to add
   */
  public void addButton(Button button) {
    int rowIndex = table.getRowCount();
    table.setWidget(rowIndex, 0, button);
    table.getFlexCellFormatter().setColSpan(rowIndex, 0, 2);
    table.getCellFormatter().setHorizontalAlignment(rowIndex, 0,
        HasHorizontalAlignment.ALIGN_CENTER);
  }

  /**
   * Add a label and a widget on a single row.
   * 
   * @param text the text of the label
   * @param widget the widget to add
   */
  public void addLabeledWidget(String text, Widget widget) {
    int rowIndex = table.getRowCount();
    table.setText(rowIndex, 0, text);
    table.getCellFormatter().setStyleName(rowIndex, 0, STYLENAME_LABEL);
    table.setWidget(rowIndex, 1, widget);
  }

  /**
   * Add a title to the form.
   * 
   * @param title the text of the title
   */
  public void addTitle(String title) {
    int rowIndex = table.getRowCount();
    table.setText(rowIndex, 0, title);
    table.getFlexCellFormatter().setColSpan(rowIndex, 0, 2);
    table.getCellFormatter().setHorizontalAlignment(rowIndex, 0,
        HasHorizontalAlignment.ALIGN_CENTER);
  }
}
