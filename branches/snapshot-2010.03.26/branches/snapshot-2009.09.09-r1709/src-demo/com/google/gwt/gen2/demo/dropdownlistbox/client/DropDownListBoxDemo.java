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

package com.google.gwt.gen2.demo.dropdownlistbox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.gen2.commonwidget.client.Decorator;
import com.google.gwt.gen2.logging.shared.Log;
import com.google.gwt.gen2.selection.client.DropDownListBox;
import com.google.gwt.gen2.widgetbase.client.Gen2CssInjector;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Custom List Box sample.
 * 
 * <p>
 * This sample should eventually be moved to the incubator's museum and a more
 * user-friendly one replace it. In the mean time, this is what we have to work
 * with.
 */
public class DropDownListBoxDemo implements EntryPoint {

  /**
   * A custom list box wrapped with one level of divs.
   */
  static class WrappedDropDownListBox extends DropDownListBox<String> {

    private static Decorator DIV_WRAPPER = Decorator.createDivDecorator("demo-decorator");

    public WrappedDropDownListBox(String html) {
      super(html);
    }

    @Override
    protected Decorator supplyItemDecorator() {
      return DIV_WRAPPER;
    }

    @Override
    protected Decorator supplyItemListDecorator() {
      return DIV_WRAPPER;
    }
  }

  private final class Stepper extends Composite implements ClickHandler {
    boolean katieWasChosen = true;

    final ValueChangeHandler<String> valueChangeHandler = new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        currentValue = event.getValue();
      }
    };
    boolean wrapped = false;
    private final VerticalPanel panel = new VerticalPanel();
    private final HTML lastActionLabel = new HTML();
    private final HTML transientLabel = new HTML();
    private DropDownListBox<String> customBox;
    private final Button action = new Button();

    private String lastActionStored = "";

    private String currentValue;

    private int state;

    private Stepper(DropDownListBox<String> dropDownBox) {
      this.customBox = dropDownBox;
      panel.add(action);
      panel.add(lastActionLabel);
      panel.add(transientLabel);
      initWidget(panel);
      setStyleName("gwt-Stepper");
      action.addClickHandler(this);

      dropDownBox.addValueChangeHandler(valueChangeHandler);

      // Do initial setup.
      state = -1;
      onClick(null);
    }

    public void onClick(ClickEvent event) {
      String nextAction = "finished";
      String lastAction = null;

      transientLabel.setText("");
      switch (state) {
        case -1:
          nextAction = "click to choose Abby";
          break;
        case 0:
          customBox.setValue("Abigail Crutcher", true);
          customBox.showItemList();
          lastAction = "List should show Abby selected";
          check("Abigail Crutcher", currentValue,
              "Current value should be Abigail Crutcher it is:" + currentValue);
          nextAction = "click to choose Henry";
          break;
        case 1:
          customBox.setValue("Henry Crutcher");
          lastAction = "Henry should be selected<br/> Popup should be closed";
          nextAction = "Manual: choose Katie";
          break;
        case 2:
          if (!"Kaitlyn Crutcher".equals(currentValue)) {
            katieWasChosen = false;
            lastAction = "<b>Katie was not succesfully chosen</b>";
          } else {
            lastAction = "Current item is Katie, as expected";
          }
          nextAction = "Add a seperator and another item";
          break;
        case 3:
          customBox.addSeparator();
          customBox.addItem("Helen", "Helen Chapman", "My mother",
              "Mom's selected");
          customBox.showItemList();
          lastAction = "Four items should now be displayed. ";
          nextAction = "Manual: test keyboard support";
          customBox.getButton().setTabIndex(0);
          break;
        case 4:
          lastAction = "Should have tabbed to item<br/> hit return to show <br/> arrows to navigate<br/> then tab away";
          if (!wrapped) {
            nextAction = "Repeat tests with a decorated suggest box";
          }
          break;
        case 5:
          if (!wrapped) {
            wrapped = true;
            customBox = new WrappedDropDownListBox("wrapped family");
            fillInBox(customBox);
            customBox.addValueChangeHandler(valueChangeHandler);

            customBox.setAnimationEnabled(true);
            currentValue = null;
            lastAction = "tests should now wrap";

            state = -1;
            onClick(null);
            return;
          }
      }

      action.setText(nextAction);
      if (lastAction == null) {
        lastActionLabel.setText(lastActionStored);
      } else {
        lastActionLabel.setHTML(lastAction);
      }

      lastActionStored = "did: " + nextAction;
      ++state;
    }

    private void check(String val1, String val2, String details) {
      if (val1 != val2) {
        if (details == null) {
          details = "values " + val1 + " and " + val2 + " should be the same";
        }
        Window.alert(details);
      }
    }
  }

  // TODO(ecc) generalize this class into a command driven stepping utility
  // class.

  static {
    // By default, all widgets use non-css resources, here we override that if
    // we are not suppressing them altogether.
    if (Gen2CssInjector.isInjectionEnabled()) {
      DropDownListBox.injectDefaultCss();
    } else {
      Log.info("Using debug css");
    }
  }

  HorizontalPanel hook = new HorizontalPanel();

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    Window.setTitle("Loading...");

    VerticalPanel p = new VerticalPanel();
    RootPanel.get().add(p);

    p.setStyleName("demo-pane");
    p.setHeight("100%");

    final DropDownListBox<String> customBox = new DropDownListBox<String>(
        "My family");
    fillInBox(customBox);
    p.add(hook);
    hook.setHeight("200px");
    p.add(new Stepper(customBox));
    Window.setTitle("DropDownListBox demo");
  }

  void fillInBox(DropDownListBox<String> box) {
    box.addItem("Abby", "Abigail Crutcher", "My baby");
    box.addItem("Katie Bear", "Kaitlyn Crutcher", "My first born");
    box.addItem("Henry", "Henry Crutcher", "My husband", null);
    hook.clear();
    hook.add(box);
  }

}
