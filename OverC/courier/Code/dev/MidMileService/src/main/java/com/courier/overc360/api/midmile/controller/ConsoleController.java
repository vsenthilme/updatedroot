package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.UploadResponse;
import com.courier.overc360.api.midmile.primary.model.consignment.AddConsignment;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.model.console.*;
import com.courier.overc360.api.midmile.primary.model.prealert.PreAlert;
import com.courier.overc360.api.midmile.replica.model.console.*;
import com.courier.overc360.api.midmile.service.ConsoleService;
import com.courier.overc360.api.midmile.service.ConsoleServiceV2;
import com.opencsv.exceptions.CsvException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Validated
@Api(tags = {"Console"}, value = "Console Operations related to ConsoleController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Console", description = "Operations related to Console")})
@RequestMapping("/console")
@RestController
public class ConsoleController {


    @Autowired
    ConsoleService consoleService;

    @Autowired
    ConsoleServiceV2 consoleServiceV2;

    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/

    // Create new Console
//    @ApiOperation(response = Console.class, value = "Create new Console") // label for swagger
//    @PostMapping("/create/list")
//    public ResponseEntity<?> postConsole(@Valid @RequestBody List<AddConsole> addConsoleList,
//                                         @RequestParam String loginUserID)
//            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
//        List<Console> console = consoleService.createConsoleNormal(addConsoleList, loginUserID);
//        return new ResponseEntity<>(console, HttpStatus.OK);
//    }

//    // Create new Console
//    @ApiOperation(response = Console.class, value = "Create new Console") // label for swagger
//    @PostMapping("/create/list/con")
//    public ResponseEntity<?> postConsoleCon(@Valid @RequestBody List<ReplicaAddConsignment> addConsoleList,
//                                         @RequestParam String loginUserID)
//            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
//        List<Console> console = consoleService.createConsoleInConsign(addConsoleList, loginUserID);
//        return new ResponseEntity<>(console, HttpStatus.OK);
//    }

