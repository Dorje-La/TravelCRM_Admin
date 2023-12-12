package com.weekytripstravelcrm.repository;
import com.weekytripstravelcrm.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,String> {
    List<Customer> findByName(String name);
    Customer findByEmail(String email);
    void deleteById(String id);
    void deleteByEmail(String email);

    @Query("SELECT MAX(CAST(SUBSTRING(c.customerId, 3) AS long)) FROM Customer c")
    Long findMaxCustomerIdAsLong();

}

