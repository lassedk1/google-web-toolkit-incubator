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
package com.google.gwt.demos.event.client;

import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.google.gwt.widgetideas.graphics.client.Color;

/**
 * TODO
 */
public class RectWidget extends DraggableWidget {

  public RectWidget(Bounds bounds, Color ccolor) {
    super(bounds, ccolor);
  }

  public RectWidget(int x, int y, int width, int height, Color color) {
    this(new Bounds(x,y,width,height), color);
  }

  public void paintComponent(GWTCanvas canvas) {
    canvas.setFillStyle(color);
    canvas.moveTo(bounds.x, bounds.y);
    canvas.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
  }
}
