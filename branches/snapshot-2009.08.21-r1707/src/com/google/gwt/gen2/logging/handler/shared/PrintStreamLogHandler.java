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

package com.google.gwt.gen2.logging.handler.shared;

import com.google.gwt.gen2.logging.shared.LogEvent;
import com.google.gwt.gen2.logging.shared.LogFormatter;
import com.google.gwt.gen2.logging.shared.LogHandler;

import java.io.PrintStream;

/**
 * Handler to print out to the standard console.
 */
public class PrintStreamLogHandler implements LogHandler {

  private PrintStream stream;

  /**
   * Constructor.
   * 
   * @param stream stream to print to
   */
  public PrintStreamLogHandler(PrintStream stream) {
    this.stream = stream;
  }

  public void onLog(LogEvent event) {
    stream.println(LogFormatter.TEXT.format(event));
  }
}
