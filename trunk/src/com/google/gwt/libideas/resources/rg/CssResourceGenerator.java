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
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.util.DefaultTextOutput;
import com.google.gwt.dev.util.Util;
import com.google.gwt.dom.client.Element;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.DataResource;
import com.google.gwt.libideas.resources.client.impl.ImageResourcePrototype;
import com.google.gwt.libideas.resources.client.impl.SpriteImpl;
import com.google.gwt.libideas.resources.css.CssGenerationVisitor;
import com.google.gwt.libideas.resources.css.GenerateCssAst;
import com.google.gwt.libideas.resources.css.ast.Context;
import com.google.gwt.libideas.resources.css.ast.CssCompilerException;
import com.google.gwt.libideas.resources.css.ast.CssDef;
import com.google.gwt.libideas.resources.css.ast.CssEval;
import com.google.gwt.libideas.resources.css.ast.CssIf;
import com.google.gwt.libideas.resources.css.ast.CssMediaRule;
import com.google.gwt.libideas.resources.css.ast.CssModVisitor;
import com.google.gwt.libideas.resources.css.ast.CssNode;
import com.google.gwt.libideas.resources.css.ast.CssProperty;
import com.google.gwt.libideas.resources.css.ast.CssRule;
import com.google.gwt.libideas.resources.css.ast.CssSelector;
import com.google.gwt.libideas.resources.css.ast.CssSprite;
import com.google.gwt.libideas.resources.css.ast.CssStylesheet;
import com.google.gwt.libideas.resources.css.ast.CssUrl;
import com.google.gwt.libideas.resources.css.ast.CssVisitor;
import com.google.gwt.libideas.resources.css.ast.HasNodes;
import com.google.gwt.libideas.resources.rebind.AbstractResourceGenerator;
import com.google.gwt.libideas.resources.rebind.ResourceContext;
import com.google.gwt.libideas.resources.rebind.ResourceGeneratorUtil;
import com.google.gwt.libideas.resources.rebind.StringSourceWriter;
import com.google.gwt.user.rebind.SourceWriter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Adler32;

/**
 * Provides implementations of CSSResources.
 */
public class CssResourceGenerator extends AbstractResourceGenerator {
  private static class ClassRenamer extends CssVisitor {
    private final Map<String, String> classReplacements;
    private final TreeLogger logger;
    private final Set<String> missingClasses;

    public ClassRenamer(TreeLogger logger, Map<String, String> classReplacements) {
      this.logger = logger.branch(TreeLogger.DEBUG, "Replacing CSS class names");
      this.classReplacements = classReplacements;
      missingClasses = new HashSet<String>(classReplacements.keySet());
    }

    @Override
    public void endVisit(CssSelector x, Context ctx) {
      String sel = x.getSelector();

      // TODO This would be simplified by having a class hierarchy for selectors
      for (Map.Entry<String, String> entry : classReplacements.entrySet()) {
        Pattern p = Pattern.compile("(.*)\\.(" + Pattern.quote(entry.getKey())
            + ")([ :>+#].*|$)");
        Matcher m = p.matcher(sel);
        if (m.find()) {
          sel = m.group(1) + "." + entry.getValue() + m.group(3);
          missingClasses.remove(entry.getKey());
        }
      }

      sel = sel.trim();
      x.setSelector(sel);
    }

    @Override
    public void endVisit(CssStylesheet x, Context ctx) {
      if (missingClasses.isEmpty()) {
        return;
      }

      TreeLogger errorLogger = logger.branch(TreeLogger.INFO,
          "The following obfuscated style classes were missing from "
              + "the source CSS file:");
      for (String c : missingClasses) {
        errorLogger.log(TreeLogger.ERROR, c + ": Fix by adding ." + c + "{}");
      }
      throw new RuntimeException("Missing a CSS replacement");
    }
  }

  /**
   * This delegate class bypasses traversal of a node, instead traversing the
   * node's children.
   * 
   * @param <T> the original type of node
   */
  private static class CollapsedNode<T extends HasNodes> extends CssNode
      implements HasNodes {

    private final T delegate;

    public CollapsedNode(T delegate) {
      this.delegate = delegate;
    }

    public List<CssNode> getNodes() {
      return delegate.getNodes();
    }

    public void traverse(CssVisitor visitor, Context context) {
      visitor.acceptWithInsertRemove(getNodes());
    }
  }

