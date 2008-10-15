/*******************************************************************************
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

package com.google.gwt.gen2.demo.logging.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.gen2.event.logical.shared.SelectionEvent;
import com.google.gwt.gen2.event.logical.shared.SelectionHandler;
import com.google.gwt.gen2.logging.handler.client.LogHandlerUtil;
import com.google.gwt.gen2.logging.handler.client.PopupLogHandler;
import com.google.gwt.gen2.logging.handler.client.RemoteLogHandler;
import com.google.gwt.gen2.logging.handler.client.SimpleLogHandler;
import com.google.gwt.gen2.logging.handler.client.GroupedLogHandler;
import com.google.gwt.gen2.logging.shared.Level;
import com.google.gwt.gen2.logging.shared.Log;
import com.google.gwt.gen2.logging.shared.LogFormatter;
import com.google.gwt.gen2.logging.shared.LogHandler;
import com.google.gwt.gen2.logging.shared.SmartLogHandler;
import com.google.gwt.gen2.selection.client.DropDownListBox;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LoggingDemo implements EntryPoint {
  class HandlerConfig extends FlowPanel {
    HandlerConfig(final String title, final SmartLogHandler handler) {
      Log.addLogHandler(handler);
      setStyleName("handler-control");

      add(new HTML("<h3>" + title + "</h3>"));

      final DropDownListBox<Level> levelList = new DropDownListBox("");
      Iterator<Level> levels = Log.levelIterator();
      while (levels.hasNext()) {
        final Level level = levels.next();
        levelList.addItem(level.getName(), level, "logging level "
            + level.getName().toLowerCase(), "handler level: <b> "
            + level.getName() + "</b>");
      }
      levelList.setValue(Level.OFF);
      handler.setLevel(Level.OFF);
      levelList.addSelectionHandler(new SelectionHandler() {
        public void onSelection(SelectionEvent event) {
          handler.setLevel(levelList.getValue());
          Log.finest("Setting " + title + " to level " + levelList.getValue(),
              Log.CATEGORY);
        }
      });
      add(levelList);
    }
  }

  static String myCategory = "com.google.gwt.gen2.demo.logging.client.LoggingDemo";

  boolean b = true;

  int messageNumber = 1;

  int exceptionCount = 0;

  int popupTop = 200;

  int popupLeft = 200;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    if (Log.isLoggingSupported() && !Log.isLoggingMinimal()) {
      LogHandlerUtil.injectDefaultCss();
      DropDownListBox.injectDefaultCss();
      Log.setDefaultLevel(Level.ALL);

      final FlexTable control = new FlexTable();
      RootPanel.get().add(control);
      addLevelControls(control);
      addHandlers(control);
    } else {
      Window.alert("Now that you have logging turned off or to minimal, now check your compiled output.");
      Log.warning("Should be compiled out when logging is turned off or in minimal mode");
      Log.severe("Should be compiled out when logging is turned off, but present in minimal mode");
    }
  }

  private void addHandlers(final FlexTable control) {
    int pos = 0;

    // Smart text log handler.
    SmartLogHandler textHandler = new SmartLogHandler() {
      @Override
      protected void ensureLogHandler() {
        setLogHandler(asPopup(new SimpleLogHandler(LogFormatter.TEXT)));
      }
    };
    control.setWidget(pos, 0, new HandlerConfig("Text panel", textHandler));
    ++pos;

    // Smart html log handler.
    SmartLogHandler htmlHandler = new SmartLogHandler() {
      @Override
      protected void ensureLogHandler() {
        setLogHandler(asPopup(new SimpleLogHandler()));
      }
    };
    control.setWidget(pos, 0, new HandlerConfig("Html panel", htmlHandler));
    ++pos;

    // Tree based log handler.
    SmartLogHandler treeHandler = new SmartLogHandler() {
      @Override
      protected void ensureLogHandler() {
        setLogHandler(asPopup(new GroupedLogHandler()));
      }
    };
    control.setWidget(pos, 0, new HandlerConfig("Grouped panel", treeHandler));
    ++pos;

    // Remote logger.
    SmartLogHandler remoteHandler = new SmartLogHandler() {
      @Override
      protected void ensureLogHandler() {
        setLogHandler(new RemoteLogHandler());
        // Grouped panel is longer then the others.
        popupLeft += 100;
      }
    };
    control.setWidget(pos, 0,
        new HandlerConfig("Remote logging", remoteHandler));
    ++pos;
  }

  private void addLevelControls(final FlexTable control) {
    final TextBox box = new TextBox();
    box.setText("message 0");
    VerticalPanel loggingMessage = new VerticalPanel();
    loggingMessage.add(new Label("Message to log"));
    loggingMessage.add(box);
    control.setWidget(0, 1, loggingMessage);
    Iterator<Level> levels = Log.levelIterator();
    final DropDownListBox<Level> levelList = new DropDownListBox<Level>();

    while (levels.hasNext()) {
      final Level level = levels.next();
      if (level != Level.ALL || level != Level.OFF) {
        levelList.addItem(level.getName(), level, "logging level "
            + level.getName().toLowerCase(), "use <b> "
            + level.getName().toLowerCase() + "</b> level");
      }
    }
    control.setWidget(0, 2, levelList);
    levelList.setValue(Level.INFO);

    Button log = new Button("Log message", new ClickListener() {
      public void onClick(Widget sender) {

        Log.log(box.getText(), levelList.getValue(), "user", null);
        box.setText("message " + messageNumber++);
      }
    });

    control.setWidget(0, 3, log);

    Button error = new Button("Log error", new ClickListener() {
      public void onClick(Widget sender) {

        Log.log(box.getText(), levelList.getValue(), "user.error",
            new NullPointerException());
        box.setText("message " + messageNumber++);
      }
    });

    control.setWidget(0, 4, error);
  }

  private <Handler extends Widget & LogHandler> PopupLogHandler asPopup(
      Handler handler) {
    PopupLogHandler popup = new PopupLogHandler(handler);
    popup.setPopupPosition(popupLeft, popupTop);
    popupLeft += 100;
    return popup;
  }

}
