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

import com.google.gwt.gen2.commonwidget.client.impl.StandardCssImpl;
import com.google.gwt.gen2.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.gen2.event.dom.client.KeyDownEvent;
import com.google.gwt.gen2.event.dom.client.KeyDownHandler;
import com.google.gwt.gen2.event.logical.shared.SelectionEvent;
import com.google.gwt.gen2.event.logical.shared.SelectionHandler;
import com.google.gwt.gen2.event.logical.shared.WrongFormatEvent;
import com.google.gwt.gen2.event.logical.shared.WrongFormatHandler;
import com.google.gwt.gen2.event.shared.HandlerRegistration;
import com.google.gwt.gen2.widgetbase.client.Gen2Composite;
import com.google.gwt.gen2.widgetbase.client.WidgetCss;
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

  /**
   * Css interface for date box.
   */
  public static interface Css extends WidgetCss {
    /**
     * Main style name.
     * 
     * @return 'gwt-DateBox'
     */
    @ClassName("gwt-DateBox")
    String dateBox();
  }

  /**
   * Standard css processing for {@link DateBox}.
   * 
   */
  private static class StandardCss extends StandardCssImpl implements Css {
    static Css DEFAULT = new StandardCss("gwt-DateBox");

    public StandardCss(String baseStyleName) {
      super(baseStyleName, "dateBox");
    }

    public String dateBox() {
      return getWidgetStyleName();
    }
  }

  private static final DateTimeFormat DEFAULT_FORMATTER = DateTimeFormat.getMediumDateFormat();
  private boolean dirtyText = false;
  private DropDownPanel<DateBox> popup = new DropDownPanel<DateBox>();
  private TextBox box = new TextBox();
  private DatePicker picker;
  private DateTimeFormat dateFormatter;
  private boolean allowDPShow = true;

  /**
   * Constructor.
   */
  public DateBox() {
    this(StandardCss.DEFAULT);
  }

  /**
   * Constructor.
   * 
   * @param css the css to use
   */
  public DateBox(Css css) {
    this(new DatePicker(), css);
  }

  /**
   * Constructor.
   * 
   * @param picker the date picker to use
   */
  public DateBox(final DatePicker picker) {
    this(picker, StandardCss.DEFAULT);
  }

  /**
   * Constructor.
   * 
   * @param picker the picker to drop down
   * @param css the css to use for this date box.
   */
  public DateBox(final DatePicker picker, Css css) {
    this(picker, css, DEFAULT_FORMATTER);
  }

  /**
   * Constructor.
   * 
   * @param picker the picker to drop down
   * @param css the css to use for this date box.
   * @param formatter date time formatter to use for parsing the dates in this
   *          date box
   */
  public DateBox(final DatePicker picker, Css css, DateTimeFormat formatter) {
    FlowPanel p = new FlowPanel();
    this.dateFormatter = formatter;
    p.add(box);
    this.picker = picker;
    popup.setWidget(picker);
    initWidget(p);
    setStyleName(css.dateBox());
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
          showCurrentDate();
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
        showCurrentDate();
      }
    });

    box.addKeyboardListener(new KeyboardListener() {
      public void onKeyDown(Widget sender, char keyCode, int modifiers) {
        fireEvent(new KeyDownEvent(DOM.eventGetCurrentEvent()));

        switch (keyCode) {
          case KEY_ENTER:
          case KEY_TAB:
          case KEY_ESCAPE:
          case KEY_UP:
            updateDateFromTextBox();
            popup.hide();
            break;
          case KEY_DOWN:
            showCurrentDate();
            break;
          default:
            dirtyText = true;
        }
      }

      public void onKeyPress(Widget sender, char keyCode, int modifiers) {
      }

      public void onKeyUp(Widget sender, char keyCode, int modifiers) {
      }
    });
    setWrongFormatHandler(new WrongFormatHandler<String>() {
      public void onWrongFormat(WrongFormatEvent<String> event) {
        Log.warning("DateBox cannot parse: " + event.getValue());
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
    picker.setValue(null, false);
    box.setText("");
  }

  /**
   * Gets the current cursor position in the date box.
   * 
   * @return the cursor position
   * 
   */
  public int getCursorPos() {
    return box.getCursorPos();
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
   * 
   * @return the text in the date box
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
    if (format != dateFormatter) {
      dateFormatter = format;
      String cur = box.getText();
      if (cur != null && cur.length() != 0) {
        try {
          box.setText(dateFormatter.format(picker.getValue()));
        } catch (IllegalArgumentException e) {
          box.setText("");
        }
      }
    }
  }

  /**
   * Sets the default css for {@link DateBox}.
   * 
   * @param css the css to use as the default
   * 
   */
  public void setDefaultCss(Css css) {
    StandardCss.DEFAULT = css;
  }

  /**
   * Sets whether the date box is enabled.
   * 
   * @param enabled is the box enabled
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
   * Parses the current date box's value and shows that date.
   */
  public void showCurrentDate() {
    Date current = null;

    String value = box.getText().trim();
    if (!value.equals("")) {
      try {
        current = dateFormatter.parse(value);
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
   * Show the given date.
   * 
   * @param date picker
   */
  public void showDate(Date date) {
    picker.setValue(date, false);
    picker.showDate(date);
    setText(date);
    dirtyText = false;
  }

  /**
   * Sets the handler used to handle wrong format events. The previous handler
   * is removed.
   * 
   * @param handler the wrong format handler
   */
  protected void setWrongFormatHandler(WrongFormatHandler<String> handler) {
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
    box.setText(dateFormatter.format(value));
    dirtyText = false;
  }

  private void updateDateFromTextBox() {
    String text = box.getText().trim();
    if (text.equals("")) {
      return;
    }
    try {
      Date d = dateFormatter.parse(text);
      showDate(d);
    } catch (IllegalArgumentException exception) {
      fireEvent(new WrongFormatEvent<String>(text));
    }
    dirtyText = false;
  }

}
