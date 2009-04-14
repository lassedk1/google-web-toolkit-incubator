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

package com.google.gwt.libideas.logging.client;

import com.google.gwt.libideas.client.LibTestBase;
import com.google.gwt.libideas.logging.shared.ArrayListLogHandler;
import com.google.gwt.libideas.logging.shared.Log;

/**
 * Test for logger.
 */
public class LogTest extends LibTestBase {
  // Most testing is done using server side logging, this is just a liveness
  // check.
  public void testBasic() {
    String messageOne = "MessageOne";
    Log.info("check default system");
    ArrayListLogHandler basic = new ArrayListLogHandler();
    Log.addLogHandler(basic);
    Log.info(messageOne);
    assertEquals(messageOne, basic.getLastMessage());
  }
}
