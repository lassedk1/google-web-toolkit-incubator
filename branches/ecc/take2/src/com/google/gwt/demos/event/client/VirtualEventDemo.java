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
package com.google.gwt.demos.event.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.widgetideas.graphics.client.Color;

/**
 */
public class VirtualEventDemo implements EntryPoint {

  public void onModuleLoad() {
    VirtualRootPanel root = new VirtualRootPanel();
    DraggableWidget circle = new CircleWidget(200, 200, 50, new Color(255,0,0));
    DraggableWidget circle2 = new RectWidget(300, 300, 50, 50, new Color(0, 0, 255));
    
    root.add(circle);
    root.add(circle2);
    
    RootPanel.get().add(root);
  }
}
