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
package com.google.gwt.widgetideas.table.client.filter;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * ColumnTextFilter filters columns based on text 
 */
public class ColumnTextFilter extends ColumnFilter {
  private int operator = ColumnTextFilterInfo.STARTS_WITH;
  private String[] operatorButtonText = new String[] {
      "xx..", "Xx..", "..xx..", "..Xx..", "..xx", "..Xx"};

  private TextBox filterTextBox;
  private PushButton operatorButton;
  private ClickListener clickListener = new ClickListener() {
    public void onClick(Widget sender) {
      operator++;
      if (operator == ColumnTextFilterInfo.COUNT_OPERATORS) {
        operator = 0;
      }
      setButtonText(((PushButton) operatorButton),operator);
      fireColumnFilterChanged(new ColumnTextFilterInfo(getColumn(),
          filterTextBox.getText(), operator));
    }
  };

  /**
   * Creates a filter suitable for filtering columns containing text
   * @param column the column to be filtered
   */
  public ColumnTextFilter(int column) {
    super(column);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.widgetideas.table.client.filter.ColumnFilter#createFilterWidget()
   */
  public Widget createFilterWidget() {
    filterTextBox = new TextBox();
    filterTextBox.setWidth("100%");
    filterTextBox.addStyleName("textBox");
    operatorButton = new PushButton();
    setButtonText(operatorButton, operator);
    operatorButton.addClickListener(clickListener);
    operatorButton.addStyleName("operatorButton");
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    horizontalPanel.addStyleName("columnTextFilter");
    horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
    horizontalPanel.add(operatorButton);
    horizontalPanel.add(filterTextBox);
    horizontalPanel.setCellWidth(operatorButton, "30px");
    horizontalPanel.setCellWidth(filterTextBox, "100%");
    horizontalPanel.setSpacing(2);
    filterTextBox.addKeyboardListener(new KeyboardListener() {
      public void onKeyDown(Widget sender, char keyCode, int modifiers) {
      }

      public void onKeyPress(Widget sender, char keyCode, int modifiers) {
      }

      public void onKeyUp(Widget sender, char keyCode, int modifiers) {
        fireColumnFilterChanged(new ColumnTextFilterInfo(getColumn(),
            filterTextBox.getText(), operator));
      }
    });
    return horizontalPanel;
  }

  private void setButtonText(PushButton pushButton, int operator) {
    pushButton.getUpFace().setText(operatorButtonText[operator]);
    pushButton.getUpHoveringFace().setText(operatorButtonText[operator]);
    pushButton.getDownFace().setText(operatorButtonText[operator]);
  }
}