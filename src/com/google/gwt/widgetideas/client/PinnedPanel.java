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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

/**
 * {@link PinnedPanel} creates a panel that is, by default, pinned in place.
 * When the pinned state it toggled, the contents of the panel will display only
 * when the users mouse hovers over it.
 */
public class PinnedPanel extends Composite {
  public static class PinnedPanelImplMozilla extends PinnedPanelImpl {

    protected void hiddenState() {
      DOM.setStyleAttribute(e, "width", pin.peekoutSize + "px");
      DOM.setStyleAttribute(e, "overflow", "hidden");
      DOM.setStyleAttribute(e, "marginRight", "0px");
    }

    protected void setPanelWidth(int width) {
      DOM.setStyleAttribute(e, "marginRight", width - pin.peekoutSize + "px");
      DOM.setStyleAttribute(e, "width", width + "px");
    }
  }
  /**
   * Pinned panel impl.
   */
  static class PinnedPanelImpl {
    class OverlayTimer extends Timer {
      int finalSize;
      int interval;
      int currentSize;
      boolean cancel;

      public void run() {
        if (cancel) {
          cancel = false;
        } else {
          currentSize += interval;
          currentSize = Math.min(currentSize, finalSize);
          setPanelWidth(currentSize);
          if (currentSize < finalSize) {
            this.schedule(OVERLAY_SPEED);
          }
        }
      }
    }

    protected PinnedPanel pin;

    protected Element e;

    OverlayTimer overlayTimer = new OverlayTimer();

    protected void hiddenState() {
      DOM.setStyleAttribute(e, "marginRight", -(pin.peekoutSize) + "px");
      DOM.setStyleAttribute(e, "width", pin.peekoutSize + "px");
      DOM.setStyleAttribute(e, "overflow", "hidden");
      overlayTimer.cancel = true;
    }

    protected void overlayState() {

      overlayTimer.cancel = false;
      overlayTimer.currentSize = pin.peekoutSize;
      overlayTimer.finalSize = pin.width;
      overlayTimer.interval = (pin.width - pin.peekoutSize)
          / NUMBER_OF_INTEVALS;
      overlayTimer.run();
    };

    protected void pinnedState() {
      DOM.setStyleAttribute(e, "marginRight", "0px");
    }

    protected void setPanelWidth(int width) {
      DOM.setStyleAttribute(e, "marginRight", -width + "px");
      DOM.setStyleAttribute(e, "width", width + "px");

    }

    void setPinnedPanel(PinnedPanel pinnedPanel) {
      this.pin = pinnedPanel;
      e = pinnedPanel.getElement();
      DOM.setStyleAttribute(e, "position", "relative");

    }
  }

  private static int OVERLAY_SPEED = 1;
  private static int NUMBER_OF_INTEVALS = 20;

  private static final String DEFAULT_STYLENAME = "gwt-PinnedPanel";

  private PinnedPanelImpl impl = (PinnedPanelImpl) GWT.create(PinnedPanelImpl.class);

  private int width;

  private Grid grid = new Grid(2, 1);
  private ToggleButton switchButton = new ToggleButton("Pinned");
  private int peekoutSize;

  public PinnedPanel(int width) {
    this(width, 30);
  }

  public PinnedPanel(int width, int peekoutSize) {
    this.peekoutSize = peekoutSize;

    SimplePanel container = new SimplePanel() {
      {
        sinkEvents(Event.ONMOUSEOUT);
        sinkEvents(Event.ONMOUSEOVER);
      }

      public void onBrowserEvent(Event event) {
        if (!switchButton.isDown()) {
          switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEOUT:
              hiddenState();
              break;
            case Event.ONMOUSEOVER:
              overlayState();
              break;

          }
        }
        super.onBrowserEvent(event);
      }
    };

    initWidget(container);
    grid.setHeight("100%");

    container.setWidget(grid);
    impl.setPinnedPanel(this);
    CellFormatter formatter = grid.getCellFormatter();
    formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
    formatter.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
    formatter.setHeight(0, 0, "5%");
    switchButton.setWidth("3em");
    switchButton.setDown(true);
    grid.setWidget(0, 0, switchButton);
    grid.setWidth("100%");
    setWidth(width + "px");
    this.width = width;
    setStyleName(DEFAULT_STYLENAME);
    switchButton.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        setPinned(switchButton.isDown());
      }

    });

  }

  public ToggleButton getSwitchButton() {
    return switchButton;
  }

  public void setStyleNamded(String styleName) {
    super.setStyleName(grid.getElement(), styleName, true);
  }

  public void setWidget(Widget widget) {
    grid.setWidget(1, 0, widget);
  }

  protected void hiddenState() {
    impl.hiddenState();
  }

  /**
   * This method is called immediately after a widget becomes attached to the
   * browser's document.
   */
  protected void onLoad() {
    int clientWidth = DOM.getElementPropertyInt(this.getElement(),
        "clientWidth");
    grid.setWidth(clientWidth + "px");
  }

  protected void overlayState() {
    impl.overlayState();
  }

  protected void pinnedState() {
    impl.pinnedState();
  }

  private void setPinned(boolean pinned) {
    if (pinned) {
      switchButton.setDown(pinned);
      pinnedState();
    } else {
      switchButton.setDown(false);
      hiddenState();

    }
  }

}
