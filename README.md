SuperAwesome Mobile SDK for Android
===================================

[![GitHub tag](https://img.shields.io/github/tag/superawesome-org/sa-mobile-sdk-android.svg)]() 
[![GitHub contributors](https://img.shields.io/github/contributors/superawesome-org/sa-mobile-sdk-android.svg)]() 
[![Language](https://img.shields.io/badge/language-java-f48041.svg?style=flat)]() 
[![Platform](https://img.shields.io/badge/platform-android-lightgrey.svg)]()

An SDK provided by SuperAwesome for Android app developers to load ads from AwesomeAds.

## Documentation

Full documantation for the AwesomeAds Android Publisher SDK can be found on our [AwesomeAds Publisher SDK Docs](https://www.kidswebservices.com/docs/awesomeads/sdks/publisher).

There are global feature flags that are stored remotely in the AA-SDK S3 bucket here: 
https://s3.console.aws.amazon.com/s3/object/aa-sdk?region=eu-west-1&bucketType=general&prefix=featureFlags/android/featureFlags.json

> [!IMPORTANT]
> There's a new schema for feature flags, explained below:

- You can find schema v2 in the new path `featureFlags/v2/android/featureFlags.json`
- The json looks like the following:
```json
{
  "featureFlag1": {
    "value": <number>/<boolean>,
    "conditions": {
      "placementIds": [...],
      "lineItemIds": [...],
      "creativeIds": [...],
      "percentage": <integer 0-100>,
    }
  }
}
```

The `conditions` is completely optional, as is any of the are properties inside it. Include as many
or as few as you want. The conditions are all inclusive. Meaning that if you add a condition, the flag
will only be enabled for those that match ALL the conditions at the same time.

E.g.: 
```json
{
  "isExoPlayerEnabled": {
    "value": true,
    "conditions": {
      "placementIds": [82090],
      "percentage": 50
    }
  }
}
```
In this example, the flag `isExoPlayerEnabled` will only be active for the placement with ID `82090`
and for roughly 50% of the users.

The feature flags are loaded in the app init function only.

At the time of writing the current feature flags are:

| Flag                             | Type    | Value
| -------------------------------- |---------| -----
| `isAdResponseVASTEnabled`        | Boolean | false
| `isExoPlayerEnabled`             | Boolean | false
| `videoStabilityFailsafeTimeout`  | Number  | 2500
| `rewardGivenAfterErrorDelay`     | Number  | Long.MAX_VALUE 

For more information check out the [SuperAwesome Developer Portal](https://superawesome-org.github.io/sa-mobile-sdk-android/).

**Contributing to the Android SDK**

- To contribute to the Android SDK, create a new branch with your desired commits.
- Create a pull request, and once it's merged, our automated build pipeline will release a new version based on the commit message prefaces given. You can find out more about the necessary commit prefaces for your desired release version [here](https://superawesomeltd.atlassian.net/wiki/spaces/AA/pages/4932993069/Releasing+Versions+with+Semantic+Release).
