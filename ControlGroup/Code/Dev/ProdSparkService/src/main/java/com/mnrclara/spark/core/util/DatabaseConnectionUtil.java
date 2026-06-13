package com.mnrclara.spark.core.util;

import java.util.Properties;


public class DatabaseConnectionUtil {

    // ALM DEV
    public static Properties getDatabaseConnectionProperties() {
        Properties connProp = new Properties();
        connProp.put("user", "sa");
        connProp.put("password", "30NcyBuK");
        connProp.put("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return connProp;
    }

    // DB CONNECTION
    public static String getJdbcUrl() {
        return "jdbc:sqlserver://10.0.2.8;databaseName=WMS_ALMPRD";
    }
<<<<<<< HEAD

    //============================================ WMS Walkaroo PROD V3================================
    public static String getWalkarooCoreJdbcUrl() {
        return "jdbc:sqlserver://13.203.34.203;databaseName=WMS_WK_PRD";
    }

    public static Properties getWalkarooCoreDatabaseConnectionProperties() {
        Properties connProp = new Properties();
        connProp.put("user", "sa");
        connProp.put("password", "2iptDVoe6WZw3^()D4uKb");
        connProp.put("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return connProp;
    }
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
}
