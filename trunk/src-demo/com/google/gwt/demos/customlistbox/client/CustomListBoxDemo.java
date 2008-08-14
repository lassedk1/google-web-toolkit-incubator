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

package com.google.gwt.demos.customlistbox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.event.shared.RenderEvent;
import com.google.gwt.libideas.event.shared.RenderHandler;
import com.google.gwt.libideas.event.shared.UpdateValueEvent;
import com.google.gwt.libideas.event.shared.UpdateValueHandler;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.CustomListBox;
import com.google.gwt.widgetideas.client.Decorator;
import com.google.gwt.widgetideas.client.TopLevelCssInfo;
import com.google.gwt.widgetideas.theme.client.GwtUICssInfo;
import com.google.gwt.widgetideas.theme.client.WidgetCssInfo;

/**
 * Custom List Box sample.
 * 
 * <p>
 * This sample should eventually be moved to the incubator's museum and a more
 * user-friendly one replace it. In the mean time, this is what we have to work
 * with.
 */
public class CustomListBoxDemo implements EntryPoint {
  /**
   * CSS resource for {@link WrappedCustomListBox}.
   * 
   */
  public interface Resources extends ImmutableResourceBundle {

    @Resource("com/google/gwt/widgetideas/theme/public/CustomListBox.css")
    BundledCss css();
  }

  interface BundledCss extends CustomListBox.Css, CssResource {
  }

  /**
   * A custom list box wrapped with one level of divs.
   */
  static class WrappedCustomListBox extends CustomListBox {

    private static Decorator DIV_WRAPPER;

    static {
      Element e = DOM.createDiv();
      e.setClassName("demo-decorator");
      DIV_WRAPPER = new Decorator(e, e);
    }

    public WrappedCustomListBox(String html) {
      super(html, new CustomListBox.CssAdaptor("someName"));
      setStyleName("wrapped-CustomListBox");
    }

    @Override
    protected Decorator getButtonDecorator() {
      return DIV_WRAPPER;
    }

    @Override
    protected Decorator getItemDecorator() {
      return DIV_WRAPPER;
    }
  }

  private final class Stepper extends Composite implements ClickListener {
    boolean katieWasChosen = true;
    final RenderHandler renderHandler = new RenderHandler() {
      public void onRender(RenderEvent event) {
        transientLabel.setText("Rendering... ");
      }
    };
    final UpdateValueHandler updateHandler = new UpdateValueHandler<String>() {
      public void onUpdateValue(UpdateValueEvent<String> event) {
        check(currentValue, event.getOldValue(), null);
        currentValue = event.getNewValue();
      }
    };
    boolean wrapped = false;
    private final VerticalPanel panel = new VerticalPanel();
    private final HTML lastActionLabel = new HTML();
    private final HTML transientLabel = new HTML();
    private CustomListBox<String> customBox;
    private final Button action = new Button();

    private String lastActionStored = "";

    private String currentValue;

    private int state;

    private Stepper(CustomListBox<String> customBox) {
      this.customBox = customBox;
      panel.add(action);
      panel.add(lastActionLabel);
      panel.add(transientLabel);
      initWidget(panel);
      setStyleName("gwt-Stepper");
      action.addClickListener(this);

      customBox.addRenderHandler(renderHandler);
      customBox.addValueUpdateHandler(updateHandler);

      // Do initial setup.
      state = -1;
      onClick(null);
    }

    public void onClick(Widget sender) {
      String nextAction = "finished";
      String lastAction = null;
      transientLabel.setText("");
      switch (state) {
        case -1:
          nextAction = "click to choose Abby";
          break;
        case 0:
          customBox.setCurrentValue("Abigail Crutcher");
          customBox.showItems();
          lastAction = "current value is Abby. You should see 'Rendering...' below this label";
          check("Abigail Crutcher", currentValue,
              "Current value should be Abby");
          nextAction = "click to choose Henry";
          break;
        case 1:
          customBox.setCurrentValue("Henry Crutcher");
          lastAction = "Henry should be chosen <br/>title should have been changed <br/> Popup should be closed";
          nextAction = "Manual: choose Katie";
          break;
        case 2:
          if (!"Kaitlyn Crutcher".equals(currentValue)) {
            Window.alert("Uh oh, Katie was not chosen. Instead we have:"
                + customBox.getCurrentValue());
            katieWasChosen = false;
            lastAction = "Katie was not succesfully chosen";
          } else {
            lastAction = "Current item is Katie, as expected";
          }

          nextAction = "Add a seperator and another item";
          break;
        case 3:
          customBox.addSeparator();
          customBox.addItem("Helen", "Helen Chapman", "My mother");
          customBox.showItems();
          lastAction = "Four items should now be displayed. You should have seen another 'Rendering..' message as well";
          nextAction = "Manual: test keyboard support";
          customBox.setTabIndex(0);
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
            customBox = new WrappedCustomListBox("wrapped family");
            customBox.addRenderHandler(renderHandler);
            customBox.addValueUpdateHandler(updateHandler);

            customBox.setAnimationEnabled(true);
            currentValue = null;
            lastAction = "tests should now wrap";
            fillInBox(customBox);
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
    if (WidgetCssInfo.get().shouldInject()) {
      TopLevelCssInfo.init(new TopLevelCssInfo() {

        public CustomListBox.Css forCustomListBox() {
          BundledCss css = ((Resources) GWT.create(Resources.class)).css();
          StyleInjector.injectStylesheet(css.getText());
          GwtUICssInfo.get().forButtons();
          return css;
        }
      });
    }
  }

  SimplePanel hook = new SimplePanel();

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    HorizontalPanel tests = new HorizontalPanel();
    RootPanel.get().add(tests);
    VerticalPanel p = new VerticalPanel();
    tests.add(p);
    final CustomListBox<String> customBox = new CustomListBox<String>(
        "My family");
    fillInBox(customBox);
    p.add(hook);
    p.setCellHeight(customBox, "100px");
    p.add(new Stepper(customBox));
  }

  void fillInBox(CustomListBox customBox) {
    customBox.addItem("Abby", "Abigail Crutcher", "My baby");
    customBox.addItem("Katie", "Kaitlyn Crutcher", "My first born");
    customBox.addItem("Henry", "Henry Crutcher", "My husband", new Command() {
      public void execute() {
        Window.setTitle("Love ya");
      }
    });
    hook.setWidget(customBox);
  }

}
