# What is Server Push? #

Web applications communicate using the HTTP protocol. HTTP has no support for allowing a server to notify a client; the model involves a strict request-response model where the **client** (webbrowser running a GWT app) makes a _request_ to the **server** (your web server running a servlet, for example), which must then _respond_ with the requested data. There doesn't seem to be any room in this protocol for allowing the **server** to send a notification to the **client**.

However, with some magic and creative implementation of the protocol you can 'fake' a notification from server to client, initiated by the server. This is called **Server Push**. This document will describe how to accomplish this, what the issues are, and how to integrate Server Push into GWT.

A common use case for Server Push is a web chat client. The 'event' of a new chat message needs to be communicated from the server to the client.

# Polling as an alternative #

Server Push is a more advanced and more efficient replacement for a technique called **polling**.

_Polling Example_: A web chat app application may make a request for the 200th line of an open chat conversation by making a request to an API: `http://my.chat.app/getChatLine?idx=200`.

If this line does not exist yet (because no one is chatting), the server will simply reply "Does Not Exist" and the client must now re-run the same query in a second or two, over and over again, until someone types something. This is polling. It has two significant problems:

  1. **Inefficient**: If this webapp polls the server every second for new chat lines, and the chat room sees on average 1 new text message very 30 seconds, 29 out of every 30 requests is just 'no new data'. Put differently, to send 1 line of chat, server and client communicate several kilobytes of HTTP content and headers. This is extremely inefficient use of bandwidth.
  1. **Slow**: Imagine for a moment someone types something almost immediatly after one of the clients received a 'no new messages yet' notification. This client will not see the new line for at least another second, as that's how much time will pass before the client will try again. Trying to improve efficiency by polling less often automatically means events get delayed longer.

Server Push avoids both of those problems.

# The Basic mechanism of Server Push #

Taking the same example of the previous section, your client makes a request for `http://my.chat.app/getChatLine?idx=200`. However, instead of returning 'does not exist' if this chat line hasn't been typed yet, the server intentionally doesn't respond. It effectively acts like a very slow server would. Once the 200th line is typed by someone, it 'wakes up' and responds with the line. The client doesn't have to keep asking for updates, and there is no poll delay either.

This idea is simple enough, but unfortunately HTTP, and all the tools around the HTTP protocol, such as most web servers, weren't designed with this idea in mind. As a result there are a large number of caveats.

# Server Push pitfalls #

## No Flush ##

The HTTP protocol has no 'flush'. A normal HTTP connection can get very complicated. A browser might connect to an internal proxy, which connects to an ISP proxy, which ends in your server park at a reverse proxy, and finally connect to your real web server. Any proxy server in the chain, and the web server itself, is allowed to cache responses. In fact, because in HTTP you cannot send an error response once you begin answering, most servlet containers will cache your entire response just in case your code throws an Exception and a response of '500 - Server Error' is warranted.

Because there is no 'flush', this can cause problems. Imagine this chat app. Instead of asking for the '200th' line, it just asks for a live stream. Every time a new chat message arrives on the server, you push this out on the HTTP connection, never actually closing it.

When testing your application this might work fine, but the HTTP protocol does not guarantee that a byte sent out will actually move all the way to the requesting client. Anyone behind a caching proxy will likely see chat messages only in large bursts instead of line by line.

The solution is to always close the connection immediately after you emit any sort of data. In our hypothetical chat app, we would have 2 practical use cases for the `http://my.chat.app/getChatLine?idx=200` request:

  1. The 200th line is available. In this case, respond with it (and optionally any other available lines) immediately and close the request. This is similar to any other normal request/response: The client asks something, and you reply with it.
  1. The 200th line is _NOT_ available. In this case, you do not respond with anything, but you wait. Once the 200th line is available, you stop waiting, respond with the 200th line, and close the connection.

In either situation, once you reply with anything, don't freeze the connection again. It's the only practical way to avoid flush problems.

  * **Practical Result**: Once you send anything in a server push connection, close the connection. The only time you can meaningfully freeze the connection is at the beginning. Once you start sending, don't freeze it again.

NB: There's another reason why this is useful. Internet Explorer basically never allows access to HTTP connections that haven't finished yet. Until you close it, your GWT code can't access the material you've sent so far.

## Spinners ##

