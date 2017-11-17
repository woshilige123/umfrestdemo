package com.umftech.api.sample.rest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Configuration
@ComponentScan(basePackages = { "com.umftech.api.sample.*" })
@PropertySources({ @PropertySource("/WEB-INF/conf/cert.conf"), @PropertySource("/WEB-INF/conf/demo-app.conf")})
public class EncryptUtil {
	private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
	private static String MER_PRIVATE_KEY;
	private static String UMF_PUBLIC_KEY;
	private static String encryptItems;
	private static String encryptObjects;

	public String getEncryptItems() {
		return encryptItems;
	}
	@Value("${encrypt.item.name}")
	public void setEncryptItems(String encryptItems) {
		this.encryptItems = encryptItems;
	}

	public String getEncryptObjects() {
		return encryptObjects;
	}
	@Value("${encrypt.object.name}")
	public void setEncryptObjects(String encryptObjects) {
		this.encryptObjects = encryptObjects;
	}

	public static JsonElement encryptParam(JsonElement jelement, boolean reqEncrypt) {
		// 
		if(reqEncrypt){
			JsonObject jObject = jelement.getAsJsonObject();
			for(Map.Entry<String, JsonElement> entry : jObject.entrySet()){
				String key = entry.getKey();
				if(encryptItems.indexOf(key) != -1){
					jObject.addProperty(key, encryptByPublic(jObject.get(key).toString()));
				}
			}
		}
		if(jelement.isJsonPrimitive() || jelement.isJsonNull()){
			return jelement;
		}
		if(jelement.isJsonArray()){
			for(JsonElement element : jelement.getAsJsonArray()){
				encryptParam(element, false);
			}
		}
		if(jelement.isJsonObject()){
			JsonObject jObject = jelement.getAsJsonObject();
			for(Map.Entry<String, JsonElement> entry : jObject.entrySet()){
				String key = entry.getKey();
				if(encryptObjects.indexOf(key) != -1){
					encryptParam(jObject.get(key), true);
				}else{
					encryptParam(jObject.get(key), false);
				}
			}
		}
		return jelement;
	}
	public static String encryptByPrivate(String str) {
		EncryptUtil util = new EncryptUtil();
		Cipher cipher = util.createCipher();
		String rs = null;
		try {
			rs = util.encryptText(str, util.getPrivate(MER_PRIVATE_KEY), cipher);
		}catch (Exception e) {
			logger.error("encrypt error.",e);
		}
		return rs;
	}
	

	public static String encryptByPublic(String str) {
		EncryptUtil util = new EncryptUtil();
		Cipher cipher = util.createCipher();
		String rs = null;
		try {
			rs = util.encryptText(str, util.getPublic(UMF_PUBLIC_KEY), cipher);
		}catch (Exception e) {
			logger.error("encrypt error.",e);
		}
		return rs;
	}

	public static String decryptByPublic(String str) {
		EncryptUtil util = new EncryptUtil();
		Cipher cipher = util.createCipher();
		String rs = null;
		try {
			rs = util.decryptText(str, util.getPublic(UMF_PUBLIC_KEY), cipher);
		}catch (Exception e) {
			logger.error("encrypt error.",e);
		}
		return rs;
	}
	
	public static String decryptByPrivate(String str) {
		EncryptUtil util = new EncryptUtil();
		Cipher cipher = util.createCipher();
		String rs = null;
		try {
			rs = util.decryptText(str, util.getPrivate(MER_PRIVATE_KEY), cipher);
		}catch (Exception e) {
			logger.error("encrypt error.",e);
		}
		return rs;
	}

	private Cipher createCipher() {
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");
		} catch (Exception e) {
			logger.error("cann't create RSA cipher.", e);
		}
		return cipher;
	}

	// https://docs.oracle.com/javase/8/docs/api/java/security/spec/PKCS8EncodedKeySpec.html
	private PrivateKey getPrivate(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	// https://docs.oracle.com/javase/8/docs/api/java/security/spec/X509EncodedKeySpec.html
	private PublicKey getPublic(String filename) throws Exception {
	    X509Certificate cert = genCertificate(UMF_PUBLIC_KEY);
	    byte[] keyBytes = cert.getPublicKey().getEncoded();
	    
	    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);

		return publicKey;
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
	
	private String encryptText(String msg, PrivateKey key, Cipher cipher)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			UnsupportedEncodingException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeyException {
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return Base64.getEncoder().encodeToString(cipher.doFinal(msg.getBytes("UTF-8")));
	}
	
	private String encryptText(String msg, PublicKey key, Cipher cipher)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			UnsupportedEncodingException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeyException {
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return Base64.getEncoder().encodeToString(cipher.doFinal(msg.getBytes("UTF-8")));
	}

	private String decryptText(String msg, PublicKey key, Cipher cipher)
			throws InvalidKeyException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException {
		cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(java.util.Base64.getDecoder().decode(msg)), "UTF-8");
	}
	
	private String decryptText(String msg, PrivateKey key, Cipher cipher)
			throws InvalidKeyException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException {
		cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(java.util.Base64.getDecoder().decode(msg)), "UTF-8");
	}

	@Value("${cert.mer.key.private}")
	public void setMER_PRIVATE_KEY(String mER_PRIVATE_KEY) {
		if(mER_PRIVATE_KEY.indexOf("{WEB_BASE}")>-1){
			mER_PRIVATE_KEY = mER_PRIVATE_KEY.replace("{WEB_BASE}", getWebBasePath());
		}
		MER_PRIVATE_KEY = mER_PRIVATE_KEY;
	}

	@Value("${cert.umf.key.public}")
	public void setUMF_PUBLIC_KEY(String uMF_PUBLIC_KEY) {
		if(uMF_PUBLIC_KEY.indexOf("{WEB_BASE}")>-1){
			uMF_PUBLIC_KEY = uMF_PUBLIC_KEY.replace("{WEB_BASE}", getWebBasePath());
		}
		UMF_PUBLIC_KEY = uMF_PUBLIC_KEY;
	}
	
	private static String getWebBasePath(){
		String webBase = SignUtil.class.getClassLoader().getResource("/").getPath();
		if(webBase.indexOf("WEB-INF")>0){
			webBase = webBase.substring(0, webBase.indexOf("WEB-INF")-1);
		}
		return webBase;
	}
	
}
