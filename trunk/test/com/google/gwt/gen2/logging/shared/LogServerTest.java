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

import com.google.gwt.gen2.logging.handler.shared.ListLogHandler;

import junit.framework.TestCase;

import java.util.Iterator;

/**
 * We test logging using junit as it is much faster that way.
 */
public class LogServerTest extends TestCase {

  /**
   * For now, this is a very basic visual test. Later we can try to make it more
   * robust.
   */
  public void testJavaLogging() {
    // For now, (visual test)
    // TODO (ecc): Remove Logger reference or move this test out of shared
    // Logger.getLogger("gwt").getParent().getHandlers()[0].setLevel(java.util.logging.Level.ALL);
    // Logger.getLogger("gwt").setLevel(java.util.logging.Level.ALL);

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

  public void testBasicLogger() {
    Log.clearLogHandlers();
    Log.setDefaultLevel(Level.INFO);

    final ListLogHandler handler = new ListLogHandler();
    Log.addLogHandler(handler);

    // Check set up
    assertEquals(Level.INFO, Log.getDefaultLevel());

    // Check level iterator.
    Iterator iterator = Log.levelIterator();
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