  /**
   * Statically evaluates {@literal @if} rules.
   */
  private static class IfEvaluator extends CssModVisitor {
    private final PropertyOracle oracle;
    private final TreeLogger logger;

    public IfEvaluator(TreeLogger logger, PropertyOracle oracle) {
      this.logger = logger.branch(TreeLogger.DEBUG,
          "Replacing property-based @if blocks");
      this.oracle = oracle;
    }

    @Override
    public void endVisit(CssIf x, Context ctx) {
      if (x.getExpression() != null) {
        // This gets taken care of by the runtime substitution visitor
      } else {
        try {
          String propValue = oracle.getPropertyValue(logger,
              x.getPropertyName());

          /*
           * If the deferred binding property's value is in the list of values
           * in the @if rule, move the rules into the @if's context.
           */
          if (Arrays.asList(x.getPropertyValues()).contains(propValue)
              ^ x.isNegated()) {
            for (CssNode n : x.getNodes()) {
              ctx.insertBefore(n);
            }
          }

          // Always delete @if rules that we can statically evaluate
          ctx.removeMe();
        } catch (BadPropertyValueException e) {
          logger.log(TreeLogger.ERROR, "Unable to evaluate @if block", e);
          throw new RuntimeException("Unable to parse CSS", e);
        }
      }
      super.endVisit(x, ctx);
    }
  }

  /**
   * Merges rules that have matching selectors.
   */
  private static class MergeIdenticalSelectorsVisitor extends CssModVisitor {
    private final Map<String, CssRule> canonicalRules = new HashMap<String, CssRule>();

    @Override
    public boolean visit(CssIf x, Context ctx) {
      // Can't merge rules across if rules
      (new MergeIdenticalSelectorsVisitor()).accept(x.getNodes());
      return false;
    }

    @Override
    public boolean visit(CssMediaRule x, Context ctx) {
      // Can't merge rules across media rules
      (new MergeIdenticalSelectorsVisitor()).accept(x.getNodes());
      return false;
    }

    @Override
    public boolean visit(CssRule x, Context ctx) {
      // Assumed to run immediately after SplitRulesVisitor
      assert x.getSelectors().size() == 1;
      CssSelector sel = x.getSelectors().get(0);
      if (canonicalRules.containsKey(sel.getSelector())) {
        CssRule canonical = canonicalRules.get(sel.getSelector());
        canonical.getProperties().addAll(x.getProperties());
        ctx.removeMe();
      } else {
        canonicalRules.put(sel.getSelector(), x);
      }
      return false;
    }
  }

  /**
   * Merges rules that have identical content.
   */
  private static class MergeRulesByContentVisitor extends CssModVisitor {
    private Map<String, CssRule> rulesByContents = new HashMap<String, CssRule>();

    @Override
    public boolean visit(CssIf x, Context ctx) {
      // Can't merge rules across if rules
      (new MergeIdenticalSelectorsVisitor()).accept(x.getNodes());
      return false;
    }

    @Override
    public boolean visit(CssMediaRule x, Context ctx) {
      // Can't merge rules across media rules
      (new MergeRulesByContentVisitor()).accept(x.getNodes());
      return false;
    }

    @Override
    public boolean visit(CssRule x, Context ctx) {
      StringBuilder b = new StringBuilder();
      for (CssProperty p : x.getProperties()) {
        b.append(p.getName()).append(":");
        if (p.getExpression() != null) {
          b.append(p.getExpression());
        } else {
          for (String s : p.getValues()) {
            b.append(s).append(" ");
          }
          b.append(";");
        }
      }

      String content = b.toString();
      if (rulesByContents.containsKey(content)) {
        CssRule r = rulesByContents.get(content);
        r.getSelectors().addAll(x.getSelectors());
        ctx.removeMe();
        return false;
      }

      // Look for prefixes
      for (Map.Entry<String, CssRule> entry : rulesByContents.entrySet()) {
        if (content.startsWith(entry.getKey())) {
          CssRule r = entry.getValue();
          r.getSelectors().addAll(x.getSelectors());
          x.getProperties().subList(0, r.getProperties().size()).clear();
          return false;
        }
      }

      rulesByContents.put(content, x);
      return false;
    }
  }

