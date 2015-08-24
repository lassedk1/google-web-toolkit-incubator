# Code Examples #

The following example shows how ImageLoader can be used with GWTCanvas to draw images with transformations.

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