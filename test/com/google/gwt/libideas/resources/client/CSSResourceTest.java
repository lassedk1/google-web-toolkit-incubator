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

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.client.LibTestBase;

/**
 * 
 */
public class CSSResourceTest extends LibTestBase {

  interface MyCssResource extends CssResource {
    @ClassName("replacement-not-java-ident")
    String nameOverride();

    String replacement();
  }

  /*
   * Check type inheritance.
   */
  interface MyCssResourceWithSprite extends MyCssResource {
    Sprite spriteClass();
  }

  interface Resources extends ImmutableResourceBundle {
    Resources INSTANCE = GWT.create(Resources.class);

    @Resource("test.css")
    MyCssResourceWithSprite css();

    @Resource("32x32.png")
    DataResource dataMethod();

    @Resource("16x16.png")
    ImageResource spriteMethod();
  }

  @CssResource.ClassPrefix("T")
  interface SiblingResources extends ImmutableResourceBundle {
    @Resource("siblingTestA.css")
    MyCssResource a();

    @Resource("siblingTestB.css")
    MyCssResource b();
  }

  public static String red() {
    return "orange";
  }

  public void testCSS() {
    MyCssResourceWithSprite css = Resources.INSTANCE.css();
    String text = css.getText();
    System.out.println(text);

    // Check the sprite
    assertTrue(text.contains("height:16px"));
    assertTrue(text.contains("width:16px"));

    // Make sure renaming works
    assertFalse("replacement".equals(css.replacement()));
    assertTrue(text.contains("." + css.replacement()));
    assertTrue(text.contains("." + css.replacement() + ":after"));
    assertTrue(text.contains("." + css.nameOverride()));

    // Check static if evaluation
    assertTrue(text.contains("static:PASSED;"));
    assertFalse(text.contains("FAIL"));

    // Check runtime if evaluation
    assertTrue(text.contains("runtime:PASSED;"));

    // Check interestingly-named idents
    assertTrue(text.contains("\\-some-wacky-extension"));
    assertTrue(text.contains(".ns\\:tag"));
    assertTrue(text.contains(".ns\\:tag:pseudo"));
    assertTrue(text.contains("\"Hello world\""));

    // Check data URL expansion
    assertTrue(text.contains(Resources.INSTANCE.dataMethod().getUrl()));

    // Check @eval expansion
    assertTrue(text.contains(red() + ";"));

    // Check @def substitution
    assertTrue(text.contains("50px"));

    // Check merging semantics
    assertTrue(text.indexOf("static:PASSED") < text.indexOf("runtime:PASSED"));
    assertTrue(text.indexOf("before:merge") != -1);
    assertTrue(text.indexOf("before:merge") < text.indexOf("after:merge"));
    assertTrue(text.indexOf(".may-combine,.may-combine2") != -1);
    assertTrue(text.indexOf("merge:merge") != -1);
    assertTrue(text.indexOf("merge:merge") < text.indexOf("may-not-combine"));
    assertTrue(text.indexOf("may-not-combine") < text.indexOf("prevent:true"));
    assertTrue(text.indexOf("prevent:true") < text.indexOf("prevent-merge:true"));
    assertTrue(text.indexOf("prevent:true") < text.indexOf("may-not-combine2"));
  }

  public void testSiblingCSS() {
    SiblingResources r = GWT.create(SiblingResources.class);

    assertEquals(r.a().replacement(), r.b().replacement());

    String a = r.a().getText();
    String b = r.b().getText();

    System.out.println(a);
    System.out.println(b);

    assertTrue(a.contains(".other"));
    assertTrue(b.contains(".other"));
  }
}
