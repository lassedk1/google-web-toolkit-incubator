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

/**
 * Currency Text Widget With Preview
 * 
 * This class further extends CurrencyWidget with the functionality of preview.
 * User will be able to see the formatted string with marked visual clue about
 * the value they are inputting.
 * 
 * Usage: RootPanel rootPanel = RootPanel.get();
 * 
 * CurrencyWidgetWithPreview currencyWidget = new CurrencyWidget();
 * rootPanel.add(currencyWidget, 180, 32);
 * 
 * clickMeButton = new Button(); rootPanel.add(clickMeButton, 394, 32);
 * clickMeButton.setText("See what you got!");
 * clickMeButton.addClickListener(new ClickListener() { public void
 * onClick(Widget sender) { Window.alert("You entered: " +
 * Double.toString(currencyWidget.getAmount())); } });
 * 
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.TextResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.PopupPanelOverride;
import com.google.gwt.user.client.ui.Widget;

/**
 * TODO JavaDoc.
 * 
 */
public class CurrencyWidgetWithPreview extends CurrencyWidget {
  interface Resources extends ImmutableResourceBundle {
    public static final Resources INSTANCE = GWT.create(Resources.class);

    @Resource("CurrencyWidgetPreview.css")
    public TextResource css();
  }

  // private static final String STYLENAME_DEFAULT = "gwt-FastTree";
  private static final String decimalSeparator = !GWT.isClient() ? null
      : CurrencyWidget.numberConstants.decimalSeparator();
  CurrencyWidgetWithPreview widget = this;
  private final PopupPanelOverride previewPanel = new PopupPanelOverride();
  private final HTML previewField = new HTML();
  private final Timer previewTimer = new Timer() {
    public void run() {
      String str = reformatContent();
      int pos = str.indexOf(decimalSeparator);
      previewField.setHTML("<div class='previewPanel'>"
          + "<span class='integerPart'>" + str.substring(0, pos + 1)
          + "</span>" + "<span class='decimalPart'>" + str.substring(pos + 1)
          + "</span></div>");
    }
  };

  /**
   * Constructs a CurrencyWidgetWithPreview object.
   */
  public CurrencyWidgetWithPreview() {
    this(numberConstants.defCurrencyCode());
  }

  /**
   * Constructs a CurrencyWidgetWithPreview object with specified currency.
   * 
   * @param currencyCode International currency code (ISO 4217).
   */
  public CurrencyWidgetWithPreview(String currencyCode) {
    super(currencyCode);
    StyleInjector.injectStylesheet(Resources.INSTANCE.css().getText());
    
    previewPanel.setWidget(previewField);
    amountBox.addFocusListener(new FocusListener() {
      public void onFocus(Widget sender) {
        if (!valueInitiated) {
          amountBox.setText("");
          valueInitiated = true;
        }
        previewPanel.setPopupPosition(amountBox.getAbsoluteLeft(),
            amountBox.getAbsoluteTop() + amountBox.getOffsetHeight() + 2);
        previewPanel.show();
      }

      public void onLostFocus(Widget sender) {
        amountBox.setText(widget.reformatContent());
        previewPanel.hide();
      }
    });

    amountBox.addKeyboardListener(new KeyboardListenerAdapter() {
      public void onKeyDown(Widget sender, char keyCode, int modifiers) {
        if (keyCode == (char) KEY_ENTER) {
          previewPanel.hide();
          amountBox.setText(widget.reformatContent());
        } else if (keyCode != (char) KEY_TAB) {
          previewPanel.show();
        }
        previewTimer.schedule(100);
      }
    });
  }
}
