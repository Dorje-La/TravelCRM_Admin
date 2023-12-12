package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.entity.PackageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends CrudRepository<PackageEntity , String> {

    @Query("SELECT MAX(CAST(substring(p.packageId, 3) AS long)) FROM PackageEntity p")
    Long findMaxPackageId();
}
