/*
 * Copyright 2007 Google Inc.
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

package com.google.gwt.gen2.demo.collapsiblepanel.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.complexpanel.client.CollapsiblePanel;
import com.google.gwt.gen2.complexpanel.client.FastTree;
import com.google.gwt.gen2.complexpanel.client.FastTreeItem;
import com.google.gwt.gen2.complexpanel.client.CollapsiblePanel.CollapsedStateEvent;
import com.google.gwt.gen2.complexpanel.client.CollapsiblePanel.CollapsedStateHandler;
import com.google.gwt.gen2.complexpanel.client.CollapsiblePanel.ExpandedStateEvent;
import com.google.gwt.gen2.complexpanel.client.CollapsiblePanel.ExpandedStateHandler;
import com.google.gwt.gen2.logging.shared.Log;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * {@link CollapsiblePanel} demo.
 */
public class CollapsiblePanelDemo implements EntryPoint {

  private ToggleButton controlButton;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    try {

      // Some random contents to make the tree interesting.
      Panel contents = createSchoolNavBar();
      FastTree.injectDefaultCss();
      CollapsiblePanel.injectDefaultCss();

      // The panel.
      final CollapsiblePanel panel = new CollapsiblePanel();
      panel.addCollapsedStateHandler(new CollapsedStateHandler() {
        public void onCollapsedState(CollapsedStateEvent e) {
          Window.alert("panel collapsed");
        }
      });

      panel.addExpandedStateHandler(new ExpandedStateHandler() {
        public void onExpandedState(ExpandedStateEvent e) {
          Window.alert("panel expanded");
        }
      });

      String value = Location.getParameter("collapsed");
      if (value != null) {
        value = value.trim();
        if (value.equals("true")) {
          panel.setCollapsedState(true);
        } else if (value.equals("false")) {
          // do nothing, default.
        } else {
          Window.alert("collapsed should not be given " + value
              + " use true or false instead");
        }
      }
      RootPanel.get("collapsible-panel").add(panel);
      panel.add(contents);
      panel.setHeight(Window.getClientHeight() - 1 + "px");
      panel.hookupControlToggle(controlButton);
    } catch (RuntimeException e) {
      if (GWT.isScript()) {
        Log.severe(e.getMessage());
      }
      throw e;
    }
  }

  private Panel createSchoolNavBar() {
    controlButton = new ToggleButton();
    if (LocaleInfo.getCurrentLocale().isRTL()) {
      controlButton.getUpFace().setHTML("<i>click to pin &larr; </i>");
      controlButton.getDownFace().setHTML("<i> &rarr; click to collapse</i>  ");
    } else {
      controlButton.getUpFace().setHTML("<i>click to pin &rarr;</i>");
      controlButton.getDownFace().setHTML("<i>&larr; click to collapse</i> ");
    }

    FlowPanel navBar = new FlowPanel();
    navBar.setSize("200px", "");

    navBar.add(controlButton);
    controlButton.setStylePrimaryName("CollapsibleTitle");

    final FastTree contents = new FastTree();
    navBar.add(contents);

    FastTreeItem students = contents.addItem("Students");
    students.addItem("Jill");
    students.addItem("Jack");
    students.addItem("Molly");
    students.addItem("Ms. Muffat");

    FastTreeItem teachers = contents.addItem("Teachers");
    teachers.addItem("Mrs Black");
    teachers.addItem("Mr White");

    FastTreeItem admin = contents.addItem("Administrators");
    admin.addItem("The Soup Nazi");
    admin.addItem("The Grand High Supreme Master Pubba");
    return navBar;
  }
}
