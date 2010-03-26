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

package com.google.gwt.museum.client.feature;

import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.museum.client.common.AbstractIssueOverride;
import com.google.gwt.museum.client.common.TreeHelper.TreeType;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.FastTree;
import com.google.gwt.widgetideas.client.FastTreeItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Fast tree visuals.
 * 
 */
public class FastTreeVisuals extends AbstractIssueOverride {

  @Override
  public StackPanel createIssue() {
    FastTree.addDefaultCSS();

    StackPanel p = new StackPanel();
    p.add(treeInTree(), "Internal tree");
    p.add(focusTree(), "Focus");
    p.add(treeWithVarietyWidgets(), "Widgets");
    p.add(textTreeInScroll(), "In scroll");
    p.add(widgetTreeInScroll(), "Widget tree in scroll");
    p.add(zoomingTree(), "zooming tree");
    return p;
  }

  @Override
  public String getInstructions() {
    return "visual test of basic tree features";
  }

  @Override
  public String getSummary() {
    return "FastTree test";
  }

  @Override
  public boolean hasCSS() {
    return false;
  }

  public void report(String s) {
    Log.info(s);
  }

  private FastTreeItem add(FastTreeItem parent, String value, TreeType type) {
    if (type == TreeType.TEXT) {
      return parent.addItem(value);
    } else if (type == TreeType.HTML) {
      FastTreeItem item = new FastTreeItem();
      item.setHTML("<a>" + value + "</a>");
      parent.addItem(item);
      return item;
    } else if (type == TreeType.CHECKBOX) {
      return parent.addItem(new CheckBox(value));
    } else {
      throw new RuntimeException("What?");
    }
  }

  private Widget focusTree() {
    // Create a tree with a few items in it.
    FastTreeItem root = new FastTreeItem("root");
    root.addItem("item0");
    root.addItem("item1");
    root.addItem("item2");
    root.addItem(new CheckBox("item3"));
    FastTree t = new FastTree();

    // tree2
    FastTreeItem root2 = new FastTreeItem("child root");
    root2.addItem("child item0");
    root2.addItem("child item1");
    root2.addItem("child item2");
    root2.addItem(new CheckBox("child item3"));

    // nest the trees
    root.addItem(root2);
    t.addItem(root);
    t.addFocusListener(new FocusListener() {

      public void onFocus(Widget sender) {
        report("on focus tree");
      }

      public void onLostFocus(Widget sender) {
        report("lost focus tree");
      }
    });

    return t;
  }

  private void populateTree(FastTree t, int numBranches, int numNodes,
      TreeType type) {
    FastTreeItem root = t.addItem("root");

    ArrayList front = new ArrayList();
    front.add(add(root, "first child", type));
    int count = 0;
    while (true) {
      ArrayList newFront = new ArrayList();
      for (int i = 0; i < front.size(); i++) {
        for (int j = 0; j < numBranches; j++) {
          newFront.add(add((FastTreeItem) front.get(i), "Tree item" + i + ","
              + j, type));
          if (++count == numNodes) {
            return;
          }
        }
      }
      front = newFront;
    }
  }

  private Widget textTreeInScroll() {
    FastTree tree = new FastTree();
    populateTree(tree, 4, 100, TreeType.TEXT);
    ScrollPanel sp = new ScrollPanel(tree);
    sp.setSize("200px", "200px");
    return sp;
  }

  private Widget treeInTree() {
    // Create a tree with a few items in it.
    FastTreeItem root = new FastTreeItem("root");
    root.addItem("item0");
    root.addItem("item1");
    root.addItem("item2");
    root.addItem(new CheckBox("item3"));
    FastTree t = new FastTree();

    // tree2
    FastTreeItem root2 = new FastTreeItem("child root");
    root2.addItem("child item0");
    root2.addItem("child item1");
    root2.addItem("child item2");
    root2.addItem(new CheckBox("child item3"));
    FastTree t2 = new FastTree();

    // nest the trees
    t2.addItem(root2);
    FastTreeItem item = new FastTreeItem(t2);
    root.addItem(item);
    t.addItem(root);

    return t;
  }

  private Widget treeWithVarietyWidgets() {
    FastTree t = new FastTree();
    FastTreeItem item = t.addItem("Text");
    item.addItem(new TextBox());
    ListBox lb = new ListBox();
    for (int i = 0; i < 100; i++) {
      lb.addItem(i + "");
    }
    item.addItem(lb);
    item.addItem(new TextArea());

    return t;
  }

  private Widget widgetTreeInScroll() {
    FastTree tree = new FastTree();
    populateTree(tree, 4, 100, TreeType.CHECKBOX);
    ScrollPanel sp = new ScrollPanel(tree);
    DOM.setStyleAttribute(sp.getElement(), "position", "relative");
    sp.setSize("400px", "100px");
    sp.setAlwaysShowScrollBars(true);
    return sp;
  }

  private Widget zoomingTree() {
    VerticalPanel p = new VerticalPanel();
    final TextBox b = new TextBox();

    p.add(b);
    final FastTree tree = new FastTree();
    final HashMap<String, FastTreeItem> treeItems = new HashMap<String, FastTreeItem>();

    b.addKeyboardListener(new KeyboardListener() {

      public void onKeyDown(Widget sender, char keyCode, int modifiers) {
        if (keyCode == KEY_ENTER) {
          String value = b.getText().trim();
          FastTreeItem chosen = treeItems.get(value);
          if (chosen == null) {
            Window.alert("No such tree item exists");
          }
          tree.setSelectedItem(chosen);
          tree.ensureSelectedItemVisible();
        }
      }

      public void onKeyPress(Widget sender, char keyCode, int modifiers) {
      }

      public void onKeyUp(Widget sender, char keyCode, int modifiers) {
      }
    });
    ScrollPanel scroller = new ScrollPanel();

    for (int i = 0; i < 10; i++) {
      FastTreeItem item = tree.addItem("" + i);
      treeItems.put("" + i, item);
      for (int j = 0; j < 5; j++) {
        String value = "" + i + "." + j;
        FastTreeItem subItem = item.addItem(value);
        treeItems.put(value, subItem);
        for (int k = 0; k < 2; k++) {
          String newValue = i + "." + j + "." + k;
          treeItems.put(newValue, subItem.addItem(newValue));
        }
      }
    }
    scroller.setWidget(tree);
    scroller.setHeight("200px");
    scroller.setWidth("100px");
    p.add(scroller);
    return p;
  }

}
