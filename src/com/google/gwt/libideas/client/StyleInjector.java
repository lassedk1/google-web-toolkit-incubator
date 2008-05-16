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
package com.google.gwt.libideas.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.DataResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.ResourcePrototype;

/**
 * Used to add stylesheets to the document.
 */
public class StyleInjector {
  /**
   * The DOM-compatible way of adding stylesheets.
   */
  public static class StyleInjectorImpl {
    public void injectStyleSheet(String contents) {
      StyleElement style = Document.get().createStyleElement();
      style.setPropertyString("language", "text/css");
      style.setInnerText(contents);
      Document.get().getElementsByTagName("head").getItem(0).appendChild(style);
    }
  }

  /**
   * IE doesn't allow manipulation of a style element through DOM methods.
   */
  public static class StyleInjectorImplIE extends StyleInjectorImpl {
    public native void injectStyleSheet(String contents) /*-{
      $doc.createStyleSheet().cssText = contents;
    }-*/;
  }

  /**
   * Inject all CSSResources in an ImmutableResourceBundle.
   * 
   * @param resources the ImmutableResourceBundle to inject
   */
  public static void injectStylesheet(ImmutableResourceBundle resources) {
    StringBuilder sb = new StringBuilder();
    for (ResourcePrototype p : resources.getResources()) {
      if (p instanceof CssResource) {
        sb.append(((CssResource) p).getText()).append("\n");
      }
    }
    injectStylesheet(sb.toString());
  }

  /**
   * Add a stylesheet to the document.
   * 
   * @param contents the CSS contents of the stylesheet
   */
  public static void injectStylesheet(String contents) {
    ((StyleInjectorImpl) GWT.create(StyleInjectorImpl.class)).injectStyleSheet(contents);
  }

  /**
   * Add a stylesheet to the document. Any occurrence of
   * <code>%resourceName%</code> in the stylesheet's contents will be replaced
   * by the URL of the named {@link DataResource} contained in
   * <code>references</code>.
   * 
   * @param contents the CSS contents of the stylesheet
   * @param references the resources to substitute into the stylesheet.
   * @deprecated Use {@link CssResource} with {@code @url} rules instead.
   */
  @Deprecated
  public static void injectStylesheet(String contents,
      ImmutableResourceBundle references) {
    if (references != null) {
      for (ResourcePrototype p : references.getResources()) {
        if (p instanceof DataResource) {
          contents = contents.replaceAll("%" + p.getName() + "%",
              ((DataResource) p).getUrl());
        }
      }
    }

    injectStylesheet(contents);
  }

  /**
   * Utility class.
   */
  private StyleInjector() {
  }
}
