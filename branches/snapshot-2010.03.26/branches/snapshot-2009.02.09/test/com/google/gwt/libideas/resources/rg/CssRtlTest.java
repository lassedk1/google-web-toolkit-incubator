/*
 * Copyright 2009 Google Inc.
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
package com.google.gwt.libideas.resources.rg;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.libideas.resources.rg.CssResourceGenerator.RtlVisitor;

/**
 * This is a static test of the automatic RTL support.
 */
public class CssRtlTest extends CssTestCase {
  public void testBackgroundProperties() throws UnableToCompleteException {
    test(TreeLogger.NULL, "backgroundProperties", true, new RtlVisitor());
  }

  public void testCursorProperties() throws UnableToCompleteException {
    test(TreeLogger.NULL, "cursorProperties", true, new RtlVisitor());
  }

  public void testDirectionUpdatedInBodyOnly() throws UnableToCompleteException {
    test(TreeLogger.NULL, "directionProperty", true, new RtlVisitor());
  }

  public void testFourValuedProperties() throws UnableToCompleteException {
    test(TreeLogger.NULL, "fourValuedProperties", true, new RtlVisitor());
  }

  public void testLeftRightProperties() throws UnableToCompleteException {
    test(TreeLogger.NULL, "leftRightProperties", true, new RtlVisitor());
  }

  public void testNoFlip() throws UnableToCompleteException {
    test(TreeLogger.NULL, "noflip", true, new RtlVisitor());
  }
}
