package com.google.gwt.demos.scrollingbulkloadedtable.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.table.client.BulkEditablePagingGrid;
import com.google.gwt.widgetideas.table.client.EditableGridView;
import com.google.gwt.widgetideas.table.client.FixedWidthFlexTable;
import com.google.gwt.widgetideas.table.client.PagingScrollTable;
import com.google.gwt.widgetideas.table.client.ScrollTable;
import com.google.gwt.widgetideas.table.client.TableModel;
import com.google.gwt.widgetideas.table.client.TextCellEditor;
import com.google.gwt.widgetideas.table.client.MutableTableModel.TableModelAdaptor;

/**
 * Demo for a scrolling bulk loaded table.
 */
public class ScrollingBulkLoadedTableDemo implements EntryPoint {
  int numColumns = 8;
  int numRows = 200;
  ScrollTable current;

  /**
   * Create Demo.
   */
  public void onModuleLoad() {
    final TableModelAdaptor adaptor = createOracle();
    final TextBox numColumnsBox = new TextBox();
    numColumnsBox.setText(numColumns + "");
    final TextBox numRowsBox = new TextBox();
    numRowsBox.setText(numRows + "");
    FlexTable t = new FlexTable();
    t.setText(0, 0, "num rows");
    t.setWidget(0, 1, numRowsBox);
    t.setText(0, 2, "num columns:");
    t.setWidget(0, 3, numColumnsBox);
    RootPanel.get().add(t);

    Button b = new Button("Create traditional ScrollTable");
    b.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        EditableGridView body = new EditableGridView(adaptor);
        createScrollTable(body);
      }
    });
    RootPanel.get().add(b);
    Button b2 = new Button("Create bulkloading ScrollTable");
    b2.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        EditableGridView body = new BulkEditablePagingGrid(adaptor,
            toInt(numRowsBox), toInt(numColumnsBox));
        createScrollTable(body);
      }

      private int toInt(TextBox box) {
        String s = box.getText();
        return Integer.parseInt(s);
      }
    });
    RootPanel.get().add(b2);
    Button c = new Button("Clear current table", new ClickListener() {
      public void onClick(Widget sender) {
        if (current != null) {
          current.removeFromParent();
        }
      }
    });

    RootPanel.get().add(c);
  }

  private void createScrollTable(EditableGridView body) {
    // Must set page size for paging scroll table to display.
    body.setPageSize(numRows / 2);

    // Add cell editor.
    for (int i = 0; i < numColumns; i++) {
      body.setCellEditor(i, new TextCellEditor());
    }

    // Scroll Table header.
    FixedWidthFlexTable header = new FixedWidthFlexTable();
    for (int i = 0; i < numColumns; i++) {
      header.setText(0, i, "Col " + i);
    }

    PagingScrollTable scrollTable = new PagingScrollTable(body, header);

    // Must set pixel size if scrolling is enabled.
    scrollTable.setPixelSize(700, 200);
    scrollTable.setScrollingEnabled(true);
    body.gotoFirstPage();
    RootPanel.get().add(scrollTable);
    current = scrollTable;

  }

  private TableModelAdaptor createOracle() {
    TableModel.ClientTableModel oracle = new TableModel.ClientTableModel() {
      public Object getCell(int rowNum, int cellNum) {

        if (rowNum >= numRows | cellNum >= numColumns) {
          return null;
        }
        return "cell " + rowNum + ", " + cellNum;
      }
    };

    TableModelAdaptor adaptor = new TableModelAdaptor(oracle) {
      public void onSetData(int row, int cell, Object data) {
        RootPanel.get().add(new Label("Setting cell:" + data));
      }
    };
    return adaptor;
  }
}
