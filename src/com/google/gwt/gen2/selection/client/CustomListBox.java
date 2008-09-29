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
import com.google.gwt.gen2.commonwidget.client.impl.CellGridImpl;
import com.google.gwt.gen2.commonwidget.client.impl.StandardCssImpl;
import com.google.gwt.gen2.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.gen2.event.logical.shared.SelectionEvent;
import com.google.gwt.gen2.event.logical.shared.SelectionHandler;
import com.google.gwt.gen2.event.shared.HandlerRegistration;
import com.google.gwt.gen2.widgetbase.client.Gen2Composite;
import com.google.gwt.gen2.widgetbase.client.WidgetCss;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashMap;

/**
 * A custom list box. Used as a base class for {@link DropDownListBox} and
 * (eventually) a multi-select list box. The abstract custom list box is a
 * composite to allow inherited classes to specialize the look and feel of the
 * widget.
 * 
 * <br/>
 * 
 * @param <ValueType> the type of the values backing this list box
 */
public abstract class CustomListBox<ValueType> extends Gen2Composite<Widget>
    implements HasSelectionHandlers<ValueType> {

  /**
   * Interface used to allow the widget access to css style names. <p/> The
   * class names indicate the default gwt names for these styles. <br>
   * 
   */
  public static interface Css extends WidgetCss {
    /**
     * Disabled item.
     */
    String customListBoxDisabledItem();

    /**
     * Highlighted item.
     */
    String customListBoxHighlightedItem();

    /**
     * Inner div of a list separator.
     */
    String customListBoxInnerSeparator();

    /**
     * List item.
     */
    String customListBoxItem();

    /**
     * Wrapper around the list.
     */
    String customListBoxList();

    /**
     * Outer div of a list separator.
     */
    String customListBoxOuterSeparator();

    /**
     * Selected and disabled item.
     */
    String customListBoxSelectedAndDisabledItem();

    /**
     * Selected and highlighted item.
     */
    String customListBoxSelectedAndHighlightedItem();

    /**
     * Selected item.
     */
    String customListBoxSelectedItem();
  }

  /**
   * Used by instantiable subclasses to actually create a standard css impl.
   */
  abstract static class StandardCss extends StandardCssImpl implements
      CustomListBox.Css {

    /**
     * Constructor.
     */
    public StandardCss(String baseStyleName) {
      super(baseStyleName);
    }

    public String customListBoxDisabledItem() {
      return wrap("DisabledItem");
    }

    public String customListBoxHighlightedItem() {
      return wrap("HighlightedItem");
    }

    public String customListBoxInnerSeparator() {
      return wrap("InnerSeparator");
    }

    public String customListBoxItem() {
      return wrap("Item");
    }

    public String customListBoxList() {
      return wrap("ListBox");
    }

    public String customListBoxOuterSeparator() {
      return wrap("OuterSeparator");
    }

    public String customListBoxSelectedAndDisabledItem() {
      return wrap("SelectedAndDisabledItem");
    }

    public String customListBoxSelectedAndHighlightedItem() {
      return wrap("SelectedAndHighlightedItem");
    }

    public String customListBoxSelectedItem() {
      return wrap("SelectedItem");
    }
  }

  private class ItemList extends CellGridImpl<ValueType> {
    private class Item extends Cell {
      private String summary;

      public Item(Element element, ValueType value, String summary) {
        super(element, value);

        this.summary = summary;
        updateStyle();

        valueToCell.put(value, this);
      }

      public String getSummary() {
        return summary;
      }

      protected void updateStyle() {
        String style = css.customListBoxItem() + " ";
        if (isHighlighted() && isSelected()) {
          style += css.customListBoxSelectedAndHighlightedItem();
        } else if (isHighlighted()) {
          style += css.customListBoxHighlightedItem();
        } else if (isSelected()) {
          style += css.customListBoxSelectedItem();
        }
        setStyleName(style);
      }
    }

    private Cell oldCell;

    private HashMap<ValueType, Cell> valueToCell = new HashMap<ValueType, Cell>();

    public ItemList() {
      resizeColumns(1);
    }

    public void addItem(String html, ValueType value, String tooltip,
        String summary) {
      assert (html != null);
      assert (value != null);
      Element e = itemList.addHtml(supplyItemDecorator().wrapHTML(html));
      e.setTitle(tooltip);
      new Item(e, value, summary);
    }

    public void addSeparator() {
      itemList.addHtml("<div class='" + css.customListBoxInnerSeparator()
          + "'> <div class='" + css.customListBoxOuterSeparator() + "'/></div>");
    }

    public Cell getCellFromValue(ValueType value) {
      return valueToCell.get(value);
    }

    public final void setSelectedValue(ValueType value) {
      setSelected(getCellFromValue(value));
    }

    @Override
    protected void onSelected(Cell oldCell, Cell newCell) {
      Item oldItem = (Item) oldCell;
      Item newItem = (Item) newCell;

      currentSummary = defaultSummary;
      if (newItem != null && (newItem.summary != null)) {
        currentSummary = newItem.summary;
      }
      fireEvent(new SelectionEvent<ValueType>(getValue(oldItem),
          getValue(newItem)));
    }

    private Element addHtml(String html) {
      int nextRow = this.numRows;
      resizeRows(nextRow + 1);
      setHTML(nextRow, 0, html);
      return getCellFormatter().getElement(nextRow, 0);
    }
  }

  private Css css;

  // The fact that we are backed by an item grid is not part of the public API.
  private ItemList itemList = new ItemList();

  /**
   * default summary for this list box.
   */
  private String defaultSummary;

  /**
   * Current summary of this list box.
   */
  private String currentSummary;

  /**
   * Creates a custom list box with the given css.
   */
  public CustomListBox(Css css) {
    this(css, "");
  }

  /**
   * Creates a custom list box with the given css and default summary describing
   * the list.
   */
  public CustomListBox(Css css, String defaultSummary) {
    setCss(css);
    this.defaultSummary = defaultSummary;
    this.currentSummary = defaultSummary;
  }

  /**
   * Adds an item to the custom list box.
   * 
   * @param html the html of the item to be displayed
   * @param value the item's value.
   */
  public final void addItem(String html, ValueType value) {
    addItem(html, value, null, null);
  }

  /**
   * Adds an item to the custom list box.
   * 
   * @param html the html of the item to be added
   * @param value the item's value.
   * @param tooltip tooltip
   */
  public final void addItem(String html, ValueType value, String tooltip) {
    itemList.addItem(html, value, tooltip, null);
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
  public final void addItem(String html, ValueType value, String tooltip,
      String buttonFace) {
    itemList.addItem(html, value, tooltip, buttonFace);
  }

  public final HandlerRegistration addSelectionHandler(
      SelectionHandler<ValueType> handler) {
    return super.addHandler(SelectionEvent.KEY, handler);
  }

  /**
   * Adds a list item separator.
   */
  public final void addSeparator() {
    itemList.addSeparator();
  }

  /**
   * Gets the number of items in the list.
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
   * Sets the selected value.
   * 
   * @param value the selected value
   */
  public final void setSelectedValue(ValueType value) {
    itemList.setSelectedValue(value);
  }

  /**
   * Gets the title for the most recently selected value.
   */
  protected final ValueType getLastSelectedValue() {
    return itemList.getSelectedValue();
  }

  /**
   * Returns the actual list box widget.
   */
  protected Widget getListBox() {
    return itemList;
  }

  /**
   * Gets an html summary for this list box's current state. Each item may
   * supply its own summary, and that summary will be active when the item is
   * selected.
   */
  protected String getSummary() {
    return currentSummary;
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
  protected Decorator supplyListBoxDecorator() {
    return Decorator.DEFAULT;
  }

  /**
   * Sets the css for this widget.
   */
  void setCss(Css css) {
    this.css = css;
    itemList.setStylePrimaryName(css.customListBoxList());
  }
}
