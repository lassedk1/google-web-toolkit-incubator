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
package com.google.gwt.widgetideas.table.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link ScrollTable} that acts as a view for an underlying
 * {@link TableModel}.
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class="css">
 * <li> .gwt-ScrollTable .pagingOptions { paging options panel } </li>
 * <li> .gwt-ScrollTable .pagingOptions .errorLabel { error text in paging
 * options }</li>
 * </ul>
 */
public class PagingScrollTable extends ScrollTable {
  /**
   * An {@link com.google.gwt.user.client.ui.ImageBundle} that provides images
   * for {@link PagingScrollTable}.
   */
  public static interface PagingScrollTableImages extends ScrollTableImages {
    /**
     * An image used to navigate to the first page.
     * 
     * @return a prototype of this image
     */
    AbstractImagePrototype scrollTableViewFirstPage();

    /**
     * An image used to navigate to the last page.
     * 
     * @return a prototype of this image
     */
    AbstractImagePrototype scrollTableViewLastPage();

    /**
     * An image used to navigate to the next page.
     * 
     * @return a prototype of this image
     */
    AbstractImagePrototype scrollTableViewNextPage();

    /**
     * An image used to navigate to the previous page.
     * 
     * @return a prototype of this image
     */
    AbstractImagePrototype scrollTableViewPrevPage();
  }

  /**
   * The label used to display errors.
   */
  private HTML errorLabel = new HTML();

  /**
   * Listens for click events from the images that allow navigation.
   */
  private ClickListener imageClickListener = new ClickListener() {
    public void onClick(Widget sender) {
      // Determine which page to load
      final HasRowPaging dataTable = (HasRowPaging) getDataTable();
      int numPages = dataTable.getNumPages();
      int loadPage = -1;
      if (sender == pagingButtonFirst) {
        loadPage = 0;
      } else if (sender == pagingButtonLast) {
        loadPage = numPages - 1;
      } else if (sender == pagingButtonNext) {
        loadPage = getPagingBoxValue() + 1;
      } else if (sender == pagingButtonPrev) {
        loadPage = getPagingBoxValue() - 1;
      }
      
      // Load the page
      int curPage = dataTable.getCurrentPage();
      if ((loadPage >= 0) && (loadPage < numPages) && (loadPage != curPage)) {
        // Update the paging bar
        pagingCurPageBox.setText((loadPage + 1) + "");
        loadingImage.setVisible(true);
        errorLabel.setHTML("");

        // Use a DeferredCommand to let the UI update
        DeferredCommand.addCommand(new Command() {
          public void execute() {
            dataTable.gotoPage(getPagingBoxValue(), false);
          }
        });
      }
    }
  };

  /**
   * The loading image.
   */
  private Image loadingImage = new Image("scrollTableLoading.gif");

  /**
   * A listener that listens for page events from the {@link HasRowPaging}.
   */
  private RowPagingListener rowPagingListener = new RowPagingListener() {
    public void onNumPagesChanges(int numPages) {
      if (numPages < 0) {
        pagingNumPagesLabel.setHTML("");
        pagingButtonLast.setVisible(false);
      } else {
        pagingNumPagesLabel.setHTML("of&nbsp;&nbsp;" + numPages);
        pagingNumPagesLabel.setVisible(true);
        pagingButtonLast.setVisible(true);
      }
    }

    public void onPageChanged(int page) {
    }

    public void onPageLoaded(int page) {
      pagingCurPageBox.setText((page + 1) + "");
      loadingImage.setVisible(false);
      errorLabel.setHTML("");
      redraw();
    }

    public void onPagingFailure(Throwable caught) {
      loadingImage.setVisible(false);
      errorLabel.setHTML(caught.getMessage());
    }
  };

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
  private HTML pagingNumPagesLabel = new HTML();

  /**
   * The wrapper that contains the buttons and text used for paging.
   */
  private Element pagingWrapper = DOM.createDiv();

  /**
   * Constructor.
   * 
   * @param dataTable the {@link HasRowPaging} used in as the data table
   * @param headerTable the header table
   */
  public PagingScrollTable(HasRowPaging dataTable,
      HasFixedColumnWidth headerTable) {
    this(dataTable, headerTable,
        (PagingScrollTableImages) GWT.create(PagingScrollTableImages.class));
  }

