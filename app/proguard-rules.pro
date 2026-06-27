# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.namijapanese.core.model.** { *; }
-keep class com.namijapanese.core.database.entity.** { *; }
