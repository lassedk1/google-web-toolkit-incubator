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

import java.io.Serializable;

/**
 * This is a stripped-down clone of the java level class. It does not support
 * all the functionality of java level. In specific, there is no localization
 * support for names.
 */

public class Level implements Comparable, Serializable {

  /**
   * OFF is a special level that can be used to turn off logging. This level is
   * initialized to <CODE>Integer.MAX_VALUE</CODE>.
   */
  public static Level OFF;

  /**
   * SEVERE is a message level indicating a serious failure.
   * <p>
   * In general SEVERE messages should describe events that are of considerable
   * importance and which will prevent normal program execution. They should be
   * reasonably intelligible to end users and to system administrators. This
   * level is initialized to <CODE>1000</CODE>.
   */
  public static Level SEVERE;

  /**
   * WARNING is a message level indicating a potential problem.
   * <p>
   * In general WARNING messages should describe events that will be of interest
   * to end users or system managers, or which indicate potential problems. This
   * level is initialized to <CODE>900</CODE>.
   */
  public static Level WARNING;

  /**
   * INFO is a message level for informational messages.
   * <p>
   * Typically INFO messages will be written to the console or its equivalent.
   * So the INFO level should only be used for reasonably significant messages
   * that will make sense to end users and system admins. This level is
   * initialized to <CODE>800</CODE>.
   */
  public static Level INFO;

  /**
   * CONFIG is a message level for static configuration messages.
   * <p>
   * CONFIG messages are intended to provide a variety of static configuration
   * information, to assist in debugging problems that may be associated with
   * particular configurations. For example, CONFIG message might include the
   * CPU type, the graphics depth, the GUI look-and-feel, etc. This level is
   * initialized to <CODE>700</CODE>.
   */
  public static Level CONFIG;

  /**
   * FINE is a message level providing tracing information.
   * <p>
   * All of FINE, FINER, and FINEST are intended for relatively detailed
   * tracing. The exact meaning of the three levels will vary between
   * subsystems, but in general, FINEST should be used for the most voluminous
   * detailed output, FINER for somewhat less detailed output, and FINE for the
   * lowest volume (and most important) messages.
   * <p>
   * In general the FINE level should be used for information that will be
   * broadly interesting to developers who do not have a specialized interest in
   * the specific subsystem.
   * <p>
   * FINE messages might include things like minor (recoverable) failures.
   * Issues indicating potential performance problems are also worth logging as
   * FINE. This level is initialized to <CODE>500</CODE>.
   */
  public static Level FINE;

  /**
   * FINER indicates a fairly detailed tracing message. By default logging calls
   * for entering, returning, or throwing an exception are traced at this level.
   * This level is initialized to <CODE>400</CODE>.
   */
  public static Level FINER;

  /**
   * FINEST indicates a highly detailed tracing message. This level is
   * initialized to <CODE>300</CODE>.
   */
  public static Level FINEST;

  /**
   * ALL indicates that all messages should be logged. This level is initialized
   * to <CODE>Integer.MIN_VALUE</CODE>.
   */
  public static Level ALL;

  static {
    // This create a level clinit for level. Feedback from the community has
    // been that this is preferable then having level value be null until after
    // the first log call.
    Log.init();
  }

  /**
   * Return the correct level based on name.
   * 
   * @param levelName the name of the level
   * @return the parsed level
   * 
   * @throws IllegalArgumentException for unknown levels.
   */
  public static Level parse(String levelName) {
    return Log.parseLevel(levelName);
  }

  int value;

  String name;

  /**
   * Constructor used for serialization.
   */
  public Level() {
    name = null;
    value = -1;
  }

  /**
   * Create a named Level with a given integer value.
   * <p>
   * 
   * @param name the name of the Level, for example "SEVERE".
   * @param value an integer value for the level.
   * @throws NullPointerException if the name is null
   */
  public Level(String name, int value) {
    this.value = value;
    this.name = name;
    Log.registerLevel(this);
  }

  public int compareTo(Object o) {
    Level that = (Level) o;
    if (value < that.value) {
      return -1;
    } else if (value > that.value) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * Gets the name of the level.
   * 
   * @return the name of the level
   */
  public String getName() {
    return name;
  }

  /**
   * Generate a hash code.
   * 
   * @return a hash code based on the level value
   */
  @Override
  public int hashCode() {
    return this.value;
  }

  /**
   * Get the integer value for this level. This integer value can be used for
   * efficient ordering comparisons between Level objects.
   * 
   * @return the integer value for this level.
   */
  public final int intValue() {
    return value;
  }

  @Override
  public String toString() {
    return getName();
  }

  /**
   * Would the given event be logged at this level?
   * 
   * @param event the event
   * @return would the event be logged
   */
  public boolean wouldLog(LogEvent event) {
    return value <= event.getLevel().value;
  }

}
