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
import com.google.gwt.gen2.client.Gen2TestBase;
import com.google.gwt.gen2.event.shared.ChoiceEvent;
import com.google.gwt.gen2.event.shared.ChoiceHandler;
import com.google.gwt.gen2.event.shared.RenderEvent;
import com.google.gwt.gen2.event.shared.RenderHandler;
import com.google.gwt.gen2.widget.client.Decorator;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;

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

    public WrappedListBox(String html) {
      super(html);
      this.setStyleName("WrappedCustomBox");
    }

    @Override
    protected Decorator supplyItemDecorator() {
      return DIV_WRAPPER;
    }
  }

  boolean rendering;

  final RenderHandler renderHandler = new RenderHandler() {
    public void onRender(RenderEvent event) {
      rendering = true;
    }
  };
  Object oldValue;
  Object newValue;
  final ChoiceHandler updateHandler = new ChoiceHandler<String>() {
    public void onUpdateValue(ChoiceEvent<String> event) {
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

  private void globalHelper(DropDownListBox customBox) {
    basicHelper(customBox);
    renderingHelper(customBox);
    customBox.chose(null);
    updateHelper(customBox);
  }

  private void basicHelper(DropDownListBox customBox) {
    assertNull(customBox.getChosenValue());
  }

  private void updateHelper(DropDownListBox customBox) {
    customBox.chose("Abigail Crutcher");
    assertEquals(null, oldValue);
    assertEquals("Abigail Crutcher", newValue);
    customBox.chose("Henry Crutcher");
    assertEquals("Abigail Crutcher", oldValue);
    assertEquals("Henry Crutcher", newValue);
    customBox.addSeparator();
    customBox.addItem("Helen", "Helen Chapman", "My mother");
    customBox.chose("Helen Chapman");
    assertEquals("Henry Crutcher", oldValue);
    assertEquals("Helen Chapman", newValue);
  }

  private void renderingHelper(DropDownListBox customBox) {
    assertFalse(rendering);
     
    customBox.chose("Abigail Crutcher");
    assertFalse(rendering);
    customBox.showItems();
    assertTrue(rendering);

    rendering = false;
    customBox.chose("Henry Crutcher");
    customBox.addSeparator();
    customBox.addItem("Helen", "Helen Chapman", "My mother");
    assertFalse(rendering);
    customBox.showItems();
    assertTrue(rendering);
    rendering = false;
    customBox.showItems();
    assertFalse(rendering);
  }

  private DropDownListBox createWrappedBox() {
    return fillInBox(new WrappedListBox("wrapped family"));
  }

  private DropDownListBox createBox() {
    return fillInBox(new DropDownListBox<String>("My family"));
  }

  DropDownListBox fillInBox(DropDownListBox customBox) {
    customBox.addItem("Abby", "Abigail Crutcher", "My baby");
    customBox.addItem("Katie", "Kaitlyn Crutcher", "My first born");
    customBox.addItem("Henry", "Henry Crutcher", "My husband", "henry",
        new Command() {
          public void execute() {
            Window.setTitle("Love ya");
          }
        });
    customBox.addRenderHandler(renderHandler);
    customBox.addChoiceHandler(updateHandler);
    return customBox;
  }

}
