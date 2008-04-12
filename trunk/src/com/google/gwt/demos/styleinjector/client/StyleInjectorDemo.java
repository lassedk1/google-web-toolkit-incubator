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
package com.google.gwt.demos.styleinjector.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.DataResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.TextResource;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StyleInjectorDemo implements EntryPoint {

  /**
   * Resources used.
   */
  public interface Resources extends ImmutableResourceBundle {
    Resources INSTANCE = GWT.create(Resources.class);

    @Resource("StyleInjectorDemo.jpg")
    DataResource background();

    @Resource("StyleInjectorDemo.css")
    TextResource css();
  }

  /**
   * This is the worlds simplest.
   */
  public void onModuleLoad() {
    StyleInjector.injectStylesheet(Resources.INSTANCE.css().getText(),
        Resources.INSTANCE);
    RootPanel.get().add(
        new HTML(
            "<h1>This, a picture of my mom and daughter, it should take some time to load the first time. It should load like lighting the second time to show the value of immutable resources.</h1>"));
  }
}
