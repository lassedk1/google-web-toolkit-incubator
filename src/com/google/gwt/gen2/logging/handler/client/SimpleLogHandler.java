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

package com.google.gwt.gen2.logging.handler.client;

import com.google.gwt.gen2.logging.shared.LogEvent;
import com.google.gwt.gen2.logging.shared.LogFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * A simple log handler to display log messages.
 */
public class SimpleLogHandler extends ScrollableLogHandler<VerticalPanel> {
  private LogFormatter formatter;

  /**
   * Constructor.
   * 
   */
  public SimpleLogHandler() {
    this(LogFormatter.HTML);
  }

  /**
   * Constructor.
   * 
   * @param formatter log formatter to use
   */
  public SimpleLogHandler(LogFormatter formatter) {
    super("gwt-SimpleLogHandler");
    this.formatter = formatter;
  }

  public void onLog(LogEvent event) {
    Label message;

    if (formatter.isHtml()) {
      message = new HTML(formatter.format(event));
    } else {
      message = new Label(formatter.format(event));
    }
    getContents().add(message);
  }

  /**
   * Creates the internal panel's contents.
   */
  @Override
  protected VerticalPanel createContents() {
    return new VerticalPanel();
  }

}
