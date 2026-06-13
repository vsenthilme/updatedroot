package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.UploadResponse;
import com.courier.overc360.api.midmile.primary.model.piecedetails.AddPieceDetails;
import com.courier.overc360.api.midmile.primary.model.piecedetails.PieceDetails;
import com.courier.overc360.api.midmile.primary.model.piecedetails.UpdatePieceDetails;
import com.courier.overc360.api.midmile.replica.model.dto.LabelFormInput;
import com.courier.overc360.api.midmile.replica.model.dto.LabelFormOutput;
import com.courier.overc360.api.midmile.replica.model.piecedetails.FindPieceDetails;
import com.courier.overc360.api.midmile.replica.model.piecedetails.ReplicaPieceDetails;
import com.courier.overc360.api.midmile.service.PieceDetailsService;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"PieceDetails"}, value = "PieceDetails Operations related to PieceDetails") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "PieceDetails", description = "Operations related to PieceDetails")})
@RequestMapping("/piecedetails")
@RestController
public class PieceDetailsController {

    @Autowired
    PieceDetailsService pieceDetailsService;

    /*========================================PRIMARY==================================================================*/

    //Create
    @ApiOperation(response = PieceDetails.class, value = "Create PieceDetails")
    @PostMapping("")
    public ResponseEntity<?> postPieceDetails(@Valid @RequestBody AddPieceDetails addPieceDetails, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        PieceDetails createdPieceDetails = pieceDetailsService.createPieceDetails(addPieceDetails, loginUserID);
        return new ResponseEntity<>(createdPieceDetails, HttpStatus.OK);
    }

    //Update
    @ApiOperation(response = PieceDetails.class, value = "Update PieceDetails")
    @PatchMapping("/{pieceId}")
    public ResponseEntity<?> patchPieceDetails(@PathVariable String pieceId, @RequestParam String languageId, @RequestParam String companyId,
                                               @RequestParam String partnerId, @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill,
                                               @RequestBody UpdatePieceDetails updatePieceDetails, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        PieceDetails updatedPieceDetails = pieceDetailsService.updatePieceDetails(languageId, companyId, partnerId,
                masterAirwayBill, houseAirwayBill, pieceId, updatePieceDetails, loginUserID);
        return new ResponseEntity<>(updatedPieceDetails, HttpStatus.OK);
    }

    //Delete
    @ApiOperation(response = PieceDetails.class, value = "Delete PieceDetails")
    @DeleteMapping("/{pieceId}")
    public ResponseEntity<?> deletePieceDetails(@PathVariable String pieceId, @RequestParam String languageId, @RequestParam String companyId,
                                                @RequestParam String partnerId, @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill,
                                                @RequestParam String loginUserID) {
        pieceDetailsService.deletePieceDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /*========================================REPLICA==================================================================*/

    //Get All
    @ApiOperation(response = ReplicaPieceDetails.class, value = "Get All PieceDetails")
    @GetMapping("")
    public ResponseEntity<?> getAllPieceDetails() {
        List<ReplicaPieceDetails> pieceDetailsList = pieceDetailsService.getAllPieceDetails();
        return new ResponseEntity<>(pieceDetailsList, HttpStatus.OK);
    }

    //Get
    @ApiOperation(response = ReplicaPieceDetails.class, value = "Get PieceDetails")
    @GetMapping("/{pieceId}")
    public ResponseEntity<?> getPieceDetails(@PathVariable String pieceId, @RequestParam String languageId, @RequestParam String companyId,
                                             @RequestParam String partnerId, @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill) {
        ReplicaPieceDetails dbPieceDetails = pieceDetailsService.getReplicaPieceDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId);
        return new ResponseEntity<>(dbPieceDetails, HttpStatus.OK);
    }

    //Find
    @ApiOperation(response = ReplicaPieceDetails.class, value = "Find PieceDetails")
    @PostMapping("/find")
    public ResponseEntity<?> findPieceDetails(@Valid @RequestBody FindPieceDetails findPieceDetails) throws Exception {
        List<ReplicaPieceDetails> pieceDetailsList = pieceDetailsService.findPieceDetails(findPieceDetails);
        return new ResponseEntity<>(pieceDetailsList, HttpStatus.OK);
    }

    //getLabelFormOutput
    @ApiOperation(response = LabelFormOutput.class, value = "get pdf LabelFormOutput")
    @PostMapping("/pdfLabel")
    public ResponseEntity<?> findLabelFormOutput(@Valid @RequestBody LabelFormInput labelFormInput) throws Exception {
        List<LabelFormOutput> labelFormOutputList = pieceDetailsService.getLabelFormOutput(labelFormInput);
        return new ResponseEntity<>(labelFormOutputList, HttpStatus.OK);
    }

    // Piece Upload For Update
    @ApiOperation(response = UploadResponse.class, value = "Piece Update Upload")
    @PostMapping("/upload")
    public ResponseEntity<?> pieceUpload(@Valid @RequestBody List<PieceDetails> pieceDetails, @RequestParam String loginUserID) {

        List<UploadResponse> uploadResponseList = new ArrayList<>();
        List<PieceDetails> addPiece = pieceDetailsService.updatePieceDetailsList(pieceDetails, loginUserID);
        if (!addPiece.isEmpty()) {
            UploadResponse newUpload = new UploadResponse();
            newUpload.setStatusCode("200");
            newUpload.setMessage("Piece Upload Successfully");
            uploadResponseList.add(newUpload);
        }
        return new ResponseEntity<>(uploadResponseList, HttpStatus.OK);
    }
}
