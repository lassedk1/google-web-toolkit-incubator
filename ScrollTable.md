# Introduction #
The ScrollTable Widget is a three part table consisting of a fixed header table, a fixed footer table (optional), and a scrollable data grid.

Example:
http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src-demo/com/google/gwt/gen2/demo/scrolltable/client/ScrollTableDemo.java



# Features #

In addition to the scrolling feature, the ScrollTable supports many other features as described below.  All of the features can be enabled or disabled, and they can be accessed programatically.

### Complex Headers and Footers ###

The header and footer tables support colspans and rowspans, which allows you to make them as complex as needed.  For example, you can have a column headers that are grouped into sections.

### Row Selection ###

The data portion supports selection of rows via the mouse.  If multiple row selection is enabled, users can select multiple rows using the ctrl key, or they can shift click to select a range of rows.

### Row and Cell Highlighting ###

Rows or cells can be highlighted on mouseover.  This feature can be used with row selection.

### Column Resizing and Auto Resizing ###

Users can drag the edge of a column header to resize the column.  If the header spans multiple columns, all columns will be resized proportional to their original sizes.  The ScrollTable supports many resize policies as well.  You can allow users to resize the entire grid, or confine the grid to a specific width and allow users to redistribute the space between the columns.

In addition, users can double click on the edge of a header cell to auto resize the column to the minimum width required to fit the content.

### Multi Column Sorting ###

The ScrollTable supports sorting of multiple columns.  As the user clicks on columns, the grid keeps track of a ColumnSortList, which contains the sequential list of all columns that have been sorted, and their sort order.

You can specify your own ColumnSorter or use the build in ColumnSorter, which sorts using native Javascript comparisons.



# Setup Instructions #

Add these line to your module.gwt.xml file:
```
  <inherits name='com.google.gwt.widgetideas.WidgetIdeas'/>
  <inherits name='com.google.gwt.widgetideas.ScrollTable'/>

  <stylesheet src='ScrollTableDemo.css'/>
```

In your public directory, include the following stylesheet:
[ScrollTableDemo.css](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/demo/ScrollTable/ScrollTableDemo.css)

Create a subdirectory called "images" and add the background image:
[bg\_header\_gradient.gif](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/demo/ScrollTable/images/bg_header_gradient.gif)



# Sample Code #
See the demo code for a complete sample.  This sample code produces a grid containing data on 100 fictional students, with cumulative averages.

