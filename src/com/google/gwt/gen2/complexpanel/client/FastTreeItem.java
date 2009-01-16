/*
 * Copyright 2009 Google Inc.
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
package com.google.gwt.gen2.complexpanel.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.overrides.DOMHelper;

import java.util.ArrayList;
import java.util.List;

public class FastTreeItem extends UIObject 
    implements HasFastTreeItems, HasHTML {

  enum TreeNodeState {
    TREE_NODE_LEAF, 
    TREE_NODE_INTERIOR_NEVER_OPENED,
    TREE_NODE_INTERIOR_OPEN,
    TREE_NODE_INTERIOR_CLOSED
  }
  
  private static final String STYLENAME_SELECTED = "selected";
  private static final String STYLENAME_CHILDREN = "children";
  private static final String STYLENAME_LEAF = "gwt-FastTreeItem-leaf";
  private static final String STYLENAME_LEAF_DEFAULT = "gwt-FastTreeItem " + STYLENAME_LEAF;
  private static final String STYLENAME_OPEN = "open";
  private static final String STYLENAME_CLOSED = "closed";
  private static final String STYLENAME_CONTENT = "treeItemContent";
  
  /**
   * Interface used to allow the widget access to css style names. 
   * <p/> 
   * The class names indicate the default gwt names for these styles.
   */
  public static interface Css extends FastTree.Css {
    
    String selected();
    
    String children();
    
    @ClassName(STYLENAME_LEAF)
    String leaf();
    
    String leaf_default();
    
    String open();
    
    String closed();
    
    @ClassName(STYLENAME_CONTENT)
    String content();   
  }
  
  /**
   * Creates the standard css instance used for this widget and groups all the
   * css processing under it.
   */
  static class StandardCss extends FastTree.StandardCss implements Css {
    
    public StandardCss(String styleName) {
      super(styleName);
    }
    
    public static Css DEFAULT_TREEITEM_CSS;
    
    static Css ensureDefaultCss() {
      if (DEFAULT_TREEITEM_CSS == null) {
        DEFAULT_TREEITEM_CSS = new StandardCss(FastTree.STYLENAME_DEFAULT);
      }
      return DEFAULT_TREEITEM_CSS;
    }

    /**
     * CSS resource.
     */
    public static interface Resources extends FastTree.StandardCss.Resources {
    }

    public String closed() {
      return pad(STYLENAME_CLOSED);
    }

    public String open() {
      return pad(STYLENAME_OPEN);
    }
    
    public String selected() {
      return pad(STYLENAME_SELECTED);
    }

    public String leaf() {
      return pad(STYLENAME_LEAF);
    }
    
    public String content() {
      return pad(STYLENAME_CONTENT);
    }
    
    public String children() {
      return pad(STYLENAME_CHILDREN);
    }
    
    private String pad(String styleName) {
      return " " + styleName;
    }

    @Override
    public String leaf_default() {
      return pad(STYLENAME_LEAF_DEFAULT);
    }    
  }

  /**
   * The base tree item element that will be cloned.
   */
  private static Element TREE_LEAF;

  /**
   * Static constructor to set up clonable elements.
   */
  static {
    if (GWT.isClient()) {
      // Create the base element that will be cloned
      TREE_LEAF = DOM.createDiv();
      // leaf contents.
      setStyleName(TREE_LEAF, STYLENAME_LEAF_DEFAULT);
      Element content = DOM.createDiv();
      setStyleName(content, STYLENAME_CONTENT);
      DOM.appendChild(TREE_LEAF, content);
    }
  }

  /*
   * Default state.
   */
  private TreeNodeState state = TreeNodeState.TREE_NODE_LEAF;
  private ArrayList<FastTreeItem> children;
  private Element contentElement, childElems;
  private FastTreeItem parent;
  /**
   * The tree that this is associated with currently.
   */
  private FastTree tree;
  private Widget widget;
  private Css css;

  /**
   * Creates an empty tree item.
   */
  public FastTreeItem() {
    css = StandardCss.ensureDefaultCss();
    Element elem = createLeafElement();
    setElement(elem);
  }

  /**
   * Constructs a tree item with the given HTML.
   * 
   * @param html the item's HTML
   */
  public FastTreeItem(String html) {
    this();
    DOM.setInnerHTML(getElementToAttach(), html);
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
    if (isLeafNode()) {
      becomeInteriorNode();
    }
    if (children == null) {
      // Never had children.
      children = new ArrayList<FastTreeItem>();
    }
    // Logical attach.
    item.setParentItem(this);
    children.add(item);

    // Physical attach.
    if (state != TreeNodeState.TREE_NODE_INTERIOR_NEVER_OPENED) {
      DOM.appendChild(childElems, item.getElement());
    }

    // Adopt.
    if (tree != null) {
      item.setTree(tree);
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
      state = TreeNodeState.TREE_NODE_INTERIOR_NEVER_OPENED;

      Element control = DOM.createDiv();
      setStyleName(control, css.closed());
      DOM.appendChild(control, contentElement);
      convertElementToInteriorNode(control);
    }
  }
  
  public void becomeLeaf() {
    becomeLeaf(true);
  }
  
  /**
   * @param removeChildNodes
   */
  public void becomeLeaf(boolean removeChildNodes) {
    if (!isLeafNode()) {
      
      // remove child nodes
      if (removeChildNodes) {
        if (isOpen()) {
          removeItems();
        } else {
          if (children != null) {
            children.clear();
          }
          children = null;
        }
      }
      
      // clear element
      getElement().setInnerHTML("");
      
      // set leaf state
      state = TreeNodeState.TREE_NODE_LEAF;     
      DOM.appendChild(getElement(), contentElement);
      setStyleName(getElement(), css.leaf_default());
    }
  }

  public FastTreeItem getChild(int index) {
    if ((index < 0) || (index >= getChildCount())) {
      throw new IndexOutOfBoundsException("No child at index " + index);
    }
    return children.get(index);
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
    return DOM.getInnerHTML(getElementToAttach());
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
    return DOM.getInnerText(getElementToAttach());
  }

  /**
   * Gets the tree that contains this item.
   * 
   * @return the containing tree
   */
  public FastTree getTree() {
    return tree;
  }

  /**
   * Gets the {@code Widget} associated with this tree item.
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
    return state == TreeNodeState.TREE_NODE_INTERIOR_OPEN;
  }

  /**
   * Does this {@link FastTreeItem} represent an interior node?
   */
  public boolean isInteriorNode() {
    return state.compareTo(TreeNodeState.TREE_NODE_INTERIOR_NEVER_OPENED) >= 0;
  }

  /**
   * Is this {@link FastTreeItem} a leaf node?
   */
  public boolean isLeafNode() {
    // return state <= TREE_NODE_LEAF;
    return state.compareTo(TreeNodeState.TREE_NODE_LEAF) < 1;
  }

  /**
   * Is the {@link FastTreeItem} open? Returns false if the {@link FastTreeItem}
   * is closed or a leaf node.
   */
  public boolean isOpen() {
    return state == TreeNodeState.TREE_NODE_INTERIOR_OPEN;
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

  /**
   * Removes an item from the tree. Note, if you want to switch the node back to
   * be a leaf node, must call becomeLeaf()
   */
  public void removeItem(FastTreeItem item) {
    // Validate.
    if (children == null || !children.contains(item)) {
      return;
    }

    // Orphan.
    item.clearTree();

    // Physical detach.
    if (state != TreeNodeState.TREE_NODE_INTERIOR_NEVER_OPENED) {
      DOM.removeChild(childElems, item.getElement());
    }

    // Logical detach.
    item.setParentItem(null);
    children.remove(item);
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
    DOM.setInnerHTML(getElementToAttach(), html);
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
   * @param fireEvents <code>true</code> to allow open/close events to be fired
   */
  public void setState(boolean open, boolean fireEvents) {
    if (open == isOpen()) {
      return;
    }
    // Cannot open leaf nodes.
    if (isLeafNode()) {
      return;
    }
    if (open) {
      boolean isFirstTime = state == TreeNodeState.TREE_NODE_INTERIOR_NEVER_OPENED;
      if (beforeOpen(isFirstTime)) {
        childElems = DOM.createDiv();
        UIObject.setStyleName(childElems, css.children());
        convertElementToHaveChildren(childElems);

        if (children != null) {
          for (FastTreeItem item : children) {
            DOM.appendChild(childElems, item.getElement());
          }
        }
      }

      state = TreeNodeState.TREE_NODE_INTERIOR_OPEN;
    } else {
      beforeClose();
      state = TreeNodeState.TREE_NODE_INTERIOR_CLOSED;
    }
    updateState();
    if (open) {
      afterOpen();
    } else {
      afterClose();
    }
  }

  public void setText(String text) {
    clearWidget();
    DOM.setInnerText(getElementToAttach(), text);
  }

  public void setWidget(Widget widget) {
    
    // Physical detach old from self.
    // Clear out any existing content before adding a widget.
    DOM.setInnerHTML(getElementToAttach(), "");
    clearWidget();
    addWidget(widget);
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
  
  void clearTree() {
    if (tree != null) {
      if (widget != null) {
        tree.treeOrphan(widget);
      }
      if (tree.getSelectedItem() == this) {
        tree.setSelectedItem(null);
      }
      tree = null;
      for (int i = 0, n = getChildCount(); i < n; ++i) {
        children.get(i).clearTree();
      }
    }
  }

  void convertElementToHaveChildren(Element children) {
    DOM.appendChild(getElement(), children);
  }

  void convertElementToInteriorNode(Element control) {
    
    // remove dependent style name
    setStyleName(getElement(), getStylePrimaryName() + "-leaf", false);
    DOM.appendChild(getElement(), control);
  }
  
  Element createLeafElement() {
    Element elem = DOMHelper.clone(TREE_LEAF, true);
    contentElement = DOMHelper.rawFirstChild(elem);
    return elem;
  }

  void dumpTreeItems(List<FastTreeItem> accum) {
    if (isInteriorNode() && getChildCount() > 0) {
      for (int i = 0; i < children.size(); i++) {
        FastTreeItem item = children.get(i);
        accum.add(item);
        item.dumpTreeItems(accum);
      }
    }
  }

  ArrayList<FastTreeItem> getChildren() {
    return children;
  }

  Element getContentElement() {
    return contentElement;
  }

  Element getControlElement() {
    return DOM.getParent(contentElement);
  }

  Element getElementToAttach() {
    return contentElement;
  }

  void setParentItem(FastTreeItem parent) {
    this.parent = parent;
  }

  /**
   * Selects or deselects this item.
   * 
   * @param selected {@code true} to select the item, {@code false} to deselect
   *        it.
   */
  void setSelection(boolean selected, boolean fireEvents) {
    tree.beforeSelected(this);
    setStyleName(getControlElement(), css.selected(), selected);
    if (selected && fireEvents) {
      onSelected();
    }
  }

  public void setTree(FastTree newTree) {
    if (this.tree == newTree) {
      return;
    }

    // Early out.
    if (this.tree != null) {
      throw new IllegalStateException(
          "Each Tree Item must be removed from its current tree before being added to another.");
    }
    this.tree = newTree;

    if (widget != null) {
      // Add my widget to the new tree.
      newTree.adopt(widget, this);
    }

    for (int i = 0, n = getChildCount(); i < n; ++i) {
      children.get(i).setTree(newTree);
    }
  }

  /**
   * Sets the state of the item.
   */
  void updateState() {
    // No work to be done.
    if (isLeafNode()) {
      return;
    }
    if (isOpen()) {
      showOpenImage();
      UIObject.setVisible(childElems, true);
    } else {
      showClosedImage();
      UIObject.setVisible(childElems, false);
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
      DOM.appendChild(getElementToAttach(), widget.getElement());
      // bidiSupport();
      // Attach child to tree.
      if (tree != null) {
        tree.adopt(widget, this);
      }
    }
  }

  private void clearWidget() {
    // Detach old child from tree.
    if (widget != null && this.tree != null) {
      this.tree.treeOrphan(widget);
      widget = null;
    }
  }
  
  private void showClosedImage() {
    setStyleName(getControlElement(), css.open(), false);
    setStyleName(getControlElement(), css.closed(), true);
  }

  private void showOpenImage() {
    setStyleName(getControlElement(), css.closed(), false);
    setStyleName(getControlElement(), css.open(), true);
  }
  
  /**
   * Called after the tree item is closed.
   */
  void afterClose() {
    tree.afterClose(this);
  }

  /**
   * Called after the tree item is opened.
   */
  void afterOpen() {
    tree.afterOpen(this);
  }

  /**
   * Called before the tree item is closed.
   */
  void beforeClose() {
    tree.beforeClose(this);
  }

  /**
   * Called before the tree item is opened.
   */
  boolean beforeOpen(boolean isFirstTime) {
    tree.beforeOpen(this, isFirstTime);
    return isFirstTime;
  }

  /**
   * Called when tree item is being unselected. Returning <code>false</code>
   * cancels the unselection.
   */
  boolean beforeSelectionLost() {
    return true;
  }

  /**
   * Called when a tree item is selected.
   */
  void onSelected() {
    tree.onSelected(this);
  } 
}
