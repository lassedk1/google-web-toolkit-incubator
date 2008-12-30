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

import com.google.gwt.event.shared.GwtEvent;

import java.util.Date;

/**
 * Represents a log event, in specific a {@link LogEvent} is triggered when
 * {@link Log} or a custom logger needs to output a log message.
 * 
 * When fired from {@link Log}, the event has no source, when fired from a
 * custom logger, the event should have that logger as its source.
 */
public class LogEvent extends GwtEvent<LogHandler> {
  private static Type<LogHandler> TYPE = new Type<LogHandler>();
  private String message;
  private Level level;
  private String category;
  private Throwable thrown;
  private Date time;

  /**
   * Constructor.
   */
  public LogEvent() {
  }

  /**
   * Constructor.
   * 
   * @param message the log message
   * @param level the log level
   * @param category the log category
   * @param thrown the exception/error thrown
   * @param time time when the message was reported
   */
  public LogEvent(String message, Level level, String category,
      Throwable thrown, Date time) {
    this.setMessage(message);
    this.level = level;
    this.category = category;
    this.thrown = thrown;
  }

  /**
   * Gets the category of the event. The category uses "." to denote
   * sub-categories.
   * 
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * Gets the {@link Level} of the event.
   * 
   * @return the level
   */
  public Level getLevel() {
    return level;
  }

  /**
   * Gets the current message.
   * 
   * @return the message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Gets the thrown exception or error, returns null if none exists.
   * 
   * @return the exception or error
   */
  public Throwable getThrown() {
    return thrown;
  }

  /**
   * Gets the time this message was logged.
   * 
   * @return the time
   */
  public Date getTime() {
    return time;
  }

  /**
   * Copies the current log event. The copy will always be live and never
   * garbage collected.
   * 
   * @return a copy of the current log event
   */
  public LogEvent saveCopy() {
    return new LogEvent(message, level, category, thrown, time);
  }

  /**
   * Sets the category of the event. The category uses "." to denote
   * sub-categories.
   * 
   * @param category the category
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * Sets the {@link Level} of this event.
   * 
   * @param level
   */
  public void setLevel(Level level) {
    this.level = level;
  }

  /**
   * Sets the message associated with this event.
   * 
   * @param message the message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Sets the thrown error or exception, if one exists.
   * 
   * @param thrown the thrown error or exception
   */
  public void setThrown(Throwable thrown) {
    this.thrown = thrown;
  }

  /**
   * Sets the time when this message was logged.
   * 
   * @param time the time
   */
  public void setTime(Date time) {
    this.time = time;
  }

  @Override
  protected void dispatch(LogHandler handler) {
    handler.onLog(this);
  }

  @Override
  protected Type<LogHandler> getAssociatedType() {
    return TYPE;
  }

}
