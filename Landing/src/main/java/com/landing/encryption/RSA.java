/**
 * 文件名称：RSA.java
 * 描述：非对称性加密类
 * 创建日期：2019.9.1
 * 最后修改日期：2019.9.3
 * 编码人员：陈文龙
 */
package com.landing.encryption;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

/**
 * 非对称性加密（公私钥加密） 1、每次生成的公私钥对都是不同的，所以要求对其进行存储
 * 
 *
 */
public class RSA {

	private static final String RSA = "RSA";

	/**
	 * 加密
	 * 
	 * @param publicKey
	 * @param srcBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] encrypt(String publicKey, byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		try {
			if (publicKey != null) {
				PublicKey key = getPublicKey(publicKey);
				return rsa(key, srcBytes, Cipher.ENCRYPT_MODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] encrypt2(String privateKey, byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		try {
			if (privateKey != null) {
				PrivateKey key = getPrivateKey(privateKey);
				return rsa(key, srcBytes, Cipher.ENCRYPT_MODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取公钥
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	private static PublicKey getPublicKey(String publicKey) throws Exception {
		byte[] publicKeys = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeys);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		return kf.generatePublic(spec);
	}

	/**
	 * 获取私钥
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	private static PrivateKey getPrivateKey(String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		return kf.generatePrivate(spec);
	}

	/**
	 * 解密
	 * 
	 * @param privateKey
	 * @param srcBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] decrypt(String privateKey, byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		try {
			if (privateKey != null) {
				PrivateKey key = getPrivateKey(privateKey);
				// 根据公钥，对Cipher对象进行初始化
				return rsa(key, srcBytes, Cipher.DECRYPT_MODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] decrypt2(String publicKey, byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		try {
			if (publicKey != null) {
				PublicKey key = getPublicKey(publicKey);
				// 根据公钥，对Cipher对象进行初始化
				return rsa(key, srcBytes, Cipher.DECRYPT_MODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] rsa(Key key, byte[] input, int mode) throws IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance(RSA);
		// 根据公钥，对Cipher对象进行初始化
		cipher.init(mode, key);
		byte[] resultBytes = cipher.doFinal(input);
		return resultBytes;
	}

	public KeyPair getKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
		// 初始化密钥对生成器，密钥大小为1024位
		keyPairGen.initialize(1024);
		// 生成一个密钥对，保存在keyPair中
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		RSA rsa = new RSA();
		String msg = "www.suning.com/index.jsp";
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPair keyPair = rsa.getKeyPair();
		// 得到私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 得到公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 用公钥加密
		byte[] srcBytes = msg.getBytes();
		byte[] resultBytes = rsa.encrypt2(Base64.encodeBase64String(privateKey.getEncoded()), srcBytes);
		System.out.println("privateKey" + Base64.encodeBase64String(privateKey.getEncoded()));
		// 用私钥解密
		byte[] decBytes = rsa.decrypt2(Base64.encodeBase64String(publicKey.getEncoded()), resultBytes);
		System.out.println("publicKey" + Base64.encodeBase64String(publicKey.getEncoded()));
		System.out.println("明文是:" + msg);
		System.out.println("加密后是:" + Base64.encodeBase64String(resultBytes));
		System.out.println("解密后是:" + new String(decBytes));
	}
}
/*
 * 公钥加密: byte[] resultBytes =
 * rsa.encrypt(Base64.encodeBase64String(publicKey.getEncoded()),
 * msg.getBytes()); rsa为RSA对象,
 * resultBytes为返回结果,publicKey为rsa调用getKeyPair得到的公钥,msg为待加密内容 私钥加密: byte[]
 * resultBytes =
 * rsa.encrypt2(Base64.encodeBase64String(privateKey.getEncoded()),
 * msg.getBytes()); rsa为RSA对象,
 * resultBytes为返回结果,privateKey为rsa调用getKeyPair得到的私钥,msg为待加密内容
 * 解密调用decrypt或decrypt2,传入参数为另一个key
 */
