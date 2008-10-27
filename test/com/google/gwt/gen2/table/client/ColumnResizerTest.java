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
package com.google.gwt.gen2.table.client;

import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.gen2.table.client.ColumnResizer.ColumnWidthInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests methods in the {@link ColumnResizer} class.
 */
public class ColumnResizerTest extends Gen2TestBase {
  /**
   * Test the column width info.
   */
  public void testCurrentColumnWidthInfo() {
    // getPercentageDifference
    {
      ColumnWidthInfo info = new ColumnWidthInfo(100, 300, 200, 150);

      // getters
      assertEquals(150, info.getCurrentWidth());
      assertEquals(100, info.getMinimumWidth());
      assertEquals(300, info.getMaximumWidth());
      assertEquals(200, info.getPreferredWidth());

      // percentage difference
      info.setNewWidth(150);
      assertEquals(-0.25, info.getPercentageDifference(), 0.001);

      // required width
      info.setRequiredWidth(40);
      assertEquals(40, info.getRequiredWidth());

      // new width
      info.setNewWidth(190);
      assertEquals(190, info.getNewWidth());
    }
  }

  /**
   * Verify that we can distribute a negative width.
   */
  public void testDistributeNegativeWidth() {
    // Create some column info
    ColumnResizer resizer = new ColumnResizer();
    List<ColumnWidthInfo> columns = new ArrayList<ColumnWidthInfo>();
    columns.add(new ColumnWidthInfo(100, 300, 200, 150));
    columns.add(new ColumnWidthInfo(0, 100, 50, 50));
    columns.add(new ColumnWidthInfo(0, 500, 300, 151));

    // Only the first column gets any width, goes to -10%
    {
      assertEquals(0, resizer.distributeWidth(columns, -5));
      assertEquals(150, columns.get(0).getNewWidth());
      assertEquals(45, columns.get(1).getNewWidth());
      assertEquals(151, columns.get(2).getNewWidth());
    }

    // First two columns get width, go to -30%
    {
      assertEquals(0, resizer.distributeWidth(columns, -25));
      assertEquals(140, columns.get(0).getNewWidth(), 1);
      assertEquals(35, columns.get(1).getNewWidth(), 1);
      assertEquals(151, columns.get(2).getNewWidth(), 1);
    }

    // All columns get width, go to -50%
    {
      assertEquals(0, resizer.distributeWidth(columns, -76));
      assertEquals(100, columns.get(0).getNewWidth());
      assertEquals(25, columns.get(1).getNewWidth());
      assertEquals(150, columns.get(2).getNewWidth());
    }

    // Too much width to distribute
    {
      assertEquals(-49, resizer.distributeWidth(columns, -300));
      assertEquals(100, columns.get(0).getNewWidth());
      assertEquals(0, columns.get(1).getNewWidth());
      assertEquals(0, columns.get(2).getNewWidth());
    }

    // Difference results in columns out of proportion by 1
    {
      assertEquals(0, resizer.distributeWidth(columns, -8));
      assertEquals(149, columns.get(0).getNewWidth(), 1);
      assertEquals(43, columns.get(1).getNewWidth(), 1);
      assertEquals(151, columns.get(2).getNewWidth(), 1);
    }

    // Difference results in columns out of proportion by 2
    {
      assertEquals(0, resizer.distributeWidth(columns, -78));
      assertEquals(99, columns.get(0).getNewWidth(), 1);
      assertEquals(24, columns.get(1).getNewWidth(), 1);
      assertEquals(150, columns.get(2).getNewWidth(), 1);
    }
  }

  /**
   * Verify that we can distribute a positive width.
   */
  public void testDistributePositiveWidth() {
    // Create some column info
    ColumnResizer resizer = new ColumnResizer();
    List<ColumnWidthInfo> columns = new ArrayList<ColumnWidthInfo>();
    columns.add(new ColumnWidthInfo(100, 300, 200, 150));
    columns.add(new ColumnWidthInfo(0, 100, 50, 50));
    columns.add(new ColumnWidthInfo(0, 500, 300, 100));

    // Only the first column gets any width, goes to -50%
    {
      assertEquals(0, resizer.distributeWidth(columns, 50));
      assertEquals(150, columns.get(0).getNewWidth());
      assertEquals(50, columns.get(1).getNewWidth());
      assertEquals(150, columns.get(2).getNewWidth());
    }

    // First two columns get width, go to -10%
    {
      assertEquals(0, resizer.distributeWidth(columns, 200));
      assertEquals(180, columns.get(0).getNewWidth());
      assertEquals(50, columns.get(1).getNewWidth());
      assertEquals(270, columns.get(2).getNewWidth());
    }

    // All columns get width, go to +10%
    {
      assertEquals(0, resizer.distributeWidth(columns, 305));
      assertEquals(220, columns.get(0).getNewWidth());
      assertEquals(55, columns.get(1).getNewWidth());
      assertEquals(330, columns.get(2).getNewWidth());
    }

    // Too much width to distribute
    {
      assertEquals(400, resizer.distributeWidth(columns, 1000));
      assertEquals(300, columns.get(0).getNewWidth());
      assertEquals(100, columns.get(1).getNewWidth());
      assertEquals(500, columns.get(2).getNewWidth());
    }

    // Difference results in columns out of proportion by 1
    {
      assertEquals(0, resizer.distributeWidth(columns, 201));
      assertEquals(180, columns.get(0).getNewWidth());
      assertEquals(50, columns.get(1).getNewWidth());
      assertEquals(271, columns.get(2).getNewWidth());
    }

    // Difference results in columns out of proportion by 2
    {
      assertEquals(0, resizer.distributeWidth(columns, 251));
      assertEquals(200, columns.get(0).getNewWidth());
      assertEquals(50, columns.get(1).getNewWidth());
      assertEquals(301, columns.get(2).getNewWidth());
    }
  }

  /**
   * Verify that we can distribute a zero width.
   */
  public void testDistributeZeroWidth() {
    // Create some column info
    List<ColumnWidthInfo> columns = new ArrayList<ColumnWidthInfo>();
    columns.add(new ColumnWidthInfo(100, 300, 200, 150));
    columns.add(new ColumnWidthInfo(0, 100, 50, 50));
    columns.add(new ColumnWidthInfo(0, 500, 300, 200));

    // Sort the column
    ColumnResizer resizer = new ColumnResizer();
    resizer.distributeWidth(columns, 0);

    // Check outputs
    assertEquals(150, columns.get(0).getNewWidth());
    assertEquals(50, columns.get(1).getNewWidth());
    assertEquals(200, columns.get(2).getNewWidth());
  }
}
