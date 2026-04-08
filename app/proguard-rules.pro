# 通用 Android 混淆规则
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 保留四大组件
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Compose 不混淆
-keep class androidx.compose.** { *; }
-keep class com.google.android.material.** { *; }

# Kotlin 协程 & 反射
-keep class kotlin.** { *; }
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlin.**
-dontwarn kotlinx.**

# ViewModel 不混淆
-keep class androidx.lifecycle.** { *; }

# 你的包名不混淆（避免 ROOT / Shell 执行异常）
-keep class com.flash9006.stars.** { *; }

# 忽略无关警告
-dontwarn android.**
-dontwarn androidx.**
-dontwarn java.lang.invoke**
-dontwarn java.lang.reflect**