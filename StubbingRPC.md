# Stubbing RPC calls in Google Web Toolkit's Hosted Mode #
> _original by [Richard Bondi](http://gwttips.blogpspot.com/)_

(This article requires an understanding of the Google Web Tookit's so-called "hosted" and "web" modes, which you can read about [here](http://code.google.com/webtoolkit/overview.html#Modes).)

In this article I describe a way to "fake" or "stub" your RPC calls in GWT's hosted mode. It allows you to deploy to web mode without having to change any code, change any configurations, and without any stub or development-only code being deployed. It's very clean, and will work with any web application setup (for example, Spring is optional).

### Two advantages of this technique ###
his technique allows you to not only create wireframes, something GWT is well-suited for anyway, but allows you to do so on top of servlet (as well as UI) [prototypes or tracer bullets](http://www.artima.com/intv/tracer.html). This can be a good [stone soup](http://en.wikipedia.org/wiki/Stone_soup) with which to introduce GWT gradually into your organization.

The other unique advantage of this technique is that it allows Java web programmers to specialize on programming the front end, without having to first learn a system's entire stack and its related technologies. This is something new in Java. Currently, adding a single HTML field can involve a breath-taking number of fields and technologies: the schema and SQL for the corresponding table column, Hibernate, Spring, service and mapping layers, Struts or Webwork or some other framework and its tags, JSP, JSTL, Javascript libraries like Prototype, and so on. For this reasons alone, it can take a very long time before a junior Java programmer can program the UI, and front-end programming will always be slow. With this stubbing technique and GWT, all a programmer essentially needs to know anymore is Java, HTML, CSS, and Javascript, and coding it can be remarkably swift.

## Part 1: Hosted Mode RPC in a nutshell ##
This technique has two parts. First you set up GWT to call one RPC URL for hosted mode, and another for web mode. In the second part, you stub your GWT service and configure GWT to call the stub only in hosted mode.

### Server (Service) side ###
GWT services, the Java classes which receive an Ajax/RPC call from the client, all extend RemoteServiceServlet, which in turn is just an extension of javax's Servlet class. Hosted mode launches a little Tomcat server to serve up your RPC calls. In a normal Tomcat server, you have to edit web.xml to tell Tomcat which URLs to route to which Servlets.

In hosted mode though, there's no web.xml to edit. Instead, you tell hosted mode's Tomcat server which Servlet to use in your `.gwt.xml` file using the **servlet** tag. The **path** attribute specifies a part of the URL, about which I'll say more in a moment. The **class** attribute specifies the RemoteServiceServlet to use.

In the example below, the servlet tag is saying to hosted mode's Tomcat: "If you get sent a request URL that ends in `gumby`, pass that request on to the class MyExampleServiceImpl. It's a Servlet, so it will know what to do with the request."

> Example module file: **MyExample.gwt.xml**
```
<module>
<source path ="client"/>
<public path ="public"/>

<entry-point class="com.foo.gwt.myexample.client.MyExample"/>

<servlet path ="/gumby"
class ="com.foo.gwt.myexample.server.MyExampleServiceImpl"/>
</module>
```

That all there is to configuring the server end of a hosted mode RPC call. **NOTE:** this `<servlet>` tag has no meaning in web mode. It is completely ignored when you deploy.

### Client Side ###
So now we know what happens on the server side in hosted mode. But how do we tell the GWT client code to call this URL?

Different programmers put this client code in different places. Where you put it doesn't matter, just don't be thrown off by where I put it -- you probably put the same code in a different place, that's all.

The client side code has two parts. First you code a proxy that will be used to make the RPC call; then, in the part of your code where the RPC call is made, you code the proxy's call itself.

I set the proxy up in my client directory's service interface, by creating a public static inner class called "ProxyApp." I can use this class any time to get a GWT proxy for making an RPC call by calling:

> `MyExampleService.ProxyApp.getInstance()`
> <sup>(The code for !ProxyApp and how it is called is shown in the examples below.)</sup>

The key point here is where I tell ProxyApp to point its RPC request -- what URL to use. If I'm in hosted mode, I only want it to use the URL **GWT.getModuleBaseURL() + "gumby"**. If you look back at MyExample.gwt.xml, you'll see I said that "gumby" specified the hosted mode Servlet to call. (Don't worry about putting a slash in front of "gumby": GWT.getModuleBaseURL() is guaranteed to add a forward slash when necessary (that's what its Javadoc says).)

But in web mode, I want ProxyApp to use whatever URL pattern I specified in web.xml (or, if you are using Spring, in your application context file for SimpleUrlHandlerMapping). In the example below, this is "myExample.rpc".

To get GWT to distinguish between hosted and web mode, and use different URLs for each, use the function GWT.isScripted().

The two code examples below illustrate how to do set all this up.

> Example file: **client/MyExampleService.java**
```
public interface MyExampleService extends RemoteService
{
  /** See below. */
  static final String URL = "myExample.rpc";

  /**
   * The method that will be called for the RPC data.
   */
  public String getSomeData( String someParam) throws SerializableException;

  /**
   * Utility/Convenience class for the GWT RPC proxy.
   * Use MyExampleService.App.getInstance() to access static instance of MyExampleServiceAsync
   */
  public static class ProxyApp
  {
    private static MyExampleServiceAsync clientRPCProxy = null;

    public static synchronized MyExampleServiceAsync getInstance()
    {
      if ( clientRPCProxy == null )
      {
        // Get proxy instance implementing our *Async interface
        clientRPCProxy = ( MyExampleServiceAsync ) GWT.create( MyExampleServiceAsync .class );
        // Cast proxy to GWT's ServiceDefTarget interface, specify target of RPC call (from where the client directory is)
        if(GWT.isScript()) // web mode
          ( ( ServiceDefTarget )clientRPCProxy ).setServiceEntryPoint( GWT.getModuleBaseURL() + URL );
        else // hosted mode
          ( ( ServiceDefTarget )clientRPCProxy ).setServiceEntryPoint( GWT.getModuleBaseURL() + "gumby" );
      }
      return clientRPCProxy;
    }
  }
}
```

> Example file: **any .java file in your GWT /client directory from which you make an RPC call**
```
    ...
    MyExampleService.ProxyApp.getInstance().getSomeData( "bla bla bla", new AsyncCallback()
    {
      public void onFailure( Throwable caught ) { ... }
      public void onSuccess( Object result ) { ... }
    }
    ...
```

## Part 2: Stubbing your GWT Services ##
Now we know how to make GWT call one URL in hosted mode, and another in web mode.

What we also want is to call production code in hosted mode, but with fake data. That way we don't have to start our web and/or application server(s) every time we want to test GWT apps. Typically this involves starting Apache, Tomcat or Weblogic or JBoss or whatever, which perhaps will load Spring, Hibernate or iBatis, a database, and hundreds if not thousands of objects that we absolutely do not need to test our itty bitty GWT page.

The GWT service in our /server directory, MyExampleServiceImpl in the example code below, typically has to use one or more objects that are not part of GWT. For example, it may well want to use a service to access your database. Let's call this service `com.foo.service.InfoSvc`. (That's a lousy name for a service, because "info" can mean anything, but it's good for a generic example!)

Then `com.foo.gwt.MyModule.MyExample.server.MyExampleServiceImpl` will have either a setter for InfoSvc, or it will receive it through the constructor. If you use Spring, Spring will inject InfoSvc into MyExampleServiceImpl; if you don't, you'll have written that code yourself somewhere: `myExampleService.setInfoSvc(infoSvc)`, or something like that.

> File: **com/foo/gwt/MyModule/MyExample/MyExampleServiceImpl.java**
```
public class MyExampleServiceImpl extends RemoteServiceServlet implements MyExampleService
{
  protected InfoSvc infoSvc;

  public MyExampleServiceImpl() {}

  public MyExampleServiceImpl( InfoSvc infoSvc )
  {
    this.infoSvc = infoSvc;
  }

  public String getSomeData(String someData) throws SerializableException
  {
    return invoSvc.infoData(someData);
  }
}
```

In web mode, this works nicely: you set MyExampleServiceImpl.infoSvc via the constructor (perhaps Spring sets it for you, perhaps you hard-code setting it; it doesn't matter).

But if you try to make an RPC call to this class in hosted mode, invoSvc.infoData(someData) will throw a NullPointerException, because invoSvc will be null.

So what we are going to is stub InfoSvc. We are going to do three things:

Write _MyExampleServiceImplStub,_ which extends MyExampleServiceImpl. (The stub class will be written in italics to make it easy to distinguish the two).

In the constructor of _MyExampleServiceImplStub,_ we create a stub of an InfoSvc instance, and set the field infoSvc to this stub. (That's why the infoSvc field has to be protected in MyExampleServiceImpl, so that it can be accessed by extending classes like _MyExampleServiceImplStub._ )
In MyExample.gwt.xml, change 

&lt;servlet&gt;

 to point to _MyExampleServiceImplStub_ instead of MyExampleServiceImpl.

In hosted mode, when your client code makes an RPC call, GWT's Tomcat will instantiate _MyExampleServiceImplStub_ using its empty constructor. Inside this constructor, we will create the InfoSvc stub, and set it to the protected field infoSvc. From that point on, all calls to _MyExampleServiceImplStub_ will call code from MyExampleServiceImpl -- and that's the beauty of this setup. Apart from the stub data, we use our actual production, web mode code in hosted mode!

> File: **com/foo/gwt/MyModule/MyExample/MyExampleServiceImplStub.java**
```
public class MyExampleServiceImplStub extends MyExampleServiceImpl
{
  protected InfoSvc infoSvc;

  /** GWT's Tomcat will call this constructor on the first RPC call. */
  public MyExampleServiceImpl()
  {
    super();
    infoSvc = new InfoSvc(null, ...) // feed InfoSvc's constructor
                                     // whatever it needs to stay happy:
                                     // we only need to stub it!
    {
      public String infoData(String data)
      {
        return "Some stub data.";
      }
    }; // end InfoSvc stub
   } // end constructor
} // end MyExampleServiceImplStub
```
> Example module file: **MyExample.gwt.xml**
```
<module>

  <source path="client"/>
  <public path="public"/>

  <entry-point class="com.foo.gwt.myexample.client.MDSDemo"/>

  <servlet path="/gumby" class="com.foo.gwt.myexample.server.MyExampleServiceImplStub"/>

</module>
```

This approach is very clean. The only development-only code consists of the 

&lt;servlet&gt;

 tag in our `.gwt.xml` file, the Stub class, and the GWT.isScript() conditional in your client code. You don't have to touch any of this to deploy to web mode, or your production environment. The 

&lt;servlet&gt;

 tag is for hosted mode only anyway. The Stub class -- or classes, because you'll find you want to write more than one for different kinds of stub data -- can simply be excluded from deployment by editing your deployment scripts to ignore all files in the /server directory which end in "Stub".

An especially nice feature of GWT and this approach is that, perhaps for the first time in Java web development, a front-end Java developer really only needs to know front-end stuff (CSS, HTML, Javascript) and Java, and nothing else. She doesn't need to understand Spring, Hibernate, JSTL, JSP, Velocity, Struts, Webwork, MVC, SQL, or anything else in that seemingly endless list of technologies. With this stubbing approach, if a middle-tier programmer changes the signature of service, the GWT stubs won't even compile anymore -- it's hard for them to get out of synch accidentally!

Finally, front-end programmers can concentrate on what they do best, the front-end. This is one of the most valuable advantages GWT offers over other Java web application frameworks' UI solutions.