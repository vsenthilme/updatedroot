public class Main extends TestParent{
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Parent obj = new Parent();
        obj.print();
        System.out.println(obj.a);
        Child obj1 = new Child();
        obj1.print();
        System.out.println(obj1.a);
        SecondChild obj2 = new SecondChild();
        obj2.print();
        System.out.println(obj2.a);
        Parent obj4 = new SecondChild();
        obj4.print();
        System.out.println(obj4.a);
        TestParent.m1();
    }
}