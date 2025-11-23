package com.example.mydbproject.repository;

import com.example.mydbproject.model.Lens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LensRepository extends JpaRepository<Lens, Long> {
}