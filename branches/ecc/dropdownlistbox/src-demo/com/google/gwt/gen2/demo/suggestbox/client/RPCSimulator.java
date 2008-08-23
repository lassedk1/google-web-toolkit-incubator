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

package com.google.gwt.gen2.demo.suggestbox.client;

import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.MultiWordSuggestOracleOverride;
import com.google.gwt.widgetideas.client.RPCSuggestOracle;

import java.util.Arrays;
import java.util.List;

/**
 * This class simulates answers coming over a slow RPC channel. Each query takes
 * up to two seconds to return.
 * 
 */
public class RPCSimulator extends RPCSuggestOracle {
  MultiWordSuggestOracleOverride oracle = new MultiWordSuggestOracleOverride();

  public RPCSimulator() {
    String[] suggestions = {
        "Dog", "Cat", "Pig", "Goat", "Piglet", "Dog", "Puppy"};
    List<String> l = Arrays.asList(suggestions);
    oracle.addAll(l);
  }

  public boolean isDisplayStringHTML() {
    return true;
  }

  @Override
  public void sendRequest(final Request request, final Callback callback) {
    int millisecondsToDelay = Random.nextInt(2000);
    Log.info("Sending: " + request.getQuery() + " will take "
        + millisecondsToDelay + " to recieve answer", "RPC");

    Timer t = new Timer() {
      @Override
      public void run() {
        Log.info("Recieving " + request.getQuery(), "RPC");
        oracle.requestSuggestions(request, callback);
      }
    };
    t.schedule(millisecondsToDelay);
  }
}
