package com.courier.overc360.api.idmaster.controller;


import com.courier.overc360.api.idmaster.primary.model.hhtnotification.NotificationMessage;
import com.courier.overc360.api.idmaster.replica.model.hhtnotification.FindNotificationMessage;
import com.courier.overc360.api.idmaster.replica.model.hhtnotification.ReplicaNotificationMessage;
import com.courier.overc360.api.idmaster.service.NotificationMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@Slf4j
@RestController
@Api(tags = {"Notification"}, value = "NotificationMessage Operation related to NotificationController")
@SwaggerDefinition(tags = {@Tag(name = "Notification", description = "Operations related to Notification")})
@RequestMapping("/notificationmessage")
public class NotificationMessageController {

    @Autowired
    NotificationMessageService notificationMessageService;

    // Get All
    @ApiOperation(response = ReplicaNotificationMessage.class, value = "Get All Notification Message")
    @GetMapping("")
    public ResponseEntity<?> getAllNotification() {
        List<ReplicaNotificationMessage> notificationMessages = notificationMessageService.getAll();
        return new ResponseEntity<>(notificationMessages, HttpStatus.OK);
    }

    // Find
    @ApiOperation(response = ReplicaNotificationMessage.class, value = "Find Notification Message")
    @PostMapping("/findNotification")
    public List<ReplicaNotificationMessage> findNotification(@RequestBody FindNotificationMessage findNotificationMessage) throws ParseException {
        return notificationMessageService.findNotification(findNotificationMessage);
    }

    //Update
    @ApiOperation(response = NotificationMessage.class, value = "Update Notification Message")
    @PatchMapping("/update")
    public ResponseEntity<?> updateNotification(@RequestBody List<NotificationMessage> updateNotification,
                                                @RequestParam String loginUserID) {
        List<NotificationMessage> dbNotification = notificationMessageService.updateNotification(updateNotification, loginUserID);
        return new ResponseEntity<>(dbNotification, HttpStatus.OK);
    }

    // Delete
    @ApiOperation(response = NotificationMessage.class, value = "Delete Notification Message")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteNotification(@RequestParam(required = false) Long notificationId, @RequestParam(required = false) String companyId,
                                                @RequestParam(required = false) String languageId, @RequestParam(required = false) String houseAirwayBill,
                                                @RequestParam String loginUserID) {
        notificationMessageService.deleteNotificationMessage(notificationId, companyId, languageId, houseAirwayBill, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
