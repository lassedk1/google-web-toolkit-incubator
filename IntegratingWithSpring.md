# Explained: How to Integrate GWT with Spring 2.0 #
> _original by [Richard Bondi](http://gwttips.blogspot.com/)_

These instructions are based on [this GWT newsgroup post](http://groups.google.com/group/Google-Web-Toolkit/browse_frm/thread/c50c76d0f88ba919).

**Prerequisites:**
You are comfortable with using Spring without GWT. You also know the basics of how to code a GWT client making an RPC call to a GWT service. Specifically, you know that for any GWT module:
  * (module)/client/MyService.java interface extends GWT's RemoteService interface;
  * (module)/server/MyServiceImpl.java ...
    * ...implements RemoteService too;
    * ...extends GWT's RemoteServiceServlet, which extends javax.servlet.http.HttpServlet.

[Spring's MVC](http://www.springframework.org/docs/reference/mvc.html) works first by your browser request/url being redirected to DispatcherServlet.

So let's make that happen. First, we'll configure our web.xml file to redirect all urls ending in "whatever" to go to Spring's DispatcherServlet:

> File: **web.xml**
```
<web-app>
...
<servlet-name>gwtGumby</servlet-name>
  <servlet-class>
org.springframework.web.servlet.DispatcherServlet
</servlet-class>

<servlet-mapping>
  <servlet-name>gwtGumby</servlet-name>
  <url-pattern>*.whatever</url-pattern>
</servlet-mapping>
...
</web-app>
```


So far so good. Next, the DispatcherServlet will have to choose a [org.springframework.web.portlet.mvc.Controller](http://www.springframework.org/docs/api/org/springframework/web/portlet/mvc/Controller.html) to process this request. But how does it choose?

With either a BeanNameUrlHandlerMapping or a SimpleUrlHandlerMapping implementation, which you specify in an application context file. Here's an example:

> File: **applicationContext.xml** (or related file) (ignore the syntax highlighting, it's wrong)
```
<beans>

<!-- == SPRING DISPATCH HANDLER == -->

<bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
<property name="mappings">
  <value>
    /**/chart.whatever=chartController
    /**/gumby.whatever=gumbyController
  </value>
</property>
</bean>
...
```

This says that the DispatcherServlet will take any request whose url ends in "chart.whatever" and forward it to a Spring org.springframework.web.portlet.mvc.Controller whose application context id is "chartController".

I'll show you the application context entries for chartController and gumbyController only at the end of this post. Before that, we have to look at these controllers.

Or rather, Controller: it's actually just one Controller, which will be called with different injections for different requests.

The code of this Controller class, which we've named **GWTController**, is shown below; here is how it works.

### How GWTController works ###
GWTController extends GWT's RemoteServiceServlet, but it also implements Spring's MVC Controller interface. When DispatcherServlet calls this Controller, it calls its **handleRequest**() method; **handleRequest**() simply calls **doPost**(), and returns `null`.

What does doPost() do? Well, it is a GWT RemoteServiceServlet method that overrides javax.servlet.http.HttpServlet#doPost(). Whatever else this GWT method does, it also calls the GWT RemoteServiceServlet's **processCall**() method.

When a GWT client makes an ajax RPC call to a GWT (RemoteServiceServlet) service, the client is sending an RPC-encoded payload. Under normal circumstances -- in other words, outside of Spring -- the service's **processCall**() will RPC decode the payload by calling `RPCRequest rpcRequest = RPC.decode(payload, this)`. But that won't work now: `this` is the Spring Controller we've written, GWTController. Given how we have set things up in Spring, _every_ different GWT client calll will made to the same GWT service, GWTController, instead of every client calling its matching GWT service. What to do?

Answer: have Spring inject the GWT RemoteService so that we can access it from `this.remoteServiceClass`, and then call `RPCRequest rpcRequest = RPC.decodeRequest(payload, this.remoteServiceClass)`. Voila: now every GWT client will have its payload decoded by the client's corresponding GWT service.

Once again, so far so good. With this neat trick we've captured what some GWT Javascript client sent to us via RPC: we have our decoded payload. Now our GWT (RemoteService) service needs to process it, too. But how?

We just run the same code GWT would run outside of Spring, but once again substituting the injected `this.remoteServie for this`. We just call:
```
return RPC.invokeAndEncodeResponse(this.remoteService, rpcRequest.getMethod(), rpcRequest.getParameters());
```

And now we're done. Everything else in this class is just commentary. See for yourself:

> File: **GWTController.java**
```
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.google.gwt.user.client.rpc.RemoteService;

public class GWTController
extends RemoteServiceServlet
implements Controller
{
  // Instance fields
  private RemoteService remoteService;
  private Class remoteServiceClass;


  // Public methods
  /**
  * Implements Spring Controller interface method.
  *
  * Call GWT's RemoteService doPost() method and return null.
  *
  * @param request  current HTTP request
  * @param response current HTTP response
  * @return a ModelAndView to render, or null if handled directly
  * @throws Exception in case of errors
  */
  public ModelAndView handleRequest(HttpServletRequest request,
                                 HttpServletResponse response)
                                 throws Exception
  {
    doPost(request, response);
    return null; // response handled by GWT RPC over XmlHttpRequest
  }

  /**
  * Process the RPC request encoded into the payload string and return a string
  * that encodes either the method return or an exception thrown by it.
  */
  public String processCall(String payload) throws SerializationException
  {
    try
    {
       RPCRequest rpcRequest =
           RPC.decodeRequest(payload, this.remoteServiceClass);

       // delegate work to the spring injected service
       return RPC.invokeAndEncodeResponse(this.remoteService,
                                          rpcRequest.getMethod(),
                                          rpcRequest.getParameters() );
    }
    catch (IncompatibleRemoteServiceException e)
    {
      return RPC.encodeResponseForFailure(null, e);
    }
  }

  /**
  * Setter for Spring injection of the GWT RemoteService object.
  * @param RemoteService the GWT RemoteService implementation
  * that will be delegated to by
  * the {@code GWTController}.
  */
  public void setRemoteService( RemoteService remoteService )
  {
    this.remoteService = remoteService;
    this.remoteServiceClass = this.remoteService.getClass();
  }
}
```

One thing to note is that in addition to injecting a single GWT RemoteService implementation, you can inject as many non-GWT classes, services, etc etc as you wish.

Even better, if you write your GWT RemoteService correctly, you can test it with stubs for all these injections -- in hosted mode, without every having to launch your web application or database! But how to do that is another post.

Finally, then, here is how you inject your GWT service into the GWTController, in an application context file:

> File: **applicationContext.xml** (or related file)
```
<beans>

  <!-- == SPRING DISPATCH HANDLER == -->

  <bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
    <property name="mappings">
      <value>
      /**/chart.whatever=chartController
      /**/gumby.whatever=gumbyController
      </value>
    </property>
  </bean>
  ...

  <!-- == CHART GWT SERVICE == -->

  <bean name="chartController" class="com.aspentech.imos.servlet.GWTController">
    <property name="remoteService">
      <bean class="com.foo.gwt.chart.server.ChartServiceImpl"/>
    </property>
  </bean>

  <!-- == GUMBY GWT SERVICE == -->

  <bean name="gumbyController" class="com.aspentech.imos.servlet.GWTController">
    <property name="remoteService">
      <bean class="com.foo.gwt.gumby.server.GumbyServiceImpl">
        <constructor-arg index="0" ref="someNonGWTService1"/>
        <constructor-arg index="0" ref="someNonGWTService2"/>
        <constructor-arg index="0" ref="someNonGWTService3"/>
      </bean>
    </property>
  </bean>

</beans>
```

That's it! Enjoy using GWT with Spring.