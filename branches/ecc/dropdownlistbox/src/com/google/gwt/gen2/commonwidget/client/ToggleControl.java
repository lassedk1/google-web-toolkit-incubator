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

import com.google.gwt.gen2.event.shared.ChoiceEvent;
import com.google.gwt.gen2.event.shared.ChoiceHandler;
import com.google.gwt.gen2.event.shared.HandlerRegistration;
import com.google.gwt.gen2.widget.client.IncubatorComposite;
import com.google.gwt.gen2.widget.client.IncubatorWidget.AbstractCss;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

/**
 * Controller for a toggle button. Needed to support DropDownListBox having a
 * custom implementation of {@link ToggleButton}.
 * 
 * Control widgets have the following contract:
 * <ul>
 * <li> The control should supply a default implementation </li>
 * <li> The control should not make any assumptions about the DOM structure of
 * the underlying widget.</li>
 * <li>Unlike normal widgets, when used as the base of the composite, a control's style
 * name should not be reset</li>
  * 
 * @param <ToggleType> the actual toggle widget
 */
public abstract class ToggleControl<ToggleType extends Widget> extends
    IncubatorComposite<ToggleType, AbstractCss> implements
    ChoiceEvent.Source<ToggleControl.ToggleState> {

  /**
   * The default toggle button control.
   */
  public static class Default extends ToggleControl<ToggleButton> {
    private ToggleButton button = new ToggleButton();

    {
      initWidget(button);
      button.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          ChoiceEvent<ToggleState> event;
          if (getWidget().isDown()) {
            event = new ChoiceEvent<ToggleState>(ToggleState.UP,
                ToggleState.DOWN);
          } else {
            event = new ChoiceEvent<ToggleState>(ToggleState.DOWN,
                ToggleState.UP);
          }
          fireEvent(event);
        }
      });
    }

    public Default() {
    }

    public Default(String styleName) {
      button.setStylePrimaryName(styleName);
    }

    public HandlerRegistration addChoiceHandler(final ChoiceHandler handler) {
      return addHandler(ChoiceEvent.KEY, handler);
    }

    @Override
    public FocusWidget getFocusable() {
      return button;
    }

    @Override
    public void setFace(String toggleFace) {
      button.getUpFace().setHTML(toggleFace);
    }

    @Override
    public void setState(ToggleState state) {
      button.setDown(state == ToggleState.DOWN);
    }
  };

  /**
   * The toggle states.
   */
  public static enum ToggleState {
    UP, DOWN
  }

  /**
   * Gets the focusable element associated with this toggle control.
   */
  public abstract HasFocus getFocusable();

  /**
   * Sets the button face to the given html.
   * 
   * @param buttonFace buttonFace
   */
  public abstract void setFace(String buttonFace);

  /**
   * Sets the toggle state.
   * 
   * @param state the state
   */
  public abstract void setState(ToggleState state);

}
