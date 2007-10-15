/*
 * Copyright 2007 Google Inc.
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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

/**
 * {@link PinnedPanel} creates a panel that is, by default, pinned in place.
 * When the pinned state it toggled, the contents of the panel will display only
 * when the users mouse hovers over it, otherwise is will collapse to the left.
 * <p>
 * The default style name is gwt-PinnedPanel.
 * <p>
 * Planned enhancements: Allow panel to be collapsed in arbitrary direction.
 */
public class PinnedPanel extends Composite {
  DelayAction delayAction = new DelayAction();

  private class DelayAction extends Timer {
    private static final int DELAY_MILLI = 20;
    private static final int NONE = -10000;
    int savedState;

    public void activate() {
      if (savedState == state.currentState) {
        // Do nothing, timer is already running.
        return;
      } else if (savedState != NONE) {
        this.cancel();
      }
      savedState = state.currentState;
      delayAction.schedule(DELAY_MILLI);
    }

    public void run() {
      if (savedState != state.currentState) {
        throw new IllegalStateException("How did this happen?" + savedState
            + " current state:" + state.currentState);
      }
      if (state.currentState == State.WILL_HIDE) {
        impl.hide();
      } else if (state.currentState == State.WILL_SHOW) {
        impl.show();
      } else {
        throw new IllegalStateException("Why are we in state "
            + state.currentState + " rather than state " + savedState);
      }

      savedState = NONE;
    }
  }

  /**
   * Pinned panel impl.
   */
  abstract static class PinnedPanelImpl {
    abstract class SlidingTimer extends Timer {
      int interval;
      boolean cancel;

      public void run() {
        if (cancel) {
          cancel = false;
        } else {
          if (processSizeChange()) {
            this.schedule(OVERLAY_SPEED);
          } else {
            finish();
          }
        }
      }

      protected abstract void finish();

      protected abstract boolean processSizeChange();
    }

    private class HidingTimer extends SlidingTimer {
      protected void finish() {
        pin.state.currentState = State.IS_HIDDEN;
      }

      protected boolean processSizeChange() {
        pin.currentWidth -= interval;
        pin.currentWidth = Math.max(pin.currentWidth, pin.peekoutSize);
        setPanelWidth(pin.currentWidth);
        return pin.currentWidth > pin.peekoutSize;
      }
    }
    private class OverlayTimer extends SlidingTimer {
      protected void finish() {
        pin.state.currentState = State.IS_SHOWN;
      }

      protected boolean processSizeChange() {
        pin.currentWidth += interval;
        pin.currentWidth = Math.min(pin.currentWidth, pin.width);
        setPanelWidth(pin.currentWidth);
        return pin.currentWidth < pin.width;
      }
    }

    private static int OVERLAY_SPEED = 1;

    private static int NUMBER_OF_INTERVALS = 20;

    protected PinnedPanel pin;
    protected Element e;

    OverlayTimer overlayTimer = new OverlayTimer();

    HidingTimer hidingTimer = new HidingTimer();

    public void setPinnedPanel(PinnedPanel pinnedPanel) {
      pin = pinnedPanel;
      e = pinnedPanel.getElement();
      DOM.setStyleAttribute(e, "position", "relative");
      DOM.setStyleAttribute(e, "overflow", "hidden");
    }

    protected void hide() {
      pin.state.currentState = State.HIDING;
      overlayTimer.cancel = true;
      hidingTimer.cancel = false;
      hidingTimer.interval =
          (pin.width - pin.peekoutSize) / NUMBER_OF_INTERVALS;
      hidingTimer.run();
    }

    protected abstract void setPanelWidth(int width);

    protected void show() {
      pin.state.currentState = State.SHOWING;
      overlayTimer.cancel = false;
      hidingTimer.cancel = true;
      overlayTimer.interval =
          (pin.width - pin.peekoutSize) / NUMBER_OF_INTERVALS;
      overlayTimer.run();
    }

    protected void showPinned() {
      // for later cleanup code.
    }
  }

  static class PinnedPanelImplStandard extends PinnedPanelImpl {
    protected void setPanelWidth(int width) {

      DOM.setStyleAttribute(e, "marginRight", pin.getRightMargin()
          - (width - pin.peekoutSize) + "px");
      DOM.setStyleAttribute(pin.innerContents.getElement(), "marginLeft",
          -(pin.width - width) + "px");
      DOM.setStyleAttribute(e, "width", width + "px");
    }
  }

