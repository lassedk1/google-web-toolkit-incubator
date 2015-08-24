# Introduction #

It is desirable to be able to incorporate audible feedback into GWT client-side applications.  To this end, the SoundResource has been added to the incubator to provide a runtime API that is easy to use and improve the deployment scenario by bundling multiple sound resources into a single download.

## Features ##
  * Concurrent playback of samples, up to platform maximum (8 channels in Flash 8, 32 channels in Flash 9)
  * Independent volume and pan controls per sample instance.
  * Bundles multiple samples into a single, strongly-named download.

# Use Case #

SoundResource is an ImmutableResourceBundle resource type and is declared in the same manner as any other resource.

```
public interface Resources extends ImmutableResourceBundle {
  /**
   * @gwt.resource beep.mp3
   */
  public SoundResource beep();
}
```

At compile time, every sound sample listed in a bundle definition will be packaged into a single SWF file.  When the bundle class is instantiated via a call to `GWT.create()` a Flash plugin instance will be created to handle that bundle's SWF file.

Because SWF files are downloaded and processed in a streaming manner, some sound resources will be available before others.  The programmer can register a SoundResourceCallback to be notified when a sound has been loaded or has finished playing.

# Implementation Details #

Flash 8 or better is required on the client machine.  We use the [Haxe](http://haxe.org/) language to build the [core logic](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/src/com/google/gwt/libideas/resources/rg/SoundKernel.hx) in the SWF file. Additional frames, one per sample, are appended at compile time with [JawaSWF2](http://www.anotherbigidea.com/javaswf/).

Sound support can be disabled at compile time by adding
```
  <set-property name="ResourceBundle.enableSound" value="false" />
```
to your `gwt.xml` file.  This will cause the core SoundPlugin to be replaced by a no-op implementation and the support code will compile away.  It may be desirable to provide an optional  `gwt.xml` file to add an unbound deferred binding property to be evaluated on the client machine.

Only MP3 files at 11, 22, and 44KhZ sampling rates are supported.  WAV files are also supported at the same sampling rates.  Encoding or converting sound samples is an explicit non-goal.

# Open questions #
  * Effects versus soundtracks.  It's technically possible to play samples on a demand/streaming basis.  How should this be specified in the bundle definition?
  * Do we provide a non-Flash fallback in browsers without flash, but support for embedding media in the DOM?  This restricts the amount of control available and eliminates the possibility of bundling sounds for only an incremental increase in the install base.