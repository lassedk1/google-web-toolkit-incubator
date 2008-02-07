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

package com.google.gwt.demos.currencywidget.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

import com.google.gwt.widgetideas.client.CurrencyWidget;
import com.google.gwt.widgetideas.client.CurrencyWidgetWithPreview;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CurrencyWidgetDemo implements EntryPoint {
  public void onModuleLoad() {
    RootPanel rootPanel = RootPanel.get();

    final Label currencyInputWidgetLabel = new Label("Currency Input Widget:");
    rootPanel.add(currencyInputWidgetLabel, 50, 32);

    final CurrencyWidget currencyWidget = new CurrencyWidget("EUR");
    rootPanel.add(currencyWidget, 50, 56);
    
    final Button clickMeButton = new Button();
    rootPanel.add(clickMeButton, 250, 56);
    clickMeButton.setText("See what you got!");
    clickMeButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        Window.alert("You entered: " + Double.toString(currencyWidget.getAmount()));
      }
    });


    final Label currencyWidgetWithPreviewLabel = new Label("Currency Widget With Preview");
    rootPanel.add(currencyWidgetWithPreviewLabel, 50, 108);

    final CurrencyWidgetWithPreview currencyWidgetWithPreview = new CurrencyWidgetWithPreview("EUR");
    rootPanel.add(currencyWidgetWithPreview, 50, 132);
    currencyWidgetWithPreview.setSize("156px", "18px");

    final Button seeWhatYouButton = new Button();
    rootPanel.add(seeWhatYouButton, 250, 132);
    seeWhatYouButton.setSize("157px", "24px");
    seeWhatYouButton.setText("See what you got!");
    seeWhatYouButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        Window.alert("You entered: " + Double.toString(currencyWidgetWithPreview.getAmount()));
      }
    });

    
    final Label aNaiveTextLabel = new Label("A Naive Text Widget:");
    rootPanel.add(aNaiveTextLabel, 50, 180);
    final TextBox textBox = new TextBox();
    rootPanel.add(textBox, 50, 204);
  }

}
