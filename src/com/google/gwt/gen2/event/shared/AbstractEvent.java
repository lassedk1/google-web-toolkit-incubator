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
package com.google.gwt.gen2.event.shared;

/**
 * Root of all gwt events. All gwt events are considered dead and should no
 * longer be accessed once the {@link HandlerManager} which originally fired the
 * event finishes with it.
 * 
 */
public abstract class AbstractEvent {
  /**
   * Key class used to register events with HandlerManager.
   * <p>
   * Key is parameterized by the event handler type in order to make the
   * addHandler method type safe.
   * 
   * @param <EventType> event type
   * @param <HandlerType> handler type
   */
  public abstract static class Key<EventType extends AbstractEvent, HandlerType extends EventHandler> {

    private int index;

    /**
     * Constructs a new event key.
     */
    public Key() {
      index = HandlerManager.createKeyIndex();
    }

    // We override hash code to make it as efficient as possible.
    @Override
    public final int hashCode() {
      return index;
    }

    /**
     * Fires the given handler on the supplied event.
     * 
     * @param handler
     * @param event
     */
    protected abstract void fire(HandlerType handler, EventType event);
  }

  private boolean dead;

  private Object source;

  /**
   * Constructor.
   */
  protected AbstractEvent() {
  }

  /**
   * Returns the source that last fired this event.
   * 
   * @return object representing the source of this event
   */
  public Object getSource() {
    assertLive();
    return source;
  }

  /**
   * This is a method used primarily for debugging. It gives a string
   * representation of the event details. This does not override the toString
   * method because the compiler cannot always optimize toString out correctly.
   * Event types should override as desired.
   * 
   * @return a string representing the event's specifics.
   */
  public String toDebugString() {
    String name = this.getClass().getName();
    name = name.substring(name.lastIndexOf("."));
    return name + ": source = " + source;
  }

  @Override
  public String toString() {
    return "event type";
  }

  /**
   * Asserts that the event attributes still should be accessed. All events are
   * considered to be "dead" after their original event handler finishing
   * activating them.
   */
  protected void assertLive() {
    assert (!dead) : "This event has already finished being processed by its original event manager, so you can no longer access it";
  }

  /**
   * Returns the Key used to register this event.
   * 
   * @return th key
   */
  protected abstract Key getKey();

  /**
   * Is the event current live?
   * 
   * @return whether the event is live
   */
  protected boolean isLive() {
    return !dead;
  }

  /**
   * Set this event to live. Used when reycling event instances.
   */
  protected void revive() {
    dead = false;
    source = null;
  }

  /**
   * Called after the event manager has finished processing the event.
   */
  void onRelease() {
    dead = true;
  }

  /**
   * Set the source that triggered this event.
   * 
   * @param source the source of this event, should only be set by a
   *          {@link HandlerManager}
   */
  void setSource(Object source) {
    this.source = source;
  }
}
