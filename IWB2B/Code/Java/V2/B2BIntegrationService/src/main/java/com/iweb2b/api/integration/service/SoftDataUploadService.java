package com.iweb2b.api.integration.service;

import com.iweb2b.api.integration.config.PropertiesConfig;
import com.iweb2b.api.integration.controller.exception.BadRequestException;
import com.iweb2b.api.integration.model.consignment.dto.*;
import com.iweb2b.api.integration.model.consignment.dto.emiratespost.EmiratesPost;
import com.iweb2b.api.integration.model.consignment.dto.emiratespost.EmiratesPostConsignmentResponse;
import com.iweb2b.api.integration.model.consignment.dto.flow.FlowLogConsignmentResponse;
import com.iweb2b.api.integration.model.consignment.entity.*;
import com.iweb2b.api.integration.model.tracking.ConsignmentTracking;
import com.iweb2b.api.integration.model.tracking.DestinationDetail;
import com.iweb2b.api.integration.model.tracking.OriginDetail;
import com.iweb2b.api.integration.model.tracking.PiecesDetail;
import com.iweb2b.api.integration.repository.*;
import com.iweb2b.api.integration.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
	private static final String AJX = "AJX";
	private static final String FLOW_LOG_HUBCODE = "FLOW";
    private static final String INTRANSIT_TO_HUB = "intransit_to_hub";
    private static final List<String> FLO_COUNTRY_CODE_LIST = Arrays.asList("UAE", "KSA", "UKSA");
    private static final String FLOW_LOG_CUST_CODE = "UAE";
    private static final String FLOW_LOG_COUNTRY_CODE = "KSA";
    private static final String FLOW_LOG_COUNTRY_CODE_2 = "UAE";
    private static final String FLOW_LOG_CUST_CODE_TO = "INNERWORKS";
    private static final String EP_COUNTRY_CODE = "Kuwait";
    private static final String EP_CUST_CODE = "2951";
    private static final String EP_HUBCODE = "EMIRATE";
    private static final String PICKUP_AWAITED = "pickup_awaited";
	private static final List<String> FLOW_LOG_CUST_CODE_PROD = Arrays.asList("2565", "2634", "2400", "2591", "2707", "2738", "2755", "2852", "2773", "2620", "2982", "UAE", "KSA", "INNERWORKS", "2951", "3039");
	
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
			
