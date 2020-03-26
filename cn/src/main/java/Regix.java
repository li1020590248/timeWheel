import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

public class Regix {

    public static void main(String[] args) {

        //System.out.println(ClassLoadSecquence.aa);
        ClassLoadSecquence classLoadSecquence = new ClassLoadSecquence();
        String str  = "cert_id:12345678900";
        System.out.println(isInteger(str));

    }

    public static String isInteger(String str) {
        String str1 = null;
        if(str.startsWith("cert_id:")){
            str1 = StringUtils.substring(str,8);
        }
        return str1;
    }
}
