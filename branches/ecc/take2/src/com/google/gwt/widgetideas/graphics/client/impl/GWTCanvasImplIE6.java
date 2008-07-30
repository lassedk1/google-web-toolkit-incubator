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

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.widgetideas.graphics.client.CanvasGradient;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.google.gwt.widgetideas.graphics.client.JSOStack;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Deferred binding implementation of GWTCanvas for IE6. It is an implementation
 * of canvas on top of VML.
 */
public class GWTCanvasImplIE6 implements GWTCanvasImpl {

  public static final String BUTT = "flat";

  public static final String DESTINATION_OVER = "afterBegin";

  public static final String SOURCE_OVER = "beforeEnd";

  static {
    init();
  }

  public static native int doubleToFlooredInt(double val) /*-{
   return (val | 0);
  }-*/;

  private static native void init() /*-{
    if (!$doc.namespaces["v"]) {
      $doc.namespaces.add("v", "urn:schemas-microsoft-com:vml");
      $doc.createStyleSheet().cssText = "v\\:*{behavior:url(#default#VML);}";
    }
  }-*/;

  protected VMLContext context;

  protected double[] matrix;

  /**
   * This will be used for an array join. Currently a bit faster than
   * StringBuilder.append() & toString() because of the extra collections
   * overhead.
   */
  protected JSOStack<String> pathStr = JSOStack.create();

  /**
   * Stack uses preallocated arrays which makes push() slightly faster than
   * [].push() since each push is simply an indexed setter.
   */
  private Stack<VMLContext> contextStack = new Stack<VMLContext>();

  private double currentX = 0;

  private double currentY = 0;

  private Element parentElement = null;

  private int parentHeight = 0;

  private int parentWidth = 0;

  public void arc(double x, double y, double radius, double startAngle,
      double endAngle, boolean anticlockwise) {
    pathStr.push(PathElement.arc(x, y, radius, startAngle, endAngle,
        anticlockwise, this));
  }

  public void beginPath() {
    pathStr.clear();
  }

  public void clear() {
    pathStr.clear();
    DOM.setInnerHTML(parentElement, "");
  }

  public void clear(int width, int height) {
    clear();
  }

  public void closePath() {
    pathStr.push(PathElement.closePath());
  }

  public Element createElement() {
    context = new VMLContext();
    matrix = context.matrix;
    return createParentElement();
  }

  public Element createParentElement() {
    parentElement = DOM.createElement("div");
    DOM.setStyleAttribute(parentElement, "overflow", "hidden");
    return parentElement;
  }

