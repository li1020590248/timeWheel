import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class RSAUtils {

    public static final String KEY_ALGORITHM = "RSA";
    public  static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final Charset charset = Charset.forName("UTF-8");
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();

    public static Map<String, String> genKey() throws NoSuchAlgorithmException {
        Map<String, String> keyMap = new HashMap<>();
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom random = new SecureRandom();
        //用1024位,最好用2048位
        keygen.initialize(1024, random);
        // 取得密钥对
        KeyPair kp = keygen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
        String privateKeyString = encoder.encodeToString(privateKey.getEncoded());
        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        String publicKeyString = encoder.encodeToString(publicKey.getEncoded());
        keyMap.put(PUBLIC_KEY, publicKeyString);
        keyMap.put(PRIVATE_KEY, privateKeyString);
        return keyMap;
    }

    public static String sign(String privateKey, String plain_text) {

        String signString = null;
        try {
            Signature signature = Signature.getInstance("");
            byte[] keyBytes = decoder.decode(privateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(spec);
            signature.initSign(rsaPrivateKey);
            signature.update(plain_text.getBytes(charset));
            byte[] signed = signature.sign();
            //可以选择是使用十六进制返回还是encode返回字符串，只要验签的时候使用对应方式再转回byte即可
            signString = encoder.encodeToString(signed);
            //signString = bytesToHexString(signed);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return signString;
    }

    public static boolean verifySing(String publicKey, String paramsString,String signString) {
        boolean signedSuccess = false;
        try {
            byte[] keyBytes = decoder.decode(publicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPublicKey rsaPublicKey =  (RSAPublicKey) keyFactory.generatePublic(spec);
            Signature verifySign = Signature.getInstance(SIGNATURE_ALGORITHM);
            verifySign.initVerify(rsaPublicKey);
            verifySign.update(paramsString.getBytes(charset));
            //与签名时使用的方式对应
            signedSuccess = verifySign.verify(decoder.decode(signString));
            //signedSuccess = verifySign.verify(hexToByteArray(signString));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException |InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return signedSuccess;
    }

    /**
     * bytes[]换成16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte aSrc : src) {
            //byte转int会发生类型提升，变为32位，而java存储的是补码，
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * hex字符串转byte数组
     * @param inHex 待转换的Hex字符串
     * @return  转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex){
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1){
            //奇数
            hexlen++;
            result = new byte[(hexlen/2)];
            inHex="0"+inHex;
        }else {
            //偶数
            result = new byte[(hexlen/2)];
        }
        int j=0;
        for (int i = 0; i < hexlen; i+=2){
            result[j] = (byte)Integer.parseInt(inHex.substring(i,i+2),16);
            j++;
        }
        return result;
    }

    /**
     * @Description 对除掉sign和值为null的参数进行排序,返回排序后拼接字符串
     * @Author  liping
     * @Date   2019/8/14 16:29
     * @Param  [sArray]
     * @Return String 准备签名的字符串
     **/
    public static String paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<>(sArray.size());
        if (sArray == null || sArray.size() <= 0) {
            return "";
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") ||"signature".equals(key)) {
                continue;
            }
            result.put(key, value);
        }
        //按字典排序后拼接如 "name=liping&sex=man"
        StringBuilder sb  = new StringBuilder();
        List<String> keys = new ArrayList<>(result.keySet());
        Collections.sort(keys);
        for(String key : keys){
            sb.append(key).append("=");
            sb.append(result.get(key));
            sb.append("&");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
