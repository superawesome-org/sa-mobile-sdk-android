CHANGELOG
=========

5.4.9
 - Banner ads don't fire up an "adClosed" event on first load
 - Fixed a bug with the Unity plugin that meant that "adFailedToLoad" events were sent back to Unity as "adFailedToShow" for video ads.
 - The video ad close button will appear by default after 15 seconds of content playing, meaning that disabling
the close button will have effect only for the first 15 seconds of play, or for ads shorter than 15 seconds. The close button will appear once the ad has ended nonetheless in that scenario.

5.4.8
 - Added a new SAImageUtils class in the SAUtils library. This helps create bitmaps and drawables from base colors, base64 encoded strings or files. It can also add round corners.
 - Refactored the video player library to be more simple and reliable. All drawables in this lib have been replaced by calles to SAImageUtils, so there are no dependencies, just code.
 - Refactored the main SDK banner, interstitial, video and app wall classes to have all their UI elements created in code and not dependent on xml layouts. This removes the need to add a layouts folder when you integrate the SDK as .jar files.
 - Refactored the main SDK banner, interstitial, video and app wall classes to have all their resources (drawables, pngs, etc) created by using the SAImageUtils class in the SAUtils library. This removes the need to add a res/drawables folder when you integrate the DSK as .jar files.
 - Refactored the main SDK video class to work with the new video player library.
 - Overall the SDK now does not depend on layouts or drawables to perform it's task and is more versatile when it comes to video playing. Also, when adding the SDK as a series of .jar dependencies, you don't have to worry about adding resoures, etc., just updating your manifest file.  

5.4.7
5.4.6
5.4.5
5.4.4
5.4.3
5.4.2
5.4.1
5.4.0
 - Refactored the SAWebPlayer class to load & display HTML content at a 1:1 ratio. Then that gets scaled using Matrix transforms to the desired width & height to fit a container properly. This means that ad scaling will not happen in HTML anymore, but in native code.
 - Refactored the SABannerAd & SAInterstitialAd classes to use the new web player and to not reload data on screen rotations, etc.
 - Improved SABannerAd to work with dynamically created fragments. This fixes a bug in which more than one banners displayed on the same activity would cause a crash.
 - Refactored the SABannerAd class to close an existing ad if a subsequent "load" method is called so as to reset the ad and keep a consistent visiual & internal state.
 - Refactored the SAWebPlayer click mechanism to be more simple and avoid a series of potential JavaScript issues. Now there are simply no briges between the underlining web view and native code.
 - Refactored the ad loader SAProcessHTML class to output simple encapsulating HTML for image, rich media and tag ads.
 - Added support for the adEnded event, fired when a video ad ends (but not necessarily closes)
 - Added support for the adAlreadyLoaded event, fired when an Insterstial, Video or AppWall tries to load ad data for an already existing placement
 - Added support for the clickCounterUrl; that's been added as part of the native Ad Creative model class and is now fired when a user clicks an ad.
 - Fixed a potentail NulLPointerException in SABannerad

