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

package com.google.gwt.gen2.logging.handler.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.logging.shared.LogHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A scrollable pane to display log messages.
 * 
 * @param <ContentType> the content display type
 */
abstract class ScrollableLogHandler<ContentType extends Widget & HasWidgets>
    extends Composite implements LogHandler {

  private ContentType contents;
  final ScrollPanel scroller = new ScrollPanel();

  /**
   * Constructor.
   * 
   * @param styleName the style name of this scrollable log handler
   */
  protected ScrollableLogHandler(String styleName) {
    VerticalPanel panel = new VerticalPanel();
    initWidget(panel);
    setStyleName(styleName);
    scroller.setStyleName("logContentPane");
    panel.add(scroller);

    this.contents = createContents();
    scroller.add(contents);
    Button button = new Button("clear log", new ClickHandler() {
      
      public void onClick(ClickEvent event) {
        clear();
      }

    });
    panel.add(button);
    panel.setCellHeight(button, "1px");
  }

  /**
   * Clears all messages.
   */
  public void clear() {
    scroller.remove(contents);
    this.contents = createContents();
    scroller.add(contents);
  }

  /**
   * Creates the internal panel's contents.
   * 
   * @return the contents
   */
  protected abstract ContentType createContents();

  protected ContentType getContents() {
    return contents;
  }
}
