import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class dateTime {
    public static void main(String[] args) throws ParseException {
        String inDate = "2024-07-17";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp ts = new Timestamp(((java.util.Date)df.parse(inDate)).getTime());
//        Date rescheduleddate = DateUtils.convertStringToYYYYMMDD(createdConsignmentWebhook.getRescheduled_date());
        Date rescheduleddate = new SimpleDateFormat("yyyy-MM-dd").parse(inDate);
        Long rescheduledDateMilliSec = rescheduleddate.getTime();
        long epoch = rescheduledDateMilliSec/1000;
            System.out.println(rescheduleddate);
            System.out.println(rescheduledDateMilliSec);
            System.out.println(ts.getTime());
            System.out.println(epoch);

            Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        System.out.println(strDate);

//        LocalTime time = LocalTime.now();
//        strDate = strDate + " " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond();
//        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
        System.out.println(date2);

    }
}