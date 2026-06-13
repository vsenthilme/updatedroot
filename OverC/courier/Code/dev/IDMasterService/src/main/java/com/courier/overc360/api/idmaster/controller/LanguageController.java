package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.replica.model.language.ReplicaLanguage;
import com.courier.overc360.api.idmaster.primary.model.language.AddLanguage;
import com.courier.overc360.api.idmaster.primary.model.language.Language;
import com.courier.overc360.api.idmaster.primary.model.language.UpdateLanguage;
import com.courier.overc360.api.idmaster.replica.model.language.FindLanguage;
import com.courier.overc360.api.idmaster.service.LanguageService;
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
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"Language"}, value = "Language Operations related to LanguageController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Language", description = "Operations related to Language")})
@RequestMapping("/language")
@RestController
public class LanguageController {

    @Autowired
    LanguageService languageService;

    /*========================================PRIMARY==================================================================*/

    // Create Language
    @ApiOperation(response = Language.class, value = "Create new Language") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> createLanguage(@Valid @RequestBody AddLanguage addLanguage, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException {
        Language newLanguage = languageService.createLanguage(addLanguage, loginUserID);
        return new ResponseEntity<>(newLanguage, HttpStatus.OK);
    }

    // Update Language
    @ApiOperation(response = Language.class, value = "Update Language") // label for swagger
    @PatchMapping("/{languageId}")
    public ResponseEntity<?> patchLanguage(@PathVariable String languageId, @Valid @RequestBody UpdateLanguage updateLanguage, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException {
        Language createdLanguage = languageService.updateLanguage(languageId, updateLanguage, loginUserID);
        return new ResponseEntity<>(createdLanguage, HttpStatus.OK);
    }

    // Delete Language
    @ApiOperation(response = Language.class, value = "Delete Language") // label for swagger
    @DeleteMapping("/{languageId}")
    public ResponseEntity<?> deleteLanguage(@PathVariable String languageId,
                                            @RequestParam String loginUserID) {
        languageService.deleteLanguage(languageId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*===================================================REPLICA========================================================*/

    // Get All Language Details
    @ApiOperation(response = ReplicaLanguage.class, value = "Get all Language details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllLanguageDetails() {
        List<ReplicaLanguage> languageList = languageService.getAllLanguageIds();
        return new ResponseEntity<>(languageList, HttpStatus.OK);
    }

    // Get Language
    @ApiOperation(response = Language.class, value = "Get Language") // label for swagger
    @GetMapping("/{languageId}")
    public ResponseEntity<?> getLanguage(@PathVariable String languageId) {

        ReplicaLanguage language = languageService.getReplicaLanguage(languageId);
        return new ResponseEntity<>(language, HttpStatus.OK);
    }

    // Find Language
    @ApiOperation(response = Language.class, value = "Find Language") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findLanguage(@Valid @RequestBody FindLanguage findLanguageId) throws Exception {
        List<ReplicaLanguage> createdLanguages = languageService.findLanguage(findLanguageId);
        return new ResponseEntity<>(createdLanguages, HttpStatus.OK);
    }
}