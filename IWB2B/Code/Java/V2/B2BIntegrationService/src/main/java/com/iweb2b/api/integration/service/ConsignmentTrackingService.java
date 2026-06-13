package com.iweb2b.api.integration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iweb2b.api.integration.config.PropertiesConfig;
import com.iweb2b.api.integration.model.consignment.dto.CancelResponse;
import com.iweb2b.api.integration.model.consignment.dto.CancelShipmentRequest;
import com.iweb2b.api.integration.model.consignment.dto.CancelShipmentResponse;
import com.iweb2b.api.integration.model.consignment.dto.ShipsyCancelShipmentRequest;
import com.iweb2b.api.integration.model.consignment.entity.ConsignmentEntity;
import com.iweb2b.api.integration.model.consignment.entity.DestinationDetailEntity;
import com.iweb2b.api.integration.model.tracking.ConsignmentTracking;
import com.iweb2b.api.integration.repository.ConsignmentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsignmentTrackingService {
	
	@Autowired
	PropertiesConfig propertiesConfig;
	
	@Autowired
	IntegrationService integrationService;
	
	@Autowired
	ConsignmentRepository consignmentRepository;

	/**
	 * 
	 * @param referrenceNumber
	 * @return
	 */
	public ConsignmentTracking getConsignmentTracking (String referrenceNumber) {
		// https://demodashboardapi.shipsy.in/api/client/integration/consignment/track?reference_number=E1234567
		ConsignmentTracking consignmentTracking = integrationService.getClientConsignmentTracking(referrenceNumber);
//		log.info("consignmentTracking------> : " + consignmentTracking);
		return consignmentTracking;
	}
	
	/**
	 * 
	 * @param wayBillNumber
	 * @return
	 */
	public ConsignmentTracking getConsignmentTrackingByCustomerReferenceNumber (String customerReferenceNumber) {
		// Getting ReferenceNumber from our local DB for the given wayBillNumber
		String referrenceNumber = integrationService.getConsigmentByCustomerReferenceNumber(customerReferenceNumber);
		ConsignmentTracking consignmentTracking = integrationService.getClientConsignmentTracking(referrenceNumber);
		log.info("consignmentTracking------> : " + consignmentTracking);
		DestinationDetailEntity dbDestinationDetailEntity = integrationService.getDestinationDetail(referrenceNumber);
		consignmentTracking.getDestination_details().setAlternate_phone(dbDestinationDetailEntity.getAlternate_phone());
		consignmentTracking.getDestination_details().setDistrict(dbDestinationDetailEntity.getDistrict());
		return consignmentTracking;
	}
	
	/**
	 * 
	 * @param cancelShipmentRequest
	 * @return
	 */
//	public CancelResponse cancelShipment (CancelShipmentRequest cancelShipmentRequest) {
//		List<String[]> consignments =
//				consignmentRepository.findConsigmentByCustomerReferenceNumber (cancelShipmentRequest.getCustomerReferenceNo());
//		log.info("cancelShipment-----> : " + consignments);
//		if (consignments != null && !consignments.isEmpty()) {
//			for (String[] consignmentArr : consignments) {
//				ShipsyCancelShipmentRequest cancelReq = new ShipsyCancelShipmentRequest();
//				cancelReq.setAWBNo(new String[] {consignmentArr[0]});
//				cancelReq.setCustomerCode(consignmentArr[1]);
//				CancelShipmentResponse cancelShipmentResponse = integrationService.cancelShipment(cancelReq);
//				log.info("cancelShipmentResponse------> : " + cancelShipmentResponse);
//
//				if (cancelShipmentResponse.getStatus() != null && cancelShipmentResponse.getStatus().equalsIgnoreCase("OK")) {
//					ConsignmentEntity consignmentEntity = consignmentRepository.findConsigment (consignmentArr[0]);
//					consignmentEntity.setCancelStatus(1L);
//					consignmentRepository.save(consignmentEntity);
//					log.info("cancelShipmentResponse-updated---in---local----> : " + consignmentEntity);
//
//					CancelResponse cancelResponse = new CancelResponse();
//					cancelResponse.setResponseCode("100");
//					cancelResponse.setResponseMessage("Success");;
//					return cancelResponse;
//				}
//			}
//		} else {
//			CancelResponse cancelResponse = new CancelResponse();
//			cancelResponse.setResponseCode("201");
//			cancelResponse.setResponseMessage("CustomerReferenceNo : " + cancelShipmentRequest.getCustomerReferenceNo() + " not found.");
//			return cancelResponse;
//		}
//		return null;
//	}

	//Logic change done by V.Senthil - 22_04_2024 as instructed by Raj Sir based on client request
	/**
	 *
	 * @param cancelShipmentRequest
	 * @return
	 */
	public CancelResponse cancelShipment (CancelShipmentRequest cancelShipmentRequest) {
		List<String[]> consignments =
				consignmentRepository.findConsigmentByCustomerReferenceNumber (cancelShipmentRequest.getCustomerReferenceNo());
		log.info("cancelShipment-----> : " + consignments);

		List<String[]> cancelledConsignments =
				consignmentRepository.findConsigmentByCancelledCustomerReferenceNumber (cancelShipmentRequest.getCustomerReferenceNo());
		log.info("alreadyCancelShipment-----> : " + cancelledConsignments);

		if (consignments != null && !consignments.isEmpty()) {
			for (String[] consignmentArr : consignments) {
				ShipsyCancelShipmentRequest cancelReq = new ShipsyCancelShipmentRequest();
				cancelReq.setAWBNo(new String[] {consignmentArr[0]});
				cancelReq.setCustomerCode(consignmentArr[1]);
				CancelShipmentResponse cancelShipmentResponse = integrationService.cancelShipment(cancelReq);
				log.info("cancelShipmentResponse------> : " + cancelShipmentResponse);

				if (cancelShipmentResponse.getStatus() != null && cancelShipmentResponse.getStatus().equalsIgnoreCase("OK")) {
					ConsignmentEntity consignmentEntity = consignmentRepository.findConsigment (consignmentArr[0]);
					consignmentEntity.setCancelStatus(1L);
					consignmentRepository.save(consignmentEntity);
					log.info("cancelShipmentResponse-updated---in---local----> : " + consignmentEntity);

					CancelResponse cancelResponse = new CancelResponse();
					cancelResponse.setResponseCode("100");
					cancelResponse.setResponseMessage("Success");
					return cancelResponse;
				}
			}
		} else if(cancelledConsignments != null && !cancelledConsignments.isEmpty()){
			CancelResponse cancelResponse = new CancelResponse();
			cancelResponse.setResponseCode("100");
			cancelResponse.setResponseMessage("Success");
			return cancelResponse;
		} else {
			CancelResponse cancelResponse = new CancelResponse();
			cancelResponse.setResponseCode("201");
			cancelResponse.setResponseMessage("CustomerReferenceNo : " + cancelShipmentRequest.getCustomerReferenceNo() + " not found.");
			return cancelResponse;
		}
		return null;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//------------------------------------FLOW=LOG--------------------------------------------------------------------------
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ConsignmentTracking getFlowConsignmentTracking (String referrenceNumber) {
		ConsignmentTracking consignmentTracking = integrationService.getFlowCustomerConsignmentTracking(referrenceNumber);
		log.info("consignmentTracking------> : " + consignmentTracking);
		return consignmentTracking;
	}
}
