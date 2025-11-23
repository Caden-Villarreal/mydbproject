package com.example.mydbproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_line_item")
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lineItemId;

    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "lens_id")
    private Lens lens;

    // Getters and setters
    public Long getLineItemId() { return lineItemId; }
    public void setLineItemId(Long lineItemId) { this.lineItemId = lineItemId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Orders getOrder() { return order; }
    public void setOrder(Orders order) { this.order = order; }

    public Lens getLens() { return lens; }
    public void setLens(Lens lens) { this.lens = lens; }
}
