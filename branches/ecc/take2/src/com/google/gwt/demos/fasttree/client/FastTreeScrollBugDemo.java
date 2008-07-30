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

package com.google.gwt.demos.fasttree.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.widgetideas.client.FastTree;

/**
 * Fast tree bug.
 * 
 * 
 */
public class FastTreeScrollBugDemo {
  public static void run() {
    FastTree t = new FastTree();
    ScrollPanel p = new ScrollPanel(t);
    Window.alert("P:P" + DOM.getStyleAttribute(p.getElement(), "overflow"));
    // DOM.setStyleAttribute(p.getElement(), "overflow", "hidden");
    RootPanel.get().add(p);
    FastTree.addDefaultCSS();
    p.setPixelSize(200, 200);
    for (int i = 0; i < 30; i++) {
      t.addItem(new Hyperlink(i + "th", "http:\\www.google.com"));
    }
  }

}
