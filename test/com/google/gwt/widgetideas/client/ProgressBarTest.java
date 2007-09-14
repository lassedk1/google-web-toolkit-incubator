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
import com.google.gwt.user.client.DOM;

/**
 * Tests the ProgressBar widget.
 */
public class ProgressBarTest extends GWTTestCase {

  public String getModuleName() {
    return "com.google.gwt.widgetideas.WidgetIdeas";
  }

  /**
   * Test that the accessors correctly adjust the progress and relative
   * percent completion.
   */
  public void testAccessors() {
    // Create a progress bar with extra accessors
    ProgressBar bar = new ProgressBar() {
      public String generateText(double curProgress) {
        return super.generateText(curProgress);
      }
    };
   
    // Test the accessors
    bar.setMinProgress(-1000.0);
    assertEquals(bar.getMinProgress(), -1000.0, 0.0);
    bar.setMaxProgress(1000.0);
    assertEquals(bar.getMaxProgress(), 1000.0, 0.0);
    bar.setProgress(500.0);
    assertEquals(bar.getProgress(), 500.0, 0.0);
    assertEquals(bar.getPercent(), 0.75, 0.0);

    // Test the bounds
    bar.setMinProgress(-1000.0);
    bar.setMaxProgress(1000.0);
    bar.setProgress(-2000.0);
    assertEquals(bar.getProgress(), -1000.0, 0.0);
    assertEquals(bar.getPercent(), 0.0, 0.0);
    bar.setProgress(2000.0);
    assertEquals(bar.getProgress(), 1000.0, 0.0);
    assertEquals(bar.getPercent(), 1.0, 0.0);
    bar.setProgress(-1000.0);
    assertEquals(bar.getProgress(), -1000.0, 0.0);
    assertEquals(bar.getPercent(), 0.0, 0.0);
    bar.setProgress(1000.0);
    assertEquals(bar.getProgress(), 1000.0, 0.0);
    assertEquals(bar.getPercent(), 1.0, 0.0);
    
    // Check progress constraints
    bar.setMinProgress(-1000.0);
    bar.setMaxProgress(1000.0);
    bar.setProgress(750.0);
    assertEquals(bar.getProgress(), 750.0, 0.0);
    bar.setMaxProgress(500.0);
    assertEquals(bar.getProgress(), 500.0, 0.0);
    bar.setProgress(-750.0);
    assertEquals(bar.getProgress(), -750.0, 0.0);
    bar.setMinProgress(-500.0);
    assertEquals(bar.getProgress(), -500.0, 0.0);

    // Test the text
    bar.setMinProgress(-1000.0);
    bar.setMaxProgress(1000.0);
    bar.setProgress(500.0);
    assertEquals(bar.generateText(bar.getProgress()), "75%");
    bar.setProgress(2000.0);
    assertEquals(bar.generateText(bar.getProgress()), "100%");
    
    // Test invalid range
    bar.setMaxProgress(1000.0);
    bar.setMinProgress(1000.0);
    assertEquals(bar.getPercent(), 0.0,  0.0);
    bar.setMaxProgress(1000.0);
    bar.setMinProgress(2000.0);
    assertEquals(bar.getPercent(), 0.0,  0.0);
    
    // Test show/hide text
    bar.setTextVisible(false);
    assertEquals(DOM.getStyleAttribute(bar.getTextElement(), "display"), "none");
    bar.setTextVisible(true);
    assertEquals(DOM.getStyleAttribute(bar.getTextElement(), "display"), "");
  }
  
  /**
   * Test that the constructors correctly set default parameters.
   */
  public void testConstructors() {
    ProgressBar bar1 = new ProgressBar();
    assertEquals(bar1.getMinProgress(), 0.0, 0.0);
    assertEquals(bar1.getMaxProgress(), 100.0, 0.0);
    assertEquals(bar1.getProgress(), 0.0, 0.0);
    assertEquals(bar1.getPercent(), 0.0, 0.0);

    ProgressBar bar2 = new ProgressBar(84.0);
    assertEquals(bar2.getMinProgress(), 0.0, 0.0);
    assertEquals(bar2.getMaxProgress(), 100.0, 0.0);
    assertEquals(bar2.getProgress(), 84.0, 0.0);
    assertEquals(bar2.getPercent(), 0.84, 0.0);

    ProgressBar bar3 = new ProgressBar(-100.0, 100.0);
    assertEquals(bar3.getMinProgress(), -100.0, 0.0);
    assertEquals(bar3.getMaxProgress(), 100.0, 0.0);
    assertEquals(bar3.getProgress(), 0.0, 0.0);
    assertEquals(bar3.getPercent(), 0.50, 0.0);

    ProgressBar bar4 = new ProgressBar(-100.0, 100.0, 50.0);
    assertEquals(bar4.getMinProgress(), -100.0, 0.0);
    assertEquals(bar4.getMaxProgress(), 100.0, 0.0);
    assertEquals(bar4.getProgress(), 50.0, 0.0);
    assertEquals(bar4.getPercent(), 0.75, 0.0);
  }
  
  /**
   * Tests the various uses of TextFormatters.
   */
  public void testTextFormatter() {
    // Set a new text formatter
    ProgressBar bar = new ProgressBar() {
      protected String generateText(double curProgress) {
        return super.generateText(curProgress);
      }      
    };
    bar.setTextFormatter(new ProgressBar.TextFormatter() {
      protected String getText(ProgressBar bar, double curProgress) {
        return "PROG: " + (int) curProgress;
      }
    });
    
    // Verify the new text formatter overrides the old one
    bar.setProgress(43.123);
    assertEquals(bar.generateText(43.123), "PROG: 43");
    
    // Check text format without a text formatter
    bar.setTextFormatter(null);
    assertEquals(bar.generateText(43.123), "43%");
  }
}
