package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.imagereference.AddImageReference;
import com.courier.overc360.api.midmile.primary.model.imagereference.ImageReference;
import com.courier.overc360.api.midmile.primary.model.imagereference.UpdateImageReference;
import com.courier.overc360.api.midmile.replica.model.imagereference.ReplicaImageReference;
import com.courier.overc360.api.midmile.service.ImageReferenceService;
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
@Api(tags = {"ImageReference"}, value = "ImageReference related to ImageReferenceController")
@SwaggerDefinition(tags = {@Tag(name = "ImageReference", description = "Operations related to ImageReference")})
@RequestMapping("/imageReference")
@RestController
public class ImageReferenceController {

    @Autowired
    ImageReferenceService imageReferenceService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create ImageReference
    @ApiOperation(response = ImageReference.class, value = "Create ImageReference") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postImageReference(@Valid @RequestBody AddImageReference addImageReference, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ImageReference createdImageReference = imageReferenceService.createImageReference(addImageReference, loginUserID);
        return new ResponseEntity<>(createdImageReference, HttpStatus.OK);
    }

    // Update ImageReference
    @ApiOperation(response = ImageReference.class, value = "Update ImageReference") // label for swagger
    @PatchMapping("/{imageRefId}")
    public ResponseEntity<?> patchImageReference(@PathVariable String imageRefId, @RequestParam String languageId, @RequestParam String companyId,
                                                 @RequestParam String partnerId, @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill,
                                                 @RequestParam String pieceId, @RequestParam String pieceItemId,
                                                 @RequestParam String loginUserID, @RequestBody UpdateImageReference updateImageReference)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ImageReference updatedImageReference = imageReferenceService.updateImageReference(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, imageRefId, updateImageReference, loginUserID);
        return new ResponseEntity<>(updatedImageReference, HttpStatus.OK);
    }

    // Delete ImageReference
    @ApiOperation(response = ImageReference.class, value = "Delete ImageReference") // label for swagger
    @DeleteMapping("/{imageRefId}")
    public ResponseEntity<?> deleteImageReference(@PathVariable String imageRefId, @RequestParam String languageId,
                                                  @RequestParam String companyId, @RequestParam String partnerId,
                                                  @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill,
                                                  @RequestParam String pieceId, @RequestParam String pieceItemId, @RequestParam String loginUserID) {
        imageReferenceService.deleteImageReference(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, imageRefId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All ImageReference Details
    @ApiOperation(response = ReplicaImageReference.class, value = "Get all ImageReference details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllImageReferences() {
        List<ReplicaImageReference> imageReferenceList = imageReferenceService.getAllImageReferenceIds();
        return new ResponseEntity<>(imageReferenceList, HttpStatus.OK);
    }

    // Get ImageReference
    @ApiOperation(response = ReplicaImageReference.class, value = "Get a ImageReference") // label for swagger
    @GetMapping("/{imageRefId}")
    public ResponseEntity<?> getImageReference(@PathVariable String imageRefId, @RequestParam String languageId, @RequestParam String companyId,
                                     @RequestParam String partnerId, @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill, @RequestParam String pieceId, @RequestParam String pieceItemId ) {

        ReplicaImageReference imageReference = imageReferenceService.getReplicaImageReference(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, imageRefId);
        return new ResponseEntity<>(imageReference, HttpStatus.OK);
    }
    // Find ImageReference
//    @ApiOperation(response = ReplicaImageReference.class, value = "Find ImageReference") // label for swagger
//    @PostMapping("/find")
//    public ResponseEntity<?> findImageReference(@RequestBody FindImageReference findImageReference) throws Exception {
//        List<ReplicaImageReference> createdImageReference = imageReferenceService.findImageReference(findImageReference);
//        return new ResponseEntity<>(createdImageReference, HttpStatus.OK);
//    }

}
