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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.FastTree;
import com.google.gwt.widgetideas.client.FastTreeItem;
import com.google.gwt.widgetideas.client.HasFastTreeItems;
import com.google.gwt.widgetideas.client.ListeningFastTreeItem;
import com.google.gwt.widgetideas.client.overrides.DOMHelper;

import java.util.ArrayList;

/**
 * Demo for the new {@link FastTree} widget.
 */
public class FastTreeDemo implements EntryPoint {

  public StackPanel createDemo() {
    FastTree.addDefaultCSS();

    StackPanel p = new StackPanel();

    p.add(basicTree(), "Basic tree");
    p.add(lazyTree(), "Lazy tree");
    p.add(verboseTree(), "Verbose tree");
    p.add(profileTree(), "Profiling tree");
    return p;
  }

  public String getInstructions() {
    return "Opening both trees should display correctly";
  }

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    // FastTreeScrollBugDemo.run();
    RootPanel.get().add(new FastTreeRTLDemo().createDemo());
  }

  public void report(String s) {
    RootPanel.get().add(new HTML(s));
  }

  public void store() {
    FastTree t = new FastTree();
    FastTreeItem camping = t.addItem("Camping Gear");
    camping.addItem("Camping tents");
    FastTreeItem cooking = camping.addItem("Cooking gear");
    camping.setState(true);

    t.setSelectedItem(cooking);

    FastTreeItem ap = t.addItem("Apparel");
    ap.addItem("Jackets");
    ap.addItem("Shirts");
    t.addItem("Footwear").becomeInteriorNode();
    t.addItem("Coolers");
    RootPanel.get().add(t);
  }

  protected Widget basicTree() {
    FastTree t = new FastTree();
    FastTreeItem a = t.addItem("A root tree item");
    a.addItem("A child");
    FastTreeItem aXb = a.addItem("Another child");
    aXb.addItem("a grand child");
    FastTreeItem widgetBranch = a.addItem(new CheckBox("A checkbox child"));
    FastTreeItem textBoxParent = widgetBranch.addItem("A TextBox parent");
    textBoxParent.addItem(new TextBox());
    textBoxParent.addItem("and another one...");
    textBoxParent.addItem(new TextArea());

    ListBox lb = new ListBox();
    for (int i = 0; i < 100; i++) {
      lb.addItem(i + "");
    }
    widgetBranch.addItem("A ListBox parent").addItem(lb);
    return t;
  }

  /**
   * Creates a lazy tree.
   */
  protected Widget lazyTree() {
    FastTree t = new FastTree();
    lazyCreateChild(t, 0, 50);
    return t;
  }

  protected Widget profileTree() {
    final FlexTable table = new FlexTable();
    final TextBox branches = new TextBox();
    int row = 0;
    table.setText(row, 0, "children per node");
    table.setText(row, 1, "total number of rows");
    table.setText(row, 2, "what type of node");
    ++row;
    table.setWidget(row, 0, branches);
    branches.setText("5");
    final TextBox nodes = new TextBox();

    table.setWidget(row, 1, nodes);
    nodes.setText("2000");
    table.setTitle("Number of nodes");

    final ListBox type = new ListBox();
    type.addItem("Text");
    type.addItem("HTML");
    type.addItem("CheckBox");
    type.setSelectedIndex(1);
    table.setWidget(row, 2, type);
    ++row;
    final int widgetRow = row + 1;
    table.setWidget(row, 0, new Button("Normal tree", new ClickListener() {
      public void onClick(Widget sender) {
        long time = System.currentTimeMillis();
        Tree t = new Tree();
        profileCreateTree(t, Integer.parseInt(branches.getText()),
            Integer.parseInt(nodes.getText()),
            TreeType.getType(type.getSelectedIndex()));
        table.setWidget(widgetRow, 0, t);
        Window.alert("Elapsed time: " + (System.currentTimeMillis() - time));
      }
    }));

    table.setWidget(row, 1, new Button("Fast tree", new ClickListener() {
      public void onClick(Widget sender) {
        long time = System.currentTimeMillis();
        FastTree t = new FastTree();
        profileCreateTree(t, Integer.parseInt(branches.getText()),
            Integer.parseInt(nodes.getText()),
            TreeType.getType(type.getSelectedIndex()));
        table.setWidget(widgetRow, 1, t);
        Window.alert("Elapsed time: " + (System.currentTimeMillis() - time));
      }
    }));
    ++row;
    table.setText(row, 0, "tree results");
    table.getCellFormatter().setVerticalAlignment(row, 0,
        HasAlignment.ALIGN_TOP);

    table.setText(row, 1, "fasttree results");
    table.getCellFormatter().setVerticalAlignment(row, 1,
        HasAlignment.ALIGN_TOP);
    return table;
  }

  protected Widget verboseTree() {
    FastTree tree = new FastTree();
    verboseTreeItem(tree, 10);
    return tree;
  }

  private void lazyCreateChild(final HasFastTreeItems parent, final int index,
      final int children) {

    FastTreeItem item = new FastTreeItem("child" + index + " (" + children
        + " children)") {
      public void ensureChildren() {
        for (int i = 0; i < children; i++) {
          lazyCreateChild(this, i, children + (i * 10));
        }
      }
    };
    item.becomeInteriorNode();
    parent.addItem(item);
  }

  private FastTreeItem profileAdd(FastTreeItem parent, TreeType type) {
    if (type == TreeType.TEXT) {
      FastTreeItem text = new FastTreeItem();
      text.setText("item");
      parent.addItem(text);
      return text;
    } else if (type == TreeType.HTML) {
      FastTreeItem item = new FastTreeItem("<h1>html</h1>");
      parent.addItem(item);
      return item;
    } else if (type == TreeType.CHECKBOX) {
      return parent.addItem(new CheckBox("myBox"));
    } else {
      throw new RuntimeException("What?");
    }
  }

  private TreeItem profileAdd(TreeItem parent, TreeType type) {
    if (type == TreeType.TEXT) {
      TreeItem item = new TreeItem();
      item.setText("text");
      parent.addItem(item);
      return item;
    } else if (type == TreeType.HTML) {
      TreeItem item = new TreeItem("<h1>html</h1>");
      parent.addItem(item);
      return item;
    } else if (type == TreeType.CHECKBOX) {
      return parent.addItem(new CheckBox("myBox"));
    } else {
      throw new RuntimeException("What?");
    }
  }

  private void profileCreateTree(FastTree t, int numBranches, int numNodes,
      TreeType type) {
    FastTreeItem root = t.addItem("root");
    ArrayList front = new ArrayList();
    front.add(profileAdd(root, type));
    int count = 0;
    while (true) {
      ArrayList newFront = new ArrayList();
      for (int i = 0; i < front.size(); i++) {
        for (int j = 0; j < numBranches; j++) {
          newFront.add(profileAdd((FastTreeItem) front.get(i), type));
          if (++count == numNodes) {
            RootPanel.get().add(t);
            return;
          }
        }
      }
      front = newFront;
    }
  }

  private void profileCreateTree(Tree t, int numBranches, int numNodes,
      TreeType type) {
    TreeItem root = t.addItem("root");

    ArrayList front = new ArrayList();
    front.add(profileAdd(root, type));
    int count = 0;
    while (true) {
      ArrayList newFront = new ArrayList();
      for (int i = 0; i < front.size(); i++) {
        for (int j = 0; j < numBranches; j++) {
          newFront.add(profileAdd((TreeItem) front.get(i), type));
          if (++count == numNodes) {
            RootPanel.get().add(t);
            return;
          }
        }
      }
      front = newFront;
    }
  }

  private void verboseTreeItem(HasFastTreeItems parent, int children) {
    for (int i = 0; i < children; i++) {
      final int index = i;

      FastTreeItem item = new ListeningFastTreeItem("child " + i) {

        public void beforeClose() {
          Window.alert("Close item" + index);
        }

        public void beforeOpen() {
          Window.alert("Open item " + index);
        }

        protected boolean beforeSelectionLost() {
          return DOMHelper.confirm("Are you sure you want to leave me?");
        }

        protected void ensureChildren() {
          Window.alert("You are about to open my children for the first time");
        }

        protected void onSelected() {
          Window.alert("You selected item " + index);
        }
      };
      parent.addItem(item);
      verboseTreeItem(item, children - (i + 1));
    }
  }

}
