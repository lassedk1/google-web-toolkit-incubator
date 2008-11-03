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

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A helper class that provides all of the inner classes used by
 * {@link TableModel}. These classes cannot be included in the
 * {@link TableModel} class itself because of a JDT error that prevents the GWT
 * RPC generator from creating implementations of remote services:
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=243820
 * 
 * This class should be removed once this bug is fixed.
 */
public final class TableModelHelper {
  /**
   * Information about the sort order of a specific column in a table.
   */
  public static class ColumnSortInfo implements IsSerializable {
    /**
     * True if the sort order is ascending.
     */
    private boolean ascending;

    /**
     * The column index.
     */
    private int column;

    /**
     * Default constructor used for RPC.
     */
    public ColumnSortInfo() {
      this(0, true);
    }

    /**
     * Construct a new {@link ColumnSortInfo}.
     * 
     * @param column the column index
     * @param ascending true if sorted ascending
     */
    public ColumnSortInfo(int column, boolean ascending) {
      this.column = column;
      this.ascending = ascending;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof ColumnSortInfo) {
        return equals((ColumnSortInfo) obj);
      }
      return false;
    }

    /**
     * Check if this object is equal to another. The objects are equal if the
     * column and ascending values are equal.
     * 
     * @param csi the other object
     * @return true if objects are the same
     */
    public boolean equals(ColumnSortInfo csi) {
      if (csi == null) {
        return false;
      }
      return getColumn() == csi.getColumn()
          && isAscending() == csi.isAscending();
    }

    /**
     * @return the column index
     */
    public int getColumn() {
      return column;
    }

    @Override
    public int hashCode() {
      return super.hashCode();
    }

    /**
     * @return true if ascending, false if descending
     */
    public boolean isAscending() {
      return ascending;
    }

    /**
     * Set whether or not the sorting is ascending or descending.
     * 
     * @param ascending true if ascending, false if descending
     */
    public void setAscending(boolean ascending) {
      this.ascending = ascending;
    }

