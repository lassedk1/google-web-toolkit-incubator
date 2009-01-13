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
package com.google.gwt.libideas.resources.css.ast;

import com.google.gwt.core.ext.Generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Maps a named property to a Value.
 */
public class CssProperty extends CssNode {

  /**
   * Represents a sequence of no-arg method invocations.
   */
  public static class DotPathValue implements Value {
    private final String path;
    private final String suffix;

    public DotPathValue(String path, String suffix) {
      this.path = path;
      this.suffix = suffix;
    }

    public String getExpression() {
      return path.replace(".", "().") + "() + \"" + Generator.escape(suffix)
          + "\"";
    }

    public String getPath() {
      return path;
    }

    public String getSuffix() {
      return suffix;
    }

    public String toCss() {
      return "value(\"" + path + "\""
          + (suffix == null ? "" : (", \"" + suffix + "\"")) + ")";
    }

    /**
     * For debugging only.
     */
    @Override
    public String toString() {
      return path + "()" + (suffix != null ? " " + suffix : "");
    }
  }

  /**
   * Represents a literal Java expression.
   */
  public static class ExpressionValue implements Value {
    private final String expression;

    public ExpressionValue(String expression) {
      this.expression = expression;
    }

    public String getExpression() {
      return expression;
    }

    public String toCss() {
      return "/* Java expression */";
    }

    /**
     * For debugging only.
     */
    @Override
    public String toString() {
      return expression;
    }
  }

  /**
   * Represents an identifier in the CSS source.
   */
  public static class IdentValue implements Value {
    private final String ident;

    public IdentValue(String ident) {
      this.ident = ident;
    }

    public String getExpression() {
      return '"' + Generator.escape(ident) + '"';
    }

    public String getIdent() {
      return ident;
    }

    public String toCss() {
      return ident;
    }

    /**
     * For debugging only.
     */
    @Override
    public String toString() {
      return ident;
    }
  }

  /**
   * Represents a space-separated list of Values.
   */
  public static class ListValue implements Value {
    private final List<Value> values;

    public ListValue(List<Value> values) {
      this.values = new ArrayList<Value>(values);
    }

    public ListValue(Value... values) {
      this(Arrays.asList(values));
    }

    public String getExpression() {
      StringBuilder toReturn = new StringBuilder();
      for (Iterator<Value> i = values.iterator(); i.hasNext();) {
        toReturn.append(i.next().getExpression());
        if (i.hasNext()) {
          toReturn.append("+ \" \" +");
        }
      }
      return toReturn.toString();
    }

    public List<Value> getValues() {
      return values;
    }

    public String toCss() {
      StringBuffer sb = new StringBuffer();
      for (Value v : values) {
        sb.append(" ").append(v.toCss());
      }
      return sb.substring(1);
    }

    /**
     * For debugging only.
     */
    @Override
    public String toString() {
      return values.toString();
    }
  }

  /**
   * Represents a numeric value, possibly with attached units.
   */
  public static class NumberValue implements Value {
    private final String expression;
    private final String units;
    private final float value;

    public NumberValue(float value) {
      this(value, null);
    }

    public NumberValue(float value, String units) {
      this.value = value;
      this.units = units;

      String s;
      int i = (int) value;
      if (i == value) {
        s = String.valueOf(i);
      } else {
        s = String.valueOf(value);
      }

      if (units != null && value != 0) {
        expression = '"' + s + Generator.escape(units) + '"';
      } else {
        expression = '"' + s + '"';
      }
    }

    public String getExpression() {
      return expression;
    }

    public String getUnits() {
      return units;
    }

    public float getValue() {
      return value;
    }

    public String toCss() {
      return value + (units != null ? units : "");
    }

    /**
     * For debugging only.
     */
    @Override
    public String toString() {
      return toCss();
    }
  }

  /**
   * Represents one or more unquoted string literals.
   */
  public static class StringValue implements Value {
    private final String value;

    public StringValue(String value) {
      this.value = value;
    }

    public String getExpression() {
      return "\"" + Generator.escape(value) + "\"";
    }

    public String getValue() {
      return value;
    }

    public String toCss() {
      return '"' + value + '"';
    }

    /**
     * For debugging only.
     */
    @Override
    public String toString() {
      return toCss();
    }
  }

  /**
   * An abstract encapsulation of property values in GWT CSS.
   */
  public interface Value {
    /**
     * Generate a Java expression whose execution results in the value.
     */
    String getExpression();

    /**
     * Generate a CSS expression that represents the Value.
     */
    String toCss();
  }

  private boolean important;

  private String name;
  private ListValue value;

  public CssProperty(String name, Value value, boolean important) {
    this.name = name;
    setValue(value);
    this.important = important;
  }

  public String getName() {
    return name;
  }

  public ListValue getValues() {
    return value;
  }

  public boolean isImportant() {
    return important;
  }

  public void setImportant(boolean important) {
    this.important = important;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setValue(Value value) {
    if (value instanceof ListValue) {
      this.value = (ListValue) value;
    } else {
      this.value = new ListValue(value);
    }
  }

  public void traverse(CssVisitor visitor, Context context) {
    visitor.visit(this, context);
    visitor.endVisit(this, context);
  }
}
