package com.google.gwt.demos.event.client;

import com.google.gwt.libideas.event.virtual.shared.VMouseDownHandler;
import com.google.gwt.libideas.event.virtual.shared.VMouseMoveHandler;
import com.google.gwt.libideas.event.virtual.shared.VMouseOutHandler;
import com.google.gwt.libideas.event.virtual.shared.VMouseOverHandler;
import com.google.gwt.libideas.event.virtual.shared.VMouseUpHandler;
import com.google.gwt.libideas.event.virtual.shared.VMouseWheelHandler;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class CircleWidget extends DraggableWidget implements
    VMouseDownHandler, VMouseUpHandler, VMouseWheelHandler, VMouseOutHandler,
    VMouseOverHandler, VMouseMoveHandler, VDragHandler {

  private static boolean showBounds = false;

  public CircleWidget(int x, int y, int radius, Color ccolor) {
    super(new Bounds(x - radius, y - radius, radius * 2, radius * 2), ccolor);
  }

  public void paintComponent(GWTCanvas canvas) {
    canvas.setFillStyle(color);
    canvas.beginPath();
    float radius = bounds.width / 2;
    canvas.arc(bounds.x + radius, bounds.y + radius, radius, 0,
        (float) (2 * Math.PI), true);
    canvas.closePath();
    canvas.fill();
    if (showBounds) {
      canvas.setStrokeStyle(new Color(0, 0, 0));
      canvas.beginPath();
      canvas.moveTo(bounds.x, bounds.y);
      canvas.rect(bounds.x, bounds.y, bounds.width, bounds.height);
      canvas.stroke();
    }
  }

  public boolean contains(float lx, float ly) {
    float radius = bounds.width / 2;

    return Math.hypot(bounds.x + radius - lx, bounds.y + radius - ly) < radius;
  }
}