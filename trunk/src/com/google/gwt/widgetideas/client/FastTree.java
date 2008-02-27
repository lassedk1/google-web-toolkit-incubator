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

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.DataResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.TextResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FocusListenerCollection;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.KeyboardListenerCollection;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.google.gwt.widgetideas.client.overrides.DOMHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A standard hierarchical tree widget. The tree contains a hierarchy of
 * {@link FastTreeItem}s.
 * <p>
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.gwt-FastTree { the tree itself }</li>
 * <li>.gwt-FastTree .gwt-FastTreeItem { a tree item }</li>
 * <li>.gwt-FastTree .selection-bar {the selection bar used to highlight the
 * selected tree item}</li>
 * </ul>
 */
public class FastTree extends Panel implements HasWidgets, HasFocus,
    HasFastTreeItems {

  /**
   * Resources used.
   */
  public interface DefaultResources extends ImmutableResourceBundle {

    /**
     * @gwt.resource FastTree.css
     */
    public TextResource css();

    /**
     * @gwt.resource FastTreeRTL.css
     */
    public TextResource cssRTL();

    /**
     * @gwt.resource selectionBar.gif
     */
    public DataResource selectionBar();

    /**
     * @gwt.resource treeClosed.gif
     */

    public DataResource treeClosed();

    /**
     * @gwt.resource treeOpen.gif
     */
    public DataResource treeOpen();
  }

  private static final String STYLENAME_DEFAULT = "gwt-FastTree";

  private static final String STYLENAME_SELECTION = "selection-bar";

  private static FocusImpl impl = (FocusImpl) GWT.create(FocusImpl.class);

  /**
   * Add the default style sheet and images.
   */
  public static void addDefaultCSS() {
    DefaultResources instance = (DefaultResources) GWT.create(DefaultResources.class);
    if (LocaleInfo.getCurrentLocale().isRTL()) {
      StyleInjector.injectStylesheet(instance.cssRTL().getText(), instance);
    } else {
      StyleInjector.injectStylesheet(instance.css().getText(), instance);
    }
  }

  private boolean lostMouseDown = true;
  /**
   * Map of TreeItem.widget -> TreeItem.
   */
  private final Map childWidgets = new HashMap();
  private FastTreeItem curSelection;
  private final Element focusable;
  private FocusListenerCollection focusListeners;
  private KeyboardListenerCollection keyboardListeners;
  private MouseListenerCollection mouseListeners;
  private final FastTreeItem root;
  private Event keyDown;

  private Event lastKeyDown;

  /**
   * Constructs a tree.
   */
  public FastTree() {

    setElement(DOM.createDiv());

    focusable = createFocusElement();
    setStyleName(focusable, STYLENAME_SELECTION);

    sinkEvents(Event.MOUSEEVENTS | Event.ONCLICK | Event.KEYEVENTS
        | Event.MOUSEEVENTS);

    // The 'root' item is invisible and serves only as a container
    // for all top-level items.
    root = new FastTreeItem() {
      public void addItem(FastTreeItem item) {
        super.addItem(item);

        DOM.appendChild(FastTree.this.getElement(), item.getElement());

        // Explicitly set top-level items' parents to null.
        item.setParentItem(null);

        // Use no margin on top-most items.
        DOM.setIntStyleAttribute(item.getElement(), "margin", 0);
      }

      public void removeItem(FastTreeItem item) {
        if (!getChildren().contains(item)) {
          return;
        }

        // Update Item state.
        item.clearTree();
        item.setParentItem(null);
        getChildren().remove(item);

        DOM.removeChild(FastTree.this.getElement(), item.getElement());
      }
    };
    root.setTree(this);

    setStyleName(STYLENAME_DEFAULT);
    moveSelectionBar(curSelection);
  }

  /**
   * Adds the widget as a root tree item.
   * 
   * @see com.google.gwt.user.client.ui.HasWidgets#add(com.google.gwt.user.client.ui.Widget)
   * @param widget widget to add.
   */
  public void add(Widget widget) {
    addItem(widget);
  }

  public void addFocusListener(FocusListener listener) {
    if (focusListeners == null) {
      focusListeners = new FocusListenerCollection();
    }
    focusListeners.add(listener);
  }

  /**
   * Adds an item to the root level of this tree.
   * 
   * @param item the item to be added
   */
  public void addItem(FastTreeItem item) {
    root.addItem(item);
  }

  /**
   * Adds a simple tree item containing the specified text.
   * 
   * @param itemText the text of the item to be added
   * @return the item that was added
   */
  public FastTreeItem addItem(String itemText) {
    FastTreeItem ret = new FastTreeItem(itemText);
    addItem(ret);

    return ret;
  }

  /**
   * Adds a new tree item containing the specified widget.
   * 
   * @param widget the widget to be added
   */
  public FastTreeItem addItem(Widget widget) {
    return root.addItem(widget);
  }

  public void addKeyboardListener(KeyboardListener listener) {
    if (keyboardListeners == null) {
      keyboardListeners = new KeyboardListenerCollection();
    }
    keyboardListeners.add(listener);
  }

  public void addMouseListener(MouseListener listener) {
    if (mouseListeners == null) {
      mouseListeners = new MouseListenerCollection();
    }
    mouseListeners.add(listener);
  }

  /**
   * Clears all tree items from the current tree.
   */
  public void clear() {
    int size = root.getChildCount();
    for (int i = size - 1; i >= 0; i--) {
      root.getChild(i).remove();
    }
  }

  /**
   * Ensures that the currently-selected item is visible, opening its parents
   * and scrolling the tree as necessary.
   */
  public void ensureSelectedItemVisible() {
    if (curSelection == null) {
      return;
    }

    FastTreeItem parent = curSelection.getParentItem();
    while (parent != null) {
      parent.setState(true);
      parent = parent.getParentItem();
    }
    moveFocus(curSelection);
  }

  public FastTreeItem getChild(int index) {
    return root.getChild(index);
  }

  public int getChildCount() {
    return root.getChildCount();
  }

  public int getChildIndex(FastTreeItem child) {
    return root.getChildIndex(child);
  }

  /**
   * Gets the top-level tree item at the specified index.
   * 
   * @param index the index to be retrieved
   * @return the item at that index
   */
  public FastTreeItem getItem(int index) {
    return root.getChild(index);
  }

  /**
   * Gets the number of items contained at the root of this tree.
   * 
   * @return this tree's item count
   */
  public int getItemCount() {
    return root.getChildCount();
  }

  /**
   * Gets the currently selected item.
   * 
   * @return the selected item
   */
  public FastTreeItem getSelectedItem() {
    return curSelection;
  }

  public int getTabIndex() {
    return impl.getTabIndex(focusable);
  }

  public Iterator iterator() {
    final Widget[] widgets = new Widget[childWidgets.size()];
    childWidgets.keySet().toArray(widgets);
    return WidgetIterators.createWidgetIterator(this, widgets);
  }

  public void onBrowserEvent(Event event) {
    int eventType = DOM.eventGetType(event);

    switch (eventType) {
      case Event.ONCLICK: {
        Element e = DOM.eventGetTarget(event);
        if (shouldTreeDelegateFocusToElement(e)) {
          // The click event should have given focus to this element already.
          // Avoid moving focus back up to the tree (so that focusable widgets
          // attached to TreeItems can receive keyboard events).
        } else {
          clickedOnFocus(DOM.eventGetTarget(event));
        }
        break;
      }

      case Event.ONMOUSEMOVE: {
        if (mouseListeners != null) {
          mouseListeners.fireMouseEvent(this, event);
        }
        break;
      }

      case Event.ONMOUSEUP: {
        if (lostMouseDown) {
          // artificial mouse down due to IE bug where mouse downs are lost.
          elementClicked(root, event);
        }
        if (mouseListeners != null) {
          mouseListeners.fireMouseEvent(this, event);
        }
        lostMouseDown = true;
        break;
      }
      case Event.ONMOUSEDOWN: {
        lostMouseDown = false;
        if (mouseListeners != null) {
          mouseListeners.fireMouseEvent(this, event);
        }
        elementClicked(root, event);
        break;
      }
      case Event.ONMOUSEOVER: {
        if (mouseListeners != null) {
          mouseListeners.fireMouseEvent(this, event);
        }
        break;
      }

      case Event.ONMOUSEOUT: {
        if (mouseListeners != null) {
          mouseListeners.fireMouseEvent(this, event);
        }
        break;
      }

      case Event.ONFOCUS:
        // If we already have focus, ignore the focus event.
        if (focusListeners != null) {
          focusListeners.fireFocusEvent(this, event);
        }
        break;

      case Event.ONBLUR: {
        if (focusListeners != null) {
          focusListeners.fireFocusEvent(this, event);
        }

        break;
      }

      case Event.ONKEYDOWN:
        keyDown = event;
        // Intentional fallthrough.
      case Event.ONKEYUP:
        if (eventType == Event.ONKEYUP) {
          // If we got here because of a key tab, then we need to make sure the
          // current tree item is selected.
          if (DOM.eventGetKeyCode(event) == KeyboardListener.KEY_TAB) {
            ArrayList chain = new ArrayList();
            collectElementChain(chain, getElement(), DOM.eventGetTarget(event));
            FastTreeItem item = findItemByChain(chain, 0, root);
            if (item != getSelectedItem()) {
              setSelectedItem(item, true);
            }
          }
        }

        // Intentional fall through.
      case Event.ONKEYPRESS: {

        if (keyboardListeners != null) {
          keyboardListeners.fireKeyboardEvent(this, event);
        }

        // Trying to avoid duplicate key downs and fire navigation despite
        // missing key downs.
        if (eventType != Event.ONKEYUP) {
          if (lastKeyDown == null || (!lastKeyDown.equals(keyDown))) {
            keyboardNavigation(event);
          }
          if (eventType == Event.ONKEYPRESS) {
            lastKeyDown = null;
          } else {
            lastKeyDown = keyDown;
          }
        }
        if (DOMHelper.isArrowKey(DOM.eventGetKeyCode(event))) {
          DOM.eventCancelBubble(event, true);
          DOM.eventPreventDefault(event);
        }
        break;
      }
    }

    // We must call SynthesizedWidget's implementation for all other events.
    super.onBrowserEvent(event);
  }

  public boolean remove(Widget w) {
    // Validate.
    FastTreeItem item = (FastTreeItem) childWidgets.get(w);
    if (item == null) {
      return false;
    }

    // Delegate to TreeItem.setWidget, which performs correct removal.
    item.setWidget(null);
    return true;
  }

  public void removeFocusListener(FocusListener listener) {
    if (focusListeners != null) {
      focusListeners.remove(listener);
    }
  }

  /**
   * Removes an item from the root level of this tree.
   * 
   * @param item the item to be removed
   */
  public void removeItem(FastTreeItem item) {
    root.removeItem(item);
  }

  /**
   * Removes all items from the root level of this tree.
   */
  public void removeItems() {
    while (getItemCount() > 0) {
      removeItem(getItem(0));
    }
  }

  public void removeKeyboardListener(KeyboardListener listener) {
    if (keyboardListeners != null) {
      keyboardListeners.remove(listener);
    }
  }

  public void setAccessKey(char key) {
    impl.setAccessKey(focusable, key);
  }

  public void setFocus(boolean focus) {
    if (focus) {
      impl.focus(focusable);
    } else {
      impl.blur(focusable);
    }
  }

  /**
   * Selects a specified item.
   * 
   * @param item the item to be selected, or <code>null</code> to deselect all
   *          items
   */
  public void setSelectedItem(FastTreeItem item) {
    setSelectedItem(item, true);
  }

  /**
   * Selects a specified item.
   * 
   * @param item the item to be selected, or <code>null</code> to deselect all
   *          items
   * @param fireEvents <code>true</code> to allow selection events to be fired
   */
  public void setSelectedItem(FastTreeItem item, boolean fireEvents) {
    if (item == null) {
      if (curSelection == null) {
        return;
      }
      curSelection.setSelection(false);
      curSelection = null;
      return;
    }

    onSelection(item, fireEvents, true);
  }

  public void setTabIndex(int index) {
    impl.setTabIndex(focusable, index);
  }

  /**
   * Iterator of tree items.
   */
  public Iterator treeItemIterator() {
    List accum = new ArrayList();
    root.dumpTreeItems(accum);
    return accum.iterator();
  }

  protected void doAttachChildren() {
    super.doAttachChildren();
    DOM.setEventListener(focusable, this);
  }

  protected void doDetachChildren() {
    super.doDetachChildren();
    DOM.setEventListener(focusable, null);
  }

  protected FastTreeItem getRoot() {
    return root;
  }

  /**
   * Moves the selection bar around the given {@link FastTreeItem}.
   * 
   * @param item the item to move selection bar to
   */
  protected void moveSelectionBar(FastTreeItem item) {
    if (item == null || item.isShowing() == false) {
      UIObject.setVisible(focusable, false);
      return;
    }
    // focusable is being used for highlight as well.
    // Get the location and size of the given item's content element relative
    // to the tree.
    Element selectedElem = item.getContentElem();
    moveElementOverTarget(focusable, selectedElem);
    UIObject.setVisible(focusable, true);
  }

  protected void onLoad() {
    if (getSelectedItem() != null) {
      moveSelectionBar(getSelectedItem());
    }
  }

  protected void onSelection(FastTreeItem item, boolean fireEvents,
      boolean moveFocus) {
    // 'root' isn't a real item, so don't let it be selected
    // (some cases in the keyboard handler will try to do this)
    if (item == root) {
      return;
    }

    if (curSelection == item) {
      return;
    }
    if (curSelection != null) {
      if (curSelection.beforeSelectionLost() == false) {
        return;
      }
      curSelection.setSelection(false);
    }

    curSelection = item;

    if (curSelection != null) {
      if (moveFocus) {
        moveFocus(curSelection);
      } else {
        // Move highlight even if we do no not need to move focus.
        moveSelectionBar(curSelection);
      }

      // Select the item and fire the selection event.
      curSelection.setSelection(true);
    }
  }

  /**
   * This method is called immediately before a widget will be detached from the
   * browser's document.
   */
  protected void onUnload() {
  }

  void adopt(Widget widget, FastTreeItem treeItem) {
    assert (!childWidgets.containsKey(widget));
    childWidgets.put(widget, treeItem);
    super.adopt(widget);
  }

  /*
   * This method exists solely to support unit tests.
   */
  Map getChildWidgets() {
    return childWidgets;
  }

  void treeOrphan(Widget widget) {
    super.orphan(widget);

    // Logical detach.
    childWidgets.remove(widget);
  }

  private void clickedOnFocus(Element e) {
    // An element was clicked on that is not focusable, so we use the hidden
    // focusable to not shift focus.
    moveElementOverTarget(focusable, e);
    impl.focus(focusable);
  }

  /**
   * Collects parents going up the element tree, terminated at the tree root.
   */
  private void collectElementChain(ArrayList chain, Element hRoot, Element hElem) {
    if ((hElem == null) || DOM.compare(hElem, hRoot)) {
      return;
    }

    collectElementChain(chain, hRoot, DOM.getParent(hElem));
    chain.add(hElem);
  }

  private Element createFocusElement() {
    Element e = impl.createFocusable();
    DOM.setStyleAttribute(e, "position", "absolute");
    DOM.appendChild(getElement(), e);
    DOM.sinkEvents(e, Event.FOCUSEVENTS | Event.ONMOUSEDOWN);
    // Needed for IE only
    DOM.setElementAttribute(e, "focus", "false");
    return e;
  }

  /**
   * Disables the selection text on IE.
   */
  private native void disableSelection(Element element) /*-{
           element.onselectstart = function() {
              return false;
           };
        }-*/;

  private boolean elementClicked(FastTreeItem root, Event event) {
    Element target = DOM.eventGetTarget(event);
    ArrayList chain = new ArrayList();
    collectElementChain(chain, getElement(), target);
    FastTreeItem item = findItemByChain(chain, 0, root);
    if (item != null) {
      if (item.isInteriorNode()) {
        boolean switchState;
        if (LocaleInfo.getCurrentLocale().isRTL()) {
          switchState = getStyleName(target).indexOf("placeholder") != -1;
        } else {
          int mousePos = DOM.eventGetClientX(event);
          switchState = DOM.isOrHasChild(item.getControlElement(), target)
              && (mousePos - item.getAbsoluteLeft() < item.getControlImageWidth());
        }
        if (switchState) {
          item.setState(!item.isOpen(), true);
          moveSelectionBar(curSelection);
          disableSelection(target);
          return false;
        }
      }
      onSelection(item, true, !shouldTreeDelegateFocusToElement(target));
      return true;
    }
    return false;
  }

  private FastTreeItem findDeepestOpenChild(FastTreeItem item) {
    if (!item.isOpen()) {
      return item;
    }
    return findDeepestOpenChild(item.getChild(item.getChildCount() - 1));
  }

  private FastTreeItem findItemByChain(ArrayList chain, int idx,
      FastTreeItem root) {
    if (idx == chain.size()) {
      return root;
    }

    Element hCurElem = (Element) chain.get(idx);
    for (int i = 0, n = root.getChildCount(); i < n; ++i) {
      FastTreeItem child = root.getChild(i);
      if (DOM.compare(child.getElement(), hCurElem)) {
        FastTreeItem retItem = findItemByChain(chain, idx + 1, root.getChild(i));
        if (retItem == null) {
          return child;
        }
        return retItem;
      }
    }

    return findItemByChain(chain, idx + 1, root);
  }

  private void keyboardNavigation(Event event) {
    // If nothing's selected, select the first item.
    if (curSelection == null) {
      if (root.getChildCount() > 0) {
        onSelection(root.getChild(0), true, true);
      }
      super.onBrowserEvent(event);
    } else {

      // Handle keyboard events if keyboard navigation is enabled

      switch (DOMHelper.standardizeKeycode(DOM.eventGetKeyCode(event))) {
        case KeyboardListener.KEY_UP: {
          moveSelectionUp(curSelection);
          break;
        }
        case KeyboardListener.KEY_DOWN: {
          moveSelectionDown(curSelection, true);
          break;
        }
        case KeyboardListener.KEY_LEFT: {
          if (curSelection.isOpen()) {
            curSelection.setState(false);
          } else {
            FastTreeItem parent = curSelection.getParentItem();
            if (parent != null) {
              setSelectedItem(parent);
            }
          }
          break;
        }
        case KeyboardListener.KEY_RIGHT: {
          if (!curSelection.isOpen()) {
            curSelection.setState(true);
          }
          // Do nothing if the element is already open.
          break;
        }
      }
    }
  }

  private void moveElementOverTarget(Element movable, Element target) {
    int containerTop = getAbsoluteTop();

    int top = DOM.getAbsoluteTop(target) - containerTop;
    int height = DOM.getElementPropertyInt(target, "offsetHeight");

    // Set the element's position and size to exactly underlap the
    // item's content element.

    DOM.setStyleAttribute(movable, "height", height + "px");
    DOM.setStyleAttribute(movable, "top", top + "px");
  }

  /**
   * Move the tree focus to the specified selected item.
   * 
   * @param selection
   */
  private void moveFocus(FastTreeItem selection) {
    moveSelectionBar(selection);
    DOM.scrollIntoView(focusable);
    HasFocus focusableWidget = selection.getFocusableWidget();
    if (focusableWidget != null) {
      focusableWidget.setFocus(true);
    } else {
      // Ensure Focus is set, as focus may have been previously delegated by
      // tree.
      impl.focus(focusable);
    }
  }

  /**
   * Moves to the next item, going into children as if dig is enabled.
   */
  private void moveSelectionDown(FastTreeItem sel, boolean dig) {
    if (sel == root) {
      return;
    }
    FastTreeItem parent = sel.getParentItem();
    if (parent == null) {
      parent = root;
    }
    int idx = parent.getChildIndex(sel);

    if (!dig || !sel.isOpen()) {
      if (idx < parent.getChildCount() - 1) {
        onSelection(parent.getChild(idx + 1), true, true);
      } else {
        moveSelectionDown(parent, false);
      }
    } else if (sel.getChildCount() > 0) {
      onSelection(sel.getChild(0), true, true);
    }
  }

  /**
   * Moves the selected item up one.
   */
  private void moveSelectionUp(FastTreeItem sel) {
    FastTreeItem parent = sel.getParentItem();
    if (parent == null) {
      parent = root;
    }
    int idx = parent.getChildIndex(sel);

    if (idx > 0) {
      FastTreeItem sibling = parent.getChild(idx - 1);
      onSelection(findDeepestOpenChild(sibling), true, true);
    } else {
      onSelection(parent, true, true);
    }
  }

  private native boolean shouldTreeDelegateFocusToElement(Element elem) /*-{
    var name = elem.nodeName;
    return ((name == "SELECT") ||
      (name == "INPUT")  ||
      (name == "TEXTAREA") ||
      (name == "OPTION") ||
      (name == "BUTTON") ||
      (name == "LABEL") 
    );
   }-*/;
}

