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
package com.google.gwt.demos.suggestbox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.libideas.logging.client.SimpleLogHandler;
import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracleOverride;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.widgetideas.client.DropDownPanel;
import com.google.gwt.widgetideas.client.LazyPanel;
import com.google.gwt.widgetideas.client.SuggestBoxOverride;
import com.google.gwt.widgetideas.client.event.HighlightEvent;
import com.google.gwt.widgetideas.client.event.HighlightHandler;

import java.util.Arrays;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SuggestBoxDemo implements EntryPoint {
  MultiWordSuggestOracleOverride girlsNames = new MultiWordSuggestOracleOverride();
  MultiWordSuggestOracleOverride girlsNamesWithDefault = new MultiWordSuggestOracleOverride();

  SuggestBoxDemo() {
    List<String> femaleNames = Arrays.asList(new String[] {
        "Jamie", "Jill", "Jackie", "Susan", "Helen", "Emily", "Karen"});

    girlsNames.addAll(femaleNames);
    girlsNamesWithDefault.addAll(femaleNames);
    girlsNamesWithDefault.setDefaultSuggestions(femaleNames);
  }

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    HorizontalPanel master = new HorizontalPanel();
    SimpleLogHandler handler = new SimpleLogHandler(false);
    Log.addLogHandler(handler);

    TabPanel panel = new TabPanel();
    LazyPanel.addTabListener(panel);
    master.add(panel);
    master.add(handler.getWidget());
    panel.add(new LazyPanel() {

      @Override
      public Widget createWidget() {
        return simpleSuggestBox();
      }
    }, "simple");
    panel.add(new LazyPanel() {

      @Override
      public Widget createWidget() {
        return rpcSuggestBox();
      }
    }, "simulated RPC");

    panel.add(new LazyPanel() {

      @Override
      public Widget createWidget() {
        return manuallyShowAndHide();
      }
    }, "show/hide");

    panel.add(new LazyPanel() {

      @Override
      public Widget createWidget() {
        return alternateDropDown();
      }

    }, "alternate drop down");

    RootPanel.get().add(master);

    panel.add(new LazyPanel() {

      @Override
      public Widget createWidget() {
        return suggestBoxWithDefault();
      }

    }, "with default");

    RootPanel.get().add(master);

    RootPanel.get().add(master);
  }

  private SuggestBoxOverride suggestBoxWithDefault() {
    SuggestBoxOverride b = new SuggestBoxOverride(girlsNamesWithDefault);
    b.showSuggestions();
    return b;
  }

  private Widget alternateDropDown() {
    class CustomDropDownPanel extends DropDownPanel<SuggestBoxOverride> {
      public CustomDropDownPanel() {
        setStyleName("CustomDropDown");
      }

      @Override
      public void position(SuggestBoxOverride widget, int offsetWidth,
          int offsetHeight) {
        int left = widget.getAbsoluteLeft() + widget.getOffsetWidth();
        int top = widget.getAbsoluteTop();
        setPopupPosition(left, top);
        show();
      }
    }

    final SuggestBoxOverride b = new SuggestBoxOverride(girlsNames,
        new TextBox(), new CustomDropDownPanel());

    b.addHighlightHandler(new HighlightHandler<Suggestion>() {
      public void onHighlight(HighlightEvent<Suggestion> event) {
        b.setText(event.getHighlightedValue().getReplacementString());
      }
    });

    return b;
  }

  private Widget rpcSuggestBox() {
    RPCSimulator simulator = new RPCSimulator();
    SuggestBoxOverride b = new SuggestBoxOverride(simulator);
    simulator.setSuggestWidget(b);
    return b;
  }

  private SuggestBoxOverride simpleSuggestBox() {

    SuggestBoxOverride b = new SuggestBoxOverride(girlsNames);
    return b;
  }

  private Widget manuallyShowAndHide() {
    FlexTable t = new FlexTable();
    t.getFlexCellFormatter().setColSpan(0, 0, 20);
    final SuggestBoxOverride box = simpleSuggestBox();
    box.setAnimationEnabled(true);
    t.setWidget(0, 0, box);

    Button showSuggestions = new Button("show suggestions",
        new ClickListener() {
          public void onClick(Widget sender) {
            box.showSuggestions();
          }
        });
    t.setWidget(1, 0, showSuggestions);

    Button hideSuggestions = new Button("hide suggestions",
        new ClickListener() {
          public void onClick(Widget sender) {
            box.hideSuggestions();
          }
        });
    t.setWidget(1, 1, hideSuggestions);
    return t;
  }
}