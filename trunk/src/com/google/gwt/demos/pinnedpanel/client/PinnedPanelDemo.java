package com.google.gwt.demos.pinnedpanel.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
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
    // Some random contents to make the tree interesting.
    Widget contents = createSchoolNavBar();

    // The toggle Button.
    ToggleButton toggler = createToggleButton();

    // The actual pinned panel.
    PinnedPanel pinned = new PinnedPanel(200, toggler, contents);

    // Right margin needs to be set programmatically as it is used to create the
    // panel slide-out effect.
    pinned.setRightMargin(3);

    RootPanel.get("pinned-panel").add(pinned);



  }

  private ToggleButton createToggleButton() {
    Image show = new Image("show.jpg");
    Image hide = new Image("hide.jpg");
    ToggleButton toggler = new ToggleButton(hide, show);
    return toggler;
  }

  private Widget createSchoolNavBar() {
    FlowPanel navBar = new FlowPanel();
    navBar.setSize("100%", "100%");
    
    HTML title = new HTML("School Directory");
    title.setStyleName("nav-tree-title");
    navBar.add(title);
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
    return navBar;
  }
}
