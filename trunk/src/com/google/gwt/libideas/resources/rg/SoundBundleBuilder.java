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
package com.google.gwt.libideas.resources.rg;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.libideas.resources.ext.ResourceContext;
import com.google.gwt.libideas.resources.ext.ResourceGeneratorUtil;

import com.anotherbigidea.flash.SWFConstants;
import com.anotherbigidea.flash.interfaces.SWFActionBlock;
import com.anotherbigidea.flash.interfaces.SWFActions;
import com.anotherbigidea.flash.interfaces.SWFTags;
import com.anotherbigidea.flash.readers.SWFReader;
import com.anotherbigidea.flash.readers.TagParser;
import com.anotherbigidea.flash.sound.ADPCMHelper;
import com.anotherbigidea.flash.sound.MP3Helper;
import com.anotherbigidea.flash.sound.SoundDefinition;
import com.anotherbigidea.flash.writers.SWFWriter;
import com.anotherbigidea.flash.writers.TagWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Assimilates sound resources into a flash application.
 */
class SoundBundleBuilder {
  /**
   * Appends sound and export tags for each sample.
   */
  private class Appender extends TagWriter {

    public Appender(SWFTags out) {
      super(out);
    }

    @Override
    public void header(int version, long length, int twipsWidth,
        int twipsHeight, int frameRate, int frameCount) throws IOException {
      // -1 forces the frame count to be recomputed
      super.header(version, length, twipsWidth, twipsHeight, frameRate, -1);
    }

    @Override
    public void tagEnd() throws IOException {
      // Create a sorted array of names by comparing the map entries with the
      // ordered list
      String[] names = new String[sounds.size()];
      for (Map.Entry<String, SoundDefinition> entry : sounds.entrySet()) {
        int i = orderedSounds.indexOf(entry.getValue());
        names[i] = entry.getKey();
      }

      // Place each sound in its own frame.
      for (SoundDefinition def : orderedSounds) {
        int i = orderedSounds.indexOf(def);

        tagDefineSound(i + 100, def.format, def.frequency, def.is16bit,
            def.isStereo, def.sampleCount, def.soundData);

        tagExport(new String[] {names[i]}, new int[] {i + 100});
        tagShowFrame();
      }

      // Prevent the movie from looping
      SWFActions actions = tagDoAction();
      SWFActionBlock block = actions.start(0);
      block.stop();
      block.end();
      actions.done();
      tagShowFrame();

      // End the SWF file
      super.tagEnd();
    }
  }

  private final Map<String, SoundDefinition> sounds = new IdentityHashMap<String, SoundDefinition>();

  private final List<SoundDefinition> orderedSounds = new ArrayList<SoundDefinition>();

  public void assimilate(TreeLogger logger, String name, URL resource)
      throws UnableToCompleteException {
    try {
      SoundDefinition def;

      if (ResourceGeneratorUtil.baseName(resource).endsWith("mp3")) {
        // MP3's are special-cased
        def = MP3Helper.getSoundDefinition(resource.openStream());

      } else {
        // Otherwise, fall back to a helper based on javax.sound
        ADPCMHelper helper = new ADPCMHelper(resource.openStream(), 30);
        def = helper.getSoundDefinition();
      }

      sounds.put(name, def);
    } catch (IOException e) {
      logger.log(TreeLogger.ERROR, "Unable to open resource", e);
      throw new UnableToCompleteException();
    } catch (UnsupportedAudioFileException e) {
      logger.log(TreeLogger.ERROR, "Unsupported audio format", e);
      throw new UnableToCompleteException();
    }
  }

  /**
   * Return the duration in seconds of the named sound.
   */
  public long getDuration(String name) {
    SoundDefinition def = sounds.get(name);
    switch (def.frequency) {
      case SWFConstants.SOUND_FREQ_44KHZ:
        return 1000L * def.sampleCount / 44000;
      case SWFConstants.SOUND_FREQ_22KHZ:
        return 1000L * def.sampleCount / 22000;
      case SWFConstants.SOUND_FREQ_11KHZ:
        return 1000L * def.sampleCount / 11000;
      case SWFConstants.SOUND_FREQ_5_5KHZ:
        return 1000L * def.sampleCount / 5500;
      default:
        return -1;
    }
  }

  /**
   * Returns the order in which the sounds will have been encoded into the sound
   * bundle. This method is valid only after
   * {@link #writeBundle(TreeLogger, ResourceContext)} has been called.
   */
  public int getIndex(String name) {
    return orderedSounds.indexOf(sounds.get(name));
  }

  /**
   * Finalize the sound bundle and write it into the output.
   * 
   * @return A Java expression that will evaluate to the location of the sound
   *         bundle at runtime.
   */
  public String writeBundle(TreeLogger logger, ResourceContext context)
      throws UnableToCompleteException {

    // Sort the sounds from shortest to longest.
    orderedSounds.addAll(sounds.values());
    Collections.sort(orderedSounds, new Comparator<SoundDefinition>() {
      public int compare(SoundDefinition o1, SoundDefinition o2) {
        return o1.soundData.length - o2.soundData.length;
      }
    });

    // Get a handle to the SoundKernel app
    InputStream in = getClass().getClassLoader().getResourceAsStream(
        "com/google/gwt/libideas/resources/rg/SoundKernel.swf");
    if (in == null) {
      logger.log(TreeLogger.ERROR, "Could not load SoundKernel.swf", null);
      throw new UnableToCompleteException();
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    // Set up the processing chain
    SWFWriter writer = new SWFWriter(out);
    Appender ins = new Appender(writer);
    TagParser parser = new TagParser(ins);
    SWFReader reader = new SWFReader(parser, in);

    // Try running the chain
    try {
      reader.readFile();
    } catch (IOException e) {
    }

    // Write the completed sound bundle into the output
    return context.addToOutput(
        context.getResourceBundleType().getQualifiedSourceName()
            + ".SoundBundle.swf", "application/x-shockwave-flash",
        out.toByteArray(), true);
  }
}
