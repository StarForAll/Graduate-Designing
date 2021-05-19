package cn.edu.ncu.common.util.basic;
import cn.edu.ncu.common.constant.CommonConst;
import cn.edu.ncu.service.exception.BaseDataExceptionCode;
import cn.edu.ncu.common.core.exception.BusinessException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

/**
 * @Author: XiongZhiCong
 * @Description: DES加密乱码,相同字符串加密两次结果不同 PASSWORD必须是8的倍数
 * @Date: Created in  2020/6/17 11:33
 * @Modified By
 */
public class DESUtil {
    private static final String ALGORITHM_MODE = "DES";
    public static final String ALGORITHM_DES ="DES/CBC/PKCS5Padding";
    /**
     * 8位的随机码
     */
    private static final String ivString ="xsdswaqe";
    /**
     * 字符串字符集
     */
    private static final String codeType ="UTF-8";
    /**
     * 加密key
     */
    private static final String PASSWORD="l$V11s~uwy&b.)Aet@cPwcs0";

    private  static Cipher getCipher(int mode){
        DESKeySpec dks;
        try {
            dks = new DESKeySpec(PASSWORD.getBytes(codeType));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM_MODE);
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(mode, secretKey, paramSpec);
            return cipher;
        } catch (Exception e) {
            throw new BusinessException(BaseDataExceptionCode.DES_EXCEPTION_CODE,e);
        }
    }
    /**
     * @Description: 加密
     * @Param [plaintext]
     * @return java.lang.String
     * @since 2020/6/17 14:41
     */
    public static String encrypt(String format,String plaintext)  {
        return encrypt(String.format(format,plaintext));
    }
  /**
   * @Description: 加密
   * @Param [plaintext]
   * @return java.lang.String
   * @since 2020/6/17 14:41
   */
  public static String encrypt(String plaintext)  {
      try{
          Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
          byte[] bytes = cipher.doFinal(plaintext.getBytes(codeType));
          return new String(Base64.encodeBase64(bytes),codeType);
      }catch (Exception e){
          throw new BusinessException(BaseDataExceptionCode.DES_EXCEPTION_CODE,e);
      }
    }
    /**
     * @Description: 解密
     * @Param [cipherText]
     * @return java.lang.String
     * @since 2020/6/17 14:41
     */
    public static String decrypt(String cipherText){
        try{
            byte[] buf = Base64.decodeBase64(cipherText);
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
            return new String(cipher.doFinal(buf),codeType);
        }catch (Exception e){
            throw new BusinessException(BaseDataExceptionCode.DES_EXCEPTION_CODE,e);
        }
    }

    public static void main(String[] args) {
        System.out.println(DESUtil.encrypt(CommonConst.Password.SALT_FORMAT, "123456"));
    }
}
