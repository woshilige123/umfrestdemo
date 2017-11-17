package com.umf.base.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umftech.api.sample.rest.util.SignUtil;

public class RSAUtils
{
  private static final Logger logger = LoggerFactory.getLogger(RSAUtils.class);
  private static String crtPath = null;
  private static String p8Path = null;

  public static PrivateKey genPrivateKey(byte[] key) { PrivateKey pk = null;
    try {
      PKCS8EncodedKeySpec e = new PKCS8EncodedKeySpec(key);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePrivate(e);
    } catch (Exception arg3) {
      logger.error(arg3.getMessage());
    }
    return null; }

  public RSAUtils(String crtPath, String p8Path)
  {
    this.crtPath = crtPath;
    this.p8Path = p8Path;
  }

  public static byte[] sign(PrivateKey pk, byte[] data) {
    byte[] sb = (byte[])null;
    try {
      Signature e = Signature.getInstance("SHA256withRSA");
      e.initSign(pk);
      e.update(data);
      return e.sign();
    } catch (Exception arg3) {
      logger.error(arg3.getMessage());
    }
    return null;
  }

  public static X509Certificate genCertificate(byte[] certData) {
    ByteArrayInputStream bais = new ByteArrayInputStream(certData);
    X509Certificate cert = null;
    try {
      CertificateFactory e = CertificateFactory.getInstance("X.509");
      return (X509Certificate)e.generateCertificate(bais);
    } catch (Exception arg3) {
      logger.error(arg3.getMessage());
    }
    return null;
  }

  public static boolean verifySign(X509Certificate cert, byte[] plain, byte[] signData)
  {
    try {
      Signature e = Signature.getInstance("SHA256withRSA");
      //Signature e = Signature.getInstance("SHA1withRSA");
      e.initVerify(cert);
      e.update(plain);
      return e.verify(signData);
    } catch (Exception arg3) {
      logger.error(arg3.getMessage());
    }
    return false;
  }

  public static String createSign(String inStr) {
    String returnSign = null;
    InputStream in = null;
    byte[] key = (byte[])null;
    try {
      key = new byte[4096];

      in = new FileInputStream(p8Path);
      in.read(key);
      PrivateKey pk = genPrivateKey(key);
      byte[] signData = sign(pk, inStr.getBytes("UTF-8"));
      returnSign = Base64.encode(signData);
    } catch (Exception localException) {
      localException.printStackTrace();
      try {
        if (in != null)
          in.close();
      } catch (IOException localIOException) {
        localIOException.printStackTrace();
        logger.error(localIOException.getMessage());
      }
      try
      {
        if (in != null)
          in.close();
      }
      catch (IOException localIOException1) {
        localIOException1.printStackTrace();
        logger.error(localIOException1.getMessage());
      }
    }
    finally
    {
      try
      {
        if (in != null)
          in.close();
      }
      catch (IOException localIOException1) {
        localIOException1.printStackTrace();
        logger.error(localIOException1.getMessage());
      }
    }
    return returnSign;
  }

  public String Sensitivity(String reqStr) throws Exception {
    byte[] certFile = new byte[20480];
    InputStream in_cert = null;
    String values = reqStr;
    try
    {
      in_cert = new FileInputStream(crtPath);

      in_cert.read(certFile);
    } catch (Exception localException) {
      if (in_cert != null) {
        try {
          in_cert.close();
        } catch (IOException e) {
          logger.error(e.getMessage());
        }
      }
      if (in_cert != null)
        try {
          in_cert.close();
        } catch (IOException e) {
          logger.error(e.getMessage());
        }
    }
    finally
    {
      if (in_cert != null) {
        try {
          in_cert.close();
        } catch (IOException e) {
          logger.error(e.getMessage());
        }
      }
    }
    X509Certificate cert = SignUtil.genCertificate(certFile);

    byte[] keyBytes = cert.getPublicKey().getEncoded();
    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key publicKey = keyFactory.generatePublic(x509KeySpec);

    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(1, publicKey);
    values = new String(Base64.encode(cipher.doFinal(values
      .getBytes("UTF-8"))));

    return values;
  }
}