  static class State {
    static int MUST_HIDE = 0;
    static int WILL_HIDE = 1;
    static int HIDING = 2;
    static int IS_HIDDEN = 3;
    static int WILL_SHOW = -1;
    static int SHOWING = -2;
    static int IS_SHOWN = -3;
    int currentState;

    public boolean shouldHide() {
      return (currentState < 0);
    }

    public boolean shouldShow() {
      return currentState > 0;
    }
  }

  private static final String DEFAULT_STYLENAME = "gwt-PinnedPanel";

  State state = new State();

  private int userDefinedRightMargin = 0;

  // Managed to remove ie/firefox differences, left impl class seperated for
  // later support of safari and opera.
  private PinnedPanelImpl impl = new PinnedPanelImplStandard();

  private int width;
  private int currentWidth;
  private HorizontalPanel innerContents = new HorizontalPanel();
  private int peekoutSize;
  private ToggleButton switchButton;

  /**
   * Create a new {@link PinnedPanel}. The toggle button is always going to be
   * displayed.
   * 
   * @param width width of the panel in it's exposed state.
   * @param pinnedToggle the button to toggle the collapsing state
   */
  public PinnedPanel(int width, ToggleButton pinnedToggle, Widget contents) {
    this.switchButton = pinnedToggle;
    SimplePanel container = new SimplePanel() {
      {
        sinkEvents(Event.ONMOUSEOUT);
        sinkEvents(Event.ONMOUSEOVER);
      }

      public void onBrowserEvent(Event event) {

        if (!switchButton.isDown()) {
          switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEOUT:
              Element to = DOM.eventGetToElement(event);
              if (to != null) {
                maybeHide(to);
              }
              break;
            case Event.ONMOUSEOVER:
              Element from = DOM.eventGetFromElement(event);
              maybeShow(from);
              break;
          }
        }
        super.onBrowserEvent(event);
        setStyleName(DEFAULT_STYLENAME);
      }

      private void maybeHide(Element e) {
        if (!DOM.isOrHasChild(this.getElement(), e)) {
          if ((state.shouldHide())) {
            state.currentState = State.WILL_HIDE;
            delayAction.activate();
          }
        }
      }

      private void maybeShow(Element e) {
        if (!DOM.isOrHasChild(this.getElement(), e)) {
          if ((state.shouldShow())) {
            state.currentState = State.WILL_SHOW;
            delayAction.activate();
          }
        }
      }

    };
    container.setSize("100%", "100%");
    initWidget(container);
    innerContents.setHeight("100%");
    innerContents.add(contents);
    innerContents.setCellWidth(contents, "100%");
    innerContents.setCellHeight(contents, "100%");
    innerContents.add(switchButton);
    innerContents.setCellHeight(switchButton, "100%");

    container.setWidget(innerContents);
    impl.setPinnedPanel(this);

    setWidth(width + "px");
    currentWidth = width;
    this.width = width;
    setStyleName(DEFAULT_STYLENAME);
    switchButton.setDown(true);
    switchButton.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        setPinned(switchButton.isDown());
      }

    });
  }

  public int getRightMargin() {
    return userDefinedRightMargin;
  }

  public ToggleButton getSwitchButton() {
    return switchButton;
  }

  /**
   * As {@link PinnedPanel} needs to manipulate the right margin, the right
   * margin must be controlled in code.
   * 
   * @param rightMargin in px
   */
  public void setRightMargin(int rightMargin) {
    this.userDefinedRightMargin = rightMargin;
    if (switchButton.isDown()) {
      impl.showPinned();
    } else {
      impl.hide();
    }
  }

  public void setWidget(Widget widget) {
  }

  /**
   * This method is called immediately after a widget becomes attached to the
   * browser's document.
   */
  protected void onLoad() {
    int clientWidth =
        DOM.getElementPropertyInt(this.getElement(), "clientWidth");
    innerContents.setWidth(clientWidth + "px");
    this.peekoutSize = switchButton.getOffsetWidth();
    innerContents.setCellWidth(switchButton, peekoutSize + "px");
  }

  private void setPinned(boolean pinned) {
    state.currentState = State.MUST_HIDE;
    switchButton.setDown(pinned);
    if (pinned) {
      impl.showPinned();
    } else {
      impl.hide();
    }
  }

}
