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

package com.google.gwt.demos.logging.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.logging.client.DivHandler;
import com.google.gwt.libideas.logging.client.FireBugHandler;
import com.google.gwt.libideas.logging.client.GWTHandler;
import com.google.gwt.libideas.logging.client.RemoteLoggingHandler;
import com.google.gwt.libideas.logging.client.TreeHandler;
import com.google.gwt.libideas.logging.shared.Handler;
import com.google.gwt.libideas.logging.shared.Level;
import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LoggingDemo implements EntryPoint {

  class HandlerConfig extends FlexTable {
    class MyHandlerClickListener implements ClickListener {
      Handler handler;
      boolean added;

      public MyHandlerClickListener(Handler handler) {
        this.handler = handler;
      }

      public void onClick(Widget sender) {
        if (added) {
          Log.removeHandler(handler);
          added = false;
        } else {
          Log.addHandler(handler);
          added = true;
        }
      }
    }

    HandlerConfig(final Handler handler) {
      this.setStyleName("handler-control");
      final String name = GWT.getTypeName(handler);
      int index = name.lastIndexOf(".") + 1;

      CheckBox enabled = new CheckBox("enabled");
      enabled.addClickListener(new MyHandlerClickListener(handler));

      this.setText(0, 0, name.substring(index));
      this.getFlexCellFormatter().setColSpan(0, 0, 2);
      this.setWidget(1, 0, enabled);
      this.setText(2, 0, "Current level");
      final ListBox box = new ListBox();
      Iterator levels = Log.levelIterator();
      while (levels.hasNext()) {
        Level l = (Level) levels.next();
        box.addItem(l.getName());
      }
      box.addChangeListener(new ChangeListener() {
        public void onChange(Widget sender) {
          int index = box.getSelectedIndex();
          String level = box.getItemText(index);
          Log.info("Setting " + name + " to level " + level, Log.CATEGORY);
          handler.setLevel(Log.parseLevel(level));
        }

      });
      this.setWidget(2, 1, box);
    }
  }

  public static String myCategory = "com.google.gwt.demos.logging.client.LoggingDemo";

  boolean b = true;

  int messageNumber = 1;

  int exceptionCount = 0;

  /**
   * Check out this method compiled.
   */
  public void checkingForLevelRemoval() {
    String category = "simple";
    Log.finest("finest", category);
    Log.finer("finer", category);
    Log.fine("fine", category);
    Log.config("config", category);
    Log.info("info", category);
    Log.warning("warning", category);

    // Stop inlining
    if (b) {
      b = false;
      checkingForLevelRemoval();
    }
  }

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    if (Log.isLoggingSupported()) {
      Window.alert("Logging loaded, current logging level is "
          + Log.getDefaultLevel() + " Setting it to ALL");
      Log.clearHandlers();
      Log.setDefaultLevel(Level.ALL);
      final FlexTable control = new FlexTable();
      RootPanel.get().add(control);
      addLevelControls(control);
      addHandlers(control);
    }
    checkingForLevelRemoval();
  }

  private void addHandlers(final FlexTable control) {
    int pos = 0;
    control.setWidget(pos, 0, new HandlerConfig(new TreeHandler(true)));
    control.setWidget(++pos, 0, new HandlerConfig(new FireBugHandler()));
    control.setWidget(++pos, 0, new HandlerConfig(new DivHandler()));
    control.setWidget(++pos, 0, new HandlerConfig(new GWTHandler()));
    RemoteLoggingHandler handler = new RemoteLoggingHandler();
    control.setWidget(++pos, 0, new HandlerConfig(handler));
  }

  private void addLevelControls(final FlexTable control) {
    final TextBox box = new TextBox();
    box.setText("message 0");
    VerticalPanel loggingMessage = new VerticalPanel();
    loggingMessage.add(new Label("Logging message"));
    loggingMessage.add(box);
    control.setWidget(0, 1, loggingMessage);
    Iterator levels = Log.levelIterator();
    int pos = 0;

    while (levels.hasNext()) {
      final Level level = (Level) levels.next();
      VerticalPanel p = new VerticalPanel();
      p.add(new Label((level.getName().toLowerCase())));
      control.setWidget(pos, 2, p);
      p.setStyleName("level-control");
      if (!level.isControl()) {
        Button b1 = new Button("log ", new ClickListener() {
          public void onClick(Widget sender) {
            Log.log(box.getText(), level, "user", null);
            box.setText("message " + messageNumber++);
          }
        });

        p.add(b1);

        Button b2 = new Button("throw exception ", new ClickListener() {
          public void onClick(Widget sender) {
            Log.log("User Exception " + exceptionCount++, level, "user",
                new SerializableException(box.getText()));

            box.setText("message " + messageNumber++);
          }
        });

        p.add(b2);
      }
      Button b2 = new Button("set level", new ClickListener() {

        public void onClick(Widget sender) {
          Log.setDefaultLevel(level);
        }
      });
      p.add(b2);
      ++pos;
    }
  }

}
