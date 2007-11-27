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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.PinnedPanel;

import java.util.ArrayList;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PinnedPanelDemo implements EntryPoint {

  /**
   * A special purpose widget to allow scrollable stack panels.
   */
  public class MyStackPanel extends StackPanel {
    private ArrayList scrollers = new ArrayList();

    public void insert(Widget w, int before) {
      ScrollPanel p = new ScrollPanel(w);
      p.setWidth("100%");
      scrollers.add(before, p);
      super.insert(p, before);
    }

    public void onLoad() {
      setWidth("100%");
      showStack(getSelectedIndex());
    }

    /**
     * Shows the widget at the specified child index.
     * 
     * @param index the index of the child to be shown
     */
    public void showStack(int index) {
      super.showStack(index);

      if (this.isAttached()) {
        ScrollPanel me = (ScrollPanel) scrollers.get(index);
        me.setHeight("1px");
        Element tr = DOM.getChild(DOM.getFirstChild(getElement()),
            index * 2 + 1);
        int trHeight = DOM.getElementPropertyInt(tr, "offsetHeight");
        me.setHeight(trHeight + "px");
      }
    }
  }

  MyStackPanel wrapper;

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

    RootPanel.get("pinned-panel").add(pinned);

    sizePinnedPanel(pinned, Window.getClientHeight());
    Window.addWindowResizeListener(new WindowResizeListener() {
      public void onWindowResized(int width, int height) {

        sizePinnedPanel(pinned, height);
      }

    });
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
    wrapper = new MyStackPanel();
    wrapper.setHeight("250px");
    Tree contents = new Tree();
    wrapper.add(contents, "<b>People</b>", true);

    wrapper.add(new Label("None"), "<b>Academics</b>", true);
    navBar.add(wrapper);

    TreeItem students = contents.addItem("Students");
    students.addItem("Jill");
    students.addItem("Jack");
    students.addItem("Molly");
    students.addItem("Ms. Muffat");

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

  private void sizePinnedPanel(final PinnedPanel pinned, int height) {
    // TODO(ECC) remove magic numbers when I have time to pound in it.
    pinned.setHeight(height - 120 + "px");
    wrapper.setHeight(height - 160 + "px");
    wrapper.showStack(wrapper.getSelectedIndex());
  }
}
