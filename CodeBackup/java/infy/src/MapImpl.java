import java.util.HashMap;
import java.util.Map;

public class MapImpl {
    public static void main(String[] args) {
        Map< Person, String> map=new HashMap<Person, String>();
        map.put(new Person("Jack"), "sendSalesReport");
        map.put(new Person("Jack"), "sendAudit Report");
        map.put(new Person("mady"), "sendInventoryReport");
        System.out.println(map.size());
    }
}