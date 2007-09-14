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
package com.google.gwt.widgetideas.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Tests the ProgressBar widget.
 */
public class SliderBarTest extends GWTTestCase {
    
  public String getModuleName() {
    return "com.google.gwt.widgetideas.WidgetIdeas";
  }
  
  /**
   * Test that the accessors correctly change the slider value.
   */
  public void testAccessors() {
    // Create a slider bar
    SliderBar slider = new SliderBar(0.0, 100.0) {
      /**
       * Format the label to display above the ticks
       * 
       * Override this method in a subclass to customize the format.  By
       * default, this method returns the integer portion of the value.
       * 
       * @param value the value at the label
       * @return the text to put in the label
       */
      public String formatLabel(double value) {
        return super.formatLabel(value);
      }
    };
    slider.setCurrentValue(50.0);
    slider.setStepSize(2.0);
    RootPanel.get().add(slider);
    
    // Test Accessors
    slider.setMaxValue(200.0);
    assertEquals(slider.getMaxValue(), 200.0, 0.0);
    slider.setMinValue(-200.0);
    assertEquals(slider.getMinValue(), -200.0, 0.0);
    assertEquals(slider.getTotalRange(), 400.0, 0.0);
    slider.setNumLabels(5);
    assertEquals(slider.getNumLabels(), 5);
    slider.setNumTicks(10);
    assertEquals(slider.getNumTicks(), 10);
    slider.setStepSize(10.25);
    assertEquals(slider.getStepSize(), 10.25, 0.0);

    // Test current values 
    slider.setMinValue(-100.0);
    slider.setMaxValue(100.0);
    slider.setStepSize(1.0);
    slider.setCurrentValue(100.0);
    assertEquals(slider.getCurrentValue(), 100.0, 0.0);
    assertEquals(slider.formatLabel(10.123), "10.1");
    slider.setCurrentValue(-100.0);
    assertEquals(slider.getCurrentValue(), -100.0, 0.0);
    slider.setCurrentValue(0.0);
    assertEquals(slider.getCurrentValue(), 0.0, 0.0);
    slider.setCurrentValue(200.0);
    assertEquals(slider.getCurrentValue(), 100.0, 0.0);
    slider.setCurrentValue(-200.0);
    assertEquals(slider.getCurrentValue(), -100.0, 0.0);

    // Test current values between steps
    slider.setMinValue(-1.0);
    slider.setMaxValue(0.99);
    slider.setStepSize(0.1);
    slider.setCurrentValue(0.56);
    assertEquals(slider.getCurrentValue(), 0.6, 0.0001);
    slider.setCurrentValue(0.54);
    assertEquals(slider.getCurrentValue(), 0.5, 0.0001);
    slider.setCurrentValue(0.99);
    assertEquals(slider.getCurrentValue(), 0.9, 0.0001);
    slider.setCurrentValue(-1.0);
    assertEquals(slider.getCurrentValue(), -1.0, 0.0);
    
    // Test illegal bounds
    slider.setMinValue(200.0);
    slider.setMaxValue(100.0);
    assertEquals(slider.getTotalRange(), 0.0, 0.0);
    slider.setCurrentValue(150.0);
    assertEquals(slider.getCurrentValue(), 200.0, 0.0);

    // Test bounds when changing range and step size
    slider.setMinValue(0.0);
    slider.setMaxValue(100.0);
    slider.setStepSize(15.0);
    slider.setCurrentValue(90.0);
    assertEquals(slider.getCurrentValue(), 90.0, 0.0);
    slider.setMaxValue(80.0);
    assertEquals(slider.getCurrentValue(), 75.0, 0.0);
    slider.setMinValue(5.0);
    assertEquals(slider.getCurrentValue(), 80.0, 0.0);
    slider.setStepSize(10.0);
    assertEquals(slider.getCurrentValue(), 75.0, 0.0);
  }
  
  /**
   * Tests the various uses of TextFormatters.
   */
  public void testTextFormatter() {
    // Set a new label formatter
    SliderBar slider = new SliderBar(0.0, 100.0) {
      protected String formatLabel(double value) {
        return super.formatLabel(value);
      }      
    };
    slider.setLabelFormatter(new SliderBar.LabelFormatter() {
      protected String formatLabel(SliderBar slider, double value) {
        return "L: " + (int) value;
      }
    });
    
    // Verify the new label formatter overrides the old one
    slider.setCurrentValue(43.123);
    assertEquals(slider.formatLabel(43.123), "L: 43");
    
    // Check label format without a label formatter
    slider.setLabelFormatter(null);
    assertEquals(slider.formatLabel(43.123), "43.1");
  }
}
