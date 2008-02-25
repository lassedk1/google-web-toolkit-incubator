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
 * Wrapper for the Style and Transformation state of the VMLElement Context.
 */
public class VMLContext {
  
  /**
   * Creates a copy of a 3x3 Matrix.
   * 
   * @param from
   * @return A new float[3][3] with the same contents as the input matrix 
   */
  public static float[][] clone3x3Matrix(float[][] from) {
    float[][] to = new float[3][3];
    
    to[0][0] = from[0][0];
    to[0][1] = from[0][1];
    to[0][2] = from[0][2];
    
    to[1][0] = from[1][0];
    to[1][1] = from[1][1];
    to[1][2] = from[1][2];
    
    to[2][0] = from[2][0];
    to[2][1] = from[2][1];
    to[2][2] = from[2][2];
    
    return to;
  } 
   
  /**
   * Creates a copy of a VMLContext.
   *  
   * @param from
   * @return A new VMLContext object that has the same values as
   * the input VMLContext
   */
  public static VMLContext cloneContext(VMLContext from) {
    VMLContext to =  new VMLContext();
    
    to.fillStyle = from.fillStyle;
    to.globalAlpha = from.globalAlpha;
    to.lineWidth = from.lineWidth;
    to.offsetX = from.offsetX;
    to.offsetY = from.offsetY;
    to.strokeStyle = from.strokeStyle;
    to.scaleX = from.scaleX;
    to.scaleY = from.scaleY;
    // to.gradFillStyle = from.gradFillStyle.cloneGradient();
    // to.gradStrokeStyle = from.gradStrokeStyle.cloneGradient(); 
    
    // We can't do simple assignment for objects
    to.transform = VMLContext.clone3x3Matrix(from.transform);
     
    // clone the path too
    to.path = new StringBuffer();
    to.path.append(from.path.toString());
    
    return to;
  }
  
  public String fillStyle;
  public float globalAlpha;
  public int lineWidth;
  public int offsetX;
  public int offsetY;
  
  // Concatenation of currently opened Path Elements which have not yet been applied
  // via an ending call (eg. closePath, stroke, fill, etc...)
  public StringBuffer path;
  
  // We need to know the x,y components of scale operations
  // as a convenience operation for knowing how much to
  public float scaleX,scaleY;
  
  public String strokeStyle;
  
  public float[][] transform;
  
  // TODO: Awaiting me fixing IE6 gradients...
  // If we have a gradient stroke or fill style specified, we
  // should use those instead of the string stroke/fill style
  // public CanvasGradientIE6 gradFillStyle;
  // public CanvasGradientIE6 gradStrokeStyle;
  
  /*
   * TODO: Explore adding rest of canvas state info
   *    
   * this.globalCompositeOperation = '';
   *
   * this.lineCap = '';
   *
   * this.lineJoin = '';
   *
   * this.miterLimit = '';
   *
   * this.shadowBlur = '';
   *
   * this.shadowColor = '';
   *
   * this.shadowOffsetX = '';
   *
   * this.shadowOffsetY = '';
   */
  
  public VMLContext() {
  }
  
  public VMLContext(boolean initTransform) {
    if (initTransform) {
      init();
    }
  }
  
  /**
   * Default Values.
   */
  public void init() {
    this.fillStyle = "black";
    this.globalAlpha = 1.0f;
    this.lineWidth = 1;
    this.offsetX = 0;
    this.offsetY = 0;
    this.strokeStyle = "black";
    this.scaleX = 1;
    this.scaleY = 1;
    // this.gradFillStyle = null;
    // this.gradStrokeStyle = null;
    this.transform = new float[][] { {1,0,0}, {0,1,0}, {0,0,1} };
    this.path = new StringBuffer();
  }
  
}
