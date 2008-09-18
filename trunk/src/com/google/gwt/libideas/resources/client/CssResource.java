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

import com.google.gwt.libideas.resources.ext.ResourceGeneratorType;
import com.google.gwt.libideas.resources.rg.CssResourceGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Aggregates and minifies CSS stylesheets. A CssResource represents a regular
 * CSS file with GWT-specific at-rules.
 * <p>
 * Currently-supported accessor functions:
 * 
 * <ul>
 * <li>{@code String someClassName();} will allow the css class
 * <code>.someClassName</code> to be obfuscated at runtime. The function will
 * return the obfuscated class name.</li>
 * </ul>
 * 
 * <p>
 * Currently-supported rules:
 * 
 * <ul>
 * <li>{@code @def NAME literal-value; .myClass background: NAME;} Define a
 * static constant.</li>
 * <li>{@code @eval NAME Java-expression; .myClass background: NAME;} Define a
 * constant based on a Java expression.</li>
 * <li><code>{@literal @if} [!]property (list of values) {ruleBlock}</code> Include
 * or exclude CSS rules based on the value of a deferred-binding property. Also
 * {@code @elif} and {@code @else} follow the same pattern.</li>
 * <li><code>{@literal @if} Java-expression {ruleBlock}</code> Include or exclude
 * CSS rules based on a boolean Java expression.</li>
 * <li>
 * <code>{@literal @}sprite .any .selector {gwt-image: "imageResourceFunction";}</code>
 * . The appearance, size, and height of the sprite will be affected by any
 * {@link ImageResource.ImageOptions} annotations present on the related
 * {@link ImageResource} accessor function. Additional properties may be
 * specified in the rule block.</li>
 * <li>{@code @url NAME siblingDataResource; .myClass background: NAME
 * repeat-x;} Use a DataResource to generate a <code>url('...'}</code> value.</li>
 * </ul>
 * 
 * <p>
 * Currently-supported CSS functions:
 * <ul>
 * <li>{@code value("bundleFunction.someFunction[.other[...]]" [, "suffix"])}
 * substitute the value of a sequence of named zero-arg function invocations. An
 * optional suffix will be appended to the return value of the function. The
 * first name is resolved relative to the bundle interface passed to
 * {@link com.google.gwt.core.client.GWT#create(Class)}.</li>
 * </ul>
 * 
 * @see <a href= *
 *      "http://code.google.com/p/google-web-toolkit-incubator/wiki/CssResource"
 *      * >CssResource design doc< /a>
 */
@ResourceGeneratorType(CssResourceGenerator.class)
public interface CssResource extends ResourcePrototype {
  /**
   * The original CSS class name specified in the resource. This allows CSS
   * classes that do not correspond to Java identifiers to be mapped onto
   * obfuscated class accessors.
   * 
   * <pre>
   * .some-non-java-ident { background: blue; }
   * 
   * interface MyCssResource extends CssResource { {@literal @}ClassName("some-non-java-ident") String classAccessor();
   * }
   * 
   * </pre>
   */
  @Documented
  @Target(ElementType.METHOD)
  @interface ClassName {
    String value();
  }

  /**
   * Provides the contents of the CssResource.
   */
  String getText();
}
