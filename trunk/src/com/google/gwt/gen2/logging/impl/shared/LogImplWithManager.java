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

package com.google.gwt.gen2.logging.impl.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.logging.shared.DefaultClientLogHandler;
import com.google.gwt.gen2.logging.shared.Level;
import com.google.gwt.gen2.logging.shared.Log;
import com.google.gwt.gen2.logging.shared.LogHandler;

import java.util.Iterator;

/**
 * A {@link LogImpl} implementation that delegates all the methods to the
 * underlying log manager
 */
public class LogImplWithManager extends LogImpl {

  private static class AvoidLogClInit {
    static LogManager manager = new LogManager();
    static {
      // Triggers Level clinit in order to call logimpl.init.
      if (Level.ALL == null) {
        // Do nothing, this is true the first time through
      }
    }
  }

  protected static void addDefaultLogHandler() {
    LogHandler handler = GWT.create(DefaultClientLogHandler.class);
    assert handler != null : "The default log handler must be initialized.";
    Log.addLogHandler(handler);
  }

  protected static LogManager getManager() {
    return AvoidLogClInit.manager;
  }

  public void addLogHandler(LogHandler handler) {
    getManager().addLogHandler(handler);
  }

  public void clearLogHandlers() {
    getManager().clearLogHandlers();
  }

  public void config(String msg, String category) {
    getManager().config(msg, category);
  }

  public void fine(String msg, String category) {
    getManager().fine(msg, category);
  }

  public void finer(String msg, String category) {
    getManager().finer(msg, category);
  }

  public void finest(String msg, String category) {
    getManager().finest(msg, category);
  }

  public Level getDefaultLevel() {
    return getManager().getLevel();
  }

  public void info(String msg, String category) {
    getManager().info(msg, category);
  }

  @Override
  public void init() {
  }

  public boolean isLoggable(Level level) {
    return getManager().isLoggable(level);
  }

  public boolean isLoggingMinimal() {
    return false;
  }

  public boolean isLoggingSupported() {
    return true;
  }

  public Iterator levelIterator() {
    return getManager().levelIterator();
  }

  public void log(String msg, Level level, String category, Throwable t) {
    getManager().log(level, msg, category, t);
  }

  public Level parse(String levelName) {
    return getManager().parse(levelName);
  }

  public void registerLevel(Level level) {
    getManager().registerLevel(level);
  }

  public void removeLogHandler(LogHandler handler) {
    getManager().removeLogHandler(handler);
  }

  public void setDefaultLevel(Level level) {
    getManager().setDefaultLevel(level);
  }

  public void setLevel(String category, Level level) {
    getManager().setLevel(category, level);
  }

  public void severe(String msg, String category, Throwable t) {
    getManager().severe(msg, category, t);
  }

  public String toString() {
    return getManager().toString();
  }

  public void warning(String msg, String category, Throwable t) {
    getManager().warning(msg, category, t);
  }

  protected void initializeLevels() {
    Level.OFF = new Level("OFF", Integer.MAX_VALUE);
    Level.SEVERE = new Level("SEVERE", 1000);
    Level.WARNING = new Level("WARNING", 900);
    Level.INFO = new Level("INFO", 800);
    Level.CONFIG = new Level("CONFIG", 700);
    Level.FINE = new Level("FINE", 500);
    Level.FINER = new Level("FINER", 400);
    Level.FINEST = new Level("FINEST", 300);
    Level.ALL = new Level("ALL", Integer.MIN_VALUE);
  }

}
