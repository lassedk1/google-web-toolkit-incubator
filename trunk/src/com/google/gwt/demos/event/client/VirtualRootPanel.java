package com.google.gwt.demos.event.client;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class VirtualRootPanel extends SimplePanel {

  private GWTCanvas canvas;

  private ArrayList<CanvasWidget> widgets = new ArrayList<CanvasWidget>();

  private CanvasWidget lastWidget = null;

  private double dragTimestamp;

  private boolean maybeDrag;

  private int dragX;

  private int dragY;

  private boolean dragging = false;

  public VirtualRootPanel() {
    canvas = new GWTCanvas(400, 400);
    setWidget(canvas);
    sinkEvents(Event.MOUSEEVENTS);
    sinkEvents(Event.ONMOUSEWHEEL);
  }

  public void onBrowserEvent(Event event) {
    switch (event.getTypeInt()) {
      case Event.ONMOUSEDOWN:
      case Event.ONMOUSEUP:
      case Event.ONMOUSEOUT:
      case Event.ONMOUSEOVER:
      case Event.ONMOUSEMOVE:
      case Event.ONCLICK:
      case Event.ONMOUSEWHEEL:
        fireVirtualEvent(event);
        break;
      default:
        super.onBrowserEvent(event);
    }
  }

  private void fireVirtualEvent(Event event) {
    VEventDataCustomImpl vEventData = new VEventDataCustomImpl();
    vEventData.setClientX(event.getClientX());
    vEventData.setClientY(event.getClientY());
    vEventData.setButton(event.getButton());
    vEventData.setAltKey(event.getAltKey());
    vEventData.setShiftKey(event.getShiftKey());
    vEventData.setMetaKey(event.getMetaKey());
    vEventData.setMouseWheelVelocityY(event.getMouseWheelVelocityY());
    vEventData.setKeyCode(event.getKeyCode());
    vEventData.setRepeat(event.getRepeat());

    ArrayList<CanvasWidget> rev = new ArrayList<CanvasWidget>();
    rev.addAll(widgets);
    Collections.reverse(rev);
    for (Iterator<CanvasWidget> cw = rev.iterator(); cw.hasNext();) {
      CanvasWidget canvasWidget = cw.next();
      int lx = event.getClientX() - DOM.getAbsoluteLeft(getElement());
      int ly = event.getClientY() - DOM.getAbsoluteTop(getElement());
      if (canvasWidget.contains(lx, ly)) {
        if(dragging && canvasWidget != lastWidget && event.getTypeInt() ==
            Event.ONMOUSEMOVE) continue;
        if (Event.ONMOUSEDOWN == event.getTypeInt()) {
          dragTimestamp = Duration.currentTimeMillis();
          maybeDrag = true;
          dragX = lx;
          dragY = ly;
        } else if (Event.ONMOUSEUP == event.getTypeInt()) {
          maybeDrag = false;
          dragging = false;
        }
        if (lastWidget == null || (lastWidget != canvasWidget && !dragging)) {
          if (lastWidget != null) {
            lastWidget.fireVirtualEvent(Event.ONMOUSEOUT, vEventData);
          }
          canvasWidget.fireVirtualEvent(Event.ONMOUSEOVER, vEventData);
        } else {
//          GWT.log("lx-dragx="+Math.abs(lx-dragX)+" ly-dragy="+Math.abs(ly-dragY)+" dragging="+dragging+" maybedrag="+maybeDrag, null);
          if (!dragging && maybeDrag && event.getTypeInt() == Event.ONMOUSEMOVE
              && (Math.abs(lx - dragX) > 3 || Math
              .abs(ly - dragY) > 3)) {
            dragging = true;
            canvasWidget
                .fireCustomVirtualEvent(VDragStartEvent.KEY, vEventData);
            return;
          } else if (dragging && event.getTypeInt() == Event.ONMOUSEMOVE) {
            vEventData.setDragX(lx - dragX);
            vEventData.setDragY(ly - dragY);
            canvasWidget.fireCustomVirtualEvent(VDragEvent.KEY, vEventData);
            dragX = lx;
            dragY = ly;
            lastWidget = canvasWidget;
            return;
          } else if (dragging && event.getTypeInt() == Event.ONMOUSEUP) {
            canvasWidget.fireCustomVirtualEvent(VDragEndEvent.KEY, vEventData);
            dragging = false;
            maybeDrag = false;
            lastWidget = canvasWidget;
            return;
          } else {
            dragging = false;
          }
        }
        lastWidget = canvasWidget;
        canvasWidget
            .fireVirtualEvent(event.getTypeInt(), vEventData);

        return;
      }
    }
    if (lastWidget != null) {
      lastWidget.fireVirtualEvent(Event.ONMOUSEOUT, vEventData);
      lastWidget = null;
    }
  }

  public void add(CanvasWidget widget) {
    widgets.add(widget);
    widget.setVirtualRoot(this);
    redraw();
  }

  public void remove(CanvasWidget widget) {
    widgets.remove(widget);
    widget.setVirtualRoot(null);
    redraw();
  }

  void redraw() {
    canvas.clear();
    for (CanvasWidget widget : widgets) {
      widget.paint(canvas);
    }
  }
}
