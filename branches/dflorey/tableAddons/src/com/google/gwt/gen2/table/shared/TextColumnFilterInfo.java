package com.google.gwt.gen2.table.shared;


/**
 * 
 */
public class TextColumnFilterInfo extends ColumnFilterInfo<String> {
  public enum Operator {
    STARTS_WITH("xx..", "startsWith", "Show text starting with"),
    STARTS_WITH_CASE_SENSITIVE("Xx..", "startsWithCaseSensitive", "Show items starting with (case sensitive)"),
    CONTAINS("..xx..", "contains", "Show items containing"),
    CONTAINS_CASE_SENSITIVE("..Xx..", "containsCaseSensitive", "Show items containing (case sensitive)"),
    ENDS_WITH("..xx", "endsWith", "Show items ending with"),
    ENDS_WITH_CASE_SENSITIVE("..Xx", "endsWithCaseSensitive", "Show items ending with (case sensitive)");
    
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

  private String filterText;
  private Operator operator;

  public TextColumnFilterInfo() {
    super();
  }

  public TextColumnFilterInfo(int column, String filterText, Operator operator) {
    super(column);
    this.filterText = filterText;
    this.operator = operator;
  }

  public String getFilterText() {
    return filterText;
  }

  public Operator getOperator() {
    return operator;
  }

  public String parse(String cellContent) {
    return cellContent;
  }

  public boolean isFilterMatching(String value) {
    if (operator == Operator.STARTS_WITH) {
      return value.toLowerCase().startsWith(filterText.toLowerCase());
    } else if (operator == Operator.STARTS_WITH_CASE_SENSITIVE) {
      return value.startsWith(filterText);
    } else if (operator == Operator.CONTAINS) {
      return value.toLowerCase().contains(filterText.toLowerCase());
    } else if (operator == Operator.CONTAINS_CASE_SENSITIVE) {
      return value.contains(filterText);
    } else if (operator == Operator.ENDS_WITH) {
      return value.toLowerCase().endsWith(filterText.toLowerCase());
    } else if (operator == Operator.ENDS_WITH_CASE_SENSITIVE) {
      return value.endsWith(filterText);
    }
    return false;
  }

  public ColumnFilterInfo<String> copy() {
    return new TextColumnFilterInfo(getColumn(), filterText, operator);
  }
}