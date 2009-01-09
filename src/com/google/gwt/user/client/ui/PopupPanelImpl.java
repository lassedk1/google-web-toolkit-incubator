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

package com.google.gwt.user.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.impl.PopupImpl;

/**
 * 
 * Adaptor for incubator's DropDownPanel so DropDownPanel can get access to the
 * popup fields it needs.
 * 
 */
public class PopupPanelImpl extends PopupPanel {

  /**
   * Creates a PopupAnimation.
   */
  public static class PopupAnimation extends ResizeAnimation {
    public PopupAnimation(PopupPanelImpl panel) {
      super(panel);
    }

    protected void clip(int top, int right, int bottom, int left) {
      impl.setClip(getPanel().getElement(), getRectString(top, right, bottom,
          left));
    }

    protected final native int getOffsetHeight()/*-{
      return this.@com.google.gwt.user.client.ui.PopupPanel.ResizeAnimation::offsetHeight;
    }-*/;

    protected final native int getOffsetWidth()/*-{
      return this.@com.google.gwt.user.client.ui.PopupPanel.ResizeAnimation::offsetWidth;
    }-*/;

    /**
     * Returns the {@link PopupPanelOverride} associated with this animation.
     * 
     * @return the popup panel;
     */

    protected final native PopupPanelImpl getPanel()/*-{
      return this.@com.google.gwt.user.client.ui.PopupPanel.ResizeAnimation::curPanel;
    }-*/;

    /**
     * Is the {@link PopupPanelOverride} being shown?
     * 
     * @return whether the widget is being shown
     */

    protected native boolean isShowing()/*-{
      return this.@com.google.gwt.user.client.ui.PopupPanel.ResizeAnimation::showing;
    }-*/;

    /**
     * Clicks the popup element.
     */
    protected void removeClip() {
      impl.setClip(getPanel().getElement(), "rect(auto, auto, auto, auto)");
    }
  }

  private static final PopupImpl impl = GWT.create(PopupImpl.class);

  /**
   * @return a rect string
   */
  private static String getRectString(int top, int right, int bottom, int left) {
    return "rect(" + top + "px, " + right + "px, " + bottom + "px, " + left
        + "px)";
  }

  public PopupPanelImpl(boolean autoHide) {
    super(autoHide);
  }

  protected void setAnimation(PopupAnimation animation) {
    assert (this.isAttached() == false);
    super.setAnimation(animation);
  }

}
