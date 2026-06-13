package com.courier.overc360.api.midmile.controller;


import com.courier.overc360.api.midmile.primary.model.notificationmessage.NotificationMessage;
import com.courier.overc360.api.midmile.primary.model.notificationmessage.ReadMessages;
import com.courier.overc360.api.midmile.service.PushNotificationService;
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
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/notification")
@Validated
@Api(tags = {"NotificationMessage"}, value = "Notification Operations Related to Notification Controller")
@SwaggerDefinition(tags = {@Tag(name = "Notification", description = "Operation related to Notification Message")})
public class PushNotificationController {

    @Autowired
    PushNotificationService pushNotificationService;

    @ApiOperation(response = NotificationMessage.class, value = "Update Notification Message")
    @PatchMapping("/update")
    public ResponseEntity<?> updateNotification(@Valid @RequestBody List<NotificationMessage> notificationMessageList, @RequestParam String loginUserID) {
        List<ReadMessages> response = pushNotificationService.updateReadMessages(notificationMessageList, loginUserID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
