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

package com.google.gwt.gen2.commonwidget.client;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupListenerCollection;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.PopupImpl;

/**
 * A popup panel that can position itself relative to another widget.
 * 
 * @param <R> Relative widget type
 */
public class DropDownPanel<R extends Widget> extends PopupPanel {
  /**
   * An {@link Animation} used to enlarge the popup into view.
   */
  private static class RollDownAnimation extends Animation {
    private static final PopupImpl impl = GWT.create(PopupImpl.class);

    /**
     * The {@link DropDownPanel} being affected.
     */
    private DropDownPanel<? extends Widget> curPanel = null;

    /**
     * The offset height and width of the current {@link PopupPanel}.
     */
    private int offsetHeight, offsetWidth = -1;

    /**
     * A boolean indicating whether we are showing or hiding the popup.
     */
    private boolean showing = false;

    @Override
    public void onCancel() {
      onComplete();
    }

    @Override
    public void onComplete() {
      if (!showing) {
        RootPanel.get().remove(curPanel);
        impl.onHide(curPanel.getElement());
        PopupListenerCollection popupListeners = curPanel.getPopupListeners();
        if (popupListeners != null) {
          popupListeners.firePopupClosed(curPanel, curPanel.autoHiding);
        }
      }
      impl.setClip(curPanel.getElement(), "rect(auto, auto, auto, auto)");
      DOM.setStyleAttribute(curPanel.getElement(), "overflow", "visible");
      curPanel = null;
    }

    public void onInstantaneousRun() {
      if (showing) {
        DOM.setStyleAttribute(curPanel.getElement(), "position", "absolute");
        RootPanel.get().add(curPanel);
        impl.onShow(curPanel.getElement());
      } else {
        RootPanel.get().remove(curPanel);
        impl.onHide(curPanel.getElement());
      }
      DOM.setStyleAttribute(curPanel.getElement(), "overflow", "visible");
      curPanel = null;
    }

    @Override
    public void onStart() {
      // Attach to the page
      if (showing) {
        // Set the position attribute, and then attach to the DOM. Otherwise,
        // the PopupPanel will appear to 'jump' from its static/relative
        // position to its absolute position (issue #1231).
        DOM.setStyleAttribute(curPanel.getElement(), "position", "absolute");
        impl.setClip(curPanel.getElement(), getRectString(0, 0, 0, 0));
        RootPanel.get().add(curPanel);
        impl.onShow(curPanel.getElement());
      }
      offsetHeight = curPanel.getOffsetHeight();
      offsetWidth = curPanel.getOffsetWidth();
      DOM.setStyleAttribute(curPanel.getElement(), "overflow", "hidden");
      onUpdate(0.0);
    }

    @Override
    public void onUpdate(double progress) {
      if (!showing) {
        progress = 1.0 - progress;
      }

      // Determine the clipping size
      int top = 0;
      int left = 0;
      int right = offsetWidth;
      int bottom = (int) (progress * offsetHeight);

      // Set the rect clipping
      impl.setClip(curPanel.getElement(), getRectString(top, right, bottom,
          left));
    }

    /**
     * Open or close the content. This method always called immediately after
     * the PopupPanel showing state has changed, so we base the animation on the
     * current state.
     * 
     * @param panel the panel to open or close
     */
    public void setOpen(final DropDownPanel<? extends Widget> panel) {
      // Immediately complete previous open/close animation
      cancel();

      // Determine if we need to animate
      boolean animate = panel.isAnimationEnabled;

      // Open the new item
      showing = panel.showing;
      curPanel = panel;
      if (animate) {
        run(200);
      } else {
        onInstantaneousRun();
      }
    }

    /**
     * @return a rect string
     */
    private String getRectString(int top, int right, int bottom, int left) {
      return "rect(" + top + "px, " + right + "px, " + bottom + "px, " + left
          + "px)";
    }
  }

  private RollDownAnimation rollDownAnimation;

  private boolean isAnimationEnabled = false;

  private boolean autoHiding, showing;

  private R anchor;

  public DropDownPanel() {
    super(false);
  }
  
  @Override
  public void hide() {
    hide(false);
  }

  public void hide(boolean autohide) {
    if (!showing) {
      return;
    }
    showing = false;
    autoHiding = autohide;
    anchor = null;
    // Hide the popup
    animate();
  }

  @Override
  public boolean isAnimationEnabled() {
    return isAnimationEnabled;
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
  public void setAnimationEnabled(boolean enable) {
    isAnimationEnabled = enable;
  }

  @Override
  public void show() {
    if (showing) {
      return;
    }
    showing = true;
    DOM.addEventPreview(this);

    animate();
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

  private void animate() {
    if (rollDownAnimation == null) {
      rollDownAnimation = new RollDownAnimation();
    }
    rollDownAnimation.setOpen(this);
  }

  private native PopupListenerCollection getPopupListeners()/*-{
    this.@com.google.gwt.user.client.ui.PopupPanel::popupListeners;
  }-*/;
}
