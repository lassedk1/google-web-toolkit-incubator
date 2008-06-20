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
package com.google.gwt.libideas.resources.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.libideas.resources.rebind.ResourceGeneratorType;
import com.google.gwt.libideas.resources.rg.CssResourceGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Aggregates and minifies CSS stylesheets. A CssResource represents a regular
 * CSS file with GWT-specific at-rules.
 * 
 * Currently-supported rules:
 * 
 * <ul>
 * <li>{@code @def NAME literal-value; .myClass {background: NAME;}} Define a
 * static constant.
 * <li>{@code @eval NAME Java-expression; .myClass {background: NAME;}} Define
 * a constant based on a Java expression.
 * <li>{@code @if [!]property (list of values) {ruleBlock}} Include or exclude
 * CSS rules based on the value of a deferred-binding property.
 * <li>{@code @if Java-expression {ruleBlock}} Include or exclude CSS rules
 * based on a boolean Java expression.
 * <li>{@code @sprite className siblingImageResource;} Return a {@link Sprite}
 * to access the style.
 * <li>{@code @url NAME siblingDataResource; .myClass {background: NAME repeat-x;}}
 * Use a DataResource to generate a <code>url('...'}</code> value.
 * </ul>
 * Any named value will be replaced
 */
@ResourceGeneratorType(CssResourceGenerator.class)
public interface CssResource extends ResourcePrototype {
  /**
   * The original CSS class name specified in the resource.
   */
  @Documented
  @Target(ElementType.METHOD)
  @interface ClassName {
    String value();
  }

  /**
   * Override the prefix used for obfuscated CSS class names. The default
   * algorithm is designed to be safe, but will not produce the shortest
   * possible CSS class identifiers. The developer should choose a prefix that
   * is known to not conflict with external CSS class names. An application
   * written by FooBar Inc. might choose to use a prefix <code>FB</code>.
   */
  @Target(ElementType.METHOD)
  @interface ClassPrefix {
    String value();
  }

  /**
   * Represents a {@literal @sprite} meta-class in the stylesheet.
   */
  interface Sprite {
    /**
     * Alters an Element to show the sprite. The structure of the Element should
     * be considered opaque after applying the Sprite function.
     */
    void apply(Element e);
  }

  String getText();
}
