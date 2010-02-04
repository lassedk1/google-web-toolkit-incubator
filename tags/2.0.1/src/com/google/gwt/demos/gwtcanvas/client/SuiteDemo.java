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

import com.google.gwt.core.client.Duration;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

import java.util.Date;

/**
 * Demo showcaseing a range of tests from the Mozilla Canvas Tutorial and from
 * contributions from Oliver Zoran.
 * 
 */
public class SuiteDemo extends SimpleCanvasDemo {

  class SuiteDemoControls extends Composite {
    public SuiteDemoControls() {

      Button compositeButton = new Button("Compositing", new ClickHandler() {

        public void onClick(ClickEvent event) {
          if (timer != null) {
            timer.cancel();
          }
          canvas.saveContext();
          canvas.clear();
          canvas.translate(40, 40);
          canvas.setFillStyle(new Color("#f00"));
          canvas.fillRect(75, 50, 100, 100);
          canvas.setGlobalCompositeOperation(GWTCanvas.DESTINATION_OVER);
          canvas.setFillStyle(new Color("#0f0"));
          canvas.fillRect(110, 110, 100, 100);
          canvas.setGlobalCompositeOperation(GWTCanvas.SOURCE_OVER);
          canvas.setFillStyle(new Color("#00f"));
          canvas.fillRect(40, 85, 100, 100);
          canvas.restoreContext();
        }

      });

      Button pathsButton = new Button("Paths & shapes", new ClickHandler() {

        public void onClick(ClickEvent event) {
          if (timer != null) {
            timer.cancel();
          }
          canvas.saveContext();
          canvas.clear();
          canvas.translate(30, 30);
          canvas.setFillStyle(new Color("#fff"));
          canvas.setStrokeStyle(new Color("#fff"));
          canvas.beginPath();
          canvas.moveTo(25, 25);
          canvas.lineTo(105, 25);
          canvas.lineTo(25, 105);
          canvas.closePath();
          canvas.fill();
          canvas.beginPath();
          canvas.moveTo(125, 125);
          canvas.lineTo(125, 45);
          canvas.lineTo(45, 125);
          canvas.closePath();
          canvas.stroke();
          canvas.beginPath();
          canvas.moveTo(265, 265);
          canvas.lineTo(165, 265);
          canvas.lineTo(265, 165);
          canvas.lineTo(265, 265);
          canvas.fillRect(25, 165, 100, 100);
          canvas.setFillStyle(Color.BLACK);
          canvas.fillRect(45, 185, 60, 60);
          canvas.strokeRect(50, 190, 50, 50);
          canvas.rect(165, 25, 100, 100);
          canvas.stroke();
          canvas.restoreContext();
        }

      });

      Button arcsButton = new Button("Arcs & circles", new ClickHandler() {

        public void onClick(ClickEvent event) {
          if (timer != null) {
            timer.cancel();
          }
          canvas.saveContext();
          canvas.clear();
          canvas.translate(30, 70);
          canvas.setFillStyle(new Color("#f0f"));
          canvas.setStrokeStyle(new Color("#0ff"));
          canvas.setLineWidth(2);
          for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 3; ++j) {
              canvas.beginPath();
              float x = 25 + j * 50;
              float y = 25 + i * 50;
              float radius = 20;
              float startAngle = 0;
              float endAngle = (float) (Math.PI + (Math.PI * j) / 2);
              boolean anticlockwise = (i % 2 == 0) ? false : true;
              canvas.arc(x, y, radius, startAngle, endAngle, anticlockwise);
              if (i > 1) {
                canvas.fill();
              } else {
                canvas.stroke();
              }
            }
          }
          canvas.translate(160, 20);
          canvas.setStrokeStyle(new Color("#ff0"));
          canvas.setLineWidth(4);
          canvas.beginPath();
          canvas.arc(75, 75, 50, 0, (float) (Math.PI * 2), true);
          canvas.moveTo(110, 75);
          canvas.arc(75, 75, 35, 0, (float) Math.PI, false);
          canvas.moveTo(65, 65);
          canvas.arc(60, 65, 5, 0, (float) (Math.PI * 2), true);
          canvas.moveTo(95, 65);
          canvas.arc(90, 65, 5, 0, (float) (Math.PI * 2), true);
          canvas.stroke();
          canvas.restoreContext();
        }

      });