```
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyEntryPoint implements EntryPoint {
  /**
   * A list of first names.
   */
  private static final String[] FIRST_NAMES = {
      "Joel", "John", "Kelly", "Freeland", "Bruce", "Rajeev"};

  /**
   * A list of last names.
   */
  private static final String[] LAST_NAMES = {
      "Smith", "Paterson", "Haterson", "Raterson", "Johnson", "Sonson"};

  /**
   * A list of towns.
   */
  private static final String[] TOWNS = {
      "Atlanta", "New York", "Dubai", "Moscow", "Venice", "Old York"};

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    // Get the components of the ScrollTable
    FixedWidthFlexTable headerTable = createHeaderTable();
    FixedWidthFlexTable footerTable = createFooterTable();
    FixedWidthGrid dataTable = createDataTable();

    // Combine the components into a ScrollTable
    ScrollTable scrollTable = new ScrollTable(dataTable, headerTable);
    scrollTable.setFooterTable(footerTable);

    // Set some options in the scroll table
    scrollTable.setSortingEnabled(true);
    scrollTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);
    scrollTable.setSize("80%", "80%");

    // Add some data to the data table
    int numStudents = 100;
    int totalAge = 0;
    double totalGPA = 0;
    for (int i = 0; i < numStudents; i++) {
      // Generate stats
      int age = (18 + Random.nextInt(4));
      totalAge += age;
      double gpa = 1.0 + 3.0 * Random.nextDouble();
      totalGPA += gpa;

      // Set the data in the current row
      dataTable.setHTML(i, 0, LAST_NAMES[Random.nextInt(LAST_NAMES.length)]);
      dataTable.setHTML(i, 1, FIRST_NAMES[Random.nextInt(FIRST_NAMES.length)]);
      dataTable.setHTML(i, 2, age + "");
      dataTable.setHTML(i, 3, TOWNS[Random.nextInt(TOWNS.length)]);
      dataTable.setHTML(i, 4, (100000 + Random.nextInt(899999)) + "");
      dataTable.setHTML(i, 5, ((int) (gpa * 100.0)) / 100.0 + "");
    }

    // Set the stats in the footer table
    double averageAge = (100 * totalAge / numStudents) / 100.0;
    double averageGPA = ((int) (100.0 * totalGPA / numStudents)) / 100.0;
    footerTable.setHTML(0, 1, averageAge + "");
    footerTable.setHTML(0, 3, averageGPA + "");
    footerTable.setHTML(1, 1, numStudents + "");

    // Setup some default widths
    scrollTable.setColumnWidth(2, 50);
    scrollTable.setColumnWidth(5, 50);

    // Add the components to the page
    RootPanel.get().add(scrollTable);
  }

  /**
   * Create the data table.
   * 
   * @return a data table
   */
  private FixedWidthGrid createDataTable() {
    // Create a new table
    FixedWidthGrid dataTable = new FixedWidthGrid();

    // Set some options in the data table
    dataTable.setSelectionPolicy(SelectionGrid.SelectionPolicy.MULTI_ROW);

    // Return the data table
    return dataTable;
  }

  /**
   * Create the footer table.
   * 
   * @return a footer table
   */
  private FixedWidthFlexTable createFooterTable() {
    // Create a new table
    FixedWidthFlexTable footerTable = new FixedWidthFlexTable();
    FlexCellFormatter formatter = footerTable.getFlexCellFormatter();

    // Level 1 headers
    footerTable.setHTML(0, 0, "<b>Average Age:</b>");
    formatter.setColSpan(0, 0, 2);
    formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
    footerTable.setHTML(0, 1, "0");
    footerTable.setHTML(0, 2, "<b>Average GPA:</b>");
    formatter.setColSpan(0, 2, 2);
    formatter.setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);
    footerTable.setHTML(0, 3, "0");

    // Level 2 headers
    footerTable.setHTML(1, 0, "<b>Number of Students:</b>");
    formatter.setColSpan(1, 0, 5);
    formatter.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
    footerTable.setHTML(1, 1, "0");

    // Return the footer table
    return footerTable;
  }

  /**
   * Create the header table.
   * 
   * @return a header table
   */
  private FixedWidthFlexTable createHeaderTable() {
    // Create a new table
    FixedWidthFlexTable headerTable = new FixedWidthFlexTable();
    FlexCellFormatter formatter = headerTable.getFlexCellFormatter();

    // Level 1 headers
    headerTable.setHTML(0, 0, "<b>Student Profiles</b>");
    formatter.setColSpan(0, 0, 6);
    formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

    // Level 2 headers
    headerTable.setHTML(1, 0, "<b>General</b>");
    formatter.setColSpan(1, 0, 4);
    formatter.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
    headerTable.setHTML(1, 1, "<b>Student ID</b>");
    formatter.setRowSpan(1, 1, 2);
    formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);
    headerTable.setHTML(1, 2, "<b>GPA</b>");
    formatter.setRowSpan(1, 2, 2);
    formatter.setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_CENTER);

    // Level 3 headers
    headerTable.setHTML(2, 0, "Last Name");
    formatter.setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
    headerTable.setHTML(2, 1, "First Name");
    formatter.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);
    headerTable.setHTML(2, 2, "Age");
    formatter.setHorizontalAlignment(2, 2, HasHorizontalAlignment.ALIGN_CENTER);
    headerTable.setHTML(2, 3, "Hometown");
    formatter.setHorizontalAlignment(3, 3, HasHorizontalAlignment.ALIGN_CENTER);

    // Return the header table
    return headerTable;
  }
}
```








