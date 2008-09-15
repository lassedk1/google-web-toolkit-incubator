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

import com.google.gwt.gen2.event.dom.client.MouseDownEvent;
import com.google.gwt.gen2.event.dom.client.MouseDownHandler;
import com.google.gwt.gen2.event.dom.client.MouseMoveEvent;
import com.google.gwt.gen2.event.dom.client.MouseMoveHandler;
import com.google.gwt.gen2.event.dom.client.MouseOutEvent;
import com.google.gwt.gen2.event.dom.client.MouseOutHandler;
import com.google.gwt.gen2.event.dom.client.MouseOverEvent;
import com.google.gwt.gen2.event.dom.client.MouseOverHandler;
import com.google.gwt.gen2.event.dom.client.MouseUpEvent;
import com.google.gwt.gen2.event.dom.client.MouseUpHandler;
import com.google.gwt.gen2.event.shared.AbstractEvent.Key;
import com.google.gwt.user.client.ui.MouseListener;

/**
 * Adaptor for {@link MouseListener}.
 */
public class MouseListenerWrapper extends
    AbstractListenerWrapper<MouseListener> implements MouseDownHandler,
    MouseUpHandler, MouseOutHandler, MouseOverHandler, MouseMoveHandler {
  private static Key[] keys = {
      MouseDownEvent.KEY, MouseUpEvent.KEY, MouseOutEvent.KEY,
      MouseOverEvent.KEY, MouseMoveEvent.KEY};

  protected MouseListenerWrapper(MouseListener listener) {
    super(listener);
  }

  public void onMouseDown(MouseDownEvent event) {
    listener.onMouseDown(source(event), event.getClientX(), event.getScreenY());
  }

  public void onMouseMove(MouseMoveEvent event) {
    listener.onMouseMove(source(event), event.getClientX(), event.getClientY());
  }

  public void onMouseOut(MouseOutEvent event) {
    listener.onMouseLeave(source(event));
  }

  public void onMouseOver(MouseOverEvent event) {
    listener.onMouseEnter(source(event));
  }

  public void onMouseUp(MouseUpEvent event) {
    listener.onMouseUp(source(event), event.getClientX(), event.getClientY());
  }

  @Override
  protected Key[] getKeys() {
    return keys;
  }
}
