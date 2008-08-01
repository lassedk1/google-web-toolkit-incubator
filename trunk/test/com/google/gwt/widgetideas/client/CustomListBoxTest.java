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

package com.google.gwt.widgetideas.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.libideas.event.shared.RenderEvent;
import com.google.gwt.libideas.event.shared.RenderHandler;
import com.google.gwt.libideas.event.shared.UpdateValueEvent;
import com.google.gwt.libideas.event.shared.UpdateValueHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;

/**
 * Tests the custom list box widget.
 */
public class CustomListBoxTest extends WidgetTestBase {
  /**
   * A custom list box wrapped with one level of divs and using bundled
   * resources.
   */
  static class WrappedCustomListBox extends CustomListBox {

    private static Decorator DIV_WRAPPER;

    static {
      Element e = DOM.createDiv();
      e.setClassName("demo-decorator");
      DIV_WRAPPER = new Decorator(e, e);
    }

    public WrappedCustomListBox(String html) {
      super(html);
      this.setStyleName("WrappedCustomBox");
    }

    @Override
    protected Decorator getButtonDecorator() {
      return DIV_WRAPPER;
    }

    @Override
    protected Decorator getItemDecorator() {
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
  final UpdateValueHandler updateHandler = new UpdateValueHandler<String>() {
    public void onUpdateValue(UpdateValueEvent<String> event) {
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
    renderingHelper(customBox);
    customBox.setCurrentValue(null);
    updateHelper(customBox);
  }

  private void basicHelper(CustomListBox customBox) {
    assertNull(customBox.getCurrentValue());
  }

  private void updateHelper(CustomListBox customBox) {
    customBox.setCurrentValue("Abigail Crutcher");
    assertEquals(null, oldValue);
    assertEquals("Abigail Crutcher", newValue);
    customBox.setCurrentValue("Henry Crutcher");
    assertEquals("Abigail Crutcher", oldValue);
    assertEquals("Henry Crutcher", newValue);
    customBox.addSeparator();
    customBox.addItem("Helen", "Helen Chapman", "My mother");
    customBox.setCurrentValue("Helen Chapman");
    assertEquals("Henry Crutcher", oldValue);
    assertEquals("Helen Chapman", newValue);
  }

  private void renderingHelper(CustomListBox customBox) {
    assertFalse(rendering);
    customBox.setCurrentValue("Abigail Crutcher");
    assertFalse(rendering);
    customBox.showItems();
    assertTrue(rendering);

    rendering = false;
    customBox.setCurrentValue("Henry Crutcher");
    customBox.addSeparator();
    customBox.addItem("Helen", "Helen Chapman", "My mother");
    assertFalse(rendering);
    customBox.showItems();
    assertTrue(rendering);
    rendering = false;
    customBox.showItems();
    assertFalse(rendering);
  }

  private CustomListBox createWrappedBox() {
    return fillInBox(new WrappedCustomListBox("wrapped family"));
  }

  private CustomListBox createBox() {
    return fillInBox(new CustomListBox<String>("My family"));
  }

  CustomListBox fillInBox(CustomListBox customBox) {
    customBox.addItem("Abby", "Abigail Crutcher", "My baby");
    customBox.addItem("Katie", "Kaitlyn Crutcher", "My first born");
    customBox.addItem("Henry", "Henry Crutcher", "My husband", new Command() {
      public void execute() {
        Window.setTitle("Love ya");
      }
    });
    customBox.addRenderHandler(renderHandler);
    customBox.addValueUpdateHandler(updateHandler);
    return customBox;
  }

}
