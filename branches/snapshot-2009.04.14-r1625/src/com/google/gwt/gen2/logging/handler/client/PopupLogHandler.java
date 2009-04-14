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
import com.google.gwt.gen2.logging.shared.LogHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Shows a widget log handler in a {@link PopupPanel}.
 * 
 * @param <WidgetLogHandler> the widget log handler
 */
public class PopupLogHandler<WidgetLogHandler extends Widget & LogHandler>
    extends PopupPanel implements LogHandler {
  private final WidgetLogHandler widget;
  private boolean autoShow;

  /**
   * Constructor. If autoShow is true, the popup is shown whenever a new message
   * is added and hidden whenever the popup loses focus.
   * 
   * @param autoShow show popup when messages are logged
   * @param widget the underlying widget log handler
   */
  public PopupLogHandler(boolean autoShow, WidgetLogHandler widget) {
    super(autoShow);
    this.autoShow = autoShow;
    setStyleName("gwt-PopupLogHandler");
    this.widget = widget;
    setWidget(widget);
  }

  /**
   * Constructor.
   * 
   * @param widget the underlying widget log handler
   */
  public PopupLogHandler(WidgetLogHandler widget) {
    this(true, widget);
  }

  /**
   * Gets the underlying log handler widget.
   * 
   * @return the underlying widget
   */
  @Override
  public WidgetLogHandler getWidget() {
    return widget;
  }

  public void onLog(LogEvent event) {
    widget.onLog(event);
    if (autoShow) {
      this.show();
    }
  }

}
