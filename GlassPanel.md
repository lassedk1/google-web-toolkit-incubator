# Introduction #

A number of use cases are supported by the glass panel widget:
  * Darken/colorize any `AbsolutePanel`. This includes any subclasses such as `RootPanel`, which leads to additional use cases:
  * Attach a glass panel to the document BODY (=`RootPanel.get()`), which effectively shades or darkens the entire web page. Useful when showing modal dialog boxes where interaction with the rest of the page is not desirable.
  * Darken/colorize any `id`-based `RootPanel` (i.e. those obtained via `RootPanel.get(id)`) with a semi-transparent layer.



# Live Demo #

Here is a [live demo](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/demo/GlassPanel/GlassPanelDemo.html) with accompanying [demo source code](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/src/com/google/gwt/demos/glasspanel/client/GlassPanelDemo.java).



# Implementation goals #
The glass panel widget should work (display) correctly under all combinations of the following conditions:
  * On every GWT supported browser/platform
  * Work with GWT 1.4.62 and GWT 1.5.x
  * In both Quirks (`BackCompat`) and Strict (`CSS1Compat`) modes
  * With or without an explicitly sized document BODY
```
  RootPanel.get().setSize("1000px", "1000px");

  // or

  RootPanel.get().setSize("", "");
```
  * With or without an explicitly sized HTML element
```
  Element htmlElement = DOM.getParent(RootPanel.getBodyElement());
  DOM.setStyleAttribute(htmlElement, "width", "2000px");
  DOM.setStyleAttribute(htmlElement, "height", "2000px");

  // or

  Element htmlElement = DOM.getParent(RootPanel.getBodyElement());
  DOM.setStyleAttribute(htmlElement, "width", "");
  DOM.setStyleAttribute(htmlElement, "height", "");
```
  * When document scrolling is enabled or disabled
```
  Window.enableScrolling(true);

  // or

  Window.enableScrolling(false);
```
  * With margins and/or borders on either the HTML and BODY elements (although negative margins are not properly supported at this time)
```
  /* To fully test CSS math:
   *  1. Use a different value for each of the four edges
   *  2. Use different values for BODY and HTML elements
   */

  HTML {
    padding: 5px 15px 25px 35px;
    margin: 10px 20px 30px 40px;
  }

  BODY {
    padding: 3px 13px 23px 33px;
    margin: 12px 22px 32px 42px;
  }
```

  * Attached to the document BODY (`RootPanel.get()`), various HTML elements (`RootPanel.get(id)`) or instances of `AbsolutePanel`
  * When the browser window is sized either smaller or larger than the contents of the document body
  * While the user is resizing the browser window


# The basics #

## Adding GlassPanel to your project ##
  1. Follow the  instructions in the [How To Use the Incubator](http://code.google.com/p/google-web-toolkit-incubator/wiki/HowToUseTheIncubator)
  1. Make sure your IDE, Java Compiler and GWT compile all have access to the compiled classes and source code:
    * If you use Eclipse, modify your launch configuration(s) to include the new jar so that the GWT compiler will be able to locate the Java **source** files
    * If you use the command line shell/compile scripts, then update the `*shell*` and `*compile*` scripts in your project directory to include the new jar file in the class path
  1. You may either inherit all the incubator widgets as documented in the above wiki, or you can inherit just the glass panel module:
```
    <!-- Inherit GlassPanel module                       -->
    <inherits name="com.google.gwt.widgetideas.GlassPanel"/>
```


## How to override the default CSS ##
```
  .gwt-GlassPanel {

    /* Override glass panel color to be `green` */
    background-color: green !important;

    /* Override glass panel to 10% opacity (= 90% transparency) */
    filter: alpha(opacity = 10) !important; /* IE */
    opacity: 0.1 !important; /* non-IE */

    /*
      NOTE: Please do NOT use either CSS 'width' or 'height' here, as
      this would defeat much of the effort that went into GlassPanel
      in order to get it to work consistently in a variety of
      circumstances. Specifically, please resist the urge to use
      'width/height: 100%' or similar constructs.
    */
  }
```


## Constructor javadoc ##
```
  /**
   * Create a glass panel widget that can be attached to an AbsolutePanel via
   * `absolutePanel.add(glassPanel, 0, 0)`.
   * 
   * @param autoHide `true` if the glass panel should be automatically hidden
   *          when the user clicks on it or presses `ESC`.
   */
  public GlassPanel(boolean autoHide);
```


## Attaching (displaying) your glass panel ##
```
  // Create a glass panel with `autoHide = false`
  GlassPanel glassPanel = new GlassPanel(false);

  // Attach (display) the glass panel
  absolutePanel.add(glassPanel, 0, 0);

  // Some time later remove (hide) the glass panel
  glassPanel.removeFromParent();
```





# A few examples #


## Covering the entire window (lightbox effect) ##
```
  // Create a glass panel with `autoHide = true`
  GlassPanel glassPanel = new GlassPanel(true);

  // Attach (display) the glass panel
  RootPanel.get().add(new GlassPanel(true), 0, 0);
```


## Covering a simple `AbsolutePanel` ##
```
  // Create a simple AbsolutePanel
  AbsolutePanel absolutePanel = new AbsolutePanel();

  // Explicitly size it to 100 x 100 pixels
  absolutePanel.setPixelSize(100, 100);

  // Add a border so we can see what's going on
  DOM.setStyleAttribute(absolutePanel.getElement(),
      "border", "1px solid black");

  // Attach the AbsolutePanel
  RootPanel.get().add(absolutePanel, 50, 50);

  // Create a glass panel with `autoHide = true`
  GlassPanel glassPanel = new GlassPanel(true);

  // Attach (display) the glass panel
  absolutePanel.add(glassPanel, 0, 0);
```


## Covering an `id`-based `RootPanel` ##
Start with this example snippet in your HTML page.
```
  <table border="1">
    <tr>
      <td>CELL 1</td>
      <td id="cell2"></td>
      <td>CELL 3</td>
    </tr>
  </table>
```

Use the following code to cover the second cell with a glass panel.
```
  // Get the RootPanel reference, e.g. the second `TD` element
  RootPanel rootPanel = RootPanel.get("cell2");

  // Add some content to our RootPanel to give it dimensions
  rootPanel.add(new Label("This is 'cell2'.");

  // Workaround GWT issue 1813
  Element element = rootPanel.getElement();
  DOM.setStyleAttribute(element, "position", "relative");
  DOM.setStyleAttribute(element, "overflow", "hidden");
  DOM.setStyleAttribute(element, "display", "block");

  // Create a glass panel with `autoHide = true`
  GlassPanel glassPanel = new GlassPanel(true);

  // Attach (display) the glass panel
  rootPanel.add(glassPanel, 0, 0);
```