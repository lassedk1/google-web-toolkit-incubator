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

package com.google.gwt.widgetideas.graphics.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImpl;

/**
 * 2D Graphics API.
 * API mimicks functionality found in the Javascript canvas API (see 
 * <a href="http://developer.mozilla.org/en/docs/Canvas_tutorial">canvas tutorial</a>).
 * 
 * <p>Performance may scale differently for IE than for browsers with a native
 * canvas implementation. Sub-pixel precision is supported where possible.</p>
 */
public class GWTCanvas extends Widget {
  
  private final GWTCanvasImpl impl = (GWTCanvasImpl) GWT.create(GWTCanvasImpl.class);
  // private final GradientFactory gradientFactoryImpl = (GradientFactory) GWT.create(GradientFactory.class);
  
  private int coordHeight = 0;
  private int coordWidth = 0;
  
  /**
   * Creates a GWTCanvas element.
   * Element type depends on deferred binding. Default is CANVAS HTML5 DOM element.
   * In the case of IE it should be VML.
   * 
   * <p>Screen size of canvas in pixels defaults to <b>300x150</b>
   * pixels.</p>
   */
  public GWTCanvas() {
    setElement(impl.createElement());
    setPixelWidth(300);
    setPixelHeight(150);
    
    setCoordSize(300,150);
  }
  
  /**
   * Creates a GWTCanvas element.
   * Element type depends on deferred binding. Default is CANVAS HTML5 DOM element.
   * In the case of IE it should be VML.
   * 
   * <p>Screen size of canvas in pixels defaults to the coordinate space
   * dimensions for this constructor.</p>
   * 
   * @param coordX the size of the coordinate space in the x direction
   * @param coordY the size of the coordinate space in the y direction
   */
  public GWTCanvas(int coordX, int coordY) {
    setElement(impl.createElement());
    setPixelWidth(coordX);
    setPixelHeight(coordY);
    
    setCoordSize(coordX,coordY);
  }

  /**
   * Creates a GWTCanvas element.
   * Element type depends on deferred binding. Default is CANVAS HTML5 DOM element.
   * In the case of IE it should be VML.
   * 
   * <p>Different coordinate spaces and pixel spaces will cause aliased scaling.
   * Use <code>scale(float,float)</code> and consistent coordinate and pixel
   * spaces for better results.</p>
   * 
   * @param coordX the size of the coordinate space in the x direction
   * @param coordY the size of the coordinate space in the y direction
   * @param pixelX the CSS width in pixels of the canvas element
   * @param pixelY the CSS height in pixels of the canvas element
   */
  public GWTCanvas(int coordX, int coordY, int pixelX, int pixelY) {
    setElement(impl.createElement());
    setPixelWidth(pixelX);
    setPixelHeight(pixelY);
    
    setCoordSize(coordX, coordY);
  }
  
  /**
   * Draws an arc. If the context has a non-empty path, then the method must add a 
   * straight line from the last point in the path to the start point of the arc.
   * 
   * @param x center X coordinate
   * @param y center Y coordinate
   * @param radius radius of drawn arc
   * @param startAngle angle measured from positive X axis to start of arc CW
   * @param endAngle angle measured from positive X axis to end of arc CW
   * @param antiClockwise direction that the arc line is drawn
   */
  public void arc(float x, float y, float radius, float startAngle, float endAngle, boolean antiClockwise) {
    impl.arc(x, y, radius, startAngle, endAngle, antiClockwise);
  }

  /**
   * Erases the current path and prepares it for a new path.
   */
  public void beginPath() {
    impl.beginPath();
  }

  /**
   * Clears the entire canvas.
   */
  public void clear() {
    // we used local references instead of looking up the attributes
    // on the DOM element
    impl.clear(coordWidth,coordHeight);
  }

  /**
   * Closes the current path. "Closing" simply means that a line is drawn
   * from the last element in the path back to the first.
   */
  public void closePath() {
    impl.closePath();
  }
  
