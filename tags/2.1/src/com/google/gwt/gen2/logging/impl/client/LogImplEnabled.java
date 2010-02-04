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

package com.google.gwt.gen2.logging.impl.client;

import com.google.gwt.gen2.logging.impl.shared.LogImplWithManager;
import com.google.gwt.gen2.logging.shared.Level;
import com.google.gwt.gen2.logging.shared.Log;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;

/**
 * LogImpl used when logging is set to "all".
 */
public class LogImplEnabled extends LogImplWithManager {

  private static void readLevel() {
    String param = Location.getParameter("logLevel");
    if (param != null) {
      try {
        Level l = Level.parse(param);
        Log.setDefaultLevel(l);
      } catch (IllegalArgumentException argument) {
        Window.alert(param
            + " is an illegal arguement for debugLevel. We are ignoring it, use 'SEVERE', 'WARNING', 'CONFIG', 'FINE',etc. instead.");
      }
    }
  }

  @Override
  public void init() {
    initializeLevels();
    readLevel();
    addDefaultLogHandler();
  }
}
