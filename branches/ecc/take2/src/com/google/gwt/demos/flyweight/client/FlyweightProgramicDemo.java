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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.event.ClickEvent;
import com.google.gwt.widgetideas.client.event.ClickHandler;
import com.google.gwt.widgetideas.table.client.ClientTableModel;
import com.google.gwt.widgetideas.table.client.FlexTableBulkRenderer;
import com.google.gwt.widgetideas.table.client.RendererCallback;
import com.google.gwt.widgetideas.table.client.TableModel;
import com.google.gwt.widgetideas.table.client.overrides.FlexTable;
import com.google.gwt.widgetideas.table.client.overrides.Grid;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FlyweightProgramicDemo implements EntryPoint {

  // /////////////////////////////////////////
  // Set up code
  // /////////////////////////////////////////
  protected static final int NUM_ROWS = 150;
  protected static final int NUM_COLUMNS = 5;

  final CaptionPanel panel = new CaptionPanel("table");
  long time;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    final Button button = new Button("Table with widgets");

    button.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        straightlineCode();
      }
    });
    RootPanel.get().add(button);

    final Button button2 = new Button("Table with flyweights");

    button2.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        flyweightCode();
      }
    });
    RootPanel.get().add(button2);

    RootPanel.get().add(panel);
  }

  private void flyweightCode() {
    panel.setContentWidget(null);
    time = System.currentTimeMillis();

    // Set up table
    final FlexTable table = new FlexTable();

    // Create Flyweight.
    final TextBoxFlyweight fly = new TextBoxFlyweight();

    fly.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        Window.alert("Clicked on "
            + ((TextBoxFlyweight) event.getSource()).getInstanceText());
      }
    });

    // Create FlyweightInstanceFacotry
    final TextBoxFlyweightInstanceFactory factory = new TextBoxFlyweightInstanceFactory(
        fly);

    // Create table model.
    TableModel m = new ClientTableModel() {
      public Object getCell(int rowNum, int colNum) {
        if (rowNum >= NUM_ROWS || colNum >= NUM_COLUMNS) {
          return null;
        }
        String input = rowNum + "-" + colNum;
        factory.setInstanceId(input);
        factory.setInstanceText(input);
        return factory;
      }

      @Override
      protected boolean onRowInserted(int beforeRow) {
        return false;
      }

      @Override
      protected boolean onRowRemoved(int row) {
        return false;
      }

      @Override
      protected boolean onSetData(int row, int cell, Object data) {
        return false;
      }
    };

    // Render rows.
    SimpleFlyweightPanel flyListenerPanel = new SimpleFlyweightPanel();
    flyListenerPanel.setWidget(table);
    panel.setContentWidget(flyListenerPanel);
    new FlexTableBulkRenderer(table,
        FlyweightInstanceFactory.TABLE_CELL_RENDERER).renderRows(m,
        new RendererCallback() {
          public void onRendered() {
            reportTime();
          }
        });
  }

  private void reportTime() {
    this.panel.setCaptionHTML("Took" + (System.currentTimeMillis() - time)
        + "milliseconds");
  }

  private void straightlineCode() {
    panel.setContentWidget(null);
    time = System.currentTimeMillis();
    final ClickListener listener = new ClickListener() {
      public void onClick(Widget sender) {
        TextBox box = (TextBox) sender;
        panel.setTitle("Clicked box is " + box.getText());
      }
    };
    // A grid is as efficient as a bulk loaded table if there are no other
    // fields.
    Grid grid = new Grid(NUM_ROWS, NUM_COLUMNS);
    for (int i = 0; i < NUM_ROWS; i++) {
      for (int j = 0; j < NUM_COLUMNS; j++) {
        TextBox b = new TextBox();
        b.setText(i + "-" + j);
        b.addClickListener(listener);
        grid.setWidget(i, j, b);
      }
    }
    panel.setContentWidget(grid);
    reportTime();
  }
}