  /*
   * TODO: Awaiting me fixing IE6 gradients...
   * Creates a LinearGradient Object for use as a fill or stroke style.
   * 
   * @param x0 x coord of start point of gradient
   * @param y0 y coord of start point of gradient
   * @param x1 x coord of end point of gradient
   * @param y1 y coord of end point of gradient
   * @return returns the CanvasGradient
   *
  public CanvasGradient createLinearGradient(float x0, float y0, float x1, float y1) {
     return gradientFactoryImpl.createLinearGradient(x0, y0, x1, y1, getElement());
  } */
   
  /*
   * TODO: Awaiting me fixing IE6 gradients...
   * Creates a RadialGradient Object for use as a fill or stroke style.
   * 
   * @param x0 x coord of origin of start circle
   * @param y0 y coord of origin of start circle
   * @param r0 radius of start circle
   * @param x1 x coord of origin of end circle
   * @param y1 y coord of origin of end circle
   * @param r1 radius of the end circle
   * @return returns the CanvasGradient
   *
  public CanvasGradient createRadialGradient(float x0, float y0, float r0, float x1, float y1, float r1) {
     return gradientFactoryImpl.createRadialGradient(x0, y0, r0, x1, y1, r1, getElement());
  } */
  
  /**
   *
   * Does nothing if the context's path is empty. Otherwise, it connects 
   * the last point in the path to the given point <b>(x, y)</b> using a cubic BŽzier 
   * curve with control points <b>(cp1x, cp1y)</b> and <b>(cp2x, cp2y)</b>. Then, it must add the 
   * point <b>(x, y)</b> to the path.
   * 
   * This function corresponds to the <code>bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y)</code> 
   * method in canvas element Javascript API.
   *
   * @param cp1x x coord of first Control Point
   * @param cp1y y coord of first Control Point
   * @param cp2x x coord of second Control Point
   * @param cp2y y coord of second Control Point
   * @param x x coord of point
   * @param y x coord of point
   */
  public void cubicCurveTo(float cp1x, float cp1y, float cp2x, float cp2y, float x, float y) {
    impl.cubicCurveTo(cp1x, cp1y, cp2x, cp2y, x, y);
  }

  /**
   * Draws an input image to a specified position on the canvas.
   * Size defaults to the default dimensions of the image.
   *
   * @param img the image to be drawn
   * @param offsetX x coord of the top left corner in the destination space 
   * @param offsetY y coord of the top left corner in the destination space
   */
  public void drawImage(ImageHandle img, int offsetX, int offsetY) {
    drawImage(img,offsetX,offsetY,img.getWidth(),img.getHeight());
  }

