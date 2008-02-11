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

import com.google.gwt.libideas.logging.shared.Handler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Shows a widget handler in a popup.
 */
public abstract class PopupWidgetHandler extends Handler {

  protected PopupPanel popup;
  protected boolean autoShow;

  PopupWidgetHandler(boolean autoShow) {
    this.popup = new PopupPanel(true);
    popup.setStyleName("gwt-PopupWidgetHandler");
    this.autoShow = autoShow;
  }

  public abstract Widget getWidget();

  public void hideHandler() {
    popup.hide();
  }

  public void setPopupPosition(int left, int top) {
    popup.setPopupPosition(left, top);
  }

  public void showLog() {
    popup.show();
  }

}
