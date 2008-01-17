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
package com.google.gwt.widgetideas.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.overrides.DOMHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * An item that can be contained within a
 * {@link com.google.gwt.widgetideas.client.FastTree}.
 * <p>
 * <h3>Example</h3>
 * {@example com.google.gwt.examples.TreeExample}
 */

public class FastTreeItem extends UIObject implements HasHTML, HasFastTreeItems {
  private static final String STYLENAME_SELECTED = "selected";

  // If an element was open, has all of its children removed, then new children
  // added, it should become open again.
  private static final int TREE_NODE_LEAF_OPEN = 0;
  private static final int TREE_NODE_LEAF = 1;
  private static final int TREE_NODE_INTERIOR_NEVER_OPENED = 2;
  private static final int TREE_NODE_INTERIOR_OPEN = 3;
  private static final int TREE_NODE_INTERIOR_CLOSED = 4;
  private static final String STYLENAME_CHILDREN = "children";

  private static final String STYLENAME_LEAF = "leaf";
  private static final String STYLENAME_DEFAULT = "gwt-FastTreeItem";
  private static final String STYLENAME_OPEN = "open";
  private static final String STYLENAME_CLOSED = "closed";

  /**
   * The base tree item element that will be cloned.
   */
  private static Element TREE_LEEF;

  /**
   * Static constructor to set up clonable elements.
   */
  static {

    // Create the base element that will be cloned
    TREE_LEEF = DOM.createDiv();
    // Internal contents.
    Element contentElem = DOM.createDiv();
    setStyleName(TREE_LEEF, STYLENAME_DEFAULT);
    setStyleName(contentElem, STYLENAME_LEAF);
    DOM.appendChild(TREE_LEEF, contentElem);
  }

  private int state = TREE_NODE_LEAF;
  private ArrayList children;
  private Element contentElem, childElems;
  private FastTreeItem parent;
  private FastTree tree;
  private Widget widget;

  /**
   * Creates an empty tree item.
   */
  public FastTreeItem() {
    Element elem = DOMHelper.clone(TREE_LEEF, true);
    setElement(elem);
    contentElem = DOMHelper.rawFirstChild(elem);
  }

  /**
   * Constructs a tree item with the given HTML.
   * 
   * @param html the item's HTML
   */
  public FastTreeItem(String html) {
    this();
    DOM.setInnerHTML(contentElem, html);
  }

  /**
   * Constructs a tree item with the given <code>Widget</code>.
   * 
   * @param widget the item's widget
   */
  public FastTreeItem(Widget widget) {
    this();
    addWidget(widget);
  }

  public void addItem(FastTreeItem item) {
    // Detach item from existing parent.
    if ((item.getParentItem() != null) || (item.getTree() != null)) {
      item.remove();
    }


    if (children == null) {
      // Never had children.
      if (isLeafNode()) {
        becomeInteriorNode();
      }
      childElems = DOM.createDiv();
      DOM.appendChild(getElement(), childElems);
      UIObject.setStyleName(childElems, STYLENAME_CHILDREN);
      children = new ArrayList();
    } else if (isLeafNode()) {
      // Previously had children.
      if (state == TREE_NODE_LEAF) {
        becomeInteriorNode();
      } else{
        state = TREE_NODE_INTERIOR_OPEN;
        updateState();
      }
      setVisible(childElems, true);
    }

    // Logical attach.
    item.setParentItem(this);
    children.add(item);

    // Physical attach.
    DOM.appendChild(childElems, item.getElement());

    // Adopt.
    if (tree != null) {
      item.setTree(tree);
    }

    if (children.size() == 1) {
      updateState();
    }
  }

  public FastTreeItem addItem(String itemText) {
    FastTreeItem ret = new FastTreeItem(itemText);
    addItem(ret);
    return ret;
  }

  public FastTreeItem addItem(Widget widget) {
    FastTreeItem ret = new FastTreeItem(widget);
    addItem(ret);
    return ret;
  }

  /**
   * Become an interior node.
   */
  public void becomeInteriorNode() {
    if (!isInteriorNode()) {
      state = TREE_NODE_INTERIOR_NEVER_OPENED;
      setStyleName(contentElem, STYLENAME_CLOSED);
    }
  }

  public FastTreeItem getChild(int index) {
    if ((index < 0) || (index >= getChildCount())) {
      throw new IndexOutOfBoundsException("No child at index " + index);
    }

    return (FastTreeItem) children.get(index);
  }

