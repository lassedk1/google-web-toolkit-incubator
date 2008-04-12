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
package com.google.gwt.libideas.resources.client.impl;

import com.google.gwt.libideas.resources.client.ResourceCallback;
import com.google.gwt.libideas.resources.client.ResourceException;
import com.google.gwt.libideas.resources.client.SoundResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.HashMap;
import java.util.Map;

/**
 * A deferred-binding interface to encapsulate different mechanisms for playing
 * sounds.
 */
abstract class SoundPlugin {
  /**
   * Used with browsers that support the <code>embed</code> tag.
   */
  static class PluginEmbed extends SoundPlugin {
    protected Element doAttach(String url) {
      Element toReturn = DOM.createElement("embed");
      DOM.setElementAttribute(toReturn, "src", url);
      DOM.setElementAttribute(toReturn, "type", "application/x-shockwave-flash");
      DOM.setElementAttribute(toReturn, "pluginspage",
          "http://www.macromedia.com/go/getflashplayer");
      DOM.appendChild(RootPanel.getBodyElement(), toReturn);

      return toReturn;
    }
  }

  /**
   * This can be used to completely stub out sound support.
   */
  static class PluginNull extends SoundPlugin {

    public Element attach(String url) {
      return null;
    }

    public boolean isComplete(Element elt, int handle) {
      return true;
    }

    public boolean isReady(Element elt, int index) {
      return true;
    }

    public int play(Element elt, String name, int volume, int pan) {
      return 0;
    }

    public int position(Element elt, int handle) {
      return -1;
    }

    public void prepare(Element elt, SoundResource resource,
        ResourceCallback<SoundResource> callback) {
      callback.onSuccess(resource);
    }

    public void setPan(Element elt, int handle, int pan) {
    }

    public void setVolume(Element elt, int handle, int volume) {
    }

    public void stop(Element elt, int handle) {
    }

    protected Element doAttach(String url) {
      return null;
    }
  }

  /**
   * Used with browsers that support the <code>object</code> tag.
   */
  static class PluginObject extends SoundPlugin {
    public PluginObject() {
      nativeInit();
    }

    protected Element doAttach(String url) {
      String id = "SoundPluginId" + getNextSoundPluginId();
      Element elt = DOM.createElement("div");
      DOM.setInnerHTML(elt, "<OBJECT ID=\"" + id
          + "\"  CLASSID=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" "
          + "CODEBASE=\"http://fpdownload.macromedia.com/pub/shockwave/"
          + "cabs/flash/swflash.cab#version=8,0,0,0\" >"
          + "<PARAM NAME=\"movie\"  VALUE=\"" + url + "\" ></PARAM>"
          + "<PARAM NAME=\"allowScriptAccess\"  VALUE=\"always\"></PARAM>"
          + "</OBJECT>");
      DOM.appendChild(RootPanel.getBodyElement(), elt);

      return fixOuterHTML(id);
    }

    /**
     * This hack allows the plugin to be activated automatically.
     */
    private native Element fixOuterHTML(String id) /*-{$wnd[id].outerHTML = $wnd[id].outerHTML; return $wnd[id];}-*/;

    private native int getNextSoundPluginId() /*-{return $wnd.SoundPluginId++;}-*/;

    /**
     * When assigning the id for the object, make sure that the id will be
     * unique within the window, just in case there are multiple GWT modules.
     * We'll conditionally initialize the value to something that is not equal
     * to false.
     */
    private native void nativeInit() /*-{$wnd.SoundPluginId || ($wnd.SoundPluginId = 1);}-*/;
  }

  // Wait up to one minute for a sample to be ready.
  private static final int MAX_WAIT = 60000;

  private static final int READY_WAIT = 10;

  /**
   * Tracks plugins that have already been installed into the page.
   */
  private final Map<String, Element> plugins = new HashMap<String, Element>();

  /**
   * Attach a SoundBundle at a given URL to the page. If the sound bundle was
   * previously attached, this method will return the previously-created
   * Element.
   * 
   * @param url The URL of the sound bundle file
   * @return The Element associated with the sound bundle
   */
  public Element attach(String url) {
    if (plugins.containsKey(url)) {
      return plugins.get(url);
    }

    Element toReturn = doAttach(url);
    DOM.setStyleAttribute(toReturn, "position", "absolute");
    DOM.setStyleAttribute(toReturn, "top", "0");
    DOM.setStyleAttribute(toReturn, "left", "0");
    DOM.setStyleAttribute(toReturn, "width", "1px");
    DOM.setStyleAttribute(toReturn, "height", "1px");
    DOM.setStyleAttribute(toReturn, "visibility", "hidden");
    plugins.put(url, toReturn);

    return toReturn;
  }

  /**
   * Determines if a sound has finished playing.
   */
  public native boolean isComplete(Element elt, int handle) /*-{return elt.GWTisComplete(handle);}-*/;

  /**
   * Determine if a sound resource at a given index has been loaded.
   */
  public boolean isReady(Element elt, int index) {
    // The frame number is one-based and we have a leading frame with the haxe
    // code
    return pluginReady(elt) && currentFrame(elt) - 2 >= index;
  }

  /**
   * Play a named sound resource.
   */
  public native int play(Element elt, String name, int volume, int pan) /*-{return elt.GWTplay(name, volume, pan);}-*/;

  /**
   * Returns the number of seconds a sound has been playing.
   */
  public native int position(Element elt, int handle) /*-{return elt.GWTposition(handle);}-*/;

  /**
   * The SoundResource is built into the main SWF file so all we really have to
   * wait for is for the particular frame to be rendered. More complicated
   * loading cases, such as sharded container files, would be triggered here.
   */
  public void prepare(final Element elt, final SoundResource resource,
      final ResourceCallback<SoundResource> callback) {
    if (callback == null) {
      return;
    }

    // XXX can Flash reliably call back into JS?
    Timer t = new Timer() {
      private int count;

      public void run() {
        if (count++ > MAX_WAIT / READY_WAIT) {
          cancel();
          callback.onError(new ResourceException(resource,
              "Timeout waiting for resource"));
        } else if (resource.isReady()) {
          cancel();
          callback.onSuccess(resource);
        }
      }
    };
    t.scheduleRepeating(10);
  }

  /**
   * Set the panning of a currently playing resource.
   */
  public native void setPan(Element elt, int handle, int pan) /*-{elt.GWTsetPan(handle, pan);}-*/;

  /**
   * Set the volume of a currently playing resource.
   */
  public native void setVolume(Element elt, int handle, int volume) /*-{elt.GWTsetVolume(handle, volume);}-*/;

  /**
   * Stop a named sound resource.
   */
  public native void stop(Element elt, int handle) /*-{elt.GWTstop(handle);}-*/;

  /**
   * Hook point for attaching the sound bundle element to the page.
   */
  protected abstract Element doAttach(String url);

  private native int currentFrame(Element elt) /*-{return elt.GWTcurrentFrame();}-*/;

  /**
   * Determine if the plugin is ready to accept commands. We depend on the
   * <code>play</code> function being the last in the plugin's export list.
   * 
   * @param elt
   * @return
   */
  private native boolean pluginReady(Element elt) /*-{try {return elt && Boolean(elt.GWTplay)} catch (e) {return false;}}-*/;
}