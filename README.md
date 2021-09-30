Legacy Compatibility SSLSocketFactory
=====================================

A `SSLSocketFactory` that implements `SSLSession.getPeerCertificateChain()`.

Usage
-----

```java
SSLSocketFactory socketFactory = new LegacyCompatibilitySSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
```


Why would you want to use this project?
---------------------------------------

You wouldn't want to use this project unless you have to.

Why would you have to use this project?
---------------------------------------

You want to migrate to Java 17 but rely on a third party dependency that calls [SSLSession.getPeerCertificateChain()](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/javax/net/ssl/SSLSession.html#getPeerCertificateChain()).


[JDK-8241047](https://bugs.openjdk.java.net/browse/JDK-8241047)
