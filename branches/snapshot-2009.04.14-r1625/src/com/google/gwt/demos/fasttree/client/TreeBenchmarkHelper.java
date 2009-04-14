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
package com.google.gwt.demos.fasttree.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

import java.util.ArrayList;

/**
 * Shows how the core engine for TreeBenchmark actually produces trees.
 * 
 */
public class TreeBenchmarkHelper implements EntryPoint {
  /**
   * Tree types
   */
  public static class TreeType {
    static TreeType TEXT = new TreeType();
    static TreeType HTML = new TreeType();
    static TreeType CHECKBOX = new TreeType();

    public static TreeType getType(int index) {
      if (index == 1) {
        return HTML;
      } else if (index == 2) {
        return CHECKBOX;
      } else {
        return TEXT;
      }
    }
  }

  public void onModuleLoad() {
    createTree(2, 11, TreeType.TEXT);
    createTree(10, 10, TreeType.CHECKBOX);
    createTree(4, 16, TreeType.HTML);
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

  private void createTree(int numBranches, int numNodes, TreeType type) {
    Tree t = new Tree();
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

}
