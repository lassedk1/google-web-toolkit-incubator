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

package com.google.gwt.widgetideas.client;

import com.google.gwt.libideas.event.shared.HandlerRegistration;
import com.google.gwt.libideas.event.shared.RenderEvent;
import com.google.gwt.libideas.event.shared.RenderHandler;
import com.google.gwt.libideas.event.shared.UpdateValueEvent;
import com.google.gwt.libideas.event.shared.UpdateValueHandler;
import com.google.gwt.libideas.resources.client.CssResource.ClassName;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HasAnimation;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestionMenuImpl;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.common.IncubatorComposite;
import com.google.gwt.widgetideas.common.IncubatorWidget;

import com.google.gwt.widgetideas.common.IncubatorWidget.AbstractCss;

import java.util.HashMap;

/**
 * A custom list box.
 * 
 * @param <Value> the type of the values backing this list box
 */
public class CustomListBox<Value> extends
    IncubatorComposite<ToggleButton, CustomListBox.Css> implements
    UpdateValueEvent.Source, RenderEvent.Source, HasAnimation {

  /**
   * Interface used to allow the widget access to css style names.
   * 
   */
  public interface Css extends AbstractCss {

    @ClassName("gwt-CustomListBox-currentAndHighlightedItem")
    String currentAndHighlightedItem();

    @ClassName("gwt-CustomListBox-currentItem")
    String currentItem();

    @ClassName("gwt-CustomListBox-dropdown")
    String dropdown();

    @ClassName("gwt-CustomListBox-highlightedItem")
    String highlightedItem();

    @ClassName("gwt-CustomListBox-innerSep")
    String innerSep();

    @ClassName("gwt-CustomListBox-item")
    String item();

    @ClassName("gwt-CustomListBox-outerSep")
    String outerSep();

    @ClassName("gwt-CustomListBox")
    String styleName();
  }

  /**
   * This is a hacked together class to deal with the following realities. We
   * need style names that lend themselves to be used without decendent
   * selectors Style names are particularly hard to change after a widget is
   * published. This widget is a 2.0 widget, so we will have CSSResources by
   * then, so using bad style names in the mean time would make migration
   * harder.
   */
  public static class CssAdaptor extends IncubatorWidget.CssAdaptor implements
      CustomListBox.Css {

    public CssAdaptor(String baseStyleName) {
      setBaseName(baseStyleName);
    }

    @Override
    public IncubatorWidget.CssAdaptor copy(String name) {
      return new CssAdaptor(name);
    }

    public String currentAndHighlightedItem() {
      return wrap("currentAndHighlightedItem");
    }

    public String currentItem() {
      return wrap("currentItem");
    }

    public String dropdown() {
      return wrap("dropdown");
    }

    public String highlightedItem() {
      return wrap("highlightedItem");
    }

    public String innerSep() {
      return wrap("innerSep");
    }

    public String item() {
      return wrap("item");
    }

    public String outerSep() {
      return wrap("outerSep");
    }
  }

  private class DropDown extends DropDownPanel<ToggleButton> {
    @Override
    public void hide() {
      CustomListBox.this.hideItems();
    }

    private void onHide() {
      super.hide();
    }
  }

  /**
   * Menu shared with SuggestBox for efficiency.
   */
  private class ItemMenu extends SuggestionMenuImpl {
    private class Item extends SuggestionItem {
      private Command customCommand;
      private Value value;

      public Item(String html, Value value, String tooltip,
          Command customCommand) {
        // As we are not controlling the button face yet, replacement value is
        // always null.
        super(new MultiWordSuggestOracle.MultiWordSuggestion(null, html), true);
        this.value = value;
        this.customCommand = customCommand;
        if (tooltip != null) {
          this.setTitle(tooltip);
        }
      }

      public Command getCustomCommand() {
        return customCommand;
      }

      public String getTooltip() {
        return getSuggestion().getReplacementString();
      }

      public Value getValue() {
        return value;
      }

      public boolean isCurrent() {
        return this == current;
      }

      public boolean isHighlighted() {
        return getHighlightedItem() == this;
      }

      public void updateStyle(boolean isHighlighted, boolean isCurrent) {
        String style;
        if (isHighlighted && isCurrent) {
          style = css().currentAndHighlightedItem();
        } else if (isHighlighted) {
          style = css().highlightedItem();
        } else if (isCurrent) {
          style = css().currentItem();
        } else {
          style = css().item();
        }
        this.getElement().setClassName(style);
      }

      @Override
      protected void updateStyle(boolean isHighlighted) {
        updateStyle(isHighlighted, isCurrent());
      }
    }

    // TODO(ECC) create smaller map structure to save code and make it common
    // to all incubator widgets.
    private HashMap<Value, Item> map = new HashMap<Value, Item>();

    private Item current;

    public void addItem(String html, Value value, String tooltip,
        Command command) {
      assert (html != null);
      assert (value != null);

      Item menuItem = new Item(getItemDecorator().wrapHTML(html), value,
          tooltip, null);
      menuItem.setStyleName(css().item());
      menuItem.customCommand = command;
      map.put(value, menuItem);
      super.addItem(menuItem);
      rerender = true;
    }

    public Value getCurrentValue() {
      return getValue(current);
    }

    public Value getHighlightedValue() {
      return getValue(getHighlightedItem());
    }

    public void onHide() {
      // TODO(ecc) once we get rid of the PopupPanelOverride hack, should be
      // able to again pass in dropdown.
      super.onPopupClosed(null, false);

      // Clear current highlight.
      highlightItem(null);
    }

    public void setCurrentValue(Value value) {
      onValueUpdated(getItem(value));
    }

    @Override
    protected void onHighlight(SuggestionItem s) {
      // we don't need to do anything on highlight yet.
    }

    @Override
    protected void onValueUpdated(SuggestionItem newItem) {
      if (newItem == current) {
        return;
      }
      Value oldValue = null;
      if (current != null) {
        current.updateStyle(current.isHighlighted(), false);
        oldValue = current.getValue();
      }

      current = (Item) newItem;
      if (newItem != null) {
        current.updateStyle(current.isHighlighted(), true);
        if (current.getCustomCommand() != null) {
          current.getCustomCommand().execute();
        }
      }
      hideItems();
      Value newValue = current == null ? null : current.getValue();
      fireEvent(new UpdateValueEvent(oldValue, newValue));
    }

    private Item getItem(Value value) {
      return map.get(value);
    }

    private Value getValue(SuggestionItem item) {
      return (item == null ? null : ((Item) item).getValue());
    }
  }

  private static final Css DEFAULT_CSS;

  static {
    DEFAULT_CSS = TopLevelCssInfo.get().forCustomListBox();
  }

  private ItemMenu itemMenu = new ItemMenu();
  private DropDown dropDown = new DropDown();
  private boolean rerender = true;

  public CustomListBox(String html) {
    this(html, DEFAULT_CSS);
  }

  /**
   * Creates a custom list box with the given html as its face.
   */
  public CustomListBox(String html, Css css) {
    initWidget(createButton());
    initCss(css);
    getWidget().addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        if (getWidget().isDown()) {
          showItems();
        } else {
          hideItems();
        }
      }
    });

    dropDown.setWidget(itemMenu);

    getWidget().setHTML(getButtonDecorator().wrapHTML(html));
  }

  /**
   * Adds an item to the custom list box.
   * 
   * @param html the html of the item to be displayed
   * @param value the item's value.
   */
  public void addItem(String html, Value value) {
    addItem(html, value, null, null);
  }

  /**
   * Adds an item to the custom list box.
   * 
   * @param html the html of the item to be added
   * @param value the item's value.
   * @param command the command to run once the item is chosen
   */
  public void addItem(String html, Value value, Command command) {
    itemMenu.addItem(html, value, null, command);
  }

  /**
   * Adds an item to the custom list box.
   * 
   * @param html the html of the item to be added
   * @param value the item's value.
   * @param tooltip tooltip
   */
  public void addItem(String html, Value value, String tooltip) {
    itemMenu.addItem(html, value, tooltip, null);
  }

  /**
   * Adds an item to the custom list box, specifying an initial value for the
   * item.
   * 
   * @param html the html of the item to be added
   * @param value the item's value.
   * @param tooltip the tooltip for this item
   * @param command the command to run once the item is chosen
   */
  public void addItem(String html, Value value, String tooltip, Command command) {
    itemMenu.addItem(html, value, tooltip, command);
  }

  public HandlerRegistration addRenderHandler(RenderHandler handler) {
    return addHandler(RenderEvent.KEY, handler);
  }

  /**
   * Adds a list item separator.
   */
  public void addSeparator() {
    MenuItemSeparator sep = itemMenu.addSeparator();
    sep.setStyleName(css().outerSep());
    sep.getElement().getFirstChildElement().setClassName(css().innerSep());
    rerender = true;
  }

  public HandlerRegistration addValueUpdateHandler(UpdateValueHandler handler) {
    return super.addHandler(UpdateValueEvent.KEY, handler);
  }

  public Value getCurrentValue() {
    return itemMenu.getCurrentValue();
  }

  public Value getHighlightedValue() {
    return itemMenu.getHighlightedValue();
  }

  /**
   * Gets the widget's position in the tab index.
   * 
   * @return the widget's tab index
   */
  public int getTabIndex() {
    return getWidget().getTabIndex();
  }

  public void hideItems() {
    getWidget().setDown(false);
    itemMenu.onHide();
    dropDown.onHide();
  }

  public boolean isAnimationEnabled() {
    return dropDown.isAnimationEnabled();
  }

  @Override
  public void onBrowserEvent(Event e) {

    switch (e.getTypeInt()) {
      case Event.ONKEYDOWN:
        int keycode = e.getKeyCode();
        if (keycode == KeyboardListener.KEY_TAB) {
          hideItems();
        } else if (itemMenu.isAttached()) {
          itemMenu.onBrowserEvent(e);
        } else if (keycode == KeyboardListener.KEY_DOWN) {
          showItems();
        }
        break;
    }
    super.onBrowserEvent(e);
  }

  /**
   * Sets the widget's 'access key'. This key is used (in conjunction with a
   * browser-specific modifier key) to automatically focus the widget.
   * 
   * @param key the widget's access key
   */
  public void setAccessKey(char key) {
    getWidget().setAccessKey(key);
  }

  public void setAnimationEnabled(boolean enabled) {
    dropDown.setAnimationEnabled(enabled);
  }

  /**
   * Sets the current value.
   * 
   * @param value the current value
   */
  public void setCurrentValue(Value value) {
    itemMenu.setCurrentValue(value);
  }

  /**
   * Explicitly focus/unfocus this widget. Only one widget can have focus at a
   * time, and the widget that does will receive all keyboard events.
   * 
   * @param focused whether this widget should take focus or release it
   */
  public void setFocus(boolean focused) {
    getWidget().setFocus(focused);
  }

  /**
   * Sets the widget's position in the tab index. If more than one widget has
   * the same tab index, each such widget will receive focus in an arbitrary
   * order. Setting the tab index to <code>-1</code> will cause this widget to
   * be removed from the tab order.
   * 
   * @param index the widget's tab index
   */
  public void setTabIndex(int index) {
    getWidget().setTabIndex(index);
  }

  public void showItems() {
    if (listeningTo(RenderEvent.KEY) && rerender) {
      rerender = false;
      fireEvent(new RenderEvent());
    }
    getWidget().setDown(true);
    dropDown.showRelativeTo(getWidget());
  }

  /**
   * Creates a new toggle button for use by the custom list box.
   */
  protected ToggleButton createButton() {
    return new ToggleButton();
  }

  protected Decorator getButtonDecorator() {
    return Decorator.DEFAULT;
  }

  protected Decorator getItemDecorator() {
    return Decorator.DEFAULT;
  }

  /**
   * On css init.
   * 
   * @param css the new css.
   */
  @Override
  protected void onCssInit(Css css) {
    // We add rather then replace as we want to share the default button.
    addStyleName(css.styleName());

    // Drop down must be changed in tandom.
    dropDown.setStylePrimaryName(css.dropdown());
  }

}