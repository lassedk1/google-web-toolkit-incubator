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

package com.google.gwt.gen2.selection.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.gen2.event.shared.SelectionEvent;
import com.google.gwt.gen2.event.shared.SelectionHandler;
import com.google.gwt.gen2.widgetbase.client.Decorator;
import com.google.gwt.user.client.DOM;

/**
 * Tests the custom list box widget.
 */
public class DropDownListBoxTest extends Gen2TestBase {
  /**
   * A custom list box wrapped with one level of divs and using bundled
   * resources.
   */
  static class WrappedListBox extends DropDownListBox {

    private static Decorator DIV_WRAPPER;

    static {
      Element e = DOM.createDiv();
      e.setClassName("demo-decorator");
      DIV_WRAPPER = new Decorator(e, e);
    }

    public WrappedListBox() {
      this.setStyleName("WrappedCustomBox");
    }

    @Override
    protected Decorator supplyItemDecorator() {
      return DIV_WRAPPER;
    }
  }

  Object oldValue;
  Object newValue;
  final SelectionHandler updateHandler = new SelectionHandler<String>() {
    public void onSelection(SelectionEvent<String> event) {
      oldValue = event.getOldValue();
      newValue = event.getNewValue();
    }
  };

  public void testCustomListBox() {
    globalHelper(createBox());
  }

  public void testWrappedCustomListBox() {
    globalHelper(createWrappedBox());
  }

  private void globalHelper(CustomListBox customBox) {
    basicHelper(customBox);
    customBox.setSelectedValue(null);
    updateHelper(customBox);
  }

  private void basicHelper(CustomListBox customBox) {
    assertNull(customBox.getLastSelectedValue());
  }

  private void updateHelper(CustomListBox customBox) {
    customBox.setSelectedValue("Abigail Crutcher");
    assertEquals(null, oldValue);
    assertEquals("Abigail Crutcher", newValue);
    customBox.setSelectedValue("Henry Crutcher");
    assertEquals("Abigail Crutcher", oldValue);
    assertEquals("Henry Crutcher", newValue);
    customBox.addSeparator();
    customBox.addItem("Helen", "Helen Chapman", "My mother");
    customBox.setSelectedValue("Helen Chapman");
    assertEquals("Henry Crutcher", oldValue);
    assertEquals("Helen Chapman", newValue);
  }

  private CustomListBox createWrappedBox() {
    return fillInBox(new WrappedListBox());
  }

  private CustomListBox createBox() {
    return fillInBox(new DropDownListBox<String>());
  }

  CustomListBox fillInBox(DropDownListBox<String> customBox) {
    customBox.addItem("Abby", "Abigail Crutcher", "My baby");
    customBox.addItem("Katie", "Kaitlyn Crutcher", "My first born");
    customBox.addItem("Henry", "Henry Crutcher", "My husband", "henry");

    customBox.addSelectionHandler(updateHandler);
    return customBox;
  }

}
