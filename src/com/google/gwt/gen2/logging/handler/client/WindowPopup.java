// CHECKSTYLE_OFF (the copywrite is Fred's because it is based on the code from
// the window's logger.
/*
 * Copyright 2008 Fred Sauer
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
package com.google.gwt.gen2.logging.handler.client;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

/**
 * Popup window used for logging. Note, currently only tested for IE.
 */
class WindowPopup {

  private static native void clear(JavaScriptObject w)
  /*-{
    w.document.body.innerHTML = "";
  }-*/;

  private static native void close(JavaScriptObject window)
  /*-{
    window.close();
  }-*/;

  private static native JavaScriptObject open(String features)
  /*-{
    var w = $wnd.open("", "_blank", features);
    $wnd.focus();
    return w;
  }-*/;

  private static native String readyState(JavaScriptObject window)
  /*-{
    return window.document.readyState;
  }-*/;

  private static native String setTitle(JavaScriptObject window, String title)
  /*-{
    window.document.title = title;
  }-*/;

  private static native void wrapAndAppendHTML(JavaScriptObject window,
      String html) throws JavaScriptException
  /*-{
    var div = window.document.createElement('div');
    div.innerHTML = html;
    window.document.body.appendChild(div);
  }-*/;

  private String logHtml = "";

  private boolean ready = false;

  private final CloseHandler<Window> closeHandler = new CloseHandler<Window>() {

    public void onClose(CloseEvent<Window> event) {
      closeWindowIfOpen();
    }
  };

  private JavaScriptObject window;

  /**
   * Default constructor.
   */
  public WindowPopup() {
    Window.addCloseHandler(closeHandler);
  }

  /**
   * Adds the given html to the popup window
   * 
   * @param html
   */
  public void addHtml(String html) {
    logHtml += html;
    if (window == null) {
      openNewWindow();
    }
    logPendingHtml();
  }

  /**
   * Clears the current window.
   */
  public final void clear() {
    if (ready) {
      try {
        clear(window);
      } catch (RuntimeException e) {
        // ignore
      }
    }
  }

  private void closeWindowIfOpen() {
    if (window != null) {
      ready = false;
      close(window);
      window = null;
    }
  }

  private void logPendingHtml() {
    if (ready) {
      try {
        wrapAndAppendHTML(window, logHtml);
        logHtml = "";
      } catch (JavaScriptException e) {
        openNewWindow();
      }
    }
  }

  private void openNewWindow() {
    closeWindowIfOpen();
    window = open("");
    new Timer() {
      int counter = 0;

      @Override
      public void run() {
        try {
          if (counter++ > 100 || "complete".equals(readyState(window))) {
            setTitle(window, "[log] " + Window.getTitle());
            ready = true;
            cancel();
            logPendingHtml();
          }
        } catch (RuntimeException e) {
          window = null;
          cancel();
        }
      }
    }.scheduleRepeating(100);
  }
}
