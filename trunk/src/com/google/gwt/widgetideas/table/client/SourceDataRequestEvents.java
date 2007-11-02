/*
 * Copyright 2007 Google Inc.
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
package com.google.gwt.widgetideas.table.client;

/**
 * An interface for tables that request data.
 */
public interface SourceDataRequestEvents {
  /**
   * Add a new {@link DataRequestListener}.
   * 
   * @param listener the listener
   */
  public void addDataRequestListener(DataRequestListener listener);

  /**
   * Remove a {@link DataRequestListener}.
   * 
   * @param listener the listener to remove
   */
  public void removeDataRequestListener(DataRequestListener listener);
}
