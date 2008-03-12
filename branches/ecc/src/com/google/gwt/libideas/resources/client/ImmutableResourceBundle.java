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
package com.google.gwt.libideas.resources.client;

/**
 * The use of this interface is similar to that of ImageBundle. Declare
 * no-argument functions that return subclasses of {@link ResourcePrototype},
 * which are annotated with <code>gwt.resource</code> annotations specifying
 * the classpath location of the resource to include in the output. At runtime,
 * the functions will return an object that can be used to access the data in
 * the original resource.
 */
public interface ImmutableResourceBundle {

  /**
   * Find a resource by the name of the function in which it is declared.
   * 
   * @param name the name of the desired resource
   * @return the resource, or <code>null</code> if no such resource is
   *         defined.
   */
  public ResourcePrototype getResource(String name);

  /**
   * A convenience method to iterate over all ResourcePrototypes contained in
   * the ResourceBundle.
   */
  public ResourcePrototype[] getResources();
}
