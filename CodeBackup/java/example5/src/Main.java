import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String code = convertEpochToYYYYMMDD(1726130296L);
        System.out.println(code);
        String milli = String.valueOf(System.currentTimeMillis()) + 1;
        Long m2 = Long.valueOf(String.valueOf(System.currentTimeMillis())+1);
        System.out.println(milli);
        System.out.println(m2);
        int suffix = 1; // You can change this to 1, 2, or 3
        Long m3 = Long.valueOf(System.currentTimeMillis() + "" + 1);
        System.out.println(m3);
    }

    public static String convertEpochToYYYYMMDD(Long epoch) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(epoch * 1000));
    }

}