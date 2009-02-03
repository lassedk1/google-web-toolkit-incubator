/*
 * Copyright 2009 Google Inc.
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
package com.google.gwt.gen2.demo.picker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.gen2.logging.shared.Log;
import com.google.gwt.gen2.picker.client.DropDownDatePicker;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Date Picker with Drop-down month selector demo.
 */
public class DropDownDatePickerDemo implements EntryPoint {
  /**
   * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
   */
  @SuppressWarnings/* TODO pending resolution of GWT issue #3233 */("deprecation")
  public void onModuleLoad() {
    DropDownDatePicker.injectDefaultCss();
    final DropDownDatePicker datePicker = new DropDownDatePicker();
    final TextBox startYearField = new TextBox();
    final TextBox yearsToDisplayField = new TextBox();

    startYearField.setText("1950");
    yearsToDisplayField.setText("100");

    startYearField.addChangeListener(new ChangeListener() {
      public void onChange(Widget sender) {
        // TODO pending resolution of GWT issue #3233
        // startYearField.addValueChangeHandler(new ValueChangeHandler<String>()
        // {
        // public void onValueChange(ValueChangeEvent<String> event) {
        try {
          // datePicker.setStartYear(Integer.parseInt(event.getValue()));
          datePicker.setStartYear(Integer.parseInt(startYearField.getValue()));
        } catch (NumberFormatException e) {
         
          Log.warning("Invalid number:" + startYearField.getValue());
        }
      }
    });

    yearsToDisplayField.addChangeListener(new ChangeListener() {
      public void onChange(Widget sender) {
        // TODO pending resolution of GWT issue #3233
        // yearsToDisplayField.addValueChangeHandler(new
        // ValueChangeHandler<String>() {
        // public void onValueChange(ValueChangeEvent<String> event) {
        try {
          // datePicker.setYearsToDisplay(Integer.parseInt(event.getValue()));
          datePicker.setYearsToDisplay(Integer.parseInt(yearsToDisplayField.getValue()));
        } catch (NumberFormatException e) {
          // System.out.println("Invalid number:" + event.getValue());
          System.out.println("Invalid number:" + yearsToDisplayField.getValue());
        }
      }
    });

    RootPanel.get().add(new Label("First year in list:"));
    RootPanel.get().add(startYearField);
    RootPanel.get().add(new Label("Number of years in list:"));
    RootPanel.get().add(yearsToDisplayField);
    RootPanel.get().add(datePicker);
  }
}
