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

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.SourceWriter;

import java.net.URL;

/**
 * Context object for ResourceGenerators.
 */
public interface ResourceContext {

  /**
   * Cause a specific collection of bytes to be available in the program's
   * compiled output.
   * 
   * @param resource the resource to add to the compiled output
   * @return a Java expression which will evaluate to the location of the
   *         provided resource at runtime.
   */
  public String addToOutput(URL resource) throws UnableToCompleteException;

  /**
   * Cause a specific collection of bytes to be available in the program's
   * compiled output.
   * 
   * @param suggestedFileName an unobfuscated filename to possibly use for the
   *          resource
   * @param mimeType the MIME type of the data being provided
   * @param data the bytes to add to the output
   * @return a Java expression which will evaluate to the location of the
   *         provided resource at runtime.
   */
  public String addToOutput(String suggestedFileName, String mimeType,
      byte[] data) throws UnableToCompleteException;

  public GeneratorContext getGeneratorContext();

  public JClassType getResourceBundleType();

  public SourceWriter getSourceWriter();
}
