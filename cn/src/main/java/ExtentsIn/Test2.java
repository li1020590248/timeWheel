package ExtentsIn;

public class Test2 {
    public static void main(String[] args) {
        Test1 test1 = new Test1();
        if(test1 instanceof Test){
            System.out.println("属于父类");
        }
    }
}
