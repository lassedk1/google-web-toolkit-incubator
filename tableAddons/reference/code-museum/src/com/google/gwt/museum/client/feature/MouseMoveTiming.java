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
import com.google.gwt.gen2.event.dom.client.MouseMoveEvent;
import com.google.gwt.gen2.event.dom.client.MouseMoveHandler;
import com.google.gwt.gen2.event.dom.client.MouseOverEvent;
import com.google.gwt.gen2.event.dom.client.MouseOverHandler;
import com.google.gwt.museum.client.common.AbstractIssueOverride;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * Mouse move timings.
 */
public class MouseMoveTiming extends AbstractIssueOverride {

  private class Monitor {
    int repeat;
    int maxRepeat;
    double runningAverage;
    Duration duration;
    private int myRow = 3;

    Monitor(int maxRepeat) {
      this.maxRepeat = maxRepeat;
    }

    public String getRunningAverage() {
      return "Running average:" + runningAverage;
    }

    int getReportRow() {
      return myRow++;
    }

    String repeat() {
      if (repeat == 0) {
        duration = new Duration();
      } else if (repeat == maxRepeat) {
        double milli = duration.elapsedMillis();
        runningAverage = (runningAverage + milli) / 2;
        repeat = -5;
        return "Took " + milli + " to repeat " + maxRepeat + " times";
      }
      ++repeat;
      return null;
    }
  }

  HTML listener;
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
    return "move over both widgets";
  }

  @Override
  public String getSummary() {
    return "mouse move timing";
  }

  @Override
  public boolean hasCSS() {
    return false;
  }

  private void setupHandler() {
    final Monitor handlerMonitor = new Monitor(50);
    handler = new SimpleWidget("<div>" + "move" + " handler widget</div>");
    handler.setSize("200px", "100px");
    handler.getElement().getStyle().setProperty("background", "#efefef");
    t.setWidget(1, 0, handler);

    handler.addMouseMoveHandler(new MouseMoveHandler() {

      public void onMouseMove(MouseMoveEvent event) {
        String results = handlerMonitor.repeat();
        if (results != null) {
          t.setText(2, 0, handlerMonitor.getRunningAverage());
          t.setText(handlerMonitor.getReportRow(), 0, results);
        }
      }
    });
    handler.addMouseOverHandler(new MouseOverHandler() {

      public void onMouseOver(MouseOverEvent event) {
        handlerMonitor.repeat = -3;
      }
    });
  }

  private void setupListener() {
    final Monitor listenerMonitor = new Monitor(50);
    listener = new HTML("move" + " listener widget");
    listener.setSize("200px", "100px");
    listener.getElement().getStyle().setProperty("background", "#dedede");
    t.setWidget(1, 1, listener);
    listener.addMouseListener(new MouseListener() {

      public void onMouseDown(Widget sender, int x, int y) {
      }

      public void onMouseEnter(Widget sender) {
        listenerMonitor.repeat = -3;
      }

      public void onMouseLeave(Widget sender) {
      }

      public void onMouseMove(Widget sender, int x, int y) {
        String results = listenerMonitor.repeat();
        if (results != null) {
          t.setText(2, 1, listenerMonitor.getRunningAverage());
          t.setText(listenerMonitor.getReportRow(), 1, results);
        }
      }

      public void onMouseUp(Widget sender, int x, int y) {
      }
    });
  }

}
