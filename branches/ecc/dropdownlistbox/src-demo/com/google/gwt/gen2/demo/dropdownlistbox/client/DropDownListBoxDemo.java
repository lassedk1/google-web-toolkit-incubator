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
import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.commonwidget.client.ToggleControl;
import com.google.gwt.gen2.event.shared.ChoiceEvent;
import com.google.gwt.gen2.event.shared.ChoiceHandler;
import com.google.gwt.gen2.event.shared.RenderEvent;
import com.google.gwt.gen2.event.shared.RenderHandler;
import com.google.gwt.gen2.selection.client.DropDownListBox;
import com.google.gwt.gen2.widget.client.Decorator;
import com.google.gwt.gen2.widget.client.WidgetCssInfo;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.user.client.Command;
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
   * TODO(ECC) for some reason we can't just inherit DropDownListBox.css class
   * name annotations and must override them again below. Will coordinate with
   * BobV to get this fixed...
   * 
   */
  public static interface BundledCss extends CssResource, DropDownListBox.Css {
    @ClassName("gwt-DropDownListBox-chosenAndHighlightedItem")
    String chosenAndHighlightedItem();

    @ClassName("gwt-DropDownListBox-chosenItem")
    String chosenItem();

    @ClassName("gwt-DropDownListBox-dropdown")
    String dropdown();

    @ClassName("gwt-DropDownListBox-highlightedItem")
    String highlightedItem();

    @ClassName("gwt-DropDownListBox-innerSep")
    String innerSep();

    @ClassName("gwt-DropDownListBox-item")
    String item();

    @ClassName("gwt-DropDownListBox-outerSep")
    String outerSep();

    @ClassName("gwt-DropDownListBox")
    String styleName();
  }

  /**
   * CSS resource for {@link WrappedDropDownListBox}.
   * 
   */
  public static interface Resources extends ImmutableResourceBundle {
    @Resource("com/google/gwt/gen2/widget/public/DropDownListBox.css")
    
    BundledCss dropDownListBoxCss();
  }

  /**
   * A custom list box wrapped with one level of divs.
   */
  static class WrappedDropDownListBox extends DropDownListBox {

    private static Decorator DIV_WRAPPER = Decorator.createDivDecorator("demo-decorator");

    public WrappedDropDownListBox(String html) {
      super(html, new ToggleControl.Default("wrapped-Button"),
          new DropDownListBox.CssAdaptor(
              "some name here, it is about to be overriden anyway"));
      setStyleName("wrapped-DropDownListBox");
    }

    @Override
    protected Decorator supplyDropDownDecorator() {
      return DIV_WRAPPER;
    }

    @Override
    protected Decorator supplyItemDecorator() {
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
    final ChoiceHandler updateHandler = new ChoiceHandler<String>() {
      public void onUpdateValue(ChoiceEvent<String> event) {
        check(currentValue, event.getOldValue(), null);
        currentValue = event.getNewValue();
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

    private Stepper(DropDownListBox<String> customBox) {
      this.customBox = customBox;
      panel.add(action);
      panel.add(lastActionLabel);
      panel.add(transientLabel);
      initWidget(panel);
      setStyleName("gwt-Stepper");
      action.addClickListener(this);

      customBox.addRenderHandler(renderHandler);
      customBox.addChoiceHandler(updateHandler);

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
          customBox.chose("Abigail Crutcher");
          customBox.showItems();
          lastAction = "current value is Abby. You should see 'Rendering...' below this label";
          check("Abigail Crutcher", currentValue,
              "Current value should be Abby");
          nextAction = "click to choose Henry";
          break;
        case 1:
          customBox.chose("Henry Crutcher");
          lastAction = "Henry should be chosen <br/>title should have been changed <br/> Popup should be closed";
          nextAction = "Manual: choose Katie";
          break;
        case 2:
          if (!"Kaitlyn Crutcher".equals(currentValue)) {
            Window.alert("Uh oh, Katie was not chosen. Instead we have:"
                + customBox.getChosenValue());
            katieWasChosen = false;
            lastAction = "Katie was not succesfully chosen";
          } else {
            lastAction = "Current item is Katie, as expected";
          }

          nextAction = "Add a seperator and another item";
          break;
        case 3:
          customBox.addSeparator();
          customBox.addItem("Helen", "Helen Chapman", "My mother",
              "Mom's selected");
          customBox.showItems();
          lastAction = "Four items should now be displayed. You should have seen another 'Rendering..' message as well";
          nextAction = "Manual: test keyboard support";
          customBox.getToggle().getFocusable().setTabIndex(0);
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
            customBox.addRenderHandler(renderHandler);
            customBox.addChoiceHandler(updateHandler);

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
    if (WidgetCssInfo.isInjectionEnabled()) {
      BundledCss css = ((Resources) GWT.create(Resources.class)).dropDownListBoxCss();
      StyleInjector.injectStylesheet(css.getText());
      DropDownListBox.DEFAULT_CSS = css;
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
    final DropDownListBox<String> customBox = new DropDownListBox<String>(
        "My family");
    fillInBox(customBox);
    p.add(hook);
    p.setCellHeight(customBox, "100px");
    p.add(new Stepper(customBox));
  }

  void fillInBox(DropDownListBox customBox) {
    customBox.addItem("Abby", "Abigail Crutcher", "My baby");
    customBox.addItem("Katie", "Kaitlyn Crutcher", "My first born");
    customBox.addItem("Henry", "Henry Crutcher", "My husband", null,
        new Command() {
          public void execute() {
            Window.setTitle("Love ya");
          }
        });
    hook.setWidget(customBox);
  }

}
