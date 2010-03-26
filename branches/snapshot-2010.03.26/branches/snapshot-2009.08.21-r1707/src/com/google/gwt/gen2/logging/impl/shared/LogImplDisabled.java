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
 * Log impl used when logging is completely turned off.
 */
public class LogImplDisabled extends LogImpl {

  @Override
  public void addLogHandler(LogHandler handler) {
  }

  @Override
  public void clearLogHandlers() {
  }

  @Override
  public void config(String msg, String category) {
  }

  @Override
  public void fine(String msg, String category) {
  }

  @Override
  public void finer(String msg, String category) {
  }

  @Override
  public void finest(String msg, String category) {
  }

  @Override
  public Level getDefaultLevel() {
    return null;
  }

  @Override
  public void info(String msg, String category) {
  }

  @Override
  public void init() {
  }

  @Override
  public boolean isLoggable(Level level) {
    return false;
  }

  @Override
  public boolean isLoggingEnabled() {
    return false;
  }

  @Override
  public boolean isLoggingProduction() {
    return false;
  }

  @Override
  public Iterator<Level> levelIterator() {
    return null;
  }

  @Override
  public void log(String msg, Level level, String category, Throwable e) {
  }

  @Override
  public Level parse(String levelName) {
    return null;
  }

  @Override
  public void registerLevel(Level level) {
  }

  @Override
  public void removeLogHandler(LogHandler handler) {
  }

  @Override
  public void setDefaultLevel(Level newLevel) {
  }

  @Override
  public void setLevel(String category, Level level) {
  }

  @Override
  public void severe(String msg, String category, Throwable t) {
  }

  public String[] splitCategory(String category) {
    return null;
  }

  @Override
  public void warning(String msg, String category, Throwable t) {
  }

}
