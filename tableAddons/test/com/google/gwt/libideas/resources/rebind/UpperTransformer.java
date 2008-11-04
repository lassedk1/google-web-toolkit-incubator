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
package com.google.gwt.libideas.resources.rebind;

import com.google.gwt.core.ext.typeinfo.JMethod;

/**
 * Used by TextResourceText. Transformers are not guaranteed to be translatable,
 * so they exist outside of the client source-path.
 */
public class UpperTransformer extends Transformer<String> {
  public String transform(JMethod method, String input) {
    return input.toUpperCase();
  }
}