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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.graphics.client.CanvasGradient;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

/**
 * Simple Demo of gradient fills and strokes.
 */
public class GradientDemo extends SimpleCanvasDemo {

  class GradientDemoControls extends Composite {
    public GradientDemoControls() {
      VerticalPanel layout = new VerticalPanel();
      layout.add(new HTML(
          "<b style=\"color:#f00;\">Gradients currently not working correctly in IE. Contributor assistance welcome :).</b>"));
      initWidget(layout);
    }
  }

  public GradientDemo(GWTCanvas theCanvas) {
    super(theCanvas);
    width = 400;
    height = 400;
    demoName = "Gradients";
  }

  public void createControls() {
    controls = new GradientDemoControls();
  }

  public void drawDemo() {
    canvas.resize(width, height);

    // DRAW SOME LINEAR GRADIENTS
    // Demos ported from Mozilla Development Center Canvas Tutorial
    canvas.saveContext();

    // Create gradients
    CanvasGradient lingrad = canvas.createLinearGradient(0, 0, 0, 150);
    lingrad.addColorStop(0.0f, new Color("#00ABEB"));
    lingrad.addColorStop(0.5f, new Color("#fff"));
    lingrad.addColorStop(0.5f, new Color("#26C000"));
    lingrad.addColorStop(1, new Color("#fff"));

    CanvasGradient lingrad2 = canvas.createLinearGradient(0, 50, 0, 95);
    lingrad2.addColorStop(0.5f, new Color("#000"));
    lingrad2.addColorStop(1, new Color("rgba(0,0,0,0)"));

    // assign gradients to fill and stroke styles
    canvas.setFillStyle(lingrad);
    canvas.setStrokeStyle(lingrad2);

    // draw shapes
    canvas.fillRect(10, 10, 130, 130);
    canvas.strokeRect(50, 50, 50, 50);

    canvas.restoreContext();

    // DRAW SOME RADIAL GRADIENTS
    // Demos ported from Mozilla Development Center Canvas Tutorial
    canvas.saveContext();
    canvas.translate(150, 150);

    CanvasGradient radgrad = canvas.createRadialGradient(45, 45, 10, 52, 50, 30);
    radgrad.addColorStop(0, new Color("#A7D30C"));
    radgrad.addColorStop(0.9f, new Color("#019F62"));
    radgrad.addColorStop(1, new Color("rgba(1,159,98,0)"));

    CanvasGradient radgrad2 = canvas.createRadialGradient(105, 105, 20, 112,
        120, 50);
    radgrad2.addColorStop(0, new Color("#FF5F98"));
    radgrad2.addColorStop(0.75f, new Color("#FF0188"));
    radgrad2.addColorStop(1, new Color("rgba(255,1,136,0)"));

    CanvasGradient radgrad3 = canvas.createRadialGradient(95, 15, 15, 102, 20,
        40);
    radgrad3.addColorStop(0, new Color("#00C9FF"));
    radgrad3.addColorStop(0.8f, new Color("#00B5E2"));
    radgrad3.addColorStop(1, new Color("rgba(0,201,255,0)"));

    CanvasGradient radgrad4 = canvas.createRadialGradient(0, 150, 50, 0, 140,
        90);
    radgrad4.addColorStop(0, new Color("#F4F201"));
    radgrad4.addColorStop(0.8f, new Color("#E4C700"));
    radgrad4.addColorStop(1, new Color("rgba(228,199,0,0)"));

    // draw shapes
    canvas.setFillStyle(radgrad4);
    canvas.fillRect(0, 0, 150, 150);
    canvas.setFillStyle(radgrad3);
    canvas.fillRect(0, 0, 150, 150);
    canvas.setFillStyle(radgrad2);
    canvas.fillRect(0, 0, 150, 150);
    canvas.setFillStyle(radgrad);
    canvas.fillRect(0, 0, 150, 150);

    canvas.restoreContext();
  }

  public void stopDemo() {
    // no-op
  }
}
