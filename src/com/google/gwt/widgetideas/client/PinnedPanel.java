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
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

/**
 * {@link PinnedPanel} creates a panel that is, by default, pinned in place.
 * When the pinned state it toggled, the contents of the panel will display only
 * when the users mouse hovers over it, otherwise is will collapse to the left.
 * A {@link ChangeEvent} is fired whenever the {@link PinnedPanel} changes it
 * pinned state.
 * <p>
 * The default style name is gwt-PinnedPanel.
 * <p>
 * Planned enhancements: Allow panel to be collapsed in arbitrary direction.
 */
public class PinnedPanel extends Composite implements SourcesChangeEvents {
  /**
   * Hides the {@link PinnedPanel}
   */
  class HidingTimer extends SlidingTimer {
    protected void finish() {
      state.currentState = State.IS_HIDDEN;
    }

    protected boolean processSizeChange() {
      currentOffshift -= interval;
      currentOffshift = Math.max(currentOffshift, 0);
      impl.setPanelPos(currentOffshift);
      return currentOffshift >= 0;
    }
  }

  /**
   * Shows the hovering {@link PinnedPanel}
   */
  class ShowingTimer extends SlidingTimer {
    protected void finish() {
      state.currentState = State.IS_SHOWN;
    }

    protected boolean processSizeChange() {
      currentOffshift += interval;
      currentOffshift = Math.min(currentOffshift, maxOffshift);
      impl.setPanelPos(currentOffshift);
      return currentOffshift < maxOffshift;
    }
  }

  /**
   * Common code for {@link HidingTimer} and {@link ShowingTimer}.
   */
  abstract class SlidingTimer extends Timer {
    int interval;
    boolean fireChangeListeners = false;

    public void run() {
      if (processSizeChange()) {
        this.schedule(OVERLAY_SPEED);
      } else {
        if (fireChangeListeners) {
          changeListeners.fireChange(PinnedPanel.this);
          fireChangeListeners = false;
        }
        finish();
      }
    }

    protected abstract void finish();

    protected abstract boolean processSizeChange();
  }

  /**
   * Delays showing of the {@link PinnedPanel}.
   */
  private class DelayShow extends Timer {

    public void activate() {
      delayedShow.schedule(DELAY_MILLI);
    }

    public void run() {
      show();
    }
  }

  /**
   * Pinned panel implementation. Factored out of {@link PinnedPanel} to allow
   * eventual use of differed bindings.
   */
  private static class PinnedPanelImpl {
    protected PinnedPanel pin;
    protected Element e;
    protected Element mover;

    public void setPinnedPanel(PinnedPanel pinnedPanel) {
      pin = pinnedPanel;
      e = pinnedPanel.getElement();
      mover = pinnedPanel.mover.getElement();
    }

    protected void becomeOverlay() {
      int hoverBarWidth = pin.hoverContainer.getOffsetWidth();
      int aboutHalf = (hoverBarWidth / 2) + 1;
      int offset = pin.width - aboutHalf - pin.getRightMargin();
      int newWidth = pin.width + aboutHalf;
      pin.maxOffshift = newWidth;
      pin.currentOffshift = pin.width;
      // Notice that the actual width does not change.
      pin.setWidth(newWidth + "px");
      DOM.setStyleAttribute(e, "marginRight", -offset + "px");
      pin.hide();
    }

    protected void becomePinned() {
      DOM.setStyleAttribute(e, "marginRight", pin.getRightMargin() + "px");
      DOM.setStyleAttribute(mover, "left", "0px");
      pin.state.currentState = State.PINNED;

      // The master width needs to be readjusted back to it's original size.
      if (pin.isAttached()) {
        pin.refreshWidth();
      }
    }

    protected void setPanelPos(int pos) {
      DOM.setStyleAttribute(mover, "left", pos - pin.width + "px");
    }
  }

  /**
   * Current {@link PinnedPanel} state.
   */
  private static class State {
    static int MUST_HIDE = 0;
    static int WILL_HIDE = 1;
    static int HIDING = 2;
    static int IS_HIDDEN = 3;
    static int WILL_SHOW = -1;
    static int SHOWING = -2;
    static int IS_SHOWN = -3;
    static int PINNED = -1000;
    int currentState;

    public boolean shouldHide() {
      return (currentState < 0);
    }

    public boolean shouldShow() {
      return currentState > 0;
    }
  }

  /**
   * Pause between each interval.
   */
  private static int OVERLAY_SPEED = 1;
  /**
   * Number of intervals used to display panel.
   */
  private static int NUMBER_OF_INTERVALS = 10;

  /**
   * How many milliseconds to delay a hover event before executing it.
   */
  private static final int DELAY_MILLI = 200;

  /**
   * Default style name.
   */
  private static final String DEFAULT_STYLENAME = "gwt-PinnedPanel";

  ShowingTimer overlayTimer = new ShowingTimer();

  HidingTimer hidingTimer = new HidingTimer();

  private DelayShow delayedShow = new DelayShow();

  private State state = new State();

  private int userDefinedRightMargin = 0;
  private PinnedPanelImpl impl = new PinnedPanelImpl();
  private int width;
  private int maxOffshift;
  private int currentOffshift;
  private Panel mover;
  private SimplePanel hoverContainer;
  private ToggleButton pinnedToggle;
  private AbsolutePanel master;
  private ChangeListenerCollection changeListeners = new ChangeListenerCollection();

