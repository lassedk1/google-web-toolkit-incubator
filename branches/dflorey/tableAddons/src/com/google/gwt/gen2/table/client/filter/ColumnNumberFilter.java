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
package com.google.gwt.gen2.table.client.filter;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * ColumnTextFilter filters columns containing numbers
 */
public class ColumnNumberFilter extends ColumnFilter {
  private int operator = ColumnTextFilterInfo.STARTS_WITH;
  private String[] operatorButtonText = new String[] {
      "=", "!=", "<", ">", ">-<"};

  private TextBox primaryNumberBox, secondaryNumberBox;
  private PushButton operatorButton;
  private HorizontalPanel horizontalPanel = new HorizontalPanel();

  private ClickListener clickListener = new ClickListener() {
    public void onClick(Widget sender) {
      operator++;
      if (operator == ColumnNumberFilterInfo.COUNT_OPERATORS) {
        operator = 0;
      }
      if (operator == ColumnNumberFilterInfo.BETWEEN) {
        secondaryNumberBox.setVisible(true);
        horizontalPanel.setCellWidth(primaryNumberBox, "50%");
        horizontalPanel.setCellWidth(secondaryNumberBox, "50%");
      } else {
        horizontalPanel.setCellWidth(primaryNumberBox, "100%");
        secondaryNumberBox.setVisible(false);
      }
      setButtonText(((PushButton) operatorButton), operator);
      fireColumnFilterChangedEvent();
    }
  };
  private KeyboardListener keyboardListener = new KeyboardListener() {
    public void onKeyDown(Widget sender, char keyCode, int modifiers) {
    }

    public void onKeyPress(Widget sender, char keyCode, int modifiers) {
    }

    public void onKeyUp(Widget sender, char keyCode, int modifiers) {
      fireColumnFilterChangedEvent();
    }
  };

  /**
   * Creates a filter suitable for filtering columns containing numbers
   */
  public ColumnNumberFilter() {
    super();
  }

  public Widget createFilterWidget() {
    primaryNumberBox = new TextBox();
    primaryNumberBox.setWidth("100%");
    primaryNumberBox.addStyleName("numberBox");
    secondaryNumberBox = new TextBox();
    secondaryNumberBox.setWidth("100%");
    secondaryNumberBox.addStyleName("numberBox");
    operatorButton = new PushButton();
    setButtonText(operatorButton, operator);
    operatorButton.addClickListener(clickListener);
    operatorButton.addStyleName("operatorButton");
    horizontalPanel.addStyleName("columnNumberFilter");
    horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
    horizontalPanel.add(operatorButton);
    horizontalPanel.add(primaryNumberBox);
    horizontalPanel.add(secondaryNumberBox);
    horizontalPanel.setCellWidth(operatorButton, "30px");
    horizontalPanel.setCellWidth(primaryNumberBox, "100%");
    secondaryNumberBox.setVisible(false);
    horizontalPanel.setSpacing(2);
    primaryNumberBox.addKeyboardListener(keyboardListener);
    secondaryNumberBox.addKeyboardListener(keyboardListener);
    return horizontalPanel;
  }

  private void fireColumnFilterChangedEvent() {
    Float primaryNumber = null, secondaryNumber = null;
    if (primaryNumberBox.getText().length() > 0) {
      try {
        primaryNumber = Float.parseFloat(primaryNumberBox.getText());
        try {
          secondaryNumber = Float.parseFloat(secondaryNumberBox.getText());
        } catch (NumberFormatException e) {
        }
      } catch (NumberFormatException e) {
      }
    }
    fireColumnFilterChanged(new ColumnNumberFilterInfo(getColumn(),
        primaryNumber, secondaryNumber, operator));
  }

  private void setButtonText(PushButton pushButton, int operator) {
    pushButton.getUpFace().setText(operatorButtonText[operator]);
    pushButton.getUpHoveringFace().setText(operatorButtonText[operator]);
    pushButton.getDownFace().setText(operatorButtonText[operator]);
  }
}