    /**
     * Set the column index.
     * 
     * @param column the column index
     */
    public void setColumn(int column) {
      this.column = column;
    }
  }

  /**
   * An ordered list of sorting info where each entry tells us how to sort a
   * single column. The first entry is the primary sort order, the second entry
   * is the first tie-breaker, and so on.
   */
  public static class ColumnSortList implements IsSerializable,
      Iterable<ColumnSortInfo> {
    /**
     * A List used to manage the insertion/removal of {@link ColumnSortInfo}.
     */
    private List<ColumnSortInfo> infos = new ArrayList<ColumnSortInfo>();

    /**
     * Add a {@link ColumnSortInfo} to this list. If the column already exists,
     * it will be removed from its current position and placed at the start of
     * the list, becoming the primary sort info.
     * 
     * This add method inserts an entry at the beginning of the list. It does
     * not append the entry to the end of the list.
     * 
     * @param sortInfo the {@link ColumnSortInfo} to add
     */
    public void add(ColumnSortInfo sortInfo) {
      add(0, sortInfo);
    }

    /**
     * Inserts the specified {@link ColumnSortInfo} at the specified position in
     * this list. If the column already exists in the sort info, the index will
     * be adjusted to account for any removed entries.
     * 
     * @param sortInfo the {@link ColumnSortInfo} to add
     */
    public void add(int index, ColumnSortInfo sortInfo) {
      // Remove sort info for duplicate columns
      int column = sortInfo.getColumn();
      for (int i = 0; i < infos.size(); i++) {
        ColumnSortInfo curInfo = infos.get(i);
        if (curInfo.getColumn() == column) {
          infos.remove(i);
          i--;
          if (column < index) {
            index--;
          }
        }
      }

      // Insert the new sort info
      infos.add(index, sortInfo);
    }

    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
      infos.clear();
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof ColumnSortList) {
        return equals((ColumnSortList) obj);
      }
      return false;
    }

    /**
     * Check if this object is equal to another.
     * 
     * @param csl the other object
     * @return true if objects are equal
     */
    public boolean equals(ColumnSortList csl) {
      // Object is null
      if (csl == null) {
        return false;
      }

      // Check the size of the lists
      int size = size();
      if (size != csl.size()) {
        return false;
      }

      // Compare the entries
      for (int i = 0; i < size; i++) {
        if (!infos.get(i).equals(csl.infos.get(i))) {
          return false;
        }
      }

      // Everything is equal
      return true;
    }

    /**
     * Get the primary (first) {@link ColumnSortInfo}'s column index.
     * 
     * @return the primary column or -1 if not sorted
     */
    public int getPrimaryColumn() {
      ColumnSortInfo primaryInfo = getPrimaryColumnSortInfo();
      if (primaryInfo == null) {
        return -1;
      }
      return primaryInfo.getColumn();
    }

    /**
     * Get the primary (first) {@link ColumnSortInfo}.
     * 
     * @return the primary column sort info
     */
    public ColumnSortInfo getPrimaryColumnSortInfo() {
      if (infos.size() > 0) {
        return infos.get(0);
      }
      return null;
    }

    @Override
    public int hashCode() {
      return super.hashCode();
    }

    /**
     * Get the primary (first) {@link ColumnSortInfo}'s sort order.
     * 
     * @return true if ascending, false if descending
     */
    public boolean isPrimaryAscending() {
      ColumnSortInfo primaryInfo = getPrimaryColumnSortInfo();
      if (primaryInfo == null) {
        return true;
      }
      return primaryInfo.isAscending();
    }

    public Iterator<ColumnSortInfo> iterator() {
      return new ImmutableIterator<ColumnSortInfo>(infos.iterator());
    }

    /**
     * Remove a {@link ColumnSortInfo} from the list.
     * 
     * @param sortInfo the {@link ColumnSortInfo} to remove
     */
    public boolean remove(Object sortInfo) {
      return infos.remove(sortInfo);
    }

    /**
     * @return the number of {@link ColumnSortInfo} in the list
     */
    public int size() {
      return infos.size();
    }

    /**
     * @return a duplicate of this list
     */
    ColumnSortList copy() {
      ColumnSortList copy = new ColumnSortList();
      for (ColumnSortInfo info : this) {
        copy.infos.add(new ColumnSortInfo(info.getColumn(), info.isAscending()));
      }
      return copy;
    }
  }

  /**
   * Information about the filtering of a specific column in a table.
   */
  public abstract static class ColumnFilterInfo implements IsSerializable {
    /**
     * The column index.
     */
    private int column;

    /**
     * Default Constructor.
     */
    public ColumnFilterInfo() {
      this(0);
    }

    /**
     * Constructor.
     * 
     * @param column the column index
     */
    public ColumnFilterInfo(int column) {
      this.column = column;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof ColumnFilterInfo) {
        return equals((ColumnFilterInfo) obj);
      }
      return false;
    }

    /**
     * Check if this object is equal to another. The objects are equal if the
     * column and filter values are equal.
     * 
     * @param csi the other object
     * @return true if objects are the same
     */
    public boolean equals(ColumnFilterInfo cfi) {
      if (cfi == null) {
        return false;
      }
      return getColumn() == cfi.getColumn();
    }

    /**
     * @return the column index
     */
    public int getColumn() {
      return column;
    }

    @Override
    public int hashCode() {
      return super.hashCode();
    }
    
    public abstract boolean isFilterMatchingCellContent(String cellContent);

    public abstract boolean isFilterMatchingValueObject(Object object);

    public abstract ColumnFilterInfo copy();
    
    /**
     * Set the column index.
     * 
     * @param column the column index
     */
    public void setColumn(int column) {
      this.column = column;
    }
  }

  /**
   * An ordered list of filter info where each entry tells us how to filter a
   * single column.
   */
  public static class ColumnFilterList implements IsSerializable,
      Iterable<ColumnFilterInfo> {
    /**
     * A List used to manage the insertion/removal of {@link ColumnFilterInfo}.
     */
    private List<ColumnFilterInfo> infos = new ArrayList<ColumnFilterInfo>();

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof ColumnFilterList) {
        return equals((ColumnFilterList) obj);
      }
      return false;
    }

    /**
     * Check if this object is equal to another.
     * 
     * @param csl the other object
     * @return true if objects are equal
     */
    public boolean equals(ColumnFilterList cfl) {
      // Object is null
      if (cfl == null) {
        return false;
      }

      // Check the size of the lists
      int size = size();
      if (size != cfl.size()) {
        return false;
      }

      // Compare the entries
      for (int i = 0; i < size; i++) {
        if (!infos.get(i).equals(cfl.infos.get(i))) {
          return false;
        }
      }

      // Everything is equal
      return true;
    }

    @Override
    public int hashCode() {
      return super.hashCode();
    }

    public Iterator<ColumnFilterInfo> iterator() {
      return new ImmutableIterator<ColumnFilterInfo>(infos.iterator());
    }

    /**
     * Returns the number of {@link ColumnFilterInfo} in the list.
     * 
     * @return the size of the list
     */
    public int size() {
      return infos.size();
    }

    /**
     * Add a {@link ColumnFilterInfo} to this list. If the column already
     * exists, it will be removed from its current position and placed at the
     * start of the list, becoming the primary sort info.
     * 
     * This add method inserts an entry at the beginning of the list. It does
     * not append the entry to the end of the list.
     * 
     * @param column the column index
     * @param filter is the filter
     */
    void add(ColumnFilterInfo info) {
      // Remove sort info for duplicate columns
      for (int i = 0; i < infos.size(); i++) {
        ColumnFilterInfo curInfo = infos.get(i);
        if (curInfo.getColumn() == info.getColumn()) {
          infos.remove(i);
          i--;
        }
      }

      // Insert the new sort info
      infos.add(0, info);
    }

    /**
     * @return a duplicate of this list
     */
    ColumnFilterList copy() {
      ColumnFilterList copy = new ColumnFilterList();
      for (ColumnFilterInfo info : this) {
        copy.infos.add(info.copy());
      }
      return copy;
    }


  }

  
  
  /**
   * A {@link TableModelHelper} request.
   */
  public static class Request implements IsSerializable {
    /**
     * The number of rows to request.
     */
    private int numRows;

    /**
     * An ordered list of ColumnSortInfo.
     */
    private ColumnSortList columnSortList;

    /**
     * An ordered list of ColumnFilterInfo.
     */
    private ColumnFilterList columnFilterList;

    /**
     * The first row of table data to request.
     */
    private int startRow;

    /**
     * Default constructor.
     */
    public Request() {
      this(0, 0, null);
    }

    /**
     * Constructor.
     * 
     * @param startRow the first row to request
     * @param numRows the number of rows to request
     */
    public Request(int startRow, int numRows) {
      this(startRow, numRows, null);
    }

    /**
     * Constructor.
     * 
     * @param startRow the first row to request
     * @param numRows the number of rows to request
     * @param columnSortList column sort orders
     */
    public Request(int startRow, int numRows, ColumnSortList columnSortList) {
      this.startRow = startRow;
      this.numRows = numRows;
      this.columnSortList = columnSortList;
    }

    /**
     * Constructor.
     * 
     * @param startRow the first row to request
     * @param numRows the number of rows to request
     * @param columnSortList column sort orders
     * @param columnFilterList column filters
     */
    public Request(int startRow, int numRows, ColumnSortList columnSortList, ColumnFilterList columnFilterList) {
      this.startRow = startRow;
      this.numRows = numRows;
      this.columnSortList = columnSortList;
      this.columnFilterList = columnFilterList;
    }

    /**
     * Get the ColumnSortInfo, which includes the sort indexes and
     * ascending or descending order info.
     * 
     * @return the sort info
     */
    public ColumnSortList getColumnSortList() {
      return columnSortList;
    }

    /**
     * Get the ColumnFilterInfo, which includes the filter information
     * 
     * @return the sort info
     */
    public ColumnFilterList getColumnFilterList() {
      return columnFilterList;
    }
    
    /**
     * Get the number of rows to request.
     * 
     * @return the number of requested rows
     */
    public int getNumRows() {
      return numRows;
    }

    /**
     * Get the first row to request.
     * 
     * @return the first requested row
     */
    public int getStartRow() {
      return startRow;
    }
  }

  /**
   * A response from the {@link TableModelHelper}.
   * 
   * @param <RowType> the data type of the row values
   */
  public abstract static class Response<RowType> {
    /**
     * Get the objects associated with the retrieved rows.
     * 
     * @return the objects associated with the retrieved row
     */
    public abstract Iterator<RowType> getRowValues();
  }

  /**
   * A response from the {@link TableModelHelper} that is serializable, and can
   * by used over RPC.
   * 
   * @param <RowType> the serializable data type of the row values
   */
  public static class SerializableResponse<RowType extends IsSerializable>
      extends Response<RowType> implements IsSerializable {
    /**
     * The {@link Collection} of row values.
     */
    private Collection<RowType> rowValues;

    /**
     * Default constructor used for RPC.
     */
    public SerializableResponse() {
      this(null);
    }

    /**
     * Create a new {@link SerializableResponse}.
     */
    public SerializableResponse(Collection<RowType> rowValues) {
      this.rowValues = rowValues;
    }

    @Override
    public Iterator<RowType> getRowValues() {
      return rowValues.iterator();
    }
  }

  /**
   * Wrap an {@link Iterator} in an immutable iterator.
   */
  private static class ImmutableIterator<E> implements Iterator<E> {
    private Iterator<E> iterator;

    public ImmutableIterator(Iterator<E> iterator) {
      this.iterator = iterator;
    }

    public boolean hasNext() {
      return iterator.hasNext();
    }

    public E next() {
      return iterator.next();
    }

    public void remove() {
      throw (new UnsupportedOperationException());
    }
  }
}
