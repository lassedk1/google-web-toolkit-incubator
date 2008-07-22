package com.google.gwt.demos.event.client;

import com.google.gwt.libideas.event.virtual.shared.VEventDataImpl;

public class VEventDataCustomImpl extends VEventDataImpl {

  private int dragY;

  private int dragX;

  public void setDragY(int dragY) {
    this.dragY = dragY;
  }

  public int getDragX() {
    return dragX;
  }

  public int getDragY() {
    return dragY;
  }

  public void setDragX(int dragX) {
    this.dragX = dragX;
  }
}
