# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Retain generic type information for use by reflection by converters and adapters.

-obfuscationdictionary proguard-dictionary.txt
-packageobfuscationdictionary proguard-dictionary.txt
-classobfuscationdictionary proguard-dictionary.txt

# retrofit
-keepattributes Signature
# Retain service method parameters.
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# okhttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keep class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# RxJava
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Jgit
-dontwarn com.jcraft.jsch.**
-dontwarn org.apache.commons.**
-dontwarn org.apache.http.**
-dontwarn org.slf4j.**
-dontwarn com.morirain.jgit.utils.**
-dontwarn org.eclipse.jgit.**
-keep class org.eclipse.jgit.** { *; }
-keep class org.eclipse.jgit.internal.JGitText.** { *; }

-keep class org.eclipse.jgit.nls.TranslationBundle.** { *; }

-keep class org.slf4j.** { *; }
-keep class org.eclipse.jgit.nls.TranslationBundle.** { *; }
-keep class org.eclipse.jgit.internal.JGitText.** { *; }
-keep class org.eclipse.jgit.lib.Repository.**
-keep class com.jcraft.jsch.jce.**
-keep class * extends com.jcraft.jsch.KeyExchange
-keep class com.jcraft.jsch.**
-keep class org.apache.commons.io.FileUtils { public *** openOutputStream(...); }
-keep class org.apache.commons.io.IOUtils  { public *** closeQuietly(...); public *** copy(...); }
-keep class org.apache.commons.io.input.BoundedInputStream { *; }
-keep class org.eclipse.jgit.transport.RemoteConfig { *** removeURI(...); }

# BRVAH
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}