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

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.logging.impl.shared.LogImpl;
import com.google.gwt.gen2.logging.impl.shared.ProxyServerBindingsImpl;

import java.util.Iterator;

/**
 * This class is very loosely based Java's Logger class. It is static to allow
 * differed binding to remove logging information in production code.
 * <p>
 * In order to use logging you must include one of the following gwt.xml modules
 * <table border = "1px">
 * <tr>
 * <td>com.google.gwt.gen2.logging.Logging</td>
 * <td>Enables logging.<br/>
 * The gwt.logging property is set to 'enabled'</td>
 * </tr>
 * <tr>
 * <td>com.google.gwt.gen2.logging.Logging-disabled</td>
 * <td>Disables all logging, but allows the source code to compile.<br/>
 * The gwt.logging property is set to 'disabled'</td>
 * </tr>
 * <tr>
 * <td>com.google.gwt.gen2.logging.Logging-production</code></td>
 * <td>Allows only severe error messages to be logged. <br/>
 * The gwt.logging property is set to 'production'</td>
 * </tr>
 * </table>
 * <p>
 * A default logger is always installed in hosted mode and is installed in web
 * mode when logging is set to "enabled".
 * 
 * In "production" mode, you must add a log handler manually to see your log
 * messages.
 * </p>
 */

public class Log {
  /**
   * Category used for logging messages.
   */
  public static final String CATEGORY = "gwt.logging";
  private static LogImpl impl;

  static {
    if (GWT.isClient()) {
      impl = GWT.create(LogImpl.class);
    } else {
      impl = ProxyServerBindingsImpl.getLogImpl();
    }
  }

  /**
   * Add a log handler to receive all logging messages. Use a
   * {@link SmartLogHandler} to filter messages by handler.
   * 
   * @param handler the new handler
   * 
   */
  public static void addLogHandler(LogHandler handler) {
    impl.addLogHandler(handler);
  }

  /**
   * Clear all handlers, including the default handler if it exists.
   */
  public static void clearLogHandlers() {
    impl.clearLogHandlers();
  }

  /**
   * Log a {@link Level#CONFIG} message.
   * 
   * @param message the message
   */
  public static void config(String message) {
    config(message, null);
  }

  /**
   * Log a {@link Level#CONFIG} message. The message category uses "." to denote
   * sub-categories.
   * 
   * @param message the message
   * @param category the message category
   */
  public static void config(String message, String category) {
    impl.config(message, category);
  }

  /**
   * Log a {@link Level#FINE} message.
   * 
   * @param message the message
   */
  public static void fine(String message) {
    fine(message, null);
  }

  /**
   * Log a {@link Level#FINE} message. The message category uses "." to denote
   * sub-categories.
   * 
   * @param message the message param category the message
   * @param category the message category
   */
  public static void fine(String message, String category) {
    impl.fine(message, category);
  }

  /**
   * Log a {@link Level#FINER} message.
   * 
   * @param message the message
   */
  public static void finer(String message) {
    finer(message, null);
  }

  /**
   * Log a {@link Level#FINER} message. The message category uses "." to denote
   * sub-categories.
   * 
   * @param message the message param category the message category
   * @param category the message category
   */
  public static void finer(String message, String category) {
    impl.finer(message, category);
  }

  /**
   * Log a {@link Level#FINEST} message.
   * 
   * @param message the message
   */
  public static void finest(String message) {
    finest(message, null);
  }

  /**
   * Log a {@link Level#FINEST} message. The message category uses "." to denote
   * sub-categories.
   * 
   * @param message the message param category the message category
   * @param category the message category
   */
  public static void finest(String message, String category) {
    impl.finest(message, category);
  }

  /**
   * Get the default log level needed to publish messages. This can be
   * overridden by level settings per category.
   * 
   * @return this Logger's level
   */
  public static Level getDefaultLevel() {
    return impl.getDefaultLevel();
  }

  /**
   * Log an {@link Level#INFO} message.
   * 
   * @param message the message
   */
  public static void info(String message) {
    info(message, null);
  }

  /**
   * Log an {@link Level#INFO}message. The message category uses "." to denote
   * sub-categories.
   * 
   * @param message the message param category the message category
   * @param category the message category
   */
  public static void info(String message, String category) {
    impl.info(message, category);
  }

