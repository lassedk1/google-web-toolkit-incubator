/*
 * Copyright 2009 Google Inc.
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

package com.google.gwt.gen2.logging.client;

import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.gen2.logging.handler.shared.ListLogHandler;
import com.google.gwt.gen2.logging.shared.Level;
import com.google.gwt.gen2.logging.shared.Log;
import com.google.gwt.gen2.logging.shared.SmartLogHandler;

import java.util.Iterator;

/**
 * Test Client side logging.
 */
public class LogClientTest extends Gen2TestBase {

  public void testDefaultLogger() {
    // Test that any log message works.
    Log.severe("I am a test log message, please ignore me");

    // Test that printing an exception does not cause problems
    Log.severe("I am still a test log message, now with a stack trace:"
        + new NullPointerException().toString());
    // Test that a log message escapes html correctly.
    Log.severe("<I am not html>!@#$%^&*()_+");
  }

  public void testBasicLogger() {
    Log.clearLogHandlers();
    Log.setDefaultLevel(Level.INFO);

    final ListLogHandler handler = new ListLogHandler();
    Log.addLogHandler(handler);

    // Check set up
    assertEquals(Level.INFO, Log.getDefaultLevel());

    // Check level iterator.
    Iterator<Level> iterator = Log.levelIterator();
    assertEquals(Level.ALL, iterator.next());
    assertEquals(Level.FINEST, iterator.next());
    assertEquals(Level.FINER, iterator.next());

    // Basic Test.
    Log.severe("severe");
    assertEquals("severe", getLastMessage(handler));
    Log.finest("finest");
    assertEquals("severe", getLastMessage(handler));

    // Test setting the log level.

    // On
    Log.setDefaultLevel(Level.FINEST);
    Log.finest("finest");
    assertEquals("finest", getLastMessage(handler));

    // Off
    Log.setDefaultLevel(Level.OFF);
    Log.severe("going into the void");
    assertEquals("finest", getLastMessage(handler));

    // Test category filters.
    String cParent = "com.mycompany";
    String cChild1 = "com.mycompany.package1";
    String cChild2 = "com.mycompany.package2";

    Log.setDefaultLevel(Level.CONFIG);
    Log.config("config", cChild1);
    assertEquals("config", getLastMessage(handler));
    assertEquals(cChild1, handler.getLast().getCategory());
    Log.setDefaultLevel(Level.WARNING);
    Log.config("bad", cChild1);
    assertEquals("config", getLastMessage(handler));
    Log.setLevel(cParent, Level.CONFIG);
    Log.setLevel(cChild2, Level.INFO);

    Log.config("log", cChild1);
    Log.config("don't log", cChild2);
    assertEquals("log", getLastMessage(handler));

    // Test handler filters.
    Log.removeLogHandler(handler);
    SmartLogHandler smart = new SmartLogHandler() {

      @Override
      protected void ensureLogHandler() {
        setLogHandler(handler);
      }

    };
    Log.severe("handler should be removed");
    assertEquals("log", getLastMessage(handler));

    Log.addLogHandler(smart);
    Log.warning("log me");
    smart.setLevel(Level.OFF);
    Log.warning("don't");
    assertEquals("log me", getLastMessage(handler));
    smart.setLevel(Level.ALL);

    // Test removing logger.
    Log.severe("hello");
    assertEquals("hello", getLastMessage(handler));
    Log.removeLogHandler(smart);
    Log.severe("don't log");
    assertEquals("hello", getLastMessage(handler));
  }

  private String getLastMessage(ListLogHandler handler) {
    return handler.getLast().getMessage();
  }

}
