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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
// import com.google.gwt.widgetideas.graphics.client.CanvasGradient;
import com.google.gwt.widgetideas.graphics.client.ImageHandle;

import java.util.Stack;

/**
 * Deferred binding implementation of GWTCanvas for IE6.
 * It is an implementation of canvas on top of VML.
 */
public class GWTCanvasImplIE6 implements GWTCanvasImpl {

  private static Element fillPrototype; 
  
  private static Element groupPrototype;
  
  private static Element imagePrototype;
  
  private static Element shapePrototype;
  
  private static Element strokePrototype; 
  
  static {
    bootStrapVML();
    initPrototypes();
  }
  
  /* 
   * Bootstrap VML namespace and Active X object 
   * Set CSS style rules for VMLRender behavior
   */
  public static native void bootStrapVML() /*-{

    $doc.namespaces.add("v","urn:schemas-microsoft-com:vml");
  
    styleObj = $doc.createStyleSheet();
    styleObj.addRule('v\\:*', "behavior: url(#default#VML);");
    
  }-*/;
  
  public static native Element cloneElement(Element elem, boolean deep) /*-{
    return elem.cloneNode(deep);
  }-*/;
  
  public static float[][] multiply3x3Matrices(float[][] m1, float[][] m2) {
    float[][] rtn = new float[3][3];
    
    for (int i = 0;i < 3;i++) {

      for (int j = 0;j < 3;j++) {
        float dotProd = 0;
        
        for (int k = 0;k < 3;k++) {
          dotProd += m1[i][k] * m2[k][j];
        }
        
        rtn[i][j] = dotProd;
      }   
    }
    
    return rtn;
  }
 
  private static void initPrototypes() {
    fillPrototype = DOM.createElement("v:fill");
    
    groupPrototype = DOM.createElement("v:group");
    
    imagePrototype = DOM.createElement("v:image");
    
    shapePrototype = DOM.createElement("v:shape");
    
    strokePrototype = DOM.createElement("v:stroke");
    
    // Initialize shape prototype
    DOM.setElementAttribute(shapePrototype, "fillColor", "none");
    DOM.setElementAttribute(shapePrototype, "filled", "false");
    DOM.setElementAttribute(shapePrototype, "strokecolor", "black");
    DOM.setElementAttribute(shapePrototype, "stroked", "true");
    DOM.setElementAttribute(shapePrototype,"strokeweight", "1");
    
    DOM.setElementAttribute(shapePrototype, "joinstyle", "miter");
    DOM.setElementAttribute(shapePrototype, "miterlimit", "1");
    DOM.setElementAttribute(shapePrototype, "endcap", "flat");
    
    DOM.setElementAttribute(shapePrototype, "coordorigin","0,0");
    DOM.setElementAttribute(shapePrototype, "coordsize", "300,150");
    // Default Style info
    DOM.setStyleAttribute(shapePrototype, "position", "absolute");
    DOM.setStyleAttribute(shapePrototype, "left", "0px");
    DOM.setStyleAttribute(shapePrototype, "top", "0px");
    DOM.setStyleAttribute(shapePrototype, "width", "300px");
    DOM.setStyleAttribute(shapePrototype, "height", "150px");    
    
    DOM.setElementAttribute(fillPrototype, "opacity", "1.0");
    DOM.setElementAttribute(strokePrototype, "opacity", "1.0");
    
    DOM.setElementAttribute(groupPrototype, "coordsize", "300,150");
    DOM.setElementAttribute(groupPrototype, "coordorigin", "0,0");
    DOM.setStyleAttribute(groupPrototype, "position", "absolute"); 
  }
  
  // VML internal coord space.
  private int coordX;
  private int coordY;
  
  // CSS pixel space
  private int cssHeight;
  private int cssWidth;
  
  private VMLContext currentContext = new VMLContext(true);
   
  private Element parentElement = null;
  
  private Stack<VMLContext> vmlContextStack = new Stack<VMLContext>();
  
