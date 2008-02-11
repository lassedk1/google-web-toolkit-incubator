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

package com.google.gwt.libideas.logging.client;

import com.google.gwt.libideas.logging.shared.Level;
import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.FastTree;
import com.google.gwt.widgetideas.client.FastTreeItem;
import com.google.gwt.widgetideas.client.HasFastTreeItems;

/**
 * Publishes log messages into a popup tree sorted by category.
 */
public class TreeHandler extends PopupWidgetHandler {
  private FastTree tree = new FastTree();

  /**
   * TreeHandler, used only in development.
   * 
   * @param autoShow is the tree handler shown when new logging messages are
   *          added.
   */
  public TreeHandler(boolean autoShow) {
    super(autoShow);
    FastTree.addDefaultCSS();
    popup.setWidget(tree);
  }

  /**
   * Clears the contents of the current log messages.
   */
  public void clear() {
    tree = new FastTree();
    popup.setWidget(tree);
  }

  public FastTreeItem getChild(HasFastTreeItems parent, String childName) {
    FastTreeItem key = null;
    for (int j = 0; j < parent.getChildCount(); j++) {
      FastTreeItem child = parent.getChild(j);
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

  public Widget getWidget() {
    return tree;
  }

  public void publish(String message, Level level, String category, Throwable e) {
    HasFastTreeItems parent = tree;
    if (category == null) {
      parent = getChild(tree, "default");
    } else {
      String[] args = Log.splitCategory(category);

      for (int i = 0; i < args.length; i++) {
        parent = getChild(parent, args[i]);
      }
    }
    FastTreeItem target = new FastTreeItem(message);
    parent.addItem(target);
    target.addStyleName(level.getName().toLowerCase());
    if (e != null) {
      target.addItem(DivHandler.formatMessage("thrown", level, e));
    }

    tree.setSelectedItem(target);
    tree.ensureSelectedItemVisible();
    if (autoShow) {
      popup.show();
    }
  }
}
