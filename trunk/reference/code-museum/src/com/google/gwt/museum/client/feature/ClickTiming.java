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

package com.google.gwt.museum.client.feature;

import com.google.gwt.core.client.Duration;
import com.google.gwt.gen2.commonwidget.client.SimpleWidget;
import com.google.gwt.gen2.event.dom.client.ClickEvent;
import com.google.gwt.gen2.event.dom.client.ClickHandler;
import com.google.gwt.museum.client.common.AbstractIssueOverride;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

/**
 * Mouse move timings. Using artificial repeat to get anyway at all. Note,
 * cannot set max repeat higher then 200 here while using artificial clicks, as
 * the browser will start to colect the event.
 */
public class ClickTiming extends AbstractIssueOverride {
  private class Monitor {
    int repeat;
    double runningAverage;
    int inputs = 1;
    Duration duration;
    Widget widget;
    Event savedEvent;
    private int myRow = 3;

    Monitor(Widget widget) {
      this.widget = widget;
    }

    public String getRunningAverage() {
      return "Running average:" + runningAverage / inputs;
    }

    int getReportRow() {
      return myRow++;
    }

    String repeat() {
      if (repeat == 0) {
        duration = new Duration();
        if (usePretend) {
          savedEvent = DOM.eventGetCurrentEvent();
          ++repeat;
          while (savedEvent != null && savedEvent.getType() != null
              && savedEvent.getType().equals("click")) {
            widget.onBrowserEvent(savedEvent);
            if (repeat == 0) {
              return null;
            }
          }
        }
      } else if (repeat == maxRepeat) {
        double elapses = duration.elapsedMillis();
        runningAverage = runningAverage + elapses;
        ++inputs;
        repeat = 0;
        return "This iteration of " + maxRepeat + " clicks:"
            + duration.elapsedMillis();
      } else if (repeat > maxRepeat) {
        throw new IllegalStateException("this should never happen");
      }
      ++repeat;

      return null;
    }
  }

  static int maxRepeat = 500;

  boolean usePretend = true;

  Button listener;
  SimpleWidget handler;

  FlexTable t = new FlexTable();

  @Override
  public Widget createIssue() {

    t.setBorderWidth(1);
    t.setText(0, 0, "Mouse Handler Info");
    t.setText(0, 1, "Mouse Listener Info");

    setupListener();
    setupHandler();
    return t;
  }

  @Override
  public String getInstructions() {
    return "instructions";
  }

  @Override
  public String getSummary() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean hasCSS() {
    return false;
  }

  private void setupHandler() {

    handler = new SimpleWidget("<button>" + "click"
        + " handler widget</button>");
    final Monitor monitor = new Monitor(handler);
    handler.setSize("200px", "100px");
    handler.getElement().getStyle().setProperty("background", "#efefef");
    t.setWidget(1, 0, handler);

    handler.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        String results = monitor.repeat();
        if (results != null) {
          t.setText(2, 0, monitor.getRunningAverage());
          t.setText(monitor.getReportRow(), 0, results);
        }
      }
    });
  }

  private void setupListener() {
    listener = new Button("move" + " listener widget");
    final Monitor monitor = new Monitor(listener);
    listener.setSize("200px", "100px");
    listener.getElement().getStyle().setProperty("background", "#dedede");
    t.setWidget(1, 1, listener);
    listener.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        String results = monitor.repeat();
        if (results != null) {
          t.setText(2, 1, monitor.getRunningAverage());
          t.setText(monitor.getReportRow(), 1, results);
        }
      }

    });
  }

}
