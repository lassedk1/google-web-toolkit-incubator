| Live demo | http://collectionofdemos.appspot.com/demo/com.google.gwt.demos.spinner.SpinnerDemo/SpinnerDemo.html |
|:----------|:----------------------------------------------------------------------------------------------------|
| Java docs | [Javadocs](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/doc/javadoc/index.html?com/google/gwt/widgetideas/client/Spinner.html)|
| Sample code | [Demo code](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/src/com/google/gwt/demos/spinner/client/SpinnerDemoPanel.java) |
| Source starting point| [Spinner.java](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/src/com/google/gwt/widgetideas/client/Spinner.java) |

# Introduction #
A Spinner provides to arrows to increase and decrease a value. In combination with an input field this becomes a well known widget used mainly for setting times.

# Details #
The Spinner class provdes two arrow images and the logic to increase or decrease a given value by pressing on these images.
SpinnerListeners can be registered to listen to value changes.
The incubator provides some widgets using the Spinner:

| **ValueSpinner** |Textbox to display a numeric value that can be adjusted by using the arrows |
|:-----------------|:---------------------------------------------------------------------------|
| **TimePicker**   |Widget to enter a time using arrows for each field (hours, minutes, seconds) |
| **DateTimePicker** |Combination of a DatePicker and the TimePicker to select a date and time    |
| **VolumeSlider** |Combination of a ProgressBar and arrows to increase decrease the value      |

# Examples #

### ValueSpinner ###
```
// Create a value spinner with initial value=0 that allows values between -1000 and 1000
ValueSpinner simpleSpinner = new ValueSpinner(0, -1000, 1000);
```

### Customized ValueSpinner to display value as formatted date ###
```
// Create a value spinner that displays the value as a formatted date
ValueSpinner dateSpinner = new ValueSpinner(new Date().getTime()) {
  protected String formatValue(long value) {
    return DateTimeFormat.getLongDateFormat().format(new Date(value));
  }
  protected long parseValue(String value) {
    return DateTimeFormat.getLongDateFormat().parse(value).getTime();
  }
};
// Min step = milliseconds per day
dateSpinner.getSpinner().setMinStep(86400000);
```

### TimePicker ###
```
// A TimePicker using AM/PM 12h format
TimePicker timePicker = new TimePicker(false);

// A TimePicker with no "AM/PM" and no "seconds" field and using 24H formatting for "hours" filed
TimePicker customTimePicker = new TimePicker(new Date(), null, DateTimeFormat.getFormat("HH"), DateTimeFormat.getFormat("mm"), null);
```

### DateTimePicker ###
```
// Creating a date time picker with AM/PM 12h TimePicker
final DateTimePicker dateTimePicker = new DateTimePicker(false);
// Create a label to display the selected date
final Label dateLabel = new Label();
// Wrap the DateTimePicker into a DecoratorPanel
DecoratorPanel decoratorPanel = new DecoratorPanel();
decoratorPanel.setWidget(dateTimePicker);
Button getDate = new Button("Show date");
getDate.addClickListener(new ClickListener() {
  public void onClick(Widget sender) {
    // Display the selected date/time using DateTimeFormat
    dateLabel.setText(DateTimeFormat.getMediumDateTimeFormat().format(dateTimePicker.getDate()));
  }
});
```

### VolumeSlider ###
```
// Initial value = 0, min-value=-100 max-value=100
VolumeSlider volumeSlider = new VolumeSlider(0, -100, 100);
```