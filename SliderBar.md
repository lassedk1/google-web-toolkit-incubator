# Introduction #
The SliderBar input Widget lets the user select a value from a range of values via mouse events or keyboard events.  You can dynamically change the minimum and maximum values and step size.  The size size allows you to restrict the selected value based on an incremental step.

The widget itself can display tick marks and labels along the range.  You can specify how many tick marks or labels to show.

The widget is highly customizable via CSS, and the knob itself can be swapped using an Image Bundle.

Example:
http://google-web-toolkit-incubator.googlecode.com/svn/trunk/demo/SliderBar/index.html


# CSS #

Here is an example CSS style for the SliderBar Widget:
```
.gwt-SliderBar-shell {
  border: 2px solid #faf9f7;
  border-right: 2px solid #848280;
  border-bottom: 2px solid #848280;
  background-color: #efebe7;
  height: 34pt;
  width: 50%;
}
.gwt-SliderBar-shell .gwt-SliderBar-line {
  border: 1px solid black;
  background-color: white;
  height: 4px;
  width: 95%;
  top: 22pt;
  overflow: hidden;
}
.gwt-SliderBar-shell .gwt-SliderBar-knob {
  top: 14pt;
  width: 11px;
  height: 21px;
  z-index: 1;
  cursor: pointer;
}
.gwt-SliderBar-shell .gwt-SliderBar-tick {
  top: 12pt;
  width: 1px;
  height: 8pt;
  background: black;
  overflow: hidden;
}
.gwt-SliderBar-shell .gwt-SliderBar-label {
  top: 2pt;
  font-size: 8pt;
  cursor: default;
}
.gwt-SliderBar-shell-focused {
}
.gwt-SliderBar-shell .gwt-SliderBar-line-sliding {
  background-color: #DDDDDD;
  cursor: pointer;
}
```


# Sample Code #

```
  SliderBar slider = new SliderBar(0.0, 100.0);
  slider.setStepSize(5.0);
  slider.setCurrentValue(50.0);
  slider.setNumTicks(10);
  slider.setNumLabels(5);
```


