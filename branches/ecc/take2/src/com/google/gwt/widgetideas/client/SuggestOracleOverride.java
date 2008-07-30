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
package com.google.gwt.widgetideas.client;

import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;

import java.util.ArrayList;

/**
 * A {@link com.google.gwt.widgetideas.client.SuggestOracleOverride} can be used to
 * create suggestions associated with a specific query string. It is currently
 * used by {@link SuggestBoxOverride}.
 * 
 * @see SuggestBoxOverride
 */
public abstract class SuggestOracleOverride extends SuggestOracle {

  /**
   * An empty response, used for the default requestDefaultSuggestions.
   */
  private Response emptyResponse = new Response(new ArrayList());

  /**
   * Generate a {@link Response} based on a default request. The request query
   * must be null as it represents the results the oracle should return based on
   * no query string.
   * <p>
   * After the {@link Response} is created, it is passed into
   * {@link Callback#onSuggestionsReady(com.google.gwt.user.client.ui.SuggestOracleOverride.Request, com.google.gwt.user.client.ui.SuggestOracleOverride.Response)}.
   * </p>
   * 
   * @param request the request
   * @param callback the callback to use for the response
   */
  public void requestDefaultSuggestions(Request request, Callback callback) {
    assert (request.getQuery() == null);
    callback.onSuggestionsReady(request, emptyResponse);
  }

}