5.3.13
 - Improved the SuperAwesome CPI to do the following:
 	- The CPI install event has to be triggered manually now by the SDK user (usually when the app starts). The CPI method now is accessible through the main SuperAwesome singleton interface and returns an async callback to the SDK user to indicate if the Ad Server recognized the install as valid or not.
	- The CPI install event now checks the user device against a list of possible app packages that the ad server sends to the SDK. If one of the packages sent by the ad server is also found on the user device, that information is passed down to the install event so that the Ad Server counts that install as being much more statistially significant.
 	- If the app using the SDK intercepts an INSTALL_REFERRER broadcast, it tries to parse that data and send a "custom.reffered_install" event to the ad server. These types of really direct installs (where the correlation is strict) will now be counted separately, so that advertisers will know they have N impressions/clicks, M statistical installs, out of which P are directly correlated installs.  
 - Made the Video ad close button appear at the end of the ad, if it's set to be invisible and the ad
 is set not to automatically close at the end. This removes an issue where if you disable both the close button and auto-close at end, the video would never be closed. This also improves the experience with regards to rewarded videos since now you can trigger your reward UI while still having the video in the background.
 - Refactored some of the SuperAwesome libraries that go in supporting the main SDK
        - SANetworking added a new class that downloads files from a list, sequentially
        - SAVASTParser was updated and now recursively parses VAST tags
        - SAAdLoader had the VAST & AppWall loading classes removed. Now it depends on SAVASTParser to figure out a VAST tag and the sequential file list downloader to get AppWall data
        - SAModelSpace added two classes needed for VAST parsing: SAVASTAd and SAVASTMedia
 	- Removed VAST elements from the SAAd model class, since now they're contained in SAVASTAd and SAVASTMedia. SAAd models are not associated with VAST and the VAST parser will not try to produce a SAAd model, but a SAVASTAd model.
	- Added methods to all enum definitions to initiate an enum value from an integer or string or vice-versa. This has taken the burden of getting correct enum values from JSON / Models to the enums, not the parsers.
        - SAEvents was simplified in relation to handling MOAT
        - SAUtils now has SAAlert and SALoadingScreen as classes (same for Android)
        - Small refactoring for the AIR, Unity & MoPub plugins
        - Renamed a lot of callbacks used by the SDK to include the "sa" particle at the start (so as not to have conflicts with other block definitions) and follow the "saDidDoThisOrThat" pattern.
        - Renamed a lot of enums to contain the "SA_" particle so as not to have conflicts with other
 C enum definitions.
 - Added, updated or improved tests for:
        - SANetworking
        - SAModelSpace
        - SAAdLoader
        - SAEvents
        - SAUtils
 - Added comments to each library and SDK file 

5.3.12
 - Minor change - update the SAAdLoader dependency to change the "bundle" parameter to "sourceBundle" to work with the actual ad server naming convention


5.3.11
 - Minor change - update the SAAdLoader dependency to add the "bundle" parameter to every click, impression, event; needed for CPI

5.3.10
 - Updated the AIR & Unity plugins to be more modular. That means that in both of them the code is not bundled any more into one big class or file, but split into multiple classes / files, such as SAAIRBannerAd, SAUnityVideoAd, etc. This not only spearates concerns but also makes it more manageable and easier to spot errors.
 - The AIR & Unity plugins can now override the main SDK version & sdk type. Meaning that when bundled as part of any of those SDKs, the Android SDK will report as "air_x.y.z." or "unity_x.y.z" instead of "android_x.y.z". This makes reporting so much more accurate. 


5.3.9
5.3.8
 - Made the Video ad close button invisible by default. Developers will now actively have to enable it in our SDK. In historical cases where this has been a problem it will mean an increased VCR rate.
 - Refactored the way the SDK works with default values. A default "SDK state" is now stored by the main SuperAwesome singleton. This dictates banner background color, default orientation, whether the close button is visible or not on video and all types of default values for everything that is customizable.
 - Refactored the AIR & Unity plugins to be more robust and do more error checking and to use default values in case any of the plugins don't somehow send values.
 - Refactored the MoPub plugin to be more robust and do more error checking.
 - Updated dependencies

5.3.7
 - Fixed another issue with SABannerAd and the background color - namely that at the start, unless calling mybanner.setColor(true/false) the banner background color would be the old color.

5.3.6
 - Updated SABannerAd and SAInterstitialAd background colors to 224, 224, 224 

5.3.5
 - Added generic setters for each of the properties needed to configure loading & playing for banners, interstitials, videos and app wall
 - Banners:
   - load parameters: test mode, configuration (production or staging)
   - play parameters: parental gate, background color
 - Interstitials:
   - load parameters: test mode, configuration (production or staging)
   - play parameters: parental gate, orientation (any, portrait, landscape)
 - Videos:
   - load parameters: test mode, configuration (production or staging)
   - play parameters: parental gate, orientation (any, portrait, landscape), close button, auto close, small click button
 - App Wall:
   - load parameters: test mode, configuration (production or staging)
   - play parameters: parental gate

5.1.0
- Added SAAppWall as a new ad format that's supported by the SDK

5.0.0
 - Base version 5
 - Added new publisher interface for SABannerAd, SAInterstitialAd, SAVideoAd
 - In this new version each type of ad is responsbile for loading & playing the ad unit
 - Also, onse an ad has been played, it has to be loaded again