//			if(originDetailRepository.findOriginId() != null) {
//				dbOriginDetailsEntity.setOriginId(originDetailRepository.findOriginId());
//			} else {
//				dbOriginDetailsEntity.setOriginId(recordId);
//			}
			
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
//			if(destinationDetailRepository.findDestinationId() != null) {
//				dbDestinationDetailEntity.setDestinationId(destinationDetailRepository.findDestinationId());
//			} else {
//				dbDestinationDetailEntity.setDestinationId(recordId);
//			}
			
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
     * @param newConsignment
     * @param loginUserId
     * @return
     * @throws Exception
     */
    public ConsignmentResponse createConsignmentNew(Consignment newConsignment, String loginUserId) throws Exception {
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

			//Customer asked for hardcode
			if(newConsignment.getCustomer_code().equalsIgnoreCase("BOQ")) {
				dbConsignmentEntity.setInco_terms("DDP");
				newConsignment.setInco_terms("DDP");
				if(newConsignment.getCustomer_civil_id() != null) {
					dbDestinationDetailEntity.setLatitude(newConsignment.getCustomer_civil_id());
					dbDestinationDetailEntity.setLongitude(newConsignment.getCustomer_civil_id());
					newConsignment.getDestination_details().setLatitude(newConsignment.getCustomer_civil_id());
					newConsignment.getDestination_details().setLongitude(newConsignment.getCustomer_civil_id());
				}
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
						dbPiecesDetailsEntity.setQuantity(String.valueOf(piece.getQuantity()));
						dbPiecesDetailsEntity.setHeight(Double.valueOf(piece.getHeight()));
						dbPiecesDetailsEntity.setConsignmentId(dbConsignmentEntity.getConsignmentId());
						listPiecesDetailsEntity.add(dbPiecesDetailsEntity);
					});
				}
				dbConsignmentEntity.setOriginDetailsEntity(dbOriginDetailsEntity);
				dbConsignmentEntity.setDestinationDetailEntity(dbDestinationDetailEntity);
				dbConsignmentEntity.setPiecesDetailsEntities(listPiecesDetailsEntity);
				ConsignmentEntity createdConsignmentEntity = consignmentRepository.save(dbConsignmentEntity);
				log.info("Local createdConsignmentEntity ------->: " + createdConsignmentEntity);

                /*
                 * Sending the created order to Flow Log only if origin and destination Country UAE
                 */
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //---------------------FLOW-LOGISTICS--------------------------------------------------------------------------------------//
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // pickup_awaited
                // This will pull Order from Shipsy or from Local DB
				boolean isPresent = FLOW_LOG_CUST_CODE_PROD.stream().anyMatch(n -> n.equalsIgnoreCase(createdConsignmentEntity.getCustomer_code()));
                if (createdConsignmentEntity.getCustomer_code() != null && isPresent) {
                    Consignment dbConsignment = getConsignment(createdConsignmentEntity.getReference_number());
                    dbConsignment.setConsignment_type("FORWARD");
                    dbConsignment.setLoad_type("NON-DOCUMENT");
//                    dbConsignment.setService_type_id("DELIVERY");

                    boolean originCtyPass = false;
                    boolean destinationCtyPass = false;
                    if (dbConsignment.getOrigin_details() != null && dbConsignment.getOrigin_details().getCountry() != null) {
                        originCtyPass = FLO_COUNTRY_CODE_LIST.stream().anyMatch(n -> n.equalsIgnoreCase(dbConsignment.getOrigin_details().getCountry()));
                    }
                    if (dbConsignment.getDestination_details() != null && dbConsignment.getDestination_details().getCountry() != null) {
                        destinationCtyPass = FLO_COUNTRY_CODE_LIST.stream().anyMatch(n -> n.equalsIgnoreCase(dbConsignment.getDestination_details().getCountry()));
                    }

                    log.info("originCtyPass, destinationCtyPass : " + originCtyPass + ", " + destinationCtyPass);
                    if (originCtyPass && destinationCtyPass) {
                        ConsignmentEntity consignmentEntity = consignmentRepository.findConsigmentUniqueRecord(createdConsignmentEntity.getReference_number());
                        log.info("---FLOW---consignmentFromLocalDB---order---found---status------>: " + consignmentEntity);
                        FlowLogConsignmentResponse createdResponse = null;

                        log.info("------postFlowLogCreateRequest---------Consignment------------>: " + dbConsignment);
                        if (dbConsignment.getCustomer_code() != null) {
                            dbConsignment.setCustomer_code(FLOW_LOG_CUST_CODE_TO);
                        }
                        if (dbConsignment.getDeclared_value() == null) {
                            dbConsignment.setDeclared_value(0d);
                        }
                        if (dbConsignment.getOrderType() != null && dbConsignment.getOrderType().equalsIgnoreCase("PREPAID")) {
                            dbConsignment.setCod_amount("");
                        }
                        String hubCode = null;
                        if (dbConsignment.getDestination_details().getCountry().equalsIgnoreCase(FLOW_LOG_COUNTRY_CODE) &&
                                dbConsignment.getOrigin_details().getCountry().equalsIgnoreCase(FLOW_LOG_COUNTRY_CODE)) {
                            hubCode = FLOW_LOG_COUNTRY_CODE;
                        }
                        if ((dbConsignment.getDestination_details().getCountry().equalsIgnoreCase(FLOW_LOG_COUNTRY_CODE_2) &&
                                dbConsignment.getOrigin_details().getCountry().equalsIgnoreCase(FLOW_LOG_COUNTRY_CODE_2)) ||
                                (dbConsignment.getDestination_details().getCountry().equalsIgnoreCase(FLOW_LOG_COUNTRY_CODE) &&
                                        dbConsignment.getOrigin_details().getCountry().equalsIgnoreCase(FLOW_LOG_COUNTRY_CODE_2))) {
                            hubCode = FLOW_LOG_COUNTRY_CODE_2;
                        }
                        if (consignmentEntity == null) {
                            createdResponse = integrationService.postCustomerSoftdataUploadForFlowLog(hubCode, dbConsignment);
                            log.info("---FLOW--1---createdResponse-----> : " + createdResponse);
                        } else if (consignmentEntity != null && consignmentEntity.getAwb_3rd_Party() == null) {
                            createdResponse = integrationService.postCustomerSoftdataUploadForFlowLog(hubCode, dbConsignment);
                            log.info("---FLOW--2---createdResponse-----> : " + createdResponse);
                        }

                        if (createdResponse == null) {
                            throw new BadRequestException("consignment already pushed to FlowLogistics :" +
                                                                  createdConsignmentEntity.getReference_number());
                        }
                    }
                }

                /*
                 * Sending the created order to Emirates post only if origin UAE and destination Country Kuwait
                 */
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //--------------------------------------------------Emirate-Post-----------------------------------------------------------//
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                if (createdConsignmentEntity.getCustomer_code() != null &&
                        (createdConsignmentEntity.getCustomer_code().equalsIgnoreCase(EP_CUST_CODE))) {
                    // This will pull Order from Shipsy or from Local DB
                    Consignment dbConsignment = getConsignment(createdConsignmentEntity.getReference_number());

                    boolean originCtyPass = false;
                    boolean destinationCtyPass = false;
                    if (dbConsignment.getOrigin_details() != null && dbConsignment.getOrigin_details().getCountry() != null) {
                        originCtyPass = FLOW_LOG_COUNTRY_CODE_2.equalsIgnoreCase(dbConsignment.getOrigin_details().getCountry());
                    }
                    if (dbConsignment.getDestination_details() != null && dbConsignment.getDestination_details().getCountry() != null) {
                        destinationCtyPass = EP_COUNTRY_CODE.equalsIgnoreCase(dbConsignment.getDestination_details().getCountry());
                    }
                    log.info("Emirates Post originCtyPass, destinationCtyPass : " + originCtyPass + ", " + destinationCtyPass);
                    if (originCtyPass && destinationCtyPass) {
                        ConsignmentEntity consignmentEntity = consignmentRepository.findConsigmentUniqueRecord(createdConsignmentEntity.getReference_number());
                        log.info("---EP---consignmentFromLocalDB---order---found---status------>: " + consignmentEntity);
                        EmiratesPostConsignmentResponse createdResponse = null;
                        log.info("------postEPLogCreateRequest---------Consignment------------>: " + dbConsignment);

                        EmiratesPost emiratesPost = integrationService.mapEmiratesPostCreate(dbConsignment);
                        if (consignmentEntity == null) {
                            createdResponse = integrationService.postEmiratesPostCustomerSoftdataUpload(emiratesPost);
                            log.info("---EP--1---createdResponse-----> : " + createdResponse);
                        } else if (consignmentEntity != null && consignmentEntity.getAwb_3rd_Party() == null) {
                            createdResponse = integrationService.postEmiratesPostCustomerSoftdataUpload(emiratesPost);
                            log.info("---EP--2---createdResponse-----> : " + createdResponse);
                        }

                        if (createdResponse == null) {
                            throw new BadRequestException("consignment already pushed to EmiratesPost :" + createdConsignmentEntity.getReference_number());
                        }

                        if (createdResponse != null) {
                            if (consignmentEntity == null) {
                                try {
                                    Consignment consignment = getConsignmentFromShipsy(createdConsignmentEntity.getReference_number());
                                    consignment.setScanType(PICKUP_AWAITED);
                                    consignment.setAwb_3rd_Party(createdResponse.getAwbNumber());
                                    ConsignmentEntity epResponse =
                                            createConsignmentInLocalNew(consignment, createdResponse.getAwbNumber(), "From Shipsy");
                                    log.info("---EP---created Consignment in  LocalDB : " + epResponse);
                                } catch (Exception e) {
                                    log.info("ERROR: Create Consignment in  LocalDB : " + e.toString());
                                }
                            }
                            log.info("---EP--1-----Track_number--------> : " + createdResponse.getAwbNumber() + " - " + createdResponse.getReferenceNumber());
                            ConsignmentEntity updatedConsignmentEntity =
                                    updateEmiratesPostConsignment(createdConsignmentEntity.getReference_number(),
                                                                                        EP_HUBCODE, createdResponse.getAwbNumber());
                            log.info("---EP---updatedConsignmentEntity--------> : " + updatedConsignmentEntity);
                        }
                    }

                }
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
	public ConsignmentResponse createConsignmentNonBOQ(Consignment newConsignment, String loginUserId) {
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
			
			ConsignmentResponse response = null;
			
			// Changes for AJEX
			if (newConsignment.getCustomer_code().equalsIgnoreCase(AJX)) {
				ConsignmentWebhookEntity consignmentWebhookEntity = consignmentWebhookRepository.findOrdersByType(newConsignment.getCustomer_reference_number());
				log.info("--------CancelStatus------->: " + consignmentWebhookEntity );
				if (consignmentWebhookEntity != null && consignmentWebhookEntity.getType().equalsIgnoreCase("cancelled")) {
					log.info("--------CancelStatus---consignmentWebhookEntity.getType()---->: " + consignmentWebhookEntity.getType() );
					
					// Call Shipsy API to create an order
					response = integrationService.postClientSoftdataUpload (newConsignment);
					log.info("Shipsy Response-----1-----> : " + response);
				} else {
					String referenceNumber = consignmentRepository.findConsigmentByCustomerReferenceNumberV3 (newConsignment.getCustomer_reference_number());
					log.info("-------@-----> : " + referenceNumber);
					if (referenceNumber == null) {
						// Call Shipsy API to create an order
						response = integrationService.postClientSoftdataUpload (newConsignment);
						log.info("Shipsy Response-----11-----> : " + response);
//						return response;
					} else {
						response = new ConsignmentResponse();
						response.setReference_number(referenceNumber);	
						response.setCustomer_reference_number(newConsignment.getCustomer_reference_number());						
						response.setSuccess(true);						
						return response;
					}
				}
			} else {
				// Call Shipsy API to create an order
				response = integrationService.postClientSoftdataUpload (newConsignment);
				log.info("Shipsy Response-----3-----> : " + response);
			}

			log.info("Shipsy Response-----5-----> : " + response);
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

    /**
     * \
	 * @param newConsignment
	 * @param loginUserId
	 * @return
	 */
	// POST - CREATE ORDER LOCALLY
	public ConsignmentEntity createConsignmentInLocalNew (Consignment newConsignment, String shopiniTrackNumber, String loginUserId) {
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

			if (shopiniTrackNumber != null) {
				dbConsignmentEntity.setAwb_3rd_Party(shopiniTrackNumber);
			}

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
	 */
	public ConsignmentEntity updateConsignmentInLocal (Consignment newConsignment, String loginUserId) {
		try {
			ConsignmentEntity dbConsignmentEntity = consignmentRepository.findConsigment(newConsignment.getReference_number());
			log.info("---------dbConsignmentEntity-----found----for----update-------> : " + dbConsignmentEntity);

			BeanUtils.copyProperties(newConsignment, dbConsignmentEntity, CommonUtils.getNullPropertyNames(newConsignment));
			OriginDetailsEntity dbOriginDetailsEntity = originDetailRepository.findByConsignmentId(dbConsignmentEntity.getConsignmentId());

			Origin_Details newOriginDetail = newConsignment.getOrigin_details();
			BeanUtils.copyProperties(newOriginDetail, dbOriginDetailsEntity, CommonUtils.getNullPropertyNames(newOriginDetail));

			DestinationDetailEntity dbDestinationDetailEntity = destinationDetailRepository.findByConsignmentId(dbConsignmentEntity.getConsignmentId());
			Destination_Details newDestinationDetail = newConsignment.getDestination_details();
			BeanUtils.copyProperties(newDestinationDetail, dbDestinationDetailEntity, CommonUtils.getNullPropertyNames(newDestinationDetail));

			Set<PiecesDetailsEntity> listPiecesDetailsEntity = new HashSet<>();
			if (newConsignment.getPieces_detail() != null) {
				newConsignment.getPieces_detail().forEach(piece -> {
					dbConsignmentEntity.getPiecesDetailsEntities().forEach( p -> {
						PiecesDetailsEntity dbPiecesDetailsEntity = p;
						BeanUtils.copyProperties(piece, dbPiecesDetailsEntity, CommonUtils.getNullPropertyNames(piece));
						listPiecesDetailsEntity.add(dbPiecesDetailsEntity);
					});
				});
			}
			dbConsignmentEntity.setOriginDetailsEntity(dbOriginDetailsEntity);
			dbConsignmentEntity.setDestinationDetailEntity(dbDestinationDetailEntity);
			dbConsignmentEntity.setPiecesDetailsEntities(listPiecesDetailsEntity);
			ConsignmentEntity createdConsignmentEntity = consignmentRepository.save(dbConsignmentEntity);
			log.info("updated ConsignmentEntity ------->: " + createdConsignmentEntity);
			return createdConsignmentEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

    /**
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
     * @param HUBCODE
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
	 * @param referenceNumber
	 * @param billCode
	 * @param HUBCODE
	 * @return
	 */
	public ConsignmentEntity updateShopiniConsignment (String referenceNumber, String billCode, String HUBCODE, String shopiniTrackNumber) {
		ConsignmentEntity consignmentEntity = consignmentRepository.findConsigmentUniqueRecord(referenceNumber);
		consignmentEntity.setHubCode_3rd_Party(HUBCODE);
		consignmentEntity.setShopini_webhook_status("PASS");
		consignmentEntity.setOrderType("1");
		consignmentEntity.setAwb_3rd_Party(shopiniTrackNumber);
		return consignmentRepository.save(consignmentEntity);
	}
	
	/**
     * @param referenceNumber
     * @param billCode
     * @param HUBCODE
     * @param shopiniTrackNumber
     * @return
     */
    public ConsignmentEntity updateFlowlogConsignment(String referenceNumber, String billCode, String HUBCODE, String shopiniTrackNumber) {
        ConsignmentEntity consignmentEntity = consignmentRepository.findConsigmentUniqueRecord(referenceNumber);
        consignmentEntity.setHubCode_3rd_Party(HUBCODE);
        consignmentEntity.setFlowLog_webhook_status("PASS");
        consignmentEntity.setOrderType("1");
        consignmentEntity.setAwb_3rd_Party(shopiniTrackNumber);
        return consignmentRepository.save(consignmentEntity);
    }

    /**
	 * @param referenceNumber
	 * @param HUBCODE
	 * @return
	 */
	public ConsignmentEntity cancelConsignment (String referenceNumber, String HUBCODE) {
		ConsignmentEntity consignmentEntity = consignmentRepository.findConsigmentUniqueRecord(referenceNumber);
		consignmentEntity.setHubCode_3rd_Party(HUBCODE);
		consignmentEntity.setCancelStatus(1L);
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
	public List<String[]> getConsigmentByShopini(String hubCode) {
		List<String[]> qpConsignments = consignmentRepository.createOrdersInShopiniWebhook(hubCode);
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

	/**
	 *
	 * @param hubCode
	 * @return
	 */
	public List<String> getShopiniOrdersByHubcode(String hubCode) {
		List<String> qpConsignments = consignmentRepository.getShopiniOrdersByHubcode(hubCode);
		return qpConsignments;
	}

//======================================Emirates Post==========================================================================
    public ConsignmentEntity updateEmiratesPostConsignment(String referenceNumber, String HUBCODE, String emiratesAWBTrackNumber) {
        ConsignmentEntity consignmentEntity = consignmentRepository.findConsigmentUniqueRecord(referenceNumber);
        consignmentEntity.setHubCode_3rd_Party(HUBCODE);
        consignmentEntity.setEmiratesPostWebhookStatus("PASS");
        consignmentEntity.setOrderType("1");
        consignmentEntity.setAwb_3rd_Party(emiratesAWBTrackNumber);
        return consignmentRepository.save(consignmentEntity);
    }

    /**
     *
     * @param referenceNumber
     * @param HUBCODE
     * @return
     */
    public List<ConsignmentWebhookEntity> updateEmiratesPostConsignmentWebhook(String referenceNumber, String HUBCODE) {
        List<ConsignmentWebhookEntity> consignmentWebhookEntityList = consignmentRepository.getEPOrdersByHubcode(referenceNumber);
        if(consignmentWebhookEntityList != null && !consignmentWebhookEntityList.isEmpty()) {
            consignmentWebhookEntityList.stream().forEach(consignmentWebhookEntity -> {
                consignmentWebhookEntity.setHub_code(HUBCODE);
                consignmentWebhookRepository.save(consignmentWebhookEntity);
            });
        }
        return consignmentWebhookEntityList;
    }
}