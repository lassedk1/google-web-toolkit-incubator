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

package com.google.gwt.gen2.logging.handler.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.logging.shared.Level;
import com.google.gwt.gen2.logging.shared.Log;
import com.google.gwt.gen2.logging.shared.LogEvent;
import com.google.gwt.gen2.logging.shared.LogHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import java.io.Serializable;

/**
 * Handler to publish messages over RPC.
 */
public final class RemoteLogHandler implements LogHandler {

  /**
   * Provides a wrapper around the throwable thrown by the logging system.
   */
  public static class RemoteThrowable extends Throwable implements
      Serializable, IsSerializable {

    /**
     * Constructor needed for serialization.
     */
    public RemoteThrowable() {
    }

    /**
     * Creates a new remote throwable wrapped around the logged throwable.
     * 
     * @param throwable the throwable
     */
    public RemoteThrowable(Throwable throwable) {
      super(throwable);
    }
  }

  /**
   * Logging service definition. Note, this interface may have more methods
   * added to it over time.
   */
  public static interface Service extends RemoteService {
    /**
     * Publishes the given message.
     * 
     * @param message the message
     * @param level the level
     * @param category the category
     * @param remoteThrowable wraps the throwable object that was logged
     */
    void publish(String message, Level level, String category,
        RemoteThrowable remoteThrowable);
  }

  /**
   * Logging service Async for remote logging. Note, this interface may have
   * more methods added to it over time.
   */
  public static interface ServiceAsync {
    /**
     * Publishes the given message.
     * 
     * @param message the message
     * @param level the level
     * @param category the category
     * @param remoteThrowable wraps the throwable that was logged
     * @param callback the callback
     */
    void publish(String message, Level level, String category,
        RemoteThrowable remoteThrowable, AsyncCallback<Object> callback);
  }

  /**
   * Default callback for remote log handler.
   */
  class DefaultCallback implements AsyncCallback<Object> {

    public void onFailure(Throwable caught) {
      Log.removeLogHandler(RemoteLogHandler.this);
      Log.severe(
          "Remote logging failed,  remote handler is now removed as a valid handler",
          CATEGORY, caught);
    }

    public void onSuccess(Object result) {
      Log.finest("Remote logging message acknowledged", CATEGORY);
    }
  }

  private static final String CATEGORY = "gwt.logging.RemoteLogHandler";

  private ServiceAsync service;

  private AsyncCallback<Object> callback;

  /**
   * Constructor.
   */
  public RemoteLogHandler() {
    this((ServiceAsync) GWT.create(Service.class));
  }

  /**
   * Remote log handler.
   * 
   * @param service the service
   */
  public RemoteLogHandler(ServiceAsync service) {
    ServiceDefTarget target = (ServiceDefTarget) service;
    target.setServiceEntryPoint(GWT.getModuleBaseURL() + "logging");
    this.service = service;

    this.callback = new DefaultCallback();
  }

  public void onLog(LogEvent event) {
    // Don't log messages about myself.
    if (event.getCategory() == CATEGORY) {
      return;
    }
    RemoteThrowable wrappedThrown = null;
    if (event.getThrown() != null) {
      wrappedThrown = createRemoteThrowable(event.getThrown());
    }
    service.publish(event.getMessage(), event.getLevel(), event.getCategory(),
        wrappedThrown, callback);
  }

  /**
   * Sets the callback.
   * 
   * @param callback the callback
   */
  public void setCallBack(AsyncCallback<Object> callback) {
    this.callback = callback;
  }

  /**
   * Creates the remote throwable object which wraps the actual logged
   * throwable. Override this method to provide more specialized versions of the
   * remote throwable instance.
   * 
   * @param thrown thrown exception
   * @return the wrapped thrown exception
   */
  protected RemoteThrowable createRemoteThrowable(Throwable thrown) {
    return new RemoteThrowable(thrown);
  }
}
