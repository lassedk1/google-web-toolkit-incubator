package com.google.gwt.demos.event.client;

import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.google.gwt.widgetideas.graphics.client.Color;

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
