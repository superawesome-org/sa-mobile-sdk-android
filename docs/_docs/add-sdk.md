---
title: Add the SDK through Gradle
description: Add the SDK through Gradle
---

# Add the SDK through Gradle

The simplest way of adding the Android Publisher SDK to your Android Studio project is to download the AAR library through Gradle.

The first step is to include the following Maven repository in your module’s <strong>build.gradle</strong> file (usually the file under MyApplication/app/):

{% highlight gradle %}
repositories {
    maven { url "https://aa-sdk.s3-eu-west-1.amazonaws.com/android_repo" }
}
{% endhighlight %}


Next you can to add the SDK as a dependency. This will contain everything you need in order to load and display banner, interstitial and video ads.

{% highlight gradle %}
dependencies {
    implementation 'tv.superawesome.sdk.publisher:superawesome:{{ site.latest_version }}'
}
{% endhighlight %}

Once you’ve added the Android Publisher SDK, you can access all functionality by including:

{% highlight java %}
import tv.superawesome.sdk.publisher.*;
{% endhighlight %}
