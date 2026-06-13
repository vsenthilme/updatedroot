package com.iweb2b.api.integration.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.iweb2b.api.integration.model.consignment.dto.shopini.CancelRequest;
import com.iweb2b.api.integration.model.consignment.dto.shopini.CreateShipment;
import com.iweb2b.api.integration.model.consignment.dto.shopini.ParcelBox;
import com.iweb2b.api.integration.model.consignment.dto.shopini.ParcelItems;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.iweb2b.api.integration.model.consignment.dto.jnt.JNTOrderCreateRequest;
import com.iweb2b.api.integration.model.consignment.dto.jnt.JNTPrintLabelBzContent;

public class JsonConvertor {
	
	/**
	 * 
	 * @param jntOrderCreateRequest
	 * @return
	 * @throws Exception
	 */
	public static String toJsonString(JNTOrderCreateRequest jntOrderCreateRequest) throws Exception {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);
			String jsonInString = mapper.writeValueAsString(jntOrderCreateRequest);
			System.out.println(jsonInString);
			return jsonInString;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 
	 * @param jntPrintLabelBzContent
	 * @return
	 * @throws Exception
	 */
	public static String toJsonString(JNTPrintLabelBzContent jntPrintLabelBzContent) throws Exception {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);
			String jsonInString = mapper.writeValueAsString(jntPrintLabelBzContent);
			System.out.println(jsonInString);
			return jsonInString;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		//By default all fields without explicit view definition are included, disable this
		mapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);
		 
		//For testing
		User user = new User ();
		user.setName("mkyong");
		user.setAge(33);
		List<String> msg = new ArrayList<>();
		msg.add("hello jackson 1");
		msg.add("hello jackson 2");
		msg.add("hello jackson 3");
		user.setMessages(msg);
		
		try {
			//display name only
			String jsonInString = mapper.writeValueAsString(user);
			System.out.println(jsonInString);
			
//			//display namd ana age
//			jsonInString = mapper.writeValueAsString(user);
//			System.out.println(jsonInString);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param createShipment
	 * @return
	 * @throws Exception
	 */
	public static String toJsonString3(ParcelItems parcelItems) throws Exception {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);
			String jsonInString = mapper.writeValueAsString(parcelItems);
			System.out.println(jsonInString);
			return jsonInString;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static String toJsonString4(ParcelBox parcel_box) throws Exception {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);
			String jsonInString = mapper.writeValueAsString(parcel_box);
			System.out.println(jsonInString);
			return jsonInString;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static String toJsonString2(CreateShipment createShipment) throws Exception {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);
			String jsonInString = mapper.writeValueAsString(createShipment);
			System.out.println(jsonInString);
			return jsonInString;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 *
	 * @param cancelRequest
	 * @return
	 * @throws Exception
	 */
	public static String toJsonString_CancelRequest(CancelRequest cancelRequest) throws Exception {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);
			String jsonInString = mapper.writeValueAsString(cancelRequest);
			System.out.println(jsonInString);
			return jsonInString;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}