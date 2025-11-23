package com.example.mydbproject.repository;

import com.example.mydbproject.model.CustomerAddressRef;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerAddressRefRepository extends JpaRepository<CustomerAddressRef, Long> {

    // Optional helper to find all addresses for a given customer
    List<CustomerAddressRef> findByCustomerId(Long customerId);
}