package com.google.gwt.libideas.event.virtual.shared;

import com.google.gwt.libideas.event.shared.AbstractEvent;
import com.google.gwt.libideas.event.virtual.shared.EventData;
import com.google.gwt.libideas.event.shared.EventHandler;

public abstract class VirtualEvent<T extends EventHandler>
    extends AbstractEvent<T> {

  public static class Key<T extends EventHandler> extends AbstractEvent.Key<T> {

  }

  private EventData eventData;

  private Key<T> key;

  public VirtualEvent(Key<T> key, EventData eventData) {
    this.key = key;
    this.eventData = eventData;
  }

  public EventData getEventData() {
    return eventData;
  }

  public AbstractEvent.Key<T> getKey() {
    return key;
  }
}
