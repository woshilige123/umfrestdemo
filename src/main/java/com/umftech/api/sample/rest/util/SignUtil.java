package com.umftech.api.sample.rest.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan(basePackages = { "com.umftech.api.sample.*" })
@PropertySources({ @PropertySource("/WEB-INF/conf/cert.conf") })
public class SignUtil {
	private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

	public static String UMF_PUBLIC_KEY;

	public static String PRIVATE_KEY;

	public static String createSign(String str) {

		String returnSign = null;

		byte[] key = null;
		String privateKeyPath = PRIVATE_KEY;
		
		try (InputStream in = new FileInputStream(privateKeyPath)) {
			key = new byte[4096];
			in.read(key);
			PrivateKey pk = genPrivateKey(key);
			byte[] signData = (byte[])null;
			
			Signature sig = Signature.getInstance("SHA1withRSA");
			sig.initSign(pk);
			sig.update(str.getBytes(StandardCharsets.UTF_8));
			signData = sig.sign();
			returnSign = Base64.getEncoder().encodeToString(signData);
		} catch (Exception e) {
			logger.error("Sign error", e);
		}
		logger.debug("origin string:" + str);
		logger.debug("sign:" + returnSign);

		return returnSign;
	}
	
	public static byte[] readPrivateKey(){
		byte[] key = null;
		String privateKeyPath = PRIVATE_KEY;

		try (InputStream in = new FileInputStream(privateKeyPath)) {
			key = new byte[4096];
			in.read(key);
		} catch (Exception e) {
			logger.error("Sign error", e);
		}
		return key;
	}

	public static PrivateKey genPrivateKey(byte[] key) {
		PrivateKey pk = null;
		try {
			PKCS8EncodedKeySpec peks = new PKCS8EncodedKeySpec(key);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			pk = kf.generatePrivate(peks);
		} catch (Exception e) {
			return null;
		}
		return pk;
	}

	public static X509Certificate genCertificate(String certFileName) {

		try (InputStream inStream = new FileInputStream(certFileName)) {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			return (X509Certificate) cf.generateCertificate(inStream);
		} catch (Exception e) {
			logger.error("create x509 cert fail.",e);
			return null;
		}

	}  
	
	public static X509Certificate genCertificate(byte[] certData) {
		ByteArrayInputStream bais = new ByteArrayInputStream(certData);
		X509Certificate cert = null;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			cert = (X509Certificate) cf.generateCertificate(bais);
		} catch (Exception e) {
			return null;
		}
		return cert;
	}

	public static boolean verifySign(String str, String sign) {
		try {
			//byte[] key = Files.readAllBytes(new File(UMF_PUBLIC_KEY).toPath());
			X509Certificate cert = SignUtil.genCertificate(SignUtil.UMF_PUBLIC_KEY);
			Signature e = Signature.getInstance("SHA256withRSA");
			e.initVerify(cert);
			e.update(str.getBytes(StandardCharsets.UTF_8));
			return e.verify(Base64.getDecoder().decode(sign));

		} catch (Exception e) {
			logger.error("Sign error", e);
			return false;
		}
	}

	@Value("${cert.umf.key.public}")
	public void setUmfPublicKey(String publicKey) {
		logger.info(String.format("original publicKey param: %s", publicKey));
		if (publicKey.indexOf("{WEB_BASE}") > -1) {
			publicKey = publicKey.replace("{WEB_BASE}", getWebBasePath());
		}
		logger.info(String.format("publicKey param: %s", publicKey));
		this.UMF_PUBLIC_KEY = publicKey;
	}

	@Value("${cert.mer.key.private}")
	public void setMerPrivateKey(String privateKey) {
		logger.info(String.format("original privateKey param: %s", privateKey));
		if (privateKey.indexOf("{WEB_BASE}") > -1) {
			privateKey = privateKey.replace("{WEB_BASE}", getWebBasePath());
		}
		logger.info(String.format("param: %s", privateKey));
		SignUtil.PRIVATE_KEY = privateKey;
	}

	private static String getWebBasePath() {
		String webBase = SignUtil.class.getClassLoader().getResource("/").getPath();
		if (webBase.indexOf("WEB-INF") > 0) {
			webBase = webBase.substring(0, webBase.indexOf("WEB-INF") - 1);
		}
		logger.info(String.format("getWebBasePath: %s", webBase));
		return webBase;
	}

}
