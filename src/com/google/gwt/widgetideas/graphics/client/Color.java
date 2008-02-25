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
package com.google.gwt.widgetideas.graphics.client;

/**
 * Simple Wrapper specifying a color in RGB format.
 * Provides various methods for converting to String representations
 * of the specified color for easy compatibility with various APIs
 */
public class Color {
  
  /*
   * Some basic color strings that are often used for the web.
   * Compiler should optimize these out.
   */
  public static final Color GREEN = new Color("green");
  public static final Color RED = new Color("red");
  public static final Color BLUE = new Color("blue");
  public static final Color BLACK = new Color("black");
  
  private String colorStr = "";
  
  public Color(int r, int g, int b) {
    this.colorStr = "rgb(" + r + "," + g + "," + b + ")";
  }
  
  public Color(int r, int b, int g, float a) {
    this.colorStr = "rgba(" + r + "," + g + "," + b + "," + a + ")";
  }
  
  // We allow a user to supply a valid CSS3String
  // ... or Browser will not know what to do with it!
  public Color(String colorStr) {
    this.colorStr = colorStr;
  }
  
  public String toString() {
    return this.colorStr;
  }
}
