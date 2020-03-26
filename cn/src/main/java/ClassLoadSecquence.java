public class ClassLoadSecquence {

    public static int aa = 5;

    static {
        System.out.println(aa);
        System.out.println("jinlai ");
    }

    {
        System.out.println("kolll");
    }

    public ClassLoadSecquence(){
        System.out.println("构造器");
    }
}
