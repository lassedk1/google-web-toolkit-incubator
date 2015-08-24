The simple widget is meant to help users create widgets quickly and easily.

|Demo:| [live demo](http://collectionofdemos.appspot.com/demo/com.google.gwt.gen2.demo.simplewidget.SimpleWidgetDemo/SimpleWidgetDemo.html)|
|:----|:-----------------------------------------------------------------------------------------------------------------------------------|
|Java doc: | [java doc](http://collectionofdemos.appspot.com/javadoc/com/google/gwt/gen2/commonwidget/client/SimpleWidget.html)                 |
|Sample Code:| [sample code](http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src-demo/com/google/gwt/gen2/demo/simplewidget/client/SimpleWidgetDemo.java) |
|Module:| [CommonWidget](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/src/com/google/gwt/gen2/commonwidget/CommonWidget.gwt.xml)|
|Source |[source code](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/src/com/google/gwt/gen2/commonwidget/client/SimpleWidget.java)|

# Sample usage #
## Create a clickable widget ##
```
 SimpleWidget clickable = new SimpleWidget("<h1>clickMe</h1>");
    RootPanel.get().add(clickable);
    clickable.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        report("clicked on clickable");
      }

    });
```

## Create a widget with a mouse move handler ##
```
  SimpleWidget mouseMove = new SimpleWidget("<h2>mouseMove</h2>");
    RootPanel.get().add(mouseMove);
    mouseMove.addMouseMoveHandler(new MouseMoveHandler() {
      public void onMouseMove(MouseMoveEvent event) {
        report("moved over mouseMove");
      }
    });
```

## Create a widget with a mouse down and mouse up handler ##
```
 SimpleWidget mouseDownAndUp = new SimpleWidget(
        "<h3>mouse down and up events</h3>");
    RootPanel.get().add(mouseDownAndUp);

    class MyHandler implements MouseDownHandler, MouseUpHandler {
      public void onMouseDown(MouseDownEvent event) {
        report("on mouse down");
      }

      public void onMouseUp(MouseUpEvent event) {
        report("on mouse up");
      }
    }
    MyHandler myHandler = new MyHandler();
    mouseDownAndUp.addMouseDownHandler(myHandler);
    mouseDownAndUp.addMouseUpHandler(myHandler);
  }
```