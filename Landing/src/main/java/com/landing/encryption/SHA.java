/**
 * 文件名称：SHA.java
 * 描述：SHA加密类
 * 创建日期：2019.9.1
 * 最后修改日期：2019.9.3
 * 编码人员：陈文龙
 */
package com.landing.encryption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SHA{
 
  /**
   * 传入文本内容，返回 SHA-256 串
   * 
   * @param strText
   * @return
   */
  public String SHA256(final String strText){
    return shaEncryption(strText, "SHA-256");
  }
 
  /**
   * 传入文本内容，返回 SHA-512 串
   * 
   * @param strText
   * @return
   */
  public String SHA512(final String strText){
    return shaEncryption(strText, "SHA-512");
  }
 
  /**
   * 字符串 SHA 加密
   * 
   * @param strSourceText
   * @return
   */
  private String shaEncryption(final String strText, final String strType){
    // 返回值
    String strResult = null;
 
    // 是否是有效字符串
    if (strText != null && strText.length() > 0){
      try{
        // SHA 加密开始
        // 创建加密对象 并傳入加密類型
        MessageDigest messageDigest = MessageDigest.getInstance(strType);
        // 传入要加密的字符串
        messageDigest.update(strText.getBytes());
        // 得到 byte 類型结果
        byte byteBuffer[] = messageDigest.digest();
 
        // 將 byte 轉換爲 string
        StringBuffer strHexString = new StringBuffer();
        // 遍歷 byte buffer
        for (int i = 0; i < byteBuffer.length; i++){
          String hex = Integer.toHexString(0xff & byteBuffer[i]);
          if (hex.length() == 1){
            strHexString.append('0');
          }
          strHexString.append(hex);
        }
        // 得到返回結果
        strResult = strHexString.toString();
      }
      catch (NoSuchAlgorithmException e){
        e.printStackTrace();
      }
    }
 
    return strResult;
  }
  
  public static void main(String[]args) {
	  System.out.println(new SHA().SHA256("123213131231"));
	  System.out.println(new SHA().SHA512("123213131231"));
  }
}