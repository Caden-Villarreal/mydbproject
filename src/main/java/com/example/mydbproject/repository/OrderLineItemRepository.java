package com.example.mydbproject.repository;

import com.example.mydbproject.model.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Long> {

    // Find line items by Lens ID
    List<OrderLineItem> findByLensLensId(Long lensId);

    // Find line items by Order ID
    List<OrderLineItem> findByOrderOrderId(Long orderId);
}