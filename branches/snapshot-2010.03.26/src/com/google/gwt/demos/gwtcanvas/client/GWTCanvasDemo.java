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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

import java.util.ArrayList;

/**
 * Simple demo of the GWTCanvas Widget. 
 * 
 * Selection box at top to choose demo.
 * Single shared GWT Canvas in middle.
 * Optional control panel at bottom for demo descriptions
 * and demo UI controls
 * 
 * Entry point classes define <code>onModuleLoad()</code>.
 *
 */
public class GWTCanvasDemo implements EntryPoint {
  
  /*
   * Single shared canvas for all demos 
   */
  private GWTCanvas canvas;
  
  private SimpleCanvasDemo currentDemo;
  
  /*
   * Collection of the demos available
   */
  private ArrayList<SimpleCanvasDemo> demos = new ArrayList<SimpleCanvasDemo>();

  private HorizontalPanel layout;  

  public void onModuleLoad() {
    
    layout = new HorizontalPanel();    
    
    // Each demo will set their own dimensions, so it doesn't matter
    // what we initialize the canvas to.
    canvas = new GWTCanvas(400,400);
 
    canvas.addStyleName("gwt-canvas");
    
    // Create demos
    demos.add(new StaticDemo(canvas));
    demos.add(new LogoDemo(canvas));
    demos.add(new ParticleDemo(canvas));
    demos.add(new GradientDemo(canvas));
    demos.add(new SuiteDemo(canvas));
    
    // Add them to the selection list box
    ListBox lb = new ListBox();
    lb.setStyleName("listBox");
    
    for (int i = 0; i < demos.size(); i++) {
      lb.addItem(((SimpleCanvasDemo) demos.get(i)).getName()); 
    }
    
    lb.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        int choice = ((ListBox) event.getSource()).getSelectedIndex();
        swapDemo(demos.get(choice));
      }
    });
    
    // start off with the first demo
    currentDemo = demos.get(0);   
    
    // Add widgets to layout and RootPanel
    VerticalPanel vp = new VerticalPanel();
    vp.add(lb);
    vp.add(canvas);
    layout.add(vp);
    if (currentDemo.getControls() != null) {
      layout.add(currentDemo.getControls());
    }
    RootPanel.get().add(layout);
    currentDemo.drawDemo();
  }
  
 /*
  * Changes the current demo for the input demo
  */
  private void swapDemo(SimpleCanvasDemo newDemo) {
    currentDemo.stopDemo();
    layout.remove(currentDemo.getControls());
    currentDemo = newDemo;
    layout.add(currentDemo.getControls());
    currentDemo.drawDemo();
  }

}
