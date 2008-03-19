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

package com.google.gwt.libideas.logging.client;

import com.google.gwt.libideas.logging.shared.Level;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A very simple log handler meant to display simple messages.
 */
public class SimpleLogHandler extends PopupWidgetLogHandler {
  Widget widget;
  VerticalPanel contents;

  public SimpleLogHandler(boolean popup) {
    super(popup);
    VerticalPanel p = new VerticalPanel();
    ScrollPanel scroller = new ScrollPanel();
    contents = new VerticalPanel();
    scroller.add(contents);

    p.add(new Button("clear log", new ClickListener() {
      public void onClick(Widget sender) {
        contents.clear();
      }
    }));
    p.add(scroller);
    widget = p;
    widget.setStyleName("gwt-SimpleLogHandler");
  }

  @Override
  public Widget getWidget() {
    return widget;
  }

  @Override
  public void publish(String message, Level level, String category, Throwable e) {
    String output = category == null ? message : category + ": " + message;
    Label l = new Label(output);
    l.setTitle("level:" + level);
    contents.add(l);
    if (e != null) {
      Label eLabel = new Label("&nbsp;&nbsp;&nbsp;&nbsp;" + e.getMessage());
      contents.add(eLabel);
    }
  }
}
