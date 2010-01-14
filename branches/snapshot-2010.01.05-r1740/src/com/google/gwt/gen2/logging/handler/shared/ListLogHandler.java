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

package com.google.gwt.gen2.logging.handler.shared;

import com.google.gwt.gen2.logging.shared.LogEvent;
import com.google.gwt.gen2.logging.shared.LogHandler;

import java.util.ArrayList;

/**
 * This handler is used to integrate with a secondary analysis or testing
 * system. It stores a copy of all log events received in an
 * {@link java.util.List}.
 */
public class ListLogHandler extends ArrayList<LogEvent> implements LogHandler {

  /**
   * Gets the last log event registered with this handler.
   * 
   * @return the last log event
   */
  public LogEvent getLast() {
    return get(size() - 1);
  }

  public void onLog(LogEvent event) {
    add(event.saveCopy());
  }
}