      Button bezierButton = new Button("Bezier curves", new ClickHandler() {

        public void onClick(ClickEvent event) {
          if (timer != null) {
            timer.cancel();
          }
          canvas.saveContext();
          canvas.clear();
          canvas.translate(30, 70);
          canvas.setFillStyle(new Color("#f00"));
          canvas.setStrokeStyle(new Color("#0f0"));
          canvas.setLineWidth(2);
          canvas.beginPath();
          canvas.moveTo(75, 25);
          canvas.quadraticCurveTo(25, 25, 25, 62.5f);
          canvas.quadraticCurveTo(25, 100, 50, 100);
          canvas.quadraticCurveTo(50, 120, 30, 125);
          canvas.quadraticCurveTo(60, 120, 65, 100);
          canvas.quadraticCurveTo(125, 100, 125, 62.5f);
          canvas.quadraticCurveTo(125, 25, 75, 25);
          canvas.stroke();
          canvas.translate(140, 0);
          canvas.beginPath();
          canvas.moveTo(75, 40);
          canvas.cubicCurveTo(75, 37, 70, 25, 50, 25);
          canvas.cubicCurveTo(20, 25, 20, 62.5f, 20, 62.5f);
          canvas.cubicCurveTo(20, 80, 40, 102, 75, 120);
          canvas.cubicCurveTo(110, 102, 130, 80, 130, 62.5f);
          canvas.cubicCurveTo(130, 62.5f, 130, 25, 100, 25);
          canvas.cubicCurveTo(85, 25, 75, 37, 75, 40);
          canvas.fill();
          canvas.restoreContext();
        }

      });

