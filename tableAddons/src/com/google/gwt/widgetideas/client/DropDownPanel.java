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

import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.PopupPanelImpl;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;

/**
 * A popup panel that can position itself relative to another widget.
 * 
 * @param <R> Relative widget type
 */
// Direct copy of gen2 version, will be deprecated soon.
public class DropDownPanel<R extends Widget> extends PopupPanelImpl {

  /**
   * An animation used to enlarge the popup into view.
   */
  private static class RollDownAnimation extends PopupAnimation {

    public RollDownAnimation(PopupPanelImpl panel) {
      super(panel);
    }

    @Override
    public void onUpdate(double progress) {
      if (!isShowing()) {
        progress = 1.0 - progress;
      }

      // Determine the clipping size
      int top = 0;
      int left = 0;
      int right = getOffsetWidth();
      int bottom = (int) (progress * getOffsetHeight());

      // Set the rect clipping
      clip(top, right, bottom, left);
    }
  }

  // Set of open panels so we can close them on window resize, because resizing
  // the window is equivalent to the user clicking outside the widget.
  private static ArrayList<DropDownPanel> openPanels;

  private static WindowResizeListener resizeListener = new WindowResizeListener() {

    public void onWindowResized(int width, int height) {
      for (DropDownPanel panel : openPanels) {
        panel.hide();
      }
    }
  };

  private R anchor;

  /**
   * Constructor.
   */
  public DropDownPanel() {
    super(false);
    setAnimation(new RollDownAnimation(this));
  }

  @Override
  public void hide() {
    hide(false);
  }

  @Override
  public void hide(boolean autohide) {
    if (!isShowing()) {
      return;
    }
    super.hide(autohide);

    // Removes this from the list of open panels.
    openPanels.remove(this);
  }

  @Override
  public boolean onEventPreview(Event event) {
    Element target = event.getTarget();

    boolean eventTargetsPopup = (target != null)
        && getElement().isOrHasChild(target);

    boolean eventTargetsAnchor = (target != null) && (anchor != null)
        && anchor.getElement().isOrHasChild(target);

    int type = DOM.eventGetType(event);
    switch (type) {
      case Event.ONMOUSEDOWN:
        if (!eventTargetsPopup && !eventTargetsAnchor) {
          hide(true);
          return true;
        }
        break;
    }
    return super.onEventPreview(event);
  }

  @Override
  public void show() {
    if (isShowing()) {
      return;
    }
    super.show();

    // Add this to the set of open panels.
    if (openPanels == null) {
      openPanels = new ArrayList();
      Window.addWindowResizeListener(resizeListener);
    }
    openPanels.add(this);
  }

  /**
   * The default position of the DropDown is directly below its decorated
   * widget's, with its left edge aligned with the left edge of the widget.
   * Depending on the width and height of the popup and the distance from the
   * widget to the bottom and right edges of the window, the popup may be
   * displayed directly above the widget, and/or its right edge may be aligned
   * with the right edge of the widget.
   */

  public final void showRelativeTo(final R widget) {
    anchor = widget;
    // Set the position of the popup right before it is shown.
    setPopupPositionAndShow(new PositionCallback() {
      public void setPosition(int offsetWidth, int offsetHeight) {
        position(widget, offsetWidth, offsetHeight);
      }

    });
  }

