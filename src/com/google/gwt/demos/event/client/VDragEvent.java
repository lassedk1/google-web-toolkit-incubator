package com.google.gwt.demos.event.client;

import com.google.gwt.libideas.event.virtual.shared.EventData;
import com.google.gwt.libideas.event.virtual.shared.VMouseEvent;

public class VDragEvent extends VMouseEvent<VDragHandler> {

  public static final Key<VDragHandler> KEY = new Key<VDragHandler>();

  public VDragEvent(EventData eventData) {
    super(KEY, eventData);
  }

  public void fireEvent(VDragHandler handler) {
    handler.onDrag(this);
  }

  public int getDragX() {
    if (getEventData() instanceof VEventDataCustomImpl) {
      return ((VEventDataCustomImpl) getEventData()).getDragX();
    }
    return 0;
  }

  public int getDragY() {
    if (getEventData() instanceof VEventDataCustomImpl) {
      return ((VEventDataCustomImpl) getEventData()).getDragY();
    }
    return 0;
  }
}