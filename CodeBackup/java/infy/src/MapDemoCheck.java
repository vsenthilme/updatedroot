import java.util.*;
public class MapDemoCheck {
    public static void main(String[] args) {
        //Which is the correct way of comparing the contents of the below strings?

                String firstString ="String";
        StringBuilder secondString =new StringBuilder("String");

//        firstString == secondString;
        System.out.println(firstString.contains(secondString));
        System.out.println(secondString.equals(firstString));
        System.out.println(firstString.equals(secondString));
    }}