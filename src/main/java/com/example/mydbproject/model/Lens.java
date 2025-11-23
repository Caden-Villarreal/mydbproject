package com.example.mydbproject.model;

import jakarta.persistence.*;

@Entity
public class Lens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lensId;

    private String brand;
    private String lensType;
    private Double price;

    // Getters and setters
    public Long getLensId() { return lensId; }
    public void setLensId(Long lensId) { this.lensId = lensId; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getLensType() { return lensType; }
    public void setLensType(String lensType) { this.lensType = lensType; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}