---
title: Parental gate
description: Parental gate
---

# Parental gate

The Parental gate is an optional UI element you can add to your ad placements so that when a user clicks on an ad he is presented with a popup asking him to solve a simple math sum.

Its role is to prevent very young users from simply clicking on an ad and instead ask their parents for guidance.

You can enable it like so:

{% highlight java %}
// enable Parental gate on one banner placement
mybanner.enableParentalGate();

// enable Parental gate on all interstitial ads
SAInterstitialAd.enableParentalGate();

// enable Parental gate on all video ads
SAVideoAd.enableParentalGate();
{% endhighlight %}

It can subsequently be disabled by calling:

{% highlight java %}
// disable the Parental gate for a particular banner placement
mybanner.disableParentalGate();

// disable the Parental gate on all interstitial ads
SAInterstitialAd.disableParentalGate();

// disable the Parental gate on all video ads
SAVideoAd.disableParentalGate();
{% endhighlight %}

The final result will look something similar to this:

![image-title-here]({{ site.baseurl }}/assets/img/IMG_06_ParentalGate.png){:class="img-responsive"}

These are the default values:

| Parameter | Value |
|-----|-----|
| Parental gate | Disabled |