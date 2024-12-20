---
title: Initialise the SDK
description: Initialise the SDK
---

# Initialise the SDK

The first thing you’ll need to do after adding the SDK is to initialise it in a custom <strong>Application</strong> subclass in your Android app.

{% highlight java %}
public class MyApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    AwesomeAds.init(this, true);
  }
}
{% endhighlight %}

Where the <strong>initSDK</strong> method takes a boolean parameter indicating whether logging is enabled or not. For production environments logging should be <strong>off</strong>.

The SDK can also be initialised with an options dictionary. The options dictionary is used to set additional tracking information that will be sent when events are fired from the SDK in the form of key value pairs:

{% highlight java %}
public class MyApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    AwesomeAds.init(this, true, mapOf("key" to "value"))
  }
}
{% endhighlight %}