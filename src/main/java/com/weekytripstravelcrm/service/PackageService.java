package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.PackageEntity;
import com.weekytripstravelcrm.exception.InvalidPackageModelException;
import com.weekytripstravelcrm.exception.PackageNotFoundException;
import com.weekytripstravelcrm.model.PackageModel;
import com.weekytripstravelcrm.repository.PackageRepository;
import com.weekytripstravelcrm.util.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.weekytripstravelcrm.util.PackageUtil;

@Service
public class PackageService {
    Logger log= LoggerFactory.getLogger(PackageService.class);

    @Autowired
    private PackageRepository packageRepository;
    public PackageModel addPackage(PackageModel packageModel) throws InvalidPackageModelException {
        if(!PackageUtil.isValidModel(packageModel)){
            log.error("Package model is not valid");
            throw new InvalidPackageModelException();
        }

        PackageEntity packageEntity = PackageUtil.getPackageEntityFromModel(packageModel);

        Long currentMaxId = this.packageRepository.findMaxPackageId();
        String getPrimaryKey = PackageUtil.generatePackageId("WT", currentMaxId);
        packageEntity.setPackageId(getPrimaryKey);

        PackageEntity savedEntity = this.packageRepository.save(packageEntity);
        log.info("Package created");
        return PackageUtil.getPackageModelFromEntity(savedEntity);
    }

    public List<PackageModel> getAllPackages(){
        log.info("Fetching all the packages");
      Iterable<PackageEntity> listOfPackages=  this.packageRepository.findAll();
      if(!listOfPackages.iterator().hasNext()){
          log.error("No packages found in the Database");
          throw new PackageNotFoundException();
      }
      ArrayList<PackageModel> packagesToReturn = new ArrayList<>();
      for(PackageEntity packageEntity:listOfPackages){
          packagesToReturn.add(PackageUtil.getPackageModelFromEntity(packageEntity));
      }

      log.info("Returning all the found packages. Found " + packagesToReturn.size() + " packages");
      return packagesToReturn;
    }

    public PackageModel getPackageById(String Id) {
        log.info("Fetching package with ID: " + Id);
        Optional<PackageEntity> packageEntity = this.packageRepository.findById(Id);
        if (packageEntity.isPresent()) {
            log.info("Found package with ID: " + Id);
            return PackageUtil.getPackageModelFromEntity(packageEntity.get());
        } else {
            log.error("Failed to find package with ID: " + Id);
            throw new com.weekytripstravelcrm.exception.PackageNotFoundException();
        }
    }

    public void deletePackage(String id) {
        log.info("Fetching package with ID: " + id + " for deletion");
        Optional<PackageEntity> existingPackage = this.packageRepository.findById(id);

        if (existingPackage.isPresent()) {
            this.packageRepository.deleteById(id);
            log.info("Deleted package with ID: " + id);
        } else {
            log.error("Failed to find package with ID: " + id + " to delete");
            throw new com.weekytripstravelcrm.exception.PackageNotFoundException();
        }

    }

    public PackageModel updatePackage(String id , PackageModel updatedPackage) throws InvalidPackageModelException {
        log.info("Updating package with id: " + id);
        if(!PackageUtil.isValidModel(updatedPackage)){
            log.error("Invalid package model passed in for update");
            throw new InvalidPackageModelException();
        }

        Optional<PackageEntity> existingPackage = this.packageRepository.findById(id);
        PackageEntity packageEntity = null;
        if(existingPackage.isPresent()){
            packageEntity = existingPackage.get();
        }else{
            log.error("Failed to find package to update");
            throw new com.weekytripstravelcrm.exception.PackageNotFoundException();
        }
        packageEntity.setPackageName(updatedPackage.getPackageName());
        packageEntity.setCity(updatedPackage.getCity());
        packageEntity.setDescription(updatedPackage.getDescription());

        packageEntity.setTravelDays(updatedPackage.getTravelDays());
        packageEntity.setItinery(PackageUtil.getItineraryEntityFromModel(updatedPackage.getItinerary()));
        packageEntity.setImage(updatedPackage.getImage());
        packageEntity.setInclusions(updatedPackage.getInclusions());
        packageEntity.setExclusions(updatedPackage.getExclusions());
        packageEntity.setCancellationPolicy(updatedPackage.getCancellationPolicy());
        packageEntity.setImportantNotes(updatedPackage.getImportantNotes());
        packageEntity.setStartDate(updatedPackage.getStartDate());
        packageEntity.setEndDate(updatedPackage.getEndDate());
        packageEntity.setTotalPackageCost(updatedPackage.getTotalPackageCost());
        packageEntity.setCommissionPercent(updatedPackage.getCommisionPercent());
        packageEntity.setDiscountPercent(updatedPackage.getDiscountPercent());


        PackageEntity updatedEntity = this.packageRepository.save(packageEntity);
        log.info("Updated package with id: " + id);
        return PackageUtil.getPackageModelFromEntity(updatedEntity);
    }
}
