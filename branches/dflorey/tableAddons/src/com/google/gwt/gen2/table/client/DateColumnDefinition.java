package com.google.gwt.gen2.table.client;

import com.google.gwt.gen2.datepicker.client.DateBox;
import com.google.gwt.gen2.datepicker.client.DatePicker;
import com.google.gwt.gen2.table.client.TableDefinition.AbstractCellView;
import com.google.gwt.gen2.table.client.filter.ColumnDateFilter;
import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;

public abstract class DateColumnDefinition<RowType> extends
    AbstractColumnDefinition<RowType, Date> {
  class DateCellRenderer implements CellRenderer<RowType, Date> {
    public void renderRowValue(RowType rowValue,
        ColumnDefinition<RowType, Date> columnDef, AbstractCellView<RowType> view) {
      Date cellValue = columnDef.getCellValue(rowValue);
      if (cellValue == null) {
        view.setText("");
      } else {
        DateTimeFormat dateTimeFormat = DateColumnDefinition.this.dateTimeFormat;
        if (dateTimeFormat != null) {
          view.setHTML(dateTimeFormat.format(cellValue));
        } else {
          view.setHTML(cellValue.toString());
        }
      }
    }
  }
  /**
   * An {@link InlineCellEditor} that can be used to edit {@link Date Dates}.
   */
  class DateCellEditor extends InlineCellEditor<Date> {
    /**
     * The date box used in this editor.
     */
    private DateBox dateBox;

    /**
     * Construct a new {@link DateCellEditor} using a {@link DateBox}.
     */
    public DateCellEditor() {
      this(new DateBox());
    }

    /**
     * Construct a new {@link DateCellEditor} using the specified
     * {@link DateBox}.
     * 
     * @param dateBox the text box to use
     */
    public DateCellEditor(DateBox dateBox) {
      super(dateBox);
      this.dateBox = dateBox;
      DatePicker.injectDefaultCss();
    }

    /**
     * Construct a new {@link DateCellEditor} using the specified
     * {@link DateBox} and images.
     * 
     * @param dateBox the date box to use
     * @param images the images to use for the accept/cancel buttons
     */
    public DateCellEditor(DateBox dateBox, InlineCellEditorImages images) {
      super(dateBox, images);
      this.dateBox = dateBox;
      DatePicker.injectDefaultCss();
    }

    @Override
    public void editCell(CellEditInfo cellEditInfo, Date cellValue,
        Callback<Date> callback) {
      super.editCell(cellEditInfo, cellValue, callback);
      dateBox.setFocus(true);
    }

    /**
     * @return the date box used in the editor
     */
    protected DateBox getDateBox() {
      return dateBox;
    }

    @Override
    protected Date getValue() {
      return dateBox.getDatePicker().getValue();
    }

    @Override
    protected void setValue(Date cellValue) {
      if (cellValue == null) {
        cellValue = new Date();
      }
      dateBox.getDatePicker().setValue(cellValue);
    }
  }

  protected DateTimeFormat dateTimeFormat;

  public DateColumnDefinition(DateTimeFormat dateTimeFormat,
      boolean editingEnabled) {
    this.dateTimeFormat = dateTimeFormat;
    setColumnFilter(new ColumnDateFilter(dateTimeFormat.getPattern()));
    setCellRenderer(new DateCellRenderer());
    if (editingEnabled) {
      setCellEditor(new DateCellEditor());
    }
  }
}