  public void cubicCurveTo(double cp1x, double cp1y, double cp2x, double cp2y,
      double x, double y) {
    pathStr.push(PathElement.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y, this));
    currentX = x;
    currentY = y;
  }

  public void drawImage(ImageElement img, double sourceX, double sourceY,
      double sourceWidth, double sourceHeight, double destX, double destY,
      double destWidth, double destHeight) {

    double fullWidth = img.getWidth();
    double fullHeight = img.getHeight();

    JSOStack<String> vmlStr = JSOStack.getScratchArray();

    vmlStr.push("<v:group style=\"position:absolute;width:10;height:10;");
    double dX = getCoordX(matrix, destX, destY);
    double dY = getCoordY(matrix, destX, destY);

    // If we have a transformation matrix with rotation/scale, we
    // apply a filter
    if (context.matrix[0] != 1 || context.matrix[1] != 0) {

      // We create a padding bounding box to prevent clipping.
      vmlStr.push("padding-right:");
      vmlStr.push(parentWidth + "px;");
      vmlStr.push("padding-bottom:");
      vmlStr.push(parentHeight + "px;");
      vmlStr.push("filter:progid:DXImageTransform.Microsoft.Matrix(M11='");
      vmlStr.push("" + matrix[0]);
      vmlStr.push("',");
      vmlStr.push("M12='");
      vmlStr.push("" + matrix[1]);
      vmlStr.push("',");
      vmlStr.push("M21='");
      vmlStr.push("" + matrix[3]);
      vmlStr.push("',");
      vmlStr.push("M22='");
      vmlStr.push("" + matrix[4]);
      vmlStr.push("',");
      vmlStr.push("Dx='");
      vmlStr.push("" + Math.floor(((dX / 10))));
      vmlStr.push("',");
      vmlStr.push("Dy='");
      vmlStr.push("" + Math.floor(((dY / 10))));
      vmlStr.push("', SizingMethod='clip');");

    } else {
      vmlStr.push("left:");
      vmlStr.push((int) dX / 10 + "px;");
      vmlStr.push("top:");
      vmlStr.push((int) dY / 10 + "px");
    }

    vmlStr.push("\" coordsize=\"100,100\" coordorigin=\"0,0\"><v:image src=\"");
    vmlStr.push(img.getSrc());
    vmlStr.push("\" style=\"");

    vmlStr.push("width:");
    vmlStr.push(String.valueOf((int) (destWidth * 10)));
    vmlStr.push(";height:");
    vmlStr.push(String.valueOf((int) (destHeight * 10)));
    vmlStr.push(";\" cropleft=\"");
    vmlStr.push(String.valueOf(sourceX / fullWidth));
    vmlStr.push("\" croptop=\"");
    vmlStr.push(String.valueOf(sourceY / fullHeight));
    vmlStr.push("\" cropright=\"");
    vmlStr.push(String.valueOf((fullWidth - sourceX - sourceWidth) / fullWidth));
    vmlStr.push("\" cropbottom=\"");
    vmlStr.push(String.valueOf((fullHeight - sourceY - sourceHeight)
        / fullHeight));
    vmlStr.push("\"/></v:group>");

    insert("BeforeEnd", vmlStr.join());
  }

  public void fill() {
    if (pathStr.isEmpty()) {
      return;
    }
    JSOStack<String> shapeStr = JSOStack.getScratchArray();
    shapeStr.push("<v:shape style=\"position:absolute;width:10;height:10;\" coordsize=\"100,100\" fillcolor=\"");
    shapeStr.push(context.fillStyle);
    shapeStr.push("\" stroked=\"f\" path=\"");

    shapeStr.push(pathStr.join());

    shapeStr.push(" e\"><v:fill opacity=\"");
    shapeStr.push("" + context.globalAlpha * context.fillAlpha);

    if (context.fillGradient != null
        && context.fillGradient.colorStops.size() > 0) {
      ArrayList<ColorStop> colorStops = context.fillGradient.colorStops;

      shapeStr.push("\" color=\"");
      shapeStr.push(colorStops.get(0).color.toString());
      shapeStr.push("\" color2=\"");
      shapeStr.push(colorStops.get(colorStops.size() - 1).color.toString());
      shapeStr.push("\" type=\"");
      shapeStr.push(context.fillGradient.type);

      double minX = pathStr.getMinCoordX();
      double maxX = pathStr.getMaxCoordX();
      double minY = pathStr.getMinCoordY();
      double maxY = pathStr.getMaxCoordY();

      double dx = maxX - minX;
      double dy = maxY - minY;

      double fillLength = Math.sqrt((dx * dx) + (dy * dy));
      double gradLength = context.fillGradient.length;

      // Now add all the color stops
      String colors = "";
      for (int i = 1; i < colorStops.size() - 1; i++) {
        ColorStop cs = colorStops.get(i);
        double stopPosn = cs.offset * gradLength;
        // /(Math.min(((stopPosn / fillLength) * 100), 100))
        colors += 100 - (int) (((stopPosn / fillLength) * 100)) + "% "
            + cs.color.toString() + ",";
        if (stopPosn > fillLength) {
          break;
        }
      }
      shapeStr.push("\" colors=\"");
      // shapeStr.push(colors);
      shapeStr.push("50% white,51% #0f0,100% #fff,");
      shapeStr.push("\" angle=\"");
      shapeStr.push(/* context.fillGradient.angle */"180" + "");
    }

    shapeStr.push("\"></v:fill></v:shape>");
    String daStr = shapeStr.join();
    // Window.alert(daStr);
    insert(context.globalCompositeOperation, daStr);
  }

  public void fillRect(double x, double y, double w, double h) {
    w += x;
    h += y;
    beginPath();
    moveTo(x, y);
    lineTo(x, h);
    lineTo(w, h);
    lineTo(w, y);
    closePath();
    fill();
    pathStr.clear();
  }

  public VMLContext getContext() {
    return context;
  }

  public int getCoordX(double[] matrix, double x, double y) {
    int coordX = doubleToFlooredInt(Math.floor(10 * (matrix[0] * x + matrix[1]
        * y + matrix[2]) - 4.5f));
    // record current point to derive bounding box of current open path.
    pathStr.logCoordX(coordX / 10);
    return coordX;
  }

  public int getCoordY(double[] matrix, double x, double y) {
    int coordY = doubleToFlooredInt(Math.floor(10 * (matrix[3] * x + matrix[4]
        * y + matrix[5]) - 4.5f));
    // record current point to derive bounding box of current open path.
    pathStr.logCoordY(coordY / 10);
    return coordY;
  }

  public String getFillStyle() {
    return context.fillStyle;
  }

  public double getGlobalAlpha() {
    return context.globalAlpha;
  }

  public String getGlobalCompositeOperation() {
    if (context.globalCompositeOperation == GWTCanvasImplIE6.DESTINATION_OVER) {
      return GWTCanvas.DESTINATION_OVER;
    } else {
      return GWTCanvas.SOURCE_OVER;
    }
  }

  public String getLineCap() {
    if (context.lineCap == GWTCanvasImplIE6.BUTT) {
      return GWTCanvas.BUTT;
    }
    return context.lineCap;
  }

  public String getLineJoin() {
    return context.lineJoin;
  }

  public double getLineWidth() {
    return context.lineWidth;
  }

  public double getMiterLimit() {
    return context.miterLimit;
  }

  public String getStrokeStyle() {
    return context.strokeStyle;
  }

  public void lineTo(double x, double y) {
    pathStr.push(PathElement.lineTo(x, y, this));
    currentX = x;
    currentY = y;
  }

  public void moveTo(double x, double y) {
    pathStr.push(PathElement.moveTo(x, y, this));
    currentX = x;
    currentY = y;
  }

  public void quadraticCurveTo(double cpx, double cpy, double x, double y) {
    double cp1x = (currentX + 2.0 / 3.0 * (cpx - currentX));
    double cp1y = (currentY + 2.0 / 3.0 * (cpy - currentY));
    double cp2x = (cp1x + (x - currentX) / 3.0);
    double cp2y = (cp1y + (y - currentY) / 3.0);
    pathStr.push(PathElement.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y, this));
    currentX = x;
    currentY = y;
  }

  public void rect(double x, double y, double w, double h) {
    pathStr.push(PathElement.moveTo(x, y, this));
    pathStr.push(PathElement.lineTo(x + w, y, this));
    pathStr.push(PathElement.lineTo(x + w, y + h, this));
    pathStr.push(PathElement.lineTo(x, y + h, this));
    pathStr.push(PathElement.closePath());
    currentX = x;
    currentY = y + h;
  }

  public void restoreContext() {
    if (!contextStack.isEmpty()) {
      context = contextStack.pop();
      matrix = context.matrix;
    }
  }

  public void rotate(double angle) {
    double s = Math.sin(-angle);
    double c = Math.cos(-angle);
    double a = matrix[0];
    double b = matrix[1];
    matrix[0] = a * c - (b * s);
    matrix[1] = a * s + b * c;
    a = matrix[3];
    b = matrix[4];
    matrix[3] = a * c - (b * s);
    matrix[4] = a * s + b * c;
  }

  public void saveContext() {
    contextStack.push(context);
    context = new VMLContext(context);
    matrix = context.matrix;
  }

  public void scale(double x, double y) {
    context.arcScaleX *= x;
    context.arcScaleY *= y;
    matrix[0] *= x;
    matrix[1] *= y;
    matrix[3] *= x;
    matrix[4] *= y;
  }

  public void setBackgroundColor(Element element, String color) {
    DOM.setStyleAttribute(element, "backgroundColor", color);
  }

  public void setCoordHeight(Element elem, int height) {
    DOM.setElementAttribute(elem, "width", String.valueOf(height));
    clear(0, 0);
  }

  public void setCoordWidth(Element elem, int width) {
    DOM.setElementAttribute(elem, "width", String.valueOf(width));
    clear(0, 0);
  }

  public void setCurrentX(double currentX) {
    this.currentX = currentX;
  }

  public void setCurrentY(double currentY) {
    this.currentY = currentY;
  }

  public void setFillStyle(CanvasGradient gradient) {
    context.fillGradient = (CanvasGradientImplIE6) gradient;
  }

  public void setFillStyle(String fillStyle) {
    fillStyle = fillStyle.trim();
    if (fillStyle.startsWith("rgba(")) {
      int end = fillStyle.indexOf(")", 12);
      if (end > -1) {
        String[] guts = fillStyle.substring(5, end).split(",");
        if (guts.length == 4) {
          context.fillAlpha = Double.parseDouble(guts[3]);
          context.fillStyle = "rgb(" + guts[0] + "," + guts[1] + "," + guts[2]
              + ")";
        }
      }
    } else {
      context.fillAlpha = 1;
      context.fillStyle = fillStyle;
    }
  }

  public void setGlobalAlpha(double globalAlpha) {
    context.globalAlpha = globalAlpha;
  }

  public void setGlobalCompositeOperation(String gco) {
    gco = gco.trim();
    if (gco.equalsIgnoreCase(GWTCanvas.SOURCE_OVER)) {
      context.globalCompositeOperation = GWTCanvasImplIE6.SOURCE_OVER;
    } else if (gco.equalsIgnoreCase(GWTCanvas.DESTINATION_OVER)) {
      context.globalCompositeOperation = GWTCanvasImplIE6.DESTINATION_OVER;
    }
  }

  public void setLineCap(String lineCap) {
    if (lineCap.trim().equalsIgnoreCase(GWTCanvas.BUTT)) {
      context.lineCap = GWTCanvasImplIE6.BUTT;
    } else {
      context.lineCap = lineCap;
    }
  }

  public void setLineJoin(String lineJoin) {
    context.lineJoin = lineJoin;
  }

  public void setLineWidth(double lineWidth) {
    context.lineWidth = lineWidth;
  }

  public void setMiterLimit(double miterLimit) {
    context.miterLimit = miterLimit;
  }

  public void setParentElement(Element g) {
    this.parentElement = g;
  }

  public void setPixelHeight(Element elem, int height) {
    DOM.setStyleAttribute(elem, "height", String.valueOf(height) + "px");
    parentHeight = height;
  }

  public void setPixelWidth(Element elem, int width) {
    DOM.setStyleAttribute(elem, "width", String.valueOf(width) + "px");
    parentWidth = width;
  }

  public void setStrokeStyle(CanvasGradient gradient) {
    context.strokeGradient = (CanvasGradientImplIE6) gradient;
  }

  public void setStrokeStyle(String strokeStyle) {
    strokeStyle = strokeStyle.trim();
    if (strokeStyle.startsWith("rgba(")) {
      int end = strokeStyle.indexOf(")", 12);
      if (end > -1) {
        String[] guts = strokeStyle.substring(5, end).split(",");
        if (guts.length == 4) {
          context.strokeAlpha = Double.parseDouble(guts[3]);
          context.strokeStyle = "rgb(" + guts[0] + "," + guts[1] + ","
              + guts[2] + ")";
        }
      }
    } else {
      context.strokeAlpha = 1;
      context.strokeStyle = strokeStyle;
    }
  }

  public void stroke() {
    if (pathStr.isEmpty()) {
      return;
    }
    JSOStack<String> shapeStr = JSOStack.getScratchArray();
    shapeStr.push("<v:shape style=\"position:absolute;width:10;height:10;\" coordsize=\"100,100\" filled=\"f\" strokecolor=\"");
    shapeStr.push(context.strokeStyle);
    shapeStr.push("\" strokeweight=\"");
    shapeStr.push("" + context.lineWidth);
    shapeStr.push("px\" path=\"");

    shapeStr.push(pathStr.join());

    shapeStr.push(" e\"><v:stroke opacity=\"");
    shapeStr.push("" + context.globalAlpha * context.strokeAlpha);
    shapeStr.push("\" miterlimit=\"");
    shapeStr.push("" + context.miterLimit);
    shapeStr.push("\" joinstyle=\"");
    shapeStr.push(context.lineJoin);
    shapeStr.push("\" endcap=\"");
    shapeStr.push(context.lineCap);

    shapeStr.push("\"></v:stroke></v:shape>");
    insert(context.globalCompositeOperation, shapeStr.join());
  }

  public void strokeRect(double x, double y, double w, double h) {
    w += x;
    h += y;
    beginPath();
    moveTo(x, y);
    lineTo(x, h);
    lineTo(w, h);
    lineTo(w, y);
    closePath();
    stroke();
    pathStr.clear();
  }

  public void transform(double m11, double m12, double m21, double m22,
      double dx, double dy) {
    double a = matrix[0];
    double b = matrix[1];
    matrix[0] = a * m11 + b * m21;
    matrix[1] = a * m12 + b * m22;
    matrix[2] += a * dx + b * dy;
    a = matrix[3];
    b = matrix[4];
    matrix[3] = a * m11 + b * m21;
    matrix[4] = a * m12 + b * m22;
    matrix[5] += a * dx + b * dy;
  }

  public void translate(double x, double y) {
    matrix[2] += matrix[0] * x + matrix[1] * y;
    matrix[5] += matrix[3] * x + matrix[4] * y;
  }

  private native void insert(String gco, String html) /*-{
    this.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplIE6::parentElement.insertAdjacentHTML(gco, html);
  }-*/;
}
