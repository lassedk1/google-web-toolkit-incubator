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

package com.google.gwt.gen2.logging.shared;

/**
 * A {@link LogHandler} decorator which is sensitive to log levels and lazily
 * creates the underlying log handler.
 * 
 */
public abstract class SmartLogHandler implements LogHandler {
  private Level logLevel;
  private LogHandler wrapped;

  /**
   * Constructor.
   */
  public SmartLogHandler() {
  }

  /**
   * Get the log level specifying which messages will be logged by this
   * <tt>Handler</tt>. Message levels lower than this level will be discarded.
   * 
   * @return the level of messages being logged.
   */
  public Level getLevel() {
    if (logLevel == null) {
      return Level.ALL;
    }
    return logLevel;
  }

  public void onLog(LogEvent event) {
    if (getLevel().wouldLog(event)) {
      if (wrapped == null) {
        ensureLogHandler();
        assert wrapped != null : "You must can setLogHandler before or after ensureLogHandler() is called";
      }
      wrapped.onLog(event);
    }
  }

  /**
   * Set the log level specifying which message levels will be logged by this
   * <tt>Handler</tt>. Message levels lower than this value will be discarded.
   * <p>
   * The intention is to allow developers to turn on voluminous logging, but to
   * limit the messages that are sent to certain <tt>Handlers</tt>.
   * 
   * @param newLevel the new value for the log level
   * 
   */
  public void setLevel(Level newLevel) {
    logLevel = newLevel;
  }

  /**
   * Sets the wrapped log handler.
   * 
   * @param wrapped the wrapped handler
   */
  public void setLogHandler(LogHandler wrapped) {
    this.wrapped = wrapped;
  }

  /**
   * Called when the underlying log handler needs to be accessed. User code
   * should ensure that {@link SmartLogHandler#setLogHandler(LogHandler)} has
   * been called at least once during this method.
   * 
   */
  protected abstract void ensureLogHandler();

}
