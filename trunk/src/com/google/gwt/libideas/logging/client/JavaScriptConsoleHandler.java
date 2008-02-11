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

package com.google.gwt.libideas.logging.client;

import com.google.gwt.libideas.logging.shared.Handler;
import com.google.gwt.libideas.logging.shared.Level;

/**
 * Lightweight JavaScript handler which degrades in the following way
 * <li> Use Firebug console</li>
 * <li> Use the JavaScript console</li>
 * <li> Logging done to popup window. </li>
 */
public class JavaScriptConsoleHandler extends Handler {

  private native void publish(String msg, int level) /*-{
    if($wnd.console){
      if($wnd.console.firebug){
        if(level <= 800) {
          console.info(msg);
        } else {
          console.warn(msg);
        }
      } else{
        console.log(msg);
      }
    } else {
      var logging = $wnd.open('','logging','width=100,height=200');
      if(logging){
        logging.document.write(msg + "<br/>");
      }
    }
  }-*/;

  public void publish(String message, Level level, String category, Throwable e) {
    publish(format(message, level, category, e), level.intValue());
  }
}
