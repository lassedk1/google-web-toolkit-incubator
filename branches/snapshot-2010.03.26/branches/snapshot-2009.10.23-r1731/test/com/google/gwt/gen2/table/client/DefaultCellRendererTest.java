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
package com.google.gwt.gen2.table.client;

import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.gen2.table.client.TableDefinition.AbstractCellView;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

/**
 * Tests for {@link DefaultCellRenderer}.
 */
public class DefaultCellRendererTest extends Gen2TestBase {

  /**
   * A custom {@link ColumnDefinition} used for testing.
   */
  private static class CustomColumnDefinition extends
      AbstractColumnDefinition<Object, Object> {

    private Object cellValue;

    @Override
    public Object getCellValue(Object rowValue) {
      return cellValue;
    }

    @Override
    public void setCellValue(Object rowValue, Object cellValue) {
      this.cellValue = cellValue;
    }
  }

  /**
   * A custom {@link AbstractCellView} used for testing.
   */
  private static class CustomCellView extends AbstractCellView<Object> {

    String html;
    String text;
    Widget widget;

    public CustomCellView() {
      super(null);
    }

    @Override
    public void setHTML(String html) {
      this.html = html;
    }

    public void assertHtml(String expected) {
      assertEquals(expected, html);
    }

    public void assertText(String expected) {
      assertEquals(expected, text);
    }

    public void assertWidget(Widget expected) {
      assertEquals(expected, widget);
    }

    @Override
    public void setHorizontalAlignment(HorizontalAlignmentConstant align) {
    }

    @Override
    public void setStyleAttribute(String attr, String value) {
    }

    @Override
    public void setStyleName(String stylename) {
    }

    @Override
    public void setText(String text) {
      this.text = text;
    }

    @Override
    public void setVerticalAlignment(VerticalAlignmentConstant align) {
    }

    @Override
    public void setWidget(Widget widget) {
      this.widget = widget;
    }
  }

  public void testRenderRowValue() {
    DefaultCellRenderer<Object, Object> renderer = new DefaultCellRenderer<Object, Object>();

    // Normal text string.
    {
      CustomCellView view = renderRowValueImpl(renderer, "test");
      view.assertHtml(null);
      view.assertText("test");
      view.assertWidget(null);
    }

    // Widget.
    {
      Label label = new Label("hello");
      CustomCellView view = renderRowValueImpl(renderer, label);
      view.assertHtml(null);
      view.assertText(null);
      view.assertWidget(label);
    }

    // Html converted to text.
    {
      CustomCellView view = renderRowValueImpl(renderer, "<b>test</b>");
      view.assertHtml(null);
      view.assertText("<b>test</b>");
      view.assertWidget(null);
    }
  }

  public void testRenderRowValueAsHtml() {
    DefaultCellRenderer<Object, Object> renderer = new DefaultCellRenderer<Object, Object>(
        true);

    // Normal text string.
    {
      CustomCellView view = renderRowValueImpl(renderer, "test");
      view.assertHtml("test");
      view.assertText(null);
      view.assertWidget(null);
    }

    // Widget.
    {
      Label label = new Label("hello");
      CustomCellView view = renderRowValueImpl(renderer, label);
      view.assertHtml(null);
      view.assertText(null);
      view.assertWidget(label);
    }

    // Html as html.
    {
      CustomCellView view = renderRowValueImpl(renderer, "<b>test</b>");
      view.assertHtml("<b>test</b>");
      view.assertText(null);
      view.assertWidget(null);
    }
  }

  /**
   * Render a value using a {@link DefaultCellRenderer} and return the modified
   * {@link CustomCellView}.
   * 
   * @param renderer the renderer to use
   * @param value the value to render
   * @return the {@link CustomCellView}
   */
  private CustomCellView renderRowValueImpl(
      DefaultCellRenderer<Object, Object> renderer, Object value) {
    CustomColumnDefinition colDef = new CustomColumnDefinition();
    CustomCellView view = new CustomCellView();
    colDef.setCellValue(null, value);
    renderer.renderRowValue(value, colDef, view);
    return view;
  }
}