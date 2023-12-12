package com.weekytripstravelcrm.util;

import com.weekytripstravelcrm.entity.ItineraryEntity;
import com.weekytripstravelcrm.entity.PackageEntity;
import com.weekytripstravelcrm.model.ItineraryModel;
import com.weekytripstravelcrm.model.PackageModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PackageUtil {

    private static final int defaultInitialId = 120923;

    public static boolean isValidModel(PackageModel model) {
        return !isStringNullOrEmpty(model.getPackageName()) &&
                !isStringNullOrEmpty(model.getCity()) &&
                !isStringNullOrEmpty(model.getDescription()) &&
                model.getTravelDays()>=1 &&
                model.getItinerary()!=null && model.getItinerary().size()==model.getTravelDays() &&
                !isStringNullOrEmpty(model.getImage()) &&
                !isStringNullOrEmpty(model.getInclusions())&&
                !isStringNullOrEmpty(model.getExclusions()) &&
                !isStringNullOrEmpty(model.getCancellationPolicy()) &&
                !isStringNullOrEmpty(model.getImportantNotes()) &&
                model.getStartDate()!=null && model.getStartDate().after(new Date()) &&
                model.getEndDate()!=null &&
                model.getEndDate().after(model.getStartDate());
    }

    public static PackageEntity getPackageEntityFromModel(PackageModel model){
        PackageEntity entity = new PackageEntity();

        entity.setPackageName(model.getPackageName());
        entity.setCity(model.getCity());
        entity.setDescription(model.getDescription());
        entity.setTravelDays(model.getTravelDays());
        entity.setItinery(getItineraryEntityFromModel(model.getItinerary()));
        entity.setImage(model.getImage());
        entity.setInclusions(model.getInclusions());
        entity.setExclusions(model.getExclusions());
        entity.setCancellationPolicy(model.getCancellationPolicy());
        entity.setImportantNotes(model.getImportantNotes());
        entity.setStartDate(model.getStartDate());
        entity.setEndDate(model.getEndDate());
        entity.setTotalPackageCost(model.getTotalPackageCost());
        entity.setCommissionPercent(model.getCommisionPercent());
        entity.setDiscountPercent(model.getDiscountPercent());

        return entity;
    }

    public static PackageModel getPackageModelFromEntity(PackageEntity entity){
        PackageModel model = new PackageModel();

        model.setPackageName(entity.getPackageName());
        model.setCity(entity.getCity());
        model.setDescription(entity.getDescription());
        model.setTravelDays(entity.getTravelDays());
        model.setItinerary(getItineraryModelFromEntity(entity.getItinery()));
        model.setImage(entity.getImage());
        model.setInclusions(entity.getInclusions());
        model.setExclusions(entity.getExclusions());
        model.setCancellationPolicy(entity.getCancellationPolicy());
        model.setImportantNotes(entity.getImportantNotes());
        model.setStartDate(entity.getStartDate());
        model.setEndDate(entity.getEndDate());
        model.setTotalPackageCost(entity.getTotalPackageCost());
        model.setCommisionPercent(entity.getCommissionPercent());
        model.setDiscountPercent(entity.getDiscountPercent());

        return model;
    }

    public static List<ItineraryEntity> getItineraryEntityFromModel(List<ItineraryModel> itineryModelList){
        var itinerary = new ArrayList<ItineraryEntity>();
        itinerary.add(new ItineraryEntity());
        return itinerary;
    }

    public static List<ItineraryModel> getItineraryModelFromEntity(List<ItineraryEntity> itineryEntityList){
        var itinerary = new ArrayList<ItineraryModel>();
        itinerary.add(new ItineraryModel());
        return itinerary;
    }

    public static String generatePackageId(String prefix, Long currentMaxId){
        Long finalValue ;
        String finalGeneratedKey;

        KeyGenerator keyGenerator = new KeyGenerator();

        if (currentMaxId == null) {
            finalValue = keyGenerator.getNextPrimaryKey(PackageEntity.class,(long)defaultInitialId);
        } else {
            finalValue = keyGenerator.getNextPrimaryKey(PackageEntity.class,currentMaxId);
        }
        finalGeneratedKey = prefix + finalValue;
        return finalGeneratedKey;
    }

    private static boolean isStringNullOrEmpty(String toCheck){
        return toCheck == null ||
                toCheck.isEmpty() ||
                toCheck.isBlank();
    }
}
