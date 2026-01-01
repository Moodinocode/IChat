# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep data classes
-keep class com.capstone.securemessaging.data.model.** { *; }

# Keep Room entities
-keep @androidx.room.Entity class * { *; }

