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
package com.google.gwt.gen2.demo.scrolltable.client;

import com.google.gwt.gen2.demo.scrolltable.shared.Student;
import com.google.gwt.gen2.table.client.ColumnDefinition;
import com.google.gwt.gen2.table.client.FixedWidthGridBulkRenderer;
import com.google.gwt.gen2.table.client.SelectionGrid;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.gen2.table.client.TableDefinition.HTMLCellView;

import java.util.List;

/**
 * A customized version of the {@link FixedWidthGridBulkRenderer} that adds
 * additional styles.
 */
public class StudentBulkRenderer extends FixedWidthGridBulkRenderer<Student> {
  /**
   * A customized cell view used by the {@link StudentBulkRenderer}.
   */
  protected class StudentBulkCellView extends SelectionBulkCellView {
    /**
     * A boolean used to toggle alternate rows.
     * 
     * TODO (jlabanca): move up to TableBulkRenderer
     */
   private boolean altRow = false;

    /**
     * Construct a new {@link StudentBulkCellView}.
     * 
     * @param options the RenderingOptions to apply to the table
     */
    public StudentBulkCellView(RenderingOptions options) {
      super(options);
    }

    @Override
    protected void renderRowImpl(Student rowValue,
        List<ColumnDefinition<Student, ?>> visibleColumns) {
      StringBuffer buffer = getStringBuffer();
      if (altRow) {
        buffer.append("<tr class=\"altRow\">");
      } else {
        buffer.append("<tr>");
      }
      altRow = !altRow;

      // Add the input column
      SelectionPolicy selectionPolicy = ((SelectionGrid) getTable()).getSelectionPolicy();
      if (selectionPolicy.hasInputColumn()) {
        buffer.append("<td align='CENTER'>");
        buffer.append(getInputHtml((SelectionGrid) getTable(), selectionPolicy));
        buffer.append("</td>");
      }

      // Add the columns
      int numColumns = visibleColumns.size();
      for (int i = 0; i < numColumns; i++) {
        setCellIndex(i);
        renderCellImpl(rowValue, visibleColumns.get(i));
      }
      buffer.append("</tr>");
    }
  }

  /**
   * Construct a new {@link StudentBulkRenderer}.
   * 
   * @param scrollTable {@link StudentPagingScrollTable} to be be bulk rendered
   */
  public StudentBulkRenderer(StudentPagingScrollTable scrollTable) {
    super(scrollTable.getDataTable(), scrollTable);
  }

  @Override
  protected HTMLCellView<Student> createCellView(final RenderingOptions options) {
    return new StudentBulkCellView(options);
  }

  private native String getInputHtml(SelectionGrid table,
      SelectionPolicy selectionPolicy)
  /*-{
    return table.@com.google.gwt.gen2.table.client.SelectionGrid::getInputHtml(Lcom/google/gwt/gen2/table/client/SelectionGrid$SelectionPolicy;)(selectionPolicy);
  }-*/;
}
