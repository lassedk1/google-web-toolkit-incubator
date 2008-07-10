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
package com.google.gwt.libideas.events.client;

import com.google.gwt.dom.client.Element;

public interface EventData {
  /**
   * Cancels bubbling for the given event. This will stop the event from being
   * propagated to parent elements.
   *
   * @param cancel <code>true</code> to cancel bubbling
   */
  void cancelBubble(boolean cancel);

  /**
   * Gets whether the ALT key was depressed when the given event occurred.
   *
   * @return <code>true</code> if ALT was depressed when the event occurred
   */
  boolean getAltKey();

  /**
   * Gets the mouse buttons that were depressed when the given event occurred.
   *
   * @return a bit-field, defined by {@link Event#BUTTON_LEFT}, {@link
   *         Event#BUTTON_MIDDLE}, and {@link Event#BUTTON_RIGHT}
   */
  int getButton();

  /**
   * Gets the mouse x-position within the browser window's client area.
   *
   * @return the mouse x-position
   */
  int getClientX();

  /**
   * Gets the mouse y-position within the browser window's client area.
   *
   * @return the mouse y-position
   */
  int getClientY();

  /**
   * Gets whether the CTRL key was depressed when the given event occurred.
   *
   * @return <code>true</code> if CTRL was depressed when the event occurred
   */
  boolean getCtrlKey();

  /**
   * Gets the current target element of this event. This is the element whose
   * listener fired last, not the element which fired the event initially.
   *
   * @return the event's current target element
   */
  Element getCurrentTarget();

  /**
   * Gets the element from which the mouse pointer was moved (only valid for
   * {@link Event#ONMOUSEOVER}).
   *
   * @return the element from which the mouse pointer was moved
   */
  Element getFromElement();

  /**
   * Gets the key code associated with this event.
   *
   * <p> For {@link Event#ONKEYPRESS}, this method returns the Unicode value of
   * the character generated. For {@link Event#ONKEYDOWN} and {@link
   * Event#ONKEYUP}, it returns the code associated with the physical key. </p>
   *
   * @return the Unicode character or key code.
   * @see com.google.gwt.user.client.ui.KeyboardListener
   */
  int getKeyCode();

  /**
   * Gets whether the META key was depressed when the given event occurred.
   *
   * @return <code>true</code> if META was depressed when the event occurred
   */
  boolean getMetaKey();

  /**
   * Gets the velocity of the mouse wheel associated with the event along the Y
   * axis. <p> The velocity of the event is an artifical measurement for
   * relative comparisons of wheel activity. It is affected by some non-browser
   * factors, including choice of input hardware and mouse acceleration
   * settings. The sign of the velocity measurement agrees with the screen
   * coordinate system; negative values are towards the origin and positive
   * values are away from the origin. Standard scrolling speed is approximately
   * ten units per event. </p>
   *
   * @return The velocity of the mouse wheel.
   */
  int getMouseWheelVelocityY();

  /**
   * Gets the key-repeat state of this event.
   *
   * @return <code>true</code> if this key event was an auto-repeat
   */
  boolean getRepeat();

  /**
   * Gets the mouse x-position on the user's display.
   *
   * @return the mouse x-position
   */
  int getScreenX();

  /**
   * Gets the mouse y-position on the user's display.
   *
   * @return the mouse y-position
   */
  int getScreenY();

  /**
   * Gets whether the shift key was depressed when the given event occurred.
   *
   * @return <code>true</code> if shift was depressed when the event occurred
   */
  boolean getShiftKey();

  /**
   * Gets a string representation of this event.
   *
   * We do not override {@link #toString()} because it is final in {@link
   * com.google.gwt.core.client.JavaScriptObject}.
   *
   * @return the string representation of this event
   */
  String getString();

  /**
   * Returns the element that was the actual target of the given event.
   *
   * @return the target element
   */
  Element getTarget();

  /**
   * Gets the element to which the mouse pointer was moved (only valid for
   * {@link Event#ONMOUSEOUT}).
   *
   * @return the element to which the mouse pointer was moved
   */
  Element getToElement();

  /**
   * Gets the enumerated type of this event (as defined in {@link Event}).
   *
   * @return the event's enumerated type
   */
  String getType();

  /**
   * Gets the enumerated type of this event, as defined by {@link #ONCLICK},
   * {@link #ONMOUSEDOWN}, and so forth.
   *
   * @return the event's enumerated type
   */
  int getTypeInt();

  /**
   * Prevents the browser from taking its default action for the given event.
   */
  void preventDefault();

  void stopPropagation();
}
