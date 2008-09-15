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
package com.google.gwt.gen2.event.virtual.shared;
/**
 * TODO(cromwellian).
 */
public class VLoadEvent extends VirtualEvent<VLoadHandler> {

  public static Key<VLoadHandler> KEY = new Key<VLoadHandler>();

  public VLoadEvent(EventData data) {
    super(KEY, data);
  }

  @Override
  protected void fireEvent(VLoadHandler handler) {
    handler.onLoad(this);
  }
}