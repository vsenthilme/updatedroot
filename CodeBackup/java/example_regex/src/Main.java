import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException {
        String s1 = "[{\"status\":\"Cancel\",\"date_time\":\"2024-06-11 01:51:17\"}]";
        String ss1 = "[{\"staus\":\"date_Added\",\"date_time\":\"2024-06-24\n" +
                "12:18:30\"},{\"staus\":\"date_Equiped\",\"date_time\":\"2024-06-27 08:26:28\"},{\"staus\":\"date_Shipped\",\"date_time\":\"2024-06-27\n" +
                "08:28:05\"},{\"staus\":\"date_Received\",\"date_time\":\"2024-06-27 08:31:16\"},{\"staus\":\"date_Not_Received\",\"date_time\":\"2024-06-27 08:25:45\"}]";
String s2 = s1.replaceAll("\\/","");
String ss2 = ss1.replaceAll("\\/","");
String ss3 = ss2.replaceAll("\\n"," ");
String s3 = s2.substring(s2.indexOf(":")+2,s2.indexOf(",")-1);
String s4 = s2.substring(s2.indexOf("date_time")+12,s2.indexOf("}")-1);
        String equipedString = ss3.substring(ss3.indexOf("date_Equiped"), ss3.indexOf("date_Equiped") + 46);
        String shipmentStatus = equipedString.substring(0,equipedString.indexOf("date_Equiped")+12);
        String shipmentStatusDate = equipedString.substring(equipedString.indexOf("date_time")+12,equipedString.indexOf("date_time")+31);
        String shippedString = ss3.substring(ss3.indexOf("date_Shipped"), ss3.indexOf("date_Shipped") + 46);
        String shippedShipmentStatus = shippedString.substring(0,shippedString.indexOf("date_Shipped") + 12);
        String shippedShipmentStatusDate = shippedString.substring(shippedString.indexOf("date_time")+12,shippedString.indexOf("date_time") + 31);
        String receivedString = ss3.substring(ss3.indexOf("date_Received"), ss3.indexOf("date_Received") + 47);
        String rxdShipmentStatus = receivedString.substring(0,shippedString.indexOf("date_Received") + 14);
        String rxdShipmentStatusDate = receivedString.substring(receivedString.indexOf("date_time")+12,receivedString.indexOf("date_time") + 31);
        String nRxdString = ss3.substring(ss3.indexOf("date_Not_Received"), ss3.indexOf("date_Not_Received") + 51);
        String nRxdShipmentStatus = nRxdString.substring(0,shippedString.indexOf("date_Not_Received") + 18);
        String nRxdShipmentStatusDate = nRxdString.substring(nRxdString.indexOf("date_time")+12,nRxdString.indexOf("date_time") + 31);
String strDate = "2024-06-20 14:07:41";
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
        Long time = date.getTime();
        System.out.println(s2);
        System.out.println(ss2);
        System.out.println(ss3);
        System.out.println(equipedString);
        System.out.println(shipmentStatus);
        System.out.println(shipmentStatusDate);
        System.out.println(shippedString);
        System.out.println(shippedShipmentStatus);
        System.out.println(shippedShipmentStatusDate);
        System.out.println(receivedString);
        System.out.println(rxdShipmentStatus);
        System.out.println(rxdShipmentStatusDate);
        System.out.println(nRxdString);
        System.out.println(nRxdShipmentStatus);
        System.out.println(nRxdShipmentStatusDate);
//        System.out.println(s3);
//        System.out.println(s4);
//        System.out.println(date);
//        System.out.println(time);
//        System.out.println(new Date().getTime());
    }
}