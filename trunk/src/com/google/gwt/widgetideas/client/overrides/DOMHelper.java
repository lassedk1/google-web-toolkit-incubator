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

package com.google.gwt.widgetideas.client.overrides;

import com.google.gwt.user.client.Element;

/**
 * Useful DOM methods not included in {@link  com.google.gwt.user.client.Window}
 * or {@link com.google.gwt.user.client.DOM}.
 * 
 */
public class DOMHelper {

  /**
   * Clones a DOM element.
   */
  public static native Element clone(Element elem, boolean deep) /*-{
    return elem.cloneNode(deep);
  }-*/;

  /**
   * Displays a message in a modal dialog box. Should eventually be moved to
   * Window.
   * 
   * @param msg the message to be displayed.
   */
  public static native boolean confirm(String msg) /*-{
    return $wnd.confirm(msg);
  }-*/;

  /**
   * Gets the first child. You must *KNOW* that the first child exists and is an
   * element to use this method safely.
   */
  public static native Element rawFirstChild(Element elem) /*-{
    return elem.firstChild;
  }-*/;

}