/**
 * A collection of convenience factories for creating iterators for widgets.
 * This mostly helps developers support {@link HasWidgets} without having to
 * implement their own {@link Iterator}.
 */
class WidgetIterators {

  /**
   * Wraps an array of widgets to be returned during iteration.
   * <code>null</code> is allowed in the array and will be skipped during
   * iteration.
   * 
   * @param container the container of the widgets in <code>contained</code>
   * @param contained the array of widgets
   * @return the iterator
   */
  static final Iterator createWidgetIterator(final HasWidgets container,
      final Widget[] contained) {
    return new Iterator() {
      int index = -1, last = -1;
      boolean widgetsWasCopied = false;
      Widget[] widgets = contained;

      {
        gotoNextIndex();
      }

      public boolean hasNext() {
        return (index < contained.length);
      }

      public Object next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        last = index;
        final Widget w = contained[index];
        gotoNextIndex();
        return w;
      }

      public void remove() {
        if (last < 0) {
          throw new IllegalStateException();
        }

        if (!widgetsWasCopied) {
          widgets = copyWidgetArray(widgets);
          widgetsWasCopied = true;
        }

        container.remove(contained[last]);
        last = -1;
      }

      private void gotoNextIndex() {
        ++index;
        while (index < contained.length) {
          if (contained[index] != null) {
            return;
          }
          ++index;
        }
      }
    };
  }

  private static final Widget[] copyWidgetArray(final Widget[] widgets) {
    final Widget[] clone = new Widget[widgets.length];
    for (int i = 0; i < widgets.length; i++) {
      clone[i] = widgets[i];
    }
    return clone;
  }

  private WidgetIterators() {
    // Not instantiable.
  }
}
