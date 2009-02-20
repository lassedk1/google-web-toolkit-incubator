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

package com.google.gwt.gen2.logging.handler.client;

import com.google.gwt.core.client.GWT; 
import com.google.gwt.gen2.logging.shared.DefaultClientLogHandler;
import com.google.gwt.gen2.logging.shared.Level;
import com.google.gwt.gen2.logging.shared.LogEvent;
import com.google.gwt.gen2.logging.shared.LogFormatter;
import com.google.gwt.gen2.logging.shared.LogHandler;
import com.google.gwt.gen2.logging.shared.SmartLogHandler;

/**
 * Client console logger. Chooses the best default client available depending
 * upon the current browser.
 * <dl>
 * <dt>Hosted mode</dt>
 * <dd>hosted mode logger</dd>
 * <dt>Internet Explorer</dt>
 * <dd>Popup window</dd>
 * <dt>Firefox</dt>
 * <dd>FireBug log. If you do not have firebug and you developing for firefox,
 * you should get it <a
 * href="https://addons.mozilla.org/en-US/firefox/addon/1843"> here </a>.</dd>
 * <dt>Safari/Chrome</dt>
 * <dd>built in console</dd>
 * <dt>Opera</dt>
 * <dd>window.opera.postError</dd>
 * </dl>
 * 
 * @deprecated as of feb 09, users should not directly use or modify
 *             ClientConsoleLogHandler themselves.
 */

@Deprecated
public class ClientConsoleLogHandler extends SmartLogHandler implements
    DefaultClientLogHandler {

  /**
   * Hosted mode console.
   */
  public static class Hosted implements LogHandler {
    public void onLog(LogEvent event) {
      // Does not use default text formatter because thrown is dealt with by the
      // hosted logger.
      String formatted = event.getLevel().toString();
      if (event.getCategory() != null) {
        formatted += "-" + event.getCategory();
      }
      GWT.log(formatted + ": " + event.getMessage(), event.getThrown());
    }
  }

  /**
   * Standard console, used by Safari and Chrome. Also is used by FireFox is
   * firebug is not detected. Because all firefox's support console, can get set
   * to the null logger in unusual cases.
   */
  static class Console implements Impl {
    public void init(ClientConsoleLogHandler handler) {
      if (!isSupported()) {
        handler.setLogHandler(new Null());
      }
    }

    public void onLog(LogEvent event) {
      log(LogFormatter.TEXT.format(event));
    }

    native void log(String message) /*-{
      $wnd.console.log(message);
    }-*/;

    private native boolean isSupported() /*-{
      return $wnd.console != null && typeof($wnd.console.log) == 'function';
    }-*/;
  }

  /**
   * Firebug console.
   */
  static class Firebug implements Impl {
    public void init(ClientConsoleLogHandler handler) {
      if (!isSupported()) {
        handler.setLogHandler(new Null());
      }
    }

    public void onLog(LogEvent event) {
      Level level = event.getLevel();
      String msg = LogFormatter.TEXT.format(event);
      int val = level.intValue();
      if (val <= Level.FINE.intValue()) {
        debug(msg);
      } else if (val < Level.WARNING.intValue()) {
        info(msg);
      } else if (val < Level.SEVERE.intValue()) {
        warn(msg);
      } else {
        error(msg);
      }
    }

    private native void debug(String message) /*-{
      $wnd.console.debug(message);
    }-*/;

    private native void error(String message) /*-{
      $wnd.console.error(message);
    }-*/;

    private native void info(String message) /*-{
      $wnd.console.info(message);
    }-*/;

    private native boolean isSupported() /*-{
      return $wnd.console != null && $wnd.console.firebug;
    }-*/;

    private native void warn(String message) /*-{
      $wnd.console.warn(message);
    }-*/;
  }

  static interface Impl extends LogHandler {
    void init(ClientConsoleLogHandler handler);
  }

  /**
   * Null console logger.
   */
  static class Null implements Impl {
    public void init(ClientConsoleLogHandler handler) {
    }

    public void onLog(LogEvent event) {
    }
  }

  /**
   * Opera implementation using <code>postError</code>.
   */
  static class Opera implements Impl {
    public void init(ClientConsoleLogHandler handler) {
    }

    public void onLog(LogEvent event) {
      log(LogFormatter.TEXT.format(event));
    }

    private native boolean log(String msg) /*-{
      $wnd.opera.postError(msg);
    }-*/;
  }

  /**
   * Popup Console, using s separate popup window. Note, tested only for
   * Internet Explorer.
   */
  static class Popup implements Impl {
    private static int CLEAR_AFTER = 200;
    WindowPopup window = new WindowPopup();
    int counter = 0;

    public void init(ClientConsoleLogHandler handler) {
    }

    public void onLog(LogEvent event) {
      // To prevent an infinitely growing log, the window is cleared
      // after 200 messages.
      if (CLEAR_AFTER == ++counter) {
        window.clear();
        counter = 0;
      }
      // Formatting as text so all console log handler outputs are as close as
      // practical.
      window.addHtml(LogFormatter.TEXT.format(event));
    }
  }

  /**
   * Constructor.
   */
  public ClientConsoleLogHandler() {
    // Do nothing, we do not want to the work of setting up the loggers until we
    // have to.
  }

  @Override
  protected void ensureLogHandler() {
    if (GWT.isScript()) {
      Impl impl = GWT.create(Impl.class);
      setLogHandler(impl);
      // Gives the impl the chance to replace itself.
      impl.init(this);
    } else {
      setLogHandler(new Hosted());
    }
  }

}
