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

package com.google.gwt.gen2.selection.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.gen2.commonwidget.client.Decorator;
import com.google.gwt.gen2.commonwidget.client.impl.StandardCssImpl;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.datepicker.client.ItemGridImpl;

/**
 * A custom list box contains a list of items. Used as a base class for
 * {@link DropDownListBox} and (eventually) a multi-select list box. The
 * abstract custom list box is a composite to allow inherited classes to
 * specialize the look and feel of the widget.
 * 
 * <br/>
 * 
 * @param <V> the type of the values backing this list box
 */

abstract class CustomListBox<V> extends Composite {

  class ItemList extends ItemGridImpl<V> {
    private boolean firstInGroup = true;

    ItemList(Css css) {
      super(css);
      resizeColumns(1);
    }

    public void addItem(String html, V value, String tooltip, String displayText) {
      assert (html != null);
      assert (value != null);
      Element e = itemList.addHtml(supplyItemDecorator().wrapHTML(html));
      e.setTitle(tooltip);
      new Item(e, value, displayText, firstInGroup);
      firstInGroup = false;
    }

    public void addSeparator() {
      itemList.addHtml("<div class='" + css.innerSeparator()
          + "'> <div class='" + css.outerSeparator() + "'/></div>");
      firstInGroup = true;
    }

    public String getDisplayText(V value) {
      return getItemFromValue(value).getDisplayText();
    }

    public void setSelectedItem(V value) {
      setSelected(getItemFromValue(value));
    }

    private Element addHtml(String html) {
      int nextRow = this.numRows;
      resizeRows(nextRow + 1);
      setHTML(nextRow, 0, html);
      return getCellFormatter().getElement(nextRow, 0);
    }
  }

  /**
   * Helper class to support styling.
   */
  static class StandardCss extends StandardCssImpl implements ItemGridImpl.Css {

    public StandardCss() {
      super("gwt-CustomListBox", "ItemList");
    }

    public StandardCss(String styleName) {
      super(styleName, "ListItem");
    }

    public String button() {
      return "DropDownButton";
    }

    public String disabledItem() {
      return wrap("IsDisabled");
    }

    public String innerSeparator() {
      return wrap("InnerSeparator");
    }

    public String item() {
      return wrap("");
    }

    public String itemFirstInGroup() {
      return wrap("FirstInGroup");
    }

    public String itemIsHighlighted() {
      return wrap("IsHighlighted");
    }

    public String itemIsSelected() {
      return wrap("IsSelected");
    }

    public String itemIsSelectedAndDisabled() {
      return wrap("IsSelectedAndDisabled");
    }

    public String itemIsSelectedAndHighlighted() {
      return wrap("IsSelectedAndHighlighted");
    }

    public String itemList() {
      return wrap("s");
    }

    public String outerSeparator() {
      return wrap("OuterSeparator");
    }
  }

  StandardCss css;

  // The fact that we are backed by an item grid is not part of the public API.
  private ItemList itemList;

  /**
   * Creates a custom list box with the given css and default displayText
   * describing the list.
   */
  CustomListBox(StandardCss css) {
    setCss(css);
    itemList = new ItemList(css);
  }

  /**
   * Adds an item to the custom list box.
   * 
   * @param html the html of the item to be displayed
   * @param value the item's value.
   */
  public final void addItem(String html, V value) {
    addItem(html, value, null, html);
  }

  /**
   * Adds an item to the custom list box.
   * 
   * @param html the html of the item to be added
   * @param value the item's value.
   * @param tooltip tooltip
   */
  public final void addItem(String html, V value, String tooltip) {
    itemList.addItem(html, value, tooltip, html);
  }
 
  /**
   * Adds an item to the custom list box.
   * 
   * @param html the html of the item to be added
   * @param value the item's value.
   * @param tooltip tooltip
   * @param buttonFace the button face that should be displayed when this item
   *          is selected
   */
  public final void addItem(String html, V value, String tooltip,
      String buttonFace) {
    itemList.addItem(html, value, tooltip, buttonFace);
  }

  /**
   * Adds a list item separator.
   */
  public final void addSeparator() {
    itemList.addSeparator();
  }

  /**
   * Gets the number of items in the list.
   * 
   * @return the number of items in the list
   */
  public int getNumItems() {
    return itemList.getNumCells();
  }

  @Override
  public void onBrowserEvent(Event e) {
    itemList.onBrowserEvent(e);
    super.onBrowserEvent(e);
  }

  /**
   * Supply a decorator for the list items.
   * 
   * @return a decorator
   */
  protected Decorator supplyItemDecorator() {
    return Decorator.DEFAULT;
  }

  /**
   * Supply a decorator for the list box.
   * 
   * @return a decorator
   */
  protected Decorator supplyItemListDecorator() {
    return Decorator.DEFAULT;
  }

  /**
   * Returns the actual list of items.
   * 
   * @return the list of items
   */
  ItemList getItemList() {
    return itemList;
  }

  /**
   * Sets the css for this widget.
   */
  void setCss(StandardCss css) {
    this.css = css;
  }
}
