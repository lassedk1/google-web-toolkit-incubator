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
package com.google.gwt.user.client.ui;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The default {@link com.google.gwt.user.client.ui.SuggestOracleOverride}. The
 * default oracle returns potential suggestions based on breaking the query into
 * separate words and looking for matches. It also modifies the returned text to
 * show which prefix matched the query term. The matching is case insensitive.
 * All suggestions are sorted before being passed into a response.
 * <p>
 * Example Table
 * </p>
 * <p>
 * <table width = "100%" border = "1">
 * <tr>
 * <td><b> All Suggestions </b> </td>
 * <td><b>Query string</b> </td>
 * <td><b>Matching Suggestions</b></td>
 * </tr>
 * <tr>
 * <td> John Smith, Joe Brown, Jane Doe, Jane Smith, Bob Jones</td>
 * <td> Jo</td>
 * <td> John Smith, Joe Brown, Bob Jones</td>
 * </tr>
 * <tr>
 * <td> John Smith, Joe Brown, Jane Doe, Jane Smith, Bob Jones</td>
 * <td> Smith</td>
 * <td> John Smith, Jane Smith</td>
 * </tr>
 * <tr>
 * <td> Georgia, New York, California</td>
 * <td> g</td>
 * <td> Georgia</td>
 * </tr>
 * </table>
 * </p>
 */
public final class MultiWordSuggestOracleOverride extends SuggestOracleOverride {
  MultiWordSuggestOracle oracle;

  private Response defaultResponse;

  /**
   * Constructor for <code>MultiWordSuggestOracle</code>. This uses a space
   * as the whitespace character.
   * 
   * @see #MultiWordSuggestOracle(String)
   */
  public MultiWordSuggestOracleOverride() {
    this(" ");
  }

  /**
   * Constructor for <code>MultiWordSuggestOracle</code> which takes in a set
   * of whitespace chars that filter its input.
   * <p>
   * Example: If <code>".,"</code> is passed in as whitespace, then the string
   * "foo.bar" would match the queries "foo", "bar", "foo.bar", "foo...bar", and
   * "foo, bar". If the empty string is used, then all characters are used in
   * matching. For example, the query "bar" would match "bar", but not "foo
   * bar".
   * </p>
   * 
   * @param whitespaceChars the characters to treat as word separators
   */
  public MultiWordSuggestOracleOverride(String whitespaceChars) {
    oracle = new MultiWordSuggestOracle();
  }

  public void addAll(List<String> l) {
    oracle.addAll(l);
  }

  @Override
  public boolean isDisplayStringHTML() {
    return true;
  }

  @Override
  public void requestDefaultSuggestions(Request request, Callback callback) {
    if (defaultResponse != null) {
      callback.onSuggestionsReady(request, defaultResponse);
    } else {
      super.requestDefaultSuggestions(request, callback);
    }
  }

  @Override
  public void requestSuggestions(Request request, Callback callback) {
    oracle.requestSuggestions(request, callback);
  }

  /**
   * Sets the default suggestion collection.
   * 
   * @param suggestions the default suggestions
   */
  public void setDefaultSuggestions(Collection<String> suggestions) {
    if (suggestions == null) {
      defaultResponse = null;
      return;
    }

    List<MultiWordSuggestion> suggestionList = new ArrayList<MultiWordSuggestion>();

    for (String candidate : suggestions) {
      suggestionList.add(new MultiWordSuggestion(candidate, candidate));
    }
    this.defaultResponse = new Response(suggestionList);
  }
}
