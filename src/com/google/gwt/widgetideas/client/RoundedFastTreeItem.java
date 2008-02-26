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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.widgetideas.client.overrides.DOMHelper;

/**
 * Simple extension to FastTreeItem that adds styleable rounded corners like
 * in the DecoratorPanel.
 */
public class RoundedFastTreeItem extends FastTreeItem {
  private Element treeItemPart;
  private boolean rounded;

  /**
   * If rounded is true, make a FastTreeItem with divs set for rounded corners.
   */
  public RoundedFastTreeItem(boolean rounded) {
    super();
    
    this.rounded = rounded;
    
    if (rounded) {
      Element table = DOM.createTable();
      Element tbody;
      Element middle;
      
      this.treeItemPart = DOMHelper.clone(TREE_LEAF, true);
      contentElem = DOMHelper.rawFirstChild(treeItemPart);

      // Create the nine-box table
      tbody = DOM.createTBody();
      DOM.appendChild(table, tbody);

      // Add the top row
      DOM.appendChild(tbody, createTR("Top"));

      // Add the middle row
      Element middleRow = createTR("Middle");
      DOM.appendChild(tbody, middleRow);
      middle = DOM.getChild(middleRow, 1);

      // Add the bottom row
      DOM.appendChild(tbody, createTR("Bottom"));

      // Set the style name of the element
      DOM.appendChild(middle, treeItemPart);
      setElement(table);
    }
  }
  
  @Override
  Element getElementForChildElems() {
    if (!this.rounded) {
      return super.getElementForChildElems();
    }
    
    if (treeItemPart == null) {
      treeItemPart.toString();
    }
    return treeItemPart;
  }
  
  /**
   * Create a new cell with a specific style name.
   * 
   * @param trElem the row {@link Element} to add the new cell
   * @param styleName the style name
   * @return the new td {@link Element}
   */
  private Element createTD(Element trElem, String styleName) {
    Element tdElem = DOM.createTD();
    Element inner = DOM.createDiv();
    DOM.appendChild(trElem, tdElem);
    DOM.appendChild(tdElem, inner);
    setStyleName(tdElem, styleName);
    setStyleName(inner, styleName + "Inner");
    return tdElem;
  }

  /**
   * Create a new table with a specific style name. The row will contain three
   * cells (Left, Center, and Right), each prefixed with the specified style
   * name.
   * 
   * @param styleName the style name
   * @return the new tr {@link Element}
   */
  private Element createTR(String styleName) {
    Element trElem = DOM.createTR();
    createTD(trElem, styleName + "Left");
    createTD(trElem, styleName + "Center");
    createTD(trElem, styleName + "Right");
    return trElem;
  }
}
