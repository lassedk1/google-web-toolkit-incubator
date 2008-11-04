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
 * A default logger is always installed in hosted mode and is installed in web
 * mode when logging is set to "all". In "minimal" mode, you must add a log
 * handler manually to see your log messages.
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
   * Is the logging system in minimal mode? i.e. only severe logging messages
   * are logged.
   * 
   * @return whether logging is minimal
   */
  public static boolean isLoggingMinimal() {
    return impl.isLoggingMinimal();
  }

  /**
   * Is any form of Logging supported?
   * 
   * @return whether logging is supported
   */
  public static boolean isLoggingSupported() {
    return impl.isLoggingSupported();
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
   * 
   * @param message The string message
   */
  public static void severe(String message) {
    severe(message, null);
  }

  /**
   * Log a {@link Level#SEVERE} message. The message category uses "." to denote
   * sub-categories.
   * 
   * param category the message category
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
   * @param message The string message
   */
  public static void warning(String message) {
    warning(message, null);
  }

  /**
   * Log a {@link Level#WARNING} message. The message category uses "." to
   * denote sub-categories.
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

  /**
   * Installs an uncaught exception handler that logs messages under the
   * "uncaught" category.
   */
  public final void installUncaughtExceptionHandler() {
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable e) {
        Log.severe(e.getMessage(), "uncaught", e);
      }
    });
  }
}