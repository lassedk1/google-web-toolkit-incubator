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

package com.google.gwt.demos.statebutton.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanelImpl;
import com.google.gwt.user.client.ui.Widget;

/**
 * A simple popup menu.
 */

public class PopupMenu extends MenuBar {
  /**
   * A PopupPanel with a SuggestionMenu as its widget. The SuggestionMenu is
   * placed in a PopupPanel so that it can be displayed at various positions
   * around the SuggestBox's text field. Moreover, the SuggestionMenu needs to
   * appear on top of any other widgets on the page, and the PopupPanel provides
   * this behavior.
   * 
   * A non-static member class is used because the popup uses the SuggestBox's
   * SuggestionMenu as its widget, and the position of the SuggestBox's TextBox
   * is needed in order to correctly position the popup.
   */
  private class Popup extends PopupPanelImpl {
    private static final String STYLENAME_DEFAULT = "gwt-PopupMenu-popup";

    public Popup() {
      super(true);
      setWidget(PopupMenu.this);
      setStyleName(STYLENAME_DEFAULT);
    }

    public void showAlignedPopup() {
      // Set the position of the popup right before it is shown.
      setPopupPositionAndShow(new PositionCallback() {
        public void setPosition(int offsetWidth, int offsetHeight) {
          // Calculate left position for the popup.

          int left = target.getAbsoluteLeft();
          int offsetWidthDiff = offsetWidth - target.getOffsetWidth();

          // If the popup is not as wide as the text box, always
          // align to the left edge of the text box. Otherwise, figure out
          // whether to
          // left-align or right-align the popup.
          if (offsetWidthDiff > 0) {
            // Make sure scrolling is taken into account, since
            // box.getAbsoluteLeft()
            // takes scrolling into account.
            int windowRight = Window.getClientWidth() + Window.getScrollLeft();
            int windowLeft = Window.getScrollLeft();

            // Distance from the left edge of the text box to the right edge of
            // the
            // window
            int distanceToWindowRight = windowRight - left;

            // Distance from the left edge of the text box to the left edge of
            // the
            // window
            int distanceFromWindowLeft = left - windowLeft;

            // If there is not enough space for the popup's width overflow to
            // the
            // right of the text box and there IS enough space for the popup's
            // width overflow to the left of the text box, then right-align
            // the popup. However, if there is not enough space on either side,
            // then stick with left-alignment.
            if (distanceToWindowRight < offsetWidth
                && distanceFromWindowLeft >= (offsetWidth - target.getOffsetWidth())) {
              // Align with the right edge of the text box.
              left -= offsetWidthDiff;
            }
          }

          // Calculate top position for the popup

          int top = target.getAbsoluteTop();

          // Make sure scrolling is taken into account, since
          // box.getAbsoluteTop()
          // takes scrolling into account.
          int windowTop = Window.getScrollTop();
          int windowBottom = Window.getScrollTop() + Window.getClientHeight();

          // Distance from the top edge of the window to the top edge of the
          // text
          // box
          int distanceFromWindowTop = top - windowTop;

          // Distance from the bottom edge of the window to the bottom edge of
          // the
          // text box
          int distanceToWindowBottom = windowBottom
              - (top + target.getOffsetHeight());

          // If there is not enough space for the popup's height below the text
          // box
          // and there IS enough space for the popup's height above the text
          // box,
          // then then position the popup above the text box. However, if there
          // is
          // not enough space on either side, then stick with displaying the
          // popup
          // below the text box.
          if (distanceToWindowBottom < offsetHeight
              && distanceFromWindowTop >= offsetHeight) {
            top -= offsetHeight;
          } else {
            // Position above the text box
            top += target.getOffsetHeight();
          }

          setPopupPosition(left, top);
        }
      });
    }
  }

  private static String DEFAULT_STYLENAME = "gwt-PopupMenu";
  Widget target;

  Popup popup = new Popup();

  public PopupMenu(Widget target) {
    super(true);
    setStyleName(DEFAULT_STYLENAME);
  }

  public void showMenu() {
    popup.showAlignedPopup();
  }

}
