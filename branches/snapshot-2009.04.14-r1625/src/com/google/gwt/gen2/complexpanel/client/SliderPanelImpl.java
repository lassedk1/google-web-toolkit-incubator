/*
 * Copyright 2009 Google Inc.
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
package com.google.gwt.gen2.complexpanel.client;

import com.google.gwt.gen2.complexpanel.client.CollapsiblePanel.Styles;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The panel which actually slides out and in.
 * 
 */
class SliderPanelImpl extends SimplePanel {

  private Element slider;

  // Values used to animate
  private int width;
  private int height;

  private int currentOffshift;
  private int startFrom;

  SliderPanelImpl() {
    Element e = getElement();
    e.getStyle().setProperty("zoom", "1");
    slider = DOM.createDiv();
    e.appendChild(slider);
    getElement().getStyle().setProperty("overflow", "hidden");
    slider.getStyle().setProperty("position", "absolute");
    slider.setClassName(Styles.CONTAINER);
  }

  public void completeHide() {
    setVisible(false);
    setSliderPos(0);
  }

  public void completeShow() {
    setVisible(true);
    setSliderPos(width);
  }

  public void computeSize() {
    if (!isAttached() || getWidget() == null) {
      return;
    }
    boolean visible = isVisible();
    if (!visible) {
      setVisible(true);
      doComputeSize();
      setVisible(false);

    } else {
      doComputeSize();
    }
  }

  @Override
  public int getOffsetHeight() {
    return height;
  }

  @Override
  public int getOffsetWidth() {
    return width;
  }

  @Override
  public void setHeight(String height) {
    slider.getStyle().setProperty("height", height);
    computeSize();
  }

  @Override
  public void setWidget(Widget widget) {
    super.setWidget(widget);
    computeSize();
  }

  @Override
  public void setWidth(String width) {
    slider.getStyle().setProperty("width", width);
    computeSize();
  }

  public void startHide() {
    startFrom = currentOffshift;
  }

  public void startShow() {
    setVisible(true);
    startFrom = currentOffshift;
  }

  public void updateHide(double progress) {
    int slide = (int) (startFrom - (progress * startFrom));
    setSliderPos(slide);
  }

  public void updateShow(double progress) {
    int pos = (int) ((double) (width - startFrom) * progress);
    setSliderPos(pos + startFrom);
  }

  @Override
  protected Element getContainerElement() {
    return slider;
  }

  private void doComputeSize() {
    width = slider.getOffsetWidth();
    height = slider.getOffsetHeight();
    getElement().getStyle().setProperty("width", width + "px");
    getElement().getStyle().setProperty("height", this.getOffsetHeight() + "px");
  }

  private void setSliderPos(int pos) {
    currentOffshift = pos;
    slider.getStyle().setProperty(BiDiRelativePanel.getBegin(),
        (pos - width) + "px");
  }

}