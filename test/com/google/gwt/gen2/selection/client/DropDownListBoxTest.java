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
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.gen2.commonwidget.client.Decorator;
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

  Object value;
  final ValueChangeHandler<String> updateHandler = new ValueChangeHandler<String>() {
    public void onValueChange(ValueChangeEvent<String> event) {
      value = event.getValue();
    }
  };

  public void testDropDownListBox() {
    globalHelper(createBox());
  }

  public void testWrappedDropDownListBox() {
    globalHelper(createWrappedBox());
  }

  private void globalHelper(DropDownListBox box) {
    basicHelper(box);
    box.setValue(null);
    updateHelper(box);
  }

  private void basicHelper(DropDownListBox box) {
    assertNull(box.getValue());
  }

  private void updateHelper(DropDownListBox box) {
    box.setValue("Abigail Crutcher", true);

    assertEquals("Abigail Crutcher", value);
    box.setValue("Henry Crutcher", true);
    assertEquals("Henry Crutcher", value);
    box.addSeparator();
    box.addItem("Helen", "Helen Chapman", "My mother");
    box.setValue("Helen Chapman", true);
    assertEquals("Helen Chapman", value);
  }

  private DropDownListBox createWrappedBox() {
    return fillInBox(new WrappedListBox());
  }

  private DropDownListBox createBox() {
    return fillInBox(new DropDownListBox<String>());
  }

  DropDownListBox fillInBox(DropDownListBox<String> box) {
    box.addItem("Abby", "Abigail Crutcher", "My baby");
    box.addItem("Katie", "Kaitlyn Crutcher", "My first born");
    box.addItem("Henry", "Henry Crutcher", "My husband", "henry");

    box.addValueChangeHandler(updateHandler);
    return box;
  }

}
