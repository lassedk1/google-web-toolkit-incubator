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
package com.google.gwt.user.client.ui;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.PopupPanel.AnimationType;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.widgetideas.client.DropDownPanel;
import com.google.gwt.widgetideas.client.event.EventHandlers;
import com.google.gwt.widgetideas.client.event.FiresHighlightEvents;
import com.google.gwt.widgetideas.client.event.HighlightEvent;
import com.google.gwt.widgetideas.client.event.HighlightHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A {@link SuggestBox} is a text box or text area which displays a
 * pre-configured set of selections that match the user's input.
 * 
 * Each {@link SuggestBox} is associated with a single {@link SuggestOracle}.
 * The {@link SuggestOracle} is used to provide a set of selections given a
 * specific query string.
 * 
 * <p>
 * By default, the {@link SuggestBox} uses a {@link MultiWordSuggestOracle} as
 * its oracle. Below we show how a {@link MultiWordSuggestOracle} can be
 * configured:
 * </p>
 * 
 * <pre> 
 *   MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();  
 *   oracle.add("Cat");
 *   oracle.add("Dog");
 *   oracle.add("Horse");
 *   oracle.add("Canary");
 *   
 *   SuggestBox box = new SuggestBox(oracle);
 * </pre>
 * 
 * Using the example above, if the user types "C" into the text widget, the
 * oracle will configure the suggestions with the "Cat" and "Canary"
 * suggestions. Specifically, whenever the user types a key into the text
 * widget, the value is submitted to the <code>MultiWordSuggestOracle</code>.
 * 
 * <p>
 * Note that there is no method to retrieve the "currently selected suggestion"
 * in a SuggestBox, because there are points in time where the currently
 * selected suggestion is not defined. For example, if the user types in some
 * text that does not match any of the SuggestBox's suggestions, then the
 * SuggestBox will not have a currently selected suggestion. It is more useful
 * to know when a suggestion has been chosen from the SuggestBox's list of
 * suggestions. A SuggestBox fires {@link SuggestionEvent SuggestionEvents}
 * whenever a suggestion is chosen, and handlers for these events can be added
 * using the {@link #addEventHandler(SuggestionHandler)} method.
 * </p>
 * 
 * <p>
 * <img class='gallery' src='SuggestBox.png'/>
 * </p>
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.gwt-SuggestBox { the suggest box itself }</li>
 * <li>.gwt-SuggestBoxPopup { the suggestion popup }</li>
 * <li>.gwt-SuggestBoxPopup .item { an unselected suggestion }</li>
 * <li>.gwt-SuggestBoxPopup .item-selected { a selected suggestion }</li>
 * </ul>
 * 
 * @see SuggestOracle
 * @see MultiWordSuggestOracle
 * @see TextBoxBase
 */
public final class SuggestBox extends Composite implements HasText, HasFocus,
    HasAnimation, SourcesClickEvents, SourcesFocusEvents, SourcesChangeEvents,
    SourcesKeyboardEvents, FiresSuggestionEvents,
    FiresHighlightEvents<Suggestion> {
  /**
   * The SuggestionMenu class is used for the display and selection of
   * suggestions in the SuggestBox widget. SuggestionMenu differs from MenuBar
   * in that it always has a vertical orientation, and it has no submenus. It
   * also allows for programmatic selection of items in the menu, and
   * programmatically performing the action associated with the selected item.
   * In the MenuBar class, items cannot be selected programatically - they can
   * only be selected when the user places the mouse over a particlar item.
   * Additional methods in SuggestionMenu provide information about the number
   * of items in the menu, and the index of the currently selected item.
   */
  private class SuggestionMenu extends MenuBar {

    public SuggestionMenu(boolean vertical) {
      super(vertical);
      // Make sure that CSS styles specified for the default Menu classes
      // do not affect this menu
      setStyleName("");
    }

    public void doSelectedItemAction() {
      // In order to perform the action of the item that is currently
      // selected, the menu must be showing.
      MenuItem selectedItem = getSelectedItem();
      if (selectedItem != null) {
        doItemAction(selectedItem, true);
      }
    }

    public int getNumItems() {
      return getItems().size();
    }

    /**
     * Returns the index of the menu item that is currently selected.
     */
    public int getSelectedItemIndex() {
      // The index of the currently selected item can only be
      // obtained if the menu is showing.
      MenuItem selectedItem = getSelectedItem();
      if (selectedItem != null) {
        return getItems().indexOf(selectedItem);
      }
      return -1;
    }

    /**
     * Selects the item at the specified index in the menu. Selecting the item
     * does not perform the item's associated action; it only changes the style
     * of the item and updates the value of SuggestionMenu.selectedItem.
     */
    public void selectItem(int index) {
      List<MenuItem> items = getItems();
      if (index > -1 && index < items.size()) {
        itemOver(items.get(index));
      }
    }

    @Override
    void itemOver(MenuItem item) {
      super.itemOver(item);
      if (item != null) {
        Suggestion s = ((SuggestionMenuItem) item).getSuggestion();
        handlers.fire(new HighlightEvent<Suggestion>(SuggestBox.this, s));
      }
    }
  }

  /**
   * Class for menu items in a SuggestionMenu. A SuggestionMenuItem differs from
   * a MenuItem in that each item is backed by a Suggestion object. The text of
   * each menu item is derived from the display string of a Suggestion object,
   * and each item stores a reference to its Suggestion object.
   */
  private static class SuggestionMenuItem extends MenuItem {

    private static final String STYLENAME_DEFAULT = "item";

    private Suggestion suggestion;

    public SuggestionMenuItem(Suggestion suggestion, boolean asHTML) {
      super(suggestion.getDisplayString(), asHTML);
      // Each suggestion should be placed in a single row in the suggestion
      // menu. If the window is resized and the suggestion cannot fit on a
      // single row, it should be clipped (instead of wrapping around and
      // taking up a second row).
      DOM.setStyleAttribute(getElement(), "whiteSpace", "nowrap");
      setStyleName(STYLENAME_DEFAULT);
      setSuggestion(suggestion);
    }

    public Suggestion getSuggestion() {
      return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
      this.suggestion = suggestion;
    }
  }

  private static final String STYLENAME_DEFAULT = "gwt-SuggestBox";

  /**
   * Creates the suggestion drop down popup. Can be overridden by subclasses to
   * introduce new drop down behavior.
   * <p>
   * The default suggestion drop down popup has a style name of
   * "GWT-SuggestBoxPopup and selects the first suggestion in the suggestion
   * list.
   */
  private static DropDownPanel<SuggestBox> createSuggestionPopup() {
    DropDownPanel dropDown = new DropDownPanel<SuggestBox>() {
      public void position(SuggestBox widget, int offsetWidth, int offsetHeight) {
        super.position(widget, offsetWidth, offsetHeight);

        // Select the first item in the suggestion menu.
        widget.selectSuggestion(0);
      }
    };
    dropDown.setStyleName("gwt-SuggestBoxPopup");
    dropDown.setAnimationType(AnimationType.ONE_WAY_CORNER);
    return dropDown;
  }

  private EventHandlers handlers = new EventHandlers();

  private int limit = 20;
  private SuggestOracle oracle;
  private String currentText;
  private final SuggestionMenu suggestionMenu;
  private final DropDownPanel<SuggestBox> suggestionPopup;
  private final TextBoxBase box;
  private ArrayList<SuggestionHandler> suggestionHandlers = null;
  private DelegatingClickListenerCollection clickListeners;
  private DelegatingChangeListenerCollection changeListeners;
  private DelegatingFocusListenerCollection focusListeners;
  private DelegatingKeyboardListenerCollection keyboardListeners;

  private final Callback callback = new Callback() {
    public void onSuggestionsReady(Request request, Response response) {
      showSuggestions(response.getSuggestions());
    }
  };

  /**
   * Constructor for {@link SuggestBox}. Creates a
   * {@link MultiWordSuggestOracle} and {@link TextBox} to use with this
   * {@link SuggestBox}.
   */
  public SuggestBox() {
    this(new MultiWordSuggestOracle());
  }

  /**
   * Constructor for {@link SuggestBox}. Creates a {@link TextBox} to use with
   * this {@link SuggestBox}.
   * 
   * @param oracle the oracle for this <code>SuggestBox</code>
   */
  public SuggestBox(SuggestOracle oracle) {
    this(oracle, new TextBox());
  }

  /**
   * Constructor for {@link SuggestBox}. The text box will be removed from it's
   * current location and wrapped by the {@link SuggestBox}.
   * 
   * @param oracle supplies suggestions based upon the current contents of the
   *          text widget
   * @param box the text widget
   */
  public SuggestBox(SuggestOracle oracle, TextBoxBase box) {
    this(oracle, box, createSuggestionPopup());
  }

  /**
   * Constructor for {@link SuggestBox}. The text box will be removed from it's
   * current location and wrapped by the {@link SuggestBox}.
   * <p>
   * The default dropDownPanel selects the first suggestion to display and has
   * the style name of "GWT-SuggestBoxPopup" and has animations set to
   * ONE_WAY_CORNER. So, if you are passing in a custom drop down panel, make
   * sure you implement the features you are relying on.
   * </p>
   * 
   * @param oracle supplies suggestions based upon the current contents of the
   *          text widget
   * @param box the text widget
   * @param suggestionPopup the drop down panel used to display the suggestions
   */
  public SuggestBox(SuggestOracle oracle, TextBoxBase box,
      DropDownPanel<SuggestBox> suggestionPopup) {
    this.box = box;
    initWidget(box);
    suggestionMenu = new SuggestionMenu(true);
    this.suggestionPopup = suggestionPopup;
    suggestionPopup.setWidget(suggestionMenu);
    addKeyboardSupport();
    setOracle(oracle);
    setStyleName(STYLENAME_DEFAULT);
  }

  /**
   * Adds a listener to receive change events on the SuggestBox's text box. The
   * source Widget for these events will be the SuggestBox.
   * 
   * @param listener the listener interface to add
   */
  public void addChangeListener(ChangeListener listener) {
    if (changeListeners == null) {
      changeListeners = new DelegatingChangeListenerCollection(this, box);
    }
    changeListeners.add(listener);
  }

  /**
   * Adds a listener to recieve click events on the SuggestBox's text box. The
   * source Widget for these events will be the SuggestBox.
   * 
   * @param listener the listener interface to add
   */
  public void addClickListener(ClickListener listener) {
    if (clickListeners == null) {
      clickListeners = new DelegatingClickListenerCollection(this, box);
    }
    clickListeners.add(listener);
  }

  public void addEventHandler(SuggestionHandler handler) {
    if (suggestionHandlers == null) {
      suggestionHandlers = new ArrayList<SuggestionHandler>();
    }
    suggestionHandlers.add(handler);
  }

  /**
   * Adds a listener to receive focus events on the SuggestBox's text box. The
   * source Widget for these events will be the SuggestBox.
   * 
   * @param listener the listener interface to add
   */
  public void addFocusListener(FocusListener listener) {
    if (focusListeners == null) {
      focusListeners = new DelegatingFocusListenerCollection(this, box);
    }
    focusListeners.add(listener);
  }

  public void addHighlightHandler(HighlightHandler<Suggestion> handler) {
    handlers.add(HighlightEvent.class, handler);
  }

  /**
   * Adds a listener to recieve keyboard events on the SuggestBox's text box.
   * The source Widget for these events will be the SuggestBox.
   * 
   * @param listener the listener interface to add
   */
  public void addKeyboardListener(KeyboardListener listener) {
    if (keyboardListeners == null) {
      keyboardListeners = new DelegatingKeyboardListenerCollection(this, box);
    }
    keyboardListeners.add(listener);
  }

  /**
   * Gets the limit for the number of suggestions that should be displayed for
   * this box. It is up to the current {@link SuggestOracle} to enforce this
   * limit.
   * 
   * @return the limit for the number of suggestions
   */
  public int getLimit() {
    return limit;
  }

  /**
   * Gets the suggest box's {@link com.google.gwt.user.client.ui.SuggestOracle}.
   * 
   * @return the {@link SuggestOracle}
   */
  public SuggestOracle getSuggestOracle() {
    return oracle;
  }

  public int getTabIndex() {
    return box.getTabIndex();
  }

  public String getText() {
    return box.getText();
  }

  /**
   * Hide current suggestions.
   */
  public void hideSuggestions() {
    this.suggestionPopup.hide();
  }

  public boolean isAnimationEnabled() {
    return suggestionPopup.isAnimationEnabled();
  }

  public void removeChangeListener(ChangeListener listener) {
    if (changeListeners != null) {
      changeListeners.remove(listener);
    }
  }

  public void removeClickListener(ClickListener listener) {
    if (clickListeners != null) {
      clickListeners.remove(listener);
    }
  }

  public void removeEventHandler(SuggestionHandler handler) {
    if (suggestionHandlers == null) {
      return;
    }
    suggestionHandlers.remove(handler);
  }

  public void removeFocusListener(FocusListener listener) {
    if (focusListeners != null) {
      focusListeners.remove(listener);
    }
  }

  public void removeHighlightHandler(HighlightHandler<Suggestion> handler) {
    handlers.remove(HighlightEvent.class, handler);
  }

  public void removeKeyboardListener(KeyboardListener listener) {
    if (keyboardListeners != null) {
      keyboardListeners.remove(listener);
    }
  }

  /**
   * Select the ith suggestion currently displayed in the suggestions popup.
   * This method only operates with the popup is visible.
   * 
   * @param index the index of the ith item in the current popup
   */
  public void selectSuggestion(int index) {
    suggestionMenu.selectItem(index);
  }

  public void setAccessKey(char key) {
    box.setAccessKey(key);
  }

  public void setAnimationEnabled(boolean enable) {
    suggestionPopup.setAnimationEnabled(enable);
  }

  public void setFocus(boolean focused) {
    box.setFocus(focused);
  }

  /**
   * Sets the limit to the number of suggestions the oracle should provide. It
   * is up to the oracle to enforce this limit.
   * 
   * @param limit the limit to the number of suggestions provided
   */
  public void setLimit(int limit) {
    this.limit = limit;
  }

  /**
   * Sets the style name of the suggestion popup.
   * 
   * @param style the new primary style name
   * @see UIObject#setStyleName(String)
   */
  public void setPopupStyleName(String style) {
    suggestionPopup.setStyleName(style);
  }

  public void setTabIndex(int index) {
    box.setTabIndex(index);
  }

  public void setText(String text) {
    box.setText(text);
  }

  /**
   * Show current suggestions. Does not do anything unless suggest box is
   * attatched to DOM.
   */
  public void showSuggestions() {
    if (this.isAttached()) {
      refreshSuggestions();
    }
  }

  /**
   * <b>Affected Elements:</b>
   * <ul>
   * <li>-popup = The popup that appears with suggestions.</li>
   * <li>-items-item# = The suggested item at the specified index.</li>
   * </ul>
   * 
   * @see UIObject#onEnsureDebugId(String)
   */
  @Override
  protected void onEnsureDebugId(String baseID) {
    super.onEnsureDebugId(baseID);
    suggestionPopup.ensureDebugId(baseID + "-popup");
    suggestionMenu.setMenuItemDebugIds(baseID);
  }

  private void addKeyboardSupport() {
    box.addKeyboardListener(new KeyboardListenerAdapter() {

      @Override
      public void onKeyDown(Widget sender, char keyCode, int modifiers) {
        // Make sure that the menu is actually showing. These keystrokes
        // are only relevant when choosing a suggestion.
        if (suggestionPopup.isAttached()) {
          switch (keyCode) {
            case KeyboardListener.KEY_DOWN:
              suggestionMenu.selectItem(suggestionMenu.getSelectedItemIndex() + 1);
              break;
            case KeyboardListener.KEY_UP:
              suggestionMenu.selectItem(suggestionMenu.getSelectedItemIndex() - 1);
              break;
            case KeyboardListener.KEY_ENTER:
            case KeyboardListener.KEY_TAB:
              suggestionMenu.doSelectedItemAction();
              break;
          }
        }
      }

      @Override
      public void onKeyUp(Widget sender, char keyCode, int modifiers) {
        // After every user key input, refresh the popup's suggestions.
        if (keyCode != KeyboardListener.KEY_DOWN && keyCode != KEY_UP) {
          refreshSuggestions();
        }
      }

    });
  }

  private void fireSuggestionEvent(Suggestion selectedSuggestion) {
    if (suggestionHandlers != null) {
      SuggestionEvent event = new SuggestionEvent(this, selectedSuggestion);
      for (SuggestionHandler handler : suggestionHandlers) {
        handler.onSuggestionSelected(event);
      }
    }
  }

  private void refreshSuggestions() {
    // Get the raw text.
    String text = box.getText();
    if (text.equals(currentText) && suggestionPopup.isAttached()) {
      return;
    } else {
      currentText = text;
    }
    showSuggestions(text);
  }

  private void setNewSelection(SuggestionMenuItem menuItem) {
    Suggestion curSuggestion = menuItem.getSuggestion();
    currentText = curSuggestion.getReplacementString();
    box.setText(currentText);
    suggestionPopup.hide();
    fireSuggestionEvent(curSuggestion);
  }

  /**
   * Sets the suggestion oracle used to create suggestions.
   * 
   * @param oracle the oracle
   */
  private void setOracle(SuggestOracle oracle) {
    this.oracle = oracle;
  }

  /**
   * Show the given collection of suggestions.
   * 
   * @param suggestions suggestions to show
   */
  private void showSuggestions(Collection<? extends Suggestion> suggestions) {
    if (suggestions.size() > 0) {

      /*
       * Hide the popup before we manipulate the menu within it. If we do not do
       * this, some browsers will redraw the popup as items are removed and
       * added to the menu.
       * 
       * As an optimization, setVisible(false) is used in place of the hide()
       * method. hide() removes the popup from the DOM, whereas
       * setVisible(false) does not. Since the popup is going to be shown again
       * as soon as the menu is rebuilt, it makes more sense to leave the popup
       * attached to the DOM.
       * 
       * Notice that setVisible(true) is never called. This is because the call
       * to showAlignedPopup() will cause show() to be called, which in turn
       * calls setVisible(true).
       */
      suggestionPopup.setVisible(false);

      suggestionMenu.clearItems();

      for (Suggestion curSuggestion : suggestions) {
        final SuggestionMenuItem menuItem = new SuggestionMenuItem(
            curSuggestion, oracle.isDisplayStringHTML());
        menuItem.setCommand(new Command() {
          public void execute() {
            SuggestBox.this.setNewSelection(menuItem);
          }
        });

        suggestionMenu.addItem(menuItem);
      }

      suggestionPopup.showRelativeTo(this);
    } else {
      suggestionPopup.hide();
    }
  }

  private void showSuggestions(String query) {
    if (query.length() == 0) {
      oracle.requestDefaultSuggestions(new Request(null, limit), callback);
    } else {
      oracle.requestSuggestions(new Request(query, limit), callback);
    }
  }
}
