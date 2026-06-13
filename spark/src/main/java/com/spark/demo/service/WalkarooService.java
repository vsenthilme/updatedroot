package com.spark.demo.service;

import com.spark.demo.model.*;
import com.spark.demo.util.ConditionUtils;
import com.spark.demo.util.DatabaseConnectionUtil;
import com.spark.demo.util.SparkSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@Slf4j
public class WalkarooService {

    // SparkSession
    SparkSession spark = SparkSessionUtil.createSparkSession();

    Properties connProp = DatabaseConnectionUtil.getWalkarooCoreDatabaseConnectionProperties();
    String jdbcUrl = DatabaseConnectionUtil.getWalkarooCoreJdbcUrl();

    /**
     * @param findBusinessPartner
     * @return
     * @throws Exception
     */
    public List<BusinessPartnerV2> findBusinessPartner(FindBusinessPartner findBusinessPartner) throws Exception {
        try {
            String sqlQuery = "SELECT " +
                    " LANG_ID as languageId, " +
                    " C_ID as companyCodeId, " +
                    " PLANT_ID as plantId, " +
                    " WH_ID as warehouseId, " +
                    " PARTNER_TYP as businessPartnerType, " +
                    " PARTNER_CODE as partnerCode, " +
                    " PARTNER_NM as partnerName, " +
                    " STATUS_ID as statusId, " +
                    " CTD_BY as createdBy, " +
                    " CTD_ON as createdOn " +
                    " FROM tblbusinesspartner";

            List<String> conditions = new ArrayList<>();
            ConditionUtils.addCondition(conditions, "C_ID", findBusinessPartner.getCompanyCodeId());
            ConditionUtils.addCondition(conditions, "PLANT_ID", findBusinessPartner.getPlantId());
            ConditionUtils.addCondition(conditions, "LANG_ID", findBusinessPartner.getLanguageId());
            ConditionUtils.addCondition(conditions, "WH_ID", findBusinessPartner.getWarehouseId());
            ConditionUtils.addCondition(conditions, "PARTNER_CODE", findBusinessPartner.getPartnerCode());
            ConditionUtils.addCondition(conditions, "PARTNER_NM", findBusinessPartner.getPartnerName());
            ConditionUtils.addCondition(conditions, "CTD_BY", findBusinessPartner.getCreatedBy());

            ConditionUtils.numericConditions(conditions, "PARTNER_TYP", findBusinessPartner.getBusinessPartnerType());
            ConditionUtils.numericConditions(conditions, "STATUS_ID", findBusinessPartner.getStatusId());

            ConditionUtils.addDateCondition(conditions, "CTD_ON", findBusinessPartner.getStartCreatedOn(), findBusinessPartner.getEndCreatedOn());
            ConditionUtils.addDateCondition(conditions, "UTD_ON", findBusinessPartner.getStartUpdatedOn(), findBusinessPartner.getEndUpdatedOn());

            if (!conditions.isEmpty()) {
                sqlQuery += " WHERE IS_DELETED = 0  AND " + String.join(" AND ", conditions);
            } else {
                sqlQuery += " WHERE IS_DELETED = 0 ";
            }

            Dataset<Row> data = spark.read()
                    .option("fetchSize", "10000")
                    .option("pushDownloadPredicate", true)
                    .jdbc(jdbcUrl, "(" + sqlQuery + ") as tmp", connProp);

            data.show();
            Encoder<BusinessPartnerV2> businessPartnerEncoder = Encoders.bean(BusinessPartnerV2.class);
            Dataset<BusinessPartnerV2> dataset = data.as(businessPartnerEncoder);
            return dataset.collectAsList();
        } catch (Exception e) {
            log.error("Find Business Partner Spark Exception : " + e.toString());
            throw e;
        }
    }

    
}