  /**
   * Splits rules with compound selectors into multiple rules.
   */
  private static class SplitRulesVisitor extends CssModVisitor {
    @Override
    public void endVisit(CssRule x, Context ctx) {
      if (x.getSelectors().size() == 1) {
        return;
      }

      for (CssSelector sel : x.getSelectors()) {
        CssRule newRule = new CssRule();
        newRule.getSelectors().add(sel);
        newRule.getProperties().addAll(x.getProperties());
        ctx.insertBefore(newRule);
      }
      ctx.removeMe();
      return;
    }
  }

  /**
   * The real trick with spriting the images is to reuse the ImageResource
   * processing framework by requiring the sprite to be defined in terms of an
   * ImageResource.
   */
  private static class Spriter extends CssModVisitor {
    private final TreeLogger logger;
    private final ResourceContext context;

    public Spriter(TreeLogger logger, ResourceContext context) {
      this.logger = logger.branch(TreeLogger.DEBUG,
          "Creating image sprite classes");
      this.context = context;
    }

    @Override
    public void endVisit(CssSprite x, Context ctx) {
      String className = x.getCssClass();
      String functionName = x.getResourceFunction();

      // Find the method
      JMethod method = context.getResourceBundleType().findMethod(functionName,
          new JType[0]);

      if (method == null) {
        logger.log(TreeLogger.ERROR, "Unable to find ImageResource method "
            + functionName + " in "
            + context.getResourceBundleType().getQualifiedSourceName());
        throw new RuntimeException("Cannot find image function");
      }

      CssRule spriteRule = new CssRule();
      CssSelector classSelector = new CssSelector("." + className);
      spriteRule.getSelectors().add(classSelector);

      /*
       * This casts the ImageResource to ImageResourcePrototype to get at the
       * precomputed size metrics.
       */
      String instance = "((" + ImageResourcePrototype.class.getName() + ")("
          + context.getImplementationSimpleSourceName() + ".this."
          + functionName + "()))";

      // Set the class to constrain the height and width of the element
      List<CssProperty> properties = spriteRule.getProperties();

      // TODO Decide if the sprite should constrain the size.
      properties.add(new CssProperty("height", instance
          + ".getHeight() + \"px\"", false));
      properties.add(new CssProperty("width",
          instance + ".getWidth() + \"px\"", false));
      properties.add(new CssProperty("overflow", "\"hidden\"", false));

      /*
       * IE6 needs a clipper element sized to the size of the image which
       * contains a screen that displays the image. If the user is running in
       * IE6, then we'll create some extra rules for the child div (to be
       * created by SpriteImpl at runtime) to show the image.
       * 
       * Otherwise, we'll just set the background to the URL of the
       * ImageResource.
       */
      try {
        if (context.getGeneratorContext().getPropertyOracle().getPropertyValue(
            logger, "user.agent").equals("ie6")) {

          CssRule screenRule = new CssRule();
          CssSelector screenSelector = new CssSelector(
              classSelector.getSelector() + " div");
          screenRule.getSelectors().add(screenSelector);

          List<CssProperty> screenProperties = screenRule.getProperties();

          screenProperties.add(new CssProperty("filter",
              "\"progid:DXImageTransform.Microsoft.AlphaImageLoader(src='\" + "
                  + instance + ".getURL() + \"',sizingMethod='crop')\"", false));
          screenProperties.add(new CssProperty("margin-left", "\"-\" + "
              + instance + ".getLeft() + \"px\"", false));
          screenProperties.add(new CssProperty("margin-top", "\"-\" + "
              + instance + ".getTop() + \"px\"", false));
          screenProperties.add(new CssProperty("width", "(" + instance
              + ".getLeft() + " + instance + ".getWidth()) + \"px\"", false));
          screenProperties.add(new CssProperty("height", "(" + instance
              + ".getTop() + " + instance + ".getHeight()) + \"px\"", false));

          ctx.insertAfter(screenRule);
        } else {
          properties.add(new CssProperty("background", "\"url(\\\"\" + "
              + instance + ".getURL() + \"\\\") -\" + " + instance
              + ".getLeft() + \"px -\" + " + instance + ".getTop() + \"px\"",
              false));
        }
      } catch (BadPropertyValueException e) {
        logger.log(TreeLogger.ERROR, "No user.agent property", e);
        throw new RuntimeException("No user.agent property");
      }
      ctx.replaceMe(spriteRule);
    }
  }

