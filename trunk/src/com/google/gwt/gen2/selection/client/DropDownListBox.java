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

import com.google.gwt.gen2.commonwidget.client.DropDownPanel;
import com.google.gwt.gen2.event.shared.BeforeShowEvent;
import com.google.gwt.gen2.event.shared.BeforeShowHandler;
import com.google.gwt.gen2.event.shared.HandlerRegistration;
import com.google.gwt.gen2.event.shared.SelectionEvent;
import com.google.gwt.gen2.event.shared.SelectionHandler;
import com.google.gwt.gen2.widgetbase.client.Decorator;
import com.google.gwt.gen2.widgetbase.client.DecoratorPanel;
import com.google.gwt.gen2.widgetbase.client.Gen2CssInfo;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasAnimation;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * A custom list box that is shown/hidden depending upon the state of a supplied
 * toggle button.
 * 
 * @param <ValueType> the type of values stored in the list box.
 */
public class DropDownListBox<ValueType> extends CustomListBox<ValueType>
    implements HasAnimation, BeforeShowEvent.Source {

  /**
   * Drop down panel class.
   */
  private class MyDropDown extends DropDownPanel<ToggleButton> {

    // This will be replaced with an AfterHide event, at which point MyDropDown
    // class can be removed.
    @Override
    public void hide(boolean b) {
      hideItems();
    }

    public void onHide() {
      // auto closed not tracked, for now.
      super.hide(false);
    }
  }

  /**
   * Provides the default css info for this widget.
   */
  public static DropDownListBox.Css DEFAULT_CSS = new CssAdaptor(
      "gwt-DropDownListBox");

  /**
   * Inject the default css for this widget.
   */
  public static void injectDefaultCss() {
    if (Gen2CssInfo.isInjectionEnabled()) {
      Gen2CssInfo.addDropDownListBoxDefault();
    }
  }

  private final ToggleButton button = new ToggleButton() {
    boolean cancelNextClick;

    @Override
    public void onBrowserEvent(Event e) {
      switch (e.getTypeInt()) {
        case Event.ONKEYDOWN:
          int keycode = e.getKeyCode();
          if (keycode == KeyboardListener.KEY_TAB) {
            hideItems();
          } else if (dropDown.isAttached()) {
            super.onBrowserEvent(e);
          } else if (keycode == KeyboardListener.KEY_DOWN) {
            showItems();
          }
          getListBox().onBrowserEvent(e);
          if (keycode == KeyboardListener.KEY_ENTER) {
            if (keycode == '\n' || keycode == '\r') {
              cancelNextClick = true;
            }
          }
          return;
        case Event.ONKEYPRESS:
          if (cancelNextClick) {
            cancelNextClick = false;
            return;
          }
      }
      super.onBrowserEvent(e);
    }

    @Override
    public void onClick() {
      super.onClick();
      if (isDown()) {
        showItems();
      } else {
        hideItems();
      }
    }
  };

  private MyDropDown dropDown = new MyDropDown();

  /**
   * Default constructor.
   */
  public DropDownListBox() {
    this("");
  }

  /**
   * Constructor.
   */
  public DropDownListBox(String defaultFace) {
    this(defaultFace, DEFAULT_CSS);
  }

  /**
   * Constructor.
   */
  public DropDownListBox(String defaultFace, Css css) {
    super(css, defaultFace);
    initWidget(new DecoratorPanel(button, supplyButtonDecorator()));

    getWidget().setStyleName(css.styleName());

    dropDown.add(new DecoratorPanel(getListBox(), supplyListBoxDecorator()));

    this.addSelectionHandler(new SelectionHandler<ValueType>() {
      public void onSelection(SelectionEvent<ValueType> event) {
        updateFace();
        hideItems();
      }
    });
    updateFace();
  }

  public HandlerRegistration addBeforeShowHandler(BeforeShowHandler handler) {
    return addHandler(BeforeShowEvent.KEY, handler);
  }

  /**
   * Gets the associated toggle button. Usually used to customize the buttons
   * styles.
   */
  public ToggleButton getButton() {
    return button;
  }

  /**
   * Gets the selected value.
   */
  public ValueType getSelectedValue() {
    return super.getLastSelectedValue();
  }

  /**
   * Hides the item list.
   */
  public final void hideItems() {
    button.setDown(false);
    dropDown.onHide();
  }

  public final boolean isAnimationEnabled() {
    return dropDown.isAnimationEnabled();
  }

  public final void setAnimationEnabled(boolean enabled) {
    dropDown.setAnimationEnabled(enabled);
  }

  /**
   * Shows the item list.
   */
  public final void showItems() {
    fireEvent(new BeforeShowEvent());
    button.setDown(true);
    dropDown.showRelativeTo(button);
  }

  /**
   * Supplied a decorator to wrap around the toggle button.
   * 
   * @return the decorator;
   */
  public Decorator supplyButtonDecorator() {
    return Decorator.DEFAULT;
  }

  /**
   * Supply a decorator for the toggle down face.
   * 
   */
  protected Decorator supplyFaceDownDecorator() {
    return Decorator.DEFAULT;
  }

  /**
   * Supply a decorator for the toggle up face.
   * 
   */
  protected Decorator supplyFaceUpDecorator() {
    return Decorator.DEFAULT;
  }

  private void updateFace() {
    String summary = getSummary();
    button.getUpFace().setHTML(supplyFaceUpDecorator().wrapHTML(summary));
    button.getDownFace().setHTML(supplyFaceUpDecorator().wrapHTML(summary));
  }
}
