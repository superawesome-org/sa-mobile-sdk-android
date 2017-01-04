CHANGELOG
=========

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
