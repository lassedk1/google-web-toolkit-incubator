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
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

/**
 * This is a utility class to include subsets of the default GWT css resources
 * where needed.
 * 
 */
public class GwtUICssInfo extends WidgetCssInfo {

  /**
   * CSS resource for {@link WrappedCustomListBox}.
   */
  public interface DefaultBundle extends ImmutableResourceBundle {
    @Resource("com/google/gwt/widgetideas/theme/public/GwtButtons.css")
    CssResource buttonCss();
  }

  private static DefaultBundle bundle = null;

  private static GwtUICssInfo ME;

  public static GwtUICssInfo get() {
    if (ME == null) {
      ME = new GwtUICssInfo();
    }
    return ME;
  }

  public static void init(GwtUICssInfo resources) {
    validate(ME, resources);
    ME = resources;
  }

  public void forButtons() {
    if (shouldInject()) {
      inject(bundle().buttonCss());
    }
  }

  private DefaultBundle bundle() {
    if (bundle == null) {
      bundle = GWT.create(DefaultBundle.class);
    }
    return bundle;
  }
}