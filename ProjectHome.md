# Google Web Toolkit Incubator #

<font color='red'>
The Google Web Toolkit Incubator project is deprecated and is no longer supported by the GWT team.<br>
<br>
Most of the important widgets and APIs made their way into GWT trunk a long time ago.  The rest may be included at a later date.<br>
<br>
<b>Status of incubator APIs:</b>
<table><thead><th> <b>API</b> </th><th> <b>Equivalent in GWT trunk</b> </th><th> <b>Comments</b> </th></thead><tbody>
<tr><td> Logging    </td><td> java.util.logging              </td><td> <a href='http://code.google.com/webtoolkit/doc/latest/DevGuideLogging.html'>Logging Dev Guide</a> </td></tr>
<tr><td> StyleInjector </td><td> <a href='http://code.google.com/webtoolkit/doc/latest/DevGuideClientBundle.html'>ClientBundle</a> and <a href='http://code.google.com/webtoolkit/doc/latest/DevGuideClientBundle.html#CssResource'>CssResource</a> </td><td>                 </td></tr>
<tr><td> Graphics   </td><td> HTML5 <a href='http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/canvas/client/Canvas.html'>Canvas Widget</a> </td><td>                 </td></tr>
<tr><td> SoundResource </td><td> HTML5 <a href='http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/media/client/Audio.html'>Audio Widget</a> </td><td>                 </td></tr>
<tr><td> Validation </td><td> <a href='http://code.google.com/webtoolkit/doc/latest/DevGuideUiEditors.html'>Editor framework</a> </td><td>                 </td></tr></tbody></table>

<b>Status of incubator Widgets:</b>
<table><thead><th> <b>Incubator Widget</b> </th><th> <b>Equivalent in GWT trunk</b> </th><th> <b>Comments</b> </th></thead><tbody>
<tr><td> PagingScrollTable       </td><td> <a href='http://code.google.com/webtoolkit/doc/latest/DevGuideUiCellTable.html'>DataGrid</a> </td><td> Introduced in GWT 2.4 </td></tr>
<tr><td> GlassPanel              </td><td> <a href='http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/user/client/ui/PopupPanel.html#setGlassEnabled(boolean)'>PopupPanel.setGlassEnabled(boolean)</a> </td><td>                 </td></tr>
<tr><td> FastTree                </td><td> <a href='http://code.google.com/webtoolkit/doc/latest/DevGuideUiCellWidgets.html#celltree'>CellTree</a> </td><td>                 </td></tr>
<tr><td> DatePicker              </td><td> <a href='http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/user/datepicker/client/DatePicker.html'>DatePicker</a> </td><td>                 </td></tr>
<tr><td> SliderBar               </td><td>                                </td><td> We may add the <a href='http://dev.w3.org/html5/markup/input.range.html'>HTML5 Range Input</a> in a future release </td></tr>
<tr><td> ProgressBar             </td><td>                                </td><td> We may add the <a href='http://www.w3.org/wiki/HTML/Elements/progress'>HTML5 Progress Element</a> in a future release </td></tr>
<tr><td> CollapsiblePanel        </td><td>                                </td><td> There is no replacement for CollapsiblePanel, but <a href='http://code.google.com/webtoolkit/doc/latest/DevGuideUiPanels.html#LayoutPanels'>LayoutPanels</a> allow you to create your own </td></tr>
<tr><td> Drop Down List Box      </td><td>                                </td><td> Not planned at this time, but some people use a vertical <a href='http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/user/client/ui/MenuBar.html'>MenuBar</a> to achieve the same effect </td></tr>
<tr><td> Spinner                 </td><td>                                </td><td> Not planned at this time </td></tr>
</font></tbody></table>

The Google Web Toolkit (GWT) Incubator fosters additional widgets and libraries for GWT before they are added to the core toolkit. The project is managed by the GWT engineering team at Google, and is used as a place to share, discuss, and vet future or speculative GWT features. Future releases of GWT may or may not use these features, but you are welcome to pull them from here to use today.

We encourage the creation of separate projects on Google Code, in the spirit of [GIN](http://code.google.com/p/google-gin/) and [gwt-dnd](http://code.google.com/p/gwt-dnd/).

### Downloads ###
The most current incubator jar can be downloaded [here](http://code.google.com/p/google-web-toolkit-incubator/wiki/Downloads?tm=2). All gwt-incubator source is contained in a single jar file for convenience.

### Documentation ###
See the [gwt-incubator documentation](http://code.google.com/docreader/#p=google-web-toolkit-incubator&s=google-web-toolkit-incubator&t=FunctionalitySummary) to learn more about the current set of widgets and libraries.

For specific API documentation, please see the [API class reference](http://collectionofdemos.appspot.com/javadoc/index.html) (Javadoc)