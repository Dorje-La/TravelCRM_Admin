package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.entity.PackageEntity;
import com.weekytripstravelcrm.entity.TripEntity;
import com.weekytripstravelcrm.exception.InvalidPackageModelException;
import com.weekytripstravelcrm.exception.PackageNotFoundException;
import com.weekytripstravelcrm.model.PackageModel;

import com.weekytripstravelcrm.service.PackageService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/package")
@SecurityRequirement(name = "test-swagger")
public class PackageController {
    Logger log= LoggerFactory.getLogger(PackageController.class);

    @Autowired
    private PackageService packageService;

    /**
     *
     * @param packageModel
     * Method to add package to the database
     *
     * @return ResponseEntity
     */
    @PostMapping(value = "/add-package" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPackage(@RequestBody PackageModel packageModel){
        try {
            PackageModel createdPackage = this.packageService.addPackage(packageModel);
            log.info("Package was successfully created");
            return ResponseEntity.ok(createdPackage);
        }
        catch (InvalidPackageModelException ex) {
            log.info("Invalid Package");
            return ResponseEntity.badRequest().body("Invalid Package Model");
        }
        catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Failed to add package");
        }
    }

    @GetMapping(value = "get-package/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPackage(@PathVariable(name="id") String Id){
        try {
            return ResponseEntity.ok(this.packageService.getPackageById(Id));
        }
        catch (PackageNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body("Failed to get package");
        }
    }

    @GetMapping(value="/get-packages" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllPackages() {

        try {
            List<PackageModel> list = packageService.getAllPackages();
            return ResponseEntity.ok(list);

        } catch (PackageNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Failed to get package");
        }
    }

    @DeleteMapping(value = "delete-package/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deletePackage(@PathVariable(name = "id") String id){
        try{
            this.packageService.deletePackage(id);
            return ResponseEntity.ok("Package deleted successfully.");
        }
        catch (PackageNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body("Failed to get package");
        }
    }

    @PutMapping(value = "/update-package/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePackage(@PathVariable(name="id") String id, @RequestBody PackageModel updatedPackage) {
        try{
            PackageModel model = this.packageService.updatePackage(id, updatedPackage);
            return ResponseEntity.ok(model);
        }
        catch (InvalidPackageModelException ex) {
            return ResponseEntity.badRequest().body("Invalid Package Model");
        }
        catch (PackageNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body("Failed to get package");
        }
    }
}
