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

package com.google.gwt.libideas.client;

/**
 * Allows authors to support Right-To-Left languages such as Hebrew and Arabic.
 * This is a temporary class to allow support until the official GWT
 * internationalization <code>LocalUtil</code> class is implemented. Therefore, there are no Wiki
 * Pages, examples, or other documentation associated with this class.
 * 
 */
public class BiDiUtil {
  private static RTLOracle ORACLE;

  public static void setRTLOracle(RTLOracle oracle) {
    ORACLE = oracle;
  }

  /**
   * Supplies the BiDi information to the system.
   */
  public abstract static class RTLOracle {
    public abstract boolean isRightToLeft();
  };

  /**
   * Is the language left to right? If no Oracle is set,defaults to false.
   * 
   * @return is the current language setting right to left?
   */
  public static boolean isRightToLeft() {
    if (ORACLE == null) {
      return false;
    } else {
      return ORACLE.isRightToLeft();
    }
  }

}
