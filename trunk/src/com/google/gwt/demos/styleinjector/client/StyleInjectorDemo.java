package com.google.gwt.demos.styleinjector.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.DataResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.TextResource;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StyleInjectorDemo implements EntryPoint {

  public interface Resources extends ImmutableResourceBundle {
    public static final Resources INSTANCE = (Resources) GWT.create(Resources.class);

    /**
     * @gwt.resource StyleInjectorDemo.jpg
     */
    public DataResource background();

    /**
     * @gwt.resource StyleInjectorDemo.css
     */
    public TextResource css();
  }

  /**
   * This is the worlds simplest
   */
  public void onModuleLoad() {
    StyleInjector.injectStylesheet(Resources.INSTANCE.css().getText(),
        Resources.INSTANCE);
    RootPanel.get().add(
        new HTML(
            "<h1>This, a picture of my mom and daughter, it should take some time to load the first time. It should load like lighting the second time to show the value of immutable resources.</h1>"));
  }
}
