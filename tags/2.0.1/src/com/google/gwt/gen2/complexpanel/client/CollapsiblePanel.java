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

package com.google.gwt.gen2.complexpanel.client;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.gen2.widgetbase.client.Gen2CssInjector;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;

/**
 * {@link CollapsiblePanel} is intended to be used as a navigation panel with
 * the ability to collapse itself.
 * 
 * The {@link CollapsiblePanel} will collapse itself to the left in LTR mode and
 * to the right in RTL mode.
 * 
 * When collapsed, the contents of the panel will be displayed only when the
 * user mouse hovers over the hover bar, otherwise is will stay collapsed to the
 * left.
 * <p>
 * 
 * The user must specify a fixed width and height either programatically or via
 * css that must already be fully loaded before the panel is attached.
 * 
 * <dl>
 * <dd>.gwt-CollapsiblePanel</dd>
 * <dt>the collapsible panel</dt>
 * <dd>.CollapsibleHoverBox</dd>
 * <dt>The hover bar that appears when the widget is collapsed</dt>
 * <dd>.CollapsibleContainer</dd>
 * <dt>The container for the panel's content</dt>
 * </dl>
 */
public class CollapsiblePanel extends Composite implements HasWidgets {

  /**
   * Event fired after the panel shown in its collapsed state.
   */
  public static class CollapsedStateEvent extends
      GwtEvent<CollapsedStateHandler> {
    static Type<CollapsedStateHandler> type = new Type<CollapsedStateHandler>();;

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<CollapsedStateHandler> getAssociatedType() {
      return type;
    }

    @Override
    protected void dispatch(CollapsedStateHandler handler) {
      handler.onCollapsedState(this);
    }
  }

  /**
   * Handler for {@link CollapsedStateEvent} events.
   */
  public static interface CollapsedStateHandler extends EventHandler {
    void onCollapsedState(CollapsedStateEvent e);
  }

  /**
   * Event fired after the panel is shown in a expanded state.
   */
  public static class ExpandedStateEvent extends GwtEvent<ExpandedStateHandler> {
    static Type<ExpandedStateHandler> type = new Type<ExpandedStateHandler>();;

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ExpandedStateHandler> getAssociatedType() {
      return type;
    }

    @Override
    protected void dispatch(ExpandedStateHandler handler) {
      handler.onExpandedState(this);
    }
  }

  /**
   * Handler for {@link ExpandedStateEvent} events.
   */
  public static interface ExpandedStateHandler extends EventHandler {
    void onExpandedState(ExpandedStateEvent e);
  }

  /**
   * Current {@link CollapsiblePanel} state.
   */
  enum State {
    WILL_HIDE, HIDING, IS_HIDDEN, WILL_SHOW, SHOWING, IS_SHOWN, EXPANDED;

    public static void checkTo(State from, State to) {
      boolean valid = check(from, to);
      if (!valid) {
        throw new IllegalStateException(from + " -> " + to
            + " is an illegal state transition");
      }
    }

    private static boolean check(State from, State to) {

      if (to == EXPANDED || from == null) {
        return true;
      }
      switch (from) {
        case WILL_HIDE:
          return (to == IS_SHOWN || to == HIDING);
        case HIDING:
          return (to == IS_HIDDEN || to == SHOWING);
        case IS_HIDDEN:
          return (to == WILL_SHOW);
        case WILL_SHOW:
          return (to == SHOWING || to == IS_HIDDEN);
        case SHOWING:
          return (to == IS_SHOWN || to == HIDING);
        case IS_SHOWN:
          return to == WILL_HIDE;
        case EXPANDED:
          return to == HIDING;
        default:
          throw new IllegalStateException("Unknown state");
      }
    }

    public boolean shouldHide() {
      return this == SHOWING || this == IS_SHOWN;
    }

    public boolean shouldShow() {
      return this == HIDING || this == IS_HIDDEN;
    }
  }

  /**
   * {@link CollapsiblePanel} styles.
   * 
   */
  static class Styles {
    static String DEFAULT = "gwt-CollapsiblePanel";
    static String CONTAINER = "CollapsibleContainer";
    static String HOVER_BAR = "CollapsibleHoverBar";
  }

  /**
   * Delays showing of the {@link CollapsiblePanel}.
   */
  private class DelayHide extends Timer {
    public void activate() {
      setState(State.WILL_HIDE);
      delayedHide.schedule(getDelayBeforeHide());
    }

