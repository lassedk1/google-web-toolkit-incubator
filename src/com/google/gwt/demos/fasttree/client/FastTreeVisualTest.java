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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.demos.fasttree.client.TreeBenchmarkHelper.TreeType;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.FastTree;
import com.google.gwt.widgetideas.client.FastTreeItem;
import com.google.gwt.widgetideas.client.HasFastTreeItems;

import java.util.ArrayList;

/**
 * Dumping ground for {@link FastTree} visual tests.
 */
public class FastTreeVisualTest extends FastTreeDemo implements EntryPoint {

  public StackPanel createDemo() {
    StackPanel p = super.createDemo();
    p.add(treeInTree(), "Internal tree");
    p.add(focusTree(), "Focus");
    p.add(treeWithVarietyWidgets(), "Widgets");
    p.add(treeWithCustomImages(), "Custom Images");
    p.add(textTreeInScroll(), "In scroll");
    p.add(widgetTreeInScroll(), "Widget tree in scroll");
    p.add(timingTests(), "Timing tests");
    return p;
  }

  public String getInstructions() {
    return "Opening both trees should display correctly";
  }

  public void report(String s) {
    RootPanel.get().add(new HTML(s));
  }

  private FastTreeItem add(FastTreeItem parent, TreeType type) {
    if (type == TreeType.TEXT) {
      return parent.addItem("text");
    } else if (type == TreeType.HTML) {
      FastTreeItem item = new FastTreeItem();
      item.setHTML("<h1>html</h1>");
      parent.addItem(item);
      return item;
    } else if (type == TreeType.CHECKBOX) {
      return parent.addItem(new CheckBox("myBox"));
    } else {
      throw new RuntimeException("What?");
    }
  }

  private TreeItem add(TreeItem parent, TreeType type) {
    if (type == TreeType.TEXT) {
      return parent.addItem("text");
    } else if (type == TreeType.HTML) {
      TreeItem item = new TreeItem();
      item.setHTML("<h1>html</h1>");
      parent.addItem(item);
      return item;
    } else if (type == TreeType.CHECKBOX) {
      return parent.addItem(new CheckBox("myBox"));
    } else {
      throw new RuntimeException("What?");
    }
  }

  private void createChild(final HasFastTreeItems parent, final int index,
      final int children) {

    FastTreeItem item = new FastTreeItem("child" + index + " (" + children
        + " children)") {
      public void ensureChildren() {
        for (int i = 0; i < children; i++) {
          createChild(this, i, children + (i * 10));
        }
      }
    };
    item.becomeInteriorNode();
    parent.addItem(item);
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
    front.add(add(root, type));
    int count = 0;
    while (true) {
      ArrayList newFront = new ArrayList();
      for (int i = 0; i < front.size(); i++) {
        for (int j = 0; j < numBranches; j++) {
          newFront.add(add((FastTreeItem) front.get(i), type));
          if (++count == numNodes) {
            RootPanel.get().add(t);
            return;
          }
        }
      }
      front = newFront;
    }
  }

  private void populateTree(Tree t, int numBranches, int numNodes, TreeType type) {
    TreeItem root = t.addItem("root");

    ArrayList front = new ArrayList();
    front.add(add(root, type));
    int count = 0;
    while (true) {
      ArrayList newFront = new ArrayList();
      for (int i = 0; i < front.size(); i++) {
        for (int j = 0; j < numBranches; j++) {
          newFront.add(add((TreeItem) front.get(i), type));
          if (++count == numNodes) {
            RootPanel.get().add(t);
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
    sp.setSize("500px", "200px");
    sp.setAlwaysShowScrollBars(true);
    return sp;
  }

  private Widget timingTests() {
    final FlexTable table = new FlexTable();
    final TextBox branches = new TextBox();
    table.setWidget(0, 0, branches);
    branches.setText("10");
    branches.setTitle("Number of branches");
    final TextBox nodes = new TextBox();
    table.setWidget(0, 1, nodes);
    nodes.setText("200");
    table.setTitle("Number of nodes");

    table.setWidget(1, 0, new Button("Normal tree", new ClickListener() {
      public void onClick(Widget sender) {
        long time = System.currentTimeMillis();
        Tree t = new Tree();
        populateTree(t, Integer.parseInt(branches.getText()),
            Integer.parseInt(nodes.getText()), TreeType.CHECKBOX);
        table.setWidget(2, 0, t);
        Window.alert("Elapsed time:" + (System.currentTimeMillis() - time));
      }
    }));

    table.setWidget(1, 1, new Button("Fast tree", new ClickListener() {
      public void onClick(Widget sender) {
        long time = System.currentTimeMillis();
        FastTree t = new FastTree();
        populateTree(t, Integer.parseInt(branches.getText()),
            Integer.parseInt(nodes.getText()), TreeType.CHECKBOX);
        table.setWidget(2, 1, t);
        Window.alert("Elapsed time:" + (System.currentTimeMillis() - time));
      }
    }));
    return table;
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

  private Widget treeWithCustomImages() {
    FastTree t = new FastTree();
    populateTree(t, 4, 5, TreeType.CHECKBOX);
    // now change style names;
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
}
