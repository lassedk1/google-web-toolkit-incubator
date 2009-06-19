/*
 * Copyright 2009 Google Inc.
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
package com.google.gwt.gen2.demo.simplelogging.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.gen2.logging.shared.DefaultClientLogHandler;
import com.google.gwt.gen2.logging.shared.Log;
import com.google.gwt.gen2.logging.shared.LogEvent;

/**
 * Simple logging demo to show how logging is not restricted to gwt user code.
 */
public class SimpleLoggingDemo implements DefaultClientLogHandler, EntryPoint {

  /**
   * Displays a message in a modal dialog box.
   * 
   * @param msg the message to be displayed.
   */
  public static native void alert(String msg) /*-{
    $wnd.alert(msg);
  }-*/;

  public void onLog(LogEvent event) {
    alert("Default logger:" + event.getLevel() + ":" + event.getMessage());
  }

  public void onModuleLoad() {
    Log.info("I can be used by widget sets that do not include gwt's user libraries");
    // com.google.gwt.user.client.Window.alert("If you uncomment me, your compile should fail, as user libraries are not included");
  }

}
