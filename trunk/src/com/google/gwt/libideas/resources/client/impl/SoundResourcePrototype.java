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

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.ResourceException;
import com.google.gwt.libideas.resources.client.SoundResource;
import com.google.gwt.libideas.resources.client.SoundResourceCallback;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;

/**
 * A base implementation for use by generated code.
 */
public class SoundResourcePrototype implements SoundResource {
  private class HandleImpl implements Handle {
    int handle;
    boolean complete;

    public HandleImpl(final int volume, final int pan) {
      if (isReady()) {
        handle = plugin.play(flashElement, name, volume, pan);
      } else {
        prepare(new SoundResourceCallback() {

          public void onError(ResourceException e) {
            if (GWT.getUncaughtExceptionHandler() != null) {
              GWT.getUncaughtExceptionHandler().onUncaughtException(e);
            }
          }

          public void onSuccess(SoundResource resource) {
            handle = plugin.play(flashElement, name, volume, pan);
          }
        });
      }
    }

    public long getPosition() {
      return plugin.position(flashElement, handle);
    }

    public boolean isComplete() {
      return complete || (complete = plugin.isComplete(flashElement, handle));
    }

    public void setPan(int pan) {
      plugin.setPan(flashElement, handle, normalize(pan, PAN_LEFT, PAN_RIGHT));
    }

    public void setVolume(int volume) {
      plugin.setVolume(flashElement, handle, normalize(volume, MIN_VOLUME,
          MAX_VOLUME));
    }

    public void stop() {
      complete = true;
      plugin.stop(flashElement, handle);
    }
  }

  private static final int MAX_VOLUME = 100;
  private static final int MIN_VOLUME = 0;
  private static final int PAN_LEFT = -100;
  private static final int PAN_RIGHT = 100;

  /**
   * A lazily-constructed singleton instance.
   */
  private static SoundPlugin plugin;

  public static Element attach(String url) {
    SoundPlugin p = GWT.create(SoundPlugin.class);
    return p.attach(url);
  }

  private final long duration;
  private final Element flashElement;
  private final int index;
  private final String name;

  private boolean ready;

  public SoundResourcePrototype(Element flashElement, String name, int index,
      long duration) {
    this.flashElement = flashElement;
    this.name = name;
    this.index = index;
    this.duration = duration;

    if (plugin == null) {
      plugin = GWT.create(SoundPlugin.class);
    }
  }

  public long getDuration() {
    return duration;
  }

  /**
   * This is there for those who need access to the element being used to
   * control the sound. This API exists because it is useful, but isn't
   * supported.
   * 
   * @deprecated This is an implementation detail, subject to change.
   */
  public Element getElement() {
    return flashElement;
  }

  public String getName() {
    return name;
  }

  public boolean isReady() {
    return ready || (ready = plugin.isReady(flashElement, index));
  }

  public Handle play() {
    return play(100, 0);
  }

  public Handle play(int volume, int pan) {
    return new HandleImpl(normalize(volume, MIN_VOLUME, MAX_VOLUME), normalize(
        pan, PAN_LEFT, PAN_RIGHT));
  }

  public Handle play(SoundResourceCallback callback) {
    return play(callback, 100, 0);
  }

  public Handle play(final SoundResourceCallback callback, int volume, int pan) {
    final Handle toReturn = play(volume, pan);
    // XXX Can flash call JS functions?
    (new Timer() {
      public void run() {
        if (toReturn.isComplete()) {
          cancel();
          callback.onSuccess(SoundResourcePrototype.this);
        }
      }
    }).scheduleRepeating(1);
    return toReturn;
  }

  /**
   * The SoundResource is built into the main SWF file so all we really have to
   * wait for is for the particular frame to be rendered. More complicated
   * loading cases, such as sharded container files, would be triggered here.
   */
  public void prepare(final SoundResourceCallback callback) {
    plugin.prepare(flashElement, this, callback);
  }

  private int normalize(int value, int min, int max) {
    return Math.max(Math.min(max, value), min);
  }
}
