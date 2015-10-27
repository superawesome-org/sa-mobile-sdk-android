If you already have MoPub ads serving in your app, but want to integrate SuperAwesome as well, without having to directly use the AwesomeAds SDK, you can follow the steps below:

#### Integrate the SDK

The first thing you should do is integrate the SDK in your current app by following the instructions in [Getting Started / Adding the Library to Your Project](https://developers.superawesome.tv/docs/androidsdk/Getting%20Started/Adding%20the%20Library%20to%20Your%20Project?version=2).

This will add the SDK to your current project. 

#### Download Adapters

Next, you'll need to download a list of files called Adapters:

  * [SuperAwesomeBannerCustomEvent.java](https://raw.githubusercontent.com/SuperAwesomeLTD/sa-mobile-sdk-android/master/superawesomesdk/sa-sdk/mopub/SuperAwesomeBannerCustomEvent.java)
  * [SuperAwesomeInterstitialCustomEvent.java](https://raw.githubusercontent.com/SuperAwesomeLTD/sa-mobile-sdk-android/master/superawesomesdk/sa-sdk/mopub/SuperAwesomeInterstitialCustomEvent.java)

These will act as a convenient bridge between SuperAwesome and MoPub, so that you don't have to write any aditional line of code.
You'll also need to place them in your project. To do this, in your `/app/src/main/java/com` folder, create a folder called `mopub` and inside it another one called `mobileads`. Here copy the two files.

#### Setup a MoPub Custom Network

From your MoPub admin interface you should create a `New Network`

![](img/IMG_07_MoPub_1.png "Adding a new Network")

Form the next menu, select `Custom Native Network`

![](img/IMG_07_MoPub_2.png "Creating a Custom Native Network")

You'll be taken to a new page. Here select the title of the new network

![](img/IMG_07_MoPub_3.png "Create the Super Awesome Network")

And assign custom inventory details for Banner and Interstitial ads:

![](img/IMG_07_MoPub_4.png "Setup custom inventory")

Custom Event Class is:
  * for Banner Ads: `com.mopub.mobileads.SuperAwesomeBannerCustomEvent`
  * for Interstitial Ads: `com.mopub.mobileads.SuperAwesomeInterstitialCustomEvent`

Notice these are identical to the names of the four files you downloaded in step one.

Custom Event Data is always required, and must be given in the form of  JSON:

```
{
	"placementId": 5692,
	"testMode": true,
	"parentalGateEnabled": true
}

```

If you don't yet have a Placement ID for Awesome Ads, check out the [Getting Started / Registering Your App on the Dashboard](https://developers.superawesome.tv/docs/androidsdk/Getting%20Started/Registering%20Your%20App%20on%20the%20Dashboard?version=2) section.
