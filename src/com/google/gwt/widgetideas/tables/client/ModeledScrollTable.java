/*
 * Copyright 2007 Google Inc.
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
package com.google.gwt.widgetideas.tables.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link ScrollTable} that contains a modeled grid, allowing for
 * dynamic loading.
 */
public class ModeledScrollTable extends ScrollTable
    implements ClickListener, ModeledGridListener {
  /**
   * An {@link ImageBundle} that provides images for {@link ModeledScrollTable}.
   */
  public static interface ModelledScrollTableImages extends ScrollTableImages {
    /**
     * An image used to navigate to the first page.
     * 
     * @return a prototype of this image
     */
    AbstractImagePrototype modeledScrollTableFirst();

    /**
     * An image used to navigate to the last page.
     * 
     * @return a prototype of this image
     */
    AbstractImagePrototype modeledScrollTableLast();

    /**
     * An image used to navigate to the next page.
     * 
     * @return a prototype of this image
     */
    AbstractImagePrototype modeledScrollTableNext();

    /**
     * An image used to navigate to the previous page.
     * 
     * @return a prototype of this image
     */
    AbstractImagePrototype modeledScrollTablePrev();
  }

  /**
   * The button used to go to the first page.
   */
  private Image pagingButtonFirst = new Image();

  /**
   * The button used to go to the last page.
   */
  private Image pagingButtonLast = new Image();

  /**
   * The button used to go to the next page.
   */
  private Image pagingButtonNext = new Image();
  
  /**
   * The button used to go to the previous page.
   */
  private Image pagingButtonPrev = new Image();

  /**
   * The box that contains the current page number.
   */
  private TextBox pagingCurPageBox = new TextBox();
  
  /**
   * The HTML field that contains the number of pages.
   */
  private HTML pagingNumPagesLabel = new HTML("<B>1</B>");
  
  /**
   * The wrapper that contains the buttons and text used for paging.
   */
  private Element pagingWrapper = DOM.createDiv();

  /**
   * Constructor.
   * 
   * @param oracle the oracle used to retrieve data
   */
  public ModeledScrollTable(TableOracle oracle) {
    this(new GridModel(oracle));
  }
  
  /**
   * Constructor.
   * 
   * @param gridModel the gridModel to apply to the dataTable.
   */
  public ModeledScrollTable(GridModel gridModel) {
    this(new ModeledGrid(gridModel));
  }
  
  /**
   * Constructor.
   * 
   * @param dataTable the data table
   */
  public ModeledScrollTable(ModeledGrid dataTable) {
    this(dataTable, (ModelledScrollTableImages)
        GWT./*<ModelledScrollTableImages>*/create(ModelledScrollTableImages.class));
  }
  
  /**
   * Constructor.
   * 
   * @param dataTable the data table
   * @param images the images to use in the table
   */
  public ModeledScrollTable(ModeledGrid dataTable,
      ModelledScrollTableImages images) {
    super(dataTable, images);
    dataTable.addModeledGridListener(this);
    
    // Disallow non-numeric pages
    pagingCurPageBox.addKeyboardListener(new KeyboardListenerAdapter() {
      public void onKeyPress(Widget sender, char keyCode, int modifiers) {
        if ( keyCode == (char) KEY_ENTER) {
          int column = 0;
          try {
            column = Integer.parseInt(pagingCurPageBox.getText()) - 1;
          } catch (NumberFormatException e) {
            // This will catch an empty box
            pagingCurPageBox.setText("1");
          }
          ModeledGrid dataTable = (ModeledGrid) getDataTable();
          dataTable.gotoPage(column);
        } else if ((!Character.isDigit(keyCode)) && (keyCode != (char) KEY_TAB)
            && (keyCode != (char) KEY_BACKSPACE)
            && (keyCode != (char) KEY_DELETE) && (keyCode != (char) KEY_ENTER) 
            && (keyCode != (char) KEY_HOME) && (keyCode != (char) KEY_END)
            && (keyCode != (char) KEY_LEFT) && (keyCode != (char) KEY_UP)
            && (keyCode != (char) KEY_RIGHT) && (keyCode != (char) KEY_DOWN)) {
          ((TextBox) sender).cancelKey();
        }
      }
    });
    
    // Create the paging widget
    pagingCurPageBox.setWidth("3em");
    pagingCurPageBox.setText("1");
    pagingCurPageBox.setTextAlignment(TextBoxBase.ALIGN_RIGHT);
    pagingNumPagesLabel.setText("1");
    DOM.setElementProperty(pagingWrapper, "className",
        "gwt-ModeledScrollTable-paging");
    HorizontalPanel pagingWrapperPanel = new HorizontalPanel();
    pagingWrapperPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    pagingWrapperPanel.add(new HTML("&nbsp;&nbsp;"));
    pagingWrapperPanel.add(pagingButtonFirst);
    pagingWrapperPanel.add(new HTML("&nbsp;&nbsp;"));
    pagingWrapperPanel.add(pagingButtonPrev);
    pagingWrapperPanel.add(new HTML("&nbsp;&nbsp;"));
    pagingWrapperPanel.add(pagingCurPageBox);
    pagingWrapperPanel.add(new HTML("&nbsp;&nbsp;of&nbsp;&nbsp;"));
    pagingWrapperPanel.add(pagingNumPagesLabel);
    pagingWrapperPanel.add(new HTML("&nbsp;&nbsp;"));
    pagingWrapperPanel.add(pagingButtonNext);
    pagingWrapperPanel.add(new HTML("&nbsp;&nbsp;"));
    pagingWrapperPanel.add(pagingButtonLast);
    
    // Apply the images
    images.modeledScrollTableFirst().applyTo(pagingButtonFirst);
    pagingButtonFirst.setTitle("First Page");
    pagingButtonFirst.addClickListener(this);
    DOM.setStyleAttribute(pagingButtonFirst.getElement(), "cursor", "pointer");
    images.modeledScrollTableLast().applyTo(pagingButtonLast);
    pagingButtonLast.setTitle("Last Page");
    pagingButtonLast.addClickListener(this);
    DOM.setStyleAttribute(pagingButtonLast.getElement(), "cursor", "pointer");
    images.modeledScrollTableNext().applyTo(pagingButtonNext);
    pagingButtonNext.setTitle("Next Page");
    pagingButtonNext.addClickListener(this);
    DOM.setStyleAttribute(pagingButtonNext.getElement(), "cursor", "pointer");
    images.modeledScrollTablePrev().applyTo(pagingButtonPrev);
    pagingButtonPrev.setTitle("Previous Page");
    pagingButtonPrev.addClickListener(this);
    DOM.setStyleAttribute(pagingButtonPrev.getElement(), "cursor", "pointer");
    
    // Adopt the paging widget into the table
    getChildren().add(pagingWrapperPanel);
    DOM.appendChild(getElement(), pagingWrapper);
    DOM.appendChild(pagingWrapper, pagingWrapperPanel.getElement());
    adopt(pagingWrapperPanel);
    
    // Set the page size
    onPageSizeChange(dataTable.getPageSize(), dataTable.getNumPages());
  }
  
  /**
   * Method to process events generated from the browser.
   * 
   * @param event the generated event
   */
  public void onBrowserEvent(Event event) {
    super.onBrowserEvent(event);

    switch (DOM.eventGetType(event)) {
      case Event.ONSCROLL:
        // Dynamically load rows
        break;
    }
  }
  
  /**
   * Fired when the user clicks on a widget.
   * 
   * @param sender the widget sending the event.
   */
  public void onClick(Widget sender) {
    ModeledGrid dataTable = (ModeledGrid) getDataTable();
    if (sender == pagingButtonFirst) {
      // Load first page
      dataTable.gotoFirstPage();
    } else if (sender == pagingButtonLast) {
      // Load last page
      dataTable.gotoLastPage();
    } else if (sender == pagingButtonNext) {
      // Load next page
      dataTable.gotoNextPage();
    } else if (sender == pagingButtonPrev) {
      // Load previous page
      dataTable.gotoPreviousPage();
    }
  }
  
  /**
   * Fired when the number of rows changes.
   * 
   * @param numRows the new number of rows
   * @param numPages the new number of pages
   */
  public void onNumRowsChanges(int numRows, int numPages) {
    pagingNumPagesLabel.setHTML(numPages + "");
  }
  
  /**
   * Fired when the current page changes.
   * 
   * @param page the new page
   */
  public void onPageChanged(int page) {
    pagingCurPageBox.setText((page + 1) + "");
  }

  /**
   * Fired when the page size changes.
   * 
   * @param pageSize the new page size
   * @param numPages the number of pages
   */
  public void onPageSizeChange(int pageSize, int numPages) {
    pagingNumPagesLabel.setHTML(numPages + "");
  }

  /**
   * Set the width of a column.
   * 
   * @param column the index of the column
   * @param width the width in pixels
   * @return the new column width
   */
  public int setColumnWidth(int column, int width) {
    int toRet = super.setColumnWidth(column, width);
    return toRet;
  }
  
  /**
   * Show or hide the paging options.
   * 
   * @param visible true to show paging options
   */
  public void setPagingOptionsVisible(boolean visible) {
    if (visible) {
      DOM.setStyleAttribute(pagingWrapper, "display", "");
    } else {
      DOM.setStyleAttribute(pagingWrapper, "display", "none");
    }
    resizeTablesVertically();
  }
  
  /**
   * Helper method that actually performs the vertical resizing.
   */
  protected void resizeTablesVerticallyNow() {
    Element dataWrapper = getDataWrapper();
    Element headerWrapper = getHeaderWrapper();
    int totalHeight = DOM.getElementPropertyInt(getElement(), "clientHeight");
    
    // Header takes what it needs
    int headerHeight = getHeaderTable().getOffsetHeight();
    DOM.setStyleAttribute(headerWrapper, "height", headerHeight + "px");
    
    // Data takes whatever is left over
    int pagingHeight = DOM.getElementPropertyInt(pagingWrapper, "offsetHeight");
    int dataHeight = totalHeight - headerHeight - pagingHeight;
    DOM.setStyleAttribute(dataWrapper, "height", dataHeight + "px");
    DOM.setStyleAttribute(dataWrapper, "overflow", "hidden");
    DOM.setStyleAttribute(dataWrapper, "overflow", "auto");
   
    // Scroll tables to be safe
    scrollTables(true);
  }
}
