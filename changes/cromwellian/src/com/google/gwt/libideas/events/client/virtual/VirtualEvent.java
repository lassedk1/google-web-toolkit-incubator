package com.google.gwt.libideas.events.client.virtual;

import com.google.gwt.libideas.events.client.AbstractEvent;
import com.google.gwt.libideas.events.client.EventData;
import com.google.gwt.libideas.events.client.EventHandler;

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