  /**
   * Constructor.
   * 
   * @param width width of panel
   * @param pinnedToggle toggle for pinned state
   * @param contents contents to be displayed
   */
  public PinnedPanel(int width, final ToggleButton pinnedToggle, Widget contents) {
    this(width, pinnedToggle, contents, new HTML());
  }

  /**
   * Constructor.
   * 
   * @param width width of panel
   * @param pinnedToggle toggle for pinned state
   * @param contents contents to be displayed
   * @param hoverBar hover bar
   */
  public PinnedPanel(int width, final ToggleButton pinnedToggle,
      Widget contents, Widget hoverBar) {
    this.width = width;

    // Create the composite widget.
    master = new AbsolutePanel() {
      {
        sinkEvents(Event.ONMOUSEOUT);
      }

      public void onBrowserEvent(Event event) {

        if (state.shouldHide()) {
          if (!PinnedPanel.this.pinnedToggle.isDown()) {
            switch (DOM.eventGetType(event)) {
              case Event.ONMOUSEOUT:
                Element to = DOM.eventGetToElement(event);
                if (state.currentState == State.WILL_SHOW) {
                  state.currentState = State.HIDING;
                  delayedShow.cancel();
                  break;
                }
                if (to == null || (!DOM.isOrHasChild(this.getElement(), to))) {
                  hide();
                  break;
                }
            }
          }
        }
        super.onBrowserEvent(event);
      }
    };
    DOM.setStyleAttribute(master.getElement(), "overflow", "hidden");
    initWidget(master);
    setStyleName(DEFAULT_STYLENAME);
    master.setWidth(width + "px");

    // Create hovering container.
    hoverContainer = new SimplePanel() {
      {
        sinkEvents(Event.ONMOUSEOVER);
      }

      public void onBrowserEvent(Event event) {
        if (!PinnedPanel.this.pinnedToggle.isDown()) {
          switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEOVER:
              Element from = DOM.eventGetFromElement(event);
              maybeShow(from);
              break;
          }
        }
        super.onBrowserEvent(event);
      }

      private void maybeShow(Element e) {
        if ((state.shouldShow())) {
          state.currentState = State.WILL_SHOW;
          delayedShow.activate();
        }
      }
    };
    hoverContainer.setWidget(hoverBar);
    hoverContainer.setStyleName("hover-bar");
    master.add(hoverContainer, 0, 0);

    // Create the contents container.
    mover = new SimplePanel();
    mover.add(contents);
    contents.addStyleName("content");
    mover.setStyleName("mover");

    mover.setWidth(width + "px");
    master.add(mover, 0, 0);

    // Process the toggle.
    this.pinnedToggle = pinnedToggle;
    pinnedToggle.addStyleName("toggle");

    pinnedToggle.setDown(true);
    pinnedToggle.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        setPinned(PinnedPanel.this.pinnedToggle.isDown());
      }
    });

    impl.setPinnedPanel(this);
    state.currentState = State.PINNED;
  }

  public void addChangeListener(ChangeListener listener) {
    changeListeners.add(listener);
  }

  public int getRightMargin() {
    return userDefinedRightMargin;
  }

  public ToggleButton getSwitchButton() {
    return pinnedToggle;
  }

  /**
   * Is the panel pinned?
   */
  public boolean isPinned() {
    return state.currentState == State.PINNED;
  }

  public void removeChangeListener(ChangeListener listener) {
    changeListeners.remove(listener);
  }

  /**
   * As {@link PinnedPanel} needs to manipulate the right margin, the right
   * margin must be controlled in code.
   * 
   * @param rightMargin in px
   */
  public void setRightMargin(int rightMargin) {
    this.userDefinedRightMargin = rightMargin;
    if (isPinned()) {
      impl.becomePinned();
    } else {
      hide();
    }
  }

  protected void hide() {
    state.currentState = State.HIDING;
    overlayTimer.cancel();
    hidingTimer.cancel();
    hidingTimer.interval = maxOffshift / NUMBER_OF_INTERVALS;
    hidingTimer.run();
  }

  /**
   * This method is called immediately after a widget becomes attached to the
   * browser's document.
   */
  protected void onLoad() {
    refreshWidth();
    // Adjust mover height to include borders.
    int height = master.getOffsetHeight();
    master.setHeight(height + "px");
    int moverHeight = mover.getOffsetHeight();
    mover.setHeight(height - (moverHeight - height) + "px");
  }

  private void refreshWidth() {
    // Now include borders into master..
    width = mover.getOffsetWidth();
    master.setWidth(width + "px");
  }

  protected void show() {
    state.currentState = State.SHOWING;
    overlayTimer.cancel();
    hidingTimer.cancel();
    overlayTimer.interval = maxOffshift / NUMBER_OF_INTERVALS;
    overlayTimer.run();
  }

  private void setPinned(boolean pinned) {
    if (isPinned() == pinned) {
      return;
    }
    pinnedToggle.setDown(pinned);
    if (pinned) {
      impl.becomePinned();
      changeListeners.fireChange(this);
    } else {
      overlayTimer.fireChangeListeners = true;
      impl.becomeOverlay();
    }
  }
}
