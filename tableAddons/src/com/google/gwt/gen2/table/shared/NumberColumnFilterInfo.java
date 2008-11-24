package com.google.gwt.gen2.table.shared;

import com.google.gwt.i18n.client.NumberFormat;

/**
 * 
 */
public class NumberColumnFilterInfo extends ColumnFilterInfo<Double> {
  public enum Operator {
    EQUALS("=", "equals", "Shows numbers that are equal"),
    NOT_EQUAL("!=", "not equal", "Shows numbers that not equal"),
    LESS_THAN("<", "less", "Show numbers less than given value"),
    GREATER_THAN(">", "greater", "Show numbers greater than given value"),
    BETWEEN("-", "between", "Show numbers between the given values");
    
    private final String symbol;
    private final String title;
    private final String tooltip;

    Operator(String symbol, String title, String tooltip) {
      this.symbol = symbol;
      this.title = title;
      this.tooltip = tooltip;
    }

    public String getSymbol() {
      return symbol;
    }

    public String getTitle() {
      return title;
    }

    public String getTooltip() {
      return tooltip;
    }
  };      
  

  private Double primaryNumber, secondaryNumber;
  private Operator operator;
  private transient NumberFormat numberFormat;

  public NumberColumnFilterInfo(NumberFormat numberFormat) {
    super();
    this.numberFormat = numberFormat;
  }

  public NumberColumnFilterInfo(NumberFormat numberFormat, int column,
      Double primaryNumber, Double secondaryNumber, Operator operator) {
    super(column);
    this.numberFormat = numberFormat;
    this.primaryNumber = primaryNumber;
    this.secondaryNumber = secondaryNumber;
    this.operator = operator;
  }

  public Operator getOperator() {
    return operator;
  }

  public double getPrimaryNumber() {
    return primaryNumber;
  }

  public double getSecondaryNumber() {
    return secondaryNumber;
  }

  public Double parse(String cellContent) {
    try {
      return numberFormat.parse(cellContent);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public boolean isFilterMatching(Double value) {
    // Empty filter will match everything
    if (primaryNumber == null) {
      return true;
    }
    if (operator == Operator.EQUALS) {
      return value == primaryNumber;
    } else if (operator == Operator.NOT_EQUAL) {
      return value != primaryNumber;
    } else if (operator == Operator.LESS_THAN) {
      return value < primaryNumber;
    } else if (operator == Operator.GREATER_THAN) {
      return value > primaryNumber;
    } else if (operator == Operator.BETWEEN) {
      return value >= primaryNumber && value <= secondaryNumber;
    }
    return false;
  }

  public ColumnFilterInfo<Double> copy() {
    return new NumberColumnFilterInfo(numberFormat, getColumn(),
        primaryNumber, secondaryNumber, operator);
  }
}