package com.mnrclara.api.management.controller;


import com.mnrclara.api.management.model.hhtnotification.CountOrderType;
import com.mnrclara.api.management.model.hhtnotification.FindNotificationMessage;
import com.mnrclara.api.management.model.hhtnotification.NotificationMessage;
import com.mnrclara.api.management.model.hhtnotification.UpdateNotification;
import com.mnrclara.api.management.service.NotificationMessageService;
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
    @ApiOperation(response = NotificationMessage.class, value = "Get All Notification")
    @GetMapping("")
    public ResponseEntity<?> getAllNotification() {
        List<NotificationMessage> notificationMessages = notificationMessageService.getAll();
        return new ResponseEntity<>(notificationMessages, HttpStatus.OK);
    }

    // Find
    @ApiOperation(response = NotificationMessage.class, value = "Find Notification Message")
    @PostMapping("/findNotification")
    public List<NotificationMessage> findNotification(@RequestBody FindNotificationMessage findNotificationMessage) throws ParseException {
        return notificationMessageService.findNotification(findNotificationMessage);
    }

    //Update
    @ApiOperation(response = NotificationMessage.class, value = "Update Notification Message")
    @PatchMapping("/update")
    public ResponseEntity<?> updateNotification(@RequestBody List<UpdateNotification> updateNotification,
                                                @RequestParam String loginUserID){
        List<NotificationMessage> dbNotification = notificationMessageService.updateNotification(updateNotification, loginUserID);
        return new ResponseEntity<>(dbNotification, HttpStatus.OK);
    }

    // Get Count
    @ApiOperation(response = CountOrderType.class, value = "Count OrderType")
    @GetMapping("/{client}")
    public ResponseEntity<?> getCount(@PathVariable String client) {
        CountOrderType count = notificationMessageService.getCountForOrderType(client);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // Get Count - by ClientUserId
    @ApiOperation(response = CountOrderType.class, value = "Count OrderType")
    @GetMapping("/clientUserId/{clientUserId}")
    public ResponseEntity<?> getCountUserId(@PathVariable String clientUserId) {
        CountOrderType count = notificationMessageService.getCountForOrderTypeByClientUserId(clientUserId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // Delete
    @ApiOperation(response = NotificationMessage.class, value = "Delete Notification Message")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteNotification(@RequestParam(required = false) Long notificationId, @RequestParam(required = false) String clientId,
                                                @RequestParam(required = false) String classId, @RequestParam String loginUserID) {
        notificationMessageService.deleteNotificationMessage(notificationId, clientId, classId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
