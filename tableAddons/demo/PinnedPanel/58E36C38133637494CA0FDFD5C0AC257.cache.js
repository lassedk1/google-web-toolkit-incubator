(function(){
var $wnd = window;
var $doc = $wnd.document;
var $moduleName, $moduleBase;
var _, package_com_google_gwt_core_client_ = 'com.google.gwt.core.client.', package_com_google_gwt_demos_pinnedpanel_client_ = 'com.google.gwt.demos.pinnedpanel.client.', package_com_google_gwt_lang_ = 'com.google.gwt.lang.', package_com_google_gwt_user_client_ = 'com.google.gwt.user.client.', package_com_google_gwt_user_client_impl_ = 'com.google.gwt.user.client.impl.', package_com_google_gwt_user_client_ui_ = 'com.google.gwt.user.client.ui.', package_com_google_gwt_user_client_ui_impl_ = 'com.google.gwt.user.client.ui.impl.', package_com_google_gwt_widgetideas_client_ = 'com.google.gwt.widgetideas.client.', package_java_lang_ = 'java.lang.', package_java_util_ = 'java.util.';
function nullMethod(){
}

function equals_2(other){
  return this === other;
}

function hashCode_3(){
  return identityHashCode(this);
}

function Object_0(){
}

_ = Object_0.prototype = {};
_.equals$ = equals_2;
_.hashCode$ = hashCode_3;
_.typeName$ = package_java_lang_ + 'Object';
_.typeId$ = 1;
function getModuleBaseURL(){
  return getModuleBaseURL_0();
}

function getTypeName(o){
  return o == null?null:o.typeName$;
}

var sUncaughtExceptionHandler = null;
function getHashCode(o){
  return o == null?0:o.$H?o.$H:(o.$H = getNextHashId());
}

function getHashCode_0(o){
  return o == null?0:o.$H?o.$H:(o.$H = getNextHashId());
}

function getModuleBaseURL_0(){
  return $moduleBase;
}

function getNextHashId(){
  return ++sNextHashId;
}

var sNextHashId = 0;
function $equals(this$static, other){
  if (!instanceOf(other, 2)) {
    return false;
  }
  return equalsImpl(this$static, dynamicCast(other, 2));
}

function $hashCode(this$static){
  return getHashCode(this$static);
}

function createArray(){
  return [];
}

function createObject(){
  return {};
}

function equals(other){
  return $equals(this, other);
}

function equalsImpl(o, other){
  return o === other;
}

function hashCode_0(){
  return $hashCode(this);
}

function JavaScriptObject(){
}

_ = JavaScriptObject.prototype = new Object_0();
_.equals$ = equals;
_.hashCode$ = hashCode_0;
_.typeName$ = package_com_google_gwt_core_client_ + 'JavaScriptObject';
_.typeId$ = 7;
function $createSchoolNavBar(this$static, toggler){
  var admin, contents, navBar, panel, students, teachers, title;
  navBar = $FlowPanel(new FlowPanel());
  $setSize(navBar, '100%', '100%');
  title = $HTML_0(new HTML(), 'School Directory');
  panel = $HorizontalPanel(new HorizontalPanel());
  $setWidth(panel, '100%');
  $add_3(panel, toggler);
  $add_3(panel, title);
  $add_2(navBar, panel);
  $setStyleName(panel, 'nav-tree-title');
  this$static.wrapper = $PinnedPanelDemo$MyStackPanel(new PinnedPanelDemo$MyStackPanel(), this$static);
  $setHeight(this$static.wrapper, '250px');
  contents = $Tree(new Tree());
  $add_6(this$static.wrapper, contents, '<b>People<\/b>', true);
  $add_6(this$static.wrapper, $Label_0(new Label(), 'None'), '<b>Academics<\/b>', true);
  $add_2(navBar, this$static.wrapper);
  students = $addItem_2(contents, 'Students');
  $addItem_0(students, 'Jill');
  $addItem_0(students, 'Jack');
  $addItem_0(students, 'Molly');
  $addItem_0(students, 'Ms. Muffat');
  teachers = $addItem_2(contents, 'Teachers');
  $addItem_0(teachers, 'Mrs Black');
  $addItem_0(teachers, 'Mr White');
  admin = $addItem_2(contents, 'Administrators');
  $addItem_0(admin, 'The Soup Nazi');
  $addItem_0(admin, 'The Grand High Supreme Master Pubba');
  return navBar;
}

function $createToggleButton(this$static){
  var hide, show, toggler;
  show = $Image_0(new Image_0(), 'show.jpg');
  hide = $Image_0(new Image_0(), 'hide.jpg');
  toggler = $ToggleButton(new ToggleButton(), hide, show);
  return toggler;
}

function $onModuleLoad(this$static){
  var contents, pinned, toggler;
  toggler = $createToggleButton(this$static);
  contents = $createSchoolNavBar(this$static, toggler);
  pinned = $PinnedPanel(new PinnedPanel(), 200, toggler, contents);
  $setRightMargin(pinned, 10);
  $add(get('pinned-panel'), pinned);
  $sizePinnedPanel(this$static, pinned, getClientHeight());
  addWindowResizeListener($PinnedPanelDemo$1(new PinnedPanelDemo$1(), this$static, pinned));
}

function $sizePinnedPanel(this$static, pinned, height){
  $setHeight(pinned, height - 120 + 'px');
  $setHeight(this$static.wrapper, height - 160 + 'px');
  $showStack(this$static.wrapper, this$static.wrapper.visibleStack);
}

function PinnedPanelDemo(){
}

_ = PinnedPanelDemo.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_demos_pinnedpanel_client_ + 'PinnedPanelDemo';
_.typeId$ = 8;
_.wrapper = null;
function $PinnedPanelDemo$1(this$static, this$0, val$pinned){
  this$static.this$0 = this$0;
  this$static.val$pinned = val$pinned;
  return this$static;
}

function onWindowResized(width, height){
  $sizePinnedPanel(this.this$0, this.val$pinned, height);
}

function PinnedPanelDemo$1(){
}

_ = PinnedPanelDemo$1.prototype = new Object_0();
_.onWindowResized = onWindowResized;
_.typeName$ = package_com_google_gwt_demos_pinnedpanel_client_ + 'PinnedPanelDemo$1';
_.typeId$ = 9;
function $addStyleDependentName(this$static, styleSuffix){
  $addStyleName(this$static, $getStylePrimaryName(this$static) + charToString(45) + styleSuffix);
}

function $addStyleName(this$static, style){
  setStyleName_0(this$static.element, style, true);
}

function $getAbsoluteLeft_0(this$static){
  return getAbsoluteLeft(this$static.getElement());
}

function $getAbsoluteTop_0(this$static){
  return getAbsoluteTop(this$static.getElement());
}

function $getOffsetWidth(this$static){
  return getElementPropertyInt(this$static.element, 'offsetWidth');
}

function $getStylePrimaryName(this$static){
  return getStylePrimaryName(this$static.element);
}

function $removeStyleDependentName(this$static, styleSuffix){
  $removeStyleName(this$static, $getStylePrimaryName(this$static) + charToString(45) + styleSuffix);
}

function $removeStyleName(this$static, style){
  setStyleName_0(this$static.element, style, false);
}

function $replaceNode(this$static, node, newNode){
  var p = node.parentNode;
  if (!p) {
    return;
  }
  p.insertBefore(newNode, node);
  p.removeChild(node);
}

function $setElement_0(this$static, elem){
  if (this$static.element !== null) {
    $replaceNode(this$static, this$static.element, elem);
  }
  this$static.element = elem;
}

function $setHeight(this$static, height){
  setStyleAttribute(this$static.element, 'height', height);
}

function $setSize(this$static, width, height){
  $setWidth(this$static, width);
  $setHeight(this$static, height);
}

function $setStyleName(this$static, style){
  setStyleName(this$static.element, style);
}

function $setVisible(this$static, visible){
  setVisible(this$static.element, visible);
}

function $setWidth(this$static, width){
  setStyleAttribute(this$static.element, 'width', width);
}

function $sinkEvents_0(this$static, eventBitsToAdd){
  sinkEvents(this$static.getElement(), eventBitsToAdd | getEventsSunk(this$static.getElement()));
}

function getElement_0(){
  return this.element;
}

function getStyleName(elem){
  return getElementProperty(elem, 'className');
}

function getStylePrimaryName(elem){
  var fullClassName, spaceIdx;
  fullClassName = getStyleName(elem);
  spaceIdx = $indexOf_0(fullClassName, 32);
  if (spaceIdx >= 0) {
    return $substring_0(fullClassName, 0, spaceIdx);
  }
  return fullClassName;
}

function setElement_0(elem){
  $setElement_0(this, elem);
}

function setStyleName(elem, styleName){
  setElementProperty(elem, 'className', styleName);
}

function setStyleName_0(elem, style, add){
  var begin, end, idx, last, lastPos, newClassName, oldStyle;
  if (elem === null) {
    throw $RuntimeException(new RuntimeException(), 'Null widget handle. If you are creating a composite, ensure that initWidget() has been called.');
  }
  style = $trim(style);
  if ($length(style) == 0) {
    throw $IllegalArgumentException(new IllegalArgumentException(), 'Style names cannot be empty');
  }
  oldStyle = getStyleName(elem);
  idx = $indexOf_1(oldStyle, style);
  while (idx != (-1)) {
    if (idx == 0 || $charAt(oldStyle, idx - 1) == 32) {
      last = idx + $length(style);
      lastPos = $length(oldStyle);
      if (last == lastPos || last < lastPos && $charAt(oldStyle, last) == 32) {
        break;
      }
    }
    idx = $indexOf_2(oldStyle, style, idx + 1);
  }
  if (add) {
    if (idx == (-1)) {
      if ($length(oldStyle) > 0) {
        oldStyle += ' ';
      }
      setElementProperty(elem, 'className', oldStyle + style);
    }
  }
   else {
    if (idx != (-1)) {
      begin = $trim($substring_0(oldStyle, 0, idx));
      end = $trim($substring(oldStyle, idx + $length(style)));
      if ($length(begin) == 0) {
        newClassName = end;
      }
       else if ($length(end) == 0) {
        newClassName = begin;
      }
       else {
        newClassName = begin + ' ' + end;
      }
      setElementProperty(elem, 'className', newClassName);
    }
  }
}

function setVisible(elem, visible){
  elem.style.display = visible?'':'none';
}

function UIObject(){
}

_ = UIObject.prototype = new Object_0();
_.getElement = getElement_0;
_.setElement = setElement_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'UIObject';
_.typeId$ = 10;
_.element = null;
function $onAttach(this$static){
  if (this$static.isAttached()) {
    throw $IllegalStateException(new IllegalStateException(), "Should only call onAttach when the widget is detached from the browser's document");
  }
  this$static.attached = true;
  setEventListener(this$static.getElement(), this$static);
  this$static.doAttachChildren();
  this$static.onLoad();
}

function $onDetach(this$static){
  if (!this$static.isAttached()) {
    throw $IllegalStateException(new IllegalStateException(), "Should only call onDetach when the widget is attached to the browser's document");
  }
  try {
    this$static.onUnload();
  }
   finally {
    this$static.doDetachChildren();
    setEventListener(this$static.getElement(), null);
    this$static.attached = false;
  }
}

function $removeFromParent(this$static){
  if (instanceOf(this$static.parent, 14)) {
    dynamicCast(this$static.parent, 14).remove_0(this$static);
  }
   else if (this$static.parent !== null) {
    throw $IllegalStateException(new IllegalStateException(), "This widget's parent does not implement HasWidgets");
  }
}

function $setElement_1(this$static, elem){
  if (this$static.isAttached()) {
    setEventListener(this$static.getElement(), null);
  }
  $setElement_0(this$static, elem);
  if (this$static.isAttached()) {
    setEventListener(elem, this$static);
  }
}

function $setParent(this$static, parent){
  var oldParent;
  oldParent = this$static.parent;
  if (parent === null) {
    if (oldParent !== null && oldParent.isAttached()) {
      this$static.onDetach();
    }
    this$static.parent = null;
  }
   else {
    if (oldParent !== null) {
      throw $IllegalStateException(new IllegalStateException(), 'Cannot set a new parent without first clearing the old parent');
    }
    this$static.parent = parent;
    if (parent.isAttached()) {
      this$static.onAttach();
    }
  }
}

function doAttachChildren_1(){
}

function doDetachChildren_1(){
}

function isAttached_0(){
  return this.attached;
}

function onAttach_1(){
  $onAttach(this);
}

function onBrowserEvent_6(event_0){
}

function onDetach_1(){
  $onDetach(this);
}

function onLoad_2(){
}

function onUnload_0(){
}

function setElement_1(elem){
  $setElement_1(this, elem);
}

function Widget(){
}

_ = Widget.prototype = new UIObject();
_.doAttachChildren = doAttachChildren_1;
_.doDetachChildren = doDetachChildren_1;
_.isAttached = isAttached_0;
_.onAttach = onAttach_1;
_.onBrowserEvent = onBrowserEvent_6;
_.onDetach = onDetach_1;
_.onLoad = onLoad_2;
_.onUnload = onUnload_0;
_.setElement = setElement_1;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Widget';
_.typeId$ = 11;
_.attached = false;
_.parent = null;
function $adopt(this$static, child){
  $setParent(child, this$static);
}

function $orphan(this$static, child){
  $setParent(child, null);
}

function doAttachChildren(){
  var child, it;
  for (it = this.iterator_0(); it.hasNext();) {
    child = dynamicCast(it.next_0(), 11);
    child.onAttach();
  }
}

function doDetachChildren(){
  var child, it;
  for (it = this.iterator_0(); it.hasNext();) {
    child = dynamicCast(it.next_0(), 11);
    child.onDetach();
  }
}

function onLoad_0(){
}

function onUnload(){
}

function Panel(){
}

_ = Panel.prototype = new Widget();
_.doAttachChildren = doAttachChildren;
_.doDetachChildren = doDetachChildren;
_.onLoad = onLoad_0;
_.onUnload = onUnload;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Panel';
_.typeId$ = 12;
function $$init_1(this$static){
  this$static.children = $WidgetCollection(new WidgetCollection(), this$static);
}

function $ComplexPanel(this$static){
  $$init_1(this$static);
  return this$static;
}

function $add_1(this$static, child, container){
  $removeFromParent(child);
  $add_7(this$static.children, child);
  appendChild(container, child.getElement());
  $adopt(this$static, child);
}

function $adjustIndex(this$static, child, beforeIndex){
  var idx;
  $checkIndexBoundsForInsertion(this$static, beforeIndex);
  if (child.parent === this$static) {
    idx = $getWidgetIndex(this$static, child);
    if (idx < beforeIndex) {
      beforeIndex--;
    }
  }
  return beforeIndex;
}

function $checkIndexBoundsForInsertion(this$static, index){
  if (index < 0 || index > this$static.children.size) {
    throw new IndexOutOfBoundsException();
  }
}

function $getWidget(this$static, index){
  return $get(this$static.children, index);
}

function $getWidgetIndex(this$static, child){
  return $indexOf(this$static.children, child);
}

function $insert_0(this$static, child, container, beforeIndex, domInsert){
  beforeIndex = $adjustIndex(this$static, child, beforeIndex);
  $removeFromParent(child);
  $insert_2(this$static.children, child, beforeIndex);
  if (domInsert) {
    insertChild(container, child.getElement(), beforeIndex);
  }
   else {
    appendChild(container, child.getElement());
  }
  $adopt(this$static, child);
}

function $remove_0(this$static, w){
  var elem;
  if (w.parent !== this$static) {
    return false;
  }
  $orphan(this$static, w);
  elem = w.getElement();
  removeChild(getParent(elem), elem);
  $remove_5(this$static.children, w);
  return true;
}

function iterator(){
  return $iterator_0(this.children);
}

function remove_1(w){
  return $remove_0(this, w);
}

function ComplexPanel(){
}

_ = ComplexPanel.prototype = new Panel();
_.iterator_0 = iterator;
_.remove_0 = remove_1;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'ComplexPanel';
_.typeId$ = 13;
function $StackPanel(this$static){
  var table;
  $ComplexPanel(this$static);
  table = createTable();
  this$static.setElement(table);
  this$static.body_0 = createTBody();
  appendChild(table, this$static.body_0);
  setElementPropertyInt(table, 'cellSpacing', 0);
  setElementPropertyInt(table, 'cellPadding', 0);
  sinkEvents(table, 1);
  $setStyleName(this$static, 'gwt-StackPanel');
  return this$static;
}

function $add_5(this$static, w){
  $insert(this$static, w, this$static.children.size);
}

function $add_6(this$static, w, stackText, asHTML){
  $add_5(this$static, w);
  $setStackText(this$static, this$static.children.size - 1, stackText, asHTML);
}

function $findDividerIndex(this$static, elem){
  var expando, ownerHash;
  while (elem !== null && !compare(elem, this$static.getElement())) {
    expando = getElementProperty(elem, '__index');
    if (expando !== null) {
      ownerHash = getElementPropertyInt(elem, '__owner');
      if (ownerHash == this$static.hashCode$()) {
        return parseInt_0(expando);
      }
       else {
        return (-1);
      }
    }
    elem = getParent(elem);
  }
  return (-1);
}

function $insert_1(this$static, w, beforeIndex){
  var effectiveIndex, tdb, tdh, trb, trh;
  trh = createTR();
  tdh = createTD();
  appendChild(trh, tdh);
  trb = createTR();
  tdb = createTD();
  appendChild(trb, tdb);
  beforeIndex = $adjustIndex(this$static, w, beforeIndex);
  effectiveIndex = beforeIndex * 2;
  insertChild(this$static.body_0, trb, effectiveIndex);
  insertChild(this$static.body_0, trh, effectiveIndex);
  setStyleName_0(tdh, 'gwt-StackPanelItem', true);
  setElementPropertyInt(tdh, '__owner', this$static.hashCode$());
  setElementProperty(tdh, 'height', '1px');
  setElementProperty(tdb, 'height', '100%');
  setElementProperty(tdb, 'vAlign', 'top');
  $insert_0(this$static, w, tdb, beforeIndex, false);
  $updateIndicesFrom(this$static, beforeIndex);
  if (this$static.visibleStack == (-1)) {
    $showStack(this$static, 0);
  }
   else {
    $setStackVisible(this$static, beforeIndex, false);
    if (this$static.visibleStack >= beforeIndex) {
      ++this$static.visibleStack;
    }
  }
}

function $remove_2(this$static, child, index){
  var removed, rowIndex, tr;
  removed = $remove_0(this$static, child);
  if (removed) {
    rowIndex = 2 * index;
    tr = getChild(this$static.body_0, rowIndex);
    removeChild(this$static.body_0, tr);
    tr = getChild(this$static.body_0, rowIndex);
    removeChild(this$static.body_0, tr);
    if (this$static.visibleStack == index) {
      this$static.visibleStack = (-1);
    }
     else if (this$static.visibleStack > index) {
      --this$static.visibleStack;
    }
    $updateIndicesFrom(this$static, rowIndex);
  }
  return removed;
}

function $setStackText(this$static, index, text, asHTML){
  var td;
  if (index >= this$static.children.size) {
    return;
  }
  td = getChild(getChild(this$static.body_0, index * 2), 0);
  if (asHTML) {
    setInnerHTML(td, text);
  }
   else {
    setInnerText(td, text);
  }
}

function $setStackVisible(this$static, index, visible){
  var td, tr;
  tr = getChild(this$static.body_0, index * 2);
  if (tr === null) {
    return;
  }
  td = getFirstChild(tr);
  setStyleName_0(td, 'gwt-StackPanelItem-selected', visible);
  tr = getChild(this$static.body_0, index * 2 + 1);
  setVisible(tr, visible);
  $setVisible($getWidget(this$static, index), visible);
}

function $showStack_0(this$static, index){
  if (index >= this$static.children.size || index == this$static.visibleStack) {
    return;
  }
  if (this$static.visibleStack >= 0) {
    $setStackVisible(this$static, this$static.visibleStack, false);
  }
  this$static.visibleStack = index;
  $setStackVisible(this$static, this$static.visibleStack, true);
}

function $updateIndicesFrom(this$static, beforeIndex){
  var c, childTD, childTR, i;
  for (i = beforeIndex , c = this$static.children.size; i < c; ++i) {
    childTR = getChild(this$static.body_0, i * 2);
    childTD = getFirstChild(childTR);
    setElementPropertyInt(childTD, '__index', i);
  }
}

function onBrowserEvent_4(event_0){
  var index, target;
  if (eventGetType(event_0) == 1) {
    target = eventGetTarget(event_0);
    index = $findDividerIndex(this, target);
    if (index != (-1)) {
      this.showStack(index);
    }
  }
}

function remove_4(child){
  return $remove_2(this, child, $getWidgetIndex(this, child));
}

function showStack_0(index){
  $showStack_0(this, index);
}

function StackPanel(){
}

_ = StackPanel.prototype = new ComplexPanel();
_.onBrowserEvent = onBrowserEvent_4;
_.remove_0 = remove_4;
_.showStack = showStack_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'StackPanel';
_.typeId$ = 14;
_.body_0 = null;
_.visibleStack = (-1);
function $$init(this$static){
  this$static.scrollers = $ArrayList(new ArrayList());
}

function $PinnedPanelDemo$MyStackPanel(this$static, this$0){
  $StackPanel(this$static);
  $$init(this$static);
  return this$static;
}

function $insert(this$static, w, before){
  var p;
  p = $ScrollPanel_0(new ScrollPanel(), w);
  $setWidth(p, '100%');
  $add_8(this$static.scrollers, before, p);
  $insert_1(this$static, p, before);
}

function $showStack(this$static, index){
  var me, tr, trHeight;
  $showStack_0(this$static, index);
  if (this$static.isAttached()) {
    me = dynamicCast($get_0(this$static.scrollers, index), 3);
    $setHeight(me, '1px');
    tr = getChild(getFirstChild(this$static.getElement()), index * 2 + 1);
    trHeight = getElementPropertyInt(tr, 'offsetHeight');
    $setHeight(me, trHeight + 'px');
  }
}

function onLoad(){
  $setWidth(this, '100%');
  $showStack(this, this.visibleStack);
}

function showStack(index){
  $showStack(this, index);
}

function PinnedPanelDemo$MyStackPanel(){
}

_ = PinnedPanelDemo$MyStackPanel.prototype = new StackPanel();
_.onLoad = onLoad;
_.showStack = showStack;
_.typeName$ = package_com_google_gwt_demos_pinnedpanel_client_ + 'PinnedPanelDemo$MyStackPanel';
_.typeId$ = 15;
function $Array(this$static, length, typeId, queryId, typeName){
  this$static.length_0 = length;
  this$static.queryId = queryId;
  this$static.typeName$ = typeName;
  this$static.typeId$ = typeId;
  return this$static;
}

function _set(array, index, value){
  return array[index] = value;
}

function clonify_0(a, length){
  return clonify(a, length);
}

function clonify(a, length){
  return $Array(new Array_0(), length, a.typeId$, a.queryId, a.typeName$);
}

function getIntValue(values, index){
  return values[index];
}

function getValueCount(values){
  return values.length;
}

function initDims_0(typeName, typeIdExprs, queryIdExprs, dimExprs, defaultValue){
  return initDims(typeName, typeIdExprs, queryIdExprs, dimExprs, 0, getValueCount(dimExprs), defaultValue);
}

function initDims(typeName, typeIdExprs, queryIdExprs, dimExprs, index, count, defaultValue){
  var i, length, result;
  if ((length = getIntValue(dimExprs, index)) < 0) {
    throw new NegativeArraySizeException();
  }
  result = $Array(new Array_0(), length, getIntValue(typeIdExprs, index), getIntValue(queryIdExprs, index), typeName);
  ++index;
  if (index < count) {
    typeName = $substring(typeName, 1);
    for (i = 0; i < length; ++i) {
      _set(result, i, initDims(typeName, typeIdExprs, queryIdExprs, dimExprs, index, count, defaultValue));
    }
  }
   else {
    for (i = 0; i < length; ++i) {
      _set(result, i, defaultValue);
    }
  }
  return result;
}

function setCheck(array, index, value){
  if (value !== null && array.queryId != 0 && !instanceOf(value, array.queryId)) {
    throw new ArrayStoreException();
  }
  return _set(array, index, value);
}

function Array_0(){
}

_ = Array_0.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_lang_ + 'Array';
_.typeId$ = 16;
function canCast(srcId, dstId){
  return !(!(srcId && typeIdArray[srcId][dstId]));
}

function charToString(x){
  return String.fromCharCode(x);
}

function dynamicCast(src, dstId){
  if (src != null)
    canCast(src.typeId$, dstId) || throwClassCastException();
  return src;
}

function instanceOf(src, dstId){
  return src != null && canCast(src.typeId$, dstId);
}

function narrow_char(x){
  return x & 65535;
}

function narrow_int(x){
  return ~(~x);
}

function round_int(x){
  if (x > ($clinit_116() , MAX_VALUE))
    return $clinit_116() , MAX_VALUE;
  if (x < ($clinit_116() , MIN_VALUE))
    return $clinit_116() , MIN_VALUE;
  return x >= 0?Math.floor(x):Math.ceil(x);
}

function throwClassCastException(){
  throw new ClassCastException();
}

function throwClassCastExceptionUnlessNull(o){
  if (o !== null) {
    throw new ClassCastException();
  }
  return o;
}

function wrapJSO(jso, seed){
  _ = seed.prototype;
  if (jso && !(jso.typeId$ >= _.typeId$)) {
    var oldToString = jso.toString;
    for (var i in _) {
      jso[i] = _[i];
    }
    jso.toString = oldToString;
  }
  return jso;
}

var typeIdArray;
function $Throwable(this$static, message){
  message;
  return this$static;
}

function Throwable(){
}

_ = Throwable.prototype = new Object_0();
_.typeName$ = package_java_lang_ + 'Throwable';
_.typeId$ = 3;
function $Exception(this$static, message){
  $Throwable(this$static, message);
  return this$static;
}

function Exception(){
}

_ = Exception.prototype = new Throwable();
_.typeName$ = package_java_lang_ + 'Exception';
_.typeId$ = 4;
function $RuntimeException(this$static, message){
  $Exception(this$static, message);
  return this$static;
}

function RuntimeException(){
}

_ = RuntimeException.prototype = new Exception();
_.typeName$ = package_java_lang_ + 'RuntimeException';
_.typeId$ = 5;
function $CommandCanceledException(this$static, command){
  return this$static;
}

function CommandCanceledException(){
}

_ = CommandCanceledException.prototype = new RuntimeException();
_.typeName$ = package_com_google_gwt_user_client_ + 'CommandCanceledException';
_.typeId$ = 19;
function $$init_0(this$static){
  this$static.cancellationTimer = $CommandExecutor$1(new CommandExecutor$1(), this$static);
  this$static.commands = $ArrayList(new ArrayList());
  this$static.executionTimer = $CommandExecutor$2(new CommandExecutor$2(), this$static);
  this$static.iterator = $CommandExecutor$CircularIterator(new CommandExecutor$CircularIterator(), this$static);
}

function $CommandExecutor(this$static){
  $$init_0(this$static);
  return this$static;
}

function $doCommandCanceled(this$static){
  var cmd, ex, ueh;
  cmd = $getLast(this$static.iterator);
  $remove(this$static.iterator);
  ex = null;
  if (instanceOf(cmd, 5)) {
    ex = $CommandCanceledException(new CommandCanceledException(), dynamicCast(cmd, 5));
  }
   else {
  }
  if (ex !== null) {
    ueh = sUncaughtExceptionHandler;
  }
  $setExecuting(this$static, false);
  $maybeStartExecutionTimer(this$static);
}

function $doExecuteCommands(this$static, startTimeMillis){
  var command, element, removeCommand, wasCanceled;
  wasCanceled = false;
  try {
    $setExecuting(this$static, true);
    $setEnd(this$static.iterator, this$static.commands.size);
    $schedule(this$static.cancellationTimer, 10000);
    while ($hasNext(this$static.iterator)) {
      element = $next(this$static.iterator);
      removeCommand = true;
      try {
        if (element === null) {
          return;
        }
        if (instanceOf(element, 5)) {
          command = dynamicCast(element, 5);
          command.execute();
        }
         else {
        }
      }
       finally {
        wasCanceled = $wasRemoved(this$static.iterator);
        if (wasCanceled) {
          return;
        }
        if (removeCommand) {
          $remove(this$static.iterator);
        }
      }
      if (hasTimeSliceExpired(currentTimeMillis_0(), startTimeMillis)) {
        return;
      }
    }
  }
   finally {
    if (!wasCanceled) {
      $cancel(this$static.cancellationTimer);
      $setExecuting(this$static, false);
      $maybeStartExecutionTimer(this$static);
    }
  }
}

function $maybeStartExecutionTimer(this$static){
  if (!$isEmpty(this$static.commands) && !this$static.executionTimerPending && !this$static.executing) {
    $setExecutionTimerPending(this$static, true);
    $schedule(this$static.executionTimer, 1);
  }
}

function $setExecuting(this$static, executing){
  this$static.executing = executing;
}

function $setExecutionTimerPending(this$static, pending){
  this$static.executionTimerPending = pending;
}

function $submit(this$static, command){
  $add_9(this$static.commands, command);
  $maybeStartExecutionTimer(this$static);
}

function hasTimeSliceExpired(currentTimeMillis, startTimeMillis){
  return abs(currentTimeMillis - startTimeMillis) >= 100;
}

function CommandExecutor(){
}

_ = CommandExecutor.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ + 'CommandExecutor';
_.typeId$ = 20;
_.executing = false;
_.executionTimerPending = false;
function $clinit_21(){
  $clinit_21 = nullMethod;
  timers = $ArrayList(new ArrayList());
  {
    hookWindowClosing();
  }
}

function $Timer(this$static){
  $clinit_21();
  return this$static;
}

function $cancel(this$static){
  if (this$static.isRepeating) {
    clearInterval(this$static.timerId);
  }
   else {
    clearTimeout(this$static.timerId);
  }
  $remove_8(timers, this$static);
}

function $fireImpl(this$static){
  if (!this$static.isRepeating) {
    $remove_8(timers, this$static);
  }
  this$static.run();
}

function $schedule(this$static, delayMillis){
  if (delayMillis <= 0) {
    throw $IllegalArgumentException(new IllegalArgumentException(), 'must be positive');
  }
  $cancel(this$static);
  this$static.isRepeating = false;
  this$static.timerId = createTimeout(this$static, delayMillis);
  $add_9(timers, this$static);
}

function clearInterval(id){
  $clinit_21();
  $wnd.clearInterval(id);
}

function clearTimeout(id){
  $clinit_21();
  $wnd.clearTimeout(id);
}

function createTimeout(timer, delay){
  $clinit_21();
  return $wnd.setTimeout(function(){
    timer.fire();
  }
  , delay);
}

function fire(){
  var handler;
  handler = sUncaughtExceptionHandler;
  {
    $fireImpl(this);
  }
}

function hookWindowClosing(){
  $clinit_21();
  addWindowCloseListener(new Timer$1());
}

function Timer(){
}

_ = Timer.prototype = new Object_0();
_.fire = fire;
_.typeName$ = package_com_google_gwt_user_client_ + 'Timer';
_.typeId$ = 21;
_.isRepeating = false;
_.timerId = 0;
var timers;
function $clinit_10(){
  $clinit_10 = nullMethod;
  $clinit_21();
}

function $CommandExecutor$1(this$static, this$0){
  $clinit_10();
  this$static.this$0 = this$0;
  $Timer(this$static);
  return this$static;
}

function run(){
  if (!this.this$0.executing) {
    return;
  }
  $doCommandCanceled(this.this$0);
}

function CommandExecutor$1(){
}

_ = CommandExecutor$1.prototype = new Timer();
_.run = run;
_.typeName$ = package_com_google_gwt_user_client_ + 'CommandExecutor$1';
_.typeId$ = 22;
function $clinit_11(){
  $clinit_11 = nullMethod;
  $clinit_21();
}

function $CommandExecutor$2(this$static, this$0){
  $clinit_11();
  this$static.this$0 = this$0;
  $Timer(this$static);
  return this$static;
}

function run_0(){
  $setExecutionTimerPending(this.this$0, false);
  $doExecuteCommands(this.this$0, currentTimeMillis_0());
}

function CommandExecutor$2(){
}

_ = CommandExecutor$2.prototype = new Timer();
_.run = run_0;
_.typeName$ = package_com_google_gwt_user_client_ + 'CommandExecutor$2';
_.typeId$ = 23;
function $CommandExecutor$CircularIterator(this$static, this$0){
  this$static.this$0 = this$0;
  return this$static;
}

function $getLast(this$static){
  return $get_0(this$static.this$0.commands, this$static.last);
}

function $hasNext(this$static){
  return this$static.next < this$static.end;
}

function $next(this$static){
  var command;
  this$static.last = this$static.next;
  command = $get_0(this$static.this$0.commands, this$static.next++);
  if (this$static.next >= this$static.end) {
    this$static.next = 0;
  }
  return command;
}

function $remove(this$static){
  $remove_7(this$static.this$0.commands, this$static.last);
  --this$static.end;
  if (this$static.last <= this$static.next) {
    if (--this$static.next < 0) {
      this$static.next = 0;
    }
  }
  this$static.last = (-1);
}

function $setEnd(this$static, end){
  this$static.end = end;
}

function $wasRemoved(this$static){
  return this$static.last == (-1);
}

function hasNext(){
  return $hasNext(this);
}

function next_0(){
  return $next(this);
}

function CommandExecutor$CircularIterator(){
}

_ = CommandExecutor$CircularIterator.prototype = new Object_0();
_.hasNext = hasNext;
_.next_0 = next_0;
_.typeName$ = package_com_google_gwt_user_client_ + 'CommandExecutor$CircularIterator';
_.typeId$ = 24;
_.end = 0;
_.last = (-1);
_.next = 0;
function $clinit_15(){
  $clinit_15 = nullMethod;
  sEventPreviewStack = $ArrayList(new ArrayList());
  {
    impl = new DOMImplSafari();
    $init(impl);
  }
}

function appendChild(parent, child){
  $clinit_15();
  $appendChild(impl, parent, child);
}

function compare(elem1, elem2){
  $clinit_15();
  return $compare(impl, elem1, elem2);
}

function createDiv(){
  $clinit_15();
  return $createElement(impl, 'div');
}

function createImg(){
  $clinit_15();
  return $createElement(impl, 'img');
}

function createSpan(){
  $clinit_15();
  return $createElement(impl, 'span');
}

function createTBody(){
  $clinit_15();
  return $createElement(impl, 'tbody');
}

function createTD(){
  $clinit_15();
  return $createElement(impl, 'td');
}

function createTR(){
  $clinit_15();
  return $createElement(impl, 'tr');
}

function createTable(){
  $clinit_15();
  return $createElement(impl, 'table');
}

function dispatchEvent(evt, elem, listener){
  $clinit_15();
  var handler;
  handler = sUncaughtExceptionHandler;
  {
    dispatchEventImpl(evt, elem, listener);
  }
}

function dispatchEventImpl(evt, elem, listener){
  $clinit_15();
  var prevCurrentEvent;
  if (elem === sCaptureElem) {
    if (eventGetType(evt) == 8192) {
      sCaptureElem = null;
    }
  }
  prevCurrentEvent = currentEvent;
  currentEvent = evt;
  try {
    listener.onBrowserEvent(evt);
  }
   finally {
    currentEvent = prevCurrentEvent;
  }
}

function eventCancelBubble(evt, cancel){
  $clinit_15();
  $eventCancelBubble(impl, evt, cancel);
}

function eventGetCurrentTarget(evt){
  $clinit_15();
  return $eventGetCurrentTarget(impl, evt);
}

function eventGetFromElement(evt){
  $clinit_15();
  return $eventGetFromElement(impl, evt);
}

function eventGetKeyCode(evt){
  $clinit_15();
  return $eventGetKeyCode(impl, evt);
}

function eventGetTarget(evt){
  $clinit_15();
  return $eventGetTarget(impl, evt);
}

function eventGetToElement(evt){
  $clinit_15();
  return $eventGetToElement(impl, evt);
}

function eventGetType(evt){
  $clinit_15();
  return $eventGetTypeInt(impl, evt);
}

function eventPreventDefault(evt){
  $clinit_15();
  $eventPreventDefault(impl, evt);
}

function getAbsoluteLeft(elem){
  $clinit_15();
  return $getAbsoluteLeft(impl, elem);
}

function getAbsoluteTop(elem){
  $clinit_15();
  return $getAbsoluteTop(impl, elem);
}

function getChild(parent, index){
  $clinit_15();
  return $getChild(impl, parent, index);
}

function getElementById(id){
  $clinit_15();
  return $getElementById(impl, id);
}

function getElementProperty(elem, prop){
  $clinit_15();
  return $getElementProperty(impl, elem, prop);
}

function getElementPropertyBoolean(elem, prop){
  $clinit_15();
  return $getElementPropertyBoolean(impl, elem, prop);
}

function getElementPropertyInt(elem, prop){
  $clinit_15();
  return $getElementPropertyInt(impl, elem, prop);
}

function getEventsSunk(elem){
  $clinit_15();
  return $getEventsSunk(impl, elem);
}

function getFirstChild(elem){
  $clinit_15();
  return $getFirstChild(impl, elem);
}

function getParent(elem){
  $clinit_15();
  return $getParent(impl, elem);
}

function insertChild(parent, child, index){
  $clinit_15();
  $insertChild(impl, parent, child, index);
}

function isOrHasChild(parent, child){
  $clinit_15();
  return $isOrHasChild(impl, parent, child);
}

function previewEvent(evt){
  $clinit_15();
  var preview, ret;
  ret = true;
  if (sEventPreviewStack.size > 0) {
    preview = throwClassCastExceptionUnlessNull($get_0(sEventPreviewStack, sEventPreviewStack.size - 1));
    if (!(ret = null.nullMethod())) {
      eventCancelBubble(evt, true);
      eventPreventDefault(evt);
    }
  }
  return ret;
}

function releaseCapture(elem){
  $clinit_15();
  if (sCaptureElem !== null && compare(elem, sCaptureElem)) {
    sCaptureElem = null;
  }
  $releaseCapture(impl, elem);
}

function removeChild(parent, child){
  $clinit_15();
  $removeChild(impl, parent, child);
}

function scrollIntoView(elem){
  $clinit_15();
  $scrollIntoView(impl, elem);
}

function setCapture(elem){
  $clinit_15();
  sCaptureElem = elem;
  $setCapture(impl, elem);
}

function setElementProperty(elem, prop, value){
  $clinit_15();
  $setElementProperty(impl, elem, prop, value);
}

function setElementPropertyInt(elem, prop, value){
  $clinit_15();
  $setElementPropertyInt(impl, elem, prop, value);
}

function setEventListener(elem, listener){
  $clinit_15();
  $setEventListener(impl, elem, listener);
}

function setImgSrc(img, src){
  $clinit_15();
  $setImgSrc(impl, img, src);
}

function setInnerHTML(elem, html){
  $clinit_15();
  $setInnerHTML(impl, elem, html);
}

function setInnerText(elem, text){
  $clinit_15();
  $setInnerText(impl, elem, text);
}

function setIntStyleAttribute(elem, attr, value){
  $clinit_15();
  $setIntStyleAttribute(impl, elem, attr, value);
}

function setStyleAttribute(elem, attr, value){
  $clinit_15();
  $setStyleAttribute(impl, elem, attr, value);
}

function sinkEvents(elem, eventBits){
  $clinit_15();
  $sinkEvents(impl, elem, eventBits);
}

function windowGetClientHeight(){
  $clinit_15();
  return $windowGetClientHeight(impl);
}

function windowGetClientWidth(){
  $clinit_15();
  return $windowGetClientWidth(impl);
}

var currentEvent = null, impl = null, sCaptureElem = null, sEventPreviewStack;
function $clinit_16(){
  $clinit_16 = nullMethod;
  commandExecutor = $CommandExecutor(new CommandExecutor());
}

function addCommand(cmd){
  $clinit_16();
  if (cmd === null) {
    throw $NullPointerException(new NullPointerException(), 'cmd can not be null');
  }
  $submit(commandExecutor, cmd);
}

var commandExecutor;
function $equals_0(this$static, other){
  if (instanceOf(other, 6)) {
    return compare(this$static, dynamicCast(other, 6));
  }
  return $equals(wrapJSO(this$static, Element), other);
}

function equals_0(other){
  return $equals_0(this, other);
}

function hashCode_1(){
  return $hashCode(wrapJSO(this, Element));
}

function Element(){
}

_ = Element.prototype = new JavaScriptObject();
_.equals$ = equals_0;
_.hashCode$ = hashCode_1;
_.typeName$ = package_com_google_gwt_user_client_ + 'Element';
_.typeId$ = 25;
function equals_1(other){
  return $equals(wrapJSO(this, Event), other);
}

function hashCode_2(){
  return $hashCode(wrapJSO(this, Event));
}

function Event(){
}

_ = Event.prototype = new JavaScriptObject();
_.equals$ = equals_1;
_.hashCode$ = hashCode_2;
_.typeName$ = package_com_google_gwt_user_client_ + 'Event';
_.typeId$ = 26;
function onWindowClosed(){
  while (($clinit_21() , timers).size > 0) {
    $cancel(dynamicCast($get_0(($clinit_21() , timers), 0), 7));
  }
}

function onWindowClosing(){
  return null;
}

function Timer$1(){
}

_ = Timer$1.prototype = new Object_0();
_.onWindowClosed = onWindowClosed;
_.onWindowClosing = onWindowClosing;
_.typeName$ = package_com_google_gwt_user_client_ + 'Timer$1';
_.typeId$ = 27;
function $clinit_24(){
  $clinit_24 = nullMethod;
  closingListeners = $ArrayList(new ArrayList());
  resizeListeners = $ArrayList(new ArrayList());
  {
    init();
  }
}

function addWindowCloseListener(listener){
  $clinit_24();
  $add_9(closingListeners, listener);
}

function addWindowResizeListener(listener){
  $clinit_24();
  $add_9(resizeListeners, listener);
}

function fireClosedImpl(){
  $clinit_24();
  var it, listener;
  for (it = $iterator_1(closingListeners); $hasNext_1(it);) {
    listener = dynamicCast($next_1(it), 8);
    listener.onWindowClosed();
  }
}

function fireClosingImpl(){
  $clinit_24();
  var it, listener, msg, ret;
  ret = null;
  for (it = $iterator_1(closingListeners); $hasNext_1(it);) {
    listener = dynamicCast($next_1(it), 8);
    msg = listener.onWindowClosing();
    {
      ret = msg;
    }
  }
  return ret;
}

function fireResizedImpl(){
  $clinit_24();
  var it, listener;
  for (it = $iterator_1(resizeListeners); $hasNext_1(it);) {
    listener = dynamicCast($next_1(it), 9);
    listener.onWindowResized(getClientWidth(), getClientHeight());
  }
}

function getClientHeight(){
  $clinit_24();
  return windowGetClientHeight();
}

function getClientWidth(){
  $clinit_24();
  return windowGetClientWidth();
}

function init(){
  $clinit_24();
  __gwt_initHandlers(function(){
    onResize();
  }
  , function(){
    return onClosing();
  }
  , function(){
    onClosed();
    $wnd.onresize = null;
    $wnd.onbeforeclose = null;
    $wnd.onclose = null;
  }
  );
}

function onClosed(){
  $clinit_24();
  var handler;
  handler = sUncaughtExceptionHandler;
  {
    fireClosedImpl();
  }
}

function onClosing(){
  $clinit_24();
  var handler;
  handler = sUncaughtExceptionHandler;
  {
    return fireClosingImpl();
  }
}

function onResize(){
  $clinit_24();
  var handler;
  handler = sUncaughtExceptionHandler;
  {
    fireResizedImpl();
  }
}

var closingListeners, resizeListeners;
function $appendChild(this$static, parent, child){
  parent.appendChild(child);
}

function $createElement(this$static, tag){
  return $doc.createElement(tag);
}

function $eventCancelBubble(this$static, evt, cancel){
  evt.cancelBubble = cancel;
}

function $eventGetCurrentTarget(this$static, evt){
  return evt.currentTarget;
}

function $eventGetKeyCode(this$static, evt){
  return evt.which || (evt.keyCode || -1);
}

function $eventGetTypeInt(this$static, evt){
  switch (evt.type) {
    case 'blur':
      return 4096;
    case 'change':
      return 1024;
    case 'click':
      return 1;
    case 'dblclick':
      return 2;
    case 'focus':
      return 2048;
    case 'keydown':
      return 128;
    case 'keypress':
      return 256;
    case 'keyup':
      return 512;
    case 'load':
      return 32768;
    case 'losecapture':
      return 8192;
    case 'mousedown':
      return 4;
    case 'mousemove':
      return 64;
    case 'mouseout':
      return 32;
    case 'mouseover':
      return 16;
    case 'mouseup':
      return 8;
    case 'scroll':
      return 16384;
    case 'error':
      return 65536;
    case 'mousewheel':
      return 131072;
    case 'DOMMouseScroll':
      return 131072;
  }
}

function $getElementById(this$static, id){
  var elem = $doc.getElementById(id);
  return elem || null;
}

function $getElementProperty(this$static, elem, prop){
  var ret = elem[prop];
  return ret == null?null:String(ret);
}

function $getElementPropertyBoolean(this$static, elem, prop){
  return !(!elem[prop]);
}

function $getElementPropertyInt(this$static, elem, prop){
  var i = parseInt(elem[prop]);
  if (!i) {
    return 0;
  }
  return i;
}

function $getEventsSunk(this$static, elem){
  return elem.__eventBits || 0;
}

function $removeChild(this$static, parent, child){
  parent.removeChild(child);
}

function $scrollIntoView(this$static, elem){
  var left = elem.offsetLeft, top = elem.offsetTop;
  var width = elem.offsetWidth, height = elem.offsetHeight;
  if (elem.parentNode != elem.offsetParent) {
    left -= elem.parentNode.offsetLeft;
    top -= elem.parentNode.offsetTop;
  }
  var cur = elem.parentNode;
  while (cur && cur.nodeType == 1) {
    if (cur.style.overflow == 'auto' || (cur.style.overflow == 'scroll' || cur.tagName == 'BODY')) {
      if (left < cur.scrollLeft) {
        cur.scrollLeft = left;
      }
      if (left + width > cur.scrollLeft + cur.clientWidth) {
        cur.scrollLeft = left + width - cur.clientWidth;
      }
      if (top < cur.scrollTop) {
        cur.scrollTop = top;
      }
      if (top + height > cur.scrollTop + cur.clientHeight) {
        cur.scrollTop = top + height - cur.clientHeight;
      }
    }
    var offsetLeft = cur.offsetLeft, offsetTop = cur.offsetTop;
    if (cur.parentNode != cur.offsetParent) {
      offsetLeft -= cur.parentNode.offsetLeft;
      offsetTop -= cur.parentNode.offsetTop;
    }
    left += offsetLeft - cur.scrollLeft;
    top += offsetTop - cur.scrollTop;
    cur = cur.parentNode;
  }
}

function $setElementProperty(this$static, elem, prop, value){
  elem[prop] = value;
}

function $setElementPropertyInt(this$static, elem, prop, value){
  elem[prop] = value;
}

function $setEventListener(this$static, elem, listener){
  elem.__listener = listener;
}

function $setImgSrc(this$static, img, src){
  img.src = src;
}

function $setInnerHTML(this$static, elem, html){
  if (!html) {
    html = '';
  }
  elem.innerHTML = html;
}

function $setInnerText(this$static, elem, text){
  while (elem.firstChild) {
    elem.removeChild(elem.firstChild);
  }
  if (text != null) {
    elem.appendChild($doc.createTextNode(text));
  }
}

function $setIntStyleAttribute(this$static, elem, attr, value){
  elem.style[attr] = value;
}

function $setStyleAttribute(this$static, elem, attr, value){
  elem.style[attr] = value;
}

function DOMImpl(){
}

_ = DOMImpl.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_impl_ + 'DOMImpl';
_.typeId$ = 28;
function $compare(this$static, elem1, elem2){
  return elem1 == elem2;
}

function $eventGetFromElement(this$static, evt){
  return evt.relatedTarget?evt.relatedTarget:null;
}

function $eventGetTarget(this$static, evt){
  return evt.target || null;
}

function $eventGetToElement(this$static, evt){
  return evt.relatedTarget || null;
}

function $eventPreventDefault(this$static, evt){
  evt.preventDefault();
}

function $getChild(this$static, elem, index){
  var count = 0, child = elem.firstChild;
  while (child) {
    var next = child.nextSibling;
    if (child.nodeType == 1) {
      if (index == count)
        return child;
      ++count;
    }
    child = next;
  }
  return null;
}

function $getFirstChild(this$static, elem){
  var child = elem.firstChild;
  while (child && child.nodeType != 1)
    child = child.nextSibling;
  return child || null;
}

function $getParent(this$static, elem){
  var parent = elem.parentNode;
  if (parent == null) {
    return null;
  }
  if (parent.nodeType != 1)
    parent = null;
  return parent || null;
}

function $init(this$static){
  $wnd.__dispatchCapturedMouseEvent = function(evt){
    if ($wnd.__dispatchCapturedEvent(evt)) {
      var cap = $wnd.__captureElem;
      if (cap && cap.__listener) {
        dispatchEvent(evt, cap, cap.__listener);
        evt.stopPropagation();
      }
    }
  }
  ;
  $wnd.__dispatchCapturedEvent = function(evt){
    if (!previewEvent(evt)) {
      evt.stopPropagation();
      evt.preventDefault();
      return false;
    }
    return true;
  }
  ;
  $wnd.addEventListener('click', $wnd.__dispatchCapturedMouseEvent, true);
  $wnd.addEventListener('dblclick', $wnd.__dispatchCapturedMouseEvent, true);
  $wnd.addEventListener('mousedown', $wnd.__dispatchCapturedMouseEvent, true);
  $wnd.addEventListener('mouseup', $wnd.__dispatchCapturedMouseEvent, true);
  $wnd.addEventListener('mousemove', $wnd.__dispatchCapturedMouseEvent, true);
  $wnd.addEventListener('mousewheel', $wnd.__dispatchCapturedMouseEvent, true);
  $wnd.addEventListener('keydown', $wnd.__dispatchCapturedEvent, true);
  $wnd.addEventListener('keyup', $wnd.__dispatchCapturedEvent, true);
  $wnd.addEventListener('keypress', $wnd.__dispatchCapturedEvent, true);
  $wnd.__dispatchEvent = function(evt){
    var listener, curElem = this;
    while (curElem && !(listener = curElem.__listener))
      curElem = curElem.parentNode;
    if (curElem && curElem.nodeType != 1)
      curElem = null;
    if (listener)
      dispatchEvent(evt, curElem, listener);
  }
  ;
  $wnd.__captureElem = null;
}

function $insertChild(this$static, parent, toAdd, index){
  var count = 0, child = parent.firstChild, before = null;
  while (child) {
    if (child.nodeType == 1) {
      if (count == index) {
        before = child;
        break;
      }
      ++count;
    }
    child = child.nextSibling;
  }
  parent.insertBefore(toAdd, before);
}

function $isOrHasChild(this$static, parent, child){
  while (child) {
    if (parent == child) {
      return true;
    }
    child = child.parentNode;
    if (child && child.nodeType != 1) {
      child = null;
    }
  }
  return false;
}

function $releaseCapture(this$static, elem){
  if (elem == $wnd.__captureElem)
    $wnd.__captureElem = null;
}

function $setCapture(this$static, elem){
  $wnd.__captureElem = elem;
}

function $sinkEvents(this$static, elem, bits){
  elem.__eventBits = bits;
  elem.onclick = bits & 1?$wnd.__dispatchEvent:null;
  elem.ondblclick = bits & 2?$wnd.__dispatchEvent:null;
  elem.onmousedown = bits & 4?$wnd.__dispatchEvent:null;
  elem.onmouseup = bits & 8?$wnd.__dispatchEvent:null;
  elem.onmouseover = bits & 16?$wnd.__dispatchEvent:null;
  elem.onmouseout = bits & 32?$wnd.__dispatchEvent:null;
  elem.onmousemove = bits & 64?$wnd.__dispatchEvent:null;
  elem.onkeydown = bits & 128?$wnd.__dispatchEvent:null;
  elem.onkeypress = bits & 256?$wnd.__dispatchEvent:null;
  elem.onkeyup = bits & 512?$wnd.__dispatchEvent:null;
  elem.onchange = bits & 1024?$wnd.__dispatchEvent:null;
  elem.onfocus = bits & 2048?$wnd.__dispatchEvent:null;
  elem.onblur = bits & 4096?$wnd.__dispatchEvent:null;
  elem.onlosecapture = bits & 8192?$wnd.__dispatchEvent:null;
  elem.onscroll = bits & 16384?$wnd.__dispatchEvent:null;
  elem.onload = bits & 32768?$wnd.__dispatchEvent:null;
  elem.onerror = bits & 65536?$wnd.__dispatchEvent:null;
  elem.onmousewheel = bits & 131072?$wnd.__dispatchEvent:null;
}

function DOMImplStandard(){
}

_ = DOMImplStandard.prototype = new DOMImpl();
_.typeName$ = package_com_google_gwt_user_client_impl_ + 'DOMImplStandard';
_.typeId$ = 29;
function $getAbsoluteLeft(this$static, elem){
  if (elem.offsetLeft == null) {
    return 0;
  }
  var left = 0;
  var curr = elem.parentNode;
  if (curr) {
    while (curr.offsetParent) {
      left -= curr.scrollLeft;
      curr = curr.parentNode;
    }
  }
  while (elem) {
    left += elem.offsetLeft;
    var parent = elem.offsetParent;
    if (parent && (parent.tagName == 'BODY' && elem.style.position == 'absolute')) {
      break;
    }
    elem = parent;
  }
  return left;
}

function $getAbsoluteTop(this$static, elem){
  if (elem.offsetTop == null) {
    return 0;
  }
  var top = 0;
  var curr = elem.parentNode;
  if (curr) {
    while (curr.offsetParent) {
      top -= curr.scrollTop;
      curr = curr.parentNode;
    }
  }
  while (elem) {
    top += elem.offsetTop;
    var parent = elem.offsetParent;
    if (parent && (parent.tagName == 'BODY' && elem.style.position == 'absolute')) {
      break;
    }
    elem = parent;
  }
  return top;
}

function $windowGetClientHeight(this$static){
  return $wnd.innerHeight;
}

function $windowGetClientWidth(this$static){
  return $wnd.innerWidth;
}

function DOMImplSafari(){
}

_ = DOMImplSafari.prototype = new DOMImplStandard();
_.typeName$ = package_com_google_gwt_user_client_impl_ + 'DOMImplSafari';
_.typeId$ = 30;
function $AbsolutePanel(this$static){
  $ComplexPanel(this$static);
  this$static.setElement(createDiv());
  setStyleAttribute(this$static.getElement(), 'position', 'relative');
  setStyleAttribute(this$static.getElement(), 'overflow', 'hidden');
  return this$static;
}

function $add(this$static, w){
  $add_1(this$static, w, this$static.getElement());
}

function $add_0(this$static, w, left, top){
  $removeFromParent(w);
  $setWidgetPositionImpl(this$static, w, left, top);
  $add(this$static, w);
}

function $setWidgetPositionImpl(this$static, w, left, top){
  var h;
  h = w.getElement();
  if (left == (-1) && top == (-1)) {
    changeToStaticPositioning(h);
  }
   else {
    setStyleAttribute(h, 'position', 'absolute');
    setStyleAttribute(h, 'left', left + 'px');
    setStyleAttribute(h, 'top', top + 'px');
  }
}

function changeToStaticPositioning(elem){
  setStyleAttribute(elem, 'left', '');
  setStyleAttribute(elem, 'top', '');
  setStyleAttribute(elem, 'position', '');
}

function remove_0(w){
  var removed;
  removed = $remove_0(this, w);
  if (removed) {
    changeToStaticPositioning(w.getElement());
  }
  return removed;
}

function AbsolutePanel(){
}

_ = AbsolutePanel.prototype = new ComplexPanel();
_.remove_0 = remove_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'AbsolutePanel';
_.typeId$ = 31;
function AbstractImagePrototype(){
}

_ = AbstractImagePrototype.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'AbstractImagePrototype';
_.typeId$ = 32;
function $clinit_42(){
  $clinit_42 = nullMethod;
  $clinit_95() , implWidget;
}

function $FocusWidget(this$static, elem){
  $clinit_95() , implWidget;
  $setElement(this$static, elem);
  return this$static;
}

function $addClickListener(this$static, listener){
  if (this$static.clickListeners === null) {
    this$static.clickListeners = $ClickListenerCollection(new ClickListenerCollection());
  }
  $add_9(this$static.clickListeners, listener);
}

function $fireClickListeners(this$static){
  if (this$static.clickListeners !== null) {
    $fireClick(this$static.clickListeners, this$static);
  }
}

function $isEnabled(this$static){
  return !getElementPropertyBoolean(this$static.getElement(), 'disabled');
}

function $onBrowserEvent(this$static, event_0){
  switch (eventGetType(event_0)) {
    case 1:
      if (this$static.clickListeners !== null) {
        $fireClick(this$static.clickListeners, this$static);
      }

      break;
    case 4096:
    case 2048:
      break;
    case 128:
    case 512:
    case 256:
      break;
  }
}

function $setElement(this$static, elem){
  $setElement_1(this$static, elem);
  $sinkEvents_0(this$static, 7041);
}

function onBrowserEvent_0(event_0){
  $onBrowserEvent(this, event_0);
}

function setElement(elem){
  $setElement(this, elem);
}

function FocusWidget(){
}

_ = FocusWidget.prototype = new Widget();
_.onBrowserEvent = onBrowserEvent_0;
_.setElement = setElement;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'FocusWidget';
_.typeId$ = 33;
_.clickListeners = null;
function $clinit_30(){
  $clinit_30 = nullMethod;
  $clinit_95() , implWidget;
}

function $ButtonBase(this$static, elem){
  $clinit_95() , implWidget;
  $FocusWidget(this$static, elem);
  return this$static;
}

function ButtonBase(){
}

_ = ButtonBase.prototype = new FocusWidget();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'ButtonBase';
_.typeId$ = 34;
function $CellPanel(this$static){
  $ComplexPanel(this$static);
  this$static.table = createTable();
  this$static.body_0 = createTBody();
  appendChild(this$static.table, this$static.body_0);
  this$static.setElement(this$static.table);
  return this$static;
}

function $setCellHorizontalAlignment(this$static, td, align){
  setElementProperty(td, 'align', align.textAlignString);
}

function $setCellVerticalAlignment(this$static, td, align){
  setStyleAttribute(td, 'verticalAlign', align.verticalAlignString);
}

function CellPanel(){
}

_ = CellPanel.prototype = new ComplexPanel();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'CellPanel';
_.typeId$ = 35;
_.body_0 = null;
_.table = null;
function $advanceToFind(this$static, iter, o){
  var t;
  while (iter.hasNext()) {
    t = iter.next_0();
    if (o === null?t === null:o.equals$(t)) {
      return iter;
    }
  }
  return null;
}

function add_0(o){
  throw $UnsupportedOperationException(new UnsupportedOperationException(), 'add');
}

function contains(o){
  var iter;
  iter = $advanceToFind(this, this.iterator_0(), o);
  return iter !== null;
}

function toArray(a){
  var i, it, size;
  size = this.size_0();
  if (a.length_0 < size) {
    a = clonify_0(a, size);
  }
  i = 0;
  for (it = this.iterator_0(); it.hasNext();) {
    setCheck(a, i++, it.next_0());
  }
  if (a.length_0 > size) {
    setCheck(a, size, null);
  }
  return a;
}

function AbstractCollection(){
}

_ = AbstractCollection.prototype = new Object_0();
_.add_0 = add_0;
_.contains = contains;
_.toArray = toArray;
_.typeName$ = package_java_util_ + 'AbstractCollection';
_.typeId$ = 36;
function $indexOutOfBounds(this$static, i){
  throw $IndexOutOfBoundsException(new IndexOutOfBoundsException(), 'Index: ' + i + ', Size: ' + this$static.size);
}

function $iterator_1(this$static){
  return $AbstractList$IteratorImpl(new AbstractList$IteratorImpl(), this$static);
}

function add_1(index, element){
  throw $UnsupportedOperationException(new UnsupportedOperationException(), 'add');
}

function add_2(obj){
  this.add(this.size_0(), obj);
  return true;
}

function equals_4(o){
  var elem, elemOther, iter, iterOther, other;
  if (o === this) {
    return true;
  }
  if (!instanceOf(o, 18)) {
    return false;
  }
  other = dynamicCast(o, 18);
  if (this.size_0() != other.size_0()) {
    return false;
  }
  iter = $iterator_1(this);
  iterOther = other.iterator_0();
  while ($hasNext_1(iter)) {
    elem = $next_1(iter);
    elemOther = $next_1(iterOther);
    if (!(elem === null?elemOther === null:elem.equals$(elemOther))) {
      return false;
    }
  }
  return true;
}

function hashCode_5(){
  var coeff, iter, k, obj;
  k = 1;
  coeff = 31;
  iter = $iterator_1(this);
  while ($hasNext_1(iter)) {
    obj = $next_1(iter);
    k = 31 * k + (obj === null?0:obj.hashCode$());
  }
  return k;
}

function iterator_2(){
  return $iterator_1(this);
}

function remove_6(index){
  throw $UnsupportedOperationException(new UnsupportedOperationException(), 'remove');
}

function AbstractList(){
}

_ = AbstractList.prototype = new AbstractCollection();
_.add = add_1;
_.add_0 = add_2;
_.equals$ = equals_4;
_.hashCode$ = hashCode_5;
_.iterator_0 = iterator_2;
_.remove = remove_6;
_.typeName$ = package_java_util_ + 'AbstractList';
_.typeId$ = 37;
function $$init_12(this$static){
  {
    $clearImpl(this$static);
  }
}

function $ArrayList(this$static){
  $$init_12(this$static);
  return this$static;
}

function $add_8(this$static, index, o){
  if (index < 0 || index > this$static.size) {
    $indexOutOfBounds(this$static, index);
  }
  addImpl(this$static.array, index, o);
  ++this$static.size;
}

function $add_9(this$static, o){
  setImpl(this$static.array, this$static.size++, o);
  return true;
}

function $clearImpl(this$static){
  this$static.array = createArray();
  this$static.size = 0;
}

function $contains(this$static, o){
  return $indexOf_3(this$static, o) != (-1);
}

function $get_0(this$static, index){
  if (index < 0 || index >= this$static.size) {
    $indexOutOfBounds(this$static, index);
  }
  return getImpl(this$static.array, index);
}

function $indexOf_3(this$static, o){
  return $indexOf_4(this$static, o, 0);
}

function $indexOf_4(this$static, o, index){
  if (index < 0) {
    $indexOutOfBounds(this$static, index);
  }
  for (; index < this$static.size; ++index) {
    if (equals_7(o, getImpl(this$static.array, index))) {
      return index;
    }
  }
  return (-1);
}

function $isEmpty(this$static){
  return this$static.size == 0;
}

function $remove_7(this$static, index){
  var previous;
  previous = $get_0(this$static, index);
  removeRangeImpl(this$static.array, index, 1);
  --this$static.size;
  return previous;
}

function $remove_8(this$static, o){
  var i;
  i = $indexOf_3(this$static, o);
  if (i == (-1)) {
    return false;
  }
  $remove_7(this$static, i);
  return true;
}

function add_3(index, o){
  $add_8(this, index, o);
}

function add_4(o){
  return $add_9(this, o);
}

function addImpl(array, index, o){
  array.splice(index, 0, o);
}

function contains_2(o){
  return $contains(this, o);
}

function equals_7(a, b){
  return a === b || a !== null && a.equals$(b);
}

function get_1(index){
  return $get_0(this, index);
}

function getImpl(array, index){
  return array[index];
}

function remove_7(index){
  return $remove_7(this, index);
}

function removeRangeImpl(array, index, count){
  array.splice(index, count);
}

function setImpl(array, index, o){
  array[index] = o;
}

function size_2(){
  return this.size;
}

function toArray_0(a){
  var i;
  if (a.length_0 < this.size) {
    a = clonify_0(a, this.size);
  }
  for (i = 0; i < this.size; ++i) {
    setCheck(a, i, getImpl(this.array, i));
  }
  if (a.length_0 > this.size) {
    setCheck(a, this.size, null);
  }
  return a;
}

function ArrayList(){
}

_ = ArrayList.prototype = new AbstractList();
_.add = add_3;
_.add_0 = add_4;
_.contains = contains_2;
_.get = get_1;
_.remove = remove_7;
_.size_0 = size_2;
_.toArray = toArray_0;
_.typeName$ = package_java_util_ + 'ArrayList';
_.typeId$ = 38;
_.array = null;
_.size = 0;
function $ChangeListenerCollection(this$static){
  $ArrayList(this$static);
  return this$static;
}

function $fireChange(this$static, sender){
  var it, listener;
  for (it = $iterator_1(this$static); $hasNext_1(it);) {
    listener = throwClassCastExceptionUnlessNull($next_1(it));
    null.nullMethod();
  }
}

function ChangeListenerCollection(){
}

_ = ChangeListenerCollection.prototype = new ArrayList();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'ChangeListenerCollection';
_.typeId$ = 39;
function $ClickListenerCollection(this$static){
  $ArrayList(this$static);
  return this$static;
}

function $fireClick(this$static, sender){
  var it, listener;
  for (it = $iterator_1(this$static); $hasNext_1(it);) {
    listener = dynamicCast($next_1(it), 10);
    listener.onClick_0(sender);
  }
}

function ClickListenerCollection(){
}

_ = ClickListenerCollection.prototype = new ArrayList();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'ClickListenerCollection';
_.typeId$ = 40;
function $getElement(this$static){
  if (this$static.widget === null) {
    throw $IllegalStateException(new IllegalStateException(), 'initWidget() was never called in ' + getTypeName(this$static));
  }
  return this$static.element;
}

function $initWidget(this$static, widget){
  if (this$static.widget !== null) {
    throw $IllegalStateException(new IllegalStateException(), 'Composite.initWidget() may only be called once.');
  }
  $removeFromParent(widget);
  this$static.setElement(widget.getElement());
  this$static.widget = widget;
  $setParent(widget, this$static);
}

function $isAttached(this$static){
  if (this$static.widget !== null) {
    return this$static.widget.isAttached();
  }
  return false;
}

function getElement(){
  return $getElement(this);
}

function isAttached(){
  return $isAttached(this);
}

function onAttach(){
  this.widget.onAttach();
  this.onLoad();
}

function onDetach(){
  try {
    this.onUnload();
  }
   finally {
    this.widget.onDetach();
  }
}

function Composite(){
}

_ = Composite.prototype = new Widget();
_.getElement = getElement;
_.isAttached = isAttached;
_.onAttach = onAttach;
_.onDetach = onDetach;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Composite';
_.typeId$ = 41;
_.widget = null;
function $clinit_39(){
  $clinit_39 = nullMethod;
  $clinit_95() , implWidget;
}

function $CustomButton_0(this$static, upImage){
  $clinit_95() , implWidget;
  $CustomButton(this$static);
  $setImage(this$static.up, upImage);
  return this$static;
}

function $CustomButton_1(this$static, upImage, downImage){
  $clinit_95() , implWidget;
  $CustomButton_0(this$static, upImage);
  $setImage($getDownFace(this$static), downImage);
  return this$static;
}

function $CustomButton(this$static){
  $clinit_95() , implWidget;
  $ButtonBase(this$static, $createFocusable(($clinit_41() , impl_0)));
  $sinkEvents_0(this$static, 6269);
  $setUpFace(this$static, $createFace(this$static, null, 'up', 0));
  $setStyleName(this$static, 'gwt-CustomButton');
  return this$static;
}

function $cleanupCaptureState(this$static){
  if (this$static.isCapturing || this$static.isFocusing) {
    releaseCapture(this$static.getElement());
    this$static.isCapturing = false;
    this$static.isFocusing = false;
  }
}

function $createFace(this$static, delegateTo, name, faceID){
  return $CustomButton$1(new CustomButton$1(), delegateTo, this$static, name, faceID);
}

function $finishSetup(this$static){
  if (this$static.curFace === null) {
    $setCurrentFace_0(this$static, this$static.up);
  }
}

function $getCurrentFace(this$static){
  $finishSetup(this$static);
  return this$static.curFace;
}

function $getDownDisabledFace(this$static){
  if (this$static.downDisabled === null) {
    $setDownDisabledFace(this$static, $createFace(this$static, $getDownFace(this$static), 'down-disabled', 5));
  }
  return this$static.downDisabled;
}

function $getDownFace(this$static){
  if (this$static.down === null) {
    $setDownFace(this$static, $createFace(this$static, this$static.up, 'down', 1));
  }
  return this$static.down;
}

function $getDownHoveringFace(this$static){
  if (this$static.downHovering === null) {
    $setDownHoveringFace(this$static, $createFace(this$static, $getDownFace(this$static), 'down-hovering', 3));
  }
  return this$static.downHovering;
}

function $getFaceFromID(this$static, id){
  switch (id) {
    case 1:
      return $getDownFace(this$static);
    case 0:
      return this$static.up;
    case 3:
      return $getDownHoveringFace(this$static);
    case 2:
      return $getUpHoveringFace(this$static);
    case 4:
      return $getUpDisabledFace(this$static);
    case 5:
      return $getDownDisabledFace(this$static);
    default:throw $IllegalStateException(new IllegalStateException(), id + ' is not a known face id.');
  }
}

function $getUpDisabledFace(this$static){
  if (this$static.upDisabled === null) {
    $setUpDisabledFace(this$static, $createFace(this$static, this$static.up, 'up-disabled', 4));
  }
  return this$static.upDisabled;
}

function $getUpHoveringFace(this$static){
  if (this$static.upHovering === null) {
    $setUpHoveringFace(this$static, $createFace(this$static, this$static.up, 'up-hovering', 2));
  }
  return this$static.upHovering;
}

function $isDown(this$static){
  return (1 & $getCurrentFace(this$static).val$faceID) > 0;
}

function $isHovering(this$static){
  return (2 & $getCurrentFace(this$static).val$faceID) > 0;
}

function $onClick(this$static){
  $fireClickListeners(this$static);
}

function $setCurrentFace_0(this$static, newFace){
  if (this$static.curFace !== newFace) {
    if (this$static.curFace !== null) {
      $removeStyleDependentName(this$static, this$static.curFace.val$name);
    }
    this$static.curFace = newFace;
    $setCurrentFaceElement(this$static, $getFace(newFace));
    $addStyleDependentName(this$static, this$static.curFace.val$name);
  }
}

function $setCurrentFace(this$static, faceID){
  var newFace;
  newFace = $getFaceFromID(this$static, faceID);
  $setCurrentFace_0(this$static, newFace);
}

function $setCurrentFaceElement(this$static, newFaceElement){
  if (this$static.curFaceElement !== newFaceElement) {
    if (this$static.curFaceElement !== null) {
      removeChild(this$static.getElement(), this$static.curFaceElement);
    }
    this$static.curFaceElement = newFaceElement;
    appendChild(this$static.getElement(), this$static.curFaceElement);
  }
}

function $setDown(this$static, down){
  if (down != $isDown_0(this$static)) {
    $toggleDown(this$static);
  }
}

function $setDownDisabledFace(this$static, downDisabled){
  this$static.downDisabled = downDisabled;
}

function $setDownFace(this$static, down){
  this$static.down = down;
}

function $setDownHoveringFace(this$static, downHovering){
  this$static.downHovering = downHovering;
}

function $setFocus(this$static, focused){
  if (focused) {
    $focus(($clinit_41() , impl_0), this$static.getElement());
  }
   else {
    $blur(($clinit_41() , impl_0), this$static.getElement());
  }
}

function $setHovering(this$static, hovering){
  if (hovering != $isHovering(this$static)) {
    $toggleHover(this$static);
  }
}

function $setUpDisabledFace(this$static, upDisabled){
  this$static.upDisabled = upDisabled;
}

function $setUpFace(this$static, up){
  this$static.up = up;
}

function $setUpHoveringFace(this$static, upHovering){
  this$static.upHovering = upHovering;
}

function $toggleDown(this$static){
  var newFaceID;
  newFaceID = $getCurrentFace(this$static).val$faceID ^ 1;
  $setCurrentFace(this$static, newFaceID);
}

function $toggleHover(this$static){
  var newFaceID;
  newFaceID = $getCurrentFace(this$static).val$faceID ^ 2;
  newFaceID &= (-5);
  $setCurrentFace(this$static, newFaceID);
}

function onAttach_0(){
  $finishSetup(this);
  $onAttach(this);
}

function onBrowserEvent(event_0){
  var keyCode, type;
  if ($isEnabled(this) == false) {
    return;
  }
  type = eventGetType(event_0);
  switch (type) {
    case 4:
      $setFocus(this, true);
      setCapture(this.getElement());
      this.isCapturing = true;
      eventPreventDefault(event_0);
      break;
    case 8:
      if (this.isCapturing) {
        this.isCapturing = false;
        releaseCapture(this.getElement());
        if ($isHovering(this)) {
          this.onClick();
        }
      }

      break;
    case 64:
      if (this.isCapturing) {
        eventPreventDefault(event_0);
      }

      break;
    case 32:
      if (isOrHasChild(this.getElement(), eventGetTarget(event_0)) && !isOrHasChild(this.getElement(), eventGetToElement(event_0))) {
        $setHovering(this, false);
      }

      break;
    case 16:
      if (isOrHasChild(this.getElement(), eventGetTarget(event_0))) {
        $setHovering(this, true);
      }

      break;
    case 1:
      return;
    case 4096:
      if (this.isFocusing) {
        this.isFocusing = false;
      }

      break;
    case 8192:
      if (this.isCapturing) {
        this.isCapturing = false;
      }

      break;
  }
  $onBrowserEvent(this, event_0);
  keyCode = narrow_char(eventGetKeyCode(event_0));
  switch (type) {
    case 128:
      if (keyCode == 32) {
        this.isFocusing = true;
      }

      break;
    case 512:
      if (this.isFocusing && keyCode == 32) {
        this.isFocusing = false;
        this.onClick();
      }

      break;
    case 256:
      if (keyCode == 10 || keyCode == 13) {
        this.onClick();
      }

      break;
  }
}

function onClick(){
  $onClick(this);
}

function onDetach_0(){
  $onDetach(this);
  $cleanupCaptureState(this);
}

function CustomButton(){
}

_ = CustomButton.prototype = new ButtonBase();
_.onAttach = onAttach_0;
_.onBrowserEvent = onBrowserEvent;
_.onClick = onClick;
_.onDetach = onDetach_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'CustomButton';
_.typeId$ = 42;
_.curFace = null;
_.curFaceElement = null;
_.down = null;
_.downDisabled = null;
_.downHovering = null;
_.isCapturing = false;
_.isFocusing = false;
_.up = null;
_.upDisabled = null;
_.upHovering = null;
function $CustomButton$Face(this$static, delegateTo, this$0){
  this$static.this$0 = this$0;
  this$static.delegateTo = delegateTo;
  return this$static;
}

function $getFace(this$static){
  if (this$static.face === null) {
    if (this$static.delegateTo === null) {
      this$static.face = createDiv();
      return this$static.face;
    }
     else {
      return $getFace(this$static.delegateTo);
    }
  }
   else {
    return this$static.face;
  }
}

function $setImage(this$static, image){
  this$static.face = image.getElement();
  $updateButtonFace(this$static);
}

function $updateButtonFace(this$static){
  if (this$static.this$0.curFace !== null && $getFace(this$static.this$0.curFace) === $getFace(this$static)) {
    $setCurrentFaceElement(this$static.this$0, this$static.face);
  }
}

function CustomButton$Face(){
}

_ = CustomButton$Face.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'CustomButton$Face';
_.typeId$ = 43;
_.delegateTo = null;
_.face = null;
function $CustomButton$1(this$static, $anonymous0, this$0, val$name, val$faceID){
  this$static.val$name = val$name;
  this$static.val$faceID = val$faceID;
  $CustomButton$Face(this$static, $anonymous0, this$0);
  return this$static;
}

function CustomButton$1(){
}

_ = CustomButton$1.prototype = new CustomButton$Face();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'CustomButton$1';
_.typeId$ = 44;
function $FlowPanel(this$static){
  $ComplexPanel(this$static);
  this$static.setElement(createDiv());
  return this$static;
}

function $add_2(this$static, w){
  $add_1(this$static, w, this$static.getElement());
}

function FlowPanel(){
}

_ = FlowPanel.prototype = new ComplexPanel();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'FlowPanel';
_.typeId$ = 45;
function $SimplePanel(this$static){
  $SimplePanel_0(this$static, createDiv());
  return this$static;
}

function $SimplePanel_0(this$static, elem){
  this$static.setElement(elem);
  return this$static;
}

function $add_4(this$static, w){
  if (this$static.widget !== null) {
    throw $IllegalStateException(new IllegalStateException(), 'SimplePanel can only contain one child widget');
  }
  $setWidget(this$static, w);
}

function $getContainerElement(this$static){
  return this$static.getElement();
}

function $remove_1(this$static, w){
  if (this$static.widget !== w) {
    return false;
  }
  $orphan(this$static, w);
  removeChild($getContainerElement(this$static), w.getElement());
  this$static.widget = null;
  return true;
}

function $setWidget(this$static, w){
  if (w === this$static.widget) {
    return;
  }
  if (w !== null) {
    $removeFromParent(w);
  }
  if (this$static.widget !== null) {
    $remove_1(this$static, this$static.widget);
  }
  this$static.widget = w;
  if (w !== null) {
    appendChild($getContainerElement(this$static), this$static.widget.getElement());
    $adopt(this$static, w);
  }
}

function iterator_0(){
  return $SimplePanel$1(new SimplePanel$1(), this);
}

function remove_3(w){
  return $remove_1(this, w);
}

function SimplePanel(){
}

_ = SimplePanel.prototype = new Panel();
_.iterator_0 = iterator_0;
_.remove_0 = remove_3;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'SimplePanel';
_.typeId$ = 46;
_.widget = null;
function $clinit_41(){
  $clinit_41 = nullMethod;
  impl_0 = ($clinit_95() , implPanel);
}

var impl_0;
function $Label(this$static){
  this$static.setElement(createDiv());
  $sinkEvents_0(this$static, 131197);
  $setStyleName(this$static, 'gwt-Label');
  return this$static;
}

function $Label_0(this$static, text){
  $Label(this$static);
  $setText(this$static, text);
  return this$static;
}

function $setText(this$static, text){
  setInnerText(this$static.getElement(), text);
}

function onBrowserEvent_2(event_0){
  switch (eventGetType(event_0)) {
    case 1:
      break;
    case 4:
    case 8:
    case 64:
    case 16:
    case 32:
      break;
    case 131072:
      break;
  }
}

function Label(){
}

_ = Label.prototype = new Widget();
_.onBrowserEvent = onBrowserEvent_2;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Label';
_.typeId$ = 47;
function $HTML(this$static){
  $Label(this$static);
  this$static.setElement(createDiv());
  $sinkEvents_0(this$static, 125);
  $setStyleName(this$static, 'gwt-HTML');
  return this$static;
}

function $HTML_0(this$static, html){
  $HTML(this$static);
  $setHTML(this$static, html);
  return this$static;
}

function $setHTML(this$static, html){
  setInnerHTML(this$static.getElement(), html);
}

function HTML(){
}

_ = HTML.prototype = new Label();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HTML';
_.typeId$ = 48;
function $clinit_48(){
  $clinit_48 = nullMethod;
  $HasHorizontalAlignment$HorizontalAlignmentConstant(new HasHorizontalAlignment$HorizontalAlignmentConstant(), 'center');
  ALIGN_LEFT = $HasHorizontalAlignment$HorizontalAlignmentConstant(new HasHorizontalAlignment$HorizontalAlignmentConstant(), 'left');
  $HasHorizontalAlignment$HorizontalAlignmentConstant(new HasHorizontalAlignment$HorizontalAlignmentConstant(), 'right');
}

var ALIGN_LEFT;
function $HasHorizontalAlignment$HorizontalAlignmentConstant(this$static, textAlignString){
  this$static.textAlignString = textAlignString;
  return this$static;
}

function HasHorizontalAlignment$HorizontalAlignmentConstant(){
}

_ = HasHorizontalAlignment$HorizontalAlignmentConstant.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HasHorizontalAlignment$HorizontalAlignmentConstant';
_.typeId$ = 49;
_.textAlignString = null;
function $clinit_51(){
  $clinit_51 = nullMethod;
  $HasVerticalAlignment$VerticalAlignmentConstant(new HasVerticalAlignment$VerticalAlignmentConstant(), 'bottom');
  $HasVerticalAlignment$VerticalAlignmentConstant(new HasVerticalAlignment$VerticalAlignmentConstant(), 'middle');
  ALIGN_TOP = $HasVerticalAlignment$VerticalAlignmentConstant(new HasVerticalAlignment$VerticalAlignmentConstant(), 'top');
}

var ALIGN_TOP;
function $HasVerticalAlignment$VerticalAlignmentConstant(this$static, verticalAlignString){
  this$static.verticalAlignString = verticalAlignString;
  return this$static;
}

function HasVerticalAlignment$VerticalAlignmentConstant(){
}

_ = HasVerticalAlignment$VerticalAlignmentConstant.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HasVerticalAlignment$VerticalAlignmentConstant';
_.typeId$ = 50;
_.verticalAlignString = null;
function $$init_2(this$static){
  this$static.horzAlign = ($clinit_48() , ALIGN_LEFT);
  this$static.vertAlign = ($clinit_51() , ALIGN_TOP);
}

function $HorizontalPanel(this$static){
  $CellPanel(this$static);
  $$init_2(this$static);
  this$static.tableRow = createTR();
  appendChild(this$static.body_0, this$static.tableRow);
  setElementProperty(this$static.table, 'cellSpacing', '0');
  setElementProperty(this$static.table, 'cellPadding', '0');
  return this$static;
}

function $add_3(this$static, w){
  var td;
  td = $createAlignedTd(this$static);
  appendChild(this$static.tableRow, td);
  $add_1(this$static, w, td);
}

function $createAlignedTd(this$static){
  var td;
  td = createTD();
  $setCellHorizontalAlignment(this$static, td, this$static.horzAlign);
  $setCellVerticalAlignment(this$static, td, this$static.vertAlign);
  return td;
}

function remove_2(w){
  var removed, td;
  td = getParent(w.getElement());
  removed = $remove_0(this, w);
  if (removed) {
    removeChild(this.tableRow, td);
  }
  return removed;
}

function HorizontalPanel(){
}

_ = HorizontalPanel.prototype = new CellPanel();
_.remove_0 = remove_2;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HorizontalPanel';
_.typeId$ = 51;
_.tableRow = null;
function $clinit_60(){
  $clinit_60 = nullMethod;
  $HashMap(new HashMap());
}

function $Image(this$static){
  $clinit_60();
  $changeState(this$static, $Image$UnclippedState(new Image$UnclippedState(), this$static));
  $setStyleName(this$static, 'gwt-Image');
  return this$static;
}

function $Image_0(this$static, url){
  $clinit_60();
  $changeState(this$static, $Image$UnclippedState_0(new Image$UnclippedState(), this$static, url));
  $setStyleName(this$static, 'gwt-Image');
  return this$static;
}

function $changeState(this$static, newState){
  this$static.state = newState;
}

function $setUrlAndVisibleRect(this$static, url, left, top, width, height){
  this$static.state.setUrlAndVisibleRect(this$static, url, left, top, width, height);
}

function onBrowserEvent_1(event_0){
  switch (eventGetType(event_0)) {
    case 1:
      {
        break;
      }

    case 4:
    case 8:
    case 64:
    case 16:
    case 32:
      {
        break;
      }

    case 131072:
      break;
    case 32768:
      {
        break;
      }

    case 65536:
      {
        break;
      }

  }
}

function Image_0(){
}

_ = Image_0.prototype = new Widget();
_.onBrowserEvent = onBrowserEvent_1;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Image';
_.typeId$ = 52;
_.state = null;
function execute(){
}

function Image$1(){
}

_ = Image$1.prototype = new Object_0();
_.execute = execute;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Image$1';
_.typeId$ = 53;
function Image$State(){
}

_ = Image$State.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Image$State';
_.typeId$ = 54;
function $clinit_56(){
  $clinit_56 = nullMethod;
  impl_1 = new ClippedImageImpl();
}

function $Image$ClippedState(this$static, image, url, left, top, width, height){
  $clinit_56();
  this$static.left = left;
  this$static.top = top;
  this$static.width_0 = width;
  this$static.height_0 = height;
  this$static.url = url;
  image.setElement($createStructure(impl_1, url, left, top, width, height));
  $sinkEvents_0(image, 131197);
  $fireSyntheticLoadEvent(this$static, image);
  return this$static;
}

function $fireSyntheticLoadEvent(this$static, image){
  addCommand(new Image$1());
}

function setUrlAndVisibleRect(image, url, left, top, width, height){
  if (!$equals_1(this.url, url) || this.left != left || this.top != top || this.width_0 != width || this.height_0 != height) {
    this.url = url;
    this.left = left;
    this.top = top;
    this.width_0 = width;
    this.height_0 = height;
    $adjust(impl_1, image.getElement(), url, left, top, width, height);
    $fireSyntheticLoadEvent(this, image);
  }
}

function Image$ClippedState(){
}

_ = Image$ClippedState.prototype = new Image$State();
_.setUrlAndVisibleRect = setUrlAndVisibleRect;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Image$ClippedState';
_.typeId$ = 55;
_.height_0 = 0;
_.left = 0;
_.top = 0;
_.url = null;
_.width_0 = 0;
var impl_1;
function $Image$UnclippedState(this$static, image){
  image.setElement(createImg());
  $sinkEvents_0(image, 229501);
  return this$static;
}

function $Image$UnclippedState_0(this$static, image, url){
  $Image$UnclippedState(this$static, image);
  $setUrl(this$static, image, url);
  return this$static;
}

function $setUrl(this$static, image, url){
  setImgSrc(image.getElement(), url);
}

function setUrlAndVisibleRect_0(image, url, left, top, width, height){
  $changeState(image, $Image$ClippedState(new Image$ClippedState(), image, url, left, top, width, height));
}

function Image$UnclippedState(){
}

_ = Image$UnclippedState.prototype = new Image$State();
_.setUrlAndVisibleRect = setUrlAndVisibleRect_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Image$UnclippedState';
_.typeId$ = 56;
function $clinit_65(){
  $clinit_65 = nullMethod;
  rootPanels = $HashMap(new HashMap());
}

function $RootPanel(this$static, elem){
  $clinit_65();
  $AbsolutePanel(this$static);
  if (elem === null) {
    elem = getBodyElement();
  }
  this$static.setElement(elem);
  this$static.onAttach();
  return this$static;
}

function get(id){
  $clinit_65();
  var elem, gwt;
  gwt = dynamicCast($get_1(rootPanels, id), 12);
  if (gwt !== null) {
    return gwt;
  }
  elem = null;
  if (id !== null) {
    if (null === (elem = getElementById(id))) {
      return null;
    }
  }
  if (rootPanels.size == 0) {
    hookWindowClosing_0();
  }
  $put(rootPanels, id, gwt = $RootPanel(new RootPanel(), elem));
  return gwt;
}

function getBodyElement(){
  $clinit_65();
  return $doc.body;
}

function hookWindowClosing_0(){
  $clinit_65();
  addWindowCloseListener(new RootPanel$1());
}

function RootPanel(){
}

_ = RootPanel.prototype = new AbsolutePanel();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'RootPanel';
_.typeId$ = 57;
var rootPanels;
function onWindowClosed_0(){
  var gwt, it;
  for (it = $iterator_3($values(($clinit_65() , rootPanels))); $hasNext_3(it);) {
    gwt = dynamicCast($next_3(it), 12);
    if (gwt.isAttached()) {
      gwt.onDetach();
    }
  }
}

function onWindowClosing_0(){
  return null;
}

function RootPanel$1(){
}

_ = RootPanel$1.prototype = new Object_0();
_.onWindowClosed = onWindowClosed_0;
_.onWindowClosing = onWindowClosing_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'RootPanel$1';
_.typeId$ = 58;
function $ScrollPanel(this$static){
  $SimplePanel(this$static);
  $setAlwaysShowScrollBars(this$static, false);
  $sinkEvents_0(this$static, 16384);
  return this$static;
}

function $ScrollPanel_0(this$static, child){
  $ScrollPanel(this$static);
  $setWidget(this$static, child);
  return this$static;
}

function $setAlwaysShowScrollBars(this$static, alwaysShow){
  setStyleAttribute(this$static.getElement(), 'overflow', alwaysShow?'scroll':'auto');
}

function onBrowserEvent_3(event_0){
  eventGetType(event_0) == 16384;
}

function ScrollPanel(){
}

_ = ScrollPanel.prototype = new SimplePanel();
_.onBrowserEvent = onBrowserEvent_3;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'ScrollPanel';
_.typeId$ = 59;
function $$init_3(this$static){
  this$static.hasElement = this$static.this$0.widget !== null;
}

function $SimplePanel$1(this$static, this$0){
  this$static.this$0 = this$0;
  $$init_3(this$static);
  return this$static;
}

function hasNext_0(){
  return this.hasElement;
}

function next_1(){
  if (!this.hasElement || this.this$0.widget === null) {
    throw new NoSuchElementException();
  }
  this.hasElement = false;
  return this.this$0.widget;
}

function SimplePanel$1(){
}

_ = SimplePanel$1.prototype = new Object_0();
_.hasNext = hasNext_0;
_.next_0 = next_1;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'SimplePanel$1';
_.typeId$ = 60;
function $clinit_79(){
  $clinit_79 = nullMethod;
  $clinit_95() , implWidget;
}

function $$init_4(this$static){
  {
    $setStyleName(this$static, STYLENAME_DEFAULT);
  }
}

function $ToggleButton(this$static, upImage, downImage){
  $clinit_95() , implWidget;
  $CustomButton_1(this$static, upImage, downImage);
  $$init_4(this$static);
  return this$static;
}

function $isDown_0(this$static){
  return $isDown(this$static);
}

function $setDown_0(this$static, down){
  $setDown(this$static, down);
}

function onClick_0(){
  $toggleDown(this);
  $onClick(this);
}

function ToggleButton(){
}

_ = ToggleButton.prototype = new CustomButton();
_.onClick = onClick_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'ToggleButton';
_.typeId$ = 61;
var STYLENAME_DEFAULT = 'gwt-ToggleButton';
function $$init_6(this$static){
  this$static.childWidgets = $HashMap(new HashMap());
}

function $Tree(this$static){
  $Tree_0(this$static, $TreeImages_generatedBundle(new TreeImages_generatedBundle()));
  return this$static;
}

function $Tree_0(this$static, images){
  $$init_6(this$static);
  this$static.images = images;
  this$static.setElement(createDiv());
  setStyleAttribute(this$static.getElement(), 'position', 'relative');
  this$static.focusable = $createFocusable(($clinit_41() , impl_0));
  setStyleAttribute(this$static.focusable, 'fontSize', '0');
  setStyleAttribute(this$static.focusable, 'position', 'absolute');
  setIntStyleAttribute(this$static.focusable, 'zIndex', (-1));
  appendChild(this$static.getElement(), this$static.focusable);
  $sinkEvents_0(this$static, 1021);
  sinkEvents(this$static.focusable, 6144);
  this$static.root = $Tree$1(new Tree$1(), this$static);
  $setTree(this$static.root, this$static);
  $setStyleName(this$static, 'gwt-Tree');
  return this$static;
}

function $addItem_2(this$static, itemText){
  var ret;
  ret = $TreeItem_0(new TreeItem(), itemText);
  $addItem_1(this$static, ret);
  return ret;
}

function $addItem_1(this$static, item){
  $addItem(this$static.root, item);
}

function $collectElementChain(this$static, chain, hRoot, hElem){
  if (hElem === null || compare(hElem, hRoot)) {
    return;
  }
  $collectElementChain(this$static, chain, hRoot, getParent(hElem));
  $add_9(chain, wrapJSO(hElem, Element));
}

function $elementClicked(this$static, root, hElem){
  var chain, item;
  chain = $ArrayList(new ArrayList());
  $collectElementChain(this$static, chain, this$static.getElement(), hElem);
  item = $findItemByChain(this$static, chain, 0, root);
  if (item !== null) {
    if (isOrHasChild($getImageElement(item), hElem)) {
      $setState_0(item, !item.open, true);
      return true;
    }
     else if (isOrHasChild(item.getElement(), hElem)) {
      $onSelection(this$static, item, true, !$shouldTreeDelegateFocusToElement(this$static, hElem));
      return true;
    }
  }
  return false;
}

function $findDeepestOpenChild(this$static, item){
  if (!item.open) {
    return item;
  }
  return $findDeepestOpenChild(this$static, $getChild_0(item, item.children.size - 1));
}

function $findItemByChain(this$static, chain, idx, root){
  var child, hCurElem, i, n, retItem;
  if (idx == chain.size) {
    return root;
  }
  hCurElem = dynamicCast($get_0(chain, idx), 6);
  for (i = 0 , n = root.children.size; i < n; ++i) {
    child = $getChild_0(root, i);
    if (compare(child.getElement(), hCurElem)) {
      retItem = $findItemByChain(this$static, chain, idx + 1, $getChild_0(root, i));
      if (retItem === null) {
        return child;
      }
      return retItem;
    }
  }
  return $findItemByChain(this$static, chain, idx + 1, root);
}

function $iterator(this$static){
  var widgets;
  widgets = initDims_0('[Lcom.google.gwt.user.client.ui.Widget;', [107], [11], [this$static.childWidgets.size], null);
  $keySet(this$static.childWidgets).toArray(widgets);
  return createWidgetIterator(this$static, widgets);
}

function $moveFocus(this$static, selection){
  var containerLeft, containerTop, focusableWidget, height, left, selectedElem, top, width;
  focusableWidget = $getFocusableWidget(selection);
  {
    selectedElem = selection.contentElem;
    containerLeft = $getAbsoluteLeft_0(this$static);
    containerTop = $getAbsoluteTop_0(this$static);
    left = getAbsoluteLeft(selectedElem) - containerLeft;
    top = getAbsoluteTop(selectedElem) - containerTop;
    width = getElementPropertyInt(selectedElem, 'offsetWidth');
    height = getElementPropertyInt(selectedElem, 'offsetHeight');
    setIntStyleAttribute(this$static.focusable, 'left', left);
    setIntStyleAttribute(this$static.focusable, 'top', top);
    setIntStyleAttribute(this$static.focusable, 'width', width);
    setIntStyleAttribute(this$static.focusable, 'height', height);
    scrollIntoView(this$static.focusable);
    $focus(($clinit_41() , impl_0), this$static.focusable);
  }
}

function $moveSelectionDown(this$static, sel, dig){
  var idx, parent;
  if (sel === this$static.root) {
    return;
  }
  parent = sel.parent;
  if (parent === null) {
    parent = this$static.root;
  }
  idx = $getChildIndex(parent, sel);
  if (!dig || !sel.open) {
    if (idx < parent.children.size - 1) {
      $onSelection(this$static, $getChild_0(parent, idx + 1), true, true);
    }
     else {
      $moveSelectionDown(this$static, parent, false);
    }
  }
   else if (sel.children.size > 0) {
    $onSelection(this$static, $getChild_0(sel, 0), true, true);
  }
}

function $moveSelectionUp(this$static, sel){
  var idx, parent, sibling;
  parent = sel.parent;
  if (parent === null) {
    parent = this$static.root;
  }
  idx = $getChildIndex(parent, sel);
  if (idx > 0) {
    sibling = $getChild_0(parent, idx - 1);
    $onSelection(this$static, $findDeepestOpenChild(this$static, sibling), true, true);
  }
   else {
    $onSelection(this$static, parent, true, true);
  }
}

function $onSelection(this$static, item, fireEvents, moveFocus){
  if (item === this$static.root) {
    return;
  }
  if (this$static.curSelection !== null) {
    $setSelected(this$static.curSelection, false);
  }
  this$static.curSelection = item;
  if (moveFocus && this$static.curSelection !== null) {
    $moveFocus(this$static, this$static.curSelection);
    $setSelected(this$static.curSelection, true);
  }
}

function $removeItem_0(this$static, item){
  $removeItem(this$static.root, item);
}

function $setFocus_0(this$static, focus){
  if (focus) {
    $focus(($clinit_41() , impl_0), this$static.focusable);
  }
   else {
    $blur(($clinit_41() , impl_0), this$static.focusable);
  }
}

function $setSelectedItem(this$static, item){
  $setSelectedItem_0(this$static, item, true);
}

function $setSelectedItem_0(this$static, item, fireEvents){
  if (item === null) {
    if (this$static.curSelection === null) {
      return;
    }
    $setSelected(this$static.curSelection, false);
    this$static.curSelection = null;
    return;
  }
  $onSelection(this$static, item, fireEvents, true);
}

function $shouldTreeDelegateFocusToElement(this$static, elem){
  var name = elem.nodeName;
  return name == 'SELECT' || (name == 'INPUT' || (name == 'TEXTAREA' || (name == 'OPTION' || (name == 'BUTTON' || name == 'LABEL'))));
}

function doAttachChildren_0(){
  var child, it;
  for (it = $iterator(this); $hasNext_0(it);) {
    child = $next_0(it);
    child.onAttach();
  }
  setEventListener(this.focusable, this);
}

function doDetachChildren_0(){
  var child, it;
  for (it = $iterator(this); $hasNext_0(it);) {
    child = $next_0(it);
    child.onDetach();
  }
  setEventListener(this.focusable, null);
}

function iterator_1(){
  return $iterator(this);
}

function onBrowserEvent_5(event_0){
  var chain, e, eventType, item, parent;
  eventType = eventGetType(event_0);
  switch (eventType) {
    case 1:
      {
        e = eventGetTarget(event_0);
        if ($shouldTreeDelegateFocusToElement(this, e)) {
        }
         else {
          $setFocus_0(this, true);
        }
        break;
      }

    case 4:
      {
        if ($equals_0(eventGetCurrentTarget(event_0), wrapJSO(this.getElement(), Element))) {
          $elementClicked(this, this.root, eventGetTarget(event_0));
        }
        break;
      }

    case 8:
      {
        break;
      }

    case 64:
      {
        break;
      }

    case 16:
      {
        break;
      }

    case 32:
      {
        break;
      }

    case 2048:
      break;
    case 4096:
      {
        break;
      }

    case 128:
      if (this.curSelection === null) {
        if (this.root.children.size > 0) {
          $onSelection(this, $getChild_0(this.root, 0), true, true);
        }
        return;
      }

      if (this.lastEventType == 128) {
        return;
      }

      {
        switch (eventGetKeyCode(event_0)) {
          case 38:
            {
              $moveSelectionUp(this, this.curSelection);
              eventPreventDefault(event_0);
              break;
            }

          case 40:
            {
              $moveSelectionDown(this, this.curSelection, true);
              eventPreventDefault(event_0);
              break;
            }

          case 37:
            {
              if (this.curSelection.open) {
                $setState(this.curSelection, false);
              }
               else {
                parent = this.curSelection.parent;
                if (parent !== null) {
                  $setSelectedItem(this, parent);
                }
              }
              eventPreventDefault(event_0);
              break;
            }

          case 39:
            {
              if (!this.curSelection.open) {
                $setState(this.curSelection, true);
              }
               else if (this.curSelection.children.size > 0) {
                $setSelectedItem(this, $getChild_0(this.curSelection, 0));
              }
              eventPreventDefault(event_0);
              break;
            }

        }
      }

    case 512:
      if (eventType == 512) {
        if (eventGetKeyCode(event_0) == 9) {
          chain = $ArrayList(new ArrayList());
          $collectElementChain(this, chain, this.getElement(), eventGetTarget(event_0));
          item = $findItemByChain(this, chain, 0, this.root);
          if (item !== this.curSelection) {
            $setSelectedItem_0(this, item, true);
          }
        }
      }

    case 256:
      {
        break;
      }

  }
  this.lastEventType = eventType;
}

function onLoad_1(){
  $updateStateRecursive(this.root);
}

function remove_5(w){
  var item;
  item = dynamicCast($get_1(this.childWidgets, w), 13);
  if (item === null) {
    return false;
  }
  $setWidget_0(item, null);
  return true;
}

function Tree(){
}

_ = Tree.prototype = new Widget();
_.doAttachChildren = doAttachChildren_0;
_.doDetachChildren = doDetachChildren_0;
_.iterator_0 = iterator_1;
_.onBrowserEvent = onBrowserEvent_5;
_.onLoad = onLoad_1;
_.remove_0 = remove_5;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Tree';
_.typeId$ = 62;
_.curSelection = null;
_.focusable = null;
_.images = null;
_.lastEventType = 0;
_.root = null;
function $$init_5(this$static){
  this$static.children = $ArrayList(new ArrayList());
  this$static.statusImage = $Image(new Image_0());
}

function $TreeItem(this$static){
  var tbody, tdContent, tdImg, tr;
  $$init_5(this$static);
  this$static.setElement(createDiv());
  this$static.itemTable = createTable();
  this$static.contentElem = createSpan();
  this$static.childSpanElem = createSpan();
  tbody = createTBody();
  tr = createTR();
  tdImg = createTD();
  tdContent = createTD();
  appendChild(this$static.itemTable, tbody);
  appendChild(tbody, tr);
  appendChild(tr, tdImg);
  appendChild(tr, tdContent);
  setStyleAttribute(tdImg, 'verticalAlign', 'middle');
  setStyleAttribute(tdContent, 'verticalAlign', 'middle');
  appendChild(this$static.getElement(), this$static.itemTable);
  appendChild(this$static.getElement(), this$static.childSpanElem);
  appendChild(tdImg, this$static.statusImage.getElement());
  appendChild(tdContent, this$static.contentElem);
  setStyleAttribute(this$static.contentElem, 'display', 'inline');
  setStyleAttribute(this$static.getElement(), 'whiteSpace', 'nowrap');
  setStyleAttribute(this$static.childSpanElem, 'whiteSpace', 'nowrap');
  setStyleName_0(this$static.contentElem, 'gwt-TreeItem', true);
  return this$static;
}

function $TreeItem_0(this$static, html){
  $TreeItem(this$static);
  $setHTML_0(this$static, html);
  return this$static;
}

function $addItem_0(this$static, itemText){
  var ret;
  ret = $TreeItem_0(new TreeItem(), itemText);
  this$static.addItem(ret);
  return ret;
}

function $getChild_0(this$static, index){
  if (index < 0 || index >= this$static.children.size) {
    return null;
  }
  return dynamicCast($get_0(this$static.children, index), 13);
}

function $getChildIndex(this$static, child){
  return $indexOf_3(this$static.children, child);
}

function $getFocusableWidget(this$static){
  var w;
  w = this$static.widget;
  {
    return null;
  }
}

function $getImageElement(this$static){
  return this$static.statusImage.getElement();
}

function $remove_3(this$static){
  if (this$static.parent !== null) {
    this$static.parent.removeItem(this$static);
  }
   else if (this$static.tree !== null) {
    $removeItem_0(this$static.tree, this$static);
  }
}

function $setHTML_0(this$static, html){
  $setWidget_0(this$static, null);
  setInnerHTML(this$static.contentElem, html);
}

function $setParentItem(this$static, parent){
  this$static.parent = parent;
}

function $setSelected(this$static, selected){
  if (this$static.selected == selected) {
    return;
  }
  this$static.selected = selected;
  setStyleName_0(this$static.contentElem, 'gwt-TreeItem-selected', selected);
}

function $setState(this$static, open){
  $setState_0(this$static, open, true);
}

function $setState_0(this$static, open, fireEvents){
  if (open && this$static.children.size == 0) {
    return;
  }
  this$static.open = open;
  $updateState(this$static);
}

function $setTree(this$static, newTree){
  var i, n;
  if (this$static.tree === newTree) {
    return;
  }
  if (this$static.tree !== null) {
    if (this$static.tree.curSelection === this$static) {
      $setSelectedItem(this$static.tree, null);
    }
  }
  this$static.tree = newTree;
  for (i = 0 , n = this$static.children.size; i < n; ++i) {
    $setTree(dynamicCast($get_0(this$static.children, i), 13), newTree);
  }
  $updateState(this$static);
}

function $setWidget_0(this$static, newWidget){
  setInnerHTML(this$static.contentElem, '');
  this$static.widget = newWidget;
}

function $updateState(this$static){
  var images;
  if (this$static.tree === null) {
    return;
  }
  images = this$static.tree.images;
  if (this$static.children.size == 0) {
    setVisible(this$static.childSpanElem, false);
    $applyTo(($clinit_82() , treeLeaf_SINGLETON), this$static.statusImage);
    return;
  }
  if (this$static.open) {
    setVisible(this$static.childSpanElem, true);
    $applyTo(($clinit_82() , treeOpen_SINGLETON), this$static.statusImage);
  }
   else {
    setVisible(this$static.childSpanElem, false);
    $applyTo(($clinit_82() , treeClosed_SINGLETON), this$static.statusImage);
  }
}

function $updateStateRecursive(this$static){
  var i, n;
  $updateState(this$static);
  for (i = 0 , n = this$static.children.size; i < n; ++i) {
    $updateStateRecursive(dynamicCast($get_0(this$static.children, i), 13));
  }
}

function addItem_0(item){
  if (item.parent !== null || item.tree !== null) {
    $remove_3(item);
  }
  $setParentItem(item, this);
  $add_9(this.children, item);
  setStyleAttribute(item.getElement(), 'marginLeft', '16px');
  appendChild(this.childSpanElem, item.getElement());
  $setTree(item, this.tree);
  if (this.children.size == 1) {
    $updateState(this);
  }
}

function removeItem_0(item){
  if (!$contains(this.children, item)) {
    return;
  }
  $setTree(item, null);
  removeChild(this.childSpanElem, item.getElement());
  $setParentItem(item, null);
  $remove_8(this.children, item);
  if (this.children.size == 0) {
    $updateState(this);
  }
}

function TreeItem(){
}

_ = TreeItem.prototype = new UIObject();
_.addItem = addItem_0;
_.removeItem = removeItem_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'TreeItem';
_.typeId$ = 63;
_.childSpanElem = null;
_.contentElem = null;
_.itemTable = null;
_.open = false;
_.parent = null;
_.selected = false;
_.tree = null;
_.widget = null;
function $Tree$1(this$static, this$0){
  this$static.this$0 = this$0;
  $TreeItem(this$static);
  return this$static;
}

function $addItem(this$static, item){
  if (item.parent !== null || item.tree !== null) {
    $remove_3(item);
  }
  appendChild(this$static.this$0.getElement(), item.getElement());
  $setTree(item, this$static.tree);
  $setParentItem(item, null);
  $add_9(this$static.children, item);
  setIntStyleAttribute(item.getElement(), 'marginLeft', 0);
}

function $removeItem(this$static, item){
  if (!$contains(this$static.children, item)) {
    return;
  }
  $setTree(item, null);
  $setParentItem(item, null);
  $remove_8(this$static.children, item);
  removeChild(this$static.this$0.getElement(), item.getElement());
}

function addItem(item){
  $addItem(this, item);
}

function removeItem(item){
  $removeItem(this, item);
}

function Tree$1(){
}

_ = Tree$1.prototype = new TreeItem();
_.addItem = addItem;
_.removeItem = removeItem;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Tree$1';
_.typeId$ = 64;
function $clinit_82(){
  $clinit_82 = nullMethod;
  IMAGE_BUNDLE_URL = getModuleBaseURL() + '6270670BB31873C9D34757A8AE5F5E86.cache.png';
  treeClosed_SINGLETON = $ClippedImagePrototype(new ClippedImagePrototype(), IMAGE_BUNDLE_URL, 0, 0, 16, 16);
  treeLeaf_SINGLETON = $ClippedImagePrototype(new ClippedImagePrototype(), IMAGE_BUNDLE_URL, 16, 0, 16, 16);
  treeOpen_SINGLETON = $ClippedImagePrototype(new ClippedImagePrototype(), IMAGE_BUNDLE_URL, 32, 0, 16, 16);
}

function $TreeImages_generatedBundle(this$static){
  $clinit_82();
  return this$static;
}

function TreeImages_generatedBundle(){
}

_ = TreeImages_generatedBundle.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'TreeImages_generatedBundle';
_.typeId$ = 65;
var IMAGE_BUNDLE_URL, treeClosed_SINGLETON, treeLeaf_SINGLETON, treeOpen_SINGLETON;
function $WidgetCollection(this$static, parent){
  this$static.array = initDims_0('[Lcom.google.gwt.user.client.ui.Widget;', [107], [11], [4], null);
  return this$static;
}

function $add_7(this$static, w){
  $insert_2(this$static, w, this$static.size);
}

function $get(this$static, index){
  if (index < 0 || index >= this$static.size) {
    throw new IndexOutOfBoundsException();
  }
  return this$static.array[index];
}

function $indexOf(this$static, w){
  var i;
  for (i = 0; i < this$static.size; ++i) {
    if (this$static.array[i] === w) {
      return i;
    }
  }
  return (-1);
}

function $insert_2(this$static, w, beforeIndex){
  var i, newArray;
  if (beforeIndex < 0 || beforeIndex > this$static.size) {
    throw new IndexOutOfBoundsException();
  }
  if (this$static.size == this$static.array.length_0) {
    newArray = initDims_0('[Lcom.google.gwt.user.client.ui.Widget;', [107], [11], [this$static.array.length_0 * 2], null);
    for (i = 0; i < this$static.array.length_0; ++i) {
      setCheck(newArray, i, this$static.array[i]);
    }
    this$static.array = newArray;
  }
  ++this$static.size;
  for (i = this$static.size - 1; i > beforeIndex; --i) {
    setCheck(this$static.array, i, this$static.array[i - 1]);
  }
  setCheck(this$static.array, beforeIndex, w);
}

function $iterator_0(this$static){
  return $WidgetCollection$WidgetIterator(new WidgetCollection$WidgetIterator(), this$static);
}

function $remove_4(this$static, index){
  var i;
  if (index < 0 || index >= this$static.size) {
    throw new IndexOutOfBoundsException();
  }
  --this$static.size;
  for (i = index; i < this$static.size; ++i) {
    setCheck(this$static.array, i, this$static.array[i + 1]);
  }
  setCheck(this$static.array, this$static.size, null);
}

function $remove_5(this$static, w){
  var index;
  index = $indexOf(this$static, w);
  if (index == (-1)) {
    throw new NoSuchElementException();
  }
  $remove_4(this$static, index);
}

function WidgetCollection(){
}

_ = WidgetCollection.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'WidgetCollection';
_.typeId$ = 66;
_.array = null;
_.size = 0;
function $WidgetCollection$WidgetIterator(this$static, this$0){
  this$static.this$0 = this$0;
  return this$static;
}

function hasNext_1(){
  return this.index < this.this$0.size - 1;
}

function next_2(){
  if (this.index >= this.this$0.size) {
    throw new NoSuchElementException();
  }
  return this.this$0.array[++this.index];
}

function WidgetCollection$WidgetIterator(){
}

_ = WidgetCollection$WidgetIterator.prototype = new Object_0();
_.hasNext = hasNext_1;
_.next_0 = next_2;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'WidgetCollection$WidgetIterator';
_.typeId$ = 67;
_.index = (-1);
function createWidgetIterator(container, contained){
  return $WidgetIterators$1(new WidgetIterators$1(), contained, container);
}

function $$init_7(this$static){
  {
    $gotoNextIndex(this$static);
  }
}

function $WidgetIterators$1(this$static, val$contained, val$container){
  this$static.val$contained = val$contained;
  $$init_7(this$static);
  return this$static;
}

function $gotoNextIndex(this$static){
  ++this$static.index;
  while (this$static.index < this$static.val$contained.length_0) {
    if (this$static.val$contained[this$static.index] !== null) {
      return;
    }
    ++this$static.index;
  }
}

function $hasNext_0(this$static){
  return this$static.index < this$static.val$contained.length_0;
}

function $next_0(this$static){
  var w;
  if (!$hasNext_0(this$static)) {
    throw new NoSuchElementException();
  }
  w = this$static.val$contained[this$static.index];
  $gotoNextIndex(this$static);
  return w;
}

function hasNext_2(){
  return $hasNext_0(this);
}

function next_3(){
  return $next_0(this);
}

function WidgetIterators$1(){
}

_ = WidgetIterators$1.prototype = new Object_0();
_.hasNext = hasNext_2;
_.next_0 = next_3;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'WidgetIterators$1';
_.typeId$ = 68;
_.index = (-1);
function $adjust(this$static, img, url, left, top, width, height){
  var style;
  style = 'url(' + url + ') no-repeat ' + (-left + 'px ') + (-top + 'px');
  setStyleAttribute(img, 'background', style);
  setStyleAttribute(img, 'width', width + 'px');
  setStyleAttribute(img, 'height', height + 'px');
}

function $createStructure(this$static, url, left, top, width, height){
  var tmp;
  tmp = createSpan();
  setInnerHTML(tmp, $getHTML(this$static, url, left, top, width, height));
  return getFirstChild(tmp);
}

function $getHTML(this$static, url, left, top, width, height){
  var clippedImgHtml, style;
  style = 'width: ' + width + 'px; height: ' + height + 'px; background: url(' + url + ') no-repeat ' + (-left + 'px ') + (-top + 'px');
  clippedImgHtml = "<img src='" + getModuleBaseURL() + "clear.cache.gif' style='" + style + "' border='0'>";
  return clippedImgHtml;
}

function ClippedImageImpl(){
}

_ = ClippedImageImpl.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_impl_ + 'ClippedImageImpl';
_.typeId$ = 69;
function $ClippedImagePrototype(this$static, url, left, top, width, height){
  this$static.url = url;
  this$static.left = left;
  this$static.top = top;
  this$static.width_0 = width;
  this$static.height_0 = height;
  return this$static;
}

function $applyTo(this$static, image){
  $setUrlAndVisibleRect(image, this$static.url, this$static.left, this$static.top, this$static.width_0, this$static.height_0);
}

function ClippedImagePrototype(){
}

_ = ClippedImagePrototype.prototype = new AbstractImagePrototype();
_.typeName$ = package_com_google_gwt_user_client_ui_impl_ + 'ClippedImagePrototype';
_.typeId$ = 70;
_.height_0 = 0;
_.left = 0;
_.top = 0;
_.url = null;
_.width_0 = 0;
function $clinit_95(){
  $clinit_95 = nullMethod;
  implPanel = $FocusImplSafari(new FocusImplSafari());
  implWidget = implPanel !== null?$FocusImpl(new FocusImpl()):implPanel;
}

function $FocusImpl(this$static){
  $clinit_95();
  return this$static;
}

function FocusImpl(){
}

_ = FocusImpl.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_impl_ + 'FocusImpl';
_.typeId$ = 71;
var implPanel, implWidget;
function $clinit_93(){
  $clinit_93 = nullMethod;
  $clinit_95();
}

function $$init_8(this$static){
  this$static.blurHandler = $createBlurHandler(this$static);
  this$static.focusHandler = $createFocusHandler(this$static);
  this$static.mouseHandler = $createMouseHandler(this$static);
}

function $FocusImplOld(this$static){
  $clinit_93();
  $FocusImpl(this$static);
  $$init_8(this$static);
  return this$static;
}

function $createBlurHandler(this$static){
  return function(evt){
    if (this.parentNode.onblur) {
      this.parentNode.onblur(evt);
    }
  }
  ;
}

function $createFocusHandler(this$static){
  return function(evt){
    if (this.parentNode.onfocus) {
      this.parentNode.onfocus(evt);
    }
  }
  ;
}

function $createFocusable(this$static){
  var div = $doc.createElement('div');
  var input = this$static.createHiddenInput();
  input.addEventListener('blur', this$static.blurHandler, false);
  input.addEventListener('focus', this$static.focusHandler, false);
  div.addEventListener('mousedown', this$static.mouseHandler, false);
  div.appendChild(input);
  return div;
}

function createHiddenInput(){
  var input = $doc.createElement('input');
  input.type = 'text';
  input.style.width = input.style.height = 0;
  input.style.zIndex = -1;
  input.style.position = 'absolute';
  return input;
}

function FocusImplOld(){
}

_ = FocusImplOld.prototype = new FocusImpl();
_.createHiddenInput = createHiddenInput;
_.typeName$ = package_com_google_gwt_user_client_ui_impl_ + 'FocusImplOld';
_.typeId$ = 72;
function $clinit_94(){
  $clinit_94 = nullMethod;
  $clinit_93();
}

function $FocusImplSafari(this$static){
  $clinit_94();
  $FocusImplOld(this$static);
  return this$static;
}

function $blur(this$static, elem){
  $wnd.setTimeout(function(){
    elem.firstChild.blur();
  }
  , 0);
}

function $createMouseHandler(this$static){
  return function(){
    var firstChild = this.firstChild;
    $wnd.setTimeout(function(){
      firstChild.focus();
    }
    , 0);
  }
  ;
}

function $focus(this$static, elem){
  $wnd.setTimeout(function(){
    elem.firstChild.focus();
  }
  , 0);
}

function createHiddenInput_0(){
  var input = $doc.createElement('input');
  input.type = 'text';
  input.style.opacity = 0;
  input.style.zIndex = -1;
  input.style.height = '1px';
  input.style.width = '1px';
  input.style.overflow = 'hidden';
  input.style.position = 'absolute';
  return input;
}

function FocusImplSafari(){
}

_ = FocusImplSafari.prototype = new FocusImplOld();
_.createHiddenInput = createHiddenInput_0;
_.typeName$ = package_com_google_gwt_user_client_ui_impl_ + 'FocusImplSafari';
_.typeId$ = 73;
function $$init_11(this$static){
  this$static.overlayTimer = $PinnedPanel$ShowingTimer(new PinnedPanel$ShowingTimer(), this$static);
  this$static.hidingTimer = $PinnedPanel$HidingTimer(new PinnedPanel$HidingTimer(), this$static);
  this$static.delayedShow = $PinnedPanel$DelayShow(new PinnedPanel$DelayShow(), this$static);
  this$static.state = new PinnedPanel$State();
  this$static.impl = new PinnedPanel$PinnedPanelImpl();
  this$static.changeListeners = $ChangeListenerCollection(new ChangeListenerCollection());
}

function $PinnedPanel(this$static, width, pinnedToggle, contents){
  $PinnedPanel_0(this$static, width, pinnedToggle, contents, $HTML(new HTML()));
  return this$static;
}

function $PinnedPanel_0(this$static, width, pinnedToggle, contents, hoverBar){
  $$init_11(this$static);
  this$static.width_0 = width;
  this$static.master = $PinnedPanel$1(new PinnedPanel$1(), this$static);
  setStyleAttribute(this$static.master.getElement(), 'overflow', 'hidden');
  $initWidget(this$static, this$static.master);
  $setStyleName(this$static, 'gwt-PinnedPanel');
  $setWidth(this$static.master, width + 'px');
  this$static.hoverContainer = $PinnedPanel$2(new PinnedPanel$2(), this$static);
  $setWidget(this$static.hoverContainer, hoverBar);
  $setStyleName(this$static.hoverContainer, 'hover-bar');
  $add_0(this$static.master, this$static.hoverContainer, 0, 0);
  this$static.mover = $SimplePanel(new SimplePanel());
  $add_4(this$static.mover, contents);
  $addStyleName(contents, 'content');
  $setStyleName(this$static.mover, 'mover');
  $setWidth(this$static.mover, width + 'px');
  $add_0(this$static.master, this$static.mover, 0, 0);
  this$static.pinnedToggle = pinnedToggle;
  $addStyleName(pinnedToggle, 'toggle');
  $setDown_0(pinnedToggle, true);
  $addClickListener(pinnedToggle, $PinnedPanel$3(new PinnedPanel$3(), this$static));
  $setPinnedPanel(this$static.impl, this$static);
  this$static.state.currentState = PINNED;
  return this$static;
}

function $hide(this$static){
  this$static.state.currentState = HIDING;
  $cancel(this$static.overlayTimer);
  $cancel(this$static.hidingTimer);
  this$static.hidingTimer.interval = round_int(this$static.maxOffshift / NUMBER_OF_INTERVALS);
  $run(this$static.hidingTimer);
}

function $isPinned(this$static){
  return this$static.state.currentState == PINNED;
}

function $refreshWidth(this$static){
  this$static.width_0 = $getOffsetWidth(this$static.mover);
  $setWidth(this$static.master, this$static.width_0 + 'px');
}

function $setPinned(this$static, pinned){
  if ($isPinned(this$static) == pinned) {
    return;
  }
  $setDown_0(this$static.pinnedToggle, pinned);
  if (pinned) {
    $becomePinned(this$static.impl);
    $fireChange(this$static.changeListeners, this$static);
  }
   else {
    this$static.overlayTimer.fireChangeListeners = true;
    $becomeOverlay(this$static.impl);
  }
}

function $setRightMargin(this$static, rightMargin){
  this$static.userDefinedRightMargin = rightMargin;
  if ($isPinned(this$static)) {
    $becomePinned(this$static.impl);
  }
   else {
    $hide(this$static);
  }
}

function $show(this$static){
  this$static.state.currentState = SHOWING;
  $cancel(this$static.overlayTimer);
  $cancel(this$static.hidingTimer);
  this$static.overlayTimer.interval = round_int(this$static.maxOffshift / NUMBER_OF_INTERVALS);
  $run(this$static.overlayTimer);
}

function onLoad_3(){
  $refreshWidth(this);
}

function PinnedPanel(){
}

_ = PinnedPanel.prototype = new Composite();
_.onLoad = onLoad_3;
_.typeName$ = package_com_google_gwt_widgetideas_client_ + 'PinnedPanel';
_.typeId$ = 74;
_.currentOffshift = 0;
_.hoverContainer = null;
_.master = null;
_.maxOffshift = 0;
_.mover = null;
_.pinnedToggle = null;
_.userDefinedRightMargin = 0;
_.width_0 = 0;
var NUMBER_OF_INTERVALS = 10, OVERLAY_SPEED = 1;
function $$init_9(this$static){
  {
    $sinkEvents_0(this$static, 32);
  }
}

function $PinnedPanel$1(this$static, this$0){
  this$static.this$0 = this$0;
  $AbsolutePanel(this$static);
  $$init_9(this$static);
  return this$static;
}

function onBrowserEvent_7(event_0){
  var to;
  if ($shouldHide(this.this$0.state)) {
    if (!$isDown_0(this.this$0.pinnedToggle)) {
      switch (eventGetType(event_0)) {
        case 32:
          to = eventGetToElement(event_0);
          if (this.this$0.state.currentState == WILL_SHOW) {
            this.this$0.state.currentState = HIDING;
            $cancel(this.this$0.delayedShow);
            break;
          }

          if (to === null || !isOrHasChild(this.getElement(), to)) {
            $hide(this.this$0);
            break;
          }

      }
    }
  }
}

function PinnedPanel$1(){
}

_ = PinnedPanel$1.prototype = new AbsolutePanel();
_.onBrowserEvent = onBrowserEvent_7;
_.typeName$ = package_com_google_gwt_widgetideas_client_ + 'PinnedPanel$1';
_.typeId$ = 75;
function $$init_10(this$static){
  {
    $sinkEvents_0(this$static, 16);
  }
}

function $PinnedPanel$2(this$static, this$0){
  this$static.this$0 = this$0;
  $SimplePanel(this$static);
  $$init_10(this$static);
  return this$static;
}

function $maybeShow(this$static, e){
  if ($shouldShow(this$static.this$0.state)) {
    this$static.this$0.state.currentState = WILL_SHOW;
    $activate(this$static.this$0.delayedShow);
  }
}

function onBrowserEvent_8(event_0){
  var from;
  if (!$isDown_0(this.this$0.pinnedToggle)) {
    switch (eventGetType(event_0)) {
      case 16:
        from = eventGetFromElement(event_0);
        $maybeShow(this, from);
        break;
    }
  }
}

function PinnedPanel$2(){
}

_ = PinnedPanel$2.prototype = new SimplePanel();
_.onBrowserEvent = onBrowserEvent_8;
_.typeName$ = package_com_google_gwt_widgetideas_client_ + 'PinnedPanel$2';
_.typeId$ = 76;
function $PinnedPanel$3(this$static, this$0){
  this$static.this$0 = this$0;
  return this$static;
}

function onClick_1(sender){
  $setPinned(this.this$0, $isDown_0(this.this$0.pinnedToggle));
}

function PinnedPanel$3(){
}

_ = PinnedPanel$3.prototype = new Object_0();
_.onClick_0 = onClick_1;
_.typeName$ = package_com_google_gwt_widgetideas_client_ + 'PinnedPanel$3';
_.typeId$ = 77;
function $clinit_99(){
  $clinit_99 = nullMethod;
  $clinit_21();
}

function $PinnedPanel$DelayShow(this$static, this$0){
  $clinit_99();
  this$static.this$0 = this$0;
  $Timer(this$static);
  return this$static;
}

function $activate(this$static){
  $schedule(this$static.this$0.delayedShow, 200);
}

function run_1(){
  $show(this.this$0);
}

function PinnedPanel$DelayShow(){
}

_ = PinnedPanel$DelayShow.prototype = new Timer();
_.run = run_1;
_.typeName$ = package_com_google_gwt_widgetideas_client_ + 'PinnedPanel$DelayShow';
_.typeId$ = 78;
function $clinit_103(){
  $clinit_103 = nullMethod;
  $clinit_21();
}

function $PinnedPanel$SlidingTimer(this$static, this$0){
  $clinit_103();
  this$static.this$0_0 = this$0;
  $Timer(this$static);
  return this$static;
}

function $run(this$static){
  if (this$static.processSizeChange()) {
    $schedule(this$static, OVERLAY_SPEED);
  }
   else {
    if (this$static.fireChangeListeners) {
      $fireChange(this$static.this$0_0.changeListeners, this$static.this$0_0);
      this$static.fireChangeListeners = false;
    }
    this$static.finish();
  }
}

function run_2(){
  $run(this);
}

function PinnedPanel$SlidingTimer(){
}

_ = PinnedPanel$SlidingTimer.prototype = new Timer();
_.run = run_2;
_.typeName$ = package_com_google_gwt_widgetideas_client_ + 'PinnedPanel$SlidingTimer';
_.typeId$ = 79;
_.fireChangeListeners = false;
_.interval = 0;
function $clinit_100(){
  $clinit_100 = nullMethod;
  $clinit_103();
}

function $PinnedPanel$HidingTimer(this$static, this$0){
  $clinit_100();
  this$static.this$0 = this$0;
  $PinnedPanel$SlidingTimer(this$static, this$0);
  return this$static;
}

function finish_0(){
  this.this$0.state.currentState = IS_HIDDEN;
}

function processSizeChange(){
  this.this$0.currentOffshift -= this.interval;
  this.this$0.currentOffshift = max(this.this$0.currentOffshift, 0);
  $setPanelPos(this.this$0.impl, this.this$0.currentOffshift);
  return this.this$0.currentOffshift >= 0;
}

function PinnedPanel$HidingTimer(){
}

_ = PinnedPanel$HidingTimer.prototype = new PinnedPanel$SlidingTimer();
_.finish = finish_0;
_.processSizeChange = processSizeChange;
_.typeName$ = package_com_google_gwt_widgetideas_client_ + 'PinnedPanel$HidingTimer';
_.typeId$ = 80;
function $becomeOverlay(this$static){
  var aboutHalf, hoverBarWidth, newWidth, offset;
  hoverBarWidth = $getOffsetWidth(this$static.pin.hoverContainer);
  aboutHalf = round_int(hoverBarWidth / 2) + 1;
  offset = this$static.pin.width_0 - aboutHalf - this$static.pin.userDefinedRightMargin;
  newWidth = this$static.pin.width_0 + aboutHalf;
  this$static.pin.maxOffshift = newWidth;
  this$static.pin.currentOffshift = this$static.pin.width_0;
  $setWidth(this$static.pin, newWidth + 'px');
  setStyleAttribute(this$static.e, 'marginRight', -offset + 'px');
  $hide(this$static.pin);
}

function $becomePinned(this$static){
  setStyleAttribute(this$static.e, 'marginRight', this$static.pin.userDefinedRightMargin + 'px');
  setStyleAttribute(this$static.mover, 'left', '0px');
  this$static.pin.state.currentState = PINNED;
  if ($isAttached(this$static.pin)) {
    $refreshWidth(this$static.pin);
  }
}

function $setPanelPos(this$static, pos){
  setStyleAttribute(this$static.mover, 'left', pos - this$static.pin.width_0 + 'px');
}

function $setPinnedPanel(this$static, pinnedPanel){
  this$static.pin = pinnedPanel;
  this$static.e = $getElement(pinnedPanel);
  this$static.mover = pinnedPanel.mover.getElement();
}

function PinnedPanel$PinnedPanelImpl(){
}

_ = PinnedPanel$PinnedPanelImpl.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_widgetideas_client_ + 'PinnedPanel$PinnedPanelImpl';
_.typeId$ = 81;
_.e = null;
_.mover = null;
_.pin = null;
function $clinit_102(){
  $clinit_102 = nullMethod;
  $clinit_103();
}

function $PinnedPanel$ShowingTimer(this$static, this$0){
  $clinit_102();
  this$static.this$0 = this$0;
  $PinnedPanel$SlidingTimer(this$static, this$0);
  return this$static;
}

function finish_1(){
  this.this$0.state.currentState = IS_SHOWN;
}

function processSizeChange_0(){
  this.this$0.currentOffshift += this.interval;
  this.this$0.currentOffshift = min(this.this$0.currentOffshift, this.this$0.maxOffshift);
  $setPanelPos(this.this$0.impl, this.this$0.currentOffshift);
  return this.this$0.currentOffshift < this.this$0.maxOffshift;
}

function PinnedPanel$ShowingTimer(){
}

_ = PinnedPanel$ShowingTimer.prototype = new PinnedPanel$SlidingTimer();
_.finish = finish_1;
_.processSizeChange = processSizeChange_0;
_.typeName$ = package_com_google_gwt_widgetideas_client_ + 'PinnedPanel$ShowingTimer';
_.typeId$ = 82;
function $shouldHide(this$static){
  return this$static.currentState < 0;
}

function $shouldShow(this$static){
  return this$static.currentState > 0;
}

function PinnedPanel$State(){
}

_ = PinnedPanel$State.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_widgetideas_client_ + 'PinnedPanel$State';
_.typeId$ = 83;
_.currentState = 0;
var HIDING = 2, IS_HIDDEN = 3, IS_SHOWN = (-3), PINNED = (-1000), SHOWING = (-2), WILL_SHOW = (-1);
function ArrayStoreException(){
}

_ = ArrayStoreException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'ArrayStoreException';
_.typeId$ = 84;
function digit(c, radix){
  if (radix < 2 || radix > 36) {
    return (-1);
  }
  if (c >= 48 && c < 48 + min(radix, 10)) {
    return c - 48;
  }
  if (c >= 97 && c < radix + 97 - 10) {
    return c - 97 + 10;
  }
  if (c >= 65 && c < radix + 65 - 10) {
    return c - 65 + 10;
  }
  return (-1);
}

function ClassCastException(){
}

_ = ClassCastException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'ClassCastException';
_.typeId$ = 85;
function $IllegalArgumentException(this$static, message){
  $RuntimeException(this$static, message);
  return this$static;
}

function IllegalArgumentException(){
}

_ = IllegalArgumentException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'IllegalArgumentException';
_.typeId$ = 86;
function $IllegalStateException(this$static, s){
  $RuntimeException(this$static, s);
  return this$static;
}

function IllegalStateException(){
}

_ = IllegalStateException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'IllegalStateException';
_.typeId$ = 87;
function $IndexOutOfBoundsException(this$static, message){
  $RuntimeException(this$static, message);
  return this$static;
}

function IndexOutOfBoundsException(){
}

_ = IndexOutOfBoundsException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'IndexOutOfBoundsException';
_.typeId$ = 88;
function $clinit_121(){
  $clinit_121 = nullMethod;
  {
    initNative();
  }
}

function __isLongNaN(x){
  $clinit_121();
  return isNaN(x);
}

function __parseAndValidateLong(s, radix, lowerBound, upperBound){
  $clinit_121();
  var i, length, startIndex, toReturn;
  if (s === null) {
    throw $NumberFormatException(new NumberFormatException(), 'Unable to parse null');
  }
  length = $length(s);
  startIndex = length > 0 && $charAt(s, 0) == 45?1:0;
  for (i = startIndex; i < length; i++) {
    if (digit($charAt(s, i), radix) == (-1)) {
      throw $NumberFormatException(new NumberFormatException(), 'Could not parse ' + s + ' in radix ' + radix);
    }
  }
  toReturn = __parseInt(s, radix);
  if (__isLongNaN(toReturn)) {
    throw $NumberFormatException(new NumberFormatException(), 'Unable to parse ' + s);
  }
   else if (toReturn < lowerBound || toReturn > upperBound) {
    throw $NumberFormatException(new NumberFormatException(), 'The string ' + s + ' exceeds the range for the requested data type');
  }
  return toReturn;
}

function __parseInt(s, radix){
  $clinit_121();
  return parseInt(s, radix);
}

function initNative(){
  $clinit_121();
  floatRegex = /^[+-]?\d*\.?\d*(e[+-]?\d+)?$/i;
}

var floatRegex = null;
function $clinit_116(){
  $clinit_116 = nullMethod;
  $clinit_121();
}

function parseInt_0(s){
  $clinit_116();
  return parseInt_1(s, 10);
}

function parseInt_1(s, radix){
  $clinit_116();
  return narrow_int(__parseAndValidateLong(s, radix, (-2147483648), 2147483647));
}

var MAX_VALUE = 2147483647, MIN_VALUE = (-2147483648);
function abs(x){
  return x < 0?-x:x;
}

function max(x, y){
  return x > y?x:y;
}

function min(x, y){
  return x < y?x:y;
}

function NegativeArraySizeException(){
}

_ = NegativeArraySizeException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'NegativeArraySizeException';
_.typeId$ = 89;
function $NullPointerException(this$static, message){
  $RuntimeException(this$static, message);
  return this$static;
}

function NullPointerException(){
}

_ = NullPointerException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'NullPointerException';
_.typeId$ = 90;
function $NumberFormatException(this$static, message){
  $IllegalArgumentException(this$static, message);
  return this$static;
}

function NumberFormatException(){
}

_ = NumberFormatException.prototype = new IllegalArgumentException();
_.typeName$ = package_java_lang_ + 'NumberFormatException';
_.typeId$ = 91;
function $charAt(this$static, index){
  return this$static.charCodeAt(index);
}

function $equals_1(this$static, other){
  if (!instanceOf(other, 1))
    return false;
  return __equals(this$static, other);
}

function $hashCode_0(this$static){
  var hashCache = hashCache_0;
  if (!hashCache) {
    hashCache = hashCache_0 = {};
  }
  var key = ':' + this$static;
  var hashCode = hashCache[key];
  if (hashCode == null) {
    hashCode = 0;
    var n = this$static.length;
    var inc = n < 64?1:n / 32 | 0;
    for (var i = 0; i < n; i += inc) {
      hashCode <<= 1;
      hashCode += this$static.charCodeAt(i);
    }
    hashCode |= 0;
    hashCache[key] = hashCode;
  }
  return hashCode;
}

function $indexOf_0(this$static, ch){
  return this$static.indexOf(String.fromCharCode(ch));
}

function $indexOf_1(this$static, str){
  return this$static.indexOf(str);
}

function $indexOf_2(this$static, str, startIndex){
  return this$static.indexOf(str, startIndex);
}

function $length(this$static){
  return this$static.length;
}

function $substring(this$static, beginIndex){
  return this$static.substr(beginIndex, this$static.length - beginIndex);
}

function $substring_0(this$static, beginIndex, endIndex){
  return this$static.substr(beginIndex, endIndex - beginIndex);
}

function $trim(this$static){
  var r1 = this$static.replace(/^(\s*)/, '');
  var r2 = r1.replace(/\s*$/, '');
  return r2;
}

function __equals(me, other){
  return String(me) == other;
}

function equals_3(other){
  return $equals_1(this, other);
}

function hashCode_4(){
  return $hashCode_0(this);
}

_ = String.prototype;
_.equals$ = equals_3;
_.hashCode$ = hashCode_4;
_.typeName$ = package_java_lang_ + 'String';
_.typeId$ = 2;
var hashCache_0 = null;
function currentTimeMillis_0(){
  return new Date().getTime();
}

function identityHashCode(o){
  return getHashCode_0(o);
}

function $UnsupportedOperationException(this$static, message){
  $RuntimeException(this$static, message);
  return this$static;
}

function UnsupportedOperationException(){
}

_ = UnsupportedOperationException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'UnsupportedOperationException';
_.typeId$ = 93;
function $AbstractList$IteratorImpl(this$static, this$0){
  this$static.this$0 = this$0;
  return this$static;
}

function $hasNext_1(this$static){
  return this$static.i < this$static.this$0.size_0();
}

function $next_1(this$static){
  if (!$hasNext_1(this$static)) {
    throw new NoSuchElementException();
  }
  return this$static.this$0.get(this$static.last = this$static.i++);
}

function $remove_6(this$static){
  if (this$static.last < 0) {
    throw new IllegalStateException();
  }
  this$static.this$0.remove(this$static.last);
  this$static.i = this$static.last;
  this$static.last = (-1);
}

function hasNext_3(){
  return $hasNext_1(this);
}

function next_4(){
  return $next_1(this);
}

function AbstractList$IteratorImpl(){
}

_ = AbstractList$IteratorImpl.prototype = new Object_0();
_.hasNext = hasNext_3;
_.next_0 = next_4;
_.typeName$ = package_java_util_ + 'AbstractList$IteratorImpl';
_.typeId$ = 94;
_.i = 0;
_.last = (-1);
function $implFindEntry(this$static, key, remove){
  var entry, iter, k;
  for (iter = $iterator_4(this$static.entrySet()); $hasNext_4(iter);) {
    entry = $next_4(iter);
    k = entry.getKey();
    if (key === null?k === null:key.equals$(k)) {
      if (remove) {
        $remove_9(iter);
      }
      return entry;
    }
  }
  return null;
}

function $keySet(this$static){
  var entrySet;
  entrySet = this$static.entrySet();
  return $AbstractMap$1(new AbstractMap$1(), this$static, entrySet);
}

function $values(this$static){
  var entrySet;
  entrySet = $entrySet(this$static);
  return $AbstractMap$3(new AbstractMap$3(), this$static, entrySet);
}

function containsKey(key){
  return $implFindEntry(this, key, false) !== null;
}

function equals_5(obj){
  var iter, key, keys, otherKeys, otherMap, otherValue, value;
  if (obj === this) {
    return true;
  }
  if (!instanceOf(obj, 19)) {
    return false;
  }
  otherMap = dynamicCast(obj, 19);
  keys = $keySet(this);
  otherKeys = otherMap.keySet();
  if (!$equals_2(keys, otherKeys)) {
    return false;
  }
  for (iter = $iterator_2(keys); $hasNext_2(iter);) {
    key = $next_2(iter);
    value = this.get_0(key);
    otherValue = otherMap.get_0(key);
    if (value === null?otherValue !== null:!value.equals$(otherValue)) {
      return false;
    }
  }
  return true;
}

function get_0(key){
  var entry;
  entry = $implFindEntry(this, key, false);
  return entry === null?null:entry.getValue();
}

function hashCode_6(){
  var entry, hashCode, iter;
  hashCode = 0;
  for (iter = $iterator_4(this.entrySet()); $hasNext_4(iter);) {
    entry = $next_4(iter);
    hashCode += entry.hashCode$();
  }
  return hashCode;
}

function keySet(){
  return $keySet(this);
}

function AbstractMap(){
}

_ = AbstractMap.prototype = new Object_0();
_.containsKey = containsKey;
_.equals$ = equals_5;
_.get_0 = get_0;
_.hashCode$ = hashCode_6;
_.keySet = keySet;
_.typeName$ = package_java_util_ + 'AbstractMap';
_.typeId$ = 95;
function $equals_2(this$static, o){
  var iter, other, otherItem;
  if (o === this$static) {
    return true;
  }
  if (!instanceOf(o, 20)) {
    return false;
  }
  other = dynamicCast(o, 20);
  if (other.size_0() != this$static.size_0()) {
    return false;
  }
  for (iter = other.iterator_0(); iter.hasNext();) {
    otherItem = iter.next_0();
    if (!this$static.contains(otherItem)) {
      return false;
    }
  }
  return true;
}

function equals_6(o){
  return $equals_2(this, o);
}

function hashCode_7(){
  var hashCode, iter, next;
  hashCode = 0;
  for (iter = this.iterator_0(); iter.hasNext();) {
    next = iter.next_0();
    if (next !== null) {
      hashCode += next.hashCode$();
    }
  }
  return hashCode;
}

function AbstractSet(){
}

_ = AbstractSet.prototype = new AbstractCollection();
_.equals$ = equals_6;
_.hashCode$ = hashCode_7;
_.typeName$ = package_java_util_ + 'AbstractSet';
_.typeId$ = 96;
function $AbstractMap$1(this$static, this$0, val$entrySet){
  this$static.this$0 = this$0;
  this$static.val$entrySet = val$entrySet;
  return this$static;
}

function $iterator_2(this$static){
  var outerIter;
  outerIter = $iterator_4(this$static.val$entrySet);
  return $AbstractMap$2(new AbstractMap$2(), this$static, outerIter);
}

function contains_0(key){
  return this.this$0.containsKey(key);
}

function iterator_3(){
  return $iterator_2(this);
}

function size_0(){
  return this.val$entrySet.this$0.size;
}

function AbstractMap$1(){
}

_ = AbstractMap$1.prototype = new AbstractSet();
_.contains = contains_0;
_.iterator_0 = iterator_3;
_.size_0 = size_0;
_.typeName$ = package_java_util_ + 'AbstractMap$1';
_.typeId$ = 97;
function $AbstractMap$2(this$static, this$1, val$outerIter){
  this$static.val$outerIter = val$outerIter;
  return this$static;
}

function $hasNext_2(this$static){
  return $hasNext_4(this$static.val$outerIter);
}

function $next_2(this$static){
  var entry;
  entry = $next_4(this$static.val$outerIter);
  return entry.getKey();
}

function hasNext_4(){
  return $hasNext_2(this);
}

function next_5(){
  return $next_2(this);
}

function AbstractMap$2(){
}

_ = AbstractMap$2.prototype = new Object_0();
_.hasNext = hasNext_4;
_.next_0 = next_5;
_.typeName$ = package_java_util_ + 'AbstractMap$2';
_.typeId$ = 98;
function $AbstractMap$3(this$static, this$0, val$entrySet){
  this$static.this$0 = this$0;
  this$static.val$entrySet = val$entrySet;
  return this$static;
}

function $iterator_3(this$static){
  var outerIter;
  outerIter = $iterator_4(this$static.val$entrySet);
  return $AbstractMap$4(new AbstractMap$4(), this$static, outerIter);
}

function contains_1(value){
  return $containsValue(this.this$0, value);
}

function iterator_4(){
  return $iterator_3(this);
}

function size_1(){
  return this.val$entrySet.this$0.size;
}

function AbstractMap$3(){
}

_ = AbstractMap$3.prototype = new AbstractCollection();
_.contains = contains_1;
_.iterator_0 = iterator_4;
_.size_0 = size_1;
_.typeName$ = package_java_util_ + 'AbstractMap$3';
_.typeId$ = 99;
function $AbstractMap$4(this$static, this$1, val$outerIter){
  this$static.val$outerIter = val$outerIter;
  return this$static;
}

function $hasNext_3(this$static){
  return $hasNext_4(this$static.val$outerIter);
}

function $next_3(this$static){
  var value;
  value = $next_4(this$static.val$outerIter).getValue();
  return value;
}

function hasNext_5(){
  return $hasNext_3(this);
}

function next_6(){
  return $next_3(this);
}

function AbstractMap$4(){
}

_ = AbstractMap$4.prototype = new Object_0();
_.hasNext = hasNext_5;
_.next_0 = next_6;
_.typeName$ = package_java_util_ + 'AbstractMap$4';
_.typeId$ = 100;
function $clinit_143(){
  $clinit_143 = nullMethod;
  UNDEFINED = createUndefinedValue();
}

function $$init_13(this$static){
  {
    $clearImpl_0(this$static);
  }
}

function $HashMap(this$static){
  $clinit_143();
  $$init_13(this$static);
  return this$static;
}

function $clearImpl_0(this$static){
  this$static.hashCodeMap = createArray();
  this$static.stringMap = createObject();
  this$static.nullSlot = wrapJSO(UNDEFINED, JavaScriptObject);
  this$static.size = 0;
}

function $containsKey(this$static, key){
  if (instanceOf(key, 1)) {
    return getStringValue(this$static.stringMap, dynamicCast(key, 1)) !== UNDEFINED;
  }
   else if (key === null) {
    return this$static.nullSlot !== UNDEFINED;
  }
   else {
    return getHashValue(this$static.hashCodeMap, key, key.hashCode$()) !== UNDEFINED;
  }
}

function $containsValue(this$static, value){
  if (this$static.nullSlot !== UNDEFINED && equalsWithNullCheck(this$static.nullSlot, value)) {
    return true;
  }
   else if (containsStringValue(this$static.stringMap, value)) {
    return true;
  }
   else if (containsHashValue(this$static.hashCodeMap, value)) {
    return true;
  }
  return false;
}

function $entrySet(this$static){
  return $HashMap$EntrySet(new HashMap$EntrySet(), this$static);
}

function $get_1(this$static, key){
  var result;
  if (instanceOf(key, 1)) {
    result = getStringValue(this$static.stringMap, dynamicCast(key, 1));
  }
   else if (key === null) {
    result = this$static.nullSlot;
  }
   else {
    result = getHashValue(this$static.hashCodeMap, key, key.hashCode$());
  }
  return result === UNDEFINED?null:result;
}

function $put(this$static, key, value){
  var previous;
  if (key !== null) {
    previous = putStringValue(this$static.stringMap, key, value);
  }
   else if (key === null) {
    previous = this$static.nullSlot;
    this$static.nullSlot = value;
  }
   else {
    previous = putHashValue(this$static.hashCodeMap, key, value, $hashCode_0(key));
  }
  if (previous === UNDEFINED) {
    ++this$static.size;
    return null;
  }
   else {
    return previous;
  }
}

function $remove_10(this$static, key){
  var previous;
  if (instanceOf(key, 1)) {
    previous = removeStringValue(this$static.stringMap, dynamicCast(key, 1));
  }
   else if (key === null) {
    previous = this$static.nullSlot;
    this$static.nullSlot = wrapJSO(UNDEFINED, JavaScriptObject);
  }
   else {
    previous = removeHashValue(this$static.hashCodeMap, key, key.hashCode$());
  }
  if (previous === UNDEFINED) {
    return null;
  }
   else {
    --this$static.size;
    return previous;
  }
}

function addAllHashEntries(hashCodeMap, dest){
  $clinit_143();
  for (var hashCode in hashCodeMap) {
    if (hashCode == parseInt(hashCode)) {
      var array = hashCodeMap[hashCode];
      for (var i = 0, c = array.length; i < c; ++i) {
        dest.add_0(array[i]);
      }
    }
  }
}

function addAllStringEntries(stringMap, dest){
  $clinit_143();
  for (var key in stringMap) {
    if (key.charCodeAt(0) == 58) {
      var value = stringMap[key];
      var entry = create(key.substring(1), value);
      dest.add_0(entry);
    }
  }
}

function containsHashValue(hashCodeMap, value){
  $clinit_143();
  for (var hashCode in hashCodeMap) {
    if (hashCode == parseInt(hashCode)) {
      var array = hashCodeMap[hashCode];
      for (var i = 0, c = array.length; i < c; ++i) {
        var entry = array[i];
        var entryValue = entry.getValue();
        if (equalsWithNullCheck(value, entryValue)) {
          return true;
        }
      }
    }
  }
  return false;
}

function containsKey_0(key){
  return $containsKey(this, key);
}

function containsStringValue(stringMap, value){
  $clinit_143();
  for (var key in stringMap) {
    if (key.charCodeAt(0) == 58) {
      var entryValue = stringMap[key];
      if (equalsWithNullCheck(value, entryValue)) {
        return true;
      }
    }
  }
  return false;
}

function createUndefinedValue(){
  $clinit_143();
}

function entrySet_0(){
  return $entrySet(this);
}

function equalsWithNullCheck(a, b){
  $clinit_143();
  if (a === b) {
    return true;
  }
   else if (a === null) {
    return false;
  }
   else {
    return a.equals$(b);
  }
}

function get_2(key){
  return $get_1(this, key);
}

function getHashValue(hashCodeMap, key, hashCode){
  $clinit_143();
  var array = hashCodeMap[hashCode];
  if (array) {
    for (var i = 0, c = array.length; i < c; ++i) {
      var entry = array[i];
      var entryKey = entry.getKey();
      if (equalsWithNullCheck(key, entryKey)) {
        return entry.getValue();
      }
    }
  }
}

function getStringValue(stringMap, key){
  $clinit_143();
  return stringMap[':' + key];
}

function putHashValue(hashCodeMap, key, value, hashCode){
  $clinit_143();
  var array = hashCodeMap[hashCode];
  if (array) {
    for (var i = 0, c = array.length; i < c; ++i) {
      var entry = array[i];
      var entryKey = entry.getKey();
      if (equalsWithNullCheck(key, entryKey)) {
        var previous = entry.getValue();
        entry.setValue(value);
        return previous;
      }
    }
  }
   else {
    array = hashCodeMap[hashCode] = [];
  }
  var entry = create(key, value);
  array.push(entry);
}

function putStringValue(stringMap, key, value){
  $clinit_143();
  key = ':' + key;
  var result = stringMap[key];
  stringMap[key] = value;
  return result;
}

function removeHashValue(hashCodeMap, key, hashCode){
  $clinit_143();
  var array = hashCodeMap[hashCode];
  if (array) {
    for (var i = 0, c = array.length; i < c; ++i) {
      var entry = array[i];
      var entryKey = entry.getKey();
      if (equalsWithNullCheck(key, entryKey)) {
        if (array.length == 1) {
          delete hashCodeMap[hashCode];
        }
         else {
          array.splice(i, 1);
        }
        return entry.getValue();
      }
    }
  }
}

function removeStringValue(stringMap, key){
  $clinit_143();
  key = ':' + key;
  var result = stringMap[key];
  delete stringMap[key];
  return result;
}

function HashMap(){
}

_ = HashMap.prototype = new AbstractMap();
_.containsKey = containsKey_0;
_.entrySet = entrySet_0;
_.get_0 = get_2;
_.typeName$ = package_java_util_ + 'HashMap';
_.typeId$ = 101;
_.hashCodeMap = null;
_.nullSlot = null;
_.size = 0;
_.stringMap = null;
var UNDEFINED;
function $HashMap$EntryImpl(this$static, key, value){
  this$static.key = key;
  this$static.value = value;
  return this$static;
}

function create(key, value){
  return $HashMap$EntryImpl(new HashMap$EntryImpl(), key, value);
}

function equals_8(other){
  var entry;
  if (instanceOf(other, 21)) {
    entry = dynamicCast(other, 21);
    if (equalsWithNullCheck(this.key, entry.getKey()) && equalsWithNullCheck(this.value, entry.getValue())) {
      return true;
    }
  }
  return false;
}

function getKey(){
  return this.key;
}

function getValue(){
  return this.value;
}

function hashCode_8(){
  var keyHash, valueHash;
  keyHash = 0;
  valueHash = 0;
  if (this.key !== null) {
    keyHash = this.key.hashCode$();
  }
  if (this.value !== null) {
    valueHash = this.value.hashCode$();
  }
  return keyHash ^ valueHash;
}

function setValue(object){
  var old;
  old = this.value;
  this.value = object;
  return old;
}

function HashMap$EntryImpl(){
}

_ = HashMap$EntryImpl.prototype = new Object_0();
_.equals$ = equals_8;
_.getKey = getKey;
_.getValue = getValue;
_.hashCode$ = hashCode_8;
_.setValue = setValue;
_.typeName$ = package_java_util_ + 'HashMap$EntryImpl';
_.typeId$ = 102;
_.key = null;
_.value = null;
function $HashMap$EntrySet(this$static, this$0){
  this$static.this$0 = this$0;
  return this$static;
}

function $iterator_4(this$static){
  return $HashMap$EntrySetIterator(new HashMap$EntrySetIterator(), this$static.this$0);
}

function contains_3(o){
  var entry, key, value;
  if (instanceOf(o, 21)) {
    entry = dynamicCast(o, 21);
    key = entry.getKey();
    if ($containsKey(this.this$0, key)) {
      value = $get_1(this.this$0, key);
      return equalsWithNullCheck(entry.getValue(), value);
    }
  }
  return false;
}

function iterator_5(){
  return $iterator_4(this);
}

function size_3(){
  return this.this$0.size;
}

function HashMap$EntrySet(){
}

_ = HashMap$EntrySet.prototype = new AbstractSet();
_.contains = contains_3;
_.iterator_0 = iterator_5;
_.size_0 = size_3;
_.typeName$ = package_java_util_ + 'HashMap$EntrySet';
_.typeId$ = 103;
function $HashMap$EntrySetIterator(this$static, this$0){
  var list;
  this$static.this$0 = this$0;
  list = $ArrayList(new ArrayList());
  if (this$static.this$0.nullSlot !== ($clinit_143() , UNDEFINED)) {
    $add_9(list, $HashMap$EntryImpl(new HashMap$EntryImpl(), null, this$static.this$0.nullSlot));
  }
  addAllStringEntries(this$static.this$0.stringMap, list);
  addAllHashEntries(this$static.this$0.hashCodeMap, list);
  this$static.iter = $iterator_1(list);
  return this$static;
}

function $hasNext_4(this$static){
  return $hasNext_1(this$static.iter);
}

function $next_4(this$static){
  return this$static.last = dynamicCast($next_1(this$static.iter), 21);
}

function $remove_9(this$static){
  if (this$static.last === null) {
    throw $IllegalStateException(new IllegalStateException(), 'Must call next() before remove().');
  }
   else {
    $remove_6(this$static.iter);
    $remove_10(this$static.this$0, this$static.last.getKey());
    this$static.last = null;
  }
}

function hasNext_6(){
  return $hasNext_4(this);
}

function next_7(){
  return $next_4(this);
}

function HashMap$EntrySetIterator(){
}

_ = HashMap$EntrySetIterator.prototype = new Object_0();
_.hasNext = hasNext_6;
_.next_0 = next_7;
_.typeName$ = package_java_util_ + 'HashMap$EntrySetIterator';
_.typeId$ = 104;
_.iter = null;
_.last = null;
function NoSuchElementException(){
}

_ = NoSuchElementException.prototype = new RuntimeException();
_.typeName$ = package_java_util_ + 'NoSuchElementException';
_.typeId$ = 105;
function init_0(){
  $onModuleLoad(new PinnedPanelDemo());
}

function gwtOnLoad(errFn, modName, modBase){
  $moduleName = modName;
  $moduleBase = modBase;
  if (errFn)
    try {
      init_0();
    }
     catch (e) {
      errFn(modName);
    }
   else {
    init_0();
  }
}

var typeIdArray = [{}, {15:1}, {1:1, 15:1}, {4:1, 15:1}, {4:1, 15:1}, {4:1, 15:1}, {4:1, 15:1}, {2:1, 15:1}, {15:1}, {9:1, 15:1}, {15:1, 16:1}, {11:1, 15:1, 16:1, 17:1}, {11:1, 14:1, 15:1, 16:1, 17:1}, {11:1, 14:1, 15:1, 16:1, 17:1}, {11:1, 14:1, 15:1, 16:1, 17:1}, {11:1, 14:1, 15:1, 16:1, 17:1}, {15:1}, {15:1}, {15:1}, {4:1, 15:1}, {15:1}, {7:1, 15:1}, {7:1, 15:1}, {7:1, 15:1}, {15:1}, {2:1, 6:1, 15:1}, {2:1, 15:1}, {8:1, 15:1}, {15:1}, {15:1}, {15:1}, {11:1, 14:1, 15:1, 16:1, 17:1}, {15:1}, {11:1, 15:1, 16:1, 17:1}, {11:1, 15:1, 16:1, 17:1}, {11:1, 14:1, 15:1, 16:1, 17:1}, {15:1}, {15:1, 18:1}, {15:1, 18:1}, {15:1, 18:1}, {15:1, 18:1}, {11:1, 15:1, 16:1, 17:1}, {11:1, 15:1, 16:1, 17:1}, {15:1}, {15:1}, {11:1, 14:1, 15:1, 16:1, 17:1}, {11:1, 14:1, 15:1, 16:1, 17:1}, {11:1, 15:1, 16:1, 17:1}, {11:1, 15:1, 16:1, 17:1}, {15:1}, {15:1}, {11:1, 14:1, 15:1, 16:1, 17:1}, {11:1, 15:1, 16:1, 17:1}, {5:1, 15:1}, {15:1}, {15:1}, {15:1}, {11:1, 12:1, 14:1, 15:1, 16:1, 17:1}, {8:1, 15:1}, {3:1, 11:1, 14:1, 15:1, 16:1, 17:1}, {15:1}, {11:1, 15:1, 16:1, 17:1}, {11:1, 14:1, 15:1, 16:1, 17:1}, {13:1, 15:1, 16:1}, {13:1, 15:1, 16:1}, {15:1}, {15:1}, {15:1}, {15:1}, {15:1}, {15:1}, {15:1}, {15:1}, {15:1}, {11:1, 15:1, 16:1, 17:1}, {11:1, 14:1, 15:1, 16:1, 17:1}, {11:1, 14:1, 15:1, 16:1, 17:1}, {10:1, 15:1}, {7:1, 15:1}, {7:1, 15:1}, {7:1, 15:1}, {15:1}, {7:1, 15:1}, {15:1}, {4:1, 15:1}, {4:1, 15:1}, {4:1, 15:1}, {4:1, 15:1}, {4:1, 15:1}, {4:1, 15:1}, {4:1, 15:1}, {4:1, 15:1}, {15:1}, {4:1, 15:1}, {15:1}, {15:1, 19:1}, {15:1, 20:1}, {15:1, 20:1}, {15:1}, {15:1}, {15:1}, {15:1, 19:1}, {15:1, 21:1}, {15:1, 20:1}, {15:1}, {4:1, 15:1}, {15:1}, {15:1}, {15:1}, {15:1}];

if (com_google_gwt_demos_pinnedpanel_PinnedPanelDemo) {
  var __gwt_initHandlers = com_google_gwt_demos_pinnedpanel_PinnedPanelDemo.__gwt_initHandlers;  com_google_gwt_demos_pinnedpanel_PinnedPanelDemo.onScriptLoad(gwtOnLoad);
}
})();
