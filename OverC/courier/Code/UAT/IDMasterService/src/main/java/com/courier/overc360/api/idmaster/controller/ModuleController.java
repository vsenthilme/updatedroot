package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.module.AddModule;
import com.courier.overc360.api.idmaster.primary.model.module.Module;
import com.courier.overc360.api.idmaster.primary.model.module.UpdateModule;
import com.courier.overc360.api.idmaster.replica.model.module.FindModule;
import com.courier.overc360.api.idmaster.replica.model.module.ReplicaModule;
import com.courier.overc360.api.idmaster.service.ModuleService;
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
@Api(tags = {"Module"}, value = "Module  Operations related to ModuleController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Module", description = "Operations related to Module")})
@RequestMapping("/module")
@RestController
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------*/

    // Create module
    @ApiOperation(response = Module.class, value = "Create Module") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postModule(@Valid @RequestBody List<AddModule> newModule, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Module> createdModule = moduleService.createModule(newModule, loginUserID);
        return new ResponseEntity<>(createdModule, HttpStatus.OK);
    }

    // Update Module
    @ApiOperation(response = Module.class, value = "Update Module") // label for swagger
    @PatchMapping("/{moduleId}")
    public ResponseEntity<?> patchModule(@PathVariable String moduleId, @RequestParam String companyId, @RequestParam String languageId,
                                         @Valid @RequestBody List<UpdateModule> updateModule, @RequestParam String loginUserID) throws IOException, CsvException {
        List<Module> updatedModule = moduleService.updateModule(moduleId, companyId, languageId, loginUserID, updateModule);
        return new ResponseEntity<>(updatedModule, HttpStatus.OK);
    }

    // Delete Module
    @ApiOperation(response = Module.class, value = "Delete Module") // label for swagger
    @DeleteMapping("/{moduleId}")
    public ResponseEntity<?> deleteModule(@PathVariable String moduleId, @RequestParam String companyId,
                                          @RequestParam String languageId, @RequestParam String loginUserID) {
        moduleService.deleteModule(moduleId, companyId, languageId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*--------------------------------------------------REPLICA------------------------------------------------------*/

    // Get ModuleList
    @ApiOperation(response = ReplicaModule.class, value = "Get ModuleList") // label for swagger
    @GetMapping("/modulelist/{moduleId}")
    public ResponseEntity<?> getModuleList(@PathVariable String moduleId, @RequestParam String companyId, @RequestParam String languageId) {
        List<ReplicaModule> module = moduleService.getReplicaModuleList(moduleId, companyId, languageId);
        return new ResponseEntity<>(module, HttpStatus.OK);
    }

    // Get Module
    @ApiOperation(response = ReplicaModule.class, value = "Get a Module") // label for swagger
    @GetMapping("/{moduleId}")
    public ResponseEntity<?> getModule(@PathVariable String moduleId, @RequestParam String companyId, @RequestParam String languageId,
                                       @RequestParam Long menuId, @RequestParam Long subMenuId) {
        ReplicaModule module = moduleService.getReplicaModule(menuId, moduleId, companyId, languageId, subMenuId);
        return new ResponseEntity<>(module, HttpStatus.OK);
    }

    // Get all Module details
    @ApiOperation(response = ReplicaModule.class, value = "Get all Module details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllModules() {
        List<ReplicaModule> moduleList = moduleService.getAllModule();
        return new ResponseEntity<>(moduleList, HttpStatus.OK);
    }

    // Find modules
    @ApiOperation(response = ReplicaModule.class, value = "Find Module") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findModule(@Valid @RequestBody FindModule findModule) throws Exception {
        List<ReplicaModule> moduleList = moduleService.findModules(findModule);
        return new ResponseEntity<>(moduleList, HttpStatus.OK);
    }

}