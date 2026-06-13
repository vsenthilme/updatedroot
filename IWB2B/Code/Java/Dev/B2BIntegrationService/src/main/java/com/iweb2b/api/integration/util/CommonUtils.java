package com.iweb2b.api.integration.util;

import java.beans.PropertyDescriptor;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtils {

	/**
	 * 
	 * @author Murugavel
	 *
	 */
	public static enum DashboardTypes {
		AGREEMENT, AGREEMENT_TOTAL, AGREEMENT_SENT, AGREEMENT_RECEIVED, AGREEMENT_RESENT
	}

	/**
	 * getNullPropertyNames
	 * 
	 * @param source
	 * @return
	 */
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	/**
	 * copy
	 * 
	 * @param fromBean
	 * @return
	 */
	public static Object copy(Object fromBean) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		XMLEncoder out = new XMLEncoder(bos);
		out.writeObject(fromBean);
		out.close();
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		XMLDecoder in = new XMLDecoder(bis);
		Object toBean = in.readObject();
		in.close();
		return toBean;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getStatusMapping(String mapString) {
		/*
		 * Pickup scan - Pickup completed
		 * Station sending/DC sending - Intransit to HUB
		 * Station arrival - Intransit to Hub
		 * DC arrival - Inscan at Hub
		 * Delivery Scan - Attempted
		 * Sign Scan - Delivered
		 * Abnormal Parcel Scan - Cancel
		 * Returned Parcel Scan - RTO
		 * Returned Signed - RTO
		 */
		Map<String, String> mapStatus = new HashMap<>();
		mapStatus.put("PICKUP SCAN", "shipment_clear_successfully");
		mapStatus.put("STATION SENDING/DC SENDING", "intransit_to_hub");
		mapStatus.put("STATION ARRIVAL", "inscan_at_hub");	
		mapStatus.put("DC ARRIVAL", "reached_at_hub");
		mapStatus.put("DELIVERY SCAN", "accept");
		mapStatus.put("SIGN SCAN", "delivered");
		mapStatus.put("ABNORMAL PARCEL SCAN", "attempted");
		mapStatus.put("RETURNED PARCEL SCAN", "rto");
		mapStatus.put("RETURNED SIGNED", "delivered");
		mapStatus.put("DELIVERED", "delivered");
		return mapStatus.get(mapString);
	}

	/**
	 * FLOW LOGISTICS
	 * @param mapString
	 * @return
	 */
	public static String getFlowLogisticsStatusMapping(String mapString) {

		Map<String, String> mapStatus = new HashMap<>();
		mapStatus.put("outfordelivery", "accept");
		mapStatus.put("reachedathub", "reached_at_hub");
		mapStatus.put("intransittohub", "intransit_to_hub");
		mapStatus.put("attempted", "attempted");
		mapStatus.put("inscan_at_hub", "inscan_at_hub");
		mapStatus.put("rto", "rto");
		mapStatus.put("rto_outfordelivery", "accept");
		mapStatus.put("rto_delivered", "delivered");
		mapStatus.put("cancelled", "cancel");
		mapStatus.put("delivered", "delivered");
		mapStatus.put("on_hold", "on_hold");
		mapStatus.put("release_on_hold", "release_on_hold");
		mapStatus.put("pickup_completed", "pickup_completed");
		mapStatus.put("undelivered", "attempted");
		mapStatus.put("assigned_courier_partner", "assigned_courier_partner");
		mapStatus.put("pickup_awaited", "pickup_awaited");
		return mapStatus.get(mapString);
	}

	/**
	 * Emirates Post
	 * @param mapString
	 * @return
	 */
	public static String getEmiratesPostStatusMapping(String mapString) {

		Map<String, String> mapStatus = new HashMap<>();
		mapStatus.put("RECEIVED", "pickup_completed");		//Received/Collected
		mapStatus.put("COLLECTED", "pickup_completed");		//Received/Collected
		mapStatus.put("CLC55", "pickup_completed");		//Received/Collected
		mapStatus.put("INTERNATIONAL DISPATCH", "intransit_to_hub");		//International Dispatch
		mapStatus.put("CLC4", "intransit_to_hub");		//International Dispatch
		return mapStatus.get(mapString);
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getStatusMapping_QP(String mapString) {
		/*
		 * Pickup scan - Pickup completed
		 * Station sending/DC sending - Intransit to HUB
		 * Station arrival - Intransit to Hub
		 * DC arrival - Inscan at Hub
		 * Delivery Scan - Attempted
		 * Sign Scan - Delivered
		 * Abnormal Parcel Scan - Cancel
		 * Returned Parcel Scan - RTO
		 * Returned Signed - RTO
		 */
		Map<String, String> mapStatus = new HashMap<>();
//		mapStatus.put("CREATED", "No Mapping");
		mapStatus.put("PICKUP", "pickup_completed");
		mapStatus.put("RECIEVED", "reached_at_hub");	
		mapStatus.put("ASSIGNEDTODRIVER", "accept");
		mapStatus.put("OUTFORDELV", "accept");
		mapStatus.put("DELIVERED", "delivered");
		mapStatus.put("CANCELLED", "cancel");
		mapStatus.put("FAILED", "attempted");
		mapStatus.put("PICKUPSUCCESS", "pickup_completed");
		mapStatus.put("PICKUPFAILED", "attempted");
		mapStatus.put("DELIVERY  FAILED", "attempted");
		mapStatus.put("ITEMDISPATCHEDTOSENDER", "rto");
		mapStatus.put("ITEMSTORED", "inscan_at_hub");
		mapStatus.put("COUNTERDELIVERYSUCCESS", "delivered");
		mapStatus.put("ITEMRECEIVEDATGPO", "reached_at_hub");
		mapStatus.put("RTS", "rto");
		mapStatus.put("DISPATCHEDTOBRANCH", "intransit_to_hub");
		mapStatus.put("RETURNFROMCUSTOMS", "shipment_clear_successfully");
		log.info("%%%%%%%%%%%%%%%%%%%%%----1----> : " + mapString + ":" + mapStatus.get(mapString.trim().toUpperCase()));
		return mapStatus.get(mapString.trim().toUpperCase());
	}
	
	/**
	 * 
	 * @param mapString
	 * @return
	 */
	public static String getStatusMapping_Shopini(String mapString) {
		/*
		 *	Pending - Unmapped
		 *	Equipped - Reached at Hub
		 *	Shipped	- Out for Delivery (accept)
		 *	Received - Delivered
		 *	Not received - Undelivered
		 */
		mapString.toUpperCase();
		Map<String, String> mapStatus = new HashMap<>();
//		mapStatus.put("PENDING", "Unmapped");
		mapStatus.put("EQUIPPED", "reached_at_hub");
		mapStatus.put("SHIPPED", "accept");
		mapStatus.put("RECEIVED", "delivered");
		mapStatus.put("NOT RECEIVED", "undelivered");
		return mapStatus.get(mapString.trim().toUpperCase());
	}

	/**
	 *
	 * @param mapString
	 * @return
	 */
	public static String getFlowMappingFailReasonCode(String mapString) {

//		WMN	WRONG MOBILE NUMBER	165	Delivery attempted  Consignee incorrect phone number
//		RMT	REMOTE SERVICE AREA	"	142"	REMOTE DELIVERY AREA DUE TO REMOTE SERVICE AREA
//		RES	RESCHEDULED FOR NEXT DAY	164	RESCHEDULED FOR NEXT DAY
//		PNR	PAYMENT NOT READY	107	DELIVERY ATTEMPTED - CONSIGNEE REFUSED DUE TO AMOUNT NOT READY
//		OCC	ORDER CANCELLED BY CUSTOMER	105	Delivery attempted - Consignee refused Delivery
//		NSA	NO SERVICE AREA	"	142"	NO SERVICE AREA
//		MSO	MOBILE SWITCHED OFF	166	Delivery attempted  Consignee phone closed
//		MNA	MOBILE NO ANSWER	167	Delivery attempted  Consignee not answered
//		LC	LOCATION CHANGED	103	"DELIVERY ATTEMPTED  CONSIGNEE MOVED TO NEW AND KNOWN ADDRESS	"
//		INA	INCOMPLETE ADDRESS	200	DELIVERY ATTEMPTED- BAD ADDRESS
//		IMP	IMPROPER PACKAGING	301	REFUSED BY CONSIGNEE - INCORRECT CONTENTS
//		HBC	HOLIDAY-BUSINESS CLOSED
//		FD	CUSTOMER REQUESTED FUTURE DELIVERY	164	DELIVERY ATTEMPTED CONSIGNEE REQUEST FUTURE DELIVERY
//		DOOR LOCKED	DOOR LOCKED	162	DELIVERY ATTEMPTED  CONSIGNEE PREMISES CLOSED
//		CUSTOMER REFUSED	CUSTOMER REFUSED	105	Delivery attempted - Consignee refused Delivery
//		CRP	CUSTOMER REFUSED PAY	303	REFUSED - COD ISSUE
//		CRA	CUSTOMER REFUSED TO ACCEPT	105	Delivery attempted - Consignee refused Delivery
//		CNA	CONSIGNEE NOT AVAILABLE	168	DELIVERY ATTEMPTED  CONSIGNEE NOT AT HOME

		mapString.toUpperCase();
		Map<String, String> mapStatus = new HashMap<>();
		mapStatus.put("WRONG MOBILE NUMBER", "165");
		mapStatus.put("REMOTE SERVICE AREA", "142");
		mapStatus.put("RESCHEDULED FOR NEXT DAY", "164");
		mapStatus.put("PAYMENT NOT READY", "107");
		mapStatus.put("ORDER CANCELLED BY CUSTOMER", "105");
		mapStatus.put("NO SERVICE AREA", "142");
		mapStatus.put("MOBILE SWITCHED OFF", "166");
		mapStatus.put("MOBILE NO ANSWER", "167");
		mapStatus.put("LOCATION CHANGED", "103");
		mapStatus.put("INCOMPLETE ADDRESS", "200");
		mapStatus.put("IMPROPER PACKAGING", "301");
		mapStatus.put("CUSTOMER REQUESTED FUTURE DELIVERY", "164");
		mapStatus.put("DOOR LOCKED", "162");
		mapStatus.put("CUSTOMER REFUSED", "105");
		mapStatus.put("CUSTOMER REFUSED PAY", "303");
		mapStatus.put("CUSTOMER REFUSED TO ACCEPT", "105");
		mapStatus.put("CONSIGNEE NOT AVAILABLE", "168");
		return mapStatus.get(mapString.trim());
	}

	/**
	 *
	 * @param mapString
	 * @return
	 */
	public static String getAllStatusShopiniMapping(String mapString) {
		/*
		 *	date_Added - Unmapped
		 *	date_Equiped - Reached at Hub
		 *	date_Shipped	- Out for Delivery (accept)
		 *	date_Received - Delivered
		 *	date_Not_Received - Undelivered
		 */
		Map<String, String> mapStatus = new HashMap<>();
//		mapStatus.put("date_Added", "created");
		mapStatus.put("DATE_EQUIPED", "EQUIPPED");
		mapStatus.put("DATE_SHIPPED", "SHIPPED");
		mapStatus.put("DATE_RECEIVED", "RECEIVED");
		mapStatus.put("DATE_NOT_RECEIVED", "NOT RECEIVED");
		return mapStatus.get(mapString.trim().toUpperCase());
	}
	
	/**
	 * 
	 * @param date
	 * @param tz
	 * @return
	 */
	public static String toISOString(Date date, TimeZone tz) {
        try {
        	String FORMAT_DATE_ISO = "yyyy-MM-dd'T'HH:mm:ss'Z'";
			if (tz == null) {
			    tz = TimeZone.getDefault();
			}
			DateFormat f = new SimpleDateFormat(FORMAT_DATE_ISO);
			f.setTimeZone(tz);
			return f.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

	/**
	 * 
	 * @param date
	 * @return
	 */
    public static String toISOString (Date date) {
        return toISOString(date, TimeZone.getDefault());
    }

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
//		LocalDate localDate = LocalDate.now();
//		System.out.println(localDate);
//		
//		DateTimeFormatter newPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDateTime datetime = LocalDateTime.now();
//		String currentDatetime = datetime.format(newPattern);
//		System.out.println(currentDatetime);
//		
//		String date = toISOString(new Date());
//		log.info("date : " + date);
		
//		Map<String, String> mapStatus = new HashMap<>();
//		mapStatus.put("PICKUP", "pickup_completed");
//		mapStatus.put("RECIEVED", "reached_at_hub");	
//		mapStatus.put("ASSIGNEDTODRIVER", "accept");
//		mapStatus.put("OUTFORDELV", "accept");
//		mapStatus.put("DELIVERED", "delivered");
//		mapStatus.put("CANCELLED", "cancel");
//		mapStatus.put("FAILED", "attempted");
//		mapStatus.put("PICKUPSUCCESS", "pickup_completed");
//		mapStatus.put("PICKUPFAILED", "attempted");
//		mapStatus.put("DELIVERYFAILED", "attempted");
//		mapStatus.put("ITEMDISPATCHEDTOSENDER", "rto");
//		mapStatus.put("ITEMSTORED", "inscan_at_hub");
//		mapStatus.put("COUNTERDELIVERYSUCCESS", "delivered");
//		mapStatus.put("ITEMRECEIVEDATGPO", "reached_at_hub");
//		mapStatus.put("RTS", "rto");
//		mapStatus.put("DISPATCHEDTOBRANCH", "intransit_to_hub");
//		mapStatus.put("RETURNFROMCUSTOMS", "shipment_clear_successfully");
//		
//		String s = "RECIEVED";
//		s = s.replaceAll("\\s" , "");
//		System.out.println(s);
//		System.out.println(mapStatus.get(s.toUpperCase()));
//		
//		getStatusMapping_QP(s);
		
//		findFLHubcode ("KHM_HUB12");
	}
	
	/**
	 * 
	 * @param hubCode
	 * @return
	 */
//	public static String findFLHubcode (String hubCode) {
//		if (isHubCodePresented(hubCode).equalsIgnoreCase("UAE")) {
//			return "UAE";
//		} else if (isHubCodePresented(hubCode).equalsIgnoreCase("NOT_UAE")) {
//			return "NOT_UAE";
//		} else if (isHubCodePresented(hubCode).equalsIgnoreCase("NOT_FL")) {
//			return "NOT_FL";
//		}
//		return hubCode;
//	}
	
	/**
	 * 
	 * @param hCode
	 * @return
	 */
//	public static String isHubCodePresented(String hCode) {
//		List<String> hubCodeList = Arrays.asList("KHM_HUB","HOF_HUB","JAZ_HUB","RYD_HUB","JAZ_HUB",
//				"KHM_HUB","DMM_HUB","TAB_HUB","BHA_HUB","HAIL_HUB","DMM_HUB","KHM_HUB","DMM_HUB",
//				"JBL_HUB","TAB_HUB","RYD_HUB","TAIF","RYD_HUB","YNB_HUB","QAS_HUB","QAS_HUB",
//				"JED_HUB","QAS_HUB","DMM_HUB","BHA_HUB","RYD_HUB","HAIL_HUB","HFR_HUB","JAZ_HUB",
//				"BHA_HUB","TAIF","QAS_HUB","YNB_HUB","MAK_HUB","KHM_HUB","KHM_HUB","KHM_HUB","HOF_HUB",
//				"BHA_HUB","JAZ_HUB","JAZ_HUB","KHM_HUB","QAS_HUB","QAS_HUB","RYD_HUB","JAZ_HUB","DMM_HUB",
//				"JAZ_HUB","JBL_HUB","RYD_HUB","RYD_HUB","DMM_HUB","NAJ_HUB","TAIF","RYD_HUB","HAIL_HUB",
//				"TAB_HUB","KHM_HUB","BHA_HUB","HFR_HUB","HAIL_HUB","JED_HUB","TAB_HUB","TAB_HUB","HOF_HUB",
//				"RYD_HUB","RYD_HUB","MED_HUB","HOF_HUB","RYD_HUB","RYD_HUB","HOF_HUB","RYD_HUB","JED_HUB",
//				"JAZ_HUB","HAIL_HUB","JBL_HUB","MAK_HUB","JBL_HUB","MED_HUB","KHM_HUB","KHM_HUB","RYD_HUB",
//				"DMM_HUB","JED_HUB","TAIF","JED_HUB","MED_HUB","MED_HUB","KHM_HUB","QAS_HUB","MAK_HUB","BHA_HUB",
//				"RYD_HUB","JED_HUB","QAS_HUB","BHA_HUB","KHM_HUB","HOF_HUB","KHM_HUB","JED_HUB","TAIF","RYD_HUB",
//				"NAJ_HUB","KHM_HUB","TAB_HUB","JBL_HUB","QAS_HUB","MED_HUB","JAZ_HUB","HAIL_HUB","HOF_HUB","HFR_HUB",
//				"DMM_HUB","HFR_HUB","JED_HUB","HAIL_HUB","RYD_HUB","JED_HUB","HFR_HUB","BHA_HUB","TAIF","JBL_HUB",
//				"DMM_HUB","KHM_HUB","KHM_HUB","RYD_HUB","RYD_HUB","QAS_HUB","RYD_HUB","RYD_HUB","BHA_HUB","JAZ_HUB",
//				"JBL_HUB","DMM_HUB","QAS_HUB","HAIL_HUB","HOF_HUB","JAZ_HUB","JBL_HUB","KHM_HUB","DMM_HUB","RYD_HUB",
//				"NAJ_HUB","JED_HUB","RYD_HUB","TAB_HUB","TAIF","KHM_HUB","DMM_HUB","KHM_HUB","TAB_HUB","RYD_HUB","RYD_HUB",
//				"JED_HUB","KHM_HUB","HAIL_HUB","TAIF","HOF_HUB","YNB_HUB","DMM_HUB","HOF_HUB","KHM_HUB","KHM_HUB","RYD_HUB",
//				"YNB_HUB","HAIL_HUB","QAS_HUB");
//		
//		if (hubCodeList.stream().anyMatch(e -> e != null && e.equalsIgnoreCase(hCode))) {
//			log.info("FL is matched : " + hCode);
//			return "FL";
//		} else {
//			log.info("FL is not matched : " + hCode);
//			return "NOT_FL";
//		}
//		
////		if ("DUB_Hub".equalsIgnoreCase(hCode)) {
////			return "UAE";
////		} else if (hubCodeList.stream().anyMatch(e -> e != null && e.equalsIgnoreCase(hCode))) { // Hubcode is presented
////			return "NOT_UAE";
////		}
////		return "NOT_FL";
//	}
}