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

import com.google.gwt.gen2.logging.impl.server.LogImplServer;
import com.google.gwt.gen2.logging.shared.LogHandler;

/**
 * Server logging configuration, use this class to configure server side
 * logging.
 */
public class ServerLoggingConfiguration {
  /**
   * Sets the server log configuration to use.
   * 
   * @param config the server logging configuration
   */
  public static void set(ServerLoggingConfiguration config) {
    LogImplServer.config = config;
  }

  /**
   * Gets the default log handler to use when logging to the server.
   * 
   * @return the default log handler
   */
  public LogHandler getDefaultLogHandler() {
    return new JavaLoggingHandler();
  }
}
