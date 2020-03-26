import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static enum CallType{
        EOPS("EOPS"),
        OPEN_APP("OPEN_APP"),
        OTHER("OTHER");
        public final String callType;
        private CallType (String callType) {
            this.callType = callType;
        }

    }

    public static void test(CallType callType){
        if(callType.equals(CallType.OTHER)){
            System.out.println("dsafdafadsf");
        }else {
            System.out.println("dasfadsdddddddddddddddddddd");
        }
    }

    public static String  tes1(CallType callType){
        if(callType.equals(CallType.EOPS)){
            return "sdfsdfdsf";
        }else {
            System.out.println("dasfadsdddddddddddddddddddd");
        }
        return null;
    }

    public static void main(String[] args) {
//        String  a = "https://mail.huawei.com ";
//        Pattern p = Pattern.compile("(?<=\\$)\\d+");
//        Matcher m1;
//
//        p = Pattern.compile(".+(?=m)");
//        m1 = p.matcher(a);
//        while (m1.find ())
//        {
//            String group = m1.group ();
//            System.out.println (group);
//        }
        String str = "{\"alipay_trade_pay_response\":{\"code\":\"10003\",\"msg\":\" order success pay inprocess\",\"trade_no\":\"332019111422001417670560299722\",\"out_trade_no\":\"20191113004520015737218080000000\",\"buyer_logon_id\":\"774***@qq.com\",\"total_amount\":\"0.01\",\"receipt_amount\":\"0.00\",\"buyer_pay_amount\":\"0.00\",\"point_amount\":\"0.00\",\"invoice_amount\":\"0.00\",\"buyer_user_id\":\"2088702879517673\"},\"sign\":\"MEUCIEr6YhRHSK1kIABPYIbiBjdITw7NaKwctx6yxG0/XFgfAiEAuWz1luwqrj/y7LsUluyhk8XfCsGl4fn4wmGWrkJP4Tg=\"}";
        String str2 = "{\"code\":\"10003\",\"msg\":\" order success pay inprocess\",\"{trade_no}\":\"332019111422001417670560299722\",\"out_trade_no\":\"20191113004520015737218080000000\",\"buyer_logon_id\":\"774***@qq.com\",\"total_amount\":\"0.01\",\"receipt_amount\":\"0.00\",\"buyer_pay_amount\":\"0.00\",\"point_amount\":\"0.00\",\"invoice_amount\":\"0.00\",\"buyer_user_id\":\"2088702879517673\"},\"sign\":\"MEUCIEr6YhRHSK1kIABPYIbiBjdITw7NaKwctx6yxG0/XFgfAiEAuWz1luwqrj/y7LsUluyhk8XfCsGl4fn4wmGWrkJP4Tg=\"}";
        // 创建 Pattern 对象

        Pattern r = Pattern.compile("\\{.*?(\\{.*}(?=.*}))");
        // 现在创建 matcher 对象
        Matcher m = r.matcher(str);
        if(m.find()){
            System.out.println(m.group(1));
//            Pattern r1 = Pattern.compile(".+}(?=.*})");
//            // 现在创建 matcher 对象
//            Matcher m1 = r1.matcher(m.group(1));
//            if(m1.find()){
//                System.out.println(m1.group());
//            }
        }
//        String results = "{\"name\": \"liping\",\"age\":\"18\"}";
//        Map<String,String> map = new HashMap<>();
//        map.put("name","liping");
//        map.put("age","18");
        //map.put("pay_info","{\"name\": \"liping\",\"age\":\"18\"}");
//        Map<String,String> map1 = JSON.parseObject(map.get("pay_info"),Map.class);
//        String name = map1.get("name");
//         List<String> appOrgKeyLists = new ArrayList<>();
//        appOrgKeyLists.add("a");
//        appOrgKeyLists.add("b");
//        appOrgKeyLists.add("c");
//        appOrgKeyLists.add("d");
//        boolean signSuccess = false;
//        for (int i = 0; i < appOrgKeyLists.size(); i++) {
//
//            if (!"c".equals(appOrgKeyLists.get(i))) {
//                System.out.println("进来了");
//                continue;
//            }
//            signSuccess = true;
//            System.out.println("完成了");
//            break;
//        }
//        System.out.println("signSuccess=" + signSuccess);
//        tes1(CallType.EOPS);
//         System.out.println(CallType.EOPS.name());
//        System.out.println(CallType.EOPS);
//        String statusInfo = null;
//        String str1 = "errCode[FREQUENCY_LIMITED],errMsg[你的操作过于频繁]#1";
//        String str2 = "Internal error#1";
//
//        if (statusInfo.contains("errCode") && statusInfo.contains("errMsg")) {
//            Pattern p1 = Pattern.compile("\\[(.*?)\\]");
//            Matcher m = p1.matcher(str1);
//        while (m.find()) {
//           statusInfo = m.group();
//        }
//        if(statusInfo.length() >0){
//            statusInfo = statusInfo.substring(1,statusInfo.length()-1);
//        }
//        }else {
//            statusInfo = str2.split("#")[0];
//        }
//        System.out.println("statusInfo:" + statusInfo);
    }
}
