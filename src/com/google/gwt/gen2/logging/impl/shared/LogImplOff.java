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
public class LogImplOff extends LogImpl {

  public void addLogHandler(LogHandler handler) {
  }

  public void clearLogHandlers() {
  }

  public void config(String msg, String category) {
  }

  public void fine(String msg, String category) {
  }

  public void finer(String msg, String category) {
  }

  public void finest(String msg, String category) {
  }

  public Level getDefaultLevel() {
    return null;
  }

  public void info(String msg, String category) {
  }

  @Override
  public void init() {
  }

  public boolean isLoggable(Level level) {
    return false;
  }

  public boolean isLoggingMinimal() {
    return false;
  }

  public boolean isLoggingSupported() {
    return false;
  }

  public Iterator levelIterator() {
    return null;
  }

  public void log(String msg, Level level, String category, Throwable e) {
  }

  public Level parse(String levelName) {
    return null;
  }

  public void registerLevel(Level level) {
  }

  public void removeLogHandler(LogHandler handler) {
  }

  public void setDefaultLevel(Level newLevel) {
  }

  public void setLevel(String category, Level level) {
  }

  public void severe(String msg, String category, Throwable t) {
  }

  public String[] splitCategory(String category) {
    return null;
  }

  public void warning(String msg, String category, Throwable t) {
  }

}
