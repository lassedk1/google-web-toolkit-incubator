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
package com.google.gwt.demos.progressbar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.ProgressBar;
import com.google.gwt.widgetideas.client.ResizableWidgetCollection;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ProgressBarDemo implements EntryPoint {
  /**
   * A boolean indicating whether or not we should use custom text.
   */
  private boolean useCustomText = true;

  /**
   * The first example at the bottom of the page.
   */
  private ProgressBar exampleBar1 = new ProgressBar();

  /**
   * The second example at the bottom of the page.
   */
  private ProgressBar exampleBar2 = new ProgressBar();

  /**
   * The main progress bar at the top of the page.
   */
  private ProgressBar mainProgressBar = new ProgressBar(0.0, 2100.0, 0.0) {
    @Override
    protected String generateText(double curProgress) {
      if (useCustomText) {
        if (getPercent() >= 1.0) {
          return "FileTreeTableItem Uploaded";
        } else {
          return kb2s(curProgress) + " out of " + kb2s(getMaxProgress()) + "("
              + super.generateText(curProgress) + ") uploaded";
        }
      } else {
        return super.generateText(curProgress);
      }
    }

    /**
     * Convert kilobytes to a string
     * 
     * @param kb the number kilobytes
     * @return a string representation of the filesize
     */
    private String kb2s(double kb) {
      if (kb > 1000) {
        return ((int) (kb / 1000 * 10)) / 10.0 + "Mb";
      } else {
        return ((int) kb) + "kb";
      }
    }
  };

  /**
   * A timer used for simulations.
   */
  private Timer simulationTimer = new Timer() {
    @Override
    public void run() {
      if (mainProgressBar.getPercent() >= 1.0) {
        cancel();
      } else {
        mainProgressBar.setProgress(mainProgressBar.getProgress() + 123);
        exampleBar1.setProgress(100 * mainProgressBar.getPercent());
        exampleBar2.setProgress(100 * mainProgressBar.getPercent());
      }
    }
  };

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    // Setup the progress bars
    exampleBar2.setTextVisible(false);
    exampleBar2.addStyleName("gwt-ProgressBar-thin");

    // Place everything in a nice looking grid
    Grid grid = new Grid(7, 3);
    grid.setBorderWidth(1);
    grid.setCellPadding(3);

    // Set the current progress
    final TextBox curBox = new TextBox();
    curBox.setText("0.0");
    grid.setWidget(0, 1, curBox);
    grid.setHTML(0, 2, "The current progress.");
    grid.setWidget(0, 0, new Button("Set Progress", new ClickListener() {
      public void onClick(Widget sender) {
        simulationTimer.cancel();
        mainProgressBar.setProgress(new Float(curBox.getText()).floatValue());
      }
    }));

    // Set the minimum progress
    final TextBox minBox = new TextBox();
    minBox.setText(mainProgressBar.getMinProgress() + "");
    grid.setWidget(1, 1, minBox);
    grid.setHTML(1, 2, "The minimum progress progress.");
    grid.setWidget(1, 0, new Button("Set Min Progress", new ClickListener() {
      public void onClick(Widget sender) {
        simulationTimer.cancel();
        mainProgressBar.setMinProgress(new Float(minBox.getText()).floatValue());
      }
    }));

    // Set the maximum progress
    final TextBox maxBox = new TextBox();
    maxBox.setText(mainProgressBar.getMaxProgress() + "");
    grid.setWidget(2, 1, maxBox);
    grid.setHTML(2, 2, "The maximum progress.");
    grid.setWidget(2, 0, new Button("Set Max Progress", new ClickListener() {
      public void onClick(Widget sender) {
        simulationTimer.cancel();
        mainProgressBar.setMaxProgress(new Float(maxBox.getText()).floatValue());
      }
    }));

    // Show or hide the text
    final HTML textVisibleLabel = new HTML("visible");
    grid.setWidget(3, 1, textVisibleLabel);
    grid.setHTML(3, 2, "Show or hide the text in the progress bar.");
    grid.setWidget(3, 0, new Button("Toggle Text", new ClickListener() {
      public void onClick(Widget sender) {
        if (mainProgressBar.isTextVisible()) {
          textVisibleLabel.setHTML("hidden");
          mainProgressBar.setTextVisible(false);
        } else {
          textVisibleLabel.setHTML("visible");
          mainProgressBar.setTextVisible(true);
        }
      }
    }));

    // Add the default text option
    final HTML defaultTextLabel = new HTML("custom");
    grid.setWidget(4, 1, defaultTextLabel);
    grid.setHTML(4, 2, "Override the format of the text with a custom"
        + "format.");
    grid.setWidget(4, 0, new Button("Toggle Custom Text", new ClickListener() {
      public void onClick(Widget sender) {
        if (useCustomText) {
          defaultTextLabel.setHTML("default");
          useCustomText = false;
          mainProgressBar.setProgress(mainProgressBar.getProgress());
        } else {
          defaultTextLabel.setHTML("custom");
          useCustomText = true;
          mainProgressBar.setProgress(mainProgressBar.getProgress());
        }
      }
    }));

    // Add static resize timer methods
    final HTML resizeCheckLabel = new HTML("enabled");
    grid.setWidget(5, 1, resizeCheckLabel);
    grid.setHTML(5, 2, "When resize checking is enabled, a Timer will "
        + "periodically check if the Widget's dimensions have changed.  If "
        + "they change, the widget will be redrawn.");
    grid.setWidget(5, 0, new Button("Toggle Resize Checking",
        new ClickListener() {
          public void onClick(Widget sender) {
            if (ResizableWidgetCollection.get().isResizeCheckingEnabled()) {
              ResizableWidgetCollection.get().setResizeCheckingEnabled(false);
              resizeCheckLabel.setHTML("disabled");

            } else {
              ResizableWidgetCollection.get().setResizeCheckingEnabled(true);
              resizeCheckLabel.setHTML("enabled");
            }
          }
        }));

    // Create a form to set width of element
    final TextBox widthBox = new TextBox();
    widthBox.setText("50%");
    grid.setWidget(6, 1, widthBox);
    grid.setHTML(6, 2, "Set the width of the widget.  Use this to see how "
        + "resize checking detects the new dimensions and redraws the widget.");
    grid.setWidget(6, 0, new Button("Set Width", new ClickListener() {
      public void onClick(Widget sender) {
        mainProgressBar.setWidth(widthBox.getText());
      }
    }));

    // Add all components to the page
    RootPanel.get().add(mainProgressBar);
    RootPanel.get().add(new HTML("<BR>"));
    RootPanel.get().add(grid);
    RootPanel.get().add(new HTML("<BR>Additional Progress Bars:<BR>"));
    RootPanel.get().add(exampleBar1);
    RootPanel.get().add(new HTML("<BR>"));
    RootPanel.get().add(exampleBar2);

    // Simulate progress over time
    simulateProgress();
  }

  /**
   * Run an example timer that shows a progression of progress over time.
   */
  private void simulateProgress() {
    simulationTimer.scheduleRepeating(200);
  }

}
