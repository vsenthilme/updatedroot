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
}
