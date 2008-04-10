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
 * Tests for TextResource assembly and use.
 */
public class TextResourceTest extends LibTestBase {

  private static final String HELLO = "Hello World!";

  static interface Resources extends ImmutableResourceBundle {
    @Resource("com/google/gwt/libideas/resources/client/hello.txt")
    TextResource helloWorldAbsolute();

    @Resource("hello.txt")
    ExternalTextResource helloWorldExternal();

    @Resource("hello.txt")
    TextResource helloWorldRelative();

    @Resource("hello.txt")
    @Transform("com.google.gwt.libideas.resources.rebind.UpperTransformer")
    TextResource helloWorldUpper();
  }

  public void testExternal() throws ResourceException {
    final Resources r = GWT.create(Resources.class);

    TextResourceCallback c = new TextResourceCallback() {

      public void onError(ResourceException e) {
        e.printStackTrace();
        fail("Unable to fetch " + e.getResource().getName());
      }

      public void onSuccess(TextResource resource) {
        assertEquals(r.helloWorldExternal().getName(), resource.getName());
        assertEquals(HELLO, resource.getText());
        finishTest();
      }
    };

    r.helloWorldExternal().getText(c);
    delayTestFinish(2000);
  }

  public void testInline() {
    Resources r = GWT.create(Resources.class);
    assertEquals(HELLO, r.helloWorldRelative().getText());
    assertEquals(HELLO, r.helloWorldAbsolute().getText());
  }

  public void testMeta() {
    Resources r = GWT.create(Resources.class);
    assertEquals("helloWorldAbsolute", r.helloWorldAbsolute().getName());
    assertEquals("helloWorldRelative", r.helloWorldRelative().getName());
    assertEquals("helloWorldExternal", r.helloWorldExternal().getName());
    assertEquals("helloWorldUpper", r.helloWorldUpper().getName());

    ResourcePrototype[] resources = r.getResources();
    assertEquals(4, resources.length);
  }

  public void testTransformer() {
    Resources r = GWT.create(Resources.class);
    assertEquals(HELLO.toUpperCase(), r.helloWorldUpper().getText());
  }
}
