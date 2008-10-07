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

package com.google.gwt.museum.client.feature;

import com.google.gwt.dom.client.Document;
import com.google.gwt.gen2.commonwidget.client.SimpleWidget;
import com.google.gwt.gen2.event.dom.client.HasKeyboardHandlers;
import com.google.gwt.gen2.event.dom.client.KeyDownEvent;
import com.google.gwt.gen2.event.dom.client.KeyDownHandler;
import com.google.gwt.gen2.event.dom.client.KeyPressEvent;
import com.google.gwt.gen2.event.dom.client.KeyPressHandler;
import com.google.gwt.gen2.event.dom.client.KeyUpEvent;
import com.google.gwt.gen2.event.dom.client.KeyUpHandler;
import com.google.gwt.gen2.event.shared.HandlerRegistration;
import com.google.gwt.museum.client.common.AbstractIssueOverride;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

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

/**
 * Visual test of keyboard handler.
 */
public class KeyboardHandlerVisuals extends AbstractIssueOverride {

  private class MyTextBox extends SimpleWidget implements HasKeyboardHandlers {

    public MyTextBox() {
      super(Document.get().createTextInputElement());
    }

    public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
      return addHandlerAndSink(KeyDownEvent.KEY, handler);
    }

    public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
      return addHandlerAndSink(KeyPressEvent.KEY, handler);
    }

    public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
      return addHandlerAndSink(KeyUpEvent.KEY, handler);
    }
  }

  @Override
  public Widget createIssue() {
    final FlexTable t = new FlexTable();
    t.setText(0, 0, "input widget");
    t.setText(1, 0, "key down event");
    t.setText(2, 0, "key up event");
    t.setText(3, 0, "key press event");

    final MyTextBox box = new MyTextBox();

    t.setWidget(0, 1, box);
    new HasKeyboardHandlers.Adaptor<MyTextBox>(box) {
      public void onKeyDown(KeyDownEvent event) {
        t.setText(1, 1, "key code: " + event.getKeyCode());
      }

      public void onKeyPress(KeyPressEvent event) {
        t.setText(3, 1, "key code: " + event.getCharCode());
      }

      public void onKeyUp(KeyUpEvent event) {
        t.setText(2, 1, "key code: " + event.getKeyCode());
      }
    };
    return t;
  }

  @Override
  public String getInstructions() {
    return "type in random charactors into text box";
  }

  @Override
  public String getSummary() {
    return "keyboard handlers";
  }

  @Override
  public boolean hasCSS() {
    return false;
  }

}
