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

package com.google.gwt.gen2.datepicker.client;

import com.google.gwt.gen2.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.gen2.event.dom.client.KeyDownEvent;
import com.google.gwt.gen2.event.dom.client.KeyDownHandler;
import com.google.gwt.gen2.event.logical.shared.SelectionEvent;
import com.google.gwt.gen2.event.logical.shared.SelectionHandler;
import com.google.gwt.gen2.event.logical.shared.WrongFormatEvent;
import com.google.gwt.gen2.event.logical.shared.WrongFormatHandler;
import com.google.gwt.gen2.event.shared.HandlerRegistration;
import com.google.gwt.gen2.widgetbase.client.Gen2Composite;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HasAnimation;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.DropDownPanel;

import java.util.Date;

/**
 * A simple date box.
 */
public class DateBox extends Gen2Composite implements HasKeyDownHandlers,
    HasAnimation {

  private static class Styles {
    public static String DEFAULT = "gwt-DateBox";
  }

  private boolean dirtyText = false;

  private DropDownPanel<DateBox> popup = new DropDownPanel<DateBox>();
  private TextBox box = new TextBox();
  private DatePicker picker;
  private DateTimeFormat formatter = DateTimeFormat.getMediumDateFormat();
  private boolean allowDPShow = true;

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
    picker.addSelectionHandler(new SelectionHandler<Date>() {
      public void onSelection(SelectionEvent<Date> event) {
        setText(event.getNewValue());
        hideDatePicker();
        preventDatePickerPopup();
        box.setFocus(true);
      }

    });

    box.addFocusListener(new FocusListener() {
      public void onFocus(Widget sender) {
        if (allowDPShow) {
          showDatePicker();
        }
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
        fireEvent(new KeyDownEvent(DOM.eventGetCurrentEvent()));

        switch (keyCode) {
          case KEY_ENTER:
          case KEY_TAB:
          case KEY_ESCAPE:
          case KEY_DOWN: {
            updateDateFromTextBox();
            popup.hide();
            break;
          }
          default:
            dirtyText = true;
        }
      }

      public void onKeyPress(Widget sender, char keyCode, int modifiers) {
      }

      public void onKeyUp(Widget sender, char keyCode, int modifiers) {
      }
    });
  }

  public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
    return addHandler(KeyDownEvent.KEY, handler);
  }

  /**
   * Clears the current selection.
   */
  public void clear() {
    picker.setSelectedDate(null, false);
    box.setText("");
  }

  /**
   * Gets the current cursor position in the date box.
   */
  public int getCursorPos() {
    return box.getCursorPos();
  }

  /**
   * Gets the current date formatter.
   */
  public DateTimeFormat getDateFormatter() {
    return formatter;
  }

  /**
   * Gets the date picker.
   * 
   * @return the date picker
   */
  public DatePicker getDatePicker() {
    return picker;
  }

  /**
   * Gets the date box's position in the tab index.
   * 
   * @return the date box's tab index
   */
  public int getTabIndex() {
    return box.getTabIndex();
  }

  /**
   * Get current text in text box.
   */
  public String getText() {
    return box.getText();
  }

  /**
   * Hide the date picker.
   */
  public void hideDatePicker() {
    popup.hide();
  }

  public boolean isAnimationEnabled() {
    return popup.isAnimationEnabled();
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

  public void setAnimationEnabled(boolean enable) {
    popup.setAnimationEnabled(enable);
  }

  /**
   * Sets the date format to the given format. If date box is not empty,
   * contents of date box will be replaced with current date in new format.
   * 
   * @param format format.
   */
  public void setDateFormat(DateTimeFormat format) {
    if (format != formatter) {
      formatter = format;
      String cur = box.getText();
      if (cur != null && cur.length() != 0) {
        try {
          box.setText(formatter.format(picker.getSelectedDate()));
        } catch (IllegalArgumentException e) {
          box.setText("");
        }
      }
    }
  }

  /**
   * Sets whether the date box is enabled
   */
  public void setEnabled(boolean enabled) {
    box.setEnabled(enabled);
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
    popup.showRelativeTo(this);
  }

  /**
   * Default way to handle format errors.
   * 
   * @param text text
   */
  protected void handleIllegalInput(String text) {
    Log.info("Format error: " + text);
  }

  /**
   * Sets the handler used to handle wrong format events. The previous handler
   * is removed.
   * 
   * @param handler the wrong format handler
   */
  protected void setWrongFormatHandler(WrongFormatHandler handler) {
    getHandlerManager().clearHandlers(WrongFormatEvent.KEY);
  }

  private void preventDatePickerPopup() {
    allowDPShow = false;
    DeferredCommand.addCommand(new Command() {
      public void execute() {
        allowDPShow = true;
      }
    });
  }

  private void setText(Date value) {
    box.setText(formatter.format(value));
    dirtyText = false;
  }

  private void updateDateFromTextBox() {

    String text = box.getText().trim();
    if (text.equals("")) {
      return;
    }
    try {
      Date d = formatter.parse(text);
      showDate(d);
    } catch (IllegalArgumentException exception) {
      handleIllegalInput(text);
    }
    dirtyText = false;
  }

}
