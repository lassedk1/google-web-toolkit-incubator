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
package com.google.gwt.gen2.demo.picker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.gen2.picker.client.SliderBar;
import com.google.gwt.gen2.picker.client.SliderBar.LabelFormatter;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.widgetideas.client.ResizableWidgetCollection;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SliderBarDemo implements EntryPoint {
  /**
   * A boolean indicating whether or not we should use custom text.
   */
  private boolean useCustomText = true;

  /**
   * The first example at the bottom of the page.
   */
  private SliderBar exampleBar1 = new SliderBar(0.0, 1.0);

  /**
   * The second example at the bottom of the page.
   */
  private SliderBar exampleBar2 = new SliderBar(0.0, 25.0,
      new LabelFormatter() {
        public String formatLabel(SliderBar slider, double value) {
          return ((char) ('a' + value)) + "";
        }
      });

  /**
   * The main slider bar at the top of the page.
   */
  private SliderBar mainSliderBar = new SliderBar(0.0, 100.0,
      new LabelFormatter() {
        public String formatLabel(SliderBar slider, double value) {
          if (useCustomText) {
            return (int) value + "s";
          } else {
            return (int) (10 * value) / 10.0 + "";
          }
        }
      });

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    SliderBar.injectDefaultCss();
    // TextBox to display or set current value
    final TextBox curBox = new TextBox();

    // Setup the slider bars
    mainSliderBar.setStepSize(5.0);
    mainSliderBar.setCurrentValue(50.0);
    mainSliderBar.setNumTicks(10);
    mainSliderBar.setNumLabels(5);
    mainSliderBar.addValueChangeHandler(new ValueChangeHandler<Double>() {

      @Override
      public void onValueChange(ValueChangeEvent<Double> event) {
        curBox.setText(event.getValue().toString());
      }
    });
    exampleBar1.setStepSize(0.1);
    exampleBar1.setCurrentValue(0.5);
    exampleBar1.setNumTicks(10);
    exampleBar1.setNumLabels(10);
    exampleBar2.setStepSize(1.0);
    exampleBar2.setCurrentValue(13.0);
    exampleBar2.setNumTicks(25);
    exampleBar2.setNumLabels(25);

    // Place everything in a nice looking grid
    Grid grid = new Grid(9, 3);
    grid.setBorderWidth(1);
    grid.setCellPadding(3);

    // The type of text to display
    final HTML defaultTextLabel = new HTML("custom");

    // Set the current slider position
    curBox.setText("50.0");
    grid.setWidget(0, 1, curBox);
    grid.setHTML(0, 2, "The current value of the knob.");
    grid.setWidget(0, 0, new Button("Set Current Value", new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        mainSliderBar.setCurrentValue(new Float(curBox.getText()).floatValue());
      }
    }));

    // Set the minimum value
    final TextBox minBox = new TextBox();
    minBox.setText("0.0");
    grid.setWidget(1, 1, minBox);
    grid.setHTML(1, 2, "The lower bounds (minimum) of the range.");
    grid.setWidget(1, 0, new Button("Set Min Value", new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        mainSliderBar.setMinValue(new Float(minBox.getText()).floatValue());
      }
    }));

    // Set the maximum value
    final TextBox maxBox = new TextBox();
    maxBox.setText("100.0");
    grid.setWidget(2, 1, maxBox);
    grid.setHTML(2, 2, "The upper bounds (maximum) of the range.");
    grid.setWidget(2, 0, new Button("Set Max Value", new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        mainSliderBar.setMaxValue(new Float(maxBox.getText()).floatValue());
      }
    }));

    // Set the step size
    final TextBox stepSizeBox = new TextBox();
    stepSizeBox.setText("1.0");
    grid.setWidget(3, 1, stepSizeBox);
    grid.setHTML(3, 2, "The increments between each knob position.");
    grid.setWidget(3, 0, new Button("Set Step Size", new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        mainSliderBar.setStepSize(new Float(stepSizeBox.getText()).floatValue());
      }
    }));

    // Set the number of tick marks
    final TextBox numTicksBox = new TextBox();
    numTicksBox.setText("10");
    grid.setWidget(4, 1, numTicksBox);
    grid.setHTML(4, 2,
        "The vertical black lines along the range of value.  Note that the "
            + "number of ticks is actually one more than the number you "
            + "specify, so setting the number of ticks to one will display a "
            + "tick at each end of the slider.");
    grid.setWidget(4, 0, new Button("Set Num Ticks", new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        mainSliderBar.setNumTicks(new Integer(numTicksBox.getText()).intValue());
      }
    }));

    // Set the number of labels
    final TextBox numLabelsBox = new TextBox();
    numLabelsBox.setText("5");
    grid.setWidget(5, 1, numLabelsBox);
    grid.setHTML(5, 2, "The labels above the ticks.");
    grid.setWidget(5, 0, new Button("Set Num Labels", new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        mainSliderBar.setNumLabels(new Integer(numLabelsBox.getText()).intValue());
      }
    }));

    // Create a form to set width of element
    final TextBox widthBox = new TextBox();
    widthBox.setText("50%");
    grid.setWidget(6, 1, widthBox);
    grid.setHTML(6, 2, "Set the width of the slider.  Use this to see how "
        + "resize checking detects the new dimensions and redraws the widget.");
    grid.setWidget(6, 0, new Button("Set Width", new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        mainSliderBar.setWidth(widthBox.getText());
      }
    }));

    // Add the default text option
    grid.setWidget(7, 1, defaultTextLabel);
    grid.setHTML(7, 2, "Override the format of the labels with a custom"
        + "format.");
    grid.setWidget(7, 0, new Button("Toggle Custom Text", new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {

        if (useCustomText) {
          defaultTextLabel.setHTML("default");
          useCustomText = false;
          mainSliderBar.redraw();
        } else {
          defaultTextLabel.setHTML("custom");
          useCustomText = true;
          mainSliderBar.redraw();
        }
      }
    }));

    // Add static resize timer methods
    final HTML resizeCheckLabel = new HTML("enabled");
    grid.setWidget(8, 1, resizeCheckLabel);
    grid.setHTML(8, 2, "When resize checking is enabled, a Timer will "
        + "periodically check if the Widget's dimensions have changed.  If "
        + "they change, the widget will be redrawn.");
    grid.setWidget(8, 0, new Button("Toggle Resize Checking",
        new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
            if (ResizableWidgetCollection.get().isResizeCheckingEnabled()) {
              ResizableWidgetCollection.get().setResizeCheckingEnabled(false);
              resizeCheckLabel.setHTML("disabled");

            } else {
              ResizableWidgetCollection.get().setResizeCheckingEnabled(true);
              resizeCheckLabel.setHTML("enabled");
            }
          }
        }));

    // Add elements to page
    RootPanel.get().add(mainSliderBar);
    RootPanel.get().add(new HTML("<BR>"));
    RootPanel.get().add(grid);
    RootPanel.get().add(new HTML("<BR>Additional SliderBars:<BR>"));
    RootPanel.get().add(exampleBar1);
    RootPanel.get().add(new HTML("<BR>"));
    RootPanel.get().add(exampleBar2);
  }
}
