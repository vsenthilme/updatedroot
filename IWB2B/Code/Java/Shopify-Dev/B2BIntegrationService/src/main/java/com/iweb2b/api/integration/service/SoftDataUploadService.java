package com.iweb2b.api.integration.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.iweb2b.api.integration.config.PropertiesConfig;
import com.iweb2b.api.integration.model.consignment.dto.Consignment;
import com.iweb2b.api.integration.model.consignment.dto.ConsignmentResponse;
import com.iweb2b.api.integration.model.consignment.dto.ConsignmentWebhook;
import com.iweb2b.api.integration.model.consignment.dto.Destination_Details;
import com.iweb2b.api.integration.model.consignment.dto.Origin_Details;
import com.iweb2b.api.integration.model.consignment.dto.Pieces_Details;
import com.iweb2b.api.integration.model.consignment.dto.shopify.Order;
import com.iweb2b.api.integration.model.consignment.dto.shopify.ShopifyOrderResponse;
import com.iweb2b.api.integration.model.consignment.entity.ConsignmentEntity;
import com.iweb2b.api.integration.model.consignment.entity.ConsignmentWebhookEntity;
import com.iweb2b.api.integration.model.consignment.entity.DestinationDetailEntity;
import com.iweb2b.api.integration.model.consignment.entity.OriginDetailsEntity;
import com.iweb2b.api.integration.model.consignment.entity.PiecesDetailsEntity;
import com.iweb2b.api.integration.model.tracking.ConsignmentTracking;
import com.iweb2b.api.integration.model.tracking.DestinationDetail;
import com.iweb2b.api.integration.model.tracking.OriginDetail;
import com.iweb2b.api.integration.model.tracking.PiecesDetail;
import com.iweb2b.api.integration.repository.ConsignmentRepository;
import com.iweb2b.api.integration.repository.ConsignmentWebhookRepository;
import com.iweb2b.api.integration.repository.DestinationDetailRepository;
import com.iweb2b.api.integration.repository.OriginDetailRepository;
import com.iweb2b.api.integration.repository.PiecesDetailRepository;
import com.iweb2b.api.integration.util.CommonUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SoftDataUploadService {
	
	@Autowired
	private PropertiesConfig propertiesConfig;

	@Autowired
	private IntegrationService integrationService;
	
	@Autowired
	private ConsignmentRepository consignmentRepository;
	
	@Autowired
	private ConsignmentWebhookRepository consignmentWebhookRepository;

	@Autowired
	private PiecesDetailRepository piecesDetailRepository;

	@Autowired
	private DestinationDetailRepository destinationDetailRepository;

	@Autowired
	private OriginDetailRepository originDetailRepository;
	
	@Autowired
	private ConsignmentTrackingService consignmentTrackingService;

	private long recordId = 1;
	
    /**
     * Returns RestTemplate Object
     *
     * @return
     */
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Object Convertor
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter
                .setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        return restTemplate;
    }
	
	// POST - Consignment
	public ConsignmentResponse createConsignment (Consignment newConsignment, String loginUserId)
			throws IllegalAccessException, InvocationTargetException, Exception {
		try {
			ConsignmentEntity dbConsignmentEntity = new ConsignmentEntity();
			BeanUtils.copyProperties(newConsignment, dbConsignmentEntity, CommonUtils.getNullPropertyNames(newConsignment));
			
			OriginDetailsEntity dbOriginDetailsEntity = new OriginDetailsEntity();
			Origin_Details newOriginDetail = newConsignment.getOrigin_details();
			BeanUtils.copyProperties(newOriginDetail, dbOriginDetailsEntity, CommonUtils.getNullPropertyNames(newOriginDetail));
			dbOriginDetailsEntity.setOriginId(System.currentTimeMillis());
			
			// State validation
			log.info("dbOriginDetailsEntity.getState() : " + dbOriginDetailsEntity.getState());
			if (dbOriginDetailsEntity.getState() == null) {
				dbOriginDetailsEntity.setState("KUWAIT");
			} else if (dbOriginDetailsEntity.getState() != null && dbOriginDetailsEntity.getState().trim().length() == 0) {
				dbOriginDetailsEntity.setState("KUWAIT");
			}
			
			DestinationDetailEntity dbDestinationDetailEntity = new DestinationDetailEntity();
			Destination_Details newDestinationDetail = newConsignment.getDestination_details();
			BeanUtils.copyProperties(newDestinationDetail, dbDestinationDetailEntity, CommonUtils.getNullPropertyNames(newDestinationDetail));
			dbDestinationDetailEntity.setDestinationId(System.currentTimeMillis());
			
			// State validation
			if (dbDestinationDetailEntity.getState() == null) {
				dbDestinationDetailEntity.setState("KSA");
			} else if (dbDestinationDetailEntity.getState() != null && dbDestinationDetailEntity.getState().trim().length() == 0) {
				dbDestinationDetailEntity.setState("KSA");
			}
			
			dbConsignmentEntity.setCreated_at(new Date());
			
			// Call Shipsy API
			ConsignmentResponse response = integrationService.postClientSoftdataUpload (newConsignment);
			log.info("Shipsy Response : " + response);
			
			if (response != null) {
				dbConsignmentEntity.setReference_number(response.getReference_number());
				//hard code for success orders
				dbConsignmentEntity.setBoutiqaatPushStatus("PASS");
				dbConsignmentEntity.setOrderType("1");

				ConsignmentEntity createdConsignmentEntity = consignmentRepository.save(dbConsignmentEntity);
				log.info("createdConsignmentEntity ------->: " + createdConsignmentEntity);
				
				dbOriginDetailsEntity.setConsignmentId(createdConsignmentEntity.getConsignmentId());
				OriginDetailsEntity createdOriginDetailsEntity = originDetailRepository.save(dbOriginDetailsEntity);
				log.info("createdOriginDetailsEntity ------->: " + createdOriginDetailsEntity);
				
				dbDestinationDetailEntity.setConsignmentId(createdConsignmentEntity.getConsignmentId());
				DestinationDetailEntity createdDestinationDetail = destinationDetailRepository.save(dbDestinationDetailEntity);
				log.info("createdDestinationDetail ------->: " + createdDestinationDetail);
				
				List<PiecesDetailsEntity> listPiecesDetailsEntity = new ArrayList<>();
				if (newConsignment.getPieces_detail() != null) {
					newConsignment.getPieces_detail().forEach(piece -> {
						PiecesDetailsEntity dbPiecesDetailsEntity = new PiecesDetailsEntity();
						BeanUtils.copyProperties(piece, dbPiecesDetailsEntity, CommonUtils.getNullPropertyNames(piece));
						if(piecesDetailRepository.findPiecesId() != null) {
							dbPiecesDetailsEntity.setPiecesId(piecesDetailRepository.findPiecesId());
						} else {
							dbPiecesDetailsEntity.setPiecesId(recordId);
						}
						dbPiecesDetailsEntity.setDeclared_value(newConsignment.getDeclared_value());
						dbPiecesDetailsEntity.setConsignmentId(createdConsignmentEntity.getConsignmentId());
						piecesDetailRepository.save(dbPiecesDetailsEntity);
						listPiecesDetailsEntity.add(dbPiecesDetailsEntity);
					});
				}
				log.info("createdListPiecesDetailsEntity ------->: " + listPiecesDetailsEntity);
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	/**
	 * 
	 * @param newConsignment
	 * @param loginUserId
	 * @return
	 */
	public ConsignmentResponse createConsignmentNew (Consignment newConsignment, String loginUserId) {
		try {
			ConsignmentEntity dbConsignmentEntity = new ConsignmentEntity();
			BeanUtils.copyProperties(newConsignment, dbConsignmentEntity, CommonUtils.getNullPropertyNames(newConsignment));

			OriginDetailsEntity dbOriginDetailsEntity = new OriginDetailsEntity();
			Origin_Details newOriginDetail = newConsignment.getOrigin_details();
			BeanUtils.copyProperties(newOriginDetail, dbOriginDetailsEntity, CommonUtils.getNullPropertyNames(newOriginDetail));

			// State validation
			log.info("dbOriginDetailsEntity.getState() : " + dbOriginDetailsEntity.getState());
			if (dbOriginDetailsEntity.getState() == null) {
				dbOriginDetailsEntity.setState("KUWAIT");
			} else if (dbOriginDetailsEntity.getState() != null && dbOriginDetailsEntity.getState().trim().length() == 0) {
				dbOriginDetailsEntity.setState("KUWAIT");
			}

			DestinationDetailEntity dbDestinationDetailEntity = new DestinationDetailEntity();
			Destination_Details newDestinationDetail = newConsignment.getDestination_details();
			BeanUtils.copyProperties(newDestinationDetail, dbDestinationDetailEntity, CommonUtils.getNullPropertyNames(newDestinationDetail));

			// State validation
			if (dbDestinationDetailEntity.getState() == null) {
				dbDestinationDetailEntity.setState("KSA");
			} else if (dbDestinationDetailEntity.getState() != null && dbDestinationDetailEntity.getState().trim().length() == 0) {
				dbDestinationDetailEntity.setState("KSA");
			}

			dbConsignmentEntity.setCreated_at(new Date());

			// Call Shipsy API
			ConsignmentResponse response = integrationService.postClientSoftdataUpload (newConsignment);
			log.info("Shipsy Response : " + response);

			if (response != null) {
				dbConsignmentEntity.setReference_number(response.getReference_number());

				//hard code for success orders
				dbConsignmentEntity.setBoutiqaatPushStatus("PASS");
				dbConsignmentEntity.setOrderType("1");

				Set<PiecesDetailsEntity> listPiecesDetailsEntity = new HashSet<>();

				dbConsignmentEntity.setConsignmentId(System.currentTimeMillis());
				dbOriginDetailsEntity.setConsignmentId(dbConsignmentEntity.getConsignmentId());
				dbDestinationDetailEntity.setConsignmentId(dbConsignmentEntity.getConsignmentId());

				if (newConsignment.getPieces_detail() != null) {
					newConsignment.getPieces_detail().forEach(piece -> {
						PiecesDetailsEntity dbPiecesDetailsEntity = new PiecesDetailsEntity();
						BeanUtils.copyProperties(piece, dbPiecesDetailsEntity, CommonUtils.getNullPropertyNames(piece));
						dbPiecesDetailsEntity.setDeclared_value(newConsignment.getDeclared_value());
						dbPiecesDetailsEntity.setConsignmentId(dbConsignmentEntity.getConsignmentId());
						listPiecesDetailsEntity.add(dbPiecesDetailsEntity);
					});
				}
				dbConsignmentEntity.setOriginDetailsEntity(dbOriginDetailsEntity);
				dbConsignmentEntity.setDestinationDetailEntity(dbDestinationDetailEntity);
				dbConsignmentEntity.setPiecesDetailsEntities(listPiecesDetailsEntity);
				ConsignmentEntity createdConsignmentEntity = consignmentRepository.save(dbConsignmentEntity);
				log.info("Local createdConsignmentEntity ------->: " + createdConsignmentEntity);
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**\
	 * 
	 * @param newConsignment
	 * @param loginUserId
	 * @return
	 */
	// POST - CREATE ORDER LOCALLY
	public ConsignmentEntity createConsignmentInLocalNew (Consignment newConsignment, String loginUserId) {
		try {
			ConsignmentEntity dbConsignmentEntity = new ConsignmentEntity();
			BeanUtils.copyProperties(newConsignment, dbConsignmentEntity, CommonUtils.getNullPropertyNames(newConsignment));
			
			OriginDetailsEntity dbOriginDetailsEntity = new OriginDetailsEntity();
			Origin_Details newOriginDetail = newConsignment.getOrigin_details();
			BeanUtils.copyProperties(newOriginDetail, dbOriginDetailsEntity, CommonUtils.getNullPropertyNames(newOriginDetail));
			
			DestinationDetailEntity dbDestinationDetailEntity = new DestinationDetailEntity();
			Destination_Details newDestinationDetail = newConsignment.getDestination_details();
			BeanUtils.copyProperties(newDestinationDetail, dbDestinationDetailEntity, CommonUtils.getNullPropertyNames(newDestinationDetail));
			
			dbConsignmentEntity.setCreated_at(new Date());
			
			dbConsignmentEntity.setReference_number(newConsignment.getReference_number());

			dbConsignmentEntity.setConsignmentId(System.currentTimeMillis());
			dbOriginDetailsEntity.setConsignmentId(dbConsignmentEntity.getConsignmentId());
			dbDestinationDetailEntity.setConsignmentId(dbConsignmentEntity.getConsignmentId());

			Set<PiecesDetailsEntity> listPiecesDetailsEntity = new HashSet<>();
			if (newConsignment.getPieces_detail() != null) {
				newConsignment.getPieces_detail().forEach(piece -> {
					PiecesDetailsEntity dbPiecesDetailsEntity = new PiecesDetailsEntity();
					BeanUtils.copyProperties(piece, dbPiecesDetailsEntity, CommonUtils.getNullPropertyNames(piece));
					dbPiecesDetailsEntity.setConsignmentId(dbConsignmentEntity.getConsignmentId());
					listPiecesDetailsEntity.add(dbPiecesDetailsEntity);
				});
			}
			dbConsignmentEntity.setOriginDetailsEntity(dbOriginDetailsEntity);
			dbConsignmentEntity.setDestinationDetailEntity(dbDestinationDetailEntity);
			dbConsignmentEntity.setPiecesDetailsEntities(listPiecesDetailsEntity);
			ConsignmentEntity createdConsignmentEntity = consignmentRepository.save(dbConsignmentEntity);
			log.info("createdConsignmentEntity ------->: " + createdConsignmentEntity);
			return createdConsignmentEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 
	 * @param newConsignment
	 * @param loginUserId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	// POST - CREATE ORDER LOCALLY
	public ConsignmentEntity createConsignmentInLocal (Consignment newConsignment, String loginUserId)
			throws IllegalAccessException, InvocationTargetException, Exception {
		try {
			ConsignmentEntity dbConsignmentEntity = new ConsignmentEntity();
			BeanUtils.copyProperties(newConsignment, dbConsignmentEntity, CommonUtils.getNullPropertyNames(newConsignment));
			
			OriginDetailsEntity dbOriginDetailsEntity = new OriginDetailsEntity();
			Origin_Details newOriginDetail = newConsignment.getOrigin_details();
			BeanUtils.copyProperties(newOriginDetail, dbOriginDetailsEntity, CommonUtils.getNullPropertyNames(newOriginDetail));
			if(originDetailRepository.findOriginId() != null) {
				dbOriginDetailsEntity.setOriginId(originDetailRepository.findOriginId());
			} else {
				dbOriginDetailsEntity.setOriginId(recordId);
			}
			
			DestinationDetailEntity dbDestinationDetailEntity = new DestinationDetailEntity();
			Destination_Details newDestinationDetail = newConsignment.getDestination_details();
			BeanUtils.copyProperties(newDestinationDetail, dbDestinationDetailEntity, CommonUtils.getNullPropertyNames(newDestinationDetail));
			if(destinationDetailRepository.findDestinationId() != null) {
				dbDestinationDetailEntity.setDestinationId(destinationDetailRepository.findDestinationId());
			} else {
				dbDestinationDetailEntity.setDestinationId(recordId);
			}
			
			dbConsignmentEntity.setCreated_at(new Date());
			
			dbConsignmentEntity.setReference_number(newConsignment.getReference_number());
			ConsignmentEntity createdConsignmentEntity = consignmentRepository.save(dbConsignmentEntity);
			log.info("createdConsignmentEntity ------->: " + createdConsignmentEntity);
			
			dbOriginDetailsEntity.setConsignmentId(createdConsignmentEntity.getConsignmentId());
			OriginDetailsEntity createdOriginDetailsEntity = originDetailRepository.save(dbOriginDetailsEntity);
			log.info("createdOriginDetailsEntity ------->: " + createdOriginDetailsEntity);
			
			dbDestinationDetailEntity.setConsignmentId(createdConsignmentEntity.getConsignmentId());
			DestinationDetailEntity createdDestinationDetail = destinationDetailRepository.save(dbDestinationDetailEntity);
			log.info("createdDestinationDetail ------->: " + createdDestinationDetail);
			
			List<PiecesDetailsEntity> listPiecesDetailsEntity = new ArrayList<>();
			if (newConsignment.getPieces_detail() != null) {
				newConsignment.getPieces_detail().forEach(piece -> {
					PiecesDetailsEntity dbPiecesDetailsEntity = new PiecesDetailsEntity();
					BeanUtils.copyProperties(piece, dbPiecesDetailsEntity, CommonUtils.getNullPropertyNames(piece));
					if(piecesDetailRepository.findPiecesId() != null) {
						dbPiecesDetailsEntity.setPiecesId(piecesDetailRepository.findPiecesId());
					} else {
						dbPiecesDetailsEntity.setPiecesId(recordId);
					}
					dbPiecesDetailsEntity.setConsignmentId(createdConsignmentEntity.getConsignmentId());
					piecesDetailRepository.save(dbPiecesDetailsEntity);
					listPiecesDetailsEntity.add(dbPiecesDetailsEntity);
				});
			}
			log.info("createdListPiecesDetailsEntity ------->: " + listPiecesDetailsEntity);
			return createdConsignmentEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Consignment> getConsignments () {
		List<ConsignmentEntity> consignmentEntityList = consignmentRepository.findAll();
		List<Consignment> consignmentList = new ArrayList<>();
		if (!consignmentEntityList.isEmpty()) {
			consignmentEntityList.stream().forEach(c -> {
				Consignment consignment = new Consignment();
				BeanUtils.copyProperties(c, consignment, CommonUtils.getNullPropertyNames(c));
				
				OriginDetailsEntity originDetailsEntity = originDetailRepository.findByConsignmentId(c.getConsignmentId());
				if (originDetailsEntity != null) { 
					Origin_Details odetails = new Origin_Details();
					BeanUtils.copyProperties(originDetailsEntity, odetails, CommonUtils.getNullPropertyNames(originDetailsEntity));
					consignment.setOrigin_details(odetails);
				}
				
				DestinationDetailEntity destinationDetailsEntity = destinationDetailRepository.findByConsignmentId(c.getConsignmentId());
				if (destinationDetailsEntity !=  null) {
					Destination_Details ddetails = new Destination_Details();
					BeanUtils.copyProperties(destinationDetailsEntity, ddetails, CommonUtils.getNullPropertyNames(destinationDetailsEntity));
					consignment.setDestination_details(ddetails);
				}
				
				List<PiecesDetailsEntity> piecesDetailsEntityList = piecesDetailRepository.findByConsignmentId(c.getConsignmentId());
				Set<Pieces_Details> piecesDetailsList = new HashSet<>();
				piecesDetailsEntityList.stream().forEach(p -> {
					Pieces_Details pdetails = new Pieces_Details();
					BeanUtils.copyProperties(p, pdetails, CommonUtils.getNullPropertyNames(p));
					piecesDetailsList.add(pdetails);
				});
				consignment.setPieces_detail(piecesDetailsList);
				consignmentList.add(consignment);
			});
			return consignmentList;
		}
		return consignmentList;
	}
	
	/**
	 * 
	 * @param referenceNumber
	 * @return
	 */
	public Consignment getConsignment (String referenceNumber) {
		ConsignmentEntity consignmentEntity = consignmentRepository.findConsigmentUniqueRecord(referenceNumber);
		if (consignmentEntity != null) {
			Consignment consignment = new Consignment();
			BeanUtils.copyProperties(consignmentEntity, consignment, CommonUtils.getNullPropertyNames(consignmentEntity));
			
			OriginDetailsEntity originDetailsEntity = 
					originDetailRepository.findByConsignmentId(consignmentEntity.getConsignmentId());
			if (originDetailsEntity != null) { 
				Origin_Details odetails = new Origin_Details();
				BeanUtils.copyProperties(originDetailsEntity, odetails, CommonUtils.getNullPropertyNames(originDetailsEntity));
				consignment.setOrigin_details(odetails);
			}
			
			DestinationDetailEntity destinationDetailsEntity =
					destinationDetailRepository.findByConsignmentId(consignmentEntity.getConsignmentId());
			if (destinationDetailsEntity !=  null) {
				Destination_Details ddetails = new Destination_Details();
				BeanUtils.copyProperties(destinationDetailsEntity, ddetails, CommonUtils.getNullPropertyNames(destinationDetailsEntity));
				consignment.setDestination_details(ddetails);
			}
			
			List<PiecesDetailsEntity> piecesDetailsEntityList = 
					piecesDetailRepository.findByConsignmentId(consignmentEntity.getConsignmentId());
			Set<Pieces_Details> piecesDetailsList = new HashSet<>();
			piecesDetailsEntityList.stream().forEach(p -> {
				Pieces_Details pdetails = new Pieces_Details();
				BeanUtils.copyProperties(p, pdetails, CommonUtils.getNullPropertyNames(p));
				piecesDetailsList.add(pdetails);
			});
			consignment.setPieces_detail(piecesDetailsList);
			return consignment;
		} else {
			Consignment consignment = getConsignmentFromShipsy (referenceNumber);
			log.info("consignment----------from Shipsy------> : " + consignment);
			return consignment;
		}
	}
	
	/**
	 * 
	 * @param referenceNumber
	 * @return
	 */
	public Consignment getConsignmentFromShipsy (String referenceNumber) {
		ConsignmentTracking consignmentTracking = consignmentTrackingService.getConsignmentTracking(referenceNumber);
		Consignment consignment = new Consignment();
		BeanUtils.copyProperties(consignmentTracking, consignment, CommonUtils.getNullPropertyNames(consignmentTracking));
		if (consignmentTracking.getWeight() != null) {
			double weight = Double.valueOf(consignmentTracking.getWeight()).doubleValue();
			consignment.setWeight(weight);
		}
		
		if (consignmentTracking.getNum_pieces() != null) {
			long num_pieces = Long.valueOf(consignmentTracking.getNum_pieces()).longValue();
			consignment.setNum_pieces(num_pieces);
		}
		
		OriginDetail originDetailsEntity = consignmentTracking.getOrigin_details();
		if (originDetailsEntity != null) { 
			Origin_Details odetails = new Origin_Details();
			BeanUtils.copyProperties(originDetailsEntity, odetails, CommonUtils.getNullPropertyNames(originDetailsEntity));
			consignment.setOrigin_details(odetails);
		}
		
		DestinationDetail destinationDetailsEntity = consignmentTracking.getDestination_details();
		if (destinationDetailsEntity !=  null) {
			Destination_Details ddetails = new Destination_Details();
			BeanUtils.copyProperties(destinationDetailsEntity, ddetails, CommonUtils.getNullPropertyNames(destinationDetailsEntity));
			consignment.setDestination_details(ddetails);
		}
		
		List<PiecesDetail> piecesDetailsEntityList = consignmentTracking.getPieces_detail();
		Set<Pieces_Details> piecesDetailsList = new HashSet<>();
		if (piecesDetailsEntityList != null) {
			piecesDetailsEntityList.stream().forEach(p -> {
				Pieces_Details pdetails = new Pieces_Details();
				BeanUtils.copyProperties(p, pdetails, CommonUtils.getNullPropertyNames(p));
				piecesDetailsList.add(pdetails);
			});
			consignment.setPieces_detail(piecesDetailsList);
		}
		return consignment;
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	public List<ConsignmentWebhook> getConsignmentByType(String type) {
		List<ConsignmentWebhookEntity> consignmentWebhookEntityList = consignmentWebhookRepository.findByType(type);
		if (consignmentWebhookEntityList != null && !consignmentWebhookEntityList.isEmpty()) {
			List<ConsignmentWebhook> consignmentWebhookList = new ArrayList<>();
			consignmentWebhookEntityList.stream().forEach(p -> {
				ConsignmentWebhook consignmentWebhook = new ConsignmentWebhook();
				BeanUtils.copyProperties(p, consignmentWebhook, CommonUtils.getNullPropertyNames(p));
				consignmentWebhookList.add(consignmentWebhook);
			});
			return consignmentWebhookList;
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getConsigmentByQP() {
		List<String> qpConsignments = consignmentRepository.findConsigmentByQP();
		return qpConsignments;
	}
	
	/**
	 * 
	 * @param billCode
	 * @return
	 */
	public String getConsigmentByJntBillCode(String billCode) {
		String reference_number = consignmentRepository.findConsigmentByBillCode(billCode);
		return reference_number;
	}
	
	/**
	 * 
	 * @param referenceNumber
	 * @param billCode
	 * @param jNT_HUBCODE 
	 * @return
	 */
	public ConsignmentEntity updateConsignment (String referenceNumber, String billCode, String jNT_HUBCODE) {
		ConsignmentEntity consignmentEntity = consignmentRepository.findConsigmentUniqueRecord(referenceNumber);
		consignmentEntity.setAwb_3rd_Party(billCode);
		consignmentEntity.setHubCode_3rd_Party(jNT_HUBCODE);
		//hard code for success orders
		consignmentEntity.setJntPushStatus("PASS");
		consignmentEntity.setOrderType("1");

		return consignmentRepository.save(consignmentEntity);
	}
	
	/**
	 * 
	 * @param referenceNumber
	 * @param billCode
	 * @param jNT_HUBCODE
	 * @return
	 */
	public ConsignmentEntity updateQPConsignment (String referenceNumber, String billCode, String HUBCODE) {
		ConsignmentEntity consignmentEntity = consignmentRepository.findConsigmentUniqueRecord(referenceNumber);
		consignmentEntity.setHubCode_3rd_Party(HUBCODE);
		consignmentEntity.setQp_webhook_status("PASS");
		consignmentEntity.setOrderType("1");
		return consignmentRepository.save(consignmentEntity);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String[]> getConsigmentByQP(String hubCode) {
		List<String[]> qpConsignments = consignmentRepository.getByHubcode(hubCode);
		return qpConsignments;
	}
	
	/**
	 * 
	 * @param hubCode
	 * @return
	 */
	public List<String> getOrdersByHubcode(String hubCode) {
		List<String> qpConsignments = consignmentRepository.getOrdersByHubcode(hubCode);
		return qpConsignments;
	}
	
	//-----------------------------------SHOPIFY-----------------------------------------------------------------
	
	/**
	 * 
	 */
	@Scheduled(fixedDelay = 60 * 60 * 1000)
	public void processShopifyOrders() {
		List<Consignment> consignments = prepareShopifyOrders();
		consignments.stream().forEach(o-> {
			String referenceNumber = consignmentRepository.findConsigmentByCustomerReferenceNumberV2(o.getCustomer_reference_number());
			if (referenceNumber == null) {
				// Call Shipsy API
				ConsignmentResponse response = integrationService.postClientSoftdataUpload (o);
				log.info("Shipsy Response : " + response);
				
				o.setReference_number(response.getReference_number());
				ConsignmentEntity createdConsignmentEntity = createConsignmentInLocalNew(o, "From Shopify");
		        log.info("--------created Shipify Consignment in Local DB : " + createdConsignmentEntity);
		        
		        ConsignmentEntity newlyCreatedConsignmentEntity = updateQPConsignment(o.getReference_number(), o.getReference_number(), "SHOPIFY");
				log.info("------newlyCreatedConsignmentEntity--------> : " + newlyCreatedConsignmentEntity);
			}
		});	
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Consignment> prepareShopifyOrders() {
		ShopifyOrderResponse shopifyOrderResponse = listenShopify();
		List<Order> orders = shopifyOrderResponse.getOrders();
		List<Consignment> consignments = new ArrayList<>();
		if (orders != null) {
			for (Order o : orders) {
				Consignment newConsignment = new Consignment();
				newConsignment.setCustomer_code("TEX");
				newConsignment.setCustomer_reference_number(o.getName());
				newConsignment.setCurrency(o.getCurrency());
				newConsignment.setNotes(o.getNote());
				newConsignment.setDeclared_value(Double.valueOf(o.getTotal_price()));
				newConsignment.setWeight(o.getTotal_weight());
				
				// Service type : Premium, Load type: Non-Document.
				newConsignment.setService_type_id("Premium");
				newConsignment.setLoad_type("NON-DOCUMENT");
			
				Origin_Details originDetails = new Origin_Details();
				originDetails.setAddress_line_1(o.getAddress1());
				originDetails.setPhone(o.getPhone());
				originDetails.setCity(o.getCity());
				originDetails.setPincode(o.getZip());
				originDetails.setState(o.getProvince());
				originDetails.setCountry(o.getCountry());
				originDetails.setAddress_line_2(o.getAddress2());
				originDetails.setName(o.getName());
				newConsignment.setOrigin_details(originDetails);
				
				Destination_Details destinationDetails = new Destination_Details();
				destinationDetails.setAddress_line_1(o.getAddress1());
				destinationDetails.setPhone(o.getPhone());
				destinationDetails.setCity(o.getCity());
				destinationDetails.setPincode(o.getZip());
				destinationDetails.setState(o.getProvince());
				destinationDetails.setCountry(o.getCountry());
				destinationDetails.setAddress_line_2(o.getAddress2());
				destinationDetails.setName(o.getName());
				newConsignment.setDestination_details(destinationDetails);
				
				Set<Pieces_Details> piecesDetailsEntityList = new HashSet<>();
				Pieces_Details pdetails = new Pieces_Details();
				pdetails.setDescription("1");
				pdetails.setDeclared_value("0");
				pdetails.setWeight(1D);
				pdetails.setHeight(1L);
				pdetails.setLength(1L);
				pdetails.setWidth(1L);
				piecesDetailsEntityList.add(pdetails);
				newConsignment.setPieces_detail(piecesDetailsEntityList);
				consignments.add(newConsignment);
			}
		}
		log.info("-----consignments------>" + consignments);
		return consignments;
	}
	
	/**
	 * 
	 * @return
	 */
	public ShopifyOrderResponse listenShopify() {
        try {
            String authToken = propertiesConfig.getShopifyAuthAccessToken();
            String webhookUrl = propertiesConfig.getShopifyApiOrdersGetAll();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "B2B_IW_PortalRestTemplate");
            headers.add("X-Shopify-Access-Token", authToken);
            headers.add("Content-Type", "application/json");
            
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(webhookUrl + "?status=any");
            ResponseEntity<ShopifyOrderResponse> result = getRestTemplate().exchange(builder.toUriString(),
                    HttpMethod.GET, entity, ShopifyOrderResponse.class);
            log.info("-----ShopifyOrderResponse----Orderdetails----->" + result.getBody());
            
            return result.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
