package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.notification.AddNotification;
import com.courier.overc360.api.idmaster.primary.model.notification.Notification;
import com.courier.overc360.api.idmaster.primary.model.notification.UpdateNotification;
import com.courier.overc360.api.idmaster.replica.model.notification.FindNotification;
import com.courier.overc360.api.idmaster.replica.model.notification.ReplicaNotification;
import com.courier.overc360.api.idmaster.service.NotificationService;
import com.opencsv.exceptions.CsvException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"Notification"}, value = "Notification Operations related to Notification") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Notification", description = "Operations related to Notification")})
@RequestMapping("/notification")
@RestController
public class NotificationController {

    @Autowired
    NotificationService notificationService;


    /*========================================PRIMARY==================================================================*/

    //Create
    @ApiOperation(response = Notification.class, value = "Create Notification")
    @PostMapping("")
    public ResponseEntity<?> postNotification(@Valid @RequestBody AddNotification addNotification, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Notification createdNotification = notificationService.createNotification(addNotification, loginUserID);
        return new ResponseEntity<>(createdNotification, HttpStatus.OK);
    }

    //Update
    @ApiOperation(response = Notification.class, value = "Update Notification")
    @PatchMapping("/{notificationId}")
    public ResponseEntity<?> patchNotification(@PathVariable String notificationId, @RequestParam String languageId, @RequestParam String companyId,
                                               @RequestBody UpdateNotification updateNotification, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Notification updatedNotification = notificationService.updateNotification(languageId, companyId, notificationId, updateNotification, loginUserID);
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }

    //Delete
    @ApiOperation(response = Notification.class, value = "Delete Notification")
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable String notificationId, @RequestParam String languageId, @RequestParam String companyId, @RequestParam String loginUserID) {
        notificationService.deleteNotification(languageId, companyId, notificationId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*========================================REPLICA==================================================================*/

    //Get All
    @ApiOperation(response = ReplicaNotification.class, value = "Get All Notification")
    @GetMapping("")
    public ResponseEntity<?> getAllNotification() {
        List<ReplicaNotification> notificationList = notificationService.getAllNotification();
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    //Get
    @ApiOperation(response = ReplicaNotification.class, value = "Get Notification")
    @GetMapping("/{notificationId}")
    public ResponseEntity<?> getNotification(@PathVariable String notificationId, @RequestParam String languageId,
                                             @RequestParam String companyId) {
        ReplicaNotification dbNotification = notificationService.getReplicaNotification(languageId, companyId, notificationId);
        return new ResponseEntity<>(dbNotification, HttpStatus.OK);
    }

    //Find
    @ApiOperation(response = ReplicaNotification.class, value = "Find Notification")
    @PostMapping("/find")
    public ResponseEntity<?> findNotification(@Valid @RequestBody FindNotification findNotification) throws Exception {
        List<ReplicaNotification> notificationList = notificationService.findNotification(findNotification);
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }
}





