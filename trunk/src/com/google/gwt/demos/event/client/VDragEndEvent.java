package com.google.gwt.demos.event.client;

import com.google.gwt.libideas.event.virtual.shared.VMouseEvent;
import com.google.gwt.libideas.event.virtual.shared.VirtualEvent;
import com.google.gwt.libideas.event.virtual.shared.EventData;

public class VDragEndEvent extends VMouseEvent<VDragEndHandler> {

  public static final Key<VDragEndHandler> KEY = new Key<VDragEndHandler>();

  public VDragEndEvent(EventData eventData) {
    super(KEY, eventData);
  }

  public void fireEvent(VDragEndHandler handler) {
    handler.onDragEnd(this);
  }
}