package com.tekclover.wms.core.controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.tekclover.wms.core.model.auth.AuthToken;
import com.tekclover.wms.core.model.threepl.*;
import com.tekclover.wms.core.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tekclover.wms.core.exception.BadRequestException;
import com.tekclover.wms.core.exception.CustomErrorResponse;
import com.tekclover.wms.core.model.idmaster.AddRoleAccess;
import com.tekclover.wms.core.model.idmaster.BarcodeSubTypeId;
import com.tekclover.wms.core.model.idmaster.BarcodeTypeId;
import com.tekclover.wms.core.model.idmaster.*;
import com.tekclover.wms.core.model.idmaster.CompanyId;
import com.tekclover.wms.core.model.idmaster.Country;
import com.tekclover.wms.core.model.idmaster.Currency;
import com.tekclover.wms.core.model.idmaster.FloorId;
import com.tekclover.wms.core.model.idmaster.HhtUser;
import com.tekclover.wms.core.model.idmaster.ItemGroupId;
import com.tekclover.wms.core.model.idmaster.ItemTypeId;
import com.tekclover.wms.core.model.idmaster.LevelId;
import com.tekclover.wms.core.model.idmaster.MenuId;
import com.tekclover.wms.core.model.idmaster.PlantId;
import com.tekclover.wms.core.model.idmaster.ProcessSequenceId;
import com.tekclover.wms.core.model.idmaster.RoleAccess;
import com.tekclover.wms.core.model.idmaster.RowId;
import com.tekclover.wms.core.model.idmaster.State;
import com.tekclover.wms.core.model.idmaster.StatusId;
import com.tekclover.wms.core.model.idmaster.StorageBinTypeId;
import com.tekclover.wms.core.model.idmaster.StorageClassId;
import com.tekclover.wms.core.model.idmaster.StorageSectionId;
import com.tekclover.wms.core.model.idmaster.StrategyId;
import com.tekclover.wms.core.model.idmaster.StorageTypeId;
import com.tekclover.wms.core.model.idmaster.SubItemGroupId;
import com.tekclover.wms.core.model.idmaster.UomId;
import com.tekclover.wms.core.model.idmaster.UserTypeId;
import com.tekclover.wms.core.model.idmaster.VariantId;
import com.tekclover.wms.core.model.idmaster.Vertical;
import com.tekclover.wms.core.model.idmaster.WarehouseId;
import com.tekclover.wms.core.model.idmaster.WarehouseTypeId;
import com.tekclover.wms.core.model.user.AddUserManagement;
import com.tekclover.wms.core.model.user.UpdateUserManagement;
import com.tekclover.wms.core.model.user.UserManagement;
import com.tekclover.wms.core.service.IDMasterService;
import com.tekclover.wms.core.service.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/wms-idmaster-service")
@Api(tags = {"IDMaster Service"}, value = "IDMaster Service Operations") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "User",description = "Operations related to IDMaster Service")})
public class IDMasterServiceController {

	@Autowired
	IDMasterService idmasterService;

	@Autowired
	RegisterService registerService;

	@Autowired
	FileStorageService fileStorageService;

	@Autowired
	DocStorageService docStorageService;

	@Autowired
	AuthTokenService authTokenService;

	private String getAuthToken() {
		AuthToken authTokenForIDMasterService = authTokenService.getIDMasterServiceAuthToken();
		return authTokenForIDMasterService.getAccess_token();
	}

	/* --------------------------------LOGIN-------------------------------------------------------------------------------------*/

