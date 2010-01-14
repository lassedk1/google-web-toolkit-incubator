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

import com.google.gwt.gen2.logging.shared.Level;
import com.google.gwt.gen2.logging.shared.LogEvent;
import com.google.gwt.gen2.logging.shared.LogHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Default manager for logging.
 */
class LogManager {
  private HashMap<String, Level> levels = new HashMap<String, Level>();
  private HashMap<String, Level> categoryLevels = null;
  private ArrayList<LogHandler> handlers = new ArrayList<LogHandler>();
  private LogEvent event = new LogEvent();
  private Level globalLevel;

  public LogManager() {
  }

  public void addLogHandler(LogHandler handler) {
    handlers.add(handler);
  }

  public void clearLogHandlers() {
    handlers.clear();
  }

  public void config(String msg, String category) {
    log(Level.CONFIG, msg, category);
  }

  public void fine(String msg, String category) {
    log(Level.FINE, msg, category);
  }

  public void finer(String msg, String category) {
    log(Level.FINER, msg, category);
  }

  public void finest(String msg, String category) {
    log(Level.FINEST, msg, category);
  }

  public Level getLevel() {
    if (globalLevel == null) {
      globalLevel = Level.INFO;
      assert (globalLevel != null);
    }
    return globalLevel;
  }

  public void info(String msg, String category) {
    log(Level.INFO, msg, category);
  }

  public boolean isLoggable(Level level) {
    return allows(getLevel(), level);
  }

  public boolean isLoggingMinimal() {
    return false;
  }

  public boolean isLoggingSupported() {
    return true;
  }

  public Iterator<Level> levelIterator() {
    ArrayList<Level> accum = new ArrayList<Level>();
    accum.addAll(levels.values());
    Collections.sort(accum);
    return accum.iterator();
  }

  public void log(Level level, String msg, String category, Throwable e) {
    if (isLoggable(level, category)) {
      event.setCategory(category);
      event.setThrown(e);
      event.setLevel(level);
      event.setTime(new Date());
      event.setMessage(msg);
      for (int i = 0; i < handlers.size(); i++) {
        LogHandler handler = handlers.get(i);
        handler.onLog(event);
      }
    }
  }

  public Level parse(String levelName) {
    Level value = null;
    if (levels != null) {
      value = levels.get(levelName);
    }
    if (value == null) {
      throw new IllegalArgumentException(levelName + " is not a known Level");
    }
    return value;
  }

  public void registerLevel(Level level) {
    levels.put(level.getName(), level);
  }

  public void removeLogHandler(LogHandler handler) {
    handlers.remove(handler);
  }

  public void setDefaultLevel(Level level) {
    if (level == null) {
      throw new IllegalArgumentException("Cannot set global level to null");
    }
    globalLevel = level;
  }

  public void setLevel(String category, Level level) {
    if (categoryLevels == null) {
      categoryLevels = new HashMap<String, Level>();
    }
    categoryLevels.put(category, level);
  }

  public void setLevels(HashMap<String, Level> levels) {
    this.levels = levels;
  }

  public void severe(String msg, String category, Throwable e) {
    log(Level.SEVERE, msg, category, e);
  }

  public void warning(String msg, String category, Throwable t) {
    log(Level.WARNING, msg, category, t);
  }

  protected boolean allows(Level parent, Level child) {
    return parent.intValue() <= child.intValue();
  }

  protected Level getLevelForCategory(String category) {
    Level obj = categoryLevels.get(category);
    if (obj != null) {
      return obj;
    }
    int index = category.lastIndexOf(".");
    if (index == -1) {
      return globalLevel;
    }
    return getLevelForCategory(category.substring(0, index));
  }

  protected boolean isLoggable(Level level, String category) {
    if (category == null || categoryLevels == null) {
      return isLoggable(level);
    }
    return allows(getLevelForCategory(category), level);
  }

  private void log(Level level, String message, String category) {
    log(level, message, category, null);
  }

}
