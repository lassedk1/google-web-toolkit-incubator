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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.gen2.commonwidget.client.HasWidgetsTester;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
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

  // TODO(nwolf/ecc): Figure out why this isn't working, currently unknown.
  public void disabledTestAttachDetachOrder() {
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

  public void testKeyboardSupportDisabled() {
    FastTree t = new FastTree();
    RootPanel.get().add(t);
    FastTreeItem t0 = t.addItem("item 0");
    FastTreeItem t1 = t.addItem("item 1");
    FastTreeItem t2 = t.addItem("item 2");

    // test accessors
    {
      assertTrue(t.isKeyboardSupportEnabled());
      t.setKeyboardSupportEnabled(false);
      assertFalse(t.isKeyboardSupportEnabled());
    }

    // test keyboard support
    {
      t.setSelectedItem(t0);

      // disable keyboard support
      NativeEvent keyEvent0 = Document.get().createKeyPressEvent(false, false,
          false, false, KeyCodes.KEY_DOWN, 0);
      assertEquals(t0, t.getSelectedItem());
      t.setKeyboardSupportEnabled(false);
      t.getElement().dispatchEvent(keyEvent0);
      assertEquals(t0, t.getSelectedItem());

      // enable keyboard support
      NativeEvent keyEvent1 = Document.get().createKeyPressEvent(false, false,
          false, false, KeyCodes.KEY_DOWN, 0);
      assertEquals(t0, t.getSelectedItem());
      t.setKeyboardSupportEnabled(true);
      t.getElement().dispatchEvent(keyEvent1);
      assertEquals(t1, t.getSelectedItem());
    }
    
    RootPanel.get().remove(t);
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

  public void testBeforeSelectRespectsCancel() {
    FastTree tree = createBeforeSelectTestableTree(true);

    // Get first branch
    FastTreeItem item0 = tree.getItem(0);
    tree.setSelectedItem(item0);
    assertEquals("a", item0.getText());
    assertFalse(item0.isSelected());

    // Get second branch
    FastTreeItem item1 = tree.getItem(1);
    tree.setSelectedItem(item1);
    assertEquals("b", item1.getText());
    assertTrue(item1.isSelected());

    // Now that the second branch is open
    // Try to select the first child
    FastTreeItem item2 = item1.getChild(0);
    tree.setSelectedItem(item2);
    assertEquals("b-a", item2.getText());
    assertFalse(item2.isSelected());
    assertTrue(item1.isSelected());

    FastTreeItem item3 = item1.getChild(1);
    tree.setSelectedItem(item3);
    assertEquals("b-b", item3.getText());
    assertTrue(item3.isSelected());
    assertFalse(item1.isSelected());
  }

  public void testBeforeSelectNotFiredWhenEventsAreOff() {
    FastTree tree = createBeforeSelectTestableTree(true);

    // Get first branch
    FastTreeItem item0 = tree.getItem(0);
    tree.setSelectedItem(item0, false);
    assertEquals("a", item0.getText());
    assertTrue(item0.isSelected());

    // Get second branch
    FastTreeItem item1 = tree.getItem(1);
    tree.setSelectedItem(item1, false);
    assertEquals("b", item1.getText());
    assertTrue(item1.isSelected());

    // Now that the second branch is open
    // Try to select the first child
    FastTreeItem item2 = item1.getChild(0);
    tree.setSelectedItem(item2, false);
    assertEquals("b-a", item2.getText());
    assertTrue(item2.isSelected());
    assertFalse(item1.isSelected());

    FastTreeItem item3 = item1.getChild(1);
    tree.setSelectedItem(item3, false);
    assertEquals("b-b", item3.getText());
    assertTrue(item3.isSelected());
    assertFalse(item1.isSelected());
  }

  public void testBeforeSelectNotFiredWhenUndefined() {
    FastTree tree = createBeforeSelectTestableTree(false);

    // Get first branch
    FastTreeItem item0 = tree.getItem(0);
    assertNotNull(tree.beforeSelected(item0));
    tree.setSelectedItem(item0);
    assertEquals("a", item0.getText());
    assertTrue(item0.isSelected());

    // Get second branch
    FastTreeItem item1 = tree.getItem(1);
    tree.setSelectedItem(item1);
    assertEquals("b", item1.getText());
    assertTrue(item1.isSelected());

    // Now that the second branch is open
    // Try to select the first child
    FastTreeItem item2 = item1.getChild(0);
    tree.setSelectedItem(item2);
    assertEquals("b-a", item2.getText());
    assertTrue(item2.isSelected());
    assertFalse(item1.isSelected());

    FastTreeItem item3 = item1.getChild(1);
    tree.setSelectedItem(item3);
    assertEquals("b-b", item3.getText());
    assertTrue(item3.isSelected());
    assertFalse(item1.isSelected());
  }

  public void testFindDeepestOpenChild() {
    FastTree tree = createBeforeSelectTestableTree(false);
    FastTreeItem item = tree.getItem(0);
    item.setState(true);
    tree.setSelectedItem(item);
    assertTrue(item.isOpen());
    assertEquals(2, item.getChildCount());

    // no try something funky
    item.removeItems();

    try {
      assertNotNull(tree.findDeepestOpenChild(item));
    } catch (IndexOutOfBoundsException iobe) {
      fail("Out of bound exception thrown!");
    }
  }

  /**
   * helper method for testing beforeSelect event.
   * 
   * @param hasBeforeSelectionHandler TODO
   */
  private FastTree createBeforeSelectTestableTree(
      boolean hasBeforeSelectionHandler) {
    final FastTree tree = new FastTree();
    FastTreeItem firstBranch = tree.addItem("a");
    firstBranch.addItem("a-a");
    firstBranch.addItem("a-b");
    FastTreeItem secondBranch = tree.addItem("b");
    secondBranch.addItem("b-a");
    secondBranch.addItem("b-b");

    if (hasBeforeSelectionHandler) {
      tree.addBeforeSelectionHandler(new BeforeSelectionHandler<FastTreeItem>() {
        public void onBeforeSelection(BeforeSelectionEvent<FastTreeItem> event) {
          if (event.getItem().getText().contains("a")) {
            event.cancel();
          }
        }
      });
    }

    return tree;
  }
}
