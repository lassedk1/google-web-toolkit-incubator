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

import com.google.gwt.user.client.Event;
import com.google.gwt.dom.client.Element;

public abstract class BrowserEvent<T extends EventHandler>
    extends AbstractEvent<T> implements EventData {

  private Event eventData;

  public void cancelBubble(boolean cancel) {
    eventData.cancelBubble(cancel);
  }

  public boolean getAltKey() {
    return eventData.getAltKey();
  }

  public int getButton() {
    return eventData.getButton();
  }

  public int getClientX() {
    return eventData.getClientX();
  }

  public int getClientY() {
    return eventData.getClientY();
  }

  public boolean getCtrlKey() {
    return eventData.getCtrlKey();
  }

  public Element getCurrentTarget() {
    return eventData.getCurrentTarget();
  }

  public Element getFromElement() {
    return eventData.getFromElement();
  }

  public int getKeyCode() {
    return eventData.getKeyCode();
  }

  public boolean getMetaKey() {
    return eventData.getMetaKey();
  }

  public int getMouseWheelVelocityY() {
    return eventData.getMouseWheelVelocityY();
  }

  public boolean getRepeat() {
    return eventData.getRepeat();
  }

  public int getScreenX() {
    return eventData.getScreenX();
  }

  public int getScreenY() {
    return eventData.getScreenY();
  }

  public boolean getShiftKey() {
    return eventData.getShiftKey();
  }

  public String getString() {
    return eventData.getString();
  }

  public Element getTarget() {
    return eventData.getTarget();
  }

  public Element getToElement() {
    return eventData.getToElement();
  }

  public String getType() {
    return eventData.getType();
  }

  public int getTypeInt() {
    return eventData.getTypeInt();
  }

  public void preventDefault() {
    eventData.preventDefault();
  }

  public static class Key extends AbstractEvent.Key {

    private int eventBits;

    // type safe constructor, unfortunately only a single event supported
    // as EnumSet is not efficient on GWT
    public Key(BrowserEvents event) {

      this.eventBits = event.getEventBits();
    }

    // old style, multi-bits
    public Key(int eventBits) {
      this.eventBits = eventBits;
    }

    public int getEventBitsToSink() {
      return eventBits;
    }
  }

  protected BrowserEvent(Event data) {
    this.eventData = data;
  }

  public EventData getEventData() {
    return this;
  }
}
