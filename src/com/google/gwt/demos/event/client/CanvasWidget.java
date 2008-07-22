package com.google.gwt.demos.event.client;

import com.google.gwt.libideas.event.shared.HandlerManager;
import com.google.gwt.libideas.event.shared.AbstractEvent;
import com.google.gwt.libideas.event.virtual.shared.VClickEvent;
import com.google.gwt.libideas.event.virtual.shared.VEventDataImpl;
import com.google.gwt.libideas.event.virtual.shared.VMouseDownEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseDownHandler;
import com.google.gwt.libideas.event.virtual.shared.VMouseMoveEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseOutEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseOverEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseUpEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseWheelEvent;
import com.google.gwt.libideas.event.virtual.shared.VirtualEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public abstract class CanvasWidget {

  protected Bounds bounds;

  protected HandlerManager manager;

  private VirtualRootPanel vroot;

  public CanvasWidget(Bounds bounds) {
    this.bounds = bounds;
  }

  public void paint(GWTCanvas canvas) {
    canvas.saveContext();
    paintComponent(canvas);
    canvas.restoreContext();
  }

  public abstract void paintComponent(GWTCanvas canvas);

  public Bounds getBounds() {
    return bounds;
  }

  public void addMouseDownHanlder(VMouseDownHandler handler) {
    tryCreateManager();
    manager.addEventHandler(VMouseDownEvent.KEY, handler);
  }

  void tryCreateManager() {
    if (manager == null) {
      manager = new HandlerManager(this);
    }
  }

  public void fireVirtualEvent(int event, VEventDataImpl vEventData) {
    switch (event) {
      case Event.ONMOUSEDOWN:
        manager.fireEvent(new VMouseDownEvent(vEventData));
        break;
      case Event.ONMOUSEUP:
        manager.fireEvent(new VMouseUpEvent(vEventData));
        break;
      case Event.ONMOUSEOUT:
        manager.fireEvent(new VMouseOutEvent(vEventData));
        break;
      case Event.ONMOUSEOVER:
        manager.fireEvent(new VMouseOverEvent(vEventData));
        break;
      case Event.ONMOUSEMOVE:
        manager.fireEvent(new VMouseMoveEvent(vEventData));
        break;
      case Event.ONCLICK:
        manager.fireEvent(new VClickEvent(vEventData));
        break;
      case Event.ONMOUSEWHEEL:
        manager.fireEvent(new VMouseWheelEvent(vEventData));
        break;
    }
  }

  protected void redraw() {
    if (vroot != null) {
      vroot.redraw();
    }
  }

  public void setVirtualRoot(VirtualRootPanel vroot) {
    this.vroot = vroot;
  }

  public boolean contains(float lx, float ly) {
    return bounds.contains(lx, ly);
  }

  public void fireCustomVirtualEvent(AbstractEvent.Key key,
      VEventDataImpl vEventData) {
    if(VDragStartEvent.KEY == key) {
      manager.fireEvent(new VDragStartEvent(vEventData));
    }
    else if(VDragEndEvent.KEY == key) {
      manager.fireEvent(new VDragEndEvent(vEventData));
    }
    else if(VDragEvent.KEY == key) {
      manager.fireEvent(new VDragEvent(vEventData));
    }
  }
}
