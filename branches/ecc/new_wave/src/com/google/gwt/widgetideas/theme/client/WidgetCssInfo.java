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

package com.google.gwt.widgetideas.theme.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.libideas.resources.client.CssResource;

/**
 * Agglomeration class to add separate widget ideas css files. All files are
 * relative to the gwt/standard module.
 * <p>
 * As it is useful to have all the css files stored in a logical directory, that
 * code is localized here.
 */
public abstract class WidgetCssInfo {
  static class DisabledMode extends Mode {
    @Override
    public void inject(CssResource res) {
      Log.info("default css is disabled, so not including " + res.getName());
    }

    @Override
    protected boolean shouldInject() {
      return false;
    }
  }

  static class Mode {
    public void inject(CssResource res) {
      StyleInjector.injectStylesheet(res.getText());
    }

    protected boolean shouldInject() {
      return true;
    }
  }

  private static Mode m = GWT.create(Mode.class);

  private static WidgetCssInfo ME = new WidgetCssInfo() {
  };

  public static WidgetCssInfo get() {
    return ME;
  }

  public static void validate(Object currentBundle, Object newBundle) {
    if (currentBundle != null) {
      assert false : "Should not initialize css info after the info has been accessed";
      throw new IllegalStateException();
    }
    if (newBundle == null) {
      assert false : "Should never set a null resources";
      throw new IllegalStateException();
    }
  }

  protected static <CssType extends CssResource> CssType inject(CssType b) {
    m.inject(b);
    return b;
  }

  private boolean disabled;

  public boolean getDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public boolean shouldInject() {
    return !disabled && m.shouldInject();
  }

}
