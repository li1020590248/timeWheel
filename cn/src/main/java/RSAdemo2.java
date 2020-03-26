import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class RSAdemo2 {


    public static void main(String[] args) throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("name","liping");

        System.out.println( StringUtils.isBlank(map.get("age")));
        System.out.println(Integer.toHexString(28));
        Map<String, String> keyMap = RSAUtils.genKey();
        String publicKey = keyMap.get(RSAUtils.PUBLIC_KEY);
        String privateKey = keyMap.get(RSAUtils.PRIVATE_KEY);
        publicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDigTCyGkjvmmvOdcdXWcHeOXmXauVGsLQTwT/aPSkq0cIoaPfVbdr0O8OFzoMvr6M458SgReylEXJRGL8a/O7mN07NFVryoPQob44v6D76qtRz7V33URiLGq6SyCiqwUvCzj5qvcnxGCUcOwETH1D6BbIJQVKCMY6vzG12BlRs0QIDAQAB";
        privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOKBMLIaSO+aa851x1dZwd45eZdq5UawtBPBP9o9KSrRwiho99Vt2vQ7w4XOgy+vozjnxKBF7KURclEYvxr87uY3Ts0VWvKg9Chvji/oPvqq1HPtXfdRGIsarpLIKKrBS8LOPmq9yfEYJRw7ARMfUPoFsglBUoIxjq/MbXYGVGzRAgMBAAECgYAOWnN0hdfjXxy/y5TjQyvW4juDTeHTlfUDX3alZMTMEtjD6Mrzum9qlLLyMsD7b5zfSUibg3LYjv94hiHuGuMkkwlzNHHuWbO7jdASaufTOn4JAvi4YSXw5w5INDajg2KsT2KhC+LZ2iYk0eSXcHTdhXeEjH52YHMF3EFpan8DRQJBAPj4Dk10bzAuOf0ha32/rl9T6rcqTEE68gV17wQNN5HMzpEORYnFxsr5NJksz/nqrmSnNuZknZkLYfn4zCIRbncCQQDo5rn6r2r9vmUypfT9zp+N0BXBaArcrs71V0KGmRf0H300ULo45G+Gj4U/uydH5oM1uIdGN4BuwaqzH+0Gkej3AkEA2TDsqT0t0kZBaEXDVAvnvlmipO9NXophn8nmHQ66r4KPoZNlrm7OoTsNW6LwPAEy6bPF/O9BXMXFTyBwVDgIuQJAJlAWVhvXUOiqQxuz4rDD5JNPkOJprwX+u7NgGT5EB3/Km/mPuN4zToJ7nBFQk+5itUqmzjFBKncnepIM7uC/3wJBAOuEfosYkQD+44RMXitZbyoHRq5lYRPKrAYu38Q+kYBQI8mcVdJTOIR0yGGbqDVHs8C4YpdxIm/Wq9vPP3Dvsww=";

        System.out.println("publicKey: " + publicKey);
        System.out.println("privateKey: " + privateKey);
        Map<String,String> params = new HashMap<>();
        params.put("app_id","2019081366189445");
        params.put("charset","UTF-8");
        params.put("code","691c834484ad466f997c4008192cTX26");
        params.put("grant_type","authorization_code");
        params.put("method","alipay.system.oauth.token");
        params.put("sign_type","RSA2");
        params.put("timestamp","2019-08-14 09:37:39");
        params.put("version","1.0");

        String preSign = RSAUtils.paraFilter(params);
        System.out.println("预签名数据 : "+ preSign);
        String signString = RSAUtils.sign(privateKey,preSign);
        System.out.println("SHA256withRSA签名后sign-----》" + signString);
        params.put("sign",signString);
        String verifySing = RSAUtils.paraFilter(params);
        System.out.println("预验证签名数据 : "+ preSign);
        boolean flag = RSAUtils.verifySing(publicKey,verifySing,signString);
        System.out.println("验证结果: " + flag);

    }

}
