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

package com.google.gwt.gen2.commonwidget.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Test of the decorator class.
 */
public class DecoratorTest extends Gen2TestBase {
  Element basicWrapper;

  public void testBasicElementCase() {
    basicWrapper = DOM.createDiv();
    addDiv(basicWrapper, "first");

    Element attach = createDiv("");
    basicWrapper.appendChild(attach);
    addDiv(basicWrapper, "<strong>last</strong>");

    Decorator dec = new Decorator(basicWrapper, attach);

    Element parent = createDiv("parent");
    RootPanel.getBodyElement().appendChild(parent);
    Element foo = createDiv("foo");
    Element fooDec = dec.wrapElement(foo);
    parent.appendChild(fooDec);
    Element bar = createDiv("bar");
    Element barDec = dec.wrapElement(bar);
    parent.appendChild(barDec);
    // TODO(ecc) add tests to validate returned structure.
  }

  void addDiv(Element parent, String inner) {
    Element div = createDiv(inner);
    parent.appendChild(div);
  }

  Element createDiv(String inner) {
    Element div = DOM.createDiv();
    div.setInnerHTML(inner);
    return div;
  }
}
