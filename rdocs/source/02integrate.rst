Getting started
===============

Add the SDK through Gradle
^^^^^^^^^^^^^^^^^^^^^^^^^^

The simplest way of installing the AwesomeAds SDK in Android Studio is to download the AAR library through Gradle.

Just include the following in your module's **build.gradle** file (usually the file under MyApplication/app/):

.. code-block:: shell

    repositories {
        maven {
            url  "http://dl.bintray.com/sharkofmirkwood/maven"
        }
    }

    dependencies {
        compile 'tv.superawesome.sdk:sa-sdk:3.6.4@aar'
        compile 'com.google.android.gms:play-services:8.4.0'
    }

Add the SDK as a JAR library
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

If you're running an environment which does not support Gradle, then you'll need to add the SDK manually.

First, download the JAR file `sa-sdk-3.6.4.jar <http://dashboard.superawesome.tv>`_