  public int getChildCount() {
    if (children == null) {
      return 0;
    }
    return children.size();
  }

  public int getChildIndex(FastTreeItem child) {
    if (children == null) {
      return -1;
    }
    return children.indexOf(child);
  }

  /**
   * Returns the width of the control open/close image. Must be overridden if
   * the TreeItem is using a control image that is <i>not</i> 16 pixels wide.
   * 
   * @return the width of the control image
   */
  public int getControlImageWidth() {
    return 16;
  }

  public String getHTML() {
    return DOM.getInnerHTML(contentElem);
  }

  /**
   * Gets this item's parent.
   * 
   * @return the parent item
   */
  public FastTreeItem getParentItem() {
    return parent;
  }

  public String getText() {
    return DOM.getInnerText(contentElem);
  }

  /**
   * Gets the tree that contains this item.
   * 
   * @return the containing tree
   */
  public final FastTree getTree() {
    return tree;
  }

  /**
   * Gets the <code>Widget</code> associated with this tree item.
   */
  public Widget getWidget() {
    return widget;
  }

  /**
   * Has this {@link FastTreeItem} ever been opened?
   * 
   * @return whether the {@link FastTreeItem} has ever been opened.
   */
  public boolean hasBeenOpened() {
    return state == TREE_NODE_INTERIOR_OPEN;
  }

  /**
   * Does this {@link FastTreeItem} represent an interior node?
   */
  public boolean isInteriorNode() {
    return state >= TREE_NODE_INTERIOR_NEVER_OPENED;
  }

  /**
   * Is this {@link FastTreeItem} a leaf node?
   */
  public boolean isLeafNode() {
    return state <= TREE_NODE_LEAF;
  }

  /**
   * Is the {@link FastTreeItem} open? Returns false if the {@link FastTreeItem}
   * is closed or a leaf node.
   */
  public boolean isOpen() {
    return state == TREE_NODE_INTERIOR_OPEN;
  }

  /**
   * Determines whether this item is currently selected.
   * 
   * @return <code>true</code> if it is selected
   */
  public boolean isSelected() {
    if (tree == null) {
      return false;
    } else {
      return tree.getSelectedItem() == this;
    }
  }

  /**
   * Returns whether the tree is currently showing this {@link FastTreeItem}.
   */
  public boolean isShowing() {
    if (tree == null || isVisible() == false) {
      return false;
    } else if (parent == null) {
      return true;
    } else if (!parent.isOpen()) {
      return false;
    } else {
      return parent.isShowing();
    }
  }

  /**
   * Removes this item from its tree.
   */
  public void remove() {
    if (parent != null) {
      // If this item has a parent, remove self from it.
      parent.removeItem(this);
    } else if (tree != null) {
      // If the item has no parent, but is in the Tree, it must be a top-level
      // element.
      tree.removeItem(this);
    }
  }

  public void removeItem(FastTreeItem item) {
    // Validate.
    if (children == null || !children.contains(item)) {
      return;
    }

    // Orphan.
    item.clearTree();

    // Physical detach.
    DOM.removeChild(childElems, item.getElement());

    // Logical detach.
    item.setParentItem(null);
    children.remove(item);

    if (children.size() == 0) {
      if (childElems != null) {
        UIObject.setVisible(childElems, false);
      }
      becomeLeaf();
      return;
    }

    updateState();
  }

  /**
   * Removes all of this item's children.
   */
  public void removeItems() {
    while (getChildCount() > 0) {
      removeItem(getChild(0));
    }
  }

  public void setHTML(String html) {
    clearWidget();
    DOM.setInnerHTML(contentElem, html);
  }

  /**
   * Sets whether this item's children are displayed.
   * 
   * @param open whether the item is open
   */
  public final void setState(boolean open) {
    setState(open, true);
  }

  /**
   * Sets whether this item's children are displayed.
   * 
   * @param open whether the item is open
   * @param fireEvents <code>true</code> to allow open/close events to be
   *        fired
   */
  public void setState(boolean open, boolean fireEvents) {
    if (open == isOpen()) {
      return;
    }
    // Cannot open leaf nodes.
    if (isLeafNode()){
      return;
    }
    if (open) {
      if (state == TREE_NODE_INTERIOR_NEVER_OPENED) {
        ensureChildren();
      }
      state = TREE_NODE_INTERIOR_OPEN;
      beforeOpen();
    } else {
      state = TREE_NODE_INTERIOR_CLOSED;
    }

    updateState();
  }

