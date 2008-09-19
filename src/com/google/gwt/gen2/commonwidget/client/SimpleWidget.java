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

import com.google.gwt.dom.client.Element;
import com.google.gwt.gen2.commonwidget.impl.StandardCssImpl;
import com.google.gwt.gen2.event.dom.client.ClickEvent;
import com.google.gwt.gen2.event.dom.client.ClickHandler;
import com.google.gwt.gen2.event.dom.client.HasClickHandlers;
import com.google.gwt.gen2.event.dom.client.HasMouseHandlers;
import com.google.gwt.gen2.event.dom.client.MouseDownEvent;
import com.google.gwt.gen2.event.dom.client.MouseDownHandler;
import com.google.gwt.gen2.event.dom.client.MouseMoveEvent;
import com.google.gwt.gen2.event.dom.client.MouseMoveHandler;
import com.google.gwt.gen2.event.dom.client.MouseOutEvent;
import com.google.gwt.gen2.event.dom.client.MouseOutHandler;
import com.google.gwt.gen2.event.dom.client.MouseOverEvent;
import com.google.gwt.gen2.event.dom.client.MouseOverHandler;
import com.google.gwt.gen2.event.dom.client.MouseUpEvent;
import com.google.gwt.gen2.event.dom.client.MouseUpHandler;
import com.google.gwt.gen2.event.dom.client.MouseWheelEvent;
import com.google.gwt.gen2.event.dom.client.MouseWheelHandler;
import com.google.gwt.gen2.event.shared.HandlerRegistration;
import com.google.gwt.gen2.widgetbase.client.Gen2Widget;
import com.google.gwt.gen2.widgetbase.client.WidgetCss;
import com.google.gwt.user.client.DOM;

/**
 * Simple widget which wraps a single element and adds mouse and click events to
 * it.
 */
public class SimpleWidget extends Gen2Widget implements HasMouseHandlers,
    HasClickHandlers {

  /**
   * Default css.
   */
  public static WidgetCss DEFAULT_CSS;
  private static Element translater;

  /**
   * Create the css for this widget with the given base name.
   */
  public static WidgetCss createCss(String baseName) {
    return new StandardCssImpl(baseName);
  }

  private static Element convertHtml(String html) {
    if (translater == null) {
      translater = DOM.createDiv();
    }
    translater.setInnerHTML(html);
    assert (translater.getChildNodes().getLength() == 1);
    return translater.getFirstChildElement();
  }

  private static WidgetCss ensureDefaultCss() {
    if (DEFAULT_CSS == null) {
      DEFAULT_CSS = createCss("gwt-SimpleWidget");
    }
    return DEFAULT_CSS;
  }

  /**
   * Constructor.
   */
  public SimpleWidget(Element e) {
    this(e, ensureDefaultCss());
  }

  /**
   * Constructor.
   */
  public SimpleWidget(Element e, WidgetCss css) {
    assert (e != null);
    assert (css != null);
    setElement(e);
    setStyleName(e, css.baseName());
  }

  /**
   * Constructor.
   */
  public SimpleWidget(String html) {
    this(convertHtml(html), ensureDefaultCss());
  }

  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return addHandlerAndSink(ClickEvent.KEY, handler);
  }

  public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
    return addHandlerAndSink(MouseDownEvent.KEY, handler);
  }

  public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
    return addHandlerAndSink(MouseMoveEvent.KEY, handler);
  }

  public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
    return addHandlerAndSink(MouseOutEvent.KEY, handler);
  }

  public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
    return addHandlerAndSink(MouseOverEvent.KEY, handler);
  }

  public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
    return addHandlerAndSink(MouseUpEvent.KEY, handler);
  }

  public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
    return addHandlerAndSink(MouseWheelEvent.KEY, handler);
  }
}
