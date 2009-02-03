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

package com.google.gwt.gen2.selection.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.gen2.commonevent.shared.BeforeOpenEvent;
import com.google.gwt.gen2.commonevent.shared.BeforeOpenHandler;
import com.google.gwt.gen2.commonevent.shared.HasBeforeOpenHandlers;
import com.google.gwt.gen2.commonwidget.client.DecoratorPanel;
import com.google.gwt.gen2.commonwidget.client.DropDownPanel;
import com.google.gwt.gen2.selection.client.CustomListBox.ItemList;
import com.google.gwt.gen2.widgetbase.client.Gen2CssInjector;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasAnimation;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * A custom list box that is shown/hidden depending upon the state of a supplied
 * toggle button.
 * 
 * @param <V> the type of values stored in the list box.
 */
public class DropDownListBox<V> extends CustomListBox<V> implements
    HasAnimation, HasBeforeOpenHandlers<DropDownListBox<V>>, HasValue<V> {
  class MyHandlers implements SelectionHandler<ItemList.Item> {
    public void onSelection(SelectionEvent<ItemList.Item> event) {
      // Two items are allowed to have the value, so even if the values are the
      // same, we must do all the update work unless we also want to cache the
      // current cell.
      ItemList.Item item = event.getSelectedItem();
      String displayText = item == null ? null : item.getDisplayText();
      updateButtonText(displayText);
      hideItems();
      V oldValue = value;
      value = item == null ? null : item.getValue();
      ValueChangeEvent.fireIfNotEqual(DropDownListBox.this, oldValue, value);
    }
  }

  private static StandardCss CSS = new StandardCss("gwt-DropDownListBox");

  /**
   * Injects the default styles as a css resource.
   */
  public static void injectDefaultCss() {
    Gen2CssInjector.addDropDownListBoxDefault();
  }

  private boolean firstTime;

  private final ToggleButton button = new ToggleButton() {

    @Override
    public void onBrowserEvent(Event e) {

      switch (e.getTypeInt()) {
        case Event.ONKEYDOWN:
          int keycode = e.getKeyCode();
          if (keycode == KeyCodes.KEY_TAB) {
            dropDown.hide();
          } else if (dropDown.isAttached()) {
            getItemList().onBrowserEvent(e);
          } else if (keycode == KeyCodes.KEY_DOWN) {
            showItemList();
          }
      }
      super.onBrowserEvent(e);
    }

    @Override
    public void onClick() {
      super.onClick();
      if (isDown()) {
        showItemList();
      } else {
        dropDown.hide();
      }
    }
  };

  private DropDownPanel dropDown = new DropDownPanel();

  private final String defaultDisplayText;
  private V value;

  /**
   * Default constructor.
   */
  public DropDownListBox() {
    this("");
  }

  /**
   * Constructor.
   * 
   * @param buttonText defaultDisplayText
   */
  public DropDownListBox(String buttonText) {
    this(buttonText, CSS);
  }

  /**
   * Constructor.
   */
  DropDownListBox(String defaultDisplayText, StandardCss css) {
    super(css);
    this.defaultDisplayText = defaultDisplayText;
    SimplePanel p = new SimplePanel();
    p.setWidget(button);
    initWidget(p);

    getWidget().setStyleName(css.getWidgetStyleName());
    dropDown.add(new DecoratorPanel(getItemList(), supplyItemListDecorator()));
    dropDown.setStyleName(css.getWidgetStyleName());
    button.addStyleName(css.button());
    updateButtonText(defaultDisplayText);
    dropDown.addCloseHandler(new CloseHandler<PopupPanel>() {

      public void onClose(CloseEvent<PopupPanel> event) {
        button.setDown(false);
      }
    });

    getItemList().addSelectionHandler(new MyHandlers());
  }

  public HandlerRegistration addBeforeOpenHandler(
      BeforeOpenHandler<DropDownListBox<V>> handler) {
    return addHandler(handler, BeforeOpenEvent.getType());
  }

  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<V> handler) {
    return addHandler(handler, ValueChangeEvent.getType());
  }

  /**
   * Gets the associated toggle button. Usually used to customize the buttons
   * styles.
   * 
   * @return the toggle button
   */
  public final FocusWidget getButton() {
    return button;
  }

  /**
   * Gets the selected value.
   */
  public V getValue() {
    return getItemList().getSelectedValue();
  }

  /**
   * Hides the item list.
   */
  public final void hideItems() {
    dropDown.hide();
  }

  public final boolean isAnimationEnabled() {
    return dropDown.isAnimationEnabled();
  }

  /**
   * Is the item list currently shown?
   * 
   * @return whether the item list is currently shown
   */
  public boolean isItemListShowing() {
    return dropDown.isShowing();
  }

  public final void setAnimationEnabled(boolean enabled) {
    dropDown.setAnimationEnabled(enabled);
  }

  @Override
  public void setStyleName(String styleName) {
    setCss(new StandardCss(styleName));
    super.setStyleName(styleName);
  }

  @Override
  public void setStylePrimaryName(String styleName) {
    setCss(new StandardCss(styleName));
    super.setStylePrimaryName(styleName);
  }

  public void setValue(V value) {
    setValue(value, false);
  }

  public void setValue(V value, boolean fireEvents) {
    if (fireEvents == false) {
      this.value = value;
    }
    getItemList().setSelectedItem(value);
  }

  /**
   * Shows the item list.
   */
  public final void showItemList() {
    BeforeOpenEvent.fire(this, firstTime);
    firstTime = false;
    button.setDown(true);
    dropDown.showRelativeTo(button);
  }

  /**
   * Updates the current button html. Called whenever a new list item is
   * selected.
   */
  private void updateButtonText(String displayText) {
    if (displayText == null) {
      displayText = defaultDisplayText;
    }
    button.getUpFace().setHTML(
        "<table width='100%' cellpadding='0' cellspacing='0'><tr><td class='DropDownTextHolder'>"
            + displayText
            + "</td><td class='DropDownImageHolder'><div class='DropDownImage'> </td></tr></table>");
  }

}
