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
package com.google.gwt.gen2.demo.scrolltable.server;

import com.google.gwt.gen2.demo.scrolltable.client.RemoteTableService;
import com.google.gwt.gen2.demo.scrolltable.shared.FileTreeTableItem;
import com.google.gwt.gen2.demo.scrolltable.shared.Student;
import com.google.gwt.gen2.demo.scrolltable.shared.StudentGenerator;
import com.google.gwt.gen2.table.shared.ColumnSortInfo;
import com.google.gwt.gen2.table.shared.ColumnSortList;
import com.google.gwt.gen2.table.shared.Request;
import com.google.gwt.gen2.table.shared.SerializableResponse;
import com.google.gwt.gen2.table.shared.TreeRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Implementation of {@link RemoteTableService}.
 */
public class RemoteTableServiceImpl extends RemoteServiceServlet implements
    RemoteTableService {
  class FileComparator implements Comparator<File> {
    private final ColumnSortInfo sortInfo;

    public FileComparator(ColumnSortInfo sortInfo) {
      this.sortInfo = sortInfo;
    }

    public int compare(File file1, File file2) {
      int result = 0;
      if (sortInfo.getColumn() == 0) {
        String name1 = file1.getName();
        if (name1.length() == 0) {
          name1 = file1.getAbsolutePath();
        }
        String name2 = file1.getName();
        if (name2.length() == 0) {
          name2 = file2.getAbsolutePath();
        }
        result = name1.compareTo(name2);
      } else if (sortInfo.getColumn() == 1) {
        result = Long.valueOf(file1.length()).compareTo(
            Long.valueOf(file2.length()));
      } else if (sortInfo.getColumn() == 2) {
        result = new Date(file1.lastModified()).compareTo(new Date(
            file2.lastModified()));
      }
      return result * (sortInfo.isAscending() ? 1 : -1);
    }
  }

  private static final long serialVersionUID = 1L;

  /**
   * The source of the data.
   */
  private StudentGenerator data = new StudentGenerator() {
    @Override
    protected int getRandomInt(int max) {
      return random.nextInt(max);
    }
  };

  /**
   * A random number generator.
   */
  private Random random = new Random();

  public SerializableResponse<Student> requestRows(Request request) {
    // Get the sort info, even though we ignore it
    ColumnSortList sortList = request.getColumnSortList();
    sortList.getPrimaryColumn();
    sortList.isPrimaryAscending();

    // Return the data
    int numRows = request.getNumRows();
    return new SerializableResponse<Student>(data.generateStudents(numRows));
  }

  public SerializableResponse<FileTreeTableItem> requestTreeItems(TreeRequest request) {
    try {
      int startRow = request.getStartRow();
      int numRows = request.getNumRows();
      Set<String> invertedNodes = request.getInvertedNodes();
      boolean open = request.isOpen();

      ColumnSortList columnSortList = request.getColumnSortList();
      ColumnSortInfo columnSortInfo = columnSortList.getPrimaryColumnSortInfo();
      FileComparator comparator = null;
      File[] roots = File.listRoots();
      if (columnSortInfo != null) {
        comparator = new FileComparator(columnSortInfo);
        Arrays.sort(roots, comparator);
      }
      List<FileTreeTableItem> fileTreeItems = new ArrayList<FileTreeTableItem>();
      int row = 0;
      for (File root : roots) {
        FileTreeTableItem fileTreeItem = createFileTreeItem(root);
        fileTreeItem.setChildren(false);
        fileTreeItem.setParent(null);
        fileTreeItem.setRow(row);
        if (row >= startRow && row < startRow + numRows) {
          fileTreeItems.add(fileTreeItem);
        }
        row++;
        if (row == startRow + numRows) {
          break;
        }
        row = addChildren(fileTreeItems, fileTreeItem, root, row, startRow,
            numRows, invertedNodes, open, comparator);
      }
      return new SerializableResponse<FileTreeTableItem>(fileTreeItems, row);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private boolean isNodeOpen(String id, Set<String> invertedNodes, boolean open) {
    if (invertedNodes.contains(id)) {
      return !open;
    } else {
      return open;
    }
  }

  private int addChildren(List<FileTreeTableItem> fileTreeItems,
      FileTreeTableItem parentTreeItem, File parentFile, int row, int startRow,
      int numRows, Set<String> invertedNodes, boolean open,
      FileComparator comparator) {
    if (parentFile.isDirectory()) {
      File[] children = parentFile.listFiles();
      if (children != null) {
        if (comparator != null) {
          Arrays.sort(children, comparator);
        }
        if (children.length > 0) {
          parentTreeItem.setChildren(true);
          if (isNodeOpen(parentTreeItem.getId(), invertedNodes, open)) {
            for (File childFile : children) {
              FileTreeTableItem childTreeItem = createFileTreeItem(childFile);
              childTreeItem.setParent(parentTreeItem);
              childTreeItem.setRow(row);
              if (row >= startRow && row <= startRow + numRows) {
                fileTreeItems.add(childTreeItem);
              }
              row++;
              if (row >= startRow + numRows) {
                return row;
              }
              row = addChildren(fileTreeItems, childTreeItem, childFile, row,
                  startRow, numRows, invertedNodes, open, comparator);
            }
          }
        }
      } else {
        parentTreeItem.setChildren(false);
      }
    }
    return row;
  }

  private FileTreeTableItem createFileTreeItem(File root) {
    String name = root.getName();
    double size = root.length();
    if (name.length() == 0) {
      name = root.getAbsolutePath();
    }
    return new FileTreeTableItem(root.getAbsolutePath(), name, size, new Date(
        root.lastModified()));
  }
}
