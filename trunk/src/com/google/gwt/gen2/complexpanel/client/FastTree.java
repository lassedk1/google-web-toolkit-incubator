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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.gen2.commonevent.shared.BeforeCloseEvent;
import com.google.gwt.gen2.commonevent.shared.BeforeCloseHandler;
import com.google.gwt.gen2.commonevent.shared.BeforeOpenEvent;
import com.google.gwt.gen2.commonevent.shared.BeforeOpenHandler;
import com.google.gwt.gen2.commonwidget.client.Decorator;
import com.google.gwt.gen2.commonwidget.client.impl.StandardCssImpl;
import com.google.gwt.gen2.widgetbase.client.Gen2CssInjector;
import com.google.gwt.gen2.widgetbase.client.WidgetCss;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.DataResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Accessibility;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.google.gwt.widgetideas.client.event.KeyboardHandler;
import com.google.gwt.widgetideas.client.overrides.DOMHelper;
import com.google.gwt.widgetideas.client.overrides.WidgetIterators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FastTree 
    extends Panel 
    implements HasClickHandlers, HasMouseDownHandlers,
               HasSelectionHandlers<FastTreeItem>, HasFocusHandlers, 
               HasFastTreeItems, HasKeyDownHandlers, HasKeyPressHandlers,
               HasKeyUpHandlers, HasFastTreeItemHandlers {
  
  /**
   * Interface used to allow the widget access to css style names. 
   * <p/> 
   * The class names indicate the default gwt names for these styles.
   */
  static interface Css extends WidgetCss {
    
    @ClassName(STYLENAME_DEFAULT)
    String fastTree();

    @ClassName(STYLENAME_SELECTION)
    String selectionBar();
  }
  
  static class StandardCss extends StandardCssImpl implements Css {

    /**
     * CSS resource.
     */
    static interface Resources extends ImmutableResourceBundle {

      static final String PUBLIC_PATH = "com/google/gwt/gen2/widgetbase/public/";
      static final String CSS_PATH = PUBLIC_PATH;
      static final String IMAGE_PATH = PUBLIC_PATH;
      
      @Resource(CSS_PATH + "FastTree.css")
      FastTree.Css fastTreeCss();
      
      @Resource(IMAGE_PATH + "treeOpen.gif")
      DataResource treeOpen();
      
      @Resource(IMAGE_PATH + "treeClosed.gif")
      DataResource treeClosed();      
      
      @Resource(IMAGE_PATH + "selectionBar.gif")
      DataResource selectionBar();  
    }
    
    static FastTree.Css DEFAULT_CSS;
    
    public StandardCss(String styleName) {
      super(styleName, "gwt-FastTree");
    }

    public String fastTree() {
      return getWidgetStyleName();
    }

    public String selectionBar() {
      return " selection-bar";
    }   
    
    static Css ensureDefaultCss() {
      if (DEFAULT_CSS == null) {
        DEFAULT_CSS = createCss(STYLENAME_DEFAULT);
      }
      return DEFAULT_CSS;
    }
    
    /**
     * Inject the default css for this widget.
     */
    static void injectCss() {
      if (Gen2CssInjector.isInjectionEnabled()) {
        FastTree.Css css = ((Resources) GWT.create(Resources.class)).fastTreeCss();
        StyleInjector.injectStylesheet(css.getText());
        DEFAULT_CSS = css;
      }
    }
  }
  
  static final String STYLENAME_DEFAULT = "gwt-FastTree";
  private static final String STYLENAME_SELECTION = "selection-bar";

  /**
   * Map of TreeItem.widget -> TreeItem.
   */
  private final Map<Widget, FastTreeItem> childWidgets = new HashMap<Widget, FastTreeItem>();
  private FastTreeItem curSelection;
  private final Element focusable;
  private final FastTreeItem root;
  private static FocusImpl impl = FocusImpl.getFocusImplForPanel();
  private Event keyDown;
  private Event lastKeyDown;
  
  /**
   * Constructs a tree.
   */
  public FastTree() {
    this(StandardCss.ensureDefaultCss());
  }
  
  FastTree(Css css) {
    setElement(DOM.createDiv());

    focusable = createFocusElement();
    setStyleName(focusable, css.selectionBar());
    
    sinkEvents(Event.ONMOUSEDOWN | Event.ONCLICK | Event.KEYEVENTS);
    
    // The 'root' item is invisible and serves only as a container
    // for all top-level items.
    root = buildRootItem();
    root.setTree(this);

    setStyleName(css.fastTree());
    moveSelectionBar(curSelection);
    
    // Add accessibility role to tree.
    Accessibility.setRole(getElement(), Accessibility.ROLE_TREE);    
    Accessibility.setRole(focusable, Accessibility.ROLE_TREEITEM);
  }

  /**
   * Adds the widget as a root tree item.
   * 
   * @see com.google.gwt.user.client.ui.HasWidgets#add(com.google.gwt.user.client.ui.Widget)
   * @param widget widget to add.
   */
  @Override
  public void add(Widget widget) {
    addItem(widget);
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
    FastTreeItem fastTreeItem = new FastTreeItem(itemText);
    addItem(fastTreeItem);
    return fastTreeItem;
  }

  /**
   * Adds a new tree item containing the specified widget.
   * 
   * @param widget the widget to be added
   */
  public FastTreeItem addItem(Widget widget) {
    return root.addItem(widget);
  }

  /**
   * Clears all tree items from the current tree.
   */
  @Override
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

  public Iterator<Widget> iterator() {
    final Widget[] widgets = new Widget[childWidgets.size()];
    childWidgets.keySet().toArray(widgets);
    return WidgetIterators.createWidgetIterator(this, widgets);
  }

  @Override
  @SuppressWarnings("fallthrough")
  public void onBrowserEvent(Event e) {
    switch (e.getTypeInt()) {
      case Event.ONCLICK:
        Element element = DOM.eventGetTarget(e);
        if (shouldTreeDelegateFocusToElement(element)) {
          // The click event should have given focus to this element already.
          // Avoid moving focus back up to the tree (so that focusable widgets
          // attached to TreeItems can receive keyboard events).
        } else {
          if (!hasModifiers(e)) {
            clickedOnFocus(DOM.eventGetTarget(e));
          }
        }
        return;
      case Event.ONMOUSEDOWN:
        boolean left = e.getButton() == Event.BUTTON_LEFT;
        
        if (left && !hasModifiers(e)) {
          elementClicked(root, e);
        }
        return;
      
      case Event.ONKEYDOWN:
        keyDown = e;
        // Intentional fallthrough.
      case Event.ONKEYUP:
        if (e.getTypeInt() == Event.ONKEYUP) {
          // If we got here because of a key tab, then we need to make sure the
          // current tree item is selected.
          if (DOM.eventGetKeyCode(e) == KeyboardHandler.KEY_TAB) {
            ArrayList<Element> chain = new ArrayList<Element>();
            collectElementChain(chain, getElement(), DOM.eventGetTarget(e));
            FastTreeItem item = findItemByChain(chain, 0, root);
            if (item != getSelectedItem()) {
              setSelectedItem(item, true);
            }
          }
        }
        // Intentional fallthrough.
      case Event.ONKEYPRESS:
        if (hasModifiers(e)) {
          break;
        }

        // Trying to avoid duplicate key downs and fire navigation despite
        // missing key downs.
        if (e.getTypeInt() != Event.ONKEYUP) {
          if (lastKeyDown == null || (!lastKeyDown.equals(keyDown))) {
            keyboardNavigation(e);
          }
          if (e.getTypeInt() == Event.ONKEYPRESS) {
            lastKeyDown = null;
          } else {
            lastKeyDown = keyDown;
          }
        }
        if (DOMHelper.isArrowKey(DOM.eventGetKeyCode(e))) {
          DOM.eventCancelBubble(e, true);
          DOM.eventPreventDefault(e);
        }
        break;
    }
    super.onBrowserEvent(e);
  }

  /**
   * Creates a {@link Css} instance with the given style name. Note, only the
   * primary style name changes.
   * 
   * @param styleName style name for the widget
   * @return the standard css
   */
  public static Css createCss(String styleName) {
    return new StandardCss(styleName);
  }
  
  /**
   * Injects the default styles as a css resource.
   */
  public static void injectDefaultCss() {
    StandardCss.injectCss();
  }
  
  @Override
  public HandlerRegistration addOpenHandler(OpenHandler<FastTreeItem> handler) {
    return addHandler(handler, OpenEvent.getType());
  }

  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return addHandler(handler, ClickEvent.getType());
  }

  @Override
  public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
    return addDomHandler(handler, MouseDownEvent.getType());
  }

  @Override
  public HandlerRegistration addSelectionHandler(SelectionHandler<FastTreeItem> handler) {
    return addHandler(handler, SelectionEvent.getType());
  }

  @Override
  public HandlerRegistration addFocusHandler(FocusHandler handler) {
    return addHandler(handler, FocusEvent.getType());
  }

  @Override
  public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
    return addHandler(handler, KeyDownEvent.getType()); 
  }

  @Override
  public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
    return addHandler(handler, KeyPressEvent.getType()); 
  }

  @Override
  public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
    return addHandler(handler, KeyUpEvent.getType()); 
  }

  @Override
  public HandlerRegistration addBeforeOpenHandler(BeforeOpenHandler<FastTreeItem> handler) {
    return addHandler(handler, BeforeOpenEvent.getType());
  }

  @Override
  public HandlerRegistration addCloseHandler(CloseHandler<FastTreeItem> handler) {
    return addHandler(handler, CloseEvent.getType());
  }

  @Override
  public HandlerRegistration addBeforeCloseHandler(BeforeCloseHandler<FastTreeItem> handler) {
    return addHandler(handler, BeforeCloseEvent.getType());
  }

  @Override
  public HandlerRegistration addBeforeSelectionHandler(BeforeSelectionHandler<FastTreeItem> handler) {
    return addHandler(handler, BeforeSelectionEvent.getType());
  } 
  
  @Override
  public boolean remove(Widget w) {
    // Validate.
    FastTreeItem item = childWidgets.get(w);
    if (item == null) {
      return false;
    }

    // Delegate to TreeItem.setWidget, which performs correct removal.
    item.setWidget(null);
    return true;
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

  /**
   * Selects a specified item.
   * 
   * @param item the item to be selected, or <code>null</code> to deselect all
   *        items
   */
  public void setSelectedItem(FastTreeItem item) {
    setSelectedItem(item, true);
  }

  /**
   * Selects a specified item.
   * 
   * @param item the item to be selected, or <code>null</code> to deselect all
   *        items
   * @param fireEvents <code>true</code> to allow selection events to be fired
   */
  public void setSelectedItem(FastTreeItem item, boolean fireEvents) {
    if (item == null) {
      if (curSelection == null) {
        return;
      }
      curSelection.setSelection(false, fireEvents);
      curSelection = null;
      return;
    }

    onSelection(item, fireEvents, true);
  }

  /**
   * Iterator of tree items.
   */
  public Iterator<FastTreeItem> treeItemIterator() {
    List<FastTreeItem> accum = new ArrayList<FastTreeItem>();
    root.dumpTreeItems(accum);
    return accum.iterator();
  }

  protected FastTreeItem getRoot() {
    return root;
  }
  
  protected void keyboardNavigation(Event e) {
    // If nothing's selected, select the first item.
    if (curSelection == null) {
      if (root.getChildCount() > 0) {
        onSelection(root.getChild(0), true, true);
      }
      super.onBrowserEvent(e);
    } else {

      // Handle keyboard events if keyboard navigation is enabled

      switch (DOMHelper.standardizeKeycode(DOM.eventGetKeyCode(e))) {
        case KeyboardHandler.KEY_UP: {
          moveSelectionUp(curSelection);
          break;
        }
        case KeyboardHandler.KEY_DOWN: {
          moveSelectionDown(curSelection, true);
          break;
        }
        case KeyboardHandler.KEY_LEFT: {
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
        case KeyboardHandler.KEY_RIGHT: {
          if (!curSelection.isOpen()) {
            curSelection.setState(true);
          }
          // Do nothing if the element is already open.
          break;
        }
      }
    }
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
    Element selectedElem = item.getContentElement();
    moveElementOverTarget(focusable, selectedElem);
    UIObject.setVisible(focusable, true);
  }

  @Override
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
      curSelection.setSelection(false, fireEvents);
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
      curSelection.setSelection(true, fireEvents);
    }
  }

  /**
   * This is called when a valid selectable element is clicked in the tree.
   * Subclasses can override this method to decide whether or not FastTree
   * should keep processing the element clicked. For example, a subclass may
   * decide to return false for this method if selecting a new item in the tree
   * is subject to asynchronous approval from other components of the
   * application.
   * 
   * @returns true if element should be processed normally, false otherwise.
   *          Default returns true.
   */
  protected boolean processElementClicked(FastTreeItem item) {
    return true;
  }

  /**
   * Supply a decorator for the tree items.
   * 
   * @return a decorator
   */
  protected Decorator supplyFastTreeItemDecorator() {
    return Decorator.DEFAULT;
  }

  /**
   * Supply a decorator for the fast tree.
   * 
   * @return a decorator
   */
  protected Decorator supplyFastTreeDecorator() {
    return Decorator.DEFAULT;
  }
  
  void adopt(Widget widget, FastTreeItem treeItem) {
    assert (!childWidgets.containsKey(widget));
    childWidgets.put(widget, treeItem);
    super.adopt(widget);
  }

  /*
   * This method exists solely to support unit tests.
   */
  Map<Widget, FastTreeItem> getChildWidgets() {
    return childWidgets;
  }

  void treeOrphan(Widget widget) {
    super.orphan(widget);

    // Logical detach.
    childWidgets.remove(widget);
  }
  
  /**
   * Called before the tree item is opened.
   */
  void beforeOpen(FastTreeItem fastTreeItem, boolean isFirstTime) {
    BeforeOpenEvent.fire(this, fastTreeItem, isFirstTime);
  }
  
  /**
   * Called after the tree item is opened.
   */
  void afterOpen(FastTreeItem fastTreeItem) {
    OpenEvent.fire(this, fastTreeItem);    
  }
  
  /**
   * Called before the tree item is closed.
   */
  
  void beforeClose(FastTreeItem fastTreeItem) {
    BeforeCloseEvent.fire(this, fastTreeItem);
  }
  
  /**
   * Called after the tree item is closed.
   */
  
  void afterClose(FastTreeItem fastTreeItem) {
    CloseEvent.fire(this, fastTreeItem);
  }
  
  /**
   * Called when a tree item is selected.
   */
  
  void onSelected(FastTreeItem fastTreeItem) {
    SelectionEvent.fire(this, fastTreeItem);
  }
  
  void beforeSelected(FastTreeItem fastTreeItem) {
    BeforeSelectionEvent.fire(this, fastTreeItem);
  }

  private boolean hasModifiers(Event event) {
    boolean alt = event.getAltKey();
    boolean ctrl = event.getCtrlKey();
    boolean meta = event.getMetaKey();
    boolean shift = event.getShiftKey();

    return alt || ctrl || meta || shift;
  }
  
  /**
   * Collects parents going up the element tree, terminated at the tree root.
   */
  private void collectElementChain(ArrayList<Element> chain, Element hRoot, Element hElem) {
    if ((hElem == null) || hElem.equals(hRoot)) {
      return;
    }

    collectElementChain(chain, hRoot, DOM.getParent(hElem));
    chain.add(hElem);
  }

  /**
   * Disables the selection text on IE.
   */
  private native void disableSelection(Element element)
  /*-{
    element.onselectstart = function() {
      return false;
    };  
  }-*/;

  private FastTreeItem findDeepestOpenChild(FastTreeItem item) {
    if (!item.isOpen()) {
      return item;
    }
    return findDeepestOpenChild(item.getChild(item.getChildCount() - 1));
  }

  private FastTreeItem findItemByChain(ArrayList<Element> chain, int index, FastTreeItem root) {
    if (index == chain.size()) {
      return root;
    }

    Element hCurElem = chain.get(index);
    for (int i = 0, n = root.getChildCount(); i < n; ++i) {
      FastTreeItem child = root.getChild(i);
      if (child.getElement().equals(hCurElem)) {
        FastTreeItem retItem = findItemByChain(chain, index + 1, root.getChild(i));
        if (retItem == null) {
          return child;
        }
        return retItem;
      }
    }

    return findItemByChain(chain, index + 1, root);
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

  private void moveElementOverTarget(Element movable, Element target) {
    int containerTop = getAbsoluteTop();

    int top = DOM.getAbsoluteTop(target) - containerTop;
    int height = DOM.getElementPropertyInt(target, "offsetHeight");

    // Set the element's position and size to exactly underlap the
    // item's content element.
    DOM.setStyleAttribute(movable, "height", height + "px");
    DOM.setStyleAttribute(movable, "top", top + "px");
  }

  private native boolean shouldTreeDelegateFocusToElement(Element elem)
  /*-{
    var name = elem.nodeName;
    return ((name == "SELECT") ||
       (name == "INPUT")  ||
       (name == "TEXTAREA") ||
       (name == "OPTION") ||
       (name == "BUTTON") ||
       (name == "LABEL") 
    );
  }-*/;

  private void clickedOnFocus(Element e) {
    // An element was clicked on that is not focusable, so we use the hidden
    // focusable to not shift focus.
    moveElementOverTarget(focusable, e);
    impl.focus(focusable);
  }
  
  private boolean elementClicked(FastTreeItem root, Event event) {
    Element target = DOM.eventGetTarget(event);
    ArrayList<Element> chain = new ArrayList<Element>();
    collectElementChain(chain, getElement(), target);
    FastTreeItem item = findItemByChain(chain, 0, root);
    if (item != null) {
      if (item.isInteriorNode() && item.getControlElement().equals(target)) {
        item.setState(!item.isOpen(), true);
        moveSelectionBar(curSelection);
        disableSelection(target);
        return false;
      }
      if (processElementClicked(item)) {
        onSelection(item, true, !shouldTreeDelegateFocusToElement(target));
        return true;
      }
    }
    return false;
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
    
    // Update ARIA attributes to reflect the information from the
    // newly-selected item.
    updateAriaAttributes(selection);
  }
  
  private void updateAriaAttributes(FastTreeItem selection) {
    
    // Set the 'aria-level' state. To do this, we need to compute the level of
    // the currently selected item.

    // We initialize itemLevel to -1 because the level value is zero-based.
    // Note that the root node is not a part of the TreeItem hierarchy, and we
    // do not consider the root node to have a designated level. The level of
    // the root's children is level 0, its children's children is level 1, etc.
    int curSelectionLevel = -1;
    FastTreeItem tmpItem = selection;

    while (tmpItem != null) {
      tmpItem = tmpItem.getParentItem();
      ++curSelectionLevel;
    }

    Element selectionContentElement = selection.getContentElement();
    Accessibility.setState(selectionContentElement, Accessibility.STATE_LEVEL,
        String.valueOf(curSelectionLevel + 1));
    
    // Set the 'aria-setsize' and 'aria-posinset' states. To do this, we need to
    // compute the the number of siblings that the currently selected item has,
    // and the item's position among its siblings.

    FastTreeItem curSelectionParent = selection.getParentItem();
    if (curSelectionParent == null) {
      curSelectionParent = root;
    }

    Accessibility.setState(selectionContentElement,
        Accessibility.STATE_SETSIZE,
        String.valueOf(curSelectionParent.getChildCount()));

    int selectionIndex = curSelectionParent.getChildIndex(selection);

    Accessibility.setState(selectionContentElement,
        Accessibility.STATE_POSINSET, String.valueOf(selectionIndex + 1));
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
  
  /**
   * Helper to build the root item.
   */
  private FastTreeItem buildRootItem() {
    return new FastTreeItem() {
      @Override
      public void addItem(FastTreeItem item) {
        super.addItem(item);

        DOM.appendChild(FastTree.this.getElement(), item.getElement());

        // Explicitly set top-level items' parents to null.
        item.setParentItem(null);

        // Use no margin on top-most items.
        DOM.setIntStyleAttribute(item.getElement(), "margin", 0);
      }

      @Override
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
  }
}
