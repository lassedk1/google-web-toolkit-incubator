package com.google.gwt.libideas.event.vevent.shared;

import com.google.gwt.libideas.event.vevent.shared.EventData;
import com.google.gwt.libideas.event.shared.EventHandler;

public abstract class VKeyboardEvent<T extends EventHandler> extends VirtualEvent<T> {

  public VKeyboardEvent(Key<T> key, EventData eventData) {
    super(key, eventData);
  }

  /**
   * Gets the current key code.
   *
   * @return the key code
   */
  public int getKeyCode() {
    return getEventData().getKeyCode();
  }

  /**
   * Is the key code alpha-numeric (i.e. A-z or 0-9).
   *
   * @return is the key code alpha numeric.
   */
  public boolean isAlphaNumeric() {
    int keycode = getKeyCode();
    return (48 <= keycode && keycode <= 57) || (65 <= keycode && keycode <= 90);
  }

  /**
   * Is <code>alt</code> key down.
   *
   * @return whether the alt key is down
   */
  public boolean isAltKeyDown() {
    return getEventData().getAltKey();
  }

  /**
   * Gets the key-repeat state of this event.
   *
   * @return <code>true</code> if this key event was an auto-repeat
   */
  public boolean isAutoRepeat() {
    return getEventData().getRepeat();
  }

  /**
   * Is <code>control</code> key down.
   *
   * @return whether the control key is down
   */
  public boolean isControlKeyDown() {
    return getEventData().getCtrlKey();
  }

  /**
   * Is <code>shift</code> key down.
   *
   * @return whether the shift key is down
   */
  public boolean isShiftKeyDown() {
    return getEventData().getShiftKey();
  }
}
