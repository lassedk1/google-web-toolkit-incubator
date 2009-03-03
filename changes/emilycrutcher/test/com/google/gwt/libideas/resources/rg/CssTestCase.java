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

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.libideas.resources.css.GenerateCssAst;
import com.google.gwt.libideas.resources.css.ast.CssStylesheet;
import com.google.gwt.libideas.resources.css.ast.CssVisitor;
import com.google.gwt.libideas.resources.ext.ResourceContext;

import junit.framework.TestCase;

import java.net.URL;

/**
 * Contains functions for golden-output tests that are concerned with structural
 * modifications to the CSS AST.
 */
class CssTestCase extends TestCase {

  /**
   * Total fake, no implementations.
   */
  private static class FakeContext implements ResourceContext {
    public String deploy(String suggestedFileName, String mimeType,
        byte[] data, boolean xhrCompatible) throws UnableToCompleteException {
      return null;
    }

    public String deploy(URL resource, boolean xhrCompatible)
        throws UnableToCompleteException {
      return null;
    }

    public GeneratorContext getGeneratorContext() {
      return null;
    }

    public String getImplementationSimpleSourceName()
        throws IllegalStateException {
      return null;
    }

    public JClassType getResourceBundleType() {
      return null;
    }

    public boolean supportsDataUrls() {
      return true;
    }
  }

  /**
   * Runs a test.
   * 
   * @param testName is used to compute the test and expected resource paths.
   * @param reversible if <code>true</code>, the test will attempt to transform
   *          the expected css into the test css
   */
  void test(TreeLogger logger, String testName, boolean reversible,
      CssVisitor... visitors) throws UnableToCompleteException {
    String packagePath = getClass().getPackage().getName().replace('.', '/')
        + "/";
    URL testUrl = getClass().getClassLoader().getResource(
        packagePath + testName + "_test.css");
    assertNotNull("Cauld not find testUrl", testUrl);
    URL expectedUrl = getClass().getClassLoader().getResource(
        packagePath + testName + "_expected.css");
    assertNotNull("Cauld not find testUrl", expectedUrl);

    test(logger, testUrl, expectedUrl, visitors);

    if (reversible) {
      test(logger, expectedUrl, testUrl, visitors);
    }
  }

  /**
   * Compares the generated Java expressions for an input file, transformed in
   * order by the specified visitors, and a golden-output file.
   */
  private void test(TreeLogger logger, URL test, URL expected,
      CssVisitor... visitors) throws UnableToCompleteException {

    CssStylesheet expectedSheet = null;
    CssStylesheet testSheet = null;

    try {
      expectedSheet = GenerateCssAst.exec(logger, new URL[] {expected});
      testSheet = GenerateCssAst.exec(logger, new URL[] {test});
    } catch (UnableToCompleteException e) {
      fail("Unable to parse stylesheet");
    }

    for (CssVisitor v : visitors) {
      v.accept(testSheet);
    }

    String testCss = CssResourceGenerator.makeExpression(logger,
        new FakeContext(), null, testSheet, false);
    String expectedCss = CssResourceGenerator.makeExpression(logger,
        new FakeContext(), null, expectedSheet, false);
    assertEquals(expectedCss, testCss);
  }
}
