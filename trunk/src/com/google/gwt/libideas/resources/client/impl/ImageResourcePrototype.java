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
package com.google.gwt.libideas.resources.client.impl;

import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.impl.ClippedImagePrototype;

/**
 * This is part of aa prototype implementation of the ImageBundle optimization
 * implemented with ImmutableResourceBundle.
 */
public class ImageResourcePrototype implements ImageResource {

  final String name;

  final String bundleUrl;
  final int left;
  final int top;
  final int width;
  final int height;
  ClippedImagePrototype clippedImage;
  
  public ImageResourcePrototype(String name,
      String bundleUrl, int left, int top, int width, int height) {
    this.name = name;
    this.left = left;
    this.top = top;
    this.height = height;
    this.width = width;
    this.bundleUrl = bundleUrl;
  }

  public void applyTo(Image image) {
    ensureImage();
    clippedImage.applyTo(image);
  }

  public Image createImage() {
    ensureImage();
    return clippedImage.createImage();
  }

  public String getHTML() {
    ensureImage();
    return clippedImage.getHTML();
  }

  public String getName() {
    return name;
  }

  private void ensureImage() {
    if (clippedImage == null) {
      clippedImage =
          new ClippedImagePrototype(bundleUrl, left, top, width, height);
    }
  }
}
