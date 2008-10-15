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

import com.google.gwt.gen2.logging.handler.client.LogHandlerUtil;

/**
 * Formatter used to format logging messages.
 */
public abstract class LogFormatter {

  /**
   * Default text log formatter.
   */
  public static LogFormatter TEXT = new LogFormatter() {
    @Override
    public String format(LogEvent event) {
      StringBuffer accum = new StringBuffer(event.getLevel().getName());
      if (event.getCategory() != null) {
        accum.append("-" + event.getCategory());
      }
      accum.append("-" + event.getTime());
      accum.append("\n" + event.getMessage());
      if (event.getThrown() != null) {
        accum.append("\n thrown: " + event.getThrown());
      }
      return accum.toString();
    }

    @Override
    public boolean isHtml() {
      return false;
    }
  };

  /**
   * Default html log formatter.
   */
  public static LogFormatter HTML = new LogFormatter() {
    @Override
    public String format(LogEvent event) {
      String baseMessage = escapeText(event.getMessage());
      String title = "level: " + event.getLevel();
      String category = event.getCategory();
      if (category != null) {
        title += "  category: " + event.getCategory();
      }
      title += "  time: " + event.getTime();
      if (event.getThrown() != null) {
        title += "  thrown: " + event.getThrown();
      }

      String style = LogHandlerUtil.getStyle(event.getLevel());
      String htmlMessage = "<code class='" + style + "'" + "title='" + title
          + "'>" + baseMessage + "</code>";
      return htmlMessage;
    }

    @Override
    public boolean isHtml() {
      return true;
    }

  };

  /**
   * Escapes the given text so it can be displayed as html.
   * 
   * @param text the text to escape
   * @return the escaped text
   */
  // TODO(ecc) check for other chars to escape.
  public static String escapeText(String text) {
    text = text.replaceAll("<", "&lt;");
    text = text.replaceAll(">", "&gt;");
    return text;
  }

  /**
   * Formats the given log event.
   * 
   * @param event the event
   * @return the formatted string
   */
  public abstract String format(LogEvent event);

  /**
   * Is the resulting formatted string in html format?
   * 
   * @return is html
   */
  public abstract boolean isHtml();
}
