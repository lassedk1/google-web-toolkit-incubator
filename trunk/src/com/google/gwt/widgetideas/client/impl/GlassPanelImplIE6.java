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

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.widgetideas.client.GlassPanel;

/**
 * IE6 implementation of {@link GlassPanelImpl}.
 */
public class GlassPanelImplIE6 extends GlassPanelImpl {
  public void matchDocumentSize(GlassPanel glassPanel) {
    int clientWidth = windowGetClientWidth();
    int clientHeight = windowGetClientHeight();

    int offsetWidth = RootPanel.get().getOffsetWidth();
    int offsetHeight = RootPanel.get().getOffsetHeight();

    int scrollWidth = getWindowScrollWidth();
    int scrollHeight = getWindowScrollHeight();

    int width = Math.max(clientWidth, Math.max(offsetWidth, scrollWidth));
    int height = Math.max(clientHeight, Math.max(offsetHeight, scrollHeight));

    glassPanel.setPixelSize(width, height);
  }

  /**
   * Borrowed from DOMImplIE6 in GWT 1.5.
   */
  protected native int windowGetClientHeight()
  /*-{
    // IE standard mode || IE quirks mode.
    return $doc.documentElement.clientHeight || $doc.body.clientHeight; 
  }-*/;

  /**
   * Borrowed from DOMImplIE6 in GWT 1.5.
   */
  protected native int windowGetClientWidth()
  /*-{
    // IE standard mode || IE quirks mode.
    return $doc.documentElement.clientWidth || $doc.body.clientWidth;
  }-*/;
}
