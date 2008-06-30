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

  public static String red() {
    return "orange";
  }

  interface MyCssResource extends CssResource {
    String replacement();
  }

  interface Resources extends ImmutableResourceBundle {
    Resources INSTANCE = GWT.create(Resources.class);

    @Resource("test.css")
    MyCssResource css();

    @Resource("16x16.png")
    ImageResource spriteMethod();

    @Resource("32x32.png")
    DataResource dataMethod();
  }

  @CssResource.ClassPrefix("T")
  interface SiblingResources extends ImmutableResourceBundle {
    @Resource("siblingTestA.css")
    MyCssResource a();

    @Resource("siblingTestB.css")
    MyCssResource b();
  }

  public void testCSS() {
    MyCssResource css = Resources.INSTANCE.css();
    assertFalse("replacement".equals(css.replacement()));

    String text = css.getText();
    System.out.println(text);
    assertTrue(text.contains(Resources.INSTANCE.dataMethod().getUrl()));
    assertTrue(text.contains(red() + ";"));
    assertTrue(text.contains("50px"));
    assertFalse(text.contains("should-never-see-this"));
    // Make sure that we can handle tags in a namespace
    assertTrue(text.contains("ns\\:tag:pseudo"));

    // Make sure we escape property names that aren't idents
    assertTrue(text.contains("\\-some-wacky-extension"));
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
