package com.google.gwt.demos.event.client;

import com.google.gwt.libideas.event.virtual.shared.VMouseEvent;
import com.google.gwt.libideas.event.virtual.shared.VirtualEvent;
import com.google.gwt.libideas.event.virtual.shared.EventData;

public class VDragStartEvent extends VMouseEvent<VDragStartHandler> {

  public static final Key<VDragStartHandler> KEY = new Key<VDragStartHandler>();

  public VDragStartEvent(EventData eventData) {
    super(KEY, eventData);
  }

  public void fireEvent(VDragStartHandler handler) {
    handler.onDragStart(this);
  }
}
