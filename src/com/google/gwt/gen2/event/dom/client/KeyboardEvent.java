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
package com.google.gwt.gen2.event.dom.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.KeyboardListener;

/**
 * Base class for Keyboard events.
 */
public abstract class KeyboardEvent extends DomEvent {
  /**
   * Alt key code.
   */
  public static final int KEY_ALT = 18;
  /**
   * Backspace key code.
   */
  public static final int KEY_BACKSPACE = 8;
  /**
   * Control key code.
   */
  public static final int KEY_CTRL = 17;
  /**
   * Delete key code.
   */
  public static final int KEY_DELETE = 46;
  /**
   * Down arrow code.
   */
  public static final int KEY_DOWN = 40;
  /**
   * End key code.
   */
  public static final int KEY_END = 35;
  /**
   * Enter key code.
   */
  public static final int KEY_ENTER = 13;
  /**
   * Escape key code.
   */
  public static final int KEY_ESCAPE = 27;

  /**
   * Home key code.
   */
  public static final int KEY_HOME = 36;
  /**
   * Left key code.
   */
  public static final int KEY_LEFT = 37;

  /**
   * Page down key code.
   */
  public static final int KEY_PAGEDOWN = 34;

  /**
   * Page up key code.
   */
  public static final int KEY_PAGEUP = 33;
  /**
   * Right arrow key code.
   */
  public static final int KEY_RIGHT = 39;
  /**
   * Shift key code.
   */
  public static final int KEY_SHIFT = 16;
  /**
   * Tab key code.
   */
  public static final int KEY_TAB = 9;

  /**
   * Up Arrow key code.
   */
  public static final int KEY_UP = 38;

  /**
   * Alt modifier.
   */
  public static final int MODIFIER_ALT = 4;

  /**
   * Control modifier.
   */
  public static final int MODIFIER_CTRL = 2;

  /**
   * Meta modifier.
   */
  public static final int MODIFIER_META = 8;

  /**
   * Shift modifier.
   */
  public static final int MODIFIER_SHIFT = 1;

  // I'm hoping these can eventually go away as safari fixes its bugs.
  private static final int OTHER_KEY_UP = 63232;

  private static final int OTHER_KEY_DOWN = 63233;

  private static final int OTHER_KEY_LEFT = 63234;

  private static final int OTHER_KEY_RIGHT = 63235;

  /**
   * Constructor.
   * 
   * @param nativeEvent the wrapped native event
   */
  protected KeyboardEvent(Event nativeEvent) {
    super(nativeEvent);
  }

  /**
   * Gets the keyboard modifiers associated with this event.
   * 
   * @return the modifiers as defined in {@link KeyboardListener}.
   */
  public int getKeyboardModifiers() {
    Event event = getNativeEvent();
    return (DOM.eventGetShiftKey(event) ? MODIFIER_SHIFT : 0)
        | (DOM.eventGetMetaKey(event) ? MODIFIER_META : 0)
        | (DOM.eventGetCtrlKey(event) ? MODIFIER_CTRL : 0)
        | (DOM.eventGetAltKey(event) ? MODIFIER_ALT : 0);
  }

  /**
   * Gets the current key code.
   * 
   * @return the key code
   */
  public int getKeyCode() {
    return getNativeEvent().getKeyCode();
  }

  /**
   * Is the key code alpha-numeric (i.e. A-z or 0-9).
   * 
   * @return is the key code alpha numeric.
   */
  public boolean isAlphaNumeric() {
    int keycode = getKeyCode();
    return (48 <= keycode && keycode <= 57) || (65 <= keycode && keycode <= 90);
  }

  /**
   * Is the <code>alt</code> key down.
   * 
   * @return whether the alt key is down
   */
  public boolean isAltKeyDown() {
    return getNativeEvent().getAltKey();
  }

  /**
   * Does the key code represent an arrow key?
   * 
   * @param code the key code
   * @return if it is an arrow key code
   */
  public boolean isArrowKeyCode(int code) {
    switch (code) {
      case OTHER_KEY_DOWN:
      case OTHER_KEY_RIGHT:
      case OTHER_KEY_UP:
      case OTHER_KEY_LEFT:
      case KeyboardListener.KEY_DOWN:
      case KeyboardListener.KEY_RIGHT:
      case KeyboardListener.KEY_UP:
      case KeyboardListener.KEY_LEFT:
        return true;
      default:
        return false;
    }
  }

  /**
   * Gets the key-repeat state of this event.
   * 
   * @return <code>true</code> if this key event was an auto-repeat
   */
  public boolean isAutoRepeat() {
    return getNativeEvent().getRepeat();
  }

  /**
   * Is the <code>control</code> key down.
   * 
   * @return whether the control key is down
   */
  public boolean isControlKeyDown() {
    return getNativeEvent().getCtrlKey();
  }

  /**
   * Is this a key down?
   * 
   * @return whether this is a down arrow key event
   */
  public boolean isDownKeyCode() {
    return getKeyCode() == KEY_DOWN || getKeyCode() == OTHER_KEY_DOWN;
  }

  /**
   * Is this a key left?
   * 
   * @return whether this is a left arrow key event
   */
  public boolean isLeftKeyCode() {
    return getKeyCode() == KEY_LEFT || getKeyCode() == OTHER_KEY_LEFT;
  }

  /**
   * Is this a key right?
   * 
   * @return whether this is a right arrow key event
   */
  public boolean isRightKeyCode() {
    return getKeyCode() == KEY_RIGHT || getKeyCode() == OTHER_KEY_RIGHT;
  }

  /**
   * Is the <code>shift</code> key down.
   * 
   * @return whether the shift key is down
   */
  public boolean isShiftKeyDown() {
    return getNativeEvent().getShiftKey();
  }

  /**
   * Is this a key up?
   * 
   * @return whether this is a up arrow key event
   */
  public boolean isUpKeyCode() {
    return getKeyCode() == KEY_UP || getKeyCode() == OTHER_KEY_UP;
  }
}