  public float[] applyCurrentTransform(float x, float y) {
    float[] point = new float[2];
    float[][] currentMatrix = this.currentContext.transform;
    
    point[0] = currentMatrix[0][0] * x + currentMatrix[0][1] * y +
      currentMatrix[0][2];
    
    point[1] = currentMatrix[1][0] * x + currentMatrix[1][1] * y +
    currentMatrix[1][2];
    
    return point;
  }
  
  public void arc(float x, float y, float radius, float startAngle,
      float endAngle, boolean antiClockwise) {
    
    int left,top,right,bottom;
    float realStartAngle,realEndAngle;
    
    float[] point = applyCurrentTransform(x,y);
    
    left = (int) (point[0] - this.currentContext.scaleX * radius);
    top = (int) (point[1] + this.currentContext.scaleY * radius);
    right = (int) (point[0] + this.currentContext.scaleX * radius);
    bottom = (int) (point[1] - this.currentContext.scaleY * radius);
    
    // See if we need to reverse the radial vectors
    if (!antiClockwise) {
      realStartAngle = endAngle;
      realEndAngle = startAngle;
    } else {
      realStartAngle = startAngle;
      realEndAngle = endAngle;
    }
    
    int startX,startY,endX,endY;
    
    // Convert from polar to cartesian coords
    startX = (int) (point[0] + radius * Math.cos((double) realStartAngle));
    startY = (int) ((float) (point[1] + radius * Math.sin((double) realStartAngle)));
    
    endX = (int) ((float) (point[0] + radius * Math.cos((double) realEndAngle)));
    endY = (int) ((float) (point[1] + radius * Math.sin((double) realEndAngle)));
    
    this.currentContext.path.append(PathElement.ARC);
    this.currentContext.path.append(left);
    this.currentContext.path.append(",");
    this.currentContext.path.append(top);
    this.currentContext.path.append(",");
    this.currentContext.path.append(right);
    this.currentContext.path.append(",");
    this.currentContext.path.append(bottom);
    this.currentContext.path.append(",");
    this.currentContext.path.append(startX);
    this.currentContext.path.append(",");
    this.currentContext.path.append(startY);
    this.currentContext.path.append(",");
    this.currentContext.path.append(endX);
    this.currentContext.path.append(",");
    this.currentContext.path.append(endY);
  }

  // Reset the path
  public void beginPath() {
    this.currentContext.path = new StringBuffer();
  }

  public void clear(int width, int height) {
  
    // For now, we simply wipe the entire group clean.
    // It's a dirty hack for the most common use case which is to clear
    // the entire "canvas" frame buffer
    DOM.setInnerHTML(this.parentElement,"");
  }

  public void closePath() {
    this.currentContext.path.append(PathElement.CLOSE);
  }

  public native Element createElement() /*-{
    var p = $doc.createElement("div");
    p.style.overflow="hidden";
    this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplIE6::setParentElement(Lcom/google/gwt/user/client/Element;)(p);
    return p;
  }-*/;

  public void cubicCurveTo(float cp1x, float cp1y, float cp2x, float cp2y,
      float x, float y) {

    float[] point1 = applyCurrentTransform(cp1x,cp1y);
    float[] point2 = applyCurrentTransform(cp2x, cp2y);
    float[] p = applyCurrentTransform(x,y);
    
    this.currentContext.path.append(PathElement.CUBIC);
    this.currentContext.path.append((int) point1[0]);
    this.currentContext.path.append(",");
    this.currentContext.path.append((int) point1[1]);
    this.currentContext.path.append(",");
    this.currentContext.path.append((int) point2[0]);
    this.currentContext.path.append(",");
    this.currentContext.path.append((int) point2[1]);
    this.currentContext.path.append(",");
    this.currentContext.path.append((int) p[0]);
    this.currentContext.path.append(",");
    this.currentContext.path.append((int) p[1]);  
  }