    @Override
    public void run() {
      hide();
    }
  }

  /**
   * Delays showing of the {@link CollapsiblePanel}.
   */
  private class DelayShow extends Timer {
    public void activate() {
      setState(State.WILL_SHOW);
      delayedShow.schedule(getDelayBeforeShow());
    }

    @Override
    public void run() {
      show();
    }
  }

  /**
   * Injects the default css for the collapsible panel.
   */
  public static void injectDefaultCss() {
    Gen2CssInjector.addCollapsiblePanelDefault();
  }

  private int hoverBarWidth;

  /**
   * Number of intervals used to display panel.
   */
  private int timeToSlide = 150;

  /**
   * How many milliseconds to delay a hover event before executing it.
   */
  private int delayBeforeShow = 300;

  /**
   * How many milliseconds to delay a hide event before executing it.
   */
  private int delayBeforeHide = 200;

  /**
   * The internal state of the Collapsible panel.
   */
  private State state;

  /**
   * Showing the panel bar sliding in.
   */
  private Animation hidingTimer = new Animation() {
    @Override
    public void onCancel() {
      // Override default behavior to do nothing instead
    }

    @Override
    public void onComplete() {
      container.completeHide();
      setState(State.IS_HIDDEN);
    }

    @Override
    public void onUpdate(double progress) {
      container.updateHide(progress);
    }
  };

  /**
   * Showing the panel sliding out.
   */
  private Animation showingTimer = new Animation() {
    @Override
    public void onCancel() {
      // Override default behavior to do nothing instead
    }

    @Override
    public void onComplete() {
      container.completeShow();
      setState(State.IS_SHOWN);
    }

    @Override
    public void onUpdate(double progress) {
      container.updateShow(progress);
    }
  };
  /**
   * How long to delay the showing of the panel.
   */
  private DelayShow delayedShow = new DelayShow();
  /**
   * How long to delay the hiding of the panel.
   */
  private DelayHide delayedHide = new DelayHide();

  // Collapsible panel structure
  private SliderPanelImpl container;

  private SimplePanel hoverBar;
  private ToggleButton collapseToggle;
  private BiDiRelativePanel master;
  private boolean hasContents;

  /**
   * Create a new collapsible panel.
   */

  public CollapsiblePanel() {

    // Create the composite widget.
    master = new BiDiRelativePanel() {
      {
        sinkEvents(Event.ONMOUSEOUT | Event.ONMOUSEOVER);
      }

      @Override
      public void onBrowserEvent(Event event) {
        // Cannot handle browser events until contents are initialized.
        if (!hasContents) {
          return;
        }
        if (isCollapsed()) {
          switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEOUT:
              Element to = DOM.eventGetToElement(event);
              if (to == null && state == State.SHOWING) {
                // Linux hosted mode hack.
                return;
              }
              if (to != null && DOM.isOrHasChild(master.getElement(), to)) {
                break;
              }
              switch (state) {
                case WILL_SHOW:
                  setState(State.IS_HIDDEN);
                  delayedShow.cancel();
                  break;
                case SHOWING:
                  hide();
                  break;
                case IS_SHOWN:
                  delayedHide.activate();
                  break;
              }
              break;
            case Event.ONMOUSEOVER:
              if (state == State.WILL_HIDE) {
                setState(State.IS_SHOWN);
                delayedHide.cancel();
              }
              break;
          }
        }
        super.onBrowserEvent(event);
      }
    };

    master.getElement().getStyle().setProperty("overflow", "visible");

    initWidget(master);
    setStyleName(Styles.DEFAULT);

    // Create hovering container.
    // Create the composite widget.
    hoverBar = new SimplePanel() {
      {
        sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEDOWN);
      }

