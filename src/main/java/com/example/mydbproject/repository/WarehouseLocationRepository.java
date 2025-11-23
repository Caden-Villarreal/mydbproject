package com.example.mydbproject.repository;

import com.example.mydbproject.model.WarehouseLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseLocationRepository extends JpaRepository<WarehouseLocation, Long> {
}