  public void setText(String text) {
    clearWidget();
    DOM.setInnerText(contentElem, text);
  }

  public void setWidget(Widget widget) {
    // Physical detach old from self.
    // Clear out any existing content before adding a widget.
    DOM.setInnerHTML(contentElem, "");
    clearWidget();
    addWidget(widget);
  }

  /**
   * Called before the tree item is opened.
   */
  protected void beforeOpen() {
  }

  /**
   * Called when tree item is being unselected. Returning <code>false</code>
   * cancels the unselection.
   * 
   */
  protected boolean beforeSelectionLost() {
    return true;
  }

  /**
   * Fired when a tree item receives a request to open for the first time.
   * Should be overridden in child clases.
   */
  protected void ensureChildren() {
  }

  /**
   * Returns the widget, if any, that should be focused on if this TreeItem is
   * selected.
   * 
   * @return widget to be focused.
   */
  protected HasFocus getFocusableWidget() {
    Widget w = getWidget();
    if (w instanceof HasFocus) {
      return (HasFocus) w;
    } else {
      return null;
    }
  }

  /**
   * Called when a tree item is selected.
   * 
   */
  protected void onSelected() {
  }

  void clearTree() {
    if (tree != null) {
      if (widget != null) {
        tree.treeOrphan(widget);
      }
      for (int i = 0, n = getChildCount(); i < n; ++i) {
        ((FastTreeItem) children.get(i)).clearTree();
      }
    }
  }

  void dumpTreeItems(List accum) {
    if (isInteriorNode()) {
      for (int i = 0; i < children.size(); i++) {
        FastTreeItem item = (FastTreeItem) children.get(i);
        accum.add(item);
        item.dumpTreeItems(accum);
      }
    }
  }

  ArrayList getChildren() {
    return children;
  }

  Element getContentElem() {
    return contentElem;
  }

  void setParentItem(FastTreeItem parent) {
    this.parent = parent;
  }

  /**
   * Selects or deselects this item.
   * 
   * @param selected <code>true</code> to select the item, <code>false</code>
   *        to deselect it
   */
  void setSelection(boolean selected) {
    setStyleName(contentElem, STYLENAME_SELECTED, selected);
    if (selected) {
      onSelected();
    }
  }

  void setTree(FastTree newTree) {
    if (tree == newTree) {
      return;
    }

    // Early out.
    if (tree != null) {
      throw new IllegalStateException(
          "Each Tree Item can only be added to one tree");
    }
    tree = newTree;

    if (widget != null) {
      // Add my widget to the new tree.
      tree.adopt(widget, this);
    }

    for (int i = 0, n = getChildCount(); i < n; ++i) {
      ((FastTreeItem) children.get(i)).setTree(newTree);
    }
  }

  void updateState() {
    // No work to be done.
    if (isLeafNode()) {
      return;
    }
    if (isOpen()) {
      if (getChildCount() > 0) {
        UIObject.setVisible(childElems, true);
      }
      showOpenImage();
    } else {
      if (getChildCount() > 0) {
        UIObject.setVisible(childElems, false);
      }
      showClosedImage();
    }
  }

  /**
   * Adds a widget to an already empty {@link FastTreeItem}.
   */
  private void addWidget(Widget newWidget) {
    // Detach new child from old parent.
    if (newWidget != null) {
      newWidget.removeFromParent();
    }

    // Logical detach old/attach new.
    widget = newWidget;

    if (newWidget != null) {
      // Physical attach new.
      DOM.appendChild(contentElem, newWidget.getElement());

      // Attach child to tree.
      if (tree != null) {
        tree.adopt(widget, this);
      }
    }
  }

  private void becomeLeaf() {
    if (isOpen()) {
      state = TREE_NODE_LEAF_OPEN;
    } else {
      state = TREE_NODE_LEAF;
    }
    setStyleName(contentElem, STYLENAME_LEAF);
  }

  private void clearWidget() {
    // Detach old child from tree.
    if (widget != null && tree != null) {
      tree.treeOrphan(widget);
      widget = null;
    }
  }

  private void showClosedImage() {
    setStyleName(contentElem, STYLENAME_CLOSED);
  }

  private void showOpenImage() {
    setStyleName(contentElem, STYLENAME_OPEN);
  }

}
