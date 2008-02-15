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

import com.google.gwt.user.client.Element;
import com.google.gwt.widgetideas.client.GlassPanel;

/**
 * Safari implementation of {@link GlassPanelImpl}.
 */
public class GlassPanelImplSafari extends GlassPanelImplStandard {
  private static native void implMatchDocumentSize(Element elem)
  /*-{
    // TODO Safari does not yet define $doc.compatMode, so we always see $doc.body scroll sizes here
    // This works, but may be producing slightly larger glass panels than strictly necessary
    var scrollWidth = @com.google.gwt.widgetideas.client.impl.GlassPanelImpl::getWindowScrollWidth()();
    var scrollHeight = @com.google.gwt.widgetideas.client.impl.GlassPanelImpl::getWindowScrollHeight()();

    var width = Math.max($wnd.innerWidth, scrollWidth);
    var height = Math.max($wnd.innerHeight, scrollHeight);

    var computedStyle = $doc.defaultView.getComputedStyle($doc.documentElement, null);
    var marginLeft = parseInt(computedStyle.getPropertyValue("margin-left"));
    var marginTop = parseInt(computedStyle.getPropertyValue("margin-top"));
    
    // TODO Verify 'negative HTML element margins' case
    elem.style.marginLeft = -marginLeft + "px";
    elem.style.marginTop = -marginTop + "px";
    
    elem.style.width = (width + marginLeft) + "px";
    elem.style.height = (height + marginTop) + "px";
  }-*/;

  public void matchDocumentSize(GlassPanel glassPanel) {
    implMatchDocumentSize(glassPanel.getElement());
  }

  /**
   * Borrowed from DOMImplSafari in GWT 1.5.
   */
  protected native int windowGetClientHeight()
  /*-{
    return $wnd.innerHeight;
  }-*/;

  /**
   * Borrowed from DOMImplSafari in GWT 1.5.
   */
  protected native int windowGetClientWidth()
  /*-{
    return $wnd.innerWidth;
  }-*/;
}
