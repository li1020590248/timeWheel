import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UnionRSASignUtils {

    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();
    public static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /**
     * @Description queryString
     * eg: certId=69597475696&qrCode=https://qr.95516.com/03095810/0ed77ab3958070904e4d4aabe5bbf8b730a4e57102&reqType=0120000903&version=1.0.0
     * @Author  liping
     * @Date   2019/9/2 14:44
     * @Param  [queryString]
     * @Return java.lang.String
     **/
    public static String sortSignParams(String queryString){
        String preSignString = null;
        if(StringUtils.isBlank(queryString)){
            return preSignString;
        }
        String[] keyValueStr = queryString.split("&");
        Map<String,String> map = new HashMap<>();

        for(String keyValue : keyValueStr){
            String[] keyValueArgs = keyValue.split("=");

            map.put(keyValueArgs[0],keyValueArgs[1]);
        }
        preSignString = RSAUtils.paraFilter(map);
        return preSignString;
    }

    public static byte[] sha1X16(String data, String encoding) {
        byte[] bytes = sha1(data, encoding);
        StringBuilder sha1StrBuff = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (Integer.toHexString(0xFF & bytes[i]).length() == 1) {
                sha1StrBuff.append("0").append(
                        Integer.toHexString(0xFF & bytes[i]));
            } else {
                sha1StrBuff.append(Integer.toHexString(0xFF & bytes[i]));
            }
        }
        try {
            return sha1StrBuff.toString().getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * sha1计算.
     *
     * @param
     * @return 计算结果
     */
    private static byte[] sha1(String dataStr,String encoding) {
        byte[] data = new byte[0];
        try {
            data = dataStr.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(data);
            return md.digest();
        } catch (Exception e) {
            return null;
        }
    }

    public static String sign(String preSignString,String privateKey){
        String signString = null;
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            byte[] keyBytes = decoder.decode(privateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(spec);
            signature.initSign(rsaPrivateKey);
            signature.update(sha1X16(preSignString,"utf-8"));
            byte[] signed = signature.sign();
            //可以选择是使用十六进制返回还是encode返回字符串，只要验签的时候使用对应方式再转回byte即可
            signString = encoder.encodeToString(signed);
            //signString = bytesToHexString(signed);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return signString;
    }

    public static boolean veritySign(String publicKey,String signStr,String preSignString){
        boolean signedSuccess = false;
        try {
            byte[] keyBytes = decoder.decode(publicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPublicKey rsaPublicKey =  (RSAPublicKey) keyFactory.generatePublic(spec);
            Signature verifySign = Signature.getInstance(SIGNATURE_ALGORITHM);
            verifySign.initVerify(rsaPublicKey);
            verifySign.update(sha1X16(preSignString,"utf-8"));
            //与签名时使用的方式对应
            signedSuccess = verifySign.verify(decoder.decode(signStr));
            //signedSuccess = verifySign.verify(hexToByteArray(signString));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException |InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return signedSuccess;
    }

    public static void main(String[] args) {
        String urlpreEncode = "https://qr.95516.com/03095810/0ed77ab3958070904e4d4aabe5bbf8b730a4e57102?device_info=89757";
        String urlencode = null;
        try {
            urlencode = URLEncoder.encode(urlpreEncode,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String queryString = "qrCode="+urlencode+"&reqType=0120000903&version=1.0.0";
        System.out.println("queryString : " + queryString);
        StringBuilder sb = new StringBuilder();
        StringBuffer sbb  = new StringBuffer();
        String signStr = sortSignParams(queryString);
        String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOKBMLIaSO+aa851x1dZwd45eZdq5UawtBPBP9o9KSrRwiho99Vt2vQ7w4XOgy+vozjnxKBF7KURclEYvxr87uY3Ts0VWvKg9Chvji/oPvqq1HPtXfdRGIsarpLIKKrBS8LOPmq9yfEYJRw7ARMfUPoFsglBUoIxjq/MbXYGVGzRAgMBAAECgYAOWnN0hdfjXxy/y5TjQyvW4juDTeHTlfUDX3alZMTMEtjD6Mrzum9qlLLyMsD7b5zfSUibg3LYjv94hiHuGuMkkwlzNHHuWbO7jdASaufTOn4JAvi4YSXw5w5INDajg2KsT2KhC+LZ2iYk0eSXcHTdhXeEjH52YHMF3EFpan8DRQJBAPj4Dk10bzAuOf0ha32/rl9T6rcqTEE68gV17wQNN5HMzpEORYnFxsr5NJksz/nqrmSnNuZknZkLYfn4zCIRbncCQQDo5rn6r2r9vmUypfT9zp+N0BXBaArcrs71V0KGmRf0H300ULo45G+Gj4U/uydH5oM1uIdGN4BuwaqzH+0Gkej3AkEA2TDsqT0t0kZBaEXDVAvnvlmipO9NXophn8nmHQ66r4KPoZNlrm7OoTsNW6LwPAEy6bPF/O9BXMXFTyBwVDgIuQJAJlAWVhvXUOiqQxuz4rDD5JNPkOJprwX+u7NgGT5EB3/Km/mPuN4zToJ7nBFQk+5itUqmzjFBKncnepIM7uC/3wJBAOuEfosYkQD+44RMXitZbyoHRq5lYRPKrAYu38Q+kYBQI8mcVdJTOIR0yGGbqDVHs8C4YpdxIm/Wq9vPP3Dvsww=";
        String publicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDigTCyGkjvmmvOdcdXWcHeOXmXauVGsLQTwT/aPSkq0cIoaPfVbdr0O8OFzoMvr6M458SgReylEXJRGL8a/O7mN07NFVryoPQob44v6D76qtRz7V33URiLGq6SyCiqwUvCzj5qvcnxGCUcOwETH1D6BbIJQVKCMY6vzG12BlRs0QIDAQAB";

        String signature = sign(signStr, privateKey);
        System.out.println("signnature = " + signature);
        System.out.println("请求报文："+ queryString + "signature="+ signature);
        boolean result = veritySign(publicKey,signature,queryString);
        System.out.println("签名结果=" + result);
    }
}
