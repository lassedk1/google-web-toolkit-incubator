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

package com.google.gwt.widgetideas.datepicker.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.DropDownPanel;
import com.google.gwt.widgetideas.client.events.ChangeEvent;
import com.google.gwt.widgetideas.client.events.ChangeHandler;

import java.util.Date;

/**
 * Most of the functionality needed to create a simple date box.
 */
public class DateBox extends Composite {
  private static class Styles {
    public static String DEFAULT = "gwt-DateBox";
  }

  private boolean dirtyText = false;
  private DropDownPanel popup = new DropDownPanel();
  private TextBox box = new TextBox();
  private DatePicker picker;
  private DateTimeFormat formatter = DateTimeFormat.getMediumDateFormat();

  /**
   * Constructor.
   */
  public DateBox() {
    this(new DatePicker());
  }

  /**
   * Constructor.
   * 
   * @param picker the picker to drop down
   */
  public DateBox(final DatePicker picker) {
    FlowPanel p = new FlowPanel();
    p.add(box);
    this.picker = picker;
    popup.setWidget(picker);
    initWidget(p);
    setStyleName(Styles.DEFAULT);
    picker.addChangeHandler(new ChangeHandler<Date>() {
      public void onChange(ChangeEvent<Date> event) {
        setText(event.getNewValue());
        hideDatePicker();
      }

    });
    box.addFocusListener(new FocusListener() {
      public void onFocus(Widget sender) {
        showDatePicker();
      }

      public void onLostFocus(Widget sender) {
        if (dirtyText) {
          updateDateFromTextBox();
        }
      }

    });

    box.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        showDatePicker();
      }
    });

    box.addKeyboardListener(new KeyboardListener() {

      public void onKeyDown(Widget sender, char keyCode, int modifiers) {
        dirtyText = true;
        switch (keyCode) {
          case KEY_ENTER:
          case KEY_TAB:
          case KEY_ESCAPE:
          case KEY_DOWN: {
            updateDateFromTextBox();
          }
        }
      }

      public void onKeyPress(Widget sender, char keyCode, int modifiers) {
      }

      public void onKeyUp(Widget sender, char keyCode, int modifiers) {
      }

    });
  }

  /**
   * Gets the date picker.
   * 
   * @return the date picker
   */
  public DatePicker getDatePicker() {
    return picker;
  }

  public void hideDatePicker() {
    popup.hide();
  }

  /**
   * Sets the date box's 'access key'. This key is used (in conjunction with a
   * browser-specific modifier key) to automatically focus the widget.
   * 
   * @param key the date box's access key
   */
  public void setAccessKey(char key) {
    box.setAccessKey(key);
  }

  /**
   * Explicitly focus/unfocus this widget. Only one widget can have focus at a
   * time, and the widget that does will receive all keyboard events.
   * 
   * @param focused whether this widget should take focus or release it
   */
  public void setFocus(boolean focused) {
    box.setFocus(focused);
  }

  /**
   * Sets the date box's position in the tab index. If more than one widget has
   * the same tab index, each such widget will receive focus in an arbitrary
   * order. Setting the tab index to <code>-1</code> will cause this widget to
   * be removed from the tab order.
   * 
   * @param index the date box's tab index
   */
  public void setTabIndex(int index) {
    box.setTabIndex(index);
  }

  /**
   * Show the given date in the date picker.
   * 
   * @param date picker
   */
  public void showDate(Date date) {
    picker.setSelectedDate(date, false);
    picker.showDate(date);
    setText(date);
    dirtyText = false;
  }

  public void showDatePicker() {
    Date current = null;

    String value = box.getText().trim();
    if (!value.equals("")) {
      try {
        current = formatter.parse(value);
      } catch (IllegalArgumentException e) {
        Log.info("cannot parse as date: " + value);
      }
    }

    if (current == null) {
      current = new Date();
    }
    picker.showDate(current);
    popup.showBelow(this);
  }

  /**
   * Gets the date box's position in the tab index.
   * 
   * @return the date box's tab index
   */
  int getTabIndex() {
    return box.getTabIndex();
  }

  private void setText(Date value) {
    box.setText(formatter.format(value));
    dirtyText = false;
  }

  private void updateDateFromTextBox() {
    try {
      String text = box.getText().trim();
      if (text.equals("")) {
        return;
      }
      Date d = formatter.parse(text);
      showDate(d);
    } catch (IllegalArgumentException exception) {
      Log.info("Could not parse " + box.getText());
    }
    dirtyText = false;
  }

}
