/*
 * Copyright 2007 Google Inc.
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

package com.google.gwt.demos.flyweight.client;

// http://www.quirksmode.org/dom/innerhtml.html
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.TitledPanel;
import com.google.gwt.widgetideas.table.client.ClientTableModel;
import com.google.gwt.widgetideas.table.client.FlexTableBulkRenderer;
import com.google.gwt.widgetideas.table.client.RendererCallback;
import com.google.gwt.widgetideas.table.client.TableModel;
import com.google.gwt.widgetideas.table.client.TableBulkRenderer.CellRenderer;
import com.google.gwt.widgetideas.table.client.overrides.FlexTable;
import com.google.gwt.widgetideas.table.client.overrides.Grid;

import java.util.ArrayList;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FlyweightDemo implements EntryPoint {

  // /////////////////////////////////////////
  // Set up code
  // /////////////////////////////////////////
  protected static final int NUM_ROWS = 100;
  protected static final int NUM_COLUMNS = 10;

  final TitledPanel panel = new TitledPanel("clicked checkbox");
  long time;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    final Button button = new Button("Default Table");

    button.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        straightlineCode();
      }
    });
    RootPanel.get().add(button);

    final Button button2 = new Button("Bulk Rendered table");

    button2.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        flyweightCode();
      }
    });
    RootPanel.get().add(button2);

    RootPanel.get().add(panel);
  }

  private void flyweightCode() {
    panel.setWidget(null);
    time = System.currentTimeMillis();

    // Set up table
    final FlexTable table = new FlexTable();

    // Create Flyweight.
    final CheckBoxFlyweight fly = new CheckBoxFlyweight();

    // Create Flyweight Click Listener.
    final FlyweightClickListener listener = new FlyweightClickListener() {

      @Override
      public void onClick(Flyweight checkBox) {
        panel.setTitle("Clicked box is "
            + ((CheckBoxFlyweight) checkBox).getText());
      }

    };

    // Create table model.
    TableModel m = new ClientTableModel() {
      public Object getCell(int rowNum, int colNum) {
        if (rowNum >= NUM_ROWS || colNum >= NUM_COLUMNS) {
          return null;
        }
        fly.setText(rowNum + "-" + colNum);
        return fly;
      }
    };

    // Render rows.
    new FlexTableBulkRenderer(table, Flyweight.TABLE_CELL_RENDERER).renderRows(
        m, new RendererCallback() {
          public void onRendered() {
            FlyweightEventPanel flyListenerPanel = new FlyweightEventPanel();
            flyListenerPanel.addFlyweightListener(fly, listener);
            flyListenerPanel.setWidget(table);
            panel.setWidget(flyListenerPanel);
            reportTime();
          }
        });
  }

  private void reportTime() {
    this.panel.setTitle("Took" + (System.currentTimeMillis() - time)
        + "milliseconds");
  }

  private void straightlineCode() {
    panel.setWidget(null);
    time = System.currentTimeMillis();
    final ClickListener listener = new ClickListener() {
      public void onClick(Widget sender) {
        CheckBox box = (CheckBox) sender;
        panel.setTitle("Clicked box is " + box.getText());
      }
    };
    Grid grid = new Grid(NUM_ROWS, NUM_COLUMNS);
    for (int i = 0; i < NUM_ROWS; i++) {
      for (int j = 0; j < NUM_COLUMNS; j++) {
        CheckBox b = new CheckBox(i + "-" + j);
        b.addClickListener(listener);
        grid.setWidget(i, j, b);
      }
    }
    panel.setWidget(grid);
    reportTime();
  }
}

/**
 * Represents a CheckBox {@link Flyweight}.
 */
class CheckBoxFlyweight extends Flyweight {

  String[] args = new String[3];

  {
    setTemplate("<SPAN class='gwt-CheckBox' fly='FLY_ID'><INPUT id='check%s' type='checkbox'><LABEL for='check%s'>%s</LABEL></SPAN>");
  }

  // If this was a full implementation we would rewrite all of checkbox
  // methods. Here we only rewrite the one we need.
  public String getText() {
    return DOM.getInnerText(DOM.getNextSibling(DOM.getFirstChild(this.getElement())));
  }

