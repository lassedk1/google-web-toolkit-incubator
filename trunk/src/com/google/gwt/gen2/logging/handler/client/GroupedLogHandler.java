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

package com.google.gwt.gen2.logging.handler.client;

import com.google.gwt.gen2.logging.shared.LogEvent;
import com.google.gwt.gen2.logging.shared.LogFormatter;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * Publishes log messages into a tree grouped by category.
 */
public class GroupedLogHandler extends ScrollableLogHandler<Tree> {
  private TreeItem collated;
  private TreeItem topLevel;

  /**
   * Constructor.
   */
  public GroupedLogHandler() {
    super("gwt-GroupedLogHandler");
  }

  public void onLog(LogEvent e) {
    TreeItem parent;
    String category = e.getCategory();
    if (category == null) {
      parent = topLevel;
    } else {
      parent = collated;
      String[] args = LogHandlerUtil.splitCategory(category);

      for (int i = 0; i < args.length; i++) {
        parent = ensureChild(parent, args[i]);
      }
    }

    TreeItem target = new TreeItem(LogFormatter.HTML.format(e));
    parent.addItem(target);
    getContents().setSelectedItem(target);
    getContents().ensureSelectedItemVisible();
  }

  @Override
  protected Tree createContents() {
    Tree tree = new Tree();
    collated = tree.addItem("collated");
    topLevel = tree.addItem("top");
    return tree;
  }

  private TreeItem ensureChild(TreeItem parent, String childName) {
    TreeItem key = null;
    for (int j = 0; j < parent.getChildCount(); j++) {
      TreeItem child = parent.getChild(j);
      if (child.getText().equals(childName)) {
        key = child;
        break;
      }
    }
    if (key == null) {
      key = parent.addItem(childName);
    }
    return key;
  }
}
