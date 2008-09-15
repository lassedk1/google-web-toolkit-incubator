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

import com.google.gwt.gen2.event.dom.client.BlurEvent;
import com.google.gwt.gen2.event.dom.client.BlurHandler;
import com.google.gwt.gen2.event.dom.client.FocusEvent;
import com.google.gwt.gen2.event.dom.client.FocusHandler;
import com.google.gwt.gen2.event.shared.AbstractEvent.Key;
import com.google.gwt.user.client.ui.FocusListener;

/**
 *Handler wrapper for {@link FocusListener}.
 */
public class FocusListenerWrapper extends
    AbstractListenerWrapper<FocusListener> implements FocusHandler, BlurHandler {

  private static Key[] keys = {FocusEvent.KEY, BlurEvent.KEY};

  public FocusListenerWrapper(FocusListener listener) {
    super(listener);
  }

  public void onBlur(BlurEvent event) {
    listener.onLostFocus(source(event));
  }

  public void onFocus(FocusEvent event) {
    listener.onFocus(source(event));
  }

  @Override
  protected Key[] getKeys() {
    return keys;
  }
}
