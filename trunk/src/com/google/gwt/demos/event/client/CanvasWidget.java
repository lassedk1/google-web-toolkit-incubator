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

import com.google.gwt.libideas.event.shared.AbstractEvent;
import com.google.gwt.libideas.event.shared.HandlerManager;
import com.google.gwt.libideas.event.virtual.shared.VClickEvent;
import com.google.gwt.libideas.event.virtual.shared.VEventDataImpl;
import com.google.gwt.libideas.event.virtual.shared.VMouseDownEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseDownHandler;
import com.google.gwt.libideas.event.virtual.shared.VMouseMoveEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseOutEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseOverEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseUpEvent;
import com.google.gwt.libideas.event.virtual.shared.VMouseWheelEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
/**
 * TODO
 */
public abstract class CanvasWidget {

  protected Bounds bounds;

  protected HandlerManager manager;

  private VirtualRootPanel vroot;

  public CanvasWidget(Bounds bounds) {
    this.bounds = bounds;
  }

  public void addMouseDownHanlder(VMouseDownHandler handler) {
    tryCreateManager();
    manager.addEventHandler(VMouseDownEvent.KEY, handler);
  }

  public boolean contains(float lx, float ly) {
    return bounds.contains(lx, ly);
  }

  public void fireCustomVirtualEvent(AbstractEvent.Key key,
      VEventDataImpl vEventData) {
    if (VDragStartEvent.KEY == key) {
      manager.fireEvent(new VDragStartEvent(vEventData));
    } else if (VDragEndEvent.KEY == key) {
      manager.fireEvent(new VDragEndEvent(vEventData));
    } else if (VDragEvent.KEY == key) {
      manager.fireEvent(new VDragEvent(vEventData));
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

  public Bounds getBounds() {
    return bounds;
  }

  public void paint(GWTCanvas canvas) {
    canvas.saveContext();
    paintComponent(canvas);
    canvas.restoreContext();
  }

  public abstract void paintComponent(GWTCanvas canvas);

  public void setVirtualRoot(VirtualRootPanel vroot) {
    this.vroot = vroot;
  }

  protected void redraw() {
    if (vroot != null) {
      vroot.redraw();
    }
  }

  void tryCreateManager() {
    if (manager == null) {
      manager = new HandlerManager(this);
    }
  }
}
