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
import com.google.gwt.widgetideas.graphics.client.ImageHandle;

/**
 * Common interface for all Deferred binding implementations of 
 * GWTCanvas.
 */
public interface GWTCanvasImpl {
  
  public void arc(float x, float y, float radius, float startAngle, 
      float endAngle, boolean antiClockwise);

  public void beginPath();
  
  public void clear(int width, int height);
  
  public void closePath();
  
  public Element createElement();
  
  public void cubicCurveTo(float cp1x, float cp1y, float cp2x, float cp2y, 
      float x, float y);
  
  public void drawImage(ImageHandle img, int sourceX, int sourceY, int sourceWidth,
      int sourceHeight, int destX, int destY, int destWidth, int destHeight);
  
  public void fill();
  
  public void fillRect(float startX, float startY, float width, float height);
 
  public int getHeight(Element elem);
  
  public int getWidth(Element elem);
  
  public void lineTo(float x, float y);
  
  public void moveTo(float x, float y);
  
  public void quadraticCurveTo(float cpx, float cpy, float x, float y);
  
  public void rect(float x, float y, float width, float height);
  
  public void restoreContext();
  
  public void rotate(float angle);
  
  public void saveContext();
  
  public void scale(float x, float y);
  
  public void setCoordHeight(Element elem, int height);
  
  public void setCoordWidth(Element elem, int width);
  
  public void setFillStyle(String colorStr);
  
  public void setGlobalAlpha(float alpha);
  
  public void setLineWidth(float width);
  
  public void setPixelHeight(Element elem, int height);
  
  public void setPixelWidth(Element elem, int width);
  
  public void setStrokeStyle(String colorStr);
  
  public void setTransform(float m11, float m12, float m21, float m22, 
      float dx, float dy);
  
  public void stroke();
  
  public void strokeRect(float startX, float startY, float width, float height);
  
  public void transform(float m11, float m12, float m21,float m22, 
      float dx, float dy);
  
  public void translate(float x, float y);

}
