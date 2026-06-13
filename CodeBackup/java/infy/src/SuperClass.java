public class SuperClass {
    private void displayName() {
        System.out.println("Super Class");
    }

    public static void main(String[] args) {
    SuperClass superClass = new SubClass();
superClass.displayName();
}}