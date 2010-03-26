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

/**
 *  A {@link FastTreeItem} that can have extra DOM structure around the outside.
 */
public abstract class DecoratedFastTreeItem extends FastTreeItem {
  
  private static final String STYLENAME_LEAF_DEFAULT = "gwt-DecoratedFastTreeItem gwt-DecoratedFastTreeItem-leaf";
  private static final String STYLENAME_WRAPPED = "wrapped";

  private Element wrappedElement;

  protected DecoratedFastTreeItem() {
    super();
    wrappedElement = getElement();
    setStyleName(STYLENAME_LEAF_DEFAULT);
  }
  
  /**
   * Constructor that subclasses may call to wrap a FastTreeItem in some extra
   * DOM structure, such as a DecoratorPanel.
   * 
   * @param outer The outermost element of the wrapping structure
   * @param inner The innermost element of the wrapping structure, into which
   *            the standard FastTreeElement structure will be inserted.
   */
  protected DecoratedFastTreeItem(Element outer, Element inner) {
    super(outer);
    
    assert DOM.isOrHasChild(outer, inner);
    
    wrappedElement = createLeafElement();
    
    setStyleName(wrappedElement, STYLENAME_WRAPPED);
    setStyleName(STYLENAME_LEAF_DEFAULT);
    DOM.appendChild(inner, wrappedElement);
  }
  
  @Override
  void convertElementToHaveChildren(Element children) {
    DOM.appendChild(getWrappedElement(), children);
  }

  @Override
  void convertElementToInteriorNode(Element control) {
    setStyleName(getElement(), "gwt-DecoratedFastTreeItem-leaf", false);
    DOM.appendChild(getWrappedElement(), control);
  }
  
  private Element getWrappedElement() {
    return wrappedElement;
  }
}