      @Override
      public void onBrowserEvent(Event event) {
        // Cannot handle browser events until contents are initialized.
        if (!hasContents) {
          return;
        }
        if (isCollapsed()) {
          switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEOVER:
              switch (state) {
                case HIDING:
                  show();
                  break;
                case IS_HIDDEN:
                  delayedShow.activate();
                  break;
              }
              break;
            case Event.ONMOUSEDOWN:
              setCollapsedState(false, true);
          }
        }
      }
    };

    // Hover bar.
    hoverBar.setStyleName(Styles.HOVER_BAR);
    master.addAbsolute(hoverBar, 0, 0);

    // Actual slider panel.
    container = new SliderPanelImpl();
    master.addAbsolute(container, 0, 0);
    setState(State.EXPANDED);
  }

  /**
   * Creates a new collapsible panel.
   * 
   * @param contents the contents to be shown in the panel
   */
  public CollapsiblePanel(Widget contents) {
    this();
    initContents(contents);
  }

  /**
   * Adds the content to this panel. May only be called once.
   */
  public void add(Widget w) {
    initContents(w);
  }

  /**
   * Add handler which is called when the widget is shown in its collapsed
   * state.
   * 
   * @param handler the handler
   */
  public HandlerRegistration addCollapsedStateHandler(
      CollapsedStateHandler handler) {
    return addHandler(handler, CollapsedStateEvent.type);
  }

  /**
   * Adds a handler to be shown when the panel is in its expanded state.
   * 
   * @param handler handler
   */
  public void addExpandedStateHandler(ExpandedStateHandler handler) {
    addHandler(handler, ExpandedStateEvent.type);
  }

  public void clear() {
    throw new IllegalStateException(
        "Collapsible Panel cannot be cleared once initialized");
  }

  public Widget getContents() {
    return container.getWidget();
  }

  /**
   * Hides the panel when the panel is the collapsible state. Does nothing if
   * the panel is expanded or not attached.
   */
  public final void hide() {
    if (getState() != State.EXPANDED && isAttached()) {
      hiding();
    }
  }

  /**
   * Uses the given toggle button to control whether the panel is collapsed or
   * not. Note, does not move the control toggle, so it is up to the user to
   * place the toggle in the desired location.
   */
  public void hookupControlToggle(ToggleButton button) {
    this.collapseToggle = button;
    collapseToggle.setDown(!this.isCollapsed());
    collapseToggle.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        setCollapsedState(!collapseToggle.isDown(), true);
      }
    });
  }

  /**
   * Is the panel currently in its collapsed state.
   */
  public boolean isCollapsed() {
    return getState() != State.EXPANDED;
  }

  public Iterator<Widget> iterator() {
    return master.iterator();
  }

  public boolean remove(Widget w) {
    return false;
  }

  /**
   * Sets the state of the collapsible panel.
   * 
   * @param collapsed is the panel collapsed?
   */
  public final void setCollapsedState(boolean collapsed) {
    setCollapsedState(collapsed, false);
  }

  /**
   * Sets the state of the collapsible panel.
   * 
   * @param collapsed is the panel collapsed?
   * @param fireEvents should the change listeners be fired?
   */
  public final void setCollapsedState(boolean collapsed, boolean fireEvents) {
    if (isCollapsed() == collapsed) {
      return;
    }
    if (collapseToggle != null) {
      collapseToggle.setDown(!collapsed);
    }
    if (collapsed) {
      becomeCollapsed();
    } else {
      becomeExpanded();
    }
    if (fireEvents) {
      if (collapsed) {
        fireEvent(new CollapsedStateEvent());
      } else {
        fireEvent(new ExpandedStateEvent());
      }
    }
  }

  /**
   * Sets the delay before the collapsible panel hides the panel after the user
   * leaves the panel.
   * 
   * @param delayBeforeHide the delayBeforeHide to set
   */
  public void setDelayBeforeHide(int delayBeforeHide) {
    this.delayBeforeHide = delayBeforeHide;
  }

  /**
   * Set delay before showing the panel after the user has hovered over the
   * hover bar.
   * 
   * @param delayBeforeShow the delayBeforeShow to set
   */
  public void setDelayBeforeShow(int delayBeforeShow) {
    this.delayBeforeShow = delayBeforeShow;
  }

  @Override
  public void setHeight(String height) {
    container.setHeight(height);
    computeSize();
  }

  /**
   * Sets the contents of the hover bar.
   */
  public void setHoverBarContents(Widget bar) {
    hoverBar.setWidget(bar);
    computeSize();
  }

  /**
   * Sets the hover bar's width.
   */
  public void setHoverBarWidth(String width) {
    hoverBar.setWidth(width);
    computeSize();
  }

  /**
   * Sets the time to slide the panel out.
   * 
   * @param timeToSlide the timeToSlide to set
   */
  public void setTimeToSlide(int timeToSlide) {
    this.timeToSlide = timeToSlide;
  }

  @Override
  public void setVisible(boolean isVisible) {
    super.setVisible(isVisible);
    if (isVisible) {
      computeSize();
    }
  }

  @Override
  public void setWidth(String width) {
    container.setWidth(width);
    computeSize();
  }

  /**
   * Shows the panel if the panel is in a collapsible state. Does nothing if the
   * panel is expanded or not attached.
   * 
   * Note: this method should only be called if the user's mouse will be over
   * the panel's contents after show() is executed.
   */
  public void show() {
    if (getState() != State.EXPANDED && isAttached()) {
      cancelAllTimers();
      setState(State.SHOWING);

      container.startShow();
      showingTimer.run(this.getTimeToSlide());
    }
  }

  /**
   * This method is called immediately after a widget becomes attached to the
   * browser's document.
   */
  @Override
  protected void onLoad() {
    computeSize();
  }

  /**
   * Visible for testing.
   * 
   * @return the hover bar component
   */
  SimplePanel getHoverBar() {
    return hoverBar;
  }

  /**
   * Visible for testing. Note that the state is completely internal to the
   * implementation. 
   * 
   * @return the current state
   */
  State getState() {
    return state;
  }

  private void adjustmentsForCollapsedState() {
    master.setHeight(container.getOffsetHeight() + "px");
    // IE RTL bug create a phantom scrollbar without the +1
    master.setWidth(hoverBarWidth + 1 + "px");
    master.setAbsolutePosition(container, hoverBarWidth / 2, 0);
  }

  private void adjustmentsForExpandedState() {
    container.completeShow();
    master.setAbsolutePosition(container, 0, 0);
    master.setWidth(container.getOffsetWidth() + "px");
  }

  /**
   * Display this panel in its collapsed state. The panel's contents will be
   * hidden and only the hover var will be visible.
   */
  private void becomeCollapsed() {
    cancelAllTimers();

    // Now hide.
    if (isAttached()) {
      adjustmentsForCollapsedState();
      hiding();
    } else {
      // onLoad will ensure this is correctly displayed. Here we just ensure
      // that the panel is the correct final hidden state. Forcing the state to
      // is hidden regardless of what is was.
      state = State.IS_HIDDEN;
    }
  }

  /**
   * Display this panel in its expanded state. The panel's contents will be
   * fully visible and take up all required space.
   */
  private void becomeExpanded() {
    cancelAllTimers();

    // The master width needs to be readjusted back to it's original size.
    if (isAttached()) {
      adjustmentsForExpandedState();
    }
    setState(State.EXPANDED);
  }

  private void cancelAllTimers() {
    delayedHide.cancel();
    delayedShow.cancel();
    showingTimer.cancel();
    hidingTimer.cancel();
  }

  private void computeSize() {
    if (!(isAttached() && isVisible() && hasContents)) {
      return;
    }
    // We need this + 1 to avoid triggering on IE a phantom right scrollbar when
    // switching between collapsed and expanded states.
    hoverBarWidth = hoverBar.getOffsetWidth();
    if (hoverBarWidth <= 1) {
      throw new IllegalStateException(
          "The underlying hover bar width is not set. Please ensure that the .CollapsibleHoverBar css style has a fixed width");
    }
    container.computeSize();
    master.setHeight(container.getOffsetHeight() + "px");
    hoverBar.setHeight(container.getOffsetHeight() + "px");
    if (getState() == State.EXPANDED) {
      adjustmentsForExpandedState();
    } else {
      cancelAllTimers();
      adjustmentsForCollapsedState();
      container.completeHide();
      state = State.IS_HIDDEN;
    }
  }

  /**
   * Gets the delay before hiding.
   * 
   * @return the delayBeforeHide
   */
  private int getDelayBeforeHide() {
    return delayBeforeHide;
  }

  /**
   * Gets the delay before showing the panel.
   * 
   * @return the delayBeforeShow
   */
  private int getDelayBeforeShow() {
    return delayBeforeShow;
  }

  /**
   * Gets the time to slide the panel out.
   * 
   * @return the timeToSlide
   */
  private int getTimeToSlide() {
    return timeToSlide;
  }

  private void hiding() {
    assert (isAttached());
    cancelAllTimers();
    setState(State.HIDING);
    container.startHide();
    hidingTimer.run(timeToSlide);
  }

  /**
   * Initialize the panel's contents.
   * 
   * @param contents contents
   */
  private void initContents(Widget contents) {
    if (hasContents) {
      throw new IllegalStateException("Contents have already be set");
    }
    container.add(contents);
    hasContents = true;
    computeSize();
  }

  private void setState(State state) {
    // checks are assuming animation.
    State.checkTo(this.state, state);
    this.state = state;
  }

}