  private static class SubstitutionCollector extends CssVisitor {
    private final Map<String, CssDef> substitutions = new HashMap<String, CssDef>();

    @Override
    public void endVisit(CssDef x, Context ctx) {
      substitutions.put(x.getKey(), x);
    }

    @Override
    public void endVisit(CssEval x, Context ctx) {
      substitutions.put(x.getKey(), x);
    }

    @Override
    public void endVisit(CssUrl x, Context ctx) {
      substitutions.put(x.getKey(), x);
    }
  }

  private static class SubstitutionReplacer extends CssVisitor {
    private final ResourceContext context;
    private final TreeLogger logger;
    private final Map<String, CssDef> substitutions;

    public SubstitutionReplacer(TreeLogger logger, ResourceContext context,
        Map<String, CssDef> substitutions) {
      this.context = context;
      this.logger = logger;
      this.substitutions = substitutions;
    }

    @Override
    public void endVisit(CssProperty x, Context ctx) {
      // Nothing to do
      if (x.getValues().isEmpty()) {
        return;
      }

      // Only use the expression form if it's really necessary to do so
      StringBuilder expression = new StringBuilder();
      boolean useExpression = false;

      for (ListIterator<String> i = x.getValues().listIterator(); i.hasNext();) {
        String value = i.next();
        CssDef def = substitutions.get(value);

        if (def == null) {
          expression.append('"');
          expression.append(Generator.escape(value));
          expression.append('"');
        } else if (def instanceof CssEval) {
          expression.append(def.getValue());
          useExpression = true;
        } else if (def instanceof CssUrl) {
          String functionName = def.getValue();

          // Find the method
          JMethod method = context.getResourceBundleType().findMethod(
              functionName, new JType[0]);

          if (method == null) {
            logger.log(TreeLogger.ERROR, "Unable to find DataResource method "
                + functionName + " in "
                + context.getResourceBundleType().getQualifiedSourceName());
            throw new RuntimeException("Cannot find data function");
          }

          String instance = "((" + DataResource.class.getName() + ")("
              + context.getImplementationSimpleSourceName() + ".this."
              + functionName + "()))";

          expression.append("\"url('\" + ");
          expression.append(instance).append(".getUrl()");
          expression.append(" + \"')\"");
          useExpression = true;

        } else {
          expression.append('"');
          expression.append(Generator.escape(def.getValue()));
          if (i.hasNext()) {
            expression.append(' ');
          }
          expression.append('"');
          i.set(def.getValue());
        }

        if (i.hasNext()) {
          expression.append(" + \" \" + ");
        }
      }

      if (useExpression) {
        x.setExpression(expression.toString());
      }
    }
  }

  private String classPrefix;
  private Adler32 classPrefixHash;
  private ResourceContext context;
  private JClassType elementType;
  private boolean prettyOutput;
  private Map<String, String> replacements;
  private JClassType spriteImplType;
  private JClassType spriteType;
  private JClassType stringType;
  private Map<JMethod, URL> urlMap;

  @Override
  public String createAssignment(TreeLogger logger, JMethod method)
      throws UnableToCompleteException {

    URL resource = urlMap.get(method);
    SourceWriter sw = new StringSourceWriter();
    // Write the expression to create the subtype.
    sw.println("new " + method.getReturnType().getQualifiedSourceName()
        + "() {");
    sw.indent();

    // Convenience when examining the generated code.
    sw.println("// " + resource.toExternalForm());

    for (JMethod toImplement : method.getReturnType().isClassOrInterface().getOverridableMethods()) {
      String name = toImplement.getName();
      if ("getName".equals(name) || "getText".equals(name)) {
        continue;
      }

      String obfuscatedClassName;
      if (replacements.containsKey(name)) {
        // A replacement may have already been declared in a sibling CssResource
        obfuscatedClassName = replacements.get(name);
      } else if (prettyOutput) {
        obfuscatedClassName = name;
      } else {
        obfuscatedClassName = classPrefix + replacements.size();
      }
      replacements.put(name, obfuscatedClassName);

      // TODO check for string, no-args etc....
      if (toImplement.getReturnType().equals(stringType)
          && toImplement.getParameters().length == 0) {
        writeClassAssignment(sw, toImplement, obfuscatedClassName);

      } else if (toImplement.getReturnType().equals(spriteType)
          && toImplement.getParameters().length == 0) {
        writeSpriteAssignment(sw, toImplement, obfuscatedClassName);

      } else {
        logger.log(TreeLogger.ERROR, "Don't know how to implement method "
            + toImplement.getName());
        throw new UnableToCompleteException();
      }
    }

    sw.println("public String getText() {");
    sw.indent();

    String cssExpression = makeExpression(logger, resource, replacements);
    sw.println("return " + cssExpression + ";");
    sw.outdent();
    sw.println("}");

    sw.println("public String getName() {");
    sw.indent();
    sw.println("return \"" + method.getName() + "\";");
    sw.outdent();
    sw.println("}");

    sw.outdent();
    sw.println("}");

    return sw.toString();
  }