      Button colorsButton = new Button("Colors", new ClickHandler() {

        public void onClick(ClickEvent event) {
          if (timer != null) {
            timer.cancel();
          }
          canvas.saveContext();
          canvas.clear();
          canvas.translate(20, 20);
          canvas.setFillStyle(new Color(255, 165, 0));
          canvas.setStrokeStyle(new Color("#FFA500"));
          for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 6; ++j) {
              canvas.setFillStyle(new Color((int) Math.floor(255 - 42.5 * i),
                  (int) Math.floor(255 - 42.5 * j), 0));
              canvas.fillRect(j * 25, i * 25, 25, 25);
            }
          }
          canvas.translate(160, 160);
          for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 6; ++j) {
              canvas.setStrokeStyle(new Color(0,
                  (int) Math.floor(255 - 42.5 * i),
                  (int) Math.floor(255 - 42.5 * j)));
              canvas.beginPath();
              canvas.arc(12.5f + j * 25, 12.5f + i * 25, 10, 0,
                  (float) (Math.PI * 2), true);
              canvas.stroke();
            }
          }
          canvas.restoreContext();
        }

      });

      Button translateButton = new Button("Translating", new ClickHandler() {

        public void onClick(ClickEvent event) {
          if (timer != null) {
            timer.cancel();
          }
          Color[] color = {
              new Color("#fc0"), new Color("#0cf"), new Color("#cf0")};
          canvas.saveContext();
          canvas.clear();
          canvas.translate(25, 25);
          for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
              canvas.setStrokeStyle(color[j]);
              canvas.saveContext();
              canvas.translate(50 + j * 100, 50 + i * 100);
              drawSpirograph(2000, 20.0f * (j + 2) / (j + 1), -8.0f * (i + 3)
                  / (i + 1), 10.0f);
              canvas.restoreContext();
            }
          }
          canvas.restoreContext();
        }

      });

      Button scaleButton = new Button("Scaling", new ClickHandler() {

        public void onClick(ClickEvent event) {
          if (timer != null) {
            timer.cancel();
          }
          canvas.saveContext();
          canvas.clear();
          canvas.translate(25, 25);
          canvas.setStrokeStyle(new Color("#fc0"));
          canvas.setLineWidth(1.5f);
          canvas.saveContext();
          canvas.translate(50, 50);
          drawSpirograph(2000, 22, 6, 5);
          canvas.translate(100, 0);
          canvas.scale(0.75f, 0.75f);
          drawSpirograph(2000, 22, 6, 5);
          canvas.translate(133.333f, 0);
          canvas.scale(0.75f, 0.75f);
          drawSpirograph(2000, 22, 6, 5);
          canvas.restoreContext();
          canvas.setStrokeStyle(new Color("#0cf"));
          canvas.saveContext();
          canvas.translate(50, 150);
          canvas.scale(1, 0.75f);
          drawSpirograph(2000, 22, 6, 5);
          canvas.translate(100, 0);
          canvas.scale(1, 0.75f);
          drawSpirograph(2000, 22, 6, 5);
          canvas.translate(100, 0);
          canvas.scale(1, 0.75f);
          drawSpirograph(2000, 22, 6, 5);
          canvas.restoreContext();
          canvas.setStrokeStyle(new Color("#cf0"));
          canvas.saveContext();
          canvas.translate(50, 250);
          canvas.scale(0.75f, 1);
          drawSpirograph(2000, 22, 6, 5);
          canvas.translate(133.333f, 0);
          canvas.scale(0.75f, 1);
          drawSpirograph(2000, 22, 6, 5);
          canvas.translate(177.777f, 0);
          canvas.scale(0.75f, 1);
          drawSpirograph(2000, 22, 6, 5);
          canvas.restoreContext();
          canvas.restoreContext();
        }

      });

      Button rotateButton = new Button("Rotating", new ClickHandler() {

        public void onClick(ClickEvent event) {
          if (timer != null) {
            timer.cancel();
          }
          canvas.saveContext();
          canvas.clear();
          canvas.translate(175, 175);
          canvas.scale(1.6f, 1.6f);
          for (int i = 1; i < 6; ++i) {
            canvas.saveContext();
            canvas.setFillStyle(new Color("rgb(" + (51 * i) + ","
                + (255 - 51 * i) + ",255)"));
            for (int j = 0; j < i * 6; ++j) {
              canvas.rotate(Math.PI * 2 / (i * 6));
              canvas.beginPath();
              canvas.arc(0, i * 12.5f, 5, 0, Math.PI * 2, true);
              canvas.fill();
            }
            canvas.restoreContext();
          }
          canvas.restoreContext();
        }

      });

      Button transparencyButton = new Button("Transparency",
          new ClickHandler() {

            public void onClick(ClickEvent event) {
              if (timer != null) {
                timer.cancel();
              }
              canvas.saveContext();
              canvas.clear();
              canvas.translate(20, 180);
              canvas.setFillStyle(new Color("rgb(255,221,0)"));
              canvas.fillRect(0, 0, 150, 37.5f);
              canvas.setFillStyle(new Color("rgb(102,204,0)"));
              canvas.fillRect(0, 37.5f, 150, 37.5f);
              canvas.setFillStyle(new Color("rgb(0,153,255)"));
              canvas.fillRect(0, 75, 150, 37.5f);
              canvas.setFillStyle(new Color("rgb(255,51,0)"));
              canvas.fillRect(0, 112.5f, 150, 37.5f);
              for (int i = 0; i < 10; ++i) {
                canvas.setFillStyle(new Color("rgba(255,255,255,"
                    + ((i + 1) / 10.0f) + ")"));
                for (int j = 0; j < 4; ++j) {
                  canvas.fillRect(5 + i * 14.0f, 5 + j * 37.5f, 14, 27.5f);
                }
              }
              canvas.restoreContext();
              canvas.saveContext();
              canvas.translate(180, 20);
              canvas.setGlobalAlpha(1.0f);
              canvas.setFillStyle(new Color("#FD0"));
              canvas.fillRect(0, 0, 75, 75);
              canvas.setFillStyle(new Color("#6C0"));
              canvas.fillRect(75, 0, 75, 75);
              canvas.setFillStyle(new Color("#09F"));
              canvas.fillRect(0, 75, 75, 75);
              canvas.setFillStyle(new Color("#F30"));
              canvas.fillRect(75, 75, 75, 75);
              canvas.setFillStyle(new Color("#FFF"));
              canvas.setGlobalAlpha(0.2f);
              for (int i = 0; i < 7; ++i) {
                canvas.beginPath();
                canvas.arc(75, 75, 10 + (10 * i), 0, (float) (Math.PI * 2),
                    true);
                canvas.fill();
              }
              canvas.restoreContext();
            }

          });

      Button linesButton = new Button("Lines", new ClickHandler() {

        public void onClick(ClickEvent event) {
          if (timer != null) {
            timer.cancel();
          }
          canvas.saveContext();
          canvas.clear();
          canvas.scale(0.9f, 0.9f);
          canvas.translate(30, 40);
          canvas.saveContext();
          canvas.setStrokeStyle(new Color("#9CFF00"));
          for (int i = 0; i < 10; ++i) {
            canvas.setLineWidth(1 + i);
            canvas.beginPath();
            canvas.moveTo(5 + i * 14, 5);
            canvas.lineTo(5 + i * 14, 140);
            canvas.stroke();
          }
          canvas.restoreContext();
          canvas.saveContext();
          canvas.translate(0, 170);
          canvas.setStrokeStyle(new Color("#09f"));
          canvas.setLineWidth(2);
          canvas.beginPath();
          canvas.moveTo(10, 10);
          canvas.lineTo(140, 10);
          canvas.moveTo(10, 140);
          canvas.lineTo(140, 140);
          canvas.stroke();
          canvas.setStrokeStyle(new Color("#9CFF00"));
          canvas.setLineWidth(15);
          canvas.setLineCap(GWTCanvas.BUTT);
          canvas.beginPath();
          canvas.moveTo(25, 10);
          canvas.lineTo(25, 140);
          canvas.stroke();
          canvas.setLineCap(GWTCanvas.ROUND);
          canvas.beginPath();
          canvas.moveTo(75, 10);
          canvas.lineTo(75, 140);
          canvas.stroke();
          canvas.setLineCap(GWTCanvas.SQUARE);
          canvas.beginPath();
          canvas.moveTo(125, 10);
          canvas.lineTo(125, 140);
          canvas.stroke();
          canvas.restoreContext();
          canvas.saveContext();
          canvas.translate(170, 0);
          canvas.setStrokeStyle(new Color("#9CFF00"));
          canvas.setLineWidth(10);
          canvas.setLineJoin(GWTCanvas.ROUND);
          canvas.beginPath();
          canvas.moveTo(-5, 5);
          canvas.lineTo(35, 45);
          canvas.lineTo(75, 5);
          canvas.lineTo(115, 45);
          canvas.lineTo(155, 5);
          canvas.stroke();
          canvas.setLineJoin(GWTCanvas.BEVEL);
          canvas.beginPath();
          canvas.moveTo(-5, 5 + 40);
          canvas.lineTo(35, 45 + 40);
          canvas.lineTo(75, 5 + 40);
          canvas.lineTo(115, 45 + 40);
          canvas.lineTo(155, 5 + 40);
          canvas.stroke();
          canvas.setLineJoin(GWTCanvas.MITER);
          canvas.beginPath();
          canvas.moveTo(-5, 5 + 80);
          canvas.lineTo(35, 45 + 80);
          canvas.lineTo(75, 5 + 80);
          canvas.lineTo(115, 45 + 80);
          canvas.lineTo(155, 5 + 80);
          canvas.stroke();
          canvas.restoreContext();
          canvas.saveContext();
          canvas.translate(170, 170);
          canvas.setStrokeStyle(new Color("#09f"));
          canvas.setLineWidth(2);
          canvas.strokeRect(-5, 50, 160, 50);
          canvas.setStrokeStyle(new Color("#9CFF00"));
          canvas.setLineWidth(10);
          canvas.setMiterLimit(10);
          canvas.beginPath();
          canvas.moveTo(0, 100);
          float dy;
          for (int i = 0; i < 19; ++i) {
            if (i % 2 == 0) {
              dy = 25.0f;
            } else {
              dy = -25.0f;
            }
            canvas.lineTo((float) (Math.pow(i, 1.5) * 2.0f), 75 + dy);
          }
          canvas.stroke();
          canvas.restoreContext();
          canvas.restoreContext();
        }

      });

      Button animtationButton = new Button("Animations", new ClickHandler() {

        public void onClick(ClickEvent event) {
          if (timer == null) {
            timer = new Timer() {
              public void run() {
                drawClock();
              }
            };
          }
          drawClock();
        }

      });

      HorizontalPanel hp = new HorizontalPanel();
      VerticalPanel vp = new VerticalPanel();
      vp.setHorizontalAlignment(HasAlignment.ALIGN_LEFT);
      vp.add(new Label("MENU"));
      vp.setSpacing(6);
      vp.add(compositeButton);
      vp.add(pathsButton);
      vp.add(arcsButton);
      vp.add(bezierButton);
      vp.add(colorsButton);
      vp.add(translateButton);
      vp.add(scaleButton);
      vp.add(rotateButton);
      vp.add(transparencyButton);
      vp.add(linesButton);
      vp.add(animtationButton);
      hp.add(vp);

      initWidget(hp);
    }
  }

  private Timer timer;

  public SuiteDemo(GWTCanvas theCanvas) {
    super(theCanvas);
    width = 350;
    height = 350;
    demoName = "Feature Suite";
  }

  @Override
  public void createControls() {
    controls = new SuiteDemoControls();
  }

  @Override
  public void drawDemo() {
    canvas.resize(width, height);
    canvas.setBackgroundColor(Color.BLACK);
  }

  @Override
  public void stopDemo() {
    canvas.setBackgroundColor(GWTCanvas.TRANSPARENT);
    if (timer != null) {
      timer.cancel();
    }
  }

  @SuppressWarnings("deprecation")
  private void drawClock() {
    int a = (int) Duration.currentTimeMillis();
    canvas.saveContext();
    canvas.clear();
    canvas.translate(175, 175);
    canvas.scale(0.8f, 0.8f);
    canvas.rotate((float) (-Math.PI / 2));

    canvas.saveContext();
    canvas.beginPath();
    canvas.setLineWidth(7);
    canvas.setStrokeStyle(new Color("#325FA2"));
    canvas.setFillStyle(new Color("#fff"));
    canvas.arc(0, 0, 142, 0, (float) (Math.PI * 2), true);
    canvas.fill();
    canvas.arc(0, 0, 142, 0, (float) (Math.PI * 2), true);
    canvas.stroke();
    canvas.restoreContext();

    canvas.setStrokeStyle(Color.BLACK);
    canvas.setFillStyle(Color.WHITE);
    canvas.setLineWidth(4);
    canvas.setLineCap("round");

    // Hour marks
    canvas.saveContext();
    for (int i = 0; i < 12; ++i) {
      canvas.beginPath();
      canvas.rotate((float) (Math.PI / 6));
      canvas.moveTo(100, 0);
      canvas.lineTo(120, 0);
      canvas.stroke();
    }
    canvas.restoreContext();

    // Minute marks
    canvas.saveContext();
    canvas.setLineWidth(2.5f);
    for (int i = 0; i < 60; ++i) {
      if (i % 5 != 0) {
        canvas.beginPath();
        canvas.moveTo(117, 0);
        canvas.lineTo(120, 0);
        canvas.stroke();
      }
      canvas.rotate((float) (Math.PI / 30));
    }
    canvas.restoreContext();

    Date date = new Date();
    int sec = date.getSeconds();
    int min = date.getMinutes();
    int hr = date.getHours();
    hr = (hr >= 12) ? hr - 12 : hr;

    canvas.setFillStyle(Color.BLACK);

    // write Hours
    canvas.saveContext();
    canvas.rotate((float) (hr * Math.PI / 6 + Math.PI / 360 * min + Math.PI
        / 21600 * sec));
    canvas.setLineWidth(7);
    canvas.beginPath();
    canvas.moveTo(-20, 0);
    canvas.lineTo(80, 0);
    canvas.stroke();
    canvas.restoreContext();

    // write Minutes
    canvas.saveContext();
    canvas.rotate((float) (Math.PI / 30 * min + Math.PI / 1800 * sec));
    canvas.setLineWidth(5);
    canvas.beginPath();
    canvas.moveTo(-28, 0);
    canvas.lineTo(112, 0);
    canvas.stroke();
    canvas.restoreContext();

    // Write seconds
    canvas.saveContext();
    canvas.rotate((float) (sec * Math.PI / 30));
    canvas.setStrokeStyle(new Color("#D40000"));
    canvas.setFillStyle(new Color("#D40000"));
    canvas.setLineWidth(3);
    canvas.beginPath();
    canvas.moveTo(-30, 0);
    canvas.lineTo(83, 0);
    canvas.stroke();
    canvas.beginPath();
    canvas.moveTo(107, 0);
    canvas.lineTo(121, 0);
    canvas.stroke();
    canvas.beginPath();
    canvas.arc(0, 0, 10, 0, (float) (Math.PI * 2), true);
    canvas.fill();
    canvas.beginPath();
    canvas.arc(95, 0, 10, 0, (float) (Math.PI * 2), true);
    canvas.stroke();
    canvas.beginPath();
    canvas.setFillStyle(new Color("#555"));
    canvas.arc(0, 0, 3, 0, (float) (Math.PI * 2), true);
    canvas.fill();
    canvas.restoreContext();

    canvas.restoreContext();
    int b = (int) Duration.currentTimeMillis() - a;
    timer.schedule(1000 - b);
  }

  private void drawSpirograph(int p, float r0, float r, float o) {
    float x1 = r0 - o;
    float y1 = 0;
    int i = 1;
    float x2;
    float y2;
    canvas.beginPath();
    canvas.moveTo(x1, y1);
    do {
      x2 = (float) ((r0 + r) * Math.cos(i * Math.PI / 72) - (r + o)
          * Math.cos(((r0 + r) / r) * (i * Math.PI / 72)));
      y2 = (float) ((r0 + r) * Math.sin(i * Math.PI / 72) - (r + o)
          * Math.sin(((r0 + r) / r) * (i * Math.PI / 72)));
      canvas.lineTo(x2, y2);
      x1 = x2;
      y1 = y2;
      ++i;
    } while (x2 != r0 - o && y2 != 0 && i < p);
    canvas.stroke();
  }
}
