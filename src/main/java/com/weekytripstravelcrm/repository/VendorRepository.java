package com.weekytripstravelcrm.repository;
import com.weekytripstravelcrm.entity.Vendor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Integer> {
    List<Vendor> findByVendorCity(String VendorCity);
    Vendor findByVendorNameAndVendorCity(String VendorName, String VendorCity);
    @Query("SELECT MAX(CAST(SUBSTRING(v.vendorId, 3) AS long)) FROM Vendor v")
    Long findMaxVendorIdAsLong();

}
