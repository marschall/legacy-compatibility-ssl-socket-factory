Legacy Compatibility SSLSocketFactory [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.marschall/legacy-compatibility-ssl-socket-factory/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.marschall/legacy-compatibility-ssl-socket-factory) [![javadoc](https://javadoc.io/badge2/com.github.marschall/legacy-compatibility-ssl-socket-factory/javadoc.svg)](https://javadoc.io/doc/com.github.marschall/legacy-compatibility-ssl-socket-factory)
=====================================

A `SSLSocketFactory` that implements `SSLSession.getPeerCertificateChain()`.

Usage
-----

```xml
<dependency>
  <groupId>com.github.marschall</groupId>
  <artifactId>legacy-compatibility-ssl-socket-factory</artifactId>
  <version>1.0.0</version>
</dependency>
```

```java
SSLSocketFactory socketFactory = new LegacyCompatibilitySSLSocketFactory();

// or whatever other object
urlConnection.setSSLSocketFactory(socketFactory);
```


Why would you want to use this project?
---------------------------------------

You wouldn't want to use this project unless you have to.

Why would you have to use this project?
---------------------------------------

You want to migrate to Java 17 but rely on a third party dependency that calls [SSLSession.getPeerCertificateChain()](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/javax/net/ssl/SSLSession.html#getPeerCertificateChain()).

See [JDK-8241047](https://bugs.openjdk.java.net/browse/JDK-8241047) for details.
