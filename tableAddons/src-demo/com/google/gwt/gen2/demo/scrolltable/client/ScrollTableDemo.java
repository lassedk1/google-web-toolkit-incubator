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
import com.google.gwt.gen2.demo.scrolltable.client.option.AbstractOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.column.CellPaddingAndSpacingOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.column.ColumnResizePolicyOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.column.ResizeColumnOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.column.TableResizePolicyOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.data.InsertDataRowOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.data.SetDataTextOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.header.ColSpanOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.header.InsertHeaderRowOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.header.SetHeaderTextOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.highlight.RowSelectionPolicyOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.log.LogOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.setup.ResizeCheckingOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.setup.ScrollPolicyOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.setup.TableSizeOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.sort.ShiftRowsOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.sort.SortColumnOption;
import com.google.gwt.gen2.demo.scrolltable.client.option.sort.SwapRowsOption;
import com.google.gwt.gen2.demo.scrolltable.shared.Student;
import com.google.gwt.gen2.demo.scrolltable.shared.StudentGenerator;
import com.google.gwt.gen2.table.client.AbstractScrollTable;
import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.gen2.table.override.client.FlexTable;
import com.google.gwt.gen2.table.override.client.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashMap;
import java.util.Map;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ScrollTableDemo implements EntryPoint {
  /**
   * The height of the main menu (and table) in pixels.
   */
  private static final int MENU_HEIGHT = 400;

  /**
   * The width of the main menu in pixels.
   */
  private static final int MENU_WIDTH = 200;

  /**
   * The source of {@link Student} data.
   */
  private static final StudentGenerator STUDENT_DATA = new StudentGenerator() {
    @Override
    public int getRandomInt(int max) {
      return Random.nextInt(max);
    }
  };

  /**
   * The instance of this class.
   */
  private static ScrollTableDemo instance = null;

  /**
   * @return the instance of this class
   */
  public static ScrollTableDemo get() {
    return instance;
  }

  /**
   * A Mapping of {@link TreeItem} in the menu to their associated options.
   */
  private Map<TreeItem, Widget> optionMap = new HashMap<TreeItem, Widget>();

  /**
   * The panel that holds the visible option.
   */
  private SimplePanel optionWrapper = new SimplePanel();

  /**
   * The main layout panel.
   */
  private FlexTable layout = new FlexTable();

  /**
   * The scroll table.
   */
  private AbstractScrollTable scrollTable = null;

  /**
   * @return the data table.
   */
  public FixedWidthGrid getDataTable() {
    return getScrollTable().getDataTable();
  }

  /**
   * @return the footer table.
   */
  public FixedWidthFlexTable getFooterTable() {
    return getScrollTable().getFooterTable();
  }

  /**
   * @return the header table.
   */
  public FixedWidthFlexTable getHeaderTable() {
    return getScrollTable().getHeaderTable();
  }

  /**
   * @return the scroll table.
   */
  public AbstractScrollTable getScrollTable() {
    return scrollTable;
  }

  /**
   * Add a row of data cells each consisting of a string that describes the
   * row:column coordinates of the new cell. The number of columns in the new
   * row will match the number of columns in the grid.
   * 
   * @param beforeRow the index to add the new row into
   */
  public void insertDataRow(int beforeRow) {
    // Insert the new row
    FixedWidthGrid dataTable = getDataTable();
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
    instance = this;

    // Add the main layout to the page
    layout.setWidth("99%");
    layout.setCellPadding(0);
    layout.setCellSpacing(5);
    final FlexCellFormatter formatter = layout.getFlexCellFormatter();
    RootPanel.get().add(layout);

    // Initialize the options menu
    {
      // Create the options menu
      Tree menu = new Tree();

      // Attach a tree listener
      menu.addTreeListener(new TreeListener() {
        public void onTreeItemSelected(TreeItem item) {
          Widget option = optionMap.get(item);
          if (option != null) {
            optionWrapper.setWidget(option);
          }
        }

        public void onTreeItemStateChanged(TreeItem item) {
        }
      });

      // Add it to the layout inside a scroll panel
      ScrollPanel scrollPanel = new ScrollPanel(menu);
      scrollPanel.setAlwaysShowScrollBars(true);
      scrollPanel.setPixelSize(MENU_WIDTH, MENU_HEIGHT);
      scrollPanel.getElement().getStyle().setProperty("border",
          "1px solid #999");
      layout.setWidget(0, 0, scrollPanel);
      formatter.setWidth(0, 0, MENU_WIDTH + "px");
      formatter.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);

      // Initialize the options in the menu
      initOptions(menu);
    }

    // Initialize the tables
    {
      // Create the tables
      FixedWidthFlexTable headerTable = createHeaderTable();
      FixedWidthFlexTable footerTable = createFooterTable();
      FixedWidthGrid dataTable = createDataTable();
      scrollTable = createScrollTable(headerTable, dataTable, footerTable);
      scrollTable.setHeight("400px");

      // Add the scroll table to the layout
      layout.setWidget(0, 1, scrollTable);
      formatter.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
    }

    // Initialize the options area
    {
      optionWrapper.getElement().getStyle().setProperty("borderTop",
          "3px solid #aaa");
      optionWrapper.setWidget(new Label("Select an option from the menu"));
      layout.setWidget(1, 0, optionWrapper);
      formatter.setColSpan(1, 0, 2);
      formatter.setHorizontalAlignment(1, 0,
          HasHorizontalAlignment.ALIGN_CENTER);
    }

    // Resize the table on window resize
    {
      WindowResizeListener wrl = new WindowResizeListener() {
        public void onWindowResized(int width, int height) {
          width = (int) (width * 0.95) - MENU_WIDTH - 10;
          scrollTable.setWidth(width + "px");
          formatter.setWidth(0, 1, width + "px");
          scrollTable.redraw();
        }
      };
      Window.addWindowResizeListener(wrl);
      wrl.onWindowResized(Window.getClientWidth(), Window.getClientHeight());
    }

    // Do any required post processing
    onModuleLoaded();
  }

  /**
   * @return the newly created data table.
   */
  protected FixedWidthGrid createDataTable() {
    FixedWidthGrid dataTable = new FixedWidthGrid();
    dataTable.setSelectionPolicy(SelectionPolicy.CHECKBOX);
    return dataTable;
  }

  /**
   * @return the new footer table
   */
  protected FixedWidthFlexTable createFooterTable() {
    FixedWidthFlexTable footerTable = new FixedWidthFlexTable();
    footerTable.setHTML(0, 0, "&nbsp;");
    for (int i = 0; i < 12; i++) {
      footerTable.setText(0, i + 1, "Col " + i);
    }
    return footerTable;
  }

  /**
   * @return the new header table
   */
  protected FixedWidthFlexTable createHeaderTable() {
    FixedWidthFlexTable headerTable = new FixedWidthFlexTable();

    // Level 1 headers
    FlexCellFormatter headerFormatter = headerTable.getFlexCellFormatter();
    headerTable.setHTML(0, 0, "User Information");
    headerFormatter.setColSpan(0, 0, 13);

    // Create the select all checkbox
    final CheckBox selectAll = new CheckBox();
    selectAll.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        if (selectAll.isChecked()) {
          getDataTable().selectAllRows();
        } else {
          getDataTable().deselectAllRows();
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

    return headerTable;
  }

  /**
   * Setup the scroll table.
   */
  protected AbstractScrollTable createScrollTable(
      FixedWidthFlexTable headerTable, FixedWidthGrid dataTable,
      FixedWidthFlexTable footerTable) {
    // Add the scroll table to the page
    ScrollTable theScrollTable = new ScrollTable(dataTable, headerTable);
    theScrollTable.setFooterTable(footerTable);

    // Setup the formatting
    theScrollTable.setCellPadding(3);
    theScrollTable.setCellSpacing(0);
    theScrollTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);

    // first name
    theScrollTable.setMinimumColumnWidth(0, 50);
    theScrollTable.setPreferredColumnWidth(0, 100);

    // last name
    theScrollTable.setMinimumColumnWidth(1, 50);
    theScrollTable.setPreferredColumnWidth(1, 100);

    // age
    theScrollTable.setMinimumColumnWidth(2, 35);
    theScrollTable.setPreferredColumnWidth(2, 35);
    theScrollTable.setMaximumColumnWidth(2, 35);

    // gender
    theScrollTable.setMinimumColumnWidth(3, 45);
    theScrollTable.setPreferredColumnWidth(3, 45);
    theScrollTable.setMaximumColumnWidth(3, 45);

    // race
    theScrollTable.setMinimumColumnWidth(4, 45);
    theScrollTable.setPreferredColumnWidth(4, 45);
    theScrollTable.setMaximumColumnWidth(4, 45);

    // color
    theScrollTable.setPreferredColumnWidth(5, 80);

    // sport
    theScrollTable.setMinimumColumnWidth(6, 40);
    theScrollTable.setPreferredColumnWidth(6, 110);

    // college
    theScrollTable.setMinimumColumnWidth(7, 50);
    theScrollTable.setPreferredColumnWidth(7, 180);
    theScrollTable.setMaximumColumnWidth(7, 250);

    // year
    theScrollTable.setPreferredColumnWidth(8, 25);

    // gpa
    theScrollTable.setPreferredColumnWidth(9, 35);

    // id
    theScrollTable.setPreferredColumnWidth(10, 55);

    // pin
    theScrollTable.setPreferredColumnWidth(11, 45);

    return theScrollTable;
  }

  /**
   * @return the main layout panel
   */
  protected FlexTable getLayout() {
    return layout;
  }

  /**
   * Initialize the main menu.
   * 
   * @param menu the main menu
   */
  protected void initOptions(Tree menu) {
    // Setup
    {
      TreeItem root = menu.addItem("ScrollTable setup");
      mapOption(root.addItem("Resize Checking"), new ResizeCheckingOption());
      mapOption(root.addItem("Scroll Policy"), new ScrollPolicyOption());
      mapOption(root.addItem("Table Size"), new TableSizeOption());
    }

    // Column Widths
    {
      TreeItem root = menu.addItem("Column Width");
      mapOption(root.addItem("Table Resize Policy"),
          new TableResizePolicyOption());
      mapOption(root.addItem("Column Resize Policy"),
          new ColumnResizePolicyOption());
      mapOption(root.addItem("Resize Column"), new ResizeColumnOption());
      mapOption(root.addItem("Cell Padding and Spacing"),
          new CellPaddingAndSpacingOption());
    }

    // Highlighting
    {
      TreeItem root = menu.addItem("Highlighting & Selection");
      mapOption(root.addItem("Row Selection Policy"),
          new RowSelectionPolicyOption());
    }

    // Sorting
    {
      TreeItem root = menu.addItem("Column Sorting");
      mapOption(root.addItem("Shift Rows"), new ShiftRowsOption());
      mapOption(root.addItem("Swap Rows"), new SwapRowsOption());
      mapOption(root.addItem("Sort Column"), new SortColumnOption());
    }

    // Data Manipulation
    {
      TreeItem root = menu.addItem("Data Manipulation");
      mapOption(root.addItem("Set Text"), new SetDataTextOption());
      mapOption(root.addItem("Insert Row"), new InsertDataRowOption());
    }

    // Header Manipulation
    {
      TreeItem root = menu.addItem("Header Manipulation");
      mapOption(root.addItem("Set Text"), new SetHeaderTextOption());
      mapOption(root.addItem("Insert Row"), new InsertHeaderRowOption());
      mapOption(root.addItem("Set Row/ColSpan"), new ColSpanOption());
    }

    // Log
    {
      mapOption(menu.addItem("Log"), new LogOption());
    }
  }

  /**
   * Map a {@link TreeItem} to an option.
   * 
   * @param item the {@link TreeItem}
   * @param option the {@link Widget} to display
   */
  protected void mapOption(TreeItem item, AbstractOption option) {
    optionMap.put(item, option);
  }

  /**
   * Called when the module has finished loading.
   */
  protected void onModuleLoaded() {
    // Add some data to the data table
    getDataTable().resize(0, Student.NUM_FIELDS);
    for (int i = 0; i < 15; i++) {
      insertDataRow(i);
    }
  }
}
