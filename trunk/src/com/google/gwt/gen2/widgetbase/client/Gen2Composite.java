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

package com.google.gwt.gen2.widgetbase.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetAdaptorImpl;

/**
 * A wrapper for incubator widgets which need to extend widgets other then
 * {@link Widget}.
 * <p>
 * This is a clone of the gwt Composite class where the composite type is
 * parameterized.
 * 
 * @param <WidgetType> the wrapped widget type
 */
public abstract class Gen2Composite<WidgetType extends Widget> extends
    Gen2Widget {

  private WidgetType widget;

  @Override
  public boolean isAttached() {
    return widget.isAttached();
  }

  @Override
  public void onBrowserEvent(Event event) {
    // Delegate events to the widget.
    widget.onBrowserEvent(event);
  }

  @Override
  public void setStyleName(String styleName) {
    assert (widget != null) : "Should not ask for a widget's css before calling initWidget()";
    widget.setStyleName(styleName);
  }

  @Override
  public void setStylePrimaryName(String styleName) {
    assert (widget != null) : "Should not ask for a widget's css before calling initWidget()";
    widget.setStylePrimaryName(styleName);
  }

  /**
   * Provides subclasses access to the topmost widget that defines this
   * composite.
   * 
   * @return the widget
   */
  protected WidgetType getWidget() {
    return widget;
  }

  /**
   * Sets the widget to be wrapped by the composite. The wrapped widget must be
   * set before calling any {@link Widget} methods on this object, or adding it
   * to a panel. This method may only be called once for a given composite.
   * 
   * @param widget the widget to be wrapped
   */
  protected void initWidget(WidgetType widget) {
    // Validate. Make sure the widget is not being set twice.
    if (this.widget != null) {
      throw new IllegalStateException("Composite.initWidget() may only be "
          + "called once.");
    }

    // Detach the new child.
    widget.removeFromParent();

    // Use the contained widget's element as the composite's element,
    // effectively merging them within the DOM.
    setElement(widget.getElement());

    // Logical attach.
    this.widget = widget;

    // Adopt.
    WidgetAdaptorImpl.setParent(widget, this);
  }

  @Override
  protected void onAttach() {
    // Call the widget's onAttach() first.
    WidgetAdaptorImpl.onAttach(widget);

    // Clobber the widget's call to setEventListener(), causing all events to
    // be routed to this composite, which will delegate back to the widget by
    // default (note: it's not necessary to clear this in onDetach(), because
    // the widget's onDetach will do so).
    DOM.setEventListener(getElement(), this);

    // Call onLoad() directly, because we're not calling super.onAttach().
    onLoad();
  }

  @Override
  protected void onDetach() {
    try {
      onUnload();
    } finally {
      // We don't want an exception in user code to keep us from calling the
      // super implementation (or event listeners won't get cleaned up and
      // the attached flag will be wrong).
      WidgetAdaptorImpl.onDetach(widget);
    }
  }
}
