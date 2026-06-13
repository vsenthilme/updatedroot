package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.route.AddRoute;
import com.courier.overc360.api.idmaster.primary.model.route.Route;
import com.courier.overc360.api.idmaster.primary.model.route.UpdateRoute;
import com.courier.overc360.api.idmaster.primary.model.statusevent.AddStatusEvent;
import com.courier.overc360.api.idmaster.primary.model.statusevent.StatusEvent;
import com.courier.overc360.api.idmaster.primary.model.statusevent.UpdateStatusEvent;
import com.courier.overc360.api.idmaster.replica.model.route.FindRoute;
import com.courier.overc360.api.idmaster.replica.model.route.ReplicaRoute;
import com.courier.overc360.api.idmaster.replica.model.statusevent.FindStatusEvent;
import com.courier.overc360.api.idmaster.replica.model.statusevent.ReplicaStatusEvent;
import com.courier.overc360.api.idmaster.service.RouteService;
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
@Api(tags = {"Route"}, value = "Route related to RouteController")
@SwaggerDefinition(tags = {@Tag(name = "Route", description = "Operations related to Route")})
@RequestMapping("/route")
@RestController
public class RouteController {
    @Autowired
    private RouteService routeService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create Route
    @ApiOperation(response = Route.class, value = "Create New Route")
    @PostMapping("")
    public ResponseEntity<?> createRoute(@Valid @RequestBody AddRoute addRoute, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Route newRoute = routeService.createRoute(addRoute, loginUserID);
        return new ResponseEntity<>(newRoute, HttpStatus.OK);
    }

    //Update Route
    @ApiOperation(response = Route.class, value = "Update Route")
    @PatchMapping("/{routeId}")
    public ResponseEntity<?> patchRoute(@PathVariable String routeId, @RequestParam String companyId, @RequestParam String languageId,
                                        @RequestParam String loginUserID, @RequestBody UpdateRoute updateRoute)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Route updatedRoute= routeService.updateRoute(companyId, languageId, routeId,updateRoute, loginUserID);
        return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
    }

    //Delete Route
    @ApiOperation(response = Route.class, value = "Delete Route")
    @DeleteMapping("/{routeId}")
    public ResponseEntity<?> deleteRoute(@PathVariable String routeId, @RequestParam String companyId, @RequestParam String languageId,
                                         @RequestParam String loginUserID) {
        routeService.deleteRoute(companyId,languageId, routeId,loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Route Details
    @ApiOperation(response = ReplicaRoute.class, value = "Get all Route Details")
    @GetMapping("")
    public ResponseEntity<?> getAllRouteDetails() {
        List<ReplicaRoute> routeList = routeService.getAll();
        return new ResponseEntity<>(routeList, HttpStatus.OK);
    }

    //Get Route
    @ApiOperation(response = ReplicaRoute.class, value = "Get a Route")
    @GetMapping("/{routeId}")
    public ResponseEntity<?> getRoute(@PathVariable String routeId, @RequestParam String companyId, @RequestParam String languageId) {
        ReplicaRoute dbRoute = routeService.getReplicaRoute(companyId,languageId,routeId);
        return new ResponseEntity<>(dbRoute, HttpStatus.OK);
    }

    //Find Route
    @ApiOperation(response = ReplicaRoute.class, value = "Find Route")
    @PostMapping("/find")
    public ResponseEntity<?> findRoute(@Valid @RequestBody FindRoute findRoute) throws Exception {
        List<ReplicaRoute> routeList = routeService.findRoute(findRoute);
        return new ResponseEntity<>(routeList, HttpStatus.OK);
    }
}
