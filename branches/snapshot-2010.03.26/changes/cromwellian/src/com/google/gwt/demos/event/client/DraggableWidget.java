package com.google.gwt.demos.event.client;

import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.google.gwt.libideas.event.virtual.shared.VMouseDownHandler;
import com.google.gwt.libideas.event.virtual.shared.VMouseDownEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseUpEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseUpHandler;
import com.google.gwt.libideas.event.virtual.shared.VMouseWheelEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseWheelHandler;
import com.google.gwt.libideas.event.virtual.shared.VMouseOutHandler;
import com.google.gwt.libideas.event.virtual.shared.VMouseOverHandler;
import com.google.gwt.libideas.event.virtual.shared.VMouseOutEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseOverEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseMoveHandler;
import com.google.gwt.libideas.event.virtual.shared.VMouseMoveEvent;

public abstract class DraggableWidget extends CanvasWidget implements VMouseDownHandler,
    VMouseUpHandler, VMouseWheelHandler, VMouseOutHandler, VMouseOverHandler,
    VMouseMoveHandler, VDragHandler {

  protected Color color;

  private boolean maybeDrag=false;

  private Color ccolor;

  public DraggableWidget(Bounds bounds, Color ccolor) {
    super(bounds);
    this.ccolor = ccolor;
    tryCreateManager();
    manager.addEventHandler(VMouseDownEvent.KEY, this);
    manager.addEventHandler(VMouseUpEvent.KEY, this);
    manager.addEventHandler(VMouseWheelEvent.KEY, this);
    manager.addEventHandler(VMouseOverEvent.KEY, this);
    manager.addEventHandler(VMouseOutEvent.KEY, this);
    manager.addEventHandler(VMouseMoveEvent.KEY, this);
    manager.addEventHandler(VDragEvent.KEY, this);
            
    color = ccolor;
  }

  public void onMouseDown(VMouseDownEvent event) {
    color = new Color(0, 255, 0);
    maybeDrag=true;
    
    redraw();
  }

  public void onMouseUp(VMouseUpEvent event) {
    color = ccolor;
    maybeDrag=false;
    redraw();
    
  }

  public void onMouseWheel(VMouseWheelEvent event) {
    float r = bounds.width;
    r *= event.getMouseWheelVelocityY() < 0 ? .9 : 1.1;
    bounds.width = bounds.height = r;
    
    redraw();
  }

  public void onMouseOut(VMouseOutEvent event) {
    color = ccolor;
    maybeDrag=false;
    redraw();
  }

  public void onMouseOver(VMouseOverEvent event) {
    color = new Color(255, 255, 0);
    maybeDrag=false;
    redraw();   
  }

  public void onMouseMove(VMouseMoveEvent event) {
   if(maybeDrag) {
    redraw();
   }
  }

  
  public void onDrag(VDragEvent event) {
    bounds.x += event.getDragX();
    bounds.y += event.getDragY();
    redraw();
  }
}
