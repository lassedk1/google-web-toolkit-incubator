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
package com.google.gwt.gen2.demo.event.client;

import com.google.gwt.gen2.event.virtual.shared.EventData;
import com.google.gwt.gen2.event.virtual.shared.VMouseEvent;

/**
 * TODO
 */
public class VDragEndEvent extends VMouseEvent<VDragEndHandler> {

  public static final Key<VDragEndHandler> KEY = new Key<VDragEndHandler>();

  public VDragEndEvent(EventData eventData) {
    super(KEY, eventData);
  }

  public void fireEvent(VDragEndHandler handler) {
    handler.onDragEnd(this);
  }
}