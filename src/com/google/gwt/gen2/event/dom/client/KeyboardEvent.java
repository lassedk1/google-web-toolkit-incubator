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
 * 
 */
public abstract class KeyboardEvent extends DomEvent {

  protected KeyboardEvent(Event e) {
    super(e);
  }

  /**
   * Gets the keyboard modifiers associated with this event.
   * 
   * @return the modifiers as defined in {@link KeyboardListener}.
   */
  public int getKeyboardModifiers() {
    Event event = getNativeEvent();
    return (DOM.eventGetShiftKey(event) ? KeyboardListener.MODIFIER_SHIFT : 0)
        | (DOM.eventGetMetaKey(event) ? KeyboardListener.MODIFIER_META : 0)
        | (DOM.eventGetCtrlKey(event) ? KeyboardListener.MODIFIER_CTRL : 0)
        | (DOM.eventGetAltKey(event) ? KeyboardListener.MODIFIER_ALT : 0);
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
   * Is <code>alt</code> key down.
   * 
   * @return whether the alt key is down
   */
  public boolean isAltKeyDown() {
    return getNativeEvent().getAltKey();
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
   * Is <code>control</code> key down.
   * 
   * @return whether the control key is down
   */
  public boolean isControlKeyDown() {
    return getNativeEvent().getCtrlKey();
  }

  /**
   * Is <code>shift</code> key down.
   * 
   * @return whether the shift key is down
   */
  public boolean isShiftKeyDown() {
    return getNativeEvent().getShiftKey();
  }
}