  /**
   * Positions the popup, called after the offset width and height of the drop
   * down are known.
   * 
   * @param relativeWidget the widget
   * @param offsetWidth the drop down's offset width
   * @param offsetHeight the drop down's offset height
   */
  protected void position(final R relativeWidget, int offsetWidth,
      int offsetHeight) {
    // Calculate left position for the popup. The computation for
    // the left position is bidi-sensitive.

    int textBoxOffsetWidth = relativeWidget.getOffsetWidth();

    // Compute the difference between the popup's width and the
    // textbox's width
    int offsetWidthDiff = offsetWidth - textBoxOffsetWidth;

    int left;

    if (LocaleInfo.getCurrentLocale().isRTL()) { // RTL case

      int textBoxAbsoluteLeft = relativeWidget.getAbsoluteLeft();

      // Right-align the popup. Note that this computation is
      // valid in the case where offsetWidthDiff is negative.
      left = textBoxAbsoluteLeft - offsetWidthDiff;

      // If the suggestion popup is not as wide as the text box, always
      // align to the right edge of the text box. Otherwise, figure out whether
      // to right-align or left-align the popup.
      if (offsetWidthDiff > 0) {

        // Make sure scrolling is taken into account, since
        // box.getAbsoluteLeft() takes scrolling into account.
        int windowRight = Window.getClientWidth() + Window.getScrollLeft();
        int windowLeft = Window.getScrollLeft();

        // Compute the left value for the right edge of the textbox
        int textBoxLeftValForRightEdge = textBoxAbsoluteLeft
            + textBoxOffsetWidth;

        // Distance from the right edge of the text box to the right edge
        // of the window
        int distanceToWindowRight = windowRight - textBoxLeftValForRightEdge;

        // Distance from the right edge of the text box to the left edge of the
        // window
        int distanceFromWindowLeft = textBoxLeftValForRightEdge - windowLeft;

        // If there is not enough space for the overflow of the popup's
        // width to the right of the text box and there IS enough space for the
        // overflow to the right of the text box, then left-align the popup.
        // However, if there is not enough space on either side, stick with
        // right-alignment.
        if (distanceFromWindowLeft < offsetWidth
            && distanceToWindowRight >= offsetWidthDiff) {
          // Align with the left edge of the text box.
          left = textBoxAbsoluteLeft;
        }
      }
    } else { // LTR case

      // Left-align the popup.
      left = relativeWidget.getAbsoluteLeft();

      // If the suggestion popup is not as wide as the text box, always align to
      // the left edge of the text box. Otherwise, figure out whether to
      // left-align or right-align the popup.
      if (offsetWidthDiff > 0) {
        // Make sure scrolling is taken into account, since
        // box.getAbsoluteLeft() takes scrolling into account.
        int windowRight = Window.getClientWidth() + Window.getScrollLeft();
        int windowLeft = Window.getScrollLeft();

        // Distance from the left edge of the text box to the right edge
        // of the window
        int distanceToWindowRight = windowRight - left;

        // Distance from the left edge of the text box to the left edge of the
        // window
        int distanceFromWindowLeft = left - windowLeft;

        // If there is not enough space for the overflow of the popup's
        // width to the right of hte text box, and there IS enough space for the
        // overflow to the left of the text box, then right-align the popup.
        // However, if there is not enough space on either side, then stick with
        // left-alignment.
        if (distanceToWindowRight < offsetWidth
            && distanceFromWindowLeft >= offsetWidthDiff) {
          // Align with the right edge of the text box.
          left -= offsetWidthDiff;
        }
      }
    }

    // Calculate top position for the popup

    int top = relativeWidget.getAbsoluteTop();

    // Make sure scrolling is taken into account, since
    // box.getAbsoluteTop() takes scrolling into account.
    int windowTop = Window.getScrollTop();
    int windowBottom = Window.getScrollTop() + Window.getClientHeight();

    // Distance from the top edge of the window to the top edge of the
    // text box
    int distanceFromWindowTop = top - windowTop;

    // Distance from the bottom edge of the window to the bottom edge of
    // the text box
    int distanceToWindowBottom = windowBottom
        - (top + relativeWidget.getOffsetHeight());

    // If there is not enough space for the popup's height below the text
    // box and there IS enough space for the popup's height above the text
    // box, then then position the popup above the text box. However, if there
    // is not enough space on either side, then stick with displaying the
    // popup below the text box.
    if (distanceToWindowBottom < offsetHeight
        && distanceFromWindowTop >= offsetHeight) {
      top -= offsetHeight;
    } else {
      // Position above the text box
      top += relativeWidget.getOffsetHeight();
    }
    setPopupPosition(left, top);
  }

}
