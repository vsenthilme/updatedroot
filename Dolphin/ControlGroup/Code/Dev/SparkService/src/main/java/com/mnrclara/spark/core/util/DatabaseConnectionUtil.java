package com.mnrclara.spark.core.util;

import java.util.Properties;


public class DatabaseConnectionUtil {

    // ALM DEV
    public static Properties getDatabaseConnectionProperties() {
        Properties connProp = new Properties();
        connProp.put("user", "sa");
        connProp.put("password", "Do1cavIFK4^$pQ^zZYsX");
        connProp.put("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return connProp;
    }

    // DB CONNECTION
    public static String getJdbcUrl() {
        return "jdbc:sqlserver://3.109.20.248;databaseName=WMS_ALMDEV";
    }

    // WMS CORE
    public static  Properties getCoreDatabaseConnectionProperties(){
        Properties connProp = new Properties();
        connProp.put("user", "sa");
        connProp.put("password", "30NcyBuK" );
        connProp.put("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return connProp;
    }

    // WMS DB CONNECTION
    public static String getCoreJdbcUrl(){
        return "jdbc:sqlserver://35.154.84.178;databaseName=WMS_CORE";
    }
}
