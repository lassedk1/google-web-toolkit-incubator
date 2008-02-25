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
package com.google.gwt.widgetideas.graphics.client.impl;

import com.google.gwt.user.client.Element;
// import com.google.gwt.widgetideas.graphics.client.CanvasGradient;
import com.google.gwt.widgetideas.graphics.client.ImageHandle;

/**
 * Common interface for all Deferred binding implementations of 
 * GWTCanvas.
 */
public interface GWTCanvasImpl {
  
  void arc(float x, float y, float radius, float startAngle, 
      float endAngle, boolean antiClockwise);

  void beginPath();
  
  void clear(int width, int height);
  
  void closePath();
  
  Element createElement();
  
  void cubicCurveTo(float cp1x, float cp1y, float cp2x, float cp2y, 
      float x, float y);
  
  void drawImage(ImageHandle img, int sourceX, int sourceY, int sourceWidth,
      int sourceHeight, int destX, int destY, int destWidth, int destHeight);
  
  void fill();
  
  void fillRect(float startX, float startY, float width, float height);
 
  int getHeight(Element elem);
  
  int getWidth(Element elem);
  
  void lineTo(float x, float y);
  
  void moveTo(float x, float y);
  
  void quadraticCurveTo(float cpx, float cpy, float x, float y);
  
  void rect(float x, float y, float width, float height);
  
  void restoreContext();
  
  void rotate(float angle);
  
  void saveContext();
  
  void scale(float x, float y);
  
  void setCoordHeight(Element elem, int height);
  
  void setCoordWidth(Element elem, int width);
  
  void setFillStyle(String colorStr);
  
  // void setFillStyle(CanvasGradient gradient);
  
  void setGlobalAlpha(float alpha);
  
  void setLineWidth(float width);
  
  void setPixelHeight(Element elem, int height);
  
  void setPixelWidth(Element elem, int width);
  
  void setStrokeStyle(String colorStr);
  
  // void setStrokeStyle(CanvasGradient gradient);
  
  void setTransform(float m11, float m12, float m21, float m22, 
      float dx, float dy);
  
  void stroke();
  
  void strokeRect(float startX, float startY, float width, float height);
  
  void transform(float m11, float m12, float m21,float m22, 
      float dx, float dy);
  
  void translate(float x, float y);

}