    // Create new Console
    @ApiOperation(response = Console.class, value = "Create new Console") // label for swagger
    @PostMapping("/create/list/con")
    public ResponseEntity<?> postConsoleCon(@Valid @RequestBody List<PreAlert> preAlerts,
                                            @RequestParam String loginUserID) throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
        List<Console> console = consoleServiceV2.createConsoleBasedOnPreAlertResponse(preAlerts, loginUserID);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    // Update Console - CCR Create
    @ApiOperation(response = Console.class, value = "Update Console CCR Create") // label for Swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchConsole(@Valid @RequestBody List<UpdateConsole> updateConsoleList,
                                          @RequestParam String loginUserID)
            throws InvocationTargetException, IllegalAccessException, IOException, CsvException {
        List<Console> console = consoleService.updateConsoleCcrCreate(updateConsoleList, loginUserID);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    // Update Console For Mobile App
    @ApiOperation(response = Console.class, value = "Update Console For Mobile App") // label for Swagger
    @PatchMapping("/update/list/mobile")
    public ResponseEntity<?> patchConsoleForMobileApp(@Valid @RequestBody List<UpdateConsole> updateConsoleList,
                                                      @RequestParam String loginUserID)
            throws InvocationTargetException, IllegalAccessException, IOException, CsvException {
        List<Console> console = consoleService.updateConsoleStatusOrForMobileApp(updateConsoleList, loginUserID);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    // Update Console - Normal
    @ApiOperation(response = Console.class, value = "Update Console")
    @PatchMapping("/update/list/normal")
    public ResponseEntity<?> patchConsoleList(@Valid @RequestBody List<UpdateConsole> updateConsoleList, @RequestParam String loginUserID) {
        List<Console> dbConsole = consoleService.updateConsoleList(updateConsoleList, loginUserID);
        return new ResponseEntity<>(dbConsole, HttpStatus.OK);
    }

    // Delete Console
    @ApiOperation(response = Console.class, value = "Delete Console") // label for Swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteConsole(@Valid @RequestBody List<ConsoleDeleteInput> consoleDeleteInputs,
                                           @RequestParam String loginUserID) throws IOException, CsvException {
        consoleService.deleteConsole(consoleDeleteInputs, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Transfer Console
    @ApiOperation(response = Console.class, value = "Console Transfer")
    @PostMapping("/transfer")
    public ResponseEntity<?> consoleTransfer(@Valid @RequestBody List<TransferConsole> transferConsole, @RequestParam String loginUserID) {
        List<Console> dbConsole = consoleService.transferConsole(transferConsole, loginUserID);
        return new ResponseEntity<>(dbConsole, HttpStatus.OK);
    }
    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    // Get All Console Details
    @ApiOperation(response = ReplicaConsole.class, value = "Get all Console Details")
    // label for swagger
    @GetMapping(" ")
    public ResponseEntity<?> getAllReplicaConsole() {
        List<ReplicaConsole> console = consoleService.getAllConsole();
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    // Get a Console
    @ApiOperation(response = ReplicaConsole.class, value = "Get a Console")
    @GetMapping("/{consoleId}")
    public ResponseEntity<?> getConsoleReplica(@PathVariable String consoleId, @RequestParam String languageId,
                                               @RequestParam String companyId, @RequestParam String partnerId,
                                               @RequestParam String partnerMasterAirwayBill, @RequestParam String partnerHouseAirwayBill) {
        ReplicaConsole console = consoleService.getConsoleReplica(
                languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    // Find Consoles - normal
    @ApiOperation(response = ReplicaConsole.class, value = "Find Consoles") // label for swagger
    @PostMapping("/findConsole")
    public ResponseEntity<?> fetchConsoles(@Valid @RequestBody FindConsole findConsole) throws Exception {
        List<ConsoleProjection> consoleList = consoleService.findConsoles(findConsole);
        return new ResponseEntity<>(consoleList, HttpStatus.OK);
    }

    // Find Consoles - MobileApp
    @ApiOperation(response = ReplicaConsole.class, value = "Find Consoles - MobileApp") // label for swagger
    @PostMapping("/findConsole/mobileApp")
    public ResponseEntity<?> findConsoleMobileApp(@Valid @RequestBody FindConsole findConsole) throws Exception {
        List<ReplicaConsole> consoleList = consoleService.findConsolesMobileApp(findConsole);
        return new ResponseEntity<>(consoleList, HttpStatus.OK);
    }

    // Find Console
    @ApiOperation(response = ReplicaConsole.class, value = "Find Consoles by Pagination") // label for swagger
    @PostMapping("/findConsole/pagination")
    public ResponseEntity<?> findConsolesByPagination(@Valid @RequestBody FindConsole findConsole,
                                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      @RequestParam(defaultValue = "consoleId") String sortBy) throws Exception {
        Page<ReplicaConsole> consoleList = consoleService.findConsolesByPagination(findConsole, pageNo, pageSize, sortBy);
        return new ResponseEntity<>(consoleList, HttpStatus.OK);
    }

    @ApiOperation(response = MobileApp.class, value = "Find Console For MobileApp")
    @GetMapping("/find/mobileapp")
    public ResponseEntity<?> findMobileApp() {
        List<MobileApp> dbMobileApp = consoleService.getAllMobileApp();
        return new ResponseEntity<>(dbMobileApp, HttpStatus.OK);
    }

    @ApiOperation(response = Console.class, value = "Console Status Event Update")
    @PostMapping("/update/status")
    public ResponseEntity<?> updateConsoleStatus(@Valid @RequestBody List<ConsoleStatus> consoleStatuses,
                                                 @RequestParam String loginUserID) throws IOException,
            InvocationTargetException, IllegalAccessException, CsvException {
        List<Console> dbConsoleStatus = consoleService.updateConsoleStatus(consoleStatuses, loginUserID);
        return new ResponseEntity<>(dbConsoleStatus, HttpStatus.OK);
    }

    // Send Notification
    @ApiOperation(response = Console.class, value = "Send notification")
    @PostMapping("/send/notification")
    public ResponseEntity<?> sendNotification(@RequestParam String companyId, @RequestParam String languageId,
                                              @RequestParam String consoleId, @RequestParam String houseAirwayBill,
                                              @RequestParam String consoleGroupName, @RequestParam String consoleName){
        consoleService.sendNotificationForConsoleCreate(companyId, languageId, consoleId, houseAirwayBill, consoleName);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    // Manual Console Create
    @ApiOperation(response = Console.class, value = "Manual Console Create")
    @PostMapping("/manual/create")
    public ResponseEntity<?> manualConsoleCreate(@Valid @RequestBody List<Console> consoles, @RequestParam String loginUserID)
            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
        List<Console> consoleCreate = consoleService.manualCreateConsole(consoles, loginUserID);
        return new ResponseEntity<>(consoleCreate, HttpStatus.OK);
    }

    @ApiOperation(response = HsCode.class, value = "Find SpecialApproval")
    @PostMapping("/specialApproval")
    public ResponseEntity<?> getSpecialApproval(@Valid @RequestBody ConsoleRequest consoleIdList) {

        List<HsCode> response = consoleService.findHsCode(consoleIdList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Console Upload
    @ApiOperation(response = Console.class, value = "Upload Console")
    @PostMapping("/upload")
    public ResponseEntity<?> consoleUpload(@Valid @RequestBody List<Console> consoleList, @RequestParam String loginUserID) {
        List<UploadResponse> uploadResponseList = new ArrayList<>();
        List<Console> addConsole = consoleService.consoleUploadUpdate(consoleList, loginUserID);
        if(!addConsole.isEmpty()) {
            UploadResponse uploadResponse = new UploadResponse();
            uploadResponse.setStatusCode("200");
            uploadResponse.setMessage("Console Upload Successfully");
            uploadResponseList.add(uploadResponse);
        }
        return new ResponseEntity<>(uploadResponseList, HttpStatus.OK);
    }
}
