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
package com.google.gwt.libideas.events.client.synthetic;

import com.google.gwt.libideas.events.client.AbstractEvent;
import com.google.gwt.libideas.events.client.EventData;
import com.google.gwt.libideas.events.client.mouse.MouseOverEvent;

public class SyntheticMouseOverEvent extends MouseOverEvent {

  public static AbstractEvent.Key KEY = new AbstractEvent.Key();

  private EventData data;

  public SyntheticMouseOverEvent(EventData data) {
    super(null);
    this.data = data;
  }

  public Button getButton() {
    return Button.valueOf(getEventData().getButton());
  }

  /**
   * Gets the mouse x-position within the browser window's client area.
   *
   * @return the mouse x-position
   */
  public int getClientX() {
    return getEventData().getClientX();
  }

  /**
   * Gets the mouse y-position within the browser window's client area.
   *
   * @return the mouse y-position
   */
  public int getClientY() {
    return getEventData().getClientY();
  }

  public EventData getEventData() {
    return data;
  }

  /**
   * Gets the mouse x-position on the user's display.
   *
   * @return the mouse x-position
   */
  public int getScreenX() {
    return getEventData().getScreenX();
  }

  /**
   * Gets the mouse y-position on the user's display.
   *
   * @return the mouse y-position
   */
  public int getScreenY() {
    return getEventData().getScreenY();
  }

  /**
   * Is <code>alt</code> key down.
   *
   * @return whether the alt key is down
   */
  public boolean isAltKeyDown() {
    return getEventData().getAltKey();
  }

  /**
   * Is <code>control</code> key down.
   *
   * @return whether the control key is down
   */
  public boolean isControlKeyDown() {
    return getEventData().getCtrlKey();
  }

  /**
   * Is <code>meta</code> key down.
   *
   * @return whether the meta key is down
   */
  public boolean isMetaKeyDown() {
    return getEventData().getMetaKey();
  }

  /**
   * Is <code>shift</code> key down.
   *
   * @return whether the shift key is down
   */
  public boolean isShiftKeyDown() {
    return getEventData().getShiftKey();
  }

  public void preventDefault() {
    getEventData().preventDefault();
  }

  public void stopPropagation() {
    getEventData().stopPropagation();
  }

  protected AbstractEvent.Key getKey() {
    return KEY;
  }
}