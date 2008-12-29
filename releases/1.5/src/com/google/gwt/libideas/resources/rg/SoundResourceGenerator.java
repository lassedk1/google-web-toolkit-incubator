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
package com.google.gwt.libideas.resources.rg;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.libideas.resources.client.impl.SoundResourcePrototype;
import com.google.gwt.libideas.resources.ext.ResourceBundleFields;
import com.google.gwt.libideas.resources.ext.ResourceBundleRequirements;
import com.google.gwt.libideas.resources.ext.ResourceContext;
import com.google.gwt.libideas.resources.ext.ResourceGeneratorUtil;
import com.google.gwt.libideas.resources.rebind.StringSourceWriter;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.rebind.SourceWriter;

import java.net.URL;

/**
 * Creates implementations of SoundResources.
 */
public class SoundResourceGenerator extends AbstractResourceGenerator {
  private SoundBundleBuilder builder;
  private boolean soundEnabled;
  private String flashElementIdent;

  /**
   * Create instances of {@link SoundResourcePrototype}.
   */
  @Override
  public String createAssignment(TreeLogger logger, ResourceContext context,
      JMethod method) throws UnableToCompleteException {
    SourceWriter sw = new StringSourceWriter();

    sw.print("new ");
    sw.print(SoundResourcePrototype.class.getName());
    sw.println("(");
    sw.indent();
    sw.print(flashElementIdent + ", \"");
    sw.print(method.getName());
    sw.print("\", ");
    sw.print(String.valueOf(builder.getIndex(method.getName())));
    sw.print(", ");
    sw.print(String.valueOf(builder.getDuration(method.getName())));
    sw.outdent();
    sw.println(")");

    return sw.toString();
  }

  /**
   * Save the plugins's Element in a class field.
   */
  @Override
  public void createFields(TreeLogger logger, ResourceContext context,
      ResourceBundleFields fields) throws UnableToCompleteException {

    String urlExpression;
    if (soundEnabled) {
      urlExpression = builder.writeBundle(logger, context);
    } else {
      urlExpression = "null";
    }

    TypeOracle typeOracle = context.getGeneratorContext().getTypeOracle();
    JClassType elementType = typeOracle.findType(Element.class.getName());
    assert elementType != null;

    flashElementIdent = fields.define(elementType, "flashElement",
        SoundResourcePrototype.class.getName() + ".attach(" + urlExpression
            + ")", true, true);
  }

  @Override
  public void init(TreeLogger logger, ResourceContext context)
      throws UnableToCompleteException {
    builder = new SoundBundleBuilder();

    // Determine if sound is enabled in the build context
    PropertyOracle po = context.getGeneratorContext().getPropertyOracle();
    try {
      soundEnabled = Boolean.parseBoolean(po.getPropertyValue(logger,
          "ResourceBundle.enableSound"));
    } catch (BadPropertyValueException e) {
      logger.log(TreeLogger.ERROR, "Bad property", e);
      throw new UnableToCompleteException();
    }
  }

  /**
   * Get the resource attached to a method and assimilate it.
   */
  @Override
  public void prepare(TreeLogger logger, ResourceContext context,
      ResourceBundleRequirements requirements, JMethod method)
      throws UnableToCompleteException {
    if (!soundEnabled) {
      return;
    }

    URL[] urls = ResourceGeneratorUtil.findResources(logger, context, method);
    if (urls.length != 1) {
      logger.log(TreeLogger.ERROR, "Exactly one resource must be specified",
          null);
      throw new UnableToCompleteException();
    }

    builder.assimilate(logger, method.getName(), urls[0]);
  }
}
