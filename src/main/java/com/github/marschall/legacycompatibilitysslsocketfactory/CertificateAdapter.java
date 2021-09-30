package com.github.marschall.legacycompatibilitysslsocketfactory;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.Date;
import java.util.Objects;

/**
 * Adapts {@link java.security.cert.X509Certificate} to a {@link javax.security.cert.X509Certificate}.
 */
final class CertificateAdapter extends javax.security.cert.X509Certificate {

  private final java.security.cert.X509Certificate delegate;

  CertificateAdapter(java.security.cert.X509Certificate delegate) {
    Objects.requireNonNull(delegate, "delegate");
    this.delegate = delegate;
  }

  @Override
  public void checkValidity() throws javax.security.cert.CertificateExpiredException, javax.security.cert.CertificateNotYetValidException {
    try {
      this.delegate.checkValidity();
    } catch (java.security.cert.CertificateExpiredException e) {
      javax.security.cert.CertificateExpiredException deprecatedException = new javax.security.cert.CertificateExpiredException(e.getMessage());
      deprecatedException.initCause(e);
      throw deprecatedException;
    } catch (java.security.cert.CertificateNotYetValidException e) {
      javax.security.cert.CertificateNotYetValidException deprecatedException = new javax.security.cert.CertificateNotYetValidException(e.getMessage());
      deprecatedException.initCause(e);
      throw deprecatedException;
    }
  }

  @Override
  public void checkValidity(Date date) throws javax.security.cert.CertificateExpiredException, javax.security.cert.CertificateNotYetValidException {
    try {
      this.delegate.checkValidity(date);
    } catch (java.security.cert.CertificateExpiredException e) {
      javax.security.cert.CertificateExpiredException deprecatedException = new javax.security.cert.CertificateExpiredException(e.getMessage());
      deprecatedException.initCause(e);
      throw deprecatedException;
    } catch (java.security.cert.CertificateNotYetValidException e) {
      javax.security.cert.CertificateNotYetValidException deprecatedException = new javax.security.cert.CertificateNotYetValidException(e.getMessage());
      deprecatedException.initCause(e);
      throw deprecatedException;
    }
  }

  @Override
  public int getVersion() {
    return this.delegate.getVersion();
  }

  @Override
  public BigInteger getSerialNumber() {
    return this.delegate.getSerialNumber();
  }

  @Override
  public Principal getIssuerDN() {
    return this.delegate.getIssuerDN();
  }

  @Override
  public Principal getSubjectDN() {
    return this.delegate.getSubjectDN();
  }

  @Override
  public Date getNotBefore() {
    return this.delegate.getNotBefore();
  }

  @Override
  public Date getNotAfter() {
    return this.delegate.getNotAfter();
  }

  @Override
  public String getSigAlgName() {
    return this.delegate.getSigAlgName();
  }

  @Override
  public String getSigAlgOID() {
    return this.delegate.getSigAlgOID();
  }

  @Override
  public byte[] getSigAlgParams() {
    return this.delegate.getSigAlgParams();
  }

  @Override
  public byte[] getEncoded() throws javax.security.cert.CertificateEncodingException {
    try {
      return this.delegate.getEncoded();
    } catch (java.security.cert.CertificateEncodingException e) {
      javax.security.cert.CertificateEncodingException deprecatedException = new javax.security.cert.CertificateEncodingException(e.getMessage());
      deprecatedException.initCause(e);
      throw deprecatedException;
    }
  }

  @Override
  public void verify(PublicKey key) throws javax.security.cert.CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
    try {
      this.delegate.verify(key);
    } catch (java.security.cert.CertificateException e) {
      javax.security.cert.CertificateException deprecatedException = new javax.security.cert.CertificateException(e.getMessage());
      deprecatedException.initCause(e);
      throw deprecatedException;
    }
  }

  @Override
  public void verify(PublicKey key, String sigProvider) throws javax.security.cert.CertificateException, NoSuchAlgorithmException,
  InvalidKeyException, NoSuchProviderException, SignatureException {
    try {
      this.delegate.verify(key, sigProvider);
    } catch (java.security.cert.CertificateException e) {
      javax.security.cert.CertificateException deprecatedException = new javax.security.cert.CertificateException(e.getMessage());
      deprecatedException.initCause(e);
      throw deprecatedException;
    }
  }

  @Override
  public String toString() {
    return this.delegate.toString();
  }

  @Override
  public PublicKey getPublicKey() {
    return this.delegate.getPublicKey();
  }

}
