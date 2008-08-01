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
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

import java.util.List;

/**
 * 
 * THIS IS NOT AN API CLASS.
 * <p>
 * Currently, this class is public as it is needed for incubator widgets in two
 * packages. However, it is not being designed to be generally extensible or
 * usable by others. If there is a call for that sort of class, we can certainly
 * create one, however this is is not it. Really, I mean it.
 * <p>
 * The SuggestionMenuImpl used for the display and selection of suggestions in
 * the SuggestBox and CustomListBox widgets. SuggestionMenu differs from MenuBar
 * in that it always has a vertical orientation, and it has no submenus. It also
 * allows for programmatic selection of items in the menu, and programmatically
 * performing the action associated with the selected item. In the MenuBar
 * class, items cannot be selected programatically - they can only be selected
 * when the user places the mouse over a particlar item. Additional methods in
 * SuggestionMenu provide information about the number of items in the menu, and
 * the index of the currently selected item.
 */
public abstract class SuggestionMenuImpl extends MenuBar {

  /**
   * Class for menu items in a SuggestionMenu. A SuggestionMenuItem differs from
   * a MenuItem in that each item is backed by a Suggestion object. The text of
   * each menu item is derived from the display string of a Suggestion object,
   * and each item stores a reference to its Suggestion object.
   */
  public class SuggestionItem extends MenuItem {

    private static final String STYLENAME_DEFAULT = "item";

    private Suggestion suggestion;

    public SuggestionItem(Suggestion suggestion, boolean asHTML) {
      super(suggestion.getDisplayString(), asHTML);
      // Each suggestion should be placed in a single row in the suggestion
      // menu. If the window is resized and the suggestion cannot fit on a
      // single row, it should be clipped (instead of wrapping around and
      // taking up a second row).
      DOM.setStyleAttribute(getElement(), "whiteSpace", "nowrap");
      setStyleName(STYLENAME_DEFAULT);
      setSuggestion(suggestion);
      setCommand(new Command() {
        public void execute() {
          onValueUpdated(SuggestionItem.this);
        }
      });
    }

    public Suggestion getSuggestion() {
      return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
      this.suggestion = suggestion;
    }

    protected void updateStyle(boolean highlight) {
      super.setSelectionStyle(highlight);
    }

    /**
     * Converting overridable method from selection --> highlight.
     */
    void setSelectionStyle(boolean selected) {
      updateStyle(selected);
    }
  }

  public SuggestionMenuImpl() {
    super(true);
    // Make sure that CSS styles specified for the default Menu classes
    // do not affect this menu
    setStyleName("");
  }

  public void addItem(Suggestion suggestion, boolean isHTML) {
    SuggestionItem menuItem = new SuggestionItem(suggestion, isHTML);
    this.addItem(menuItem);
  }

  public void doCurrentAction() {
    // In order to perform the action of the item that is currently
    // selected, the menu must be showing.
    MenuItem selectedItem = getSelectedItem();
    if (selectedItem != null) {
      doItemAction(selectedItem, true);
    }
  }

  /**
   * Returns the index of the menu item that is currently chosen.
   */
  public int getHighlightedIndex() {
    // The index of the currently selected item can only be
    // obtained if the menu is showing.
    MenuItem selectedItem = getSelectedItem();
    if (selectedItem != null) {
      return getItems().indexOf(selectedItem);
    }
    return -1;
  }

  public int getNumItems() {
    return getItems().size();
  }

  /**
   * Highlights the item at the specified index in the menu. Selecting the item
   * does not perform the item's associated action; it only changes the style of
   * the item and updates the value of SuggestionMenu.selectedItem.
   */
  public void highlightItem(int index) {
    List<MenuItem> items = getItems();
    if (index > -1 && index < items.size()) {
      itemOver(items.get(index));
    }
  }

  protected SuggestionItem getHighlightedItem() {
    return (SuggestionItem) getSelectedItem();
  }

  protected final void highlightItem(SuggestionItem s) {
    selectItem(s);
  }

  /**
   * <b>Affected Elements:</b>
   * <ul>
   * <li>-item# = the {@link MenuItem} at the specified index.</li>
   * </ul>
   * 
   * @see UIObject#onEnsureDebugId(String)
   */
  @Override
  protected void onEnsureDebugId(String baseID) {
    // We do not set the super debug id as there is no base object to hang the
    // menu off of.
    setMenuItemDebugIds(baseID);
  }

  protected abstract void onHighlight(SuggestionItem s);

  protected abstract void onValueUpdated(SuggestionItem s);

  final void selectItem(MenuItem item) {
    super.selectItem(item);
    onHighlight((SuggestionItem) item);
  }

}