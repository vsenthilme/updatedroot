package com.mnrclara.api.management.controller;

import com.mnrclara.api.management.model.mattergeneral.FavouritesMatterGenAcc;
import com.mnrclara.api.management.model.mattergeneral.FavouritesMatterGenImpl;
import com.mnrclara.api.management.model.mattergeneral.SearchFavouritesMatterGenAcc;
import com.mnrclara.api.management.service.FavouritesMatterGenAccService;
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
import java.text.ParseException;
import java.util.List;

@Slf4j
@Validated
@CrossOrigin(origins = "*")
@Api(tags = {"FavouritesMatterGenAcc"}, value = "FavouritesMatterGenAcc Operations related to FavouritesMatterGenAccController")    // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "FavouritesMatterGenAcc", description = "Operations related to FavouritesMatterGenAcc")})
@RequestMapping("/favouritesmattergenacc")
@RestController
public class FavouritesMatterGenAccController {

    @Autowired
    FavouritesMatterGenAccService favouritesMatterGenAccService;

    @ApiOperation(response = FavouritesMatterGenAcc.class, value = "Search FavouritesMatterGenAcc") // label for swagger
    @PostMapping("/findFavouritesMatterGenAcc")
    public List<FavouritesMatterGenImpl> findFavouritesMatterGenAcc(@RequestBody SearchFavouritesMatterGenAcc searchFavouritesMatterGenAcc) throws ParseException {
        return favouritesMatterGenAccService.findFavouritesMatterGenAcc(searchFavouritesMatterGenAcc);
    }

    @ApiOperation(response = FavouritesMatterGenAcc.class, value = "Create FavouritesMatterGenAcc") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postFavouritesMatterGenAcc(@Valid @RequestBody FavouritesMatterGenAcc newFavouritesMatterGenAcc) {
        FavouritesMatterGenAcc createdFavouritesMatterGenAcc = favouritesMatterGenAccService.createFavouritesMatterGenAcc(newFavouritesMatterGenAcc);
        return new ResponseEntity<>(createdFavouritesMatterGenAcc, HttpStatus.OK);
    }

    @ApiOperation(response = FavouritesMatterGenAcc.class, value = "Delete FavouritesMatterGenAcc") // label for swagger
    @DeleteMapping("")
    public ResponseEntity<?> deleteFavouritesMatterGenAcc(FavouritesMatterGenAcc favouritesMatterGenAcc) {
        favouritesMatterGenAccService.deleteFavouritesMatterGenAcc(favouritesMatterGenAcc);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}