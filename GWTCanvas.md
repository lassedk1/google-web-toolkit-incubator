**This code is now deprecated. [Canvas](http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/canvas/client/Canvas.html) support is now included in the GWT mainline.**

# Introduction #

Vector graphics have made their way to the GWT Incubator. The GWTCanvas Widget exposes an API for drawing and transforming shapes and images, as well as for defining paths to create custom shapes. GWTCanvas currently supports:

  * Rotations, scales, and translations.

  * Custom paths including arcs, lines and curves.

  * Image drawing and transformation.

  * Compositing operations and transparency.

See the live demo:
http://google-web-toolkit-incubator.googlecode.com/svn/trunk/demo/GWTCanvasDemo/GWTCanvasDemo.html

# Setup Instructions #

Just add the following line to your module.gwt.xml file:
```
  <inherits name='com.google.gwt.widgetideas.GWTCanvas'/>
```

You can specify your own stylesheet to further customize the look and feel of GWTCanvas.

# Using Images #

In order for GWTCanvas to draw images, they must first be loaded by the browser. We provide an ImageLoader class in the same package as GWTCanvas to take care of this for you. Please refer to the Sample Code below for examples on how to use ImageLoader.

# Sample Code #

Please refer to the source code of the live demo (which can be found in the incubator trunk) for more detailed examples.

**Drawing a rectangle using paths.**
```

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyEntryPoint implements EntryPoint {

  public void onModuleLoad() {
    // Make a new canvas 400x400 pixels
    GWTCanvas canvas = new GWTCanvas(400,400);
    
    canvas.setLineWidth(1);
    canvas.setStrokeStyle(Color.GREEN);
    
    canvas.beginPath();
      canvas.moveTo(1,1);
      canvas.lineTo(1,50);
      canvas.lineTo(50,50);
      canvas.lineTo(50, 1);
      canvas.closePath();
    canvas.stroke();
    
    RootPanel.get().add(canvas);
    
  }

}

```

**Drawing an image** (Scaling **THEN** translating before drawing).

Please note that you must put all drawing code involving the image in the supplied callback. Drawing order for images and paths is guaranteed only within the callback method when working with images (since they have to be loaded first, and it can not be known ahead of time how long that will take).
```
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.google.gwt.widgetideas.graphics.client.ImageHandle;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyEntryPoint implements EntryPoint {

  public void onModuleLoad() {
    // Make a new canvas 400x400 pixels
    final GWTCanvas canvas = new GWTCanvas(400,400);
    
    String[] urls = new String[] {"gwt_logo.jpg"};
    
    ImageLoader.loadImages(urls, new ImageLoader.CallBack() {

      public void onImagesLoaded(ImageHandle[] imageHandles) {
        ImageHandle img = imageHandles[0];
        
        canvas.translate(40, 40);
        canvas.scale(0.5f, 0.5f);
        canvas.drawImage(img, 0, 0);
        
      }
      
    });
   
    RootPanel.get().add(canvas);
  }

}

```

**Drawing two rectangles, with different colors, in different locations.**
```

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyEntryPoint implements EntryPoint {

  public void onModuleLoad() {
    // Make a new canvas 400x400 pixels
    GWTCanvas canvas = new GWTCanvas(400,400);
    
    canvas.setLineWidth(1);
    canvas.setStrokeStyle(Color.GREEN);
    
    canvas.saveContext();

      canvas.setLineWidth(1);
      canvas.setStrokeStyle(Color.RED);
    
      canvas.translate(100, 100);
      canvas.strokeRect(0, 0, 100, 100);
    canvas.restoreContext();
    
    canvas.strokeRect(1, 1, 80, 80);
    
    RootPanel.get().add(canvas);
    
  }

}

```


# Important Caveats #

  * Not Compatible with GWT 1.4x. Use latest GWT 1.5x build.

  * We currently have supports for gradients in the API checked into the incubator trunk. However, IE support for gradients currently is incomplete and non-functional. The framework is there to go the last mile, so in the spirit of open source projects, contributor assistance is welcome :).

  * GWTCanvas works fine in Web mode across the major browser. However, we currently only support Hosted mode on the Mac platform. This should all be remedied however with the release of [OOPHM](http://code.google.com/p/google-web-toolkit/wiki/DesignOOPHM).