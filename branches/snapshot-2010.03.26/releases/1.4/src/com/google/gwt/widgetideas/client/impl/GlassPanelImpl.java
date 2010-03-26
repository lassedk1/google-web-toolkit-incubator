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
package com.google.gwt.widgetideas.client.impl;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.widgetideas.client.GlassPanel;

/**
 * Deferred binding implementation of {@link GlassPanel}.
 */
public abstract class GlassPanelImpl {
  /**
   * TODO Correctly handle  for undefined $doc.compatMode in Safari.
   */
  protected static native int getWindowScrollHeight()
  /*-{
    return $doc.compatMode == 'CSS1Compat' ?
        $doc.documentElement.scrollHeight : $doc.body.scrollHeight;
  }-*/;

  /**
   * TODO Correctly handle undefined $doc.compatMode in Safari.
   */
  protected static native int getWindowScrollWidth()
  /*-{
    return $doc.compatMode == 'CSS1Compat' ?
        $doc.documentElement.scrollWidth : $doc.body.scrollWidth;
  }-*/;

  public abstract void matchDocumentSize(GlassPanel glassPanel);

  public final void matchParentSize(GlassPanel glassPanel, AbsolutePanel parent) {
    DOM.setStyleAttribute(glassPanel.getElement(), "bottom", "0px");
    DOM.setStyleAttribute(glassPanel.getElement(), "right", "0px");
  }

  /**
   * TODO Use implementation from DOMImpl* in GWT 1.5.
   */
  protected abstract int windowGetClientHeight();

  /**
   * TODO Use implementation from DOMImpl* in GWT 1.5.
   */
  protected abstract int windowGetClientWidth();
}
