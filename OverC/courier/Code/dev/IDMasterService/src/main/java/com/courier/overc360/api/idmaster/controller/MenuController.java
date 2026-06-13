package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.menu.AddMenu;
import com.courier.overc360.api.idmaster.primary.model.menu.Menu;
import com.courier.overc360.api.idmaster.primary.model.menu.UpdateMenu;
import com.courier.overc360.api.idmaster.replica.model.menu.FindMenu;
import com.courier.overc360.api.idmaster.replica.model.menu.ReplicaMenu;
import com.courier.overc360.api.idmaster.service.MenuService;
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
@Api(tags = {"Menu"}, value = "Menu  Operations related to MenuController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Menu", description = "Operations related to Menu")})
@RequestMapping("/menu")
@RestController
public class MenuController {

    @Autowired
    MenuService menuService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------*/

    // Create new Menu
    @ApiOperation(response = Menu.class, value = "Create Menu") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postMenu(@Valid @RequestBody AddMenu addMenu, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Menu menu = menuService.createMenu(addMenu, loginUserID);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    // Create Menu-bulk
    @ApiOperation(response = Menu.class, value = "Create bulk Menu") // label for swagger
    @PostMapping("/bulk")
    public ResponseEntity<?> postBulkMenu(@Valid @RequestBody List<AddMenu> addMenuList, @RequestParam String loginUserID)
            throws InvocationTargetException, IllegalAccessException, IOException, CsvException {
        List<Menu> createdMenuId = menuService.createMenuBulk(addMenuList, loginUserID);
        return new ResponseEntity<>(createdMenuId, HttpStatus.OK);
    }

    // Update Menu
    @ApiOperation(response = Menu.class, value = "Update Menu") // label for swagger
    @PatchMapping("/{menuId}")
    public ResponseEntity<?> patchMenu(@PathVariable Long menuId, @RequestParam Long subMenuId, @RequestParam Long authorizationObjectId,
                                       @RequestParam String companyId, @RequestParam String languageId,
                                       @Valid @RequestBody UpdateMenu updateMenu, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Menu updatedMenu = menuService.updateMenu(languageId, companyId, menuId, subMenuId, authorizationObjectId, loginUserID, updateMenu);
        return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
    }

    // Delete Menu
    @ApiOperation(response = Menu.class, value = "Delete Menu") // label for swagger
    @DeleteMapping("/{menuId}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long menuId, @RequestParam Long subMenuId, @RequestParam Long authorizationObjectId,
                                        @RequestParam String companyId, @RequestParam String languageId, @RequestParam String loginUserID) {
        menuService.deleteMenu(languageId, companyId, menuId, subMenuId, authorizationObjectId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*--------------------------------------------------REPLICA------------------------------------------------------*/

    // Get All Menu Details
    @ApiOperation(response = ReplicaMenu.class, value = "Get all Menu details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllMenus() {
        List<ReplicaMenu> menuidList = menuService.getAllMenuDetails();
        return new ResponseEntity<>(menuidList, HttpStatus.OK);
    }

    // Get a Menu
    @ApiOperation(response = ReplicaMenu.class, value = "Get a Menu") // label for swagger
    @GetMapping("/{menuId}")
    public ResponseEntity<?> getMenu(@PathVariable Long menuId, @RequestParam Long subMenuId, @RequestParam Long authorizationObjectId,
                                     @RequestParam String companyId, @RequestParam String languageId) {
        ReplicaMenu menuReplica = menuService.getMenuReplica(languageId, companyId, menuId, subMenuId, authorizationObjectId);
        return new ResponseEntity<>(menuReplica, HttpStatus.OK);
    }

    // Find Menu
    @ApiOperation(response = ReplicaMenu.class, value = "Find Menu") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findMenu(@Valid @RequestBody FindMenu findMenu) throws Exception {
        List<ReplicaMenu> menuList = menuService.findMenus(findMenu);
        return new ResponseEntity<>(menuList, HttpStatus.OK);
    }
}