package com.mnrclara.api.crm.controller;

import com.mnrclara.api.crm.model.InquiryId.AddInquiryId;
import com.mnrclara.api.crm.model.InquiryId.FindInquiryId;
import com.mnrclara.api.crm.model.InquiryId.InquiryId;
import com.mnrclara.api.crm.service.InquiryIdService;
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
@Validated
@Api(tags = {"InquiryId"}, value = "InquiryId Operations related to InquiryIdController")// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "InquiryId", description = "Operations related to InquiryId")})
@RequestMapping("/inquiryId")
@RestController
public class InquiryIdController {

    @Autowired
    private InquiryIdService inquiryIdService;

    // GET ALL
    @ApiOperation(response = InquiryId.class, value = "Get all InquiryId details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<InquiryId> inquiryIdList = inquiryIdService.getInquiryIdAll();
        return new ResponseEntity<>(inquiryIdList, HttpStatus.OK);
    }

    // GET
    @ApiOperation(response = InquiryId.class, value = "Get a InquiryId") // label for swagger
    @GetMapping("/{inquiryId}")
    public ResponseEntity<?> getInquiryId(@PathVariable Long inquiryId) {
        List<InquiryId> dbInquiryId = inquiryIdService.getInquiryId(inquiryId);
        log.info("InquiryId : " + dbInquiryId);
        return new ResponseEntity<>(dbInquiryId, HttpStatus.OK);
    }

    // CREATE
    @ApiOperation(response = InquiryId.class, value = "Create InquiryId ") // label for swagger
    @PostMapping("/create")
    public ResponseEntity<?> addInquiryId(@Valid @RequestBody List<AddInquiryId> newInquiryId,
                                          @RequestParam String loginUserID) {
        List<InquiryId> createInquiry = inquiryIdService.createInquiryId(newInquiryId, loginUserID);
        return new ResponseEntity<>(createInquiry, HttpStatus.OK);
    }

    // UPDATE
    @ApiOperation(response = InquiryId.class, value = "Update InquiryId") // label for swagger
    @PatchMapping("/update")
    public ResponseEntity<?> patchInquiryId(@RequestParam String loginUserID,
                                            @RequestBody List<AddInquiryId> updateInquiryId) {
        List<InquiryId> dbInquiryId = inquiryIdService.updateInquiryId(loginUserID, updateInquiryId);
        return new ResponseEntity<>(dbInquiryId, HttpStatus.OK);
    }

    // DELETE
    @ApiOperation(response = InquiryId.class, value = "Delete InquiryId") // label for swagger
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<?> deleteInquiryId(@PathVariable Long inquiryId, @RequestParam Long id, @RequestParam String loginUserID) {
        inquiryIdService.deleteInquiryId(id, inquiryId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // SEARCH
    @ApiOperation(response = InquiryId.class, value = "Find InquiryId") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findInquiryId(@Valid @RequestBody FindInquiryId findInquiryId) throws Exception {
        List<InquiryId> results = inquiryIdService.findInquiryId(findInquiryId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}