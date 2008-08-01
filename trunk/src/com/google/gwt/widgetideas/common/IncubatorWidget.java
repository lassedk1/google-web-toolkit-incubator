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

package com.google.gwt.widgetideas.common;

import com.google.gwt.libideas.event.shared.AbstractEvent;
import com.google.gwt.libideas.event.shared.EventHandler;
import com.google.gwt.libideas.event.shared.HandlerManager;
import com.google.gwt.libideas.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

/**
 * All top-level incubator widgets should extend {@link IncubatorWidget}.
 * {@link IncubatorWidget} will eventually include all the extra handler and
 * styling information.
 * 
 * @param <Css> css helper class.
 */
public abstract class IncubatorWidget<Css extends IncubatorWidget.AbstractCss>
    extends Widget {
  /**
   * All Css interfaces for incubator widgets must extends this one.
   */
  public interface AbstractCss {
    String styleName();
  }

  /**
   * {@link CssAdaptor} is a temporary helper class used to bridge the gap so we
   * <li> Enterprise applications can use CSSResources </li>
   * <li> Unless the CSS-enabled interface is invoked explicitly, setStyleName
   * will continue to work </li>
   * <li> With reasonable, if not optimal size/performance metrics, we can
   * heavily use dependent styles to support the
   * avoid-descendant-selectors-if-at-all-possible directive.</li>
   * <li> Due to the cost of setting excess style names, we try to maintain the
   * invariant of at most one style name per element </li>
   */
  public abstract static class CssAdaptor implements AbstractCss {
    private String baseName;

    public abstract CssAdaptor copy(String name);

    /**
     * Sets the base name for this widget's styles.
     */
    public void setBaseName(String name) {
      assert (baseName == null);
      baseName = name;
    }

    /**
     * Gets the style name. Conforming to Constant/CssResource pattern of
     * omitting the "get".
     * 
     * @return the style name.
     */
    public String styleName() {
      return baseName;
    }

    /**
     * Wraps the given dependent style name.
     * 
     * @param dependent dependent style name
     * @return style name
     */
    public String wrap(String dependent) {
      return baseName + "-" + dependent;
    }
  }

  private Css css;

  private HandlerManager handlers;

  public String getStylePrimaryName() {
    return css == null ? null : css.styleName();
  }

  public void setStyleName(String name) {
    adaptCssFor(name);
  }

  public void setStylePrimaryName(String style) {
    super.setStylePrimaryName(getStyleElement(), style);
    adaptCssFor(style);
  }

  // This code can probably not be compiled out, but this toString is
  // sufficiently more useful the cost seems worth it.
  @Override
  public String toString() {
    String accum = this.getStylePrimaryName() + " widget";
    String id = this.getElement().getId();
    if (id != null && id.trim().length() > 0) {
      accum += "id " + id;
    }
    return accum;
  }

  /**
   * Adds this handler to the widget
   * 
   * @param key the event key
   * @param handler the handler
   */
  protected <T extends EventHandler> HandlerRegistration addHandler(
      AbstractEvent.Key<T> key, final T handler) {
    if (handlers == null) {
      handlers = new HandlerManager(this);
    }
    return handlers.addEventHandler(key, handler);
  }

  /**
   * Returns access to the css accessor methods.
   * 
   * @return access to css accessor methods.
   */
  protected final Css css() {
    return css;
  }

  /**
   * Fires an event.
   * 
   * @param event the event
   */
  protected void fireEvent(AbstractEvent event) {
    if (handlers != null) {
      handlers.fireEvent(event);
    }
  }

  /**
   * Initial your css object.
   * 
   * @param css css
   */
  protected final void initCss(Css css) {
    this.css = css;
    onCssInit(css);
  }

  protected final boolean listeningTo(AbstractEvent.Key key) {
    return handlers == null ? false : handlers.listeningTo(key);
  }

  /**
   * On css init
   * 
   * @param css the new css.
   */
  protected void onCssInit(Css css) {
    getElement().setClassName(css.styleName());
  }

  private void adaptCssFor(String name) {
    if (!(css instanceof CssAdaptor)) {
      assert false : "If you plan to call setStyleName() on your widget, do not override the default css resources for that widget. Your are using "
          + css.getClass();
      throw new IllegalStateException();
    }
    CssAdaptor newCss = ((CssAdaptor) css).copy(name);
    initCss((Css) newCss);
  }
}