  public void drawImage(ImageHandle img, int sourceX, int sourceY, int sourceWidth,
      int sourceHeight, int destX, int destY, int destWidth, int destHeight) {
    
    Element elem = cloneElement(imagePrototype, false);
    
    int fullWidth = img.getWidth();
    int fullHeight = img.getHeight();
    
    DOM.setElementAttribute(elem, "src", img.getUrl());
    DOM.setStyleAttribute(elem, "width", String.valueOf(destWidth) + "px");
    DOM.setStyleAttribute(elem, "height", String.valueOf(destHeight) + "px");
    DOM.setElementAttribute(elem, "cropleft", String.valueOf(sourceX / fullWidth));
    DOM.setElementAttribute(elem, "croptop", String.valueOf(sourceY / fullHeight));
    DOM.setElementAttribute(elem, "cropright", String.valueOf((fullWidth - sourceX - sourceWidth) / fullWidth));
    DOM.setElementAttribute(elem, "cropbottom", String.valueOf((fullHeight - sourceY - sourceHeight) / fullHeight));  
    
    Element group = cloneElement(groupPrototype, false);
    
    DOM.setElementAttribute(group, "coordsize", this.coordX + "," + this.coordY);
    DOM.setStyleAttribute(group, "width", String.valueOf(this.cssWidth) + "px");
    DOM.setStyleAttribute(group, "height", String.valueOf(this.cssHeight) + "px");
    
    // If we have a transformation matrix with rotation/translation/scale, we apply a filter
    if (this.currentContext.transform[0][0] != 1 || this.currentContext.transform[0][2] != 0) {
      StringBuffer filter = new StringBuffer();
   
      filter.append("M11='");
      filter.append(this.currentContext.transform[0][0]);
      filter.append("',");
      filter.append("M12='");
      filter.append(this.currentContext.transform[0][1]);
      filter.append("',");
      filter.append("M21='");
      filter.append(this.currentContext.transform[1][0]);
      filter.append("',");
      filter.append("M22='");
      filter.append(this.currentContext.transform[1][1]);
      filter.append("',");
      filter.append("Dx='");
      filter.append((int) (destX + this.currentContext.transform[0][2]));
      filter.append("',");
      filter.append("Dy='");
      filter.append((int) (destY + this.currentContext.transform[1][2]));
      filter.append("'");     
      
      // We create a padding bounding box to minimize area that filter is applied to
      float[] maxCoords = this.applyCurrentTransform((float) destX,(float) destY);
      float[] topRight = this.applyCurrentTransform((float) destX + destWidth,(float) destY);
      float[] bottomLeft = this.applyCurrentTransform((float) destX,(float) destY + destHeight);
      float[] bottomRight = this.applyCurrentTransform((float) destX + destWidth,(float) destY + destHeight);
      
      maxCoords[0] = findMax(maxCoords[0],topRight[0],bottomLeft[0],bottomRight[0]);
      maxCoords[1] = findMax(maxCoords[1],topRight[1],bottomLeft[1],bottomRight[1]);
      
      DOM.setStyleAttribute(group, "padding", "0 " + (int) maxCoords[0] + "px " + (int) maxCoords[1] + "px 0");
      DOM.setStyleAttribute(group, "filter", "progid:DXImageTransform.Microsoft.Matrix(" + filter.toString() + ", sizingmethod='clip');");
    } else {
      DOM.setStyleAttribute(group, "left", String.valueOf(destX) + "px");
      DOM.setStyleAttribute(group, "top", String.valueOf(destY) + "px");
    }
    
    DOM.appendChild(group, elem);
    DOM.appendChild(this.parentElement, group);
  }

  public void fill() {
    if (!(this.currentContext.path.length() > 0)) {
      return;
    }
    
    this.currentContext.path.append(PathElement.END);
    
    Element elem = cloneElement(shapePrototype, false);
    
    DOM.setElementAttribute(elem, "filled", "true");
    DOM.setElementAttribute(elem, "fillColor", this.currentContext.fillStyle);
    DOM.setElementAttribute(elem, "stroked", "false");
    DOM.setElementAttribute(elem, "coordsize",this.coordX + "," + this.coordY);
    
    // Default Style info
    DOM.setStyleAttribute(elem, "width", String.valueOf(this.cssWidth));
    DOM.setStyleAttribute(elem, "height", String.valueOf(this.cssHeight));
    
    DOM.setElementAttribute(elem, "path", this.currentContext.path.toString());
    
    Element fill = cloneElement(fillPrototype, false);
    DOM.setElementAttribute(fill, "opacity", String.valueOf(this.currentContext.globalAlpha));
    
    DOM.appendChild(elem, fill);
    
    DOM.appendChild(this.parentElement, elem);
  }

