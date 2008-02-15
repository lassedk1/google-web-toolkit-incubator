/*
 * Copyright 2007 Google Inc.
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
package com.google.gwt.demos.glasspanel.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.GlassPanel;

/**
 * Test EntryPoint class for GlassPanel.
 */
public class GlassPanelDemo implements EntryPoint {
  private static native String getCompatMode()
  /*-{
    return $doc.compatMode;
  }-*/;

  /**
   * TODO Replace with {@link Window#enableScrolling(boolean)} in GWT 1.5.
   */
  private static native void windowEnableScrolling(boolean enable)
  /*-{
    $doc.body.style.overflow = $doc.documentElement.style.overflow = enable ? '' : 'hidden';
  }-*/;

  private GlassPanel glassPanel3;
  private AbsolutePanel greenAbsolutePanel;
  private GlassPanel greenGlassPanel;
  private AbsolutePanel redAbsolutePanel;

  private GlassPanel redGlassPanel;

  public void onModuleLoad() {
    // Set the uncaught exception handler
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable throwable) {
        String text = "Uncaught exception: ";
        while (throwable != null) {
          StackTraceElement[] stackTraceElements = throwable.getStackTrace();
          text += new String(throwable.toString() + "\n");
          for (int i = 0; i < stackTraceElements.length; i++) {
            text += "    at " + stackTraceElements[i] + "\n";
          }
          throwable = throwable.getCause();
          if (throwable != null) {
            text += "Caused by: ";
          }
        }
        DialogBox dialogBox = new DialogBox(true);
        DOM.setStyleAttribute(dialogBox.getElement(), "backgroundColor",
            "#ABCDEF");
        System.err.print(text);
        text = text.replaceAll(" ", "&nbsp;");
        dialogBox.setHTML("<pre>" + text + "</pre>");
        dialogBox.center();
      }
    });

    // Use a deferred command so that the UncaughtExceptionHandler catches
    // exceptions thrown in onModuleLoad2()
    DeferredCommand.addCommand(new Command() {
      public void execute() {
        onModuleLoad2();
      }
    });
  }

  public void onModuleLoad2() {
    RootPanel.get().add(
        new HTML("<code>$doc.compatMode = <b>" + getCompatMode()
            + "</b></code>"));

    redAbsolutePanel = new AbsolutePanel();
    redAbsolutePanel.setPixelSize(130, 130);
    RootPanel.get().add(redAbsolutePanel, 500, 150);
    redAbsolutePanel.add(new HTML("This red GlassPanel was created<br>"
        + "with <code>autoHide</code> set to <code>false</code>."));

    greenAbsolutePanel = RootPanel.get("cell2");
    workaroundIssue1813(greenAbsolutePanel.getElement());
    greenAbsolutePanel.add(new HTML("Click or press <code>ESC</code> once<br>"
        + "to remove the gray GlassPanel.<br><br>"
        + "Click or press <code>ESC</code> a second<br>"
        + "time to remove this green GlassPanel."));

    Button buttonBodyDefault = new Button("BODY setSize(\"\", \"\")");
    buttonBodyDefault.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        removeGlassPanel();
        RootPanel.get().setSize("", "");
        resetGlassPanels();
      }
    });

    Button buttonBodyLarge = new Button("BODY setSize(\"1000px\", \"1000px\")");
    buttonBodyLarge.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        removeGlassPanel();
        RootPanel.get().setSize("1000px", "1000px");
        resetGlassPanels();
      }
    });

    Button buttonHTMLDefault = new Button("HTML setSize(\"\", \"\")");
    buttonHTMLDefault.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        removeGlassPanel();
        Element htmlElement = DOM.getParent(RootPanel.getBodyElement());
        DOM.setStyleAttribute(htmlElement, "width", "");
        DOM.setStyleAttribute(htmlElement, "height", "");
        resetGlassPanels();
      }
    });

    Button buttonHTMLLarge = new Button("HTML setSize(\"2000px\", \"2000px\")");
    buttonHTMLLarge.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        removeGlassPanel();
        Element htmlElement = DOM.getParent(RootPanel.getBodyElement());
        DOM.setStyleAttribute(htmlElement, "width", "2000px");
        DOM.setStyleAttribute(htmlElement, "height", "2000px");
        resetGlassPanels();
      }
    });

    Button buttonScrollingEnable = new Button("Window.enableScrolling(true)");
    buttonScrollingEnable.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        removeGlassPanel();
        windowEnableScrolling(true);
        resetGlassPanels();
      }
    });

    Button buttonScrollingDisable = new Button("Window.enableScrolling(false)");
    buttonScrollingDisable.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        removeGlassPanel();
        windowEnableScrolling(false);
        resetGlassPanels();
      }
    });

    FlexTable buttonTable = new FlexTable();
    buttonTable.addStyleName("buttons");
    RootPanel.get().add(buttonTable, 0, 0);
    buttonTable.setWidget(0, 0, buttonBodyDefault);
    buttonTable.setWidget(0, 1, buttonBodyLarge);

    buttonTable.setWidget(1, 0, buttonHTMLDefault);
    buttonTable.setWidget(1, 1, buttonHTMLLarge);

    buttonTable.setWidget(2, 0, buttonScrollingEnable);
    buttonTable.setWidget(2, 1, buttonScrollingDisable);

    resetGlassPanels();
  }

  private void removeGlassPanel() {
    if (redGlassPanel != null) {
      redGlassPanel.removeFromParent();
    }
    if (greenGlassPanel != null) {
      greenGlassPanel.removeFromParent();
    }
    if (glassPanel3 != null) {
      glassPanel3.removeFromParent();
    }
  }

  private void resetGlassPanels() {
    removeGlassPanel();

    redGlassPanel = new GlassPanel(false);
    redGlassPanel.addStyleName("red");
    redAbsolutePanel.add(redGlassPanel, 0, 0);

    greenGlassPanel = new GlassPanel(true);
    greenGlassPanel.addStyleName("green");
    greenAbsolutePanel.add(greenGlassPanel, 0, 0);

    glassPanel3 = new GlassPanel(true);
    RootPanel.get().add(glassPanel3, 0, 0);
  }

  /**
   * TODO Remove once issue 1813 has been resolved.
   */
  private void workaroundIssue1813(Element element) {
    DOM.setStyleAttribute(element, "position", "relative");
    DOM.setStyleAttribute(element, "overflow", "hidden");

    // Needed for TD element in non-IE browsers
    DOM.setStyleAttribute(element, "display", "block");
  }
}
