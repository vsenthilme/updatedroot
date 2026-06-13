package com.tekclover.wms.core.controller;


import com.tekclover.wms.core.model.Almailem.*;
import com.tekclover.wms.core.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@Api(tags = {"User"}, value = "User Operations related to UserController")
@SwaggerDefinition(tags = {@Tag(name = "User", description = "Operations related to User")})
@RequestMapping("/almailem/spark")
public class AlmailemSparkController {

    @Autowired
    SparkService sparkGrLineService;

    //=======================================================================================================================


    //Get GrLine
    @ApiOperation(response = GrLineV2.class, value = "Spark FindGrLine")
    @PostMapping("/grline")
    public ResponseEntity<?> findGrLineV2(@RequestBody FindGrLineV2 findGrLineV2) throws Exception {
        List<GrLineV2> stagingLineV2 = sparkGrLineService.findGrLineV2(findGrLineV2);
        return new ResponseEntity<>(stagingLineV2, HttpStatus.OK);
    }

}