  public void fillRect(float startX, float startY, float width, float height) {
    
    StringBuffer oldPath = this.currentContext.path;
    this.currentContext.path = new StringBuffer();
    
    beginPath();
      moveTo(startX,startY);
      lineTo(startX,startY + height);
      lineTo(startX + width,startY + height);
      lineTo(startX + width, startY);
      closePath();
    fill();
    
    // restore the old path
    this.currentContext.path = oldPath;
  }

  public int getHeight(Element elem) {
    return Integer.parseInt(DOM.getStyleAttribute(elem, "height"));
  }

  public int getWidth(Element elem) {
    return Integer.parseInt(DOM.getStyleAttribute(elem, "width"));
  }

  public void lineTo(float x, float y) {
    if (this.currentContext.path.length() >= 0) {
      this.currentContext.path.append(PathElement.LINETO);
      // VML does not use floating point for coords
      // It uses its internal coordinate space to get sub pixel coordinates
      float[] point = applyCurrentTransform(x,y);
      
      this.currentContext.path.append((int) point[0]);
      this.currentContext.path.append(",");
      this.currentContext.path.append((int) point[1]);
    }
  }

  public void moveTo(float x, float y) {
    if (this.currentContext.path.length() >= 0) {
      this.currentContext.path.append(PathElement.MOVETO);
      // VML does not use floating point for coords
      // It uses its internal coordinate space to get sub pixel coordinates
      float[] point = applyCurrentTransform(x,y);
      this.currentContext.path.append((int) point[0]);
      this.currentContext.path.append(",");
      this.currentContext.path.append((int) point[1]);
    }
  }

  public void quadraticCurveTo(float cpx, float cpy, float x, float y) {
    if (this.currentContext.path.length() >= 0) {
      float[] control = applyCurrentTransform(cpx,cpy);
      float[] point = applyCurrentTransform(x,y);
      this.currentContext.path.append(PathElement.QUADRATIC);
      this.currentContext.path.append((int) control[0]);
      this.currentContext.path.append(",");
      this.currentContext.path.append((int) control[1]);
      this.currentContext.path.append(",");
      this.currentContext.path.append((int) point[0]);
      this.currentContext.path.append(",");
      this.currentContext.path.append((int) point[1]);     
    }
  }

  public void rect(float x, float y, float width, float height) {
    float x2,y2;
    x2 = x + width;
    y2 = y + height;
    
    // Add a rectangle to the path. compiler should inline this
    this.moveTo(x, y);
    this.lineTo(x2, y);
    this.lineTo(x2, y2);
    this.lineTo(x, y2);
    this.currentContext.path.append(PathElement.CLOSE);
  }

  public void restoreContext() {
    this.currentContext = this.vmlContextStack.pop();
  }

  public void rotate(float angle) {
     
    float cos = (float) Math.cos(-angle);
    float sin = (float) Math.sin(-angle);
    
    float[][] rotMatrix = new float[][]{ {cos,sin,0}, {-sin,cos,0}, {0,0,1} };
    this.currentContext.transform = multiply3x3Matrices(
        this.currentContext.transform, rotMatrix);
  }

  public void saveContext() {
    this.vmlContextStack.push(VMLContext.cloneContext(this.currentContext));
  }

  public void scale(float x, float y) {
    float[][] sMatrix = new float[][] { {x,0,0}, {0,y,0}, {0,0,1} };
    this.currentContext.transform = multiply3x3Matrices(
        this.currentContext.transform, sMatrix);
  }

  public void setCoordHeight(Element elem, int height) {
    this.coordY = height;
    // To be consistent with canvas, we must blank the canvas on a dimension change
    clear(0,0);
  }

  public void setCoordWidth(Element elem, int width) {
    this.coordX = width;
    // To be consistent with canvas, we must blank the canvas on a dimension change
    clear(0,0);
  }

