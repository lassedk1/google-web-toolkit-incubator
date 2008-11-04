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

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;

/**
 * Demo Showcasing simple image rendering, transformations 
 * and animations.
 */
public class LogoDemo extends SimpleCanvasDemo {
  
  /*
   * Controls for the logo demo.
   * We dont actually have any controls at the moment,
   * so we just initialize with an empty Vertical Panel.
   */
  class LogoDemoControls extends Composite {
    public LogoDemoControls() {
      VerticalPanel layout = new VerticalPanel();
      initWidget(layout);
    }
  } 
  
  /*
   * Reference to our ImageHandle that gets initialized
   * on the callback from ImageLoader.
   */
  private ImageElement img = null;

  private float rotation = 0.1f;

  /*
   * Animation timer.
   */
  private Timer timer;

  public LogoDemo(GWTCanvas theCanvas) {
    super(theCanvas);
    height = 400;
    width = 600;
    demoName = "GWT Logo";  
  }
  
  public void createControls() {
    this.controls = new LogoDemoControls(); 
  }
  
  public void drawDemo() {
    // The following is the same as doing
    // canvas.resize(width,height);
    canvas.setCoordSize(width,height);
    canvas.setPixelHeight(height);
    canvas.setPixelWidth(width);
    
    String[] imageUrls = new String[] {
        "logo-185x175.png"
    };   

    timer = new Timer() {

      @Override
      public void run() {
        renderingLoop();  
      }
      
    };

    if (img == null) {
    // The first time this demo gets run we need to load our images.
    // Maintain a reference to the image we load so we can use it
    // the next time the demo is selected
    ImageLoader.loadImages(imageUrls, new ImageLoader.CallBack() {
      public void onImagesLoaded(ImageElement[] imageHandles) {
        // Drawing code involving images goes here
        img = imageHandles[0];
        timer.schedule(10);        
      }
    });
    
    } else {
      // Go ahead and animate
      if (isImageLoaded(img)) {
        timer.schedule(10);
      } else {
        Window.alert("Refresh the page to reload the image.");
      }
    }
  }
  
  public void stopDemo() {
    timer.cancel();
  }
  
  private native boolean isImageLoaded(ImageElement imgElem) /*-{
    return !!imgElem.__isLoaded;
  }-*/;
  
  private void renderingLoop() {
    canvas.saveContext();
    
    canvas.clear();
    canvas.translate(300, 200);
    canvas.rotate(rotation);
    canvas.scale(.9f, .9f);
    
    canvas.drawImage(img, 0, 0); 
    rotation += 0.1;

    canvas.restoreContext();
    timer.schedule(10);
  }
}
