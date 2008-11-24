package com.google.gwt.gen2.table.client;

import com.google.gwt.gen2.table.client.TableDefinition.AbstractCellView;
import com.google.gwt.gen2.table.shared.TextColumnFilterInfo;
import com.google.gwt.gen2.table.shared.TextColumnFilterInfo.Operator;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

public abstract class TextColumnDefinition<RowType> extends
    AbstractColumnDefinition<RowType, String> {
  /**
   * ColumnTextFilter filters columns based on text
   */
  public static class TextColumnFilter extends ColumnFilter<String> {
    private Operator operator;
    private final Operator[] supportedOperators;
    private TextBox filterTextBox;
    private PushButton operatorButton;
    private ClickListener clickListener = new ClickListener() {
      public void onClick(Widget sender) {
        for (int i = 0; i < supportedOperators.length; i++) {
          if (operator == supportedOperators[i]) {
            if (i < supportedOperators.length - 1) {
              operator = supportedOperators[i + 1];
            } else {
              operator = supportedOperators[0];
            }
            break;
          }
        }
        setButtonText(((PushButton) operatorButton), operator);
        fireColumnFilterChanged(new TextColumnFilterInfo(getColumn(),
            filterTextBox.getText(), operator));
      }
    };


    /**
     * Creates a filter suitable for filtering columns containing text
     * 
     * @param column the column to be filtered
     */
    public TextColumnFilter(Operator[] supportedOperators) {
      this.operator = supportedOperators[0];
      this.supportedOperators = supportedOperators;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.google.gwt.widgetideas.table.client.filter.ColumnFilter#
     * createFilterWidget()
     */
    public Widget createFilterWidget() {
      filterTextBox = new TextBox();
      filterTextBox.setWidth("100%");
      filterTextBox.addStyleName("textBox");
      operatorButton = new PushButton();
      setButtonText(operatorButton, operator);
      operatorButton.addClickListener(clickListener);
      operatorButton.addStyleName("operatorButton");
      HorizontalPanel horizontalPanel = new HorizontalPanel();
      horizontalPanel.addStyleName("columnTextFilter");
      horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
      horizontalPanel.add(operatorButton);
      horizontalPanel.add(filterTextBox);
      horizontalPanel.setCellWidth(operatorButton, "30px");
      horizontalPanel.setCellWidth(filterTextBox, "100%");
      horizontalPanel.setSpacing(2);
      filterTextBox.addKeyboardListener(new KeyboardListener() {
        public void onKeyDown(Widget sender, char keyCode, int modifiers) {
        }

        public void onKeyPress(Widget sender, char keyCode, int modifiers) {
        }

        public void onKeyUp(Widget sender, char keyCode, int modifiers) {
          fireColumnFilterChanged(new TextColumnFilterInfo(getColumn(),
              filterTextBox.getText(), operator));
        }
      });
      return horizontalPanel;
    }

    private void setButtonText(PushButton pushButton, Operator operator) {
      pushButton.getUpFace().setText(operator.getSymbol());
      pushButton.getUpHoveringFace().setText(operator.getSymbol());
      pushButton.getDownFace().setText(operator.getSymbol());
      pushButton.setTitle(operator.getTooltip());
    }
  }

  /**
   * The {@link TextCellRenderer} used by the {@link TextColumnDefinition} when
   * the user does not specify one.
   * 
   * @param <RowType> the type of the row value
   * @param <ColType> the data type of the column
   */
  class TextCellRenderer implements CellRenderer<RowType, String> {
    public void renderRowValue(RowType rowValue,
        ColumnDefinition<RowType, String> columnDef,
        AbstractCellView<RowType> view) {
      String cellValue = columnDef.getCellValue(rowValue);
      if (cellValue == null) {
        view.setText("");
      } else {
        view.setHTML(cellValue.toString());
      }
    }
  }

  /**
   * An {@link InlineCellEditor} that can be used to edit {@link String Strings}
   * .
   */
  class TextCellEditor extends InlineCellEditor<String> {
    /**
     * The text field used in this editor.
     */
    private TextBoxBase textBox;

    /**
     * Construct a new {@link TextCellEditor} using a normal {@link TextBox}.
     */
    public TextCellEditor() {
      this(new TextBox());
    }

    /**
     * Construct a new {@link TextCellEditor} using the specified
     * {@link TextBox}.
     * 
     * @param textBox the text box to use
     */
    public TextCellEditor(TextBoxBase textBox) {
      super(textBox);
      this.textBox = textBox;
    }

    /**
     * Construct a new {@link TextCellEditor} using the specified
     * {@link TextBox} and images.
     * 
     * @param textBox the text box to use
     * @param images the images to use for the accept/cancel buttons
     */
    public TextCellEditor(TextBoxBase textBox, InlineCellEditorImages images) {
      super(textBox, images);
      this.textBox = textBox;
    }

    @Override
    public void editCell(CellEditInfo cellEditInfo, String cellValue,
        Callback<String> callback) {
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
    protected String getValue() {
      return textBox.getText();
    }

    @Override
    protected void setValue(String cellValue) {
      if (cellValue == null) {
        cellValue = "";
      }
      textBox.setText(cellValue);
    }
  }

  public TextColumnDefinition(Widget headerWidget, boolean filterEnabled, boolean editingEnabled) {
    this(filterEnabled, editingEnabled);
    setHeaderWidget(headerWidget);
  }

  public TextColumnDefinition(String header, boolean filterEnabled, boolean editingEnabled) {
    this(filterEnabled, editingEnabled);
    setHeader(header);
  }

  public TextColumnDefinition(Widget headerWidget, boolean filterEnabled, Operator[] supportedOperators, boolean editingEnabled) {
	  this(filterEnabled, supportedOperators, editingEnabled);
	  setHeaderWidget(headerWidget);
  }

  public TextColumnDefinition(String header, boolean filterEnabled, Operator[] supportedOperators, boolean editingEnabled) {
	  this(filterEnabled, supportedOperators, editingEnabled);
	  setHeader(header);
  }
  
  public TextColumnDefinition(boolean filterEnabled, boolean editingEnabled) {
    this(filterEnabled, Operator.values(), editingEnabled);
  }
    
  public TextColumnDefinition(boolean filterEnabled, Operator[] supportedOperators, boolean editingEnabled) {
    if (filterEnabled) {
      setColumnFilter(createTextColumnFilter(supportedOperators));
    }
    setCellRenderer(createTextCellRenderer());
    if (editingEnabled) {
      setCellEditor(createTextCellEditor());
    }
  }

  /**
   * Creates the default text editor implementation. Override this method to
   * provide custom text editor
   * 
   * @return the created cell editor suitable for editing text
   */
  protected CellEditor<String> createTextCellEditor() {
    return new TextCellEditor();
  }

  /**
   * Creates the default text renderer implementation. Override this method to
   * provide custom text renderer
   * 
   * @return the created cell renderer suitable for rendering text
   */
  protected CellRenderer<RowType, String> createTextCellRenderer() {
    return new TextCellRenderer();
  }

  /**
   * Creates the default text filter implementation. Override this method to
   * provide custom filters
   * 
   * @return the created column filter suitable for filtering text columns
   */
  protected ColumnFilter<String> createTextColumnFilter(Operator[] supportedOperators) {
    return new TextColumnFilter(supportedOperators);
  }
  
  public void setCellValue(RowType rowValue, String cellValue) {};
}