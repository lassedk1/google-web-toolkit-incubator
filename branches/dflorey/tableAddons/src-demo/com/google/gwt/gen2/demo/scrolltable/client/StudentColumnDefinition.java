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

import com.google.gwt.gen2.demo.scrolltable.shared.Student;
import com.google.gwt.gen2.table.client.AbstractColumnDefinition;

/**
 * An {@link AbstractColumnDefinition} applied to {@link Student} row values.
 * 
 * @param <ColType> the data type of the column
 */
public abstract class StudentColumnDefinition<ColType> extends
    AbstractColumnDefinition<Student, ColType> {
  /**
   * The general grouping of the column definition.
   */
  public static enum Group {
    GENERAL("General Info"), PREFERENCES("Prefences"), SCHOOL("School Info"), LOGIN(
        "Login Info");

    private String name;

    private Group(String name) {
      this.name = name;
    }

    /**
     * @return the name of the group
     */
    public String getName() {
      return name;
    }
  }

  /**
   * The name of the column.
   */
  private String name;

  /**
   * The {@link Group} that the column is in.
   */
  private Group group;

  /**
   * Construct a new {@link StudentColumnDefinition}.
   * 
   * @param name the name of the column
   * @param group the column group
   */
  public StudentColumnDefinition(String name, Group group) {
    this.name = name;
    this.group = group;
  }

  /**
   * @return the column group, used to create the header row
   */
  public Group getGroup() {
    return group;
  }

  /**
   * @return the name of the column, used to create the header row
   */
  public String getName() {
    return name;
  }
}
