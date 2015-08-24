The following is a summary of widgets and libraries that are currently available in the GWT incubator.

## Widgets ##
  * [GlassPanel](GlassPanel.md), exactly what is sounds, very useful to produce translucent effects].
  * [DropDownListBox](DropDownListBox.md), a fully customizable drop down list box.
  * [CollapsiblePanel](CollapsiblePanel.md), an easy and convinient panel to create hidable navigation panels.
  * [FastTree](FastTree.md), a fully customizable tree with a rich event model.
  * [SliderBar/ProgressBar/Spinner/TimePicker/DateTimePicker](Spinner.md), A set of really useful widgets to add zazz to your project.

## Tables ##
  * [BulkTableRenderers](BulkTableRenderers.md) Bulk rendering of tables(up to 10x faster then normal rendering).
  * [Scrolling tables](ScrollTable.md), where the footer and header are fixed.
  * [Paging tables](PagingScrollTable.md), where the table can page through arbitrary data sets.
  * [Editable tables](PagingScrollTable.md), complete with common cell editors.

## Logging ##
[The  logging suite](Logging.md) provides additional logging capabilities, including:
  * Logging levels based on Java's logging system.
  * Categories and filters to narrow logging output.
  * A native java mode for shared code, where the output can be integrated with your server logging system.
  * Compilation options so logging can be completely removed or minimally included for production mode.
  * A variety of logging handlers that output to everything from firebug to a GWT tree widget.

## Css support ##
[CssResource](CssResource.md) optimizes `css` source files for download, allowing them to be fully commented and modularized source, but quick and efficient in production. CssResource generates CSS that is:
  * Stripped of comments and white space.
  * Aggregated into a single html file.
  * (And in an advanced configuration)
    * Obfucated css style names
    * Unused css rules eliminated
    * Rules selectively applied per browser.

## Graphics ##
The [GWTCanvas Widget](GWTCanvas.md) exposes an API for drawing and transforming shapes and images, as well as for defining paths to create custom shapes. GWTCanvas currently supports:
  * Rotations, scales, and translations.
  * Custom paths including arcs, lines and curves.
  * Image drawing and transformation.
  * Compositing operations and transparency.

## Utilities ##
  * [html decorators which allow more flexible styling of your widgets](Decorators.md)
  * [server side locale](ServerSideLocaleSelection.md)