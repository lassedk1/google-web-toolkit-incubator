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
package com.google.gwt.gen2.complexpanel.client;

import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.gen2.commonwidget.client.HasWidgetsTester;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Test for fast tree.
 */
public class FastTreeTest extends Gen2TestBase {

  static class Adder implements HasWidgetsTester.WidgetAdder {
    public void addChild(HasWidgets container, Widget child) {
      ((FastTree) container).addItem(child);
    }
  }

  public void testAttachDetachOrder() {
    HasWidgetsTester.testAll(new FastTree(), new Adder());
  }

  public void testClear() {
    FastTree t = new FastTree();
    // Adding widget to end of FastTree, widgets still have their parents set
    // correctly.
    FastTreeItem a = new FastTreeItem("a");
    FastTreeItem b = new FastTreeItem("b");
    FastTreeItem c = new FastTreeItem("c");
    FastTreeItem d = new FastTreeItem();
    FastTreeItem e = new FastTreeItem();
    Label dLabel = new Label("d");
    Label eLabel = new Label("e");
    d.setWidget(dLabel);
    d.addItem(e);
    c.addItem(d);
    b.addItem(c);
    a.addItem(b);
    t.addItem(a);
    t.addItem("hello");
    t.addItem(eLabel);
    t.clear();
  }

  public void testInsertSameItemRepeatedly() {
    FastTree t = new FastTree();
    FastTreeItem ti = new FastTreeItem();
    FastTreeItem wti = new FastTreeItem();
    wti.setWidget(new Label("label"));
    for (int i = 0; i < 10; i++) {
      t.addItem(ti);
      t.addItem(wti);
    }
    assertEquals(2, t.getItemCount());
    assertEquals(ti, t.getItem(0));
    assertEquals(wti, t.getItem(1));
  }

  public void testRootAdd() {
    FastTree t = new FastTree();
    Label l = new Label("hello");
    t.add(l);
    assertEquals(t, l.getParent());
  }

  public void testSwap() {
    FastTree t = new FastTree();

    // Start with text.
    FastTreeItem item = t.addItem("hello");
    String inner = DOM.getInnerHTML(item.getContentElement());
    assertTrue(inner.indexOf("hello") >= 0);
    t.addItem(item);
    Widget goodbyeWidget = new Label("goodbye");
    item.setWidget(goodbyeWidget);
    String innerWidget = DOM.getInnerHTML(item.getContentElement());
    assertFalse(innerWidget.indexOf("hello") >= 0);

    // Start with widget.
    Widget hello = new Label("hello");
    FastTreeItem widgetItem = t.addItem(hello);
    assertTrue(DOM.getInnerHTML(widgetItem.getContentElement()).indexOf("hello") >= 0);
    widgetItem.setText("goodbye");
    assertFalse(DOM.getInnerHTML(widgetItem.getContentElement()).indexOf(
        "hello") >= 0);
    assertTrue(DOM.getInnerHTML(widgetItem.getContentElement()).indexOf(
        "goodbye") >= 0);
    assertNull(hello.getParent());

    // Move widget.
    widgetItem.addItem(item);
    assertEquals(t, goodbyeWidget.getParent());
    assertEquals(goodbyeWidget, item.getWidget());

    // Set back to text.
    item.setText("aloha");
    assertEquals("aloha", DOM.getInnerHTML(item.getContentElement()));
    assertNull(goodbyeWidget.getParent());
    assertNull(item.getWidget());
  }

  public void testFastTree() {
    // Simple widget
    FastTree t = new FastTree();
    Label l = new Label("simple widget");
    FastTreeItem simple = new FastTreeItem(l);
    t.addItem(simple);

    // Adding test and widget
    FastTreeItem item = new FastTreeItem();
    t.addItem(item);
    item.setWidget(new Label("now widget"));
    Element elem = item.getContentElement();
    assertEquals(1, DOM.getChildCount(elem));

    // Add widget to existing FastTree.
    Label l2 = new Label("next widget");
    simple.setWidget(l2);
    assertEquals(t, l2.getParent());

    // Remove a FastTree item, make sure widget is removed from FastTree, but
    // not FastTree
    // item.
    simple.remove();
    assertEquals(l2, simple.getWidget());
    assertNull(l2.getParent());

    // Adding widget to end of FastTree, widgets still have their parents set
    // correctly.
    FastTreeItem a = new FastTreeItem("a");
    FastTreeItem b = new FastTreeItem("b");
    FastTreeItem c = new FastTreeItem("c");
    FastTreeItem d = new FastTreeItem();
    FastTreeItem e = new FastTreeItem();
    Label dLabel = new Label("d");
    Label eLabel = new Label("e");
    d.setWidget(dLabel);
    d.addItem(e);
    c.addItem(d);
    b.addItem(c);
    a.addItem(b);
    t.addItem(a);
    assertEquals(t, dLabel.getParent());
    e.setWidget(eLabel);
    assertEquals(t, eLabel.getParent());

    // FastTree inside of FastTree.
    FastTree childFastTree = new FastTree();
    t.addItem(new FastTreeItem(childFastTree));

    // Swap FastTreeItems to new FastTree.
    childFastTree.addItem(c);
    assertEquals(childFastTree, dLabel.getParent());
    assertEquals(childFastTree, eLabel.getParent());

    // Make sure remove clears.
    d.remove();
    assertNull(dLabel.getParent());
    assertNull(eLabel.getParent());
    assertFalse(childFastTree.getChildWidgets().containsKey(eLabel.getParent()));
  }
}