Because the browser has no way of knowing the difference between your server being slow, and your server explicitly waiting to respond because it's waiting for an event to occur, browsers usually give an indicator to the user that the page is loading. This can be in the form of a _Loading..._ statement in the status line (Safari does this), or the so-called spinner (the browser logo that animates to indicate the page is still loading) continually spinning. This erroneous indication that the page is not finished loading may confuse some users. In practice there does not seem to be a way to avoid spinners in all browsers. However, XmlHttpRequests, the driving mechanism behind GWT's [RPC](http://code.google.com/webtoolkit/documentation/com.google.gwt.doc.DeveloperGuide.RemoteProcedureCalls.html) mechanism, the [RequestBuilder](http://code.google.com/webtoolkit/documentation/com.google.gwt.http.client.html) class, and the [HTTPRequest](http://code.google.com/webtoolkit/documentation/com.google.gwt.user.client.HTTPRequest.html) class, show the least erroneous 'loading' indicators amongst GWT-compatible browsers of all the various ways to create Server Push connections.

  * **Practical Result**: Be aware that some users may be a bit confused about your page appearing to still be loading.

TODO: Check for each browser what actually happends and report on it. I can tell with certainty that Safari2 shows a 'loading...' statement in the status bar, which is not actually visible by default, and nothing else.


## Timeouts ##

Because proxies, the web browser running your GWT code, and sometimes even your web server can't tell the difference between a slow or hanging bit of server-side code, and code that is simply waiting for an event, at some point they may assume you're just broken and assume the response is an error.

As a practical matter this means you should never let your Server Push connections last over a minute. As a result you need to employ a tactic called **connection refresh**:

Every 50 seconds or so, if no events occur during that time, your server should respond 'no events', and your GWT code, upon receiving the 'no events' flag, simply re-establishes the connection. Effectively we still poll, but we only do so once a minute instead of once a second.

  * **Practical Result**: Respond with 'no new events' after about 50 seconds or so, and make sure your GWT code understands this response and opens a new connection. Make sure you always use an event tracking number so that your GWT code doesn't miss events that occur in the downtime between this 'connection refresh'.

## 2 connection limit ##

All GWT-compatible browsers will only make 2 connections to 1 server. If there are 3 resources to fetch from the same server, the third resource will be queued until one of the first 2 connections is completed. A Server Push connection counts as 1 of these two connections, which poses a problem. For example, two simultaneous Server Push connections to the same server completely block any further attempts by the browser to talk to this server.

One way to alleviate this problem is to make sure only 1 server push connection is open at any point in time. If you need more, _multiplex_ all events onto the same server push channel. (This is effectively what comet and cometd is - it's a multiplex protocol and architecture. In practice Server Push is often equated with the term 'comet' but this isn't technically correct).

Another method that helps is to load as much data as you can from different servers. Not all data can be downloaded from different servers (due to the SameOriginPolicy limitations) but images, CSS, and external javascript files can all be loaded from other servers.

A google maps application could for example load the map images from `img.maps.google.com` instead of `maps.google.com`. The browser would perceive the two hosts as different and thus allow 2 connections to each, even if they both resolve to the same IP address (=go to the same physical server machine).

  * **Practical Result**: Never create more than 1 server push connection. If possible, load images, CSS, and external javascript from another server, for example from `data.myserver.com` instead of `www.myserver.com`.

## Server Threads ##

In order to 'freeze' a response, you would usually freeze the thread. In a servlet, you might for example do something like:

```
  synchronized ( chatMessages ) {
    while ( chatMessages.size() < 200 ) try {
      chatMessages.wait();
    } catch ( InterruptedException e ) {
      respond("Server interrupted. Chat Session Closed.");
    }
    respond(chatMessages.get(200));
```

where, if anyone types something, you wake any frozen threads by using:

```
  synchronized ( chatMessages ) {
    chatMessages.add(theNewChatLine);
    chatMessages.notifyAll();
  }
```


Unfortunately, this means each Server Push connections ties up a thread. Usually web servers (including Tomcat, Jetty and Apache) have a limited number of threads and once they are all in use, any new requests will be ignored because the server thinks it's very busy and can't accept more connections.

You could up the thread pool count in your webserver's configuration, but there are more robust, scalable solutions available, often called 'continuations'.

The idea behind a continuation is that you tell your webserver to freeze the connection. By employing low level Input/Output directives, the webserver can do this without tying up threads.

There is not (yet) a standard way to do this in the servlet spec, but here are the links to the documentation on continuations for the two most often used servlet containers:

  * [Jetty Continuations](http://docs.codehaus.org/display/JETTY/Continuations)
  * [Tomcat Advanced I/O support](http://tomcat.apache.org/tomcat-6.0-doc/aio.html)

Note that some other web frameworks simply do not support this. At time of writing, there is no way to avoid tying up threads in PHP, Ruby on Rails, Django (Python), Turbogears (Python), and many others. Web Frameworks that can definitely handle continuations are anything written in Erlang, Twisted (Python), and Seaside (smalltalk).

  * **Practical Result**: Use continuations if your web framework supports them.


# Server Push in GWT #

Because of the reasons explained in previous sections, the most scalable, compatible way to implement server push is the **Wait, Respond, Close, Re-Open** paradigm:

  * Wait: When the GWT code makes a call to your server for some data that you don't have yet, freeze (wait)
  * Respond: Once the requested data is available, respond with it
  * Close: Then, close the connection.
  * Re-Open: Once your GWT code receives the response, immediately open up a new connection to query for the next event.

You can implement this paradigm using any of the GWT server call mechanisms; GWT-RPC, RequestBuilder and HTTPRequest are all valid options. However, you **must** keep track of which events your GWT code has received so far. In practice this means keeping a counter and sending it along every request. If you are using GWT-RPC, you should make this counter a field in the [POJO](POJO.md) object you are sending. If you are using RequestBuilder or HTTPRequest, include this counter in the [JSON](http://www.json.org/) or XML data that you are sending to the server. Similarly, all events that you send from server to client need to include their 'event number'.

TODO: Add some sample code here for both GWT-RPC and JSON-over-RequestBuilder that shows how to make the call, then check if the server wants us to just re-try (to avoid timeouts), stop bothering the server altogether (stop), or process incoming events and then re-try.