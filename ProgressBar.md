# Introduction #
The ProgressBar Widget displays progress over a range of values.  Users can dynamically change the minimum and maximum values in the range.  When the current value is set, a bar will fill the widget up to the percent relative to the range.

Example:
http://google-web-toolkit-incubator.googlecode.com/svn/trunk/demo/ProgressBar/index.html


# CSS #

Here is an example CSS style for the ProgressBar Widget:
```
.gwt-ProgressBar-shell {
  border: 2px solid #faf9f7;
  border-right: 2px solid #848280;
  border-bottom: 2px solid #848280;
  background-color: #AAAAAA;
  height: 14pt;
  width: 50%;
}
.gwt-ProgressBar-shell .gwt-ProgressBar-bar {
  background-color: #67A7E3;
}
.gwt-ProgressBar-shell .gwt-ProgressBar-text {
  padding: 0px;
  margin: 0px;
  color: white;
}
```


# Sample Code #
  ProgressBar bar = new ProgressBar(0.0, 2000.0 0.0);
  bar.setProgress(1500.0);
}}```