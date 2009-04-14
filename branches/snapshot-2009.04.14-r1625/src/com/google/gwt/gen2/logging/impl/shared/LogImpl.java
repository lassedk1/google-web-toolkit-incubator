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
import com.google.gwt.gen2.logging.shared.LogHandler;

import java.util.Iterator;

/**
 * Empty log implementation.
 */
public abstract class LogImpl {

  public abstract void addLogHandler(LogHandler handler);

  public abstract void clearLogHandlers();

  public abstract void config(String msg, String category);

  public abstract void fine(String msg, String category);

  public abstract void finer(String msg, String category);

  public abstract void finest(String msg, String category);

  public abstract Level getDefaultLevel();

  public abstract void info(String msg, String category);

  public abstract void init();

  public abstract boolean isLoggable(Level level);

  public abstract boolean isLoggingEnabled();

  public abstract boolean isLoggingProduction();

  public abstract Iterator<Level> levelIterator();

  public abstract void log(String msg, Level level, String category, Throwable e);

  public abstract Level parse(String levelName);

  public abstract void registerLevel(Level level);

  public abstract void removeLogHandler(LogHandler handler);

  public abstract void setDefaultLevel(Level newLevel);

  public abstract void setLevel(String category, Level level);

  public abstract void severe(String msg, String category, Throwable t);

  public abstract void warning(String msg, String category, Throwable t);
}
