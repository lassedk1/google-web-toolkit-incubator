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
package com.google.gwt.demos.scrolltable.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;

import java.util.Iterator;

/**
 * An iterator that serves as the data source for TableOracle requests.
 */
public class DataSourceIterator implements Iterator {
  public static final String[] colleges = {
      "Georgia", "Connecticut", "Arizona", "Florida", "Wisconsin", "Maryland",
      "Tennessee", "Arkansas", "Virginia", "Maine", "Texes", "South Carolina"};

  public static final String[] colors = {
      "red", "blue", "green", "orange", "purple"};

  public static final String[] firstNames = {
      "Arnold", "Bob", "Cathy", "Dennis", "Earl", "Frank", "George", "Hillary",
      "Irma", "John", "Kelly", "Michelle", "Natalie", "Oscar", "Paul", "Susan",
      "Todd"};

  public static final String[] lastNames = {
      "Anderson", "Billings", "Corper", "Donavon", "Elkins", "Fitzgerald",
      "Gaskins", "Haskins", "Iterby", "Johnson", "Kellickson", "Lee",
      "Peterson", "Richardson", "Sauxby", "Wilkinson", "York"};

  public static final String[] sports = {
      "baseball", "soccor", "football", "basketball", "hockey", "softball",
      "rugby", "water polo", "golf", "tennis"};

  public static final String[] races = {
      "African American", "Caucasian", "Hispanic", "Green Alien", "Asian",
      "Other"};

  /**
   * An iterator that iterates over a single row.
   */
  private class RowIterator implements Iterator {
    /**
     * The current row that will be returns on the next call to next.
     */
    int curCell = 0;

    /**
     * The last row to iterate.
     */
    int row;

    /**
     * Constructor.
     * 
     * @param row the row this iterator represents
     */
    public RowIterator(int row) {
      this.row = row;
    }

    public boolean hasNext() {
      return curCell < numColumns;
    }

    public Object next() {
      if (hasNext()) {
        int cell = curCell;
        curCell++;
        switch (cell) {
          case 0: 
            final String firstName = firstNames[Random.nextInt(firstNames.length)];
            CheckBox cb = new CheckBox(firstName) {
              public void onBrowserEvent(Event event) {
                super.onBrowserEvent(event);
                if (DOM.eventGetType(event) == Event.ONCLICK) {
                  Window.alert("Hey " + getText());
                }
              }
            };
            return cb;
          case 1:
            return lastNames[Random.nextInt(lastNames.length)];
          case 2:
            return Random.nextInt(100) + "";
          case 3:
            if (Random.nextBoolean()) {
              return "male";
            } else {
              return "female";
            }
          case 4:
            return races[Random.nextInt(races.length)];
          case 5:
            return colors[Random.nextInt(colors.length)];
          case 6:
            return sports[Random.nextInt(sports.length)];
          case 7:
            return "University of " + colleges[Random.nextInt(colleges.length)];
          case 8:
            return (1990 + Random.nextInt(20)) + "";
          case 9:
            return (int) (40 * Random.nextDouble()) / 10.0 + "";
          case 10:
            return (1000000 + Random.nextInt(8999999)) + "";
          case 11:
            return (1000 + Random.nextInt(8999)) + "";
          default:
            return null;
        }
      }
      return null;
    }

    public void remove() {
      throw new UnsupportedOperationException("Remove not supported.");
    }
  }

  /**
   * The current row that will be returns on the next call to next.
   */
  private int curRow;

  /**
   * The last row to iterate.
   */
  private int lastRow;

  /**
   * The number of columns.
   */
  private int numColumns;

  /**
   * Constructor.
   * 
   * @param firstRow the first row of data
   * @param lastRow the last row of data
   */
  public DataSourceIterator(int firstRow, int lastRow, int numColumns) {
    this.curRow = firstRow - 1;
    this.lastRow = lastRow;
    this.numColumns = numColumns;
  }

  public boolean hasNext() {
    return curRow < lastRow;
  }

  public Object next() {
    if (hasNext()) {
      curRow++;
      return new RowIterator(curRow);
    }
    return null;
  }

  public void remove() {
    throw new UnsupportedOperationException("Remove not supported.");
  }
}
