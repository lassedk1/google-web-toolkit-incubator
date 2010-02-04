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
package com.google.gwt.gen2.demo.scrolltable.client.option.setup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.demo.scrolltable.client.ScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.CustomForm;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.ResizableWidgetCollection;

/**
 * An option.
 */
public class ResizeCheckingOption extends AbstractOption {
  private static final String DESC_RESIZE_CHECKING = "Enable resize checking "
      + "to start a Timer that compares the offset width and height of the "
      + "scroll table at periodic intervals.  If the width or height change "
      + "for an undetectable reason (such as changing the width style "
      + "attribute of the widget), the ResizableWidgetCollection class will "
      + "fire the onResize() method in the ScrollTable, which implements "
      + "ResizableWidget.";

  /**
   * The label that displays the status.
   */
  private Label statusLabel = null;

  @Override
  protected String getDescription() {
    return DESC_RESIZE_CHECKING;
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Add the current status
    statusLabel = new Label();
    form.addLabeledWidget("Current Status:", statusLabel);
    refreshStatus();

    // Add button to change status
    {
      Button button = new Button("Toggle Resize Checking", new ClickHandler() {
        public void onClick(ClickEvent event) {
          ResizableWidgetCollection.get().setResizeCheckingEnabled(
              !ResizableWidgetCollection.get().isResizeCheckingEnabled());
          refreshStatus();
        }
      });
      form.addButton(button);
    }

    // Add a button to resize the table now
    {
      Button button = new Button("Redraw Now", new ClickHandler() {
        public void onClick(ClickEvent event) {
          ScrollTableDemo.get().getScrollTable().redraw();
        }
      });
      form.addButton(button);
    }

    return form;
  }

  /**
   * Refresh the status.
   */
  private void refreshStatus() {
    if (ResizableWidgetCollection.get().isResizeCheckingEnabled()) {
      statusLabel.setText("enabled");
    } else {
      statusLabel.setText("disabled");
    }
  }
}
