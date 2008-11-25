package com.google.gwt.gen2.table.client;

import com.google.gwt.gen2.datepicker.client.DateBox;
import com.google.gwt.gen2.datepicker.client.DatePicker;
import com.google.gwt.gen2.event.dom.client.KeyDownEvent;
import com.google.gwt.gen2.event.dom.client.KeyDownHandler;
import com.google.gwt.gen2.event.logical.shared.SelectionEvent;
import com.google.gwt.gen2.event.logical.shared.SelectionHandler;
import com.google.gwt.gen2.table.client.TableDefinition.AbstractCellView;
import com.google.gwt.gen2.table.shared.DateColumnFilterInfo;
import com.google.gwt.gen2.table.shared.DateColumnFilterInfo.Operator;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

import java.util.Date;

public abstract class DateColumnDefinition<RowType> extends
    AbstractColumnDefinition<RowType, Date> {
  /**
   * DateColumnFilter filters columns based on dates
   */
  public static class DateColumnFilter extends ColumnFilter<Date> {
    private Operator operator;
    private Operator[] supportedOperators;
    private DateTimeFormat dateTimeFormat;

    private DateBox primaryDateBox, secondaryDateBox;
    private PushButton operatorButton;
    private HorizontalPanel horizontalPanel = new HorizontalPanel();

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
        if (operator == Operator.BETWEEN) {
          secondaryDateBox.setVisible(true);
          horizontalPanel.setCellWidth(primaryDateBox, "50%");
          horizontalPanel.setCellWidth(secondaryDateBox, "50%");
        } else {
          horizontalPanel.setCellWidth(primaryDateBox, "100%");
          secondaryDateBox.setVisible(false);
        }
        setButtonText(((PushButton) operatorButton), operator);
        Date primaryDate = primaryDateBox.getDatePicker().getDateShown();
        Date secondaryDate = secondaryDateBox.getDatePicker().getDateShown();
        fireColumnFilterChangedEvent(primaryDate, secondaryDate);
      }
    };
    private SelectionHandler<Date> selectionHandler = new SelectionHandler<Date>() {
      public void onSelection(SelectionEvent<Date> event) {
        fireColumnFilterChangedEvent();
      }
    };
    private KeyDownHandler keyDownHandler = new KeyDownHandler() {
      public void onKeyDown(KeyDownEvent event) {
        fireColumnFilterChangedEvent();
      }
    };

    /**
     * Creates a filter suitable for filtering columns containing dates
     * 
     * @param dateTimeFormat the dateTimeFormat used for the formatting of the
     *          filter text
     */
    public DateColumnFilter(DateTimeFormat dateTimeFormat) {
      this(dateTimeFormat, Operator.values());
    }
    /**
     * Creates a filter suitable for filtering columns containing dates
     * 
     * @param dateTimeFormat the dateTimeFormat used for the formatting of the
     *          filter text
     */
    public DateColumnFilter(DateTimeFormat dateTimeFormat,
        Operator[] supportedOperators) {
      this.dateTimeFormat = dateTimeFormat;
      this.supportedOperators = supportedOperators;
      this.operator = supportedOperators[0];
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.google.gwt.widgetideas.table.client.filter.ColumnFilter#
     * createFilterWidget()
     */
    public Widget createFilterWidget() {
      primaryDateBox = new DateBox();
      primaryDateBox.setDateFormat(dateTimeFormat);
      primaryDateBox.setWidth("100%");
      primaryDateBox.addStyleName("dateBox");
      primaryDateBox.addKeyDownHandler(keyDownHandler);
      secondaryDateBox = new DateBox();
      secondaryDateBox.setDateFormat(dateTimeFormat);
      secondaryDateBox.setWidth("100%");
      secondaryDateBox.addStyleName("dateBox");
      secondaryDateBox.addKeyDownHandler(keyDownHandler);
      operatorButton = new PushButton();
      setButtonText(operatorButton, operator);
      operatorButton.addClickListener(clickListener);
      operatorButton.addStyleName("operatorButton");
      horizontalPanel.addStyleName("columnDateFilter");
      horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
      horizontalPanel.add(operatorButton);
      horizontalPanel.add(primaryDateBox);
      horizontalPanel.add(secondaryDateBox);
      horizontalPanel.setCellWidth(operatorButton, "30px");
      horizontalPanel.setCellWidth(primaryDateBox, "100%");
      secondaryDateBox.setVisible(false);
      horizontalPanel.setSpacing(2);
      primaryDateBox.getTextBox().addChangeListener(new ChangeListener() {
        public void onChange(Widget sender) {
          String text = primaryDateBox.getText();
          System.out.println("Date: " + text);
        }
      });
      return horizontalPanel;
    }

    private void fireColumnFilterChangedEvent() {
      Date primaryDate = primaryDateBox.getDatePicker().getDateShown();
      primaryDate = primaryDateBox.getDatePicker().getHighlightedDate();
      Date secondaryDate = secondaryDateBox.getDatePicker().getHighlightedDate();
      if (primaryDateBox.getText().trim().equals("")) {
        primaryDate = null;
      }
      if (secondaryDateBox.getText().trim().equals("")) {
        secondaryDate = null;
      }
      fireColumnFilterChangedEvent(primaryDate, secondaryDate);
    }

    private void fireColumnFilterChangedEvent(Date primaryDate,
        Date secondaryDate) {
      fireColumnFilterChanged(new DateColumnFilterInfo(getColumn(),
          dateTimeFormat.getPattern(), primaryDate, secondaryDate, operator));
    }

    private void setButtonText(PushButton pushButton, Operator operator) {
      pushButton.getUpFace().setText(operator.getSymbol());
      pushButton.getUpHoveringFace().setText(operator.getSymbol());
      pushButton.getDownFace().setText(operator.getSymbol());
      pushButton.setTitle(operator.getTooltip());
    }
  }

  class DateCellRenderer implements CellRenderer<RowType, Date> {
    private final DateTimeFormat dateTimeFormat;

    public DateCellRenderer(DateTimeFormat dateTimeFormat) {
      this.dateTimeFormat = dateTimeFormat;
    }

    public void renderRowValue(RowType rowValue,
        ColumnDefinition<RowType, Date> columnDef,
        AbstractCellView<RowType> view) {
      Date cellValue = columnDef.getCellValue(rowValue);
      if (cellValue == null) {
        view.setHTML("&nbsp;");
      } else {
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
     * 
     * @param dateTimeFormat the {@link DateTimeFormat} used for inline editing
     */
    public DateCellEditor(DateTimeFormat dateTimeFormat) {
      this(new DateBox(), dateTimeFormat);
    }

    /**
     * Construct a new {@link DateCellEditor} using a {@link DateBox}.
     * 
     * @param dateBox the {@link DateBox} used for inline editing
     * @param dateTimeFormat the {@link DateTimeFormat} used for inline editing
     */
    public DateCellEditor(DateBox dateBox, DateTimeFormat dateTimeFormat) {
      super(dateBox);
      dateBox.setDateFormat(dateTimeFormat);
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

  public DateColumnDefinition(String header, DateTimeFormat dateTimeFormat,
      boolean sortingEnabled, boolean filterEnabled, boolean editingEnabled) {
    this(dateTimeFormat, sortingEnabled, filterEnabled, editingEnabled);
    setHeader(header);
  }

  public DateColumnDefinition(String header, DateTimeFormat dateTimeFormat,
      boolean sortingEnabled, boolean filterEnabled,
      Operator[] supportedOperators, boolean editingEnabled) {
    this(dateTimeFormat, sortingEnabled, filterEnabled, supportedOperators,
        editingEnabled);
    setHeader(header);
  }

  public DateColumnDefinition(Widget headerWidget,
      DateTimeFormat dateTimeFormat, boolean sortingEnabled,
      boolean filterEnabled, boolean editingEnabled) {
    this(dateTimeFormat, sortingEnabled, filterEnabled, editingEnabled);
    setHeaderWidget(headerWidget);
  }

  public DateColumnDefinition(Widget headerWidget,
      DateTimeFormat dateTimeFormat, boolean sortingEnabled,
      boolean filterEnabled, Operator[] supportedOperators,
      boolean editingEnabled) {
    this(dateTimeFormat, sortingEnabled, filterEnabled, supportedOperators,
        editingEnabled);
    setHeaderWidget(headerWidget);
  }

  /**
   * Column definition used for columns containing {@link Date} objects
   * 
   * @param dateTimeFormat
   * @param sortingEnabled
   * @param filterEnabled
   * @param editingEnabled
   */
  public DateColumnDefinition(DateTimeFormat dateTimeFormat,
      boolean sortingEnabled, boolean filterEnabled, boolean editingEnabled) {
    setColumnSortable(sortingEnabled);
    if (filterEnabled) {
      setColumnFilter(createDateColumnFilter(dateTimeFormat, Operator.values()));
    }
    setCellRenderer(createDateCellRenderer(dateTimeFormat));
    if (editingEnabled) {
      setCellEditor(createDateCellEditor(dateTimeFormat));
    }
  }

  /**
   * Column definition used for columns containing {@link Date} objects
   * 
   * @param dateTimeFormat
   * @param sortingEnabled
   * @param filterEnabled
   * @param supportedOperators
   * @param editingEnabled
   */
  public DateColumnDefinition(DateTimeFormat dateTimeFormat,
      boolean sortingEnabled, boolean filterEnabled,
      Operator[] supportedOperators, boolean editingEnabled) {
    setColumnSortable(sortingEnabled);
    setColumnFilterable(filterEnabled);
    if (filterEnabled) {
      setColumnFilter(createDateColumnFilter(dateTimeFormat, supportedOperators));
    }
    setCellRenderer(createDateCellRenderer(dateTimeFormat));
    if (editingEnabled) {
      setCellEditor(createDateCellEditor(dateTimeFormat));
    }
  }

  /**
   * Creates the default date filter implementation. Override this method to
   * provide custom filters
   * 
   * @return the created column filter suitable for filtering date columns
   */
  protected ColumnFilter<Date> createDateColumnFilter(
      DateTimeFormat dateTimeFormat, Operator[] supportedOperators) {
    return new DateColumnFilter(dateTimeFormat, supportedOperators);
  }

  /**
   * Creates the default date renderer implementation. Override this method to
   * provide custom date renderer
   * 
   * @return the created cell renderer suitable for rendering dates
   */
  protected CellRenderer<RowType, Date> createDateCellRenderer(
      DateTimeFormat dateTimeFormat) {
    return new DateCellRenderer(dateTimeFormat);
  }

  /**
   * Creates the default date editor implementation. Override this method to
   * provide custom date editor
   * 
   * @return the created cell editor suitable for editing dates
   */
  protected CellEditor<Date> createDateCellEditor(DateTimeFormat dateTimeFormat) {
    return new DateCellEditor(dateTimeFormat);
  }
  
  public void setCellValue(RowType rowValue, Date cellValue) {};
}