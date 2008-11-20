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
package com.google.gwt.demos.spinner.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.ValueSpinner;
import com.google.gwt.widgetideas.client.VolumeSlider;
import com.google.gwt.widgetideas.datepicker.client.DateTimePicker;
import com.google.gwt.widgetideas.datepicker.client.TimePicker;

import java.util.Date;

/**
 * Spinner demo panel.
 */
public class SpinnerDemoPanel extends Composite {
  public SpinnerDemoPanel() {
    FlexTable table = new FlexTable();
    table.setHTML(1, 0, "Value spinner:");
    // Create a value spinner with initial value=0 that allows values between
    // -1000 and 1000
    final ValueSpinner simpleSpinner = new ValueSpinner(0, -1000, 1000);
    table.setWidget(1, 1, simpleSpinner);
    final CheckBox enableSpinner = new CheckBox("Enable/disable");
    enableSpinner.setChecked(simpleSpinner.isEnabled());
    enableSpinner.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        simpleSpinner.setEnabled(enableSpinner.isChecked());
      }
    });
    table.setWidget(1, 2, enableSpinner);

    // Create a value spinner that displays the value as a formatted date
    final ValueSpinner dateSpinner = new ValueSpinner(new Date().getTime()) {
      protected String formatValue(long value) {
        return DateTimeFormat.getLongDateFormat().format(new Date(value));
      }

      protected long parseValue(String value) {
        return DateTimeFormat.getLongDateFormat().parse(value).getTime();
      }
    };
    // Min step = milliseconds per day
    dateSpinner.getSpinner().setMinStep(86400000);
    dateSpinner.setStylePrimaryName("dateSpinner");
    table.setHTML(2, 0, "Date spinner:");
    table.setWidget(2, 1, dateSpinner);
    final CheckBox enableDateSpinner = new CheckBox("Enable/disable");
    enableDateSpinner.setChecked(dateSpinner.isEnabled());
    enableDateSpinner.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        dateSpinner.setEnabled(enableDateSpinner.isChecked());
      }
    });
    table.setWidget(2, 2, enableDateSpinner);

    // A TimePicker using AM/PM 12h format
    final TimePicker timePicker = new TimePicker(false);
    table.setHTML(3, 0, "Time picker:");
    table.setWidget(3, 1, timePicker);
    final CheckBox enableTimePicker = new CheckBox("Enable/disable");
    enableTimePicker.setChecked(timePicker.isEnabled());
    enableTimePicker.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        timePicker.setEnabled(enableTimePicker.isChecked());
      }
    });
    table.setWidget(3, 2, enableTimePicker);

    // Creating a date time picker with AM/PM 12h TimePicker
    final DateTimePicker dateTimePicker = new DateTimePicker(false);
    dateTimePicker.setWidth("200px");
    // Create a label to display the selected date
    final Label dateLabel = new Label();
    Button getDate = new Button("Show date");
    getDate.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        // Display the selected date/time using DateTimeFormat
        dateLabel.setText(DateTimeFormat.getMediumDateTimeFormat().format(
            dateTimePicker.getDate()));
      }
    });
    dateLabel.setStyleName("sandbox-Date");
    table.setHTML(4, 0, "Date time picker:");
    table.setWidget(4, 1, dateTimePicker);
    table.setWidget(5, 0, getDate);
    table.setWidget(5, 1, dateLabel);
    final CheckBox enableDateTimePicker = new CheckBox("Enable/disable");
    enableDateTimePicker.setChecked(dateTimePicker.isEnabled());
    enableDateTimePicker.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        dateTimePicker.setEnabled(enableDateTimePicker.isChecked());
      }
    });
    table.setWidget(4, 2, enableDateTimePicker);

    final VolumeSlider volumeSlider = new VolumeSlider(0, -100, 100);
    volumeSlider.setWidth("250px");
    table.setHTML(7, 0, "Volume slider:");
    table.setWidget(7, 1, volumeSlider);
    final CheckBox enableVolumeSlider = new CheckBox("Enable/disable");
    enableVolumeSlider.setChecked(volumeSlider.isEnabled());
    enableVolumeSlider.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        volumeSlider.setEnabled(enableVolumeSlider.isChecked());
      }
    });
    table.setWidget(7, 2, enableVolumeSlider);

    final TimePicker customTimePicker = new TimePicker(new Date(), null,
        DateTimeFormat.getFormat("HH"), DateTimeFormat.getFormat("mm"), null);
    table.setHTML(8, 0, "Another time picker (24h):");
    table.setWidget(8, 1, customTimePicker);
    final CheckBox enable = new CheckBox("Enable/disable");
    enable.setChecked(customTimePicker.isEnabled());
    enable.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        customTimePicker.setEnabled(enable.isChecked());
      }
    });
    table.setWidget(8, 2, enable);
    initWidget(table);
  }
}
