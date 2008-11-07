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

import com.google.gwt.gen2.datepicker.client.DateBox;
import com.google.gwt.gen2.event.dom.client.KeyDownEvent;
import com.google.gwt.gen2.event.dom.client.KeyDownHandler;
import com.google.gwt.gen2.event.logical.shared.SelectionEvent;
import com.google.gwt.gen2.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

import java.util.Date;

/**
 * ColumnTextFilter filters columns based on dates
 */
public class ColumnDateFilter extends ColumnFilter {
  private int operator = ColumnTextFilterInfo.STARTS_WITH;
  private String[] operatorButtonText = new String[] {
      "=", "!=", "<", ">", ">-<"};

  private DateBox primaryDateBox, secondaryDateBox;
  private PushButton operatorButton;
  private HorizontalPanel horizontalPanel = new HorizontalPanel();
  private String datePattern;

  private ClickListener clickListener = new ClickListener() {
    public void onClick(Widget sender) {
      operator++;
      if (operator == ColumnNumberFilterInfo.COUNT_OPERATORS) {
        operator = 0;
      }
      if (operator == ColumnNumberFilterInfo.BETWEEN) {
        secondaryDateBox.setVisible(true);
        horizontalPanel.setCellWidth(primaryDateBox, "50%");
        horizontalPanel.setCellWidth(secondaryDateBox, "50%");
      } else {
        horizontalPanel.setCellWidth(primaryDateBox, "100%");
        secondaryDateBox.setVisible(false);
      }
      setButtonText(((PushButton) operatorButton), operator);
      Date primaryDate = primaryDateBox.getDatePicker().getDateShown();
      Date secondaryDate = secondaryDateBox.getDatePicker().getDateShown();
      fireColumnFilterChangedEvent(primaryDate, secondaryDate);
    }
  };
  private SelectionHandler<Date> selectionHandler = new SelectionHandler<Date>(){
    public void onSelection(SelectionEvent<Date> event) {
      fireColumnFilterChangedEvent();
    }
  };
  private KeyDownHandler keyDownHandler = new KeyDownHandler() {
    public void onKeyDown(KeyDownEvent event) {
      fireColumnFilterChangedEvent();
    }
  };
  
  /**
   * Creates a filter suitable for filtering columns containing dates
   * @param column the column to be filtered
   * @param datePattern the pattern used for the formatting of the cells is this column 
   */
  public ColumnDateFilter(String datePattern) {
    super();
    this.datePattern = datePattern;
  }

  /* (non-Javadoc)
   * @see com.google.gwt.widgetideas.table.client.filter.ColumnFilter#createFilterWidget()
   */
  public Widget createFilterWidget() {
    primaryDateBox = new DateBox();
    primaryDateBox.setWidth("100%");
    primaryDateBox.addStyleName("dateBox");
    secondaryDateBox = new DateBox();
    secondaryDateBox.setWidth("100%");
    secondaryDateBox.addStyleName("dateBox");
    operatorButton = new PushButton();
    setButtonText(operatorButton, operator);
    operatorButton.addClickListener(clickListener);
    operatorButton.addStyleName("operatorButton");
    horizontalPanel.addStyleName("columnDateFilter");
    horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
    horizontalPanel.add(operatorButton);
    horizontalPanel.add(primaryDateBox);
    horizontalPanel.add(secondaryDateBox);
    horizontalPanel.setCellWidth(operatorButton, "30px");
    horizontalPanel.setCellWidth(primaryDateBox, "100%");
    secondaryDateBox.setVisible(false);
    horizontalPanel.setSpacing(2);
    primaryDateBox.getDatePicker().addSelectionHandler(selectionHandler);
    secondaryDateBox.getDatePicker().addSelectionHandler(selectionHandler);
    primaryDateBox.addKeyDownHandler(keyDownHandler);
    secondaryDateBox.addKeyDownHandler(keyDownHandler);
    return horizontalPanel;
  }

  private void fireColumnFilterChangedEvent() {
    Date primaryDate = primaryDateBox.getDatePicker().getDateShown();
    Date secondaryDate = secondaryDateBox.getDatePicker().getDateShown();
    if ( primaryDateBox.getText().trim().equals("") ) {
      primaryDate = null;
    }
    if ( secondaryDateBox.getText().trim().equals("") ) {
      secondaryDate = null;
    }
    fireColumnFilterChangedEvent(primaryDate, secondaryDate);
  }
  
  private void fireColumnFilterChangedEvent(Date primaryDate, Date secondaryDate) {
    fireColumnFilterChanged(new ColumnDateFilterInfo(getColumn(), datePattern, primaryDate, secondaryDate, operator));
  }

  private void setButtonText(PushButton pushButton, int operator) {
    pushButton.getUpFace().setText(operatorButtonText[operator]);
    pushButton.getUpHoveringFace().setText(operatorButtonText[operator]);
    pushButton.getDownFace().setText(operatorButtonText[operator]);
  }
}