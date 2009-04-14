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
package com.google.gwt.widgetideas.table.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.WidgetTestBase;
import com.google.gwt.widgetideas.table.client.overrides.HTMLTable;
import com.google.gwt.widgetideas.table.client.overrides.HTMLTable.RowFormatter;

import java.util.Iterator;
import java.util.List;

/**
 * Base test for HTMLTable derived classes copied from gwt user for use in table
 * testing.
 * 
 * @deprecated Replaced by
 *             {@link com.google.gwt.gen2.table.client.TableBulkRendererTestBase}
 */
@Deprecated
public abstract class TableBulkRendererTestBase extends WidgetTestBase {
  abstract class TestCallback implements RendererCallback {
    HTMLTable table;

    public void onRendered() {
      test(table);
      finishTest();
    }

    public abstract void test(HTMLTable table);
  }

  protected static final int TIME_OUT = 1000;

  public static void assertEquals(Object[] x, Object[] y) {
    assertEquals(x.length, y.length);
    for (int i = 0; i < y.length; i++) {
      assertEquals(x[i], y[i]);
    }
  }

  /**
   * Easy way to test what should be in a list.
   */
  protected static void assertEquals(Object[] array, List target) {
    if (target.size() != array.length) {
      fail(target + " should be the same length as" + array);
    }
    for (int i = 0; i < array.length; i++) {
      assertEquals(target.get(i), array[i]);
    }
  }

  public void testContents() {
    doTest(createOracle(4, 4), new TestCallback() {

      @Override
      public void test(HTMLTable table) {
        int rows = table.getRowCount();
        for (int i = 0; i < rows; i++) {
          int columns = table.getCellCount(i);
          for (int j = 0; j < columns; j++) {
            assertEquals(table.getText(i, j), cellContents(i, j));
          }
        }
      }
    });
  }

  public void testIterator() {
    doTest(createEmptyOracle(5, 5), new TestCallback() {
      @Override
      public void test(HTMLTable t) {
        Label l = new Label("hello");
        t.setWidget(0, 0, l);
        Iterator iter = t.iterator();
        assertEquals(l, iter.next());
        iter.remove();
        Iterator iter2 = t.iterator();
        assertFalse(iter2.hasNext());

        // Check put after remove.
        Widget w = new Label("bo");
        t.setWidget(0, 0, w);
        Iterator iter3 = t.iterator();
        assertEquals(w, iter3.next());
        assertFalse(iter3.hasNext());

        // Check swapping widgets.
        Widget w2 = new Label("ba");
        t.setWidget(0, 0, w2);
        Iterator iter4 = t.iterator();
        assertEquals(w2, iter4.next());
        assertFalse(iter4.hasNext());

        // Check put after put.
        Widget w3 = new Label("be");
        t.setWidget(1, 1, w3);
        Iterator iter5 = t.iterator();
        assertEquals(w2, iter5.next());
        assertEquals(w3, iter5.next());
        assertFalse(iter5.hasNext());
      }

    });
  }

  public void testStyles() {
    doTest(createEmptyOracle(4, 4), new TestCallback() {
      @Override
      public void test(HTMLTable t) throws Error {
        t.getCellFormatter().setStyleName(2, 2, "hello");
        assertEquals("hello", t.getCellFormatter().getStyleName(2, 2));
        t.getCellFormatter().setStyleName(2, 2, "goodbye");
        t.getCellFormatter().addStyleName(2, 2, "hello");

        // Visable Styles.
        t.getCellFormatter().setVisible(0, 0, false);
        assertTrue(t.getCellFormatter().isVisible(2, 2));
        assertFalse(t.getCellFormatter().isVisible(0, 0));
        RowFormatter formatter = t.getRowFormatter();
        formatter.setVisible(3, false);
        assertFalse(formatter.isVisible(3));
        assertTrue(formatter.isVisible(2));
        assertTrue(t.getCellFormatter().isVisible(2, 0));

        // Style name.
        assertEquals("goodbye hello", t.getCellFormatter().getStyleName(2, 2));
        t.getRowFormatter().setStyleName(3, "newStyle");
        assertEquals("newStyle", t.getRowFormatter().getStyleName(3));
      }
    });
  }

  protected abstract Object[] createTableAndRenderer();

  protected void doTest(TableModel<Object> model, TestCallback callback) {
    delayTestFinish(TIME_OUT);
    Object[] local = createTableAndRenderer();
    HTMLTable table = (HTMLTable) local[0];
    TableBulkRenderer renderer = (TableBulkRenderer) local[1];
    callback.table = table;
    renderer.renderRows(model, callback);
  }

  private String cellContents(int rowNum, int cellNum) {
    return "cell " + rowNum + ", " + cellNum;
  }

  private TableModel<Object> createEmptyOracle(final int numRows,
      final int numColumns) {
    ClientTableModel<Object> oracle = new ClientTableModel<Object>() {
      @Override
      public Object getCell(int rowNum, int cellNum) {
        if (rowNum >= numRows | cellNum >= numColumns) {
          return null;
        }
        return "";
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
        RootPanel.get().add(new Label("Setting cell: " + data));
        return false;
      }
    };
    return oracle;
  }

  private TableModel<Object> createOracle(final int numRows,
      final int numColumns) {
    ClientTableModel<Object> oracle = new ClientTableModel<Object>() {
      @Override
      public Object getCell(int rowNum, int cellNum) {
        if (rowNum >= numRows | cellNum >= numColumns) {
          return null;
        }
        return cellContents(rowNum, cellNum);
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
        RootPanel.get().add(new Label("Setting cell: " + data));
        return false;
      }
    };
    return oracle;
  }
}
