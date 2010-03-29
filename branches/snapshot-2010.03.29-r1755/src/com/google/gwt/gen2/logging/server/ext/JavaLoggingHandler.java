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

package com.google.gwt.gen2.logging.server.ext;

import com.google.gwt.gen2.logging.shared.Level;
import com.google.gwt.gen2.logging.shared.LogEvent;
import com.google.gwt.gen2.logging.shared.LogHandler;

import java.util.logging.Logger;

/**
 * Java logging handler for GWT.
 */
public class JavaLoggingHandler implements LogHandler {

  private Logger gwt = Logger.getLogger("gwt");

  public void onLog(LogEvent event) {
    Logger logger = gwt;
    if (event.getCategory() != null) {
      logger = Logger.getLogger("gwt." + event.getCategory());
    }
    logger.log(convertLevel(event.getLevel()), event.getMessage(),
        event.getThrown());
  }

  private java.util.logging.Level convertLevel(Level level) {
    return java.util.logging.Level.parse(level.getName());
  }

}
