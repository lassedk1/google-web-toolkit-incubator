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
package com.google.gwt.widgetideas.table.client;

import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.table.client.overrides.HTMLTable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A cell editor that lists its options as radio buttons.
 */
public class RadioCellEditor extends AbstractCellEditor {
  /**
   * A unique ID counter so give radio buttons unique names.
   */
  private static int uniqueID = 0;

  /**
   * The group name.
   */
  private String groupName;

  /**
   * The radio buttons in this editor.
   */
  private List radios = new ArrayList();

  /**
   * The vertical panel used to layout the contents.
   */
  private VerticalPanel vpanel;

  /**
   * Constructor.
   */
  public RadioCellEditor() {
    this(true);
  }

  /**
   * Constructor.
   * 
   * @param useDefaultButtons true to use default accept/cancel buttons
   */
  public RadioCellEditor(boolean useDefaultButtons) {
    super(new VerticalPanel(), useDefaultButtons);
    vpanel = (VerticalPanel) getContentWidget();

    // Assign a unique name
    uniqueID++;
    groupName = "gwtRadioCellEditor" + uniqueID;
  }

  /**
   * Add an option to this editor.
   * 
   * @param label the value of the option
   */
  public void addOption(String label) {
    RadioButton button = new RadioButton(groupName, label);
    radios.add(button);
    vpanel.add(button);
  }

  /**
   * Gets the list of {@link RadioButton} widges associated with this editor.
   */
  public List getRadioButtons() {
    return radios;
  }

  /**
   * Get the new value from the cell editor.
   * 
   * @return the new value
   */
  public Object getValue() {
    Iterator it = radios.iterator();
    while (it.hasNext()) {
      RadioButton radio = (RadioButton) it.next();
      if (radio.isChecked()) {
        return radio.getText();
      }
    }
    return null;
  }

  /**
   * Fired when editing a cell, just after the cell is shown.
   * 
   * @param table the table that contains the cell
   * @param row the row index
   * @param cell the cell index
   */
  public void onEditCell(HTMLTable table, int row, int cell) {
    Iterator it = radios.iterator();
    while (it.hasNext()) {
      RadioButton radio = (RadioButton) it.next();
      if (radio.isChecked()) {
        radio.setFocus(true);
        return;
      }
    }
  }

  /**
   * Set the current value in the cell editor.
   * 
   * @param value the current value
   */
  protected void setValue(Object value) {
    Iterator it = radios.iterator();
    while (it.hasNext()) {
      RadioButton radio = (RadioButton) it.next();
      if (radio.getText().equals(value)) {
        radio.setChecked(true);
      } else {
        radio.setChecked(false);
      }
    }
  }
}
