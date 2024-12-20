# [9.4.0](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v9.3.2...v9.4.0) (2024-07-18)


### Bug Fixes

* **events:** reduce number of retries in case of network failure from 5 to 1 ([0fdd31e](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/0fdd31ed1e4540f74a95c784eff2c507b93abc25))
* **video:** give reward if video errors after a set number of millis ([50a7413](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/50a74136cba7168cc4004cf9c65f8a906e975e65))
* **video:** video flipping when orientation is set to landscape ([55bdaa2](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/55bdaa2bafef8962bae6823c4ddcd860f68be29d))


### Features

* new feature flag system implementation ([26f9e4e](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/26f9e4ed9510c8f3153388cd631ba8054e916df9))

## [9.3.2](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v9.3.1...v9.3.2) (2024-01-29)


### Bug Fixes

* **video:** null pointer exception when parsing VAST ([ad6f9b9](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/ad6f9b99aafe3c79400ebc2d9c115a2d4acfe552))

## [9.3.1](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v9.3.0...v9.3.1) (2024-01-19)


### Bug Fixes

* **video:** added exoPlayer to android base ([13b4728](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/13b4728a576ad19e42c379dac196643599e31c77))
* **VideoPlayer:** Added AdPlaying and AdPaused events to the Video player for VAST and Direct Video. ([#551](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/551)) ([f64b15d](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/f64b15d3ff07cb49e9e895d31f2bf8b3e862b1b5))

# [9.3.0](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v9.2.3...v9.3.0) (2023-12-15)


### Bug Fixes

* **video:** possible crash when destroying the media player ([f7d0fdd](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/f7d0fddf09bd306a50739248abc3da31e4800751))
* **vpaid:** adPaused not being received properly ([5f63a48](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/5f63a48bb4c86ed921ca8ea7d512d4350f92dd6d))


### Features

* **close-button:** add custom close button delay timer ([a1f61b7](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/a1f61b72865464316a4426f5bc732b8234ce6442))

## [9.2.3](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v9.2.2...v9.2.3) (2023-11-03)


### Bug Fixes

* **config:** app crash when loading banner with multi id ([620c6c6](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/620c6c62081f7f9a7e1ee91f5f78c5d10de13bf2))
* **config:** publisher configuration null when loading multi id ([b078e5d](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/b078e5de44156851c8f79ebe956fa9f9bade7c51))
* **parental-gate:** parental gate not showing banner/interstitials ([3405bd8](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/3405bd8f1f828f68aadacd7d22573dd9841c0e9f))

## [9.2.2](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v9.2.1...v9.2.2) (2023-10-31)


### Bug Fixes

* **banner:** crash when parent activity doesn't implement LifecycleOwner ([47e4f6e](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/47e4f6e49fc9828e2003e2b0f719bd7c77c60ab0))
* **ConnectionType:** Added 5g connection type to common. ([#450](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/450)) ([979a0f6](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/979a0f68607195c9ad5a239ec3e7674e12f192b4))
* **JSBridge:** Added webSDKReady event to the JS Bridge. ([#451](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/451)) ([03d45b1](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/03d45b1ce0c017ae00e0406b954c774e24760bd0))

## [9.2.1](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v9.2.0...v9.2.1) (2023-09-29)


### Bug Fixes

* **unity:** missing library import causing build issues ([7a3d6a0](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/7a3d6a09b8aca1e74455b687ed01ac95c9117aa4))

# [9.2.0](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v9.1.2...v9.2.0) (2023-09-29)


### Bug Fixes

* **ad:** adjust openRtbPartnerId param order in all ad load calls ([6ef3599](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/6ef35994d35eb4688f4d8fdfc40be2a7bf735756))
* **events:** Add dwell time event to banners and interstitials ([de18b65](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/de18b6519616ad840762bb8f53f50ab16a5a5ec4))
* **events:** Dwell time being sent twice at the 2 second mark ([fe1bec7](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/fe1bec7e18761612ce4ced127d48bce46dc1a4df))
* **events:** Reduce dwell time event tick to every 5 seconds ([1454a4d](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/1454a4d9af474ddd84ccbfc07f3c1e056e31abf5))
* **events:** Reduce dwell time event tick to every 5 seconds (common) ([d6ec827](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/d6ec827dd01c2a778d6f3550fa05cad54dded0da))
* **Google:** Updated Google Play Ad Services to 22.4.0 to add Kotlin 1.9.0 support ([#447](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/447)) ([385f1dd](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/385f1dde7ec5a47be12e15ad6af9062666c12331))
* **interstitial:** crash when opening interstitial ([e6d9883](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/e6d98839a6b47b5c02a0c53a0632b8a5045c38ee))
* **metrics:** add load time metric for banners and interstitials ([1e74057](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/1e7405762bb5719a646197d7c6dab64aa846b8ab))
* **metrics:** add metric tags to all performance metrics ([f545dbb](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/f545dbbc6c8c4ac5e6a1349480d9a5afc9aea79b))
* **metrics:** add render time for rich media interstitials ([da749f1](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/da749f18d4cdba5b6bb8c08c141626f357ce90e5))
* **metrics:** Add render time metrics to VPAID ([7368f17](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/7368f172dc16fbbc9aeb13b0a6be6c64f3e8b0c5))
* **metrics:** enable load metrics for all video types ([e22931c](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/e22931c2ebaa98bdaa1c2f2b687353f077da8275))
* **SafeAd:** Resized Safe ad to be smaller ([#408](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/408)) ([051b47d](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/051b47d9a3e81389e6d78e631512ed5d9a2eb209))


### Features

* **ad:** Add optional openRTBPartnerId to ad load methods on the Unity SDK. ([ebaa02a](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/ebaa02a105a5cbebe0ca75c3e01cb5b9bdd82ed5))
* **ad:** Add optional openRTBPartnerId to ad load methods. ([e9e2b05](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/e9e2b05cc1930885ecec3314ed878c450e4b0bd8))
* **analytics:** added the ad request id to event and impression requests ([#421](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/421)) ([e851c81](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/e851c81731f829fc5fe24004520d814f5ef12d01))

## [9.1.2](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v9.1.1...v9.1.2) (2023-08-23)


### Bug Fixes

* **vpaid:** not loading VPAID ads when supplying creativeId and lineItemId ([3d5e495](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/3d5e49552ed610e67ede40aa3e24f5fa03bff097))

## [9.1.1](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v9.1.0...v9.1.1) (2023-08-17)


### Bug Fixes

* **Ads:** Use the random number returned from the ad response ([#362](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/362)) ([64eee68](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/64eee68e46295aa861200ecb1f79dfdecb1e75ed))
* **Ads:** Use the random number returned from the ad response for the ad events ([#360](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/360)) ([42611fc](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/42611fcba7b0a5df64f2242f85560b2c408be5a3))
* **ci:** gradle crashing on CI ([7c1e0b2](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/7c1e0b2b124bc6877773c3177823fdcf665d89ba))
* **Releases:** Added download links to release docs step of the CI ([#361](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/361)) ([10072cd](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/10072cd9689aa64cbc68f8222b2f1bb1325f9698))
* **rnd:** Reverted the rnd changes to allow a release in the base module. ([#390](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/390)) ([148404e](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/148404e50997c87e869df3a5e401db31b2dcdc6d))
* **video-player:** bufferQueue has been abandoned error message ([03c78cd](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/03c78cd681c616628aa7e50ce8a4d8e16bfe81fb))
* **Video:** Added additional logs and rearranged destroy methods ([#393](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/393)) ([fde9712](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/fde9712e27de809bc8849782b95044502ac93983))

# [9.1.0](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v9.0.2...v9.1.0) (2023-06-13)


### Bug Fixes

* **Common:** Added missing CI code to fix version incrementing when releasing. ([#350](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/350)) ([ec9b3bc](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/ec9b3bcaa36abb9afebebe0b7a075409ff03d5af))
* **Video:** Added additional safe guards against empty Ads ([#352](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/352)) ([2801515](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/28015155e0c8cd0f4c5881c6d3297dd169bd666c))


### Features

* **CloseButton:** The close button is made visible when an ad ends if the ad is configured not to dismiss automatically ([#349](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/349)) ([318b260](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/318b260640eaee55cd55bde6578681baa3aa7975))

## [9.0.2](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v9.0.1...v9.0.2) (2023-05-16)


### Bug Fixes

* **CI:** Tweaks to the CI to fix issues around publishing ([#342](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/342)) ([fdeedb4](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/fdeedb4d32e0f2ce05ee0edb42cc58fcddf61832))

## [9.0.1](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v9.0.0...v9.0.1) (2023-05-15)


### Bug Fixes

* **CI:** Added release and unit test step for the common module ([#329](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/329)) ([e5be15a](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/e5be15a83bc9ed31b53db99c837a5087d377e334))
* **ManagedAd:** Changed how adEnded behaves to bring it into line with iOS and Base ([#331](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/331)) ([8f5cd5a](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/8f5cd5adddec9bc9a16890fceb220a473eb70d41))
* **ParentalGate:** added validation for an empty string to the parental gate ([#333](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/333)) ([cf4b11b](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/cf4b11b3240631635015f01de139975fab4b3495))
* **UITesting:** Adding a test rule to retry the tests in order to reduce flakiness ([#318](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/318)) ([f03d712](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/f03d712d09d2ee23083f084ac148da31dde07453))
* **Video:** Added Play Pause for Videos in the Base module ([#320](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/320)) ([4cea1ad](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/4cea1adc8840c0443055354e3593f7f81390286d))
* **Video:** Added Video Play and Pause functionality to Direct and managed videos in Common ([#321](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/321)) ([c37f163](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/c37f1632b59920d8b7ecbfb0aeb915b8382b8867))
* **Video:** Remove the gradient from video ads and make the timer background darker ([#322](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/322)) ([fc3c583](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/fc3c58367d8a3d5276dc2af15f0899287e69a7d3))

# [9.0.0](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.5.8...v9.0.0) (2023-04-19)


* Feat/ag 3023 play pause js bridge (#315) ([5760456](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/5760456014cfc73545eb76617eac5d307fe7eb38)), closes [#315](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/315)


### BREAKING CHANGES

* The minimum supported android version increased to 19

* chore(): formatting

## [8.5.8](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.5.7...v8.5.8) (2023-04-14)


### Bug Fixes

* **Bumper Page:** Prevent clicks from registering ([#314](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/314)) ([a10e6b7](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/a10e6b73d5b4fed44e8f8bdfa96e9d2fae62291b))
* **Unity plugin:** Adds missing settings to the unity plugin for Interstitial and Video ads ([#316](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/316)) ([bea0207](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/bea020703e422087fd2e61adfef624117fcf6014))

## [8.5.6](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.5.5...v8.5.6) (2023-03-23)


### Bug Fixes

* **admob:** Concurrency issue in `EventListener` is fixed ([#306](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/306)) ([2fb9d22](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/2fb9d22bc6cd1843960426c7815b636a1f6fc9ff))

## [8.5.5](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.5.4...v8.5.5) (2023-03-21)


### Bug Fixes

* **admob:** Allow event listener to listen multiple ad loads ([#299](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/299)) ([706d239](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/706d2397cc03aa4498d1a3ca9a50b5626ad07bf7))
* **Dependencies:** Removed dependencies that cause conflicts when consuming the jar files in Unity. ([#296](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/296)) ([308d4ea](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/308d4eaf120a493d02ea38fe88922a8a3ab44c71))
* Handles the case where the device doesn't have any browser to open web URLs ([#295](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/295)) ([1cfcfa7](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/1cfcfa7d3200fcaac3ce807e0f2930de22ced1f7))
* **Unity:** Removed the Unity-Classes.jar from the SAUnity module ([#298](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/298)) ([968da70](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/968da708dcaf4820e8e3895640ce10d8f8209746))

## [8.5.4](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.5.3...v8.5.4) (2023-02-28)


### Bug Fixes

* **Syntax:** Improved the syntax of some of the video files ([#285](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/285)) ([b9961f0](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/b9961f05dc20279e704869877a584c3df92a6506))

## [8.5.3](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.5.2...v8.5.3) (2023-02-23)


### Bug Fixes

* AdMob custom adapters so that the correct events are passed through the admob plugin from the awesome ads sdk ([#280](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/280)) ([3cfeee8](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/3cfeee8865b2ba9b9a417b6a9c32a746b71f97c0))

## [8.5.2](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.5.1...v8.5.2) (2023-02-16)


### Bug Fixes

* Added a new placement to support further VPAID testing ([#241](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/241)) ([d94440d](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/d94440d6a5703d2e89e6487aad5a1bb03b9d918f))
* **Bumper:** AAG-2795: Added missing theme to Bumper ([#255](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/255)) ([e2124bf](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/e2124bf3b094ec4fd4a3b06c4b3c7d0bc1c549d0))
* **Version:** Reverted version and corrected circle ci script to run release tag phase ([#274](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/274)) ([2c33a38](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/2c33a3811a530e856fe6d5e9a3f4ef6975f2592b))
* **Version:** Reverted version to 8.5.1 ([#275](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/275)) ([73efaa2](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/73efaa2567dc17d6638710f2d37dcf0dd2efd709))

## [8.5.1](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.5.0...v8.5.1) (2022-12-07)


### Bug Fixes

* CI semantic release fix ([#238](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/238)) ([8ebacd8](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/8ebacd893fd8f209218a59d895dcbcda40f7e38e))
* This PR prevents the app from going to sleep when playing video ads ([#237](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/237)) ([d74c716](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/d74c7167d5d685ed1ba371e4e3a57c92fb515d8d))

# [8.5.0](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.4.3...v8.5.0) (2022-11-17)


### Features

* adding a new button to the video controller to mute and unmute ([#228](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/228)) ([a998600](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/a998600f7b42b41d27f6312ba2aa788758f6c2f2))

## [8.4.3](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.4.2...v8.4.3) (2022-10-26)


### Bug Fixes

* when the `WebView` fails to load ([#227](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/227)) ([b29f503](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/b29f50366c7a768074901a828dd0e630946d9ef0))

## [8.4.2](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.4.1...v8.4.2) (2022-10-17)


### Bug Fixes

* Update SDK version to 8.4.2 ([#226](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/226)) ([b910d59](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/b910d593b7b83be5e866b591690e239ee00ea8b0))

## [8.4.1](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.4.0...v8.4.1) (2022-10-17)


### Bug Fixes

* crash when the video is completed ([#225](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/225)) ([ca545c1](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/ca545c1d043a0a1a1a6752eced11a1c3cfcc40d6))

# [8.4.0](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.3.7...v8.4.0) (2022-10-05)


### Features

* add immediately close button functionality to the interstitial ads ([#222](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/222)) ([2842a03](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/2842a03a0ce1af0c7a661c3b4b35373b23dd3338))

## [8.3.7](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.3.6...v8.3.7) (2022-09-06)


### Bug Fixes

* approval step in the circle ci ([#176](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/176)) ([d12b2c4](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/d12b2c4c01e623ec3d24d58c408868e7876cecb1))
* Remoe tags on semantic release and requires on prep_deploy ([#201](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/201)) ([037e13b](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/037e13b6ce5c73df25367953e5fda214d5cb3202))
* swapping filter order ([#200](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/200)) ([79962a3](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/79962a355ec1fdac1f2844ddc8cbd1480349954d))
* triggering semantic release on dry run approval ([#199](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/199)) ([da1045e](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/da1045e5d56793c5309b8fc4da77de923440c86e))
* Vast ads that are wrapped were not loading correctly. Fixed the recursive loading issue ([#179](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/179)) ([6d51f60](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/6d51f60cf8df41430ef9ad3b6fef9afc37f2a893))
* Fixed multiple adEnded and adClosed callbacks
* Exposed the SDK version number via a public method

## [8.3.6](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.3.5...v8.3.6) (2022-06-24)


### Bug Fixes

* Version number store updates formatting ([75ac1df](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/75ac1df6fb1bf756ec3f0260021abc58afd82832))

## [8.3.5](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.3.4...v8.3.5) (2022-06-23)


### Bug Fixes

* Video Ad crashes when the Warning dialog is accessed even after the activity is closed ([#165](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/165)) ([146be04](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/146be04c369aaa6b3a6c28eb1e7800f7cc1b36ed))

## [8.3.4](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.3.3...v8.3.4) (2022-06-17)


### Bug Fixes

* Close button to display immediately without a delay ([#163](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/163)) ([e41f4fa](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/e41f4fa16232bab6aa4e22d6da912626af272e19))

## [8.3.3](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.3.2...v8.3.3) (2022-05-25)


### Bug Fixes

* loading an ad with multiple parameters ([#157](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/157)) ([e83ee11](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/e83ee117fd2071969bfec3eac7d7140ec8a6d82d))

## [8.3.2](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.3.1...v8.3.2) (2022-04-26)


### Bug Fixes

* Fixed the video player not resizing dynamically ([f7c6f68](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/f7c6f684c294790d6823df13bc95eb43deaae7ba))

## [8.3.1](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.3.0...v8.3.1) (2022-04-20)


### Bug Fixes

* fix for missing ads on Unity Android ([6936809](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/6936809e7f6309d07584ec86128e4c9687c085fe))

# [8.3.0](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.2.4...v8.3.0) (2022-04-11)


### Bug Fixes

* ad sounds continue to play even after minimising the app. Now, it is paused/resumed after the minimising. ([#145](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/145)) ([281d669](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/281d669566eb4c123f494dee82f1528526c332dc))
* safe af logo appearing multiple times when KSF ad is loaded ([#143](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/143)) ([9e55ef4](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/9e55ef4cbe36598491df03b5484f6b25843a005d))


### Features

* add timestamp ([#144](https://github.com/superawesome-org/sa-mobile-sdk-android/issues/144)) ([e6d6ae1](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/e6d6ae158d96d80b582cbbd5bc4ae64cc5c3bc1c))
* Complete VPAID support with final features ([#146](https://github.com/superawesome-org/sa-mobile-sdk-android/pull/146)) ([c97948c](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/c97948c40490dfc08a583277b8a9883edb18b87f))

## [8.2.4](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.2.3...v8.2.4) (2022-02-02)


### Bug Fixes

* generate token in semantic_release step ([16f8133](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/16f8133f1e5215a6cd97cbe76a3e228dc4055ea2))

## [8.2.3](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.2.2...v8.2.3) (2022-02-02)


### Bug Fixes

* use gha-token-generator and set GITHUB_TOKEN env var ([0bb5353](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/0bb5353628f17b6d42a1d7dbe716f5f1c49a64b3))

## [8.2.2](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.2.1...v8.2.2) (2022-02-02)


### Bug Fixes

* add KSF placements ([5536548](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/55365481f6d62a59e2d0e93e4d50c45bd522c5b1))
* add working banner, video and interstitial placements. KSF placements TODO ([37574f6](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/37574f6fd19637c88ab3839417e9a1c6ff6b2134))

## [8.2.1](https://github.com/superawesome-org/sa-mobile-sdk-android/compare/v8.2.0...v8.2.1) (2021-11-30)


### Bug Fixes

* **Manifest:** NOJIRA: Cleaned up Manifests and added exported property to activities. ([51c92a0](https://github.com/superawesome-org/sa-mobile-sdk-android/commit/51c92a0c1bfef184c494406a5a34b919690a91c2))

CHANGELOG
=========

8.1.5
 - Fix for dwell time not sending all needed parameters on certain requests
 - Fix for moat sometimes failing to load on certain versions of Android

8.1.3
 - CI configuration update to remove unneeded SDK - SAAir

8.1.0 
 - Added additional request parameters for the selection of a specific creative


8.0.2
 - Updated Android library to self-hosted location post Bintray deprecation
 - Added Dwell Time
 - Cleaned up and updated dependencies across the board, including Admob, Mopub and Kotlin
 - Added support for new formats: 3rd party display and interstitials
 - Added measurement logs for Moat for better discrepancy investigation
 - Initial functionality added for selection of a specific creative based on request parameters

7.2.10
 - Removed Google Advertiser Id from the codebase
 
7.2.9
 - Moved Moat library to it's own module
 - Fixed events firing even after the WebView is closed

7.2.8
 - MoPub adapter is fixed and updated to the latest version
 - Added SDK metric information for active adapters

7.2.7
 - AdMob adapter is fixed and updated to the latest version
 
7.2.6
 - Update Moat library to v4.0.0
 
7.2.5
 - Fix KSF campaigns

7.0.3
 - Fix click handling with a null ad bug

7.0.1
 - Transitioned to a non-scaling WebView to display banner & interstitial type ads

7.0.0
 - Prepare and improve SDK for GDPR Release
 - Improved tests for a lot of sub-components (networking, parsing, etc)
 - Added a module that dynamically checks a user's "isMinor" status based on country, etc
 - Simplified a lot of the code in different sub-components (networking, parsing, etc)
 - Fixed iFrame issue

6.1.6 
 - Fixed a bug that meant that calling "play" twice on a Video ad meant you had two impressions vs one ad attempted event
 - Fixed a bug with closing the video ad by pressing the back button
 - Made sure image ads of sizes greater than 320x480 can be displayed correctly
 - Made a fix to the bumper being closed with the back button and causing a crash
 - Generalised the mediaPlayer.prepare () catch statement
 - Fixed a bug related to closing the Interstitial Ad
 - Removed unecessary code in SAUtils, SABumperPage, SAAgeGate, etc

6.1.5
 - Improved Bumper Page experience

6.1.4
 - Removed video auto-reloading after an hour code

6.1.3
 - Update BumperPage copy

6.1.2
 - Fixed MoPub failLoad error that was prevening the MoPub Adapter t
o correctly signal either an empty ad or a mopub://failLoad tag

6.1.1
 - Small general bug fixes
 - Added null checks to callbacks

6.1.0
 - Added the Bumper Page as another option to overlap to each ad (video, interstitial, banner, app wall)
 - Separated the parental gate into a separate library (and done some refactoring)

6.0.4
 - Add a null pointer exception check to SABannerAd to guard against the case when an ad started playing but is closed before "Web_Loaded" gets called

6.0.3
 - Reverted back to MoPub classic class path

6.0.2
 - Updated version file
 
6.0.1
 - Fix an issue with MoPub fallbacks crashing banner type ads

6.0.0
 - Refactor & rename things
 - Split the SDK to have a more Publisher centric role by eliminating the CPI library (as opposed to the new Advertisers SDK)

5.7.4
 - Fixed a bug that caused video & interstitial ads that failed to load once, sending the adFailedToLoad callback event, start always sending adAlreadyLoaded on subsequent failed loads

5.7.3 
 - Added a new ad callback event called adEmpty. This will be forwarded when the ad server returns successfully (status code 200) but has no actual ad to serve.
 - Added this event to the AIR and Unity plugins
 - It is also handled by the AdMob/MoPub adapters in their own internal logic
 - Updated the way the Unity plugin displays banners in reaction to changes in Unity v5.6.x. Now all banners are displayed in as a subview of custom dialogs, above the main activity.

5.7.2
 - Updated the Moat SDK for video & display yet again to a more manual event triggered approach

5.7.1
 - Renamed a folder in SAWebPlayer from "aux" to "utilities" so as to make the whole library correctly work on Windowds

5.7.0
 - Added AdMob support
 - Refactored MoPub classes to use the same naming convention as the AdMob, Unity and AIR plugins
 - Refactored SAWebPlayer to not be a Fragment but a View (that means that banner instances won't be saved between orientation changes anymore, but some other bugs will be fixed, related to adding & removing Fragments)
 - Refactored MoPub classes to better read values form the MoPub configuration JSON 

5.6.0
 - Added MRAID capabilities to banner & interstitial ads
 - Updated the MOAT implementation to the latest one available
 - Updated the Padlock to have the new SA logo


5.5.7
 - Added camel & snake case options when parsing the SACreative object - for click, impression and install
 - Added the osTarget parameter to the SACreative
 - Added more error checks to SAEvents
 - Fixed bugs in SAVideoAd, SAInterstitialAd & SAAppWall when trying to play an ad that's not yet been properly loaded
 - The SAJsonParser library can now create JSON objects & arrays by giving a variable number of arguments instead of an array of arguments
 - Fixed tests

5.5.6
 - Updated the Model space SADetails class to have a "base" string member to hold the base Url of the creative
 - Updated the Web Player to work with a base url and eliminate a series of minor bugs where 3rd party tags weren't rendered correctly
 - Added a guard in the Web Player to not call the layout observer on a null container 

5.5.3
 - Added support for scrollable interstitials

5.5.2
 - Refactored the SAModelSpace library to:
    - remove all Ad Events from the SACreative object
    - add a SAVASTAdType object to SAMedia; for video ads this is where additional details will be stored from now on 
    - renamed the SATracking model to SAVASTEvent
    - added an integer to the SAAd model that holds the configuration (PRODUCTION / STAGING)
    - the ad itself is now responsbile for creating the SAReferral model (since it stores configuration)
    - added a new constructor for SAAd that takes placementId, configuration and JSON string
 - Updated the VAST Parser library to take into account the new modelspace; added more tests
 - Updated the SAAdLoader library:
    - to take into account the new modelspace
    - to not try to add events to the SAAd class; 
    - added more tests
 - Refactored the SAEvents library:
    - each type of event (VAST events, click through, impression, etc) is now a separate class; that makes it easier to test 
    - there are now for modules: VASTModule, ServerModule, ViewableModule and MoatModule that deal with these different things that need to be triggered
    - added a lot more tests
 - Updated the CPI library to work with the new modelspace
 - Updated the Main SDK to work with the new modelspace & eventing system

5.5.1
 - Minor update to rename some internal SAAd object members to have shorter names

5.5.0
 - Refactored click handling to now ignore null click urls
    - ads that now have a null click url won't fire either the parental gate or any associated click urls (for video, for example). 
    - refactored the web processror (SAProcessHTML) to ignore null click urls
    - improved the web view (SAWebView) to ignore urls like "about:blank"
    - refactored the "click" method of banner, interstitial & video ads to now contain the ad index (from the ad resoonse) and the destination. Now all methods more or less do the same thing and have the same structure. It's up to either the web view or the vast part to provide a valid click url, if it's available.
    - Improved the way the referral data is added to the click url. It's now part of the SACreative model (in the SAModelSpace library) as refferalData, and is properly populated in the SAAdParser library once all fields are present.
 - Refactored the CPI part in a separate library called SACPI (sa-mobile-lib-ios-cpi). 
    - Created a SAOnce class to make sure the CPI events are fired just once in the application's lifetime.
    - Created a SAInstall class that deals with sending the /install event to the ad server
    - SACPI is now a singleton
    - The library can be imported separately by advertisers if they just want to measure their installs, but don't want the full SDK
    - The library has now become a dependency of the main SDK. All previous CPI classes in the SDK have been removed
 - Improved the tag handling code to try to replace less characters in the tag so that more tags will work
 - Refactored the SAVideoPlayer class to be based on a Android Fragment and to simplify it.
    - the video player now better handles orientation changes
    - it also handles better when the activity changes (e.g. a browser window opens) and have eliminated a whole class of errors that might've happened because the underlying media player wasn't in a legal state
    - the SAVideoAd activity doesn't need to delete the video player when the ad ends (just so it can display the close button)
    - also in the main SAVideoAd activity the video player doesn't need to be played using a timer; it now emits an "Video_Prepared" event that indicates when the media should be played.
 - Reverted back the 15s close button change done in 5.4.2. Video ads have a hidden close button by default, that shows up at the end of the ad playing routine. It can be set to be visible from the start.
 - Changes to event handling:
    - Removed firing of all "impression" events for banner, interstitial & app wall ads (these are fired by the server). For video the "impression" event is still fired, but that's taken from the VAST tag, so it's OK.
    - Removed firing of all "install" events for ads
    - Removed firing of all "clickCounter" events for ads
    - Removed the VAST "custom_clicks" events alltogether
    - Renamed the "sourceBundle" query parameter to "bundle"
    - Renamed events with either "superawesome_viewable_impression" or "vast_creativeView", etc. 
 - Removed the method that determines whether to show a padlock or not. It's not controlled by an ad's "showPadlock" member 
 - Fixed a bug in the Unity & AIR plugins that meant that loading and then quickly closing a banner would have unintended consequences.

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
