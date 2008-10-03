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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.gen2.demo.scrolltable.shared.Student;
import com.google.gwt.gen2.demo.scrolltable.shared.StudentGenerator;
import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.gen2.table.override.client.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ScrollTableDemo implements EntryPoint {
  /**
   * The source of {@link Student} data.
   */
  public static final StudentGenerator STUDENT_DATA = new StudentGenerator() {
    @Override
    public int getRandomInt(int max) {
      return Random.nextInt(max);
    }
  };

  /**
   * The data portion of the {@link ScrollTable}.
   */
  protected static FixedWidthGrid dataTable = null;

  /**
   * The footer portion of the {@link ScrollTable} .
   */
  protected static FixedWidthFlexTable footerTable = null;

  /**
   * The header portion of the {@link ScrollTable}.
   */
  protected static FixedWidthFlexTable headerTable = null;

  /**
   * The scroll table.
   */
  protected static ScrollTable scrollTable = null;

  /**
   * @return the data table.
   */
  public static FixedWidthGrid getDataTable() {
    return dataTable;
  }

  /**
   * @return the footer table.
   */
  public static FixedWidthFlexTable getFooterTable() {
    return footerTable;
  }

  /**
   * @return the header table.
   */
  public static FixedWidthFlexTable getHeaderTable() {
    return headerTable;
  }

  /**
   * @return the scroll table.
   */
  public static ScrollTable getScrollTable() {
    return scrollTable;
  }

  /**
   * Add a row of data cells each consisting of a string that describes the
   * row:column coordinates of the new cell. The number of columns in the new
   * row will match the number of columns in the grid.
   * 
   * @param beforeRow the index to add the new row into
   */
  public static void insertDataRow(int beforeRow) {
    // Insert the new row
    beforeRow = dataTable.insertRow(beforeRow);

    // Set the data in the new row
    Student student = STUDENT_DATA.generateStudent();
    dataTable.setText(beforeRow, 0, student.getFirstName());
    dataTable.setText(beforeRow, 1, student.getLastName());
    dataTable.setText(beforeRow, 2, student.getAge() + "");
    dataTable.setText(beforeRow, 3, student.isMale() ? "male" : "female");
    dataTable.setText(beforeRow, 4, student.getRace());
    String color = student.getFavoriteColor();
    String colorHtml = "<FONT color=\"" + color + "\">" + color + "</FONT>";
    dataTable.setHTML(beforeRow, 5, colorHtml);
    dataTable.setText(beforeRow, 6, student.getFavoriteSport());
    dataTable.setText(beforeRow, 7, student.getCollege());
    dataTable.setText(beforeRow, 8, student.getGraduationYear() + "");
    String gpa = student.getGpa() + "";
    if (gpa.length() > 4) {
      gpa = gpa.substring(0, 4);
    }
    dataTable.setText(beforeRow, 9, gpa);
    dataTable.setText(beforeRow, 10, student.getId() + "");
    dataTable.setText(beforeRow, 11, student.getPin() + "");
  }

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    // Create the tables
    createScrollTable();

    // Add some data the data table
    dataTable.resize(0, Student.NUM_FIELDS);
    for (int i = 0; i < 15; i++) {
      insertDataRow(i);
    }

    // Redraw the scroll table
    scrollTable.redraw();

    // Add the components to the page
    RootPanel.get().add(scrollTable);
    RootPanel.get().add(new HTML("<BR>"));
    RootPanel.get().add(createTabPanel());
  }

  /**
   * Setup the footer table.
   */
  protected void createFooterTable() {
    footerTable = new FixedWidthFlexTable();
    footerTable.setHTML(0, 0, "&nbsp;");
    for (int i = 0; i < 12; i++) {
      footerTable.setText(0, i + 1, "Col " + i);
    }
  }

  /**
   * Setup the header table.
   */
  protected void createHeaderTable() {
    headerTable = new FixedWidthFlexTable();

    // Level 1 headers
    FlexCellFormatter headerFormatter = headerTable.getFlexCellFormatter();
    headerTable.setHTML(0, 0, "User Information");
    headerFormatter.setColSpan(0, 0, 13);

    // Create the select all checkbox
    final CheckBox selectAll = new CheckBox();
    selectAll.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        if (selectAll.isChecked()) {
          dataTable.selectRows();
        } else {
          dataTable.deselectRows();
        }
      }
    });

    // Level 2 headers
    headerTable.setWidget(1, 0, selectAll);
    headerFormatter.setRowSpan(1, 0, 2);
    headerFormatter.setHorizontalAlignment(1, 0,
        HasHorizontalAlignment.ALIGN_CENTER);
    headerTable.setHTML(1, 1, "First and Last Name");
    headerFormatter.setColSpan(1, 1, 2);
    headerFormatter.setRowSpan(1, 1, 2);
    headerTable.setHTML(1, 2, "General Info");
    headerFormatter.setColSpan(1, 2, 3);
    headerTable.setHTML(1, 3, "Favorite Color");
    headerFormatter.setColSpan(1, 3, 1);
    headerFormatter.setRowSpan(1, 3, 2);
    headerTable.setHTML(1, 4, "Preferred Sport");
    headerFormatter.setColSpan(1, 4, 1);
    headerFormatter.setRowSpan(1, 4, 2);
    headerTable.setHTML(1, 5, "School Info");
    headerFormatter.setColSpan(1, 5, 3);
    headerTable.setHTML(1, 6, "Login Info");
    headerFormatter.setColSpan(1, 6, 2);

    // Level 3 headers
    headerTable.setHTML(2, 0, "Age");
    headerTable.setHTML(2, 1, "Gender");
    headerTable.setHTML(2, 2, "Race");
    headerTable.setHTML(2, 3, "College");
    headerTable.setHTML(2, 4, "Year");
    headerTable.setHTML(2, 5, "GPA");
    headerTable.setHTML(2, 6, "ID");
    headerTable.setHTML(2, 7, "Pin");
  }

  /**
   * Setup the scroll table.
   */
  protected void createScrollTable() {
    // Create the inner tables
    createHeaderTable();
    createFooterTable();
    dataTable = new FixedWidthGrid();
    dataTable.setSelectionPolicy(SelectionPolicy.CHECKBOX);

    // Add the scroll table to the page
    scrollTable = new ScrollTable(dataTable, headerTable);
    scrollTable.setFooterTable(footerTable);

    setupScrollTable();
  }

  /**
   * Setup the scroll table.
   */
  protected void setupScrollTable() {
    // Setup the formatting
    scrollTable.setCellPadding(3);
    scrollTable.setCellSpacing(0);
    scrollTable.setSize("95%", "50%");
    scrollTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);

    // Set column widths
    scrollTable.setColumnWidth(0, 100);
    scrollTable.setColumnWidth(1, 100);
    scrollTable.setColumnWidth(2, 35);
    scrollTable.setColumnWidth(3, 45);
    scrollTable.setColumnWidth(4, 110);
    scrollTable.setColumnWidth(5, 80);
    scrollTable.setColumnWidth(6, 110);
    scrollTable.setColumnWidth(7, 180);
    scrollTable.setColumnWidth(8, 35);
    scrollTable.setColumnWidth(9, 35);
    scrollTable.setColumnWidth(10, 55);
    scrollTable.setColumnWidth(11, 45);
  }

  /**
   * Create a new tab panel with options to affect the grid.
   * 
   * @return the new tab panel
   */
  private TabPanel createTabPanel() {
    TabPanel panel = new TabPanel();
    panel.setWidth("95%");

    // Add the panels
    panel.add(new DemoTabResizing(), "Resizability");
    panel.add(new DemoTabColumnWidth(), "Column Width");
    panel.add(new DemoTabHighlighting(), "Highlighting");
    panel.add(new DemoTabSorting(), "Sorting");
    panel.add(new DemoTabHeaderManipulation(), "Header Manipulation");
    panel.add(new DemoTabDataManipulation(), "Data Manipulation");
    panel.add(new DemoTabPanelLog(), "Log");
    panel.selectTab(0);

    // Return the new panel
    return panel;
  }
}
