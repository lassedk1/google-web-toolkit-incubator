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

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.logging.shared.Level;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

/**
 * Helper class to allow developers to easily inject css for their widgets.
 */
public class Gen2CssInjector {
  /**
   * CSS resources for all gen2 widgets.
   */
  static interface DefaultBundle extends ImmutableResourceBundle {
    @Resource("com/google/gwt/gen2/widgetbase/public/DropDownListBox.css")
    CssResource dropDownListBoxCss();

    @Resource("com/google/gwt/gen2/widgetbase/public/Picker.css")
    CssResource pickerCss();
  }
  static class DisabledMode extends Mode {
    @Override
    public void inject(CssResource res) {
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

  /**
   * CSS resource for log handlers in handler.client.
   */
  static interface Resources extends ImmutableResourceBundle {
    @Resource("com/google/gwt/gen2/widgetbase/public/LogHandlers.css")
    CssResource css();
  }

  static DefaultBundle DEFAULT_CSS_FILES = GWT.create(DefaultBundle.class);

  private static Mode m = GWT.create(Mode.class);

  /**
   * If css dependency injection is enabled, adds the DropDownListBox.css file.
   */
  public static void addDropDownListBoxDefault() {
    inject(DEFAULT_CSS_FILES.dropDownListBoxCss());
  }

  /**
   * Injects the default css used for the log handlers defined in this package.
   */
  public static void addLogHandlerDefault() {
    if (Gen2CssInjector.isInjectionEnabled()) {
      CssResource css = ((Resources) GWT.create(Resources.class)).css();
      StyleInjector.injectStylesheet(css.getText());
    }
  }

  /**
   * If css dependency injection is enabled, adds the ToggleButton.css file
   * included under public/widget.
   */
  public static void addPickerDefault() {
    inject(DEFAULT_CSS_FILES.pickerCss());
  }

  /**
   * Gets the style name associated with all predefined levels. This interface
   * is primarily used by widget log handlers to display log levels
   * consistently.
   * 
   * <dl>
   * <dt>Severe</dt>
   * <dd>logSEVERE</dd>
   * <dt>Config</dt>
   * <dd>logCONFIG</dd>
   * <dt>Fine</dt>
   * <dd>logFINE</dd>
   * <dt>. . .</dt>
   * <dd></dd>
   * </dl>
   * 
   * 
   * In other words, uses "log" + name of level to calculate the level name.
   * 
   * @param level level
   * 
   * @return style name
   */
  public static String getStyle(Level level) {
    return "log" + level.getName();
  }

  /**
   * Injects the given css resource into the program when DebugCss is not
   * included.
   */
  public static <CssType extends CssResource> CssType inject(CssType b) {
    m.inject(b);
    return b;
  }

  /**
   * Can any css resources be injected?
   */
  public static boolean isInjectionEnabled() {
    return m.shouldInject();
  }
}
