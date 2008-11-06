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

package com.google.gwt.gen2.table.event.client;

import com.google.gwt.gen2.event.shared.AbstractEvent;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnFilterList;

/**
 * Logical event fired when a column is filtered.
 */
public class ColumnFilterEvent extends AbstractEvent {
  /**
   * Event Key for {@link ColumnFilterEvent}.
   */
  public static final Type<ColumnFilterEvent, ColumnFilterHandler> TYPE = new Type<ColumnFilterEvent, ColumnFilterHandler>() {
    @Override
    protected void fire(ColumnFilterHandler handler, ColumnFilterEvent event) {
      handler.onColumnFiltered(event);
    }
  };

  /**
   * Information about the column filtering.
   */
  private ColumnFilterList filterList;

  /**
   * Construct a new {@link ColumnFilterEvent}.
   * 
   * @param filterList information about the filter
   */
  public ColumnFilterEvent(ColumnFilterList filterList) {
    this.filterList = filterList;
  }

  /**
   * @return information about the sort order of columns
   */
  public ColumnFilterList getColumnFilterList() {
    return filterList;
  }

  @Override
  protected Type getType() {
    return TYPE;
  }
}
