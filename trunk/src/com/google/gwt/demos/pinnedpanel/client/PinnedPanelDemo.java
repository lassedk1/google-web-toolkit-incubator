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

package com.google.gwt.demos.pinnedpanel.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.PinnedPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PinnedPanelDemo implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    // The toggle Button.
    ToggleButton toggler = createToggleButton();

    // Some random contents to make the tree interesting.
    Widget contents = createSchoolNavBar(toggler);
 

    // The actual pinned panel. 
    final PinnedPanel pinned = new PinnedPanel(200, toggler, contents);

    // Right margin needs to be set programmatically as it is used to create the
    // panel slide-out effect.
    pinned.setRightMargin(10);

    // Add a change listener
    pinned.addChangeListener(new ChangeListener() {

      public void onChange(Widget sender) {
        RootPanel.get().add(new Label("Pinned: " + pinned.isPinned()));
      }
    });

     
    RootPanel.get("pinned-panel").add(pinned);
  }


  private Widget createSchoolNavBar(ToggleButton toggler) {
    FlowPanel navBar = new FlowPanel();
    navBar.setSize("100%", "100%");
    HTML title = new HTML("School Directory");
    HorizontalPanel panel = new HorizontalPanel();
    panel.setWidth("100%");
    panel.add(toggler);
    panel.add(title);
    navBar.add(panel);

    panel.setStyleName("nav-tree-title");
    Tree contents = new Tree();
    navBar.add(contents);

    TreeItem students = contents.addItem("Students");
    students.addItem("Jill");
    students.addItem("Jack");

    TreeItem teachers = contents.addItem("Teachers");
    teachers.addItem("Mrs Black");
    teachers.addItem("Mr White");

    TreeItem admin = contents.addItem("Administrators");
    admin.addItem("The Soup Nazi");
    admin.addItem("The Grand High Supreme Master Pubba");
    return navBar;
  }

  private ToggleButton createToggleButton() {
    Image show = new Image("show.jpg");
    Image hide = new Image("hide.jpg");
    ToggleButton toggler = new ToggleButton(hide, show);
    return toggler;
  }
}