  /**
   * Installs an uncaught exception handler that logs messages under the
   * "uncaught" category.
   */
  public static final void installUncaughtExceptionHandler() {
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable e) {
        Log.severe(e.getMessage(), "uncaught", e);
      }
    });
  }

  /**
   * Check if a message of the given level would actually be logged by this
   * logger. This check is based on the Loggers effective level, which may be
   * inherited from its parent.
   * 
   * @param level a message logging level
   * @return true if the given message level is currently being logged.
   */
  public static boolean isLoggable(Level level) {
    return impl.isLoggable(level);
  }

  /**
   * Is any form of Logging enabled?
   * 
   * @return whether logging is enabled
   */
  @Deprecated
  public static boolean isLoggingEnabled() {
    return impl.isLoggingEnabled();
  }

  /**
   * Is the logging system in minimal mode? i.e. only severe logging messages
   * are logged.
   * 
   * @deprecated use isLoggingProduction instead.
   * @return whether logging is minimal
   */
  @Deprecated
  public static boolean isLoggingMinimal() {
    return impl.isLoggingProduction();
  }

  /**
   * Is the logging system in production mode? i.e. only severe logging messages
   * are logged.
   * <p>
   * To set logging to production mode, include the following line in your gwt
   * module file. <br/>
   * <code class = "code"> &lt;set-property name="gwt.logging"
   * value="production"&gt;</code> <br/>
   * or include the gwt module
   * <code> com.google.gwt.gen2.logging.Logging-production</code>.
   * </p>
   * 
   * @return whether logging is production
   */
  public static boolean isLoggingProduction() {
    return impl.isLoggingProduction();
  }

  /**
   * Is any form of Logging supported?
   * 
   * @deprecated use logging enabled instead
   * @return whether logging is supported
   */
  @Deprecated
  public static boolean isLoggingSupported() {
    return impl.isLoggingEnabled();
  }

  /**
   * Returns an iterator of all currently defined levels.
   * 
   * @return level iterator
   */
  public static Iterator<Level> levelIterator() {
    return impl.levelIterator();
  }

  /**
   * Logs a message using a given level. This method cannot be removed in
   * minimal logging mode, so use it only if you cannot use one of the static
   * logging messages. The message category uses "." to denote sub-categories.
   * 
   * @param message the message
   * @param level the message level
   * @param category the message category
   * @param e the thrown exception or error
   */

  public static void log(String message, Level level, String category,
      Throwable e) {
    impl.log(message, level, category, e);
  }

  /**
   * Remove a log handler.
   * <p>
   * Returns silently if the given handler is not found or is null.
   * </p>
   * 
   * @param handler the log handler to remove
   */
  public static void removeLogHandler(LogHandler handler) {
    impl.removeLogHandler(handler);
  }

  /**
   * Set the default log level specifying which message levels will be logged by
   * this logger.
   * 
   * @param newLevel the new default log level
   */
  public static void setDefaultLevel(Level newLevel) {
    impl.setDefaultLevel(newLevel);
  }

  /**
   * Sets the level of a given logging category. This level overrides the
   * default logging level. The message category uses "." to denote
   * sub-categories.
   * 
   * @param category the category
   * @param level the category's level
   */
  public static void setLevel(String category, Level level) {
    impl.setLevel(category, level);
  }

  /**
   * Log a {@link Level#SEVERE} message.
   * <p>
   * Only severe messages are logged in production mode. To set logging to
   * production mode include the following line in your gwt module file. <pre
   * class = "code"> &lt;set-property name="gwt.logging"
   * value="production"&gt;</pre>
   * </p>
   * 
   * @param message The string message
   */
  public static void severe(String message) {
    severe(message, null);
  }

  /**
   * Log a {@link Level#SEVERE} message. The message category uses "." to denote
   * sub-categories.
   * <p>
   * Only severe messages are logged in production mode. To set logging to
   * production mode include the following line in your gwt module file. <pre
   * class = "code"> &lt;set-property name="gwt.logging"
   * value="production"&gt;</pre>
   * </p>
   * 
   * @param message the message
   * @param category the category
   */
  public static void severe(String message, String category) {
    severe(message, category, null);
  }

  /**
   * Log a {@link Level#SEVERE} message. The message category uses "." to denote
   * sub-categories.
   * 
   * <p>
   * Only severe messages are logged in production mode. To set logging to
   * production mode include the following line in your gwt module file.
   * 
   * <pre class = "code"> &lt;set-property name="gwt.logging"
   * value="production"&gt;</pre>
   * </p>
   * 
   * @param message the message
   * @param category the message category
   * @param throwable the associated error or exception
   */
  public static void severe(String message, String category, Throwable throwable) {
    impl.severe(message, category, throwable);
  }

  /**
   * Log a {@link Level#WARNING} message.
   * 
   * <p>
   * Warning message are <b> not </b> logging in production mode. Use
   * {@link #severe(String)} if you want to log a message in a normal production
   * environment.
   * </p>
   * 
   * @param message The string message
   */
  public static void warning(String message) {
    warning(message, null);
  }

  /**
   * Log a {@link Level#WARNING} message. The message category uses "." to
   * denote sub-categories.
   * 
   * <p>
   * Warning message are <b> not </b> logging in production mode. Use
   * {@link #severe(String,String)} if you want to log a message in a normal
   * production environment.
   * </p>
   * 
   * @param message the message
   * @param category the message category
   */
  public static void warning(String message, String category) {
    warning(message, category, null);
  }

  /**
   * Log a {@link Level#WARNING} message. The message category uses "." to
   * denote sub-categories.
   * <p>
   * Warning message are <b> not </b> logging in production mode. Use
   * {@link #severe(String,String,Throwable)} if you want to log a message in a
   * normal production environment.
   * </p>
   * 
   * @param message the message
   * @param category the message category
   * @param throwable the associated error or exception
   */
  public static void warning(String message, String category,
      Throwable throwable) {
    impl.warning(message, category, throwable);
  }

  // Logging setup, will be called automatically by level's clinit.
  static void init() {
    impl.init();
  }

  static Level parseLevel(String levelName) {
    return impl.parse(levelName);
  }

  static void registerLevel(Level level) {
    impl.registerLevel(level);
  }
}