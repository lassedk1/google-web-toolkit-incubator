/*
 * Copyright 2007 Google Inc.
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.client.DataResource;
import com.google.gwt.libideas.client.ImmutableResourceBundle;
import com.google.gwt.libideas.client.ResourcePrototype;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * Used to add stylesheets to the document.
 */
public class StyleInjector {
  /**
   * The DOM-compatible way of adding stylesheets.
   */
  static class StyleInjectorImpl {
    void injectStyleSheet(String contents) {
      Element style = DOM.createElement("style");
      DOM.setElementProperty(style, "language", "text/css");
      DOM.setInnerText(style, contents);
      DOM.appendChild(getDocumentHead(), style);
    }
  }

  /**
   * IE doesn't allow manipulation of a style element through DOM methods.
   */
  static class StyleInjectorImplIE extends StyleInjectorImpl {
    native void injectStyleSheet(String contents) /*-{
     $doc.createStyleSheet().cssText = contents;
     }-*/;
  }

  private static final StyleInjectorImpl IMPL =
      (StyleInjectorImpl) GWT.create(StyleInjectorImpl.class);

  /**
   * Add a stylesheet to the document.
   * 
   * @param contents the CSS contents of the stylesheet
   */
  public static void injectStylesheet(String contents) {
    injectStylesheet(contents, null);
  }

  /**
   * Add a stylesheet to the document. Any occurrence of
   * <code>%resourceName%</code> in the stylesheet's contents will be replaced
   * by the URL of the named resource contained in <code>references</code>.
   * 
   * @param contents the CSS contents of the stylesheet
   * @param references the resources to substitute into the stylesheet.
   */
  public static void injectStylesheet(String contents, ImmutableResourceBundle references) {
    if (references != null) {
      ResourcePrototype[] prototypes = references.getResources();
      for (int i = 0; i < prototypes.length; i++) {
        ResourcePrototype p = prototypes[i];
        if (p instanceof DataResource) {
          contents =
              contents.replaceAll("%" + prototypes[i].getName() + "%",
                  ((DataResource)p).getUrl());
        }
      }
    }

    IMPL.injectStyleSheet(contents);
  }

  /**
   * Append an element to the documents HEAD element. This should be in DOM.
   * 
   * @param elt the Element representing the document's head
   */
  private static native Element getDocumentHead() /*-{
   return $doc.getElementsByTagName("head")[0];
   }-*/;
}
