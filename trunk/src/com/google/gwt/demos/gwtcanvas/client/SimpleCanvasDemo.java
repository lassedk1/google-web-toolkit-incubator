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
package com.google.gwt.demos.gwtcanvas.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

/**
 * Simple abstract class for defining a demo for
 * GWTCanvasDemo. Demos should involve a single
 * canvas only which is shared amongst all other
 * demos in the suite.
 * 
 **/
public abstract class SimpleCanvasDemo { 
 
  /**
   * Single shared canvas reference.
   * We should treat it nice and save/restore our 
   * context on entering/leaving drawDemo()
   */
  protected GWTCanvas canvas;
  
  /**
   * Demos that allow some interaction with the user
   * can add controls to the lower panel
   * by creating a Composite object.
   */
  protected Composite controls;
  
  protected String demoName;
  
  /**
   * Height that the canvas will be set to.
   */
  protected int height = 400;
  
  /**
   * Width that the canvas will be set to.
   */
  protected int width = 400;
  
  public SimpleCanvasDemo(GWTCanvas theCanvas) {
    this.canvas = theCanvas;
  }
  
  /**
   * Should set controls variable to some
   * Composite object.
   */
  public abstract void createControls();
  
  /**
   * The function that "starts" the demo. Entry point for
   * drawing code should go here.
   */
  public abstract void drawDemo();
  
  public Composite getControls() {
    if (controls == null) {
      createControls();
    }
    return controls;
  }
  
  public String getName() {
    return demoName;
  }
  
  /**
   * Performs any teardowns/object destruction that should
   * happen when a demo stops.
   */
  public abstract void stopDemo();
  
}
