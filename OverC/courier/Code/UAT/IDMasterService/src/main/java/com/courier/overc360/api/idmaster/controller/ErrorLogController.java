package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.replica.model.errorlog.FindErrorLog;
import com.courier.overc360.api.idmaster.replica.model.errorlog.ReplicaErrorLog;
import com.courier.overc360.api.idmaster.service.ErrorLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"ErrorLog"}, value = "ErrorLog Operations related to ErrorLogController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ErrorLog", description = "Operations related to ErrorLog")})
@RequestMapping("/errorLog")
@RestController
public class ErrorLogController {

    @Autowired
    ErrorLogService errorLogService;

    /*--------------------------------------------------REPLICA------------------------------------------------------*/

    // Get All ErrorLogs
    @ApiOperation(response = ReplicaErrorLog.class, value = "Get all ErrorLog details") // label for swagger
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllErrorLogDetails() {
        List<ReplicaErrorLog> errorLogList = errorLogService.getAllErrorLogs();
        return new ResponseEntity<>(errorLogList, HttpStatus.OK);
    }

    // Find ErrorLogs
    @ApiOperation(response = ReplicaErrorLog.class, value = "Find ErrorLog") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findErrorLogs(@Valid @RequestBody FindErrorLog findErrorLog) throws Exception {
        List<ReplicaErrorLog> errorLogList = errorLogService.findErrorLogs(findErrorLog);
        return new ResponseEntity<>(errorLogList, HttpStatus.OK);
    }

}
