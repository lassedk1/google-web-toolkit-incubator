package com.google.gwt.demos.event.client;

public class Bounds {

  public float x;

  public float y;

  public float width;

  public float height;

  public Bounds(float x, float y, float width, float height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public boolean contains(float lx, float ly) {
    return lx >= x && lx <= x + width && ly >= y && ly <= y+height;
  }
}
