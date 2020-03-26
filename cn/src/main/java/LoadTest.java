public class LoadTest {
    public static void main(String[] args) {

        StringBuilder stringBuilder = new StringBuilder();
        //去掉最后一个“.”
        String routeKey = stringBuilder.toString();

        System.out.println("sddf"+routeKey+"1sdfdsf");
        System.out.println(Test2.n);
    }
}
  class Test2 {
    public static final int n = 2;
    static {
        System.out.println("test");
   }
}