  @Override
  public void init(TreeLogger logger, ResourceContext context)
      throws UnableToCompleteException {
    this.context = context;

    try {
      PropertyOracle propertyOracle = context.getGeneratorContext().getPropertyOracle();
      String style = propertyOracle.getPropertyValue(logger,
          "CssResource.style").toLowerCase();
      prettyOutput = style.equals("pretty");
    } catch (BadPropertyValueException e) {
      logger.log(
          TreeLogger.WARN,
          "Unable to determine desired output format, defaulting to obfuscated",
          e);
    }

    // Find all of the types that we care about in the type system
    TypeOracle typeOracle = context.getGeneratorContext().getTypeOracle();
    elementType = typeOracle.findType(Element.class.getName());
    assert elementType != null;

    stringType = typeOracle.findType(String.class.getName());
    assert stringType != null;

    spriteType = typeOracle.findType(CssResource.Sprite.class.getName().replace(
        '$', '.'));
    assert spriteType != null;

    spriteImplType = typeOracle.findType(SpriteImpl.class.getName());
    assert spriteImplType != null;

    replacements = new HashMap<String, String>();
    urlMap = new IdentityHashMap<JMethod, URL>();

    // Determine the prefix to use for obfuscated CSS class names.
    CssResource.ClassPrefix prefixAnnotation = context.getResourceBundleType().getAnnotation(
        CssResource.ClassPrefix.class);
    if (prefixAnnotation != null) {
      classPrefix = prefixAnnotation.value();
      if (!Util.isValidJavaIdent(classPrefix)) {
        logger.log(TreeLogger.ERROR,
            "The @ClassPrefix annotation must be a valid Java identifier");
        throw new UnableToCompleteException();
      }
    } else {
      classPrefixHash = new Adler32();
    }
  }

  @Override
  public void prepare(TreeLogger logger, JMethod method)
      throws UnableToCompleteException {

    URL[] resources = ResourceGeneratorUtil.findResources(logger, context,
        method);

    if (resources.length != 1) {
      logger.log(TreeLogger.ERROR, "Exactly one "
          + ResourceGeneratorUtil.METADATA_TAG + " must be specified", null);
      throw new UnableToCompleteException();
    }

    URL resource = resources[0];
    urlMap.put(method, resource);

    if (classPrefixHash == null) {
      // A prefix was specified on the bundle type so we don't need to
      return;
    }

    try {
      InputStream in = resource.openStream();
      byte[] buffer = new byte[4096];
      int len = in.read(buffer);
      while (len > 0) {
        classPrefixHash.update(buffer, 0, len);
        len = in.read(buffer);
      }
      classPrefix = "G"
          + Long.toString(classPrefixHash.getValue(), Character.MAX_RADIX);
    } catch (IOException e) {
      logger.log(TreeLogger.ERROR, "Unable to compute prefix", e);
      throw new UnableToCompleteException();
    }
  }