  /*
   * TODO: Awaiting me fixing IE6 gradients...
  public void setFillStyle(CanvasGradient gradient) {
    this.currentContext.gradFillStyle = (CanvasGradientIE6) gradient;
  } */

  public  void setFillStyle(String colorStr) {
    currentContext.fillStyle = colorStr;
  }

  public void setGlobalAlpha(float alpha) {
    this.currentContext.globalAlpha = alpha; 
  }
  
  public void setLineWidth(float width) {
    this.currentContext.lineWidth = (int) (width);
  }

  public void setParentElement(Element g) {
    this.parentElement = g;
  }

  public void setPixelHeight(Element elem, int height) {
    this.cssHeight = height;
    DOM.setStyleAttribute(elem, "height", String.valueOf(height) + "px");
  }

  public void setPixelWidth(Element elem, int width) {
    this.cssWidth = width;
    DOM.setStyleAttribute(elem, "width", String.valueOf(width) + "px");
  }

  /* TODO: Awaiting me fixing IE6 gradients...
  public void setStrokeStyle(CanvasGradient gradient) {
    this.currentContext.gradStrokeStyle = (CanvasGradientIE6) gradient;
  }*/

  public void setStrokeStyle(String colorStr) {
    this.currentContext.strokeStyle = colorStr;
  }

  public void setTransform(float m11, float m12, float m21, float m22,
      float dx, float dy) {
    
    this.currentContext.transform[0][0] = m11;
    this.currentContext.transform[0][1] = m12;
    this.currentContext.transform[0][2] = dx;
    
    this.currentContext.transform[1][0] = m21;
    this.currentContext.transform[1][1] = m22;
    this.currentContext.transform[1][2] = dy;
    
    this.currentContext.transform[2][0] = 0;
    this.currentContext.transform[2][1] = 0;
    this.currentContext.transform[2][2] = 1;
  }

  public void stroke() {
    if (!(this.currentContext.path.length() > 0)) {
      return;
    }
    
    this.currentContext.path.append(PathElement.END);
    
    Element elem = cloneElement(shapePrototype, false);
    
    DOM.setElementAttribute(elem, "strokecolor", this.currentContext.strokeStyle);
    DOM.setElementAttribute(elem,"strokeweight",String.valueOf(this.currentContext.lineWidth));
    
    DOM.setElementAttribute(elem, "coordsize",this.coordX + "," + this.coordY);
    
    // Default Style info
    DOM.setStyleAttribute(elem, "width", String.valueOf(this.cssWidth));
    DOM.setStyleAttribute(elem, "height", String.valueOf(this.cssHeight));    
    DOM.setElementAttribute(elem, "path", this.currentContext.path.toString());
     
    Element stroke = cloneElement(strokePrototype, false);
    DOM.setElementAttribute(stroke, "opacity", String.valueOf(this.currentContext.globalAlpha));
    
    DOM.appendChild(elem, stroke);
    
    DOM.appendChild(this.parentElement, elem);  
  }
  
  public void strokeRect(float startX, float startY, float width, float height) {
    
    StringBuffer oldPath = this.currentContext.path;
    this.currentContext.path = new StringBuffer();
    
    beginPath();
      moveTo(startX,startY);
      lineTo(startX,startY + height);
      lineTo(startX + width,startY + height);
      lineTo(startX + width, startY);
      closePath();
    stroke();
    
    // restore the old path
    this.currentContext.path = oldPath;
  }

  public void transform(float m11, float m12, float m21, float m22, float dx,
      float dy) {
    
    float[][] tMatrix = new float[][] { {m11,m12,0}, {m21,m22,0}, {dx,dy,1} };
    this.currentContext.transform = multiply3x3Matrices(
        this.currentContext.transform, tMatrix);
  }

  public void translate(float x, float y) {
    
    float[][] tMatrix = new float[][] { {1,0,x}, {0,1,y}, {0,0,1} };
    this.currentContext.transform = multiply3x3Matrices(
        this.currentContext.transform, tMatrix);
  }

  /*
   * Convenience method so we dont need 3 calls to Math.max for drawImage
   */
  private native float findMax(float x, float y, float z, float w)/*-{
    return Math.max(x,y,z,w);
  }-*/;

}
