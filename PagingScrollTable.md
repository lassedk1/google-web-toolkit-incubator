|Demo:| [live demo](http://collectionofdemos.appspot.com/demo/com.google.gwt.gen2.demo.scrolltable.PagingScrollTableDemo/PagingScrollTableDemo.html)|
|:----|:--------------------------------------------------------------------------------------------------------------------------------------------|
|Java doc: | [java doc](http://collectionofdemos.appspot.com/javadoc/com/google/gwt/gen2/table/client/PagingScrollTable.html)                            |
|Module:| [Table](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/src/com/google/gwt/gen2/table/Table.gwt.xml)                           |

**NOTE**: This documentation is not yet finished

# Introduction #

The gen2 PagingScrollTable supports navigating between pages of data, which can be generated on the client or via an RPC call.  It also supports column resizing within a minimum and maximum range, column sorting on the client or server side, inline cell editing, data caching, and scrolling with a fixed header and footer.  The data can be rendered very quickly as a String using a TableBulkRenderer.

Note: You can view documentation on the old version of the PagingScrollTable on the  PagingScrollTableOld wiki page.


# How It Works #

## Class Diagram ##

Coming soon...

## Row Values ##

A PagingScrollTable can represent a list of any type, which we call the row value.  For example, you might define a `Student` object to use as the row value.  Each column will contain information about the row value, such as the name, age, and GPA of the `Student`.

## TableModel ##

The [TableModel](http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src/com/google/gwt/gen2/table/client/TableModel.java) defines how the PagingScrollTable accesses data.  The method `requestRows(Request, Callback)` is called any time the PagingScrollTable needs more data.  From here, you can return data from a data source on the client, or you can send an RPC request for the data.

The `Request` includes the `ColumnSortInfo` from the PagingScrollTable, allowing you to perform server side sorting.

## ColumnDefinition ##

The [ColumnDefinition](http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src/com/google/gwt/gen2/table/client/ColumnDefinition.java) defines information about the columns in your table, such as the minimum/maximum/preferred column width, whether the column is sortable, and how to set and retrieve data from a row value.  For example, a `ColumnDefinition` used for the name column in a PagingScrollTable would define how to get and set the name of a `Student`.  The [AbstractColumnDefinition](http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src/com/google/gwt/gen2/table/client/AbstractColumnDefinition.java) is a basic implementation that allows you to get and set the minimum/maximum/preferred column widths.

You can also attach a [CellRenderer](http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src/com/google/gwt/gen2/table/client/CellRenderer.java) or [CellEditor](http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src/com/google/gwt/gen2/table/client/CellEditor.java) to a ColumnDefinition to control how it will appear.  The [InlineCellEditors](http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src/com/google/gwt/gen2/table/client/InlineCellEditor.java) (eg. [TextCellEditor](http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src/com/google/gwt/gen2/table/client/TextCellEditor.java)) are simple CellEditor implementations that popup input fields above the cell.

[ColumnDefinitions](http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src/com/google/gwt/gen2/table/client/ColumnDefinition.java) are combined into a [TableDefinition](http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src/com/google/gwt/gen2/table/client/TableDefinition.java), which defines which columns will appear in your table via the `getVisibleColumnDefinitions()` method.  The [DefaultTableDefinition](http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src/com/google/gwt/gen2/table/client/DefaultTableDefinition.java) implementation provides convenience methods for adding, removing, and hiding columns.

## TableBulkRenderer ##

The [TableBulkRenderer](http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src/com/google/gwt/gen2/table/client/TableBulkRenderer.java) allows you to render your PagingScrollTable as an HTML string, which dramatically increases performance of large tables.  Using the CellRenderers defined in the ColumnDefinitions, you can customize the look of each column or cell based on the contents.

## PagingScrollTable ##

When all of the components are combined, you get a PagingScrollTable that requests data from the TableModel and renders the columns defined the TableDefinition using the TableBulkRenderer for performance.



# A Note about Layout Issues #

As many developers have noticed, the PagingScrollTable has a layout issue when it is relatively sized and placed inside of a TD cell (such as inside of a GWT Panel) where it will continuously expand, shrink, or otherwise behave badly.  The root cause is that the PagingScrollTable pushes out on the TD cells from the inside instead of resizing with the cells.

Why haven't we fixed this issue if we know the root cause?  We're trying to find a solution that works on all browsers, but we have not found a perfect solution yet.

The simple workaround is to set the width of the PagingScrollTable in pixels.  Of course that isn't ideal, but its the easiest fix for many layout bugs.  In our sample, we use a WindowResizeListener to set the size of the table relative to the client width of the Window.



# Walkthrough #

## Create a GWT project ##

This tutorial assumes that you are familiar with GWT (Google Web Toolkit).  However, if you've stumbled across this page without ever using GWT, you can find more information at the [GWT Homepage](http://code.google.com/webtoolkit/).

Follow the instructions in the GWT [Getting Started](http://code.google.com/webtoolkit/gettingstarted.html) guide to create a new project.  Or, you can add the PagingScrollTable to an existing project.  This tutorial will use a GWT project module:
`com.google.gwt.walkthrough.Walkthrough`

## Checkout the incubator project ##

This tutorial also assumes that you are familiar with incubator and subversion (svn).  However, if you've never used incubator or svn, you can follow these steps:
  1. Create a directory called `incubator/`
  1. From within the directory, run the svn command:
"svn checkout http://google-web-toolkit-incubator.googlecode.com/svn/trunk/ trunk"
  1. From within the trunk director, run the command "ant"
  1. Include the incubator.jar file from the `trunk/build/dist/` directory into your project

If you've never used subversion, you can find a variety of free svn clients online for all operating systems at the [subversion homepage](http://subversion.tigris.org/).  TortoiseSVN is an easy to use visual client for Windows.  Most linux installations come with an svn client preinstalled.


## Define a row value ##

From within the project, define a data type to display in each row.  You can use any data type, so let's use a `Student` for this walkthrough.

```
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
package com.google.gwt.walkthrough.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * A representation of a student.
 */
public class Student implements IsSerializable {
  private String firstName = null;
  private String lastName = null;
  private int age = 0;
  private boolean isMale = true;
  private int graduationYear = 0;
  private double gpa = 0.0;

  public int getAge() {
    return age;
  }

  public String getFirstName() {
    return firstName;
  }

  public double getGpa() {
    return gpa;
  }

  public int getGraduationYear() {
    return graduationYear;
  }

  public String getLastName() {
    return lastName;
  }

  public boolean isMale() {
    return isMale;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setGpa(double gpa) {
    this.gpa = gpa;
  }

  public void setGraduationYear(int graduationYear) {
    this.graduationYear = graduationYear;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setMale(boolean isMale) {
    this.isMale = isMale;
  }
}
```

You'll notice that the above type implements IsSerializable so we can send it over RPC and do server side sorting.  Since we want to use this data type on both the server and client side, let's create a `shared` package on the same level as the `client` package.  You're path should look like this:

```
com.google.gwt.walkthrough
  public/
  Walkthrough.gwt.xml
  com.google.gwt.walkthrough.client
    Walkthrough
  com.google.gwt.walkthrough.shared
    Student
```

Go ahead and add the `Student` file now.

## Define the TableModel ##

The `TableModel` defines the source of the data.

com.google.gwt.walkthrough
  public/
  Walkthrough.gwt.xml
  com.google.gwt.walkthrough.client
    Walkthrough
  com.google.gwt.walkthrough.shared
    Student
}}}```