  /**
   * Create a Java expression that evaluates to a string representation of the
   * given node.
   */
  private <T extends CssNode & HasNodes> String makeExpression(
      TreeLogger logger, T node) throws UnableToCompleteException {
    // Generate the CSS template
    DefaultTextOutput out = new DefaultTextOutput(!prettyOutput);
    CssGenerationVisitor v = new CssGenerationVisitor(out);
    v.accept(node);

    // Generate the final Java expression
    String template = out.toString();
    StringBuilder b = new StringBuilder();
    int start = 0;
    for (Map.Entry<Integer, List<CssNode>> entry : v.getSubstitutionPositions().entrySet()) {
      // Add the static section between start and the substitution point
      b.append('"');
      b.append(Generator.escape(template.substring(start, entry.getKey())));
      b.append("\" + ");

      // Add the nodes at the substitution point
      for (CssNode x : entry.getValue()) {
        if (x instanceof CssIf) {
          CssIf asIf = (CssIf) x;

          // Generate the sub-expression
          String expression = makeExpression(logger, new CollapsedNode<CssIf>(
              asIf));

          // ((expr) ? "CSS" : "") +
          b.append("((" + asIf.getExpression() + ") ? " + expression
              + " : \"\") + ");

        } else if (x instanceof CssProperty) {
          CssProperty property = (CssProperty) x;

          // Should have been fully-converted to an expression
          assert property.getValues().isEmpty();

          // (expr) +
          b.append("(" + property.getExpression() + ") + ");

        } else {
          // This indicates that some magic node is slipping by our visitors
          logger.log(TreeLogger.ERROR, "Unhandled substitution " + x.getClass());
          throw new UnableToCompleteException();
        }
      }
      start = entry.getKey();
    }

    // Add the remaining parts of the template
    b.append('"');
    b.append(Generator.escape(template.substring(start)));
    b.append('"');

    return b.toString();
  }

  /**
   * Create a Java expression that evaluates to the string representation of the
   * stylesheet resource.
   */
  private String makeExpression(TreeLogger logger, URL css,
      Map<String, String> classReplacements) throws UnableToCompleteException {

    try {
      // Create the base AST
      CssStylesheet sheet = GenerateCssAst.exec(logger, css);

      // Perform @def and @eval substitutions
      SubstitutionCollector collector = new SubstitutionCollector();
      collector.accept(sheet);

      (new SubstitutionReplacer(logger, context, collector.substitutions)).accept(sheet);

      // Evaluate @if statements based on deferred binding properties
      (new IfEvaluator(logger,
          context.getGeneratorContext().getPropertyOracle())).accept(sheet);

      // Create CSS sprites
      (new Spriter(logger, context)).accept(sheet);

      // Rename css .class selectors
      (new ClassRenamer(logger, classReplacements)).accept(sheet);

      // Combine rules with identical selectors
      (new SplitRulesVisitor()).accept(sheet);
      (new MergeIdenticalSelectorsVisitor()).accept(sheet);
      (new MergeRulesByContentVisitor()).accept(sheet);

      return makeExpression(logger, sheet);

    } catch (CssCompilerException e) {
      // Take this as a sign that one of the visitors was unhappy
      logger.log(TreeLogger.ERROR, "Unable to process CSS", e);
      throw new UnableToCompleteException();
    }
  }

  /**
   * Write the CssResource accessor method for simple String return values.
   */
  private void writeClassAssignment(SourceWriter sw, JMethod toImplement,
      String className) {

    sw.println(toImplement.getReadableDeclaration(false, true, true, true, true)
        + "{");
    sw.indent();
    sw.println("return \"" + className + "\";");
    sw.outdent();
    sw.println("}");
  }

  /**
   * Write the CssResource accessor method for Sprite return values.
   */
  private void writeSpriteAssignment(SourceWriter sw, JMethod toImplement,
      String className) {

    sw.println(toImplement.getReadableDeclaration(false, true, true, true, true)
        + "{");
    sw.indent();

    // return new Sprite() {
    sw.println("return new "
        + toImplement.getReturnType().getQualifiedSourceName() + "() {");
    sw.indent();

    // public void apply(Element e) {
    sw.println("public void apply(" + elementType.getQualifiedSourceName()
        + " element) {");
    sw.indent();

    // GWT.create(Sprite.class).apply(e, "replacementClass")
    sw.println("((" + spriteImplType.getQualifiedSourceName() + ")GWT.create("
        + spriteImplType.getQualifiedSourceName()
        + ".class)).apply(element, \"" + className + "\");");
    sw.outdent();
    sw.println("};");
    sw.outdent();
    sw.println("};");
    sw.outdent();
    sw.println("}");
  }
}
