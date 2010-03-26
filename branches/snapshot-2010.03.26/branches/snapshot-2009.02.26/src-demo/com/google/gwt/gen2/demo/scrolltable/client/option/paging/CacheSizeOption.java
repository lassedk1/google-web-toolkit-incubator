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
package com.google.gwt.gen2.demo.scrolltable.client.option.paging;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.demo.scrolltable.client.PagingScrollTableDemo;
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.CustomForm;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * An option.
 */
public class CacheSizeOption extends AbstractOption {
  private static final String DESC_CACHE = "Set the number of rows before and "
      + "after the current page to cache.";

  @Override
  protected String getDescription() {
    return DESC_CACHE;
  }

  @Override
  protected Widget onInitialize() {
    CustomForm form = new CustomForm();

    // Pre cache size
    final TextBox preCacheBox = new TextBox();
    preCacheBox.setText("50");
    preCacheBox.setWidth("50px");
    form.addLabeledWidget("Pre Cache Size:", preCacheBox);

    // Post cache size
    final TextBox postCacheBox = new TextBox();
    postCacheBox.setText("50");
    postCacheBox.setWidth("50px");
    form.addLabeledWidget("Post Cache Size:", postCacheBox);

    // Add button to set the row count
    {
      Button button = new Button("Set Cache Size", new ClickHandler() {
        public void onClick(ClickEvent event) {
          try {
            int preCache = Integer.parseInt(preCacheBox.getText());
            int postCache = Integer.parseInt(postCacheBox.getText());
            PagingScrollTableDemo.get().getCachedTableModel().setPreCachedRowCount(
                preCache);
            PagingScrollTableDemo.get().getCachedTableModel().setPostCachedRowCount(
                postCache);
          } catch (NumberFormatException e) {
            Window.alert("Please enter valid integers for the row and column.");
          }
        }
      });
      form.addButton(button);
    }

    return form;
  }
}
