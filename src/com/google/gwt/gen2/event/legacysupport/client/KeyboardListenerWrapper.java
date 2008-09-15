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

package com.google.gwt.gen2.event.legacysupport.client;

import com.google.gwt.gen2.event.dom.client.KeyDownEvent;
import com.google.gwt.gen2.event.dom.client.KeyDownHandler;
import com.google.gwt.gen2.event.dom.client.KeyPressEvent;
import com.google.gwt.gen2.event.dom.client.KeyPressHandler;
import com.google.gwt.gen2.event.dom.client.KeyUpEvent;
import com.google.gwt.gen2.event.dom.client.KeyUpHandler;
import com.google.gwt.gen2.event.shared.AbstractEvent.Key;
import com.google.gwt.user.client.ui.KeyboardListener;

/**
 * Adaptor for {@link KeyboardListener}.
 */
public class KeyboardListenerWrapper extends
    AbstractListenerWrapper<KeyboardListener> implements KeyDownHandler,
    KeyUpHandler, KeyPressHandler {

  private static Key[] keys = {
      KeyUpEvent.KEY, KeyPressEvent.KEY, KeyDownEvent.KEY};

  public KeyboardListenerWrapper(KeyboardListener listener) {
    super(listener);
  }

  public void onKeyDown(KeyDownEvent event) {
    listener.onKeyDown(source(event), (char) event.getKeyCode(),
        event.getKeyboardModifiers());
  }

  public void onKeyPressed(KeyPressEvent event) {
    listener.onKeyPress(source(event), (char) event.getKeyCode(),
        event.getKeyboardModifiers());
  }

  public void onKeyUp(KeyUpEvent event) {
    source(event);
    listener.onKeyUp(source(event), (char) event.getKeyCode(),
        event.getKeyboardModifiers());
  }

  @Override
  protected Key[] getKeys() {
    return keys;
  }
}