  // Construct the HTML that corresponds to the element
  // created by a checkbox.
  public void render(StringBuffer accum) {
    args[0] = INSTANCE_STAMP++ + "";
    args[1] = args[0];
    renderUsingTempate(args, accum);
  }

  public void setText(String text) {
    args[2] = text;
  }
}

/**
 * A Flyweight has no fixed element. Rather its element can change without
 * warning. It should have no internal state.
 */
abstract class Flyweight extends UIObject {
  static int FLYWEIGHT_STAMP = 0;
  static String FLY_ID = "FLY_ID";
  static String BREAK = "%s";
  static int INSTANCE_STAMP = 0;

  static CellRenderer TABLE_CELL_RENDERER = new CellRenderer() {
    public void renderCell(int row, int column, Object cellData,
        StringBuffer accum) {
      ((Flyweight) cellData).render(accum);
    }
  };

  public static void stampFlyweightID(Element element, int stamp) {
    DOM.setElementProperty(element, "fly", stamp + "");
  }

  protected final int flyweightID = ++FLYWEIGHT_STAMP;

  String[] template;

  /**
   * Renders the prototype.
   */
  public abstract void render(StringBuffer buffer);

  public native void setElement(Element element) /*-{
     this.@com.google.gwt.user.client.ui.UIObject::element = element;
   }-*/;

  protected void renderUsingTempate(String[] args, StringBuffer accum) {
    for (int i = 0; i < template.length; i++) {
      if (i != 0) {
        accum.append(args[i - 1]);
      }
      accum.append(template[i]);
    }
  }

  protected void setTemplate(String template) {
    this.template = template.replaceAll("FLY_ID", flyweightID + "").split(BREAK);
  }

}

abstract class FlyweightClickListener extends FlyweightListener {
  public FlyweightClickListener() {
    super(Event.ONCLICK);
  }

  public abstract void onClick(Flyweight flyweight);

  @Override
  public void triggerEvent(Flyweight flyweight, Event event) {
    onClick(flyweight);
  }
}

/**
 * A Flyweight Panel allows the panel to capture events rather than individual
 * widgets. The FlyweightPanel is designed to hold a small number of arbitrary
 * {@link FlyweightListener} instances.
 */

class FlyweightEventPanel extends SimplePanel {
  private static final String FLYWEIGHT_ID = "fly";
  FlyweightListenerCollection collection = new FlyweightListenerCollection();

  protected FlyweightEventPanel() {
    Flyweight.stampFlyweightID(this.getElement(), -1);
  }

  public void addFlyweightListener(Flyweight flyweight,
      FlyweightListener listener) {
    sinkEvents(listener.eventMask);
    collection.add(flyweight, listener);
  }

  public void onBrowserEvent(Event event) {
    // Ignore events that do not match our target event type type.

    Element elem = DOM.eventGetTarget(event);
    String fly;
    while (true) {
      fly = DOM.getElementAttribute(elem, FLYWEIGHT_ID);
      if (fly != null) {
        break;
      }
      elem = DOM.getParent(elem);
    }

    int flyweightID = Integer.parseInt(fly);
    if (flyweightID == -1) {
      return;
    } else {
      collection.triggerEvent(event, elem, flyweightID);
    }
  }
}

/**
 * A {@link FlyweightEventPanel} for Click events.
 * 
 */

abstract class FlyweightListener {
  int eventMask;

  public FlyweightListener(int eventMask) {
    this.eventMask = eventMask;
  }

  public abstract void triggerEvent(Flyweight flyweight, Event event);

}

class FlyweightListenerCollection {
  ArrayList flyweights = new ArrayList();
  ArrayList listeners = new ArrayList();

  public void add(Flyweight flyweight, FlyweightListener listener) {
    listeners.add(listener);
    flyweights.add(flyweight);
  }

  public void triggerEvent(Event event, Element flyweightInstance,
      int flyweightID) {
    int eventType = DOM.eventGetType(event);
    for (int i = 0; i < listeners.size(); i++) {
      FlyweightListener listener = ((FlyweightListener) listeners.get(i));
      Flyweight flyweight = ((Flyweight) flyweights.get(i));
      if (flyweight.flyweightID == flyweightID
          && ((listener.eventMask & eventType) > 0)) {
        flyweight.setElement(flyweightInstance);
        listener.triggerEvent(flyweight, event);
      }
    }
  }
}