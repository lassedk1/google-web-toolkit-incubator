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

package com.google.gwt.gen2.logging.server;

import com.google.gwt.gen2.logging.shared.Level;
import com.google.gwt.gen2.logging.shared.Log;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.gen2.logging.handler.client.RemoteLogHandler;
import com.google.gwt.gen2.logging.handler.client.RemoteLogHandler.RemoteThrowable;

/**
 * Remote logging implementation.
 */
public class RemoteLoggingService extends RemoteServiceServlet implements
    RemoteLogHandler.Service {
  private static String CATEGORY = "gwt.remote";

  public final void publish(String message, Level level, String category,
      RemoteThrowable ex) {
    try {
      if (category == null) {
        category = CATEGORY;
      } else {
        category = CATEGORY + "." + category;
      }
      if (level == null) {
        throw new IllegalStateException("how did level become null?");
      }
      Log.log(message, level, category, ex);
    } catch (RuntimeException e) {
      System.err.println("Failed to log message due to " + e.toString());
      e.printStackTrace();
    }
  }
}
