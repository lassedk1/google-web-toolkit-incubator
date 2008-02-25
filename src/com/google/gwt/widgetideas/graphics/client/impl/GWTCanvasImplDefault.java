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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
// import com.google.gwt.widgetideas.graphics.client.CanvasGradient;
import com.google.gwt.widgetideas.graphics.client.ImageHandle;

/**
 * Deferred binding implementation of GWTCanvas.
 */
public class GWTCanvasImplDefault implements GWTCanvasImpl {

  // package protected to suppress warning when declared private
  JavaScriptObject canvasContext = null;
  
  public native void arc(float x, float y, float radius, float startAngle,
      float endAngle, boolean antiClockwise) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).arc(x,y,radius,startAngle,endAngle,antiClockwise);
  }-*/;
  
  public native void beginPath() /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).beginPath();
  }-*/;
  
  public void clear(int width, int height) {
    clearRect(0,0,width,height);
  }
  
  public native void closePath() /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).closePath();
  }-*/;
  
  public native Element createElement() /*-{
    var e = $doc.createElement("CANVAS");
    this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::setCanvasContext(Lcom/google/gwt/core/client/JavaScriptObject;)(e.getContext('2d'));
    return e;
  }-*/;
  
  public native void cubicCurveTo(float cp1x, float cp1y, float cp2x, float cp2y,
      float x, float y) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).bezierCurveTo(cp1x,cp1y,cp2x,cp2y,x,y);
  }-*/;
  
  public native void drawImage(ImageHandle img, int sourceX, int sourceY, int sourceWidth,
      int sourceHeight, int destX, int destY, int destWidth, int destHeight) /*-{
     
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).drawImage(img,sourceX,sourceY,sourceWidth,sourceHeight,destX,destY,destWidth,destHeight);
    
  }-*/;

  public native void fill() /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).fill();
  }-*/;

  public native void fillRect(float startX, float startY, float width, float height) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).fillRect(startX,startY,width,height);
  }-*/;

  public int getHeight(Element elem) {
    return DOM.getElementPropertyInt(elem, "height");
  }

  public int getWidth(Element elem) {
    return DOM.getElementPropertyInt(elem, "width");
  }

  public native void lineTo(float x, float y) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).lineTo(x,y);
  }-*/;

  public native void moveTo(float x, float y) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).moveTo(x,y);
  }-*/;

  public native void quadraticCurveTo(float cpx, float cpy, float x, float y) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).quadraticCurveTo(cpx,cpy,x,y);
  }-*/;

  public native void rect(float x, float y, float width, float height) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).rect(x,y,width,height);
  }-*/;

  public native void restoreContext() /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).restore();
  }-*/;

  public native void rotate(float angle) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).rotate(angle);
  }-*/;

  public native void saveContext() /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).save();
  }-*/;

  public native void scale(float x, float y) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).scale(x,y);
  }-*/;

  public void setCoordHeight(Element elem, int height) {
    DOM.setElementProperty(elem, "height", String.valueOf(height));
  }

  public void setCoordWidth(Element elem, int width) {
    DOM.setElementProperty(elem,"width", String.valueOf(width));
  }

  /* TODO: Awaiting me fixing IE6 gradients...
  public native void setFillStyle(CanvasGradient gradient) /-{ 
    var gradObj = gradient.@com.google.gwt.widgetideas.graphics.client.CanvasGradient::getObject()();
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).fillStyle = gradObj; 
  }-/;
   */
   
  public native void setFillStyle(String colorStr) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).fillStyle = colorStr;
  }-*/;
  
  public native void setGlobalAlpha(float alpha) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).globalAlpha = alpha;
  }-*/;
  
  public native void setLineWidth(float width) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).lineWidth = width;
  }-*/;

  public void setPixelHeight(Element elem, int height) {
    DOM.setStyleAttribute(elem, "height", height + "px");
  }

  public void setPixelWidth(Element elem, int width) {
    DOM.setStyleAttribute(elem, "width", width + "px");
  }

  /* TODO: Awaiting me fixing IE6 gradients...
  public native void setStrokeStyle(CanvasGradient gradient) /-{
    var gradObj = gradient.@com.google.gwt.widgetideas.graphics.client.CanvasGradient::getObject()();
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).strokeStyle = gradObj; 
  }-/;
  */
  
  public native void setStrokeStyle(String colorStr) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).strokeStyle = colorStr;
  }-*/;
  
  public native void setTransform(float m11, float m12, float m21, float m22,
      float dx, float dy) /*-{
      (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).setTransform(m11,m12,m21,m22,dx,dy);
  }-*/;
  
  public native void stroke() /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).stroke();
  }-*/;

  public native void strokeRect(float startX, float startY, float width, float height) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).strokeRect(startX,startY,width,height);
  }-*/;

  public native void transform(float m11, float m12, float m21, float m22, float dx,
      float dy) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).transform(m11,m12,m21,m22,dx,dy);
  }-*/;

  public native void translate(float x, float y) /*-{
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).translate(x,y);
  }-*/;

  private native void clearRect(float startX, float startY, float width, float height) /*-{   
    (this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).clearRect(startX,startY,width,height);
  }-*/;

  // This is called from JSNI function. Ignore warning.
  private void setCanvasContext(JavaScriptObject ctx) {
    this.canvasContext = ctx;
  }

}
