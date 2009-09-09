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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.Timer;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

import java.util.Date;

/**
 * Simple particle Simulation showing off some of the Path API.
 */
public class ParticleDemo extends SimpleCanvasDemo {
  
  /**
   * Model of  a single particle object in the simulation.
   */
  private class Particle {
    public boolean kill = false;
    public float xPos;
    public float xVel;
    public float yPos;
    public float yVel;
    private float gravity = 0.1f;
    private float xDampening = 0.9f;
    private float yDampening = 0.9f;  
    
    public Particle() {
      xPos =  (float) (Math.random() * width);
      yPos =  (float) (Math.random() * height);
      // Get some negative velocities
      xVel = (float) (Math.random() * 5 - 2.5);
      yVel = (float) (Math.random() * 5 - 2.5);
    }
    
    public void update() {

      // Reverse direction on boundaries
      if (xPos > width || xPos < 0) {
       
        xVel = -xVel;
        xPos += xVel;
        // suck out some energy
        xDampening = (float) Math.max(xDampening - 0.1, 0);
        xVel *= xDampening;
      }
    
      if (yPos > height || yPos < 0) {
        
        yVel = -yVel;
        yPos += yVel;
        // suck out some energy
        yDampening = (float) Math.max(yDampening - 0.1,0);
        yVel *=  yDampening;
 
        if ((yPos > height - 4) && (Math.abs(yVel) < 0.1)) {
          yPos = height;
          xVel = 0;
          gravity = 0;
          kill = true;
        }
      }
      
      xPos += xVel;
      yPos += yVel;
     
      // apply gravity
      yVel += gravity;
    }
  }
  
  /*
   * Not so much controls as feedback for benchmarking.
   */
  class ParticleDemoControls extends Composite {
    private double average = 1;
    private Label averageLabel;
    
    private double iterations = 1;
    private double refreshRate;
    private Label refreshRateLabel;
    
    private double startTime = -1;
    
    public ParticleDemoControls() {
      refreshRateLabel = new Label("");
      averageLabel = new Label("");
      
      VerticalPanel layout = new VerticalPanel();
      layout.add(refreshRateLabel);
      layout.add(averageLabel);
      
      initWidget(layout);
    }
    
    public void doBenchmark(double now) {
      if (startTime < 0) {
        startTime = now;
      } else {
        refreshRate = now - startTime;
        startTime = now;
        average = ((average * iterations) + refreshRate) / (iterations + 1);
        iterations++;
        
        refreshRateLabel.setText("Refresh Interval: " + String.valueOf(refreshRate));
        averageLabel.setText("Average Interval: " + String.valueOf(average));
      }
    }
    
    public void resetBenchmark() {
      this.average = 1;
      this.iterations = 1;
      this.startTime = -1;
    } 
  }
  
  private int numParticles = 20;
  
  private Particle[] particles;
  
  private Timer t;
  
  private boolean takeBenchmarks = false;

  public ParticleDemo(GWTCanvas theCanvas) {
    super(theCanvas);
    width = 400;
    height = 300;
    canvas = theCanvas;
    demoName = "Particle Demo";
  }

  public void createControls() {
    this.controls = new ParticleDemoControls();
  }
  
  public void drawDemo() {
    canvas.resize(width, height);

    particles = new Particle[numParticles];
  
    for (int i = 0;i < particles.length;i++) {
      particles[i] = new Particle();
    }
    canvas.saveContext();
    canvas.setLineWidth(2);
    canvas.setStrokeStyle(new Color(255,0,0));
  
    t = new Timer() {
      public void run() {
        renderingLoop();
      }
    };
    t.schedule(10);
  }
  
  public void renderingLoop() {
    canvas.clear();
  
    for (int i = 0; i < particles.length; i++) {
      if (particles[i].kill) {
        continue;
      }
      particles[i].update();

      canvas.beginPath();
      canvas.moveTo(particles[i].xPos, particles[i].yPos);
      canvas.lineTo(particles[i].xPos - particles[i].xVel,
               particles[i].yPos - particles[i].yVel);
      canvas.closePath();
      canvas.stroke();  
    }
      
    t.schedule(10);
    // take a benchmark
    if (takeBenchmarks) {
      ((ParticleDemoControls) this.controls).doBenchmark(Math.max(0,(new Date()).getTime()));
    }
  }
 
  public void stopDemo() {
    t.cancel();
    ((ParticleDemoControls) controls).resetBenchmark();
    canvas.restoreContext();
  }
}
