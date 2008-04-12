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
package com.google.gwt.libideas.resources.client;

/**
 * A sound sample.
 * <p>
 * Currently supported formats are:
 * <ul>
 * <li>MP3 (16bit, 44, 22, 11 khz sampling rate)</li>
 * <li>ADPCM/WAV files</li>
 * </ul>
 * The browser must support playing SWF files of version 8 or better.
 */
@ResourceGeneratorType("com.google.gwt.libideas.resources.rg.SoundResourceGenerator")
public interface SoundResource extends ResourcePrototype {
  /**
   * Represents a sound that is currently playing.
   */
  public interface Handle {

    /**
     * Return the number of milliseconds for which the sound has been playing.
     */
    long getPosition();

    /**
     * Indicates if the sound has finished playing.
     */
    boolean isComplete();

    /**
     * Change the panning of the sound after it has been started.
     * 
     * @param pan A number from -100 (far left) to 100 (far right). Out-of-range
     *          values will be coerced into the acceptable range.
     */
    void setPan(int pan);

    /**
     * Change the volume of the sound after it has been started.
     * 
     * @param volume A number from 0 to 100. Out-of-range values will be coerced
     *          into the acceptable range.
     */
    void setVolume(int volume);

    /**
     * Stop playing the resource. Calling this method on a Handle that is not
     * currently playing will have no effect.
     */
    void stop();
  }

  /**
   * The duration in milliseconds of the SoundResource.
   */
  long getDuration();

  /**
   * Indicates that a particular SoundResource is ready for immediate playback.
   */
  boolean isReady();

  /**
   * Play the sound. If the SoundResource is not ready, this will implicitly
   * invoke {@link #prepare(ResourceCallback)} and play the sound when the
   * resource is ready.
   */
  Handle play();

  /**
   * Play the sound. If the SoundResource is not ready, this will implicitly
   * invoke {@link #prepare(ResourceCallback)} and play the sound when the
   * resource is ready.
   * 
   * @param volume A number from 0 to 100. Out-of-range values will be coerced
   *          into the acceptable range.
   * @param pan A number from -100 (far left) to 100 (far right). Out-of-range
   *          values will be coerced into the acceptable range.
   */
  Handle play(int volume, int pan);

  /**
   * Play the sound. If the SoundResource is not ready, this will implicitly
   * invoke {@link #prepare(ResourceCallback)} and play the sound when the
   * resource is ready.
   * 
   * @param callback A callback to be notified when the sound has finished
   *          playing.
   */
  Handle play(SoundResourceCallback callback);

  /**
   * Play the sound. If the SoundResource is not ready, this will implicitly
   * invoke {@link #prepare(ResourceCallback)} and play the sound when the
   * resource is ready.
   * 
   * @param callback A callback to be notified when the sound has finished
   *          playing.
   * @param volume A number from 0 to 100. Out-of-range values will be coerced
   *          into the acceptable range.
   * @param pan A number from -100 (far left) to 100 (far right). Out-of-range
   *          values will be coerced into the acceptable range.
   */
  Handle play(SoundResourceCallback callback, int volume, int pan);

  /**
   * Request that a demand-loaded sound resource is loaded and receive a
   * callback when the SoundResource can be played. This method allows
   * latency-sensitive sounds to be preloaded without having to play the sound.
   */
  void prepare(SoundResourceCallback callback);
}
