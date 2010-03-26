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
package com.google.gwt.gen2.demo.fasttree.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.gen2.commonevent.shared.BeforeCloseEvent;
import com.google.gwt.gen2.commonevent.shared.BeforeCloseHandler;
import com.google.gwt.gen2.commonevent.shared.BeforeOpenEvent;
import com.google.gwt.gen2.commonevent.shared.BeforeOpenHandler;
import com.google.gwt.gen2.complexpanel.client.FastTree;
import com.google.gwt.gen2.complexpanel.client.FastTreeItem;
import com.google.gwt.gen2.complexpanel.client.HasFastTreeItems;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Fast tree demo.
 */
public class FastTreeDemo implements EntryPoint {

  public StackPanel createDemo() {

    // inject default styles
    FastTree.injectDefaultCss();

    StackPanel p = new StackPanel();

    p.add(basicTree(), "Basic tree");
    p.add(lazyTree(), "Lazy tree");
    p.add(verboseTree(), "Verbose tree");
    p.add(crazyTree(), "Crazy tree");
    return p;
  }

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    RootPanel.get().add(new FastTreeDemo().createDemo());
  }
  
  protected Widget basicTree() {
    FastTree t = new FastTree();
    FastTreeItem a = t.addItem("A root tree item");
    a.addItem("A child with different style");
    a.addItem("regular style");
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
    final FastTree t = new FastTree();
    lazyCreateChild(t, 0, 50);
    t.addBeforeOpenHandler(new BeforeOpenHandler<FastTreeItem>() {
      public void onBeforeOpen(BeforeOpenEvent<FastTreeItem> event) {
        if (event.isFirstTime()) {
          for (int i = 0; i < 50; i++) {
            lazyCreateChild(event.getTarget(), i, 50 + (i * 10));
          }
        }
      }
    });
    return t;
  }

  protected Widget verboseTree() {
    FastTree tree = new FastTree();
    verboseTreeItem(tree, 10);
    tree.addOpenHandler(new OpenHandler<FastTreeItem>() {

      public void onOpen(OpenEvent<FastTreeItem> event) {
        Window.alert("Opened " + event.getTarget().getHTML());
      }
    });
    tree.addCloseHandler(new CloseHandler<FastTreeItem>() {

      public void onClose(CloseEvent<FastTreeItem> event) {
        Window.alert("Close " + event.getTarget().getHTML());
      }
    });
    tree.addSelectionHandler(new SelectionHandler<FastTreeItem>() {

      public void onSelection(SelectionEvent<FastTreeItem> event) {
        Window.alert("You selected " + event.getSelectedItem().getHTML());
      }
    });
    tree.addBeforeOpenHandler(new BeforeOpenHandler<FastTreeItem>() {

      public void onBeforeOpen(BeforeOpenEvent<FastTreeItem> event) {
        if (Window.confirm("Would you like to change the name of the item before opening it?")) {
          event.getTarget().setHTML("Name changed before open.");
        }
      }
    });
    tree.addBeforeCloseHandler(new BeforeCloseHandler<FastTreeItem>() {

      public void onBeforeClose(BeforeCloseEvent<FastTreeItem> event) {
        if (Window.confirm("Would you like to change the name of the item before closing it?")) {
          event.getTarget().setHTML("Name changed before close.");
        }
      }
    });
    return tree;
  }

  private Widget crazyTree() {
    FastTree tree = new FastTree();
    FastTreeItem a = tree.addItem("I am root");
    a.addItem("I am root's child");
    FastTreeItem aXb = a.addItem("Root to become leaf.");
    aXb.addItem("a grand child");
    for (int i = 0; i < 10; i++) {
      aXb.addItem("grand child " + i);
    }
    tree.addOpenHandler(new OpenHandler<FastTreeItem>() {

      public void onOpen(OpenEvent<FastTreeItem> event) {
        if (!event.getTarget().isLeafNode()) {
          if (Window.confirm("Would you like me to become a leaf?")) {
            event.getTarget().becomeLeaf();
          }
        }
      }
    });

    return tree;
  }

  
  private void lazyCreateChild(final HasFastTreeItems parent, final int index,
      final int children) {

    final FastTreeItem item = new FastTreeItem("child" + index + " ("
        + children + " children)");

    item.becomeInteriorNode();
    parent.addItem(item);
  }

  private void verboseTreeItem(HasFastTreeItems parent, int children) {
    for (int i = 0; i < children; i++) {
      final int index = i;

      FastTreeItem item = new FastTreeItem();
      item.setText("item " + index);
      parent.addItem(item);
      verboseTreeItem(item, children - (i + 1));
    }
  }
}
