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
package com.google.gwt.widgetideas.table.client;

import com.google.gwt.widgetideas.table.client.TableModel.ColumnFilterList;

import java.util.ArrayList;

/**
 * A helper class for implementers of the {@link SourceFilterableColumnsEvents}
 * interface. This subclass of {@link ArrayList} assumes that all objects added
 * to it will be of type {@link FilterableColumnsListener}.
 */
public class FilterableColumnsListenerCollection extends
    ArrayList<FilterableColumnsListener> {
  private static final long serialVersionUID = -4412778421382569723L;

  /**
   * Fired when the currently sorted column changes.
   * 
   * @param filterList the list of filtered columns
   */
  public void fireColumnFiltered(ColumnFilterList filterList) {
    for (FilterableColumnsListener listener : this) {
      listener.onColumnFiltered(filterList);
    }
  }
}
