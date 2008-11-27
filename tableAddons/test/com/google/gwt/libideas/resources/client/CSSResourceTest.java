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
import com.google.gwt.libideas.resources.client.CssResource.Import;
import com.google.gwt.libideas.resources.client.CssResource.ImportedWithPrefix;
import com.google.gwt.libideas.resources.client.CssResource.Shared;
import com.google.gwt.libideas.resources.client.CssResource.Strict;

/**
 * 
 */
public class CSSResourceTest extends LibTestBase {

  interface ConcatenatedResources extends ImmutableResourceBundle {
    @Resource(value = {"concatenatedA.css", "concatenatedB.css"})
    CssResource css();
  }

  interface MyCssResource extends CssResource, MyNonCssResource {
    @ClassName("replacement-not-java-ident")
    String nameOverride();
  }

  interface MyCssResourceA extends MyCssResource, SharedClasses {
    String local();

    // This shouldn't make a difference
    String replacement();
  }

  @ImportedWithPrefix("gwt-MyCssResourceB")
  interface MyCssResourceB extends MyCssResource, SharedClasses {
    String local();

    String sharedOverrideClass();
  }

  /*
   * Check type inheritance.
   */
  interface MyCssResourceWithSprite extends MyCssResource {
    String extraSpriteClass();

    String multiClassA();

    String multiClassB();
  }

  interface MyNonCssResource {
    String nameOverride();

    String replacement();
  }

  interface Resources extends ImmutableResourceBundle {
    Resources INSTANCE = GWT.create(Resources.class);

    @Resource("siblingTestA.css")
    MyCssResourceA a();

    @Resource("siblingTestB.css")
    MyCssResourceB b();

    @Resource("test.css")
    MyCssResourceWithSprite css();

    @Resource("32x32.png")
    DataResource dataMethod();

    @Resource("unrelatedDescendants.css")
    @Import(value = {MyCssResourceA.class, MyCssResourceB.class})
    @Strict
    CssResource descendants();

    @Resource("16x16.png")
    ImageResource spriteMethod();
  }

  interface SharedBase extends CssResource {
    String unsharedClass();
  }

  @ImportedWithPrefix("gwt-Shared")
  @Shared
  interface SharedClasses extends SharedBase {
    String sharedClass();

    String sharedOverrideClass();
  }

  interface SiblingResources extends ImmutableResourceBundle {
    @Resource("siblingTestA.css")
    MyCssResourceA a();

    @Resource("siblingTestB.css")
    MyCssResourceB b();
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

    // Check the value() expansion
    assertTrue(text.contains("offset-left:\"guard\" 16px !important;"));
    assertTrue(text.contains("offset:16px 16px;"));

    // Make sure renaming works
    assertFalse("replacement".equals(css.replacement()));
    assertTrue(text.contains("." + css.replacement()));
    assertTrue(text.contains("." + css.replacement() + ":after"));
    assertTrue(text.contains("." + css.nameOverride()));

    // Make sure renaming for multi-class selectors (.foo.bar) works
    assertFalse("multiClassA".equals(css.multiClassA()));
    assertFalse("multiClassB".equals(css.multiClassB()));
    assertTrue(text.contains("." + css.multiClassA() + "." + css.multiClassB()));

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

  public void testConcatenatedResource() {
    ConcatenatedResources r = GWT.create(ConcatenatedResources.class);
    String text = r.css().getText();
    assertTrue(text.contains(".partA"));
    assertTrue(text.contains(".partB"));
  }

  public void testMultipleBundles() {
    Resources r1 = GWT.create(Resources.class);
    SiblingResources r2 = GWT.create(SiblingResources.class);

    assertEquals(r1.a().replacement(), r2.a().replacement());
    assertEquals(r1.b().replacement(), r2.b().replacement());

    assertEquals(r1.a().sharedClass(), r2.b().sharedClass());
    assertFalse(r1.a().sharedOverrideClass().equals(
        r2.b().sharedOverrideClass()));
    assertFalse(r1.a().unsharedClass().equals(r2.b().unsharedClass()));

    String text = r1.descendants().getText();
    System.out.println(text);
    assertTrue(text.contains("." + r1.a().local() + " ." + r1.b().local()));
  }

  public void testSiblingCSS() {
    SiblingResources r = GWT.create(SiblingResources.class);

    assertFalse(r.a().replacement().equals(r.b().replacement()));
    assertFalse(r.a().local().equals(r.b().local()));

    String a = r.a().getText();
    String b = r.b().getText();

    System.out.println(a);
    System.out.println(b);

    assertTrue(a.contains(".other"));
    assertTrue(b.contains(".other"));
    assertTrue(a.contains(r.a().local()));
    assertTrue(b.contains(r.b().local()));
    assertFalse(a.contains(r.b().local()));
    assertFalse(b.contains(r.a().local()));
  }
}