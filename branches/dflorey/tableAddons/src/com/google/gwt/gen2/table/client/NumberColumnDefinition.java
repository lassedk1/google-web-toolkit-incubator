package com.google.gwt.gen2.table.client;

import com.google.gwt.gen2.table.client.TableDefinition.AbstractCellView;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnFilterInfo;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

public abstract class NumberColumnDefinition<RowType> extends
    AbstractColumnDefinition<RowType, Double> {
  /**
   * ColumnTextFilter filters columns containing numbers
   */
  public static class NumberColumnFilter extends ColumnFilter {
    private int operator = 0;
    private String[] operatorButtonText = new String[] {
        "=", "!=", "<", ">", ">-<"};

    private NumberFormat numberFormat;
    private TextBox primaryNumberBox, secondaryNumberBox;
    private PushButton operatorButton;
    private HorizontalPanel horizontalPanel = new HorizontalPanel();

    private ClickListener clickListener = new ClickListener() {
      public void onClick(Widget sender) {
        operator++;
        if (operator == NumberColumnFilterInfo.COUNT_OPERATORS) {
          operator = 0;
        }
        if (operator == NumberColumnFilterInfo.BETWEEN) {
          secondaryNumberBox.setVisible(true);
          horizontalPanel.setCellWidth(primaryNumberBox, "50%");
          horizontalPanel.setCellWidth(secondaryNumberBox, "50%");
        } else {
          horizontalPanel.setCellWidth(primaryNumberBox, "100%");
          secondaryNumberBox.setVisible(false);
        }
        setButtonText(((PushButton) operatorButton), operator);
        fireColumnFilterChangedEvent();
      }
    };
    private KeyboardListener keyboardListener = new KeyboardListener() {
      public void onKeyDown(Widget sender, char keyCode, int modifiers) {
      }

      public void onKeyPress(Widget sender, char keyCode, int modifiers) {
      }

      public void onKeyUp(Widget sender, char keyCode, int modifiers) {
        fireColumnFilterChangedEvent();
      }
    };

    /**
     * Creates a filter suitable for filtering columns containing numbers
     */
    public NumberColumnFilter(NumberFormat numberFormat) {
      this.numberFormat = numberFormat;
    }

    public Widget createFilterWidget() {
      primaryNumberBox = new TextBox();
      primaryNumberBox.setWidth("100%");
      primaryNumberBox.addStyleName("numberBox");
      secondaryNumberBox = new TextBox();
      secondaryNumberBox.setWidth("100%");
      secondaryNumberBox.addStyleName("numberBox");
      operatorButton = new PushButton();
      setButtonText(operatorButton, operator);
      operatorButton.addClickListener(clickListener);
      operatorButton.addStyleName("operatorButton");
      horizontalPanel.addStyleName("columnNumberFilter");
      horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
      horizontalPanel.add(operatorButton);
      horizontalPanel.add(primaryNumberBox);
      horizontalPanel.add(secondaryNumberBox);
      horizontalPanel.setCellWidth(operatorButton, "30px");
      horizontalPanel.setCellWidth(primaryNumberBox, "100%");
      secondaryNumberBox.setVisible(false);
      horizontalPanel.setSpacing(2);
      primaryNumberBox.addKeyboardListener(keyboardListener);
      secondaryNumberBox.addKeyboardListener(keyboardListener);
      return horizontalPanel;
    }

    private void fireColumnFilterChangedEvent() {
      Double primaryNumber = null, secondaryNumber = null;
      if (primaryNumberBox.getText().length() > 0) {
        try {
          primaryNumber = numberFormat.parse(primaryNumberBox.getText());
          try {
            secondaryNumber = numberFormat.parse(secondaryNumberBox.getText());
          } catch (NumberFormatException e) {
          }
        } catch (NumberFormatException e) {
        }
      }
      fireColumnFilterChanged(new NumberColumnFilterInfo(numberFormat, getColumn(),
          primaryNumber, secondaryNumber, operator));
    }

    private void setButtonText(PushButton pushButton, int operator) {
      pushButton.getUpFace().setText(operatorButtonText[operator]);
      pushButton.getUpHoveringFace().setText(operatorButtonText[operator]);
      pushButton.getDownFace().setText(operatorButtonText[operator]);
    }
  }

  /**
   * 
   */
  public static class NumberColumnFilterInfo extends ColumnFilterInfo<Double> {
    public static final int EQUAL = 0;
    public static final int NOT_EQUAL = 1;
    public static final int LESS_THAN = 2;
    public static final int GREATER_THAN = 3;
    public static final int BETWEEN = 4;
    public static final int COUNT_OPERATORS = 5;

    private Double primaryNumber, secondaryNumber;
    private int operator;
    private transient NumberFormat numberFormat;

    public NumberColumnFilterInfo(NumberFormat numberFormat) {
      super();
      this.numberFormat = numberFormat;
    }

    public NumberColumnFilterInfo(NumberFormat numberFormat, int column, Double primaryNumber,
        Double secondaryNumber, int operator) {
      super(column);
      this.numberFormat = numberFormat;
      this.primaryNumber = primaryNumber;
      this.secondaryNumber = secondaryNumber;
      this.operator = operator;
    }

    public int getOperator() {
      return operator;
    }

    public double getPrimaryNumber() {
      return primaryNumber;
    }

    public double getSecondaryNumber() {
      return secondaryNumber;
    }

    public boolean isFilterMatchingCellContent(String cellContent) {
      try {
        Double number = numberFormat.parse(cellContent);
        return isFilterMatchingValueObject(number);
      } catch (NumberFormatException e) {
        return false;
      }
    }

    public boolean isFilterMatchingValueObject(Double value) {
      // Empty filter will match everything
      if (primaryNumber == null) {
        return true;
      }
      if (operator == EQUAL) {
        return value == primaryNumber;
      } else if (operator == NOT_EQUAL) {
        return value  != primaryNumber;
      } else if (operator == LESS_THAN) {
        return value  < primaryNumber;
      } else if (operator == GREATER_THAN) {
        return value  > primaryNumber;
      } else if (operator == BETWEEN) {
        return value >= primaryNumber
            && value <= secondaryNumber;
      }
      return false;
    }

    public ColumnFilterInfo<Double> copy() {
      return new NumberColumnFilterInfo(numberFormat, getColumn(), primaryNumber,
          secondaryNumber, operator);
    }
  }

  /**
   * The {@link NumberCellRenderer} used by the {@link NumberColumnDefinition}
   * when the user does not specify one.
   * 
   * @param <RowType> the type of the row value
   */
  class NumberCellRenderer implements CellRenderer<RowType, Double> {
    private final NumberFormat numberFormat;

    public NumberCellRenderer(NumberFormat numberFormat) {
      this.numberFormat = numberFormat;
    }

    public void renderRowValue(RowType rowValue,
        ColumnDefinition<RowType, Double> columnDef,
        AbstractCellView<RowType> view) {
      Double cellValue = columnDef.getCellValue(rowValue);
      if (cellValue == null) {
        view.setText("");
      } else {
        view.setText(numberFormat.format(cellValue));
      }
    }
  }

  /**
   * An {@link InlineCellEditor} that can be used to edit {@link Number Numbers}
   * .
   */
  class NumberCellEditor extends InlineCellEditor<Double> {
    /**
     * The text field used in this editor.
     */
    private TextBoxBase textBox;
    private NumberFormat numberFormat;

    /**
     * Construct a new {@link TextCellEditor} using a normal {@link TextBox}.
     */
    public NumberCellEditor(NumberFormat numberFormat) {
      this(new TextBox(), numberFormat);
    }

    /**
     * Construct a new {@link TextCellEditor} using the specified
     * {@link TextBox}.
     * 
     * @param textBox the text box to use
     */
    public NumberCellEditor(TextBoxBase textBox, NumberFormat numberFormat) {
      super(textBox);
      this.textBox = textBox;
      this.numberFormat = numberFormat;
    }

    /**
     * Construct a new {@link TextCellEditor} using the specified
     * {@link TextBox} and images.
     * 
     * @param textBox the text box to use
     * @param images the images to use for the accept/cancel buttons
     */
    public NumberCellEditor(TextBoxBase textBox, InlineCellEditorImages images,
        NumberFormat numberFormat) {
      super(textBox, images);
      this.textBox = textBox;
      this.numberFormat = numberFormat;
    }

    @Override
    public void editCell(CellEditInfo cellEditInfo, Double cellValue,
        Callback<Double> callback) {
      super.editCell(cellEditInfo, cellValue, callback);
      textBox.setFocus(true);
    }

    /**
     * @return the text box used in the editor
     */
    protected TextBoxBase getTextBox() {
      return textBox;
    }

    @Override
    protected Double getValue() {
      return numberFormat.parse(textBox.getText());
    }

    @Override
    protected void setValue(Double cellValue) {
      if (cellValue == null) {
        textBox.setText("");
      } else {
        textBox.setText(numberFormat.format(cellValue));
      }
    }
  }

  protected NumberFormat numberFormat;

  public NumberColumnDefinition(NumberFormat numberFormat,
      boolean filterEnabled, boolean editingEnabled) {
    this.numberFormat = numberFormat;
    if (filterEnabled) {
      setColumnFilter(createNumberColumnFilter(numberFormat));
    }
    setCellRenderer(createNumberCellRenderer(numberFormat));
    if (editingEnabled) {
      setCellEditor(createNumberCellEditor(numberFormat));
    }
  }

  /**
   * Creates the default text editor implementation. Override this method to
   * provide custom text editor
   * 
   * @return the created cell editor suitable for editing text
   */
  protected CellEditor<Double> createNumberCellEditor(NumberFormat numberFormat) {
    return new NumberCellEditor(numberFormat);
  }

  /**
   * Creates the default text renderer implementation. Override this method to
   * provide custom text renderer
   * 
   * @return the created cell renderer suitable for rendering text
   */
  protected CellRenderer<RowType, Double> createNumberCellRenderer(
      NumberFormat numberFormat) {
    return new NumberCellRenderer(numberFormat);
  }

  /**
   * Creates the default text filter implementation. Override this method to
   * provide custom filters
   * 
   * @return the created column filter suitable for filtering text columns
   */
  protected ColumnFilter createNumberColumnFilter(NumberFormat numberFormat) {
    return new NumberColumnFilter(numberFormat);
  }
}