package com.example.mydbproject.repository;

import com.example.mydbproject.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByWarehouseLocationContainingIgnoreCase(String q);
}