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
package com.google.gwt.demos.styleinjector.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.TextResource;
import com.google.gwt.libideas.resources.client.ImageResource.ImageOptions;
import com.google.gwt.libideas.resources.client.ImageResource.RepeatStyle;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StyleInjectorDemo implements EntryPoint {

  /**
   * Resources used.
   */
  public interface Resources extends ImmutableResourceBundle {
    Resources INSTANCE = GWT.create(Resources.class);

    @Resource("bt.png")
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    ImageResource bottomBorder();

    @Resource("StyleInjectorDemo.css")
    CssResource css();

    @Resource("lr.png")
    @ImageOptions(repeatStyle = RepeatStyle.Vertical, flipRtl = true)
    ImageResource leftBorder();

    @Resource("icon128.png")
    @ImageOptions(flipRtl = true)
    ImageResource logo();

    @Resource("icon32.png")
    @ImageOptions(flipRtl = true)
    ImageResource logoSmall();

    @Resource("icon16.png")
    ImageResource logoTiny();

    @Resource("rl.png")
    @ImageOptions(repeatStyle = RepeatStyle.Vertical, flipRtl = true)
    ImageResource rightBorder();

    @Resource("filler.txt")
    TextResource text();

    @Resource("filler2.txt")
    TextResource text2();

    @Resource("tb.png")
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    ImageResource topBorder();
  }

  /**
   * This is the worlds simplest.
   */
  public void onModuleLoad() {
    StyleInjector.injectStylesheet(Resources.INSTANCE.css().getText());
    Document doc = Document.get();

    ParagraphElement p = doc.getElementById("filler").cast();
    p.setInnerText(Resources.INSTANCE.text().getText());

    ParagraphElement p2 = doc.getElementById("filler2").cast();
    p2.setInnerText(Resources.INSTANCE.text2().getText());
  }
}
