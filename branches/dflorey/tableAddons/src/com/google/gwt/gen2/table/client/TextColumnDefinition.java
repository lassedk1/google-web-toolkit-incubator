package com.google.gwt.gen2.table.client;

import com.google.gwt.gen2.table.client.TableDefinition.AbstractCellView;
import com.google.gwt.gen2.table.client.filter.ColumnTextFilter;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

public abstract class TextColumnDefinition<RowType> extends
    AbstractColumnDefinition<RowType, String> {
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

  public TextColumnDefinition(boolean filterEnabled, boolean editingEnabled) {
    setCellRenderer(new TextCellRenderer());
    if (filterEnabled) {
      setColumnFilter(new ColumnTextFilter());
    }
    if (editingEnabled) {
      setCellEditor(new TextCellEditor());
    }
  }
}