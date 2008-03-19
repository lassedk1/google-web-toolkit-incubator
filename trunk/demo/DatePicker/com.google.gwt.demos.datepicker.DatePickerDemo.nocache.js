function com_google_gwt_demos_datepicker_DatePickerDemo(){
  var $wnd_0 = window, $doc_0 = document, external = $wnd_0.external, $stats = $wnd_0.__gwtstatsEvent?function(a, b, c, d){
    $wnd_0.__gwtstatsEvent(a, b, c, d);
  }
  :null, scriptsDone, loadDone, bodyDone, base = '', metaProps = {}, values = [], providers = [], answers = [], onLoadErrorFunc, propertyErrorFunc;
  $stats && $stats('com.google.gwt.demos.datepicker.DatePickerDemo', 'startup', 'selectionStart', {millis:(new Date()).getTime()});
  if (!$wnd_0.__gwt_stylesLoaded) {
    $wnd_0.__gwt_stylesLoaded = {};
  }
  if (!$wnd_0.__gwt_scriptsLoaded) {
    $wnd_0.__gwt_scriptsLoaded = {};
  }
  function isHostedMode(){
    try {
      return external && (external.gwtOnLoad && $wnd_0.location.search.indexOf('gwt.hybrid') == -1);
    }
     catch (e) {
      return false;
    }
  }

  function maybeStartModule(){
    if (scriptsDone && loadDone) {
      var iframe = $doc_0.getElementById('com.google.gwt.demos.datepicker.DatePickerDemo');
      var frameWnd = iframe.contentWindow;
      frameWnd.__gwt_initHandlers = com_google_gwt_demos_datepicker_DatePickerDemo.__gwt_initHandlers;
      if (isHostedMode()) {
        frameWnd.__gwt_getProperty = function(name){
          return computePropValue(name);
        }
        ;
      }
      com_google_gwt_demos_datepicker_DatePickerDemo = null;
      frameWnd.gwtOnLoad(onLoadErrorFunc, 'com.google.gwt.demos.datepicker.DatePickerDemo', base);
      $stats && $stats('com.google.gwt.demos.datepicker.DatePickerDemo', 'startup', 'selectionDone', {millis:(new Date()).getTime()});
    }
  }

  function computeScriptBase(){
    var thisScript, markerId = '__gwt_marker_com.google.gwt.demos.datepicker.DatePickerDemo', markerScript;
    $doc_0.write('<script id="' + markerId + '"><\/script>');
    markerScript = $doc_0.getElementById(markerId);
    thisScript = markerScript && markerScript.previousSibling;
    while (thisScript && thisScript.tagName != 'SCRIPT') {
      thisScript = thisScript.previousSibling;
    }
    function getDirectoryOfFile(path){
      var eq = path.lastIndexOf('/');
      return eq >= 0?path.substring(0, eq + 1):'';
    }

    ;
    if (thisScript && thisScript.src) {
      base = getDirectoryOfFile(thisScript.src);
    }
    if (base == '') {
      var baseElements = $doc_0.getElementsByTagName('base');
      if (baseElements.length > 0) {
        base = baseElements[baseElements.length - 1].href;
      }
       else {
        var loc = $doc_0.location;
        var href = loc.href;
        base = getDirectoryOfFile(href.substr(0, href.length - loc.hash.length));
      }
    }
     else if (base.match(/^\w+:\/\//)) {
    }
     else {
      var img = $doc_0.createElement('img');
      img.src = base + 'clear.cache.gif';
      base = getDirectoryOfFile(img.src);
    }
    if (markerScript) {
      markerScript.parentNode.removeChild(markerScript);
    }
  }

  function processMetas(){
    var metas = document.getElementsByTagName('meta');
    for (var i = 0, n = metas.length; i < n; ++i) {
      var meta = metas[i], name = meta.getAttribute('name'), content;
      if (name) {
        if (name == 'gwt:property') {
          content = meta.getAttribute('content');
          if (content) {
            var value, eq = content.indexOf('=');
            if (eq >= 0) {
              name = content.substring(0, eq);
              value = content.substring(eq + 1);
            }
             else {
              name = content;
              value = '';
            }
            metaProps[name] = value;
          }
        }
         else if (name == 'gwt:onPropertyErrorFn') {
          content = meta.getAttribute('content');
          if (content) {
            try {
              propertyErrorFunc = eval(content);
            }
             catch (e) {
              alert('Bad handler "' + content + '" for "gwt:onPropertyErrorFn"');
            }
          }
        }
         else if (name == 'gwt:onLoadErrorFn') {
          content = meta.getAttribute('content');
          if (content) {
            try {
              onLoadErrorFunc = eval(content);
            }
             catch (e) {
              alert('Bad handler "' + content + '" for "gwt:onLoadErrorFn"');
            }
          }
        }
      }
    }
  }

  function unflattenKeylistIntoAnswers(propValArray, value){
    var answer = answers;
    for (var i = 0, n = propValArray.length - 1; i < n; ++i) {
      answer = answer[propValArray[i]] || (answer[propValArray[i]] = []);
    }
    answer[propValArray[n]] = value;
  }

  function computePropValue(propName){
    var value = providers[propName](), allowedValuesMap = values[propName];
    if (value in allowedValuesMap) {
      return value;
    }
    var allowedValuesList = [];
    for (var k in allowedValuesMap) {
      allowedValuesList[allowedValuesMap[k]] = k;
    }
    if (propertyErrorFunc) {
      propertyErrorFunc(propName, allowedValuesList, value);
    }
    throw null;
  }

  providers['user.agent'] = function(){
    var ua = navigator.userAgent.toLowerCase();
    var makeVersion = function(result){
      return parseInt(result[1]) * 1000 + parseInt(result[2]);
    }
    ;
    if (ua.indexOf('opera') != -1) {
      return 'opera';
    }
     else if (ua.indexOf('webkit') != -1) {
      return 'safari';
    }
     else if (ua.indexOf('msie') != -1) {
      var result_0 = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
      if (result_0 && result_0.length == 3) {
        if (makeVersion(result_0) >= 6000) {
          return 'ie6';
        }
      }
    }
     else if (ua.indexOf('gecko') != -1) {
      var result_0 = /rv:([0-9]+)\.([0-9]+)/.exec(ua);
      if (result_0 && result_0.length == 3) {
        if (makeVersion(result_0) >= 1008)
          return 'gecko1_8';
      }
      return 'gecko';
    }
    return 'unknown';
  }
  ;
  values['user.agent'] = {gecko:0, gecko1_8:1, ie6:2, opera:3, safari:4};
  com_google_gwt_demos_datepicker_DatePickerDemo.onInjectionDone = function(){
    scriptsDone = true;
    $stats && $stats('com.google.gwt.demos.datepicker.DatePickerDemo', 'startup', 'moduleRequested', {millis:(new Date()).getTime()});
    maybeStartModule();
  }
  ;
  com_google_gwt_demos_datepicker_DatePickerDemo.onScriptLoad = function(){
    loadDone = true;
    maybeStartModule();
  }
  ;
  computeScriptBase();
  processMetas();
  var strongName;
  if (isHostedMode()) {
    strongName = 'hosted.html?com_google_gwt_demos_datepicker_DatePickerDemo';
  }
   else {
    try {
      unflattenKeylistIntoAnswers(['opera'], '503E227314F3C989EF5FCEDB240EAA39.cache.html');
      unflattenKeylistIntoAnswers(['ie6'], '97B6C3EC526723269CC4A50D3B10BA5F.cache.html');
      unflattenKeylistIntoAnswers(['safari'], 'B7AE06E01A1DB35455ACAF8060BE654C.cache.html');
      unflattenKeylistIntoAnswers(['gecko1_8'], '0822D3780EAEC47D83D77CFE3F3A68C4.cache.html');
      unflattenKeylistIntoAnswers(['gecko'], '4FD9552A93959B5DA6B0AF51F9D89215.cache.html');
      strongName = answers[computePropValue('user.agent')];
    }
     catch (e) {
      return;
    }
  }
  var onBodyDoneTimerId;
  function onBodyDone(){
    if (!bodyDone) {
      bodyDone = true;
      maybeStartModule();
      if ($doc_0.removeEventListener) {
        $doc_0.removeEventListener('DOMContentLoaded', onBodyDone, false);
      }
      if (onBodyDoneTimerId) {
        clearInterval(onBodyDoneTimerId);
      }
    }
  }

  var frameInjected;
  function maybeInjectFrame(){
    if (!frameInjected) {
      frameInjected = true;
      var iframe = $doc_0.createElement('iframe');
      iframe.src = "javascript:''";
      iframe.id = 'com.google.gwt.demos.datepicker.DatePickerDemo';
      iframe.style.cssText = 'position:absolute;width:0;height:0;border:none';
      iframe.tabIndex = -1;
      $doc_0.body.appendChild(iframe);
      iframe.src = base + strongName;
    }
  }

  if ($doc_0.addEventListener) {
    $doc_0.addEventListener('DOMContentLoaded', function(){
      maybeInjectFrame();
      onBodyDone();
    }
    , false);
  }
  var onBodyDoneTimerId = setInterval(function(){
    if (/loaded|complete/.test($doc_0.readyState)) {
      maybeInjectFrame();
      onBodyDone();
    }
  }
  , 50);
  if (!__gwt_stylesLoaded['DatePickerDemo.css']) {
    __gwt_stylesLoaded['DatePickerDemo.css'] = true;
    document.write('<link rel="stylesheet" href="' + base + 'DatePickerDemo.css">');
  }
  $doc_0.write("<script>com_google_gwt_demos_datepicker_DatePickerDemo.onInjectionDone('com.google.gwt.demos.datepicker.DatePickerDemo')<\/script>");
}

com_google_gwt_demos_datepicker_DatePickerDemo.__gwt_initHandlers = function(resize, beforeunload, unload){
  var $wnd_0 = window, oldOnResize = $wnd_0.onresize, oldOnBeforeUnload = $wnd_0.onbeforeunload, oldOnUnload = $wnd_0.onunload;
  $wnd_0.onresize = function(evt){
    try {
      resize();
    }
     finally {
      oldOnResize && oldOnResize(evt);
    }
  }
  ;
  $wnd_0.onbeforeunload = function(evt){
    var ret, oldRet;
    try {
      ret = beforeunload();
    }
     finally {
      oldRet = oldOnBeforeUnload && oldOnBeforeUnload(evt);
    }
    if (ret != null) {
      return ret;
    }
    if (oldRet != null) {
      return oldRet;
    }
  }
  ;
  $wnd_0.onunload = function(evt){
    try {
      unload();
    }
     finally {
      oldOnUnload && oldOnUnload(evt);
    }
  }
  ;
}
;
com_google_gwt_demos_datepicker_DatePickerDemo();
