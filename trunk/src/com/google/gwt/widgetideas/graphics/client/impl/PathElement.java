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

/**
 * Simple Path String generator class.
 */
public class PathElement {

  /**
   * Path Constants.
   */
  public static final String ARC = " ar";
  public static final String CLOSE = " x";
  public static final String END = " e";
  public static final String LINETO = " l";
  public static final String MOVETO = " m";
  public static final String CUBIC = " c";

  public static String arc(float x, float y, float radius, float startAngle,
      float endAngle, boolean antiClockwise, GWTCanvasImplIE6 canvas) {

    float ar;
    float endX;
    float endY;
    float startX;
    float startY;

    float realStartAngle, realEndAngle;
    
    float[] matrix = canvas.matrix;
    VMLContext context = canvas.context;
    
    if (!antiClockwise) {
      realStartAngle = endAngle;
      realEndAngle = startAngle;
    } else {
      realStartAngle = startAngle;
      realEndAngle = endAngle;
    }

    ar = radius * 10;
    startX = (float) (x + Math.cos(realStartAngle) * ar - 5);
    startY = (float) (y + Math.sin(realStartAngle) * ar - 5);
    endX = (float) (x + Math.cos(realEndAngle) * ar - 5);
    endY = (float) (y + Math.sin(realEndAngle) * ar - 5);
    if (startX == endX && !antiClockwise) {
      startX += 0.125;
    }

    float cx = canvas.getCoordX(matrix, x, y);
    float cy = canvas.getCoordY(matrix, x, y);
    float arcX = (float) (context.arcScaleX * ar);
    float arcY = (float) (context.arcScaleY * ar);
    return ARC + (Math.floor(cx - arcX + 0.5)) + ","
        + (Math.floor(cy + arcY + 0.5)) + " "
        + (Math.floor(cx + arcX + 0.5)) + ","
        + (Math.floor(cy - arcY + 0.5)) + " "
        + canvas.getCoordX(matrix, startX, startY) + ","
        + canvas.getCoordY(matrix, startX, startY) + " "
        + canvas.getCoordX(matrix, endX, endY) + ","
        + canvas.getCoordY(matrix, endX, endY);
  }

  public static String bezierCurveTo(float c1x, float c1y, float c2x,
      float c2y, float x, float y, GWTCanvasImplIE6 canvas) {
    float[] matrix = canvas.matrix;
    return CUBIC + canvas.getCoordX(matrix, c1x, c1y) + ","
        + canvas.getCoordY(matrix, c1x, c1y) + ","
        + canvas.getCoordX(matrix, c2x, c2y) + ","
        + canvas.getCoordY(matrix, c2x, c2y) + ","
        + canvas.getCoordX(matrix, x, y) + ","
        + canvas.getCoordY(matrix, x, y);
  }

  public static String closePath() {
    return CLOSE;
  }

  public static String lineTo(float x, float y, GWTCanvasImplIE6 canvas) {
    float[] matrix = canvas.matrix;
    return LINETO + canvas.getCoordX(matrix, x, y) + ","
        + canvas.getCoordY(matrix, x, y);
  }

  public static String moveTo(float x, float y, GWTCanvasImplIE6 canvas) {
    float[] matrix = canvas.matrix;
    return MOVETO + canvas.getCoordX(matrix, x, y) + ","
        + canvas.getCoordY(matrix, x, y);
  }
}