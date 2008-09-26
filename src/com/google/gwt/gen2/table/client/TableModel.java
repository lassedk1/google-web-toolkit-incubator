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
package com.google.gwt.gen2.table.client;

import com.google.gwt.gen2.event.shared.AbstractEvent;
import com.google.gwt.gen2.event.shared.EventHandler;
import com.google.gwt.gen2.event.shared.HandlerManager;
import com.google.gwt.gen2.event.shared.HandlerRegistration;
import com.google.gwt.gen2.table.client.TableModelHelper.Request;
import com.google.gwt.gen2.table.client.TableModelHelper.Response;
import com.google.gwt.gen2.table.event.client.HasRowCountChangeHandlers;
import com.google.gwt.gen2.table.event.client.RowCountChangeEvent;
import com.google.gwt.gen2.table.event.client.RowCountChangeHandler;

/**
 * A class to retrieve row data to be used in a table.
 * 
 * @param <RowType> the data type of the row values
 */
public abstract class TableModel<RowType> implements HasRowCountChangeHandlers {
  /**
   * Callback for {@link TableModel}. Every {@link Request} should be associated
   * with a {@link TableModel.Callback} that should be called after a
   * {@link Response} is generated.
   * 
   * @param <RowType> the data type of the row values
   */
  public static interface Callback<RowType> {
    /**
     * Called when an error occurs and the rows cannot be loaded.
     * 
     * @param caught the exception that was thrown
     */
    void onFailure(Throwable caught);

    /**
     * Consume the data created by {@link TableModel} in response to a Request.
     * 
     * @param request the request
     * @param response the response
     */
    void onRowsReady(Request request, Response<RowType> response);
  }

  /**
   * Use the ALL_ROWS value in place of the numRows variable when requesting all
   * rows.
   */
  public static final int ALL_ROWS = -1;

  /**
   * Indicates that the number of rows is unknown, and therefore unbounded.
   */
  public static final int UNKNOWN_ROW_COUNT = -1;

  /**
   * The manager of events.
   */
  private HandlerManager handlers;

  /**
   * The total number of rows available in the model.
   */
  private int rowCount = UNKNOWN_ROW_COUNT;

  public HandlerRegistration addRowCountChangeHandler(RowCountChangeHandler handler) {
    return addHandler(RowCountChangeEvent.KEY, handler);
  }

  /**
   * Return the total number of rows. If the number is not known, return
   * {@link #UNKNOWN_ROW_COUNT}.
   * 
   * @return the total number of rows, or {@link #UNKNOWN_ROW_COUNT}
   */
  public int getRowCount() {
    return rowCount;
  }

  /**
   * Generate a {@link Response} based on a specific {@link Request}. The
   * response is passed into the {@link Callback}.
   * 
   * @param request the {@link Request} for row data
   * @param callback the {@link Callback} to use for the {@link Response}
   */
  public abstract void requestRows(Request request, Callback<RowType> callback);

  /**
   * Set the total number of rows.
   * 
   * @param rowCount the row count
   */
  public void setRowCount(int rowCount) {
    if (this.rowCount != rowCount) {
      int oldRowCount = this.rowCount;
      this.rowCount = rowCount;
      fireEvent(new RowCountChangeEvent(oldRowCount, rowCount));
    }
  }

  /**
   * Adds this handler to the widget.
   * 
   * @param key the event key
   * @param handler the handler
   */
  protected <HandlerType extends EventHandler> HandlerRegistration addHandler(
      AbstractEvent.Key<?, HandlerType> key, final HandlerType handler) {
    if (handlers == null) {
      handlers = createHandlerManager();
    }
    return handlers.addHandler(key, handler);
  }

  /**
   * Creates the {@link HandlerManager} used by this widget for event
   * management.
   * 
   */
  protected HandlerManager createHandlerManager() {
    return new HandlerManager(this);
  }

  /**
   * Fires an event.
   * 
   * @param event the event
   */
  protected void fireEvent(AbstractEvent event) {
    if (handlers != null) {
      handlers.fireEvent(event);
    }
  }

  /**
   * Returns this widget's {@link HandlerManager} used for event management.
   */
  protected final HandlerManager getHandlerManager() {
    return handlers;
  }

  /**
   * Is the event handled by one or more handlers?
   */
  protected final boolean isEventHandled(AbstractEvent.Key key) {
    return handlers == null ? false : handlers.isEventHandled(key);
  }

  /**
   * Removes the given handler from the specified event key. Normally,
   * applications should call {@link HandlerRegistration#removeHandler()}
   * instead. This method is provided primary to support the deprecated
   * listeners api.
   * 
   * @param key the event key
   * @param handler the handler
   */
  protected <T extends EventHandler> void removeHandler(AbstractEvent.Key<?, T> key, final T handler) {
    if (handlers == null) {
      handlers = new HandlerManager(this);
    }
    handlers.removeHandler(key, handler);
  }
}
