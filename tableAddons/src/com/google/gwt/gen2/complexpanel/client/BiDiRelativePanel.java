/*
 * Copyright 2009 Google Inc.
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

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * An absolute panel which lays its content out based on the rtl propery of the
 * current locale.
 */
class BiDiRelativePanel extends com.google.gwt.user.client.ui.ComplexPanel {

  public static String getBegin() {
    if (isRTL()) {
      return "right";
    } else {
      return "left";
    }
  }

  /**
   * Changes a DOM element's positioning to static.
   * 
   * @param elem the DOM element
   */
  private static void changeToStaticPositioning(Element elem) {
    DOM.setStyleAttribute(elem, getBegin(), "");
    DOM.setStyleAttribute(elem, "top", "");
    DOM.setStyleAttribute(elem, "position", "");
  }

  private static boolean isRTL() {
    return LocaleInfo.getCurrentLocale().isRTL();
  }

  /**
   * Creates an empty absolute panel.
   */
  public BiDiRelativePanel() {
    this(DOM.createDiv());

    // Setting the panel's position style to 'relative' causes it to be
    // treated
    // as a new positioning context for its children.
    DOM.setStyleAttribute(getElement(), "position", "relative");
    DOM.setStyleAttribute(getElement(), "overflow", "hidden");
  }

  /**
   * Creates an AbsolutePanel with the given element.
   * 
   * @param elem the element to be used for this panel
   */
  protected BiDiRelativePanel(Element elem) {
    setElement(elem);
  }

  /**
   * Adds a child widget to this panel.
   * 
   * @param w the child widget to be added
   */
  @Override
  public void add(Widget w) {
    add(w, getElement());
  }

  /**
   * Adds a widget to the panel at the specified position. Setting a position of
   * <code>(-1, -1)</code> will cause the child widget to be positioned
   * statically.
   * 
   * @param w the widget to be added
   * @param begin the widget's distance from its parent's begining
   * @param top the widget's top position
   */
  public void addAbsolute(Widget w, int begin, int top) {
    // In order to avoid the potential for a flicker effect, it is necessary
    // to set the position of the widget before adding it to the
    // AbsolutePanel.
    // The Widget should be removed from its parent before any positional
    // changes are made to prevent flickering.
    w.removeFromParent();
    setWidgetPositionImpl(w, begin, top, "absolute");
    add(w);
  }

  /**
   * Adds a widget to the panel at the specified position. Setting a position of
   * <code>(-1, -1)</code> will cause the child widget to be positioned
   * statically.
   * 
   * @param w the widget to be added
   * @param begin the widget's distance from its parent's begining
   * @param top the widget's top position
   */
  public void addRelative(Widget w, int begin, int top) {
    // In order to avoid the potential for a flicker effect, it is necessary
    // to set the position of the widget before adding it to the
    // AbsolutePanel.
    // The Widget should be removed from its parent before any positional
    // changes are made to prevent flickering.
    w.removeFromParent();
    setWidgetPositionImpl(w, begin, top, "relative");
    add(w);
  }

  /**
   * Sets the position of the specified child widget.
   * 
   * @param w the child widget to be positioned
   * @param begin the widget's begin position
   * @param top the widget's top position
   */

  public void setAbsolutePosition(Widget w, int begin, int top) {
    checkWidgetParent(w);
    setWidgetPositionImpl(w, begin, top, "absolute");
  }

  /**
   * Sets the position of the specified child widget. Setting a position of
   * <code>(-1, -1)</code> will cause the child widget to be positioned
   * statically.
   * 
   * @param w the child widget to be positioned
   * @param begin the widget's begin position
   * @param top the widget's top position
   */

  public void setRelativePosition(Widget w, int begin, int top) {
    checkWidgetParent(w);
    setWidgetPositionImpl(w, begin, top, "relative");
  }

  public void setStaticPosition(Widget w) {
    setWidgetPositionImpl(w, -1, -1, "absolute");
  }

  private void checkWidgetParent(Widget w) {
    if (w.getParent() != this) {
      throw new IllegalArgumentException(
          "Widget must be a child of this panel.");
    }
  }

  private void setWidgetPositionImpl(Widget w, int begin, int top,
      String position) {
    Element h = w.getElement();
    if ((begin == -1) && (top == -1)) {
      changeToStaticPositioning(h);
    } else {
      DOM.setStyleAttribute(h, "position", position);
      DOM.setStyleAttribute(h, getBegin(), begin + "px");
      DOM.setStyleAttribute(h, "top", top + "px");
    }
  }
}