  /**
   * Draws an input image at a given position on the canvas.
   * Resizes image according to specified width and height.
   * 
   * <p>We recommend that the pixel and coordinate spaces be the same to
   * provide consistent positioning and scaling results</p>
   *
   * @param img The image to be drawn
   * @param offsetX x coord of the top left corner in the destination space
   * @param offsetY y coord of the top left corner in the destination space
   * @param width the size of the image in the destination space
   * @param height the size of the image in the destination space
   */
  public void drawImage(ImageHandle img, int offsetX, int offsetY, int width, int height) {
    
    impl.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), 
            offsetX, offsetY, width, height);
  }
  
  /**
   * Fills the current path according to the current fillstyle.
   */
  public void fill() {
    impl.fill();
  }
  
  /**
   * Fills a rectangle of the specified dimensions,
   * at the specified start coords,
   * according to the current fillstyle.
   * 
   * @param startX x coord of the top left corner in the destination space
   * @param startY y coord of the top left corner in the destination space
   * @param width destination width of image
   * @param height destination height of image
   */
  public void fillRect(float startX, float startY, float width, float height) {
    impl.fillRect(startX, startY, width, height);
  }

  /**
   * Returns the height in pixels of the canvas.
   * @return returns the height in pixels of the canvas
   */
  public int getHeight() {
    return impl.getHeight(getElement());
  }
  
  /**
   * 
   * Returns the width in pixels of the canvas.
   * @return returns the width in pixels of the canvas
   */
  public int getWidth() {
    return impl.getWidth(getElement());
  }
  
  /**
   * Adds a line from the last point in the current path to the 
   * point defined by x and y.
   * 
   * @param x x coord of point
   * @param y y coord of point
   */
  public void lineTo(float x, float y) {
    impl.lineTo(x, y);
  }
  
  /**
   * Makes the last point in the current path be <b>(x,y)</b>.
   * 
   * @param x x coord of point
   * @param y y coord of point
   */
  public void moveTo(float x, float y) {
    impl.moveTo(x, y);
  }
  
  /**
   * Does nothing if the context has an empty path. Otherwise it connects 
   * the last point in the path to the given point <b>(x, y)</b> using a quadratic 
   * BŽzier curve with control point <b>(cpx, cpy)</b>, and then adds the given 
   * point <b>(x, y)</b> to the path.
   * 
   * @param cpx x coord of the control point
   * @param cpy y coord of the control point
   * @param x x coord of the point
   * @param y y coord of the point
   */
  public void quadraticCurveTo(float cpx, float cpy, float x, float y) {
    impl.quadraticCurveTo(cpx, cpy, x, y);
  }
  
  /**
   * Adds a rectangle to the current path, and closes the path.
   * 
   * @param startX x coord of the top left corner of the rectangle 
   * @param startY y coord of the top left corner of the rectangle
   * @param width the width of the rectangle
   * @param height the height of the rectangle
   */
  public void rect(float startX, float startY, float width, float height) {
    impl.rect(startX, startY, width, height);
  }
  
  /**
   * Convenience function for resizing the canvas with consistent coordinate and
   * screen pixel spaces. Equivalent to doing:
   * <pre><code>
   * canvas.setCoordSize(width, height);
   * canvas.setPixelHeight(height);
   * canvas.setPixelWidth(width);
   * </code></pre>
   * 
   * @param width
   * @param height
   */
  public void resize(int width, int height) {
    setCoordSize(width, height);
    setPixelHeight(height);
    setPixelWidth(width);
  }
  
  /**
   * Restores the last saved context from the context stack.
   */
  public void restoreContext() {
    impl.restoreContext();
  }
  
  /**
   * Adds a rotation of the specified angle to the current transform.
   * @param angle the angle to rotate by, <b>in radians</b>
   */
  public void rotate(float angle) {
    impl.rotate(angle);
  }
  
  /**
   * Saves the current context to the context stack.
   */
  public void saveContext() {
    impl.saveContext();
  }
  
  /**
   * Adds a scale transformation to the current transformation matrix.
   * @param x ratio that we must scale in the X direction
   * @param y ratio that we must scale in the Y direction
   */
  public void scale(float x, float y) {
    impl.scale(x, y);
  }
  
  /**
   * Sets the coordinate space of the Canvas. 
   * <p>This will erase the canvas contents!</p>
   * 
   * @param width the size of the x component of the coordinate space
   * @param height the size of the y component of the coordinate space
   */
  public void setCoordSize(int width, int height) {
    impl.setCoordWidth(getElement(), width);
    impl.setCoordHeight(getElement(), height);
    
    // maintain local references to them
    coordWidth = width;
    coordHeight = height;
  }
  
  /**
   * Set the current Fill Style to the specified color. 
   * @param color {@link Color} 
   */
  public void setFillStyle(Color color) {
    impl.setFillStyle(color.toString());
  }
  
  /* TODO: Awaiting me fixing IE6 gradients...
   * Set the current Fill Style to the specified color gradient. 
   * @param grad {@link CanvasGradient} 
   *
  public void setFillStyle(CanvasGradient grad) {
    impl.setFillStyle(grad);
  } */
  
  /**
   * Set the global transparency to the specified alpha.
   * @param alpha alpha value
   */
  public void setGlobalAlpha(float alpha) {
    impl.setGlobalAlpha(alpha);
  }
  
  /**
   * Sets the current context's linewidth. Line width is the thickness
   * of a stroked line.
   * @param width the width of the canvas
   */
  public void setLineWidth(float width) {
    impl.setLineWidth(width);
  }
  
  /**
   * Sets the height of the canvas in pixels. Also defines the height of the coordinate
   * space as an integer value.
   * @param height the height of the canvas
   */
  public void setPixelHeight(int height) {
    impl.setPixelHeight(getElement(), height);
  }  

  /**
   * Sets the CSS property in pixels for the canvas. 
   * @param width width of the canvas
   */
  public void setPixelWidth(int width) {
    impl.setPixelWidth(getElement(), width);
  }  
  
  /**
   * Set the current Stroke Style to the specified color. 
   * @param color {@link Color}
   */
  public void setStrokeStyle(Color color) {
    impl.setStrokeStyle(color.toString());
  }
  
  /* TODO: Awaiting me fixing IE6 gradients...
   * Set the current Stroke Style to the specified color gradient. 
   * @param grad {@link CanvasGradient} 
   *
  public void setStrokeStyle(CanvasGradient grad) {
    impl.setStrokeStyle(grad);
  } */
  
  /**
   * The <code>setTransform(m11, m12, m21, m22, dx, dy)</code> method resets the 
   * current transform to the identity matrix, and then invoke the 
   * <code>transform(m11, m12, m21, m22, dx, dy)</code> method with the same arguments.
   *
   * <p>m11 - m22 define the rotation component of the 2x2 transform matrix, which may include
   * scaling.</p>
   *
   * @param m11 top left cell of 2x2 rotation matrix
   * @param m12 top right cell of 2x2 rotation matrix
   * @param m21 bottom left cell of 2x2 rotation matrix
   * @param m22 bottom right cell of 2x2 rotation matrix
   * @param dx Translation in X direction
   * @param dy Translation in Y direction
   */
  public void setTransform(float m11, float m12, float m21,
      float m22, float dx, float dy) {
    impl.setTransform(m11, m12, m21, m22, dx, dy);
  }
 
  /**
   * Strokes the current path according to the current stroke style.
   */
  public void stroke() {
    impl.stroke();
  }
  /**
   * Strokes a rectangle defined by the supplied arguments.
   * 
   * @param startX x coord of the top left corner
   * @param startY y coord of the top left corner
   * @param width width of the rectangle
   * @param height height of the rectangle
   */
  public void strokeRect(float startX, float startY, float width, float height) {
    impl.strokeRect(startX, startY, width, height);
  }

  /**
   * <code>The transform(m11, m12, m21, m22, dx, dy)</code> method must multiply the 
   * current transformation matrix with the input matrix. Input described by:
   *<pre> m11   m21   dx
   * m12   m22   dy
   * 0      0     1 
   *</pre>
   * @param m11 top left cell of 2x2 rotation matrix
   * @param m12 top right cell of 2x2 rotation matrix
   * @param m21 bottom left cell of 2x2 rotation matrix
   * @param m22 bottom right cell of 2x2 rotation matrix
   * @param dx Translation in X direction
   * @param dy Translation in Y direction
   */
  public void transform(float m11, float m12, float m21,
      float m22, float dx, float dy) {
    impl.transform(m11, m12, m21, m22, dx, dy);
  }  

  /**
   * Applies a translation (linear shift) by x in the horizontal
   * and by y in the vertical.
   * 
   * @param x amount to shift in the x direction
   * @param y amount to shift in the y direction
   */
  public void translate(float x, float y) {
    impl.translate(x, y);
  }
  
  // TODO: Implement strokeCircle and fillCircle convenience methods.
 
}
