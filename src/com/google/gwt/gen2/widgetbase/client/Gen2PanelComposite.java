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

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetAdaptorImpl;

import java.util.Iterator;

/**
 * A special case composite for panels.
 * 
 * @param <PanelType> the type of panel being wrapped.
 */
public abstract class Gen2PanelComposite<PanelType extends Widget & HasWidgets>
    extends Gen2Composite<PanelType> implements HasWidgets {

  /**
   * Constructor.
   */
  public Gen2PanelComposite() {
  }

  /**
   * Constructor.
   */
  public Gen2PanelComposite(PanelType widget) {
    initWidget(widget);
  }

  public void add(Widget w) {
    getWidget().add(w);
  }

  public void clear() {
    getWidget().clear();
  }

  public Iterator<Widget> iterator() {
    return getWidget().iterator();
  }

  public boolean remove(Widget w) {
    return getWidget().remove(w);
  }

  protected final void adopt(Widget widget) {
    WidgetAdaptorImpl.setParent(widget, this);
  }
}
