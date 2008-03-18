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

package com.google.gwt.widgetideas.datepicker.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A popup panel that can position itself below another widget.
 * 
 */
public class DropDownPanel extends PopupPanel {
  public DropDownPanel() {
    super(true);
  }

  /**
   * The default position of the DropDown is directly below its associated
   * widget's, with its left edge aligned with the left edge of the box.
   * Depending on the width and height of the popup and the distance from the
   * box to the bottom and right edges of the window, the popup may be displayed
   * directly above the box, and/or its right edge may be aligned with the right
   * edge of the box.
   */
  public void showBelow(final Widget showBelowMe) {

    // Set the position of the popup right before it is shown.
    setPopupPositionAndShow(new PositionCallback() {
      public void setPosition(int offsetWidth, int offsetHeight) {
        // Calculate left position for the popup.
        int left = showBelowMe.getAbsoluteLeft();
        int offsetWidthDiff = offsetWidth - showBelowMe.getOffsetWidth();

        // If the popup is not as wide as the dropDownFrom, always align
        // to the left edge of the box. Otherwise, figure out whether to
        // left-align or right-align the popup.
        if (offsetWidthDiff > 0) {
          // Make sure scrolling is taken into account, since
          // box.getAbsoluteLeft()
          // takes scrolling into account.
          int windowRight = Window.getClientWidth() + Window.getScrollLeft();
          int windowLeft = Window.getScrollLeft();

          // Distance from the left edge of the box to the right edge of the
          // window
          int distanceToWindowRight = windowRight - left;

          // Distance from the left edge of the box to the left edge of the
          // window
          int distanceFromWindowLeft = left - windowLeft;

          // If there is not enough space for the popup's width overflow to the
          // right of the box and there IS enough space for the popup's
          // width overflow to the left of the box, then right-align
          // the popup. However, if there is not enough space on either side,
          // then stick with left-alignment.
          if (distanceToWindowRight < offsetWidth
              && distanceFromWindowLeft >= (offsetWidth - showBelowMe.getOffsetWidth())) {
            // Align with the right edge of the dropDownFrom.
            left -= offsetWidthDiff;
          }
        }

        // Calculate top position for the popup
        int top = showBelowMe.getAbsoluteTop();

        // Make sure scrolling is taken into account, since
        // dropDownFrom.getAbsoluteTop()
        // takes scrolling into account.
        int windowTop = Window.getScrollTop();
        int windowBottom = Window.getScrollTop() + Window.getClientHeight();

        // Distance from the top edge of the window to the top edge of the
        // box
        int distanceFromWindowTop = top - windowTop;

        // Distance from the bottom edge of the window to the bottom edge of the
        // box
        int distanceToWindowBottom = windowBottom
            - (top + showBelowMe.getOffsetHeight());

        // If there is not enough space for the popup's height below the box
        // and there IS enough space for the popup's height above the box,
        // then then position the popup above the dropDownFrom. However, if
        // there
        // is
        // not enough space on either side, then stick with displaying the popup
        // below the dropDownFrom.
        if (distanceToWindowBottom < offsetHeight
            && distanceFromWindowTop >= offsetHeight) {
          top -= offsetHeight;
        } else {
          // Position above the box
          top += showBelowMe.getOffsetHeight();
        }

        setPopupPosition(left, top);
      }
    });
  }
}
