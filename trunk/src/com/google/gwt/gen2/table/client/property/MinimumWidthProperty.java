/*
 * Copyright 2009 Google Inc.
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
package com.google.gwt.gen2.table.client.property;

/**
 * A {@link ColumnProperty} that provides the minimum width of a column.
 */
public abstract class MinimumWidthProperty extends ColumnProperty {
  /**
   * Property type.
   */
  public static final Type<MinimumWidthProperty> TYPE = new Type<MinimumWidthProperty>() {
    private MinimumWidthProperty instance;

    @Override
    public MinimumWidthProperty getDefault() {
      if (instance == null) {
        instance = new MinimumWidthProperty() {
          @Override
          public int getMinimumColumnWidth() {
            return -1;
          }
        };
      }
      return instance;
    }
  };

  /**
   * Get the minimum width of the column. A return value of -1 indicates that
   * the column has no minimum width, but the consumer of the data may impose
   * one anyway.
   * 
   * @return the minimum allowable width of the column
   */
  public abstract int getMinimumColumnWidth();
}