  /**
   * Constructor.
   * 
   * @param dataTable the {@link HasRowPaging} used in as the data table
   * @param headerTable the header table
   * @param images the images to use in the table
   */
  public PagingScrollTable(HasRowPaging dataTable,
      HasFixedColumnWidth headerTable, PagingScrollTableImages images) {
    super(dataTable, headerTable, images);
    dataTable.addRowPagingListener(rowPagingListener);

    // Disallow non-numeric pages
    pagingCurPageBox.addKeyboardListener(new KeyboardListenerAdapter() {
      public void onKeyPress(Widget sender, char keyCode, int modifiers) {
        if (keyCode == (char) KEY_ENTER) {
          HasRowPaging dataTable = (HasRowPaging) getDataTable();
          dataTable.gotoPage(getPagingBoxValue(), false);
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
    errorLabel.setStylePrimaryName("errorLabel");
    loadingImage.setVisible(false);
    pagingCurPageBox.setWidth("3em");
    pagingCurPageBox.setText("1");
    pagingCurPageBox.setTextAlignment(TextBoxBase.ALIGN_RIGHT);
    setStylePrimaryName(pagingWrapper, "pagingOptions");
    HorizontalPanel pagingWrapperPanel = new HorizontalPanel();
    pagingWrapperPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    pagingWrapperPanel.add(new HTML("&nbsp;&nbsp;"));
    pagingWrapperPanel.add(pagingButtonFirst);
    pagingWrapperPanel.add(new HTML("&nbsp;&nbsp;"));
    pagingWrapperPanel.add(pagingButtonPrev);
    pagingWrapperPanel.add(new HTML("&nbsp;&nbsp;"));
    pagingWrapperPanel.add(pagingCurPageBox);
    pagingWrapperPanel.add(new HTML("&nbsp;&nbsp;"));
    pagingWrapperPanel.add(pagingNumPagesLabel);
    pagingWrapperPanel.add(new HTML("&nbsp;&nbsp;"));
    pagingWrapperPanel.add(pagingButtonNext);
    pagingWrapperPanel.add(new HTML("&nbsp;&nbsp;"));
    pagingWrapperPanel.add(pagingButtonLast);
    pagingWrapperPanel.add(new HTML("&nbsp;&nbsp;"));
    pagingWrapperPanel.add(loadingImage);
    pagingWrapperPanel.add(errorLabel);

    // Apply the images
    images.scrollTableViewFirstPage().applyTo(pagingButtonFirst);
    pagingButtonFirst.setTitle("First Page");
    pagingButtonFirst.addClickListener(imageClickListener);
    DOM.setStyleAttribute(pagingButtonFirst.getElement(), "cursor", "pointer");
    images.scrollTableViewLastPage().applyTo(pagingButtonLast);
    pagingButtonLast.setTitle("Last Page");
    pagingButtonLast.addClickListener(imageClickListener);
    DOM.setStyleAttribute(pagingButtonLast.getElement(), "cursor", "pointer");
    images.scrollTableViewNextPage().applyTo(pagingButtonNext);
    pagingButtonNext.setTitle("Next Page");
    pagingButtonNext.addClickListener(imageClickListener);
    DOM.setStyleAttribute(pagingButtonNext.getElement(), "cursor", "pointer");
    images.scrollTableViewPrevPage().applyTo(pagingButtonPrev);
    pagingButtonPrev.setTitle("Previous Page");
    pagingButtonPrev.addClickListener(imageClickListener);
    DOM.setStyleAttribute(pagingButtonPrev.getElement(), "cursor", "pointer");

    // Adopt the paging widget into the table
    getChildren().add(pagingWrapperPanel);
    DOM.appendChild(getElement(), pagingWrapper);
    DOM.appendChild(pagingWrapper, pagingWrapperPanel.getElement());
    adopt(pagingWrapperPanel);

    // Set the page size
    rowPagingListener.onNumPagesChanges(dataTable.getNumPages());
  }

  /**
   * @return the number of rows per page
   */
  public int getPageSize() {
    return ((HasRowPaging) getDataTable()).getPageSize();
  }

  /**
   * Set the number of rows per page.
   * 
   * By default, the page size is zero, which indicates that all rows should be
   * shown on the page.
   * 
   * @param pageSize the number of rows per page
   */
  public void setPageSize(int pageSize) {
    ((HasRowPaging) getDataTable()).setPageSize(pageSize);
  }

  /**
   * Show or hide the paging options.
   * 
   * @param visible true to show paging options, false to hide them
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
   * @see ScrollTable
   */
  protected void resizeTablesVerticallyNow() {
    super.resizeTablesVerticallyNow();

    Element dataWrapper = getDataWrapper();
    if (isScrollingEnabled()) {
      // Take some space from the data table to make room for the paging div
      int pagingHeight = DOM.getElementPropertyInt(pagingWrapper,
          "offsetHeight");
      int dataHeight = DOM.getElementPropertyInt(dataWrapper, "offsetHeight")
          - pagingHeight;
      DOM.setStyleAttribute(dataWrapper, "height", dataHeight + "px");
      DOM.setStyleAttribute(dataWrapper, "overflow", "hidden");
      DOM.setStyleAttribute(dataWrapper, "overflow", "auto");
    } else {
      DOM.setStyleAttribute(dataWrapper, "overflow", "hidden");
      DOM.setStyleAttribute(dataWrapper, "overflow", "");
    }

    // Scroll tables to be safe
    scrollTables(true);
  }

  /**
   * Get the value of in the page box. If the value is invalid, it will be set
   * to 1 automatically.
   * 
   * @return the value in the page box
   */
  private int getPagingBoxValue() {
    int page = 0;
    try {
      page = Integer.parseInt(pagingCurPageBox.getText()) - 1;
    } catch (NumberFormatException e) {
      // This will catch an empty box
      pagingCurPageBox.setText("1");
    }

    // Replace values less than 1
    if (page < 1) {
      pagingCurPageBox.setText("1");
      page = 0;
    }

    // Return the 0 based page, not the 1 based visible value
    return page;
  }
}
