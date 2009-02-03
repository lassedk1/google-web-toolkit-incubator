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
package com.google.gwt.user.datepicker.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;

import java.util.HashMap;

/**
 * Exposed the CellGridImpl in date picker for use by the drop down list box.
 * 
 * @param <V> the type of values contained in this cell grid
 */
public abstract class ItemGridImpl<V> extends CellGridImpl<V> implements
    HasSelectionHandlers<ItemGridImpl<V>.Item> {

  /**
   * Css styles for the item grid.
   * 
   */
  public interface Css {

    String item();

    String itemFirstInGroup();

    String itemIsHighlighted();

    String itemIsSelected();

    String itemIsSelectedAndHighlighted();

    String itemList();
  }

  /**
   * Cell with associated value and css styles.
   */
  public class Item extends Cell {
    private String displayText;
    private boolean firstInGroup;

    public Item(Element element, V value, String displayText,
        boolean firstInGroup) {
      super(element, value);
      this.firstInGroup = firstInGroup;

      this.displayText = displayText;
      updateStyle();

      valueToItem.put(value, this);
    }

    public String getDisplayText() {
      return displayText;
    }

    @Override
    protected void updateStyle() {
      String style = css.item() + " ";
      if (this.firstInGroup) {
        style += css.itemFirstInGroup() + " ";
      }
      if (isHighlighted() && isSelected()) {
        style += css.itemIsSelectedAndHighlighted();
      } else if (isHighlighted()) {
        style += css.itemIsHighlighted();
      } else if (isSelected()) {
        style += css.itemIsSelected();
      }

      setStyleName(style);
    }
  }

  private HashMap<V, Item> valueToItem = new HashMap<V, Item>();
  private final Css css;

  public ItemGridImpl(Css css) {
    this.css = css;
    setStylePrimaryName(css.itemList());
  }

  public HandlerRegistration addSelectionHandler(
      SelectionHandler<ItemGridImpl<V>.Item> handler) {
    return addHandler(handler, SelectionEvent.getType());
  }

  protected Item getItemFromValue(V value) {
    return valueToItem.get(value);
  }

  @Override
  protected final void onSelected(
      com.google.gwt.user.datepicker.client.CellGridImpl<V>.Cell lastSelected,
      com.google.gwt.user.datepicker.client.CellGridImpl<V>.Cell cell) {
    if (cell != null) {
      SelectionEvent.fire(this, (Item) cell);
    }
  }
}
