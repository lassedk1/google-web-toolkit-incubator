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

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.commonwidget.client.Decorator;
import com.google.gwt.gen2.commonwidget.client.DecoratorPanel;
import com.google.gwt.gen2.commonwidget.client.DropDownPanel;
import com.google.gwt.gen2.event.logical.shared.BeforeShowEvent;
import com.google.gwt.gen2.event.logical.shared.BeforeShowHandler;
import com.google.gwt.gen2.event.logical.shared.HasBeforeShowHandlers;
import com.google.gwt.gen2.event.logical.shared.SelectionEvent;
import com.google.gwt.gen2.event.logical.shared.SelectionHandler;
import com.google.gwt.gen2.event.shared.HandlerRegistration;
import com.google.gwt.gen2.widgetbase.client.Gen2CssInjector;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasAnimation;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * A custom list box that is shown/hidden depending upon the state of a supplied
 * toggle button.
 * 
 * @param <ValueType> the type of values stored in the list box.
 */
public class DropDownListBox<ValueType> extends CustomListBox<ValueType>
    implements HasAnimation, HasBeforeShowHandlers, HasValue<ValueType> {

  /**
   * Css interface for drop down list box.
   */
  public static interface Css extends CustomListBox.Css {
    @ClassName("gwt-DropDownListBox")
    String dropDownListBox();
  }

  /**
   * Creates the standard css instance used for this widget and groups all the
   * css processing under it.
   */
  static class StandardCss extends CustomListBox.StandardCss implements Css {

    /**
     * CSS resource.
     */
    public static interface Resources extends ImmutableResourceBundle {
      @Resource("com/google/gwt/gen2/widgetbase/public/DropDownListBox.css")
      DropDownListBox.Css dropDownListBoxCss();
    }

    /**
     * Provides the default css info for this widget.
     */
    public static DropDownListBox.Css DEFAULT_CSS;

    static Css ensureDefaultCss() {
      if (DEFAULT_CSS == null) {
        DEFAULT_CSS = createCss("gwt-DropDownListBox");
      }
      return DEFAULT_CSS;
    }

    static void injectCss() {
      if (Gen2CssInjector.isInjectionEnabled()) {
        Resources resources = GWT.create(Resources.class);
        DropDownListBox.Css css = resources.dropDownListBoxCss();
        StyleInjector.injectStylesheet(css.getText());
        DEFAULT_CSS = css;
      }
    }

    public StandardCss(String styleName) {
      super(styleName, "customListBox");
    }

    public String dropDownListBox() {
      return getWidgetStyleName();
    }
  }

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
  public DropDownListBox(String buttonHtml) {
    this(buttonHtml, StandardCss.ensureDefaultCss());
  }

  /**
   * Constructor.
   */
  public DropDownListBox(String buttonHtml, Css css) {
    super(css, buttonHtml);
    initWidget(new DecoratorPanel(button, supplyButtonDecorator()));

    getWidget().setStyleName(css.dropDownListBox());

    dropDown.add(new DecoratorPanel(getListBox(), supplyListBoxDecorator()));

    this.addSelectionHandler(new SelectionHandler<ValueType>() {
      public void onSelection(SelectionEvent<ValueType> event) {
        updateButtonHtml();
        hideItems();
      }
    });
    updateButtonHtml();
  }

  public HandlerRegistration addBeforeShowHandler(BeforeShowHandler handler) {
    return addHandler(BeforeShowEvent.TYPE, handler);
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
  public ValueType getValue() {
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
   * Sets the default.
   * 
   * @param css
   */
  public void setDefaultCss(Css css) {
    StandardCss.DEFAULT_CSS = css;
  }

  @Override
  public void setStyleName(String styleName) {
    setCss(createCss(styleName));
    super.setStyleName(styleName);
  }

  @Override
  public void setStylePrimaryName(String styleName) {
    setCss(createCss(styleName));
    super.setStylePrimaryName(styleName);
  }

  public void setValue(ValueType value) {
    setSelectedValue(value);
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
   * Supplied a decorator to wrap around the drop down list button's primary
   * html.
   * 
   * @return the decorator;
   */
  public Decorator supplyButtonDecorator() {
    return Decorator.DEFAULT;
  }

  /**
   * Updates the current button html. Called whenever a new list item is
   * selected.
   */
  protected void updateButtonHtml() {
    button.getUpFace().setHTML(getSummary());
  }
}
