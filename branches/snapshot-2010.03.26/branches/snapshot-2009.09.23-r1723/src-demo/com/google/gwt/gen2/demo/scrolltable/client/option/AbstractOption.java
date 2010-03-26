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
package com.google.gwt.gen2.demo.scrolltable.client.option;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option that can be lazily initialized.
 */
public abstract class AbstractOption extends Composite {
  /**
   * The style name applied to cells that contain labels.
   */
  protected static final String STYLENAME_LABEL = "AbstractOption-Label";

  /**
   * The panel that wraps the contents.
   */
  private VerticalPanel wrapper = new VerticalPanel();

  /**
   * Indicates if we've initialized this option or not.
   */
  private boolean initialized = false;

  /**
   * Construct a new {@link AbstractOption}.
   */
  public AbstractOption() {
    initWidget(wrapper);
  }

  /**
   * @return the description to display at the top of the option
   */
  protected abstract String getDescription();

  /**
   * Initialize the tab for the first time.
   */
  protected void initialize() {
    if (initialized) {
      return;
    }
    initialized = true;

    // Add the content
    wrapper.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    wrapper.add(new HTML(getDescription()));
    wrapper.add(onInitialize());
  }

  /**
   * Render the option.
   * 
   * @return the widget to initialize to.
   */
  protected abstract Widget onInitialize();

  /**
   * This method is called immediately after a widget becomes attached to the
   * browser's document.
   */
  @Override
  protected void onLoad() {
    super.onLoad();
    initialize();
  }
}
