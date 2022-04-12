SuperAwesome Mobile SDK for Android
===================================

[![GitHub tag](https://img.shields.io/github/tag/SuperAwesomeLTD/sa-mobile-sdk-android.svg)]() 
[![GitHub contributors](https://img.shields.io/github/contributors/SuperAwesomeLTD/sa-mobile-sdk-android.svg)]() 
[![license](https://img.shields.io/github/license/SuperAwesomeLTD/sa-mobile-sdk-android.svg)]() 
[![Language](https://img.shields.io/badge/language-java-f48041.svg?style=flat)]() 
[![Platform](https://img.shields.io/badge/platform-android-lightgrey.svg)]()

For more information check out the [SuperAwesome Developer Portal](https://superawesomeltd.github.io/sa-mobile-sdk-android/).

**Contributing to the Android SDK**

- To contribute to the Android SDK, create a new branch with your desired commits.
- Our automated build pipeline uses semantic-release to release the correct version based on the commits provided in the release. In order to release the desired version number (patch, minor or major), use the following commit message prefaces:
  - For a **patch** release, i.e. from `X.X.1` to `X.X.2`, preface the commit message with either `fix()` or `perf()`
  - For a **minor** release, i.e. from `X.1.X` to `X.2.X`, preface the commit message with `feat()`
  - For a **major** release, i.e. from `1.X.X` to `2.X.X`, preface the commit message with `BREAKING CHANGE:`
- Create a Pull Request, and once merged, the automated build pipeline will release the desired version.
