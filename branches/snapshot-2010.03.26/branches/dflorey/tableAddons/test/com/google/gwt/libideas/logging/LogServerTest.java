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

package com.google.gwt.libideas.logging;

import com.google.gwt.libideas.logging.server.ServerLogManager;
import com.google.gwt.libideas.logging.shared.ArrayListLogHandler;
import com.google.gwt.libideas.logging.shared.Level;
import com.google.gwt.libideas.logging.shared.Log;

import junit.framework.TestCase;

import java.util.logging.Logger;

/**
 * We test logging using junit as it is much faster that way.
 */
public class LogServerTest extends TestCase {

  public void testBasicLogger() {
    ServerLogManager.useBasicLogging();

    ArrayListLogHandler handler = new ArrayListLogHandler();
    Log.addLogHandler(handler);

    // Basic Test.
    Log.severe("severe");
    assertEquals("severe", handler.getLastMessage());
    Log.finest("finest");
    assertEquals("severe", handler.getLastMessage());

    // Test setting the log level.

    // On
    Log.setDefaultLevel(Level.FINEST);
    Log.finest("finest");
    assertEquals("finest", handler.getLastMessage());

    // Off
    Log.setDefaultLevel(Level.OFF);
    Log.severe("going into the void");
    assertEquals("finest", handler.getLastMessage());

    // Test category filters.
    String cParent = "com.mycompany";
    String cChild1 = "com.mycompany.package1";
    String cChild2 = "com.mycompany.package2";

    Log.setDefaultLevel(Level.CONFIG);
    Log.config("config", cChild1);
    assertEquals("config", handler.getLastMessage());
    assertEquals(cChild1, handler.getLastCategory());
    Log.setDefaultLevel(Level.WARNING);
    Log.config("bad", cChild1);
    assertEquals("config", handler.getLastMessage());
    Log.setLevel(cParent, Level.CONFIG);
    Log.setLevel(cChild2, Level.INFO);

    Log.config("log", cChild1);
    Log.config("don't log", cChild2);
    assertEquals("log", handler.getLastMessage());

    // Test handler filters.
    Log.warning("log me");
    handler.setLevel(Level.OFF);
    Log.warning("don't");
    assertEquals("log me", handler.getLastMessage());
    handler.setLevel(Level.ALL);

    // Test removing loggier.
    Log.severe("hello");
    assertEquals("hello", handler.getLastMessage());
    Log.removeLogHandler(handler);
    Log.severe("don't log");
    assertEquals("hello", handler.getLastMessage());
  }

  /**
   * For now, this is a very basic visual test. Later we can try to make it more
   * robust.
   */
  public void testJavaLogging() {

    // For now, (visual test)
    ServerLogManager.useJavaLogging();
    Logger.getLogger("gwt").getParent().getHandlers()[0].setLevel(java.util.logging.Level.ALL);

    int count = 0;
    // Basic Test.
    Log.severe(++count + "show(severe)");

    Log.finest("don't show");

    // Test setting the log level.

    // On
    Log.setDefaultLevel(Level.FINEST);
    Log.finest(++count + "show2(finest)");

    // Off
    Log.setDefaultLevel(Level.OFF);
    Log.severe("don't show");
  }
}
