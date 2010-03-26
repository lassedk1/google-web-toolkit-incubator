/*
 * Copyright 2007 Google Inc.
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
package com.google.gwt.demos.youtubeviewer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.YouTubeViewer;
import com.google.gwt.widgetideas.client.EmbeddedObject.EmbeddedObjectInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class YouTubeViewerDemo implements EntryPoint {
  /**
   * The main youtube viewer object.
   */
  YouTubeViewer mainViewer = new YouTubeViewer("mc336q361N0");

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    // Create the YouTube Viewer
    mainViewer = new YouTubeViewer("k56DbGDBsNk");
    mainViewer.setStyleName("YouTubeViewer");
    mainViewer.setWidth("443px");
    mainViewer.setHeight("369px");
    RootPanel.get().add(mainViewer);
    RootPanel.get().add(new HTML("<BR><BR>"));

    // Align options in a grid
    Grid grid = new Grid(5, 3);

    // Set video ID option
    final TextBox videoIDBox = new TextBox();
    videoIDBox.setText("iuywmPePYKQ");
    grid.setHTML(0, 0, "<B>Video ID:</B>");
    grid.setWidget(0, 1, videoIDBox);
    grid.setWidget(0, 2, new Button("Set", new ClickListener() {
      public void onClick(Widget sender) {
        mainViewer.setMovieID(videoIDBox.getText());
      }
    }));

    // Set player width
    final TextBox widthBox = new TextBox();
    widthBox.setText("443px");
    grid.setHTML(1, 0, "<B>Width:</B>");
    grid.setWidget(1, 1, widthBox);
    grid.setWidget(1, 2, new Button("Set", new ClickListener() {
      public void onClick(Widget sender) {
        mainViewer.setWidth(widthBox.getText());
      }
    }));

    // Set player height
    final TextBox heightBox = new TextBox();
    heightBox.setText("369px");
    grid.setHTML(2, 0, "<B>Height:</B>");
    grid.setWidget(2, 1, heightBox);
    grid.setWidget(2, 2, new Button("Set", new ClickListener() {
      public void onClick(Widget sender) {
        mainViewer.setHeight(heightBox.getText());
      }
    }));

    // Plugins page
    final TextBox pluginsBox = new TextBox();
    pluginsBox.setText("http://www.google.com");
    grid.setHTML(3, 0, "<B>Plugins Page:</B>");
    grid.setWidget(3, 1, pluginsBox);
    grid.setWidget(3, 2, new Button("Set", new ClickListener() {
      public void onClick(Widget sender) {
        EmbeddedObjectInfo info = mainViewer.getInfo();
        info.setPluginsPage(pluginsBox.getText());
        mainViewer.setInfo(info);
      }
    }));

    // Enabled/disable autoplay
    grid.setWidget(4, 0, new Button("Enable AutoPlay", new ClickListener() {
      public void onClick(Widget sender) {
        if (mainViewer.isAutoPlayEnabled()) {
          mainViewer.setAutoPlayEnabled(false);
          ((Button) sender).setText("Enable AutoPlay");
        } else {
          mainViewer.setAutoPlayEnabled(true);
          ((Button) sender).setText("Disable AutoPlay");
        }
      }
    }));

    // Enabled/disable related videos
    grid.setWidget(4, 1, new Button("Enable Related", new ClickListener() {
      public void onClick(Widget sender) {
        if (mainViewer.isRelatedVideosEnabled()) {
          mainViewer.setRelatedVideosEnabled(false);
          ((Button) sender).setText("Enable Related");
        } else {
          mainViewer.setRelatedVideosEnabled(true);
          ((Button) sender).setText("Disable Related");
        }
      }
    }));
    RootPanel.get().add(grid);
  }
}
