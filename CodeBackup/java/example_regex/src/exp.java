import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class exp {
    public static void main(String[] args) throws ParseException {
            String s = "0.8999999999999999";
            if((s.substring(s.indexOf("."),s.length()).length()>4)) {
                String s3 = s.substring(s.indexOf("."),s.length());
                String s4 = s.substring(s.indexOf("."),s.indexOf(".") + 4);
            System.out.println(s3);  // Output: 10.12
            System.out.println(s4);  // Output: 10.12
        }
            String s1 = s.substring(0,s.indexOf(".") + 4);
            DecimalFormat decimalFormat = new DecimalFormat("0.#####");
            String result = decimalFormat.format(Double.valueOf(s));
            String result1 = decimalFormat.format(Double.valueOf(s1));
            System.out.println(result);  // Output: 10.12
            System.out.println(result1);  // Output: 10.12
            System.out.println(s1);  // Output: 10.12  #Input: 10.120000

    }
}