from string import Template

copywrite = """/*
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
 */"""

logicalHandlerTemplate = Template(copywrite + """
package com.google.gwt.gen2.event.logical.shared;

import com.google.gwt.gen2.event.shared.EventHandler;

/**
 * Handler interface for {@link ${Name}Event} events.
 */
public interface ${Name}Handler extends EventHandler {

  /**
   * Called when ${Name}Event is fired.
   * 
   * @param event the {@link ${Name}Event} that was fired
   */
  void on${Name}(${Name}Event event);
}
""")
 
logicalHasHandlersTemplate = Template(copywrite + 
"""package com.google.gwt.gen2.event.logical.shared;

import com.google.gwt.gen2.event.shared.HandlerRegistration;

/**
 * A widget that implements this interface is a public source of
 * {@link ${Name}Event} events.
 */
public interface Has${Name}Handlers {
  /**
   * Adds a {@link Has${Name}Event} handler.
   * 
   * @param handler the handler
   */
  HandlerRegistration add${Name}Handler(${Name}Handler handler);
}
"""
)

logicalEventTemplate = Template(copywrite + """
package com.google.gwt.gen2.event.logical.shared;

import com.google.gwt.gen2.event.shared.AbstractEvent; 


/**
 * Represents a ${name} event.
 */
public class ${Name}Event extends ${Extends}Event {

  /**
    Event Key for ${Name}.
  */
  public static final Key<${Name}Event, ${Name}Handler> KEY = new Key<${Name}Event,${Name}Handler>() {
     @Override
     protected void fire(${Name}Handler handler, ${Name}Event event) {
       handler.on${Name}(event);
     }
   };

  /**
   * Constructs a ${Name}Event event.
   */
  public ${Name}Event() {
   }
  
  @Override
  protected Key getKey() {
    return KEY;
  }
}
""")

 

domEventTemplate = Template(copywrite + """
package com.google.gwt.gen2.event.dom.client;

import com.google.gwt.user.client.Event;

/**
 * Represents a native ${name} event.
 */
public class ${Name}Event extends ${Extends}Event {

  /**
    Event Key for ${Name}.
  */
  public static final Key<${Name}Event, ${Name}Handler> KEY = new Key<${Name}Event,${Name}Handler>(
      Event.ON${NAME}) {
     @Override
     protected void fire(${Name}Handler handler, ${Name}Event event) {
       handler.on${Name}(event);
     }

     @Override     
     public ${Name}Event wrap(Event nativeEvent) {
       return new ${Name}Event(nativeEvent);
     }
   };

  /**
   * Constructs a ${Name}Event event.
   * 
   * @param nativeEvent the native event object wrapped by this event
   */
  public ${Name}Event(Event e) {
    super(e);
  }
  
  @Override
  protected Key getKey() {
    return KEY;
  }
}
"""
)



domHasHandlersTemplate = Template(copywrite + 
"""package com.google.gwt.gen2.event.dom.client;

import com.google.gwt.gen2.event.shared.HandlerRegistration;

/**
 * A widget that implements this interface is a public source of
 * {@link ${Name}Event} events.
 */
public interface Has${Name}Handlers {
  /**
   * Adds a {@link Has${Name}Event} handler.
   * 
   * @param handler the handler
   */
  HandlerRegistration add${Name}Handler(${Name}Handler handler);
}
"""
)

def updateDomEvents():
    print("update DOM events")

    for name in "blur,change,click,double click,error,focus,key down,key press,key up,load,lose capture,mouse down,mouse up,mouse move,mouse out,mouse over,mouse wheel,scroll".split(","):
        Name = name.title().replace(" ", "")
        NAME = Name.upper()
        Extends = "Dom"
  
        if "mouse" in name:
            Extends = "Mouse"
        elif "key" in name:
            Extends = "Keyboard"
        if "double click" in name:
            NAME = "DBLCLICK"

        # Event Generator
        fileName = "C:\\gwt-incubator\\trunk\\src\\com\\google\\gwt\\gen2\\event\\dom\\client\\%sEvent.java" % (Name,)
        f = open(fileName, "w")
    
        f.write(domEventTemplate.substitute(locals()))
        f.close()

        # HasHandlers Generator
        hasName = "C:\\gwt-incubator\\trunk\\src\\com\\google\\gwt\\gen2\\event\\dom\\client\\Has%sHandlers.java" % (Name,)
        fh = open(hasName, "w")
        fh.write(domHasHandlersTemplate.substitute(locals()))
        fh.close()
    print("Finished updating DOM events, updating logical events")

def updateLogicalEvents():
    for name in "show range,hide".split(","):
        Name = name.title().replace(" ", "")
        NAME = Name.upper()
        Extends = "Abstract"
       
        # Event Generator
        fileName = "C:\\gwt-incubator\\trunk\\src\\com\\google\\gwt\\gen2\\event\\logical\\shared\\%sEvent.java" % (Name,)
        f = open(fileName, "w")
        package = "dom.client"
        f.write(logicalEventTemplate.substitute(locals()))
        f.close()
    
        # HasHandlers Generator
        hasName = "C:\\gwt-incubator\\trunk\\src\\com\\google\\gwt\\gen2\\event\\logical\\shared\\Has%sHandlers.java" % (Name,)
        fh = open(hasName, "w")
        fh.write(logicalHasHandlersTemplate.substitute(locals()))
        fh.close()
        
        # Handlers Generator
        hasName = "C:\\gwt-incubator\\trunk\\src\\com\\google\\gwt\\gen2\\event\\logical\\shared\\%sHandler.java" % (Name,)
        fh = open(hasName, "w")
        fh.write(logicalHandlerTemplate.substitute(locals()))
        fh.close()
    print("Finished updating logical events")



updateLogicalEvents()




 
 