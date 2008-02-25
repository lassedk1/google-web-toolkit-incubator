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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

/**
 * Demo that showcases most of the GWTCanvas drawing API,
 * as well as transformations, and style settings.
 */
public class StaticDemo extends SimpleCanvasDemo {
  
  // All SimpleCanvasDemos need to set some controls
  // Even if they don't have any :).
  class StaticDemoControls extends Composite {
    public StaticDemoControls() {
      VerticalPanel layout = new VerticalPanel();
      initWidget(layout);
    }
  }
   
  public StaticDemo(GWTCanvas theCanvas) {
    super(theCanvas);
    width = 400;
    height = 400;
    demoName = "Static Scene";
  }
  
  public void createControls() {
    controls = new StaticDemoControls();
  }
  
  /*
   * Test cases derived from javascript test cases by Emil Eklund
   * and from Mozilla foundation Canvas tutorial. Please note that
   * his test cases are NOT bound by license since they were
   * originally derived from mozilla foundation test cases
   */
  public void drawDemo() {
    canvas.resize(width, height);
    
    // Changing the coordinate size will implicitly clear the canvas
    // canvas.clear();
    canvas.saveContext();
    
    canvas.setLineWidth(1);
    canvas.setFillStyle(Color.GREEN);
    canvas.fillRect(5, 5, 25, 25);
    canvas.setStrokeStyle(Color.RED);
    canvas.strokeRect(20, 20, 25, 25);
   
    canvas.beginPath();
    
    canvas.setLineWidth(1);
    
    canvas.moveTo(1,1);
    canvas.lineTo(80,80);
    canvas.lineTo(100,20);
    canvas.closePath();
    canvas.stroke();
   
    canvas.setStrokeStyle(Color.BLUE);
    canvas.setFillStyle(Color.BLACK);

    canvas.beginPath();
    canvas.moveTo(120,50);
    canvas.lineTo(150,70);
    canvas.lineTo(150,50);

    canvas.quadraticCurveTo(150, 150, 80, 80);
    canvas.cubicCurveTo(85,25,75,37,75,40);
    canvas.closePath();
    canvas.fill();

    canvas.beginPath();
    canvas.rect(180,180,80,80);
    canvas.rect(200,200,80,80);
    canvas.stroke();

    canvas.beginPath();
    canvas.arc(200, 100, 20, 0, (float) Math.PI, false);
    canvas.stroke();

    canvas.saveContext();
    canvas.translate(150, 0);

    canvas.fillRect(0,0,150,150);
    canvas.setFillStyle(new Color("#09F"));
    canvas.fillRect(15,15,120,120);
    canvas.setFillStyle(new Color("#FFF"));
    canvas.setGlobalAlpha(0.5f);
    canvas.fillRect(30,30,90,90);
    canvas.fillRect(45,45,60,60);
    canvas.fillRect(60,60,30,30);

    canvas.restoreContext();
    canvas.saveContext();
    canvas.translate(10, 140);

    canvas.setFillStyle(new Color("#FD0"));
    canvas.fillRect(0,0,75,75);
    canvas.setFillStyle(new Color("#6C0"));
    canvas.fillRect(75,0,75,75);
    canvas.setFillStyle(new Color("#09F"));
    canvas.fillRect(0,75,75,75);
    canvas.setFillStyle(new Color("#F30"));
    canvas.fillRect(75,75,75,75);
    canvas.setFillStyle(new Color("#FFF"));

    canvas.setGlobalAlpha(0.2f);

    for (int i = 0;i < 7;i++) {
      canvas.beginPath();
      canvas.arc(75, 75, 10 + (10 * i), 0, (float) Math.PI * 2, false);
      canvas.fill();
    }

    canvas.saveContext();
    canvas.setGlobalAlpha(0.8f);
    canvas.beginPath();
    canvas.arc(75,75,50,0,(float) Math.PI * 2,true); // Outer circle
    canvas.moveTo(110,75);
    canvas.arc(75,75,35,0,(float) Math.PI,false);   // Mouth
    canvas.moveTo(65,65);
    canvas.arc(60,65,5,0,(float) Math.PI * 2,true);  // Left eye
    canvas.moveTo(95,65);
    canvas.arc(90,65,5,0,(float) Math.PI * 2,true);  // Right eye
    canvas.stroke();
    canvas.restoreContext();
    
    canvas.restoreContext();

    canvas.beginPath();
    canvas.arc(200, 200, 20, 0,(float) Math.PI * 2, false);
    canvas.stroke();

    canvas.saveContext();
    canvas.setGlobalAlpha(1.0f);
    
    for (int i = 1;i < 6;i++) { // Loop through rings (from inside to out)
      canvas.saveContext();
      canvas.setFillStyle(new Color((51 * i),(255 - 51 * i),255));

      for (int j = 0;j < i * 6;j++) { // draw individual dots
        canvas.rotate((float) Math.PI * 2 / ((float) i * 6));
        canvas.beginPath();
        
        canvas.rect(0,((float) i) * 12.5f,5,5);
        canvas.fill();
      }
      
      canvas.restoreContext();
    }
   
    canvas.restoreContext();
    
    canvas.restoreContext();
  }

  public void stopDemo() {
    // This demo is not animated so this is a no-op
  }
  
}
