-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
#-keep,includedescriptorclasses class com.yourcompany.yourpackage.**$$serializer { *; } # <-- change package name to your app's
#-keepclassmembers class tv.superawesome.sdk.publisher.common.models.** { # <-- change package name to your app's
#    *** Companion;
#}
#-keepclasseswithmembers class tv.superawesome.sdk.publisher.common.models.** { # <-- change package name to your app's
#    kotlinx.serialization.KSerializer serializer(...);
#}