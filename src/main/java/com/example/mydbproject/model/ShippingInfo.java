package com.example.mydbproject.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "shipping_info")
public class ShippingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shippingId;

    private String carrier;
    private String trackingNumber;
    private LocalDate shipDate;
    private String status;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    // Getters and setters
    public Long getShippingId() { return shippingId; }
    public void setShippingId(Long shippingId) { this.shippingId = shippingId; }

    public String getCarrier() { return carrier; }
    public void setCarrier(String carrier) { this.carrier = carrier; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public LocalDate getShipDate() { return shipDate; }
    public void setShipDate(LocalDate shipDate) { this.shipDate = shipDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Orders getOrder() { return order; }
    public void setOrder(Orders order) { this.order = order; }
}