	@ApiOperation(response = Optional.class, value = "Login User") // label for swagger
	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> loginUser(@RequestParam String name, @RequestParam String password, @RequestParam(required = false) String version) {
		try {
			UserManagement loggedUser = idmasterService.validateUserID(name, password, getAuthToken(), version);
			log.info("LoginUser::: " + loggedUser);
			log.info("version::: " + version);
			return new ResponseEntity<>(loggedUser, HttpStatus.OK);
		} catch (BadRequestException e) {
			log.error("Invalid user");
			String str = "Either UserId is invalid or Password does not match.";
			CustomErrorResponse error = new CustomErrorResponse();
			error.setTimestamp(LocalDateTime.now());
			error.setError(str);
			error.setStatus(HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(response = UserManagement.class, value = "Get all UserManagement details") // label for swagger
	@GetMapping("/usermanagement")
	public ResponseEntity<?> getAllUserManagements() {
		UserManagement[] userManagementList = idmasterService.getUserManagements(getAuthToken());
		return new ResponseEntity<>(userManagementList, HttpStatus.OK);
	}

	@ApiOperation(response = UserManagement.class, value = "Get a UserManagement") // label for swagger
	@GetMapping("/usermanagement/{userId}")
	public ResponseEntity<?> getUserManagement(@PathVariable String userId,@RequestParam String companyCode,@RequestParam String plantId,
											   @RequestParam String languageId,@RequestParam Long userRoleId,@RequestParam String warehouseId) {
		UserManagement dbUserManagement = idmasterService.getUserManagement(userId,companyCode,plantId,languageId,userRoleId,warehouseId, getAuthToken());
		log.info("UserManagement : " + dbUserManagement);
		return new ResponseEntity<>(dbUserManagement, HttpStatus.OK);
	}

	@ApiOperation(response = UserManagement.class, value = "Create UserManagement") // label for swagger
	@PostMapping("/usermanagement")
	public ResponseEntity<?> postUserManagement(@Valid @RequestBody AddUserManagement newUserManagement,
												@RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		UserManagement createdUserManagement = idmasterService.createUserManagement(newUserManagement, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdUserManagement, HttpStatus.OK);
	}

	@ApiOperation(response = UserManagement.class, value = "Update UserManagement") // label for swagger
	@RequestMapping(value = "/usermanagement/{userId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchUserManagement(@PathVariable String userId, @RequestParam String warehouseId,@RequestParam String companyCode,@RequestParam String plantId,@RequestParam String languageId,@RequestParam Long userRoleId,
												 @RequestParam String loginUserID, @Valid @RequestBody UpdateUserManagement updateUserManagement)
			throws IllegalAccessException, InvocationTargetException {
		UserManagement updatedUserManagement =
				idmasterService.updateUserManagement(userId,warehouseId,loginUserID,companyCode,plantId,languageId,userRoleId,updateUserManagement, getAuthToken());
		return new ResponseEntity<>(updatedUserManagement, HttpStatus.OK);
	}

	@ApiOperation(response = UserManagement.class, value = "Delete UserManagement") // label for swagger
	@DeleteMapping("/usermanagement/{userId}")
	public ResponseEntity<?> deleteUserManagement(@PathVariable String userId, @RequestParam String warehouseId,@RequestParam String companyCode,@RequestParam String plantId,@RequestParam String languageId,@RequestParam Long userRoleId,
												  @RequestParam String loginUserID) {
		idmasterService.deleteUserManagement(userId,warehouseId,companyCode,languageId,plantId,userRoleId, loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//FIND
	@ApiOperation(response = UserManagement[].class, value = "Find UserManagement")//label for swagger
	@PostMapping("/usermanagement/findUserManagement")
	public UserManagement[] findUserManagement(@RequestBody FindUserManagement findUserManagement)throws Exception{
		return idmasterService.findUserManagement(findUserManagement,getAuthToken());
	}


	/* --------------------------------City-------------------------------------------------------------------------*/

	// GET ALL
	@ApiOperation(response = Optional.class, value = "Get All Cities") // label for swagger
	@RequestMapping(value = "/city", method = RequestMethod.GET)
	public ResponseEntity<?> getCities() {
		City[] city = idmasterService.getCities(getAuthToken());
		log.info("getCities::: " + city);
		return new ResponseEntity<>(city, HttpStatus.OK);
	}

	// GET
	@ApiOperation(response = Optional.class, value = "Get City") // label for swagger
	@RequestMapping(value = "/city/{cityId}", method = RequestMethod.GET)
	public ResponseEntity<?> getCity(@PathVariable String cityId,@RequestParam String stateId,@RequestParam String countryId,
									 @RequestParam String languageId) {
		City city = idmasterService.getCity(cityId,stateId,countryId,languageId,getAuthToken());
		log.info("getCity::: " + city);
		return new ResponseEntity<>(city, HttpStatus.OK);
	}

	// CREATE
	@ApiOperation(response = Optional.class, value = "Create new city") // label for swagger
	@RequestMapping(value = "/city", method = RequestMethod.POST)
	public ResponseEntity<?> createCity(@RequestBody AddCity newCity,@RequestParam String loginUserID) {
		City city = idmasterService.addCity(newCity,loginUserID,getAuthToken());
		log.info("createCity::: " + city);
		return new ResponseEntity<>(city, HttpStatus.OK);
	}

	// UPDATE
	@ApiOperation(response = Optional.class, value = "Update City") // label for swagger
	@RequestMapping(value = "/city/{cityId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateCity(@PathVariable String cityId,@RequestParam String stateId,@RequestParam String countryId,
										@RequestParam String languageId,@RequestParam String loginUserID,
										@RequestBody UpdateCity updatedCity) {
		City city = idmasterService.updateCity(cityId,stateId,countryId,languageId,loginUserID,updatedCity,getAuthToken());
		log.info("updateCity::: " + city);
		return new ResponseEntity<>(city, HttpStatus.OK);
	}

	// DELETE
	@ApiOperation(response = Optional.class, value = "Delete City") // label for swagger
	@RequestMapping(value = "/city/{cityId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCity(@PathVariable String cityId,@RequestParam String stateId,@RequestParam String countryId,
										@RequestParam String languageId) {
		boolean isCityDeleted = idmasterService.deleteCity(cityId,stateId,countryId,languageId,getAuthToken());
		log.info("isCityDeleted::: " + isCityDeleted);
		return new ResponseEntity<>(isCityDeleted, HttpStatus.OK);
	}

	//FIND
	@ApiOperation(response = Optional[].class, value = "Find City")//label for swagger
	@PostMapping("/city/findcity")
	public City[] findCity(@RequestBody FindCity findCity)throws Exception{
		return idmasterService.findCity(findCity,getAuthToken());
	}

	/* --------------------------------Country-------------------------------------------------------------------------*/

	@ApiOperation(response = Optional.class, value = "Get All Country") // label for swagger
	@RequestMapping(value = "/country", method = RequestMethod.GET)
	public ResponseEntity<?> getCountries() {
		Country[] country = idmasterService.getCountries(getAuthToken());
		log.info("getCities::: " + country);
		return new ResponseEntity<>(country, HttpStatus.OK);
	}

	@ApiOperation(response = Optional.class, value = "Get Country") // label for swagger
	@RequestMapping(value = "/country/{countryId}", method = RequestMethod.GET)
	public ResponseEntity<?> getACountry(@PathVariable String countryId,@RequestParam String languageId) {
		Country country = idmasterService.getCountry(countryId,languageId,getAuthToken());
		log.info("getCountry::: " + country);
		return new ResponseEntity<>(country, HttpStatus.OK);
	}

	@ApiOperation(response = Optional.class, value = "Create new Country") // label for swagger
	@RequestMapping(value = "/country", method = RequestMethod.POST)
	public ResponseEntity<?> createCountry(@RequestBody AddCountry newCountry,@RequestParam String loginUserID) {
		Country country = idmasterService.addCountry(newCountry,loginUserID,getAuthToken());
		log.info("createCountry::: " + country);
		return new ResponseEntity<>(country, HttpStatus.OK);
	}

	@ApiOperation(response = Optional.class, value = "Update Country") // label for swagger
	@RequestMapping(value = "/country/{countryId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateCountry( @PathVariable String countryId,@RequestParam String languageId,
											@RequestParam String loginUserID,@RequestBody UpdateCountry updatedCountry) {
		Country country = idmasterService.updateCountry(countryId,languageId,loginUserID,updatedCountry,getAuthToken());
		log.info("updateCountry::: " + country);
		return new ResponseEntity<>(country, HttpStatus.OK);
	}

	@ApiOperation(response = Optional.class, value = "Delete Country") // label for swagger
	@RequestMapping(value = "/country/{countryId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteACountry(@PathVariable String countryId,@RequestParam String languageId) {
		boolean isCountryDeleted = idmasterService.deleteCountry(countryId,languageId,getAuthToken());
		log.info("isCountryDeleted::: " + isCountryDeleted);
		return new ResponseEntity<>(isCountryDeleted, HttpStatus.OK);
	}

	//FIND
	@ApiOperation(response = Optional[].class, value = "Find Country")//label for swagger
	@PostMapping("/country/findcountry")
	public Country[] findCountries(@RequestBody FindCountry findCountry)throws Exception{
		return idmasterService.findCountry(findCountry,getAuthToken());
	}

	/* --------------------------------State-------------------------------------------------------------------------*/

	@ApiOperation(response = Optional.class, value = "Get All States") // label for swagger
	@RequestMapping(value = "/state", method = RequestMethod.GET)
	public ResponseEntity<?> getStates() {
		State[] state = idmasterService.getStates(getAuthToken());
		log.info("getStates::: " + state);
		return new ResponseEntity<>(state, HttpStatus.OK);
	}

	@ApiOperation(response = Optional.class, value = "Get State") // label for swagger
	@RequestMapping(value = "/state/{stateId}", method = RequestMethod.GET)
	public ResponseEntity<?> getState(@PathVariable String stateId,@RequestParam String countryId,@RequestParam String languageId) {
		State state = idmasterService.getState(stateId,countryId,languageId,getAuthToken());
		log.info("getState::: " + state);
		return new ResponseEntity<>(state, HttpStatus.OK);
	}

	@ApiOperation(response = Optional.class, value = "Create new State") // label for swagger
	@RequestMapping(value = "/state", method = RequestMethod.POST)
	public ResponseEntity<?> createState(@RequestBody AddState newState,@RequestParam String loginUserID) {
		State state = idmasterService.addState(newState,loginUserID,getAuthToken());
		log.info("createState::: " + state);
		return new ResponseEntity<>(state, HttpStatus.OK);
	}

	@ApiOperation(response = Optional.class, value = "Update State") // label for swagger
	@RequestMapping(value = "/state/{stateId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateState(@PathVariable String stateId,@RequestParam String countryId,@RequestParam String languageId,
										 @RequestParam String loginUserID,@RequestBody UpdateState updatedState) {
		State state = idmasterService.updateState(stateId,countryId,languageId,loginUserID,updatedState, getAuthToken());
		log.info("updateState::: " + state);
		return new ResponseEntity<>(state, HttpStatus.OK);
	}

	@ApiOperation(response = Optional.class, value = "Delete State") // label for swagger
	@RequestMapping(value = "/state/{stateId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteState(@PathVariable String stateId,@RequestParam String countryId,
										 @RequestParam String languageId) {
		boolean isStateDeleted = idmasterService.deleteState(stateId,countryId,languageId,getAuthToken());
		log.info("isStateDeleted::: " + isStateDeleted);
		return new ResponseEntity<>(isStateDeleted, HttpStatus.OK);
	}

	//FIND
	@ApiOperation(response = Optional[].class, value = "Find State")//label for swagger
	@PostMapping("/state/findState")
	public State[] findState(@RequestBody FindState findState)throws Exception{
		return idmasterService.findState(findState,getAuthToken());
	}

	/* --------------------------------Currency-------------------------------------------------------------------------*/

	@ApiOperation(response = Optional.class, value = "Get All Currency") // label for swagger
	@RequestMapping(value = "/currency", method = RequestMethod.GET)
	public ResponseEntity<?> getCurrencies() {
		Currency[] currency = idmasterService.getCurrencies(getAuthToken());
		log.info("getCities::: " + currency);
		return new ResponseEntity<>(currency, HttpStatus.OK);
	}

	@ApiOperation(response = Optional.class, value = "Get Currency") // label for swagger
	@RequestMapping(value = "/currency/{currencyId}", method = RequestMethod.GET)
	public ResponseEntity<?> getCurrency(@PathVariable Long currencyId,@RequestParam String languageId) {
		Currency currency = idmasterService.getCurrency(currencyId,languageId,getAuthToken());
		log.info("getCurrency::: " + currency);
		return new ResponseEntity<>(currency, HttpStatus.OK);
	}

	@ApiOperation(response = Optional.class, value = "Create new Currency") // label for swagger
	@RequestMapping(value = "/currency", method = RequestMethod.POST)
	public ResponseEntity<?> createCurrency(@RequestBody AddCurrency newCurrency,@RequestParam String loginUserID) {
		Currency currency = idmasterService.addCurrency(newCurrency,loginUserID,getAuthToken());
		log.info("createCurrency::: " + currency);
		return new ResponseEntity<>(currency, HttpStatus.OK);
	}

	@ApiOperation(response = Optional.class, value = "Update Currency") // label for swagger
	@RequestMapping(value = "/currency/{currencyId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateCurrency(@PathVariable Long currencyId,@RequestParam String languageId,
											@RequestParam String loginUserID, @RequestBody UpdateCurrency updatedCurrency) {
		Currency currency = idmasterService.updateCurrency(currencyId,languageId,loginUserID,updatedCurrency, getAuthToken());
		log.info("updateCurrency::: " + currency);
		return new ResponseEntity<>(currency, HttpStatus.OK);
	}

	@ApiOperation(response = Optional.class, value = "Delete Currency") // label for swagger
	@RequestMapping(value = "/currency/{currencyId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCurrency(@PathVariable Long currencyId,@RequestParam String languageId) {
		boolean isCurrencyDeleted = idmasterService.deleteCurrency(currencyId,languageId,getAuthToken());
		log.info("isCurrencyDeleted::: " + isCurrencyDeleted);
		return new ResponseEntity<>(isCurrencyDeleted, HttpStatus.OK);
	}

	//FIND
	@ApiOperation(response = Currency[].class, value = "Find Currency")//label for swagger
	@PostMapping("/currency/findCurrency")
	public Currency[] findCurrency(@RequestBody FindCurrency findCurrency)throws Exception{
		return idmasterService.findCurrency(findCurrency,getAuthToken());
	}

	/* --------------------------------Vertical-------------------------------------------------------------------------*/

	@ApiOperation(response = Vertical.class, value = "Get All Verticals") // label for swagger
	@RequestMapping(value = "/vertical", method = RequestMethod.GET)
	public ResponseEntity<?> getVeticals() {
		Vertical[] veticals = idmasterService.getVerticals(getAuthToken());
		log.info("getVeticals::: " + veticals);
		return new ResponseEntity<>(veticals, HttpStatus.OK);
	}

	@ApiOperation(response = Vertical.class, value = "Get Vertical") // label for swagger
	@RequestMapping(value = "/vetical/{verticalId}", method = RequestMethod.GET)
	public ResponseEntity<?> getVetical(@PathVariable String verticalId,@RequestParam String languageId) {
		Vertical vertical = idmasterService.getVertical(verticalId,languageId,getAuthToken());
		log.info("getVetical::: " + vertical);
		return new ResponseEntity<>(vertical, HttpStatus.OK);
	}

	@ApiOperation(response = Vertical.class, value = "Create  Vertical") // label for swagger
	@RequestMapping(value = "/vertical", method = RequestMethod.POST)
	public ResponseEntity<?> createVertical(@Valid @RequestBody AddVertical newVertical,@RequestParam String loginUserID) {
		Vertical vertical = idmasterService.addVertical(newVertical,loginUserID, getAuthToken());
		log.info("createVertical::: " + vertical);
		return new ResponseEntity<>(vertical, HttpStatus.OK);
	}

	@ApiOperation(response = Vertical.class, value = "Update Vertical") // label for swagger
	@RequestMapping(value = "/vertical/{verticalId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateVertical(@PathVariable String verticalId,@RequestParam String languageId,
											@RequestParam String loginUserID,@RequestBody UpdateVertical updateVertical) {
		Vertical vertical = idmasterService.updateVertical(verticalId,languageId,loginUserID,updateVertical,getAuthToken());
		log.info("updateVertical::: " + vertical);
		return new ResponseEntity<>(vertical, HttpStatus.OK);
	}

	@ApiOperation(response = Vertical.class, value = "Delete Vertical") // label for swagger
	@RequestMapping(value = "/vertical/{verticalId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteVertical(@PathVariable String verticalId,@RequestParam String languageId) {
		boolean isVerticalDeleted = idmasterService.deleteVertical(verticalId,languageId,getAuthToken());
		log.info("isVerticalDeleted::: " + isVerticalDeleted);
		return new ResponseEntity<>(isVerticalDeleted, HttpStatus.OK);
	}

	//FIND
	@ApiOperation(response = Vertical[].class, value = "Find VerticalId")//label for swagger
	@PostMapping("/vertical/findVertical")
	public Vertical[] findVertical(@RequestBody FindVertical findVertical)throws Exception{
		return idmasterService.findVertical(findVertical,getAuthToken());
	}

	/*
	 * --------------------------------BarcodeSubTypeId---------------------------------
	 */
	@ApiOperation(response = BarcodeSubTypeId.class, value = "Get all BarcodeSubTypeId details") // label for swagger
	@GetMapping("/barcodesubtypeid")
	public ResponseEntity<?> getBarcodeSubTypeIds() {
		BarcodeSubTypeId[] barcodeSubTypeIdList = idmasterService.getBarcodeSubTypeIds(getAuthToken());
		return new ResponseEntity<>(barcodeSubTypeIdList, HttpStatus.OK);
	}

	@ApiOperation(response = BarcodeSubTypeId.class, value = "Get a BarcodeSubTypeId") // label for swagger
	@GetMapping("/barcodesubtypeid/{barcodeSubTypeId}")
	public ResponseEntity<?> getBarcodeSubTypeId(@RequestParam String warehouseId, @RequestParam Long barcodeTypeId,@PathVariable Long barcodeSubTypeId,
												 @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		BarcodeSubTypeId dbBarcodeSubTypeId =
				idmasterService.getBarcodeSubTypeId(warehouseId, barcodeTypeId, barcodeSubTypeId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("BarcodeSubTypeId : " + dbBarcodeSubTypeId);
		return new ResponseEntity<>(dbBarcodeSubTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = BarcodeSubTypeId.class, value = "Create BarcodeSubTypeId") // label for swagger
	@PostMapping("/barcodesubtypeid")
	public ResponseEntity<?> postBarcodeSubTypeId(@Valid @RequestBody AddBarcodeSubTypeId newBarcodeSubTypeId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		BarcodeSubTypeId createdBarcodeSubTypeId = idmasterService.createBarcodeSubTypeId(newBarcodeSubTypeId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdBarcodeSubTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = BarcodeSubTypeId.class, value = "Update BarcodeSubTypeId") // label for swagger
	@RequestMapping(value = "/barcodesubtypeid/{barcodeSubTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchBarcodeSubTypeId(@RequestParam String warehouseId, @RequestParam Long barcodeTypeId,
												   @PathVariable Long barcodeSubTypeId, @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
												   @Valid @RequestBody UpdateBarcodeSubTypeId updateBarcodeSubTypeId) throws IllegalAccessException, InvocationTargetException {
		BarcodeSubTypeId updatedBarcodeSubTypeId =
				idmasterService.updateBarcodeSubTypeId(warehouseId, barcodeTypeId, barcodeSubTypeId,companyCodeId,languageId,plantId,loginUserID, updateBarcodeSubTypeId, getAuthToken());
		return new ResponseEntity<>(updatedBarcodeSubTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = BarcodeSubTypeId.class, value = "Delete BarcodeSubTypeId") // label for swagger
	@DeleteMapping("/barcodesubtypeid/{barcodeSubTypeId}")
	public ResponseEntity<?> deleteBarcodeSubTypeId( @RequestParam String warehouseId, @RequestParam Long barcodeTypeId,
													 @PathVariable Long barcodeSubTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
													 @RequestParam String loginUserID) {
		idmasterService.deleteBarcodeSubTypeId(warehouseId, barcodeTypeId, barcodeSubTypeId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = BarcodeSubTypeId[].class, value = "Find BarcodeSubTypeId")//label for swagger
	@PostMapping("/barcodesubtypeid/findBarcodeSubTypeId")
	public BarcodeSubTypeId[] findBarcodeSubTypeId(@RequestBody FindBarcodeSubtypeId findBarcodeSubTypeId)throws Exception{
		return idmasterService.findBarcodeSubTypeId(findBarcodeSubTypeId,getAuthToken());
	}

	/*
	 * --------------------------------BarcodeTypeId---------------------------------
	 */
	@ApiOperation(response = BarcodeTypeId.class, value = "Get all BarcodeTypeId details") // label for swagger
	@GetMapping("/barcodetypeid")
	public ResponseEntity<?> getBarcodeTypeIds() {
		BarcodeTypeId[] barcodeTypeIdList = idmasterService.getBarcodeTypeIds(getAuthToken());
		return new ResponseEntity<>(barcodeTypeIdList, HttpStatus.OK);
	}

	@ApiOperation(response = BarcodeTypeId.class, value = "Get a BarcodeTypeId") // label for swagger
	@GetMapping("/barcodetypeid/{barcodeTypeId}")
	public ResponseEntity<?> getBarcodeTypeId( @RequestParam String warehouseId,@PathVariable Long barcodeTypeId,
											   @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		BarcodeTypeId dbBarcodeTypeId = idmasterService.getBarcodeTypeId(warehouseId,barcodeTypeId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("BarcodeTypeId : " + dbBarcodeTypeId);
		return new ResponseEntity<>(dbBarcodeTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = BarcodeTypeId.class, value = "Create BarcodeTypeId") // label for swagger
	@PostMapping("/barcodetypeid")
	public ResponseEntity<?> postBarcodeTypeId(@Valid @RequestBody AddBarcodeTypeId newBarcodeTypeId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		BarcodeTypeId createdBarcodeTypeId = idmasterService.createBarcodeTypeId(newBarcodeTypeId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdBarcodeTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = BarcodeTypeId.class, value = "Update BarcodeTypeId") // label for swagger
	@RequestMapping(value = "/barcodetypeid/{barcodeTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchBarcodeTypeId(@RequestParam String warehouseId,@PathVariable Long barcodeTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												@RequestParam String loginUserID, @Valid @RequestBody UpdateBarcodeTypeId updateBarcodeTypeId)
			throws IllegalAccessException, InvocationTargetException {
		BarcodeTypeId updatedBarcodeTypeId =
				idmasterService.updateBarcodeTypeId(warehouseId, barcodeTypeId,companyCodeId,languageId,plantId,loginUserID,updateBarcodeTypeId, getAuthToken());
		return new ResponseEntity<>(updatedBarcodeTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = BarcodeTypeId.class, value = "Delete BarcodeTypeId") // label for swagger
	@DeleteMapping("/barcodetypeid/{barcodeTypeId}")
	public ResponseEntity<?> deleteBarcodeTypeId( @RequestParam String warehouseId,@PathVariable Long barcodeTypeId,
												  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												  @RequestParam String loginUserID) {
		idmasterService.deleteBarcodeTypeId(warehouseId, barcodeTypeId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = BarcodeTypeId[].class, value = "Find BarcodeTypeId")//label for swagger
	@PostMapping("/barcodetypeid/findBarcodeTypeId")
	public BarcodeTypeId[] findBarcodeTypeId(@RequestBody FindBarcodeTypeId findBarcodeTypeId)throws Exception{
		return idmasterService.findBarcodeTypeId(findBarcodeTypeId,getAuthToken());
	}

	/*
	 * --------------------------------CompanyId---------------------------------
	 */
	@ApiOperation(response = CompanyId.class, value = "Get all CompanyId details") // label for swagger
	@GetMapping("/companyid")
	public ResponseEntity<?> getCompanyIds() {
		CompanyId[] companyCodeIdList = idmasterService.getCompanyIds(getAuthToken());
		return new ResponseEntity<>(companyCodeIdList, HttpStatus.OK);
	}

	@ApiOperation(response = CompanyId.class, value = "Get a CompanyId") // label for swagger
	@GetMapping("/companyid/{companyCodeId}")
	public ResponseEntity<?> getCompanyId(@PathVariable String companyCodeId,@RequestParam String languageId) {
		CompanyId dbCompanyId = idmasterService.getCompanyId(companyCodeId,languageId,getAuthToken());
		log.info("CompanyId : " + dbCompanyId);
		return new ResponseEntity<>(dbCompanyId, HttpStatus.OK);
	}

	@ApiOperation(response = CompanyId.class, value = "Create CompanyId") // label for swagger
	@PostMapping("/companyid")
	public ResponseEntity<?> postCompanyId(@Valid @RequestBody AddCompanyId newCompanyId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		CompanyId createdCompanyId = idmasterService.createCompanyId(newCompanyId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdCompanyId, HttpStatus.OK);
	}

	@ApiOperation(response = CompanyId.class, value = "Update CompanyId") // label for swagger
	@RequestMapping(value = "/companyid/{companyCodeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchCompanyId(@PathVariable String companyCodeId, @RequestParam String languageId,
											@RequestParam String loginUserID, @Valid @RequestBody UpdateCompanyId updateCompanyId)
			throws IllegalAccessException, InvocationTargetException {
		CompanyId updatedCompanyId = idmasterService.updateCompanyId(companyCodeId,languageId,loginUserID,updateCompanyId, getAuthToken());
		return new ResponseEntity<>(updatedCompanyId, HttpStatus.OK);
	}

	@ApiOperation(response = CompanyId.class, value = "Delete CompanyId") // label for swagger
	@DeleteMapping("/companyid/{companyCodeId}")
	public ResponseEntity<?> deleteCompanyId(@PathVariable String companyCodeId,@RequestParam String languageId,
											 @RequestParam String loginUserID) {
		idmasterService.deleteCompanyId(companyCodeId,languageId, loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = CompanyId[].class, value = "Find CompanyId")//label for swagger
	@PostMapping("/companyid/findcompanyid")
	public CompanyId[] findCompanyId(@RequestBody FindCompanyId findCompanyId)throws Exception{
		return idmasterService.findCompanyId(findCompanyId,getAuthToken());
	}

	/*
	 * --------------------------------FloorId---------------------------------
	 */
	@ApiOperation(response = FloorId.class, value = "Get all FloorId details") // label for swagger
	@GetMapping("/floorid")
	public ResponseEntity<?> getFloorIds() {
		FloorId[] floorIdList = idmasterService.getFloorIds(getAuthToken());
		return new ResponseEntity<>(floorIdList, HttpStatus.OK);
	}

	@ApiOperation(response = FloorId.class, value = "Get a FloorId") // label for swagger
	@GetMapping("/floorid/{floorId}")
	public ResponseEntity<?> getFloorId(@RequestParam String warehouseId,@PathVariable Long floorId,@RequestParam String companyCodeId,
										@RequestParam String languageId,@RequestParam String plantId) {
		FloorId dbFloorId = idmasterService.getFloorId(warehouseId,floorId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("FloorId : " + dbFloorId);
		return new ResponseEntity<>(dbFloorId, HttpStatus.OK);
	}

	@ApiOperation(response = FloorId.class, value = "Create FloorId") // label for swagger
	@PostMapping("/floorid")
	public ResponseEntity<?> postFloorId(@Valid @RequestBody AddFloorId newFloorId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		FloorId createdFloorId = idmasterService.createFloorId(newFloorId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdFloorId, HttpStatus.OK);
	}

	@ApiOperation(response = FloorId.class, value = "Update FloorId") // label for swagger
	@RequestMapping(value = "/floorid/{floorId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchFloorId(@RequestParam String warehouseId,@PathVariable Long floorId,@RequestParam String companyCodeId,
										  @RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID, @Valid @RequestBody UpdateFloorId updateFloorId)
			throws IllegalAccessException, InvocationTargetException {
		FloorId updatedFloorId =
				idmasterService.updateFloorId(warehouseId,floorId,companyCodeId,languageId,plantId,loginUserID,updateFloorId, getAuthToken());
		return new ResponseEntity<>(updatedFloorId, HttpStatus.OK);
	}

	@ApiOperation(response = FloorId.class, value = "Delete FloorId") // label for swagger
	@DeleteMapping("/floorid/{floorId}")
	public ResponseEntity<?> deleteFloorId(@RequestParam String warehouseId,@PathVariable Long floorId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
										   @RequestParam String loginUserID) {
		idmasterService.deleteFloorId(warehouseId,floorId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = FloorId[].class, value = "Find FloorId")//label for swagger
	@PostMapping("/floorid/findfloorid")
	public FloorId[] findFloorId(@RequestBody FindFloorId findFloorId)throws Exception{
		return idmasterService.findFloorId(findFloorId,getAuthToken());
	}

	/*
	 * --------------------------------ItemGroupId---------------------------------
	 */
	@ApiOperation(response = ItemGroupId.class, value = "Get all ItemGroupId details") // label for swagger
	@GetMapping("/itemgroupid")
	public ResponseEntity<?> getItemGroupIds() {
		ItemGroupId[] itemGroupIdList = idmasterService.getItemGroupIds(getAuthToken());
		return new ResponseEntity<>(itemGroupIdList, HttpStatus.OK);
	}

	@ApiOperation(response = ItemGroupId.class, value = "Get a ItemGroupId") // label for swagger
	@GetMapping("/itemgroupid/{itemGroupId}")
	public ResponseEntity<?> getItemGroupId( @RequestParam String warehouseId, @RequestParam Long itemTypeId,@PathVariable Long itemGroupId,
											 @RequestParam String companyCodeId,@RequestParam String plantId,@RequestParam String languageId) {
		ItemGroupId dbItemGroupId = idmasterService.getItemGroupId(warehouseId, itemTypeId, itemGroupId,companyCodeId,plantId,languageId,getAuthToken());
		log.info("ItemGroupId : " + dbItemGroupId);
		return new ResponseEntity<>(dbItemGroupId, HttpStatus.OK);
	}

	@ApiOperation(response = ItemGroupId.class, value = "Create ItemGroupId") // label for swagger
	@PostMapping("/itemgroupid")
	public ResponseEntity<?> postItemGroupId(@Valid @RequestBody AddItemGroupId newItemGroupId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		ItemGroupId createdItemGroupId = idmasterService.createItemGroupId(newItemGroupId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdItemGroupId, HttpStatus.OK);
	}

	@ApiOperation(response = ItemGroupId.class, value = "Update ItemGroupId") // label for swagger
	@RequestMapping(value = "/itemgroupid/{itemGroupId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchItemGroupId(@RequestParam String warehouseId, @RequestParam Long itemTypeId,@PathVariable Long itemGroupId,@RequestParam String companyCodeId,@RequestParam String plantId,@RequestParam String languageId,
											  @RequestParam String loginUserID, @Valid @RequestBody UpdateItemGroupId updateItemGroupId)
			throws IllegalAccessException, InvocationTargetException {
		ItemGroupId updatedItemGroupId =
				idmasterService.updateItemGroupId(warehouseId, itemTypeId, itemGroupId,companyCodeId,plantId,languageId,loginUserID,updateItemGroupId, getAuthToken());
		return new ResponseEntity<>(updatedItemGroupId, HttpStatus.OK);
	}

	@ApiOperation(response = ItemGroupId.class, value = "Delete ItemGroupId") // label for swagger
	@DeleteMapping("/itemgroupid/{itemGroupId}")
	public ResponseEntity<?> deleteItemGroupId(@RequestParam String warehouseId,@RequestParam Long itemTypeId,@PathVariable Long itemGroupId,@RequestParam String companyCodeId,@RequestParam String plantId,@RequestParam String languageId,
											   @RequestParam String loginUserID) {
		idmasterService.deleteItemGroupId(warehouseId,itemTypeId,itemGroupId,companyCodeId,plantId,languageId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = ItemGroupId[].class, value = "Find ItemGroupId")//label for swagger
	@PostMapping("/itemgroupid/findItemGroupId")
	public ItemGroupId[] findItemGroupId(@RequestBody FindItemGroupId findItemGroupId)throws Exception{
		return idmasterService.findItemGroupId(findItemGroupId,getAuthToken());
	}

	/*
	 * --------------------------------ItemTypeId---------------------------------
	 */
	@ApiOperation(response = ItemTypeId.class, value = "Get all ItemTypeId details") // label for swagger
	@GetMapping("/itemtypeid")
	public ResponseEntity<?> getItemTypeIds() {
		ItemTypeId[] itemTypeIdList = idmasterService.getItemTypeIds(getAuthToken());
		return new ResponseEntity<>(itemTypeIdList, HttpStatus.OK);
	}

	@ApiOperation(response = ItemTypeId.class, value = "Get a ItemTypeId") // label for swagger
	@GetMapping("/itemtypeid/{itemTypeId}")
	public ResponseEntity<?> getItemTypeId(@RequestParam String warehouseId,@PathVariable Long itemTypeId, @RequestParam String companyCodeId,@RequestParam String plantId,@RequestParam String languageId) {
		ItemTypeId dbItemTypeId = idmasterService.getItemTypeId(warehouseId,itemTypeId,companyCodeId,plantId,languageId,getAuthToken());
		log.info("ItemTypeId : " + dbItemTypeId);
		return new ResponseEntity<>(dbItemTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = ItemTypeId.class, value = "Create ItemTypeId") // label for swagger
	@PostMapping("/itemtypeid")
	public ResponseEntity<?> postItemTypeId(@Valid @RequestBody AddItemTypeId newItemTypeId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		ItemTypeId createdItemTypeId = idmasterService.createItemTypeId(newItemTypeId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdItemTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = ItemTypeId.class, value = "Update ItemTypeId") // label for swagger
	@RequestMapping(value = "/itemtypeid/{itemTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchItemTypeId(@RequestParam String warehouseId,@PathVariable Long itemTypeId,@RequestParam String companyCodeId,@RequestParam String plantId,@RequestParam String languageId,
											 @RequestParam String loginUserID, @Valid @RequestBody UpdateItemTypeId updateItemTypeId)
			throws IllegalAccessException, InvocationTargetException {
		ItemTypeId updatedItemTypeId =
				idmasterService.updateItemTypeId(warehouseId, itemTypeId,companyCodeId,plantId,languageId,loginUserID, updateItemTypeId, getAuthToken());
		return new ResponseEntity<>(updatedItemTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = ItemTypeId.class, value = "Delete ItemTypeId") // label for swagger
	@DeleteMapping("/itemtypeid/{itemTypeId}")
	public ResponseEntity<?> deleteItemTypeId(@RequestParam String warehouseId,@PathVariable Long itemTypeId,@RequestParam String companyCodeId,@RequestParam String plantId,@RequestParam String languageId,
											  @RequestParam String loginUserID) {
		idmasterService.deleteItemTypeId(warehouseId, itemTypeId,companyCodeId,plantId,languageId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = ItemTypeId[].class, value = "Find ItemTypeId")//label for swagger
	@PostMapping("/itemtypeid/findItemTypeId")
	public ItemTypeId[] findItemTypeId(@RequestBody FindItemTypeId findItemTypeId)throws Exception{
		return idmasterService.findItemTypeId(findItemTypeId,getAuthToken());
	}

	/*
	 * --------------------------------LevelId---------------------------------
	 */
	@ApiOperation(response = LevelId.class, value = "Get all LevelId details") // label for swagger
	@GetMapping("/levelid")
	public ResponseEntity<?> getLevelIds() {
		LevelId[] levelIdList = idmasterService.getLevelIds(getAuthToken());
		return new ResponseEntity<>(levelIdList, HttpStatus.OK);
	}

	@ApiOperation(response = LevelId.class, value = "Get a LevelId") // label for swagger
	@GetMapping("/levelid/{levelId}")
	public ResponseEntity<?> getLevelId( @RequestParam String warehouseId,@PathVariable Long levelId,
										 @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		LevelId dbLevelId = idmasterService.getLevelId(warehouseId,levelId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("LevelId : " + dbLevelId);
		return new ResponseEntity<>(dbLevelId, HttpStatus.OK);
	}

	@ApiOperation(response = LevelId.class, value = "Create LevelId") // label for swagger
	@PostMapping("/levelid")
	public ResponseEntity<?> postLevelId(@Valid @RequestBody AddLevelId newLevelId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		LevelId createdLevelId = idmasterService.createLevelId(newLevelId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdLevelId, HttpStatus.OK);
	}

	@ApiOperation(response = LevelId.class, value = "Update LevelId") // label for swagger
	@RequestMapping(value = "/levelid/{levelId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchLevelId(@RequestParam String warehouseId, @PathVariable Long levelId,
										  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
										  @RequestParam String loginUserID, @Valid @RequestBody UpdateLevelId updateLevelId)
			throws IllegalAccessException, InvocationTargetException {
		LevelId updatedLevelId =
				idmasterService.updateLevelId(warehouseId,levelId,companyCodeId,languageId,plantId,loginUserID,updateLevelId, getAuthToken());
		return new ResponseEntity<>(updatedLevelId, HttpStatus.OK);
	}

	@ApiOperation(response = LevelId.class, value = "Delete LevelId") // label for swagger
	@DeleteMapping("/levelid/{levelId}")
	public ResponseEntity<?> deleteLevelId(@RequestParam String warehouseId,@PathVariable Long levelId,
										   @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
										   @RequestParam String loginUserID) {
		idmasterService.deleteLevelId(warehouseId,levelId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = LevelId[].class, value = "Find LevelId")//label for swagger
	@PostMapping("/levelid/findLevelId")
	public LevelId[] findLevelId(@RequestBody FindLevelId findLevelId)throws Exception{
		return idmasterService.findLevelId(findLevelId,getAuthToken());
	}


	/*
	 * --------------------------------MenuId-----------------------------------------------------------------------------------
	 */
	@ApiOperation(response = MenuId.class, value = "Get all MenuId details") // label for swagger
	@GetMapping("/menuid")
	public ResponseEntity<?> getMenuIds() {
		MenuId[] menuIdList = idmasterService.getMenuIds(getAuthToken());
		return new ResponseEntity<>(menuIdList, HttpStatus.OK);
	}

	@ApiOperation(response = MenuId.class, value = "Get a MenuId") // label for swagger
	@GetMapping("/menuid/{menuId}")
	public ResponseEntity<?> getMenuId(@RequestParam String warehouseId,@PathVariable Long menuId,  @RequestParam Long subMenuId,
									   @RequestParam Long authorizationObjectId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		MenuId dbMenuId = idmasterService.getMenuId(warehouseId, menuId, subMenuId, authorizationObjectId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("MenuId : " + dbMenuId);
		return new ResponseEntity<>(dbMenuId, HttpStatus.OK);
	}

	@ApiOperation(response = MenuId.class, value = "Create MenuId") // label for swagger
	@PostMapping("/menuid")
	public ResponseEntity<?> postMenuId(@Valid @RequestBody AddMenuId newMenuId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		MenuId createdMenuId = idmasterService.createMenuId(newMenuId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdMenuId, HttpStatus.OK);
	}

	//Create - Multiple
	@ApiOperation(response = MenuId.class, value = "Create MenuId Bulk") // label for swagger
	@PostMapping("/menuid/bulk")
	public ResponseEntity<?> postMenuIdBulk(@Valid @RequestBody List<AddMenuId> newMenuId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		MenuId[] createdMenuId = idmasterService.createMenuIdBulk(newMenuId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdMenuId, HttpStatus.OK);
	}

	@ApiOperation(response = MenuId.class, value = "Update MenuId") // label for swagger
	@RequestMapping(value = "/menuid/{menuId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchMenuId( @RequestParam String warehouseId,@PathVariable Long menuId, @RequestParam Long subMenuId, @RequestParam Long authorizationObjectId,
										  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID, @Valid @RequestBody UpdateMenuId updateMenuId)
			throws IllegalAccessException, InvocationTargetException {
		MenuId updatedMenuId = idmasterService.updateMenuId(warehouseId, menuId, subMenuId, authorizationObjectId,companyCodeId,languageId,
				plantId,loginUserID, updateMenuId, getAuthToken());
		return new ResponseEntity<>(updatedMenuId, HttpStatus.OK);
	}

	@ApiOperation(response = MenuId.class, value = "Delete MenuId") // label for swagger
	@DeleteMapping("/menuid/{menuId}")
	public ResponseEntity<?> deleteMenuId(@RequestParam String warehouseId,@PathVariable Long menuId, @RequestParam Long subMenuId,
										  @RequestParam Long authorizationObjectId, @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
										  @RequestParam String loginUserID) {
		idmasterService.deleteMenuId(warehouseId, menuId, subMenuId, authorizationObjectId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = MenuId[].class, value = "Find MenuId")//label for swagger
	@PostMapping("/menuid/findMenuId")
	public MenuId[] findMenuId(@RequestBody FindMenuId findMenuId)throws Exception{
		return idmasterService.findMenuId(findMenuId,getAuthToken());
	}

	/*
	 * --------------------------------PlantId---------------------------------
	 */
	@ApiOperation(response = PlantId.class, value = "Get all PlantId details") // label for swagger
	@GetMapping("/plantid")
	public ResponseEntity<?> getPlantIds() {
		PlantId[] plantIdList = idmasterService.getPlantIds(getAuthToken());
		return new ResponseEntity<>(plantIdList, HttpStatus.OK);
	}

	@ApiOperation(response = PlantId.class, value = "Get a PlantId") // label for swagger
	@GetMapping("/plantid/{plantId}")
	public ResponseEntity<?> getPlantId(@PathVariable String plantId,@RequestParam String companyCodeId,@RequestParam String languageId) {
		PlantId dbPlantId = idmasterService.getPlantId(plantId,companyCodeId,languageId,getAuthToken());
		log.info("PlantId : " + dbPlantId);
		return new ResponseEntity<>(dbPlantId, HttpStatus.OK);
	}

	@ApiOperation(response = PlantId.class, value = "Create PlantId") // label for swagger
	@PostMapping("/plantid")
	public ResponseEntity<?> postPlantId(@Valid @RequestBody AddPlantId newPlantId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		PlantId createdPlantId = idmasterService.createPlantId(newPlantId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdPlantId, HttpStatus.OK);
	}

	@ApiOperation(response = PlantId.class, value = "Update PlantId") // label for swagger
	@RequestMapping(value = "/plantid/{plantId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchPlantId(@PathVariable String plantId, @RequestParam String companyCodeId,@RequestParam String languageId,
										  @RequestParam String loginUserID, @Valid @RequestBody UpdatePlantId updatePlantId)
			throws IllegalAccessException, InvocationTargetException {
		PlantId updatedPlantId = idmasterService.updatePlantId(plantId,companyCodeId,languageId,loginUserID,updatePlantId, getAuthToken());
		return new ResponseEntity<>(updatedPlantId, HttpStatus.OK);
	}

	@ApiOperation(response = PlantId.class, value = "Delete PlantId") // label for swagger
	@DeleteMapping("/plantid/{plantId}")
	public ResponseEntity<?> deletePlantId(@PathVariable String plantId,@RequestParam String companyCodeId,@RequestParam String languageId, @RequestParam String loginUserID) {
		idmasterService.deletePlantId(plantId,companyCodeId,languageId, loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = PlantId[].class, value = "Find PlantId")//label for swagger
	@PostMapping("/plantid/findplantid")
	public PlantId[] findPlantId(@RequestBody FindPlantId findPlantId)throws Exception{
		return idmasterService.findPlantId(findPlantId,getAuthToken());
	}

	/*
	 * --------------------------------ProcessSequenceId---------------------------------
	 */
	@ApiOperation(response = ProcessSequenceId.class, value = "Get all ProcessSequenceId details") // label for swagger
	@GetMapping("/processsequenceid")
	public ResponseEntity<?> getProcessSequenceIds() {
		ProcessSequenceId[] processIdList = idmasterService.getProcessSequenceIds(getAuthToken());
		return new ResponseEntity<>(processIdList, HttpStatus.OK);
	}

	@ApiOperation(response = ProcessSequenceId.class, value = "Get a ProcessSequenceId") // label for swagger
	@GetMapping("/processsequenceid/{processSequenceId}")
	public ResponseEntity<?> getProcessSequenceId(@RequestParam String warehouseId,@RequestParam String processId,
												  @PathVariable Long processSequenceId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		ProcessSequenceId dbProcessSequenceId =
				idmasterService.getProcessSequenceId(warehouseId,processId,processSequenceId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("ProcessSequenceId : " + dbProcessSequenceId);
		return new ResponseEntity<>(dbProcessSequenceId, HttpStatus.OK);
	}

	@ApiOperation(response = ProcessSequenceId.class, value = "Create ProcessSequenceId") // label for swagger
	@PostMapping("/processsequenceid")
	public ResponseEntity<?> postProcessSequenceId(@Valid @RequestBody AddProcessSequenceId newProcessSequenceId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		ProcessSequenceId createdProcessSequenceId = idmasterService.createProcessSequenceId(newProcessSequenceId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdProcessSequenceId, HttpStatus.OK);
	}

	@ApiOperation(response = ProcessSequenceId.class, value = "Update ProcessSequenceId") // label for swagger
	@RequestMapping(value = "/processsequenceid/{processSequenceId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchProcessSequenceId(@RequestParam String warehouseId,@RequestParam String processId,@PathVariable Long processSequenceId,@RequestParam String companyCodeId,
													@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID, @Valid @RequestBody UpdateProcessSequenceId updateProcessSequenceId)
			throws IllegalAccessException, InvocationTargetException {
		ProcessSequenceId updatedProcessSequenceId =
				idmasterService.updateProcessSequenceId(warehouseId, processId, processSequenceId, companyCodeId,languageId,plantId,loginUserID,updateProcessSequenceId, getAuthToken());
		return new ResponseEntity<>(updatedProcessSequenceId, HttpStatus.OK);
	}

	@ApiOperation(response = ProcessSequenceId.class, value = "Delete ProcessSequenceId") // label for swagger
	@DeleteMapping("/processsequenceid/{processSequenceId}")
	public ResponseEntity<?> deleteProcessSequenceId(@RequestParam String warehouseId,@RequestParam String processId, @PathVariable Long processSequenceId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
													 @RequestParam String loginUserID) {
		idmasterService.deleteProcessSequenceId(warehouseId, processId, processSequenceId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = ProcessSequenceId[].class, value = "Find ProcessSequenceId")//label for swagger
	@PostMapping("/processsequenceid/findProcessSequenceId")
	public ProcessSequenceId[] findProcessSequenceId(@RequestBody FindProcessSequenceId findProcessSequenceId)throws Exception{
		return idmasterService.findProcessSequenceId(findProcessSequenceId,getAuthToken());
	}

	/*
	 * --------------------------------RowId---------------------------------
	 */
	@ApiOperation(response = RowId.class, value = "Get all RowId details") // label for swagger
	@GetMapping("/rowid")
	public ResponseEntity<?> getRowIds() {
		RowId[] rowIdList = idmasterService.getRowIds(getAuthToken());
		return new ResponseEntity<>(rowIdList, HttpStatus.OK);
	}

	@ApiOperation(response = RowId.class, value = "Get a RowId") // label for swagger
	@GetMapping("/rowid/{rowId}")
	public ResponseEntity<?> getRowId(@RequestParam String warehouseId,@RequestParam Long floorId,
									  @RequestParam String storageSectionId,@PathVariable String rowId,@RequestParam String companyCodeId,
									  @RequestParam String languageId,@RequestParam String plantId) {
		RowId dbRowId = idmasterService.getRowId(warehouseId,floorId,storageSectionId,rowId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("RowId : " + dbRowId);
		return new ResponseEntity<>(dbRowId, HttpStatus.OK);
	}

	@ApiOperation(response = RowId.class, value = "Create RowId") // label for swagger
	@PostMapping("/rowid")
	public ResponseEntity<?> postRowId(@Valid @RequestBody AddRowId newRowId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		RowId createdRowId = idmasterService.createRowId(newRowId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdRowId, HttpStatus.OK);
	}

	@ApiOperation(response = RowId.class, value = "Update RowId") // label for swagger
	@RequestMapping(value = "/rowid/{rowId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchRowId(@RequestParam String warehouseId, @RequestParam Long floorId,
										@RequestParam String storageSectionId,@PathVariable String rowId,@RequestParam String companyCodeId,
										@RequestParam String languageId, @RequestParam String plantId,@RequestParam String loginUserID,
										@Valid @RequestBody UpdateRowId updateRowId)
			throws IllegalAccessException, InvocationTargetException {
		RowId updatedRowId =
				idmasterService.updateRowId(warehouseId,floorId, storageSectionId,rowId,companyCodeId,languageId,plantId,loginUserID,updateRowId, getAuthToken());
		return new ResponseEntity<>(updatedRowId, HttpStatus.OK);
	}

	@ApiOperation(response = RowId.class, value = "Delete RowId") // label for swagger
	@DeleteMapping("/rowid/{rowId}")
	public ResponseEntity<?> deleteRowId(@RequestParam String warehouseId, @RequestParam Long floorId,
										 @RequestParam String storageSectionId,@PathVariable String rowId,@RequestParam String companyCodeId,@RequestParam String languageId,
										 @RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deleteRowId(warehouseId, floorId, storageSectionId,rowId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = RowId[].class, value = "Find RowId")//label for swagger
	@PostMapping("/rowid/findRowId")
	public RowId[] findRowId(@RequestBody FindRowId findRowId)throws Exception{
		return idmasterService.findRowId(findRowId,getAuthToken());
	}

	/*
	 * --------------------------------StatusId----------------------------------------------------------------------------
	 */
	@ApiOperation(response = StatusId.class, value = "Get all StatusId details") // label for swagger
	@GetMapping("/statusid")
	public ResponseEntity<?> getStatusIds() {
		StatusId[] statusIdList = idmasterService.getStatusIds(getAuthToken());
		return new ResponseEntity<>(statusIdList, HttpStatus.OK);
	}

	@ApiOperation(response = StatusId.class, value = "Get a StatusId") // label for swagger
	@GetMapping("/statusid/{statusId}")
	public ResponseEntity<?> getStatusId(@PathVariable Long statusId,
										 @RequestParam String languageId) {
		StatusId dbStatusId = idmasterService.getStatusId(statusId,languageId,getAuthToken());
		log.info("StatusId : " + dbStatusId);
		return new ResponseEntity<>(dbStatusId, HttpStatus.OK);
	}

	@ApiOperation(response = StatusId.class, value = "Create StatusId") // label for swagger
	@PostMapping("/statusid")
	public ResponseEntity<?> postStatusId(@Valid @RequestBody AddStatusId newStatusId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		StatusId createdStatusId = idmasterService.createStatusId(newStatusId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdStatusId, HttpStatus.OK);
	}

	@ApiOperation(response = StatusId.class, value = "Update StatusId") // label for swagger
	@RequestMapping(value = "/statusid/{statusId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchStatusId(@PathVariable Long statusId,@RequestParam String languageId,
										   @RequestParam String loginUserID, @Valid @RequestBody UpdateStatusId updateStatusId)
			throws IllegalAccessException, InvocationTargetException {
		StatusId updatedStatusId =
				idmasterService.updateStatusId(statusId,languageId,loginUserID,updateStatusId,getAuthToken());
		return new ResponseEntity<>(updatedStatusId, HttpStatus.OK);
	}

	@ApiOperation(response = StatusId.class, value = "Delete StatusId") // label for swagger
	@DeleteMapping("/statusid/{statusId}")
	public ResponseEntity<?> deleteStatusId(@PathVariable Long statusId,@RequestParam String languageId,
											@RequestParam String loginUserID) {
		idmasterService.deleteStatusId(statusId,languageId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = StatusId[].class, value = "Find StatusId")//label for swagger
	@PostMapping("/statusid/findStatusId")
	public StatusId[] findStatusId(@RequestBody FindStatusId findStatusId)throws Exception{
		return idmasterService.findStatusId(findStatusId,getAuthToken());
	}


	/*
	 * --------------------------------StorageBinTypeId---------------------------------
	 */
	@ApiOperation(response = StorageBinTypeId.class, value = "Get all StorageBinTypeId details") // label for swagger
	@GetMapping("/storagebintypeid")
	public ResponseEntity<?> getStorageBinTypeIds() {
		StorageBinTypeId[] storageBinTypeIdList = idmasterService.getStorageBinTypeIds(getAuthToken());
		return new ResponseEntity<>(storageBinTypeIdList, HttpStatus.OK);
	}

	@ApiOperation(response = StorageBinTypeId.class, value = "Get a StorageBinTypeId") // label for swagger
	@GetMapping("/storagebintypeid/{storageBinTypeId}")
	public ResponseEntity<?> getStorageBinTypeId( @RequestParam String warehouseId,
												  @RequestParam Long storageClassId, @RequestParam Long storageTypeId,@PathVariable Long storageBinTypeId,
												  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		StorageBinTypeId dbStorageBinTypeId =
				idmasterService.getStorageBinTypeId(warehouseId, storageClassId, storageTypeId, storageBinTypeId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("StorageBinTypeId : " + dbStorageBinTypeId);
		return new ResponseEntity<>(dbStorageBinTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = StorageBinTypeId.class, value = "Create StorageBinTypeId") // label for swagger
	@PostMapping("/storagebintypeid")
	public ResponseEntity<?> postStorageBinTypeId(@Valid @RequestBody AddStorageBinTypeId newStorageBinTypeId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		StorageBinTypeId createdStorageBinTypeId = idmasterService.createStorageBinTypeId(newStorageBinTypeId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdStorageBinTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = StorageBinTypeId.class, value = "Update StorageBinTypeId") // label for swagger
	@RequestMapping(value = "/storagebintypeid/{storageBinTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchStorageBinTypeId(@RequestParam String warehouseId, @RequestParam Long storageClassId, @RequestParam Long storageTypeId,
												   @PathVariable Long storageBinTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												   @RequestParam String loginUserID,  @Valid @RequestBody UpdateStorageBinTypeId updateStorageBinTypeId)
			throws IllegalAccessException, InvocationTargetException {
		StorageBinTypeId updatedStorageBinTypeId =
				idmasterService.updateStorageBinTypeId(warehouseId, storageClassId, storageTypeId, storageBinTypeId,companyCodeId,languageId,plantId,loginUserID,updateStorageBinTypeId, getAuthToken());
		return new ResponseEntity<>(updatedStorageBinTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = StorageBinTypeId.class, value = "Delete StorageBinTypeId") // label for swagger
	@DeleteMapping("/storagebintypeid/{storageBinTypeId}")
	public ResponseEntity<?> deleteStorageBinTypeId( @RequestParam String warehouseId, @RequestParam Long storageClassId, @RequestParam Long storageTypeId,
													 @PathVariable Long storageBinTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
													 @RequestParam String loginUserID) {
		idmasterService.deleteStorageBinTypeId(warehouseId, storageClassId, storageTypeId, storageBinTypeId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = StorageBinTypeId[].class, value = "Find StorageBinTypeId")//label for swagger
	@PostMapping("/storagebintypeid/findstoragebintypeid")
	public StorageBinTypeId[] findStorageBinTypeId(@RequestBody FindStorageBinTypeId findStorageBinTypeId)throws Exception{
		return idmasterService.findStorageBinType(findStorageBinTypeId,getAuthToken());
	}

	/*
	 * --------------------------------StorageClassId---------------------------------
	 */
	@ApiOperation(response = StorageClassId.class, value = "Get all StorageClassId details") // label for swagger
	@GetMapping("/storageclassid")
	public ResponseEntity<?> getStorageClassIds() {
		StorageClassId[] storageClassIdList = idmasterService.getStorageClassIds(getAuthToken());
		return new ResponseEntity<>(storageClassIdList, HttpStatus.OK);
	}

	@ApiOperation(response = StorageClassId.class, value = "Get a StorageClassId") // label for swagger
	@GetMapping("/storageclassid/{storageClassId}")
	public ResponseEntity<?> getStorageClassId(@RequestParam String warehouseId,@PathVariable Long storageClassId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		StorageClassId dbStorageClassId = idmasterService.getStorageClassId(warehouseId, storageClassId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("StorageClassId : " + dbStorageClassId);
		return new ResponseEntity<>(dbStorageClassId, HttpStatus.OK);
	}

	@ApiOperation(response = StorageClassId.class, value = "Create StorageClassId") // label for swagger
	@PostMapping("/storageclassid")
	public ResponseEntity<?> postStorageClassId(@Valid @RequestBody AddStorageClassId newStorageClassId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		StorageClassId createdStorageClassId = idmasterService.createStorageClassId(newStorageClassId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdStorageClassId, HttpStatus.OK);
	}

	@ApiOperation(response = StorageClassId.class, value = "Update StorageClassId") // label for swagger
	@RequestMapping(value = "/storageclassid/{storageClassId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchStorageClassId( @RequestParam String warehouseId,@PathVariable Long storageClassId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												  @RequestParam String loginUserID, @Valid @RequestBody UpdateStorageClassId updateStorageClassId)
			throws IllegalAccessException, InvocationTargetException {
		StorageClassId updatedStorageClassId =
				idmasterService.updateStorageClassId(warehouseId, storageClassId,companyCodeId,languageId,plantId,loginUserID,updateStorageClassId, getAuthToken());
		return new ResponseEntity<>(updatedStorageClassId, HttpStatus.OK);
	}

	@ApiOperation(response = StorageClassId.class, value = "Delete StorageClassId") // label for swagger
	@DeleteMapping("/storageclassid/{storageClassId}")
	public ResponseEntity<?> deleteStorageClassId( @RequestParam String warehouseId, @PathVariable Long storageClassId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												   @RequestParam String loginUserID) {
		idmasterService.deleteStorageClassId(warehouseId, storageClassId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = StorageClassId[].class, value = "Find StorageClassId")//label for swagger
	@PostMapping("/storageclassid/findStorageClassId")
	public StorageClassId[] findStorageClassId(@RequestBody FindStorageClassId findStorageClassId)throws Exception{
		return idmasterService.findStorageClassId(findStorageClassId,getAuthToken());
	}

	/*
	 * --------------------------------StorageSectionId----------------------------------------------------------------
	 */
	@ApiOperation(response = StorageSectionId.class, value = "Get all StorageSectionId details") // label for swagger
	@GetMapping("/storagesectionid")
	public ResponseEntity<?> getStorageSectionIds() {
		StorageSectionId[] storageSectionIdList = idmasterService.getStorageSectionIds(getAuthToken());
		return new ResponseEntity<>(storageSectionIdList, HttpStatus.OK);
	}

	@ApiOperation(response = StorageSectionId.class, value = "Get a StorageSectionId") // label for swagger
	@GetMapping("/storagesectionid/{storageSectionId}")
	public ResponseEntity<?> getStorageSectionId(@RequestParam String warehouseId,
												 @RequestParam Long floorId,@PathVariable String storageSectionId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		StorageSectionId dbStorageSectionId = idmasterService.getStorageSectionId(warehouseId, floorId, storageSectionId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("StorageSectionId : " + dbStorageSectionId);
		return new ResponseEntity<>(dbStorageSectionId, HttpStatus.OK);
	}

	@ApiOperation(response = StorageSectionId.class, value = "Create StorageSectionId") // label for swagger
	@PostMapping("/storagesectionid")
	public ResponseEntity<?> postStorageSectionId(@Valid @RequestBody AddStorageSectionId newStorageSectionId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		StorageSectionId createdStorageSectionId = idmasterService.createStorageSectionId(newStorageSectionId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdStorageSectionId, HttpStatus.OK);
	}

	@ApiOperation(response = StorageSectionId.class, value = "Update StorageSectionId") // label for swagger
	@RequestMapping(value = "/storagesectionid/{storageSectionId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchStorageSectionId(@RequestParam String warehouseId, @RequestParam Long floorId, @PathVariable String storageSectionId,
												   @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												   @RequestParam String loginUserID, @Valid @RequestBody UpdateStorageSectionId updateStorageSectionId)
			throws IllegalAccessException, InvocationTargetException {
		StorageSectionId updatedStorageSectionId = idmasterService.updateStorageSectionId(warehouseId, floorId, storageSectionId,
				companyCodeId,languageId,plantId,loginUserID, updateStorageSectionId, getAuthToken());
		return new ResponseEntity<>(updatedStorageSectionId, HttpStatus.OK);
	}

	@ApiOperation(response = StorageSectionId.class, value = "Delete StorageSectionId") // label for swagger
	@DeleteMapping("/storagesectionid/{storageSectionId}")
	public ResponseEntity<?> deleteStorageSectionId(@RequestParam String warehouseId,
													@RequestParam Long floorId,@PathVariable String storageSectionId,
													@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deleteStorageSectionId(warehouseId,floorId,storageSectionId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = StorageSectionId[].class, value = "Find StorageSectionId")//label for swagger
	@PostMapping("/storagesectionid/findstoragesectionid")
	public StorageSectionId[] findStorageSectionId(@RequestBody FindStorageSectionId findStorageSectionId)throws Exception{
		return idmasterService.findStorageSectionId(findStorageSectionId,getAuthToken());
	}

	/*
	 * --------------------------------StrategyId---------------------------------------------------------------------------
	 */
	@ApiOperation(response = StrategyId.class, value = "Get all StrategyId details") // label for swagger
	@GetMapping("/strategyid")
	public ResponseEntity<?> getStrategyIds() {
		StrategyId[] strategyNoList = idmasterService.getStrategyIds(getAuthToken());
		return new ResponseEntity<>(strategyNoList, HttpStatus.OK);
	}

	@ApiOperation(response = StrategyId.class, value = "Get a StrategyId") // label for swagger
	@GetMapping("/strategyid/{strategyNo}")
	public ResponseEntity<?> getStrategyId( @RequestParam String warehouseId, @RequestParam Long strategyTypeId,
											@PathVariable String strategyNo,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		StrategyId dbStrategyId = idmasterService.getStrategyId(warehouseId, strategyTypeId, strategyNo,companyCodeId,languageId,plantId,getAuthToken());
		log.info("StrategyId : " + dbStrategyId);
		return new ResponseEntity<>(dbStrategyId, HttpStatus.OK);
	}

	@ApiOperation(response = StrategyId.class, value = "Create StrategyId") // label for swagger
	@PostMapping("/strategyid")
	public ResponseEntity<?> postStrategyId(@Valid @RequestBody AddStrategyId newStrategyId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		StrategyId createdStrategyId = idmasterService.createStrategyId(newStrategyId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdStrategyId, HttpStatus.OK);
	}

	@ApiOperation(response = StrategyId.class, value = "Update StrategyId") // label for swagger
	@RequestMapping(value = "/strategyid/{strategyNo}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchStrategyId( @RequestParam String warehouseId, @RequestParam Long strategyTypeId,@PathVariable String strategyNo,
											  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId, @RequestParam String loginUserID,
											  @Valid @RequestBody UpdateStrategyId updateStrategyId)
			throws IllegalAccessException, InvocationTargetException {
		StrategyId updatedStrategyId =
				idmasterService.updateStrategyId(warehouseId, strategyTypeId, strategyNo,companyCodeId,languageId,plantId,loginUserID, updateStrategyId, getAuthToken());
		return new ResponseEntity<>(updatedStrategyId, HttpStatus.OK);
	}

	@ApiOperation(response = StrategyId.class, value = "Delete StrategyId") // label for swagger
	@DeleteMapping("/strategyid/{strategyNo}")
	public ResponseEntity<?> deleteStrategyId(@RequestParam String warehouseId, @RequestParam Long strategyTypeId,@PathVariable String strategyNo,
											  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
											  @RequestParam String loginUserID) {
		idmasterService.deleteStrategyId(warehouseId, strategyTypeId, strategyNo,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = StrategyId[].class, value = "Find StrategyId")//label for swagger
	@PostMapping("/strategyid/findStrategyId")
	public StrategyId[] findStrategyId(@RequestBody FindStrategyId findStrategyId)throws Exception{
		return idmasterService.findStrategyId(findStrategyId,getAuthToken());
	}

	/*
	 * --------------------------------StroageTypeId---------------------------------
	 */
	@ApiOperation(response = StorageTypeId.class, value = "Get all StorageTypeId details") // label for swagger
	@GetMapping("/storagetypeid")
	public ResponseEntity<?> getStorageTypeIds() {
		StorageTypeId[] storageTypeIdList = idmasterService.getStroageTypeIds(getAuthToken());
		return new ResponseEntity<>(storageTypeIdList, HttpStatus.OK);
	}

	@ApiOperation(response = StorageTypeId.class, value = "Get a StorageTypeId") // label for swagger
	@GetMapping("/storagetypeid/{storageTypeId}")
	public ResponseEntity<?> getStorageTypeId(@RequestParam String warehouseId, @RequestParam Long storageClassId,@PathVariable Long storageTypeId,
											  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		StorageTypeId dbStorageTypeId = idmasterService.getStorageTypeId(warehouseId, storageClassId, storageTypeId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("StorageTypeId : " + dbStorageTypeId);
		return new ResponseEntity<>(dbStorageTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = StorageTypeId.class, value = "Create StorageTypeId") // label for swagger
	@PostMapping("/storagetypeid")
	public ResponseEntity<?> postStorageTypeId(@Valid @RequestBody AddStorageTypeId newStorageTypeId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		StorageTypeId createdStorageTypeId = idmasterService.createStorageTypeId(newStorageTypeId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdStorageTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = StorageTypeId.class, value = "Update StorageTypeId") // label for swagger
	@RequestMapping(value = "/storagetypeid/{storageTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchStorageTypeId(@PathVariable Long storageTypeId, @RequestParam String warehouseId, @RequestParam Long storageClassId,
												@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												@RequestParam String loginUserID,@Valid @RequestBody UpdateStorageTypeId updateStorageTypeId)
			throws IllegalAccessException, InvocationTargetException {
		StorageTypeId updatedStorageTypeId =
				idmasterService.updateStorageTypeId(storageTypeId,warehouseId, storageClassId, companyCodeId,languageId,plantId,loginUserID, updateStorageTypeId,getAuthToken());
		return new ResponseEntity<>(updatedStorageTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = StorageTypeId.class, value = "Delete StorageTypeId") // label for swagger
	@DeleteMapping("/storagetypeid/{storageTypeId}")
	public ResponseEntity<?> deleteStorageTypeId(@PathVariable Long storageTypeId, @RequestParam String warehouseId,
												 @RequestParam Long storageClassId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												 @RequestParam String loginUserID) {
		idmasterService.deleteStorageTypeId(storageTypeId,warehouseId, storageClassId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = StorageTypeId[].class, value = "Find StoragetypeId")//label for swagger
	@PostMapping("/storagetypeid/findStorageTypeId")
	public StorageTypeId[] findStorageTypeId(@RequestBody FindStorageTypeId findStorageTypeId)throws Exception{
		return idmasterService.findStorageTypeId(findStorageTypeId,getAuthToken());
	}

	/*
	 * --------------------------------SubItemGroupId---------------------------------
	 */

	@ApiOperation(response = SubItemGroupId.class, value = "Get all SubItemGroupId details") // label for swagger
	@GetMapping("/subitemgroupid")
	public ResponseEntity<?> getSubItemGroupIds() {
		SubItemGroupId[] subitemgroupidList = idmasterService.getSubItemGroupIds(getAuthToken());
		return new ResponseEntity<>(subitemgroupidList, HttpStatus.OK);
	}

	@ApiOperation(response = SubItemGroupId.class, value = "Get a SubItemGroupId") // label for swagger
	@GetMapping("/subitemgroupid/{subItemGroupId}")
	public ResponseEntity<?> getSubItemGroupId(@RequestParam String warehouseId,@RequestParam Long itemTypeId,
											   @RequestParam Long itemGroupId,@PathVariable Long subItemGroupId,
											   @RequestParam String companyCodeId,@RequestParam String languageId,
											   @RequestParam String plantId) {

		SubItemGroupId dbSubItemGroupId = idmasterService.getSubItemGroupId(warehouseId, itemTypeId, itemGroupId,
				subItemGroupId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("SubItemGroupId : " + dbSubItemGroupId);
		return new ResponseEntity<>(dbSubItemGroupId, HttpStatus.OK);
	}

	@ApiOperation(response = SubItemGroupId.class, value = "Create SubItemGroupId") // label for swagger
	@PostMapping("/subitemgroupid")
	public ResponseEntity<?> postSubItemGroupId(@Valid @RequestBody AddSubItemGroupId newSubItemGroupId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		SubItemGroupId createdSubItemGroupId = idmasterService.createSubItemGroupId(newSubItemGroupId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdSubItemGroupId, HttpStatus.OK);
	}

	@ApiOperation(response = SubItemGroupId.class, value = "Update SubItemGroupId") // label for swagger
	@RequestMapping(value = "/subitemgroupid/{subItemGroupId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchSubItemGroupId( @RequestParam String warehouseId,@RequestParam Long itemTypeId,
												  @RequestParam Long itemGroupId,@PathVariable Long subItemGroupId,
												  @RequestParam String companyCodeId,@RequestParam String languageId,
												  @RequestParam String plantId,@RequestParam String loginUserID,
												  @Valid @RequestBody UpdateSubItemGroupId updateSubItemGroupId)
			throws IllegalAccessException, InvocationTargetException {

		SubItemGroupId updatedSubItemGroupId = idmasterService.updateSubItemGroupId(warehouseId, itemTypeId,
				itemGroupId, subItemGroupId,companyCodeId,languageId,plantId,loginUserID,updateSubItemGroupId, getAuthToken());
		return new ResponseEntity<>(updatedSubItemGroupId, HttpStatus.OK);
	}

	@ApiOperation(response = SubItemGroupId.class, value = "Delete SubItemGroupId") // label for swagger
	@DeleteMapping("/subitemgroupid/{subItemGroupId}")
	public ResponseEntity<?> deleteSubItemGroupId(@RequestParam String warehouseId,@RequestParam Long itemTypeId,
												  @RequestParam Long itemGroupId,@PathVariable Long subItemGroupId,
												  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												  @RequestParam String loginUserID) {

		idmasterService.deleteSubItemGroupId(warehouseId, itemTypeId, itemGroupId, subItemGroupId,companyCodeId,
				languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = SubItemGroupId[].class, value = "Find SubItemGroupId")//label for swagger
	@PostMapping("/subitemgroupid/findSubItemGroupId")
	public SubItemGroupId[] findSubItemGroupId(@RequestBody FindSubItemGroupId findSubItemGroupId)throws Exception{
		return idmasterService.findSubItemGroupId(findSubItemGroupId,getAuthToken());
	}

	/*
	 * --------------------------------UomId---------------------------------
	 */
	@ApiOperation(response = UomId.class, value = "Get all UomId details") // label for swagger
	@GetMapping("/uomid")
	public ResponseEntity<?> getUomIds() {
		UomId[] uomIdList = idmasterService.getUomIds(getAuthToken());
		return new ResponseEntity<>(uomIdList, HttpStatus.OK);
	}

	@ApiOperation(response = UomId.class, value = "Get a UomId") // label for swagger
	@GetMapping("/uomid/{uomId}")
	public ResponseEntity<?> getUomId(@PathVariable String uomId, @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String warehouseId,@RequestParam String plantId) {
		UomId dbUomId = idmasterService.getUomId(uomId,companyCodeId,languageId,warehouseId,plantId,getAuthToken());
		log.info("UomId : " + dbUomId);
		return new ResponseEntity<>(dbUomId, HttpStatus.OK);
	}

	@ApiOperation(response = UomId.class, value = "Create UomId") // label for swagger
	@PostMapping("/uomid")
	public ResponseEntity<?> postUomId(@Valid @RequestBody AddUomId newUomId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		UomId createdUomId = idmasterService.createUomId(newUomId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdUomId, HttpStatus.OK);
	}

	@ApiOperation(response = UomId.class, value = "Update UomId") // label for swagger
	@RequestMapping(value = "/uomid/{uomId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchUomId(@PathVariable String uomId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String warehouseId,@RequestParam String plantId,@RequestParam String loginUserID,
										@Valid @RequestBody UpdateUomId updateUomId)
			throws IllegalAccessException, InvocationTargetException {
		UomId updatedUomId = idmasterService.updateUomId(uomId,companyCodeId,languageId,warehouseId,plantId,loginUserID,updateUomId,getAuthToken());
		return new ResponseEntity<>(updatedUomId, HttpStatus.OK);
	}

	@ApiOperation(response = UomId.class, value = "Delete UomId") // label for swagger
	@DeleteMapping("/uomid/{uomId}")
	public ResponseEntity<?> deleteUomId(@PathVariable String uomId,@RequestParam String companyCodeId,@RequestParam String languageId,
										 @RequestParam String warehouseId,@RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deleteUomId(uomId,companyCodeId,languageId,warehouseId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = UomId[].class, value = "Find UomId")//label for swagger
	@PostMapping("/uomid/findUomId")
	public UomId[] findUomId(@RequestBody FindUomId findUomId)throws Exception{
		return idmasterService.findUomId(findUomId,getAuthToken());
	}

	/*
	 * --------------------------------UserTypeId-----------------------------------------------------------------------
	 */
	@ApiOperation(response = UserTypeId.class, value = "Get all UserTypeId details") // label for swagger
	@GetMapping("/usertypeid")
	public ResponseEntity<?> getUserTypeIds() {
		UserTypeId[] userTypeIdList = idmasterService.getUserTypeIds(getAuthToken());
		return new ResponseEntity<>(userTypeIdList, HttpStatus.OK);
	}

	@ApiOperation(response = UserTypeId.class, value = "Get a UserTypeId") // label for swagger
	@GetMapping("/usertypeid/{userTypeId}")
	public ResponseEntity<?> getUserTypeId( @RequestParam String warehouseId, @PathVariable Long userTypeId,
											@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		UserTypeId dbUserTypeId = idmasterService.getUserTypeId(warehouseId, userTypeId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("UserTypeId : " + dbUserTypeId);
		return new ResponseEntity<>(dbUserTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = UserTypeId.class, value = "Create UserTypeId") // label for swagger
	@PostMapping("/usertypeid")
	public ResponseEntity<?> postUserTypeId(@Valid @RequestBody AddUserTypeId newUserTypeId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		UserTypeId createdUserTypeId = idmasterService.createUserTypeId(newUserTypeId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdUserTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = UserTypeId.class, value = "Update UserTypeId") // label for swagger
	@RequestMapping(value = "/usertypeid/{userTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchUserTypeId( @RequestParam String warehouseId,@PathVariable Long userTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
											  @RequestParam String loginUserID, @Valid @RequestBody UpdateUserTypeId updateUserTypeId)
			throws IllegalAccessException, InvocationTargetException {
		UserTypeId updatedUserTypeId =
				idmasterService.updateUserTypeId(warehouseId, userTypeId,companyCodeId,languageId,plantId,loginUserID, updateUserTypeId, getAuthToken());
		return new ResponseEntity<>(updatedUserTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = UserTypeId.class, value = "Delete UserTypeId") // label for swagger
	@DeleteMapping("/usertypeid/{userTypeId}")
	public ResponseEntity<?> deleteUserTypeId( @RequestParam String warehouseId,@PathVariable Long userTypeId,@RequestParam String companyCodeId,
											   @RequestParam String languageId,@RequestParam String plantId, @RequestParam String loginUserID) {
		idmasterService.deleteUserTypeId(warehouseId, userTypeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = UserTypeId[].class, value = "Find UserTypeId")//label for swagger
	@PostMapping("/usertypeid/findUserTypeId")
	public UserTypeId[] findUserTypeId(@RequestBody FindUserTypeId findUserTypeId)throws Exception{
		return idmasterService.findUserTypeId(findUserTypeId,getAuthToken());
	}


	/*
	 * --------------------------------VariantId---------------------------------
	 */
	@ApiOperation(response = VariantId.class, value = "Get all VariantId details") // label for swagger
	@GetMapping("/variantid")
	public ResponseEntity<?> getVariantIds() {
		VariantId[] variantCodeList = idmasterService.getVariantIds(getAuthToken());
		return new ResponseEntity<>(variantCodeList, HttpStatus.OK);
	}

	@ApiOperation(response = VariantId.class, value = "Get a VariantId") // label for swagger
	@GetMapping("/variantid/{variantCode}")
	public ResponseEntity<?> getVariantId( @RequestParam String warehouseId,@PathVariable String variantCode,@RequestParam String languageId,
										   @RequestParam String variantType, @RequestParam String variantSubCode,@RequestParam String companyCodeId,@RequestParam String plantId) {
		VariantId dbVariantId = idmasterService.getVariantId(warehouseId,variantCode,variantType,variantSubCode,companyCodeId,plantId,languageId,getAuthToken());
		log.info("VariantId : " + dbVariantId);
		return new ResponseEntity<>(dbVariantId, HttpStatus.OK);
	}

	@ApiOperation(response = VariantId.class, value = "Create VariantId") // label for swagger
	@PostMapping("/variantid")
	public ResponseEntity<?> postVariantId(@Valid @RequestBody AddVariantId newVariantId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		VariantId createdVariantId = idmasterService.createVariantId(newVariantId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdVariantId, HttpStatus.OK);
	}

	@ApiOperation(response = VariantId.class, value = "Update VariantId") // label for swagger
	@RequestMapping(value = "/variantid/{variantCode}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchVariantId(@RequestParam String warehouseId,@PathVariable String variantCode,@RequestParam String variantType, @RequestParam String variantSubCode,@RequestParam String languageId,@RequestParam String companyCodeId,@RequestParam String plantId,
											@RequestParam String loginUserID, @Valid @RequestBody UpdateVariantId updateVariantId)
			throws IllegalAccessException, InvocationTargetException {
		VariantId updatedVariantId =
				idmasterService.updateVariantId(warehouseId, variantCode, variantType, variantSubCode,companyCodeId,plantId,languageId,loginUserID,updateVariantId, getAuthToken());
		return new ResponseEntity<>(updatedVariantId, HttpStatus.OK);
	}

	@ApiOperation(response = VariantId.class, value = "Delete VariantId") // label for swagger
	@DeleteMapping("/variantid/{variantCode}")
	public ResponseEntity<?> deleteVariantId(@RequestParam String warehouseId,@PathVariable String variantCode, @RequestParam String variantType,
											 @RequestParam String variantSubCode,@RequestParam String companyCodeId,@RequestParam String plantId,@RequestParam String languageId,
											 @RequestParam String loginUserID) {
		idmasterService.deleteVariantId(warehouseId, variantCode, variantType, variantSubCode,companyCodeId,plantId,languageId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = VariantId[].class, value = "Find VariantId")//label for swagger
	@PostMapping("/variantid/findvariantid")
	public VariantId[] findVariantId(@RequestBody FindVariantId findVariantId)throws Exception{
		return idmasterService.findVariantId(findVariantId,getAuthToken());
	}

	/*
	 * --------------------------------WarehouseId---------------------------------
	 */
	@ApiOperation(response = WarehouseId.class, value = "Get all WarehouseId details") // label for swagger
	@GetMapping("/warehouseid")
	public ResponseEntity<?> getWarehouseIds() {
		WarehouseId[] warehouseIdList = idmasterService.getWarehouseIds(getAuthToken());
		return new ResponseEntity<>(warehouseIdList, HttpStatus.OK);
	}

	@ApiOperation(response = WarehouseId.class, value = "Get a WarehouseId") // label for swagger
	@GetMapping("/warehouseid/{warehouseId}")
	public ResponseEntity<?> getWarehouseId(@PathVariable String warehouseId,@RequestParam String plantId,@RequestParam String companyCodeId,@RequestParam String languageId) {
		WarehouseId dbWarehouseId = idmasterService.getWarehouseId(warehouseId,plantId,companyCodeId,languageId,getAuthToken());
		log.info("WarehouseId : " + dbWarehouseId);
		return new ResponseEntity<>(dbWarehouseId, HttpStatus.OK);
	}

	@ApiOperation(response = WarehouseId.class, value = "Create WarehouseId") // label for swagger
	@PostMapping("/warehouseid")
	public ResponseEntity<?> postWarehouseId(@Valid @RequestBody AddWarehouseId newWarehouseId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		WarehouseId createdWarehouseId = idmasterService.createWarehouseId(newWarehouseId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdWarehouseId, HttpStatus.OK);
	}

	@ApiOperation(response = WarehouseId.class, value = "Update WarehouseId") // label for swagger
	@RequestMapping(value = "/warehouseid/{warehouseId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchWarehouseId(@PathVariable String warehouseId,@RequestParam String plantId,
											  @RequestParam String companyCodeId,@RequestParam String languageId,
											  @RequestParam String loginUserID, @Valid @RequestBody UpdateWarehouseId updateWarehouseId)
			throws IllegalAccessException, InvocationTargetException {
		WarehouseId updatedWarehouseId =
				idmasterService.updateWarehouseId(warehouseId,plantId,companyCodeId,languageId, loginUserID, updateWarehouseId, getAuthToken());
		return new ResponseEntity<>(updatedWarehouseId, HttpStatus.OK);
	}

	@ApiOperation(response = WarehouseId.class, value = "Delete WarehouseId") // label for swagger
	@DeleteMapping("/warehouseid/{warehouseId}")
	public ResponseEntity<?> deleteWarehouseId(@PathVariable String warehouseId,@RequestParam String plantId,
											   @RequestParam String companyCodeId,@RequestParam String languageId,
											   @RequestParam String loginUserID) {
		idmasterService.deleteWarehouseId(warehouseId,plantId,companyCodeId,languageId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = WarehouseId[].class, value = "Find WarehouseId")//label for swagger
	@PostMapping("/warehouseid/findWarehouseId")
	public WarehouseId[] findWarehouseId(@RequestBody FindWarehouseId findWarehouseId)throws Exception{
		return idmasterService.findWarehouseId(findWarehouseId,getAuthToken());
	}

	/*
	 * --------------------------------WarehouseTypeId---------------------------------
	 */
	@ApiOperation(response = WarehouseTypeId.class, value = "Get all WarehouseTypeId details") // label for swagger
	@GetMapping("/warehousetypeid")
	public ResponseEntity<?> getWarehouseTypeIds() {
		WarehouseTypeId[] warehouseTypeIdList = idmasterService.getWarehouseTypeIds(getAuthToken());
		return new ResponseEntity<>(warehouseTypeIdList, HttpStatus.OK);
	}

	@ApiOperation(response = WarehouseTypeId.class, value = "Get a WarehouseTypeId") // label for swagger
	@GetMapping("/warehousetypeid/{warehouseTypeId}")
	public ResponseEntity<?> getWarehouseTypeId(@RequestParam String warehouseId,@PathVariable Long warehouseTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		WarehouseTypeId dbWarehouseTypeId = idmasterService.getWarehouseTypeId(warehouseId, warehouseTypeId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("WarehouseTypeId : " + dbWarehouseTypeId);
		return new ResponseEntity<>(dbWarehouseTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = WarehouseTypeId.class, value = "Create WarehouseTypeId") // label for swagger
	@PostMapping("/warehousetypeid")
	public ResponseEntity<?> postWarehouseTypeId(@Valid @RequestBody AddWarehouseTypeId newWarehouseTypeId, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		WarehouseTypeId createdWarehouseTypeId = idmasterService.createWarehouseTypeId(newWarehouseTypeId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdWarehouseTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = WarehouseTypeId.class, value = "Update WarehouseTypeId") // label for swagger
	@RequestMapping(value = "/warehousetypeid/{warehouseTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchWarehouseTypeId(@RequestParam String warehouseId,@PathVariable Long warehouseTypeId, @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												  @RequestParam String loginUserID, @Valid @RequestBody UpdateWarehouseTypeId updateWarehouseTypeId)
			throws IllegalAccessException, InvocationTargetException {
		WarehouseTypeId updatedWarehouseTypeId =
				idmasterService.updateWarehouseTypeId(warehouseId, warehouseTypeId,companyCodeId,languageId,plantId,loginUserID,updateWarehouseTypeId, getAuthToken());
		return new ResponseEntity<>(updatedWarehouseTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = WarehouseTypeId.class, value = "Delete WarehouseTypeId") // label for swagger
	@DeleteMapping("/warehousetypeid/{warehouseTypeId}")
	public ResponseEntity<?> deleteWarehouseTypeId( @RequestParam String warehouseId,@PathVariable Long warehouseTypeId,
													@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
													@RequestParam String loginUserID) {
		idmasterService.deleteWarehouseTypeId(warehouseId, warehouseTypeId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = WarehouseTypeId[].class, value = "Find WarehouseTypeId")//label for swagger
	@PostMapping("/warehousetypeid/findWarehouseTypeId")
	public WarehouseTypeId[] findWarehouseTypeId(@RequestBody FindWarehouseTypeId findWarehouseTypeId)throws Exception{
		return idmasterService.findWarehouseTypeId(findWarehouseTypeId,getAuthToken());
	}


	/*
	 * --------------------------------HhtUser---------------------------------
	 */
	@ApiOperation(response = HhtUserOutput.class, value = "Get all HhtUser details") // label for swagger
	@GetMapping("/hhtuser")
	public ResponseEntity<?> getHhtUsers() {
		HhtUserOutput[] userIdList = idmasterService.getHhtUsers(getAuthToken());
		return new ResponseEntity<>(userIdList, HttpStatus.OK);
	}

	@ApiOperation(response = HhtUserOutput.class, value = "Get a HhtUser") // label for swagger
	@GetMapping("/hhtuser/{userId}")
	public ResponseEntity<?> getHhtUser(@PathVariable String userId, @RequestParam String companyCodeId,
										@RequestParam String languageId, @RequestParam String plantId, @RequestParam String warehouseId) {

		HhtUserOutput dbHhtUser = idmasterService.getHhtUser(userId, warehouseId, companyCodeId, plantId, languageId, getAuthToken());
		log.info("HhtUser : " + dbHhtUser);
		return new ResponseEntity<>(dbHhtUser, HttpStatus.OK);
	}


	@ApiOperation(response = HhtUserOutput.class, value = "Get HhtUsers") // label for swagger
	@GetMapping("/hhtuser/{warehouseId}/hhtUser")
	public ResponseEntity<?> getHhtUser(@PathVariable String warehouseId) {
		HhtUserOutput[] hhtuser = idmasterService.getHhtUserByWarehouseId(warehouseId, getAuthToken());
		log.info("HhtUser : " + hhtuser);
		return new ResponseEntity<>(hhtuser, HttpStatus.OK);
	}

	@ApiOperation(response = HhtUser.class, value = "Create HhtUser") // label for swagger
	@PostMapping("/hhtuser")
	public ResponseEntity<?> postHhtUser(@Valid @RequestBody AddHhtUser newHhtUser, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		HhtUser createdHhtUser = idmasterService.createHhtUser(newHhtUser, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdHhtUser, HttpStatus.OK);
	}

	@ApiOperation(response = HhtUser.class, value = "Update HhtUser") // label for swagger
	@RequestMapping(value = "/hhtuser/{userId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchHhtUser(@PathVariable String userId, @RequestParam String warehouseId, @RequestParam String companyCodeId,
										  @RequestParam String languageId, @RequestParam String plantId,
										  @RequestParam String loginUserID,
										  @Valid @RequestBody UpdateHhtUser updateHhtUser) throws IllegalAccessException, InvocationTargetException {

		HhtUser updatedHhtUser = idmasterService.updateHhtUser(userId, warehouseId, companyCodeId, languageId, plantId,
				updateHhtUser, loginUserID, getAuthToken());
		return new ResponseEntity<>(updatedHhtUser, HttpStatus.OK);
	}

	@ApiOperation(response = HhtUser.class, value = "Delete HhtUser") // label for swagger
	@DeleteMapping("/hhtuser/{userId}")
	public ResponseEntity<?> deleteHhtUser(@PathVariable String userId, @RequestParam String warehouseId, @RequestParam String companyCodeId,
										   @RequestParam String plantId, @RequestParam String languageId,
										   @RequestParam String loginUserID) {

		idmasterService.deleteHhtUser(warehouseId, userId, companyCodeId, languageId, plantId, loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = HhtUserOutput[].class, value = "Find HhtUser")//label for swagger
	@PostMapping("/hhtuser/findHhtUser")
	public HhtUserOutput[] findHhtUser(@RequestBody FindHhtUser findHhtUser)throws Exception{
		return idmasterService.findHhtUser(findHhtUser,getAuthToken());
	}

	/*
	 * --------------------------------RoleAccess---------------------------------
	 */
	@ApiOperation(response = RoleAccess.class, value = "Get all RoleAccess details") // label for swagger
	@GetMapping("/roleaccess")
	public ResponseEntity<?> getRoleAccesss() {
		RoleAccess[] userRoleIdList = idmasterService.getRoleAccesss(getAuthToken());
		return new ResponseEntity<>(userRoleIdList, HttpStatus.OK);
	}

	@ApiOperation(response = RoleAccess.class, value = "Get a RoleAccess") // label for swagger
	@GetMapping("/roleaccess/{roleId}")
	public ResponseEntity<?> getRoleAccess(@PathVariable Long roleId, @RequestParam String companyCodeId,
										   @RequestParam String languageId, @RequestParam String plantId,
										   @RequestParam String warehouseId) {
		RoleAccess[] dbRoleAccess = idmasterService.getRoleAccess(warehouseId, roleId,companyCodeId,plantId,languageId,getAuthToken());
		log.info("RoleAccess : " + dbRoleAccess);
		return new ResponseEntity<>(dbRoleAccess, HttpStatus.OK);
	}

	@ApiOperation(response = RoleAccess[].class, value = "Create RoleAccess") // label for swagger
	@PostMapping("/roleaccess")
	public ResponseEntity<?> postRoleAccess(@Valid @RequestBody List<AddRoleAccess> newRoleAccess,
											@RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		RoleAccess[] createdRoleAccess = idmasterService.createRoleAccess(newRoleAccess, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdRoleAccess, HttpStatus.OK);
	}

	@ApiOperation(response = RoleAccess[].class, value = "Update RoleAccess") // label for swagger
	@RequestMapping(value = "/roleaccess/{roleId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchRoleAccess(@PathVariable Long roleId, @RequestParam String companyCodeId,
											 @RequestParam String languageId, @RequestParam String plantId,
											 @RequestParam String warehouseId, @RequestParam String loginUserID,
											 @Valid @RequestBody List<AddRoleAccess> updateRoleAccess)
			throws IllegalAccessException, InvocationTargetException {
		RoleAccess[] updatedRoleAccess =
				idmasterService.updateRoleAccess(warehouseId, roleId,companyCodeId,languageId,
						plantId,loginUserID,updateRoleAccess, getAuthToken());
		return new ResponseEntity<>(updatedRoleAccess, HttpStatus.OK);
	}

	@ApiOperation(response = RoleAccess.class, value = "Delete RoleAccess") // label for swagger
	@DeleteMapping("/roleaccess/{roleId}")
	public ResponseEntity<?> deleteRoleAccess(@PathVariable Long roleId, @RequestParam String warehouseId,
											  @RequestParam String companyCodeId, @RequestParam String languageId,
											  @RequestParam String plantId, @RequestParam String loginUserID) {
		idmasterService.deleteRoleAccess(warehouseId, roleId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = RoleAccess[].class, value = "Find RoleAccess")//label for swagger
	@PostMapping("/roleaccess/findRoleAccess")
	public RoleAccess[] findRoleAccess(@RequestBody FindRoleId findRoleId)throws Exception{
		return idmasterService.findRoleAccess(findRoleId,getAuthToken());
	}

	// --------------------------------DoorId---------------------------------

	@ApiOperation(response = DoorId.class, value = "Get all DoorId details") // label for swagger
	@GetMapping("/doorid")
	public ResponseEntity<?> getDoorIds() {
		DoorId[] doorIdList = idmasterService.getDoorIds(getAuthToken());
		return new ResponseEntity<>(doorIdList, HttpStatus.OK);
	}

	@ApiOperation(response = DoorId.class, value = "Get a DoorId") // label for swagger
	@GetMapping("/doorid/{doorId}")
	public ResponseEntity<?> getDoorId(@RequestParam String warehouseId,@PathVariable String doorId,@RequestParam String companyCodeId,
									   @RequestParam String languageId,@RequestParam String plantId) {
		DoorId dbDoorId = idmasterService.getDoorId(warehouseId, doorId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("DoorId : " + dbDoorId);
		return new ResponseEntity<>(dbDoorId, HttpStatus.OK);
	}

	@ApiOperation(response = DoorId.class, value = "Create DoorId") // label for swagger
	@PostMapping("/doorid")
	public ResponseEntity<?> postDoorId(@Valid @RequestBody AddDoorId newDoorId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		DoorId createdDoorId = idmasterService.createDoorId(newDoorId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdDoorId, HttpStatus.OK);
	}

	@ApiOperation(response = DoorId.class, value = "Update DoorId") // label for swagger
	@RequestMapping(value = "/doorid/{doorId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchDoorId( @RequestParam String warehouseId,@PathVariable String doorId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
										  @RequestParam String loginUserID, @Valid @RequestBody UpdateDoorId updateDoorId)
			throws IllegalAccessException, InvocationTargetException {
		DoorId updatedDoorId =
				idmasterService.updateDoorId(warehouseId, doorId,companyCodeId,languageId,plantId,loginUserID,updateDoorId,getAuthToken());
		return new ResponseEntity<>(updatedDoorId, HttpStatus.OK);
	}

	@ApiOperation(response = DoorId.class, value = "Delete DoorId") // label for swagger
	@DeleteMapping("/doorid/{doorId}")
	public ResponseEntity<?> deleteDoorId(@RequestParam String warehouseId,@PathVariable String doorId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
										  @RequestParam String loginUserID) {
		idmasterService.deleteDoorId(warehouseId, doorId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = DoorId[].class, value = "Find DoorId")//label for swagger
	@PostMapping("/doorid/findDoorId")
	public DoorId[] findDoorId(@RequestBody FindDoorId findDoorId)throws Exception{
		return idmasterService.findDoorId(findDoorId,getAuthToken());
	}

	// --------------------------------ModuleId---------------------------------

	@ApiOperation(response = ModuleId.class, value = "Get all ModuleId details") // label for swagger
	@GetMapping("/moduleid")
	public ResponseEntity<?> getModuleIds() {
		ModuleId[] moduleIdList = idmasterService.getModuleIds(getAuthToken());
		return new ResponseEntity<>(moduleIdList, HttpStatus.OK);
	}

	@ApiOperation(response = ModuleId.class, value = "Get a ModuleId") // label for swagger
	@GetMapping("/moduleid/{moduleId}")
	public ResponseEntity<?> getModuleId( @RequestParam String warehouseId,@PathVariable String moduleId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		ModuleId[] dbModuleId = idmasterService.getModuleId(warehouseId, moduleId,companyCodeId,languageId,plantId, getAuthToken());
		log.info("ModuleId : " + dbModuleId);
		return new ResponseEntity<>(dbModuleId, HttpStatus.OK);
	}

	@ApiOperation(response = ModuleId.class, value = "Create ModuleId") // label for swagger
	@PostMapping("/moduleid")
	public ResponseEntity<?> postModuleId(@Valid @RequestBody List<AddModuleId> newModuleId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		ModuleId[] createdModuleId = idmasterService.createModuleId(newModuleId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdModuleId, HttpStatus.OK);
	}

	@ApiOperation(response = ModuleId.class, value = "Update ModuleId") // label for swagger
	@RequestMapping(value = "/moduleid/{moduleId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchModuleId(@RequestParam String warehouseId,@PathVariable String moduleId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
										   @RequestParam String loginUserID, @Valid @RequestBody List<UpdateModuleId> updateModuleId)
			throws IllegalAccessException, InvocationTargetException {
		ModuleId[] updatedModuleId =
				idmasterService.updateModuleId(warehouseId, moduleId,companyCodeId,languageId,plantId,loginUserID,updateModuleId,getAuthToken());
		return new ResponseEntity<>(updatedModuleId, HttpStatus.OK);
	}

	@ApiOperation(response = ModuleId.class, value = "Delete ModuleId") // label for swagger
	@DeleteMapping("/moduleid/{moduleId}")
	public ResponseEntity<?> deleteModuleId( @RequestParam String warehouseId,@PathVariable String moduleId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
											 @RequestParam String loginUserID) {
		idmasterService.deleteModuleId(warehouseId, moduleId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = ModuleId[].class, value = "Find ModuleId")//label for swagger
	@PostMapping("/moduleid/findModuleId")
	public ModuleId[] findModuleId(@RequestBody FindModuleId findModuleId)throws Exception{
		return idmasterService.findModuleId(findModuleId,getAuthToken());
	}

	// --------------------------------AdhocModuleId---------------------------------

	@ApiOperation(response = AdhocModuleId.class, value = "Get all AdhocModuleId details") // label for swagger
	@GetMapping("/adhocmoduleid")
	public ResponseEntity<?> getAdhocModuleIds() {
		AdhocModuleId[] adhocModuleIdList = idmasterService.getAdhocModuleIds(getAuthToken());
		return new ResponseEntity<>(adhocModuleIdList, HttpStatus.OK);
	}

	@ApiOperation(response = AdhocModuleId.class, value = "Get a AdhocModuleId") // label for swagger
	@GetMapping("/adhocmoduleid/{adhocModuleId}")
	public ResponseEntity<?> getAdhocModuleId(@RequestParam String warehouseId,@PathVariable String adhocModuleId,
											  @RequestParam String moduleId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		AdhocModuleId dbAdhocModuleId = idmasterService.getAdhocModuleId(warehouseId, adhocModuleId, moduleId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("AdhocModuleId : " + dbAdhocModuleId);
		return new ResponseEntity<>(dbAdhocModuleId, HttpStatus.OK);
	}

	@ApiOperation(response = AdhocModuleId.class, value = "Create AdhocModuleId") // label for swagger
	@PostMapping("/adhocmoduleid")
	public ResponseEntity<?> postAdhocModuleId(@Valid @RequestBody AddAdhocModuleId newAdhocModuleId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		AdhocModuleId createdAdhocModuleId = idmasterService.createAdhocModuleId(newAdhocModuleId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdAdhocModuleId, HttpStatus.OK);
	}

	@ApiOperation(response = AdhocModuleId.class, value = "Update AdhocModuleId") // label for swagger
	@RequestMapping(value = "/adhocmoduleid/{adhocModuleId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchAdhocModuleId(@RequestParam String warehouseId,@PathVariable String adhocModuleId, @RequestParam String moduleId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												@RequestParam String loginUserID, @Valid @RequestBody UpdateAdhocModuleId updateAdhocModuleId)
			throws IllegalAccessException, InvocationTargetException {
		AdhocModuleId updatedAdhocModuleId =
				idmasterService.updateAdhocModuleId(warehouseId, adhocModuleId, moduleId,companyCodeId,languageId,plantId,loginUserID,updateAdhocModuleId, getAuthToken());
		return new ResponseEntity<>(updatedAdhocModuleId, HttpStatus.OK);
	}

	@ApiOperation(response = AdhocModuleId.class, value = "Delete AdhocModuleId") // label for swagger
	@DeleteMapping("/adhocmoduleid/{adhocModuleId}")
	public ResponseEntity<?> deleteAdhocModuleId(@RequestParam String warehouseId,@PathVariable String adhocModuleId,
												 @RequestParam String moduleId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												 @RequestParam String loginUserID) {
		idmasterService.deleteAdhocModuleId(warehouseId, adhocModuleId, moduleId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = AdhocModuleId[].class, value = "Find AdhocModuleId")//label for swagger
	@PostMapping("/adhocmoduleid/findAdhocModuleId")
	public AdhocModuleId[] findAdhocModuleId(@RequestBody FindAdhocModuleId findAdhocModuleId)throws Exception{
		return idmasterService.findAdhocModuleId(findAdhocModuleId,getAuthToken());
	}

	// --------------------------------PalletizationLevelId---------------------------------

	@ApiOperation(response = PalletizationLevelId.class, value = "Get all PalletizationLevelId details") // label for swagger
	@GetMapping("/palletizationlevelid")
	public ResponseEntity<?> getPalletizationLevelIds() {
		PalletizationLevelId[] palletizationLevelIdList = idmasterService.getPalletizationLevelIds(getAuthToken());
		return new ResponseEntity<>(palletizationLevelIdList, HttpStatus.OK);
	}

	@ApiOperation(response = PalletizationLevelId.class, value = "Get a PalletizationLevelId") // label for swagger
	@GetMapping("/palletizationlevelid/{palletizationLevelId}")
	public ResponseEntity<?> getPalletizationLevelId( @RequestParam String warehouseId,@PathVariable String palletizationLevelId,@RequestParam String palletizationLevel,
													  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		PalletizationLevelId dbPalletizationLevelId = idmasterService.getPalletizationLevelId(warehouseId, palletizationLevelId,palletizationLevel,companyCodeId,languageId,plantId,getAuthToken());
		log.info("PalletizationLevelId : " + dbPalletizationLevelId);
		return new ResponseEntity<>(dbPalletizationLevelId, HttpStatus.OK);
	}

	@ApiOperation(response = PalletizationLevelId.class, value = "Create PalletizationLevelId") // label for swagger
	@PostMapping("/palletizationlevelid")
	public ResponseEntity<?> postPalletizationLevelId(@Valid @RequestBody AddPalletizationLevelId newPalletizationLevelId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		PalletizationLevelId createdPalletizationLevelId = idmasterService.createPalletizationLevelId(newPalletizationLevelId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdPalletizationLevelId, HttpStatus.OK);
	}

	@ApiOperation(response = PalletizationLevelId.class, value = "Update PalletizationLevelId") // label for swagger
	@RequestMapping(value = "/palletizationlevelid/{palletizationLevelId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchPalletizationLevelId(@RequestParam String warehouseId,@PathVariable String palletizationLevelId, @RequestParam String palletizationLevel,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
													   @RequestParam String loginUserID, @Valid @RequestBody UpdatePalletizationLevelId updatePalletizationLevelId)
			throws IllegalAccessException, InvocationTargetException {
		PalletizationLevelId updatedPalletizationLevelId =
				idmasterService.updatePalletizationLevelId(warehouseId, palletizationLevelId, palletizationLevel,companyCodeId,languageId,plantId,loginUserID,updatePalletizationLevelId,getAuthToken());
		return new ResponseEntity<>(updatedPalletizationLevelId, HttpStatus.OK);
	}

	@ApiOperation(response = PalletizationLevelId.class, value = "Delete PalletizationLevelId") // label for swagger
	@DeleteMapping("/palletizationlevelid/{palletizationLevelId}")
	public ResponseEntity<?> deletePalletizationLevelId( @RequestParam String warehouseId,@PathVariable String palletizationLevelId,@RequestParam String palletizationLevel,
														 @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deletePalletizationLevelId(warehouseId, palletizationLevelId, palletizationLevel,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = PalletizationLevelId[].class, value = "Find PalletizationLevelId")//label for swagger
	@PostMapping("/palletizationlevelid/findPalletizationLevelId")
	public PalletizationLevelId[] findPalletizationLevelId(@RequestBody FindPalletizationLevelId findPalletizationLevelId)throws Exception{
		return idmasterService.findPalletizationLevelId(findPalletizationLevelId,getAuthToken());
	}

	// --------------------------------EmployeeId---------------------------------

	@ApiOperation(response = EmployeeId.class, value = "Get all EmployeeId details") // label for swagger
	@GetMapping("/employeeid")
	public ResponseEntity<?> getEmployeeIds() {
		EmployeeId[] employeeIdList = idmasterService.getEmployeeIds(getAuthToken());
		return new ResponseEntity<>(employeeIdList, HttpStatus.OK);
	}

	@ApiOperation(response = EmployeeId.class, value = "Get a EmployeeId") // label for swagger
	@GetMapping("/employeeid/{employeeId}")
	public ResponseEntity<?> getEmployeeId(@RequestParam String warehouseId,@PathVariable String employeeId,
										   @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		EmployeeId dbEmployeeId = idmasterService.getEmployeeId(warehouseId, employeeId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("EmployeeId : " + dbEmployeeId);
		return new ResponseEntity<>(dbEmployeeId, HttpStatus.OK);
	}

	@ApiOperation(response = EmployeeId.class, value = "Create EmployeeId") // label for swagger
	@PostMapping("/employeeid")
	public ResponseEntity<?> postEmployeeId(@Valid @RequestBody AddEmployeeId newEmployeeId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		EmployeeId createdEmployeeId = idmasterService.createEmployeeId(newEmployeeId, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdEmployeeId, HttpStatus.OK);
	}

	@ApiOperation(response = EmployeeId.class, value = "Update EmployeeId") // label for swagger
	@RequestMapping(value = "/employeeid/{employeeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchEmployeeId(@RequestParam String warehouseId,@PathVariable String employeeId,
											 @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
											 @RequestParam String loginUserID,@Valid @RequestBody UpdateEmployeeId updateEmployeeId)
			throws IllegalAccessException, InvocationTargetException {
		EmployeeId updatedEmployeeId =
				idmasterService.updateEmployeeId(warehouseId, employeeId,companyCodeId,languageId,plantId,loginUserID,updateEmployeeId, getAuthToken());
		return new ResponseEntity<>(updatedEmployeeId, HttpStatus.OK);
	}

	@ApiOperation(response = EmployeeId.class, value = "Delete EmployeeId") // label for swagger
	@DeleteMapping("/employeeid/{employeeId}")
	public ResponseEntity<?> deleteEmployeeId(@RequestParam String warehouseId,@PathVariable String employeeId,
											  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
											  @RequestParam String loginUserID) {
		idmasterService.deleteEmployeeId(warehouseId, employeeId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = EmployeeId[].class, value = "Find EmployeeId")//label for swagger
	@PostMapping("/employeeid/findEmployeeId")
	public EmployeeId[] findEmployeeId(@RequestBody FindEmployeeId findEmployeeId)throws Exception{
		return idmasterService.findEmployeeId(findEmployeeId,getAuthToken());
	}

	/*
	 * --------------------------------BinClassId---------------------------------
	 */
	@ApiOperation(response = BinClassId[].class, value = "Get all BinClassId details") // label for swagger
	@GetMapping("/binclassid")
	public ResponseEntity<?> getBinClassIds() {
		BinClassId[] userBinClassId = idmasterService.getBinClassIds(getAuthToken());
		return new ResponseEntity<>(userBinClassId, HttpStatus.OK);
	}

	@ApiOperation(response = BinClassId.class, value = "Get a BinClassId") // label for swagger
	@GetMapping("/binclassid/{binClassId}")
	public ResponseEntity<?> getBinClassId(@RequestParam String warehouseId,
										   @PathVariable Long binClassId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		BinClassId dbBinClassId = idmasterService.getBinClassId(warehouseId,binClassId,companyCodeId,languageId,plantId,getAuthToken());
		//log.info("BinClassId : " +dbBinClassId);
		return new ResponseEntity<>(dbBinClassId, HttpStatus.OK);
	}

	@ApiOperation(response = BinClassId.class, value = "Create BinClassId") // label for swagger
	@PostMapping("/binclassid")
	public ResponseEntity<?> PostBinClassId(@Valid @RequestBody AddBinClassId newBinClassId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		BinClassId createdBinClassId = idmasterService.createBinClassId(newBinClassId,loginUserID, getAuthToken());
		return new ResponseEntity<>(createdBinClassId, HttpStatus.OK);
	}

	@ApiOperation(response = BinClassId.class, value = "Update BinClassId") // label for swagger
	@RequestMapping(value = "/binclassid/{binClassId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateBinClassId(@RequestParam String warehouseId,@PathVariable Long binClassId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
											 @RequestParam String loginUserID,
											 @Valid @RequestBody UpdateBinClassId updateBinClassId) throws IllegalAccessException, InvocationTargetException {
		BinClassId updatedBinClassId =
				idmasterService.updateBinclassId(warehouseId,binClassId,companyCodeId,languageId,plantId,loginUserID,updateBinClassId,getAuthToken());
		return new ResponseEntity<>(updatedBinClassId, HttpStatus.OK);
	}

	@ApiOperation(response = BinClassId.class, value = "Delete BinClassId") // label for swagger
	@DeleteMapping("/binclassid/{binClassId}")
	public ResponseEntity<?> deleteBinClassId(@RequestParam String warehouseId,@PathVariable Long binClassId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
											  @RequestParam String loginUserID) {
		idmasterService.deleteBinClassId(warehouseId, binClassId,companyCodeId,languageId,plantId,loginUserID, getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = BinClassId[].class, value = "Find BinClassId")//label for swagger
	@PostMapping("/binclassid/findBinClassId")
	public BinClassId[] findBinClassId(@RequestBody FindBinClassId findBinClassId)throws Exception{
		return idmasterService.findBinClassId(findBinClassId,getAuthToken());
	}

	/*
	 * --------------------------------DockId---------------------------------
	 */
	@ApiOperation(response = DockId[].class, value = "Get all DockId details") // label for swagger
	@GetMapping("/dockid")
	public ResponseEntity<?> getDockIds() {
		DockId[] userDockId = idmasterService.getDockIds(getAuthToken());
		return new ResponseEntity<>(userDockId, HttpStatus.OK);
	}

	@ApiOperation(response = DockId.class, value = "Get a DockId") // label for swagger
	@GetMapping("/dockid/{dockId}")
	public ResponseEntity<?>  getDockId(@RequestParam String warehouseId,
										@PathVariable String dockId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		DockId dbDockId = idmasterService.getDockId(warehouseId,dockId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbDockId, HttpStatus.OK);
	}

	@ApiOperation(response = DockId.class, value = "Create DockId") // label for swagger
	@PostMapping("/dockid")
	public ResponseEntity<?> PostDockId(@Valid @RequestBody AddDockId newDockId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		DockId createDockId = idmasterService.createDockId(newDockId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createDockId, HttpStatus.OK);
	}

	@ApiOperation(response = DockId.class, value = "Update DockId") // label for swagger
	@RequestMapping(value = "/dockid/{dockId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateDockId(@RequestParam String warehouseId,@PathVariable String dockId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
										 @RequestParam String loginUserID, @Valid @RequestBody UpdateDockId updateDockId) throws IllegalAccessException, InvocationTargetException {
		DockId updateDockIdId = idmasterService.updateDockId(warehouseId,dockId,companyCodeId,languageId,plantId,loginUserID,updateDockId,getAuthToken());
		return new ResponseEntity<>(updateDockIdId, HttpStatus.OK);
	}

	@ApiOperation(response = DockId.class, value = "Delete DockId") // label for swagger
	@DeleteMapping("/dockid/{dockId}")
	public ResponseEntity<?> deleteDockId(@RequestParam String warehouseId,@PathVariable String dockId, @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
										  @RequestParam String loginUserID) {
		idmasterService.deleteDockId(warehouseId,dockId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = DockId[].class, value = "Find DockId")//label for swagger
	@PostMapping("/dockid/findDockId")
	public DockId[] findDockId(@RequestBody FindDockId findDockId)throws Exception{
		return idmasterService.findDockId(findDockId,getAuthToken());
	}

	/*
	 * --------------------------------WorkCenterId---------------------------------
	 */
	@ApiOperation(response = WorkCenterId[].class, value = "Get all WorkCenterId details") // label for swagger
	@GetMapping("/workcenterid")
	public ResponseEntity<?> getworkCenterIds() {
		WorkCenterId[] userWorkCenterId = idmasterService.getworkCenterIds(getAuthToken());
		return new ResponseEntity<>(userWorkCenterId, HttpStatus.OK);
	}

	@ApiOperation(response = WorkCenterId.class, value = "Get a WorkCenterId") // label for swagger
	@GetMapping("/workcenterid/{workCenterId}")
	public ResponseEntity<?>  getWorkCenterId(@RequestParam String warehouseId,@PathVariable String workCenterId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		WorkCenterId dbworkCenterId = idmasterService.getworkCenterId(warehouseId,workCenterId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbworkCenterId, HttpStatus.OK);
	}

	@ApiOperation(response = WorkCenterId.class, value = "Create WorkCenterId") // label for swagger
	@PostMapping("/workcenterid")
	public ResponseEntity<?> PostWorkCenter(@Valid @RequestBody AddWorkCenterId newWorkCenterId,
											@RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		WorkCenterId createWorkCenterId = idmasterService.createWorkCenterId(newWorkCenterId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createWorkCenterId, HttpStatus.OK);
	}

	@ApiOperation(response = WorkCenterId.class, value = "Update WorkCenterId") // label for swagger
	@RequestMapping(value = "/workcenterid/{workCenterId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateWorkCenterId(@RequestParam String warehouseId,
											   @PathVariable String  workCenterId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
											   @RequestParam String loginUserID,
											   @Valid @RequestBody UpdateWorkCenterId updateWorkCenterId) throws IllegalAccessException, InvocationTargetException {
		WorkCenterId updateworkCenterId =
				idmasterService.updateWorkCenterId(warehouseId,workCenterId,companyCodeId,languageId,plantId,loginUserID,updateWorkCenterId,getAuthToken());
		return new ResponseEntity<>(updateworkCenterId, HttpStatus.OK);
	}

	@ApiOperation(response = WorkCenterId.class, value = "Delete WorkCenterId") // label for swagger
	@DeleteMapping("/workcenterid/{workCenterId}")
	public ResponseEntity<?> workCenterId(@RequestParam String warehouseId,@PathVariable String workCenterId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
										  @RequestParam String loginUserID) {
		idmasterService.deleteWorkCenterId(warehouseId,workCenterId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = WorkCenterId[].class, value = "Find WorkCenterId")//label for swagger
	@PostMapping("/workcenterid/findWorkCenterId")
	public WorkCenterId[] findWorkCenterId(@RequestBody FindWorkCenterId findWorkCenterId)throws Exception{
		return idmasterService.findWorkCenterId(findWorkCenterId,getAuthToken());
	}

	/*
	 * --------------------------------OutboundOrderStatusId---------------------------------
	 */

	@ApiOperation(response = OutboundOrderStatusId[].class, value = "Get all OutboundOrderStatusId details") // label for swagger
	@GetMapping("/outboundorderstatusid")
	public ResponseEntity<?> getoutboundOrderStatusIds() {
		OutboundOrderStatusId[] userOutboundorderstatusId = idmasterService.getoutBoundOrderStatusIds(getAuthToken());
		return new ResponseEntity<>(userOutboundorderstatusId, HttpStatus.OK);
	}

	@ApiOperation(response = OutboundOrderStatusId.class, value = "Get a OutboundOrderStatusId") // label for swagger
	@GetMapping("/outboundorderstatusid/{outboundOrderStatusId}")
	public ResponseEntity<?>  getOutboundOrderStatusId(@RequestParam String warehouseId,
													   @PathVariable String outboundOrderStatusId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		OutboundOrderStatusId dbOutboundOrderStatusId = idmasterService.getoutBoundOrderStatusId(warehouseId,outboundOrderStatusId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbOutboundOrderStatusId, HttpStatus.OK);
	}

	@ApiOperation(response = OutboundOrderStatusId.class, value = "Create OutboundsOrderStatusId") // label for swagger
	@PostMapping("/outboundorderstatusid")
	public ResponseEntity<?> PostoutbountOrderStatusId(@Valid @RequestBody AddOutboundOrderStatusId newoutboundOrderStatusId,
													   @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		OutboundOrderStatusId createOutboundOrderStatusId = idmasterService.createoutBoundOrderStatusId(newoutboundOrderStatusId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createOutboundOrderStatusId, HttpStatus.OK);
	}

	@ApiOperation(response = OutboundOrderStatusId.class, value = "Update OutboundOrderStatusId") // label for swagger
	@RequestMapping(value = "/outboundorderstatusid/{outboundOrderStatusId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateoutboundOrderStatusId(@RequestParam String warehouseId,@PathVariable String outboundOrderStatusId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
														@RequestParam String loginUserID, @Valid @RequestBody UpdateOutboundOrderStatusId updateOutboundOrderStatusId)
			throws IllegalAccessException, InvocationTargetException {
		OutboundOrderStatusId updateoutboundOrderStatusId =
				idmasterService.updateOutboundOrderStatusId(warehouseId,outboundOrderStatusId,companyCodeId,languageId,plantId,loginUserID,updateOutboundOrderStatusId,getAuthToken());
		return new ResponseEntity<>(updateoutboundOrderStatusId, HttpStatus.OK);
	}

	@ApiOperation(response = OutboundOrderStatusId.class, value = "Delete OutBoundOrderStatusId") // label for swagger
	@DeleteMapping("/outboundorderstatusid/{outBoundOrderStatusId}")
	public ResponseEntity<?> deleteoutBoundOrderStatusId(@RequestParam String warehouseId,@PathVariable String outBoundOrderStatusId,
														 @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
														 @RequestParam String loginUserID) {
		idmasterService.deleteoutBoundOrderStatusId(warehouseId,outBoundOrderStatusId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = OutboundOrderStatusId[].class, value = "Find OutboundOrderStatusId")//label for swagger
	@PostMapping("/outboundorderstatusid/findOutboundOrderStatusId")
	public OutboundOrderStatusId[] findOutboundOrderStatusId(@RequestBody FindOutboundOrderStatusId findOutboundOrderStatusId)throws Exception{
		return idmasterService.findOutboundOrderStatusId(findOutboundOrderStatusId,getAuthToken());
	}
	/*
	 * --------------------------------InboundOrderStatusId---------------------------------
	 */

	@ApiOperation(response = InboundOrderStatusId[].class, value = "Get all InboundOrderStatusId details") // label for swagger
	@GetMapping("/inboundorderstatusid")
	public ResponseEntity<?> getinboundOrderStatusIds() {
		InboundOrderStatusId[] userinboundorderstatusId = idmasterService.getInboundOrderStatusIds(getAuthToken());
		return new ResponseEntity<>(userinboundorderstatusId, HttpStatus.OK);
	}

	@ApiOperation(response = InboundOrderStatusId.class, value = "Get a inboundOrderStatusId") // label for swagger
	@GetMapping("/inboundorderstatusid/{inboundOrderStatusId}")
	public ResponseEntity<?>  getinboundOrderStatusId(@RequestParam String warehouseId, @PathVariable String inboundOrderStatusId,@RequestParam String companyCodeId,
													  @RequestParam String languageId,@RequestParam String plantId) {
		InboundOrderStatusId dbinboundOrderStatusId = idmasterService.getInboundOrderStatusID(warehouseId,inboundOrderStatusId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbinboundOrderStatusId, HttpStatus.OK);
	}

	@ApiOperation(response = InboundOrderStatusId.class, value = "Create InboundsOrderStatusId") // label for swagger
	@PostMapping("/inboundorderstatusid")
	public ResponseEntity<?> PostinbountOrderStatusId(@Valid @RequestBody AddInboundOrderStatusId newinboundOrderStatusId,
													  @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		InboundOrderStatusId createinboundOrderStatusId = idmasterService.createinboundOrderStatusId(newinboundOrderStatusId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createinboundOrderStatusId, HttpStatus.OK);
	}

	@ApiOperation(response = InboundOrderStatusId.class, value = "Update InboundOrderStatusId") // label for swagger
	@RequestMapping(value = "/inboundorderstatusid/{inboundOrderStatusId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateinboundOrderStatusId(@RequestParam String warehouseId,@PathVariable String inboundOrderStatusId,@RequestParam String companyCodeId,
													   @RequestParam String languageId,@RequestParam String plantId, @RequestParam String loginUserID,
													   @Valid @RequestBody UpdateInboundOrderStatusId updateinboundOrderStatusId) throws IllegalAccessException, InvocationTargetException {
		InboundOrderStatusId updateinBoundOrderStatusId =
				idmasterService.updateinboundOrderStatusId(warehouseId,inboundOrderStatusId,companyCodeId,languageId,plantId,loginUserID,updateinboundOrderStatusId,getAuthToken());
		return new ResponseEntity<>(updateinBoundOrderStatusId, HttpStatus.OK);
	}

	@ApiOperation(response = InboundOrderStatusId.class, value = "Delete InboundOrderStatusId") // label for swagger
	@DeleteMapping("/inboundorderstatusid/{inboundOrderStatusId}")
	public ResponseEntity<?> deleteInBoundOrderStatusId(@RequestParam String warehouseId,@PathVariable String inboundOrderStatusId,@RequestParam String companyCodeId,@RequestParam String languageId,
														@RequestParam String plantId, @RequestParam String loginUserID) {
		idmasterService.deleteInboundOrderStatusId(warehouseId,inboundOrderStatusId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = InboundOrderStatusId[].class, value = "Find InboundOrderStatusId")//label for swagger
	@PostMapping("/inboundorderstatusid/findInboundOrderStatusId")
	public InboundOrderStatusId[] findInboundOrderStatusId(@RequestBody FindInboundOrderStatusId findInboundOrderStatusId)throws Exception{
		return idmasterService.findInboundOrderStatusId(findInboundOrderStatusId,getAuthToken());
	}
	/*
	 * --------------------------------ControlTypeId---------------------------------
	 */

	@ApiOperation(response = ControlTypeId[].class, value = "Get all ControlTypeId details") // label for swagger
	@GetMapping("/controltypeid")
	public ResponseEntity<?> getcontrolTypeIds() {
		ControlTypeId[] usercontrolTypeId = idmasterService.getcontrolTypeIds(getAuthToken());
		return new ResponseEntity<>(usercontrolTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = ControlTypeId.class, value = "Get a ControlTypeId") // label for swagger
	@GetMapping("/controltypeid/{controlTypeId}")
	public ResponseEntity<?>  getcontrolTypeId(@RequestParam String warehouseId, @PathVariable String controlTypeId,@RequestParam String companyCodeId,
											   @RequestParam String languageId,@RequestParam String plantId) {
		ControlTypeId dbControlTypeId = idmasterService.getcontrolTypeId(warehouseId,controlTypeId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbControlTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = ControlTypeId.class, value = "Create a ControlTypeId") // label for swagger
	@PostMapping("/controltypeid")
	public ResponseEntity<?> PostcontrolTypeId(@Valid @RequestBody AddControlTypeId newControllTypeID,
											   @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		ControlTypeId createcontrolTypeId = idmasterService.createcontrolTypeId(newControllTypeID,loginUserID,getAuthToken());
		return new ResponseEntity<>(createcontrolTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = ControlTypeId.class, value = "Update ControlTypeID") // label for swagger
	@RequestMapping(value = "/controltypeid/{controlTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateControlId( @RequestParam String warehouseId,@PathVariable String controlTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
											 @RequestParam String loginUserID, @Valid @RequestBody UpdateControlTypeId updateControlTypeId)
			throws IllegalAccessException, InvocationTargetException {
		ControlTypeId updatecontrolTypeId =
				idmasterService.updatecontrolTypeId(warehouseId,controlTypeId,companyCodeId,languageId,plantId,loginUserID,updateControlTypeId,getAuthToken());
		return new ResponseEntity<>(updatecontrolTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = ControlTypeId.class, value = "Delete ControlTypeId") // label for swagger
	@DeleteMapping("/controltypeid/{controlTypeId}")
	public ResponseEntity<?> deleteControlTypeId(@RequestParam String warehouseId,@PathVariable String controlTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,
												 @RequestParam String plantId, @RequestParam String loginUserID) {
		idmasterService.deletecontrolTypeId(warehouseId,controlTypeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = ControlTypeId[].class, value = "Find ControlTypeId")//label for swagger
	@PostMapping("/controltypeid/findControlTypeId")
	public ControlTypeId[] findControlTypeId(@RequestBody FindControlTypeId findControlTypeId)throws Exception{
		return idmasterService.findControlTypeId(findControlTypeId,getAuthToken());
	}

	/*
	 * --------------------------------ApprovalId---------------------------------
	 */
	@ApiOperation(response = ApprovalId[].class, value = "Get all ApprovalId details") // label for swagger
	@GetMapping("/approvalid")
	public ResponseEntity<?> getapprovalIds() {
		ApprovalId[] userapprovalId = idmasterService.getapprovalIds(getAuthToken());
		return new ResponseEntity<>(userapprovalId, HttpStatus.OK);
	}

	@ApiOperation(response = ApprovalId.class, value = "Get a ApprovalId") // label for swagger
	@GetMapping("/approvalid/{approvalId}")
	public ResponseEntity<?>  getapprovalId(@RequestParam String warehouseId,@PathVariable String approvalId,
											@RequestParam String approvalLevel,@RequestParam String approvalProcessId,
											@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		ApprovalId dbapprovalId = idmasterService.getapprovalId(warehouseId,approvalId,approvalLevel,approvalProcessId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbapprovalId, HttpStatus.OK);
	}

	@ApiOperation(response = ApprovalId.class, value = "Create a ApprovalId") // label for swagger
	@PostMapping("/approvalid")
	public ResponseEntity<?> postapprovalId(@Valid @RequestBody AddApprovalId newApprovalId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		ApprovalId createApprovalId = idmasterService.createapprovalId(newApprovalId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createApprovalId, HttpStatus.OK);
	}

	@ApiOperation(response = ApprovalId.class, value = "Update ApprovalID") // label for swagger
	@RequestMapping(value = "/approvalid/{approvalId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateApprovalId(@PathVariable String approvalId,
											 @RequestParam String warehouseId,@RequestParam String approvalLevel,@RequestParam String approvalProcessId,
											 @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
											 @Valid @RequestBody UpdateApprovalId updateApprovalId) throws IllegalAccessException, InvocationTargetException {
		ApprovalId updateapprovalId =
				idmasterService.updateApprovalId(approvalId,approvalProcessId,warehouseId,approvalLevel,companyCodeId,languageId,plantId,loginUserID,updateApprovalId,getAuthToken());
		return new ResponseEntity<>(updateapprovalId, HttpStatus.OK);
	}

	@ApiOperation(response = ApprovalId.class, value = "Delete ApprovalId") // label for swagger
	@DeleteMapping("/approvalid/{approvalId}")
	public ResponseEntity<?> deleteApprovalId(@RequestParam String warehouseId,@PathVariable String approvalId,@RequestParam String approvalLevel,@RequestParam String approvalProcessId,
											  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deleteapprovalId(warehouseId,approvalId,approvalLevel,approvalProcessId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = ApprovalId[].class, value = "Find ApprovalId")//label for swagger
	@PostMapping("/approvalid/findApprovalId")
	public ApprovalId[] findApprovalId(@RequestBody FindApprovalId findApprovalId)throws Exception{
		return idmasterService.findApprovalId(findApprovalId,getAuthToken());
	}

	/*
	 * --------------------------------RefDocTypeId---------------------------------
	 */

	@ApiOperation(response = RefDocTypeId[].class, value = "Get all RefDocTypeIdTypeId details") // label for swagger
	@GetMapping("/refdoctypeid")
	public ResponseEntity<?> getRefDocTypeIds() {
		RefDocTypeId[] userRefDocTypeId = idmasterService.getrefDocTypeIds(getAuthToken());
		return new ResponseEntity<>(userRefDocTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = RefDocTypeId.class, value = "Get a RefDocTypeId") // label for swagger
	@GetMapping("/refdoctypeid/{referenceDocumentTypeId}")
	public ResponseEntity<?>  getRefDocTypeId(@RequestParam String warehouseId, @PathVariable String referenceDocumentTypeId,@RequestParam String companyCodeId,
											  @RequestParam String languageId,@RequestParam String plantId) {
		RefDocTypeId dbRefDocTypeId = idmasterService.getrefDocTypeId(warehouseId,referenceDocumentTypeId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbRefDocTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = RefDocTypeId.class, value = "Create a RefDocTypeId") // label for swagger
	@PostMapping("/refdoctypeid")
	public ResponseEntity<?> PostRefDocTypeId(@Valid @RequestBody AddRefDocTypeId newRefDocTypeId,
											  @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		RefDocTypeId createRefDocTypeId = idmasterService.createrefDocTypeId(newRefDocTypeId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createRefDocTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = RefDocTypeId.class, value = "Update RefDocTypeId") // label for swagger
	@RequestMapping(value = "/refdoctypeid/{referenceDocumentTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateRefDocTypeId(@RequestParam String warehouseId,@PathVariable String referenceDocumentTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
											   @RequestParam String loginUserID, @Valid @RequestBody UpdateRefDocTypeId updateRefDocTypeId)
			throws IllegalAccessException, InvocationTargetException {
		RefDocTypeId updaterefdoctypeid =
				idmasterService.updateRefDocTypeId(warehouseId,referenceDocumentTypeId,companyCodeId,languageId,plantId,loginUserID,updateRefDocTypeId,getAuthToken());
		return new ResponseEntity<>(updaterefdoctypeid, HttpStatus.OK);
	}

	@ApiOperation(response = RefDocTypeId.class, value = "Delete RefDocTypeId") // label for swagger
	@DeleteMapping("/refdoctypeid/{referenceDocumentTypeId}")
	public ResponseEntity<?> deleteRefDocTypeId(@RequestParam String warehouseId,@PathVariable String referenceDocumentTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												@RequestParam String loginUserID) {
		idmasterService.deleteRefDocTypeId(warehouseId,referenceDocumentTypeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = RefDocTypeId[].class, value = "Find RefDocTypeId")//label for swagger
	@PostMapping("/refdoctypeid/findRefDocTypeId")
	public RefDocTypeId[] findRefDocTypeId(@RequestBody FindRefDocTypeId findRefDocTypeId)throws Exception{
		return idmasterService.findRefDocTypeId(findRefDocTypeId,getAuthToken());
	}
	/*
	 * --------------------------------ControlProcessId---------------------------------
	 */

	@ApiOperation(response = ControlProcessId[].class, value = "Get all ControlProcessId details") // label for swagger
	@GetMapping("/controlprocessid")
	public ResponseEntity<?> getControlProcessIds() {
		ControlProcessId[] userControlProcessId = idmasterService.getControlProcessIds(getAuthToken());
		return new ResponseEntity<>(userControlProcessId, HttpStatus.OK);
	}

	@ApiOperation(response = ControlProcessId.class, value = "Get a ControlProcessId") // label for swagger
	@GetMapping("/controlprocessid/{controlProcessId}")
	public ResponseEntity<?>  getControlProcessId(@RequestParam String warehouseId, @PathVariable String controlProcessId,@RequestParam String companyCodeId,
												  @RequestParam String languageId,@RequestParam String plantId) {
		ControlProcessId dbControlProcessId = idmasterService.getControlProcessId(warehouseId,controlProcessId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbControlProcessId, HttpStatus.OK);
	}

	@ApiOperation(response = ControlProcessId.class, value = "Create a ControlProcessId") // label for swagger
	@PostMapping("/controlprocessid")
	public ResponseEntity<?> PostControlProcessId(@Valid @RequestBody AddControlProcessId newControlProcessId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		ControlProcessId createControlProcessId = idmasterService.createControlProcessId(newControlProcessId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createControlProcessId, HttpStatus.OK);
	}

	@ApiOperation(response = ControlProcessId.class, value = "Update ControlProcessId") // label for swagger
	@RequestMapping(value = "/controlprocessid/{controlProcessId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateControlProcessId(@RequestParam String warehouseId,@PathVariable String controlProcessId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
												   @RequestParam String loginUserID, @Valid @RequestBody UpdateControlProcessId updateControlProcessId)
			throws IllegalAccessException, InvocationTargetException {
		ControlProcessId updatecontrolProcessId =
				idmasterService.updateControlProcessId(warehouseId,controlProcessId,companyCodeId,languageId,plantId,loginUserID,updateControlProcessId,getAuthToken());
		return new ResponseEntity<>(updatecontrolProcessId, HttpStatus.OK);
	}

	@ApiOperation(response = ControlProcessId.class, value = "Delete ControlProcessId") // label for swagger
	@DeleteMapping("/controlprocessid/{controlProcessId}")
	public ResponseEntity<?> deleteControlProcessId( @RequestParam String warehouseId,@PathVariable String controlProcessId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
													 @RequestParam String loginUserID) {
		idmasterService.deletecontrolProcessId(warehouseId,controlProcessId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = ControlProcessId[].class, value = "Find ControlProcessId")//label for swagger
	@PostMapping("/controlprocessid/findControlProcessId")
	public ControlProcessId[] findControlProcessId(@RequestBody FindControlProcessId findControlProcessId)throws Exception{
		return idmasterService.findControlProcessId(findControlProcessId,getAuthToken());
	}

	/*
	 * --------------------------------AisleId---------------------------------
	 */
	@ApiOperation(response = AisleId[].class, value = "Get all AisleId details") // label for swagger
	@GetMapping("/aisleid")
	public ResponseEntity<?> getAisleIds() {
		AisleId[] userAisleId = idmasterService.getAisleIds(getAuthToken());
		return new ResponseEntity<>(userAisleId, HttpStatus.OK);
	}

	@ApiOperation(response = AisleId.class, value = "Get a AisleId") // label for swagger
	@GetMapping("/aisleid/{aisleId}")
	public ResponseEntity<?>  getAisleId(@RequestParam String warehouseId,@PathVariable String aisleId, @RequestParam Long floorId, @RequestParam String storageSectionId,
										 @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		AisleId dbAisleId = idmasterService.getAisleId(warehouseId, aisleId,floorId,storageSectionId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbAisleId, HttpStatus.OK);
	}

	@ApiOperation(response = AisleId.class, value = "Create a AisleId") // label for swagger
	@PostMapping("/aisleid")
	public ResponseEntity<?> PostAisleId(@Valid @RequestBody AddAisleId newAisleId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		AisleId createAisleId = idmasterService.createAisleId(newAisleId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createAisleId, HttpStatus.OK);
	}

	@ApiOperation(response = AisleId.class, value = "Update AisleId") // label for swagger
	@RequestMapping(value = "/aisleid/{aisleId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateAisleId(@RequestParam String warehouseId,@PathVariable String aisleId, @RequestParam Long floorId, @RequestParam String storageSectionId,
										  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
										  @Valid @RequestBody UpdateAisleId updateaisleId) throws IllegalAccessException, InvocationTargetException {
		AisleId updateAisleId =
				idmasterService.updateAisleId(warehouseId,aisleId,floorId,storageSectionId,companyCodeId,languageId,plantId,loginUserID,updateaisleId,getAuthToken());
		return new ResponseEntity<>(updateAisleId, HttpStatus.OK);
	}

	@ApiOperation(response = AisleId.class, value = "Delete AisleId") // label for swagger
	@DeleteMapping("/aisleid/{aisleId}")
	public ResponseEntity<?> deleteAisleId(@RequestParam String warehouseId,@PathVariable String aisleId, @RequestParam Long floorId, @RequestParam String storageSectionId,
										   @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deleteAisleId(warehouseId,aisleId,floorId,storageSectionId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = AisleId[].class, value = "Find AisleId")//label for swagger
	@PostMapping("/aisleid/findAisleId")
	public AisleId[] findAisleId(@RequestBody FindAisleId findAisleId)throws Exception{
		return idmasterService.findAisleId(findAisleId,getAuthToken());
	}

	/*
	 * --------------------------------SpanId---------------------------------
	 */
	@ApiOperation(response = SpanId[].class, value = "Get all SpanId details") // label for swagger
	@GetMapping("/spanid")
	public ResponseEntity<?> getSpanIds() {
		SpanId[] userSpanId = idmasterService.getSpanIds(getAuthToken());
		return new ResponseEntity<>(userSpanId, HttpStatus.OK);
	}

	@ApiOperation(response = SpanId.class, value = "Get a SpanId") // label for swagger
	@GetMapping("/spanid/{spanId}")
	public ResponseEntity<?>getSpanId(@RequestParam String warehouseId,@RequestParam String aisleId,@PathVariable String spanId,
									  @RequestParam Long floorId, @RequestParam String storageSectionId,@RequestParam String companyCodeId,
									  @RequestParam String languageId,@RequestParam String plantId) {
		SpanId dbSpanId = idmasterService.getSpanId(warehouseId,aisleId,spanId,floorId,storageSectionId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbSpanId, HttpStatus.OK);
	}

	@ApiOperation(response = SpanId.class, value = "Create a SpanId") // label for swagger
	@PostMapping("/spanid")
	public ResponseEntity<?> PostSpanId(@Valid @RequestBody AddSpanId newSpanId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		SpanId createSpanId = idmasterService.createSpanId(newSpanId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createSpanId, HttpStatus.OK);
	}

	@ApiOperation(response = SpanId.class, value = "Update SpanId") // label for swagger
	@RequestMapping(value = "/spanid/{spanId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateSpanId(@RequestParam String warehouseId,@RequestParam String aisleId,@PathVariable String spanId,
										 @RequestParam Long floorId, @RequestParam String storageSectionId,@RequestParam String companyCodeId,
										 @RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
										 @Valid @RequestBody UpdateSpanId updateSpanId) throws IllegalAccessException, InvocationTargetException {
		SpanId UpdatespanId =
				idmasterService.updateSpanId(warehouseId,aisleId,spanId,floorId,storageSectionId,companyCodeId,languageId,plantId,loginUserID,updateSpanId,getAuthToken());
		return new ResponseEntity<>(UpdatespanId, HttpStatus.OK);
	}

	@ApiOperation(response = SpanId.class, value = "Delete SpanId") // label for swagger
	@DeleteMapping("/spanid/{spanId}")
	public ResponseEntity<?> deleteSpanId(@RequestParam String warehouseId,@RequestParam String aisleId,@PathVariable String spanId,
										  @RequestParam Long floorId, @RequestParam String storageSectionId,@RequestParam String companyCodeId,
										  @RequestParam String languageId,@RequestParam String plantId, @RequestParam String loginUserID) {
		idmasterService.deleteSpanId(warehouseId,aisleId,spanId,floorId,storageSectionId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = SpanId[].class, value = "Find SpanId")//label for swagger
	@PostMapping("/spanid/findSpanId")
	public SpanId[] findSpanId(@RequestBody FindSpanId findSpanId)throws Exception{
		return idmasterService.findSpanId(findSpanId,getAuthToken());
	}

	/*
	 * --------------------------------ShelfId---------------------------------
	 */
	@ApiOperation(response = ShelfId[].class, value = "Get all ShelfId details") // label for swagger
	@GetMapping("/shelfid")
	public ResponseEntity<?> getShelfIds() {
		ShelfId[] userShelfId = idmasterService.getShelfIds(getAuthToken());
		return new ResponseEntity<>(userShelfId, HttpStatus.OK);
	}

	@ApiOperation(response = ShelfId.class, value = "Get a ShelfId") // label for swagger
	@GetMapping("/shelfid/{shelfId}")
	public ResponseEntity<?>  getShelfId(@PathVariable String shelfId,
										 @RequestParam String warehouseId,@RequestParam String aisleId, @RequestParam Long floorId,@RequestParam String spanId,
										 @RequestParam String storageSectionId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		ShelfId dbShelfId = idmasterService.getShelfId(shelfId,warehouseId,aisleId,spanId, floorId,storageSectionId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbShelfId, HttpStatus.OK);
	}

	@ApiOperation(response = ShelfId.class, value = "Create a ShelfId") // label for swagger
	@PostMapping("/shelfid")
	public ResponseEntity<?> PostShelfId(@Valid @RequestBody AddShelfId newShelfId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		ShelfId createShelfId = idmasterService.createShelfId(newShelfId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createShelfId, HttpStatus.OK);
	}

	@ApiOperation(response = ShelfId.class, value = "Update ShelfId") // label for swagger
	@RequestMapping(value = "/shelfid/{shelfId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateShelfId(@PathVariable String shelfId,
										  @RequestParam String warehouseId, @RequestParam String aisleId,@RequestParam String spanId, @RequestParam Long floorId,
										  @RequestParam String storageSectionId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
										  @RequestParam String loginUserID, @Valid @RequestBody UpdateShelfId updateShelfId) throws IllegalAccessException, InvocationTargetException {
		ShelfId UpdateShelfId =
				idmasterService.updateShelfId(shelfId,warehouseId,aisleId,spanId,floorId,storageSectionId,companyCodeId,languageId,plantId,loginUserID,updateShelfId,getAuthToken());
		return new ResponseEntity<>(UpdateShelfId, HttpStatus.OK);
	}

	@ApiOperation(response = ShelfId.class, value = "Delete ShelfId") // label for swagger
	@DeleteMapping("/shelfid/{shelfId}")
	public ResponseEntity<?> deleteShelfId(@PathVariable String shelfId,@RequestParam String aisleId,@RequestParam Long floorId, @RequestParam String storageSectionId,
										   @RequestParam String warehouseId,@RequestParam String spanId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deleteShelfId(warehouseId,aisleId,shelfId,spanId,floorId,storageSectionId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = ShelfId[].class, value = "Find ShelfId")//label for swagger
	@PostMapping("/shelfid/findShelfId")
	public ShelfId[] findShelfId(@RequestBody FindShelfId findShelfId)throws Exception{
		return idmasterService.findShelfId(findShelfId,getAuthToken());
	}

	/*
	 * --------------------------------BinSectionId---------------------------------
	 */
	@ApiOperation(response = BinSectionId[].class, value = "Get all BinSectionId details") // label for swagger
	@GetMapping("/binsectionid")
	public ResponseEntity<?> getBinSectionIds() {
		BinSectionId[] userbinsectionid = idmasterService.getBinSectionIds(getAuthToken());
		return new ResponseEntity<>(userbinsectionid, HttpStatus.OK);
	}

	@ApiOperation(response = BinSectionId.class, value = "Get a BinSectionId") // label for swagger
	@GetMapping("/binsectionid/{binSectionId}")
	public ResponseEntity<?>  getBinSectionId(@RequestParam String warehouseId,@PathVariable String binSectionId,
											  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		BinSectionId dbbinSectionId = idmasterService.getBinSectionId(warehouseId,binSectionId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbbinSectionId, HttpStatus.OK);
	}

	@ApiOperation(response = BinSectionId.class, value = "Create a BinSectionId") // label for swagger
	@PostMapping("/binsectionid")
	public ResponseEntity<?> PostBinSectionId(@Valid @RequestBody AddBinSectionId newBinSectionId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		BinSectionId createbinSectionId = idmasterService.createBinSectionId(newBinSectionId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createbinSectionId, HttpStatus.OK);
	}

	@ApiOperation(response = BinSectionId.class, value = "Update BinSectionId") // label for swagger
	@RequestMapping(value = "/binsectionid/{binSectionId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateBinSectionId(@RequestParam String warehouseId,@PathVariable String binSectionId,@RequestParam String companyCodeId,
											   @RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
											   @Valid @RequestBody UpdateBinSectionId updateBinSectionId) throws IllegalAccessException, InvocationTargetException {
		BinSectionId Updatebinsectionid =
				idmasterService.updateBinSectionId(warehouseId,binSectionId,companyCodeId,languageId,plantId,loginUserID,updateBinSectionId,getAuthToken());
		return new ResponseEntity<>(Updatebinsectionid, HttpStatus.OK);
	}

	@ApiOperation(response = BinSectionId.class, value = "Delete BinSectionId") // label for swagger
	@DeleteMapping("/binsectionid/{binSectionId}")
	public ResponseEntity<?> deleteShelfId(@RequestParam String warehouseId,@PathVariable String binSectionId, @RequestParam String companyCodeId,
										   @RequestParam String languageId,@RequestParam String plantId, @RequestParam String loginUserID) {
		idmasterService.deleteBinSectionId(warehouseId,binSectionId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = BinSectionId[].class, value = "Find BinSectionId")//label for swagger
	@PostMapping("/binsectionid/findBinSectionId")
	public BinSectionId[] findBinSectionId(@RequestBody FindBinSectionId findBinSectionId) throws Exception{
		return idmasterService.findBinSectionId(findBinSectionId,getAuthToken());
	}

	/*
	 * --------------------------------DateFormatId---------------------------------
	 */
	@ApiOperation(response = DateFormatId[].class, value = "Get all DateFormatId details") // label for swagger
	@GetMapping("/dateformatid")
	public ResponseEntity<?> getDateFormatIds() {
		DateFormatId[] userdateformatid = idmasterService.getDateFormatIds(getAuthToken());
		return new ResponseEntity<>(userdateformatid, HttpStatus.OK);
	}

	@ApiOperation(response = DateFormatId.class, value = "Get a DateFormatId") // label for swagger
	@GetMapping("/dateformatid/{dateFormatId}")
	public ResponseEntity<?>  getDateFormatId(@RequestParam String warehouseId,@PathVariable String dateFormatId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		DateFormatId dbdateformatid= idmasterService.getDateFormatId(warehouseId,dateFormatId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbdateformatid, HttpStatus.OK);
	}

	@ApiOperation(response = DateFormatId.class, value = "Create a DateFormatId") // label for swagger
	@PostMapping("/dateformatid")
	public ResponseEntity<?> PostDateFormatId(@Valid @RequestBody AddDateFormatId newDateFormatId,
											  @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		DateFormatId createdateformatid = idmasterService.createDateFormatId(newDateFormatId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createdateformatid, HttpStatus.OK);
	}

	@ApiOperation(response = DateFormatId.class, value = "Update DateFormatId") // label for swagger
	@RequestMapping(value = "/dateformatid/{dateFormatId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateDateFormatId(@RequestParam String warehouseId,@PathVariable String dateFormatId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
											   @Valid @RequestBody UpdateDateFormatId updateDateFormatId) throws IllegalAccessException, InvocationTargetException {
		DateFormatId Updatedateformatid =
				idmasterService.updateDateFormatId(warehouseId,dateFormatId,companyCodeId,languageId,plantId,loginUserID,updateDateFormatId,getAuthToken());
		return new ResponseEntity<>(Updatedateformatid, HttpStatus.OK);
	}

	@ApiOperation(response = DateFormatId.class, value = "Delete DateFormatId") // label for swagger
	@DeleteMapping("/dateformatid/{dateFormatId}")
	public ResponseEntity<?> deleteDateFormatId(@RequestParam String warehouseId,@PathVariable String dateFormatId,@RequestParam String companyCodeId,
												@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deleteDateFormatId(warehouseId,dateFormatId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = DateFormatId[].class, value = "Find DateFormatId")//label for swagger
	@PostMapping("/dateformatid/findDateFormatId")
	public DateFormatId[] findDateFormatId(@RequestBody FindDateFormatId findDateFormatId) throws Exception {
		return idmasterService.findDateFormatId(findDateFormatId,getAuthToken());
	}

	/*
	 * --------------------------------DecimalNotationId---------------------------------
	 */
	@ApiOperation(response = DecimalNotationId[].class, value = "Get all DecimalNotationId details") // label for swagger
	@GetMapping("/decimalnotationid")
	public ResponseEntity<?> getDecimalNotationIds() {
		DecimalNotationId[] userDecimalNotationId = idmasterService.getDecimalNotationIds(getAuthToken());
		return new ResponseEntity<>(userDecimalNotationId, HttpStatus.OK);
	}

	@ApiOperation(response = DecimalNotationId.class, value = "Get a DecimalNotationId") // label for swagger
	@GetMapping("/decimalnotationid/{decimalNotationId}")
	public ResponseEntity<?>  getDecimalNotationId(@RequestParam String warehouseId,@PathVariable String decimalNotationId,@RequestParam String companyCodeId,@RequestParam String languageId,
												   @RequestParam String plantId) {
		DecimalNotationId dbDecimalNotationId= idmasterService.getDecimalNotationId(warehouseId,decimalNotationId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbDecimalNotationId, HttpStatus.OK);
	}

	@ApiOperation(response = DecimalNotationId.class, value = "Create a DecimalNotationId") // label for swagger
	@PostMapping("/decimalnotationid")
	public ResponseEntity<?> PostDecimalNotationId(@Valid @RequestBody AddDecimalNotationId newDecimalNotationId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		DecimalNotationId createDecimalNotationId = idmasterService.createDecimalNotationId(newDecimalNotationId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createDecimalNotationId, HttpStatus.OK);
	}

	@ApiOperation(response = DecimalNotationId.class, value = "Update DecimalNotationId") // label for swagger
	@RequestMapping(value = "/decimalnotationid/{decimalNotationId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateDecimalNotationId(@RequestParam String warehouseId,@PathVariable String decimalNotationId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
													@Valid @RequestBody UpdateDecimalNotationId updateDecimalNotationId) throws IllegalAccessException, InvocationTargetException {
		DecimalNotationId UpdateDecimalNotationId =
				idmasterService.updateDecimalNotationId(warehouseId,decimalNotationId,companyCodeId,languageId,plantId,loginUserID,updateDecimalNotationId,getAuthToken());
		return new ResponseEntity<>(UpdateDecimalNotationId, HttpStatus.OK);
	}

	@ApiOperation(response = DecimalNotationId.class, value = "Delete DecimalNotationId") // label for swagger
	@DeleteMapping("/decimalnotationid/{decimalNotationId}")
	public ResponseEntity<?> deleteDecimalNotationId(@RequestParam String warehouseId,@PathVariable String decimalNotationId,@RequestParam String companyCodeId,
													 @RequestParam String languageId,@RequestParam String plantId, @RequestParam String loginUserID) {
		idmasterService.deleteDecimalNotationId(warehouseId,decimalNotationId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = DecimalNotationId[].class, value = "Find DecimalNotationId")//label for swagger
	@PostMapping("/decimalnotationid/findDecimalNotationId")
	public DecimalNotationId[] findDecimalNotationId(@RequestBody FindDecimalNotationId findDecimalNotationId) throws Exception{
		return idmasterService.findDecimalNotationId(findDecimalNotationId,getAuthToken());
	}

	/*
	 * --------------------------------LanguageId---------------------------------
	 */
	@ApiOperation(response = LanguageId[].class, value = "Get all LanguageId details") // label for swagger
	@GetMapping("/languageid")
	public ResponseEntity<?> getLanguageIds() {
		LanguageId[] userLanguageId = idmasterService.getLanguageIds(getAuthToken());
		return new ResponseEntity<>(userLanguageId, HttpStatus.OK);
	}

	@ApiOperation(response = LanguageId.class, value = "Get a LanguageId") // label for swagger
	@GetMapping("/languageid/{languageId}")
	public ResponseEntity<?>  getLanguageId(@PathVariable String languageId) {
		LanguageId dbLanguageId= idmasterService.getLanguageId(languageId,getAuthToken());
		return new ResponseEntity<>(dbLanguageId, HttpStatus.OK);
	}

	@ApiOperation(response = LanguageId.class, value = "Create a LanguageId") // label for swagger
	@PostMapping("/languageid")
	public ResponseEntity<?> PostLanguageId(@Valid @RequestBody AddLanguageId newLanguageId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		LanguageId createLanguageId = idmasterService.createLanguageId(newLanguageId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createLanguageId, HttpStatus.OK);
	}

	@ApiOperation(response = LanguageId.class, value = "Update LanguageId") // label for swagger
	@RequestMapping(value = "/languageid/{languageId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateLanguageId(@PathVariable String languageId,@RequestParam String loginUserID,
											 @Valid @RequestBody UpdateLanguageId updateLanguageId) throws IllegalAccessException, InvocationTargetException {
		LanguageId UpdateLanguageId =
				idmasterService.updateLanguageId(languageId,loginUserID,updateLanguageId,getAuthToken());
		return new ResponseEntity<>(UpdateLanguageId, HttpStatus.OK);
	}

	@ApiOperation(response = LanguageId.class, value = "Delete LanguageId") // label for swagger
	@DeleteMapping("/languageid/{languageId}")
	public ResponseEntity<?> deleteLanguageId(@PathVariable String languageId, @RequestParam String loginUserID) {
		idmasterService.deleteLanguageId(languageId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = LanguageId[].class, value = "Find LanguageId")//label for swagger
	@PostMapping("/languageid/findlanguageid")
	public LanguageId[] findLanguageId(@RequestBody FindLanguageId findLanguageId) throws Exception{
		return idmasterService.findLanguageId(findLanguageId,getAuthToken());
	}

	/*
	 * --------------------------------StatusMessagesId---------------------------------
	 */
	@ApiOperation(response = StatusMessagesId[].class, value = "Get all StatusMessagesId details") // label for swagger
	@GetMapping("/statusmessagesid")
	public ResponseEntity<?> getStatusMessagesIds() {
		StatusMessagesId[] userStatusMessagesId = idmasterService.getStatusMessagesIds(getAuthToken());
		return new ResponseEntity<>(userStatusMessagesId, HttpStatus.OK);
	}

	@ApiOperation(response = StatusMessagesId.class, value = "Get a StatusMessagesId") // label for swagger
	@GetMapping("/statusmessagesid/{messagesId}")
	public ResponseEntity<?>  getStatusMessagesId(@PathVariable String messagesId,
												  @RequestParam String languageId, @RequestParam String messageType) {
		StatusMessagesId dbStatusMessagesId= idmasterService.getStatusMessagesId(messagesId,languageId,messageType,getAuthToken());
		return new ResponseEntity<>(dbStatusMessagesId, HttpStatus.OK);
	}

	@ApiOperation(response = StatusMessagesId.class, value = "Create a StatusMessagesId") // label for swagger
	@PostMapping("/statusmessagesid")
	public ResponseEntity<?> PostStatusMessagesId(@Valid @RequestBody AddStatusMessagesId newStatusMessagesId,
												  @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		StatusMessagesId createStatusMessagesId = idmasterService.createStatusMessagesId(newStatusMessagesId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createStatusMessagesId, HttpStatus.OK);
	}

	@ApiOperation(response = StatusMessagesId.class, value = "Update StatusMessagesId") // label for swagger
	@RequestMapping(value = "/statusmessagesid/{messagesId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateStatusMessagesId(@PathVariable String messagesId,
												   @RequestParam String languageId,
												   @RequestParam String messageType,@RequestParam String loginUserID,
												   @Valid @RequestBody UpdateStatusMessagesId updateStatusMessagesId) throws IllegalAccessException, InvocationTargetException {
		StatusMessagesId UpdateStatusMessagesId =
				idmasterService.updateStatusMessagesId(messagesId,languageId,messageType,loginUserID,updateStatusMessagesId,getAuthToken());
		return new ResponseEntity<>(UpdateStatusMessagesId, HttpStatus.OK);
	}

	@ApiOperation(response = StatusMessagesId.class, value = "Delete StatusMessagesId") // label for swagger
	@DeleteMapping("/statusmessagesid/{messagesId}")
	public ResponseEntity<?> deleteStatusMessagesId(@PathVariable String messagesId,
													@RequestParam String languageId,
													@RequestParam String messageType,
													@RequestParam String loginUserID) {
		idmasterService.deleteStatusMessagesId(messagesId,languageId,messageType,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = StatusMessagesId[].class, value = "Find StatusMessagesId")//label for swagger
	@PostMapping("/statusmessagesid/findStatusMessagesId")
	public StatusMessagesId[] findStatusMessagesId(@RequestBody FindStatusMessagesId findStatusMessagesId) throws Exception {
		return idmasterService.findStatusMessagesId(findStatusMessagesId,getAuthToken());
	}


	/*
	 * --------------------------------MovementTypeId---------------------------------
	 */
	@ApiOperation(response = MovementTypeId[].class, value = "Get all MovementTypeId details") // label for swagger
	@GetMapping("/movementtypeid")
	public ResponseEntity<?> getMovementTypeIds() {
		MovementTypeId[] userMovementTypeId = idmasterService.getMovementTypeIds(getAuthToken());
		return new ResponseEntity<>(userMovementTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = MovementTypeId.class, value = "Get a MovementTypeId") // label for swagger
	@GetMapping("/movementtypeid/{movementTypeId}")
	public ResponseEntity<?>  getMovementTypeId(@RequestParam String warehouseId,@PathVariable String movementTypeId,
												@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		MovementTypeId dbMovementTypeId= idmasterService.getMovementTypeId(warehouseId,movementTypeId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbMovementTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = MovementTypeId.class, value = "Create a MovementTypeId") // label for swagger
	@PostMapping("/movementtypeid")
	public ResponseEntity<?> PostMovementTypeId(@Valid @RequestBody AddMovementTypeId newMovementTypeId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		MovementTypeId createMovementTypeId = idmasterService.createMovementTypeId(newMovementTypeId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createMovementTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = MovementTypeId.class, value = "Update MovementTypeId") // label for swagger
	@RequestMapping(value = "/movementtypeid/{movementTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateMovementTypeId(@RequestParam String warehouseId,@PathVariable String movementTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
												 @Valid @RequestBody UpdateMovementTypeId updateMovementTypeId) throws IllegalAccessException, InvocationTargetException {
		MovementTypeId UpdateMovementTypeId =
				idmasterService.updateMovementTypeId(warehouseId,movementTypeId,companyCodeId,languageId,plantId,loginUserID,updateMovementTypeId,getAuthToken());
		return new ResponseEntity<>(UpdateMovementTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = MovementTypeId.class, value = "Delete MovementTypeId") // label for swagger
	@DeleteMapping("/movementtypeid/{movementTypeId}")
	public ResponseEntity<?> deleteMovementTypeId(@RequestParam String warehouseId,@PathVariable String movementTypeId,
												  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId, @RequestParam String loginUserID) {
		idmasterService.deleteMovementTypeId(warehouseId,movementTypeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = MovementTypeId[].class, value = "Find MovementTypeId")//label for swagger
	@PostMapping("/movementtypeid/findMovementTypeId")
	public MovementTypeId[] findMovementTypeId(@RequestBody FindMovementTypeId findMovementTypeId) throws Exception {
		return idmasterService.findMovementTypeId(findMovementTypeId,getAuthToken());
	}

	/*
	 * --------------------------------SubMovementTypeId---------------------------------
	 */
	@ApiOperation(response = SubMovementTypeId[].class, value = "Get all SubMovementTypeId details") // label for swagger
	@GetMapping("/submovementtypeid")
	public ResponseEntity<?> getSubMovementTypeIds() {
		SubMovementTypeId[] userSubMovementTypeId = idmasterService.getSubMovementTypeIds(getAuthToken());
		return new ResponseEntity<>(userSubMovementTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = SubMovementTypeId.class, value = "Get a SubMovementTypeId") // label for swagger
	@GetMapping("/submovementtypeid/{subMovementTypeId}")
	public ResponseEntity<?>  getSubMovementTypeId(@RequestParam String warehouseId,@RequestParam String movementTypeId,
												   @PathVariable String subMovementTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		SubMovementTypeId dbSubMovementTypeId= idmasterService.getSubMovementTypeId(warehouseId,movementTypeId,subMovementTypeId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbSubMovementTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = SubMovementTypeId.class, value = "Create a SubMovementTypeId") // label for swagger
	@PostMapping("/submovementtypeid")
	public ResponseEntity<?> PostSubMovementTypeId(@Valid @RequestBody AddSubMovementTypeId newSubMovementTypeId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		SubMovementTypeId createSubMovementTypeId = idmasterService.createSubMovementTypeId(newSubMovementTypeId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createSubMovementTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = SubMovementTypeId.class, value = "Update SubMovementTypeId") // label for swagger
	@RequestMapping(value = "/submovementtypeid/{subMovementTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateSubMovementTypeId(@RequestParam String warehouseId,@RequestParam String movementTypeId,@PathVariable String subMovementTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
													@Valid @RequestBody UpdateSubMovementTypeId updateSubMovementTypeId) throws IllegalAccessException, InvocationTargetException {
		SubMovementTypeId UpdateSubMovementTypeId =
				idmasterService.updateSubMovementTypeId(warehouseId,movementTypeId,subMovementTypeId,companyCodeId,languageId,plantId,loginUserID,updateSubMovementTypeId,getAuthToken());
		return new ResponseEntity<>(UpdateSubMovementTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = SubMovementTypeId.class, value = "Delete SubMovementTypeId") // label for swagger
	@DeleteMapping("/submovementtypeid/{subMovementTypeId}")
	public ResponseEntity<?> deleteSubMovementTypeId(@RequestParam String warehouseId,@RequestParam String movementTypeId,
													 @PathVariable String subMovementTypeId,@RequestParam String companyCodeId,
													 @RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deleteSubMovementTypeId(warehouseId,movementTypeId,subMovementTypeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = SubMovementTypeId[].class, value = "Find SubMovementTypeId")//label for swagger
	@PostMapping("/submovementtypeid/findSubMovementTypeId")
	public SubMovementTypeId[] findSubMovementTypeId(@RequestBody FindSubMovementTypeId findSubMovementTypeId) throws Exception {
		return idmasterService.findSubMovementTypeId(findSubMovementTypeId,getAuthToken());
	}

	/*
	 * --------------------------------OutboundOrderTypeId---------------------------------
	 */
	@ApiOperation(response = OutboundOrderTypeId[].class, value = "Get all OutboundOrderTypeId details") // label for swagger
	@GetMapping("/outboundordertypeid")
	public ResponseEntity<?> getOutboundOrderTypeIds() {
		OutboundOrderTypeId[] userOutboundOrderTypeId = idmasterService.getOutboundOrderTypeIds(getAuthToken());
		return new ResponseEntity<>(userOutboundOrderTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = OutboundOrderTypeId.class, value = "Get a OutboundOrderTypeId") // label for swagger
	@GetMapping("/outboundordertypeid/{outboundOrderTypeId}")
	public ResponseEntity<?>  getOutboundOrderTypeId(@RequestParam String warehouseId,@PathVariable String outboundOrderTypeId,
													 @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		OutboundOrderTypeId dbOutboundOrderTypeId= idmasterService.getOutboundOrderTypeId(warehouseId,outboundOrderTypeId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbOutboundOrderTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = OutboundOrderTypeId.class, value = "Create a OutboundOrderTypeId") // label for swagger
	@PostMapping("/outboundordertypeid")
	public ResponseEntity<?> PostOutboundOrderTypeId(@Valid @RequestBody AddOutboundOrderTypeId newOutboundOrderTypeId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		OutboundOrderTypeId createOutboundOrderTypeId = idmasterService.createOutboundOrderTypeId(newOutboundOrderTypeId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createOutboundOrderTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = OutboundOrderTypeId.class, value = "Update OutboundOrderTypeId") // label for swagger
	@RequestMapping(value = "/outboundordertypeid/{outboundOrderTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateOutboundOrderTypeId(@RequestParam String warehouseId,@PathVariable String outboundOrderTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
													  @Valid @RequestBody UpdateOutboundOrderTypeId  updateOutboundOrderTypeId) throws IllegalAccessException, InvocationTargetException {
		OutboundOrderTypeId UpdateOutboundOrderTypeId =
				idmasterService.updateOutboundOrderTypeId(warehouseId,outboundOrderTypeId,companyCodeId,languageId,plantId,loginUserID,updateOutboundOrderTypeId,getAuthToken());
		return new ResponseEntity<>(UpdateOutboundOrderTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = OutboundOrderTypeId.class, value = "Delete OutboundOrderTypeId") // label for swagger
	@DeleteMapping("/outboundordertypeid/{outboundOrderTypeId}")
	public ResponseEntity<?> deleteOutboundOrderTypeId(@RequestParam String warehouseId,@PathVariable String outboundOrderTypeId,
													   @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deleteOutboundOrderTypeId(warehouseId,outboundOrderTypeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = OutboundOrderTypeId[].class, value = "Find OutboundOrderTypeId")//label for swagger
	@PostMapping("/outboundordertypeid/findOutboundOrderTypeId")
	public OutboundOrderTypeId[] findOutboundOrderTypeId(@RequestBody FindOutboundOrderTypeId findOutboundOrderTypeId) throws Exception {
		return idmasterService.findOutboundOrderTypeId(findOutboundOrderTypeId,getAuthToken());
	}

	/*
	 * --------------------------------InboundOrderTypeId---------------------------------
	 */
	@ApiOperation(response = InboundOrderTypeId[].class, value = "Get all InboundOrderTypeId details") // label for swagger
	@GetMapping("/inboundordertypeid")
	public ResponseEntity<?> getInboundOrderTypeIds() {
		InboundOrderTypeId[] userInboundOrderTypeId = idmasterService.getInboundOrderTypeIds(getAuthToken());
		return new ResponseEntity<>(userInboundOrderTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = InboundOrderTypeId.class, value = "Get a InboundOrderTypeId") // label for swagger
	@GetMapping("/inboundordertypeid/{inboundOrderTypeId}")
	public ResponseEntity<?>  getInboundOrderTypeId(@RequestParam String warehouseId,@PathVariable String inboundOrderTypeId,
													@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		InboundOrderTypeId dbInboundOrderTypeId= idmasterService.getInboundOrderTypeId(warehouseId,inboundOrderTypeId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbInboundOrderTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = InboundOrderTypeId.class, value = "Create a InboundOrderTypeId") // label for swagger
	@PostMapping("/inboundordertypeid")
	public ResponseEntity<?> PostInboundOrderTypeId(@Valid @RequestBody AddInboundOrderTypeId newInboundOrderTypeId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		InboundOrderTypeId createInboundOrderTypeId = idmasterService.createInboundOrderTypeId(newInboundOrderTypeId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createInboundOrderTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = InboundOrderTypeId.class, value = "Update InboundOrderTypeId") // label for swagger
	@RequestMapping(value = "/inboundordertypeid/{inboundOrderTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateInboundOrderTypeId(@RequestParam String warehouseId,@PathVariable String inboundOrderTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
													 @Valid @RequestBody UpdateInboundOrderTypeId updateInboundOrderTypeId) throws IllegalAccessException, InvocationTargetException {
		InboundOrderTypeId UpdateInboundOrderTypeId =
				idmasterService.updateInboundOrderTypeId(warehouseId,inboundOrderTypeId,companyCodeId,languageId,plantId,loginUserID,updateInboundOrderTypeId,getAuthToken());
		return new ResponseEntity<>(UpdateInboundOrderTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = InboundOrderTypeId.class, value = "Delete InboundOrderTypeId") // label for swagger
	@DeleteMapping("/inboundordertypeid/{inboundOrderTypeId}")
	public ResponseEntity<?> deleteInboundOrderTypeId(@RequestParam String warehouseId,@PathVariable String inboundOrderTypeId,@RequestParam String companyCodeId,
													  @RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deleteInboundOrderTypeId(warehouseId,inboundOrderTypeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = InboundOrderTypeId[].class, value = "Find InboundOrderTypeId")//label for swagger
	@PostMapping("/inboundordertypeid/findInboundOrderTypeId")
	public InboundOrderTypeId[] findInboundOrderTypeId(@RequestBody FindInboundOrderTypeId findInboundOrderTypeId) throws Exception {
		return idmasterService.findInboundOrderTypeId(findInboundOrderTypeId,getAuthToken());
	}

	/*
	 * --------------------------------TransferTypeId---------------------------------
	 */
	@ApiOperation(response = TransferTypeId[].class, value = "Get all TransferTypeId details") // label for swagger
	@GetMapping("/transfertypeid")
	public ResponseEntity<?> getTransferTypeIds() {
		TransferTypeId[] userTransferTypeId = idmasterService.getTransferTypeIds(getAuthToken());
		return new ResponseEntity<>(userTransferTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = TransferTypeId.class, value = "Get a TransferTypeId") // label for swagger
	@GetMapping("/transfertypeid/{transferTypeId}")
	public ResponseEntity<?>  getTransferTypeId(@RequestParam String warehouseId,@PathVariable String transferTypeId,
												@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		TransferTypeId dbTransferTypeId= idmasterService.getTransferTypeId(warehouseId,transferTypeId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbTransferTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = TransferTypeId.class, value = "Create a TransferTypeId") // label for swagger
	@PostMapping("/transfertypeid")
	public ResponseEntity<?> PostTransferTypeId(@Valid @RequestBody AddTransferTypeId newTransferTypeId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		TransferTypeId createTransferTypeId = idmasterService.createTransferTypeId(newTransferTypeId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createTransferTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = TransferTypeId.class, value = "Update TransferTypeId") // label for swagger
	@RequestMapping(value = "/transfertypeid/{transferTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateTransferTypeId(@RequestParam String warehouseId,@PathVariable String transferTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
												 @Valid @RequestBody UpdateTransferTypeId updateTransferTypeId) throws IllegalAccessException, InvocationTargetException {
		TransferTypeId UpdateTransferTypeId =
				idmasterService.updateTransferTypeId(warehouseId,transferTypeId,companyCodeId,languageId,plantId,loginUserID,updateTransferTypeId,getAuthToken());
		return new ResponseEntity<>(UpdateTransferTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = TransferTypeId.class, value = "Delete TransferTypeId") // label for swagger
	@DeleteMapping("/transfertypeid/{transferTypeId}")
	public ResponseEntity<?> deleteTransferTypeId(@RequestParam String warehouseId,@PathVariable String transferTypeId,
												  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId, @RequestParam String loginUserID) {
		idmasterService.deleteTransferTypeId(warehouseId,transferTypeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = TransferTypeId[].class, value = "Find TransferTypeId")//label for swagger
	@PostMapping("/transfertypeid/findTransferTypeId")
	public TransferTypeId[] findTransferTypeId(@RequestBody FindTransferTypeId findTransferTypeId) throws Exception {
		return idmasterService.findTransferTypeId(findTransferTypeId,getAuthToken());
	}

	/*
	 * --------------------------------StockTypeId---------------------------------
	 */
	@ApiOperation(response = StockTypeId[].class, value = "Get all StockTypeId details") // label for swagger
	@GetMapping("/stocktypeid")
	public ResponseEntity<?> getStockTypeIds() {
		StockTypeId[] userStockTypeId = idmasterService.getStockTypeIds(getAuthToken());
		return new ResponseEntity<>(userStockTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = StockTypeId.class, value = "Get a StockTypeId") // label for swagger
	@GetMapping("/stocktypeid/{stockTypeId}")
	public ResponseEntity<?>  getStockTypeId(@RequestParam String warehouseId,@PathVariable String stockTypeId,
											 @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		StockTypeId dbStockTypeId= idmasterService.getStockTypeId(warehouseId,stockTypeId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbStockTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = StockTypeId.class, value = "Create a StockTypeId") // label for swagger
	@PostMapping("/stocktypeid")
	public ResponseEntity<?> PostStockTypeId(@Valid @RequestBody AddStockTypeId newStockTypeId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		StockTypeId createStockTypeId = idmasterService.createStockTypeId(newStockTypeId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createStockTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = StockTypeId.class, value = "Update StockTypeId") // label for swagger
	@RequestMapping(value = "/stocktypeid/{stockTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateStockTypeId( @RequestParam String warehouseId,@PathVariable String stockTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
											   @Valid @RequestBody UpdateStockTypeId updateStockTypeId) throws IllegalAccessException, InvocationTargetException {
		StockTypeId UpdateStockTypeId =
				idmasterService.updateStockTypeId(warehouseId,stockTypeId,companyCodeId,languageId,plantId,loginUserID,updateStockTypeId,getAuthToken());
		return new ResponseEntity<>(UpdateStockTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = StockTypeId.class, value = "Delete StockTypeId") // label for swagger
	@DeleteMapping("/stocktypeid/{stockTypeId}")
	public ResponseEntity<?> deleteStockTypeId(@RequestParam String warehouseId,@PathVariable String stockTypeId,@RequestParam String companyCodeId,
											   @RequestParam String languageId,@RequestParam String plantId, @RequestParam String loginUserID) {
		idmasterService.deleteStockTypeId(warehouseId,stockTypeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = StockTypeId[].class, value = "Find StockTypeId")//label for swagger
	@PostMapping("/stocktypeid/findStockTypeId")
	public StockTypeId[] findStockTypeId(@RequestBody FindStockTypeId findStockTypeId) throws Exception {
		return idmasterService.findStockTypeId(findStockTypeId,getAuthToken());
	}

	/*
	 * --------------------------------SpecialStockIndicatorId---------------------------------
	 */
	@ApiOperation(response = SpecialStockIndicatorId[].class, value = "Get all SpecialStockIndicatorId details") // label for swagger
	@GetMapping("/specialstockindicatorid")
	public ResponseEntity<?> getSpecialStockIndicatorIds() {
		SpecialStockIndicatorId[] userSpecialStockIndicatorId= idmasterService.getSpecialStockIndicatorIds(getAuthToken());
		return new ResponseEntity<>(userSpecialStockIndicatorId, HttpStatus.OK);
	}

	@ApiOperation(response = SpecialStockIndicatorId.class, value = "Get a SpecialStockIndicatorId") // label for swagger
	@GetMapping("/specialstockindicatorid/{specialStockIndicatorId}")
	public ResponseEntity<?>  getSpecialStockIndicatorId(@RequestParam String warehouseId,@RequestParam String stockTypeId,@PathVariable String specialStockIndicatorId,
														 @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		SpecialStockIndicatorId dbSpecialStockIndicatorId= idmasterService.getSpecialStockIndicatorId(warehouseId,stockTypeId,specialStockIndicatorId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbSpecialStockIndicatorId, HttpStatus.OK);
	}

	@ApiOperation(response = SpecialStockIndicatorId.class, value = "Create a SpecialStockIndicatorId") // label for swagger
	@PostMapping("/specialstockindicatorid")
	public ResponseEntity<?> PostSpecialStockIndicatorId(@Valid @RequestBody AddSpecialStockIndicatorId newSpecialStockIndicatorId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		SpecialStockIndicatorId createSpecialStockIndicatorId = idmasterService.createSpecialStockIndicatorId(newSpecialStockIndicatorId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createSpecialStockIndicatorId, HttpStatus.OK);
	}

	@ApiOperation(response = SpecialStockIndicatorId.class, value = "Update SpecialStockIndicatorId") // label for swagger
	@RequestMapping(value = "/specialstockindicatorid/{specialStockIndicatorId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateSpecialStockIndicatorId(@RequestParam String warehouseId,@RequestParam String stockTypeId,
														  @PathVariable String specialStockIndicatorId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
														  @Valid @RequestBody UpdateSpecialStockIndicatorId updateSpecialStockIndicatorId) throws IllegalAccessException, InvocationTargetException {
		SpecialStockIndicatorId UpdateSpecialStockIndicatorId =
				idmasterService.updateSpecialStockIndicatorId(warehouseId,stockTypeId,specialStockIndicatorId,companyCodeId,languageId,plantId,loginUserID,updateSpecialStockIndicatorId,getAuthToken());
		return new ResponseEntity<>(UpdateSpecialStockIndicatorId, HttpStatus.OK);
	}

	@ApiOperation(response = SpecialStockIndicatorId.class, value = "Delete SpecialStockIndicatorId") // label for swagger
	@DeleteMapping("/specialstockindicatorid/{specialStockIndicatorId}")
	public ResponseEntity<?> deleteSpecialStockIndicatorId(@RequestParam String warehouseId,@RequestParam String stockTypeId,
														   @PathVariable String specialStockIndicatorId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
														   @RequestParam String loginUserID) {
		idmasterService.deleteSpecialStockIndicatorId(warehouseId,stockTypeId,specialStockIndicatorId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = SpecialStockIndicatorId[].class, value = "Find SpecialStockIndicatorId")//label for swagger
	@PostMapping("/specialstockindicatorid/findSpecialStockIndicatorId")
	public SpecialStockIndicatorId[] findSpecialStockIndicatorId(@RequestBody FindSpecialStockIndicatorId findSpecialStockIndicatorId)throws Exception{
		return idmasterService.findSpecialStockIndicatorId(findSpecialStockIndicatorId,getAuthToken());
	}


	/*
	 * --------------------------------HandlingEquipmentId---------------------------------
	 */

	@ApiOperation(response = HandlingEquipmentId[].class, value = "Get all HandlingEquipmentId details") // label for swagger
	@GetMapping("/handlingequipmentid")
	public ResponseEntity<?> getHandlingEquipmentIds() {
		HandlingEquipmentId[] userHandlingEquipmentId= idmasterService.getHandlingEquipmentIds(getAuthToken());
		return new ResponseEntity<>(userHandlingEquipmentId, HttpStatus.OK);
	}

	@ApiOperation(response = SpecialStockIndicatorId.class, value = "Get a HandlingEquipmentId") // label for swagger
	@GetMapping("/handlingequipmentid/{handlingEquipmentNumber}")
	public ResponseEntity<?>  getHandlingEquipmentId( @RequestParam String warehouseId,@PathVariable Long handlingEquipmentNumber,
													  @RequestParam String companyCodeId,@RequestParam String languageId, @RequestParam String plantId) {

		HandlingEquipmentId dbHandlingEquipmentId=
				idmasterService.getHandlingEquipmentId(warehouseId,handlingEquipmentNumber,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbHandlingEquipmentId, HttpStatus.OK);
	}


	@ApiOperation(response = HandlingEquipmentId.class, value = "Create a HandlingEquipmentId") // label for swagger
	@PostMapping("/handlingequipmentid")
	public ResponseEntity<?> PostHandlingEquipmentId(@Valid @RequestBody AddHandlingEquipmentId newHandlingEquipmentId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		HandlingEquipmentId createHandlingEquipmentId = idmasterService.createHandlingEquipmentId(newHandlingEquipmentId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createHandlingEquipmentId, HttpStatus.OK);
	}

	@ApiOperation(response = HandlingEquipmentId.class, value = "Update HandlingEquipmentId") // label for swagger
	@RequestMapping(value = "/handlingequipmentid/{handlingEquipmentNumber}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateHandlingEquipmentId(@RequestParam String warehouseId,@PathVariable Long handlingEquipmentNumber,
													  @RequestParam String companyCodeId,@RequestParam String languageId,
													  @RequestParam String plantId,@RequestParam String loginUserID,
													  @Valid @RequestBody UpdateHandlingEquipmentId updateHandlingEquipmentId) throws IllegalAccessException, InvocationTargetException {
		HandlingEquipmentId UpdateHandlingEquipmentId =
				idmasterService.updateHandlingEquipmentId(warehouseId,handlingEquipmentNumber,companyCodeId,languageId,
						plantId,loginUserID,updateHandlingEquipmentId,getAuthToken());

		return new ResponseEntity<>(UpdateHandlingEquipmentId, HttpStatus.OK);
	}

	@ApiOperation(response = HandlingEquipmentId.class, value = "Delete HandlingEquipmentId") // label for swagger
	@DeleteMapping("/handlingequipmentid/{handlingEquipmentNumber}")
	public ResponseEntity<?> deleteHandlingEquipmentId(@RequestParam String warehouseId,@PathVariable Long handlingEquipmentNumber,
													   @RequestParam String companyCodeId,@RequestParam String languageId,
													   @RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deleteHandlingEquipmentId(warehouseId,handlingEquipmentNumber,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = HandlingEquipmentId[].class, value = "Find HandlingEquipmentId")//label for swagger
	@PostMapping("/handlingequipmentid/findHandlingEquipmentId")
	public HandlingEquipmentId[] findHandlingEquipmentId(@RequestBody FindHandlingEquipmentId findHandlingEquipmentId) throws Exception {
		return idmasterService.findHandlingEquipmentId(findHandlingEquipmentId,getAuthToken());
	}

	/*
	 * --------------------------------HandlingUnitId---------------------------------
	 */
	@ApiOperation(response = HandlingUnitId[].class, value = "Get all HandlingUnitId details") // label for swagger
	@GetMapping("/handlingunitid")
	public ResponseEntity<?> getHandlingUnitIds() {
		HandlingUnitId[] userHandlingUnitId= idmasterService.getHandlingUnitIds(getAuthToken());
		return new ResponseEntity<>(userHandlingUnitId, HttpStatus.OK);
	}

	@ApiOperation(response = HandlingUnitId.class, value = "Get a HandlingUnitId") // label for swagger
	@GetMapping("/handlingunitid/{handlingUnitNumber}")
	public ResponseEntity<?>  getHandlingUnitId(@RequestParam String warehouseId,@PathVariable String handlingUnitNumber,
												@RequestParam String companyCodeId,@RequestParam String languageId,
												@RequestParam String plantId) {

		HandlingUnitId dbHandlingUnitId =
				idmasterService.getHandlingUnitId(warehouseId,handlingUnitNumber,companyCodeId,
						languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbHandlingUnitId, HttpStatus.OK);
	}

	@ApiOperation(response = HandlingUnitId.class, value = "Create a HandlingUnitId") // label for swagger
	@PostMapping("/handlingunitid")
	public ResponseEntity<?> PostHandlingUnitId(@Valid @RequestBody AddHandlingUnitId newHandlingUnitId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		HandlingUnitId createHandlingUnitId = idmasterService.createHandlingUnitId(newHandlingUnitId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createHandlingUnitId, HttpStatus.OK);
	}

	@ApiOperation(response = HandlingUnitId.class, value = "Update HandlingUnitId") // label for swagger
	@RequestMapping(value = "/handlingunitid/{handlingUnitNumber}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateHandlingUnitId(@RequestParam String warehouseId,@PathVariable String handlingUnitNumber,
												 @RequestParam String companyCodeId,@RequestParam String languageId,
												 @RequestParam String plantId,@RequestParam String loginUserID,
												 @Valid @RequestBody UpdateHandlingUnitId updateHandlingUnitId) throws IllegalAccessException, InvocationTargetException {

		HandlingUnitId UpdateHandlingUnitId =
				idmasterService.updateHandlingUnitId(warehouseId,handlingUnitNumber,companyCodeId,languageId,
						plantId,loginUserID,updateHandlingUnitId,getAuthToken());

		return new ResponseEntity<>(UpdateHandlingUnitId, HttpStatus.OK);
	}

	@ApiOperation(response = HandlingUnitId.class, value = "Delete HandlingUnitId") // label for swagger
	@DeleteMapping("/handlingunitid/{handlingUnitNumber}")
	public ResponseEntity<?> deleteHandlingUnitId(@RequestParam String warehouseId,@PathVariable String handlingUnitNumber, @RequestParam String companyCodeId,
												  @RequestParam String languageId,@RequestParam String plantId, @RequestParam String loginUserID) {

		idmasterService.deleteHandlingUnitId(warehouseId,handlingUnitNumber,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = HandlingUnitId[].class, value = "Find HandlingUnitId")//label for swagger
	@PostMapping("/handlingunitid/findHandlingUnitId")
	public HandlingUnitId[] findHandlingUnitId(@RequestBody FindHandlingUnitId findHandlingUnitId) throws Exception {
		return idmasterService.findHandlingUnitId(findHandlingUnitId,getAuthToken());
	}


	/*
	 * --------------------------------CycleCountTypeId---------------------------------
	 */
	@ApiOperation(response = CycleCountTypeId[].class, value = "Get all CycleCountTypeId details") // label for swagger
	@GetMapping("/cyclecounttypeid")
	public ResponseEntity<?> getCycleCountTypeIds() {
		CycleCountTypeId[] userCycleCountTypeId= idmasterService.getCycleCountTypeIds(getAuthToken());
		return new ResponseEntity<>(userCycleCountTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = CycleCountTypeId.class, value = "Get a CycleCountTypeId") // label for swagger
	@GetMapping("/cyclecounttypeid/{cycleCountTypeId}")
	public ResponseEntity<?>  getCycleCountTypeId(@RequestParam String warehouseId,@PathVariable String cycleCountTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		CycleCountTypeId dbCycleCountTypeId= idmasterService.getCycleCountTypeId(warehouseId,cycleCountTypeId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbCycleCountTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = CycleCountTypeId.class, value = "Create a CycleCountTypeId") // label for swagger
	@PostMapping("/cyclecounttypeid")
	public ResponseEntity<?> PostCycleCountTypeId(@Valid @RequestBody AddCycleCountTypeId newCycleCountTypeId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		CycleCountTypeId createCycleCountTypeId = idmasterService.createCycleCountTypeId(newCycleCountTypeId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createCycleCountTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = CycleCountTypeId.class, value = "Update CycleCountTypeId") // label for swagger
	@RequestMapping(value = "/cyclecounttypeid/{cycleCountTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateCycleCountTypeId(@RequestParam String warehouseId,@PathVariable String cycleCountTypeId,
												   @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
												   @Valid @RequestBody UpdateCycleCountTypeId updateCycleCountTypeId) throws IllegalAccessException, InvocationTargetException {
		CycleCountTypeId UpdateCycleCountTypeId =
				idmasterService.updateCycleCountTypeId(warehouseId,cycleCountTypeId,companyCodeId,languageId,plantId,loginUserID,updateCycleCountTypeId,getAuthToken());
		return new ResponseEntity<>(UpdateCycleCountTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = CycleCountTypeId.class, value = "Delete CycleCountTypeId") // label for swagger
	@DeleteMapping("/cyclecounttypeid/{cycleCountTypeId}")
	public ResponseEntity<?> deleteCycleCountTypeId(@RequestParam String warehouseId,@PathVariable String cycleCountTypeId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
													@RequestParam String loginUserID) {
		idmasterService.deleteCycleCountTypeId(warehouseId,cycleCountTypeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = CycleCountTypeId[].class, value = "Find CycleCountTypeId")//label for swagger
	@PostMapping("/cyclecounttypeid/findCycleCountTypeId")
	public CycleCountTypeId[] findCycleCountTypeId(@RequestBody FindCycleCountTypeId findCycleCountTypeId) throws Exception {
		return idmasterService.findCycleCountTypeId(findCycleCountTypeId,getAuthToken());
	}


	/*
	 * --------------------------------ReturnTypeId---------------------------------
	 */
	@ApiOperation(response = ReturnTypeId[].class, value = "Get all ReturnTypeId details") // label for swagger
	@GetMapping("/returntypeid")
	public ResponseEntity<?> getReturnTypeIds() {
		ReturnTypeId[] userReturnTypeId= idmasterService.getReturnTypeIds(getAuthToken());
		return new ResponseEntity<>(userReturnTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = ReturnTypeId.class, value = "Get a ReturnTypeId") // label for swagger
	@GetMapping("/returntypeid/{returnTypeId}")
	public ResponseEntity<?>  getReturnTypeId(@RequestParam String warehouseId,@PathVariable String returnTypeId,@RequestParam String companyCodeId,
											  @RequestParam String languageId,@RequestParam String plantId) {
		ReturnTypeId dbReturnTypeId= idmasterService.getReturnTypeId(warehouseId,returnTypeId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbReturnTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = ReturnTypeId.class, value = "Create a ReturnTypeId") // label for swagger
	@PostMapping("/returntypeid")
	public ResponseEntity<?> PostReturnTypeId(@Valid @RequestBody AddReturnTypeId newReturnTypeId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		ReturnTypeId createReturnTypeId = idmasterService.createReturnTypeId(newReturnTypeId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createReturnTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = ReturnTypeId.class, value = "Update ReturnTypeId") // label for swagger
	@RequestMapping(value = "/returntypeid/{returnTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateReturnTypeId(@RequestParam String warehouseId,@PathVariable String returnTypeId,@RequestParam String companyCodeId,
											   @RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
											   @Valid @RequestBody UpdateReturnTypeId updateReturnTypeId) throws IllegalAccessException, InvocationTargetException {
		ReturnTypeId UpdateReturnTypeId =
				idmasterService.updateReturnTypeId(warehouseId,returnTypeId,companyCodeId,languageId,plantId,loginUserID,updateReturnTypeId,getAuthToken());
		return new ResponseEntity<>(UpdateReturnTypeId, HttpStatus.OK);
	}

	@ApiOperation(response = ReturnTypeId.class, value = "Delete ReturnTypeId") // label for swagger
	@DeleteMapping("/returntypeid/{returnTypeId}")
	public ResponseEntity<?> deleteReturnTypeId(@RequestParam String warehouseId,@PathVariable String returnTypeId,@RequestParam String companyCodeId,
												@RequestParam String languageId,@RequestParam String plantId, @RequestParam String loginUserID) {
		idmasterService.deleteReturnTypeId(warehouseId,returnTypeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = ReturnTypeId[].class, value = "Find ReturnTypeId")//label for swagger
	@PostMapping("/returntypeid/findReturnTypeId")
	public ReturnTypeId[] findReturnTypeId(@RequestBody FindReturnTypeId findReturnTypeId) throws Exception {
		return idmasterService.findReturnTypeId(findReturnTypeId,getAuthToken());
	}


	/*
	 * --------------------------------ApprovalProcessId---------------------------------
	 */
	@ApiOperation(response = ApprovalProcessId[].class, value = "Get all ApprovalProcessId details") // label for swagger
	@GetMapping("/approvalprocessid")
	public ResponseEntity<?> getApprovalProcessIds() {
		ApprovalProcessId[] userApprovalProcessId= idmasterService.getApprovalProcessIds(getAuthToken());
		return new ResponseEntity<>(userApprovalProcessId, HttpStatus.OK);
	}

	@ApiOperation(response = ApprovalProcessId.class, value = "Get a ApprovalProcessId") // label for swagger
	@GetMapping("/approvalprocessid/{approvalProcessId}")
	public ResponseEntity<?>  getApprovalProcessId(@RequestParam String warehouseId,@PathVariable String approvalProcessId,@RequestParam String companyCodeId,
												   @RequestParam String languageId,@RequestParam String plantId) {
		ApprovalProcessId dbApprovalProcessId= idmasterService.getApprovalProcessId(warehouseId,approvalProcessId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbApprovalProcessId, HttpStatus.OK);
	}

	@ApiOperation(response = ApprovalProcessId.class, value = "Create a ApprovalProcessId") // label for swagger
	@PostMapping("/approvalprocessid")
	public ResponseEntity<?> PostApprovalProcessId(@Valid @RequestBody AddApprovalProcessId newApprovalProcessId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		ApprovalProcessId createApprovalProcessId = idmasterService.createApprovalProcessId(newApprovalProcessId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createApprovalProcessId, HttpStatus.OK);
	}

	@ApiOperation(response = ApprovalProcessId.class, value = "Update ApprovalProcessId") // label for swagger
	@RequestMapping(value = "/approvalprocessid/{approvalProcessId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateApprovalProcessId(@RequestParam String warehouseId,@PathVariable String approvalProcessId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
													@Valid @RequestBody UpdateApprovalProcessId updateApprovalProcessId) throws IllegalAccessException, InvocationTargetException {
		ApprovalProcessId UpdateApprovalProcessId =
				idmasterService.updateApprovalProcessId(warehouseId,approvalProcessId,companyCodeId,languageId,plantId,loginUserID,updateApprovalProcessId,getAuthToken());
		return new ResponseEntity<>(UpdateApprovalProcessId, HttpStatus.OK);
	}

	@ApiOperation(response = ApprovalProcessId.class, value = "Delete ApprovalProcessId") // label for swagger
	@DeleteMapping("/approvalprocessid/{approvalProcessId}")
	public ResponseEntity<?> deleteApprovalProcessId(@RequestParam String warehouseId,@PathVariable String approvalProcessId,@RequestParam String companyCodeId,
													 @RequestParam String languageId,@RequestParam String plantId, @RequestParam String loginUserID) {
		idmasterService.deleteApprovalProcessId(warehouseId,approvalProcessId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = ApprovalProcessId[].class, value = "Find ApprovalProcessId")//label for swagger
	@PostMapping("/approvalprocessid/findApprovalProcessId")
	public ApprovalProcessId[] findApprovalProcessId(@RequestBody FindApprovalProcessId findApprovalProcessId)throws Exception{
		return idmasterService.findApprovalProcessId(findApprovalProcessId,getAuthToken());
	}


	/*
	 * --------------------------------ProcessId---------------------------------
	 */
	@ApiOperation(response = ProcessId[].class, value = "Get all ProcessId details") // label for swagger
	@GetMapping("/processid")
	public ResponseEntity<?> getProcessIds() {
		ProcessId[] userProcessId= idmasterService.getProcessIds(getAuthToken());
		return new ResponseEntity<>(userProcessId, HttpStatus.OK);
	}

	@ApiOperation(response = ProcessId.class, value = "Get a ProcessId") // label for swagger
	@GetMapping("/processid/{processId}")
	public ResponseEntity<?>  getProcessId(@RequestParam String warehouseId,@PathVariable String processId,@RequestParam String companyCodeId,
										   @RequestParam String languageId, @RequestParam String plantId) {
		ProcessId dbProcessId= idmasterService.getProcessId(warehouseId,processId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbProcessId, HttpStatus.OK);
	}

	@ApiOperation(response = ProcessId.class, value = "Create a ProcessId") // label for swagger
	@PostMapping("/processid")
	public ResponseEntity<?> PostprocessId(@Valid @RequestBody AddProcessId newProcessId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		ProcessId createProcessId = idmasterService.createProcessId(newProcessId,loginUserID,getAuthToken());
		return new ResponseEntity<>(createProcessId, HttpStatus.OK);
	}

	@ApiOperation(response = ProcessId.class, value = "Update ProcessId") // label for swagger
	@RequestMapping(value = "/processid/{processId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateProcessId(@RequestParam String warehouseId,@PathVariable String processId,
											@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID,
											@Valid @RequestBody UpdateProcessId updateProcessId) throws IllegalAccessException, InvocationTargetException {
		ProcessId UpdateProcessId =
				idmasterService.updateProcessId(warehouseId,processId,companyCodeId,languageId,plantId,loginUserID,updateProcessId,getAuthToken());
		return new ResponseEntity<>(UpdateProcessId, HttpStatus.OK);
	}

	@ApiOperation(response = ProcessId.class, value = "Delete ProcessId") // label for swagger
	@DeleteMapping("/processid/{processId}")
	public ResponseEntity<?> deleteProcessId(@RequestParam String warehouseId,@PathVariable String processId,@RequestParam String companyCodeId,
											 @RequestParam String languageId, @RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deleteProcessId(warehouseId,processId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = ProcessId[].class, value = "Find ProcessId")//label for swagger
	@PostMapping("/processid/findProcessId")
	public ProcessId[] findProcessId(@RequestBody FindProcessId findProcessId) throws Exception {
		return idmasterService.findProcessId(findProcessId,getAuthToken());
	}

	/*
	 * --------------------------------SubLevelId---------------------------------
	 */
	@ApiOperation(response = SubLevelId[].class, value = "Get all SubLevelId details") // label for swagger
	@GetMapping("/sublevelid")
	public ResponseEntity<?> getSubLevelIds() {
		SubLevelId[] userSubLevelId= idmasterService.getSubLevelIds(getAuthToken());
		return new ResponseEntity<>(userSubLevelId, HttpStatus.OK);
	}

	@ApiOperation(response = SubLevelId.class, value = "Get a SubLevelId") // label for swagger
	@GetMapping("/sublevelid/{subLevelId}")
	public ResponseEntity<?>  getSubLevelId(@RequestParam String warehouseId,@PathVariable String subLevelId,@RequestParam Long levelId,
											@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		SubLevelId dbSubLevelId= idmasterService.getSubLevelId(warehouseId, subLevelId,levelId,companyCodeId,languageId,plantId,getAuthToken());
		return new ResponseEntity<>(dbSubLevelId, HttpStatus.OK);
	}

	@ApiOperation(response = SubLevelId.class, value = "Create a SubLevelId") // label for swagger
	@PostMapping("/sublevelid")
	public ResponseEntity<?> PostSubLevelId(@Valid @RequestBody AddSubLevelId newSubLevelId, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		SubLevelId CreateSubLevelId = idmasterService.createSubLevelId(newSubLevelId, loginUserID,getAuthToken());
		return new ResponseEntity<>(CreateSubLevelId, HttpStatus.OK);
	}

	@ApiOperation(response = SubLevelId.class, value = "Update SubLevelId") // label for swagger
	@RequestMapping(value = "/sublevelid/{subLevelId}", method = RequestMethod.PATCH)
	public ResponseEntity<?>updateSubLevelId(@RequestParam String warehouseId,@PathVariable String subLevelId,@RequestParam Long levelId,@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,
											 @RequestParam String loginUserID, @Valid @RequestBody UpdateSubLevelId updateSubLevelId) throws IllegalAccessException, InvocationTargetException {
		SubLevelId UpdateSubLevelId = idmasterService.updateSubLevelId(warehouseId, subLevelId,levelId,companyCodeId,languageId,plantId,loginUserID,updateSubLevelId,getAuthToken());
		return new ResponseEntity<>(UpdateSubLevelId, HttpStatus.OK);
	}

	@ApiOperation(response = SubLevelId.class, value = "Delete SubLevelId") // label for swagger
	@DeleteMapping("/sublevelid/{subLevelId}")
	public ResponseEntity<?> deleteSubLevelId(@RequestParam String warehouseId,@PathVariable String subLevelId,@RequestParam Long levelId,
											  @RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID) {
		idmasterService.deleteSubLevelId(warehouseId, subLevelId, levelId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//FIND
	@ApiOperation(response = SubLevelId[].class, value = "Find SubLevelId")//label for swagger
	@PostMapping("/sublevelid/findSubLevelId")
	public SubLevelId[] findSubLevelId(@RequestBody FindSubLevelId findSubLevelId) throws Exception {
		return idmasterService.findSubLevelId(findSubLevelId,getAuthToken());
	}

	//ThreePL

	/* --------------------------------BillingFormatId-------------------------------------------------------------------------*/
	@ApiOperation(response = BillingFormatId.class, value = "Get All BillingFormatIds") // label for swagger
	@RequestMapping(value = "/billingformatid", method = RequestMethod.GET)
	public ResponseEntity<?> getBillingFormatIds() {
		BillingFormatId[] billingFormatId = idmasterService.getBillingFormatIds(getAuthToken());
		return new ResponseEntity<>(billingFormatId, HttpStatus.OK);
	}
	@ApiOperation(response = BillingFormatId.class, value = "Get BillingFormatId") // label for swagger
	@RequestMapping(value = "/billingformatid/{billFormatId}", method = RequestMethod.GET)
	public ResponseEntity<?> getBillingFormatId(@RequestParam String warehouseId,@PathVariable Long billFormatId,
												@RequestParam String companyCodeId,@RequestParam String languageId,@RequestParam String plantId) {
		BillingFormatId billingFormatId = idmasterService.getBillingFormatId(warehouseId,billFormatId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("BillingFormatId::: " + billingFormatId);
		return new ResponseEntity<>(billingFormatId, HttpStatus.OK);
	}
	@ApiOperation(response = BillingFormatId.class, value = "Create new BillingFormatId") // label for swagger
	@RequestMapping(value = "/billingformatid", method = RequestMethod.POST)
	public ResponseEntity<?> createBillingFormatId(@RequestParam String loginUserID, @RequestBody AddBillingFormatId newBillingFormatId) {
		BillingFormatId billingFormatId = idmasterService.addBillingFormatId(newBillingFormatId,loginUserID, getAuthToken());
		return new ResponseEntity<>(billingFormatId, HttpStatus.OK);
	}
	@ApiOperation(response = BillingFormatId.class, value = "Update BillingFormatId") // label for swagger
	@RequestMapping(value = "/billingformatid/{billFormatId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateBillingFormatId(@PathVariable Long billFormatId,@RequestParam  String loginUserID,@RequestParam String companyCodeId,@RequestParam String languageId,
												   @RequestParam String plantId, @RequestParam String warehouseId,@RequestBody UpdateBillingFormatId updatedBillingFormatId) {
		BillingFormatId billingFormatId = idmasterService.updateBillingFormatId(warehouseId,billFormatId,companyCodeId,languageId,plantId,loginUserID, updatedBillingFormatId,getAuthToken());
		return new ResponseEntity<>(billingFormatId, HttpStatus.OK);
	}
	@ApiOperation(response = BillingFormatId.class, value = "Delete BillingFormatId") // label for swagger
	@RequestMapping(value = "/billingformatid/{billFormatId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBillingFormatId(@PathVariable Long billFormatId,@RequestParam String loginUserID,@RequestParam String companyCodeId,
												   @RequestParam String languageId,@RequestParam String plantId,@RequestParam String warehouseId) {
		idmasterService.deleteBillingFormatId(warehouseId, billFormatId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>( HttpStatus.NO_CONTENT);
	}
	//FIND
	@ApiOperation(response = BillingFormatId[].class, value = "Find BillingFormatId")//label for swagger
	@PostMapping("/billingformatid/findBillingFormatId")
	public BillingFormatId[] findBillingFormatId(@RequestBody FindBillingFormatId findBillingFormatId) throws Exception {
		return idmasterService.findBillingFormatId(findBillingFormatId,getAuthToken());
	}
	/* --------------------------------BillingFrequencyId-------------------------------------------------------------------------*/
	@ApiOperation(response = BillingFrequencyId.class, value = "Get All BillingFrequencyIds") // label for swagger
	@RequestMapping(value = "/billingfrequencyid", method = RequestMethod.GET)
	public ResponseEntity<?> getBillingFrequencyIds() {
		BillingFrequencyId[] billingFrequencyId = idmasterService.getBillingFrequencyIds(getAuthToken());
		return new ResponseEntity<>(billingFrequencyId, HttpStatus.OK);
	}
	@ApiOperation(response = BillingFrequencyId.class, value = "Get BillingFrequencyId") // label for swagger
	@RequestMapping(value = "/billingfrequencyid/{billFrequencyId}", method = RequestMethod.GET)
	public ResponseEntity<?> getBillingFrequencyId(@RequestParam String warehouseId,@PathVariable Long billFrequencyId,@RequestParam String companyCodeId,
												   @RequestParam String languageId,@RequestParam String plantId) {
		BillingFrequencyId billingFrequencyId = idmasterService.getBillingFrequencyId(warehouseId,billFrequencyId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("BillingFrequencyId::: " + billingFrequencyId);
		return new ResponseEntity<>(billingFrequencyId, HttpStatus.OK);
	}
	@ApiOperation(response = BillingFrequencyId.class, value = "Create new BillingFrequencyId") // label for swagger
	@RequestMapping(value = "/billingfrequencyid", method = RequestMethod.POST)
	public ResponseEntity<?> createBillingFrequencyId(@RequestParam String loginUserID, @RequestBody AddBillingFrequencyId newBillingFrequencyId) {
		BillingFrequencyId billingFrequencyId = idmasterService.addBillingFrequencyId(newBillingFrequencyId,loginUserID, getAuthToken());
		return new ResponseEntity<>(billingFrequencyId, HttpStatus.OK);
	}
	@ApiOperation(response = BillingFrequencyId.class, value = "Update BillingFrequencyId") // label for swagger
	@RequestMapping(value = "/billingfrequencyid/{billFrequencyId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateBillingFrequencyId(@PathVariable Long billFrequencyId,@RequestParam  String loginUserID,@RequestParam String companyCodeId,
													  @RequestParam String languageId,@RequestParam String plantId, @RequestParam String warehouseId,@RequestBody UpdateBillingFrequencyId updateBillingFrequencyId ) {
		BillingFrequencyId billingFrequencyId = idmasterService.updateBillingFrequencyId(warehouseId, billFrequencyId,companyCodeId,languageId,plantId,loginUserID, updateBillingFrequencyId,getAuthToken());
		return new ResponseEntity<>(billingFrequencyId, HttpStatus.OK);
	}
	@ApiOperation(response = BillingFrequencyId.class, value = "Delete BillingFrequencyId") // label for swagger
	@RequestMapping(value = "/billingfrequencyid/{billFrequencyId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBillingFrequencyId(@PathVariable Long billFrequencyId,@RequestParam String loginUserID,@RequestParam String companyCodeId,
													  @RequestParam String languageId,@RequestParam String plantId,@RequestParam String warehouseId) {
		idmasterService.deleteBillingFrequencyId(warehouseId, billFrequencyId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//FIND
	@ApiOperation(response = BillingFrequencyId[].class, value = "Find BillingFrequencyId")//label for swagger
	@PostMapping("/billingfrequencyid/findBillingFrequencyId")
	public BillingFrequencyId[] findBillingFrequencyId(@RequestBody FindBillingFrequencyId findBillingFrequencyId) throws Exception {
		return idmasterService.findBillingFrequencyId(findBillingFrequencyId,getAuthToken());
	}
	/* --------------------------------BillingModeId-------------------------------------------------------------------------*/
	@ApiOperation(response = BillingModeId.class, value = "Get All BillingModeIds") // label for swagger
	@RequestMapping(value = "/billingmodeid", method = RequestMethod.GET)
	public ResponseEntity<?> getBillingModeIds() {
		BillingModeId[] billingModeId = idmasterService.getBillingModeIds(getAuthToken());
		return new ResponseEntity<>(billingModeId, HttpStatus.OK);
	}
	@ApiOperation(response = BillingModeId.class, value = "Get BillingModeId") // label for swagger
	@RequestMapping(value = "/billingmodeid/{billModeId}", method = RequestMethod.GET)
	public ResponseEntity<?> getBillingModeId(@RequestParam String warehouseId,@PathVariable Long billModeId,@RequestParam String companyCodeId,
											  @RequestParam String languageId,@RequestParam String plantId) {
		BillingModeId billingModeId = idmasterService.getBillingModeId(warehouseId,billModeId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("BillingModeId::: " + billingModeId);
		return new ResponseEntity<>(billingModeId, HttpStatus.OK);
	}
	@ApiOperation(response = BillingModeId.class, value = "Create new BillingModeId") // label for swagger
	@RequestMapping(value = "/billingmodeid", method = RequestMethod.POST)
	public ResponseEntity<?> createBillingModeId(@RequestParam String loginUserID, @RequestBody AddBillingModeId newBillingModeId) {
		BillingModeId billingModeId = idmasterService.addBillingModeId(newBillingModeId,loginUserID, getAuthToken());
		return new ResponseEntity<>(billingModeId, HttpStatus.OK);
	}
	@ApiOperation(response = BillingModeId.class, value = "Update BillingModeId") // label for swagger
	@RequestMapping(value = "/billingmodeid/{billModeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateBillingModeId(@PathVariable Long billModeId,@RequestParam String loginUserID,@RequestParam String companyCodeId,
												 @RequestParam String languageId,@RequestParam String plantId, @RequestParam String warehouseId,@RequestBody UpdateBillingModeId updateBillingModeId ) {
		BillingModeId billingModeId = idmasterService.updateBillingModeId(warehouseId, billModeId,companyCodeId,languageId,plantId,loginUserID, updateBillingModeId,getAuthToken());
		return new ResponseEntity<>(billingModeId, HttpStatus.OK);
	}
	@ApiOperation(response = BillingModeId.class, value = "Delete BillingModeId") // label for swagger
	@RequestMapping(value = "/billingmodeid/{billModeId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBillingModeId(@PathVariable Long billModeId,@RequestParam String companyCodeId,@RequestParam String languageId,
												 @RequestParam String plantId,@RequestParam String loginUserID,@RequestParam String warehouseId) {
		idmasterService.deleteBillingModeId(warehouseId, billModeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//FIND
	@ApiOperation(response = BillingModeId[].class, value = "Find BillingModeId")//label for swagger
	@PostMapping("/billingmodeid/findBillingModeId")
	public BillingModeId[] findBillingModeId(@RequestBody FindBillingModeId findBillingModeId) throws Exception {
		return idmasterService.findBillingModeId(findBillingModeId,getAuthToken());
	}
	/* --------------------------------PaymentModeId-------------------------------------------------------------------------*/
	@ApiOperation(response = PaymentModeId.class, value = "Get All PaymentModeIds") // label for swagger
	@RequestMapping(value = "/paymentmodeid", method = RequestMethod.GET)
	public ResponseEntity<?> getPaymentModeIds() {
		PaymentModeId[] PaymentModeId = idmasterService.getPaymentModeIds(getAuthToken());
		return new ResponseEntity<>(PaymentModeId, HttpStatus.OK);
	}
	@ApiOperation(response = PaymentModeId.class, value = "Get PaymentModeId") // label for swagger
	@RequestMapping(value = "/paymentmodeid/{paymentModeId}", method = RequestMethod.GET)
	public ResponseEntity<?> getPaymentModeId(@RequestParam String warehouseId,@PathVariable Long paymentModeId,@RequestParam String companyCodeId,
											  @RequestParam String languageId,@RequestParam String plantId) {
		PaymentModeId PaymentModeId = idmasterService.getPaymentModeId(warehouseId,paymentModeId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("PaymentModeId::: " + PaymentModeId);
		return new ResponseEntity<>(PaymentModeId, HttpStatus.OK);
	}
	@ApiOperation(response = PaymentModeId.class, value = "Create new PaymentModeId") // label for swagger
	@RequestMapping(value = "/paymentmodeid", method = RequestMethod.POST)
	public ResponseEntity<?> createPaymentModeId(@RequestParam String loginUserID, @RequestBody AddPaymentModeId newPaymentModeId) {
		PaymentModeId PaymentModeId = idmasterService.addPaymentModeId(newPaymentModeId,loginUserID, getAuthToken());
		return new ResponseEntity<>(PaymentModeId, HttpStatus.OK);
	}
	@ApiOperation(response = PaymentModeId.class, value = "Update PaymentModeId") // label for swagger
	@RequestMapping(value = "/paymentmodeid/{paymentModeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updatePaymentModeId(@PathVariable Long paymentModeId,@RequestParam  String loginUserID, @RequestParam String companyCodeId,
												 @RequestParam String languageId,@RequestParam String plantId,@RequestParam String warehouseId,@RequestBody UpdatePaymentModeId updatePaymentModeId ) {
		PaymentModeId PaymentModeId = idmasterService.updatePaymentModeId(warehouseId, paymentModeId,companyCodeId,languageId,plantId,loginUserID, updatePaymentModeId,getAuthToken());
		return new ResponseEntity<>(PaymentModeId, HttpStatus.OK);
	}
	@ApiOperation(response = PaymentModeId.class, value = "Delete PaymentModeId") // label for swagger
	@RequestMapping(value = "/paymentmodeid/{paymentModeId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePaymentModeId(@PathVariable Long paymentModeId,@RequestParam String loginUserID,@RequestParam String companyCodeId,
												 @RequestParam String languageId,@RequestParam String plantId,@RequestParam String warehouseId) {
		idmasterService.deletePaymentModeId(warehouseId, paymentModeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//FIND
	@ApiOperation(response = PaymentModeId[].class, value = "Find PaymentModeId")//label for swagger
	@PostMapping("/paymentmodeid/findPaymentModeId")
	public PaymentModeId[] findPaymentModeId(@RequestBody FindPaymentModeId findPaymentModeId) throws Exception {
		return idmasterService.findPaymentModeId(findPaymentModeId,getAuthToken());
	}
	/* --------------------------------PaymentTermId-------------------------------------------------------------------------*/
	@ApiOperation(response = PaymentTermId.class, value = "Get All PaymentTermIds") // label for swagger
	@RequestMapping(value = "/paymenttermid", method = RequestMethod.GET)
	public ResponseEntity<?> getPaymentTermIds() {
		PaymentTermId[] PaymentTermId = idmasterService.getPaymentTermIds(getAuthToken());
		return new ResponseEntity<>(PaymentTermId, HttpStatus.OK);
	}
	@ApiOperation(response = PaymentTermId.class, value = "Get PaymentTermId") // label for swagger
	@RequestMapping(value = "/paymenttermid/{paymentTermId}", method = RequestMethod.GET)
	public ResponseEntity<?> getPaymentTermId(@RequestParam String warehouseId,@PathVariable Long paymentTermId,@RequestParam String companyCodeId,
											  @RequestParam String languageId,@RequestParam String plantId) {
		PaymentTermId PaymentTermId = idmasterService.getPaymentTermId(warehouseId,paymentTermId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("PaymentTermId::: " + PaymentTermId);
		return new ResponseEntity<>(PaymentTermId, HttpStatus.OK);
	}
	@ApiOperation(response = PaymentTermId.class, value = "Create new PaymentTermId") // label for swagger
	@RequestMapping(value = "/paymenttermid", method = RequestMethod.POST)
	public ResponseEntity<?> createPaymentTermId(@RequestParam String loginUserID, @RequestBody AddPaymentTermId newPaymentTermId) {
		PaymentTermId PaymentTermId = idmasterService.addPaymentTermId(newPaymentTermId,loginUserID, getAuthToken());
		return new ResponseEntity<>(PaymentTermId, HttpStatus.OK);
	}
	@ApiOperation(response = PaymentTermId.class, value = "Update PaymentTermId") // label for swagger
	@RequestMapping(value = "/paymenttermid/{paymentTermId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updatePaymentTermId(@PathVariable Long paymentTermId,@RequestParam String loginUserID,@RequestParam String companyCodeId,
												 @RequestParam String languageId,@RequestParam String plantId, @RequestParam String warehouseId,@RequestBody UpdatePaymentTermId updatePaymentTermId ) {
		PaymentTermId PaymentTermId = idmasterService.updatePaymentTermId(warehouseId, paymentTermId,companyCodeId,languageId,plantId,loginUserID, updatePaymentTermId,getAuthToken());
		return new ResponseEntity<>(PaymentTermId, HttpStatus.OK);
	}
	@ApiOperation(response = PaymentTermId.class, value = "Delete PaymentTermId") // label for swagger
	@RequestMapping(value = "/paymenttermid/{paymentTermId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePaymentTermId(@PathVariable Long paymentTermId,@RequestParam String loginUserID,@RequestParam String companyCodeId,
												 @RequestParam String languageId,@RequestParam String plantId,@RequestParam String warehouseId) {
		idmasterService.deletePaymentTermId(warehouseId, paymentTermId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//FIND
	@ApiOperation(response = PaymentTermId[].class, value = "Find PaymentTermId")//label for swagger
	@PostMapping("/paymenttermid/findPaymentTermId")
	public PaymentTermId[] findPaymentTermId(@RequestBody FindPaymentTermId findPaymentTermId) throws Exception {
		return idmasterService.findPaymentTermId(findPaymentTermId,getAuthToken());
	}
	/* --------------------------------ServiceTypeId-------------------------------------------------------------------------*/
	@ApiOperation(response = ServiceTypeId.class, value = "Get AllServiceTypeIds") // label for swagger
	@RequestMapping(value = "/servicetypeid", method = RequestMethod.GET)
	public ResponseEntity<?> getServiceTypeIds() {
		ServiceTypeId[] ServiceTypeId = idmasterService.getServiceTypeIds(getAuthToken());
		return new ResponseEntity<>(ServiceTypeId, HttpStatus.OK);
	}
	@ApiOperation(response = ServiceTypeId.class, value = "Get ServiceTypeId") // label for swagger
	@RequestMapping(value = "/servicetypeid/{serviceTypeId}", method = RequestMethod.GET)// Long moduleId, Long serviceTypeId
	public ResponseEntity<?> getServiceTypeId(@RequestParam String warehouseId,@PathVariable Long serviceTypeId,@RequestParam String moduleId,@RequestParam String companyCodeId,
											  @RequestParam String languageId,@RequestParam String plantId) {
		ServiceTypeId ServiceTypeId = idmasterService.getServiceTypeId(warehouseId,moduleId,serviceTypeId,companyCodeId,languageId,plantId,getAuthToken());
		log.info("ServiceTypeId::: " + ServiceTypeId);
		return new ResponseEntity<>(ServiceTypeId, HttpStatus.OK);
	}
	@ApiOperation(response = ServiceTypeId.class, value = "Create new ServiceTypeId") // label for swagger
	@RequestMapping(value = "/servicetypeid", method = RequestMethod.POST)
	public ResponseEntity<?> createServiceTypeId(@RequestParam String loginUserID, @RequestBody AddServiceTypeId newServiceTypeId) {
		ServiceTypeId ServiceTypeId = idmasterService.addServiceTypeId(newServiceTypeId,loginUserID, getAuthToken());
		return new ResponseEntity<>(ServiceTypeId, HttpStatus.OK);
	}
	@ApiOperation(response = ServiceTypeId.class, value = "Update ServiceTypeId") // label for swagger
	@RequestMapping(value = "/servicetypeid/{serviceTypeId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateServiceTypeId(@PathVariable Long serviceTypeId,@RequestParam String moduleId,@RequestParam String companyCodeId,
												 @RequestParam String languageId,@RequestParam String plantId,@RequestParam String loginUserID, @RequestParam String warehouseId,
												 @RequestBody UpdateServiceTypeId updateServiceTypeId ) {
		ServiceTypeId ServiceTypeId = idmasterService.updateServiceTypeId(warehouseId, moduleId,serviceTypeId,companyCodeId,languageId,plantId,loginUserID,updateServiceTypeId,getAuthToken());
		return new ResponseEntity<>(ServiceTypeId, HttpStatus.OK);
	}
	@ApiOperation(response = ServiceTypeId.class, value = "Delete ServiceTypeId") // label for swagger
	@RequestMapping(value = "/servicetypeid/{serviceTypeId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteServiceTypeId(@RequestParam String moduleId,@PathVariable Long serviceTypeId,@RequestParam String loginUserID,@RequestParam String companyCodeId,
												 @RequestParam String languageId, @RequestParam String plantId, @RequestParam String warehouseId) {
		idmasterService.deleteServiceTypeId(warehouseId, moduleId,serviceTypeId,companyCodeId,languageId,plantId,loginUserID,getAuthToken());
		return new ResponseEntity<>( HttpStatus.NO_CONTENT);
	}
	//FIND
	@ApiOperation(response = ServiceTypeId[].class, value = "Find ServiceTypeId")//label for swagger
	@PostMapping("/servicetypeid/findServiceTypeId")
	public ServiceTypeId[] findServiceTypeId(@RequestBody FindServiceTypeId findServiceTypeId) throws Exception {
		return idmasterService.findServiceTypeId(findServiceTypeId,getAuthToken());
	}

	/* --------------------------------NumberRange-------------------------------------------------------------------------*/
	@ApiOperation(response = NumberRange.class, value = "Get All NumberRanges") // label for swagger
	@RequestMapping(value = "/numberrange", method = RequestMethod.GET)
	public ResponseEntity<?> getNumberRange() {
		NumberRange[] numberRanges = idmasterService.getNumberRanges(getAuthToken());
		return new ResponseEntity<>(numberRanges, HttpStatus.OK);
	}
	@ApiOperation(response = NumberRange.class, value = "Get NumberRange") // label for swagger
	@RequestMapping(value = "/numberrange/{numberRangeCode}", method = RequestMethod.GET)// Long moduleId, Long serviceTypeId
	public ResponseEntity<?> getNumberRange(@RequestParam String warehouseId,@PathVariable Long numberRangeCode,@RequestParam Long fiscalYear,
											@RequestParam String companyCodeId,@RequestParam String languageId, @RequestParam String plantId) {
		NumberRange numberRange = idmasterService.getNumberRange(warehouseId,companyCodeId,languageId,plantId,numberRangeCode,fiscalYear,getAuthToken());
		log.info("NumberRange::: " + numberRange);
		return new ResponseEntity<>(numberRange, HttpStatus.OK);
	}
	@ApiOperation(response = ServiceTypeId.class, value = "Create new NumberRange") // label for swagger
	@RequestMapping(value = "/numberrange", method = RequestMethod.POST)
	public ResponseEntity<?> createNumberRange(@RequestParam String loginUserID, @RequestBody AddNumberRange newNumberRange) {
		NumberRange numberRange = idmasterService.addNumberRange(newNumberRange,loginUserID, getAuthToken());
		return new ResponseEntity<>(numberRange, HttpStatus.OK);
	}
	@ApiOperation(response = NumberRange.class, value = "Update NumberRange") // label for swagger
	@RequestMapping(value = "/numberrange/{numberRangeCode}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateNumberRange(@RequestParam String warehouseId, @PathVariable Long numberRangeCode, @RequestParam Long fiscalYear,
											   @RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String plantId,
											   @Valid @RequestBody UpdateNumberRange updateNumberRange, @RequestParam String loginUserID) {
		NumberRange numberRange = idmasterService.updateNumberRange(warehouseId,companyCodeId,languageId,plantId,numberRangeCode,fiscalYear,loginUserID,updateNumberRange,getAuthToken());
		return new ResponseEntity<>(numberRange, HttpStatus.OK);
	}
	@ApiOperation(response = NumberRange.class, value = "Delete NumberRange") // label for swagger
	@RequestMapping(value = "/numberrange/{numberRangeCode}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteNumberRange(@RequestParam String warehouseId,@PathVariable Long numberRangeCode,@RequestParam Long fiscalYear,@RequestParam String companyCodeId,
											   @RequestParam String languageId,@RequestParam String plantId, @RequestParam String loginUserID) {
		idmasterService.deleteNumberRange(warehouseId,companyCodeId,languageId,plantId,numberRangeCode,fiscalYear,loginUserID,getAuthToken());
		return new ResponseEntity<>( HttpStatus.NO_CONTENT);
	}
	//FIND
	@ApiOperation(response = NumberRange[].class, value = "Find NumberRange")//label for swagger
	@PostMapping("/numberrange/findNumberRange")
	public NumberRange[] findNumberRange(@RequestBody FindNumberRange findNumberRange) throws Exception {
		return idmasterService.findNumberRange(findNumberRange,getAuthToken());
	}

	//-----------------------------------Document Upload------------------------------------------------------------
	@ApiOperation(response = UploadFileResponse.class, value = "Document Storage Upload") // label for swagger
	@PostMapping("/doc-storage/upload")
	public ResponseEntity<?> docStorageUpload(@RequestParam String location, @RequestParam("file") MultipartFile file)
			throws Exception {
		if (location == null) {
			throw new BadRequestException("Location can't be blank. Please provide either Agreement or Document as Location");
		}
		Map<String, String> response = fileStorageService.storingFile(location, file);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//-----------------------------------Document Download------------------------------------------------------------
	@ApiOperation(response = Optional.class, value = "Document Storage Download") // label for swagger
	@GetMapping("/doc-storage/download")
	public ResponseEntity<?> docStorageDownload(@RequestParam String location, @RequestParam String fileName)
			throws Exception {
		String filePath = docStorageService.getQualifiedFilePath (location, fileName);
		File file = new File (filePath);
		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");
		return ResponseEntity.ok()
				.headers(header)
				.contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}
//----------------------------------Interim-Barcode------------------------------------------------------------------

	@ApiOperation(response = InterimBarcode.class, value = "Get all InterimBarcode details") // label for swagger
	@GetMapping("/interimbarcode")
	public ResponseEntity<?> getInterimBarcodes() throws ParseException {
		com.tekclover.wms.core.model.idmaster.InterimBarcode[] interimBarcodeList = idmasterService.getInterimBarcodes(getAuthToken());
		return new ResponseEntity<>(interimBarcodeList, HttpStatus.OK);
	}

	@ApiOperation(response = InterimBarcode.class, value = "Get a InterimBarcode") // label for swagger
	@GetMapping("/interimbarcode/{storageBin}")
	public ResponseEntity<?> getInterimBarcode(@PathVariable String storageBin, @RequestParam String itemCode, @RequestParam String barcode) throws ParseException {
		InterimBarcode interimBarcode = idmasterService.getInterimBarcode(storageBin, itemCode, barcode, getAuthToken());
		log.info("InterimBarcode : " + interimBarcode);
		return new ResponseEntity<>(interimBarcode, HttpStatus.OK);
	}

	@ApiOperation(response = InterimBarcode.class, value = "Create InterimBarcode") // label for swagger
	@PostMapping("/interimbarcode")
	public ResponseEntity<?> addInterimBarcode(@Valid @RequestBody InterimBarcode newInterimBarcode, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		InterimBarcode createdInterimBarcode = idmasterService.createInterimBarcode(newInterimBarcode, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdInterimBarcode , HttpStatus.OK);
	}

	//Search
	@ApiOperation(response = InterimBarcode[].class, value = "Find InterimBarcode") //label for swagger
	@PostMapping("/interimbarcode/findInterimBarcode")
	public InterimBarcode[] findInterimBarcode(@RequestBody FindInterimBarcode findInterimBarcode) throws Exception {
		return idmasterService.findInterimBarcode(findInterimBarcode,getAuthToken());
	}

	//----------------------------------without Authtoken for testing------------------------------------------------------------------
	@ApiOperation(response = Country.class, value = "Get all Country details") // label for swagger
	@GetMapping("/test")
	public ResponseEntity<?> getAll() {
		List<com.tekclover.wms.core.model.dto.Country> countryList = idmasterService.getCountrys();
		return new ResponseEntity<>(countryList, HttpStatus.OK);
	}

	@ApiOperation(response = Country.class, value = "Get a Country") // label for swagger
	@GetMapping("/test/{countryId}")
	public ResponseEntity<?> getCountry(@PathVariable String countryId,@RequestParam String languageId) {
		com.tekclover.wms.core.model.dto.Country country = idmasterService.getCountry(countryId,languageId);
		log.info("Country : " + country);
		return new ResponseEntity<>(country, HttpStatus.OK);
	}

	@ApiOperation(response = Country.class, value = "Create Country") // label for swagger
	@PostMapping("/test")
	public ResponseEntity<?> addCountry(@Valid @RequestBody AddCountry newCountry,@RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException, ParseException {
		com.tekclover.wms.core.model.dto.Country createdCountry = idmasterService.createCountry(newCountry,loginUserID);
		return new ResponseEntity<>(createdCountry , HttpStatus.OK);
	}

	@ApiOperation(response = Country.class, value = "Update Country") // label for swagger
	@PatchMapping("/test/{countryId}")
	public ResponseEntity<?> patchCountry(@PathVariable String countryId,@RequestParam String languageId,@RequestParam String loginUserID,
										  @Valid @RequestBody UpdateCountry updateCountry)
			throws IllegalAccessException, InvocationTargetException, ParseException {
		com.tekclover.wms.core.model.dto.Country updatedCountry = idmasterService.updateCountry(countryId,languageId,loginUserID,updateCountry);
		return new ResponseEntity<>(updatedCountry , HttpStatus.OK);
	}

	@ApiOperation(response = Country.class, value = "Delete Country") // label for swagger
	@DeleteMapping("/test/{countryId}")
	public ResponseEntity<?> deleteCountry(@PathVariable String countryId, @RequestParam String languageId) {
		idmasterService.deleteCountry(countryId,languageId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//Search
	@ApiOperation(response = Country.class, value = "Find Country") // label for swagger
	@PostMapping("/test/findCountry")
	public ResponseEntity<?> findCountry(@Valid @RequestBody FindCountry findCountry) throws Exception {
		List<com.tekclover.wms.core.model.dto.Country> createdCountry = idmasterService.findCountry(findCountry);
		return new ResponseEntity<>(createdCountry, HttpStatus.OK);
	}
	@ApiOperation(response = HhtNotification.class, value = "Create HhtNotification") // label for swagger
	@PostMapping("/hhtnotification/createnotification")
	public ResponseEntity<?> createHhtNotification(@Valid @RequestBody HhtNotification newHhtNotification, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
		HhtNotification createHhtNotification = idmasterService.createHhtNotification(newHhtNotification, loginUserID, getAuthToken());
		return new ResponseEntity<>(createHhtNotification, HttpStatus.OK);
	}
	@ApiOperation(response = HhtNotification.class, value = "Get a HhtNotification") // label for swagger
	@GetMapping("/hhtnotification/getnotification")
	public ResponseEntity<?> getHhtNotification(@RequestParam String warehouseId,@RequestParam String companyId, @RequestParam String languageId, @RequestParam String plantId, @RequestParam String deviceId,
												@RequestParam String userId, @RequestParam String tokenId) {
		HhtNotification dbHhtNotification = idmasterService.getHhtNotification(warehouseId, companyId,  languageId,plantId ,  deviceId,userId, tokenId, getAuthToken());

		log.info("HhtNotification : " + dbHhtNotification);
		return new ResponseEntity<>(dbHhtNotification, HttpStatus.OK);
	}

}