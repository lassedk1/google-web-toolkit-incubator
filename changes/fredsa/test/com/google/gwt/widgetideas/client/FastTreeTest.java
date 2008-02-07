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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;

/**
 * Tests {@link FastTree}.
 * 
 */
public class FastTreeTest extends BasicWidgetTest {


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
    Element elem = item.getContentElem();
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

  
  public void testSwap() {
    FastTree t = new FastTree();

    // Start with text.
    FastTreeItem item = t.addItem("hello");
    String inner = DOM.getInnerHTML(item.getContentElem());
    assertTrue(inner.indexOf("hello") >= 0);
    t.addItem(item);
    Widget goodbyeWidget = new Label("goodbye");
    item.setWidget(goodbyeWidget);
    String innerWidget = DOM.getInnerHTML(item.getContentElem());
    assertFalse(innerWidget.indexOf("hello") >= 0);

    // Start with widget.
    Widget hello = new Label("hello");
    FastTreeItem widgetItem = t.addItem(hello);
    assertTrue(DOM.getInnerHTML(widgetItem.getContentElem()).indexOf("hello") >= 0);
    widgetItem.setText("goodbye");
    assertFalse(DOM.getInnerHTML(widgetItem.getContentElem()).indexOf("hello") >= 0);
    assertTrue(DOM.getInnerHTML(widgetItem.getContentElem()).indexOf("goodbye") >= 0);
    assertNull(hello.getParent());

    // Move widget.
    widgetItem.addItem(item);
    assertEquals(goodbyeWidget, item.getWidget());
    assertEquals(t, goodbyeWidget.getParent());
    

    // Set back to text.
    item.setText("aloha");
    assertEquals("aloha", DOM.getInnerHTML(item.getContentElem()));
    assertNull(goodbyeWidget.getParent());
    assertNull(item.getWidget());
  }

  public void testFromSteve() {
    RootPanel.get().clear();

    FastTree FastTree = new FastTree();
    RootPanel.get().add(FastTree);

    FastTree.addItem(new Label("test"));
    FastTree.clear();
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
    assertFalse(t.treeItemIterator().hasNext());
    assertEquals(0, t.getChildWidgets().size());
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

  public void testIterator() {
    FastTree tree = new FastTree();
    Iterator iter = tree.treeItemIterator();
    assertFalse(iter.hasNext());
    FastTreeItem a = tree.addItem("a");
    FastTreeItem b = tree.addItem("b");
    FastTreeItem c = tree.addItem("c");

    Iterator iter2 = tree.treeItemIterator();
    assertEquals(a, iter2.next());
    assertEquals(b, iter2.next());
    assertEquals(c, iter2.next());
    assertFalse(iter2.hasNext());

    FastTreeItem a_a = a.addItem("a_a");
    FastTreeItem a_a_a = a_a.addItem("a_a_a");
    FastTreeItem a_a_b = a_a.addItem("a_a_b");

    Iterator iter3 = tree.treeItemIterator();
    assertEquals(a, iter3.next());
    assertEquals(a_a, iter3.next());
    assertEquals(a_a_a, iter3.next());
    assertEquals(a_a_b, iter3.next());
    assertEquals(b, iter3.next());
    assertEquals(c, iter3.next());
    assertFalse(iter3.hasNext());
  }

  public void testNulls() {
    // Checking for setting the widget null then clearing the FastTree.
    FastTree t = new FastTree();
    FastTreeItem item = new FastTreeItem();
    item.setWidget(null);
    t.clear();

    FastTreeItem a = t.addItem("");
    FastTreeItem b = t.addItem(new Label("b"));
    a.setWidget(null);
    b.setWidget(null);
  }

  public void testRemove() {
    FastTree t = new FastTree();
    FastTreeItem item = t.addItem("a");
    FastTreeItem itemb = t.addItem("b");
    t.setSelectedItem(item);
    assertEquals(item, t.getSelectedItem());
    item.remove();
    assertNull(t.getSelectedItem());
    Iterator iter = t.treeItemIterator();
    assertTrue(iter.hasNext());
    iter.next();
    assertFalse(iter.hasNext());
    t.removeItem(itemb);
    assertNull(t.getSelectedItem());
    Iterator iter2 = t.treeItemIterator();
    assertFalse(iter2.hasNext());
  }

  public void testRootAdd() {
    FastTree t = new FastTree();
    Label l = new Label("hello");
    t.add(l);
    assertEquals(t, l.getParent());
  }

}
